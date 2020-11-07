/* Initial version written by Ron Goettler August, 2002 implementing model suggested by Uday Rajan and Christine Parlour. */

/* THIS FILE IS  #include 'D BY OTHERS WITH BUNCH OF  #define 'S    */

/* GetEq.c is a standalone with the #define's and this file's code. */

/* **************************************************************** */
/* stand alone c code to compute equilibrium in limit order market  */
/* convergence in each states Prob(execute) & E(price-PVmu|execute) */

/* GetEq_w.c has code for convergence in continuation values        */
/* However, since a traders continuation value (ie, w) depends      */
/* on the unknown execution probability and the known surplus       */
/* it makes sense to lump all traders with same (price, q, book)    */
/* into same state with respect to the unknown   Prob(execute).     */
/* Also, can now have new traders drawn from continuous PV cdf.     */

/* compile with:    gcc -O3 -ffast-math -o GetEq GetEq.c -lm        */

/*
  NOTES on Markov transition for PVmu:
  ===================================
  1. Could do either a random walk OR a finite number of states with fully specified transition matrix, or a mixture of the two.
  2. If use a random walk component then need states to be defined as ticks away from PVmu.
  3. Could have random walk with bigger jumps by processing more "jump draws" per period.  Indeed, #jump draws could be random each period.
  4. Could also have a "whopping big jump" with very small probability.  Such a jump will be accompanied by an influx of traders
     which will clear all current traders off the books.  These traders will not be modelled
     Limit orders on the opposite side of the WBJ will all be dropped.
  5. For buyer with PV = $15 and p=$13 (ie. 3rd tick) his EV = cs*e, where cs= (15+j*ticksize)-p. j=E(jumps|execute). j<0 for buyer.
     Suppose first in queue.  Trader starts at s =(Bt, p=2,q=0,e, j).  If exec from s, update (average) e with 1.0 and j with 0.
     IF PVmu jumps down, trader gets new stat8e s'=(Bt',p=3,q=0,e',j').  Update old s->e with e', j with j'+1. Bt'=Bt shift left.
     IF PVmu jumps up,   trader gets new state s'=(Bt',p=1,q=0,e',j').  Update old s->e with e', j with j'-1. Bt'=Bt shift right.

     IF PVmu jumps up   when already at s = (Bt, p=0, q=x,e, j) then update e with 1, j with j-1.  Left  shift Bt, p=0  limit buys dropped.
     IF PVmu jumps down when already at s = (Bt, p=nP,q=x,e, j) then update e with 0, j with j+1.  Right shift Bt, p=nP limit asks dropped.
     Number of such out-of-bound order drops should be minimal.  Keep track of their frequency.

  6. Q. How should I get initial values of e and j for new states?  Need to be sure initial E(value) > true E(value).
     A. assume j=0 since this will over-estimate the value of the state.

  7. Sequence:  1) process exog drops & PV jumps,  2) new trader,  3) update trader list and prev period states

  NOTES on Convergence Criteria:
  =============================
  1. use Pakes-McGuire criteria -- see end of main().
  2. question of how to construct/store weights:
     (a) use visitation frequency s->NN as the relevant count.
     (b) use  checked   frequency s->NC as the relevant count.
         s->NC is EASIER, since all increments occur in GetState(), 
	 s->NC also more desirable theoretically --> want convergence at states checked but not visited, as well as at states visited.
	 s->NC used to be a short int, but must be either long unsigned, or double to accomodate higher values, if used as weight.

*/

/*
  -------------------------   Summary of MAJOR CHANGE June 2006  -----------------------------
  -------------------------   Summary of MAJOR CHANGE June 2006  -----------------------------
  Add New versus Returning trader:  tt->new  (perhaps replacing  q in pHT[][nQ]...
  Retained order beliefs separate from new orders
  Also need the expected value of the replacement order PRIOR to cancelling current order.
  Then, the replacement order can be determined, either the same as a NEW trader
  or, perhaps, conditioning on the price at which order was just cancelled.

  Hence, new trader needs TWO values:  value of retained order, expected value of replacement order.
  The latter value can be updated regardless of action, by hypothetically cancelling the order and 
  using the UNDISCOUNTED s->w of what would have been the optimal replacement order.
  The value of the retained order, however, can only be updated if the order is INDEED retained.
  The (believed) value of retaining order is used to update s->w of the incoming unfilled order (which itself may be either a new or a retained order).
  The action of this trader is then updated to the "retained" order state so that the next outcome's value
  (execution or next decision) updates the value of having retained this order, after discounting by the elapsed time.
  We do NOT need separate beliefs for E(v) for returning traders since market orders are only submitted AFTER cancelling the order.
  Upon cancellation, the returning trader becomes a NEW trader who then uses the post-cancellation book to determine his action.

  Of course, if we were to allow returning traders to condition on their "private" history (versus market history that we currently use)
  then post-cancel traders would NOT be identical to NEW ARRIVALS.  Hence they would need their "own" beliefs about E(v) as well as about
  the payoff to each possible order.


  Code can be made more efficient by summarizing the "market state" PRIOR to hypothetical action (new or retained).
  State will then be a function of this "market state" and the "action state".
  The "Market" state will be SAME for all possible actions.
  Any censoring of the Market state can be done ONCE outside of the potential actions loop, with the censored outcome sent to GetState().
  Indeed since the Action can be an Index, the computation of the HASH value can be based only on Market state and computed ONCE too.
*/



#define min(x,y)   ((x) < (y) ? (x) : (y))
#define max(x,y)   ((x) > (y) ? (x) : (y))
#define RandU      ((double)rand()/(double)RAND_MAX)   /* Unif(0,1) */
#define LoU        1e-16           /* bound RandU to bound t->again and such */
#define HiU        (1.0 - 1e-16)

#define w2min      0.1         /* minimum sigma(Ew) to be used for ChiSq in WriteFiles() */
#define w2mult     1.0         /* w2 *= w2mult in WriteFiles() to make ChiSq criteria easier to satisfy (arbitrary accounting for var(Ew) being ESTIMATE only of true var(w) */

#define out_2nd_best     0     /* # of decisions for which 2nd best written to proj/xxx/2nd_best.[odd,even] */

#define useNNtt    0           /* if nonzero, use ALWAYS/NEVER tests via s->NCtt NNtt NCtj NNtj.   if 0, saves 20 bytes (5 ints) per state.  <--- MUST CHECK WHETHER STILL VALID */
#define useNe      0           /* if nonzero, track #executions from each state.  Required if UPDATE_Ne>0                                    <--- MUST CHECK WHETHER STILL VALID */
#define usePN      0           /* if nonzero, compare s->w with  (s->Pw*s->PN + s->Ew)/(s->PN + s->NN).  They should be within machine precision 1e-15 */

/* #include <pthread.h> */
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>
#include <signal.h>

#include "invcdfn.c"     /* Vassillis' pheev() and phinv() for Normal CDF and inverse CDF, respectively */

/* export MALLOC_CHECK_=1;  OR  compile with  gcc -lmcheck */
#define   CHECK  1
short int DEBUG = 0;

/* eUB is matrix of LOWER bounds for E(arrival time of trader for whome taking other side of order gives payoff>0) */
double  eUB[2][32][nP];  /* [2]: Buy/Sell. [32]: is queue [nP] is price.   intialized in main(), used in GetState() to get initial s->w */

#if use_w0sell
double        w0sell[nTmax][nPmax][nPmax][nPmax][nPV];   /* initial beliefs FOR SELLER limit & non-orders.   Updated in WriteFiles() */
double        N0sell[nTmax][nPmax][nPmax][nPmax][nPV];   /* SELLS only, so currently REQUIRE SYMM */
double        w2sell[nTmax][nPmax][nPmax][nPmax][nPV];   /* Declaring w2sell,M0sell within WriteFiles() causes seg.fault on 32-bit systems.  Why??   instead global. */
unsigned int  M0sell[nTmax][nPmax][nPmax][nPmax][nPV];   /* Should consider [H_jumps] if inspecting s->w shows sensitive to H[0][0] when nHT[informed]>0 */
#endif
short int UPDATE_PVT[nTmax][nPV], UPDATE_mkt[nT+1];      /* =0 if beliefs to be fixed (while updating beliefs for deviators) */

double Ninformed;                   /* used in GetState() as weight for conditioning variables for initial E(v) */
short int Ht[4][nRT], Hz[4][nRT];   /* nH+1<4 required. Ht is Trans.History bins of discretized RT, tracked regardless of nH, but only conditioned in GetState if nH>0. */
unsigned int jj;                    /* Ht[0][]= cum -J->n.  Ht[1][]= last trans.price.  Ht[2][]=cum net buys.  Ht[3][]= signed order last trans (1=mkt buy, -1= mkt sell) */
FILE *outNew=NULL, *outEw=NULL;     /* jj= count Big Iterations.   outNew is OPTIONAL ascii file of new states.  outEw used for convergence check. */
unsigned int N_s[26]={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}; /* [0]= count new states, [1]= count checked states, [2]= total States last jj, [3,4]= max book depth 1 side, 2 side, [5]= AVAILABLE, [6]=max #non-orders, [7]=truncated tp for limitorders in GetState(), [8,9]= adjustments in s->w for Non, RETAINED orders, [10,11]= #vJ,#MO, [12,13,14,18]= new s using w0sell,eUB,sx,E(v) [15,16,17]= checked s using w0sell,eUB,sx[19]=checked using w0sell[bid=0][ask=0], [20,21]= new,checked non-orders using fabs(pv), [22]= #decisions by traders for whom updating s->w & InsertNewState=y, [23]=#states NOT new checked during decisionmaking [24]=#orders submitted to be updated [25]=#orders updated */

/* #define useNX 1 */  /* if defined, then include #times each state is CHOSEN -- for debugging, since I think some chosen states are never getting updated.  Compare s->NX to s->NN.  Requires UPDATE_s0 = 1 so s->NN++ */
  

struct trader {           /* trader could specify simply a state, but then each state must list  pv,p,q.  This alternative worse since more states than active traders. */
  struct trader *next;    /* pointer to next trader  */
  struct state *s;        /* current state           */
  struct state *s0;       /* initial state. For comparing  Ew/NN  to  w */
  struct state *si;       /* truly initial state that NEVER gets reset, even if UPDATE_s0 = 1 */
  double again;           /* time until next action, always measured relative to current time */
  double since;           /* time since prev action, always measured relative to current time */
  double since0;          /* time since INITIAL order */
  double sincei;          /* time since INITIAL order, NOT updated if UPDATE_s0 */
  double rtime0;          /* initial realtime         */
  double CS;              /* realized discounted CS thus far (used by LARGE traders) */
  double CSu;             /* real.  Undiscounted CS thus far (used by LARGE traders) */
  double Ev;              /* E(v) rel. to last observ.*/
  double pv;              /* initial pv, tick units, inclusive of PVmu.  DOES NOT account for differences in current v and last observed v */
  short int pvtype;       /* index of pv type.   pv = PV[pvtype] */
  short int z;            /* 0 if SMALL (single share) trader, 1 for LARGE trader (in which case t->q is #remaining shares, NOT position in queue */
  short int buy;          /* 1 if buyer, 0 if seller */
  short int p;            /* tick index of p         */
  short int q;            /* queue position          */
  short int ret;          /* 0 if NEW ARRIVAL (or identical to new arrival),  1 if returning to market and RETAINING order, 2 if returning to market and CANCELLING order, 3 if E(v) for market order */
  short int type;         /* trader type: index which is translated via global  nBT[], vlagT[] into information status */
  short int tB;           /* access to book:  allows nB to vary across traders.  8,6,4, or 2  would be typical.  See GetState() */
  short int tH;           /* access to History:  allows nH to vary across traders.  See GetState() */
  short int tremble;      /* 2 if trader just trembled, 1 if trader EVER trembled, 0 if NEVER trembled */
  short int aggress;      /* aggressiveness of limit order, in terms of ticks above LL (after converting asks to bids) */
  short int v;            /* last observed v, used to shift B[] in GetState */
  short int vlag;         /* # periods since v observed.   AFTER making optimal choice,  t->v  will store initial discrepancy between observed and actual v */
  int j;                  /* count actual PV jumps   */
  int jlag;               /* count observed  jumps   */   /* NOTE: instead of t->vlag & t->tB could use vlagT[t->type] & nBT[t->type], as done for nTHT */
  unsigned int period;    /* j period arrived        */
  short int n;            /* # arrivals to market    */
#if nL>0
  short int L[nL];        /* lagged order information.  zeros if state used by new trader */
#endif
#if Ns0
  struct state *sN[Ns0];
  double    sinceN[Ns0];
#endif
};                        /* jlag NOT needed anymore, since exog delta depended on it, but no longer using exog delta */

struct state {            /* could add: pvtype, p, q, buy, etc.  to struct state to make it a full "state vector", but not needed since always access via pHT[][] indexed by these omitted var. */
  struct state *next;     /* pointer to next state in bucket */
  short int B[nB];        /* bid,ask,  depth at bid,ask,  depth off bid,ask, 2nd most agg. prices buy/sell side.  nB=2 or 4 or 6 or 8  */
  short int H[nH][nRT];   /* H = Trans. History       */
  short int L[nL];        /* lagged order information.  zeros if state used by new trader */
  unsigned int NC;        /* Number times checked     */
  unsigned int N;         /* Number times visited     */
  unsigned int NN;        /* if UPDATE_s0 = 0, NN is #Traders inititial action here.  If UPDATE_s0 = 1, NN is #times s visited (initially, or subsequently) */
  unsigned int NT;        /* # Trembles TO here.  NOTE:  Ntremble++ upon "entry" whereas N,NN++ upon UPDATE.  Hence, Ntremble>NN possible.  Okay, given "loose" use in GetFiles() */
#if usePN>0
  unsigned int PN;        /* s->N at start of nJ */
  unsigned int SSN;       /* #times chose this state upon 1st return after SS (ie, when tt->si == SS && tt->n==2) */
  double SSEw;            /* cumsum realized payoff when chose this state upon 1st return after SS */
#endif
#if useNe>0
  unsigned int Ne;        /* # executions directly from this state */
#endif
#if useNNtt>0
  unsigned int NCtt;      /* NC for new arrivals only */
  unsigned int NNtt;      /* # times s is highest utility, new arrivals.  Upon convergence, for each state either NNtt == NCtt  or  NNtt == 0 */
  unsigned int NCtj;      /* NC for returning traders */
  unsigned int NNtj;      /* # times s is highest utility, returning tj.  Upon convergence, for each state either NNtj == NCtj  or  NNtj == 0 */
#endif
#ifdef useNX
  unsigned int NX;        /* #times each state is CHOSEN -- for debugging, since I think some chosen states are never getting updated.  Compare s->NX to s->N */
#endif
  double w;               /* value of state for which convergence obtained */
  double Ew;              /* cumulative  realized outcomes    from orders initiated here over last nJ periods */
  double w2;              /* cumulative (realized outcomes)^2 from orders initiated here over last nJ periods */
  double Eoptimal;        /* if -88.x NEW state, else percent of times s chosen (pre-tremble) compared to times checked (NC).  CURRENTLY ONLY VALID if Nreset = 1 (N-NT times optimal) or UPDATE_s0=1 (NN-NT times optimal) */
  double Pw;              /* w from previous jj */
};

struct vJ {               /* list of v jumps over last nLag units realtime */
  struct vJ *next;        /* each trader conditions on a coarsening of this timeline - with events lumped into bins of length nRT > 0.0 */
  struct vJ *prev;
  short int n;            /* #ticks v jumped (<0 for decline in v) */
  double RT;
};                        /* J1 will point to most recent event.  New events will be recycled from tail, pointed to by Jx. */
struct vJ *J1, *Jx, *J;   /* New memory for event created if (Realtime - Jx->RT) < nLag */

struct MO {               /* list of Market Orders over last nLag units realtime */
  struct MO *next;        /* each trader conditions on a coarsening of this timeline - with events lumped into bins of length nRT > 0.0 */
  struct MO *prev;
  short int p;            /* transaction price */
  short int sof;          /* signed order flow */
  short int z;            /* 1 if mkt order submitted by large (tz) trader, else 0 */
  double RT;
};                        /* M1 will point to most recent MO.  New events will be recycled from tail, pointed to by Mx. */
struct MO *M1, *Mx, *M;   /* New memory for event created if (Realtime - Mx->RT) < nLag */

struct HT {                /* each (pv,p,q) 3-tuple is assigned its own Hash Table, pointed to by the array of HT pointers pHT */
  struct state *bucket[Prime-1];    /* could instead use one HT for ALL states but would then need to store (pv,p,q) in memory */
};                                  /* and would be even more reliant on having an efficient hash table.  Hence, I use the pHT */

struct HT *pHT[nPVmax][2-SYMM][nTmax][nQ][nPx1];  /* GLOBAL array of HT pointers.  nP+1 for last index since [nP] for NON-ORDER with revisit */

char InsertNewState = 'y'; /* in GetState() only insert new states into state list(s) if InsertNewState == 'y'. char= 1 byte */

unsigned int hscale[16]={397,25717,1597,12853,51437,102877,411527,823117,6421,1646237,3203,3292489,205759,6584983,13169977,797} ; /* use large primes to scale h */

struct state *S, *SS ;     /* Should NEVER reassign S.  Only assign TO S.   S must ALWAYS point to memory locations from initialization in main() */

#if usePN>0                /* usePN>0 does NOT require use of SS debugging code, but SS requires usePN */
struct state *SS ;         /* SS is for debugging.  SS is hardcoded to match a particular state that is having difficulty converging. */
unsigned int SSNN;         /* holds value of NN as of previous j in main j loop.     Enables detection of updating of state SS. */
double SSEw[8][2]={ {0.0,0.0}, {0.0,0.0}, {0.0,0.0}, {0.0,0.0}, {0.0,0.0}, {0.0,0.0}, {0.0,0.0}, {0.0,0.0} };  /* [][0]= Later Actions, [][1]= 1st action.  [1,2][]= payoffs, # by executed limits,  [3,4][]= payoffs, # upon return,   [5,6][]= payoffs,# by Mkt upon return ,   [7,8][]= payoffs,# by LIMIT upon return */
double SiEw[4][2]={ {0.0,0.0}, {0.0,0.0}, {0.0,0.0}, {0.0,0.0} };  /* [][0]= Returning tj, [][1]= NEW arrivals.  [1,2][]= payoffs, # by executed limits,  [3,4][]= payoffs, # by mkt upon return */
#endif

short int ReHash;      /* if 1 then recompute h, H[], sB[]  in GetState().  When getting optimal action, a1, h,H,B unchanged (except for SYMM) */
short int tL,tH;       /* governs range of valid limitorder ticks.  == LL, HL  if informed trader with vlag=0, else LL, HL shifted by E(v) */

/* HEY:  I removed all   state_v   stuff.   See  GetEq_continuous_code.c  (though it was ignored via  #if 0  )  */
/*       See  GetEq_NoFringe_code.c  for last version to actually USE  state_v  stuff. */

/* HEY:  also removed ALL  ns2  stuff that allowed traders to submit any TWO limitorders.  See same old versions as for state_v. */

#ifdef CheckSEGV
char buffer[30];  /* to check length of   sprintf(buffer,"%p",s)   as attempt to predict SEGV since they seem to occur (on elaine) when  s  is a long address */
#endif

/* void CaughtSEGV( int signo ) { signal(SIGSEGV, SIG_IGN);  printf("\n\n in CaughtSEGV when InsertNewState= %c, so skipping this state... \n\n", InsertNewState );
   fflush(stdout);  DEBUG = 99; } */

static struct state *GetState(t,Bt)   /* IMPORTANT: GetState() returns state CONDITIONAL on hypothetical current v = t->v, relative to ACTUAL current v */ 
     struct trader *t;		      /*            or belief about v, for traders who observe v with a lag. */ 
     short int Bt[nP+2];              /* t->v = 0 for informed t,   if uninformed t->v is last observed v relative to current v */
{
  static  unsigned int Hash;             /* should these declarations be static, since GetState() called so  ?? */
  static short int  H[nH][nRT], sB[nB];  /* Hash, H, sB are STATIC so retain value so only compute if needed as indicated by global ReHash */
  unsigned int h;
  struct HT *ht;
  short int p, q=0, bid, ask, buy, tp, tv, pvtype, doSYMM ;  /* B, buy, tp, tv, pvtype : due to SYMM */
#if nL>0
  short int L[nL];
#endif
  struct state *s, **sp, *sx;
  double pv;
#if eUBsimilar > 0
  short int da, db;
#endif
  sx= NULL;  /* if sx gets defined later, then sx is suitable state for initialization of new state (or modification of RETAINED order state) */

  /* printf("entered GetState with t: pvtype= %d  buy= %d  q=%3d  p=%3d  Bt= ",t->pvtype,t->buy,t->q,t->p);
     for(p=0; p<nP; p++)  printf(" %3d:%3d,", p, Bt[p]);  printf("\n");  fflush(stdout);  */
  
  /* tz->buy=0 always so never SYMM flip for LARGE guys. (buyers signalled by tz->pv > PVmu) */
  /* limit orders use buy/sell symmetry,  non-orders use PV symmetry,   mkt-orders (ie. E(v) ) use symmetry via signed-order of last trans */

  doSYMM = SYMM && ( t->buy
		     || (t->p==nP && (t->pv > PVmu + 1e-8 || (t->pvtype == nPV_1-t->pvtype && H_signed && Ht[3][0]== -1 && t->tH >1 )))  /* hence t->pv = 0 & NO RECENT TRANS will NOT use SYMM, but rare */
		     || (t->ret==3 && H_signed && Ht[3][0]== -1 && t->tH >1 )   );
  
  /* doSYMM = SYMM && (t->buy || (t->p==nP && t->pv > PVmu + 1e-8) || (t->ret==3 && H_signed && Ht[3][0]== -1 && t->tH >1) ); */

  /* PROBLEM: if states are "coarsenings" of many different states, then a given state can be checked from a variety of true states of the market. */
  /*          This is particularly an issue for Non-Orders which have No Priority and hence "compete" with limitorders from many different B[] */

  /* USED TO condition on priority (either at tick or overall).  Problem when not using the TRULY FULL BOOK was that
     DIFFERENT book information would be used to evaluate each action, since the COARSENING depended on the action.
     SOLUTION is to NOT condition on priority unless the FULL book is used, as in OLD CODE.  see GetEq_large_code.c or GetEq_large_code_NEW.c.

     Since we no longer condition on priority, t->q is no longer a state variable.
     Also, we now condition on the book PRIOR to the order being implemented, so that the coarsening is the same regardless of the order.
     (When we used the FULL book conditioning on book PRIOR or AFTER order implemented didn't matter, since no coarsening depended on the order.
  */
  
  /* NEW WAY: get censored Book info PRIOR to shift/flip, THEN shift/flip sB[] based on tv->v and doSYMM */
  /* OLD WAY: shift/flip B[] then censor B[] but that requires operations on ALL nP ticks -- much slower.  see GetEq_large_code.c */

  bid= Bt[nPx1];  ask = Bt[nP];  /* PRE-FLIP bid/ask */

  if(ReHash) {
    if(doSYMM) { sB[1] = nP_1 - bid;  sB[0] = nP_1 - ask; }
    else       { sB[0] = bid;         sB[1] = ask;        }

    for(p=2;p<nB;p++)   sB[p] = 0;  /* ALL traders see at least quotes.  Initialize other stuff to 0 */
  
    if (t->tB > 3) {
      if (bid != -1) sB[2 + doSYMM]= min(  Bt[bid], 15 );    /* depth at bid, truncated at 15 since more than 15 is very rare */
      if (ask != nP) sB[3 - doSYMM]= min( -Bt[ask], 15 ); }  /* depth at ask, store as positive number even though Bt[]<0 for asks */
    
    if (t->tB > 5) {
      q=0; for(p= bid-1; p>=0; p--) q+=Bt[p];   sB[4 + doSYMM]= min( q, 15 );   /* bid depth lower  than quote */
      q=0; for(p= ask+1; p<nP; p++) q+=Bt[p];   sB[5 - doSYMM]= min(-q, 15 ); } /* ask depth higher than quote */
    
    if (t->tB > 7) {
      if (sB[4]) { q=bid;  while(--q > -1 && Bt[q]==0) { }  if(doSYMM) sB[7]= nP_1 - q; else sB[6]= q; }  /* next highest tick with bids.    0 if none is okay since depth off quote also 0 */
      if (sB[5]) { q=ask;  while(++q < nP && Bt[q]==0) { }  if(doSYMM) sB[6]= nP_1 - q; else sB[7]= q; }  /* next lowest tick with asks. */
    }
  }

  if (doSYMM) {
    if(t->p==nP) tp = nP;                     /* FLIP price if NOT a Non-Order (nor LARGE trader) */
    else         tp = nP_1 - t->p;
    tv = -t->v;    pvtype= nPV_1 - t->pvtype; /* t->ret=3 --> E(v) state --> tp= 0 & pvtype= 0  <-- ENFORCED below. */
    pv = PVmu + PV[pvtype];                   /* initially  t->pv = PVmu + PV[t->pvtype]   */
    buy= 0;   }
  else {
    tp = t->p;
    tv = t->v;
    pv = t->pv;    pvtype = t->pvtype;        /* pv NOT shifted by t->v */
    buy= t->buy; }


  /* printf(" t->buy = %d  t->v= %3d  tv= %3d  p= %2d %2d  t->q= %2d \n", t->buy, t->v, tv, t->p, tp, t->q); */  /* could add H */


  /* NEW limitorders already restricted via tL,tH in main() if |tv| big then can be off-grid.  If so, assign VERY low payoff and return. */
  if (tv && tp != nP && t->ret!=3) {  /* tp = nP --> NON-ORDER.  t->ret==3 --> "market order" (ie, s->w = E(current v)).  BOTH CASES: Shift B[] but do NOT shift tp, do NOT truncate tv */
    tp -= tv;
    if(tp<0 || tp>nP_1) {  N_s[7]++;  s=S;  S->w = -999.9;  return(s); }     /* N_s[7] reports #off-grid limitorders */
  }

  if (t->ret==3) { tp = 0; pvtype= 0; pv= t->pv;}         /* s->w = E(current v) relative to last obs, is converted into market order payoffs to get optimal actions in main() */  

  if (ReHash) {  /* Tempting to combine tv<0 and tv>0 since both use  -= tv  BUT DON'T since the adjustments of sB[] when quotes off-grid are easier to follow when the direction is known */
    if (tv>0) {                                             /* t considering current v to be  tv  ticks ABOVE mid-tick.  ALL prices are LOWER relative to believed v than rel.to current v */
      if(sB[0]!=-1) { sB[0] -= tv;  if(sB[0] < 0) { sB[0]= -1; if(nB>3) sB[2]=0; if(nB>5) sB[4]=0; if(nB>7) sB[6]=0; } } /* lower bid.  if off-grid then REMOVE buy side since too unaggressive to matter. */
      if(sB[1]!=nP) { sB[1] -= tv;  if(sB[1] < 0) { sB[1]=  0; } }           /* lower ask.  if off-grid then adjust ask to 0, but keep rest same.  THIS SHOULD BE RARE as long SOMEONE is informed to pick-off */
      if(t->tB > 7) {
	if(sB[4]) { sB[6] -= tv;  if(sB[6]<0) { sB[6]=0;  sB[4]=0; } }                 /* remove off-quote bids since too unaggressive */
	if(sB[5]) { sB[7] -= tv;  if(sB[7]<0) { sB[7]=0;  sB[3]+=sB[5];  sB[5]=0; } }  /* move "too aggressive" off quote asks to the "too aggressive" ask quote */
      } }
    if (tv<0) {                                             /* t considering current v to be  tv  ticks BELOW mid-tick.  ALL prices are HIGHER relative to believed v than rel.to current v */
      if(sB[1]!=nP) { sB[1] -= tv;  if(sB[1] > nP_1) { sB[1]= nP; if(nB>3) sB[3]=0; if(nB>5) sB[5]=0; if(nB>7) sB[7]=0; } }
      if(sB[0]!=-1) { sB[0] -= tv;  if(sB[0] > nP_1) { sB[0]= nP_1; } }
      if(t->tB > 7) {
	if(sB[5]) { sB[7] -= tv;  if(sB[7] > nP_1) { sB[7]=0;  sB[5]=0; } }
	if(sB[4]) { sB[6] -= tv;  if(sB[6] > nP_1) { sB[6]=0;  sB[2]+=sB[4];  sB[4]=0; } }
      } }
  }

#if nL>0                               /* t->vlag > 0 affects t->L[] after submit order.  Use t->L as-is (rel.to last obs.v when order was submitted) since these were the "state" values then */
  if (t->ret > 0 || t->n==1)
    for(p=0;p<nL;p++) L[p] = 0;        /* only use L for NEW limit/non orders by RETURNING traders (ie, t->ret = 0 & t->n>1 ) */
  else {  
    for(p=0;p<nL;p++) L[p] = t->L[p];  /* t->L[0] is signed price of previous order, [1~2] is bid~ask.  ie, nL=3 is only option thus far */
    if(doSYMM) {
      if(L[0] != nP)  {                /* prev order not a  Non-Order */
	if(L[0]<0)    L[0] += nP_1;    /* was sell order, flip to buy */
	if(L[0]>0)    L[0] -= nP_1; }  /* was sell order, flip to buy */
      p = L[2];
      L[2] = nP_1 - L[1];              /* ask becomes flipped bid */
      L[1] = nP_1 - p;                 /* bid becomes flipped ask */
    } }
#endif
  /* if (DEBUG && tp==nP)  printf("t->buy = %d  t->pv= %5.1f  pv= %5.1f  t->v= %d  tv= %d  t->p= %d  tp= %d \n", t->buy, t->pv, pv, t->v, tv, t->p, tp);  */

  bid = sB[0];   /* post  shift/flip */
  ask = sB[1];

  /* if (bid==0 || ask==nP_1-1)  { printf("\n\n HEY:  bid~ask = %d %d  but  should probably NEVER have bid = 0 or ask = nP-2.  Flip/Shift must be wrong... aborting ...\n\n",bid,ask );  exit(8); } */

  /* if (CHECK && nLag==0 && tv!=0) {printf("\n\n tv= %d  aborting in GetState.  t: v= %d  vlag= %d  p= %d  buy= %d  pvtype= %d  type= %d \n\n",tv, t->v, t->vlag, t->p, t->buy, t->pvtype, t->type); exit(9); } */

  /* printf("\n\n tv= %d  aborting in GetState.  t: v= %d  vlag= %d  p= %d  q= %d  buy= %d  pvtype= %d  type= %d \n\n",tv, t->v, t->vlag, t->p, t->q, t->buy, t->pvtype, t->type); */  

#if 0
  if (DEBUG) {
    printf(" buy= %d %d  p= %2d %2d  v= %2d %2d  ret= %d  bid~ask= %2d %2d    B:", buy,t->buy, tp,t->p,  tv, t->v, t->ret, bid,ask);
    for(p=0;p<nP;p++) if(Bt[p]) printf(" Bt[%d]= %2d",p,Bt[p]); 
    for(p=0;p<nB;p++) printf(" sB[%d]= %2d",p,sB[p]);         printf("\n");  }
#endif
  
  /* See  GetEq_large_code_NEW.c  or GetEq_large_code.c  for  Non-Strategic trader code roughly here.  Used   #define  e1v_e0else 1 */
  
  if(CHECK && ( pvtype<0 || pvtype>=nPV || buy<0 || buy>1 || t->type<0 || t->type>=nT || tp<0 || tp>nP )) {
    printf("\n HEY: index out of range:  pvtype= %d  buy= %d  t->type= %d  t->ret= %d  tp= %d,  t->p= %d  t->v,tv= %d %d  t->vlag= %d  t->buy= %d\n\n", pvtype,buy,t->type,t->ret,tp,t->p,t->v,tv,t->vlag,t->buy);  DEBUG =9; }
  
  
  /* see GetEq_large_code_NEW.c for code CONDITIONING ON PAST QUOTES (complicated by SYMM) 
     HERE IS EXAMPLE ILLUSTRATING SYMM and vX history.  Suppose the following:
     5 ticks, 0,...,4, with current v always at 2.  current bid,ask is 1, 2.
     Last observed v is -1 relative to current v (i.e. last observed is at tick 1 since tick 2 is current v.  hence v has increased. ).
     No other changes have occurred, hence the last observed bid,ask were 2,3 on last v's grid, but 1,2 on current v grid.
     To evaluate limit buy at 1, we convert the buy into a limit sell at 3 with a hypothetical bid,ask of 2,3.
     (SYMM --> hypothetical sell = nP-1 - real bid = 4-1 = 3)
     That's the easy part. The tough part is getting the hypothetical history.
         1) cum market buys become cum market sells, and vice-versa.
	 2) the last observed bid somehow becomes last observed ask.
	 BUT do we "flip" the increase in v to a decrease??   I think so.
	 First flip the last observed bid,ask but on the OLD v grid (2,3) giving hypothetical last bid,ask= 1,2 on old grid.
	 Next, shift according to DECREASE in v --> hypoth bid,ask = 2,3.    RIGHT?
  */

  if (ReHash) {  /* NOT TABIFIED */
    
  for(p=0;p<nHnRT;p++)  *(H[0]+p)= 0;      /* faster than for(q)for(p) H[q][p] = 0.  initialize even if t->tH>0 */
  
  if( t->tH /* >0 */ ) {                   /* nH: 1= jumps (informed only),  2= also trans.prices, 3= also NetCumBuys */
    for(p=0;p<nRT;p++) {                   /* Ht[0][]= cum -J->n.  Ht[1][]= last trans.price.  Ht[2][]=cum net buys.  Ht[3][]= signed order last trans (1=mkt buy, -1= sell) */
      if(H_jumps && t->vlag==0 /* || N0_v==0 */ ) { /* TEST: condition on jumps by uninformed (if N0_v==0) --> perfect learning? since -sum(Ht[0][]) IS current v */
	if(doSYMM) H[0][p] = -Ht[0][p];     /* INFORMED condition on jump hist (since uninformed see lagged v) ... */
	else       H[0][p] =  Ht[0][p];     /* ... UNLESS  H_jumps=0 (eg, if tz, & all tt informed --> condition on trans.history but not jump history) */ 
	H[0][p] = min( H_jumps, H[0][p]);
	H[0][p] = max(-H_jumps, H[0][p]);
      }                                    /* H[0][] = lagged v (informed only), H[1][] = signed last trans.price,  H[2][] = cum net buys */
      if( t->tH > 1 ) {                    /* nH: 1= jumps (informed only),  2= also trans.prices, 3= also NetCumBuys */
	if(Ht[3][p]==0) H[1][p]= -99;      /* Ht[3][p] = 0 signals NO transactions this RT[].  Encode as -99 for state lookup (no tv shifted prices will hit -99) */
	else {
	  if(doSYMM) {                     /* If SYMM: flip price first, then shift by tv (which itself already flipped).  Also flip SIGNED ORDER FLOW in Ht[3][] */
	    q = -Ht[3][p];                 /* local store of signed order flow:  flipped */
	    H[1][p]= nP_1 - Ht[1][p] -tv;  /* Ht[1][p] is most recent transaction price, relative to CURRENT v, in RT[p] bin */
	    if(t->tH==3) H[2][p]= -Ht[2][p]; }     /* flip net buys to net sells */
	  else {                           /* No SYMM flip */
	    q =  Ht[3][p];                 /* local store of signed order flow:  not flipped */
	    H[1][p]= Ht[1][p]-tv;          /* Ht[1][p] is most recent transaction price, relative to CURRENT v, in RT[p] bin */
	    if(t->tH==3) H[2][p]= Ht[2][p]; }   /* No flip net buys to net sells */
	  
	  H[1][p] = min( H_maxp, H[1][p]); /* truncate last trans.price --> prices > H_maxp are VERY RARE */
	  H[1][p] = max( H_minp, H[1][p]); /* truncate last trans.price --> prices < H_minp are VERY RARE */
	  if(H_signed) H[1][p] *= q ;      /* q is temp storage of signed order flow (-1 or 1) */
	} } }

#if 0  /* should I use this if large trader present ??  If never large trader, then RT[] only has 1 bin anyway */
    if(t->ret==3)  /* "mkt order beliefs", E(v) only really need condition on cum net buys since nLag, and very last trans.price.  right? */
      for(p=1;p<nRT;p++) {
	if( H[1][0]== -99 ) { H[1][0] = H[1][p]; }  H[1][p]=0;     /* H[1][0] = 1st valid Price (!= -99) & zeros-out H[1][p>=1] */
	if( t->tH == 3)     { H[2][0]+= H[2][p];    H[2][p]=0; } } /* H[2][0] = sum H[2][]               & zeros-out H[2][p>=1] */
#endif
    
    if(t->z && t->vlag) { /* LARGE trader uninformed about v also conditions on his OWN past orders, in global Hz[][].  In this case, Ht[][] is NET of Hz[][] */
      for(p=0;p<nRT;p++){ /* tz->buy=0 always so never SYMM flip for LARGE guys. (buyers signalled by tz->pv > PVmu) */
	if(Hz[3][p]==0) H[1][p]+= -9900;                                /* Hz[3][p] == 0 signals NO own transactions this RT[].   Encode -99 for state lookup */
	else {
	  q = Hz[1][p] -tv;
	  q = min( H_maxp, q); /* truncate last trans.price --> prices > H_maxp are VERY RARE */
	  q = max( H_minp, q); /* truncate last trans.price --> prices < H_minp are VERY RARE */
	  if(H_signed) q *=  Hz[3][p];     /* q is temp storage of signed order flow * last p */
	  H[1][p] += 100 * q;
	  if( t->tH == 3) H[2][p]+= 100 *  Hz[2][p];      /* cum net buys, if nHT[t->type]==3 */
	} } }  /* H,Hz <=99 & short int= -32768 to 32767 --> use H[][]+= 100*Hz[][] --> ONE short int reveals both H & Hz */
  }            /* end  if( nHT[] )  process conditioning */


  Hash = 0;                 /* get hash */
  for(p=0;p<nB;   p++)   Hash+=     sB[p] * hscale[p] ;
  for(p=0;p<nHnRT;p++)   Hash+= *(H[0]+p) * hscale[p+nB] ;
#if nL>0 
  for(p=0;p<nL;   p++)   Hash+=      L[p] * hscale[p+nB+nHnRT] ;
#endif
  }   /* end ReHash  NOT TABIFIED */
  
  
  ht= pHT[pvtype][buy][t->type][t->ret][tp];
  if (ht==NULL) {                   /* initialize HT for this trader */
    ht = malloc(sizeof(struct HT));  if (ht==NULL) { printf("Out of memory for new HT in GetState \n");  exit(8); }
    for (h=0; h<Prime; h++)  ht->bucket[h] = NULL;    /* initialize buckets */
    pHT[pvtype][buy][t->type][t->ret][tp] = ht;
  }
  
#if 0
  if (DEBUG ) {
    printf("\n GetState: pvtype= %d  buy= %d  t->type= %d   tp= %d,  t->p~ret= %d %d  t->v,tv= %d %d  t->vlag= %d  t->buy= %d  Hash=%10u  H=:", 
	   pvtype,buy,t->type,tp,t->p,t->ret,t->v,tv,t->vlag,t->buy, Hash);
    for(p=0;p<nHnRT;p++)      printf(" %3d",*(H[0]+p));       printf("  B:");
    for(p=0;p<nB;p++) if(sB[p])printf(" sB[%2d]= %3d,",p,sB[p]);
    /* for(p=0;p<nL;p++) if(sB[p])printf(" L[%2d]= %3d,",p,L[p]); */  printf("\n"); }
#endif

  h = Hash % Prime;
  s = ht->bucket[h];
  sp= ht->bucket+h;  /* if *sp instead of **sp, use  sp=NULL; */

  while (s!=NULL) {

#ifdef CheckSEGV
    /* elaine.tepper is giving SEGV errors while bigp.tepper does not, so code is probably okay */
    /* problem is probably either hardware (memory? motherboard?) or SuSE is bad ... */
    /* following code inspects length of address  s  since they SEGV appears to happen when it's a long address */
    if ( sprintf(buffer,"%p",s) > 16 ) {
      printf("\n SEGV??  s = %p  appears too long (%d) to be valid, so skipping it\n", s, sprintf(buffer,"%p",s) );  fflush(stdout);
      s = S;  S->w = -999.9;  return(s);      /* could instead set   s = NULL;   and proceed by re-initializing a state */
    }
    else   /* okay to proceed */
#endif
      {

      /* first coding attempt was to "catch" the SEGV via signal handler, but I was unable to avoid the termination */
      /*
	s = NULL;  p = -999;
	signal(SIGSEGV, CaughtSEGV);  printf("\nHere\n");
	p = s->B[0];    printf("Here 2  p = %d  DEBUG=%d\n", p, DEBUG);    fflush(stdout);
	signal(SIGSEGV, SIG_DFL);   printf("Here 3\n");
	if (p == -999) {
	printf("\n\n HEY: caught SEGV when InsertNewState= %c, so skipping this state...\n\n", InsertNewState );  fflush(stdout);
	s=NULL; }
	else {
	p = s->B[0];  printf("Here 4\n");
	printf("Here 5  s=%p p= %d\n", s,p);
	... continue with code below and close the else }
    */
      
      for (p=0; p<nB; p++)   if (s->B[p] != sB[p])  break;
      if (p==nB) {     /* s->B matches sB, now check s->H */
#if nL>0
	for(p=0; p<nL; p++)  if (s->L[p] != L[p])  break;
	if (p==nL) {   /* s->L matches L, now check s->H */
	  for(p=0;p<nHnRT;p++) if( *(s->H[0]+p) != *(H[0]+p) ) break;
	  if (p==nHnRT) break;  /* s is the desired state, break the while() */
	}
#else 
	for(p=0;p<nHnRT;p++) if( *(s->H[0]+p) != *(H[0]+p) ) break;
	if (p==nHnRT) break;  /* s is the desired state, break the while() */
#endif
      }
      sp= &s->next;           /* if get this far in while() then either B or X did not match s->B,X */
      s =  s->next;           /* if *sp instead of **sp, use  sp=s; */
    }
  }
  
  /* if(s==NULL) { printf(" h= %lu  sB=", h);  for(p=0; p<nB; p++)  printf(" %3d ", sB[p]);  printf("\n");} */
  
  /* if (s!=NULL) printf("s: s= %p  N=%u  NN=%u  w= %5.2f  t: pv= %5.2f %5.2f  pvtype= %d %d  buy= %d %d  p= %d %d ret= %d h=%u ht=%p\n",s, s->N,s->NN,s->w, t->pv, pv, t->pvtype, pvtype, t->buy, buy, t->p, tp, t->ret,h,ht );  */
  
  if(s!=NULL) {                                /* t->ret =2 always has InsertNewState = 'y' */

    /* if( (InsertNewState=='n' && t->tremble!=2) || t->ret==2) s->NC++; */  /* measure freq of being optimal using either (N-1-NT)/NC or (NN-NT)/NC */
    
    if(tp==nP && t->z==0 && s->w <= V0) {  /* printf("non-order  s->w = %5.2f changed to V0+.001  N= %u\n",s->w,s->N); */
      s->w = V0 + .00001;   N_s[8]++; }    /* note: non-order can become <0 if uninformed returning trader submits market-order which happens to have payoff < 0 */
  }
  else {                   /* s==NULL  ---->   create and initialize a new state */
    if (InsertNewState == 'y') {  /* NOTE: InsertNewState = 'n' if UPDATE_[] = 0 */
      N_s[0]++;     
      if( N_s[0] < 4) { printf(" New s: m l k p = %d %d %d %3d  B:", pvtype, t->type, t->ret, tp);  for(p=0;p<nB;p++) printf(" %3d", sB[p]);  printf(" H:");   for(p=0; p<nHnRT; p++) printf(" %3d", *(H[0]+p));  printf("\n"); }
      s = malloc(sizeof(struct state));  if (s==NULL) { printf("Out of memory for state \n");  exit(8); }
      *sp = s; }           /* INSERT s at end of list at ht->bucket[h] */
    else { s = S; }        /* S is GLOBAL state for temporary storage of new states which may not be visited */
    
    s->next = NULL;
    for(p=0; p<nB;    p++)       s->B[p] = sB[p];      /* copy current book (post censoring) into s->B */
    for(p=0; p<nHnRT; p++)  *(s->H[0]+p) = *(H[0]+p);  /* copy current H history to s->H */
#if nL>0
    for(p=0; p<nL;    p++)       s->L[p] = L[p];
#endif
    s->NC   = 1;           /* states can have their value checked without ever being chosen !! */
    s->N    = 1;  if(N0>0) s->N= N0;  if(N0<0) s->N= jj-N0;   /* use high initial s->N for slower (safer) convergence to avoid pessimistic beliefs */
    s->NN   = 0;
    s->NT   = 0;
#if usePN>0
    s->PN   = 0;
    s->SSN  = 0;
    s->SSEw  = 0.0;
#endif
#if useNe>0
    s->Ne   = 0;
#endif
#if useNNtt>0
    s->NCtt = 0;
    s->NNtt = 0;
    s->NCtj = 0;
    s->NNtj = 0;
#endif
#ifdef useNX
    s->NX   = 0;        /* #times each state is CHOSEN -- for debugging, since I think some chosen states are never getting updated.  Compare s->NX to s->N */
#endif
    s->w    = 0.0;
    s->Ew   = 0.0;
    s->w2   = 0.0;
    s->Eoptimal = -88.5;  /* -88.6 if initialized nonorder,  -88.7 if via w0sell, -88.8 if via eUB, -88.9 if via sx */
    
#if eUBsimilar>0
    printf("\n\n HEY:  must copy  eUBsimilar  code from GetEq_large_code_NEW.c  and MODIFY ... aborting \n\n");  exit(8);
#endif
    
    if (sx==NULL) {  /* need initial s->w that does not use similar state.  ask==0 uses bid=0=ask averaging over all bid,ask (okay to do) */
      
      if(t->ret==3) {    /* t->ret=3 --> market order --> s->w is E(current v, relative to lastobs v), NOT surplus.  new E(v) SAME for LARGE, SMALL trader. */
	if(InsertNewState=='y') N_s[18]++;  /* InsertNewState=='y' unless UPDATE_mkt[t->type]==0 */
	if(Bt[nP]<nP && Bt[nPx1]>=0)      /* bid,ask,M1->p on CURRENT v grid.  Bt[nP,nPx1] current since E(v) obtained BEFORE tweaking Bt in main */ 
	  s->w = 0.25 * (Bt[nP] + Bt[nPx1]) + 0.5 * M1->p;   /* equal weight on midpoint & last price  */
	else s->w = (double)M1->p;                           /* either bid,ask missing --> ignore both */
	if(doSYMM) s->w = nP_1 - s->w - PVmu + t->v;         /* +tv = -(-1*tv) */
	else       s->w =        s->w - PVmu - t->v;
	s->w *= Ninformed;                  /* 0 <= Ninformed <= 1.  None informed --> Ninformed=0 --> initial E(v)= 0 */
      }              /* E(v) never updated if UPDATE_mkt[t->type]=0 --> non-deviators with fixed beliefs NEVER update! */
      /* */          /* E(v) COULD use w0sell[] for E(v) if add [q==0] dimension */

      if (t->ret==1 || t->ret==2)  s->w = -99.99;   /* flag to use v1 from optimization after cancel as init value of CANCEL */

      if (t->ret == 0 || t->ret == 4) {  /* NEW order */
	if (t->z) {  /* LARGE tz initial s->w: new trader arrives other side of market on ave. 2*Earrive, assume trade at v */
#if use_w0sell
	  s->w = w0sell[t->type][t->q][max(bid,0)][ask][pvtype];  /* REQUIRES zmax < nPmax since using t->q (shares togo) as [p] index */
#else
	  s->w = -99.99;
#endif
	  if(s->w < -99.9) { /* w0sell[] not available.  Optimistically assume all arriving traders on other side transact at PVmu */
	    s->w = 0.0;  for(p=1;p<=t->q;p++)   s->w += exp( rhoT[t->type] * 2.0 * Earrive * p ) * fabs(PV[t->pvtype]); }
	  /* else s->w = min( s->w, t->q * fabs(PV[t->pvtype]) -.001);*/ /* should perhaps also do this in WriteFiles to "fix" overly optim.beliefs */
	}            /* could add N_s[] for large tz */
	
	else {       /* else NOT LARGE tz */
	  if(tp==nP){/* non-order : w0sell[] only uses quotes --> same usage for all t->tB (though values may differ) */
#if use_w0sell         /* w0sell[]= -99.99 if not yet initialized */
	    s->w = w0sell[t->type][nP][max(bid,0)][ask][pvtype] ;
#else
	    s->w = -99.99;
#endif
	    s->w = max(s->w, CS_non[pvtype][t->type] );  /* should only have s->w < 99.9 if CS_non[][] initially hardcoded to -9999.9 */

	    if(s->w < -99.9) {         /* [tp==nP] REQUIRES nP<nPmax.   max( TrembleNew +.1, ...) assures can tremble to Non-Order */
	      s->w = max( TrembleNew+.1, exp( rhoT[t->type]*Eagain_non ) * fabs(PV[t->pvtype] ));  /* assumes will trade at PVmu next return */
	      /* */  N_s[21]++;  if(InsertNewState=='y') N_s[20]++; }
	    else {   N_s[15]++;  if(InsertNewState=='y') N_s[12]++; }
	    s->Eoptimal = -88.6;  /* initialized non-order (regardless of method) */
	  }
	  else {     /* limit order */
#if use_w0sell
	    s->w = w0sell[t->type][tp][max(bid,0)][ask][pvtype];  /* bid=-1 ask=0 truncated to bid=0 ask=0 --> erroneously uses bid=0=ask, but true ask=0 probably never */
	    if(s->w < -99.9) { s->w = w0sell[t->type][tp][0][0][pvtype];  if(s->w > -99.9) N_s[19]++;  } /* count of w0sell[bid=0=ask] does not distinguish inserted from checked states */
#else
	    s->w = -99.99;
#endif
	    if(s->w < -99.9) {      /* w0sell[] was not yet initialized.  Must use "rule based" initial belief for limit order */
	      
	      /* NEW WAY to initialize state... uses hardcoded Pe[nPlim] and p_v[nPlim] */
	      /* s->w = Prob(execute) * E(surplus) + ( 1 - Prob(execute) ) * value if had instead placed a NonOrder */
	      
	      /* tL, tH are GLOBAL:  tL = LL + floor(tt->Ev + tt->v)    tH = HH + floor()     */
	      /* tL, tH are ADJUSTED by jumps but are NOT SHIFTED in the sense of GetState(). */
	      /* tL, tH are still expressed relative to ACTUAL v (midtick) --> use t->p       */
	      /*                                             Hence, ignore SYMM and tv shifts */
	      if(t->buy) {
		p = min(nPlim-1, t->p - tL );   /* buy p on "bid aggressiveness" scale. */
		p = max(0, p);                  /* must be within 0 and nPlim-1.        */
		s->w = Pe[p][t->type] * (  PV[t->pvtype] - p_v[p][t->type] ) + (1.0-Pe[p][t->type]) * CS_non[t->pvtype][t->type] ;
	      }
	      else {
		p = min(nPlim-1, tH - t->p );   /* sell p on "bid aggressiveness" scale.*/
		p = max(0, p);                  /* must be within 0 and nPlim-1.        */
		s->w = Pe[p][t->type] * ( -PV[t->pvtype] - p_v[p][t->type] ) + (1.0-Pe[p][t->type]) * CS_non[t->pvtype][t->type] ;
	      }
#if 0  /* OLD eUB */
	      if(buy) {                    /* eUB[buy][q][p] = lower bound for Expected time until arrival of trader for which taking this order yields nonzero payoff.  q is priority at tick p */
		if(t->vlag){               /* eUB[i][0][p] = Prob(next arrival has PV > p for limit sells, PV<p for limit buys */
		  p = (int) ceil(t->p - t->Ev - t->v);  /* optimistic waiting times have higher buy prices */
		  if(p<0)   { if(jj<2) printf("p= %d to   0  for eUB s->w of buy at p= %d  v= %d  Ev= %5.2f \n", p, t->p,t->v,t->Ev);    p=0;}
		  if(p>nP_1){ if(jj<2) printf("p= %d to nP-1 for eUB s->w of buy at p= %d  v= %d  Ev= %5.2f \n", p, t->p,t->v,t->Ev);    p=nP_1;} }
		else p = t->p;  /* conditional surplus based on unshifted t->pv,t->p since shifts cancel out */
		/* assume priority is 1.  see  GetEq_large_code_NEW.c  for eUB[] initialization using priority */
		if (0) { /* consider +- 1 jump when getting eUB based init s->w */
		  s->w = Eagain/(Ejump+Eagain);            /* Prob v jumps before return.  e.g. 4/(8+4) = 1/3  */ 
		  s->w = (1.0-s->w) * (t->pv - t->p)   * exp( delta * rhoT[t->type] * eUB[1][1][p] )  /* assumes all orders of higher priority are also at p */
		    + s->w/2.0 * (t->pv + PVjN - t->p) * exp( delta * rhoT[t->type] * eUB[1][1][max(  0 ,p - PVjN)] )
		    + s->w/2.0 * (t->pv - PVjN - t->p) * exp( delta * rhoT[t->type] * eUB[1][1][min(nP_1,p + PVjN)] );
		}
		else {
		  s->w = (t->pv - t->p) * exp( delta * rhoT[t->type] * eUB[1][1][p] );  /* assumes only need ONE trader to arrive for whom trade yields CS>0 */
		}
	      }
	      else {  /* q=p; printf("eUB: 'y'= %d  pv~T= %d %d  t->buy=%d  t->p~tp= %2d %2d  t->ret= %d  t->v~tv= %2d %2d  bidask=%2d %2d  %2d %2d  w0sell= %6.2f",
			 InsertNewState=='y', t->pvtype,t->type,t->buy,t->p,tp,t->ret,t->v,tv,bid,ask,Bt[nPx1],Bt[nP], s->w); */
		
		/* shift tp,pv by t->Ev (ignore rare truncation of tv to keep tp on grid) */
		/* this is only use of t->Ev --> not flipped above due to SYMM --> flip here */
		/* tp ALREADY flipped and shifted by (flipped) tv;  pv flipped but NOT shifted */
		
		if( /* SYMM && */ t->buy) {  /* eUB[buy][q][p] = lower bound for Expected time until arrival of trader for which taking this order yields nonzero payoff.  q is priority at tick p */
		  if(t->vlag){               /* eUB[i][0][p] = Prob(next arrival has PV > p for limit sells, PV<p for limit buys */
		    p = (int) ceil(t->p - t->Ev - t->v);  /* optimistic waiting times have higher buy prices */
		    if(p<0)   { if(jj<2) printf("p= %d to   0  for eUB s->w of buy at p= %d  v= %d  Ev= %5.2f \n", p, t->p,t->v,t->Ev);    p=0;}
		    if(p>nP_1){ if(jj<2) printf("p= %d to nP-1 for eUB s->w of buy at p= %d  v= %d  Ev= %5.2f \n", p, t->p,t->v,t->Ev);    p=nP_1;} }
		  else p = t->p;  /* conditional surplus based on unshifted t->pv,t->p since shifts cancel out */
		  /* assume priority is 1.  see  GetEq_large_code_NEW.c  for eUB[] initialization using priority */
		  if(0) {               /* consider +- 1 jump when getting eUB based init s->w */
		    s->w = Eagain/(Ejump+Eagain);            /* Prob v jumps before return.  e.g. 4/(8+4) = 1/3  */ 
		    s->w = (1.0-s->w) * (t->pv - t->p)   * exp( delta * rhoT[t->type] * eUB[1][1][p] )  /* assumes all orders of higher priority are also at p */
		      + s->w/2.0 * (t->pv + PVjN - t->p) * exp( delta * rhoT[t->type] * eUB[1][1][max(  0 ,p - PVjN)] )
		      + s->w/2.0 * (t->pv - PVjN - t->p) * exp( delta * rhoT[t->type] * eUB[1][1][min(nP_1,p + PVjN)] );
		  }
		  else {
		    s->w = (t->pv - t->p) * exp( delta * rhoT[t->type] * eUB[1][1][p] );  /* assumes only need ONE trader to arrive for whom trade yields CS>0 */
		  }
		}
		
		else {  /* limit sell */
		  /* EXAMPLE:  current v = 12 (=PVmu by def.), last obs = 10. E(v) = 10.8.  So t->v = -2, t->Ev = .8.
		     consider limit sell at 13.  This order believed to be 2.2 above v, but actually only 1 above v.
		     eUB[] gives expected waiting time, which should be evaluated at perceived price p = 2.2+PVmu.
		     p = 13-(.8-2) = 14.2, or generally: p = t->price - (t->Ev + t->v). 
		     To be optimistic use eUB[][][ floor(p) ] since lower sell prices have lower time until execute. */
		  if(t->vlag){
		    p = (int) floor(t->p - t->Ev - t->v);
		    if(p<0)   { if(jj<2) printf("p= %d changed to   0  for eUB s->w of sell at p= %d  v= %d  Ev= %5.2f \n", p, t->p,t->v,t->Ev);  p=0;}
		    if(p>nP_1){ if(jj<2) printf("p= %d changed to nP-1 for eUB s->w of sell at p= %d  v= %d  Ev= %5.2f \n", p, t->p,t->v,t->Ev);  p=nP_1;} }
		  else p = t->p;  /* conditional surplus based on unshifted t->pv,t->p since shifts cancel out */
		  if(0) {               /* consider +- 1 jump when getting eUB based init s->w */
		    s->w = Eagain/(Ejump+Eagain);            /* Prob v jumps before return.  e.g. 4/(8+4) = 1/3  */
		    s->w = (1.0-s->w) * (t->p - t->pv)   * exp( delta * rhoT[t->type] * eUB[0][1][p] )  /* assumes all orders of higher priority are also at p */
		      + s->w/2.0 * (t->p - t->pv - PVjN) * exp( delta * rhoT[t->type] * eUB[0][1][max(  0 ,p - PVjN)] )
		      + s->w/2.0 * (t->p - t->pv + PVjN) * exp( delta * rhoT[t->type] * eUB[0][1][min(nP_1,p + PVjN)] );
		    /* printf(" q= %2d  p= %2d  t->p= %2d  t->pv= %5.1f  0,+4,-4: %6.3f %6.3f %6.3f  ave+-: %6.3f flipped: %6.3f %6.3f \n",  q, p, t->p, t->pv,
		       (t->p - t->pv)    * exp( delta * rhoT[t->type] * eUB[0][q][p] ),
		       (t->p - t->pv +4) * exp( delta * rhoT[t->type] * eUB[0][q][p+4] ),
		       (t->p - t->pv -4) * exp( delta * rhoT[t->type] * eUB[0][q][p-4] ),
		       ((t->p - t->pv +4) * exp( delta * rhoT[t->type] * eUB[0][q][p+4] ) +
		       (t->p - t->pv -4) * exp( delta * rhoT[t->type] * eUB[0][q][p-4] ))/2.0,
		       (t->p - t->pv +4) * exp( delta * rhoT[t->type] * eUB[0][q][p-4] ),
		       (t->p - t->pv -4) * exp( delta * rhoT[t->type] * eUB[0][q][p+4] ) );	   fflush(stdout); */
		  }
		  else {
		    s->w =  (t->p - t->pv)  * exp( delta * rhoT[t->type] * eUB[0][1][p] ); /* assumes only need ONE trader to arrive for whom trade yields CS>0 */
		  }
		  /* printf("eUB: w=%5.2f (t->p - t->pv)= %5.2f exp=%5.2f p= %2d eUB[0][1][p]= %5.2f \n", s->w, (t->p - t->pv), exp(delta*rhoT[t->type] * eUB[0][1][p] ), p,eUB[0][1][p] ); */
		}
	      }
#endif  /* OLD eUB initialization */
	      
	      N_s[16]++;  if(InsertNewState=='y') N_s[13]++;
	      s->Eoptimal = -88.8;  /* -88.7 if intialized via w0sell, -88.8 if via eUB, -88.9 if via sx */
	      /* printf("  eUB_w= %6.2f\n", s->w); */
	    } /* end s->w < -99.9 */
	    else {    /* s->w NOT -99.99, increment w0sell usage counters.  These include bid=0=ask uses. */
	      N_s[15]++;  if(InsertNewState=='y') N_s[12]++;
	      s->Eoptimal = -88.7; } /* -88.7 if intialized via w0sell, -88.8 if via eUB, -88.9 if via sx */
	  } /* end else limitorder   */
	}   /* end else not large tz */
      }     /* end else t->ret != 3 */
      
      /* if(t->ret==3) printf(" t->buy= %d  p= %2d %2d  v= %2d %2d  pv= %5.2f %5.2f  pvtype= %d %d  type= %d  w= %5.2f\n", t->buy, t->p, tp, t->v, tv, t->pv, pv, t->pvtype, pvtype, t->type, s->w); */
      
      /* printf(" SYMM= %d  N0_v= %d  t->buy = %d  t->pv= %4.1f  t->v= %3d  tv= %3d  s->w= %5.1f\n", SYMM, N0_v, t->buy, t->pv, t->v, tv, s->w); */
      /* BE SURE TO CHECK that any modification to initial s->w gives same s->w for buys when using SYMM as when not SYMM !! */
      
    }      /* end  if(sx==NULL) */
    else { /* else    sx!=NULL  */
      s->Eoptimal = -88.9;  /* -88.7 if intialized via w0sell, -88.8 if via eUB, -88.9 if via sx */
      s->w = sx->w;   N_s[17]++;     /* sx->NC++; */  /* credit sx as having been checked */
      if (InsertNewState == 'y') {
	N_s[14]++;        /* count new states initialized by similar state */
	if (N_s[14]<6) {  /* print first instances of using similar state  */     /* q associated with sx overwritten, so cannot report it */
	  if (nB>=6 ) printf(" initializing:  pv= %d  buy= %d  type= %d  p= %2d  ret= %2d  B[0-3]: %2d %2d %2d %2d  s~sx: B[4,5,6]: %2d %2d, %2d %2d, %2d %2d  sx->w~N~NN= %5.3f %u %u\n", pvtype, buy, t->type, tp, t->ret, s->B[0],s->B[1],s->B[2],s->B[3],  s->B[4], sx->B[4], s->B[5], sx->B[5], s->B[6], sx->B[6], sx->w, sx->N, sx->NN );
	}
      }
    }
    
    /*  HEY:  if use this then may want to remove code above that truncates bid,ask to be within 0,nP-1 !!
	if (s==S && s->w > 8.0) printf("t->period= %u  t->z= %d  t->buy= %d  ret= %2d  p= %2d %2d  bid~ask= %2d %2d  v= %2d %2d  pv= %5.2f %5.2f  pvtype= %d %d  type= %d  w= %5.2f sx= %p\n", t->period, t->z, t->buy, t->ret, t->p, tp, bid,ask, t->v, tv, t->pv, pv, t->pvtype, pvtype, t->type, s->w, sx); */
    
    /* print stuff to outNew about new states.  q is total priority in next block. */
    /* if (InsertNewState == 'y' && jj<2) {
       fprintf(outNew, " %8.4f %3d %3d %3d %3d %8.4f ", s->w, t->pvtype, t->buy, t->p, t->q, t->pv );    for(p=0;p<nB;p++)  fprintf(outNew, " %2d", sB[p]);  }
    */
    
    s->Pw = s->w;   /* initialize NEW state's Pw with initial w.  In general, Pw stores w from prev jj */
    
  }  /* end new state (ie. s == NULL) */
  if(InsertNewState=='n') N_s[1]++;     /* count of states checked */

  if(s->w < -9999.0 /* ie, nan */) { printf("s->w~Pw = %8.4f %8.4f  m l k p buy= %d %d %d %d %d N=%u  s==S:%1d aborting ... \n",
					    s->w, s->Pw, pvtype, t->type, t->ret, tp, buy, s->N, s==S);    exit(8); }
  /*
  if(DEBUG) {
    printf("t->period= %u  t->z= %d  t->buy= %d  ret= %2d  p= %2d %2d  bid~ask= %2d %2d  v= %2d %2d  pv= %5.2f %5.2f  pvtype= %d %d  type= %d  w= %5.2f \n", t->period, t->z, t->buy, t->ret, t->p, tp, bid,ask, t->v, tv, t->pv, pv, t->pvtype, pvtype, t->type, s->w);  fflush(stdout);  }
  */
return(s);
}


#if 0
static double Binomial( x, n )    /* x is number of drops, n is number of orders on books */
     int x, n;
{
  double f = 1.0 ;
  int i;
  for(i=n;   i>x; i--)  f *= (double)i;    /* f = n! / x! = n*(n-1)*...*(x+1), since x<n.  Faster than computing n! , x! separately */
  for(i=n-x; i>1; i--)  f /= (double)i;    /* now f = n! / ( x! * (n-x)! ) = "n choose x" */
  return( f * pow( delta, (double)x ) * pow( 1.0-delta, (double)(n-x) ));
}
#endif

/* HEY:  ALL  GetEe()  stuff removed when started   GetEq_continuous_code.c      */
/*       see  GetEq_NoFringe_code.c  for latest code that retained GetEe() stuff */



int WriteFiles(i)
     int i;
{ 

  double u, w, z, wf, w2, u2, u2max, Eopt, uopt, corr[2][6],EA[44], E88[44], E_p[nPmax][44], E_T[nTmax][44], EPV[nPVmax][44], E_0[44], E_n[44], E_1[44], E_2[44], E_3[44], E[44], ET0[nTmax][44], ETn[nTmax][44], ET1[nTmax][44], ET2[nTmax][44], ET3[nTmax][44], Eut[44], Ent[44], Eyt[44], Elm[nTmax][nPVmax][44], Elmp[nTmax][nPVmax][nPmax][44], Hcount[2], UB[nPx1][12];
  unsigned int h, Tcount[17], Acount[12], Purge;
  short int a, p, k, l, m, bid,ask;
  struct state   *s,  **sp  ;
  FILE *outB=NULL, *outMkt=NULL;

#if WRITE>0
  FILE *outw=NULL, *outN=NULL ;
  /*  declaring these within WriteFiles() causes seg.fault on 32-bit systems.  Why??   instead made global.
    double        w2sell[nTmax][nPmax][nPmax][nPmax][nPV];
    unsigned int  M0sell[nTmax][nPmax][nPmax][nPmax][nPV];
  */
#endif

  Purge=0;  w=0.0; wf=0.0; w2=0.0; u=0.0; u2=0.0;  /* avoids uninitialized warnings */    N_s[3] = 0; /* count ask==0 */

  /* i = *(short int *)buy; */  /* when used pthreads: static void *WriteFiles(void *buy) */

#if 0  /* PURGE within WriteFiles needs to be modified */
  if (PURGE_MODE > 0 && jj>0 && (jj%max(1,PURGE_MODE))==0) Purge= 1; /* Purge via NC, N if PURGE_MODE>0.  else only via NC.*/
  if ((N_s[0]+N_s[2])>MAXSTATES && PURGE_NC>0)             Purge= 1; /* N_s[2]= #states prev jj.       N_s[0]= new this jj.*/
#endif

#if WRITE>0
  if (i) {
    if ( (jj%2) == 0) {                                                      /* global jj, updated end WriteFiles()  */
      outw =  fopen(TMP "GetEq.w.buy.even", "wb");
      outN =  fopen(TMP "GetEq.N.buy.even", "wb");
      outB =  fopen(TMP "GetEq.B.buy.even", "wb"); }
    else {
      outw =  fopen(TMP "GetEq.w.buy.odd",  "wb");
      outN =  fopen(TMP "GetEq.N.buy.odd",  "wb");
      outB =  fopen(TMP "GetEq.B.buy.odd",  "wb");
    } }
  else {
    if ( (jj%2) == 0) {
      outw =  fopen(TMP "GetEq.w.sell.even", "wb");
      outN =  fopen(TMP "GetEq.N.sell.even", "wb");
      outB =  fopen(TMP "GetEq.B.sell.even", "wb");
    }
    else {
      outw =  fopen(TMP "GetEq.w.sell.odd",  "wb");
      outN =  fopen(TMP "GetEq.N.sell.odd",  "wb");
      outB =  fopen(TMP "GetEq.B.sell.odd",  "wb");
    } }
  if (outB==NULL) { printf("i= %d, fopen() ERROR opening B outfile.  No WRITING this jj ...\n",i); }
  if (outw==NULL) { printf("i= %d, fopen() ERROR opening w outfile.  No WRITING this jj ...\n",i); if(outB!=NULL) fclose(outB);  outB=NULL; }
  if (outN==NULL) { printf("i= %d, fopen() ERROR opening N outfile.  No WRITING this jj ...\n",i); if(outB!=NULL) fclose(outB);  outB=NULL; }
  if (outB==NULL) { 
    outMkt = fopen("/home/goettler/limitorder/ERROR_in_fopen_in_WriteFiles", "w");  /* existence of this file in ~/limitorder/ will be detected more quickly than above messages buried in mass of output */
    if(outMkt!=NULL) { 
      fprintf(outMkt, "\nHEY: ERROR opening outB/outw/outN for a job currently running -- check output files to find which one...\n");  fclose(outMkt);
      printf(         "\nHEY: ERROR opening outB/outw/outN for a job currently running -- check output files to find which one...\n");  }
    if(outw!=NULL) fclose(outw);   /* write aborted --> could not open at least 1 of these 3 files */
    if(outN!=NULL) fclose(outN);
  }   /* close those that were successfully opened (!=NULL) */
#endif

  if (mJ>0) outMkt = fopen(TMP "GetEq.mkt", "w");     /* ascii file to inspect mkt order beliefs (ie, current v) if s->NN > OftNN */
  
  if(outB==NULL) printf("\n WriteFiles:  i= %d  N_s[0+2]= %u  nJ= %5.1e  Purge= %u  UPDATE_maxPVT,sync,s0= %4.2f %d %d  Nreset= %d  OftNN= %d   No WRITE  jj= %u\n\n", i, N_s[0]+N_s[2], nJ, Purge, UPDATE_maxPVT, UPDATE_sync, UPDATE_s0, Nreset, OftNN, jj);
  else           printf("\n WriteFiles:  i= %d  N_s[0+2]= %u  nJ= %5.1e  Purge= %u  UPDATE_maxPVT,sync,s0= %4.2f %d %d  Nreset= %d  OftNN= %d  Yes WRITE  jj= %u\n\n", i, N_s[0]+N_s[2], nJ, Purge, UPDATE_maxPVT, UPDATE_sync, UPDATE_s0, Nreset, OftNN, jj);
  fflush(stdout);
  
  u2max = 0.0;
  for (p=0; p<6;  p++) { corr[0][p]=0.0; corr[1][p]=0.0; }
  for (l=0; l<nT; l++)               /* 1st get means to construct correlation (s->Pw, s->Ew/s->NN) across states, NN-weighted and unweighted */
    for (m=0; m<nPV; m++)
      for (k=0; k<nQ; k++)
	for (p=0; p<nPx1; p++)
	  if (pHT[m][i][l][k][p] != NULL)  /* printf("\n descending HT of:  pvtype= %2d  buy=%2d  type=%2d  q=%3d  p=%3d \n", m, i,l,k,p); */
	    for (h=0; h<Prime; h++) {      /* loop through all buckets in the HT associated with 3-tuple i,p,k */
	      s = pHT[m][i][l][k][p]->bucket[h];
	      sp= pHT[m][i][l][k][p]->bucket+h;  /* struct state **sp       */
	      while (s != NULL)   {              /* loop through all states in this bucket's list */

#ifdef CheckSEGV   /* HEY:  elaine sometimes gives SEGV  from next line.  Could detect and skip in same manner as in GetState() */
		if ( sprintf(buffer,"%p",s) > 16 ) {
		  printf("\n in WriteFiles()  A  :  SEGV??  s = %p  appears too long (%d) to be valid, so skipping it ... l m k p h = %d %d %d %d %u\n", s, sprintf(buffer,"%p",s), l,m,k,p,h );  fflush(stdout);
		  break;  }   /* break  while (s != NULL) */
#endif

		if(s->NN > OftNN) {              /* if UPDATE_s0=1 corr[] will ignore the many states that never have NN>0 */
		  wf=  s->Ew / (double)s->NN;    /* [0][] is weighted stuff, [1][] unweighted */
		  corr[0][0]+= (double)s->NN;   corr[1][0]++;
		  corr[0][1]+= s->Pw * s->NN;   corr[1][1]+= s->Pw;  /* could weight by s->NC instead of s->NN */
		  corr[0][2]+=    wf * s->NN;   corr[1][2]+=    wf;
		}
		sp = &s->next;   /* above line avoids NN overflow, rescales Ew,w2,NT accordingly */
		s  =  s->next;
	      }
	    }
  corr[0][1] = corr[0][1]/corr[0][0];    corr[1][1] = corr[1][1]/corr[1][0];  /* mean Pw    */
  corr[0][2] = corr[0][2]/corr[0][0];    corr[1][2] = corr[1][2]/corr[1][0];  /* mean Ew/NN */
  
  for (p=0; p<12; p++) { Tcount[p] = 0;  Acount[p] = 0; }  Tcount[12]=0;  Tcount[13]=0;  Tcount[14]=0;  Tcount[15]=0;  Tcount[16]=0;  Hcount[0]=0.0;  Hcount[1]=0.0;
  Tcount[7] = nP;      /* min price at which s->B[] nonzero must be initialized high */
  Tcount[9] = nP;
  for (p=0; p<44; p++) {
    EA[p]  = 0.0;      /* ALL states */
    E88[p] = 0.0;      /* new states */
    E_0[p] = 0.0;      /* 0= New Limit Orders */
    E_n[p] = 0.0;      /* n= Non-Orders --> p==nP && zT[l]==0 */
    E_1[p] = 0.0;      /* 1= retained */
    E_2[p] = 0.0;      /* 2= canceled */
    E_3[p] = 0.0;      /* 3= mkt orders */
    Eut[p] = 0.0;      /* ut= states updated  (some s have beliefs fixed to get payoffs to deviating) */
    Ent[p] = 0.0;      /* nt= states NEVER trembled to, limit orders only (ie, ignore mkt/non orders) */
    Eyt[p] = 0.0;      /* nt= states  yes  trembled to, limit orders only (ie, ignore mkt/non orders) */
    for(l=0;l<nT; l++) { E_T[l][p] = 0.0;  ET0[l][p]=0.0;  ETn[l][p]=0.0;  ET1[l][p]=0.0;  ET2[l][p]=0.0; ET3[l][p]=0.0; }
    for(m=0;m<nPV;m++) { EPV[m][p] = 0.0; }
    for(m=0;m<nP; m++) { E_p[m][p] = 0.0; }  /* by price of limitorder */
  }
  /* column headers for state-by-state printf()'s  */
  if(WRITE==0)  printf("buy~pv~T~q~p     h     Eopt: u    Prev     New     w-Pw                                        sigma    se     u/se             Ne > NN expected when UPDATE_s0 = 0\n");

#if WRITE>0 && use_w0sell  /* && UPDATE_s0==1   <--   already checked in main */
  for (l=0; l<nT; l++)
    for (p=0; p<nPx1; p++)
      for (bid=0; bid<nPx1; bid++)
	for (ask=bid; ask<nPx1; ask++)         /* ask >= bid used to reduce looping */
	  for (m=0; m<nPV; m++) {              /* only update w0sell if WRITE>0 and UPDATE_s0 (since NN weighted update) */
	    M0sell[l][p][bid][ask][m] = 0  ;   /* count #states matching each [m][l][p][k][a] */
	    N0sell[l][p][bid][ask][m] = 0.0;   /* will replace current w0sell[] with NN weighted average of s->w for s matching [m][l][p][k][a] */
	    w2sell[l][p][bid][ask][m] = 0.0; } /* to construct variance in s->w across matching states */
#endif 

  for (p=0; p<nPx1; p++) for(k=0;k<12;k++)  UB[p][k]=0.0;  /* to check eUB based init s->w with current s->w */

  for (l=0; l<nT; l++) {                   /* type on outside since want |w-wf| by type, PV */
    for (m=0; m<nPV; m++) {
      for (k=0; k<44; k++) { 
	Elm[l][m][k]=0.0;                              /* to check convergence by Type,PV */
	for (p=0; p<nPmax; p++) Elmp[l][m][p][k]=0.0;  /* to check convergence by Type,PV,price */
      }
      for (k=0; k<nQ; k++) {               /* nQ used to be max queue at each tick, but now is an arbitrary state variable:  0= NEW, 1= Retaining, 2= Cancelling, 3= E(v) */
	for (p=0; p<nPx1; p++) {
	  if (pHT[m][i][l][k][p] != NULL){ /* printf("\n descending HT of:  pvtype= %2d  buy=%2d  type=%2d  q=%3d  p=%3d \n", m, i,l,k,p); */
	    Tcount[0]++;                   /* count number    of HT */
	    Tcount[5] = 0;                 /* count buckets this HT */
	    for (h=0; h<Prime; h++) {      /* loop through all buckets in the HT associated with 3-tuple i,p,k */
	      Tcount[1] = 0;               /* count states in this bucket */
	      s = pHT[m][i][l][k][p]->bucket[h];
	      sp= pHT[m][i][l][k][p]->bucket+h;  /* struct state **sp       */
	      if (s != NULL)  {
		Tcount[4]++;               /* count total buckets used */
		Tcount[5]++;               /* count buckets this HT    */
	      }
	      while (s != NULL) {          /* loop through all states in this bucket's list */

#ifdef CheckSEGV   /* HEY:  elaine sometimes gives SEGV  from next line.  Could detect and skip in same manner as in GetState() */
		if ( sprintf(buffer,"%p",s) > 16 ) {
		  printf("\n in WriteFiles()  B  :  SEGV??  s = %p  appears too long (%d) to be valid, so skipping it ... l m k p h = %d %d %d %d %u\n", s, sprintf(buffer,"%p",s), l,m,k,p,h );  fflush(stdout);
		  break;  }   /* break  while (s != NULL) */
#endif

		Hcount[0]+= (double)s->NC * (double)Tcount[1];
		Hcount[1]+= (double)s->NC;
		Tcount[1]++;               /* this bucket's state count                     */
		Tcount[2]++;               /* total state count, including purged states    */
		
		if (k==3 && p >0 )    printf("\n\n HEY: should not have ANY states with q =3 & p >0  since q=3 is for getting E(current v) & GetState sets tp=0 if tq=0 ... p= %d\n\n",p);
		if (nQ==4 && k!=0 && p==nP && zT[l]==0)    printf("\n\n HEY: should not have ANY states with q!=0 & p==nP & zT=0 since p==nP is for Non-Orders, all of which should have q=0 ... q= %d\n\n",k);
		
		if (k!=3) Acount[9] = max(Acount[9],  s->NN);  /* max NN (excluding mkt-orders which get NN++ every period */
		/* */     Acount[10]= max(Acount[10], s->NT);
		if (s->NC) Acount[11]++;

		bid = s->B[0];
		ask = s->B[1];

		if(s->NN) {
		  wf= s->Ew / (double)s->NN;         /* s->Ew = cum payoff outcome from NEW traders with initial orders submitted at s */
		  w2= s->w2 / (double)s->NN - wf*wf; /* s->w2 = cum payoff^2 --> w2 = var(payoff outcome)  */
		  if(s->NN>1) w2 *= (double)s->NN/(s->NN-1.0);     /* since standard formula is biased low */
		  if(w2mult > 1.0) w2 *= w2mult;     /* arbitrary scaling to see how high needed to satisfy ChiSq test.  see #define top of this file. */
		  w = s->Pw;                         /* Pw is w from previous jj.  Using s->Pw (not s->w) needed for ChiSq if UPDATE_maxPVT > 0.0 */
		  u = fabs(w - wf);                  /* u distributed  N(0,w2/NN) --> u*sqrt(NN/w2) is N(0,1) */
		  u2= u*u*(double)s->NN/w2;          /* squared N(0,1) to be summed over states for chi-sq test of convergence */
		  if(w2>0.0) w2= sqrt(w2);           /* report standard deviation of payoffs below instead of var */
		  else       w2 = 0.0;               /* w2 < 0.0 due to rounding */
		  if(w2>0.0 && w2<w2min) w2 = 0.0;   /* require a minimum sigma( Ew ) to be used in Chi-Sq test since dividing by near 0.0 blows up chisq. */
		}
		else {
		  u= 0.0; u2= 0.0; w=0.0; wf= 0.0; w2=0.0;   /* NN is also 0, so these states will NOT be part of weighted averages at all */
		}
#if 0 /* mJ>0 */  /* this does not work, since all states are chosen EVERY time checked */
		if( PrTremble == 0.0 ) {
		  if (Tcount[2] == 1) printf("\n\n HEY: using ONLY states chosen EVERY time checked for ChiSq test\n\n");
		  if( w2 > w2min && s->NN != s->NC && s->NN != s->NC/2 ) {    /* uses only states that are chosen EVERY time checked.  NC initially 0 when WRITE=0 (ie, mJ>0) */
		    printf(" Excluding since not ALWAYS chosen:  state  %d %d %d  %2d %2d %7u w~Pw~Ew %7.4f %7.4f %7.4f  sig%7.4f %8.5f %5.1f  NC~N~NN~NT%10u %10u %10u %5u ",
			   i,m,l,k,p,h, s->w, s->Pw, wf, w2, w2/sqrt(s->NN),u*sqrt(s->NN)/w2, s->NC, s->N, s->NN, s->NT);
		    printf(" s= %p  B=", s);  for(a=0;a<nB;   a++) printf(" %3d",  s->B[a]);
		    printf(" H=");        for(a=0;a<nHnRT;a++) printf(" %3d",*(s->H[0]+a));   printf("\n");
		    w2 = 0.0;   } }
#endif
		if (i==0 &&  m==2  &&  vlagT[l]==0 && k==0 && p<nP && zT[l]==0) {  /* NEW limit sells only (k==0),  z use temporary within if(){} */
		  /*         m==2  restricts to only Speculators with PV = 0.  Modify to m=0 or m=1 for other private value types */
		  /* z = (p - PV[m]-PVmu) * exp( delta * rhoT[l] * ( eUB[0][1][p]  )) ; */
		  
		  z = Pe[HL-p][l] * ( -PV[m] - p_v[HL-p][l] ) + (1.0-Pe[HL-p][l]) * CS_non[m][l] ;    /* HL-p is sell price converted to "bid aggressiveness" */
		  UB[p ][11]+= z;
		  UB[nP][11]+= z;  z -= s->w ;   /* to report mean initial s->w for all limit sells, prev line by price */
		  UB[p][0] += (double)(z<0.0);   /* #times rule-based init w is LOWER than s->w */
		  UB[p][1] += z;                 /* [1]/[2] give simple mean */
		  UB[p][2] ++  ;
		  UB[p][3] += z * (double)s->N;  /* [3]/[4] is N weighted */
		  UB[p][4] += (double)s->N;
		  if(s->N > 10) {
		      UB[p ][5]+= (double)(z<0.0);  UB[p ][6]+= z;  UB[p ][7]++;
		      UB[nP][5]+= (double)(z<0.0);  UB[nP][6]+= z;  UB[nP][7]++;  }
		  UB[nP][0]+= (double)(z<0.0);   /* same thing, but ALL limitorders combined */
		  UB[nP][1]+= z;
		  UB[nP][2]++  ;
		  UB[nP][3]+= z * (double)s->N;
		  UB[nP][4]+= (double)s->N;
		  if(s->Eoptimal < -88.0 ) {     /* -88.6 if initialized via fabs(PV) (nonorder), -88.7 if via w0sell, -88.8 if via eUB, -88.9 if via sx */
		      UB[p ][8]+= (double)(z<0.0);  UB[p ][9]+= z;  UB[p ][10]++;  /* new, by price */
		      UB[nP][8]+= (double)(z<0.0);  UB[nP][9]+= z;  UB[nP][10]++;
		  }
		}  /* HEY: GetState shifted ask, p by +tv !!  is that right?? */
		/* is p-PVT[] unbiased if vlag?? */

		if(s->NN > OftNN) {                  /* corr[0][] is weighted correlations, [1][] unweighted */
		  z = s->Pw - corr[0][1];            /* temp use of z,uopt */
		  uopt = wf - corr[0][2];
		  corr[0][3] +=    z*z    * s->NN;   /* could weight by s->NC instead of s->NN */
		  corr[0][4] += uopt*uopt * s->NN;
		  corr[0][5] +=    z*uopt * s->NN;
		  z = s->Pw - corr[1][1];            /* unweighted */
		  uopt = wf - corr[1][2];
		  corr[1][3] +=    z*z    ;
		  corr[1][4] += uopt*uopt ;
		  corr[1][5] +=    z*uopt ;          /*  printf(" %8.5f  %8.5f  %8.5f  %8.5f  %8.5f  %8.5f  %8.5f \n", corr[1][1], corr[1][2], z, uopt, z*z, uopt*uopt, z*uopt); */
		}
	
		if (UPDATE_sync && UPDATE_maxPVT==0.0 && s->NN /* >OftNN */ ) {           /* NOTE: s->w == s->Pw since UPDATE_maxPVT = 0.0 */
		  s->w = s->w * s->N  +  wf * s->NN;   s->N += s->NN;   s->w = s->w / s->N ; }

		z = fabs(s->Pw - s->w);

		uopt = -9.0;
		if(s->Eoptimal < -88.0)  uopt = s->Eoptimal ;  /* -88.7 if intialized via w0sell, -88.8 if via eUB, -88.9 if via sx */

		if(jj==jj0)  s->Eoptimal = -1.0;     /* 1st jj does not have previous s->Eoptimal initialized */
		Eopt = -1.0;                         /* percent of times checked, actually chosen (pre-trembles) */
		if(s->NC > OftNN && k!=3 ) {         /* This NEW use of s->NC means NC must be reset each time NN, NT reset.  OLD use rarely reset NC */
		  if(Nreset==1)          Eopt = ((double)s->N-1 - s->NT)/(double)s->NC;  /* NOTE: if only used NEW arrivals then Eopt should be either 1.0 or 0.0 when converged */
		  else if(UPDATE_s0==1)  Eopt = ((double)s->NN  - s->NT)/(double)s->NC;  /* BUT : for Returning traders optimality of new order depends on current order option --> 0<Eopt<1 possible */ 
		}

		/* not just 0 or 1.0 since RETURNING traders have RETAIN option */
		if( s->Eoptimal > -0.5 && Eopt > -0.5)  uopt = Eopt - s->Eoptimal;

#ifdef useNX
		if( s->NN < s->NX ) {
		  printf("%d %d %d  %2d %2d %7u %9.5f%8.4f %7.4f %9.6f w~Pw~Ew %7.4f %7.4f %7.4f  sig%7.4f %8.5f %5.1f  NC~N~NN~NT~NX%10u %10u %10u %5u %5u ",
			 i,m,l,k,p,h, uopt, s->Eoptimal, Eopt, s->w - s->Pw, s->w, s->Pw, wf, w2, w2/sqrt(s->NN),u*sqrt(s->NN)/w2, s->NC, s->N, s->NN, s->NT, s->NX);
		  printf("  s= %p  B= ", s);  for(a=0;a<nB;   a++) printf(" %3d", s->B[a]);
		  printf("  H= ");        for(a=0;a<nHnRT;a++) printf(" %3d",*(s->H[0]+a));   printf("\n");
		}
#endif

		/* Acount[9] is max NN thus far.   This prints individual states' values. */
		if( WRITE==0 &&    /* WRITE==0 avoids this when converging.  (WRITE=0 when writing matlab outM) */
		   (
		    /* ( z > .01 && z > EA[25]) || */   /* big change in s->w from last jj */
		    /* ( s->NN>OftNN && k<2 && p<nP && i==0 && s->w > 2.0+(p-(PV[m]+PVmu)) ) || */  /* limitorder  s->w > 2.0 + payoff if immediate execution.   2.0+(1-2*buy)*(p-(PV[m]+PVmu))  */
		    /* ( s->NN>OftNN && s->NN > s->NT  && s->NT ) */   /* should NOT expect Always/Never choose given state UNLESS restrict to NEW traders, since retained order SOMETIMES wins */
		    ( s->NN>OftNN && w2>w2min && ( u2>u2max || (double)s->NT>0.99*(double)Acount[10] || (k<2 && ( (double)s->NN>0.99*(double)Acount[9] || (N_s[2]>0 && N_s[2]<2000) ))))
#if usePN>0
		    || (u2 > 1.0 && s->NN>OftNN )
		    || s==SS
		    || s->SSN>0
		    || ( m==2  && k==0  && p==32 && s->B[0]==30 && s->B[1]==32 && s->NN>OftNN)
		    || ( UPDATE_maxPVT>.99 && k<3 && s->Eoptimal > -88.0 && fabs(s->w - (s->Pw*s->PN + s->Ew)/(s->PN+s->NN)) > 1e-12 )  /* if updating, should be within machine tolerance, 1e-15 or so */
#endif
#if useNe>0
		    || ( s->NN>OftNN && k<2 && p<nP && s->Ne==0 && s->NN>s->NT)     /* <-- s chosen (non-tremble) for limitorder but NEVER execute (Ne=0) at s */
#endif
#if useNNtt>0
		    || ( s->NNtt > 0 && s->NNtt < s->NCtt && (EA[26]<20 /*||s->N>100*/) )  /* printf 1st 20 (or high N) states NOT satisfying ALWAYS/NEVER optimal for NEW arrivals */
		    || ( s->NNtj > 0 && s->NNtj < s->NCtj && (EA[27]<20 /*||s->N>100*/) )  /* printf 1st 20 (or high N) states NOT satisfying ALWAYS/NEVER optimal for Returning tj */
#endif
		    )) { 
		  printf("%d %d %d  %2d %2d %7u %9.5f%8.4f %7.4f %9.6f w~Pw~Ew %7.4f %7.4f %7.4f  sig%7.4f %8.5f %5.1f  N~NC~NN~NT%10u %10u %10u %5u ",
			 i,m,l,k,p,h, uopt, s->Eoptimal, Eopt, s->w - s->Pw, s->w, s->Pw, wf, w2, w2/sqrt(s->NN),u*sqrt(s->NN)/w2, s->N, s->NC, s->NN, s->NT);
#if usePN>0
		  printf(" SSN=%8u ", s->SSN);
		  if(UPDATE_maxPVT>.99)  printf(" w-(Pw*PN+Ew)/(PN+NN)=%9.1e ", s->w - (s->Pw*s->PN + s->Ew)/(s->PN+s->NN));  /* Ew is cumsum over NN visits.  This difference should be zero. */
#endif
#if useNNtt>0
		  printf(" Ne=%9u  ok~NC~NNtt,tj %d %d %10u %10u %10u %10u ", s->Ne, (int)(s->NNtt==s->NCtt || s->NNtt==0), (int)(s->NNtj==s->NCtj || s->NNtj==0), s->NCtt, s->NNtt, s->NCtj, s->NNtj);
#endif
		  printf(" s= %p  B=", s);  for(a=0;a<nB;   a++) printf(" %3d",  s->B[a]);
#if nL>0
		  printf(" L=");            for(a=0;a<nL;   a++) printf(" %3d",  s->L[a]);
#endif
		  printf(" H=");        for(a=0;a<nHnRT;a++) printf(" %3d",*(s->H[0]+a));   printf("\n");
		}
		if (uopt > -5.0)     uopt = fabs(uopt);  /* ie, != -9.0 */
		s->Eoptimal = Eopt;                      /* s->Eoptimal not altered or used again until next time call WriteFiles */
		if (s->NN > OftNN)  u2max = max(u2,u2max);

		EA[0] += (double) s->NN;          /* only uses states with s->NN >0 for comparisons giving sense of convergence */
		EA[1] += (double) s->NN * w ;     /* for numerator of weighted average. s->w >0 for k=3 but NN=0, so no impact */
		EA[2] += (double) s->NN * wf;     /* s->Ew may seem faster (since wf=Ew/NN), but if k=3 (market orders) s->Ew = s->w from last jj, whereas wf=0 for these s since NN=0 */
		EA[3] += (double) s->NN * u ;
		EA[11]+= (double) s->NN * w2 ;    /* weighted average of sigma(realized payoff) */
		EA[24]+= (double) s->NN * s->w ;  /* w = s->Pw above */
		EA[25] = max(z,EA[25]);
		EA[4]  = max(w,EA[4]);
		EA[5]  = max(u,EA[5]);
		EA[6]++;
		EA[35]+= (double)(wf>s->Pw);      /* will report %states with wf>Pw */
		EA[36]+= (double)(s->N-1) ;       /* [36,37,38] are N weighed average,  some states NEVER have NN>0 if UPDATE_s0==0 */
		EA[37]+= (double)(s->N-1) * s->Pw;/* min N is 1, so use N-1 to ignore states NEVER chosen during previous nJ sims   */
		EA[38]+= (double)(s->N-1) * s->w;
		EA[39]+= (double)(-88.75<uopt && uopt<-88.65); /* uopt = -88.7 --> w0sell based initial s->w */
		EA[40]+= (double)(-88.85<uopt && uopt<-88.75); /* uopt = -88.8 -->   eUB  based initial s->w */
		EA[41]+= (double)(-88.95<uopt && uopt<-88.85); /* uopt = -88.9 -->   sx   based initial s->w */
		EA[42]+= (double)( uopt < -88.0 );/* new s */
#if useNe>0
		EA[43]+= (double) s->Ne;          /* # executions */
#endif
		EA[7] += (double)(s->NN>0);       /* [10] = cum sum N(0,1)^2.  [8] = degrees freedom  [9]/[0]= %NN used [12]= var|OftNN */
		EA[14]+= (double)(s->NT>0);
		EA[15]+= (double) s->NT;
		if(s->NT && s->NN>s->NT) EA[16]++; /* if converged then states should never be trembled_to and chosen optimally */
		if(uopt > -0.5) { EA[17]+= uopt*s->NN;  EA[18]+=(double)s->NN;  EA[19]+= uopt; EA[20]++;     EA[21]= max(EA[21],uopt); if(uopt>.01 && s->NN > OftNN)EA[22]++;}
		if(s->NN > OftNN && w2>0.0) { EA[8]++;  EA[9]+= (double)s->NN;  EA[10]+= u2;   EA[12]+= w2;  if(u2>9.0)EA[13]++; }
		if(s->NN > OftNN && w2==0.0)  EA[23]++; /* count times states ignored  in ChiSq test since w2 = 0.0 */

#if useNNtt>0
		/* if converged, EA[26] should be 0.  States should either ALWAYS or NEVER be optimal.  NOT EXACTLY !! */
		if(s->NNtt > 0 && s->NNtt < s->NCtt) { EA[26]++;  EA[27]+=(double)s->NCtt;  if(s->NN > OftNN) EA[28]++; }  /* New Arrivals */
		if(s->NNtj > 0 && s->NNtj < s->NCtj) { EA[29]++;  EA[30]+=(double)s->NCtj;  if(s->NN > OftNN) EA[31]++; }  /* Returning tj */
#endif
		if(nT>1) {
		  E_T[l][0] += (double) s->NN;    /* by type */
		  E_T[l][1] += (double) s->NN * w ;
		  E_T[l][2] += (double) s->NN * wf;
		  E_T[l][3] += (double) s->NN * u ;
		  E_T[l][11]+= (double) s->NN * w2 ;
		  E_T[l][24]+= (double) s->NN * s->w ;
		  E_T[l][25] = max(z,E_T[l][25]);
		  E_T[l][4]  = max(w,E_T[l][4]);
		  E_T[l][5]  = max(u,E_T[l][5]);
		  E_T[l][6]++;
		  E_T[l][35]+= (double)(wf>s->Pw);
		  E_T[l][36]+= (double)(s->N-1) ;
		  E_T[l][37]+= (double)(s->N-1) * s->Pw;
		  E_T[l][38]+= (double)(s->N-1) * s->w;
		  E_T[l][39]+= (double)(-88.75<uopt && uopt<-88.65);
		  E_T[l][40]+= (double)(-88.85<uopt && uopt<-88.75);
		  E_T[l][41]+= (double)(-88.95<uopt && uopt<-88.85);
		  E_T[l][42]+= (double)( uopt < -88.0 );
#if useNe>0
		  E_T[l][43]+= (double) s->Ne;
#endif
		  E_T[l][7] += (double)(s->NN>0);
		  E_T[l][14]+= (double)(s->NT>0);
		  E_T[l][15]+= (double) s->NT;
		  if(s->NT && s->NN>s->NT) E_T[l][16]++;
		  if(uopt > -0.5) { E_T[l][17]+= uopt*s->NN;  E_T[l][18]+=(double)s->NN;  E_T[l][19]+= uopt; E_T[l][20]++;     E_T[l][21]= max(E_T[l][21],uopt); if(uopt>.01 && s->NN > OftNN)E_T[l][22]++;}
		  if(s->NN > OftNN && w2>0.0) { E_T[l][8]++;  E_T[l][9]+= (double)s->NN;  E_T[l][10]+= u2;   E_T[l][12]+= w2;  if(u2>9.0)E_T[l][13]++; }
		  if(s->NN > OftNN && w2==0.0)  E_T[l][23]++;
		}
		if(nT>1 && k==0) {                        /* k==0 are NEW ARRIVALS */
		  Elm[l][m][0] += (double) s->NN;         /* by type, PV */
		  Elm[l][m][1] += (double) s->NN * w ;
		  Elm[l][m][2] += (double) s->NN * wf;
		  Elm[l][m][3] += (double) s->NN * u ;
		  Elm[l][m][11]+= (double) s->NN * w2 ;
		  Elm[l][m][24]+= (double) s->NN * s->w ;
		  Elm[l][m][25] = max(z,Elm[l][m][25]);
		  Elm[l][m][4]  = max(w,Elm[l][m][4]);
		  Elm[l][m][5]  = max(u,Elm[l][m][5]);
		  Elm[l][m][6]++;
		  Elm[l][m][35]+= (double)(wf>s->Pw);
		  Elm[l][m][36]+= (double)(s->N-1) ;
		  Elm[l][m][37]+= (double)(s->N-1) * s->Pw;
		  Elm[l][m][38]+= (double)(s->N-1) * s->w;
		  Elm[l][m][39]+= (double)(-88.75<uopt && uopt<-88.65);
		  Elm[l][m][40]+= (double)(-88.85<uopt && uopt<-88.75);
		  Elm[l][m][41]+= (double)(-88.95<uopt && uopt<-88.85);
		  Elm[l][m][42]+= (double)( uopt < -88.0 );
#if useNe>0
		  Elm[l][m][43]+= (double) s->Ne;
#endif
		  Elm[l][m][7] += (double)(s->NN>0);
		  Elm[l][m][14]+= (double)(s->NT>0);
		  Elm[l][m][15]+= (double) s->NT;
		  if(s->NT && s->NN>s->NT) Elm[l][m][16]++;
		  if(uopt > -0.5) { Elm[l][m][17]+= uopt*s->NN;  Elm[l][m][18]+=(double)s->NN;  Elm[l][m][19]+= uopt; Elm[l][m][20]++;     Elm[l][m][21]= max(Elm[l][m][21],uopt); if(uopt>.01 && s->NN > OftNN)Elm[l][m][22]++;}
		  if(s->NN > OftNN && w2>0.0) { Elm[l][m][8]++;  Elm[l][m][9]+= (double)s->NN;  Elm[l][m][10]+= u2;   Elm[l][m][12]+= w2;  if(u2>9.0)Elm[l][m][13]++; }
		  if(s->NN > OftNN && w2==0.0)  Elm[l][m][23]++;
		}
		if(k==0) {                                    /* k==0 are NEW ARRIVALS */
		  Elmp[l][m][p][0] += (double) s->NN;         /* by type, PV, price  */
		  Elmp[l][m][p][1] += (double) s->NN * w ;
		  Elmp[l][m][p][2] += (double) s->NN * wf;
		  Elmp[l][m][p][3] += (double) s->NN * u ;
		  Elmp[l][m][p][11]+= (double) s->NN * w2 ;
		  Elmp[l][m][p][24]+= (double) s->NN * s->w ;
		  Elmp[l][m][p][25] = max(z,Elmp[l][m][p][25]);
		  Elmp[l][m][p][4]  = max(w,Elmp[l][m][p][4]);
		  Elmp[l][m][p][5]  = max(u,Elmp[l][m][p][5]);
		  Elmp[l][m][p][6]++;
		  Elmp[l][m][p][35]+= (double)(wf>s->Pw);
		  Elmp[l][m][p][36]+= (double)(s->N-1) ;
		  Elmp[l][m][p][37]+= (double)(s->N-1) * s->Pw;
		  Elmp[l][m][p][38]+= (double)(s->N-1) * s->w;
		  Elmp[l][m][p][39]+= (double)(-88.75<uopt && uopt<-88.65);
		  Elmp[l][m][p][40]+= (double)(-88.85<uopt && uopt<-88.75);
		  Elmp[l][m][p][41]+= (double)(-88.95<uopt && uopt<-88.85);
		  Elmp[l][m][p][42]+= (double)( uopt < -88.0 );
#if useNe>0
		  Elmp[l][m][p][43]+= (double) s->Ne;
#endif
		  Elmp[l][m][p][7] += (double)(s->NN>0);
		  Elmp[l][m][p][14]+= (double)(s->NT>0);
		  Elmp[l][m][p][15]+= (double) s->NT;
		  if(s->NT && s->NN>s->NT) Elmp[l][m][p][16]++;
		  if(uopt > -0.5) { Elmp[l][m][p][17]+= uopt*s->NN;  Elmp[l][m][p][18]+=(double)s->NN;  Elmp[l][m][p][19]+= uopt; Elmp[l][m][p][20]++;     Elmp[l][m][p][21]= max(Elmp[l][m][p][21],uopt); if(uopt>.01 && s->NN > OftNN)Elmp[l][m][p][22]++;}
		  if(s->NN > OftNN && w2>0.0) { Elmp[l][m][p][8]++;  Elmp[l][m][p][9]+= (double)s->NN;  Elmp[l][m][p][10]+= u2;   Elmp[l][m][p][12]+= w2;  if(u2>9.0)Elmp[l][m][p][13]++; }
		  if(s->NN > OftNN && w2==0.0)  Elmp[l][m][p][23]++;
		}
		if(k==0) {                         /* k==0 are NEW ARRIVALS */
		  EPV[m][0] += (double) s->NN;     /* by PV */
		  EPV[m][1] += (double) s->NN * w ;
		  EPV[m][2] += (double) s->NN * wf;
		  EPV[m][3] += (double) s->NN * u ;
		  EPV[m][11]+= (double) s->NN * w2 ;
		  EPV[m][24]+= (double) s->NN * s->w ;
		  EPV[m][25] = max(z,EPV[m][25]);
		  EPV[m][4]  = max(w,EPV[m][4]);
		  EPV[m][5]  = max(u,EPV[m][5]);
		  EPV[m][6]++;
		  EPV[m][35]+= (double)(wf>s->Pw);
		  EPV[m][36]+= (double)(s->N-1) ;
		  EPV[m][37]+= (double)(s->N-1) * s->Pw;
		  EPV[m][38]+= (double)(s->N-1) * s->w;
		  EPV[m][39]+= (double)(-88.75<uopt && uopt<-88.65);
		  EPV[m][40]+= (double)(-88.85<uopt && uopt<-88.75);
		  EPV[m][41]+= (double)(-88.95<uopt && uopt<-88.85);
		  EPV[m][42]+= (double)( uopt < -88.0 );
#if useNe>0
		  EPV[m][43]+= (double) s->Ne;
#endif
		  EPV[m][7] += (double)(s->NN>0);
		  EPV[m][14]+= (double)(s->NT>0);
		  EPV[m][15]+= (double) s->NT;
		  if(s->NT && s->NN>s->NT) EPV[m][16]++;
		  if(uopt > -0.5) { EPV[m][17]+= uopt*s->NN;  EPV[m][18]+=(double)s->NN;  EPV[m][19]+= uopt; EPV[m][20]++;     EPV[m][21]= max(EPV[m][21],uopt); if(uopt>.01 && s->NN > OftNN)EPV[m][22]++;}
		  if(s->NN > OftNN && w2>0.0) { EPV[m][8]++;  EPV[m][9]+= (double)s->NN;  EPV[m][10]+= u2;   EPV[m][12]+= w2;  if(u2>9.0)EPV[m][13]++; }
		  if(s->NN > OftNN && w2==0.0)  EPV[m][23]++;
		}
		if(k!=3 && p<nP) {                /* by price of limitorder --> skip mkt/non orders */
		  E_p[p][0] += (double) s->NN;    /* by PV */
		  E_p[p][1] += (double) s->NN * w ;
		  E_p[p][2] += (double) s->NN * wf;
		  E_p[p][3] += (double) s->NN * u ;
		  E_p[p][11]+= (double) s->NN * w2 ;
		  E_p[p][24]+= (double) s->NN * s->w ;
		  E_p[p][25] = max(z,E_p[p][25]);
		  E_p[p][4]  = max(w,E_p[p][4]);
		  E_p[p][5]  = max(u,E_p[p][5]);
		  E_p[p][6]++;
		  E_p[p][35]+= (double)(wf>s->Pw);
		  E_p[p][36]+= (double)(s->N-1) ;
		  E_p[p][37]+= (double)(s->N-1) * s->Pw;
		  E_p[p][38]+= (double)(s->N-1) * s->w;
		  E_p[p][39]+= (double)(-88.75<uopt && uopt<-88.65);
		  E_p[p][40]+= (double)(-88.85<uopt && uopt<-88.75);
		  E_p[p][41]+= (double)(-88.95<uopt && uopt<-88.85);
		  E_p[p][42]+= (double)( uopt < -88.0 );
#if useNe>0
		  E_p[p][43]+= (double) s->Ne;
#endif
		  E_p[p][7] += (double)(s->NN>0);
		  E_p[p][14]+= (double)(s->NT>0);
		  E_p[p][15]+= (double) s->NT;
		  if(s->NT && s->NN>s->NT) E_p[p][16]++;
		  if(uopt > -0.5) { E_p[p][17]+= uopt*s->NN;  E_p[p][18]+=(double)s->NN;  E_p[p][19]+= uopt; E_p[p][20]++;     E_p[p][21]= max(E_p[p][21],uopt); if(uopt>.01 && s->NN > OftNN)E_p[p][22]++;}
		  if(s->NN > OftNN && w2>0.0) { E_p[p][8]++;  E_p[p][9]+= (double)s->NN;  E_p[p][10]+= u2;   E_p[p][12]+= w2;  if(u2>9.0)E_p[p][13]++; }
		  if(s->NN > OftNN && w2==0.0)  E_p[p][23]++;
		}

		if(k<2 && s->NT==0) {     /* k==3 is mkt-order, k=2 is cancel beliefs (skip both) */
		  Ent[0] += (double) s->NN;
		  Ent[1] += (double) s->NN * w ;
		  Ent[2] += (double) s->NN * wf;
		  Ent[3] += (double) s->NN * u ;
		  Ent[11]+= (double) s->NN * w2 ;
		  Ent[24]+= (double) s->NN * s->w ;
		  Ent[25] = max(z,Ent[25]);
		  Ent[4]  = max(w,Ent[4]);
		  Ent[5]  = max(u,Ent[5]);
		  Ent[6]++;
		  Ent[35]+= (double)(wf>s->Pw);
		  Ent[36]+= (double)(s->N-1) ;
		  Ent[37]+= (double)(s->N-1) * s->Pw;
		  Ent[38]+= (double)(s->N-1) * s->w;
		  Ent[39]+= (double)(-88.75<uopt && uopt<-88.65);
		  Ent[40]+= (double)(-88.85<uopt && uopt<-88.75);
		  Ent[41]+= (double)(-88.95<uopt && uopt<-88.85);
		  Ent[42]+= (double)( uopt < -88.0 );
#if useNe>0
		  Ent[43]+= (double) s->Ne;
#endif
		  Ent[7] += (double)(s->NN>0);
		  Ent[14]+= (double)(s->NT>0);
		  Ent[15]+= (double) s->NT;
		  if(s->NT && s->NN>s->NT) Ent[16]++;
		  if(uopt > -0.5) { Ent[17]+= uopt*s->NN;  Ent[18]+=(double)s->NN;  Ent[19]+= uopt; Ent[20]++;     Ent[21]= max(Ent[21],uopt); if(uopt>.01 && s->NN > OftNN)Ent[22]++;}
		  if(s->NN > OftNN && w2>0.0) { Ent[8]++;  Ent[9]+= (double)s->NN;  Ent[10]+= u2;   Ent[12]+= w2;  if(u2>9.0)Ent[13]++; }
		  if(s->NN > OftNN && w2==0.0)  Ent[23]++;
		}
		if(k<2 && s->NT>0) {     /* k==3 is mkt-order, k==2 is cancel beliefs (skip both) */
		  Eyt[0] += (double) s->NN;
		  Eyt[1] += (double) s->NN * w ;
		  Eyt[2] += (double) s->NN * wf;
		  Eyt[3] += (double) s->NN * u ;
		  Eyt[11]+= (double) s->NN * w2 ;
		  Eyt[24]+= (double) s->NN * s->w ;
		  Eyt[25] = max(z,Eyt[25]);
		  Eyt[4]  = max(w,Eyt[4]);
		  Eyt[5]  = max(u,Eyt[5]);
		  Eyt[6]++;
		  Eyt[35]+= (double)(wf>s->Pw);
		  Eyt[36]+= (double)(s->N-1) ;
		  Eyt[37]+= (double)(s->N-1) * s->Pw;
		  Eyt[38]+= (double)(s->N-1) * s->w;
		  Eyt[39]+= (double)(-88.75<uopt && uopt<-88.65);
		  Eyt[40]+= (double)(-88.85<uopt && uopt<-88.75);
		  Eyt[41]+= (double)(-88.95<uopt && uopt<-88.85);
		  Eyt[42]+= (double)( uopt < -88.0 );
#if useNe>0
		  Eyt[43]+= (double) s->Ne;
#endif
		  Eyt[7] += (double)(s->NN>0);
		  Eyt[14]+= (double)(s->NT>0);
		  Eyt[15]+= (double) s->NT;
		  if(s->NT && s->NN>s->NT) Eyt[16]++;
		  if(uopt > -0.5) { Eyt[17]+= uopt*s->NN;  Eyt[18]+=(double)s->NN;  Eyt[19]+= uopt; Eyt[20]++;     Eyt[21]= max(Eyt[21],uopt); if(uopt>.01 && s->NN > OftNN)Eyt[22]++;}
		  if(s->NN > OftNN && w2>0.0) { Eyt[8]++;  Eyt[9]+= (double)s->NN;  Eyt[10]+= u2;   Eyt[12]+= w2;  if(u2>9.0)Eyt[13]++; }
		  if(s->NN > OftNN && w2==0.0)  Eyt[23]++;
		}
		if(uopt < -88.0 ) {     /* new states */
		  E88[0] += (double) s->NN;
		  E88[1] += (double) s->NN * w ;
		  E88[2] += (double) s->NN * wf;
		  E88[3] += (double) s->NN * u ;
		  E88[11]+= (double) s->NN * w2 ;
		  E88[24]+= (double) s->NN * s->w ;
		  E88[25] = max(z,E88[25]);
		  E88[4]  = max(w,E88[4]);
		  E88[5]  = max(u,E88[5]);
		  E88[6]++;
		  E88[35]+= (double)(wf>s->Pw);
		  E88[36]+= (double)(s->N-1) ;
		  E88[37]+= (double)(s->N-1) * s->Pw;
		  E88[38]+= (double)(s->N-1) * s->w;
		  E88[39]+= (double)(-88.75<uopt && uopt<-88.65);
		  E88[40]+= (double)(-88.85<uopt && uopt<-88.75);
		  E88[41]+= (double)(-88.95<uopt && uopt<-88.85);
		  E88[42]+= (double)( uopt < -88.0 );
#if useNe>0
		  E88[43]+= (double) s->Ne;
#endif
		  E88[7] += (double)(s->NN>0);
		  E88[14]+= (double)(s->NT>0);
		  E88[15]+= (double) s->NT;
		  if(s->NT && s->NN>s->NT) E88[16]++;
		  if(uopt > -0.5) { E88[17]+= uopt*s->NN;  E88[18]+=(double)s->NN;  E88[19]+= uopt; E88[20]++;     E88[21]= max(E88[21],uopt); if(uopt>.01 && s->NN > OftNN)E88[22]++;}
		  if(s->NN > OftNN && w2>0.0) { E88[8]++;  E88[9]+= (double)s->NN;  E88[10]+= u2;   E88[12]+= w2;  if(u2>9.0)E88[13]++; }
		  if(s->NN > OftNN && w2==0.0)  E88[23]++;
		}

		if(p==nP && zT[l]==0) {         /* Non-Orders.  could exclude from other Exx[] but currently do not. */
#if useNe>0
		  E_n[43]+= (double) s->Ne;        ETn[l][43]+= (double) s->Ne;
#endif
		  E_n[0] += (double) s->NN;        ETn[l][0] += (double) s->NN;     
		  E_n[1] += (double) s->NN * w ;   ETn[l][1] += (double) s->NN * w ;
		  E_n[2] += (double) s->NN * wf;   ETn[l][2] += (double) s->NN * wf;
		  E_n[3] += (double) s->NN * u ;   ETn[l][3] += (double) s->NN * u ;
		  E_n[11]+= (double) s->NN * w2;   ETn[l][11]+= (double) s->NN * w2;
		  E_n[24]+= (double) s->NN * s->w; ETn[l][24]+= (double) s->NN * s->w;
		  E_n[25] = max(z,E_n[25]);        ETn[l][25] = max(z,ETn[l][25]);
		  E_n[4]  = max(w,E_n[4]);	   ETn[l][4]  = max(w,ETn[l][4]);
		  E_n[5]  = max(u,E_n[5]);	   ETn[l][5]  = max(u,ETn[l][5]);	
		  E_n[6]++;			   ETn[l][6]++;			
		  E_n[35]+= (double)(wf>s->Pw);	   ETn[l][35]+= (double)(wf>s->Pw);
		  E_n[36]+= (double)(s->N-1) ;	   ETn[l][36]+= (double)(s->N-1) ;
		  E_n[37]+= (double)(s->N-1)*s->Pw;ETn[l][37]+= (double)(s->N-1)*s->Pw;
		  E_n[38]+= (double)(s->N-1)*s->w; ETn[l][38]+= (double)(s->N-1)*s->w;
		  if(-88.75<uopt && uopt<-88.65) { ETn[l][39]++; E_n[39]++; }
		  if(-88.85<uopt && uopt<-88.75) { ETn[l][40]++; E_n[40]++; }
		  if(-88.95<uopt && uopt<-88.85) { ETn[l][41]++; E_n[41]++; }
		  if(               uopt<-88.0 ) { ETn[l][42]++; E_n[42]++; }
		  E_n[7] += (double)(s->NN>0);     ETn[l][7] += (double)(s->NN>0);
		  E_n[14]+= (double)(s->NT>0);     ETn[l][14]+= (double)(s->NT>0);
		  E_n[15]+= (double) s->NT;        ETn[l][15]+= (double) s->NT;
		  if(s->NT && s->NN>s->NT) { E_n[16]++;  ETn[l][16]++; }
		  if(uopt > -0.5) { 
		    E_n[17]+= uopt*s->NN;     E_n[18]+=(double)s->NN;     E_n[19]+= uopt;     E_n[20]++;     E_n[21]= max(E_n[21],uopt);        if(uopt>.01 && s->NN > OftNN)E_n[22]++;
		    ETn[l][17]+= uopt*s->NN;  ETn[l][18]+=(double)s->NN;  ETn[l][19]+= uopt;  ETn[l][20]++;  ETn[l][21]= max(ETn[l][21],uopt);  if(uopt>.01 && s->NN > OftNN)ETn[l][22]++; }
		  if(s->NN > OftNN && w2>0.0) {
		    E_n[8]++;     E_n[9]+= (double)s->NN;     E_n[10]+= u2;     E_n[12]+= w2;     if(u2>9.0)E_n[13]++;
		    ETn[l][8]++;  ETn[l][9]+= (double)s->NN;  ETn[l][10]+= u2;  ETn[l][12]+= w2;  if(u2>9.0)ETn[l][13]++; }
		  if(s->NN > OftNN && w2==0.0) {  E_n[23]++;  ETn[l][23]++; }
		}
		if(k==0 && p<nP) {                         /* NEW Arrival Limit Orders */
#if useNe>0 
		  E_0[43]+= (double) s->Ne;        ET0[l][43]+= (double) s->Ne;
#endif
		  E_0[0] += (double) s->NN;        ET0[l][0] += (double) s->NN;	
		  E_0[1] += (double) s->NN * w ;   ET0[l][1] += (double) s->NN * w ;
		  E_0[2] += (double) s->NN * wf;   ET0[l][2] += (double) s->NN * wf;
		  E_0[3] += (double) s->NN * u ;   ET0[l][3] += (double) s->NN * u ;
		  E_0[11]+= (double) s->NN * w2;   ET0[l][11]+= (double) s->NN * w2;
		  E_0[24]+= s->NN*fabs(s->w);      ET0[l][24]+=          s->NN * fabs(s->w);
		  E_0[25] = max(z,E_0[25]);        ET0[l][25] = max(z,ET0[l][25]);
		  E_0[4]  = max(w,E_0[4]);	   ET0[l][4]  = max(w,ET0[l][4]);	
		  E_0[5]  = max(u,E_0[5]);	   ET0[l][5]  = max(u,ET0[l][5]);	
		  E_0[6]++;			   ET0[l][6]++;			
		  E_0[35]+= (double)(wf>s->Pw);	   ET0[l][35]+= (double)(wf>s->Pw);
		  E_0[36]+= (double)(s->N-1) ;	   ET0[l][36]+= (double)(s->N-1) ;
		  E_0[37]+= (double)(s->N-1)*s->Pw;ET0[l][37]+= (double)(s->N-1)*s->Pw;
		  E_0[38]+= (double)(s->N-1)*s->w; ET0[l][38]+= (double)(s->N-1)*s->w;
		  if(-88.75<uopt && uopt<-88.65) { ET0[l][39]++; E_0[39]++; }
		  if(-88.85<uopt && uopt<-88.75) { ET0[l][40]++; E_0[40]++; }
		  if(-88.95<uopt && uopt<-88.85) { ET0[l][41]++; E_0[41]++; }
		  if(               uopt<-88.0 ) { ET0[l][42]++; E_0[42]++; }
		  E_0[7] += (double)(s->NN>0);     ET0[l][7] += (double)(s->NN>0);  
		  E_0[14]+= (double)(s->NT>0);     ET0[l][14]+= (double)(s->NT>0);  
		  E_0[15]+= (double) s->NT;        ET0[l][15]+= (double) s->NT;  
		  if(s->NT && s->NN>s->NT) { E_0[16]++;  ET0[l][16]++; }       /* ignore  uopt  stuff for mkt orders */
		  if(s->NN > OftNN && w2>0.0) {
		    E_0[8]++;     E_0[9]+= (double)s->NN;     E_0[10]+= u2;     E_0[12]+= w2;     if(u2>9.0)E_0[13]++;
		    ET0[l][8]++;  ET0[l][9]+= (double)s->NN;  ET0[l][10]+= u2;  ET0[l][12]+= w2;  if(u2>9.0)ET0[l][13]++; }
		  if(s->NN > OftNN && w2==0.0) {  E_0[23]++;  ET0[l][23]++; }
		}

		if( k==1 ) {  		/* Retained Orders */
#if useNe>0 
		  E_1[43]+= (double) s->Ne;        ET1[l][43]+= (double) s->Ne;
#endif
		  E_1[0] += (double) s->NN;        ET1[l][0] += (double) s->NN;      /* CAN get here with book = 2 */
		  E_1[1] += (double) s->NN * w ;   ET1[l][1] += (double) s->NN * w ;
		  E_1[2] += (double) s->NN * wf;   ET1[l][2] += (double) s->NN * wf;
		  E_1[3] += (double) s->NN * u ;   ET1[l][3] += (double) s->NN * u ;
		  E_1[11]+= (double) s->NN * w2;   ET1[l][11]+= (double) s->NN * w2;
		  E_1[24]+= (double) s->NN * s->w; ET1[l][24]+= (double) s->NN * s->w;
		  E_1[25] = max(z,E_1[25]);        ET1[l][25] = max(z,ET1[l][25]);
		  E_1[4]  = max(w,E_1[4]);	   ET1[l][4]  = max(w,ET1[l][4]);	
		  E_1[5]  = max(u,E_1[5]);	   ET1[l][5]  = max(u,ET1[l][5]);	
		  E_1[6]++;			   ET1[l][6]++;
		  E_1[35]+= (double)(wf>s->Pw);	   ET1[l][35]+= (double)(wf>s->Pw);
		  E_1[36]+= (double)(s->N-1) ;	   ET1[l][36]+= (double)(s->N-1) ;
		  E_1[37]+= (double)(s->N-1)*s->Pw;ET1[l][37]+= (double)(s->N-1)*s->Pw;
		  E_1[38]+= (double)(s->N-1)*s->w; ET1[l][38]+= (double)(s->N-1)*s->w;
		  if(-88.75<uopt && uopt<-88.65) { ET1[l][39]++; E_1[39]++; }
		  if(-88.85<uopt && uopt<-88.75) { ET1[l][40]++; E_1[40]++; }
		  if(-88.95<uopt && uopt<-88.85) { ET1[l][41]++; E_1[41]++; }
		  if(               uopt<-88.0 ) { ET1[l][42]++; E_1[42]++; }
		  E_1[7] += (double)(s->NN>0);     ET1[l][7] += (double)(s->NN>0);  
		  E_1[14]+= (double)(s->NT>0);     ET1[l][14]+= (double)(s->NT>0);  
		  E_1[15]+= (double) s->NT;        ET1[l][15]+= (double) s->NT;
		  if(s->NT && s->NN>s->NT) { E_1[16]++;  ET1[l][16]++; }
		  if(uopt > -0.5) { 
		    E_1[17]+= uopt*s->NN;     E_1[18]+=(double)s->NN;     E_1[19]+= uopt;     E_1[20]++;     E_1[21]= max(E_1[21],uopt);        if(uopt>.01 && s->NN > OftNN)E_1[22]++;
		    ET1[l][17]+= uopt*s->NN;  ET1[l][18]+=(double)s->NN;  ET1[l][19]+= uopt;  ET1[l][20]++;  ET1[l][21]= max(ET1[l][21],uopt);  if(uopt>.01 && s->NN > OftNN)ET1[l][22]++; }
		  if(s->NN > OftNN && w2>0.0) {
		    E_1[8]++;     E_1[9]+= (double)s->NN;     E_1[10]+= u2;     E_1[12]+= w2;     if(u2>9.0)E_1[13]++;
		    ET1[l][8]++;  ET1[l][9]+= (double)s->NN;  ET1[l][10]+= u2;  ET1[l][12]+= w2;  if(u2>9.0)ET1[l][13]++; }
		  if(s->NN > OftNN && w2==0.0) {  E_1[23]++;  ET1[l][23]++; }
		}   /* HEY:  these k=2 and above retained states may not be accurately chosen */

		if( k==2 ) {   /* Cancelled Order =  E( value replacement order | info prior to cancel ) */
#if useNe>0 
		  E_2[43]+= (double) s->Ne;        ET2[l][43]+= (double) s->Ne;
#endif
		  E_2[0] += (double) s->NN;        ET2[l][0] += (double) s->NN;     
		  E_2[1] += (double) s->NN * w ;   ET2[l][1] += (double) s->NN * w ;
		  E_2[2] += (double) s->NN * wf;   ET2[l][2] += (double) s->NN * wf;
		  E_2[3] += (double) s->NN * u ;   ET2[l][3] += (double) s->NN * u ;
		  E_2[11]+= (double) s->NN * w2;   ET2[l][11]+= (double) s->NN * w2;
		  E_2[24]+= (double) s->NN * s->w; ET2[l][24]+= (double) s->NN * s->w;
		  E_2[25] = max(z,E_2[25]);        ET2[l][25] = max(z,ET2[l][25]);
		  E_2[4]  = max(w,E_2[4]);	   ET2[l][4]  = max(w,ET2[l][4]);	
		  E_2[5]  = max(u,E_2[5]);	   ET2[l][5]  = max(u,ET2[l][5]);	
		  E_2[6]++;			   ET2[l][6]++;			
		  E_2[35]+= (double)(wf>s->Pw);	   ET2[l][35]+= (double)(wf>s->Pw);
		  E_2[36]+= (double)(s->N-1) ;	   ET2[l][36]+= (double)(s->N-1) ;
		  E_2[37]+= (double)(s->N-1)*s->Pw;ET2[l][37]+= (double)(s->N-1)*s->Pw;
		  E_2[38]+= (double)(s->N-1)*s->w; ET2[l][38]+= (double)(s->N-1)*s->w;
		  if(-88.75<uopt && uopt<-88.65) { ET2[l][39]++; E_2[39]++; }
		  if(-88.85<uopt && uopt<-88.75) { ET2[l][40]++; E_2[40]++; }
		  if(-88.95<uopt && uopt<-88.85) { ET2[l][41]++; E_2[41]++; }
		  if(               uopt<-88.0 ) { ET2[l][42]++; E_2[42]++; }
		  E_2[7] += (double)(s->NN>0);     ET2[l][7] += (double)(s->NN>0);  
		  E_2[14]+= (double)(s->NT>0);     ET2[l][14]+= (double)(s->NT>0);  
		  E_2[15]+= (double) s->NT;        ET2[l][15]+= (double) s->NT;  
		  if(uopt > -0.5) { 
		    E_2[17]+= uopt*s->NN;     E_2[18]+=(double)s->NN;     E_2[19]+= uopt;     E_2[20]++;     E_2[21]= max(E_2[21],uopt);        if(uopt>.01 && s->NN > OftNN)E_2[22]++;
		    ET2[l][17]+= uopt*s->NN;  ET2[l][18]+=(double)s->NN;  ET2[l][19]+= uopt;  ET2[l][20]++;  ET2[l][21]= max(ET2[l][21],uopt);  if(uopt>.01 && s->NN > OftNN)ET2[l][22]++; }
		  if(s->NT && s->NN>s->NT) { E_2[16]++;  ET2[l][16]++; }
		  if(s->NN > OftNN && w2>0.0) {
		    E_2[8]++;     E_2[9]+= (double)s->NN;     E_2[10]+= u2;     E_2[12]+= w2;     if(u2>9.0)E_2[13]++;
		    ET2[l][8]++;  ET2[l][9]+= (double)s->NN;  ET2[l][10]+= u2;  ET2[l][12]+= w2;  if(u2>9.0)ET2[l][13]++; }
		  if(s->NN > OftNN && w2==0.0) {  E_2[23]++;  ET2[l][23]++; }
		}
		if(k==3) {                         /* E(v) for Market Orders */
#if useNe>0 
		  E_3[43]+= (double) s->Ne;        ET3[l][43]+= (double) s->Ne;
#endif
		  E_3[0] += (double) s->NN;        ET3[l][0] += (double) s->NN;	
		  E_3[1] += (double) s->NN * w ;   ET3[l][1] += (double) s->NN * w ;
		  E_3[2] += (double) s->NN*fabs(w);ET3[l][2] += (double) s->NN*fabs(w);  /* report mean|w| instead of mean wf for mkt orders */
		  E_3[3] += (double) s->NN * u ;   ET3[l][3] += (double) s->NN * u ;
		  E_3[11]+= (double) s->NN * w2;   ET3[l][11]+= (double) s->NN * w2;
		  E_3[24]+= s->NN*fabs(s->w);      ET3[l][24]+=          s->NN * fabs(s->w);
		  E_3[25] = max(z,E_3[25]);        ET3[l][25] = max(z,ET3[l][25]);
		  E_3[4]  = max(w,E_3[4]);	   ET3[l][4]  = max(w,ET3[l][4]);	
		  E_3[5]  = max(u,E_3[5]);	   ET3[l][5]  = max(u,ET3[l][5]);	
		  E_3[6]++;			   ET3[l][6]++;			
		  E_3[35]+= (double)(wf>s->Pw);	   ET3[l][35]+= (double)(wf>s->Pw);
		  E_3[36]+= (double)(s->N-1) ;	   ET3[l][36]+= (double)(s->N-1) ;
		  E_3[37]+= (double)(s->N-1)*s->Pw;ET3[l][37]+= (double)(s->N-1)*s->Pw;
		  E_3[38]+= (double)(s->N-1)*s->w; ET3[l][38]+= (double)(s->N-1)*s->w;
		  if(-88.75<uopt && uopt<-88.65) { ET3[l][39]++; E_3[39]++; }
		  if(-88.85<uopt && uopt<-88.75) { ET3[l][40]++; E_3[40]++; }
		  if(-88.95<uopt && uopt<-88.85) { ET3[l][41]++; E_3[41]++; }
		  if(               uopt<-88.0 ) { ET3[l][42]++; E_3[42]++; }
		  E_3[7] += (double)(s->NN>0);     ET3[l][7] += (double)(s->NN>0);  
		  E_3[14]+= (double)(s->NT>0);     ET3[l][14]+= (double)(s->NT>0);  
		  E_3[15]+= (double) s->NT;        ET3[l][15]+= (double) s->NT;  
		  if(s->NT && s->NN>s->NT) { E_3[16]++;  ET3[l][16]++; }       /* ignore  uopt  stuff for mkt orders */
		  if(s->NN > OftNN && w2>0.0) {
		    E_3[8]++;     E_3[9]+= (double)s->NN;     E_3[10]+= u2;     E_3[12]+= w2;     if(u2>9.0)E_3[13]++;
		    ET3[l][8]++;  ET3[l][9]+= (double)s->NN;  ET3[l][10]+= u2;  ET3[l][12]+= w2;  if(u2>9.0)ET3[l][13]++; }
		  if(s->NN > OftNN && w2==0.0) {  E_3[23]++;  ET3[l][23]++; }
		}

		/* only track New and Retained limitorders as "Updating" since Cancel and Market (ret = 3,4) are known to have higher chi-sq  */
		if( UPDATE_maxPVT<1.0 && ( (UPDATE_PVT[l][m] && k<2) /* || (UPDATE_mkt[l] && k==3) */ ) ) { /* Not ALL s updated, so track those s that are */
		  Eut[0] += (double) s->NN;
		  Eut[1] += (double) s->NN * w ;
		  Eut[2] += (double) s->NN * wf;
		  Eut[3] += (double) s->NN * u ;
		  Eut[11]+= (double) s->NN * w2 ;
		  Eut[24]+= (double) s->NN * s->w ;
		  Eut[25] = max(z,Eut[25]);
		  Eut[4]  = max(w,Eut[4]);
		  Eut[5]  = max(u,Eut[5]);
		  Eut[6]++;
		  Eut[35]+= (double)(wf>s->Pw);
		  Eut[36]+= (double)(s->N-1) ;
		  Eut[37]+= (double)(s->N-1) * s->Pw;
		  Eut[38]+= (double)(s->N-1) * s->w;
		  Eut[39]+= (double)(-88.75<uopt && uopt<-88.65);
		  Eut[40]+= (double)(-88.85<uopt && uopt<-88.75);
		  Eut[41]+= (double)(-88.95<uopt && uopt<-88.85);
		  Eut[42]+= (double)( uopt < -88.0 );
#if useNe>0 
		  Eut[43]+= (double) s->Ne;
#endif
		  Eut[7] += (double)(s->NN>0);
		  Eut[14]+= (double)(s->NT>0);
		  Eut[15]+= (double) s->NT;
		  if(s->NT && s->NN>s->NT) Eut[16]++;
		  if(uopt > -0.5) { Eut[17]+= uopt*s->NN;  Eut[18]+=(double)s->NN;  Eut[19]+= uopt; Eut[20]++;     Eut[21]= max(Eut[21],uopt); if(uopt>.01 && s->NN > OftNN)Eut[22]++;}
		  if(s->NN > OftNN && w2>0.0) { Eut[8]++;  Eut[9]+= (double)s->NN;  Eut[10]+= u2;   Eut[12]+= w2;  if(u2>9.0)Eut[13]++; }
		  if(s->NN > OftNN && w2==0.0)  Eut[23]++;
		}

		/* force LARGE s->w not too optimistic, based on "fact" that extreme beta pay trans.cost, on average */
		/* if(zT[l]){ z = k * fabs(PV[m]) -.001;   if(s->w > z) { s->w = z; Tcount[16]++;  Tcount[15]=max(Tcount[15],s->N); s->N = 1; } } */

#if nB==nP
		bid = nP;  while(--bid > -1 && s->B[bid]<=0) { }    /* high bid = -1 if no break */
		ask = bid; while(++ask < nP && s->B[ask]>=0) { }    /* low  ask = nP if no break */
#else
		bid = s->B[0];
		ask = s->B[1];
#endif
		if (outMkt!=NULL && s->NN>OftNN && k==0 && i==0) {
		  fprintf(outMkt, "%8.5f %8.5f %d %d %d",  s->w, w2, l, bid, ask);  /* m=0, p=0, k=0 always */
		  for(a=0; a<nB;    a++) fprintf(outMkt, " %d", s->B[a]);
		  for(a=0; a<nHnRT; a++) fprintf(outMkt, " %d", *(s->H[0]+a) );
		  fprintf(outMkt, " %u %u\n", s->N,s->NN);
		}

#if WRITE>0 && use_w0sell  /* UPDATE w0sell:  NN based weights requires UPDATE_s0 = 1, which is checked for in main */
		if (ask==0 && s->NC) {
		  N_s[3]++;     /* count ask==0 */
		  if(WRITE==0)  /* avoid printing clutter while converging */
		    printf("\n ask=0 --> bid=0=ask invoked when really want bid = -1 ask = 0.  oh well, must be rare, indeed: s->N~NN~NC = %u %u %u\n", s->N,s->NN,s->NC); }

		/* Is N or NN better weight??  N uses ALL states --> more information, but some s->w may be pessimistic.  NN may be too few.  If use NN may not want to REPLACE w0sell */
		z = (double) s->N;         /* z makes easy to go back and forth between N,NN.   I think  N  is better.  Hence I've commented out NN option*/
		/* u = 0.0;  */            /* weight to use on current w0sell[] upon find 1st matching s.  use 0.0 if z = N */
		if( k!=3 &&  z > 0.0 ) {   /* ignore k==3 mkt orders (should create new dimension for mkt beliefs w0sell[k==0]?)  Q: could use only s->NN > 10 or so ??  */
		  if(zT[l]) { a=k;  }      /* large tz:  REQUIRES zmax < nPmax since using k (shares togo) as [p] index */
		  else {                   /* not large tz */
		    a = p;
		    /* bid=0=ask : integrate over all bid,ask to use when bid,ask not yet encountered.  NOT for LARGE tz. */
		    if(M0sell[l][p][0][0][m]) {
		      w0sell[ l][p][0][0][m] +=  z * s->w ;
		      N0sell[ l][p][0][0][m] +=  z;
		      w2sell[ l][p][0][0][m] +=  z * s->w * s->w ;
		      M0sell[ l][p][0][0][m] ++; }
		    else {
		      /* if(w0sell[l][p][0][0][m] < -99.9) u2 = 0.0; else u2= u;
			 w0sell[   l][p][0][0][m] *=  u; */     /* u = weight to use on current w0sell[] upon encountering 1st matching state */
		      w0sell[ l][p][0][0][m]  =  z * s->w;
		      N0sell[ l][p][0][0][m]  =  z /* + u */ ;
		      w2sell[ l][p][0][0][m]  =  z * s->w * s->w;              /* HEY:  could PURGE  s  if  s->w  <<<  w0sell[] ?????? */
		      M0sell[ l][p][0][0][m]  =  1; }
		  }  /* end not large tz */
		  /* weight by NN (if UPDATE_s0=1) to avoid w0sell becoming pessimistic ?? */
		  if (ask) {
		    if(M0sell[l][a][max(bid,0)][ask][m]) {  /* since NN based, some w0sell[] NOT updated, so must detect 1st valid s for update */
		      w0sell[ l][a][max(bid,0)][ask][m] +=  z * s->w ;           /* combine bid= -1, 0 rather than shift bid to allow bid -1 */
		      N0sell[ l][a][max(bid,0)][ask][m] +=  z ;                  /* Harmless??   since market similar w/ bid=0 or bid = -1 ? */
		      w2sell[ l][a][max(bid,0)][ask][m] +=  z * s->w * s->w ;    /* COULD allow ask=nP (since nPmax>nP required elsewhere)   */
		      M0sell[ l][a][max(bid,0)][ask][m] ++; }                    /* but for symmetry with truncated bid, also truncate ask.  */
		    else {
		      /* if(w0sell[l][a][max(bid,0)][ask][m] < -99.9) u2 = 0.0; else u2= u;
			 w0sell[   l][a][max(bid,0)][ask][m] *=  u ; */       /* u = weight to use on current w0sell[] upon encountering 1st matching state */
		      w0sell[ l][a][max(bid,0)][ask][m]  =  z * s->w;
		      N0sell[ l][a][max(bid,0)][ask][m]  =  z /* + u */ ;
		      w2sell[ l][a][max(bid,0)][ask][m]  =  z * s->w * s->w ;
		      M0sell[ l][a][max(bid,0)][ask][m]  =  1; }
		  } /* ignore rare ask=0 since bid=0=ask refers to averaging over all bid,ask */
		}   /* if(k)   ie, not E(v) state for which w0sell[] not used. */
#endif
#if WRITE>0
		if (s->N >= WRITE && outB!=NULL) {            /* if outB==NULL then NO writes despite WRITE being defined */
		  fwrite(  (char *) &m,     1, 2, outB );     /* pvtype */
		  fwrite(  (char *) &i,     1, 2, outB );     /* 0/1 for Seller/Buyer */
		  fwrite(  (char *) &l,     1, 2, outB );     /* trader type */
		  fwrite(  (char *) &p,     1, 2, outB );     /* p  index */
		  fwrite(  (char *) &k,     1, 2, outB );     /* "old queue" currently 0-3 for  New,Retained,Canceled,E(v) */
		  fwrite(  (char *) &s->B,  1, 2*nB, outB);   /* book info */
		  if(nL)
		    fwrite((char *) &s->L,  1, 2*nL, outB);   /* Lagged order info */
		  if(nHnRT)
		    fwrite((char *) &s->H,  1, 2*nHnRT,outB); /* H history */
		  fwrite(  (char *) &s->NC, 1, 4, outN );
		  fwrite(  (char *) &s->N,  1, 4, outN );
		  fwrite(  (char *) &s->NN, 1, 4, outN );     /* NT     NOT written */
		  fwrite(  (char *) &s->w,  1, 8, outw );     /* Ew, w2 NOT written */
		}
#endif
		/* WARNING:  next code is VERY MESSY handling of myriad of options for: nJ  PURGE   UPDATE_maxPVT  Nreset */
		
		if (Purge && (s->NC < PURGE_NC || (PURGE_MODE > 0 && s->N <= PURGE_N) )) {
		  *sp= s->next;  free(s);  s= *sp;  Purge++; }    /* remove s from list */
		else {                                            /* else s not purged  */
		  if (Purge) s->NC = 0;    
#if useNNtt>0
		  if (s->NNtt && s->NNtt==s->NCtt) { s->NNtt =1;  s->NCtt =1; } /* test AlWAYS across jj */
		  if (s->NNtj && s->NNtj==s->NCtj) { s->NNtj =1;  s->NCtj =1; }
		  if (s->NCtt && s->NNtt==0)       { s->NNtt =0;  s->NCtt =1; } /* test NEVER  across jj */
		  if (s->NCtj && s->NNtj==0)       { s->NNtj =0;  s->NCtj =1; }
		  if (s->NNtt && s->NNtt <s->NCtt) { s->NNtt =0;  s->NCtt =0; } /* failed this jj, reset for next jj */
		  if (s->NNtj && s->NNtj <s->NCtj) { s->NNtj =0;  s->NCtj =0; }
#endif
		  s->Pw = s->w;            /* Pw is w from previous jj */
		  if ( (UPDATE_PVT[l][m] && k!=3) || (UPDATE_mkt[l] && k==3) ) {   /* consider resetting N unless beliefs fixed */
		    if ( Nreset >0) {
		      s->NC = 0;             /* NEW s->NC use --> NC must be reset each time NN, NT reset. OLD use rarely reset NC */
		      if(Nreset==1)     s->N = 1;
		      else if(Nreset>1) s->N = min(s->N, Nreset*(jj+1));       /* reset "less extreme" as jj increases */
		    }
		    if(Nreset!=0 && Nreset_xNN && s->NN>OftNN) /* prevent learning too slow --> reset N no more extreme than some multiple of NN */
		      s->N = min(s->N, s->NN*Nreset_xNN);      /* Nreset_xNN = 10 --> next NN encounters get 1/10 weight. */
		  }
		  if ( UPDATE_sync || (UPDATE_maxPVT /* && Nreset>0 */ )) {  /* COULD use UPDATE_PVT[][] to avoid undesired resets for fixed beliefs */
#if useNe>0
		    s->Ne = 0;  /* Ne=0 was elsewhere more sophisticated, but want to minimize #if useNNtt clutter */
#endif
		    s->NN = 0;
		    /* s->NC = 0; */     /* NEW s->NC use --> NC must be reset each time NN, NT reset. OLD use rarely reset NC */
		    s->NT = 0;
		    s->Ew = 0.0;   /* reset trader count, cum payoffs, cum payoffs^2 for traders with initial order at this s.   */
		    s->w2 = 0.0;
		  }                /* also skip resets if UPDATE_maxPVT =0  --> Ee/NN, Ej/NN (for chisq) more accurate with higher NN */
		  s->N = min(s->N, 1e8);     /* max unsigned int 4,294,967,295 */  
		  /* s->NC= min(s->NC,1e8); */    /* next line avoids NN overflow, rescales Ew,w2,NT accordingly */
		  s->NC= min(s->NC,10);     /* next line avoids NN overflow, rescales Ew,w2,NT accordingly */
		  if (s->NN > 1e8) {
		    u= 1e8/s->NN;  s->Ew= s->Ew*u;  s->w2= s->w2*u;  s->NT= (unsigned int)(s->NT*u);   s->NN = 1e8;
#if useNe>0
		    s->Ne= (unsigned int)(s->Ne*u);
#endif
		  }
#if usePN>0
		  s->PN = s->N;
		  s->NC = 0;
#endif
		  sp = &s->next;
		  s  =  s->next;
		}                  /* close else { s not purged }.   NOTE: s->NC not reset unless Purged,  see above  if(Purge)  */
	      } /* while (s!=NULL) */
	      Tcount[3] = max(Tcount[1], Tcount[3]);  /* most states per bucket */
	    }   /* h bucket loop   */
	    Tcount[6] = max(Tcount[5], Tcount[6]);    /* most buckets per HT    */
	  }     /* if HT[m,i,l,k,p]  */
	} } } }     /* p,k,m,l loops */
  printf("\n");
  /* 2nd sig number is UnWeighted average (given s->NN > OftNN).  For Mkt-Orders high sig --> high uncertainty about current v */
  /* explanation of 4 "Chi" numbers :  1) Chi/df  2) share N(0,1) error>3 (0.0027 in theory)  3) #NN>OftNN  4) sum(NN used)/sum(NN all)  5) df/sum(NN>0) */
  if(nT>1) {
    printf("\n max:w |Pw-wf|Pw-w|  /N:  w    Pw     /NN:  w    Pw        wf     wf>Pw |Pw-wf|   ||/Pw sig:/NN /OftNN Oft&0            #states    Chi/df  N(0,1)>3    df   NNused       new w0sell   eUB     sx    NT>0    NN>0  NN>NT   sumNT    sumN-1    sumNN    Ne/NN   Eopt:max   ave    /NN   >.01\n");
    for(l=0; l<nT; l++) {
      for(m=0; m<nPV; m++) {    if(Elm[l][m][6] > 0.0) printf("\n");
	for(k=0; k<nPmax; k++) {
	  if (Elmp[l][m][k][6] > 0.0) {
	    for(p=0; p<44; p++) E[p] = Elmp[l][m][k][p];
	    printf(" %5.2f %5.2f %6.3f %8.5f %8.5f %8.5f %8.5f %8.5f  %5.3f %7.4f %7.4f  %6.3f %6.3f%5.0f Tpvp=%d %d %2d %7.0f  %8.2f   %6.4f %6.0f  %4.2f %4.2f  %6.0f %6.0f %5.0f %6.0f  %6.0f %7.0f  %5.0f  %6.0f %9.0f %9.0f %6.4f  %6.4f %6.4f %6.4f %6.0f\n",
		   E[4], E[5], E[25], E[38]/E[36], E[37]/E[36], E[24]/E[0], E[1]/E[0], E[2]/E[0], E[35]/E[6], E[3]/E[0], E[3]/E[1], E[11]/E[0], E[12]/E[8], E[23],l,m,k,E[6], E[10]/E[8], E[13]/E[8], E[8], E[9]/E[0], E[8]/E[7], E[42],E[39],E[40],E[41], E[14],E[7],E[16], E[15],E[36],E[0],E[43]/E[0], E[21], E[19]/E[20], E[17]/E[18], E[22]); }
	} } }
    printf("\n above are NEW only (ie, k=0) below are mix\n");
    printf("\n max:w |Pw-wf|Pw-w|  /N:  w    Pw     /NN:  w    Pw        wf     wf>Pw |Pw-wf|   ||/Pw sig:/NN /OftNN Oft&0            #states    Chi/df  N(0,1)>3    df   NNused       new w0sell   eUB     sx    NT>0    NN>0  NN>NT   sumNT    sumN-1    sumNN    Ne/NN   Eopt:max   ave    /NN   >.01\n");
    for(l=0; l<nT; l++) {
      for(m=0; m<nPV; m++) {
	if (Elm[l][m][6] > 0.0) {
	  for(p=0; p<44; p++) E[p] = Elm[l][m][p];
	  printf(" %5.2f %5.2f %6.3f %8.5f %8.5f %8.5f %8.5f %8.5f  %5.3f %7.4f %7.4f  %6.3f %6.3f%5.0f  T~pv= %d %d %8.0f  %8.2f   %6.4f %6.0f  %4.2f %4.2f  %6.0f %6.0f %5.0f %6.0f  %6.0f %7.0f  %5.0f  %6.0f %9.0f %9.0f %6.4f  %6.4f %6.4f %6.4f %6.0f\n",
		 E[4], E[5], E[25], E[38]/E[36], E[37]/E[36], E[24]/E[0], E[1]/E[0], E[2]/E[0], E[35]/E[6], E[3]/E[0], E[3]/E[1], E[11]/E[0], E[12]/E[8], E[23],l,m,E[6], E[10]/E[8], E[13]/E[8], E[8], E[9]/E[0], E[8]/E[7], E[42],E[39],E[40],E[41], E[14],E[7],E[16], E[15],E[36],E[0],E[43]/E[0], E[21], E[19]/E[20], E[17]/E[18], E[22]); }
      }
      if (ET0[l][6] > 0.0) {
	for(p=0; p<44; p++) E[p] = ET0[l][p];    /* E_0[2,24] used  fabs() since mean s->w is 0 across states since Mkt-Ord s->w is E(v|stuff).  mae/w uses /E[2] instead of /E[1] */
	printf(" %5.2f %5.2f %6.3f %8.5f %8.5f %8.5f|%8.5f %8.5f| %5.3f %7.4f %7.4f  %6.3f %6.3f%5.0f  new lim   %8.0f  %8.2f   %6.4f %6.0f  %4.2f %4.2f  %6.0f %6.0f %5.0f %6.0f  %6.0f %7.0f  %5.0f  %6.0f %9.0f %9.0f %6.4f  %6.4f %6.4f %6.4f %6.0f\n",
	       E[4], E[5], E[25], E[38]/E[36], E[37]/E[36], E[24]/E[0], E[1]/E[0], E[2]/E[0], E[35]/E[6], E[3]/E[0], E[3]/E[2], E[11]/E[0], E[12]/E[8], E[23],      E[6], E[10]/E[8], E[13]/E[8], E[8], E[9]/E[0], E[8]/E[7], E[42],E[39],E[40],E[41], E[14],E[7],E[16], E[15],E[36],E[0],E[43]/E[0], E[21], E[19]/E[20], E[17]/E[18], E[22]); }
      if (ET1[l][6] > 0.0) {
	for(p=0; p<44; p++) E[p] = ET1[l][p];
	printf(" %5.2f %5.2f %6.3f %8.5f %8.5f %8.5f %8.5f %8.5f  %5.3f %7.4f %7.4f  %6.3f %6.3f%5.0f  retained  %8.0f  %8.2f   %6.4f %6.0f  %4.2f %4.2f  %6.0f %6.0f %5.0f %6.0f  %6.0f %7.0f  %5.0f  %6.0f %9.0f %9.0f %6.4f  %6.4f %6.4f %6.4f %6.0f\n",
	       E[4], E[5], E[25], E[38]/E[36], E[37]/E[36], E[24]/E[0], E[1]/E[0], E[2]/E[0], E[35]/E[6], E[3]/E[0], E[3]/E[1], E[11]/E[0], E[12]/E[8], E[23],      E[6], E[10]/E[8], E[13]/E[8], E[8], E[9]/E[0], E[8]/E[7], E[42],E[39],E[40],E[41], E[14],E[7],E[16], E[15],E[36],E[0],E[43]/E[0], E[21], E[19]/E[20], E[17]/E[18], E[22]); }
      if (ET2[l][6] > 0.0) {
	for(p=0; p<44; p++) E[p] = ET2[l][p];
	printf(" %5.2f %5.2f %6.3f %8.5f %8.5f %8.5f %8.5f %8.5f  %5.3f %7.4f %7.4f  %6.3f %6.3f%5.0f  canceled  %8.0f  %8.2f   %6.4f %6.0f  %4.2f %4.2f  %6.0f %6.0f %5.0f %6.0f  %6.0f %7.0f  %5.0f  %6.0f %9.0f %9.0f %6.4f  %6.4f %6.4f %6.4f %6.0f\n",
	       E[4], E[5], E[25], E[38]/E[36], E[37]/E[36], E[24]/E[0], E[1]/E[0], E[2]/E[0], E[35]/E[6], E[3]/E[0], E[3]/E[1], E[11]/E[0], E[12]/E[8], E[23],      E[6], E[10]/E[8], E[13]/E[8], E[8], E[9]/E[0], E[8]/E[7], E[42],E[39],E[40],E[41], E[14],E[7],E[16], E[15],E[36],E[0],E[43]/E[0], E[21], E[19]/E[20], E[17]/E[18], E[22]); }
      if (ETn[l][6] > 0.0) {
	for(p=0; p<44; p++) E[p] = ETn[l][p];
	printf(" %5.2f %5.2f %6.3f %8.5f %8.5f %8.5f %8.5f %8.5f  %5.3f %7.4f %7.4f  %6.3f %6.3f%5.0f  non-ord   %8.0f  %8.2f   %6.4f %6.0f  %4.2f %4.2f  %6.0f %6.0f %5.0f %6.0f  %6.0f %7.0f  %5.0f  %6.0f %9.0f %9.0f %6.4f  %6.4f %6.4f %6.4f %6.0f\n",
	       E[4], E[5], E[25], E[38]/E[36], E[37]/E[36], E[24]/E[0], E[1]/E[0], E[2]/E[0], E[35]/E[6], E[3]/E[0], E[3]/E[1], E[11]/E[0], E[12]/E[8], E[23],      E[6], E[10]/E[8], E[13]/E[8], E[8], E[9]/E[0], E[8]/E[7], E[42],E[39],E[40],E[41], E[14],E[7],E[16], E[15],E[36],E[0],E[43]/E[0], E[21], E[19]/E[20], E[17]/E[18], E[22]); }
      if (ET3[l][6] > 0.0) {
	for(p=0; p<44; p++) E[p] = ET3[l][p];    /* E_0[2,24] used  fabs() since mean s->w is 0 across states since Mkt-Ord s->w is E(v|stuff).  mae/w uses /E[2] instead of /E[1] */
	printf(" %5.2f %5.2f %6.3f %8.5f %8.5f %8.5f|%8.5f %8.5f| %5.3f %7.4f %7.4f  %6.3f %6.3f%5.0f  mkt-ord   %8.0f  %8.2f   %6.4f %6.0f  %4.2f %4.2f  %6.0f %6.0f %5.0f %6.0f  %6.0f %7.0f  %5.0f  %6.0f %9.0f %9.0f %6.4f  %6.4f %6.4f %6.4f %6.0f\n",
	       E[4], E[5], E[25], E[38]/E[36], E[37]/E[36], E[24]/E[0], E[1]/E[0], E[2]/E[0], E[35]/E[6], E[3]/E[0], E[3]/E[2], E[11]/E[0], E[12]/E[8], E[23],      E[6], E[10]/E[8], E[13]/E[8], E[8], E[9]/E[0], E[8]/E[7], E[42],E[39],E[40],E[41], E[14],E[7],E[16], E[15],E[36],E[0],E[43]/E[0], E[21], E[19]/E[20], E[17]/E[18], E[22]); }
      printf("\n");
    }  /* for(l) */
    printf(" max:w |Pw-wf|Pw-w|  /N:  w    Pw     /NN:  w    Pw        wf     wf>Pw |Pw-wf|   ||/Pw sig:/NN /OftNN Oft&0            #states    Chi/df  N(0,1)>3    df   NNused       new w0sell   eUB     sx    NT>0    NN>0  NN>NT   sumNT    sumN-1    sumNN  Eopt:max   ave    /NN   >.01\n");
    for(l=0; l<nT; l++) {
      if (E_T[l][6] > 0.0) {
	for(p=0; p<44; p++) E[p] = E_T[l][p];  /* keeps printf() inputs the same */
	printf(" %5.2f %5.2f %6.3f %8.5f %8.5f %8.5f %8.5f %8.5f  %5.3f %7.4f %7.4f  %6.3f %6.3f%5.0f  type= %2d  %8.0f  %8.2f   %6.4f %6.0f  %4.2f %4.2f  %6.0f %6.0f %5.0f %6.0f  %6.0f %7.0f  %5.0f  %6.0f %9.0f %9.0f %6.4f  %6.4f %6.4f %6.4f %6.0f\n",
	       E[4], E[5], E[25], E[38]/E[36], E[37]/E[36], E[24]/E[0], E[1]/E[0], E[2]/E[0], E[35]/E[6], E[3]/E[0], E[3]/E[1], E[11]/E[0], E[12]/E[8], E[23],  l,  E[6], E[10]/E[8], E[13]/E[8], E[8], E[9]/E[0], E[8]/E[7], E[42],E[39],E[40],E[41], E[14],E[7],E[16], E[15],E[36],E[0],E[43]/E[0], E[21], E[19]/E[20], E[17]/E[18], E[22]); } }
  }
  if(nT>1) printf("\n");
  else     printf("\n max:w |Pw-wf|Pw-w|  /N:  w    Pw     /NN:  w    Pw        wf     wf>Pw |Pw-wf|   ||/Pw sig:/NN /OftNN Oft&0            #states    Chi/df  N(0,1)>3    df   NNused       new w0sell   eUB     sx    NT>0    NN>0  NN>NT   sumNT    sumN-1    sumNN  Eopt:max   ave    /NN   >.01\n");
  for(m=0; m<nPV; m++) {
    for(p=0; p<44; p++) E[p] = EPV[m][p];
    printf(" %5.2f %5.2f %6.3f %8.5f %8.5f %8.5f %8.5f %8.5f  %5.3f %7.4f %7.4f  %6.3f %6.3f%5.0f  pv  = %2d  %8.0f  %8.2f   %6.4f %6.0f  %4.2f %4.2f  %6.0f %6.0f %5.0f %6.0f  %6.0f %7.0f  %5.0f  %6.0f %9.0f %9.0f %6.4f  %6.4f %6.4f %6.4f %6.0f\n",
	   E[4], E[5], E[25], E[38]/E[36], E[37]/E[36], E[24]/E[0], E[1]/E[0], E[2]/E[0], E[35]/E[6], E[3]/E[0], E[3]/E[1], E[11]/E[0], E[12]/E[8], E[23],  m,  E[6], E[10]/E[8], E[13]/E[8], E[8], E[9]/E[0], E[8]/E[7], E[42],E[39],E[40],E[41], E[14],E[7],E[16], E[15],E[36],E[0],E[43]/E[0], E[21], E[19]/E[20], E[17]/E[18], E[22]); }
  printf("\n");
  for(m=0; m<nP; m++) {
    for(p=0; p<44; p++) E[p] = E_p[m][p];
    if (E[6])
	printf(" %5.2f %5.2f %6.3f %8.5f %8.5f %8.5f %8.5f %8.5f  %5.3f %7.4f %7.4f  %6.3f %6.3f%5.0f  price %2d  %8.0f  %8.2f   %6.4f %6.0f  %4.2f %4.2f  %6.0f %6.0f %5.0f %6.0f  %6.0f %7.0f  %5.0f  %6.0f %9.0f %9.0f %6.4f  %6.4f %6.4f %6.4f %6.0f\n",
	       E[4], E[5], E[25], E[38]/E[36], E[37]/E[36], E[24]/E[0], E[1]/E[0], E[2]/E[0], E[35]/E[6], E[3]/E[0], E[3]/E[1], E[11]/E[0], E[12]/E[8], E[23],  m,  E[6], E[10]/E[8], E[13]/E[8], E[8], E[9]/E[0], E[8]/E[7], E[42],E[39],E[40],E[41], E[14],E[7],E[16], E[15],E[36],E[0],E[43]/E[0], E[21], E[19]/E[20], E[17]/E[18], E[22]); }

  printf("\n max:w |Pw-wf|Pw-w|  /N:  w    Pw     /NN:  w    Pw        wf     wf>Pw |Pw-wf|   ||/Pw sig:/NN /OftNN Oft&0            #states    Chi/df  N(0,1)>3    df   NNused       new w0sell   eUB     sx    NT>0    NN>0  NN>NT   sumNT    sumN-1    sumNN  Eopt:max   ave    /NN   >.01\n");

  if (E_0[6] > 0.0) {
    for(p=0; p<44; p++) E[p] = E_0[p];
    printf(" %5.2f %5.2f %6.3f %8.5f %8.5f %8.5f %8.5f %8.5f  %5.3f %7.4f %7.4f  %6.3f %6.3f%5.0f  New Lim   %8.0f  %8.2f   %6.4f %6.0f  %4.2f %4.2f  %6.0f %6.0f %5.0f %6.0f  %6.0f %7.0f  %5.0f  %6.0f %9.0f %9.0f %6.4f  %6.4f %6.4f %6.4f %6.0f\n",
	   E[4], E[5], E[25], E[38]/E[36], E[37]/E[36], E[24]/E[0], E[1]/E[0], E[2]/E[0], E[35]/E[6], E[3]/E[0], E[3]/E[1], E[11]/E[0], E[12]/E[8], E[23],  E[6], E[10]/E[8], E[13]/E[8], E[8], E[9]/E[0], E[8]/E[7], E[42],E[39],E[40],E[41], E[14],E[7],E[16], E[15],E[36],E[0],E[43]/E[0], E[21], E[19]/E[20], E[17]/E[18], E[22]); }
  if (E_1[6] > 0.0) {
    for(p=0; p<44; p++) E[p] = E_1[p];
    printf(" %5.2f %5.2f %6.3f %8.5f %8.5f %8.5f %8.5f %8.5f  %5.3f %7.4f %7.4f  %6.3f %6.3f%5.0f  Retained  %8.0f  %8.2f   %6.4f %6.0f  %4.2f %4.2f  %6.0f %6.0f %5.0f %6.0f  %6.0f %7.0f  %5.0f  %6.0f %9.0f %9.0f %6.4f  %6.4f %6.4f %6.4f %6.0f\n",
	   E[4], E[5], E[25], E[38]/E[36], E[37]/E[36], E[24]/E[0], E[1]/E[0], E[2]/E[0], E[35]/E[6], E[3]/E[0], E[3]/E[1], E[11]/E[0], E[12]/E[8], E[23],  E[6], E[10]/E[8], E[13]/E[8], E[8], E[9]/E[0], E[8]/E[7], E[42],E[39],E[40],E[41], E[14],E[7],E[16], E[15],E[36],E[0],E[43]/E[0], E[21], E[19]/E[20], E[17]/E[18], E[22]); }
  if (E_2[6] > 0.0) {
    for(p=0; p<44; p++) E[p] = E_2[p];
    printf(" %5.2f %5.2f %6.3f %8.5f %8.5f %8.5f %8.5f %8.5f  %5.3f %7.4f %7.4f  %6.3f %6.3f%5.0f  Canceled  %8.0f  %8.2f   %6.4f %6.0f  %4.2f %4.2f  %6.0f %6.0f %5.0f %6.0f  %6.0f %7.0f  %5.0f  %6.0f %9.0f %9.0f %6.4f  %6.4f %6.4f %6.4f %6.0f\n",
	   E[4], E[5], E[25], E[38]/E[36], E[37]/E[36], E[24]/E[0], E[1]/E[0], E[2]/E[0], E[35]/E[6], E[3]/E[0], E[3]/E[1], E[11]/E[0], E[12]/E[8], E[23],  E[6], E[10]/E[8], E[13]/E[8], E[8], E[9]/E[0], E[8]/E[7], E[42],E[39],E[40],E[41], E[14],E[7],E[16], E[15],E[36],E[0],E[43]/E[0], E[21], E[19]/E[20], E[17]/E[18], E[22]); }
  if (E_n[6] > 0.0) {
    for(p=0; p<44; p++) E[p] = E_n[p];
    printf(" %5.2f %5.2f %6.3f %8.5f %8.5f %8.5f %8.5f %8.5f  %5.3f %7.4f %7.4f  %6.3f %6.3f%5.0f  Non-Ord   %8.0f  %8.2f   %6.4f %6.0f  %4.2f %4.2f  %6.0f %6.0f %5.0f %6.0f  %6.0f %7.0f  %5.0f  %6.0f %9.0f %9.0f %6.4f  %6.4f %6.4f %6.4f %6.0f\n",
	   E[4], E[5], E[25], E[38]/E[36], E[37]/E[36], E[24]/E[0], E[1]/E[0], E[2]/E[0], E[35]/E[6], E[3]/E[0], E[3]/E[1], E[11]/E[0], E[12]/E[8], E[23],  E[6], E[10]/E[8], E[13]/E[8], E[8], E[9]/E[0], E[8]/E[7], E[42],E[39],E[40],E[41], E[14],E[7],E[16], E[15],E[36],E[0],E[43]/E[0], E[21], E[19]/E[20], E[17]/E[18], E[22]); }
  if (E_3[6] > 0.0) {
    for(p=0; p<44; p++) E[p] = E_3[p];    /* E_0[2,24] used  fabs() since mean s->w is 0 across states since Mkt-Ord s->w is E(v|stuff).  mae/w uses /E[2] instead of /E[1] */
    printf(" %5.2f %5.2f %6.3f %8.5f %8.5f %8.5f|%8.5f %8.5f| %5.3f %7.4f %7.4f  %6.3f %6.3f%5.0f  Mkt-Ord   %8.0f  %8.2f   %6.4f %6.0f  %4.2f %4.2f  %6.0f %6.0f %5.0f %6.0f  %6.0f %7.0f  %5.0f  %6.0f %9.0f %9.0f %6.4f  %6.4f %6.4f %6.4f %6.0f\n",
	   E[4], E[5], E[25], E[38]/E[36], E[37]/E[36], E[24]/E[0], E[1]/E[0], E[2]/E[0], E[35]/E[6], E[3]/E[0], E[3]/E[2], E[11]/E[0], E[12]/E[8], E[23],  E[6], E[10]/E[8], E[13]/E[8], E[8], E[9]/E[0], E[8]/E[7], E[42],E[39],E[40],E[41], E[14],E[7],E[16], E[15],E[36],E[0],E[43]/E[0], E[21], E[19]/E[20], E[17]/E[18], E[22]); }
  printf("\n");
  if (Eyt[6] > 0.0) {
    for(p=0; p<44; p++) E[p] = Eyt[p];
    printf(" %5.2f %5.2f %6.3f %8.5f %8.5f %8.5f %8.5f %8.5f  %5.3f %7.4f %7.4f  %6.3f %6.3f%5.0f  Y Tremble %8.0f  %8.2f   %6.4f %6.0f  %4.2f %4.2f  %6.0f %6.0f %5.0f %6.0f  %6.0f %7.0f  %5.0f  %6.0f %9.0f %9.0f %6.4f  %6.4f %6.4f %6.4f %6.0f\n",
	   E[4], E[5], E[25], E[38]/E[36], E[37]/E[36], E[24]/E[0], E[1]/E[0], E[2]/E[0], E[35]/E[6], E[3]/E[0], E[3]/E[1], E[11]/E[0], E[12]/E[8], E[23],  E[6], E[10]/E[8], E[13]/E[8], E[8], E[9]/E[0], E[8]/E[7], E[42],E[39],E[40],E[41], E[14],E[7],E[16], E[15],E[36],E[0],E[43]/E[0], E[21], E[19]/E[20], E[17]/E[18], E[22]); }
  if (Ent[6] > 0.0) {
    for(p=0; p<44; p++) E[p] = Ent[p];
    printf(" %5.2f %5.2f %6.3f %8.5f %8.5f %8.5f %8.5f %8.5f  %5.3f %7.4f %7.4f  %6.3f %6.3f%5.0f  N Tremble %8.0f  %8.2f   %6.4f %6.0f  %4.2f %4.2f  %6.0f %6.0f %5.0f %6.0f  %6.0f %7.0f  %5.0f  %6.0f %9.0f %9.0f %6.4f  %6.4f %6.4f %6.4f %6.0f\n",
	   E[4], E[5], E[25], E[38]/E[36], E[37]/E[36], E[24]/E[0], E[1]/E[0], E[2]/E[0], E[35]/E[6], E[3]/E[0], E[3]/E[1], E[11]/E[0], E[12]/E[8], E[23],  E[6], E[10]/E[8], E[13]/E[8], E[8], E[9]/E[0], E[8]/E[7], E[42],E[39],E[40],E[41], E[14],E[7],E[16], E[15],E[36],E[0],E[43]/E[0], E[21], E[19]/E[20], E[17]/E[18], E[22]); }
  if (E88[6] > 0.0) {
    for(p=0; p<44; p++) E[p] = E88[p];
    printf(" %5.2f %5.2f %6.3f %8.5f %8.5f %8.5f %8.5f %8.5f  %5.3f %7.4f %7.4f  %6.3f %6.3f%5.0f  New s     %8.0f  %8.2f   %6.4f %6.0f  %4.2f %4.2f  %6.0f %6.0f %5.0f %6.0f  %6.0f %7.0f  %5.0f  %6.0f %9.0f %9.0f %6.4f  %6.4f %6.4f %6.4f %6.0f\n",
	   E[4], E[5], E[25], E[38]/E[36], E[37]/E[36], E[24]/E[0], E[1]/E[0], E[2]/E[0], E[35]/E[6], E[3]/E[0], E[3]/E[1], E[11]/E[0], E[12]/E[8], E[23],  E[6], E[10]/E[8], E[13]/E[8], E[8], E[9]/E[0], E[8]/E[7], E[42],E[39],E[40],E[41], E[14],E[7],E[16], E[15],E[36],E[0],E[43]/E[0], E[21], E[19]/E[20], E[17]/E[18], E[22]); }
  if (Eut[6] > 0.0) {
    for(p=0; p<44; p++) E[p] = Eut[p];
    printf(" %5.2f %5.2f %6.3f %8.5f %8.5f %8.5f %8.5f %8.5f  %5.3f %7.4f %7.4f  %6.3f %6.3f%5.0f  update0,1 %8.0f  %8.2f   %6.4f %6.0f  %4.2f %4.2f  %6.0f %6.0f %5.0f %6.0f  %6.0f %7.0f  %5.0f  %6.0f %9.0f %9.0f %6.4f  %6.4f %6.4f %6.4f %6.0f\n",
	   E[4], E[5], E[25], E[38]/E[36], E[37]/E[36], E[24]/E[0], E[1]/E[0], E[2]/E[0], E[35]/E[6], E[3]/E[0], E[3]/E[1], E[11]/E[0], E[12]/E[8], E[23],  E[6], E[10]/E[8], E[13]/E[8], E[8], E[9]/E[0], E[8]/E[7], E[42],E[39],E[40],E[41], E[14],E[7],E[16], E[15],E[36],E[0],E[43]/E[0], E[21], E[19]/E[20], E[17]/E[18], E[22]); }
  if (EA[6] > 0.0) {  /* t->ret = 0,1 is ALL New Limit/Non orders and Retained orders -- CANCEL, MKT orders (ret=2,3) ignored since often have sigma = 0 --> chisq huge */
    for(p=0; p<44; p++) E[p] = E_0[p]+E_n[p]+E_1[p];       /*  OLD:  E[2]=0.0;  E[24]=0.0;   [2,24] invalid since E_0[] used  fabs() while EA[] did not */
    printf(" %5.2f %5.2f %6.3f %8.5f %8.5f %8.5f %8.5f %8.5f  %5.3f %7.4f %7.4f  %6.3f %6.3f%5.0f  ->ret 0,1 %8.0f  %8.2f   %6.4f %6.0f  %4.2f %4.2f  %6.0f %6.0f %5.0f %6.0f  %6.0f %7.0f  %5.0f  %6.0f %9.0f %9.0f %6.4f  %6.4f %6.4f %6.4f %6.0f\n",
	   E[4], E[5], E[25], E[38]/E[36], E[37]/E[36], E[24]/E[0], E[1]/E[0], E[2]/E[0], E[35]/E[6], E[3]/E[0], E[3]/E[1], E[11]/E[0], E[12]/E[8], E[23], E[6], E[10]/E[8], E[13]/E[8], E[8], E[9]/E[0], E[8]/E[7], E[42],E[39],E[40],E[41], E[14],E[7],E[16], E[15],E[36],E[0],E[43]/E[0], E[21], E[19]/E[20], E[17]/E[18], E[22]);
    for(p=0; p<44; p++) E[p] = EA[p];
    if (E_0[6]>0) u = 0.0;  else u = E[3]/E[1];  /* E[3] has Mkt Orders, which have mean 0 s->w and therefore blown up ||/w since w pulled down */
    printf(" %5.2f %5.2f %6.3f %8.5f %8.5f %8.5f %8.5f %8.5f  %5.3f %7.4f %7.4f  %6.3f %6.3f%5.0f  ALL       %8.0f  %8.2f   %6.4f %6.0f  %4.2f %4.2f  %6.0f %6.0f %5.0f %6.0f  %6.0f %7.0f  %5.0f  %6.0f %9.0f %9.0f %6.4f  %6.4f %6.4f %6.4f %6.0f\n",
	   E[4], E[5], E[25], E[38]/E[36], E[37]/E[36], E[24]/E[0], E[1]/E[0], E[2]/E[0], E[35]/E[6], E[3]/E[0],     u,     E[11]/E[0], E[12]/E[8], E[23], E[6], E[10]/E[8], E[13]/E[8], E[8], E[9]/E[0], E[8]/E[7], E[42],E[39],E[40],E[41], E[14],E[7],E[16], E[15],E[36],E[0],E[43]/E[0], E[21], E[19]/E[20], E[17]/E[18], E[22]); }
  
  /* ALWAYS/NEVER reported in next line only strictly hold if were to exclude market/nonorders */
  /* since a given nonorder pairs up with different sets of limit order options, */
  /* and given given set of limit order options pairs up with different mkt order beliefs (tt->Ev).  Nevertheless, I'm retaining its tally/reporting. */
  u = (double)Tcount[2];                          /* u = total states (including purged) as double */
  if (Purge)  printf(" #Purged= %u ", Purge);
#if useNNtt>0
  printf(" NNtt!=0_NCtt: %1.0f %1.0f %1.0f  NNtj!=0_NCtj: %1.0f %1.0f %1.0f ", EA[26], EA[28], EA[27], EA[29], EA[31], EA[30]);
#endif


  printf("\n");
  corr[0][3] = corr[0][3]/corr[0][0];      corr[1][3] = corr[1][3]/corr[1][0];  /* var Pw, NN_weighted[0], unweighted[1] */
  corr[0][4] = corr[0][4]/corr[0][0];      corr[1][4] = corr[1][4]/corr[1][0];  /* var Ew/NN      */
  corr[0][5] = corr[0][5]/corr[0][0];      corr[1][5] = corr[1][5]/corr[1][0];  /* cov (Pw,Ew/NN) */
  
  /* printf(" corr NN:  sumNN= %10.0f  mean Pw~wf: %8.5f  %8.5f  var Pw~wf: %8.5f %8.5f  cov: %8.5f  corr: %7.5f \n", corr[0][0], corr[0][1], corr[0][2], corr[0][3], corr[0][4], corr[0][5], corr[0][5]/sqrt(corr[0][3]*corr[0][4] ));
     printf(" corr   :     #s= %10.0f  mean Pw~wf: %8.5f  %8.5f  var Pw~wf: %8.5f %8.5f  cov: %8.5f  corr: %7.5f \n", corr[1][0], corr[1][1], corr[1][2], corr[1][3], corr[1][4], corr[1][5], corr[1][5]/sqrt(corr[1][3]*corr[1][4]) ); */
  

  printf(" CORR: %7.5f %7.5f  extreme ticks: any~used %d %d ~ %d %d  #ask=0: %d  mean~max~sum NN= %1.1f %u %1.0f  #NC>0: %u  #zfix= %u %u  #RC:y n i= %u %u %u\n",
	 corr[0][5]/sqrt(corr[0][3]*corr[0][4]), corr[1][5]/sqrt(corr[1][3]*corr[1][4]), Tcount[7],Tcount[8], Tcount[9],Tcount[10], N_s[3], E[0]/u, Acount[9], E[0], Acount[11], Tcount[16],Tcount[15], Tcount[12],Tcount[13],Tcount[14]);
  /*
    printf(" s->N pdf: 1: %4.2f  2: %4.2f  3: %4.2f  4: %4.2f  5: %4.2f  6-10: %4.2f  11-20: %4.2f  21-100: %4.2f  100+: %4.2f  mean~max~sum NN= %1.1f %u %1.0f  #NC>0: %u  #RC:y n i= %u %u %u\n",
    Acount[0]/u, Acount[1]/u, Acount[2]/u, Acount[3]/u, Acount[4]/u, Acount[5]/u, Acount[6]/u, Acount[7]/u, Acount[8]/u, E[0]/u, Acount[9], E[0], Acount[11], Tcount[12],Tcount[13],Tcount[14]);
  */
  
  printf(" jj=%3u  UPDATE_maxPVT,sync,s0= %4.2f %d %d  Nreset,xNN= %d %d  OftNN= %d  w2min~mult= %5.3f %3.1f  New s: %u %6.4f NewNonMkt: %1.0f %6.4f  s per bucket: max= %u mean= %3.1f %5.3f depth/NC= %3.1f #HT = %u  buckets per HT: max~mean= %u %3.1f\n",
	 jj, UPDATE_maxPVT, UPDATE_sync, UPDATE_s0, Nreset, Nreset_xNN, OftNN, w2min, w2mult, N_s[0],(double)N_s[0]/N_s[22],  N_s[0]-E_0[42],((double)N_s[0]-E_0[42])/N_s[22], Tcount[3], (double) Tcount[2]/Tcount[4], (double) Tcount[2]/(Tcount[0]*(Prime-1)), Hcount[0]/Hcount[1], Tcount[0], Tcount[6], (double) Tcount[4]/Tcount[0]);

  if(UB[nP][2]  && WRITE==0  ) { /* WRITE==0 avoids this when converging.  (WRITE=0 when writing matlab outM) */
    printf("\n lim.sells:    p  #states  UB<w     UB    UB-w   /N       #New  <0    UB-w     #N>10      <0  UB-w   (small, vlag=0 only)\n");
    for(p=0; p<nP; p++)   /* UB reporting could be by  pvtype,type */ 
      if(UB[p][2]>0.0) 
        printf("         UB:  %2d %8.0f  %5.3f %6.3f  %5.2f %5.2f   %7.0f %5.3f %5.2f   %7.0f %7.0f %5.2f\n", p, UB[p][2], UB[p][0]/UB[p][2], UB[p][11]/UB[p][2], UB[p][1]/UB[p][2], UB[p][3]/UB[p][4], UB[p][10], UB[p][8]/UB[p][10], UB[p][9]/UB[p][10], UB[p][7], UB[p][5]/UB[p][7], UB[p][6]/UB[p][7] );
    p=nP; printf("         UB: all %8.0f  %5.3f %6.3f  %5.2f %5.2f   %7.0f %5.3f %5.2f   %7.0f %7.0f %5.2f\n", UB[p][2], UB[p][0]/UB[p][2], UB[p][11]/UB[p][2], UB[p][1]/UB[p][2], UB[p][3]/UB[p][4], UB[p][10], UB[p][8]/UB[p][10], UB[p][9]/UB[p][10], UB[p][7], UB[p][5]/UB[p][7], UB[p][6]/UB[p][7] );
  }
  fflush(stdout);
  
#if WRITE>0 && use_w0sell   /* only update w0sell if WRITE>0 */
  for (a=0; a<1+(zmax>0); a++) { /* enables separate processing of LARGE tz, without copying code */
    if(a) printf("\n w0sell: large tz.   bid=0=ask integrates over ALL bid,ask.\n");
    else  printf("\n w0sell: small tt.   bid=0=ask integrates over ALL bid,ask.\n");
    Tcount[3]=0;   w= 0.0;  u2= 0.0;  /* w = max N0sell, u2 = max var(w) */
    E[0] = 9.9;  E[1] = -9.9;  E[2] = 0.0;  h = 0;       /* min,max,mean w  */
    E[10]= 9.9;  E[11]= -9.9;  E[12]= 0.0;  Tcount[0]=0; /* min,max,mean w2 */
    E[20]= 9.9;  E[21]= -9.9;  E[22]= 0.0;  Tcount[1]=0; /* min,max,mean,# w2 sum(NN) > OftNN */
    E[30]= 9.9;  E[31]= -9.9;  E[32]= 0.0;  Tcount[4]=0; /* min,max,mean w2 bid=0=ask */
    for (l=0; l<nT; l++)
      if(zT[l]==a*zmax)                                  /* assumes all large tz have zT[] = zmax */
	for (p=0; p<nPx1; p++)                           /* p=nP occurs for non-order */
	  for (bid=0; bid<nPx1; bid++)                   /* but ask, bid indices should never be > nP_1, right? */
	    for (ask=bid; ask<nPx1; ask++)               /* ask >= bid */
	      for (m=0; m<nPV; m++) {
		z= N0sell[l][p][bid][ask][m];            /* assign to scalars to avoid repeated [][][][][] lookups */
		  if( z > 0.0 ) {
		    w0sell[l][p][bid][ask][m] *= 1.0 / z; /* replace current w0sell[] with NN weighted average of s->w matching [l][p][bid][ask][m] */
		    u = w0sell[l][p][bid][ask][m];        /* w2= std.dev s->w across s matching [l][p][bid][ask][m] = sqrt( E(w^2) - ( E(w) )^2) */
		    h++;  E[0]= min(E[0],u);  E[1]= max(E[1],u);  E[2]+= u;           /* includes bid=0=ask */
		    w2= sqrt( max(1e-4, w2sell[l][p][bid][ask][m] / z - u * u) );     /* okay to use N weights?? */
		    w0sell[l][p][bid][ask][m] += w2 * w0adjust;                       /* w0adjust increases degree of optimism */
		    if(z>w || w2>u2  || (bid==0 && ask==0)  )   /* bid=0=ask integrates over all bid,ask */
		      /*
		      printf("         pv~type= %d %d  p~bid~ask= %2d %2d %2d  w0sell= %6.3f  w2= %6.3f  matches=%8u sum(N)= %1.0f \n",
		      m,l,p,bid,ask, u, w2, M0sell[l][p][bid][ask][m], z);  */
		    w = max(w, z );   /* w2 is std.dev, not variance */
		    u2= max(u2,w2);
		    
		    if(ask==0)        /* bid also 0.  bid=0=ask integration over bid,ask */
		      {             E[30]= min(E[30],w2);  E[31]= max(E[31],w2);  E[32]+= w2;  Tcount[4]++; }
		    else {	    E[10]= min(E[10],w2);  E[11]= max(E[11],w2);  E[12]+= w2;  Tcount[0]++;
		    if(z>OftNN) { E[20]= min(E[20],w2);  E[21]= max(E[21],w2);  E[22]+= w2;  Tcount[1]++; } }
		  }
		  else{ if(w0sell[l][p][bid][ask][m] > -99.9) { Tcount[3]++;  w0sell[l][p][bid][ask][m] += .1 ; } } /* avoids pessimism ?? */
		}
    if(a) printf(" w0sell: large tz: #~adj= %u %u  min~max~ave w0: %5.3f %5.3f %5.3f  w2: %5.3f %5.3f %5.3f  oft_w2: %5.3f %5.3f %5.3f\n", h, Tcount[3], E[0],E[1],E[2]/h, E[10],E[11],E[12]/Tcount[0], E[20],E[21],E[22]/Tcount[1] );
    else  printf(" w0sell: small tt: #~adj= %u %u  min~max~ave w0: %5.3f %5.3f %5.3f  w2: %5.3f %5.3f %5.3f  oft_w2: %5.3f %5.3f %5.3f  bid=0=ask: %5.3f %5.3f %5.3f\n", h, Tcount[3], E[0],E[1],E[2]/h, E[10],E[11],E[12]/Tcount[0], E[20],E[21],E[22]/Tcount[1], E[30],E[31],E[32]/Tcount[4] );
  }
#endif

  if (i==0){
    N_s[1] = N_s[2]; /* if SYMM==0 approximate #total states = 2 * #sell_states */
    N_s[2] = (Tcount[2]-Purge)*(2-SYMM);
  }
  if(outMkt!= NULL) fclose(outMkt);
#if WRITE>0
  if (outB!=NULL) {
    fclose(outw);
    fclose(outN);
    fclose(outB); 
    if(SYMM || i) jj++;  /* jj serves as iteration count if undef WRITE */
  }                  /* easiest to ensure no overwrite if update here   */
#else                /* cost is WILL overwrite outM, Fringe, etc, stuff */
  jj++;              /* if WRITE is #defined, but never actually WRITE. */
#endif               /* --> DO NOT #define WRITE, if never WRITE state space */

  if (E[6] > MAXSTATES) { printf("\n\nExiting due to MAXSTATES reached\n\n");  exit(9); }

  return(0);
}



int main()
{
  /* PV = Private Values,  Bt = Book time t, followed by lowest ask and highest bid */
  /* CS is Cumulative Surplus for the limit order [0] and market order [1]. */
  /* traders[p] is pointer to first trader with bid/ask at tick p. */
  struct state *s, **sp, *sa, *s_;                                        /* traders[nP] is list of traders with non-orders, but allowed to return */
  struct trader  *t, *tt, **tp, *tj, *tx, *tm, *tz, *traders[nP+2];  /* traders[nP+1] is stack of discarded traders to be reused instead of many malloc() free() calls */
  double u, v_, v,v0,v1,v2, CS[nTmax][nPVmax][18], RealSprd, qave, non_ave, nextPVj, realtime, zRT[2][zmax+1], VV[nTmax][nPVmax][12], wCancel[8] ;
  unsigned int h, j, Tcount[nP+2][2], Acount[nP+2], Bcount[nP+2], Bid[nP+2], Ask[nP+2], Spread[nP+3], SpreaF[nP+3], NScount[10], Jcount[4], ABAS[2][4], Ntremble[23], byT[nTmax][nPVmax][nPlim+nPlim+nPlim+21], aTremN, zpq[2][nPmax][zmax+2], sNew[11] ; /* zpq[][nPmax] could be nPx1 */
  short int p, i, k, l, m, qmax, Bt[nP+2], Bz[nP+2], Ttau[TAU+2], Ptau[TAU+2], PVj, bid1, ask1, p1, t1, t2, b1, b2, m1, m2, q1, q2, a1, a2, pmin, pmax, a_, dev_PVT[nPVmax][nT], aTrem[4+2*nPlim], BestNew, NNs;  /* 4+ to be safe for aTrem */
  long int vminmax[8][nRT], TC[nTmax][nPVmax][2][5];
  long clock1;
#ifdef Type2_No_limitorders
  double wNon[3]={0.0, 0.0, 0.0};  /* to compare s->w for NonOrders of Speculators with and without limitorder option */
#endif
  FILE *outM, *out2, *outTQ, *outCS;  /* *outDrop, *outNoAction, *outN0 */
#ifdef Use_outFringe
  FILE *outFringe;
#endif
#if READ>0
  struct HT *ht;
  FILE *outw,*outN,*outB;
#endif

  /* next line's use of ++ would lead to faster 1-line update of s->w but get compiler warning, despite working.  to be safe I'm not using it */
  /* p = 3;  v= 1.0;  v1= 2.0;  u = (v * p + v1) / ++p;  printf("\n if ++ compiled (understood) correctly, u should = ( 1.0 * 3 + 2.0 ) / 4 = 1.25,  actual u = %6.3f\n", u); */

  srand(seed); s=NULL; j=0;  tx=NULL; nextPVj=0.0; realtime=0.0; PVj=0; bid1=0; ask1=0; p1=0; q1=0; q2=0; t1=0; t2=0; b1=0, b2=0; v1=0.0; v2=0.0;
  ReHash = 1;  N_s[24] = 0;  N_s[25] = 0;  /* N_s[24,25] enable comparison of orders submitted and orders updated, to be sure no orders are "lost".  Reset AFTER j loop. */

  /* u = pow((double)nQ+1.0, (double)nP) * (double)nPx1;   v = u * (double)nP; */   /* loose upper bound on #states, books since double counts q=0 */

  printf("\n\n Running GetEq with  nB = %d  nL = %d  nP = %d  nQ= %d  nT= %d  nJ=%8.1e  mJ= %1d  Bytes/state= %d  eUBsimilar= %d  TMP= " TMP "\n", nB, nL, nP, nQ, nT, nJ, mJ, (int) sizeof(struct state), eUBsimilar);
  printf(  "\n  Ns0 = %d  V0=%5.2f  delta=%6.3f  seed= %d  Prime= %d  RAND_MAX= %d  LoU~1-HiU= %9.1e %9.1e  TAU= %d  MAXSTATES= %6.1e\n", Ns0, V0, delta, seed, Prime, RAND_MAX, LoU, 1.0-HiU, TAU, MAXSTATES);
  printf(  "\n  UPDATE_maxPVT= %4.2f  UPDATE_Ne= %d  UPDATE_s0= %d  UPDATE_sync= %d  Nreset= %d  Nreset_xNN= %d  Nreset_READ= %d  READ_w_adj= %5.3f  jj0= %u  N0= %d  PVmu=%6.2f  PVjN=%d\n", UPDATE_maxPVT, UPDATE_Ne, UPDATE_s0, UPDATE_sync, Nreset, Nreset_xNN, Nreset_READ, READ_w_adj, jj0, N0, PVmu, PVjN);
  printf(  "\n  Ejump= %5.2f  Eagain= %5.2f  Eagain_non= %5.2f  Earrive= %5.2f  PrTremble= %5.3f  TrembleNew= %5.3f  ForceNon= %5.3f  SCALE_v_v= %9.1e  UPDATE_1st= %d\n", Ejump, Eagain, Eagain_non, Earrive, PrTremble, TrembleNew, ForceNon, SCALE_v_v, UPDATE_1st);
  printf(  "\n  OftNN= %d  w2min= %5.3f  w2mult= %3.1f  ExogBuy= %d  SYMM= %d  nLag = %d  nRT= %d  nH= %d  H_signed= %d  H_jumps= %d  H_minp= %d  H_maxp= %d  N0_v= %d  LL= %d  HL= %d  nPtj= %d\n", OftNN, w2min, w2mult, ExogBuy, SYMM, nLag, nRT, nH, H_signed, H_jumps, H_minp, H_maxp, N0_v, LL, HL, nPtj);
  printf("\n  sizeof(32/64 bit dependent?): short int= %d  int= %d  long int= %d  double= %d  state= %d  trader= %d  vJ= %d  MO= %d  w0sell(MB)= %1.0f  #pHT[]= %d  *s= %d \n\n",
	 (int)sizeof(short int), (int)sizeof(int), (int)sizeof(long int), (int)sizeof(double), (int)sizeof(struct state), (int)sizeof(struct trader), (int)sizeof(struct vJ), (int)sizeof(struct MO), (double)sizeof(double)*nTmax*nPmax*nPmax*nPmax*nPV/1048576.0, nPVmax*(2-SYMM)*nTmax*nQ*nPx1, (int)sizeof(s) );
  
  if (nB+nL+nHnRT>16)                  { printf("\n\n Aborting ... increase #elements in hscale[16] since nB + nHnRT > 16 ...\n\n");  exit(8); }
  if (nB>8)                            { printf("\n\n Aborting ... nB <= 8 required \n\n");  exit(8); }
  if ( 0  && SYMM==0)                  { printf("\n\n Aborting ... check code to ensure SYMM=0 is still valid    \n\n");  exit(8); }
  if (nB> nBT[0] &&  nB> nBT[nT-1])    { printf("\n\n Aborting ... nB is bigger than first and last type, so probably can reduce nB \n\n");  exit(8); }
  if (N0_v==0 && nH!=1 && nRT!= 1)     { printf("\n\n Aborting ... when testing conditioning on v itself, via N0_v=0, should use nH=1 & nRT=1 \n\n");  exit(8); }
  if (nL!=0 && nL!=1 && nL!=3)         { printf("\n\n Aborting ... nL must be 0 or 1 or 3 \n\n");  exit(8); }
  if (Ns0!=0 && UPDATE_s0)             { printf("\n\n Aborting ... Ns0 > 0 caused seg fault at one time, so only use Ns0 when doing chi-sq with UPDATE_s0 = 0 \n\n");  exit(8); }
  if (PrTremble<1e-8 && ForceNon>1e-8) { printf("\n\n Aborting ... ForceNon should probably be ZERO since PrTremble is ZERO\n\n");  exit(8); }
  if (Nreset_xNN < 0)                  { printf("\n\n Aborting ... Nreset_xNN < 0 not allowed \n\n");  exit(8); }

  if (nRT && RT[nRT-1] != (double)nLag ) { printf("\n\n Aborting ... nLag = RT[nRT-1] required \n\n");  exit(8); }
  printf("\n  check that RT[] #defined correctly : #bins = nRT = %d, ",nRT); for(p=0; p<nRT; p++) printf(" RT[%d]=%5.2f",p,RT[p]);  printf("\n");
  
  if (SYMM==0 && use_w0sell) { printf("\n\n Aborting ... initial s->w based on  w0sell  requires SYMM.  see GetState().  code could easily be modified if want SYMM=0.\n\n");  exit(9); }

  if (UPDATE_maxPVT < 0.0 || UPDATE_maxPVT > 1.0 ) { printf("\n\n Aborting ... need   0.0 <= UPDATE_maxPVT <= 1.0 \n\n");  exit(9); }

  if (UPDATE_Ne > 0 && UPDATE_s0==0) { printf("\n\n Aborting ... UPDATE_Ne > 0 requires UPDATE_s0 = 1 \n\n");  exit(9); }

  if ( (int) sizeof(int) != 4 ) { printf("\n\n Aborting ...  sizeof( int ) != 4, so may need to adjust type declarations\n\n");  exit(9); }

  if( (jj0%2) != 0 ) { printf("\n\n Aborting ... since jj0 must initially be an EVEN number \n\n");  exit(9); }
  if(READ > 2 || WRITE >1) { printf("\n\n Aborting ... READ > 2 || WRITE >1 \n\n");  exit(9); }

  printf("\n NOTE: Perhaps should have deviators,nondev using DIFFERENT mkt beliefs since nondev should have FIXED beliefs.");
  printf("\n       Could use  s->Eoptimal  to store the FIXED mkt beliefs for nondev to use while getting deviator beliefs.");
  printf("\n       This would enable using fixed s->E(v) for nondev, updated s->E(v) for dev w/out doubling the state space!");
  printf("\n       This could be done, for example, by saving Eoptimal to file so s->Eoptimal could be reloaded. \n\n");
  for(m=0; m<nT; m++)  UPDATE_mkt[m] = vlagT[m];    /* initialize each vlag type to update market beliefs (ie, E(v) via tt->Ev) */
  UPDATE_mkt[nT] = 0;  /* initialize ALL uninformed to use E(v)=0.  changed to 1 (ie, use E(v) beliefs) if ANY informed trader is frequent (ie, nondeviator) */
  Ninformed = 0.0;     /* used in GetState() as weight for conditioning variables for initial E(v) */
  u = 0.0;  b1 = 0;  b2 = 0;
  printf(" Private Value Distribution, and Distribution of TYPE by PV.   Check for SYMMETRY.  'Deviators' are hardcoded as Pr() < .101\n\n");
  printf(" pvtype   PV     CDF  Pr(PV)  Pr(type)~UPDATE_PVT:");    for(m=0;m<nT;m++) printf("   type %d   ", m);  printf("\n");
  for(p=0; p<nPV; p++) {
    CS[0][p][4] = cdfPV[p]-u;            /* CS[0][pv][4] temp storage for pdf pv (marginal) */
    printf("  %3d  %6.2f  %5.3f  %5.3f                       ", p, PV[p], cdfPV[p],CS[0][p][4]);
    if(CS[0][p][4] <0.0)       { printf("\n\n Aborting ... Pr( pv ) < 0 \n\n");  exit(8); }
    v = 0.0; 
    for(m=0;m<nT;m++) {
      CS[m][p][0] = cdfPVT[p][m]-v;      /* m = type, p = PV.  CS[type][pv][0] temp storage for pdf type|pv */
      printf("  %5.3f ",CS[m][p][0]);
      if (vlagT[m]==0) Ninformed += CS[m][p][0] * CS[0][p][4];  /* Pr(type|pvtype) * Pr(pvtype) */
      if( CS[m][p][0] < 0.0 )  { printf("\n\n Aborting ... Pr(type) < 0 \n\n");  exit(8); }
      if( CS[m][p][0] > UPDATE_maxPVT )  /* if any Pr(type|pv) > UPDATE_max (ie, not to be updated) then UPDATE_mkt = 0. */
	UPDATE_mkt[m]=0 ;                /* all traders type=m (any pv) will use the FIXED belief of E(v) for mkt orders */
      if( CS[m][p][0] > .101 && vlagT[m]==0 ) UPDATE_mkt[nT] = 1;  /* if 1, uninformed use beliefs about v since enough informed traders for informative history */
      if( CS[m][p][0] > .101 && vlagT[m]==1 ) b1 = 1;              /* if 1, then should have H_jumps>0 --> informed condition on jumps history since enough uninformed */
      if( CS[m][p][0] >  0.0 && vlagT[m]==0 && nHT[m]==0) b2 = 1;  /* if 1, then an INFORMED trader is NOT conditioning on jumps */
      UPDATE_PVT[m][p] =  (CS[m][p][0] <= UPDATE_maxPVT) ;
      dev_PVT[p][m]    =  (CS[m][p][0] <  .101) ;   /* treat < .101 as deviators, who are only in market by w/ nondeviators */
      v= cdfPVT[p][m];
      printf("~ %d ",UPDATE_PVT[m][p]);

#if 0      
      if(UPDATE_maxPVT == 1.0 && cdfPVT[p][m] > 0.0 && cdfPVT[p][m] < 1.0) {  /* THIS check is important when storing nondeviators beliefs (in the absence of any deviators) in SAME FILES as deviators beliefs.  The presence of deviators (who use the deviators beliefs) is controlled by cdfPVT */
	printf("\n\n Aborting ... UPDATE_maxPVT = 1.0 yet cdfPVT[][] is neither 0.0 nor 1.0 --> deviators present so should FIX beliefs of non-deviators with UPDATE_maxPVT = .20 (or so)\n              IF indeed want UPDATE_maxPVT then TEMPORARILY comment out this check in GetEq_large_code.c and recompile\n"); exit(8); }
#endif

    } printf("\n");
    u = cdfPV[p];
    if (SYMM && PV[p] != -PV[nPV_1-p]) { printf("\n Aborting ... PV[] not symmetric, yet SYMM=1\n\n"); exit(8); }
  }     printf("\n");

  if(UPDATE_mkt[nT]) printf("\n  Ninformed = %6.4f and Uninformed use E(v) beliefs (tt->Ev) since enough informed traders for history to be revealing.\n\n",Ninformed);
  else               printf("\n  Ninformed = %6.4f and Uninformed use E(v)= 0.0 since not enough informed traders for history to be revealing, though beliefs still constructed for verification.\n\n",Ninformed);


  /* if(Ninformed>.9999){ printf("\n\n  Ninformed = %6.4f  so NOT constructing mkt beliefs via UPDATE_mkt[nT]=1.\n  COMMENT OUT if want beliefs constructed for deviators in later runs. \n\n", Ninformed);  fflush(stdout); } */

  if( CS_non[0][0] < -9.0 ) { /* set  CS_non[0][0] to -9.9 to read in init values for NonOrders using outCS (already discounted) */
    outCS = fopen(TMP "GetEq.CS",  "rb");  if ( outCS== NULL) { printf("\n HEY:  outCS==NULL!!   use hardcoded CS_non instead  ... aborting ...\n\n");  exit(9); }
    for(m=0;m<nPV;m++) {
      printf("\n loading  CS_non[] PVtype=%2d  ",  m );
      for(l=0;l<nT;l++) {     /* ignore seeming asymmetry in pre-updated w0sell[nonorders].  SYMM=1 --> only update pvtype's 0,1,2 (if nPV=5), and updates tend to lower s->w */
	a1 = fread( (char *) &CS_non[m][l], 1, 8, outCS );   if(a1!=8) { printf("Error reading CS_non in outCS \n");     exit(8); }
	printf(" type %2d:  %8.4f ", l, CS_non[m][l] );
      } } printf("\n"); 
    for(m=0;m<nPlim;m++) {    /* m is bid aggressiveness, l is type */
      printf("\n loading  bid aggressive= %2d  ",  m );
      for(l=0;l<nT;l++)  {
	a1 = fread( (char *)  &Pe[m][l], 1, 8, outCS );      if(a1!=8) { printf("Error reading CS_non in outCS \n");     exit(8); }
	a1 = fread( (char *) &p_v[m][l], 1, 8, outCS );      if(a1!=8) { printf("Error reading CS_non in outCS \n");     exit(8); }
	printf(" type %2d:  Pe= %6.4f  p_v=%8.4f   ", l, Pe[m][l], p_v[m][l] );
      } }
    fclose(outCS);
  }
  else {                      /* use hardcode CS_non[][] as CS per No Trem.  Discounted to next return is good init value for non-orders */
    for(p=0; p<nPV; p++)   
      for(m=0;m<nT;m++)
	if (CS_non[p][m] != CS_non[nPV_1 - p][m]) { printf("\n CS_non is NOT symmetic ... aborting \n\n");  exit(8); }
    for(p=0; p<nPV; p++)      /* hardcode CS_non[][] as CS per No Trem.  Discounted to next return is good init value for non-orders */
      for(m=0;m<nT;m++)  {
	printf("  using hardcoded  initial  CS_non[pvtype=%2d][type=%2d] = %8.4f * exp( rhoT[type]*Eagain_non ) = %8.4f\n",
	       p, m, CS_non[p][m], CS_non[p][m] * exp( rhoT[m]*Eagain_non ) );
	CS_non[p][m] *= exp( rhoT[m]*Eagain_non );
      }
  }   printf("\n\n");
  
  
  for(m=0; m<nT; m++)
    if(vlagT[m]) {
      if(nHT[m]==1) { printf("\n HEY: nHT[type=%d] = 1 but only INFORMED condition on H_jumps.  nHT[] should be 0, 2, or 3 if vlagT[]=1 ... aborting\n\n",m);  exit(8); }
      for(t1=0; t1<m; t1++)     /* if 2 types have vlag && SAME bookT,nHT then SAME s->w */
	if(vlagT[t1] && nBT[t1] == nBT[m] && nHT[t1] == nHT[m])
	  UPDATE_mkt[m]=0;      /* e.g. if differ only by rhoT[] have SAME market order beliefs! */
    }
  if(b1==1 && b2==1)   { printf("\n  nHT[]==0 for an INFORMED trader even though UNINFORMED traders are numerous.   abort \n\n");  exit(8); }
  if(0 && b1==0 && H_jumps) { printf("\n  H_jumps should be 1 IFF enough traders are uninformed that informed should condition on jumps history.  aborting...\n\n");  exit(8); }
  if(b1 && (nH==0 || H_jumps==0)) { printf("\n  H_jumps & nH should be >0 since enough traders are uninformed that informed should condition on jumps history.  aborting...\n\n");  exit(8); }
  if(H_jumps && nH==0) { printf("\n  H_jumps = 1 requires nH>0, even if nHT[]==0 for all uninformed traders.      aborting...\n  Informed condition on jumps even when uninformed condition on nothing due to too few informed for history to be revealing.\n\n");  exit(8); }
  /* H_jumps flag needed since LARGE traders condition on trans.prices & cum net buys (nHT[]=3) but need not condition on jumps if ALL traders informed */

  /* above stuff sets UPDATE_mkt[] = 1 at times even when UPDATE_maxPVT = 0.0.  Rather than find how to modify above code to avoid this, simply overwrite UDPATE_mkt */
  if (UPDATE_maxPVT < 1e-8)    for(p=0; p<nT; p++)   UPDATE_mkt[p] = 0;

  k = 0;
  for(p=0; p<nT; p++)  {
    printf("  Trader Type %2d :  vlag = %1d  UPDATE_mkt= %d  book_access = %1d  nHT=%2d  -rho= %6.3f  c= %6.3f  z= %1d\n", p, vlagT[p], UPDATE_mkt[p], nBT[p], nHT[p], rhoT[p], cT[p], zT[p]);
    u= 0.0;  v= 0.0;
    /* if(nHT[p] > nH ) { printf("\n\n Aborting ... nHT[] must be <= nH.\n\n");  exit(8); } */
    if( zT[p] >0 && nHT[p]!=nH) { printf("\n\n Aborting ... LARGE traders must have nHT[] = nH \n\n");  exit(8); }
    if( zT[p] > zmax ) { printf("\n\n Aborting ...   zT[] must be <= zmax \n\n");  exit(8); }
    if( rhoT[p]  > 0 ) { printf("\n\n Aborting ... rhoT[] must be <= 0    \n\n");  exit(8); }
    if(   cT[p]  < 0 ) { printf("\n\n Aborting ...   cT[] must be >= 0    \n\n");  exit(8); }
    if(   cT[p]  > 0 ) { printf("\n\n Aborting ...   cT[] must be  0.  That is, cT[] currently commented out in all payoffs.\n\n");  exit(8); }
    if(vlagT[p] != 0 && vlagT[p] != 1) { printf("\n\n Aborting ... vlagT[] must be either 0 or 1 \n\n");  exit(8); }
    if(nBT[p] > nB || nBT[p]<2) { printf("\n\n Aborting ... nBT[] must be either 2, 4, 6, or 8 and must be <= nB \n\n");  exit(8); }
    k = max(k,nHT[p]);
  }
  /* if(k != nH) { printf("\n\n Aborting ... nH = max(nHT) is REQUIRED, but not satisfied. \n\n");  exit(8); } */
  
  if (ExogBuy) { printf("\n\n aborting ... ExogBuy = 0 required, for now\n");  exit(8); }
  
  t1= 0; for(p=0; p<nT; p++) t1=max(t1,zT[p]);
  /* if (1-(nH==1 || nH==2 || (nH==0 && t1==0)))   { printf("\n\n Aborting ... nH must be 1, or 2, or (0 with NO LARGE traders) \n\n");  exit(8); } */
  /* Oct.2005 : allowed nH=3, but not sure if okay??? */
  if (1-(nH==1 || nH==2 || nH==3 || (nH==0 && t1==0)))   { printf("\n\n Aborting ... nH must be 1, 2, or 3 or (0 with NO LARGE traders) \n\n");  exit(8); }

  if (t1>0 && nLag==0)   { printf("\n\n Aborting ... nLag > 0 required if any traders LARGE (regardless of vlagT[]) \n\n");  exit(8); }

  if (PVmu - H_minp != H_maxp - PVmu) { printf("\n\n Aborting ... H_minp and H_maxp not equidistant from PVmu\n\n");  exit(8);}
  if (H_signed && nH==0) { printf("\n\n Aborting ... H_signed=1 but nH==0 \n\n");      exit(8); }
  if (nP_1  != nP-1)     { printf("\n\n Aborting ... nP_1 != nP-1   \n\n");            exit(8); }
  if (nPx1  != nP+1)     { printf("\n\n Aborting ... nPx1 != nP+1   \n\n");            exit(8); }
  /* if (nLag && PVjN>1)    { printf("\n\n Aborting ... PVjN must be 1 if nLag >0 \n\n"); exit(8); } */
  if (nPV-1 != nPV_1)    { printf("\n\n Aborting ... nPV-1 != nPV_1 \n\n");            exit(8); }
  if (nPV   > nPVmax)    { printf("\n\n Aborting ... nPV > nPVmax \n\n");              exit(8); }
  if (nP+1  >  nPmax)    { printf("\n\n Aborting ... nP+1> nPmax  \n\n");              exit(8); }
  if (nT    >  nTmax)    { printf("\n\n Aborting ... nT > nTmax  \n\n");               exit(8); }
  if (nT_1  != nT-1)     { printf("\n\n Aborting ... nT_1 != nT-1  \n\n");             exit(8); }
  if (nPlim != HL-LL+1 ) { printf("\n\n Aborting ... nPlim != HL-LL+1 \n\n");          exit(8); }
  if (nPtj  < 0 )        { printf("\n\n Aborting ... nPtj >= 0 required \n\n");        exit(8); }
  if (nHnRT != nH * nRT) { printf("\n\n Aborting ... nHnRT != nH * nRT \n\n");         exit(8); }
  if (nH>0 && nRT==0)    { printf("\n\n Aborting ... nH >0 & nRT==0 is stupid \n\n");  exit(8); }
  if (nPmax < 4    )     { printf("\n\n Aborting ... nPmax >= 4   required for zpq[] report \n\n");      exit(8); }
  if (nPmax == nP )      { printf("\n\n Aborting ... nPmax >  nP  required for zpq[] report & nonorders w0sell[nP] \n\n");  exit(8); }
  if (nPmax <= zmax )    { printf("\n\n Aborting ... nPmax > zmax required for zpq[] report & w0sell[large] \n\n");     exit(8); }
  /* if (nH==0 && nRT>0)    { printf("\n\n Aborting ...  nH==0 & nRT >0 is stupid \n\n"); exit(8); } */   /* NOT IF ALL UNINFORMED */
  /* if (nLag2 != 2*nLag)   { printf("\n\n Aborting ... nLag2 != 2*nLag \n\n");           exit(8); } */

  if ( (nP-(HL-LL+1))%2 != 0) { 
    printf("\n HEY: must have WHOLE number of extra ticks (at which no limitorders are accepted for informed traders) on each side of book\n\n aborting \n\n");
    exit(8);  }
  if ( (LL+HL)!= nP_1 )             { printf("\n HEY: must have  LL + HL = nP-1 ... aborting \n\n");  exit(8); }
  if ( nP_1/2 - LL != HL - nP_1/2 ) { printf("\n HEY: must have  LL and HL equidistant from mid-tick (nP_1/2) ... aborting \n\n");  exit(8); }

  if ( mJ>0 && (UPDATE_s0 || UPDATE_maxPVT || UPDATE_sync || WRITE) ) {
    printf("\n HEY: should have  WRITE=0  &  UPDATE_maxPVT~s0~sync = 0.0 0 0 when write matlab files via outM (ie, mJ>0) ... aborting \n\n");  exit(8); }

  if (UPDATE_s0==0 && WRITE && w0adjust>=0.0) { printf("\n HEY: should use UPDATE_s0=1 when converging (ie, WRITE>0) since w0sell[] update is NN weighted ... aborting \n");  exit(8); }
    
  if (PVmu != (nP_1/2.0)) { printf("\n HEY:  PVmu not at center of tick grid.  aborting ...\n\n");  exit(8); }
  
  if (e1v_e0else) {
    if((nP % 2)==0) { printf("\n HEY: cannot have e1v_e0else UNLESS common value falls on a tick (i.e., nP is odd) ... abort\n\n");  exit(8); }
    else            { printf("\n HEY: forcing s->e = 0 at all ticks EXCEPT the midtick has s->e = 1  -->  NonStrategic traders!\n"); }  }

  if (UPDATE_sync && UPDATE_maxPVT)   { printf("\n HEY: UPDATE_sync  needs  UPDATE_maxPVT = 0.0.  aborting ...\n\n");  exit(8); }
  if (UPDATE_sync)                 printf("\n UPDATE_sync = %d updates synchronously using NN, Ew if s->NN >= OftNN \n", UPDATE_sync );
  if (UPDATE_sync && UPDATE_s0==0){printf("\n HEY: UPDATE_sync  with UPDATE_s0 = 0  would only update s->w at INITIAL states of new arrivals.  Probably not desired.   aborting ...\n\n");  exit(8); }

  /* if(UPDATE_maxPVT == 0.0 && Nreset) { printf("\n HEY: aborting since Nreset != 0 but UPDATE_maxPVT == 0.0 .\n");  exit(8); } */

#if WRITE>0
  printf("\n WRITE = %d \n", WRITE);
  if(UPDATE_maxPVT == 0.0) { printf("\n HEY: aborting since WRITE >0 but UPDATE_maxPVT == 0.0 .\n");  exit(8); }
#else
  printf("\n NOT writing states to file since variable WRITE undefined.\n");
#endif

  if (SYMM==0 && eUBsimilar) { printf("\n HEY:  eUBsimilar requires SYMM==1, unless GetState() modified, which would be easy.  aborting...\n\n"); exit(9); }

  printf("\n PURGE_NC= %d  PURGE_N= %d  PURGE_MODE= %d (0: via NC if MAXSTATES)  (>0: via NC,N if (jj mod PURGE_MODE)==0 & after READ)  (<0: via NC,N after READ. NC if MAXSTATES)\n", PURGE_NC, PURGE_N, PURGE_MODE);
  
  if (PURGE_N > 0) printf(" Since PURGE_N > 0  should probably use Nreset=0 or High to decrease chance of pessimistic beliefs from string bad outcomes.\n");
  
  /* Fb[buy=0,1][price=0:nP-1] became eUB[buy=0,1][0][price=0:nP-1] so no longer need Fb variable defined */
  /* However, I'm retaining Fb[][] labels in printf()'s and comments for clarity. */
  v = 0.0;                /* BEFORE constructing Fb[] need to get #shares-based CDF of PV */
  for(m=0; m<nPV; m++) {  /* CS[0][p][4] is pdf(pv), temporarily defined above            */
    CS[0][m][1] = 0.0;    /* CS[0][pv][1] = average #shares per trader w/ pv==m * pdf(pv) */
    for(l=0;l<nT; l++)    /* CS[type][pv][0] = pdf Type, by PV, temporarily defined above */
      CS[0][m][1] += max(zT[l],1) * CS[l][m][0] * CS[0][m][4];
    v += CS[0][m][1];     /* v will be average #shares per trader arrival  */
  }
  for(p=0; p<nPV; p++) CS[0][p][2] = CS[0][p][1] / v; /* z-based pdf of pv */
  for(m=0; m<nPV; m++) {
    CS[0][m][3] = 0.0;
    for(p=0;p<=m; p++) CS[0][m][3]+= CS[0][p][2];     /* z-based cdf of pv */
  }
  for(p=0; p<nPV; p++) {  /* print z-based CDF of pv & other stuff */
    printf("\n pdf(pv= %6.2f)= %5.2f  pdf(type|pv) = ",PV[p],CS[0][p][4]); for(m=0;m<nT;m++) printf(" %5.3f",CS[m][p][0] );
    printf("   ave z|pv = %5.3f  z-based pv: pdf~cdf = %6.4f %6.4f", CS[0][p][1] / CS[0][p][4], CS[0][p][2], CS[0][p][3] );
  }
  printf("\n\n Fb[0][0,...,nP-1] = ");
  for(p=0; p<nP; p++) {   /*  +/-.001 removes asymm due to equality if use same u for [0] and[1] */
    u = 0.0;   m=0; while(m<nPV && PV[m]+PVmu < p -.001 ){ u= CS[0][m][3]; m++;}    eUB[0][0][p]= 1.0-u;   printf("%7.3f ", eUB[0][0][p]);
    u = 0.0;   m=0; while(m<nPV && PV[m]+PVmu < p +.001 ){ u= CS[0][m][3]; m++;}    eUB[1][0][p]= u; 
    /* eUB[1][0][nP_1-p]= 1.0-u; */  /* forces symm (of 1 sense??) */
    /* eUB[1][0][p]= u; */           /* forces symm (of another??) */
 }
  printf("\n Fb[1][0,...,nP-1] = ");  for(p=0; p<nP; p++)    printf("%7.3f ", eUB[1][0][p]);
  printf("\n Fb[1][nP-1,...,0] = ");  for(p=nP_1; p>=0; p--) printf("%7.3f ", eUB[1][0][p]);   /* to check symmetry */

  printf("\n\n HEY:  may/should add a new 2nd dimension of Trader Type to eUB:   eUB[buy][type][q][p] ... currently using eUB[][type 0][][] for ALL types \n\n");
  /* OLD: eUB[buy][q][p] = upper bound for Prob(execute|at priority q tick p, where ALL orders with higher priority at p or worse) */
  /*      see old code in GetEq_continuous_code.c or GetEq_MoTypes_code.c (JF paper) */

  /* NEW: eUB[buy][q][p] = lower bound for Expected time until arrival of trader for which taking this order yields nonzero payoff */
  /*      GetState() converts to initial payoff assuming that ALL orders at more aggress.prices are at PVmu */

  /* currently requires SYMM.  ie, need to add the code to compute initial s->w for limit buy orders */

  printf("\n");
  for(i=0; i<2; i++) {              /* sell (i=0) or buy (i=1)   */
    for(p=0; p<nP; p++) { 
      if(0)    printf(" Fb[%1d][%2d]= %6.3f,  E(RT elapse) = eUB[buy= %d][priority 1 to %d][p=%2d] = ", i,p,eUB[i][0][p], i,nQ,p);
      for(k=1; k<32; k++) {         /* priority at price p = k, recall that k=0 stores old Fb[] stuff */
	eUB[i][k][p]= Earrive * k / max(eUB[i][0][p], 1e-8) ;  /* eUB[i][0][p] = Prob(next arrival has PV > p for limit sells, PV<p for limit buys */
	if (0) printf(" %6.3f", min(99.999,eUB[i][k][p]));
      } if (0) printf("\n");  /* end k (priority) loop */
    }   if (0) printf("\n");  /* end p (price)    loop */
  }
  /*
  printf(" NOTE: 99.999 is truncated value for above print only.  Actual eUB[] uses  / max(Fb[][], 1e-8)\n");
  printf("       in GetState(), rhoT[] scaled by  delta = %6.3f  to create less optimistic initial s->w \n", delta);
  */
  if (delta < 1.0) { printf("\n\n aborting, delta >= 1.0 required\n\n");  exit(8); }

  /* outNew = fopen(TMP "GetEq_New", "w"); */      /* GLOBAL: printed to in GetState() */

  /* Should NEVER reassign S.  Only assign TO S.   S must ALWAYS point to memory locations from next line */
  S  = malloc(sizeof(struct state  ));  if (S ==NULL) { printf("Out of memory for GLOBAL state S  \n");  exit(8); }

  N_s[0] = 0;                  /* used as temp. count of s->j = 0 repairs during read */
  N_s[1] = 0;                  /* used as temp. count of           purges during read */
  N_s[3] = 0;                  /* used as temp. count of           ask==0 during read */
  N_s[4] = 0;                  /* used as temp. count of            H[0][] <> H_jumps */
  N_s[5] = 0;                  /* used as temp. count of      H[1][] <> H_minp,H_maxp */
  N_s[6] = 0;                  /* used as temp. count of      hash tables created     */
  N_s[7] = 0;                  /* used as temp. count of      max depth at a bucket   */
  jj = 0;                      /* initialize Big Loop counter here, before ifdef READ */
  for (p=0; p<nP+2; p++) {     /* initialize trader list at each price, current Book  */
    traders[p] = NULL;  Bt[p]=0; }   /* traders[nP] are non-orders, [nP+1] is t heap. */
  for (m=0; m<nPV; m++)
    for (i=0; i<2; i++)
      for (l=0; l<nT; l++)
	for (k=0; k<nQ; k++)
	  for (p=0; p<nPx1; p++)
	    pHT[m][i][l][k][p] = NULL;   /* initialize pHT pointers to NULL.  Will create Hash Tables as needed. */
#if use_w0sell
  for (l=0; l<nT; l++)
    for (p=0; p<nPx1; p++)
      for (bid1=0; bid1<nPx1; bid1++)
        for (ask1=0; ask1<nPx1; ask1++)
	  for (m=0; m<nPV; m++) {
	    w0sell[l][p][bid1][ask1][m] = 0.0;     /* changed upon read and updated in WriteFiles().  GetState() uses eUB[] initial s->w if w0sell == -99.99 */
	    N0sell[l][p][bid1][ask1][m] = 0.0;  }  /* stores sum(s->NN) */
#endif
#if READ>0  /* ------------  read states from files --------- see archived code related to  outT  for storing of traders[] ----  */
  
  printf("\n starting with EMPTY trader list. Cannot obtain t->s0 since loaded states have different memory location.\n");
  if (READ_w_adj > 0.000001)    printf("\n HEY: READ_w_adj = %6.4f so be sure rhoT[] has indeed been changed relative to states being loaded \n", READ_w_adj);
  if (delta > 1.0001 && nJ>1e8) printf("\n HEY: delta = %6.4f reduces returning payoffs by %6.4f to unexecuted limit orders.  Set delta= 1.0 if near convergence. \n", delta, delta-1.0);

  Bt[nPx1] = -1;  Bt[nP] = nP;  /* Bt[] initialized to zeros above */
  j = 0; m1 = 1;  v0= 0.0;      /* m1=1 --> create memory for next state, else use s from previously purged state */
  for(t1=0; t1<2-SYMM; t1++) {  /* if SYMM then only load SELLER states */
    if(t1) {
      if (READ == 2) {
	printf("\n Loading " TMP "blah.even files ... \n");
	outw =  fopen(TMP "GetEq.w.buy.even", "rb");
	outN =  fopen(TMP "GetEq.N.buy.even", "rb");
	outB =  fopen(TMP "GetEq.B.buy.even", "rb"); }
      else {
	printf("\n Loading " TMP "blah.odd files ...  \n");
	outw =  fopen(TMP "GetEq.w.buy.odd",  "rb");
	outN =  fopen(TMP "GetEq.N.buy.odd",  "rb");
	outB =  fopen(TMP "GetEq.B.buy.odd",  "rb"); } }
    else {
      if (READ == 2) {
	printf("\n Loading " TMP "blah.even files ... \n");
	outw =  fopen(TMP "GetEq.w.sell.even", "rb");
	outN =  fopen(TMP "GetEq.N.sell.even", "rb");
	outB =  fopen(TMP "GetEq.B.sell.even", "rb"); }
      else {
	printf("\n Loading " TMP "blah.odd files ...  \n");
	outw =  fopen(TMP "GetEq.w.sell.odd",  "rb");
	outN =  fopen(TMP "GetEq.N.sell.odd",  "rb");
	outB =  fopen(TMP "GetEq.B.sell.odd",  "rb"); } }
    
    if(outw==NULL) {printf("fopen() ERROR opening w infile.  Aborting...\n"); exit(8);}
    if(outN==NULL) {printf("fopen() ERROR opening N infile.  Aborting...\n"); exit(8);}
    if(outB==NULL) {printf("fopen() ERROR opening B infile.  Aborting...\n"); exit(8);}   fflush(stdout);
    
    a1 = fread( (char *) &v, 1, 8, outw );
    while (a1 == 8) {   /* outw  successfully read --> next state exists */
      j++;              /* count of states read */
      if(m1) { s = malloc(sizeof(struct state));  if (s==NULL) { printf("Out of memory loading states \n");  exit(8); }}
      m1 = 1;           /* default is s NOT purged --> m1=1 */
      s->next = NULL;
      s->w = v;         /*  NOTE:  these fread() must be in same order as fwrite() in WriteFiles() */
      a1 = fread( (char *) &m, 1, 2, outB );  if(a1!=2)    { printf("Error reading m in outB \n");  exit(8); }
      a1 = fread( (char *) &i, 1, 2, outB );  if(a1!=2)    { printf("Error reading i in outB \n");  exit(8); }
      a1 = fread( (char *) &l, 1, 2, outB );  if(a1!=2)    { printf("Error reading l in outB \n");  exit(8); }
      a1 = fread( (char *) &p, 1, 2, outB );  if(a1!=2)    { printf("Error reading p in outB \n");  exit(8); }
      a1 = fread( (char *) &k, 1, 2, outB );  if(a1!=2)    { printf("Error reading k in outB \n");  exit(8); }

      a1 = fread( (char *) &s->B, 1, 2*nB, outB );  if(a1!=2*nB) { printf("Error reading B in outB \n");     exit(8); }

#if nL>0
      a1= fread(  (char *) &s->L, 1, 2*nL, outB );  if(a1!=2*nL) { printf("Error reading L in outB \n");     exit(8); }
#endif
      if(nHnRT) {
	a1=fread( (char *) &s->H,1,2*nHnRT,outB );  if(a1!=2*nHnRT){ printf("Error reading H in outB \n");    exit(8); } }
      /* s->H[0][0] = 0; */
      
      a1 = fread( (char *) &s->NC,1, 4, outN );  if(a1!=4) { printf("Error reading NC in outN \n");  exit(8); }
      a1 = fread( (char *) &s->N, 1, 4, outN );  if(a1!=4) { printf("Error reading N  in outN \n");  exit(8); }
      a1 = fread( (char *) &s->NN,1, 4, outN );  if(a1!=4) { printf("Error reading NN in outN \n");  exit(8); }
      /* a1 = fread( (char *) &s->NT,1, 4, outN );  if(a1!=4) { printf("Error reading NT in outN \n");  exit(8); } */

      /*  if (m<3)  {  */     /*  for converting to speculators having pv = 0.0 instead -.1, .1 */

#if use_w0sell      
      if( k!=3 ) { /* Ignore k=3 states for which s->w is E(v) */
	if(zT[l]){ p1=k;  printf(" large tz must be modified ... aborting"); exit(8); }  /* large tz:  REQUIRES zmax < nPmax since using k (shares togo) as [p] index */
	else {                    /* not large tz */
	  p1 = p;
	  w0sell[l][p][0][0][m] +=  s->w * s->N;  /* integrate over all bid,ask to use when [bid][ask] not available */
	  N0sell[l][p][0][0][m] += (double)s->N;  /* NOT for LARGE tz */ 
	}  /* end not large tz */
	if (ask1) {  /* Combine bid= -1, 0 rather than shift bid to allow bid -1.  Harmless, since market similar w/ bid=0 or bid = -1 ? */
	  w0sell[l][p1][max(bid1,0)][ask1][m] +=  s->w * s->N;    /* after all states read, w0sell[] = w0sell[] / N0sell[] */
	  N0sell[l][p1][max(bid1,0)][ask1][m] += (double)s->N; }  /* if N0sell[] = 0 then w0sell == -99.99 since GetState() uses eUB[] initial s->w if w0sell == -99.99 */
	else  N_s[3]++;  /* ignore the rare cases when ask = 0 since bid=0=ask is for averaging over all bid,ask */
      } 
#endif      

      if (p>nP || (nQ==4 && p==nP && k!=0 && zT[l]==0)) { printf("\n HEY: loaded state has p > nP || (p==nP, zT[l]==0 w/out k==0).  aborting... p=%d \n\n", p);  exit(8); }
      
      if (PURGE_MODE <0 && ( s->NC < PURGE_NC || s->N <= PURGE_N ) ) {  N_s[1]++;  m1 = 0; }  /* requires NOT load traders[] since a t->s,s0 could be purged */
      if (PURGE_MODE==0 && ( s->NC < PURGE_NC || s->N <= PURGE_N ) )    N_s[1]++;             /* count states that WOULD be purged if PURGE_MODE <0 */


      /* if (p==nP) m1=0;*/   /* drop non-orders since SYMM was wrong for them */

      /* if (p==nP && PV[m] > 0.00001 )          m1=0; */  /* drop non-orders with PV > 0 since using SYMM */

      /* if (nHT[l]>1) if(k==3 && s->H[1][0]<0)  m1=0; */  /* drop E(v) states  with last trans.price < 0 (i.e., signed order = -1) since using SYMM */
      
      if (k!=3 && READ_w_adj>0.01) s->w *= READ_w_adj; /* if change |rho| adjust limit/non orders via  READ_w_adj = exp( (|rho_old|-|rho_new|) * Eagain) */

      /* if(l==1) m1=0; */     /* m1=0 to skip state */

#if 0  /* <-- drop state if H[0][] (jumps)  or  H[1][] (last trans.price) beyond potentially just tightened truncation values H_jumps, H_minp, H_maxp */
      for(p1=0; p1<nRT; p1++) {
	if( m1 && nHT[l] && zT[l]==0) { /* could expand to    ... H[][p1]  ...  if ever use nRT>1 */  /* MUST modify for LARGE traders since H[] is *100 */
	  if( s->H[0][p1] < -H_jumps ||  s->H[0][p1] > H_jumps )  {  m1=0;  N_s[4]++; }
	  if( m1 && nHT[l] > 1) {
	    b1 = s->H[1][p1];  if (b1<0) b1 = -b1; /* in case H[1][] is SIGNED last trans.price to reveal signed order flow */
	    if( b1!=99 && (b1 < H_minp  ||  b1 > H_maxp ))        {  m1=0;  N_s[5]++; } }   /* H[1][] = -99 for NO transactions within RT bin */
	  /* if(m1==0) printf("m i l p k= %d %d %d %d %2d  s->N~NN~w= %9u %9u %7.3f  H[0,1][]= %2d %3d \n", m,i,l,p,k, s->N,s->NN,s->w,s->H[0][0],s->H[1][0]); */
	} }
#endif

      
#if 0   /* print empty book for informed PV= -.1 sellers at tick 16 (or all) to see how s->w varies across s->H */
      if(nH==2 && l==0 && m==2 && (p==16 || 1) && p==ask1 && bid1==-1) {
	b1 = 0;   for(p1=p; p1<nP; p1++)  b1 += s->B[p1];  /* b1 is # asks */
	if(b1 == -1) {
	  printf(" pvtype= %d  buy= %d  type= %d  p= %2d  q=%2d  p~s->N~NN~w~H[0]~H[1]= %2d %9u %9u %7.3f %2d %3d  Book: ",
		 m,i,l,p,k, p, s->N, s->NN, s->w, s->H[0][0], s->H[1][0] );  /* repeat p so only need to yank 1 "column" for matlab */
	  for(p1=0; p1<nP; p1++)  if(s->B[p1]) printf(" %2d:%2d,", p1, s->B[p1]);  printf("\n");  } }
#endif  /* in matlab: load last 5 columns into x and then:  i = find(x(:,3)==1 & x(:,4)==16);   plot(x(i,2),x(i,1), 'b.')  */

#if 0   /* print states for uninformed (l==1) -4 guy (m==1) facing a given book a limit buy at -1 (relative to last observed), otherwise empty */
      if( l==1 && ((m==1 && ( p==ask1 || p==nP)) || (m==0 && k==0)) && bid1== PVmu ) {  /* k==0 --> E(v), p==nP --> NonOrder */
	b1 = 0;   for(p1=0; p1<nP; p1++)  b1 += max(s->B[p1], -s->B[p1]);  /* b1 is #orders, buy or sell */
	if( b1==1 || (b1==2 && p<nP && k>0))  {  /* if 2 orders then one of them is current traders new one */
	  printf(" pvtype= %d  buy= %d  type= %d  p= %2d  q=%2d  p~s->N~NN~w~H[0]~H[1]= %2d %9u %9u %7.3f %2d %3d  Book: ",
		 m,i,l,p,k, p, s->N, s->NN, s->w, s->H[0][0], s->H[1][0] );  /* H is [nH][nRT] */
	  for(p1=0; p1<nP; p1++)  if(s->B[p1]) printf(" %2d:%2d,", p1, s->B[p1]);  printf("\n");  } }
#endif
      
#if 0   /* print states for informed (l==0) -.1 guy (m==2) facing a given book a limit buy at -1 (relative to last observed), otherwise empty */
      if( l==0 && ((m==2 && ( p==ask1 || p==nP)) || (m==0 && k==0)) && bid1== PVmu ) {  /* k==0 --> E(v), p==nP --> NonOrder */
	b1 = 0;   for(p1=0; p1<nP; p1++)  b1 += max(s->B[p1], -s->B[p1]);  /* b1 is #orders, buy or sell */
	if( b1==1 || (b1==2 && p<nP && k>0))  {  /* if 2 orders then one of them is current traders new one */
	  if ( s->H[0][0]==0 &&  s->H[0][1]==PVmu ) {  /* [0] is change in v, [1] is signed last p */
	    printf(" pvtype= %d  buy= %d  type= %d  p= %2d  q=%2d  p~s->N~NN~w~H[0]~H[1]= %2d %9u %9u %7.3f %2d %3d  Book: ",
		   m,i,l,p,k, p, s->N, s->NN, s->w, s->H[0][0], s->H[1][0] );  /* H is [nH][nRT] */
	    for(p1=0; p1<nP; p1++)  if(s->B[p1]) printf(" %2d:%2d,", p1, s->B[p1]);  printf("\n");  } } }
#endif
#if 0   /* print states for  ...  facing an empty book (bid1==-1) */
      if( l==1 && ((m==2 && ( p==ask1 || p==nP)) || (m==0 && k==0)) && bid1== -1 ) {  /* k==0 --> E(v), p==nP --> NonOrder */
	b1 = 0;   for(p1=0; p1<nP; p1++)  b1 += max(s->B[p1], -s->B[p1]);  /* b1 is #orders, buy or sell */
	if( b1==0 || b1==1 || (b1==1 && k==1) )  {  /* b1=0 --> E(v) or nonorder, b1=1 and k=1 --> limitorder */
	  /* if ( s->H[0][0]==0 &&  s->H[0][1]==PVmu ) */  {  /* [0] is change in v, [1] is signed last p */
	    printf(" pvtype= %d  buy= %d  type= %d  p= %2d  q=%2d  p~s->N~NN~w~H[0]~H[1]= %2d %9u %9u %7.3f %2d %3d  Book: ",
		   m,i,l,p,k, p, s->N, s->NN, s->w, s->H[0][0], s->H[1][0] );  /* H is [nH][nRT] */
	    for(p1=0; p1<nP; p1++)  if(s->B[p1]) printf(" %2d:%2d,", p1, s->B[p1]);  printf("\n");  } } }
#endif
#if 0   /* print states for  ...  facing an empty book (bid1==-1) */
      if( ((m==1 && ( p==ask1 || p==nP)) || (m==0 && k==0)) && bid1== -1 ) {  /* k==0 --> E(v), p==nP --> NonOrder */
	b1 = 0;   for(p1=0; p1<nP; p1++)  b1 += max(s->B[p1], -s->B[p1]);  /* b1 is #orders, buy or sell */
	if( b1==0 /* || b1==1 */ || (b1==1 && k==1 && p<nP ) )  {  /* b1=0 --> E(v) or nonorder, b1=1 & k=1 & p<nP --> limitorder */
	  if ( /* s->H[0][0]==0 && */ s->H[0][1]==  PVmu )   {  /* [0] is change in v, [1] is signed last p */
	    printf(" pvtype= %d  buy= %d  type= %d  p= %2d  q=%2d  p~s->N~NN~w~H[0]~H[1]= %2d %9u %9u %7.3f %2d %3d  Book: ",
		   m,i,l,p,k, p, s->N, s->NN, s->w, s->H[0][0], s->H[1][0] );  /* H is [nH][nRT] */
	    for(p1=0; p1<nP; p1++)  if(s->B[p1]) printf(" %2d:%2d,", p1, s->B[p1]);  printf("\n");  } } }
#endif
#if 0
      if(k==0 && s->H[0][1]== PVmu /* && s->N > 1000 && fabs(s->w) > .4 */ ) {
	b1 = 0;   for(p1=0; p1<nP; p1++)  b1 += max(s->B[p1], -s->B[p1]);  /* b1 is #orders, buy or sell */
	if( b1==1 ) {
	  printf(" pvtype= %d  buy= %d  type= %d  p= %2d  q=%2d  p~s->N~NN~w~H[0]~H[1]= %2d %9u %9u %7.3f %2d %3d  Book: ",
		 m,i,l,p,k, p, s->N, s->NN, s->w, s->H[0][0], s->H[1][0] );  /* H is [nH][nRT] */
	  for(p1=0; p1<nP; p1++)  if(s->B[p1]) printf(" %2d:%2d,", p1, s->B[p1]);  printf("\n"); } }
#endif
#if 0
      if(k==0 && s->N > 5000 && ( (bid1>0 && bid1<PVmu-4) || (ask1<nP_1 && ask1>PVmu+4) ) ) {
	printf(" pvtype= %d  buy= %d  type= %d  p= %2d  q=%2d  p~s->N~NN~w~H[0]~H[1]= %2d %9u %9u %7.3f %2d %3d  Book: ",
	       m,i,l,p,k, p, s->N, s->NN, s->w, s->H[0][0], s->H[1][0] );  /* H is [nH][nRT] */
	for(p1=0; p1<nP; p1++)  if(s->B[p1]) printf(" %2d:%2d,", p1, s->B[p1]);  printf("\n"); }
#endif


      /* m1 = 0; */    /* drop ALL states */

      /* if (k==3) m1 = 0; */  /* drop all E(v) states */

      /* if (k==2) m1 = 0; */  /* drop all CANCEL states */


#ifdef Type2_No_limitorders
      /* if ( m==2 && k>0 && p<nP)  m1=0; */  /* drop all limitorder states loaded for pvtype 2 (speculators) */
#endif


      /* if (m==2 && p==nP)  s->w = max( s->w, CS_non[m][l] );  */
      
      if(s->w < -9999.0 /* ie, nan */ ) { printf(" dropping negative  s->w = %8.4f  m l k p=  %2d %2d %2d %2d  N=%u  \n", s->w, m,l,k,p,s->N);   m1=0; }

      if (m1) {       /* m1=1 --> state NOT purged.  If purged, m1=0 and next state read will simply use this s */
	if(Nreset>0 || Nreset_READ>0 || PURGE_MODE || WRITE==0 )   s->NC = 0;    /* else keep s->NC as read from file */
	s->NN = 0;
	s->NT = 0;
	s->Ew = 0.0;
	s->w2 = 0.0;
	s->Pw = s->w; /* w from previous jj */
	s->Eoptimal = -1.0;
#if useNe>0
	s->Ne = 0;
#endif
#if useNNtt>0
	s->NCtt = 0;    s->NNtt = 0;
	s->NCtj = 0;    s->NNtj = 0;
#endif
	if ( (UPDATE_PVT[l][m] && k!=3) || (UPDATE_mkt[l] && k==3) ) { /* consider resetting N unless beliefs fixed */
	  if (Nreset_READ > 0)  s->N = min(s->N, Nreset_READ);
	  if (Nreset == 1)      s->N = 1;
	  if (Nreset  > 1)      s->N = min(s->N, Nreset*(jj0+1));   /* Nreset < 0 causes NO reset during READ */
	}
#if usePN>0
	s->PN = s->N;  /* enables check that s->w indeed equals   (s->Pw * s->NN + s->Ew) / (s->NN + s->PN) */
	s->NC = 0;     /* probably in a debugging mode, so its helpful to set NC=0 */
	s->SSN  = 0;
	s->SSEw = 0.0;
#endif

#ifdef useNX
	s->NN=0;  s->NX=0;
#endif
	/*  if (m==2)  s->N = 1;  */       /* if converting from |.1| speculators to pv = 0 speculators  via  if (m<3)  {} */

      s_to_pHT:
	

	ht= pHT[m][i][l][k][p];
	if (ht==NULL) {                                     /* initialize HT for this (i,l,k,p) */
	  ht = malloc(sizeof(struct HT));  if (ht==NULL) { printf("Out of memory for new HT \n");  exit(8); }
	  for (h=0; h<Prime; h++)  ht->bucket[h] = NULL;    /* initialize buckets */
	  pHT[m][i][l][k][p] = ht;    N_s[6]++;             /* N_s[6] counts #hash tables created */
	}
	
	/* GetHash : MUST BE SAME AS USED IN GetState() */
	h = 0;  
	for(p1=0; p1<nB;    p1++)  h+=   s->B[p1]    * hscale[p1];
	for(p1=0; p1<nHnRT; p1++)  h+= *(s->H[0]+p1) * hscale[p1+nB];
	for(p1=0; p1<nL;    p1++)  h+=   s->L[p1]    * hscale[p1+nB+nHnRT];
	h = h % Prime ;

#if 0      
	if (s->NN > 1000) { /* to enter if() need nJ<10 so that NN not reset above */
	  printf("m= %2d  i= %1d  l= %1d  k=%3d  p=%2d  h=%9u  B= ",m,i,l,k,p,h);  for(p1=0; p1<nB; p1++)  printf(" %3d:%3d,", p1, s->B[p1]);  
	  printf("  w= %8.6f  NN=%9u \n", s->w,s->NN);	fflush(stdout);  }
#endif
	
	sp= ht->bucket+h;  aTremN=0;
	while( *sp != NULL ) {
	  aTremN++;  if (aTremN > N_s[7]) { N_s[7] = aTremN;  printf("\n new max depth  %4u  at bucket with mlkp=%2d %2d %2d %3d h=%10u  B:",aTremN,m,l,k,p,h); for(p1=0;p1<nB;p1++) printf(" %3d", s->B[p1]); printf("  H:"); for(p1=0;p1<nHnRT;p1++) printf(" %3d",*(s->H[0]+p1)); }
	  sp = &((*sp)->next); /* add state to END of list beginning at bucket[h] in HT array (i,k,p) */
	}
	*sp = s;  v0 += (double)aTremN;    /* v0 / #states will be average "depth" of each state */

#if 0
	if ( p==nP && m==2 && l==0 && k==0 ) {  /* create exact same state for l=2 m=2 */
	  l=1;  j++;                            /*  printf("s->w= %8.4f  ", s->w); */
	  s = malloc(sizeof(struct state));  s->N = 1;  s->NN=1;   s->NT=0;  s->NC=0;  s->Ew = 0.0;  s->w2= 0.0;  s->Eoptimal = -1.0;
	  s->w = (*sp)->w;   s->Pw = s->w;      /*  printf(" %8.4f\n ", s->w);  exit(1); */
	  for(p1=0; p1<nB;     p1++)   s->B[p1]    =    (*sp)->B[p1];
	  for(p1=0; p1<nHnRT;  p1++) *(s->H[0]+p1) = *( (*sp)->H[0]+p1);
	  goto s_to_pHT;  }
#endif
	
#if usePN>0
	/* if ( m==2 && l==0 && k==4 &&  p==32 && s->B[0]==30 && s->B[1]==32 && s->L[0]==0 && s->L[1]==0 && s->L[2]==0 ) { */

	/*	if ( m==2 && l==0 && k==0 &&  p==35 && s->B[0]==31 && s->B[1]==32 && *(s->H[0])==3 && *(s->H[0]+1)==31 ) {
	  printf( "\n\n ret=4 match SS= %p  N=%7u  w= %8.5f  mlkp= %d %d %d %d  bid~ask= %d %d  H=", s,s->N, s->w, m,l,k,p,s->B[0], s->B[1]);
	  for(p1=0; p1<nHnRT; p1++)  printf(" %3d", *(s->H[0]+p1));  printf("\n");     }
	*/
	/* find particular state which has problems in nB=2 nH=0 model */
	/* if ( m==0 && l==1 && k==0 &&  p==29  && s->B[0]==-1 && s->B[1]==24 && s->B[2]==0 && s->B[3]==3 && s->B[4]==0 && s->B[5]==2 && *(s->H[0])==0 && *(s->H[0]+1)==-26 ) { */
	if ( m==1 && l==1 && k==0 &&  p==33  && s->B[0]==29 && s->B[1]==32 && s->B[2]==1 && s->B[3]==5 && s->B[4]==1 && s->B[5]==2 && *(s->H[0])==0 && *(s->H[0]+1)==26 ) { 
	  SS=s;  printf( "\n\n found match SS= %p  N=%7u  w= %8.5f  mlkp= %d %d %d %d  bid~ask= %d %d  L=", s,s->N, s->w, m,l,k,p,s->B[0], s->B[1]);
	  for(p1=0; p1<nL; p1++)     printf(" %3d", s->L[p1]);  printf("  H:");
	  for(p1=0; p1<nHnRT; p1++)  printf(" %3d", *(s->H[0]+p1));  printf("\n");           SSNN = SS->NN;  s->NC=0; }
#endif
	
      }  /* end if(m1) */
      
      
      /*   }  else{ j--;  m1=0; }  */       /* end m<3, for converting to speculators having pv = 0.0 instead -.1, .1 */


      a1 = fread( (char *) &v, 1, 8, outw );      /* if successfully read, then a "next" state exists. */
    }    /* end while (a1==8) */
    fclose(outw);
    fclose(outN);  printf("\n");
    fclose(outB);
    if (PURGE_MODE < 0) {
      if(t1) printf("\n loaded buyer states.   Total states loaded =  %u,  %u saved,  %u purged via PURGE_NC,N= %d %d,  dropped via H[0~1][]: %d %d \n\n", j, j-N_s[1]-N_s[4]-N_s[5], N_s[1], PURGE_NC, PURGE_N, N_s[4], N_s[5]);
      else   printf("\n loaded seller states.  Total states loaded =  %u,  %u saved,  %u purged via PURGE_NC,N= %d %d,  dropped via H[0~1][]: %d %d  \n\n", j, j-N_s[1]-N_s[4]-N_s[5], N_s[1], PURGE_NC, PURGE_N, N_s[4], N_s[5]); }
    else {
      if(t1) printf("\n loaded buyer states.   Total states loaded =  %u,  %u saved,  %u purge CANDIDATES via PURGE_NC,N== %d %d,  dropped via H[0~1][]: %d %d  \n\n", j, j-N_s[4]-N_s[5], N_s[1], PURGE_NC, PURGE_N, N_s[4], N_s[5]);
      else   printf("\n loaded seller states.  Total states loaded =  %u,  %u saved,  %u purge CANDIDATES via PURGE_NC,N== %d %d,  dropped via H[0~1][]: %d %d  \n\n", j, j-N_s[4]-N_s[5], N_s[1], PURGE_NC, PURGE_N, N_s[4], N_s[5]); }


#ifdef CheckSEGV
    printf("\n address length of last state created is %d  for s= %p ... lengths > 16 are treated as invalid in an attempt to avoid the SEGV's on elaine ...\n", sprintf(buffer,"%p",s), s);
#endif

  } /* end for(t1) loop over SELL/BUY states */
  
  N_s[2]= j - (PURGE_MODE<0) * N_s[1]; /* initialize #total states = #read - #purged */

  printf("\n average depth of each state (ie, #states preceding each state at its bucket) = %8.4f\n",  v0/N_s[2]);
  printf("\n #hash tables created =  %u, each with Prime=  %u  buckets, requires %5.1f megabytes.   pHT[] uses %5.3f megabytes \n",
	 N_s[6], Prime, (double)N_s[6]*Prime/1048576.0, (double)nPVmax*(2-SYMM)*nTmax*nQ*nPx1/1048576.0 );
  
  if (READ==2) jj=1;  /* prevents overwriting the completed set of /tmp/GetEq/GetEq* files  */

  if ( N_s[0]>0 ) printf(" reset s->j = 0 due to being < -NP or > nP at  %u  states\n", N_s[0]);

#endif                /* ------------------------  if READ>0  ---------------------------- */



  /* return(1); */

  
#ifdef CHECK_STATES
  if ( 0 && nB==nP) {
    printf("\n\n s->w for a few select states,  e.g., only order at various prices. \n");
    InsertNewState = 'n';
    t = malloc(sizeof(struct trader));  if (t==NULL) { printf("Out of memory for trader to print select states. \n");  exit(8); }
    t->pv= 0;
    t->z = 0;  /* SMALL traders only */
    for( t->pvtype =0; t->pvtype < nPV;  t->pvtype++)
      for( t->buy =0; t->buy < 1;  t->buy++)          /* t->buy <1 for only sell states */
	for( t->type =0;  t->type < nT;  t->type++)   {
	  printf("\n");
	  for( t->q  =1;  t->q < 8;  t->q++)   {
	    printf("\n");
	    for( t->p =0; t->p < nP; t->p++)   {
	      for(p=0; p<nB; p++)    S->B[p] = 0;  /* using S->B to avoid declaring another short int vector (cannot overwrite current Bt) */
	      if (t->buy)            S->B[t->p] =  t->q;
	      else                   S->B[t->p] = -t->q; 
	    
	      /* S->B[3] = -2; */
	    
	      printf(" pvtype= %d  buy= %d  type= %d  p= %d  q=%2d  Book: ", t->pvtype, t->buy, t->type, t->p, t->q);  for(p=0; p<nB; p++)  printf(" %2d:%2d,", p, S->B[p]);
	      s = GetState(t, S->B);
	      printf(" s->N =%8u  s->NC =%8u  eUB= %5.3f  s->w =%10.2e \n", s->N, s->NC, eUB[t->buy][t->q-1][t->p],s->w );
	    } } }
    free(t);  t= NULL;  printf("\n");
  }

  /* check for monotonicity in w -- only for sell states, INFO==0 */
  if ( 0 && nB==nP) {
    InsertNewState = 'n';
    j = 0;  Jcount[0]=0;  Jcount[1]=0;  Jcount[2]=0;   printf("\n");
    t = malloc(sizeof(struct trader));  if (t==NULL) { printf("Out of memory for trader to check monotonicity.\n");  exit(8); }
    t->z = 0;  /* SMALL traders only */
    for( t->pvtype =0; t->pvtype < nPV;  t->pvtype++)
      for (t->buy=0; t->buy <1; t->buy++)      /* <1 --> only sell states  */
	for (t->type=0; t->type<nT; t->type++) /* trader types with respect to book & vlag */
	  for (k=1; k<nQ;  k++) {              /* k=1 --> queue >= 1 */
	    t->q= k-1;                         /* compare e to same state except 1 lower q  */
	    for (t->p=0; t->p <nP; t->p++) 
	      if (pHT[t->pvtype][t->buy][t->type][k][t->p] != NULL)
		for (h=0; h<Prime; h++) {      /* loop through all buckets in the HT associated with 3-tuple i,p,k */
		  s = pHT[t->pvtype][t->buy][t->type][k][t->p]->bucket[h];
		  sp= pHT[t->pvtype][t->buy][t->type][k][t->p]->bucket+h;  /* struct state **sp */
		  while (s != NULL) {          /* loop through states in this bucket's list */
		    for(p=0;p<nB;p++) S->B[p]= s->B[p];   /* S->B same s->B, except 1 less in q at t->p. B++ since B<0 for sells */
		    S->B[t->p]++;              /* using S->B to avoid declaring another short int vector (cannot overwrite Bt)    */
		    u = s->w;  Jcount[3]=s->N;
		    sp= &s->next;              /* since next line temporarily uses s */
		    s = GetState(t,S->B);
		    if (s==S) Jcount[2]++;
		    else {                     /* Similar State S exists */
		      if (u > s->w) {          /* Non-Monotonicity Found */
			Jcount[0]++;
			if (Jcount[3]>20 && s->N>20) {
			  printf(" w1= %8.5f  w2= %8.5f  w1-w2=%9.1e  p=%2d  q1=%3d  q2=%3d  Book2=",u,s->w,u-s->w,t->p,k,t->q);
			  for(p=0;p<nP;p++) printf(" %3d",s->B[p]);  printf("  N2= %u\n",s->N);
			  Jcount[1]++;
			} }
		      else  j++;               /* count comparisons satisfying monotonicity */
		    }
		    s = *sp;                   /* avoids declaring another s2 just for this monotonicity code */
		  } } }
    printf("\n\n Monotonicity check done: %u comparisons monotone, %u comparisons NOT, of which %u have both N>20.  %u states have no comparison.\n\n",j,Jcount[0],Jcount[1],Jcount[2]);
    free(t);  t= NULL;
  }
#endif  /* --------------------------  ifdef CHECK_STATES  -------------------------- */

#if use_w0sell
  h = 0;                           /* w0sell updated in WriteFiles() if WRITE >0 */
  for (l=0; l<nT; l++)             /* GetState() uses eUB[] initial s->w if w0sell == -99.99 */
    for (p=0; p<nPx1; p++)
      for (bid1=0; bid1<nPx1; bid1++)
        for (ask1=bid1; ask1<nPx1; ask1++) /* ask >= bid1 */
	  for (m=0; m<nPV; m++) {
	    if(N0sell[l][p][bid1][ask1][m] > 0.0) {
	      h++;  w0sell[l][p][bid1][ask1][m] *= 1.0/ N0sell[l][p][bid1][ask1][m];
	      if(ask1==0 && vlagT[l]==0)
		printf("m= %d  type= %d  p= %2d  bidask= %d %d    w0sell= %7.2f  N0sell= %1.0f\n",
			 m,l,p,bid1,ask1, w0sell[l][p][bid1][ask1][m], N0sell[l][p][bid1][ask1][m] );
	      }
	      else      w0sell[l][p][bid1][ask1][m] = -99.99; }
  printf("\n initial s->w using  w0sell[type][price][bid][ask][pv]  initially defined (ie, > -99.9) for  %u  'quotes-only' states.  w0adjust = %5.3f  #ask==0(ignored)= %u \n\n", h, w0adjust, N_s[3]);
#else
  printf("\n initial s->w does NOT use w0sell stuff...\n\n");
#endif

#ifdef LimCost
  printf("\n imposing  LimCost = %5.3f  on all limitorders submitted.  Should probably set to 0.0 when nearing convergence\n\n", LimCost);
#endif

  fflush(stdout);  qmax=0;  qave=0.0;  non_ave=0.0;  for(p=0;p<8;p++) wCancel[p]= 0.0;
  
  tz = malloc(sizeof(struct trader));
  tm = malloc(sizeof(struct trader));
  tt = malloc(sizeof(struct trader));  if (tt==NULL || tz==NULL || tm==NULL) { printf("Out of memory for tt or tz or tm \n");  exit(8); }
  tt->next   = NULL;      tm->next   = NULL;      tz->next   = NULL;   /* tz is LARGE (multishare) trader who submits ONLY MARKET ORDERS */
  tt->s      = NULL;	  tm->s      = NULL;	  tz->s      = NULL;   /* tm is trader for updating market order by vlag traders */
  tt->s0     = NULL;	  tm->s0     = NULL;	  tz->s0     = NULL;   /* tt is trader for new arrivals */
  tt->si     = NULL;	  tm->si     = NULL;	  tz->si     = NULL;
  tt->again  = 0.0;	  tm->again  = 0.0; 	  tz->again  = 0.0;  
  tt->since  = 0.0;	  tm->since  = 0.0; 	  tz->since  = 0.0;    /* if tz->q > 0 then tz considers mkt orders immed. after EACH tt,tj act */
  tt->since0 = 0.0;	  tm->since0 = 0.0; 	  tz->since0 = 0.0;    /* that is, LARGE traders are in market continuously */
  tt->sincei = 0.0;	  tm->sincei = 0.0; 	  tz->sincei = 0.0;  
  tt->rtime0 = 0.0;	  tm->rtime0 = 0.0; 	  tz->rtime0 = 0.0;  
  tt->CS     = 0.0;	  tm->CS     = 0.0; 	  tz->CS     = 0.0;    /* realized discounted CS thus far (used by LARGE traders) */
  tt->Ev     = 0.0;	  tm->Ev     = 0.0; 	  tz->Ev     = 0.0;    /* E(v) relative to last observed, t->v */
  tt->pv     = 0.0;	  tm->pv     = 0.0; 	  tz->pv     = 0.0;    /* tm->pv,pvtype,p NOT used by GetState */
  tt->pvtype = 0;	  tm->pvtype = 0;   	  tz->pvtype = 0;      /* since market order s->w is E(current v), NOT E(surplus) */
  tt->z      = 0;	  tm->z      = 0;   	  tz->z      = 0;      /* z = 0 SMALL (1-share),  z = 1 LARGE (multishare) trader */
  tt->buy    = 0;	  tm->buy    = 0;   	  tz->buy    = 0;
  tt->p      = 0;	  tm->p      = 0;   	  tz->p      = 0;    
  tt->q      = 0;	  tm->q      = 0;   	  tz->q      = 0;
  tt->ret    = 0;	  tm->ret    = 3;   	  tz->ret    = 0;      /* tm->ret= 3 is flag in GetState for E(v) market orders  */
  tt->type   = 0;	  tm->type   = 0;   	  tz->type   = 0;    
  tt->tB     = 0;	  tm->tB     = 0;   	  tz->tB     = 0;    
  tt->tH     = 0;	  tm->tH     = 0;   	  tz->tH     = 0;    
  tt->tremble= 0;	  tm->tremble= 0;   	  tz->tremble= 0;    
  tt->aggress= 0;	  tm->aggress= 0;   	  tz->aggress= 0;    
  tt->v      = 0;	  tm->v      = 0;   	  tz->v      = 0;      /* last obs. v, rel. to current v.   t->v <0 --> v increased */
  tt->vlag   = 0;	  tm->vlag   = nLag;	  tz->vlag   = nLag;   /* market order beliefs only needed when vlag=nLag */
  tt->j      = 0;	  tm->j      = 0;   	  tz->j      = 0;    
  tt->jlag   = 0;	  tm->jlag   = 0;   	  tz->jlag   = 0;    
  tt->period = 0;     	  tm->period = 0;   	  tz->period = 0;      
  tt->n      = 0;     	  tm->n      = 0;   	  tz->n      = 0;      /* # of arrivals to market */
#if nL>0
  for(p=0;p<nL;p++){  tt->L[p]= 0;  tm->L[p]= 0;  tz->L[p]= 0; }
#endif

  J  = malloc(sizeof(struct vJ));
  J1 = malloc(sizeof(struct vJ));
  Jx = malloc(sizeof(struct vJ));  if (Jx==NULL) { printf("Out of memory for Jx \n");  exit(8); }
  J1->next = J;     J1->prev = NULL;  J1->n = 0;  J1->RT = -1.0 - nLag;  /* initialize RT beyond nLag window of relevant history */
  J->next  = Jx;     J->prev = J1;     J->n = 0;   J->RT = -2.0 - nLag;  /* initialize list with 3 elements so insertions work.  */
  Jx->next = NULL;  Jx->prev = J;     Jx->n = 0;  Jx->RT = -3.0 - nLag;  N_s[10]=3;  /* count lengths of lists */

  M  = malloc(sizeof(struct MO));                                        N_s[11]=3;
  M1 = malloc(sizeof(struct MO));
  Mx = malloc(sizeof(struct MO));  if (Mx==NULL) { printf("Out of memory for Mx \n");  exit(8); }
  M1->next = M;     M1->prev = NULL;  M1->p = PVmu;  M1->sof = 0;  M1->z = 0;  M1->RT = -1.0 - nLag;  /* initialize M1->p with PVmu  */
  M->next  = Mx;     M->prev = M1;     M->p = PVmu;   M->sof = 0;   M->z = 0;   M->RT = -2.0 - nLag;  /* since GetState() uses M1->p */
  Mx->next = NULL;  Mx->prev = M;     Mx->p = PVmu;  Mx->sof = 0;  Mx->z = 0;  Mx->RT = -3.0 - nLag;  /* to initialize s->w for E(v) */

  tt->again= 0.0;   nextPVj = -log ( RandU ) * Ejump;   /* could be INSIDE j loop, but not necessary */
  
  jj0 += jj;           /* jj0 initialized in "config" file as EVEN NUMBER.  Higher jj0 for LESS EXTREME Nreset.  jj0 checked in WriteFiles since s->Eoptimal not defined until 2nd jj */

  while (1) {          /* jj loop.  initial  jj & Bt set above, in or before READ.   jj++ now in WriteFiles() */
    for(l=0; l<8; l++)  for(p=0; p<nRT;  p++)  vminmax[l][p]= 0;
    for(p=0; p<nP+2; p++) {
      Bcount[p] = 0;   /* count Bids      */  /* ALL THESE COUNTS UPDATED ONLY WHEN TRADER TAKES ACTION.      */
      Acount[p] = 0;   /* count Asks      */  /* IN CONTINUOUS TIME MODEL, THIS MAY NOT BE SAME AS UPDATING   */
      Tcount[p][0]=0;  /* count mkt sells */  /* EACH PASSAGE OF A UNIT TIME, WHATEVER THAT IS DEFINED TO BE. */
      Tcount[p][1]=0;  /* count mkt buys  */
      Spread[p] = 0;   /* count Spreads   */
      SpreaF[p] = 0;   /* Fringe Spreads  */
      Bid[p]    = 0;   /* count high bids */  /* NOTE: Bid/Ask measured AFTER drops PRIOR to new trader */
      Ask[p]    = 0;   /* count low  asks */  /* Bid[0] = count of high bids at tick 0.  Bid[nP] = count of NO Bids on book. */
    }
    Tcount[nPx1][0]=0; /* Market Fringe Sells at -1.              OLD  Tcount[nP]   */  /* NO LONGER PERMITTED */
    Tcount[nPx1][1]=0; /* Market Fringe Buys  at nP.              OLD  Tcount[nPx1] */  /* NO LONGER PERMITTED */
    Tcount[nP][0]= 0;  /* Limit Sell picked off by Fringe at -1.  OLD  Tcount[nP+2] */  /* Expanded Bt (high nP) should make */
    Tcount[nP][1]= 0;  /* Limit Buy  picked off by Fringe at nP.  OLD  Tcount[nP+3] */  /*     pick-offs by Fringe very rare */

    Spread[nP+2]= 0;   /* trade weighted average effective spread */
    SpreaF[nP+2]= 0;   /* trade weighted average  Fringe   spread */
    RealSprd = 0.0;    /* trade weighted average realized  spread TAU periods ahead */
    for (l=0; l<nT; l++) {       /* type l */
      for (m=0; m<nPVmax; m++) { /*  PV  m */
	TC[l][m][0][0] = 0;  /* count mkt orders when spread NA due to missing bid or ask */
	TC[l][m][0][1] = 0;  /* count mkt orders when spread= 1 */
	TC[l][m][0][2] = 0;  /* count mkt orders when spread= 2 */
	TC[l][m][0][3] = 0;  /* count mkt orders when spread= 3 */
	TC[l][m][0][4] = 0;  /* count mkt orders when spread> 3 */
	TC[l][m][1][0] = 0;  /* TransCosts when spread NA due to missing bid or ask */
	TC[l][m][1][1] = 0;  /* TransCosts when spread= 1 */
	TC[l][m][1][2] = 0;  /* TransCosts when spread= 2 */  /* TC[][][ 1 ][] / TC[][][ 0 ][] is mean TC by type, pv, spread */
	TC[l][m][1][3] = 0;  /* TransCosts when spread= 3 */
	TC[l][m][1][4] = 0;  /* TransCosts when spread< 3 */

	CS[l][m][0]  = 0.0;  /* discounted CS limit orders */
	CS[l][m][1]  = 0.0;  /* discounted CS market order */
	CS[l][m][2]  = 0.0;  /* sum since0 lim  */
	CS[l][m][3]  = 0.0;  /* sum since0 mkt  */
	CS[l][m][4]  = 0.0;  /* UNdiscounted CS limit orders, including trembles (hard to exclude trembles for LARGE traders) */
	CS[l][m][5]  = 0.0;  /* UNdiscounted CS market order, including trembles */
	CS[l][m][6]  = 0.0;  /* transact costs  */
	CS[l][m][7]  = 0.0;  /* discounted CS traders NEVER tremble */
	CS[l][m][8]  = 0.0;  /* discounted CS traders  EVER tremble */
	CS[l][m][9]  = 0.0;  /* discounted CS traders NEVER tremble SQUARED, for computing Var(CS) */
	CS[l][m][10] = 0.0;  /* |E(v)|         incremented each nJ decision, where E(v) is t->Ev, gives degree of usefullness of conditioning variables */
	CS[l][m][11] = 0.0;  /* |E(v) + v|     incremented each nJ decision, where E(v) is t->Ev, gives degree of uncertainty  */
	CS[l][m][12] = 0.0;  /*  E(v) + v      incremented each nJ decision, where E(v) is t->Ev, should be ZERO, given rational expectations */
	CS[l][m][13] = 0.0;  /* sum since0, NEVER tremble */
	CS[l][m][14] = 0.0;  /* sum  p-E(v)  pre-tremble action upon  1st arrival, sells converted to buys --> p-E(v) high --> aggressive */
	CS[l][m][15] = 0.0;  /* count for dividing [14]   */
	CS[l][m][16] = 0.0;  /* sum  p-E(v)  pre-tremble action any lim/mkt order, sells converted to buys --> p-E(v) high --> aggressive */
	CS[l][m][17] = 0.0;  /* count for dividing [15]   */

	byT[l][m][0] = 0;    /* count traders          */
	byT[l][m][1] = 0;    /* count any decision     */  /* [1] = [2]+[3]+[4]+[5] */
	byT[l][m][2] = 0;    /* count No Action        */
	byT[l][m][3] = 0;    /* count non_orders       */
	byT[l][m][4] = 0;    /* count market orders    */  /* post trembles */
	byT[l][m][5] = 0;    /* count submitted limits */  /* post trembles */
	byT[l][m][6] = 0;    /* count keep same order  */
	byT[l][m][7] = 0;    /* count  replace  order  */
	byT[l][m][8] = 0;    /* count times  CS<0      */
	byT[l][m][9] = 0;    /* count executed limits  */
	byT[l][m][10]= 0;    /* count times tremble    */
	byT[l][m][11]= 0;    /* count times v-v_ < .01 */
	byT[l][m][12]= 0;    /* count times v-v_ < .05 */
	byT[l][m][13]= 0;    /* count traders NEVER tremble */
	byT[l][m][14]= 0;    /* count traders  EVER tremble */
	byT[l][m][15]= 0;    /* count mkt buy  orders  */
	byT[l][m][16]= 0;    /* count mkt sell orders  */
	byT[l][m][17]= 0;    /* count lim buy  orders  */
	byT[l][m][18]= 0;    /* count lim sell orders  */
	byT[l][m][19]= 0;    /* mkt orders when arrive */  /* byT[][][ 20 ] AVAILABLE for use */
	for(p=0;p<nPlim;p++) {
	  byT[l][m][p+21] = 0;            /* old N_LH : count aggressiveness of limitorders (excluding-Trembles).  sum() should = [5] only if NO TREMBLES */
	  byT[l][m][p+21+  nPlim] = 0;    /* count execution, by order aggressiveness */
	  byT[l][m][p+21+2*nPlim] = 0; }  /* sum  p - v     , by order aggressiveness */

	VV[l][m][0]  = 0.0;  /* s->w of 1st best, Mkt order is 1st.   VV is for tracking 1st best - 2nd best */
	VV[l][m][1]  = 0.0;  /* s->w of 2nd best, Mkt order is 1st */
	VV[l][m][2]  = 0.0;  /* count             Mkt order is 1st */
	VV[l][m][3]  = 0.0;  /* s->w of 1st best, Lim order is 1st, Mkt is 2nd */
	VV[l][m][4]  = 0.0;  /* s->w of 2nd best, Lim order is 1st, Mkt is 2nd */
	VV[l][m][5]  = 0.0;  /* count             Lim order is 1st, Mkt is 2nd */
	VV[l][m][6]  = 0.0;  /* s->w of 1st best, Lim order is 1st, Lim is 2nd */
	VV[l][m][7]  = 0.0;  /* s->w of 2nd best, Lim order is 1st, Lim is 2nd */
	VV[l][m][8]  = 0.0;  /* count             Lim order is 1st, Lim is 2nd */
	VV[l][m][9]  = 0.0;  /* s->w of 1st best, non-order is 1st, mkt OR Lim 2nd, new tt only */
	VV[l][m][10] = 0.0;  /* s->w of 2nd best, non-order is 1st, mkt OR Lim 2nd, new tt only */
	VV[l][m][11] = 0.0;  /* count             non-order is 1st, mkt OR Lim 2nd, new tt only */

      } }
    for(k=0;k<=zmax;k++) {
      zRT[0][k] = 0.0;       /* report mean and variance of RT between mkt orders */
      zRT[1][k] = 0.0;   }
    for(p=0;p<nPx1;p++)
      for(k=0;k<=zmax;k++) {
	zpq[0][p][k] = 0;    /* count times quote=p encountered when tz->q = k */
	zpq[1][p][k] = 0; }  /* count mkt orders at  price  p   when tz->q = k */
    Jcount[0] = 0;     /* count out-of-bound drops when PV jumps up   */
    Jcount[1] = 0;     /* count out-of-bound drops when PV jumps down */
    Jcount[2] = 0;     /* count of jumps up   */
    Jcount[3] = 0;     /* count of jumps down */

    ABAS[0][0]= 0;  ABAS[0][1]= 0;  ABAS[0][2]= 0;  ABAS[0][3]= 0;  /* col 1 is prev period AB, 2 is AB count, 3 is AB|AB, 4 is AS|AB */
    ABAS[1][0]= 0;  ABAS[1][1]= 0;  ABAS[1][2]= 0;  ABAS[1][3]= 0;  /* col 1 is prev period AS, 2 is AS count, 3 is AB|AS, 4 is AS|AS */
    for(p=0; p<10; p++)   NScount[p]=0;              /* 0:9 are multishare counts */
    for(p=0; p<TAU+2; p++) { Ptau[p]=0; Ttau[p]=0;}  /* initialize transaction history used for realized spread TAU periods ahead */

    if (mJ>0) {        /* if (outM != NULL) write simulated Bt and actions for each period */
      /* if ((jj % 2) == 0) outM = fopen(TMP "GetEq_nJsim.even", "wb");
	 else               outM = fopen(TMP "GetEq_nJsim.odd",  "wb"); */
      outM = fopen(TMP "mJsim",  "wb");  /* since exit(1) after 1st nJ sims */
      if ( outM== NULL) { printf("\n HEY:  outM==NULL!!  You probably need to create /TMP/GetEq/ ... aborting ...\n\n");  exit(9); }
    } else outM = NULL;

    if (outM!=NULL)  outTQ = fopen(TMP "mJ_TQ", "w");  /* create ascii output file of time series:  realtime, net v jumps, bid, ask, last trans.price, cum volume */
    else outTQ = NULL;

    if ( out_2nd_best > 0) {
      if ((jj % 2) == 0) out2 = fopen(TMP "2nd_best.even", "w");
      else               out2 = fopen(TMP "2nd_best.odd",  "w");
      if ( out2== NULL) { printf("\n HEY:  out2==NULL!!  You probably need to create /TMP/GetEq/ ... aborting ...\n\n");  exit(9); }
    } else out2 = NULL;
    
#ifdef Use_outFringe
    if ((jj % 2) == 0) outFringe = fopen(TMP "GetEq_Fringe.even", "w");
    else               outFringe = fopen(TMP "GetEq_Fringe.odd",  "w");
#endif      
    /*
      if ((jj % 2) == 0) outDrop = fopen(TMP "GetEq_Drop.even", "w");
      else               outDrop = fopen(TMP "GetEq_Drop.odd",  "w");
      if ((jj % 2) == 0) outNoAction = fopen(TMP "GetEq_NoAction.even", "w");
      else               outNoAction = fopen(TMP "GetEq_NoAction.odd",  "w");
      if ((jj % 2) == 0) outN0 = fopen(TMP "GetEq_N0.even", "w");
      else               outN0 = fopen(TMP "GetEq_N0.odd",  "w");
    */
    

    /* REMOVED all  Ee_jLOOP  stuff from this version.  See GetEq_continuous_code.c  for last version in which it existed (though never used) */
    /* Last version for which Ee_jLOOP = 1 was actually used is   GetEq_NoTypes_code.c  */

    j=0;  qmax=0;  qave=0.0;  non_ave=0.0;  for(p=0;p<11;p++) sNew[p]=0;
    N_s[0]=0; N_s[1]=0;  /* N_s[2]=0 */     for(p=3;p<10;p++)  N_s[p]=0; /* skip 10,11 */  for(p=12;p<24;p++) N_s[p]=0; 
    for(p=0;p<23;p++) Ntremble[p]=0;
    clock1= clock();  realtime= 0.0;   /* NO NEED TO (since initialized before jj loop) : tt->again= 0.0;   nextPVj = -log ( RandU ) * Ejump; */

    while (j<nJ){    /* ----------------------  j loop starts here  --------------------- */

      /* check that #t in traders[] same as tracked by Bt[] */
      for (p=0; p<nP; p++) {
	t = traders[p];   k=0;
	while (t!=NULL) { k++;  t= t->next; }
	if (1-(k==Bt[p] || k==-Bt[p])) { printf("k=%d  p=%d  Bt[p]=%d \n",k,p,Bt[p]);  DEBUG = 2; } }

      if (DEBUG) {
	printf("tick  Bt   #Traders[p],  j=%u  before finding next tj\n",j);
	for (p=0; p<nP; p++) {
	  t = traders[p];   k=0;
	  while (t!=NULL) { k++; t= t->next;}
	  if(Bt[p] || k) printf(" %3d:%3d %3d \n", p, Bt[p],k); } }

      /* OPTIONAL:  track max book depth */
      k=0; i=0;    for(p=0;p<nP;p++)  { if(Bt[p]<0) k+= Bt[p];  if(Bt[p]>0) i+= Bt[p]; }
      N_s[4] = max( N_s[4], (unsigned int)i-k);     i= max(i,-k);
      N_s[3] = max( N_s[3], (unsigned int)i  );

      if(tz->q==0) tz->again = tt->again + 9.9;  /* ensures won't choose tz to be next */
      tj=tt;  tj->p = -9;                  /* == -9 only to enter loop.  upon finding actual next trader, tj->p >=0 so no infinite loop. */
      while ( tj->p <0 ) {                 /* tj->p will be -1 if next tj trader found below is dropped/executed by Fringe after v jump. */

	if(tz->again < tt->again)  tj=tz;  else tj=tt;  /* initially specify tz(LARGE) or tt(NEW) as next trader.  Returning limitorders may supplant. */
	tj->p = 0; /* both tz->p & tt->p safe to use.    while() terminates UNLESS tj->p set to -1 by FRINGE drop/execution. */

	bid1= nP;   while(--bid1 > -1 && Bt[bid1]<=0) { }  Bt[nPx1]= bid1;  /* high bid.  = -1 if no bids */
	ask1= bid1; while(++ask1 < nP && Bt[ask1]>=0) { }  Bt[nP]  = ask1;  /* low  ask.  = nP if no asks */
#if 0   /* HEY:  why did I write to outTQ PRIOR to jumps realizations???  commenting out for now ... */
	if (outTQ!=NULL && j<mJ) /* RT, bid, ask, last trans.price, cum volume(nonfringe), cum jumps up, cum jumps down */
	  fprintf(outTQ,"%5.3f %d %d %d %u %u %u\n", realtime, bid1, ask1, M1->p, Acount[nP]+Bcount[nP], Jcount[2], Jcount[3]);
#endif  /* in above line, M1->p is lastP NOT YET shifted by v jumps */

	if (DEBUG) {printf("j=%u before   jumps: bid~ask= %d %d ", j, bid1, ask1); for(p=0;p<nP;p++) if(Bt[p])printf(" B[%2d]= %3d,",p,Bt[p]); printf("\n");}

	/* if (DEBUG) printf("j=%u nextPVj=%5.2f  tj~tt~tz->again= %5.2f %5.2f %5.2f\n", j,nextPVj,tj->again,tt->again,tz->again); */

	for (p=0; p<nPx1; p++) {                    /* Compare tj->again with t->again of all traders in book & NON-ORDERS at [nP] */
	  k=0;  t = traders[p];
	  while(t != NULL) {                        /* tj points to trader who moves next */
	    k++;  if( t->q != k && p<nP) { printf("\n\n t->q does not match position in list at traders[p], p= %d.  abort.\n\n",p); exit(8);}
	    if (t->again < tj->again) tj = t;       /* printf("p= %d  t->again= %5.2f  tj->again= %5.2f\n",p,t->again,tj->again); */
	    /* if(traders[nP]!=NULL) printf("p= %2d  t= %p  again= %8.3f  since= %8.3f  since0= %8.3f  rtime0= %8.1f  realtime= %8.1f  low= %d\n", p, t, t->again, t->since, t->since0, t->rtime0, realtime,tj==t); */
	    if (CHECK && UPDATE_s0 && t->s0 != t->s) {
	      printf("j=%u  p= %2d  period= %u  since~0: %5.2f %5.2f  s: N~NN= %p %u %u  s0: N~NN= %p %u %u ",
		     j, p, t->period, t->since, t->since0, t->s, t->s->N, t->s->NN, t->s0, t->s0->N, t->s0->NN );
	      for(p1=0;p1<nB;p1++) if(t->s->B[p1]!=t->s0->B[p1]) printf(" B[%d]= %d %d ", p1,t->s->B[p1], t->s0->B[p1]);  printf("\n");  DEBUG=9; }
	    
	    t = t->next; } }
	
	PVj = 0;                                    /* process v jump, while() instead of if() since could have multiple jumps.   */
	while( Ejump>0.0  &&  nextPVj < tj->again){ /* Ejump<0 --> NO JUMPS EVER.   nextPVj < tj->again --> next event is a jump. */
	  u = RandU;                                /* If v goes UP & DOWN before tj->again  then NO Bt change or Fringe Activity.*/
	  if(u > .5) { a1 =  PVjN; Jcount[2]++; }   /* Oct 2004: FIXED BUG in which J->n = PVj (cum jumps in a row) */
	  else       { a1 = -PVjN; Jcount[3]++; }   /*                     instead of J->n = a1, where a1= +/- PVjN */
	  PVj += a1;
	  u = realtime + nextPVj;                   /* RT of this jump (realtime not updated until after all jumps done) */
	  J= Jx; if(u-J->RT <nLag){ J=malloc(sizeof(struct vJ)); if(J==NULL){printf("Out of memory for J\n");exit(8);}  J->prev = Jx;  N_s[10]++;}
	  J->n= a1;  J->RT= u;  Jx= J->prev;  Jx->next= NULL;  J->prev= NULL;  J1->prev= J;  J->next= J1;  J1= J;

	  /* J = J1;  while(J!=NULL) { printf("J==J1~Jx: %d %d J: n~Rt= %2d %9.2f\n", J==J1, J==Jx, J->n, J->RT); J=J->next; } */

	  if(tz->q){ tj=tz; tz->again = nextPVj; }  /* tz moves right after jump.  If tz active never have multi-jump. */
	  
	  u = RandU; u = min(HiU,max(LoU,u));       /* exponential inverse CDF = -ln(1-u), u~U(0,1) so is 1-u */
	  nextPVj += -log(u) * Ejump;               /* += -log(u) since not decrementing tj->again for all traders until trader loop */
	}                                           /* NOTE: bid1, ask1 not used until recomputed from Bt after fringe activity */
	
	u = tj->again;                              /* shift ALL "time untils" by time elapsed: tj->again */
	realtime+= u;
	nextPVj -= u;                               /* important to have used += -log(u) to draw nextPVj   */
	tt->again -= u;  tt->since+= u;             /* next NEW trader arrives in tt->again units realtime */
	tz->again -= u;  tz->since+= u;  tz->since0+= u;  tz->sincei+= u;     /* tz may not be active, but harmless update  */
	for (p=0; p<nPx1; p++) {
	  t = traders[p];
	  while (t != NULL) {
	    t->again  -= u;                         /* t->again becomes 0.0 for tj (which may be tt) */
	    t->since  += u;
	    t->since0 += u;
	    t->sincei += u;
#if Ns0     /* && UPDATE_s0 will always be the case */
	    for (t1=2; t1 <= t->n; t1++) {
	      t->sinceN[t1] += u;                   /* t->n is #actions taken.  First one already recorded in t->since0 */
	      /* printf("j=%6u  p= %2d  t->period=%6u  t= %p  t->sN[%d of %d] = %p \n", j, p, t->period, t, t1, t->n, t->sN[t1]); */
	    }
#endif
	    if (t->again <= 0.0 && t != tj) {
	      printf(" t->again = %9.1e being reset to .00001 since cannot have 2 traders move at same time\n",t->again);
	      t->again = .00001; }
	    
	    if(p<nP) t->p -= PVj;                   /* if v jumps up (PVj>0) then p of each order drops, except NON-ORDERS with p=nP  */
	    t->j += PVj;                            /* t->j tracks  ACTUAL  jumps in v.  written to outM to then get submission price */
	    t = t->next;  
	  } }
	
	M=M1; while(M && realtime-M->RT <RT[p]) { M->p -= PVj;  M=M->next; }  /* make recent trans.prices be relative to current v */
	
	if (outTQ!=NULL && j<mJ)                  /* RT, bid, ask, last trans.price, cum volume(nonfringe), cum jumps up, cum jumps down */
	  fprintf(outTQ,"%5.3f %d %d %d %u %u %u\n", realtime, bid1-PVj, ask1-PVj, M1->p, Acount[nP]+Bcount[nP], Jcount[2], Jcount[3]);
	
	/* process jumps in PV.  Instead of shifting future PV distribution, shift current traders PV */
	/* since t->pv defined as ticks away from the mean, then t->pv = t->pv + jump                 */
	/* but   t->pv is not used for anything beyond the initial action (and tally of cumulative Gains from Trade, via CS)  */
	/* hence the shift in the value of current traders is implemented by tracking the number of jumps that have occurred  */
	/* As with e, rather than tracking the 0/1 execution event, we have the ultimate 0/1's, or in this case PV jumps,     */
	/* propagate through intermediate states by updating current state with random draw from integrand over future states.*/
	/* Bt needs to be shifted left for PV increasing -- a current price at current mean becomes a price 1 tick below mean.*/

	/* Jan 2004 code "NoFringe" modifications expanded Bt to track orders at ticks above/below the ticks at which limit orders may be submitted. */
	/* As such, rarely should orders be picked-off by the fringe, or dropped by becoming only as aggressive as the fringe. */
	/* Nonetheless, I retain the code to process such fringe activity in the next block of code. */

	for(k=0; k<PVj; k++) { /* process each tick of multi-tick jump UP separately.  New Bt[p-1] = old Bt[p], drop old Bt[0] */
	  for(p=0; p<TAU; p++)  Ptau[p]+= -1;  /* Ptau[p] = price TAU-p periods ago */
	  t= traders[0];  
	  while (t != NULL) {
	    if (t->buy) {      /* process out-of-bounds drops by converting to non-order */
	      Jcount[0]++;     /* See GetEq_large_code.c for OLD WAY which assigned payoff of 0 and returned trader to heap */ 
	      /* optional elapsed realtime check */
	      if( fabs(t->since0 + t->rtime0 - realtime) > 1e-5 )
		printf("j= %u  rt= %12.3f  rt0+since0-rt= %10.1e  since0= %8.3f  DROP by jump up\n",j,realtime, t->since0 + t->rtime0 - realtime, t->since0);
	      t->buy= 0;    t->p= nP;    t->q= 1;  /* convert to a non-order and append to START of traders[nP] */
	      tx= t->next;  t->next= traders[nP];  traders[nP]= t;   t= tx;
	    }
	    else {             /* limit sell executes via fringe buyers at tick -1. */
	      s = t->s;
	      Tcount[nP][0]++; /* OLD: Tcount[nP+2]++ */
#ifdef Use_outFringe
	      if (outFringe!=NULL) fprintf(outFringe, "-1 %8.4f %3ld %8.4f\n", t->pv, t->j, -1.0 - t->pv);

	      if (  0  &&  j<mJ) {                   /* must update if want FRINGE to outM */
		u = (double)j;  fwrite( (char *) &u, 1,8,outM);  /* j is period/trader ID  */
		u = -1.0;       fwrite( (char *) &u, 1,8,outM);  /* fringe market buyer has pv= -1 */
		u = -99.0;      fwrite( (char *) &u, 1,8,outM);  fwrite( (char *) &u, 1,8,outM); /* a1, a2 filler   */
		u = (double)Jcount[2]-(double)Jcount[3];         fwrite( (char *) &u, 1,8,outM); /* cum jumps       */
		u = -1.0;                      fwrite( (char *) &u, 1,8,outM);  /* report ask of -1 */
		u = -1.0;                      fwrite( (char *) &u, 1,8,outM);  /* report bid of -1 */
		u = -99.0; for(p=0;p<nP+4;p++) fwrite( (char *) &u, 1,8,outM);  /* filler: A-depth, B-depth, #sells, #buys, Bt each p */
		u = -1.0;                      fwrite( (char *) &u, 1,8,outM);  /* price  */
		u =  1.0;                      fwrite( (char *) &u, 1,8,outM);  /* volume */
		u = t->pv;                     fwrite( (char *) &u, 1,8,outM);  /* initial pv */
		u = (double)t->j;              fwrite( (char *) &u, 1,8,outM);  /* cum jumps  */
		u = -99.0; for (p=0; p<4; p++) fwrite( (char *) &u, 1,8,outM);  /* filler last46 columns */
	      }
#endif
	      u = -1.0 - t->pv;
	      v1= u * exp(rhoT[t->type] * t->since0) /* - cT[t->type] * t->since0 */ ;
	      if (j<mJ)  printf("\n\n HEY: market buy by Fringe at j= %u is NOT being written to outM\n\n",j);

	      CS[ t->type][t->pvtype][0]+= v1;
	      CS[ t->type][t->pvtype][2]+= t->since0;
	      byT[t->type][t->pvtype][9]++;
	      CS[ t->type][t->pvtype][7 +(t->tremble >0)]+= v1;  if(t->tremble==0) { CS[ t->type][t->pvtype][9] += v1*v1; CS[ t->type][t->pvtype][13] += t->since0; }
	      byT[t->type][t->pvtype][13+(t->tremble >0)]++;

	      if(u<0.0) byT[t->type][t->pvtype][8]++;      /* count pick-offs */

	      /* optional elapsed realtime check */
	      if( fabs(t->since0 + t->rtime0 - realtime) > 1e-5 )
		printf("j= %u  rt= %12.3f  rt0+since0-rt= %10.1e  since0= %8.3f  Exec by jump up\n",j,realtime, t->since0 + t->rtime0 - realtime, t->since0);

	      /* v1 = u * exp(rhoT[t->type] * t->since0) - cT[t->type] * t->since0; */
	      t->s0->Ew += v1;     /* cum  realized value    */
	      t->s0->w2 += v1*v1 ; /* cum (realized value)^2 */
	      t->s0->NN++;         /* update N when leave state not when t arrived at s */
	      if (UPDATE_PVT[t->type][t->pvtype]) { s->w += ( exp(rhoT[t->type] * t->since) * u /* - cT[t->type] * t->since */ - s->w ) / ++s->N;    N_s[24]++; }
	      
	      t->p = -1; tx = t;  t = t->next;   tx->s=NULL; tx->s0=NULL; /* t->p is flag for tj dropped/exec */
	      tx->next = traders[nPx1];  traders[nPx1] = tx;  tx = NULL;  /* tx to head of trader memory heap */
	    }  /* if bought by fringe */
	  }    /* while t!= NULL */
	  for (p=1; p<nP; p++) {
	    traders[p-1] = traders[p]; Bt[ p-1]= Bt[p]; } /* shift Bt and traders[]. Do NOT shift Non-Orders at nP. */
	  traders[ nP_1] = NULL;       Bt[nP_1]= 0;
	} /* end loop over ticks of multi-tick jump UP */
	
	for(k=0; k< -PVj; k++) { /* process each tick of multi-tick jump DOWN separately. New Bt[p]=old Bt[p-1], old Bt[nP-1] drop */
	  for(p=0; p<TAU; p++)  Ptau[p]+= 1;  /* Ptau[p] = price TAU-p periods ago */
	  t= traders[nP_1];  
	  while (t != NULL) {
	    s = t->s;          /* CAREFUL:  if  SYMM  then  s  is for a LIMIT SELL  */
	    if (t->buy) {      /* limit buy executes via fringe buyers at tick nP   */
	      Tcount[nP][1]++; /* OLD: Tcount[nP+3]++ */
#ifdef Use_outFringe
	      if (outFringe!=NULL) fprintf(outFringe, "%2d %8.4f %3ld %8.4f\n", nP, t->pv, t->j, t->pv - nP);

	      if (  0  &&  j<mJ) {                   /* must update if want FRINGE to outM */
		u = (double)j;  fwrite( (char *) &u, 1,8,outM);  /* j is period/trader ID  */
		u = (double)nP; fwrite( (char *) &u, 1,8,outM);  /* fringe market seller has pv= nP */
		u = -99.0;      fwrite( (char *) &u, 1,8,outM);  fwrite( (char *) &u, 1,8,outM); /* a1, a2 filler   */
		u = (double)Jcount[2]-(double)Jcount[3];         fwrite( (char *) &u, 1,8,outM); /* cum jumps       */
		u = (double)nP;                fwrite( (char *) &u, 1,8,outM);  /* report ask of nP */
		u = (double)nP;                fwrite( (char *) &u, 1,8,outM);  /* report bid of nP */
		u = -99.0; for(p=0;p<nP+4;p++) fwrite( (char *) &u, 1,8,outM);  /* filler: A-depth, B-depth, #sells, #buys, Bt each p */
		u = (double)nP;                fwrite( (char *) &u, 1,8,outM);  /* price  */
		u = -1.0;                      fwrite( (char *) &u, 1,8,outM);  /* volume */
		u = t->pv;                     fwrite( (char *) &u, 1,8,outM);  /* initial pv */
		u = (double)t->j;              fwrite( (char *) &u, 1,8,outM);  /* cum jumps  */
		u = -99.0; for (p=0; p<4; p++) fwrite( (char *) &u, 1,8,outM);  /* filler last 4 columns */
	      }
#endif
	      u = t->pv - nP;
	      v1= u * exp(rhoT[t->type] * t->since0) /* - cT[t->type] * t->since0 */ ;
	      if (j<mJ)  printf("\n\n HEY: market sell by Fringe at j= %u is NOT being written to outM\n\n",j);

	      CS[ t->type][t->pvtype][0]+= v1;
	      CS[ t->type][t->pvtype][2]+= t->since0;
	      byT[t->type][t->pvtype][9]++;
	      CS[ t->type][t->pvtype][7 +(t->tremble >0)]+= v1;   if(t->tremble==0) { CS[ t->type][t->pvtype][9] += v1*v1;  CS[ t->type][t->pvtype][13] += t->since0; }
	      byT[t->type][t->pvtype][13+(t->tremble >0)]++;

	      if(u<0.0) byT[t->type][t->pvtype][8]++;    /* count pick-offs */

	      /* optional elapsed realtime check */
	      if( fabs(t->since0 + t->rtime0 - realtime) > 1e-5 )
		printf("j= %u  rt= %12.3f  rt0+since0-rt= %10.1e  since0= %8.3f  Exec by jump down\n",j,realtime, t->since0 + t->rtime0 - realtime, t->since0);
	      /* v1 = u * exp(rhoT[t->type] * t->since0) - cT[t->type] * t->since0; */
	      t->s0->Ew += v1;     /* cum  realized value    */
	      t->s0->w2 += v1*v1 ; /* cum (realized value)^2 */
	      t->s0->NN++;
	      if (UPDATE_PVT[t->type][t->pvtype]) { s->w += ( exp(rhoT[t->type] * t->since) * u /* - cT[t->type] * t->since */ - s->w ) / ++s->N;    N_s[24]++; }
	      
	      t->p = -1; tx = t;  t = t->next;  tx->s=NULL; tx->s0=NULL;  /* t->p is flag for tj dropped/exec */
	      tx->next = traders[nPx1];  traders[nPx1] = tx;  tx = NULL;  /* tx to head of trader memory heap */
	    }
	    else {           /* process out-of-bounds drops by converting to non-order */
	      Jcount[1]++;   /* See GetEq_large_code.c for OLD WAY which assigned payoff of 0 and returned trader to heap */ 
	      /* optional elapsed realtime check */
	      if( fabs(t->since0 + t->rtime0 - realtime) > 1e-5 )
		printf("j= %u  rt= %12.3f  rt0+since0-rt= %10.1e  since0= %8.3f  DROP by jump down\n",j,realtime, t->since0 + t->rtime0 - realtime, t->since0);
	      t->buy= 0;  t->p= nP;  t->q= 1;   /* convert to a non-order and append to START of traders[nP] */
	      tx= t->next;  t->next= traders[nP];  traders[nP]= t;   t= tx;
	    }
	  }  /* end while t != NULL */
	  for (p=nP_1; p>0; p--) {
	    traders[p] = traders[p-1];  Bt[p]= Bt[p-1]; } /* shift Bt and traders[]. Do NOT shift Non-Orders at nP. */
	  traders[0] = NULL;            Bt[0]= 0;
	} /* end loop over ticks of multi-tick jump */
	
	for(p=0;p<nP;p++) if(Bt[p]<0) qave -= Bt[p]/nJ; else qave += Bt[p]/nJ;   /* qave is sum of queues at all prices */
	t= traders[nP];  h=0;  while(t!=NULL){ h++;  t=t->next;}   N_s[6]= max(N_s[6],h);   non_ave += (double)h/nJ;

	/* could probably simply truncate bid,ask to be within 0,nP-1 to get correct bid/ask after fringe drops/executions */
	/*
	  bid1 = max(-1,bid1); bid1 = min(bid1,nP-1);
	  ask1 = min( 0,ask1); ask1 = max(ask1,nP);       OR could simply recompute from Bt[], as done in next line
	*/

	bid1= nP;   while(--bid1 > -1 && Bt[bid1]<=0) { }  Bt[nPx1]= bid1;  /* high bid.  = -1 if no bids */
	ask1= bid1; while(++ask1 < nP && Bt[ask1]>=0) { }  Bt[nP]  = ask1;  /* low  ask.  = nP if no asks */
	
	if (DEBUG ) {printf("j=%u after %2d jumps: bid~ask= %d %d  tj= %p  p= %2d  new= %d ", j, PVj, bid1, ask1, tj, tj->p, tj==tt);  for(p=0;p<nP;p++) if(Bt[p])printf(" B[%2d]= %3d,",p,Bt[p]); printf("\n\n");}

      } /* end: while( tj->p >=0 && tj->p <nP ) : in case tj was dropped/executed by Fringe, and need another tj */

      Ask[ask1]++;  if(bid1 == -1)    Bid[nP]++;            else  Bid[bid1]++;   /* Bid, Ask counts */
      if(bid1 != -1 && ask1 != nP)    Spread[ask1-bid1]++;  else Spread[nP]++;   /* Spread count, non-fringe only */
      /* */                           SpreaF[ask1-bid1]++;                       /* Spread count, with Fringe Liquidity */

      tm->v = 0; J = J1;        /* printf("j= %u  J: n~RT= %2d  %9.2f tm->v= %3d\n", j, J->n, J->RT, tm->v -J->n);  */
      for(p=0; p<nRT; p++) {    /* build global Ht[0][] history of jumps within each RT bin.  SAME for ALL tt,tz,tj */
	Ht[0][p]= 0;            /* INFORMED tz,tt,tj condition on Ht[0][] (in GetState) if OTHER traders uninformed */
	while(J && realtime - J->RT < RT[p]) {
	  Ht[0][p] -= J->n;  tm->v -= J->n;    /* tm->v stores last observed v IF trader (tz,tt,tj) has vlag = nLag */
	  J = J->next; } }

      for(p=0; p<nRT; p++) {
	if(Ht[0][p] > H_jumps) vminmax[2][p]++;
	if(Ht[0][p] <-H_jumps) vminmax[3][p]++;  }
      vminmax[0][0] = min( vminmax[0][0], tm->v);  /* could be for each nRT, but instead want total max over nLag */
      vminmax[1][0] = max( vminmax[1][0], tm->v);  /* if M1->p = PVmu and v jumped up since last observed (ie, tm->v <0) then last trans.price is above last observed v */
      if(M1->p < H_minp) vminmax[4][0]++;          /* [4] counts last price < H_minp, for   informed traders */
      if(M1->p > H_maxp) vminmax[5][0]++;          /* [5] counts last price > H_maxp, for   informed traders */
      if(M1->p - tm->v < H_minp) vminmax[6][0]++;  /* [6] counts last price < H_minp, for uninformed traders */
      if(M1->p - tm->v > H_maxp) vminmax[7][0]++;  /* [7] counts last price > H_maxp, for uninformed traders */
      if(tm->v > nP || tm->v < -nP ) { printf(" huge tm->v (ie, last observed v) = %d ... aborting\n", tm->v);  exit(8); }
      
      
      /* check that #t in traders[] same as tracked by Bt[] */
      /*
      if (DEBUG || 1) {
	for (p=0; p<nP; p++) {
	  t = traders[p];   k=0;
	  while (t!=NULL) { k++;  t= t->next; }
	  if (1-(k==Bt[p] || k==-Bt[p])) { printf("k=%d  p=%d  Bt[p]=%d \n",k,p,Bt[p]);  DEBUG = 2; }
	} 
	printf("tick  Bt   #Traders[p],  j=%u  after  finding next tj with tj->p,q = %d %d\n",j,tj->p, tj->q);
	for (p=0; p<nP; p++) {
	  t = traders[p];   k=0;
	  while (t!=NULL) { k++; t= t->next;}
	  if( Bt[p] || k)  printf(" %3d:%3d %3d \n", p, Bt[p],k);
	  }
      } */

      
      /* Currently, tz code finds optimal #mkt orders to submit this instant        */
      /* Alternatively, could merely check whether ONE order should be submitted,   */
      /* and then set tz->again = 0.0.  If optimal action is to NOT submit an order */
      /* then set tz->again to be time of next "passive" change in state, as code   */
      /* currently does after implementation of a1 mkt orders.  For now leave as-is.*/
      
      if (tj == tz) {         /* next trader to move is LARGE guy : tz, who still has tz->q shares of the original tz->z */

	printf("\n\n  HEY:  must modify LARGE tz trader code to account for NEW GetState and New w0sell[][][]  ... aborting... \n\n");  return(8);

	printf("\n\n  HEY:  must add CS[][][14,15] stuff to LARGE tz trader code... aborting... \n\n");  return(8);

	printf("\n\n  HEY:  must N_s[23] count of existing states used for decisionmaking... aborting... \n\n");  exit(8);


	for(p=0;p<=nPx1;p++)  Bz[p] = Bt[p];  /* since will need to restore Bt */
	byT[tz->type][tz->pvtype][1]++;       /* count #decisions of any sort, by trader,PV type */

	if (tz->s0==NULL) tz->CSu = 0.0;      /* tz new. initialize UNdiscounted CS */

	if(tz->vlag) {        /* uninformed about v --> condition separately on OWN history (Hz) & other history (Ht) */
	  M = M1;
	  for(p=0; p<nRT; p++) {    /* build global Ht[][], Hz[][] for uninformed tz to condition on, except Ht[0] jump history already set */
	    Ht[1][p]= 0;  Ht[2][p]= 0;  Ht[3][p]= 0;   /* [1]=last price, [2]= cum net buys, [3]= last price signed order (0 if NONE this RT bin) */
	    Hz[1][p]= 0;  Hz[2][p]= 0;  Hz[3][p]= 0;
	    while(M!=NULL && realtime - M->RT < RT[p]) {  
	      if(M->z){ Hz[2][p]+= M->sof;  if(Hz[3][p]==0){ Hz[1][p]= M->p;  Hz[3][p]= M->sof; } }
	      else    { Ht[2][p]+= M->sof;  if(Ht[3][p]==0){ Ht[1][p]= M->p;  Ht[3][p]= M->sof; } }
	      M = M->next; } }  /* [1,3] use only 1st transaction within each RT bin since M list sorts orders starting w/ most recent.*/
	  
          if( UPDATE_mkt[tz->type] ) InsertNewState = 'y';      /* insert new states */
	  else                       InsertNewState = 'n';      /* unless beliefs fixed for this type */
	  tm->z    = tz->z;
	  tm->type = tz->type;
	  tm->tB   = tz->tB;
	  tm->tH   = tz->tH;
	  s = GetState(tm,Bt);                               /* update large trader  E(v) beliefs */
	  if( UPDATE_mkt[tm->type] /* && s!=S */ )           /* InsertNewState='n' -->   if(s!=S) */ 
	    s->w += ( tm->v - s->w ) / ++s->N ;    
	  s->NN++;  s->Ew -=  tm->v ;  s->w2 +=  tm->v * tm->v;        /* Ew -= same as += -tm->v */
	  
	  if(UPDATE_mkt[nT]) tz->Ev = s->w;       /* used below to evaluate payoffs to mkt orders */
	  else               tz->Ev = 0.0;        /* not enough informed traders for inform. Ht[] */
	  tz->v  = tm->v;       /* used below to evaluate cont.values returned by GetState(tz,Bz) */
	  
	  if(N0_v==0 && tm->tH && fabs(s->w + tm->v)>1e-7) printf("j=%u  tz->Ev,v= %5.2f %3d  s->N= %u tm->q= %d  S= %d\n",j,s->w,tm->v,s->N,tm->q,s==S);
	}
	else {               /* tz knows v, so only condition on TOTAL history (Ht) inclusive of OWN history --> Hz = zeros */
	  tz->Ev = 0.0;
	  tz->v  = 0;
	  M = M1;
	  for(p=0; p<nRT; p++) {  /* build global Ht[][] for informed tz to condition on, except Ht[0] jump history already set */
	    Ht[1][p]= 0;  Ht[2][p]= 0;  Ht[3][p]= 0;   /* [1]=last price, [2]= cum net buys, [3]= last price signed order (0 if NONE this RT bin) */
	    Hz[1][p]= 0;  Hz[2][p]= 0;  Hz[3][p]= 0;   /* Hz ignored in GetState if large t->vlag==0, but zero out to be safe */
	    while(M!=NULL && realtime - M->RT < RT[p]) {
	      Ht[2][p]+= M->sof;  if(Ht[3][p]==0){ Ht[1][p]= M->p;  Ht[3][p]= M->sof; }
	      M = M->next; } }    /* [1,3] use only 1st transaction within each RT bin since M list sorts orders starting w/ most recent.*/
	}

	tz->p  = nP;             /* p=nP avoids GetState() from treating these states as limitorders (w/out having to check t->z) */
	tm->CS = tz->pv + tz->Ev + tz->v;  /* E(adj. pv), used freq'ly below, so do once.  tm->CS used temporarily */
	InsertNewState = 'n';
	s = GetState(tz,Bz);     /* get value of submitting NO orders       */
	v = s->w;  tm->s = s;    /* tm->s stores cont state of best action  */
	a1= 0;                   /* a1 will be optimal #shares to buy/sell  */
	u = 0.0;                 /* u is cum value of submitted  mkt orders */
	q1= tz->q;               /* tz->q-- as evaluate various #mkt orders */
	v0= 0.0;                 /* v0 is E(CS) from a1. If v0>0, a_ = 1    */
	a_= 0;                   /* a_ = 1 --> okay to tremble to a1++      */

	/* printf("j=%u tz: pv= %5.2f  q= %2d  bid~ask= %2d %2d  s->w= %6.3f Ht[1][0]= %2d \n", j, tz->pv, tz->q,Bt[nPx1],Bt[nP],s->w, Ht[1][0]); */
	
	if(tz->pv < PVmu) {      /* First Cut: assume only sell if pv<PVmu, only buy if pv>PVmu */
	  t1 = Bz[nPx1];         /* sell at bid = Bz[nPx1] */
	  while(tz->q && t1>=0){ /* t1 = -1 if NO BID */
	    tz->q--;             /* shares togo, AFTER this mkt order */
	    v0= t1 - tm->CS;     /* tm->CS = tz->pv + tm->Ev + tz->v = E(adjusted private value) */
	    u+= v0;              /* cum value of mkt orders, from 1 to q1 (ie, initial tz->q) */
	    Bz[t1]--;            /* remove executed limit from Bz */
	    if(tz->vlag){ Hz[1][0]= t1;  Hz[2][0]--; }  /* temporarily adjust Hz if vlag = 1 */
	    else        { Ht[1][0]= t1;  Ht[2][0]--; }  /* temporarily adjust Ht if vlag = 0 */
	    if(tz->q) { s = GetState(tz,Bz);  v1= u + s->w; }    /* u is value of  (q1-tz->q) mkt orders, s->w is continuation value of shares togo */
	    else      { s = S;                v1= u; }           /* tz->q = 0 --> tz DONE */
	    if(v1 > v){ v =v1;  a1= q1-tz->q; tm->s =s; a_=0; }  /* printf("bid= %2d  a1= %2d  tz->q= %2d  v~u= %6.3f %6.3f s->w= %6.3f\n", t1, a1, tz->q, v,u,s->w); */
	    else      { if(tz->q == q1-a1 -1) a_ = v0>0 ; }      /* a_ = 1 --> okay to tremble to a1++ */
	    /* investigate why NOT selling at bid >= 17 ?? */    /* if(t1 > 16 && a1!= q1-tz->q ) printf("j=%u bid~ask= %2d %2d  tz->q: in~now~opt= %d %d %d  cum_mkt= %7.3f  now~opt: v= %7.3f %7.3f  s->w= %7.3f %7.3f  s=S: %d %d  N: %u %u  NN: %u %u \n",  j,  t1, Bt[nP],  q1, tz->q, q1-a1,  u,  v1, v,  s->w, tm->s->w,  s==S, tm->s==S,  s->N, tm->s->N,  s->NN, tm->s->NN ); */
	    while(t1>=0) { if (Bz[t1]==0) t1--;  else break; }   /* potentially new bid */
	  }                      /* optimal a1 obtained */
	  
	  if(RandU<PrTremble) {  /* tremble by altering a1 +-1.  can always decrease a1 w/out incurring neg. payoff */
	    if(a1==q1) a1--;     /* but must be sure increasing a1 doesn't yield neg. payoff for the marginal share */
	    else {
	      if(a_) {           /* a_ is flag for whether okay to tremble to a1++ */
		if(a1==0) a1=1;  /* can only a1++ */
		else { if(RandU<.5) a1--; else a1++; }}   /* can ++ or --, so randomize */
	      else a1--; }       /* a_ =0 --> only tremble is a1--.  if a1=0 already then a1 becomes <0, which is ONLY failed tremble */
	    if(a1<0) a1=0;       /* must be case that a1-- occurred when a1 already =0 */
	    else { byT[tz->type][tz->pvtype][10]++;  tz->tremble=1;  tm->s=S;  tz->tremble=2; }
	  }                      /* tm->s=S triggers GetState below, but tz->tremble=2 needed by zpq, outM */
	  tz->q = q1 - a1;       /* q1 stored current tz->q. a1 is optimal #shares to buy/sell */

	  if(a1) {
	    M= Mx; if(realtime-M->RT <nLag){ M=malloc(sizeof(struct MO)); if(M==NULL){printf("Out of memory for M\n");exit(8);}  M->prev = Mx;  N_s[11]++;}
                                 Mx= M->prev;  Mx->next= NULL;  M->prev= NULL;  M1->prev= M;  M->next= M1;  M1= M;
	    M1->sof= -a1; M1->z = 1;  M1->RT = realtime;  /* sof = signed order flow,  z=0 for SMALL trader (tt,tj),  1 for LARGE (tz) */
	  }

	  v0 = 0.0;              /* v0 is realized CS from a1 trades          */
	  t1 = Bt[nPx1];         /* Bt not yet altered by tz action           */
	  while(a1) {            /* implement tz's mkt sells, Post-Tremble    */  /* printf("processing a1= %2d  t1= %2d\n",a1,t1); */
	    if(tz->tremble < 2) {
	      k = tz->q+a1;      /* tz->q already reflects a1.  if q=1, and a1 = 2 then processing shares 3,2 */
	      zRT[0][k] += tz->since0;
	      zRT[1][k] += tz->since0 * tz->since0;
	      zpq[0][nP_1-t1][k]++;          /* zpq[0] counts ASKS so flip t1 */  
	      zpq[1][nP_1-t1][k]++; }        /* zpq[1] counts mkt BUY prices  */
#if mJ>0                         /* outM code IDENTICAL for tz buyer & seller */
	    if(j<mJ) {           /* tz outm action */
	      u = (double)j;              fwrite( (char *) &u, 1, 8, outM ); /* 1  */  /* current "period"        */
	      u = (double)tz->period;     fwrite( (char *) &u, 1, 8, outM );           /* period   1st arrived    */
	      fwrite(                     (char *)&tz->rtime0, 1, 8, outM );           /* realtime 1st arrived    */
	      fwrite(                     (char *)&realtime,   1, 8, outM );           /* realtime current        */
	      fwrite(                     (char *)&tz->since,  1, 8, outM ); /* 5  */  /* time since prev action, 0 if new arrival (could change to time since last new */
	      fwrite(                     (char *)&tz->pv,     1, 8, outM );           /* private value           */
	      u = (double)tz->v;          fwrite( (char *) &u, 1, 8, outM);            /* last observed v */
	      u = (double)tz->type;       fwrite( (char *) &u, 1, 8, outM);
	      u = (double)tz->tB;         fwrite( (char *) &u, 1, 8, outM);
	      u = (double)tz->vlag;       fwrite( (char *) &u, 1, 8, outM);  /* 10 */
	      u = (double)tz->q + a1;     fwrite( (char *) &u, 1, 8, outM);            /* share#, zmax=1st, 1= last (0 for tt,tz) */
	      u = (double)nHT[tz->type];  fwrite( (char *) &u, 1, 8, outM);
	      u =-(double)rhoT[tz->type]; fwrite( (char *) &u, 1, 8, outM);            /* rhoT is -discount rate  */
	      u = (double)Jcount[2]-(double)Jcount[3]; fwrite( (char *) &u,1,8,outM);  /* cum jumps since j=0     */
	      u = (double)Bt[nP];         fwrite( (char *) &u, 1, 8, outM);  /* 15 */  /* Ask, before this share  */
	      u = (double)Bt[nPx1];       fwrite( (char *) &u, 1, 8, outM);            /* Bid, before this share  */
	      if (Bt[nP]==nP)    u=0.0; else   u = (double)Bt[Bt[nP]];   fwrite( (char *) &u , 1, 8, outM );   /* Ask depth */
	      if (Bt[nPx1]==-1) v1=0.0; else  v1 = (double)Bt[Bt[nPx1]]; fwrite( (char *) &v1, 1, 8, outM );   /* Bid depth */
	      for(p=nP_1; p>Bt[nP];   p--)     u+= (double)Bt[p];        fwrite( (char *) &u , 1, 8, outM );   /* # sells   */
	      for(p=0;    p<Bt[nPx1]; p++)    v1+= (double)Bt[p];        fwrite( (char *) &v1, 1, 8, outM );   /* # buys    */  /* col 20 */
	      for(p=0;    p<nP;       p++) {  v1 = (double)Bt[p];        fwrite( (char *) &v1, 1, 8, outM ); } /* Bt each p */  /* col 21:20+nP */
	      u = -9.0;      /* filler */ fwrite( (char *) &tz->CS,1,8,outM);/* disc.CS thus far (prior to this share) */
	      /* retained order filler */ fwrite( (char *) &u, 1, 8, outM);  /*       instead of retained order payoff */
	      /* retained order filler */ fwrite( (char *) &u, 1, 8, outM);
	      /* retained order filler */ fwrite( (char *) &u, 1, 8, outM);
	      u = (double)tz->tremble;    fwrite( (char *) &u, 1, 8, outM ); /* 2 if just trembled, 1 if ever trembled */
	      u = (double)a1;             fwrite( (char *) &u, 1, 8, outM ); /* mkt orders left after this one */
	      /* */                       fwrite( (char *) &v, 1, 8, outM ); /* E(payoff) of combined actions  */
	      /* */                       fwrite( (char *)&tz->Ev,1,8,outM); /* E(v) relative to last observed */
	      for(p=0; p<nRT; p++) {      /* also write Hz if tz->vlag */    /* write Ht[][] that tt,tj condition on */
		u = (double)RT[p];        fwrite( (char *) &u, 1, 8, outM);  /* p^th RT */
		u = (double)Ht[0][p];     fwrite( (char *) &u, 1, 8, outM);  /* net jumps    p^th RT */
		u = (double)Ht[1][p];     fwrite( (char *) &u, 1, 8, outM);  /* last price   p^th RT */
		u = (double)Ht[2][p];     fwrite( (char *) &u, 1, 8, outM);  /* cum net buys p^th RT */
		u = (double)Ht[3][p];     fwrite( (char *) &u, 1, 8, outM);  /* last price signed order p^th RT */
	      }
	      u = (double)t1;             fwrite( (char *) &u, 1, 8, outM );  /* price  */
	      u = -1.0;                   fwrite( (char *) &u, 1, 8, outM );  /* volume (this col.could be more inform.) */
	    }
#endif
	    a1--;  --Bt[t1];     /* t1 is bid, updated at end of loop         */
	    M1->p = t1;          /* ONLY the LAST executed order's price used */
	    t = traders[t1];     /* some outM stuff could go before this line */
	    while (t!=NULL) {
	      t->q-- ;           /* reduce queues: q<= 0 means executed */
#if mJ>0
	      if(j<mJ && t->q==0) { /* write executed limit's t->period, t->j */
		u = (double) t->period;  fwrite((char *)&u,1,8,outM);
		u = (double) t->j;       fwrite((char *)&u,1,8,outM); }
#endif
	      t= t->next; }
	    Acount[nP]++;        /* market  sell counter    */
	    Tcount[t1][0]++;     /* market  sell counter - by price */
	    byT[tz->type][tz->pvtype][4]++;   /* market trade counter - by type */
	    byT[tz->type][tz->pvtype][16]++;  /* market  sell counter - by type */
	    Spread[nP+2]+= ask1-bid1;
	    Ttau[TAU+1]++ ;      /* trade volume THIS PERIOD, not necessarily at this spread. */
	    Ptau[TAU+1]= t1;     /* used later for realized spread */

	    u = t1- tz->pv;  v1= u * exp(rhoT[tz->type] * tz->since0) /* - cT[tz->type] * tz->since0 */ ;   v0+= v1;  /* v0 is realized CS from a1 trades */
	    CS[ tz->type][tz->pvtype][1] +=  v1;          /*   discounted CS */
	    CS[ tz->type][tz->pvtype][3] +=  tz->since0;
	    CS[ tz->type][tz->pvtype][5] +=  u;           /* UNdiscounted CS */
	    CS[ tz->type][tz->pvtype][6] +=  PVmu - t1 ;  /* TransCosts : if ever change code such that PVmu is NOT the v tick, change this */

	    if(bid1==-1 || ask1==nP) m=0;  
	    else   m= min(4,(ask1-bid1));
	    TC[tz->type][tz->pvtype][0][m]++ ;            /* count mkt orders by spread */
	    TC[tz->type][tz->pvtype][1][m]+= PVmu - t1;   /* TC by spread */

	    tz->CSu += u;
	    while(t1>=0 && Bt[t1]==0)  t1--;              /* potentially new bid */
	    Bt[nPx1] = t1;
	  }                                               /* end processing a1 sells */
	  if(tz->tremble <2)  zpq[0][nP_1-t1][tz->q]++;   /* zpq[0] counts ASKS so flip t1 bid */
	}
	else {                   /* buyer since tz->pv >= PVmu.  assuming tz->pv NEVER = PVmu */
	  t1 = Bz[nP];           /* buy at ask = Bz[nP] */
	  while(tz->q && t1<nP){ /* t1 = nP if NO ASK   */
	    tz->q--;             /* shares togo, AFTER this mkt order */
	    v0= tm->CS - t1;     /* tm->CS = tz->pv + tz->Ev + tz->v = E(adjusted pv) */
	    u+= v0;              /* cum value of mkt orders, from 1 to q1 (ie, initial tz->q) */
	    Bz[t1]++;            /* remove executed limit from Bz */
	    if(tz->vlag){ Hz[1][0]= t1;  Hz[2][0]++; }  /* temporarily adjust Hz if vlag = 1 */
	    else        { Ht[1][0]= t1;  Ht[2][0]++; }  /* temporarily adjust Ht if vlag = 0 */
	    if(tz->q) { s = GetState(tz,Bz);  v1= u + s->w; }    /* u is value of  (q1-tz->q) mkt orders, s->w is continuation value of shares togo */
	    else      { s = S;                v1= u; }           /* tz DONE */
	    if(v1 > v){ v =v1;  a1= q1-tz->q; tm->s = s; a_=0; }
	    else      { if(tz->q == q1-a1 -1) a_ = v0>0 ; }      /* a_ = 1 --> okay to tremble to a1++ */
	    while(t1<nP) { if (Bz[t1]==0) t1++;  else break; }   /* potentially new ask */
	  }                      /* optimal a1 obtained */

	  if(RandU<PrTremble) {  /* tremble by altering a1 +-1.  can always decrease a1 w/out incurring neg. payoff */
	    if(a1==q1) a1--;     /* but must be sure increasing a1 doesn't yield neg. payoff for the marginal share */
	    else {
	      if(a_) {           /* a_ is flag for whether okay to tremble to a1++ */
		if(a1==0) a1=1;  /* can only a1++ */
		else { if(RandU<.5) a1--; else a1++; }}   /* can ++ or --, so randomize */
	      else a1--; }       /* a_ =0 --> only tremble is a1--.  if a1=0 already then a1 becomes <0, which is ONLY failed tremble */
	    if(a1<0) a1=0;       /* must be case that a1-- occurred when a1 already =0 */
	    else { byT[tz->type][tz->pvtype][10]++;  tz->tremble=1;  tm->s=S;  tz->tremble=2; }
	  }                      /* tm->s=S triggers GetState below, but tz->tremble=2 needed by zpq, outM */
	  tz->q = q1 - a1;       /* q1 stored current tz->q. a1 is optimal #shares to buy/sell */

	  if(a1) {
	    M= Mx; if(realtime-M->RT <nLag){ M=malloc(sizeof(struct MO)); if(M==NULL){printf("Out of memory for M\n");exit(8);}  M->prev = Mx;  N_s[11]++;}
	    Mx= M->prev;  Mx->next= NULL;  M->prev= NULL;  M1->prev= M;  M->next= M1;  M1= M;
	    M1->sof= a1;  M1->z = 1;  M1->RT = realtime;  /* sof = signed order flow,  z=0 for SMALL trader (tt,tj),  1 for LARGE (tz) */
	  }
	  v0 = 0.0;              /* v0 is realized CS from a1 trades          */
	  t1 = Bt[nP];           /* Bt not yet altered by tz action           */
	  while(a1) {            /* implement tz's mkt buys, Post-Tremble     */
	    if(tz->tremble < 2) {
	      k = tz->q+a1;      /* tz->q already reflects a1.  if q=1, and a1 = 2 then processing shares 3,2 */
	      zRT[0][k] += tz->since0;
	      zRT[1][k] += tz->since0 * tz->since0;
	      zpq[0][t1][k]++;   /* zpq[0] counts ASKS                        */
	      zpq[1][t1][k]++; } /* zpq[1] counts market BUY prices           */
#if mJ>0                         /* outM code IDENTICAL for tz buyer & seller */
	    if(j<mJ) {           /* tz outm action */
	      u = (double)j;              fwrite( (char *) &u, 1, 8, outM ); /* 1  */  /* current "period"        */
	      u = (double)tz->period;     fwrite( (char *) &u, 1, 8, outM );           /* period   1st arrived    */
	      fwrite(                     (char *)&tz->rtime0, 1, 8, outM );           /* realtime 1st arrived    */
	      fwrite(                     (char *)&realtime,   1, 8, outM );           /* realtime current        */
	      fwrite(                     (char *)&tz->since,  1, 8, outM ); /* 5  */  /* time since prev action, 0 if new arrival (could change to time since last new */
	      fwrite(                     (char *)&tz->pv,     1, 8, outM );           /* private value           */
	      u = (double)tz->v;          fwrite( (char *) &u, 1, 8, outM);            /* last observed v */
	      u = (double)tz->type;       fwrite( (char *) &u, 1, 8, outM);
	      u = (double)tz->tB  ;       fwrite( (char *) &u, 1, 8, outM);
	      u = (double)tz->vlag;       fwrite( (char *) &u, 1, 8, outM);  /* 10 */
	      u = (double)tz->q + a1;     fwrite( (char *) &u, 1, 8, outM);            /* share#, zmax=1st, 1= last (0 for tt,tz) */
	      u = (double)tz->tH;         fwrite( (char *) &u, 1, 8, outM);
	      u =-(double)rhoT[tz->type]; fwrite( (char *) &u, 1, 8, outM);            /* rhoT is -discount rate  */
	      u = (double)Jcount[2]-(double)Jcount[3]; fwrite( (char *) &u,1,8,outM);  /* cum jumps since j=0     */
	      u = (double)Bt[nP];         fwrite( (char *) &u, 1, 8, outM);  /* 15 */  /* Ask, before this share  */
	      u = (double)Bt[nPx1];       fwrite( (char *) &u, 1, 8, outM);            /* Bid, before this share  */
	      if (Bt[nP]==nP)    u=0.0; else   u = (double)Bt[Bt[nP]];   fwrite( (char *) &u , 1, 8, outM );   /* Ask depth */
	      if (Bt[nPx1]==-1) v1=0.0; else  v1 = (double)Bt[Bt[nPx1]]; fwrite( (char *) &v1, 1, 8, outM );   /* Bid depth */
	      for(p=nP_1; p>Bt[nP];   p--)     u+= (double)Bt[p];        fwrite( (char *) &u , 1, 8, outM );   /* # sells   */
	      for(p=0;    p<Bt[nPx1]; p++)    v1+= (double)Bt[p];        fwrite( (char *) &v1, 1, 8, outM );   /* # buys    */  /* col 20 */
	      for(p=0;    p<nP;       p++) {  v1 = (double)Bt[p];        fwrite( (char *) &v1, 1, 8, outM ); } /* Bt each p */  /* col 21:20+nP */
	      u = -9.0;      /* filler */ fwrite( (char *) &tz->CS,1,8,outM);/* disc.CS thus far (prior to this share) */
	      /* retained order filler */ fwrite( (char *) &u, 1, 8, outM);  /*       instead of retained order payoff */
	      /* retained order filler */ fwrite( (char *) &u, 1, 8, outM);
	      /* retained order filler */ fwrite( (char *) &u, 1, 8, outM);
	      u = (double)tz->tremble;    fwrite( (char *) &u, 1, 8, outM ); /* 2 if just trembled, 1 if ever trembled */
	      u = (double)a1;             fwrite( (char *) &u, 1, 8, outM ); /* mkt orders left after this one */
	      /* */                       fwrite( (char *) &v, 1, 8, outM ); /* E(payoff) of combined actions  */
	      /* */                       fwrite( (char *)&tz->Ev,1,8,outM); /* E(v) relative to last observed */
	      for(p=0; p<nRT; p++) {      /* also write Hz if tz->vlag */    /* write Ht[][] that tt,tj condition on */
		u = (double)RT[p];        fwrite( (char *) &u, 1, 8, outM);  /* p^th RT */
		u = (double)Ht[0][p];     fwrite( (char *) &u, 1, 8, outM);  /* net jumps    p^th RT */
		u = (double)Ht[1][p];     fwrite( (char *) &u, 1, 8, outM);  /* last price   p^th RT */
		u = (double)Ht[2][p];     fwrite( (char *) &u, 1, 8, outM);  /* cum net buys p^th RT */
		u = (double)Ht[3][p];     fwrite( (char *) &u, 1, 8, outM);  /* last price signed order p^th RT */
	      }
	      u = (double)t1;             fwrite( (char *) &u, 1, 8, outM );  /* price  */
	      u = 1.0;                    fwrite( (char *) &u, 1, 8, outM );  /* volume (this col.could be more inform.) */
	    }
#endif
	    a1--;  ++Bt[t1];     /* t1 is ask, updated at end of loop         */
	    M1->p = t1;          /* ONLY the LAST executed order's price used */
	    t = traders[t1];     /* some outM stuff could go before this line */
	    while (t!=NULL) {
	      t->q-- ;           /* reduce queues: q<= 0 means executed */
#if mJ>0
	      if(j<mJ && t->q==0) { /* write executed limit's t->period, t->j */
		u = (double) t->period;  fwrite((char *)&u,1,8,outM);
		u = (double) t->j;       fwrite((char *)&u,1,8,outM); }
#endif
	      t= t->next; }
	    Bcount[nP]++;        /* market buy counter     */
	    Tcount[t1][1]++;     /* market buy counter - by price */
	    byT[tz->type][tz->pvtype][4]++;   /* market trade counter - by type */
	    byT[tz->type][tz->pvtype][15]++;  /* market  buy  counter - by type */
	    Spread[nP+2]+= ask1-bid1;
	    Ttau[TAU+1]++ ;      /* trade volume THIS PERIOD, not necessarily at this spread. */
	    Ptau[TAU+1]= t1;     /* used later for realized spread */

	    u = tz->pv -t1;  v1 = u * exp(rhoT[tz->type] * tz->since0) /* - cT[tz->type] * tz->since0 */ ;  v0+= v1;  /* v0 is realized CS from a1 trades */
	    CS[ tz->type][tz->pvtype][1] +=  v1;          /*   discounted CS */
	    CS[ tz->type][tz->pvtype][3] +=  tz->since0;
	    CS[ tz->type][tz->pvtype][5] +=  u;           /* UNdiscounted CS */
	    CS[ tz->type][tz->pvtype][6] +=  t1 - PVmu ;  /* TransCosts : if ever change code such that PVmu is NOT the v tick, change this */

	    if(bid1==-1 || ask1==nP) m=0;  
	    else   m= min(4,(ask1-bid1));
	    TC[tz->type][tz->pvtype][0][m]++ ;            /* count mkt orders by spread */
	    TC[tz->type][tz->pvtype][1][m]+= t1 - PVmu;   /* TC by spread */

	    tz->CSu += u;
	    while(t1<nP && Bt[t1]==0)  t1++;              /* potentially new ask */
	    Bt[nP] = t1;
	  }                                               /* end processing a1 buys */
	  if(tz->tremble <2) zpq[0][t1][tz->q]++;         /* zpq[0] counts ASKS */
	}                                                 /* end: buyer since tz->pv >= PVmu */
	/* MO, Bt, tz->q now reflect tz's mkt orders (post-tremble) */
#if mJ>0
	if(j<mJ && tz->q) {           /* tz outm NON-action (ie, shares togo, but no more right now).  a1=0.  */
	  u = (double)j;              fwrite( (char *) &u, 1, 8, outM ); /* 1  */  /* current "period"        */
	  u = (double)tz->period;     fwrite( (char *) &u, 1, 8, outM );           /* period   1st arrived    */
	  fwrite(                     (char *)&tz->rtime0, 1, 8, outM );           /* realtime 1st arrived    */
	  fwrite(                     (char *)&realtime,   1, 8, outM );           /* realtime current        */
	  fwrite(                     (char *)&tz->since,  1, 8, outM ); /* 5  */  /* time since prev action, 0 if new arrival (could change to time since last new */
	  fwrite(                     (char *)&tz->pv,     1, 8, outM );           /* private value           */
	  u = (double)tz->v;          fwrite( (char *) &u, 1, 8, outM);            /* last observed v */
	  u = (double)tz->type;       fwrite( (char *) &u, 1, 8, outM);
	  u = (double)tz->tB  ;       fwrite( (char *) &u, 1, 8, outM);
	  u = (double)tz->vlag;       fwrite( (char *) &u, 1, 8, outM);  /* 10 */
	  u = (double)tz->q + a1;     fwrite( (char *) &u, 1, 8, outM);            /* share#, zmax=1st, 1= last (0 for tt,tz) */
	  u = (double)tz->tH;         fwrite( (char *) &u, 1, 8, outM);
	  u =-(double)rhoT[tz->type]; fwrite( (char *) &u, 1, 8, outM);            /* rhoT is -discount rate  */
	  u = (double)Jcount[2]-(double)Jcount[3]; fwrite( (char *) &u,1,8,outM);  /* cum jumps since j=0     */
	  u = (double)Bt[nP];         fwrite( (char *) &u, 1, 8, outM);  /* 15 */  /* Ask, before this share  */
	  u = (double)Bt[nPx1];       fwrite( (char *) &u, 1, 8, outM);            /* Bid, before this share  */
	  if (Bt[nP]==nP)    u=0.0; else   u = (double)Bt[Bt[nP]];   fwrite( (char *) &u , 1, 8, outM );   /* Ask depth */
	  if (Bt[nPx1]==-1) v1=0.0; else  v1 = (double)Bt[Bt[nPx1]]; fwrite( (char *) &v1, 1, 8, outM );   /* Bid depth */
	  for(p=nP_1; p>Bt[nP];   p--)     u+= (double)Bt[p];        fwrite( (char *) &u , 1, 8, outM );   /* # sells   */
	  for(p=0;    p<Bt[nPx1]; p++)    v1+= (double)Bt[p];        fwrite( (char *) &v1, 1, 8, outM );   /* # buys    */  /* col 20 */
	  for(p=0;    p<nP;       p++) {  v1 = (double)Bt[p];        fwrite( (char *) &v1, 1, 8, outM ); } /* Bt each p */  /* col 21:20+nP */
	  u = -9.0;      /* filler */ fwrite( (char *) &tz->CS,1,8,outM);/* disc.CS thus far (prior to this share) */
	  /* retained order filler */ fwrite( (char *) &u, 1, 8, outM);  /*       instead of retained order payoff */
	  /* retained order filler */ fwrite( (char *) &u, 1, 8, outM);
	  /* retained order filler */ fwrite( (char *) &u, 1, 8, outM);
	  u = (double)tz->tremble;    fwrite( (char *) &u, 1, 8, outM ); /* 2 if just trembled, 1 if ever trembled */
	  u = (double)a1;             fwrite( (char *) &u, 1, 8, outM ); /* mkt orders left after this one */
	  /* */                       fwrite( (char *) &v, 1, 8, outM ); /* E(payoff) of combined actions  */
	  /* */                       fwrite( (char *)&tz->Ev,1,8,outM); /* E(v) relative to last observed */
	  for(p=0; p<nRT; p++) {      /* also write Hz if tz->vlag */    /* write Ht[][] that tt,tj condition on */
	    u = (double)RT[p];        fwrite( (char *) &u, 1, 8, outM);  /* p^th RT */
	    u = (double)Ht[0][p];     fwrite( (char *) &u, 1, 8, outM);  /* net jumps    p^th RT */
	    u = (double)Ht[1][p];     fwrite( (char *) &u, 1, 8, outM);  /* last price   p^th RT */
	    u = (double)Ht[2][p];     fwrite( (char *) &u, 1, 8, outM);  /* cum net buys p^th RT */
	    u = (double)Ht[3][p];     fwrite( (char *) &u, 1, 8, outM);  /* last price signed order p^th RT */
	  }
	  u = -9.0;                   fwrite( (char *) &u, 1, 8, outM );  /* price filler */
	  u =  0.0;                   fwrite( (char *) &u, 1, 8, outM );  /* volume (this col.could be more inform.) */
	  u = -9.0;                   fwrite( (char *) &u, 1, 8, outM );
	  u = -9.0;                   fwrite( (char *) &u, 1, 8, outM );
	}
#endif
	/* update value of prev state regardless of whether act or not.  Update uses OPTIMAL v, even if trembled. */
	if (tz->s!=NULL && UPDATE_PVT[tz->type][tz->pvtype])  /* HEY: should part of v based on trades in a1 use E(payoff) or REALIZED (using actual v) ?? */
	  tz->s->w += ( exp(rhoT[tz->type] * tz->since) * v /* - cT[tz->type] * tz->since */ - tz->s->w ) / ++tz->s->N ;  /* Currently uses E(payoff) : v from a1 determination loop, not a1 implementation loop. */

	/*
	  if(tz->pvtype==0 && tz->q==tz->z && tz->tremble==0)
	  printf("j=%u tz#= %u  tz->q = %2d %2d  rt0-rt= %5.2f since= %5.2f  bid~ask= %2d %2d  s: %p Pw~w~v= %5.2f %5.2f %5.2f %5.2f N= %u\n",
	  j, byT[tz->type][tz->pvtype][0], q1, tz->q, realtime-tz->rtime0, tz->since, Bt[nPx1],Bt[nP], tz->s, tz->s->Pw, tz->s->w, v, exp(rhoT[tz->type]*tz->since)*v - cT[tz->type] * tz->since,tz->s->N);
	*/
	if(UPDATE_PVT[tz->type][tz->pvtype]) N_s[22]++;  /* #decisions by traders for whom InsertNewState='y', to be compared with #NewStates */
	tz->tremble = min(tz->tremble, 1);               /* convert tz->tremble=2 to =1 */
	tz->s = tm->s;  InsertNewState = 'y';
	if(tz->q && tz->s==S && UPDATE_PVT[tz->type][tz->pvtype]) {  /* new state, so call GetState again with InsertNewState = 'y'.  NOTE: tm->s=S if just trembled. */
	  M = M1;                 /* must rebuild Ht, Hz to reflect recent orders before calling GetState, except Ht[0] jump history already set */
	  for(p=0; p<nRT; p++) {  /* NOTE: if do not rebuild here, then Ht,Hz inaccurate until rebuild next j, but not needed. */
	    Ht[1][p]= 0;  Ht[2][p]= 0;  Ht[3][p]= 0;   /* [1]=last price, [2]= cum net buys, [3]= last price signed order (0 if NONE this RT bin) */
	    Hz[1][p]= 0;  Hz[2][p]= 0;  Hz[3][p]= 0;
	    while(M!=NULL && realtime - M->RT < RT[p]) {
	      if(M->z && tz->vlag){ Hz[2][p]+= M->sof;  if(Hz[3][p]==0){ Hz[1][p]= M->p;  Hz[3][p]= M->sof; } }
	      else                { Ht[2][p]+= M->sof;  if(Ht[3][p]==0){ Ht[1][p]= M->p;  Ht[3][p]= M->sof; } }
	      M = M->next; } }   /* [0,2] use only 1st transaction within each RT bin since M list sorts orders starting w/ most recent.*/
	  tz->s = GetState(tz,Bt);
	}
	if(tz->s0==NULL) {       /* tz's initial period.  IMPORTANT: realized outcomes only track orders submitted AFTER initial period, */
	  tz->s0 = tz->s;        /* since Ew, CS compared to belief s0->w which is cont.value of state AFTER 1st action. */
	  tz->CS = 0.0;          /* ultimate realized outcomes EXCLUDE mkt orders from INITIAL period: such orders NOT part of initial CONTINUATION state. */
	  byT[tz->type][tz->pvtype][19]+= zT[tz->type] - tz->q;  }  /* mkt orders when arrive */
	else  {                  /* tz returning: update realized outcome of prev state  */
	  tz->CS+= v0;           /* v0 is realized discounted (since0) CS, POST-TREMBLES */
	  if(UPDATE_s0) {        /* since0 = since --> could use either in next line */
	    v = exp(rhoT[tz->type] * tz->since0) * v /* - cT[tz->type] * tz->since0 */ ;  /* v undiscounted when get a1 */
	    tz->s0->Ew += v;
	    tz->s0->w2 += v*v;   /* should tz->s0 update only occur if tz is done (ie, tz->q = 0) ??  See use of tz->CS */
	    tz->s0->NN ++;
	    tz->s0 = tz->s;  tz->since0 = 0.0;  /* tz->rtime0= realtime; */  tz->j = 0; }
	  else {
	    if(tz->q == 0 && tz->tremble==0) {   /* tz DONE & UPDATE_s0==0 & Never Trembled --> update tz->s0 */
	      tz->s0->Ew += tz->CS;
	      tz->s0->w2 += tz->CS * tz->CS;
	      tz->s0->NN ++;
	      /*
	       if(tz->pvtype==0)
		printf("j=%u tz#= %u  since0= %5.2f  s0: NN= %3u  w~Pw~Ew~CS~u= %5.2f %5.2f %5.2f %5.2f %5.2f  N= %8u s0= %p  DONE\n\n",
		j, byT[tz->type][tz->pvtype][0], tz->since0, tz->s0->NN, tz->s0->w, tz->s0->Pw, tz->s0->Ew/tz->s0->NN, tz->CS, tz->CSu, tz->s0->N, tz->s0);
	      */
	    }
	    /* if(tz->pvtype==0 && tz->tremble==0 && tz->q)
	        printf("j=%u tz#= %u  since0= %5.2f  s0: NN= %3u  w~Pw~v0~CS~u= %5.2f %5.2f %5.2f %5.2f %5.2f  N= %8u s0= %p  tz->q= %d\n",
		j, byT[tz->type][tz->pvtype][0], tz->since0, tz->s0->NN, tz->s0->w, tz->s0->Pw, v0, tz->CS, tz->CSu, tz->s0->N, tz->s0, tz->q); */
	  }
	}
	if(tz->q == 0) {         /* tz DONE --> record final CS[] */
	  if(tz->tremble==0) {
	    CS[tz->type][tz->pvtype][9]+= tz->CS*tz->CS;           /* disc.CS^2 NEVER tremble */
	    CS[tz->type][tz->pvtype][13]+= tz->since0; }
	  CS[ tz->type][tz->pvtype][7 +(tz->tremble >0)]+= tz->CS; /* disc. CS  by  NEVER/EVER tremble */
	  byT[tz->type][tz->pvtype][13+(tz->tremble >0)]++;        /* count traders NEVER/EVER tremble */
	}
	M = M1;       tz->again = 999.9;        /* if tz->q get tz->again based on 1st time at which the Ht or Hz change due to passage of time */
	for(p= nRT * (tz->q==0); p<nRT; p++)    /* nRT*(tz->q==0) instead of  if(tz->q) */
	  while(M!=NULL && realtime - M->RT < RT[p]) {
	    /*  printf("  again= %5.2f  RT[%d]= %5.2f  realtime= %5.2f  M->RT= %5.2f  realtime-M->RT= %5.2f  RT[]-()= %9.1e\n",
		tz->again, p, RT[p], realtime, M->RT, realtime-M->RT, RT[p]-(realtime - M->RT));  */
	    tz->again = min(tz->again, RT[p]-(realtime - M->RT));   /* RT[p]-() is time until order M moves into bin RT[p+1] */
	    M = M->next; }
	tz->again += .0001;   /* otherwise get stuck in loop reacting to same event moving to new bin */
	tz->since = 0.0;
	
	/* printf("j=%u tz#= %u  tz->q = %2d %2d  again=%6.2f  since0= %5.2f  bid~ask= %2d %2d  s: %p w= %5.2f N= %u\n",
	   j, byT[tz->type][tz->pvtype][0], q1, tz->q, tz->again, tz->since0, Bt[nPx1],Bt[nP], tz->s, tz->s->w, tz->s->N); */

      }                       /* end tz (large) trader stuff.  Next code executed is the Traders[] update. */


      /* -----------------------------      END OF  tz  LARGE TRADER     -----------------------------  */
      
      else {                  /* else next trader is either NEW (tj==tt) or Returning SMALL (tj!=tt) */

	/* This  HUGE  else{} is NOT YET TABIFIED  (off by 2 columns) */

      M = M1;
      for(p=0; p<nRT; p++) {  /* build global Ht[][] for tt,tj to condition on, except Ht[0] jump history already set */
	Ht[1][p]= 0;  Ht[2][p]= 0;  Ht[3][p]= 0;   /* [1]=last price, [2]= cum net buys, [3]= last price signed order (0 if NONE this RT bin) */
	while(M!=NULL && realtime - M->RT < RT[p]) {
	  Ht[2][p]+= M->sof;  if(Ht[3][p]==0){ Ht[1][p]= M->p;  Ht[3][p]= M->sof; }
	  M = M->next; } }    /* [1,3] use only 1st transaction within each RT bin since M list sorts orders starting w/ most recent.*/
      
      if(Ht[3][0]==0) Ntremble[22]++;  /* count #times NO transactions in (most) recent history.  could expand if nRT>1 to look over all bins */

      InsertNewState = 'n';   /* global:  tells GetState() to NOT insert new state (since states may not actually be chosen) */
      
      if (tj == tt) {         /* next trader to move is NEW ARRIVAL */

	tt->next = NULL;      /* tt already points to memory that can be used here. */
	tt->s    = NULL;      /* create New Trader traits : re-initialize everything to be safe. */
	tt->s0   = NULL;      /* s0!=NULL --> new limitorder from revisiting trader */
	tt->si   = NULL;
	
	u = RandU; u = min(HiU,max(LoU,u));        /* draw bounded uniform 0-1 */
	tt->again  = -log(u)*Earrive;              /* exponential time until NEXT new arrival.  Hence, tt->again  must NOT be overwritten. */
	tt->since  = 0.0;
	tt->since0 = 0.0;
	tt->sincei = 0.0;
	tt->rtime0 = realtime;

	u = RandU;
	if (ExogBuy) {
	  if (u<.5)  tt->buy = 0; else { tt->buy = 1;  u= u-.5; }
	  u = 2*u;        /* 2*u back to U(0,1) --> no 2nd rand() call */
	}
	else tt->buy = 9;

	tt->p = 0;  tt->q = 0;  tt->ret= 0;  /* not needed since set below */
	
	for(p=0; p<nPV_1; p++) { if (u < cdfPV[p]) break; }        /* p becomes tt->pvtype */
	
	/* could allow trader types to depend on v History, but currently do not.  */
	u= RandU; /* nT-1 --> no concern of u being slightly > 1.0 due to rounding */
	for (m=0; m< nT_1; m++) { if (u < cdfPVT[p][m] ) break; }  /* p is still tt->pvtype */
	
	if (zT[m]) {
	  if (tz->q) {     /* already have active large trader, so get a SMALL new trader.  IGNORES EXOGBUY */
	    while(zT[m]){  /* Draw new tt until zT[m]=0 (before entering loop zT[m]=1) */
	      u = RandU;  for(p=0; p<nPV_1; p++) { if (u < cdfPV[p])     break; }
	      u = RandU;  for(m=0; m <nT_1; m++) { if (u < cdfPVT[p][m]) break; }
	    } }
	  else {
	    tz->next = NULL;     /* initialize new large trader.  THEN draw new SMALL trader. */
	    tz->s    = NULL;     /* won't use this new tz until AFTER new small trader acts.  */
	    tz->s0   = NULL;     /* s0==NULL --> tz just arrived */
	    tz->si   = NULL;
	    tz->since  = 0.0;
	    tz->since0 = 0.0;
	    tz->sincei = 0.0;
	    tz->rtime0 = realtime;
	    tz->pv     = PV[p] + PVmu;    /* pv is relative to CURRENT v, not last observed v */
	    tz->pvtype = p;
	    tz->z      = zT[m];
	    tz->buy    = 0;   
	    tz->p      = 0;
	    tz->q      = tz->z;  /* LARGE traders submit ONLY MARKET ORDERS. t->q is #shares yet, NOT priority */
	    tz->ret    = 0;
	    tz->type   = m;	    byT[m][tz->pvtype][0]++;  /* count simulated traders, by type */
	    tz->tB     = nBT[m];
	    tz->tH     = nHT[m];
	    tz->tremble= 0;      /* trembles drawn later */
	    tz->aggress= 0;
	    tz->vlag   = vlagT[m] * nLag;  /* vlagT is 0 or 1.  tz->v  set later before tz action */
	    tz->j      = 0;      /* actual   jumps in v */
	    tz->jlag   = 0;      /* observed jumps in v */
	    tz->period = j;      /* period arrived      */
	    tz->n      = 1;      /* number of arrivals  */
#if nL>0
	    for(p=0;p<nL;p++)  tz->L[p]= 0;
#endif

	    /* get new SMALL trader to move 1st (so can ALWAYS process tt,tj action before tz) */
	    while(zT[m]){          /* Draw new tt until zT[m]=0 (before entering loop zT[m]=1) */
	      u = RandU;  for(p=0; p<nPV_1; p++) { if (u < cdfPV[p])     break; }
	      u = RandU;  for(m=0; m <nT_1; m++) { if (u < cdfPVT[p][m]) break; }
	    } }
	}   /* proceed as if never drew the large tz trader */
	
	tt->pv     = PV[p] + PVmu;  /* tt->pv is relative to CURRENT v, not last observed v */
	tt->pvtype = p;
	tt->z      = 0;             /* 0--> SMALL trader (w/ 1 share).  1--> LARGE w/ 1 share togo */

	if(dev_PVT[p][m]) {         /* ensure another deviator not already active */
	  for(a1=0;a1<nPx1;a1++) {  /* next line checks whether any current traders are deviators */
	    t= traders[a1];  while(t!=NULL){ if(dev_PVT[t->pvtype][t->type]) { a1=nP+9; t=NULL;} else  t=t->next; } }
	  if(a1>nPx1)               /* deviator already active, so ... */
	    while(dev_PVT[p][m]) {  /* draw new trader type until get nondeviator */
	      u= RandU;             /* nT-1 --> no concern of u being slightly > 1.0 due to rounding */
	      for (m=0; m< nT_1; m++) { if (u < cdfPVT[p][m] ) break; }     /* p is still tt->pvtype */
	    } }

	tt->type   = m;       byT[tt->type][tt->pvtype][0]++;  /* count simulated traders, by type */
	tt->tB     = nBT[m];
	tt->tH     = nHT[m];
	tt->tremble= 0;    /* reset prior to getting optimal action */
	tt->aggress= 99;
	if(vlagT[m]){ tt->vlag = nLag;  tt->v = tm->v;}  else {tt->vlag = 0;  tt->v = 0;}  /* GetState() shifts Bt by tt->v */
	tt->j      = 0;    /* actual   jumps in v */
	tt->jlag   = 0;    /* observed jumps in v */
	tt->period = j;    /* period arrived      */
	tt->n      = 1;    /* number of arrivals  */
#if nL>0
	for(p=0;p<nL;p++)  tt->L[p]= 0;
#endif

	/* tt->pvtype = 2;  tt->pv = PVmu;  */

      }        /* end if tt==tj */


      else {   /* tt != tj    #################    next trader to move is revisiting the market   ###############*/

	/*---------------------------  Returning Trader OVERVIEW   ---------------------------
	  Returning trader FIRST faces BINOMIAL choice of RETAIN current order (flagged by tj->q = 1) or NOT (tj->q = 2).
	  To do this he compares expected payoffs for each of these TWO actions.
	  The belief of CANCELLING order may be updated even if cancel, since we can hypothetically cancel it
	  and use the s->w from what would have been the replacment order.
	  The belief of RETAINING the order, however, can only be updated if it is indeed retained, so will need trembles.
	  
	  If belief to  RETAINING is a new state, then initialize with NEW ARRIVAL's belief for new order at that tick.
	  Such an order will NOT be optimistic, but it should be a rather precise guess.  This will require checking
	  whether the RETAINING belief is new, and hypothetically changing tj->q to 0 (NEW ARRIVAL flag).

	  Beliefs about E(v) for evaluating market orders are NOT needed since market orders are only placed AFTER cancellation.
	  Hence, E(v) will be based on the post-cancellation state of market.
	  Of course, if want returning traders to condition on "private history" in addition to Ht[] then E(v) will be needed.
	*/

	/* FIRST get value of replacement GIVEN CANCEL.  Regardless of Cancel/Retain choice, the replacement value will be used
	   to update belief for CANCEL, and can be used as a starting value if this belief is currently not in the state space.
	   Can use tm->Ev to determine range of valid retained orders then too.
	*/
	tj->n++;  Ntremble[21] = max((unsigned int)tj->n, Ntremble[21]);     /* count times arrive to market */
	if( tj->vlag ) tj->v = tm->v ;   /* 0 if vlag=0.  GetState() shifts Bt by tj->v.  Will update tj->Ev with tm->EV later */

	if (tj->p < nP) {  /* NOT a Non-Order, so hypothetically cancel the order so can evaluate replacement orders */
	  if (tj->buy) {
	    Bt[tj->p] -= 1;                         /* cancel the SINGLE SHARE  buy order.      bid may change. */
	    while(bid1>=0 && Bt[bid1]==0) bid1--; } /* if needed, lower bid (min -1) until find an order (Bt>0) */
	  else {                                    /* Bt[bid1] not evaluated UNLESS bid1>=0  -->  never Bt[-1] */
	    Bt[tj->p] += 1;                         /* CANCEL the SINGLE SHARE sell order.      ask may change. */
	    while(ask1<nP && Bt[ask1]==0) ask1++; } /* if needed, raise ask (max nP) until find an order (Bt<0) */
	  Bt[nP]= ask1;  Bt[nPx1]= bid1;            /* Initially only used Bt[nP,nPx1] then began using bid1,ask1 since easier to remember */
	}
	tt->s0      = tj->s0;         /* IMPORTANT: if tj cancels/resubmits then new order inherits s0, si, since0 */
	tt->si      = tj->si;
	tt->since   = tj->since;      /* NO LONGER needed for outM since outM stuff moved to AFTER get a1.  If tt!=tj then write tj->since */
	tt->since0  = tj->since0;     /* copy tj traits into tt for evaluation of optimal action, assuming current order cancelled */
	tt->sincei  = tj->sincei;
	tt->rtime0  = tj->rtime0;     /* needed for outM */
	tt->pv      = tj->pv;         /* since do not want to change tj unless cancel and resubmit is indeed optimal        */	 
	tt->pvtype  = tj->pvtype;     /* if indeed cancel, then tj goes to heap, and tj's traits passed to new order via tt */
	tt->z       = tj->z;          /* unnecessary since tt,tj ALWAYS w/  z=0 */
	tt->buy     = 9;              /* tt->buy = 9 is flag for endog buy/sell */
	tt->p       = tj->p;
	tt->q       = tj->q;          /* tt->ret set below */
	tt->type    = tj->type;
	tt->tB      = tj->tB;         /* only some of these copied traits are relevant, but copied all  EXCEPT ->again  to be safe */
	tt->tH      = tj->tH;
	tt->tremble = tj->tremble;    /* may get overwritten below if tremble again */
	tt->aggress = nPlim;        tj->aggress = nPlim;   /* tj->aggress tracks execution frequency of NEW orders, by aggression of order measured on LL, HL grid */
	tt->v       = tj->v;                               /* tj->aggress = nPlim says IGNORE,  but tt->aggress will be reset later IF new order submitted */
	tt->vlag    = tj->vlag;
	tt->period  = tj->period;
	tt->n       = tj->n;          /* do NOT overwrite tt->again :  it determines arrival of next NEW trader */
#if nL>0
	for(p=0;p<nL;p++)  tt->L[p]= tj->L[p];
#endif

      }   /* end else tt != tj */

      /* ####  next trader to make decision is now established as either NEW or RETURNING  #### */
      /* if RETURNING then tj-> copied to tt-> and the order has been hypothetically cancelled  */
      /* Now obtain optimal new (or initial) action, a1.  If returning (ie, tj!=tt) then will   */
      /* use a1 to update beliefs of CANCEL.  Will then compare CANCEL with RETURN values.      */
      /* If indeed cancel, will use a1 as the replacement order.  Trembles considered too.      */

      byT[tt->type][tt->pvtype][1]++; /* count #decisions of any sort, by trader,PV type */

      if (CHECK && tt->rtime0 > realtime) printf("j= %u   tj  t= %p  since0= %8.3f  rtime0= %8.3f  realtime= %8.3f\n", j, tj,tj->since0,tj->rtime0,realtime);


      /* OLD LOCATION OF initial outM stuff */

      /* if (tt->pvtype==0 && bid1==-1 && ask1==nP)  DEBUG = 1; */

      if ( ask1 != Bt[nP] || bid1 != Bt[nPx1] ) {  /* Bt[nP,nPx1], M1->p  -->  init E(v), so Bt[] quotes must be accurate */
	printf("j= %u  bid1~Bt[nP+1]: %d %d   ask1~Bt[nP]: %d %d  B:",j,bid1,Bt[nPx1], ask1,Bt[nP]);
	for(p=0;p<nP;p++) printf(" %2d",Bt[p]);  printf("\n");
      }

      if (CHECK && (tt->type >= nT || tt->vlag > nLag)) { printf(" HEY: before tm, tt->type= %d  tt->vlag= %d\n", tt->type, tt->vlag);  DEBUG=9; }
      
      tm->q  = 3;  /* E(v) flag */   /* probably unnecessary: tm->q = 3 set earlier, should stay same */
      tm->z  = 0;                    /* small (1-share) traders only.   Large traders dealt w/ later. */
      tt->Ev = 0.0;                  /* E(current v) rel. to lastobs v --> tL,tH & mkt order payoffs. */
      if( nLag ) {                   /* update s->w for market orders by ANY TRADER TYPE with vlag >0 */
	for(tm->type =0; tm->type <nT; tm->type++) {   /* E(v) indep of pv --> pvtype NOT looped over */
          if( UPDATE_mkt[tm->type] ) InsertNewState = 'y';      /* Bt[nP,nPx1], M1->p  -->  init E(v) */
	  else                       InsertNewState = 'n';      /* unless beliefs fixed for this type */
	  if(vlagT[tm->type] && zT[tm->type]==0) {     /* tm->vlag = nLag already.  zT==0 --> 1-share */
	    tm->tB  = nBT[tm->type];                   /* tm->buy,p,q =0  already.      tm->p ignored */
	    tm->tH  = nHT[tm->type];
	    for(t1=0; t1< tm->type; t1++)    /* if 2 types have vlag && SAME bookT,nHT then SAME s->w */
	      if(vlagT[t1] && nBT[t1] == tm->tB && nHT[t1] == tm->tH && zT[t1]==0)
		break;               /* e.g. if differ only by rhoT[] have SAME market order beliefs! */
	    if (t1 == tm->type) {    /* no previous type matches  (vlag, nB, nH) --> no replication   */
	      s = GetState(tm,Bt);   /* tm->v already set via J list.    s->w = -tm->v if fully learn */
#if 0         /* print extreme E(v) when NONE informed, since should always have E(v)=0 */
	      HEY:  must MODIFY TO ACCOUNT FOR SYMMETRY
	      tL=0; tH=0;  for(p1=0; p1<nP; p1++) { if(s->B[p1]) tL++;  if(Bt[p1]) tH++; }
	      if( (tL==1 && s->B[11] == -1 ) || ( tL!=tH && tL==1) ) {
		printf("j=%10u  E(v): s->w~N= %5.2f %8u tm->v~q= %3d %2d  s->H[0~1][0]= %2d %2d s->B:",j,s->w,s->N,tm->v,tm->q, s->H[0][0], s->H[1][0]);
		for(p1=0; p1<nP; p1++)  if(s->B[p1]) printf(" %2d:%2d,", p1, s->B[p1]);  printf("  Bt: ");
		for(p1=0; p1<nP; p1++)  if(  Bt[p1]) printf(" %2d:%2d,", p1,   Bt[p1]);  printf("  J->n~RT:");
		J=J1;  while(J && realtime - J->RT < RT[0]) { printf(" %2d %5.2f", J->n, realtime-J->RT); J=J->next; } printf("\n"); }
#endif  
	      if(s->N<10) sNew[0]++;  /* relatively new s, or not visited often since last N=1 */  /* these 2 lines reveal degree to which E(v) are new */
	      if(s->N==1) sNew[1]++;  /* either new state, or not visited since last reset N=1 */  /* when compared to  nJ * #times "obtaining E(v)..." */
	      if(j==0) {  sNew[2]++;  /* #times E(v) beliefs obtained each decision */
	      printf(" obtaining E(v) beliefs for: tm->type= %d  UPDATE_mkt[tm->type]= %d \n", tm->type, UPDATE_mkt[tm->type] ); }

	      if(SYMM && H_signed && Ht[3][0]== -1 && nHT[t1]>1) q1 = -1;  else q1 = 1;  /* -1 if GetState uses SYMM (signed-order-flow based) */

	      if (DEBUG) printf("\n E(v) s->w~N = %8.5f  %u  s= %p\n", s->w, s->N, s);

	      if( UPDATE_mkt[tm->type] /* && s!=S */ )     /* InsertNewState='n' -->   if(s!=S) */ 
		s->w += ( -q1*tm->v - s->w ) / ++s->N ;    /* q1  = -1 if GetState uses SYMM */

	      s->NC++;  s->NN++;  s->Ew -=  q1*tm->v ;  s->w2 +=  tm->v * tm->v;
	      
	      if(tt->vlag && tt->tB==tm->tB && tt->tH==tm->tH ) /* OLD: if(tm->type==tt->type).  tt->z == 0 ALWAYS */
		tt->Ev = q1*s->w;        /* current decision maker needs this s->w.  q1 = -1 if GetState uses SYMM */

	      /* if(N0_v==0 && tm->tH && fabs(s->w + tm->v)>1e-7)
		 printf("j=%u  tt->Ev,v= %5.2f %3d  s->N= %u tm->q= %d  S= %d\n",j,s->w,tm->v,s->N,tm->q,s==S); */
	    } } } }

      CS[tt->type][tt->pvtype][10] += fabs( tt->Ev );          /* measures degree of usefullness of conditioning variables */
      CS[tt->type][tt->pvtype][11] += fabs( tt->Ev + tt->v );  /* measures degree to which still have uncertainty about v  */
      CS[tt->type][tt->pvtype][12] +=       tt->Ev + tt->v;    /* should be ZERO on average over all decisionmakers */

      if(UPDATE_PVT[tt->type][tt->pvtype]) N_s[22]++;  /* #decisions by traders for whom InsertNewState='y', to be compared with #NewStates */

      if(UPDATE_mkt[nT]==0) tt->Ev = 0.0;  /* not enough informed traders for informative Ht[].  Track E(v) beliefs above to confirm it. */

      if (CHECK && (tt->type >= nT || tt->vlag > nLag)) { printf(" HEY: after  tm, tt->type= %d  tt->vlag= %d\n", tt->type, tt->vlag);  DEBUG=9; }
      
      /* p1=0;  for(p=0;p<nP;p++) p1+= max(-Bt[p],Bt[p]);  if(p1==24  && tt==tj && ( (Bt[14]==2 && Bt[16]==22 && tt->pvtype==3) || (Bt[15]== -22 && Bt[17]== -2 && tt->pvtype==0) )) { printf("\n");  DEBUG = 1; } */

      InsertNewState = 'n';       /* global:  tells GetState() to NOT insert new state (since states may not actually be chosen) */
      tm->CS= tt->pv + tt->Ev + tt->v;   /* E(adjusted pv), used often below, so do once.  tm->CS used temporarily. */
      
      tL = LL;  tH = HL;          /* ticks at which limit orders are permitted */                    /* HEY: could set grid lower for low pv, higher for high pv. */
      if(tt->vlag) {              /* center permitted ticks around E(v) */                           /* may enable lower nPlim w/out restricting desired actions. */
	u = tt->Ev + tt->v;       /* if H fully reveals current v then tt->Ev will be -tt->v */
	p1= (short int)floor(u);  /* p1 is E(current v), relative to lastobs v, rounded to nearest tick */
	if (u - p1 > 0.5)  p1++;  /* HEY: tL, tH based on mean (belief) of v, but t->v to shift B[] in GetState() is lagged v NOT the belief of v. */
	tL = LL + p1;             /* tick Low  for which limitorder is valid.       Allow orders +- (HL-LL+1)/2 */
	tH = HL + p1;;            /* tick High for which limitorder is valid.       ticks from rounded E(v).    */
	if(tL < 0 )   { tL= 0;   tH= max(tH,0); }
	if(tH > nP_1) { tH=nP_1; tL= min(tL,nP_1); }         /* many quick jumps can lead to tL, tH off nP grid */
      }
      

#ifdef Type2_No_limitorders
      if (tt->pvtype== 2  && tt->type==1) { tL=PVmu+1;  tH= PVmu; }   /* No limitorders allowed for speculators */
#endif

      if( CHECK && N0_v==0 && tt->tH && fabs(tt->Ev + tt->v) > 1e-7 ) { printf("j=%u  tt->Ev=%5.1f  tt->v= %2d  tL~tH= %2d %2d  LL~HL= %2d %2d\n",j,tt->Ev,tt->v,tL,tH,LL,HL);  DEBUG=1; }

      NNs = 0;  /* #states NN>0 */

      Get_a1:          /* if(j>1917252) DEBUG = 1; */

      if (DEBUG || NNs>98) {
	  printf("\nj=%u  tt->pv~E(pv)= %4.2f %4.2f  type=%d  since0= %5.2f  period= %u  p= %d  q= %d  tt->v~Ev= %2d %5.2f  tL~tH= %2d %2d  Ht[][1]= %2d %2d %2d %2d  bid1~ask1= %d %d  Bt:",
		 j,tt->pv,tm->CS,tt->type,tt->since0,tt->period,tt->p,tt->q,tt->v,tt->Ev,tL,tH,Ht[0][0],Ht[1][0],Ht[2][0],Ht[3][0],bid1,ask1);
	  for(p=0;p<nP;p++) if(Bt[p])printf(" %2d:%3d,",p,Bt[p]);  printf("\n"); }
      
      /* tt->since   = 0.0;  <--  NO LONGER DO THIS.  It overwrites value copied from tj->since */

      tt->tremble = max( tt->tremble, 2 * (RandU < PrTremble) );  /* tt->tremble = 2 for "just trembled", changed to 1 when added to Traders[] */

      /* This looping over possible actions could be simplified to the OLD single-share version from very 1st coding attempt */
      /* This looping is substantially more complicated when allowing for 2-share traders */
      sa = NULL;  s_ = NULL;
      a1 = 0;  v0 = V0;  v = v0;      /* Initial best action is No Action with NO REVISIT:   a1=0  w/ surplus v */
      tt->ret = 0;  tt->q= 1;         /* tt->ret is Flag for NEW arrival (or treated as such).   tt->q ignored */
      aTrem[0]=0; aTremN= 999999999;  /* aTrem[0] is #valid actions, aTremN is initial min(s->N). actions are [1,...,2*nPlim] */
      pmin= -nP;  pmax= nP;           /* range of p1, p2 if ENDOG buy/sell, though limit orders only between tL, tH.*/
      m2 = 0;     a2 = 0;             /* NO a1=0 --> initial a1 already set differently for new, returning trader.  */
      v_ = -99.0; a_ = -99;           /* v_ = value of 2nd best option, a_, written to file to check margin of best */
      ReHash = 0;

      if(nQ==8 && tt->n > 1 && nL==0)  tt->ret = 4;  /* use different beliefs for returning and new traders */

      for (p1=pmin; p1<=pmax; p1++) {  /* p1<0 for sell at tick -p-1,  p1>0 for buy at tick p-1,  p1=0 for no action */

	/* for (p1=pmax; p1>=pmin; p1--) { */ /* see if going from high to low p1 changes preference by speculators for lim sells over lim buys */

	if (p1==0) {                  /* non-order with revisitation, with option value returned by GetState().     */
	  b1=0; t1=nP; v1=999.9; }
	else {                        /* p1==0 & v0 != V0  is a NonOrder.           p1 == 0 & v0 == V0 is Leave Mkt */
	  b1= -9;                     /* b1 is tt->buy for share 1.  If b1= -9 when at p2 loop, then p1 not valid.  */
	  if (p1>0) {                 /* buy action */
	    t1 = p1-1;                /* cannot use tick = abs(p) since 0 = -0 same for buy at 0 as sell at 0.      */
	    v1 = tm->CS - t1;         /* payoff if immediate execution:   tm->CS = E(pv) = tt->pv + tt->Ev + tt->v  */
 	    if(v1>0.0 && t1==ask1)  b1 = 1;  /* valid market buy */
	    else {
	      if(v1>0.0 && t1<ask1 && t1>=tL && t1<=tH ) {  /* valid limit buy (less than Ask).  v1>0 avoids needless check of neg.payoff limits */
		v1= 999.9;  b1=1;   } } }                   /* b1=1 indicates buy, increase Bt, v1=999.9 is limit flag */
	  else {                          /* p1 < 0 (sell action) is last case */
	    t1 = -p1 -1;
	    v1 = t1 - tm->CS;             /* payoff if immediate execution:   tm->CS = E(pv) = tt->pv + tt->Ev + tt->v  */
	    if(v1>0.0 && t1==bid1) b1=0;  /* valid market sell */
	    else {
	      if(v1>0.0 && t1>bid1 && t1>=tL && t1<=tH ) {  /* valid limit sell (more than Bid) */
		v1= 999.9;  b1=0;   } } }                   /* implicit else --> t1 is INVALID, indicated by b1 still = -9 */
	}                    /* end else p1 != 0*/
	if (b1 != -9) {      /* p1 is valid action */
	  if (v1 > 999.0) {  /* get E(surplus) of limit order, OR Non-Order with revisit option */
	    tt->buy= b1;    if( ReHash * p1 > 0) ReHash = 0;  else ReHash = 1;  /* if 1, GetState recomputes Hash,sB,H.  ==1 if ReHash and p1 same sign */
	    tt->p  = t1;

#ifdef Type2_No_limitorders
	    if(p1==0 && tt->pvtype==2) {  /*  assume tt->type is either 1 or 0 one of which submits ONLY NonOrders */
	      tt->type = 1 - tt->type;  s = GetState(tt,Bt);   wNon[tt->type] += s->w;  wNon[2]+= 1.0;   /* get payoff of nonorder for "other" type */
	      tt->type = 1 - tt->type;  s = GetState(tt,Bt);   wNon[tt->type] += s->w;  }  /* NOTE: the next GetState() is identical, but not worth complicating code to avoid repeat */
#endif

	    s = GetState(tt,Bt);

#ifdef CheckSEGV  /* indeed, the re-call also yields SEGV, so skip it.  I wonder if redoing the whole get_a1: loop would also repeat the SEGV ?? */
	    if(s->w < -999.8) {
	      /* printf(" re-call GetState() caught SEGV ...");   s = GetState(tt,Bt);   printf(" 2nd attempt yields s->w = %8.4f for s= %p\n", s->w, s); */
	      printf("\nj=%u  tt->pv~E(pv)= %4.2f %4.2f  type=%d  since0= %5.2f  period= %u  p= %d  q= %d  tt->v~Ev= %2d %5.2f  tL~tH= %2d %2d  Ht[][1]= %2d %2d %2d %2d  bid1~ask1= %d %d  Bt:",
		     j,tt->pv,tm->CS,tt->type,tt->since0,tt->period,tt->p,tt->q,tt->v,tt->Ev,tL,tH,Ht[0][0],Ht[1][0],Ht[2][0],Ht[3][0],bid1,ask1);
	      for(p=0;p<nP;p++) if(Bt[p])printf(" %2d:%3d,",p,Bt[p]);  printf("\n");
	      DEBUG = 1;
	    }
#endif
	    if(s!=S) N_s[23]++;   /* GetState() shifts Bt by tt->v positions.  N_s[23] counts #checked s already exist */
	    u = s->w;       	ReHash = p1;             /* TrembleNew=0 is good idea if optimistic S->w, bad idea if not */


	    if(NNs < 99) s->NC++;                        /* 99 is flag for repeating get_a1:   pvtype, bid=ask stuff is to avoid symmetric states.  need to adjust for nB>2 */
	    if(s!=S  &&  s->NN - s->NT > 0  &&  s->NN >= s->NT  &&  Ht[3][0]!=0  &&  (tt->pvtype != nPV_1 - tt->pvtype || bid1!=nP_1-ask1 || tt->buy==0) )    NNs++;

	    /* Ht[3][0] = 0 when no recent transactions, in which case Non-Orders do NOT use SYMM --> s->NC will be lower for NonOrders since split, and may get different optimal actions since */

	    if(s==SS && DEBUG == 0) {
	      printf("\n checked s==SS  j=%u  tt->pv~E(pv)= %4.2f %4.2f  type = %d  since0= %5.2f  period= %u  p= %d  q= %d  tt->v~Ev= %2d %5.2f  tL~tH= %2d %2d  Ht[][1]= %2d %2d %2d %2d  bid1~ask1= %d %d  Bt:",
		     j,tt->pv,tm->CS,tt->type,tt->since0,tt->period,tt->p,tt->q,tt->v,tt->Ev,tL,tH,Ht[0][0],Ht[1][0],Ht[2][0],Ht[3][0],bid1,ask1);
	      for(p=0;p<nP;p++) if(Bt[p])printf(" %2d:%3d,",p,Bt[p]);  printf("\n");
	      DEBUG=1;  /* p1= 9999;  break; */     /* will restart loop with DEBUG on */
	    }
	    
	    /* 
	    if( ( s->B[0]==30 && s->B[1]==31 &&  s->B[2]==5 && s->B[3]==1 &&  s->B[4]==1 &&  s->B[5]==7 && *(s->H[0]+0)==0 && *(s->H[0]+1)==-31) ||
		( s->B[0]==31 && s->B[1]==32 &&  s->B[2]==1 && s->B[3]==5 &&  s->B[4]==7 &&  s->B[5]==1 && *(s->H[0]+0)==0 && *(s->H[0]+1)== 31 ) )
		DEBUG = 1; */

	    /* if (s==SS && DEBUG==0) { DEBUG=1; printf("\n"); } */  /* to check particular state SS */
#ifdef LimCost
	    if (p1) u -= LimCost;    /* cost per limit order submission.  p1==0 is NonOrder, and Market Orders do not enter here */
#endif
	    /* next line used to be    u > TrembleNew  but now treating init s->w as WITHIN TrembleNew of true payoff.  ie, u+TrembleNew is optimistic  */
	    /* Of course, optimal v is not known yet (since in middle of looping) so will need to check this again when actually tremble */
	    if(tt->tremble==2 && (s!=S || (v-u < TrembleNew && u>.1)) )    /* used to require (tt->pv + tt->Ev + tt->v -t1) > V0, but now.  */
	      { aTrem[0]++;  aTrem[aTrem[0]] = p1; }                       /* (tt->pv + tt->Ev + tt->v -t1)>0 required to get here (via b1 != -9) */
	    /* HEY:  do NOT tremble to lowest s->N (via next line).  Would always tremble to SAME s when UPDATE_maxPVT = 0.0 since never s->N++ ! */
	    /*  if(s->N < aTremN) { aTrem[0]=1; aTrem[1]= p1; aTremN= s->N; }  else { if(s->N == aTremN) { aTrem[0]++; aTrem[aTrem[0]]= p1; } } */
#if useNNtt>0		
	    if(s!=S && tt==tj && tt->tremble==0) { /* WriteFiles() checks whether s is optimal ALWAYS or NEVER (for NEW arrivals), as needed for convergence */
	      /* if (s->NNtt && s->NCtt > s->NNtt) { DEBUG=1; printf("\n"); } */    /* <-- PRINTS evidence of ALWAYS/NEVER not holding due to mkt/non orders */
	      s->NCtt++;  }                        /* ALWAYS/NEVER only strictly holds when market orders && nonorders excluded (currently not done) */
#endif
	  }
	  else {                    /* else v1< 999.0 --> market order, p1 !=0, v1= surplus */   /*  <--- NO LONGER used for p1=0                */
	    if(p1) u = v1;          /*             OR --> no action,    p1 ==0, v1 = v0     */   /*  <--- p1=0 is now a Non-Order with v1= 999.9 */
	    else   u = v0;  }       /* no action has payoff v0 (or v1 since v1= v0 above)   */   /*  <--- w/ option value returned by GetState() */
	  /* if ( 1 && v1 != 999.0 && p1)    printf("j=%6u  bid1= %2d  ask1= %2d  t1= %2d  p1= %2d  tt:pv=%6.2f  vlag= %1d  v= %2d  lagged_v= %2d  v1= %6.2f  v2= %6.2f  u= %6.2f \n", j, bid1, ask1, t1, p1, tt->pv, tt->vlag, tt->v, tm->v, v1, v2, u);
	   */
	  if ( u > v  /* + 1e-8 */ ) { 
	    v_ = v;  a_ = a1;  /* v_ is value of 2nd best option, a_ */
	    
	    if ( N0_v==0 && tt->tH && b1 && (double)t1 > tt->pv && s!=NULL && tt->Ev == (double)tt->v)
	      printf("j=%u  new= %d  bid1~ask1= %2d %2d  t->v= %2d  pv=%5.1f agg buy at  t1= %d v~u= %5.2f %5.2f  N~NN= %u %u \n",j,tt==tj, bid1,ask1,tt->v,tt->pv, t1, v,u, s->N,s->NN );
	    
	    v = u;	a1 = p1;   BestNew = (s==S);  /* NEW optimal action */

	    s_= sa;     sa = s;                       /* pointers to best, 2nd best s.  will be global S if new since InsertNewState = 'n' */
	    if( ( a1<0 && (-a1-1)==bid1 ) || ( a1>0 && (a1-1)==ask1 ) )  sa = NULL;    /* market order --> no state associated with action */
	  }
	  else { if (u > v_)  { v_ = u;  a_= p1; } }  /* v_ is value of 2nd best option, a_ */
	  
	  if (DEBUG || j<2 || NNs > 98)  { /* printf("checked  p1=%4d  b1=%2d t1=%3d t1-tv=%3d q1=%3d  b2=%2d t2=%3d q2=%3d  a1~a2= %3d %d  v1=%6.2f  v2=%6.2f  u=%6.3f  v=%6.3f  v0=%6.3f  new~N= %d %8u  s= %p  ba=%2d %2d ", p1, b1, t1, t1-tt->v, q1, b2, t2, q2, a1,a2, v1, v2, u, v, v0, s==S, s->N, s, bid1, ask1); */
	    printf("checked j=%u tL~tH= %2d %2d  pv= %4.1f p1=%4d b1=%2d t1=%3d t1-tv=%3d  a1~a_= %3d %3d  v1=%6.2f  unow= %6.3f  u=%6.3f  v=%6.3f  v-v_=%8.1e new~N~NC~NN~NT= %d %9u %6u %6u %6u s= %12p  ba=%2d %2d",
		   j, tL,tH, tt->pv, p1, b1, t1, t1-tt->v, a1, a_, v1, (t1-tm->CS)*(1-2*b1)*(p1!=0), u, v, v-v_, s==S, s->N, s->NC, s->NN, s->NT, s, bid1, ask1);
	    printf("  sB:");  for(p=0;p<nB;   p++) printf(" %3d", s->B[p] );
	    printf("  H:");   for(p=0;p<nHnRT;p++) printf(" %3d", *(s->H[0]+p) );
	    /* printf("  L:");   for(p=0;p<nL;   p++) printf(" %3d", s->L[p] ); */
	    /* for(p=0;p<nP;p++)  if(Bt[p])   printf(" B[%2d]= %3d,",p,Bt[p] ); */    printf("\n");   fflush(stdout); }
	}  /* else b1= -9 --> ignore INVALID ACTION */
      }  /* for p1 */      ReHash = 1;   /* ALL future calls to GetState() will compute Hash,sB,H.   ReHash = 0 ONLY occurs within above loop. */

      if(p1==9999)  goto Get_a1; /* s==SS so restart with DEBUG on */
      
      /* NNs stuff is LOWER BOUND of #states for which multiple states found optimal, since NN not tracked for MKT orders */
      if(NNs>98) NNs = 0;                  /* NNs = 99 is flag for just repeated Get_a1.  Set NNs=0 so don't repeat again */
      if(NNs>1 &&  v - v_ > 1e-8 && (1-(tt->pvtype==nPV_1-tt->pvtype && tt->tH>0 && Ht[3][0]!=0))) {   /* v-v_ < 1e-8 causes randomize. Ht[3][0]=0 is NO recent transaction, which has no SYMM for PV=0 */
	Ntremble[20]++;   if( UPDATE_maxPVT < 1e-8  && 1 ) { NNs = 99;  DEBUG = 1;  goto Get_a1; } }   /* More than ONE state has been deemed optimal */

#ifdef Print_Speculator_Unaggressive
      if (tt->pvtype == 2 && (tH == -a1-1 || tL == a1-1 ) && DEBUG==0 && a_==0 && v-v_ > .03)
      /* if (tt->pvtype == 2 && bid1 == PVmu-1 && ask1== PVmu+1 && j>1  && DEBUG==0 ) */
	{ printf("\n");   DEBUG=1;   goto Get_a1; }     /* limit sell at highest possible tick or limit buy at lowest.  j=1 triggers  printf("checked...") */
#endif

      /* if (v>9.0) printf("j=%u %u  tj->p= %d  pv~type= %d %d  since~0= %5.2f %5.2f  v= %5.2f a1=%3d bid~ask= %2d %2d tm->v~pv=%3d %5.2f unow= %5.2f\n", j,tt->period,tj->p,tt->pvtype,tt->type,tt->since,tt->since0, v, a1,bid1,ask1, tm->v, tt->Ev, (1-2*tj->buy)*(tj->p-tt->pv)*(a1==40) + (1-2*(a1>0))*( max(a1,-a1)-1 - tt->pv)*(a1<40) ); */

      if (out2!=NULL && j < out_2nd_best)  fprintf(out2, "%6.4f %6.4f %d %d %d %d %d %d \n", v, v_, tt->pvtype, tt->type, a1, a_, bid1, ask1);

      if ( ( a1<0 && (-a1-1)==bid1 ) || ( a1>0 && (a1-1)==ask1 ) ) {    /* 1st best is market order (buy or sell) */
	VV[tt->type][tt->pvtype][0] += v ;
	VV[tt->type][tt->pvtype][1] += v_;
	VV[tt->type][tt->pvtype][2] ++;    }
      else {
	if ( ( a_<0 && (-a_-1)==bid1 ) || ( a_>0 && (a_-1)==ask1 ) ) {  /* 2nd best is mkt order */
	  VV[tt->type][tt->pvtype][3] += v ;
	  VV[tt->type][tt->pvtype][4] += v_;
	  VV[tt->type][tt->pvtype][5] ++;    }
	else {                                                          /* 2nd best is lim order */
	  VV[tt->type][tt->pvtype][6] += v ;
	  VV[tt->type][tt->pvtype][7] += v_;
	  VV[tt->type][tt->pvtype][8] ++;    }
      }
      if (a1==0 && tt==tj) {                                            /* 1st best is non-order, NEW tt only */
	VV[tt->type][tt->pvtype][9]  += v ;                             /* 2nd best is lim OR mkt */
	VV[tt->type][tt->pvtype][10] += v_;
	VV[tt->type][tt->pvtype][11] ++;    }


      if (a1) {                               /* ignore Non-Actions and Non-Orders (both a1==0).  INCLUDES a1 ignored by RETAINED (unless move this code) */
	u = tt->Ev + tt->v + PVmu;            /* E(v) on current grid, which is the grid on which the tick (a1-1, or -a1-1) is on */
	if( a1>0 ) u = a1-1 - u;              /* aggressiveness of buy order, PRE-TREMBLE */
	if( a1<0 ) u = u + a1+1;              /* aggressiveness of sell, converted to buy basis, == -( u - (-a1-1) ) */
	if (tj==tt) {
	  CS[tt->type][tt->pvtype][14] += u;  /* action upon 1st arrival */
	  CS[tt->type][tt->pvtype][15] ++  ;  /* divide by count later   */
	}
	CS[tt->type][tt->pvtype][16] += u;    /* action upon any arrival */
	CS[tt->type][tt->pvtype][17] ++  ;    /* divide by count later   */
      }
      
      v_ = v - v_;    /* NTremble[8]/nJ gives share of decisions based on difference of only .01 */
      if( v_ < .01 && v_ > 1e-5 ) { Ntremble[11+(tt==tj)]++;   byT[tt->type][tt->pvtype][11]++; }
      if( v_ < 1e-4  ) {            Ntremble[13+(tt==tj)]++;   byT[tt->type][tt->pvtype][12]++; }   /* could change 1e-4 to 1/SCALE_v_v so get count of RANDOMIZATIONS */

#if 1
      if(v_ < 1e-8 && RandU < .5) { p= a1;  a1= a_;  a_= p;  v_ = -v_;   t1 = -99; }  /* randomly choose among 2 actions with near identical payoffs */
#endif
#if 0
      if (sa) {
	  if (bid1 == nP-ask1)  h= sa->NC/2;  else  h = sa->NC;  /* but s->NN does not yet include traders[] */
	  if( h > 1  && sa->NN!=h )  /* state not chosen, but usually is ...  why the change */
	      printf("j= %u  a1= %3d, a_= %3d  sa->N~NC~NN=%10u %8u %8u  v~sa->w= %7.5f %7.5f  v_= %9.1e  sa= %p t1= %3d \n", j, a1, a_, sa->N, sa->NC, sa->NN, v, sa->w, v_, sa, t1); 
      }
#endif

      if ( DEBUG  /* || (v_ < 1e-8 && tt->pvtype ==0 ) || ((j%1)==10000 && j>650 */ ) {
	m1 = (short int) ( ( a1<0 && (-a1-1)==bid1 ) || ( a1>0 && (a1-1)==ask1 ) );  /* m1=1 if market order (buy or sell) */
	printf("comparisons DONE. j=%4u  tt->period= %u  tt:pv~E(pv)= %4.2f %4.2f  nB= %d  vlag~v= %d %d  tt->Ev= %8.4f  (0 or tj->buy~p~q= %d %d %d)  m1= %d  a1~a_= %3d %3d  v=%5.2f v-v_= %7.1e  bid~ask= %2d %2d  ",
	       j, tt->period, tt->pv, tm->CS, tt->tB, tt->vlag, tt->v, tt->Ev, tj->buy*(tj!=tt), tj->p*(tj!=tt), tj->q*(tj!=tt), m1, a1, a_, v, v_, bid1, ask1 );
	for(p=0;p<nP;p++)  if(Bt[p]) printf("B[%d]= %d, ",p,Bt[p]);  printf("\n");
      }

      if(SCALE_v_v > 10.0) {            /* Randomize over nearly equal 1st, 2nd best () */
	u = v_ * SCALE_v_v ;            /* scaling controls range over which probabability varies from .5 to 1.0.  See comment after #define SCALE_1st_2nd */
	if (u<12) {                     /* u=12 --> Prob(1st) = 0.99999385582540.  This  if()  avoids unnecessary calls to exp() when 1st-2nd is not small */
	  u = exp(u); u = u/(1.0+u);
	  if( u<.99 ) Ntremble[6+(tt==tj)]++;  /* "attempted" randomizations: 6=Returning  7=New */
	  v1 = RandU;
	  if( v1 > u ) {                /* switch to 2nd best */
	    p= a1;  a1= a_;  a_= p;
	    Ntremble[8+(tt==tj)]++;     /* next line changes v to value of 2nd best ( v_ = v-v_ above, so do not simply set v = v_ ) */
	    if(UPDATE_1st==0) v= v-v_;  /* ELSE use 1st best to update tj->s->w (returning trader). Similar to NOT using trembled actions for updating tj->s->w */
	    v_ = -v_;                   /* hence, v_ < 0 indicates 2nd best chosen.  (could also use exp(sum(log(v)+log(v_)))) */
	    /* DEBUG = 1; */
	  } }
	if(DEBUG) printf("j=%u  Randomized: Pr(1st)= %10.8f  RandU= %10.8f  tt:period= %u  pv= %4.2f  vlag= %d  nB= %d  tt->s= %p  a1~a_= %3d %3d  v=%5.2f  v-v_= %9.1e\n", j, u, v1, tt->period, tt->pv, tt->vlag, tt->tB, tt->s, a1, a_, v, v_ ); 
      }    /* end randomization */

      m1 = (short int) ( ( a1<0 && (-a1-1)==bid1 ) || ( a1>0 && (a1-1)==ask1 ) );  /* m1=1 if market order (buy or sell) */

      /* #######################    returning trader   ########################
	 Regardless of CANCEL/RETAIN choice update belief of  CANCEL  using a1, v  (except for Non-Orders which are automatically cancelled)
	 Compare belief of  RETAIN  to  CANCEL (post-update).  Choose higher action, though consider tremble to RETAIN.
	 If CANCEL, then use a1 as (pre-trembles) replacement order.

	 Regardless of action, update belief of unexecuted  tj->s->w  with discounted continuation value.
	 Continuation value is v associated with a1 if CANCEL (though could instead use belief of Cancel instead of realized value of a1).
	 Continuation value is belief of retained order, if RETAIN is optimal.
      */

      if (tj!=tt) {
	
	/* this CHECK triggers exit(8) if RANDOMIZED to 2nd best a1=nP (RETAINED order) and UPDATE_1st == 1.  Hence, turn it off. */
	/* if (CHECK && a1==nP+9 && tt->s!=S && fabs(tt->s->w - v) > 1e-7) { printf("j= %u  retained tt->s= %p  s->w= %8.5f does not match v= %8.5f, s->w-v= %9.1e  a1~a_= %d %d  abort\n", j, tt->s, tt->s->w, v, tt->s->w-v, a1,a_);  exit(8); } */

	if ( UPDATE_s0 && tj->s0 != tj->s) { printf("j=%u  tj: s: N~NN= %p %u %u  s0: N~NN= %p %u %u", j, tj->s, tj->s->N, tj->s->NN, tj->s0, tj->s0->N, tj->s0->NN );
	  
	for (p1=0;p1<nB;p1++) if(tj->s->B[p1]!=tj->s0->B[p1]) printf(" B[%d]= %d %d ", p1,tj->s->B[p1], tj->s0->B[p1]);  printf("\n");        DEBUG=1;   }

	if( CHECK && fabs(tj->since0 + tj->rtime0 - realtime) > 1e-5 )  	  /* optional elapsed realtime check */
	  printf("j= %u  rt= %12.3f  rt0+since0-rt= %10.1e  since0= %8.3f  change to market order\n",j,realtime, tj->rtime0 + tj->since0 - realtime, tj->since0);

	
	if (tj->p < nP) {  /* NOT a Non-Order, so RESTORE hypothetically cancelled order to evaluate RETAIN/CANCEL */
	  if (tj->buy) { Bt[tj->p] += 1;  bid1 = max( bid1, tj->p ); }
	  else         { Bt[tj->p] -= 1;  ask1 = min( ask1, tj->p ); }
	  Bt[nP]= ask1;  Bt[nPx1]= bid1;  }
	
	tj->Ev = tt->Ev;   /* GetState() shifts Bt by tj->v, uses tj->Ev to initialize NEW s->w via eUB[].  tj->v already updated */
	
	/* get RETAIN value  UNLESS  tj->p beyond tL, tH (nP>tH so cancel Non-Orders)  OR  payoff from immediate execution < 0 */
	if(  /* EXOG_CANCEL || */   tj->p==nP || tj->p < tL || tj->p > tH || ( (tj->buy) == (tm->CS < tj->p) )) { 
	  s=S;  S->w = -99.9;             /* automatically CANCEL.   tm->CS is expectation of private value on current grid */
	  if (tj->p!=nP) {                /* retained order is "invalid" : either neg. CS or beyond permitted range for limit orders */
	    if(UPDATE_PVT[tj->type][tj->pvtype]) { sNew[4]++;  if(tj->s->N==1) sNew[3]++; }       /* will later report  #N==1 / #valid  retained order */
	    else {                                 sNew[6]++;  if(tj->s->N==1) sNew[5]++; }  }    /* considerations (though not necessarily taken) */   
	  u = v;                          /* non-orders automatically cancelled -- don't need belief of either retain, or cancel */
	}
	else {	                          /* get legit CANCEL value, and then RETAIN value */
#ifdef CancelKnown
	  u = v;                          /* treat value of replacement order as KNOWN */
#else     /* else, have beliefs of replacement value due to uncertainty about post-cancel state */
	  /* if nB=6, when only order at a quote, then cancelling reveals info (next quote) so need s->w.  else post-cancel state is known */
	  /* if nB=2, post-cancel state is known whenever current order is not at a quote */
	  /* next line assumes nB=6 OR nB=2 */
	  if ( (    tj->tB==6 && ( (tj->p==bid1 && Bt[bid1]==1) || (tj->p==ask1 && Bt[ask1]== -1) ))
	       || ( tj->tB==2 && (  tj->p==bid1                 ||  tj->p==ask1 ) ) ) {
	    tj->ret = 2;                                    /* ret=2 for CANCEL. also use current tj->p,buy.   u  stores CANCEL payoff */
	    if(UPDATE_PVT[tj->type][tj->pvtype]) {	    /* update belief of CANCEL with UNDISCOUNTED v since no time elapses */
	      InsertNewState = 'y';  s= GetState(tj, Bt);
	      InsertNewState = 'n';   
	      if(s->w < -99.0) { s->w= v;   s->Pw= v; }     /* initialized s->w is -99.9 as signal to instead use  v  as init   */
	      else {                                        /* s is NOT new.  Compare to v, and update CANCEL s->w */
		wCancel[0] += s->w; 
		wCancel[1] += v;   u = fabs(s->w - v);
		wCancel[2] += u;
		if (u < 999.9)	wCancel[3] = max(wCancel[3], u);  /* for some reason, max() is reporting  Inf, so print large ones */
		else   printf("HEY: Cancel |s->w - v| = %6.2f > 999.9   s->N~w= %u %6.3f  v= %6.3f  a1= %d \n", u, s->N, s->w, v, a1);
		wCancel[4] ++; 
		wCancel[5] += (double)(v > s->w); 
		s->w += ( v /* - cT[tj->type] * tj->since */ - s->w ) / ++s->N ;
	      }
	      u= s->w;   /* u stores CANCEL value */
	    }       /* end if update */
	    else {  /* could also do wCancel stuff here, if desired.  NOTE: InsertNewState = 'n' already so new state will have s==S */
	      s= GetState(tj, Bt);   if (s==S)  u= v;   else  u= s->w;   /* if CANCEL state is NEW use a1 E(payoff)= v */
	    }  /* printf("CANCEL s->w = %6.2f  v= %6.2f  N= %u \n", s->w, v, s->N); */

	    s->NC++;  s->NN++;  s->Ew+= v;   s->w2+= v*v ;  /* updating with v, which is expected payoff (even if market order and uninformed) */
	    u = max( u, 0.0);  /* cancel value is  u = max(u,0) should be unnecessary, but be safe */
	  }
	  else {
	    u = v;             /* CANCEL "beliefs" not needed since post cancel state is known even without cancelling */
	    if(DEBUG) printf("cancel state known:  u=v  = %6.4f\n", u);
	  }
#endif
	  /* now get RETAIN value.  NOTE:  Retain value same as NEW if currently KNOW tj is the ONLY order at his tick, but get anyway since this is rare */
	  tj->ret = 1;  s= GetState(tj, Bt);   s->NC++;    if(s==S) { tj->ret = 0;  s= GetState(tj, Bt); }  /* if new s, then use new order that matches retained order (ie, tj->ret = 0 ) */
	}
	
	/* if (DEBUG)  printf("s->w= %8.4f  s==S: %d  ret= %d\n", s->w, s==S, tj->ret); */

	/* u is value of CANCEL,  s->w is value of RETAIN.    correct use of  tt->tremble  NOT  tj->tremble */
	if ( s->w > u || (s->w > -99.0 && tt->tremble==2 && (tj->ret==1 || (u - s->w < TrembleNew && s->w > 0.1 )))) {  /* tj->ret set to 0 if using NEW as init value of RETAIN.  so tj->ret==1 is like s!=S */

	  if(DEBUG) {  printf("j= %u  s->w= %6.4f  u= %6.4f  tt->tremble= %d  tj->ret= %d  m1=%d \n",j, s->w,  u,  tt->tremble, tj->ret, m1 ); }

	  a1 = nP+9;                           /* nP+9 is Flag for RETAIN */
	  if (tt->tremble==2) {
	    if (s->w > u)  tt->tremble = 0;    /* avoid reporting tremble=2 in outM, since tremble was NOT needed to generate RETAIN */
	    else {                             /* tremble indeed generated the RETAIN */
	      tj->tremble= 1;                  /* tt->tremble = 2 reported to outM, so can set tj->tremble= 1 here */
	      byT[tj->type][tj->pvtype][10]++; /* tj->s updated with OPTIMAL v --> trembles indirectly impact s->w */
	      Ntremble[3 + tj->buy]++;         /* count RETAINED trembles in [3,4] for sells,buys */
	      if(tj->ret==0)   Ntremble[19]++; /* count trembles to NEW states */
	    } }
	}     /* else  CANCEL  */

	if(a1!=nP+9 && a1 && tj->p == max(a1,-a1)-1 && tj->buy == (a1>0) )    Ntremble[10]++;   /* count CANCEL & resubmit limit order at same price */
	
	if(s->w > u) tj->CS = s->w;            /* RETAIN is optimal.  tj->CS is temp storage of PRE-TREMBLE value to update tj->s->w & tj->s0 stuff  */
	else {                                 /* CANCEL is optimal.  use REALIZED order of replacement, not u, which is the E(payoff).  Differs if market order by uninformed */
	  if(m1) {
	    if(a1>0) tj->CS = tj->pv - ask1;   /* if replacement is a market order do not use v from above maximization since v is E(payoff).*/
	    else     tj->CS = bid1 - tj->pv; } /* instead update with  REALIZED  payoff (will be same as v if tj->lag = 0, since market order payoff known) */
	  else       tj->CS = v;    }          /* replacement is not  market order, so "realized" replacement value is v from above optimal choice by "new" trader */
	
	
	if (UPDATE_PVT[tj->type][tj->pvtype]){ /* see GetEq_large_code_NEW.c for possible "penalties" for unexecuted limitorders  */
	  tj->s->w += ( exp(rhoT[tj->type] * tj->since) * tj->CS /* - cT[tj->type] * tj->since */ - tj->s->w ) / ++tj->s->N ;  /* tj moved to heap below */
	  
	  /* if(tj->pvtype==2)  printf("j= %7u  tj->p~buy~type= %2d %d %d  tj->since~0= %6.2f %6.2f  tj->s->N~w~v= %8u %6.2f %6.2f  tj->CS= %6.2f \n",
	     j, tj->p, tj->buy, tj->type, tj->since, tj->since0, tj->s->N, tj->s->w, v, tj->CS );  */

	  N_s[24]++; }
	
#if UPDATE_Ne > 0
	if ( s->w > u  &&  tj->s->Ne * 8 < tj->s->NN)   /* to speed converg.  may want to "punish" states that never or rarely lead to direct execution */
	  tj->s->w *= ((double)tj->s->Ne + UPDATE_Ne) / (tj->s->NN+UPDATE_Ne);       /* ONLY USE WHEN INITIALLY CONVERGING */
#endif
	
	/* if(DEBUG) printf("     tj->period= %u  tj->pv= %5.2f  pvtype= %d  w= %5.2f  buy~p= %d %d  retained s->N,NN= %u %u  NCtt~NNtt= %u %u  NCtj~NNtj= %u %u  s= %p  s==S:%d\n",
	   tj->period, tj->pv, tj->pvtype, tt->s->w, tj->buy, tj->p, tt->s->N, tt->s->NN, tt->s->NCtt, tt->s->NNtt, tt->s->NCtj, tt->s->NNtj, tt->s, tt->s==S); */
	
	if( (m1 && a1!=nP+9) || UPDATE_s0 ) {           /* market order will be submitted after CANCEL (tj moved to heap below)  OR  UPDATE_s0==1 */
	  u= tj->CS * exp(rhoT[tj->type] * tj->since0)  /* - cT[tj->type] * tj->since0 */   ;   /* This one mkt order updates TWO s0 : the limit order being cancelled right now, and the limit order being executed */
	  if ( UPDATE_s0 || tj->tremble == 0) {
	    if ( 1 || RandU < .1 ) {
	      tj->s0->Ew+= u;                               /* cum  realized value.     NOTE: CS[][] update in processing a1 below uses tj->since0 so do NOT change it! */
	      tj->s0->w2+= u*u ;                            /* cum (realized value)^2 */
	      tj->s0->NN++;                                 /* This NN++ means sum(NN) in WriteFiles() will NOT = # Exec. limit orders. */
	    }
#if UPDATE_s0==0 && Ns0
	    for(p=2; p<= tj->n -1; p++) {                 /* tj->n -1 since just had n++ */
	      if ( 1 || RandU < .1 ) {
		v0 = tj->CS * exp(rhoT[tj->type] * tj->sinceN[p] ) /* - cT[t->type] * t->since0 */ ;
		tj->sN[p]->Ew+= v0;                         /* cum  realized value.     NOTE: CS[][] update in processing a1 below uses tj->since0 so do NOT change it! */
		tj->sN[p]->w2+= v0*v0 ;                     /* cum (realized value)^2 */
		tj->sN[p]->NN++;                            /* This NN++ means sum(NN) in WriteFiles() will NOT = # Exec. limit orders. */
	      } }
#endif
	  }  /* end if tj->tremble = 0 */
	  /* HEY:  if UPDATE_s0 = 0 then the above cumulations should only occur if tt->tremble = 0 (ie, NEVER EVER) trembled.  SAME for Ew updates elsewhere. */
	  
#if usePN>0
	  p= (short int) (tj->n==2);  /* tj->n = 2 for 1st return since already n++ */ 
	  if (tj->s0==SS) {  SSEw[2][p]+= u;  SSEw[3][p]++;     if(m1 && a1!=nP+9) {  SSEw[4][p]+= u;  SSEw[5][p]++; }  else { SSEw[6][p]+= u;  SSEw[7][p]++; } }
	  if (tj->si==SS && m1 && a1!=nP+9) { SiEw[2][p]+= tj->CS * exp(rhoT[tj->type] * tj->sincei);  SiEw[3][p]++; }
	  
	  if (tj->n==2 && tj->si!=tj->s0 && UPDATE_s0)  printf("j= %u  tj->n = 2 but tj->si != s0,  si~s0: %p %p\n", j, tj->si, tj->s0);
	  
	  if (tj->n==2 && fabs(u - tj->CS * exp(rhoT[tj->type] * tj->sincei)) > 1e-12 )  printf("j= %u  tj->n = 2 but u!= tj->CS discounted  %8.5f %8.5f\n", j, u, tj->CS * exp(rhoT[tj->type] * tj->sincei) );
	  
	  /* if (tj->si==SS && tj->n==3)  printf("0 %7.5f %7.5f %u %u %d \n", tj->s->w, u, tj->s->NN, tj->s->SSN, tj->n-1); */    /* in matlab, compare mean s->w and u.  MUST ALSO use printf of exec.limits */
#endif
	  /* if(tj->pvtype==2)  printf("j= %7u  tj->p~buy~type= %2d %d %d  tj->since~0~period= %6.2f %6.2f %7u  tj->s->N~w~v= %8u %6.2f %6.2f  tj->CS= %6.2f \n",
	     j, tj->p, tj->buy, tj->type, tj->since, tj->since0, tj->period, tj->s->N, tj->s->w, v, tj->CS ); */
	}

#if useNNtt>0
	/* EXCLUDE NonOrders (tp==nP, a1==0) from NCtj,NNtj since they use SYMM wrt PV --> same RETAINED state can have different resubmission options */
	/* EXCLUDE MktOrders (m1=1) since given limit order options can have different MKT beliefs depending on v history ??? */
        printf(" ####  Must Check That This Code Is Legit.  It has not been changed from  GetEq_large_code.c  ####");  exit(3);
	if(tt->s!=S && tt->tremble==0 && tj->p<nP && a1 && m1==0) { /* tt->s = GetState() of retained order  (before hypothetical cancellation) */
	  tt->s->NCtj++;                                   /* WriteFiles() checks whether s is optimal ALWAYS or NEVER (for returning tj) */
	  if(a1==nP+9) tt->s->NNtj++;                      /* tt->s->NNtj == either 0 or NCtj when converged */
	  if(UPDATE_PVT[tt->type][tt->pvtype]==0 && a1!=nP+9 && tt->s->NNtj >0) {  
	    printf("j=%u  order NOT retained but NNtj>0 :  pv~type= %d %d  tj->v= %2d  buy= %d  p= %2d  q= %d  a1~a_= %3d %3d  v= %5.3f  v-v_= %9.1e  NCtj~NNtj= %3u %3u  tt->s= %p ",
		   j,tj->pvtype,tj->type,tj->v,tj->buy,tj->p,tj->q,a1,a_,v,v_, tt->s->NCtj,tt->s->NNtj, tt->s);
	    for(p=0;p<nP;p++)  if(Bt[p]) printf("B[%d]= %d, ",p,Bt[p]);  printf("\n"); }
	  if(UPDATE_PVT[tt->type][tt->pvtype]==0 && a1==nP+9 && tt->s->NNtj < tt->s->NCtj) {
	    printf("j=%u  order  retained but NNtj<NCtj :  pv~type= %d %d  tj->v= %2d  buy= %d  p= %2d  q= %d  a1~a_= %3d %3d  v= %5.3f  v-v_= %9.1e  NCtj~NNtj= %3u %3u  tt->s= %p ",
		   j,tj->pvtype,tj->type,tj->v,tj->buy,tj->p,tj->q,a1,a_,v,v_, tt->s->NCtj,tt->s->NNtj, tt->s);
	    for(p=0;p<nP;p++)  if(Bt[p]) printf("B[%d]= %d, ",p,Bt[p]);  printf("\n"); }
	}
	if(DEBUG)  printf("j=%u returning with  buy= %d  p= %d  q= %d %d  tj->tremble= %d  a1~a_= %d %d  v= %8.5f  v-v_=%9.1e  tt->s->w= %8.5f  tt->tremble= %d  tt->s->NCtj~NNtj= %u %u\n",
			  j,tj->buy,tj->p,tj->q,tj->tremble, a1,a_, v, v_, tt->s->w, tt->tremble, tt->s->NCtj, tt->s->NNtj);
#endif
	if( a1 == nP+9 ) {                          /* do ALL processing of RETAINED order HERE, except for the outM stuff which is done later */
	  m1 = 0;                                   /* m1 may have been optimal if had cancelled, but tj NOT cancelling, so m1 = 0 */
	  if( UPDATE_PVT[tj->type][tj->pvtype] )  N_s[25]++;
	  tj->j = 0;                                /* reset since tj->j counts #jumps since last decision */
	  byT[tj->type][tj->pvtype][6]++;           /* count retained orders   */
	  if(( tj->ret == 0 || s==S )               /* RETAINED state was new */
	     && UPDATE_PVT[tj->type][tj->pvtype]) { /* and updating beliefs  */
	    InsertNewState = 'y';    tj->ret = 1;
	    tj->s = GetState(tj, Bt);
	    tj->s->w  = s->w;                       /* s->w is from tj->ret = 0 --> use NEW order as init value of RETAINED order */
	    tj->s->Pw = s->w;   }
	  else {
	    if( tj->ret == 0 ) tj->s = S;           /* s is for NEW order, but not updating (else satisfy above if) so use global S as dummy to avoid NN++ Ew+= for WRONG state s */
	    else               tj->s = s;           /* s from checking RETAIN is the right s (which already existed) */
	  }
#if Ns0
	  t1= tt->n;  if(t1 > Ns0-1) { printf("\n HEY: must increase Ns0\n");  exit(8);}
	  tj->sN[t1] = tj->s;   tj->sinceN[t1] = 0.0;  /* t1 will be at least 2 */
#endif
	  if(tt->tremble==2) {                      /* NOTE:  tj->s->w may have just been updated above, but no biggie.   Is this  v  used later?  */
	    v = tj->s->w;  tj->s->NT++; tj->tremble = 1; } /* tj->tremble= 1 was set above too, so could remove this one?? */
	  
	  if( UPDATE_s0 ) {  /* use CS to track next decision payoffs, not ultimate decisions.  Important for CS_non[] updates */
	    v1 = tj->s->w * exp(rhoT[tt->type] * tt->since0);     /* post-tremble discounted payoff.   okay to use tt since tj stuff was copied to tt */
	    CS[ tt->type][tt->pvtype][7 +(tt->tremble >0)]+= v1;  /* disc. CS  by  NEVER/EVER tremble */
	    byT[tt->type][tt->pvtype][13+(tt->tremble >0)]++;     /* count traders NEVER/EVER tremble */
	    if(tt->tremble==0) { 
	      CS[tt->type][tt->pvtype][9]+= v1*v1;        /* squared disc. CS  by  NEVER tremble */
	      CS[tt->type][tt->pvtype][13]+= tt->since0; }
	    tj->tremble= 0;                               /* do not bother tracking whether EVER trembled, only whether JUST trembled */
	    tj->since0 = 0.0;	   /* tt->tremble is still 2 if just trembled.  tj->tremble is 1 if ever trembled, unless UPDATE_s0 = 1 in which case tj->tremble = 0 always */
	    tj->rtime0 = realtime;                        /* outM prints tt->since stuff, so okay to change tj->since stuff now */
	    tj->period = j;                               /* tt stuff will be ignored when a1==nP+9, except for outM printing */
	    tj->s0     = tj->s;
	    /*
	    if (DEBUG)  printf("j= %7u  CS never = %8.4f  N= %u  type~pvtype= %d %d CSsum= %8.4f  %d  v1~w= %8.4f %8.4f  since0= %8.4f  v=%8.4f a1= %d tj->CS= %8.4f  sw=%8.4f  N=%u\n", j,
			       CS[ tt->type][tt->pvtype][7]/byT[tt->type][tt->pvtype][13], byT[tt->type][tt->pvtype][13],
			       tt->type, tt->pvtype, CS[ tt->type][tt->pvtype][7], (int)(CS[ tt->type][tt->pvtype][7]<0), v1, tj->s->w, tt->since0, v, a1, tj->CS, s->w, tj->s->N ); 
	    */
	  }
	  u = RandU; u = min(HiU,max(LoU,u));    tj->again = -log(u) * Eagain;       tj->aggress = nPlim;  /* ie, ignore aggress */
	  tj->j= 0;   tj->since= 0.0;               /* reset actual jumps and since */
	}       /* end  a1 == nP+9 */
	else {                                      /* cancel existing order and submit new one (processed using tt, as if new trader arrival) */
	  p = tj->p;
	  if (p != nP) {                            /* NOT a Non-Order, so adjust Bt[] */
	    if(tj->buy){ Bt[p]--;  while(bid1>=0 && Bt[bid1]==0) bid1--; }       /* cancel the SINGLE SHARE  buy/sell order */
	    else {       Bt[p]++;  while(ask1<nP && Bt[ask1]==0) ask1++; }       /* and get potentially new quote.  Bt[bid1==-1] never evaluated  */
	    Bt[nP]= ask1;  Bt[nPx1]= bid1;          /* Initially only used Bt[nP,nPx1] then began using bid1,ask1 since easier to remember */
	  }                                         /* need to update list at trader[tj->p].     since,again get set when new order processed as-if new arrival) */
	  if(p==nP) {       /* non-order */         /* tj->q for non-order does NOT relate to position within traders[nP] */
	    t= traders[nP]; tp= traders+nP;
	    while ( t!=tj ) { tp= &t->next; t= t->next;  if(t==NULL) {printf("\n\n HEY: should always find tj with non-order in traders[nP]... abort\n\n");  exit(9); } }
	    *tp = tj->next;                         /* removes tj from traders[nP] linked list */
	  }
	  else {            /* limitorder */        /* tj->q reveals position in traders[p] */
	    byT[tj->type][tj->pvtype][7]++;         /* count change limit order.  */
	    q1= tj->q;                              /* Bt, ask1, bid1 from order evaluation still valid */
	    if (q1==1) {                            /* current order heads list */
	      traders[p]= tj->next;                 /* 2nd in queue becomes 1st */
	      t = traders[p]; }                     /* NOTE: tj->q,p correspond to the order being cancelled */
	    else {                                  /*       since tj NOT modified during action evaluation. */
	      t = traders[p];
	      for(q2=2; q2<q1; q2++) t= t->next;     
	      t->next = tj->next;                   /* (q2-1)th t->next points to tj->next, thereby skipping tj which is the q2th=q1th */
	      t = t->next;  }
	    while(t!=NULL) { t->q--; t= t->next; }  /* orders behind cancelled order move up queue */
	  }
	  tj->s=NULL;  tj->s0=NULL;  tj->next = traders[nPx1];  traders[nPx1] = tj;   /* cancelled tj to heap, can still read via tj-> until reused */
	} /* if cancelling order   */
      }   /* if re-visiting trader */

      /* printf("j= %u  tt==tj: %2d  tt~tj->since= %5.2f %5.2f\n", j,tt==tj, tt->since, tj->since); */


      /* NEW ARRIVAL (tj==tt) TREMBLES ONLY --> effect on converged s->w only via 2nd order effect of OTHER traders' actions.  1-share only. */

      /* OLD strategy was to randomly play NEXT AVAILABLE more/less aggresive limitorder.  See: ./archive/GetEq_OLD_TREMBLES_code */
      /* BUT this did NOT work : would not tremble to the states stuck at pessimistic values, that should have been optimal.  NEW strategy is to tremble RANDOMLY. */
      /* Trembles TO market orders not needed for convergence since market s->w updated regardless of a1.  Market orders never part of aTrem[]. */
      
      /* OPTIONAL:  remove a1 from list of valid trembles,  place last valid action in its place */ 
      
      InsertNewState = 'n';  tt->ret = 0; /* since will call GetState() to get s->w of tremble */

      if(nQ==8 && tt->n > 1)  tt->ret = 4;  /* use different beliefs for returning and new traders */

      if( a1 != nP+9 ) {   /* either tt==tj (New Arrival) or returning trader who chose to cancel and resubmit using a1 */
	
	if(tt->tremble == 2)                   
	  for(p=1; p<=aTrem[0]; p++)            /* remove optimal action from tremble options */
	    if(aTrem[p]==a1) { aTrem[p] = aTrem[aTrem[0]];  aTrem[0]--;  break; }
	
	while(tt->tremble == 2 && aTrem[0]>0) { /* trembles provide way to avoid permanently pessimistic s->w */
	  u= RandU * aTrem[0];                  /* u is Unif(0, #valid trembles).  COULD tremble more frequently to 2nd best, 3rd, etc. */
	  for(p=1; p<=aTrem[0]-1; p++)          /* aTrem[0]-1 --> last valid option not actually checked --> if none satisfy u<p then last action chosen, by default */
	    if(u < p) break;                    /* tremble to aTrem[p], since p-1 < u < p */
	  p1 = aTrem[p];                        /* aTrem[0] is count, aTrem[1] is 1st valid action */
	  
	  if(p1 >0) { t1=  p1-1; tt->p= t1; tt->buy= 1;  s= GetState(tt,Bt);  Ntremble[0]++; }  /* GetState() needed?? */
	  if(p1 <0) { t1= -p1-1; tt->p= t1; tt->buy= 0;  s= GetState(tt,Bt);  Ntremble[1]++; }  /* ie, do we need s->w */
	  if(p1==0) { t1=  nP;   tt->p= t1; tt->buy= 0;  s= GetState(tt,Bt);  Ntremble[2]++; }  /*     yes, for outM   */
	  u = s->w;
#ifdef LimCost
	  if (p1) u -= LimCost;                 /* cost per limit order submission.  p1=0 is NonOrder, and Market Orders do not enter here */
#endif
	  if( s==S && v - u > TrembleNew) {     /* do not tremble since assuming init s->w + TrembleNew is optimistic */
	    for(p=1; p<=aTrem[0]; p++)          /* remove action p1 from tremble options */
	      if(aTrem[p]==p1) { aTrem[p] = aTrem[aTrem[0]];  aTrem[0]--;  break; }
	  }
	  else {
	    if( CHECK && ( p>aTrem[0] || (p1 && ( (t1>=ask1 && p1>0) || (t1<=bid1 && p1<0) || t1<tL || t1>tH )) )) {
	      printf("\n\n HEY: invalid tremble:  p= %d  aTrem[0]= %d  p1= %d ...abort. \n\n", p, aTrem[0], p1);  exit(9); }
	    
	    /* printf("j= %u  tremble:  u~p= %16.14f %2d  a1= %3d %3d  v= %5.3f %5.3f  aTrem: #,N= %2d %4u, choices:",
	       j,u,p, a1,p1, v,s->w, aTrem[0], aTremN);    for(p=1;p<=aTrem[0];p++) printf(" %3d",aTrem[p]);   printf("\n");
	    */
	    byT[tt->type][tt->pvtype][10]++;    /* implement valid tremble.  s->NT++ when order added to Traders[] since s==S possible here. */
	    v= u;  a1= p1;  m1= 0;              /* v,a1,m1 processed below:  replace them with payoff,action of tremble */

	    if(s==S)  Ntremble[19]++;           /* count trembles to NEW states */

	    if (DEBUG /* || ((j%1)==10000 && j>650 */ ) {
	      printf("   Trembles DONE. j=%4u  tj= %p  new= %d  tt:pv= %4.2f  vlag=%2d  nB= %1d, m1= %d  a1~a_= %3d %3d  v=%5.2f  Bt:", j, tj, tt==tj, tt->pv, tt->vlag, tt->tB, m1, a1, a_, v );
	      for(p=0;p<nP;p++)if(Bt[p])printf(" B[%2d]= %3d,",p,Bt[p]); printf("\n"); }

	    break;    /* valid tremble found, so break search loop*/
	  }
	}
	
	if(tt->tremble == 2 && aTrem[0]<=0) {   /* no valid trembles available */
	  if(tt==tj)  tt->tremble=0;  else  tt->tremble = min(tj->tremble, 1); /* <-- don't overwrite tt->tremble = 1 inherited from tj */
	  Ntremble[15]++; }
	
	
	/* optional code to force MANY trembles from least aggressive limit orders to Non-Orders */
	if (m1==0 && tt->tremble != 2 && RandU < ForceNon) { /* only mess with traders who haven't just trembled */
	  if (j==0) printf("\n\n HEY:  forcing limit orders with Negative E(payoffs|execute) to tremble to Non-Orders with probability %5.3f\n\n", ForceNon);
	  if (a1<0) { 
	    t1= -a1-1;
	    p = min(nPlim-1, tH - t1 );     /* sell price on "bid aggressiveness" scale */
	    p = max(0, p);                  /* must be within 0 and nPlim-1 */
	    u = -PV[tt->pvtype] - p_v[p][tt->type] ;  /* average payoff | execute,  across ALL such limitorders by the type, pvtype, aggressiveness */
	  }
	  if (a1>0) {
	    t1= a1-1;
	    p = min(nPlim-1, t1 - tL );     /* buy price on "bid aggressiveness" scale */
	    p = max(0, p);                  /* must be within 0 and nPlim-1 */
	    u =  PV[tt->pvtype] - p_v[p][tt->type] ;  /* average payoff | execute,  across ALL such limitorders by the type, pvtype, aggressiveness */
	  }
	  if (a1 &&  u < 0.0 /* (t1==tL || t1==tH) */  ) {      /* switch to Non-Order */
	    tt->tremble = 2;  tt->p = nP;  tt->buy= 0;  tt->ret=0;  s= GetState(tt,Bt);
	    Ntremble[5]++;  wCancel[6]+= v - s->w;    /* counts these "forced" trembles, and track lost payoff */
	    if ( v - s->w > wCancel[7] ) {
	      wCancel[7] = v - s->w ;  printf(" j=%7u new max loss for ForceTremble from a1= %3d to NonOrder  old~new~diff w= %7.3f %7.3f %7.4f  s->N=%u \n", j, a1, v, s->w, v-s->w, s->N); }
	    byT[tt->type][tt->pvtype][10]++;    /* implement valid tremble.  s->NT++ when order added to Traders[] since s==S possible here. */
	    v= s->w;  a1= 0;  m1= 0;            /* v,a1,m1 processed below:  replace them with payoff,action of tremble */
	  }
	  /* if(DEBUG && tt->tremble==2)  printf("j= %u ForceNon tremble to  a1= %d  v= %5.3f  s->N= %u\n", j, a1, v, s->N); */
	  /* UNCOMMENT BELOW  to force tremble to  Least aggressive sell or least aggressive buy.  Also MUST  uncomment   t1==tH || t1==tH in prev if()  */
	  /* THIS enables true RANDOM selection among least aggressive orders and Non-Orders since BOTH are randomized. */
	  /*
	  else { 
	    if (  0  && a1==0) {
	      if( RandU < .5) { a1 = -tH-1;  tt->buy= 0;  tt->p= tH;}  else { a1 = tL+1;  tt->buy= 1;  tt->p= tL;} 
	      tt->tremble = 2;  s= GetState(tt,Bt);   Ntremble[5]++; 
                     COMMENT :  printf("j=%7u a1= %3d  tL=%2d tH= %2d  s->w~N= %5.2f %7u  s==S:%d  ba= %3d %3d\n", j, a1, tL, tH, s->w, s->N, s==S, bid1,ask1); 
	      byT[tt->type][tt->pvtype][10]++;
	      v= s->w;  m1= 0;  } } 
	  */
	}
	
	if(m1==0) {   /* CALL GetState() to get Limit/Non Order state actually being implemented and update counts.   NOTE:  a1= nP+9 not possible here */
	  if (a1>0) { tt->p=  a1-1;  tt->buy= 1; }  
	  else      { tt->p= -a1-1;  tt->buy= 0; }   if(a1==0) tt->p= nP;

	  if(UPDATE_PVT[tt->type][tt->pvtype]) { 	    N_s[25]++;  /* # orders submitted for which updates had better occur.  N_s[24] tracks updates */
	    InsertNewState= 'y';  tt->s = GetState(tt,Bt);  sNew[8]++;  if(tt->s->N==1) sNew[7]++; }  /* later report share of limitorders w/ N=1, as pseudo-convergence signal */
	  else {
	    InsertNewState= 'n';  tt->s = GetState(tt,Bt);  sNew[10]++; if(tt->s->N==1) sNew[9]++; }
	  
	  if(tt->p==nP) byT[tt->type][tt->pvtype][3]++;   /* [3] counts non-orders,  [5] counts submitted limit orders, [17,18] limit buys,sells */
	  else {        byT[tt->type][tt->pvtype][5]++;  byT[tt->type][tt->pvtype][18 - tt->buy]++; }

	  if (tt==tj) { tt->s0 = tt->s; tt->si = tt->s; } /* NEW ARRIVAL --> set initial state.  tt->s0 = tt->s for UPDATE_s0=1  LATER after processing */

	  if(CHECK && tt->vlag && tt->v!=tm->v) { printf("\n\nHEY: tt->v = %d  !=  %d = tm->v (lagged v)   tt==tj: %1d ... abort\n\n", tt->v,tm->v, tt==tj); DEBUG=9;}
#ifdef useNX
	  tt->s->NX++;  /* #times state implemented */
#endif	  
	  /* if(DEBUG) printf("j= %u will add new limit to Traders[%d]  q= %d  tremble= %d  tt->period= %u  s= %p s0= %p  \n", j,tt->p,tt->q,tt->tremble,tt->period,tt->s,tt->s0); */

	  /* fprintf(outN0, "%u\n", tt->s->N );*/                /* in matlab check dist. N */
	} /* done getting state of Limit/Non order to be implemented (stuff that used to be within processing of Traders[] */
	
	
	tt->aggress = nPlim;                   /* nPlim is flag that tt->aggress NOT being used */
	if(a1 && m1==0 && tt->tremble!=2) {    /* count #times submit limitorder at each of the nPlim allowed values.  EXCLUDING TREMBLES */
	  if(a1>0) {  /* bid */                /* a1==0 is non-order */
	    t1 = a1-1; tt->aggress = t1-tL;
	    /* if (tL<=t1 && t1<=tH)  tt->aggress = t1-tL;
	       else { printf("\n\n HEY:  j= %u  a1= %d  gives t1= %d  not between tL~tH= %d %d  bid1= %d  ask1= %d  t->v= %d ... abort. \n\n",j,a1,t1,tL,tH,bid1,ask1,tt->v);  exit(2); } */
	  }
	  else {      /* a1<0 : ask,    convert to aggressiveness of a buy order */
	    t1 = -a1-1;  tt->aggress = tH-t1;
	    /* if (tL>t1 || t1>tH) {printf("\n\n HEY:  a1= %d  gives t1= %d  not between tL~tH= %d %d   ... abort. \n\n",a1,t1,tL,tH);  exit(2); } */
	  }
	  if (tt->aggress < 0 || tt->aggress >= nPlim) { printf("\n\n HEY: tt->aggress = %2d  j= %u  a1= %d  gives t1= %d  not between tL~tH= %d %d  bid1= %d  ask1= %d  t->v= %d ... abort. \n\n",tt->aggress,j,a1,t1,tL,tH,bid1,ask1,tt->v);  exit(2); }
	  byT[tt->type][tt->pvtype][21+ tt->aggress]++;  /* will be compared to number of executions for each level of aggress to see if nPlim is high enough to offer all desired actions */
	}
      }    /* end a1!=nP+9 */
      
      /* next block is an optional check */
      /*
	if ( ask1!=Bt[nP] || bid1!=Bt[nPx1] ) {
	printf("\n Hey: ask1!=Bt[nP] || bid1!=Bt[nP+1] so restoration of bid/ask not being done correctly.  aborting...\n");
	printf(" j=%u  tt->pv= %6.3f  a2<a1  a1= %3d   a2= %3d  v=%6.3f  v0=%6.3f  bid1= %d  Bt[nP+1]= %d  ask1= %d  Bt[nP]= %d \n", j, tt->pv, a1, a2, v, v0,bid1,Bt[nPx1],ask1,Bt[nP]);
	printf("tick  Bt   #Traders[p]\n");
	for (p=0; p<nP; p++) {
	t = traders[p];   k=0;
	while (t!=NULL) { k++; t= t->next;}
	printf(" %3d:%3d %3d \n", p, Bt[p],k);
	} exit(9);
	}
      */

      if (DEBUG) {
	printf("j= %4u  tremble= %d  period= %u  tt:pv= %4.2f  m1= %d  a1~a_= %3d %3d  v=%5.2f  bid~ask= %2d %2d  ",
	       j, tt->tremble, tt->period, tt->pv, m1, a1, a_, v, bid1, ask1 );
	for(p=0;p<nP;p++)  if(Bt[p]) printf("B[%d]= %d, ",p,Bt[p]);  printf("\n\n");  fflush(stdout);
	DEBUG = 0;
      }

      
#if mJ>0
      if (j<mJ) {                     /* tt outm */                    /* col # */
	u = (double)j;              fwrite( (char *) &u, 1, 8, outM ); /* 1  */  /* current "period"        */
	u = (double)tt->period;     fwrite( (char *) &u, 1, 8, outM );           /* period   1st arrived    */
	fwrite(                     (char *)&tt->rtime0, 1, 8, outM );           /* realtime 1st arrived    */
	fwrite(                     (char *)&realtime,   1, 8, outM );           /* realtime current        */
	fwrite(                     (char *)&tt->since,  1, 8, outM ); /* 5  */  /* time since prev action, 0 if new arrival (could change to time since last new */
	fwrite(                     (char *)&tt->pv,     1, 8, outM );           /* private value           */
	u = (double)tt->v;          fwrite( (char *) &u, 1, 8, outM);            /* last observed v */
	u = (double)tt->type;       fwrite( (char *) &u, 1, 8, outM);
	u = (double)tt->tB;         fwrite( (char *) &u, 1, 8, outM);
	u = (double)tt->vlag;       fwrite( (char *) &u, 1, 8, outM);  /* 10 */
	u =  0.0;                   fwrite( (char *) &u, 1, 8, outM);            /* if large tz->z shares, 0 if small */
	u = (double)tt->tH;         fwrite( (char *) &u, 1, 8, outM);
	u =-(double)rhoT[tt->type]; fwrite( (char *) &u, 1, 8, outM);            /* rhoT is -discount rate  */
	u = (double)Jcount[2]-(double)Jcount[3]; fwrite( (char *) &u,1,8,outM);  /* cum jumps since j=0     */
	u = (double)Bt[nP];         fwrite( (char *) &u, 1, 8, outM);  /* 15 */  /* Ask, before new trader  */
	u = (double)Bt[nPx1];       fwrite( (char *) &u, 1, 8, outM);            /* Bid, before new trader  */
	if (Bt[nP]==nP)    u=0.0; else   u = (double)Bt[Bt[nP]];   fwrite( (char *) &u , 1, 8, outM );   /* Ask depth */
	if (Bt[nPx1]==-1) v1=0.0; else  v1 = (double)Bt[Bt[nPx1]]; fwrite( (char *) &v1, 1, 8, outM );   /* Bid depth */
	for(p=nP_1; p>Bt[nP];   p--)     u+= (double)Bt[p];        fwrite( (char *) &u , 1, 8, outM );   /* # sells   */
	for(p=0;    p<Bt[nPx1]; p++)    v1+= (double)Bt[p];        fwrite( (char *) &v1, 1, 8, outM );   /* # buys    */  /* col 20 */
	for(p=0;    p<nP;       p++) {  v1 = (double)Bt[p];        fwrite( (char *) &v1, 1, 8, outM ); } /* Bt each p */  /* col 21:20+nP */
	if (tj==tt) {
	  u = -9.0;
	  fwrite( (char *) &u, 1, 8, outM);   /* filler */  /* new arrival has no keep order option */
	  fwrite( (char *) &u, 1, 8, outM);   /* filler */
	  fwrite( (char *) &u, 1, 8, outM);   /* filler */
	  fwrite( (char *) &u, 1, 8, outM); } /* filler */
	else {
	  fwrite(                      (char *) &tj->CS, 1, 8, outM);   /* value of keeping order. -99.9 if invalid retention */
	  u = (double)tj->p;        fwrite( (char *) &u, 1, 8, outM);   /* p of current order      */
	  u = (double)tj->q;        fwrite( (char *) &u, 1, 8, outM);   /* q of current order      */
	  u = (double)tj->j;        fwrite( (char *) &u, 1, 8, outM);   /* jumps since prev action */
	}
	/* above stuff used to be written BEFORE  get_a1: */
	
	u = (double)tt->tremble;    fwrite( (char *) &u, 1, 8, outM );        /* 2 if just trembled, 1 if ever trembled */
	u = (double)a1;             fwrite( (char *) &u, 1, 8, outM );
	/* u = (double)a2;             fwrite( (char *) &u, 1, 8, outM ); */  /* a2 = 0 ALWAYS */
	/* */                       fwrite( (char *) &v, 1, 8, outM );        /* E(payoff) of combined actions  */
	/* */                       fwrite( (char *) &tt->Ev, 1, 8, outM );   /* E(v) relative to last observed */
	for(p=0; p<nRT; p++) {                                         /* write Ht[][] that tt,tj condition on  */
          u = (double)RT[p];        fwrite( (char *) &u, 1, 8, outM);  /* p^th RT */
          u = (double)Ht[0][p];     fwrite( (char *) &u, 1, 8, outM);  /* net jumps    p^th RT */
          u = (double)Ht[1][p];     fwrite( (char *) &u, 1, 8, outM);  /* last price   p^th RT */
	  u = (double)Ht[2][p];     fwrite( (char *) &u, 1, 8, outM);  /* cum net buys p^th RT */
	  u = (double)Ht[3][p];     fwrite( (char *) &u, 1, 8, outM);  /* last price signed order p^th RT */
	}  /* may need to add filler columns for Hz[][] if decide to write Hz */
      }
#endif

      if ( ask1 != Bt[nP] || bid1 != Bt[nPx1] ) printf("j= %u  bid1~Bt[nP+1]: %d %d   ask1~Bt[nP]: %d %d  after\n",j, bid1,Bt[nPx1], ask1,Bt[nP]);

      /* NOTE:  a1 = 0 for trader revisit who keeps old order */

      q1= 0;  q2= 0;  /* dummies for market orders.  used by NScount.  could also compare to m1, m2 after processing. */
      b1= 0;  b2= 0;  /* temporary storage for current trader having Aggressive Buy/Sell */

      if(m1) {
	M= Mx; if(realtime-M->RT <nLag){ M=malloc(sizeof(struct MO)); if(M==NULL){printf("Out of memory for M\n");exit(8);}  M->prev = Mx;  N_s[11]++;}
	Mx= M->prev;  Mx->next= NULL;  M->prev= NULL;  M1->prev= M;  M->next= M1;  M1= M;
	if(a1<0) { M1->p = -a1-1;  M1->sof= -1;}
	else     { M1->p =  a1-1;  M1->sof=  1;}   /* sof = signed order flow */
	M1->z= 0;  M1->RT= realtime;               /* z=0 for SMALL trader (tt,tj),  1 for LARGE (tz) */
	if(tt->period==j) byT[tt->type][tt->pvtype][19]++;  /* mkt orders when arrive */
      }
      
      t = NULL;                 /* if not NULL after processing share, then a Limit/Non-Order was processed and t->stuff needs to be set */

      if (a1==0 || a1==nP+9) {  /* a1=nP+9 for no change in order by tj */

	if(a1==0 && v==V0) {    /* No Action (ie, walk-away.  not a non-order), by EITHER a NEW or tj */
	  /* fprintf(outNoAction, "1 %8.4f %3d %3d ", tt->pv, bid1, ask1);
	     for(p=0;p<nP;p++)  fprintf(outNoAction, "%4.1f %3d ", (double)p, Bt[p]);   fprintf(outNoAction, "\n"); */
	  byT[tt->type][tt->pvtype][2]++;       /* count No Action, new or revisiting */
	  if(tj==tt) Bcount[nPx1]++;            /* share 1 counter no action by NEW ARRIVALS only (tj==tt) */
#if mJ>0
	  if (j<mJ) {
	    u = -10.0 - nP; /* filler data */
	    fwrite( (char *) &u, 1, 8, outM );  /* price    */
	    fwrite( (char *) &u, 1, 8, outM );  /* volume   */
	    fwrite( (char *) &u, 1, 8, outM );  /* exec limit period or s0->w */
	    fwrite( (char *) &u, 1, 8, outM ); }/* exec limit    j   or s0->w */
#endif
	}
	else {    /* v!=V0 --> must be EITHER non-order OR no change by tj (since NO MORE market orders with fringe) */
	  if (m1) { printf("\n\n REMOVED market order TO FRINGE which was here in GetEq_large_code.c ... aborting\n\n");  exit(8); }
	  else {  /* m1=0 */
	    if (a1 == nP+9) {                   /* no change by revisiting tj.  tj->since stuff ALREADY updated when RETAIN found to be optimal */
	      a1 = 0;                           /* a1=0 avoids entering processing below via a1>0 check */
#if mJ>0
	      if (j<mJ) {
		u= (double)tj->p;                    fwrite( (char *) &u, 1, 8, outM );  /* price  */
		if(tj->buy) u= 2.0;  else u= -2.0;   fwrite( (char *) &u, 1, 8, outM );  /* volume column = 2 for limit buy */
		/*   */                              fwrite( (char *) &tj->s->w, 1, 8, outM );
		u= 0.0; /* was t->s->j */            fwrite( (char *) &u, 1, 8, outM ); }
#endif
	    }
	    else { /* a1=0 --> non-order */
	      /* if (DEBUG)  printf("creating t for non-order\n"); */
	      if (a1!=0) { printf("\n\n HEY: a1=0 expected but a1= %d  v= %5.2f ... abort\n\n",a1,v);  exit(8);}
	      if(tj==tt) Acount[nPx1]++;          /* share 1 counter Non-Orders by NEW ARRIVALS.            To include revisits remove if(tj==tt).   */
	      if (traders[nPx1]==NULL) {          /* add Non-Order to HEAD of traders[nP], since order (priority) within linked list does not matter */
		t = malloc(sizeof(struct trader));  if (t==NULL) { printf("Out of memory for share 1 limit trader \n");  exit(8); } }
	      else { t = traders[nPx1];  traders[nPx1]= t->next; }  /* reuse from trader heap */
	      t->next  = traders[nP];    traders[nP] = t;
	      t->again = Eagain_non;     t->q = 1;                  /* other stuff written to t after processing all types */
#if mJ>0	      
	      if (j<mJ) {
		u= (double)nP;              fwrite( (char *) &u, 1, 8, outM );     /* price  */
		u= 0.0;                     fwrite( (char *) &u, 1, 8, outM );     /* volume column = 2 for limit buy, -2 for limit sell, 0 for non-order */
		/*   */                     fwrite( (char *) &tt->s->w, 1,8,outM); /* expected payoff */
		u= 0.0 ; /* was t->s->j */  fwrite( (char *) &u, 1, 8, outM );   }
#endif
	    } }
	} }  /* end  a1==0 || a1==nP+9 */
      
      /* Process Share 1 */
      if (a1>0) {
	t1 = a1-1;  ++Bt[t1];
	if (t1==Bt[nP]) {        /* market buy.  If returning trader, then tj->s0,Ew,NN already updated */
#if mJ>0
	  if (j<mJ) {
	    u= (double)t1;  fwrite( (char *) &u, 1, 8, outM );   /* price  */
	    u= 1.0;         fwrite( (char *) &u, 1, 8, outM ); } /* volume */
#endif
	  t = traders[t1];
	  while (t!=NULL) {
	    t->q-- ;             /* reduce queues: q<= 0 means executed */
#if mJ>0
	    if(j<mJ && t->q==0){ /* write executed limit's t->period, t->j */
	      u = (double) t->period;  fwrite((char *)&u,1,8,outM);
	      u = (double) t->j;       fwrite((char *)&u,1,8,outM); }
#endif
	    t= t->next; }
	  Bcount[nP]++;          /* market buy counter     */
	  Tcount[t1][1]++;       /* market buy counter - by price */
	  byT[tt->type][tt->pvtype][4]++;   /* market trade counter - by type */
	  byT[tt->type][tt->pvtype][15]++;  /* market  buy  counter - by type */
	  Spread[nP+2]+= ask1-bid1;
	  Ttau[TAU+1]++ ;        /* trade volume THIS PERIOD, not necessarily at this spread. */
	  Ptau[TAU+1]= t1;       /* used later for realized spread */

	  u = tt->pv - t1;  v1 = u * exp(rhoT[tt->type] * tt->since0) /* - cT[tt->type] * tt->since0 */ ;
	  CS[ tt->type][tt->pvtype][1] +=  v1;          /* Cumulative Surplus, discounted if tt not new arrival */
	  CS[ tt->type][tt->pvtype][3] +=  tt->since0;  /* NOTE:  tj->s0 and tj->s UPDATED ABOVE if tt is returning trader w/ market order */
	  CS[ tt->type][tt->pvtype][5] +=  u;           /* UNdiscounted CS */
	  CS[ tt->type][tt->pvtype][6] +=  t1 - PVmu ;  /* TransCosts : if ever change code such that PVmu is NOT the  v  tick, then must change this */
	  CS[ tt->type][tt->pvtype][7 +(tt->tremble >0)]+= v1;    /* disc. CS  by  NEVER/EVER tremble */
	  byT[tt->type][tt->pvtype][13+(tt->tremble >0)]++;       /* count traders NEVER/EVER tremble */
	  if(tt->tremble==0) {
	    CS[tt->type][tt->pvtype][9]+= v1*v1;        /* squared disc. CS  by  NEVER tremble */
	    CS[tt->type][tt->pvtype][13]+= tt->since0; }

	  if(bid1==-1 || ask1==nP) m=0;  
	  else   m= min(4,(ask1-bid1));
	  TC[tt->type][tt->pvtype][0][m]++ ;          /* count mkt orders by spread */
	  TC[tt->type][tt->pvtype][1][m]+= t1 - PVmu; /* TC by spread */

	  q1 = 1;                /* dummy for NScount */
	  /* if (DEBUG)  printf("processed share 1 market buy  at p %d\n", t1); */
	}
	else {                   /* add limit buy to end of queue */
	  /* if (DEBUG)  printf("creating t for limit buy\n"); */
	  t = traders[t1];  tp= traders+t1;
	  while (t!=NULL) { tp= &t->next; t= t->next;}
	  if (traders[nPx1]==NULL) {
	    t = malloc(sizeof(struct trader));  if (t==NULL) { printf("Out of memory for share 1 limit trader \n");  exit(8); } }
	  else { t = traders[nPx1];  traders[nPx1]= t->next; }  /* reuse from trader heap */
	  *tp= t;  t->next= NULL;    t->again = Eagain;  t->q= Bt[t1]; /* other stuff written to t after processing all types */
	  qmax = max(qmax, t->q);  ++Bcount[t1];             /* counter of bids at each price  */
	  if (t1 > bid1)  b1 = 1;                            /* agg. bid, tracked by ABAS */
#if mJ>0
	  if (j<mJ) {
	    u= (double)tt->p;            fwrite( (char *) &u, 1, 8, outM );     /* price  */
	    u= 2.0;                      fwrite( (char *) &u, 1, 8, outM );     /* volume column = 2 for limit buy, -2 for limit sell, 0 for non-order */
	    /*   */                      fwrite( (char *) &tt->s->w, 1,8,outM); /* expected payoff */
	    u= 0.0 ; /* was t->s->j */   fwrite( (char *) &u, 1, 8, outM );   }
#endif
	  /* if ( t1<6 || t1>9 ) printf("j= %u  bid t1= %d  tL= %d  tH= %d\n",j,t1,tL,tH); */

	  /* if (DEBUG)  printf("processed share 1  limit buy  at p %d\n", t1); */
	} }
      if (a1<0) {
	t1 = -a1-1;  --Bt[t1];
	if (t1==Bt[nPx1]) {      /* market sell */  /* if (DEBUG) printf("sell 1 begin\n"); */
#if mJ>0
	  if (j<mJ) {
	    u= (double)t1;  fwrite( (char *) &u, 1, 8, outM );   /* price  */
	    u= -1.0;        fwrite( (char *) &u, 1, 8, outM ); } /* volume */
#endif
	  t = traders[t1];
	  while (t!=NULL) {
	    t->q-- ;             /* reduce queues: q<= 0 means executed */
#if mJ>0
	    if(j<mJ && t->q==0){ /* write executed limit's t->period, t->j */
	      u = (double) t->period;  fwrite((char *)&u,1,8,outM);
	      u = (double) t->j;       fwrite((char *)&u,1,8,outM); }
#endif
	    t= t->next; }
	  Acount[nP]++;          /* market sell counter     */
	  Tcount[t1][0]++;       /* market sell counter - by price */
	  byT[tt->type][tt->pvtype][4]++;   /* market trade counter - by type */
	  byT[tt->type][tt->pvtype][16]++;  /* market  sell counter - by type */
	  Spread[nP+2]+= ask1-bid1;         /* Spread BEFORE new trader */
	  Ttau[TAU+1]++ ;        /* trade volume THIS PERIOD, not necessarily at this spread. */
	  Ptau[TAU+1]= t1;       /* used later for realized spread */

	  u = t1 - tt->pv;  v1 = u * exp(rhoT[tt->type] * tt->since0) /* - cT[tt->type] * tt->since0 */ ;
	  CS[ tt->type][tt->pvtype][1] +=  v1;          /* Cumulative Surplus, discounted if tt not new arrival */
	  CS[ tt->type][tt->pvtype][3] +=  tt->since0;  /* NOTE:  tj->s0 and tj->s UPDATED ABOVE if tt is returning trader w/ market order */
	  CS[ tt->type][tt->pvtype][5] +=  u;           /* UNdiscounted CS */
	  CS[ tt->type][tt->pvtype][6] +=  PVmu - t1 ;  /* TransCosts : if ever change code such that PVmu is NOT the  v  tick, then must change this */
	  CS[ tt->type][tt->pvtype][7 +(tt->tremble >0)]+= v1;    /* disc. CS  by  NEVER/EVER tremble */
	  byT[tt->type][tt->pvtype][13+(tt->tremble >0)]++;       /* count traders NEVER/EVER tremble */
	  if(tt->tremble==0) { 
	    CS[tt->type][tt->pvtype][9]+= v1*v1;        /* squared disc. CS  by  NEVER tremble */
	    CS[tt->type][tt->pvtype][13]+= tt->since0; }

	  if(bid1==-1 || ask1==nP) m=0;  
	  else   m= min(4,(ask1-bid1));
	  TC[tt->type][tt->pvtype][0][m]++ ;          /* count mkt orders by spread */
	  TC[tt->type][tt->pvtype][1][m]+= PVmu - t1; /* TC by spread */

	  q1 = -1;               /* dummy for NScount */
	  /* if (DEBUG)  printf("processed share 1 market sell at p %d\n", t1); */
	}
	else {                   /* add limit sell to end of queue */
	  /* if (DEBUG)  printf("creating t for limit sell\n"); */
	  t = traders[t1];  tp= traders+t1;
	  while (t!=NULL) { tp= &t->next; t= t->next;}
	  if (traders[nPx1]==NULL) {
	    t = malloc(sizeof(struct trader));  if (t==NULL) { printf("Out of memory for share 1 limit trader \n");  exit(8); } }
	  else { t = traders[nPx1];  traders[nPx1]= t->next; }  /* reuse from trader heap */
	  *tp= t;  t->next= NULL;    t->again = Eagain;  t->q= -Bt[t1];
	  qmax = max(qmax, t->q);  ++Acount[t1];             /* counter of asks at each price  */
	  if (t1 < ask1)  b2 = 1;                            /* agg. ask, tracked by ABAS */
#if mJ>0
	  if (j<mJ) {
	    u= (double)tt->p;            fwrite( (char *) &u, 1, 8, outM );      /* price  */
	    u= -2.0;                     fwrite( (char *) &u, 1, 8, outM );      /* volume column = 2 for limit buy, -2 for limit sell, 0 for non-order */
	    /*   */                      fwrite( (char *) &tt->s->w, 1,8,outM);  /* expected payoff */
	    u= 0.0 ; /* was t->s->j */   fwrite( (char *) &u, 1, 8, outM );   }
#endif

	  /* if ( t1<6 || t1>9 ) printf("j= %u  ask  a1= %d  t1= %d  tL= %d  tH= %d \n",j,a1,t1,tL,tH); */

	  /* if (DEBUG)  printf("processed share 1  limit sell at p %d\n", t1); */
	} }


      if (t!=NULL) {       /* submitted new limitorder.  initialize some stuff and update CS[] if UPDATE_s0 */
	
	if(tt->tremble==2) { tt->s->NT++;  tt->tremble = 1; }  /* tt->tremble = 1 if EVER trembled, 2 if just trembled. NOTE: NT++ upon "entry" whereas N,NN++ upon UPDATE */

#if useNNtt>0
	if(tt->tremble==0 && tt->period==j) tt->s->NNtt++;     /* WriteFiles() checks whether s is optimal ALWAYS or NEVER (for NEW arrivals), as needed for convergence */
#endif
	if( UPDATE_s0 ) {  /* use CS to track next decision payoffs, not ultimate decisions.  Important for CS_non[] updates */
	  /*
#ifdef LimCost
	  if (fabs(v + LimCost*(a1!=0) - tt->s->w ) > 1e-6)  printf("j= %7u  v= %8.4f  tt->s->w= %8.4f  v-w= %8.1e a1=%4d  tremble= %d\n", j, v, tt->s->w, v + LimCost*(a1!=0) - tt->s->w, a1, tt->tremble);
#else
	  if (fabs(v - tt->s->w) > 1e-6)                    printf("j= %7u  v= %8.4f  tt->s->w= %8.4f  v-w= %8.1e a1=%4d  tremble= %d\n", j, v, tt->s->w, v-tt->s->w, a1, tt->tremble);
#endif
	  */
	  if (tt->n > 1) {                                       /* only update CS stuff if returning trader.  Added this condition on 6/29/06.  was wrong before that */
	    v1 = v * exp(rhoT[tt->type] * tt->since0);           /* v is post-tremble payoff to a1.  v may differ from tt->s->w due to an update this period */
	    CS[ tt->type][tt->pvtype][7 +(tt->tremble >0)]+= v1; /* disc. CS  by  NEVER/EVER tremble */
	    byT[tt->type][tt->pvtype][13+(tt->tremble >0)]++;    /* count traders NEVER/EVER tremble */
	    if(tt->tremble==0) { 
	      CS[tt->type][tt->pvtype][9]+= v1*v1;               /* squared disc. CS  by  NEVER tremble */
	      CS[tt->type][tt->pvtype][13]+= tt->since0; }
	  }
	  tt->tremble = 0;        /* when UPDATE_s0 do not bother tracking whether EVER trembled, only whether JUST trembled */
	  tt->since0 = 0.0;
	  tt->rtime0 = realtime;
	  tt->period = j;
	  tt->s0 = tt->s;
	}
	else {  /* UPDATE_s0 == 0 */
	  /* if ( tt->n == 1 || (tt->n==2 && RandU < .5 ) ) { tt->s0 = tt->s;  tt->since0 = 0.0;  tt->rtime0 = realtime; }  */
	  if ( tt->n == 1 ) { tt->s0 = tt->s;  tt->since0 = 0.0;  tt->rtime0 = realtime; }
#if Ns0
	  else {   /* will use each realized outcome to update s->Ew of ALL states used by this trader --> Independence problem??  */
	    t1= tt->n;  if(t1 > Ns0-1) { printf("\n HEY: must increase Ns0\n");  exit(8);}
	    t->sN[t1] = tt->s;   t->sinceN[t1] = 0.0;  /* t1 will be at least 2 */
	  }
#endif
	}
	t->s= tt->s;   t->s0= tt->s0;  t->si= tt->si;  /* tt->s0 was set to be tt->s if tt==tj, or if UPDATE_s0 */
	u = RandU; u = min(HiU,max(LoU,u));    t->again = -log(u) * t->again;   t->since= 0.0;  t->since0= tt->since0;  t->sincei= tt->sincei;  t->rtime0= tt->rtime0;
	t->pv= tt->pv;  t->pvtype= tt->pvtype; t->z= 0;    t->buy= tt->buy;   t->p= tt->p;         t->type= tt->type;  t->tB= tt->tB;  t->tH= tt->tH;
	t->tremble= tt->tremble;  t->aggress= tt->aggress;  t->v = tt->v;   t->vlag  = tt->vlag;    t->j= 0;  t->jlag= 0;  t->period= tt->period;  t->n= tt->n;
#if nL>0
	if(t->buy==0 && t->p != nP) t->L[0]= -t->p;  else  t->L[0]= t->p;   /* signed order */
	if(nL==3) { t->L[1]= bid1;  t->L[2]= ask1; }
	if(t->v) { t->L[0] -= t->v; t->L[1] = max(-1, t->L[1]-t->v);  t->L[2] = min(nP_1, t->L[2]-t->v); }
#endif
	
#if usePN>0
	if(t->si==SS && t->n==2 ) { t->s->SSN++;   /* printf("t->si = SS for s= %p s->N= %u\n", t->s, t->s->N); */   }
#endif
      }

      /*
      if (byT[tt->type][tt->pvtype][13] && CS[ tt->type][tt->pvtype][7]<0) 
	printf("j= %7u  CS never = %8.4f  N= %u  type~pvtype= %d %d CSsum= %8.4f  %d\n",
	       j, CS[ tt->type][tt->pvtype][7]/byT[tt->type][tt->pvtype][13], byT[tt->type][tt->pvtype][13], tt->type, tt->pvtype, CS[ tt->type][tt->pvtype][7], (int)(CS[ tt->type][tt->pvtype][7]<0));
      */

      /* update bid/ask in Bt given share 1 action processed above */
      p= nP;
      while(--p > -1 && Bt[p]<=0) { } Bt[nPx1] = p; /* high bid.  = -1 if no bids */
      while(++p < nP && Bt[p]>=0) { } Bt[nP]   = p; /* low  ask.  = nP if no asks */

      /* Tick  q       bid1 = 0, ask1 = 2,      let  a= either a1 or a2
         ====  ==      aggressive sell is limit sell at tick 1 -->   bid1 < -a-1= 1 < ask1
  	 2     -1      aggressive buy  is limit buy  at tick 1 -->   bid1 <  a-1= 1 < ask1
	 1      0 
	 0      1
      */

      /* ABAS[2][4] = count Aggressive Bids/Asks */
      /* col 1 is prev period AB, 2 is AB count, 3 is AB|AB, 4 is AS|AB */
      /* col 1 is prev period AS, 2 is AS count, 3 is AB|AS, 4 is AS|AS */
      /*
	t1=  a1-1;  t2=  a2-1;  if (( bid1<t1 && t1<ask1 ) || ( bid1<t2 && t2<ask1 )) { b1=1;  ABAS[0][1]++; }
	t1= -a1-1;  t2= -a2-1;  if (( bid1<t1 && t1<ask1 ) || ( bid1<t2 && t2<ask1 )) { b2=1;  ABAS[1][1]++; }
      */
      if (b1)  ABAS[0][1]++;   /* b1, b2 defined when processing shares */
      if (b2)  ABAS[1][1]++; 

      if ( ABAS[0][0] ) {      /* Aggressive Buy  Prev Trader */
	if (b1) ABAS[0][2]++;
	if (b2) ABAS[0][3]++;
      }
      if ( ABAS[1][0] ) {      /* Aggressive Sell Prev Trader */
	if (b1) ABAS[1][2]++;
	if (b2) ABAS[1][3]++;
      }
      ABAS[0][0] = b1;  /* printf("count AB =%7u  AB|AB =%7u  AS|AB =%7u\n", ABAS[0][1],ABAS[0][2],ABAS[0][3]); */
      ABAS[1][0] = b2;  /* printf("count AS =%7u  AB|AS =%7u  AS|AS =%7u\n", ABAS[1][1],ABAS[1][2],ABAS[1][3]); */

      if(a1!=0 && tz->q) tz->again = 0.00001;   /* triggers nearly immediate action by tz processed next j loop */


      }   /* end of HUGE else{} containing all tt, tj code, which is NOT YET TABIFIED */


	
      /* IDEA: could have multiple states updated by single share/trader, */
      /* by adding a linked list of states ever visited to  struct trader.*/
      /* But such updates would not be independent.  Is that a problem?   */
      
      /* NOW:   update  s->w, s->Ew, s->w2.   Looping through all traders.   Remove executed traders.  */
      /* NOTE:  Bt  ALREADY UPDATED above in new trader code */

      /* next 3 lines simply to advance initial p of the Traders[] update loop in cases when no limitorders changed */
      p = 0;       tx = NULL;   t1 = 0;   /* t1 counts traders */
      if(tj!=tz && a1==0)    p= nP;       /* SMALL trader either walks away or submits Non-Order,(or RETAINED order since a1=nP+9 changed to a1=0 after processing above)  --> NO CHANGES in traders[0,...,nP-1] */

      p=0;  /* need to loop through if counting traders  via  t1  to compare to N_s[24,25] */

      for ( ; p<nPx1; p++) {              /* update traders at each tick, non-orders at traders[nP] */
	t = traders[p];                   /* if(DEBUG) printf("update traders[p] p= %d\n",p);*/
	while (t!=NULL) {    t1++;        /* t1-- executed if this trader executes */
	  s = t->s;                       /* t->s is previous state whose w to be updated */
	  
#if usePN>0
	  if (SS==s) { printf("SS in Traders[]\nj= %7u  p= %2d  t: pvtype~type~n~period= %d %d %2d %8u  p= %2d  q= %2d  s= %12p  t~tt->again: %6.3f %6.3f  Book=%3d\n", j, p, t->pvtype,t->type,t->n,t->period, t->p, t->q, t->s, t->again, tt->again, Bt[p]);  fflush(stdout); }
#endif
	  if (DEBUG)   printf("j= %7u  p= %2d  t: pvtype~type~n~period= %d %d %2d %8u  p= %2d  q= %2d  s= %12p  t~tt->again: %6.3f %6.3f  Book=%3d\n", j, p, t->pvtype,t->type,t->n,t->period, t->p, t->q, t->s, t->again, tt->again, Bt[p]);
	  if (CHECK && t->p!=p)  printf("HEY:  p= %d  t->p= %d \n", p, t->p);

	  if (t->q > 0) {                 /* t did not execute yet          */
	    t = t->next;
	  }                               /* end if (t->q > 0) --> t did not execute */
	  else {                          /* t->q <= 0 --> t executed.  Update state,  remove t */
	    if(t->buy)  u = t->pv - p;    /* limit buy: cum surplus, in ticks. p=t->p */
	    else        u = p - t->pv;
	    v0 = u * exp(rhoT[t->type] * t->since0 ) /* - cT[t->type] * t->since0 */ ;
	    if(u<0.0) byT[t->type][t->pvtype][8]++;  /* count pick-offs */
	    CS[ t->type][t->pvtype][0]+= v0;         /* discounted CS   */
	    CS[ t->type][t->pvtype][2]+= t->since0;  /* time since arrival (if UPDATE_s0 = 0) else time since order submitted */
	    CS[ t->type][t->pvtype][4]+= u;          /* undiscounted CS */
	    byT[t->type][t->pvtype][9]++;            /* count exec limits */
	    CS[ t->type][t->pvtype][7 +(t->tremble >0)]+= v0;    /* disc. CS  by  NEVER/EVER tremble */
	    byT[t->type][t->pvtype][13+(t->tremble >0)]++;       /* count traders NEVER/EVER tremble */
	    if(t->tremble==0) {
	      CS[t->type][t->pvtype][9]+= v0*v0;     /* squared disc. CS  by  NEVER tremble */
	      CS[t->type][t->pvtype][13]+= t->since0; }
	    if(t->aggress < nPlim) {
	      byT[t->type][t->pvtype][21 + nPlim + t->aggress]++;    /* count exec limits, by order aggressiveness */
	      if (t->buy) byT[t->type][t->pvtype][21+2*nPlim + t->aggress]+= (PVmu+ p - (short int)PVmu) ;  /* track p-v.  ASSUMES PVmu is WHOLE NUMBER (i.e., nP is odd) */
	      else        byT[t->type][t->pvtype][21+2*nPlim + t->aggress]+= (PVmu+ (short int)PVmu - p) ;  /* convert sell to buy.  PVmu+ since byT is UNSIGNED INT */

	      /* if (t->buy && t->pvtype==2)  printf("t->aggress= %3d  u=%6.2f  p-PVmu= %6.2f  p=%3d  t->j= %3d\n", t->aggress, u, p-PVmu, p, t->j); */
	      
	    }
	    if ( UPDATE_s0 || t->tremble==0 ) {
	      if ( 1 || RandU < .1) {
		t->s0->Ew += v0;              /* cum  realized value    */
		t->s0->w2 += v0*v0;           /* cum (realized value)^2 */
		t->s0->NN++;                  /* visitations, by NEW ARRIVAL TRADERS ONLY ?? */
#if useNe>0
		t->s0->Ne++;                  /* used to count executions at s (not s0) to report in WriteFiles(), but changed 11/03/05 to update s0 ??? */
#endif
	      }
#if usePN>0
	      a_= (short int) (t->n==1);
	      if (t->s0==SS) {  SSEw[0][a_]+= v0;  SSEw[1][a_]++; }
	      if (t->si==SS) {  SiEw[0][a_]+= u * exp(rhoT[t->type] * t->sincei );  SiEw[1][a_]++; }
	      /* if (t->si==SS && t->n==2)  printf("1 %7.5f %7.5f %u %u %d\n", t->s->w, v0, t->s->NN, t->s->SSN, t->n); */
#endif
#if UPDATE_s0==0 && Ns0
	      for(k=2; k <= t->n; k++) {
		if ( 1 || RandU < .1 )  {
		  v0 = u * exp(rhoT[t->type] * t->sinceN[k] ) /* - cT[t->type] * t->since0 */ ;
		  t->sN[k]->Ew+= v0;          /* cum  realized value.     NOTE: CS[][] update in processing a1 below uses tj->since0 so do NOT change it! */
		  t->sN[k]->w2+= v0*v0 ;      /* cum (realized value)^2 */
		  t->sN[k]->NN++;             /* This NN++ means sum(NN) in WriteFiles() will NOT = # Exec. limit orders. */
		} }
#endif
	    }  /* end if t->tremble = 0 */
	    if (UPDATE_PVT[t->type][t->pvtype]) {      /* s is t->s, the prev state at which limit order submitted *or* retained, at which time t->since= 0.0 */
	      s->w += ( exp(rhoT[t->type] * t->since) * u /* - cT[t->type] * t->since */ - s->w ) / ++s->N ;   /* t->since was updated when found next moving trader (tj) at start of j loop */
	      N_s[24]++;   t1--;   }

	    /* if (DEBUG) printf("j=%8u executed t: %p period= %u  p= %d  buy= %d  since~0= %6.4f %6.4f  s0->NN= %u\n", j,t,t->period,p,t->buy,t->since,t->since0,t->s0->NN); */
	    if ( CHECK && UPDATE_s0 && t->s0 != t->s) {
	      printf("j=%u   HEY: t->s != t->s0   s: N~NN= %p %u %u  s0: N~NN= %p %u %u", j, t->s, t->s->N, t->s->NN, t->s0, t->s0->N, t->s0->NN );
	      for (p1=0;p1<nB;p1++) if(t->s->B[p1]!=t->s0->B[p1]) printf(" B[%d]= %d %d ", p1,t->s->B[p1], t->s0->B[p1]);  printf("\n");     DEBUG=1; }
	    if( CHECK && fabs(t->since0 + t->rtime0 - realtime) > 1e-5  )	    /* optional elapsed realtime check */
	      printf("j= %u  rt= %12.3f  rt0+since0-rt= %10.1e  since0= %8.3f  executed via limit\n",j, realtime, t->since0 + t->rtime0 - realtime, t->since0);

	    tx = t;    tx->s=NULL;     tx->s0=NULL; t= t->next;  traders[p] = t;    /* executed t from HEAD of traders[].   Removal therefore easy. */
	    tx->next = traders[nPx1];  traders[nPx1] = tx;  tx = NULL;  /* executed t to heap */
	  } /* else t->q <= 0 */
	}   /* while(t!=NULL) at price p */
      }     /* p loop */                 /* if (DEBUG) printf("after update\n"); */

      /* CHECK that #updates processed is correct */
      if( UPDATE_maxPVT> .99  &&  UPDATE_s0  &&  N_s[24]+t1 != N_s[25]) {
	printf("\nHEY: j= %7u  N_s[24]+t1 != N_s[25]   t1=%3d  Nupdated= %6u (sum= %6u) compared to Nsubmitted= %6u ... aborting\n",
	       j, t1, N_s[24], (unsigned int)t1+N_s[24], N_s[25] );  fflush(stdout);    exit(8); }
      
      /*  Get TAU period ahead trade weighted realized spread.*/
      if ( Ttau[0] != 0 )      /* market buy or sell TAU periods ago. If sell, Ttau[0]<0.  bid1 is bid before new trader.*/
        RealSprd += (double) Ttau[0] * 2.0*( (double)Ptau[0]- ((double)(bid1+ask1)) / 2.0 );
      for (p=0; p<TAU+1; p++) {
	Ttau[p] = Ttau[p+1];   /* Ttau[p] gives volume TAU-p periods ago */
	Ptau[p] = Ptau[p+1];   /* Ptau[p] gives price  TAU-p periods ago */
      } Ttau[TAU+1] = 0;       /* reset current period volume to 0       */


#if 0 /* need not have #Never Tremble + traders in Traders[] = #decisions, even when PrTremble = 0 */
      /* for example, when returning trader submits mkt order. byT[][][13] up by 2, but #Traders[] down by 2, so byT + #Traders unchanged,  but j++ */
      /* indeed, the difference in #decisions and #NEVER Tremble + #Ever Tremble is #Traders[] at end of nJ */
      k=0; for (p=0; p<nPx1; p++) { t= traders[p];  while(t!=NULL) { k++;  t= t->next; } }
      h=0; for(l=0; l<nT; l++) for(p=0; p<nPV; p++) h+=byT[l][p][13];
      if( h + k < j || DEBUG)  printf(" h~k~+~j= %u %d %u %u\n", h, k, h+k, j);
#endif

      /* b1 is number of traders who submitted at state SS in following commented code */
      /*
      if(SS!=NULL && SS->NN > SSNN)  { 
	SSNN = SS->NN;  
	if(j<10000 || j>nJ-10000) {
	  b1=0;  for(p=0; p<nPx1; p++) {  t= traders[p];  while (t!=NULL) {  if(t->s==S) b1++;  t = t->next; } }
	  printf("\nj= %7u  SS:  N~NN~NT~NC~Ntraders~+NN = %7u %6u %5u %7u %2d %7u   w~Ew~Pw+Ew= %8.5f %8.5f %8.5f  w - (Pw*PN + Ew)/(PN+NN) = %9.1e  N~PN+NN~diff= %u %u %u  \n",
		 j, SS->N,SS->NN,SS->NT,SS->NC, b1, SS->NN+b1, SS->w, SS->Ew/SSNN, (SS->Pw*SS->PN + SS->Ew)/(SS->PN+SSNN),   SS->w - (SS->Pw*SS->PN + SS->Ew)/(SS->PN+SSNN), SS->N, SS->PN+SSNN, SS->N - SS->PN-SSNN );
	} }
      */

      if (DEBUG==9) { printf("\n\n Aborting now, at end of j loop, since DEBUG=9 set earlier\n\n");  exit(9); }  /* delayed abort may provide helpful debugging info via printf()'s */
      /* if (j%100000==0) { printf("%u\n",j);  fflush(stdout); } */
      j++;

      /* if(j>9262282)  exit(1); */

    }   /* nJ loop */          /* if (DEBUG) printf("j=%6.0lu done with nJ loop \n",j); */

    if (outTQ!=NULL) { /* fprintf(outTQ, "end of nJ periods\n"); */  fclose(outTQ); }
    if (outM !=NULL) fclose(outM);      /* reset these files every jj */
    if (out2 !=NULL) fclose(out2);
#ifdef Use_outFringe
    if (outFringe!=NULL) fclose(outFringe);
#endif

    
    printf("\nNlimUP=  %u %u is number of limit (new or retained) & non orders updated ~ submitted. Should only differ by #orders in Book now (%d) \n", N_s[24], N_s[25], t1);
    N_s[25]=t1;  /* reset after j loop, not before, since need #orders on book.  N_s[24] reset to 0 after its value is printed later */


    p= nP;
    while(--p > -1 && Bt[p]<=0) { } Bt[nPx1] = p; /* high bid.  = -1 if no bids */
    while(++p < nP && Bt[p]>=0) { } Bt[nP]   = p; /* low  ask.  = nP if no asks */

    printf("\n");
    for(l=0;l<nT;l++) {
      printf(" Ttype %1d  vlag=%2d  UPDATE_mkt= %d  nB= %d  nH= %d  rho~e^-rho= %5.3f %6.4f  c= %5.3f  z= %1d  cdfPVT= ", l, vlagT[l]*nLag, UPDATE_mkt[l], nBT[l], nHT[l], -rhoT[l], exp(rhoT[l]), cT[l], zT[l]);
      for(m=0;m<nPV;m++) printf(" %5.3f ", cdfPVT[m][l]);
      printf("\n");
    }

    printf("\n                    :                "); for(l=0;l<nT;l++) printf("         "); printf("      "); for(p=0;p<nPV;p++) printf("         ");       printf("   UPDATE:");  for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8d",UPDATE_PVT[l][p]); printf("  ");}
    printf("\n                    :                "); for(l=0;l<nT;l++) printf("         "); printf("cdfPV:"); for(p=0;p<nPV;p++) printf(" %8.2f",cdfPV[p]); printf("   cdfPVT:");  for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.5f",  cdfPVT[p][l]); printf("  ");}
    printf("\n           type, pv :  Total,  Types:"); for(l=0;l<nT;l++) printf(" %8d",l);    printf("  PVs:"); for(p=0;p<nPV;p++) printf(" %8.2f",   PV[p]); printf("  type,pv:");  for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %2d %5.1f",  l, PV[p]); printf("  ");}
    printf("\n------------------- :  -----   -----:"); for(l=0;l<nT;l++) printf(" --------"); printf("  ---:"); for(p=0;p<nPV;p++) printf(" --------");       printf("  -------:");  for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" --------");             printf("  ");}
    printf("\n Traders  Arrived "); i=0;  h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++) h+= byT[l][p][i];} printf(" %9u         ",h);   for(l=0;l<nT;l++){h=0;for(p=0;p<nPV;p++) h+= byT[l][p][i]; printf(" %8u",h);} printf("      ");   for(p=0;p<nPV;p++){h=0;for(l=0;l<nT;l++) h+= byT[l][p][i]; printf(" %8u",h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8u",byT[l][p][i]); printf("  ");}
    printf("\n ...Never Tremble "); i=13; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++) h+= byT[l][p][i];} printf(" %9u         ",h);   for(l=0;l<nT;l++){h=0;for(p=0;p<nPV;p++) h+= byT[l][p][i]; printf(" %8u",h);} printf("      ");   for(p=0;p<nPV;p++){h=0;for(l=0;l<nT;l++) h+= byT[l][p][i]; printf(" %8u",h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8u",byT[l][p][i]); printf("  ");}
    printf("\n .....Did Tremble "); i=14; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++) h+= byT[l][p][i];} printf(" %9u         ",h);   for(l=0;l<nT;l++){h=0;for(p=0;p<nPV;p++) h+= byT[l][p][i]; printf(" %8u",h);} printf("      ");   for(p=0;p<nPV;p++){h=0;for(l=0;l<nT;l++) h+= byT[l][p][i]; printf(" %8u",h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8u",byT[l][p][i]); printf("  ");}
    printf("\n Decisions        "); i=1;  h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++) h+= byT[l][p][i];} printf(" %9u         ",h);   for(l=0;l<nT;l++){h=0;for(p=0;p<nPV;p++) h+= byT[l][p][i]; printf("%9u" ,h);} printf("      ");   for(p=0;p<nPV;p++){h=0;for(l=0;l<nT;l++) h+= byT[l][p][i]; printf(" %8u",h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8u",byT[l][p][i]); printf("  ");}
    printf("\n");
    printf("\n 1st-2nd best <.01"); i=11; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++) h+= byT[l][p][i];} printf(" %9u         ",h);   for(l=0;l<nT;l++){h=0;for(p=0;p<nPV;p++) h+= byT[l][p][i]; printf(" %8u",h);} printf("      ");   for(p=0;p<nPV;p++){h=0;for(l=0;l<nT;l++) h+= byT[l][p][i]; printf(" %8u",h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.6f",(double)byT[l][p][i]/byT[l][p][1]); printf("  ");}
    printf("\n            < 1e-4"); i=12; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++) h+= byT[l][p][i];} printf(" %9u         ",h);   for(l=0;l<nT;l++){h=0;for(p=0;p<nPV;p++) h+= byT[l][p][i]; printf(" %8u",h);} printf("      ");   for(p=0;p<nPV;p++){h=0;for(l=0;l<nT;l++) h+= byT[l][p][i]; printf(" %8u",h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.6f",(double)byT[l][p][i]/byT[l][p][1]); printf("  ");}
    printf("\n");
    printf("\n 1st best, mkt: v "); i=0;  k=2; u=0.0; v=0.0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){v+= VV[l][p][k]; u+=VV[l][p][i];}} printf(" %9.5f         ",u/v);   for(l=0;l<nT;l++){ v=0.0;u=0.0; for(p=0;p<nPV;p++){ v+= VV[l][p][k]; u+=VV[l][p][i];} printf(" %8.4f",u/v);} printf("      ");   for(p=0;p<nPV;p++){ v=0.0;u=0.0; for(l=0;l<nT;l++){ v+= VV[l][p][k]; u+=VV[l][p][i];} printf(" %8.4f",u/v);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",VV[l][p][i]/VV[l][p][k]); printf("  ");}
    printf("\n 2nd best, lim: v_"); i=1;  k=2; u=0.0; v=0.0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){v+= VV[l][p][k]; u+=VV[l][p][i];}} printf(" %9.5f         ",u/v);   for(l=0;l<nT;l++){ v=0.0;u=0.0; for(p=0;p<nPV;p++){ v+= VV[l][p][k]; u+=VV[l][p][i];} printf(" %8.4f",u/v);} printf("      ");   for(p=0;p<nPV;p++){ v=0.0;u=0.0; for(l=0;l<nT;l++){ v+= VV[l][p][k]; u+=VV[l][p][i];} printf(" %8.4f",u/v);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",VV[l][p][i]/VV[l][p][k]); printf("  ");}
    printf("\n              : N "); i=2;  k=2; u=0.0; v=0.0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){v+= VV[l][p][k];                }} printf(" %9.0f         ",  v);   for(l=0;l<nT;l++){ v=0.0;       for(p=0;p<nPV;p++){ v+= VV[l][p][k];                } printf(" %8.0f",  v);} printf("      ");   for(p=0;p<nPV;p++){ v=0.0;       for(l=0;l<nT;l++){ v+= VV[l][p][k];                } printf(" %8.0f",  v);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.0f",            VV[l][p][k]); printf("  ");}
    printf("\n");
    printf("\n 1st best, lim: v "); i=3;  k=5; u=0.0; v=0.0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){v+= VV[l][p][k]; u+=VV[l][p][i];}} printf(" %9.5f         ",u/v);   for(l=0;l<nT;l++){ v=0.0;u=0.0; for(p=0;p<nPV;p++){ v+= VV[l][p][k]; u+=VV[l][p][i];} printf(" %8.4f",u/v);} printf("      ");   for(p=0;p<nPV;p++){ v=0.0;u=0.0; for(l=0;l<nT;l++){ v+= VV[l][p][k]; u+=VV[l][p][i];} printf(" %8.4f",u/v);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",VV[l][p][i]/VV[l][p][k]); printf("  ");}
    printf("\n 2nd best, mkt: v_"); i=4;  k=5; u=0.0; v=0.0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){v+= VV[l][p][k]; u+=VV[l][p][i];}} printf(" %9.5f         ",u/v);   for(l=0;l<nT;l++){ v=0.0;u=0.0; for(p=0;p<nPV;p++){ v+= VV[l][p][k]; u+=VV[l][p][i];} printf(" %8.4f",u/v);} printf("      ");   for(p=0;p<nPV;p++){ v=0.0;u=0.0; for(l=0;l<nT;l++){ v+= VV[l][p][k]; u+=VV[l][p][i];} printf(" %8.4f",u/v);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",VV[l][p][i]/VV[l][p][k]); printf("  ");}
    printf("\n              : N "); i=5;  k=5; u=0.0; v=0.0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){v+= VV[l][p][k];                }} printf(" %9.0f         ",  v);   for(l=0;l<nT;l++){ v=0.0;       for(p=0;p<nPV;p++){ v+= VV[l][p][k];                } printf(" %8.0f",  v);} printf("      ");   for(p=0;p<nPV;p++){ v=0.0;       for(l=0;l<nT;l++){ v+= VV[l][p][k];                } printf(" %8.0f",  v);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.0f",            VV[l][p][k]); printf("  ");}
    printf("\n");
    printf("\n 1st best, lim: v "); i=6;  k=8; u=0.0; v=0.0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){v+= VV[l][p][k]; u+=VV[l][p][i];}} printf(" %9.5f         ",u/v);   for(l=0;l<nT;l++){ v=0.0;u=0.0; for(p=0;p<nPV;p++){ v+= VV[l][p][k]; u+=VV[l][p][i];} printf(" %8.4f",u/v);} printf("      ");   for(p=0;p<nPV;p++){ v=0.0;u=0.0; for(l=0;l<nT;l++){ v+= VV[l][p][k]; u+=VV[l][p][i];} printf(" %8.4f",u/v);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",VV[l][p][i]/VV[l][p][k]); printf("  ");}
    printf("\n 2nd best, lim: v_"); i=7;  k=8; u=0.0; v=0.0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){v+= VV[l][p][k]; u+=VV[l][p][i];}} printf(" %9.5f         ",u/v);   for(l=0;l<nT;l++){ v=0.0;u=0.0; for(p=0;p<nPV;p++){ v+= VV[l][p][k]; u+=VV[l][p][i];} printf(" %8.4f",u/v);} printf("      ");   for(p=0;p<nPV;p++){ v=0.0;u=0.0; for(l=0;l<nT;l++){ v+= VV[l][p][k]; u+=VV[l][p][i];} printf(" %8.4f",u/v);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",VV[l][p][i]/VV[l][p][k]); printf("  ");}
    printf("\n              : N "); i=8;  k=8; u=0.0; v=0.0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){v+= VV[l][p][k];                }} printf(" %9.0f         ",  v);   for(l=0;l<nT;l++){ v=0.0;       for(p=0;p<nPV;p++){ v+= VV[l][p][k];                } printf(" %8.0f",  v);} printf("      ");   for(p=0;p<nPV;p++){ v=0.0;       for(l=0;l<nT;l++){ v+= VV[l][p][k];                } printf(" %8.0f",  v);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.0f",            VV[l][p][k]); printf("  ");}
    printf("\n");
    printf("\n 1st best, non: v "); i=9;  k=11;u=0.0; v=0.0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){v+= VV[l][p][k]; u+=VV[l][p][i];}} printf(" %9.5f         ",u/v);   for(l=0;l<nT;l++){ v=0.0;u=0.0; for(p=0;p<nPV;p++){ v+= VV[l][p][k]; u+=VV[l][p][i];} printf(" %8.4f",u/v);} printf("      ");   for(p=0;p<nPV;p++){ v=0.0;u=0.0; for(l=0;l<nT;l++){ v+= VV[l][p][k]; u+=VV[l][p][i];} printf(" %8.4f",u/v);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",VV[l][p][i]/VV[l][p][k]); printf("  ");}
    printf("\n 2nd best, any: v_"); i=10; k=11;u=0.0; v=0.0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){v+= VV[l][p][k]; u+=VV[l][p][i];}} printf(" %9.5f         ",u/v);   for(l=0;l<nT;l++){ v=0.0;u=0.0; for(p=0;p<nPV;p++){ v+= VV[l][p][k]; u+=VV[l][p][i];} printf(" %8.4f",u/v);} printf("      ");   for(p=0;p<nPV;p++){ v=0.0;u=0.0; for(l=0;l<nT;l++){ v+= VV[l][p][k]; u+=VV[l][p][i];} printf(" %8.4f",u/v);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",VV[l][p][i]/VV[l][p][k]); printf("  ");}
    printf("\n (new tt only): N "); i=11; k=11;u=0.0; v=0.0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){v+= VV[l][p][k];                }} printf(" %9.0f         ",  v);   for(l=0;l<nT;l++){ v=0.0;       for(p=0;p<nPV;p++){ v+= VV[l][p][k];                } printf(" %8.0f",  v);} printf("      ");   for(p=0;p<nPV;p++){ v=0.0;       for(l=0;l<nT;l++){ v+= VV[l][p][k];                } printf(" %8.0f",  v);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.0f",            VV[l][p][k]); printf("  ");}
    printf("\n");
    printf("\n No Actions       "); i=2;  h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++) h+= byT[l][p][i];} printf(" %9u         ",h);   for(l=0;l<nT;l++){h=0;for(p=0;p<nPV;p++) h+= byT[l][p][i]; printf(" %8u",h);} printf("      ");   for(p=0;p<nPV;p++){h=0;for(l=0;l<nT;l++) h+= byT[l][p][i]; printf(" %8u",h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8u",byT[l][p][i]); printf("  ");}
    printf("\n Non    Orders    "); i=3;  h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++) h+= byT[l][p][i];} printf(" %9u         ",h);   for(l=0;l<nT;l++){h=0;for(p=0;p<nPV;p++) h+= byT[l][p][i]; printf(" %8u",h);} printf("      ");   for(p=0;p<nPV;p++){h=0;for(l=0;l<nT;l++) h+= byT[l][p][i]; printf(" %8u",h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8u",byT[l][p][i]); printf("  ");}
    printf("\n Market Orders    "); i=4;  h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++) h+= byT[l][p][i];} printf(" %9u         ",h);   for(l=0;l<nT;l++){h=0;for(p=0;p<nPV;p++) h+= byT[l][p][i]; printf(" %8u",h);} printf("      ");   for(p=0;p<nPV;p++){h=0;for(l=0;l<nT;l++) h+= byT[l][p][i]; printf(" %8u",h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8u",byT[l][p][i]); printf("  ");}
    printf("\n        at arrival"); i=19; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++) h+= byT[l][p][i];} printf(" %9u         ",h);   for(l=0;l<nT;l++){h=0;for(p=0;p<nPV;p++) h+= byT[l][p][i]; printf(" %8u",h);} printf("      ");   for(p=0;p<nPV;p++){h=0;for(l=0;l<nT;l++) h+= byT[l][p][i]; printf(" %8u",h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8u",byT[l][p][i]); printf("  ");}
    printf("\n Limit  Orders    "); i=5;  h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++) h+= byT[l][p][i];} printf(" %9u         ",h);   for(l=0;l<nT;l++){h=0;for(p=0;p<nPV;p++) h+= byT[l][p][i]; printf(" %8u",h);} printf("      ");   for(p=0;p<nPV;p++){h=0;for(l=0;l<nT;l++) h+= byT[l][p][i]; printf(" %8u",h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8u",byT[l][p][i]); printf("  ");}
    printf("\n");
    printf("\n Market Buys      "); i=15; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++) h+= byT[l][p][i];} printf(" %9u         ",h);   for(l=0;l<nT;l++){h=0;for(p=0;p<nPV;p++) h+= byT[l][p][i]; printf(" %8u",h);} printf("      ");   for(p=0;p<nPV;p++){h=0;for(l=0;l<nT;l++) h+= byT[l][p][i]; printf(" %8u",h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8u",byT[l][p][i]); printf("  ");}
    printf("\n Market Sells     "); i=16; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++) h+= byT[l][p][i];} printf(" %9u         ",h);   for(l=0;l<nT;l++){h=0;for(p=0;p<nPV;p++) h+= byT[l][p][i]; printf(" %8u",h);} printf("      ");   for(p=0;p<nPV;p++){h=0;for(l=0;l<nT;l++) h+= byT[l][p][i]; printf(" %8u",h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8u",byT[l][p][i]); printf("  ");}
    printf("\n Limit  Buys      "); i=17; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++) h+= byT[l][p][i];} printf(" %9u         ",h);   for(l=0;l<nT;l++){h=0;for(p=0;p<nPV;p++) h+= byT[l][p][i]; printf(" %8u",h);} printf("      ");   for(p=0;p<nPV;p++){h=0;for(l=0;l<nT;l++) h+= byT[l][p][i]; printf(" %8u",h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8u",byT[l][p][i]); printf("  ");}
    printf("\n Limit  Sells     "); i=18; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++) h+= byT[l][p][i];} printf(" %9u         ",h);   for(l=0;l<nT;l++){h=0;for(p=0;p<nPV;p++) h+= byT[l][p][i]; printf(" %8u",h);} printf("      ");   for(p=0;p<nPV;p++){h=0;for(l=0;l<nT;l++) h+= byT[l][p][i]; printf(" %8u",h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8u",byT[l][p][i]); printf("  ");}
    printf("\n");
    printf("\n Retained Orders  "); i=6;  h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++) h+= byT[l][p][i];} printf(" %9u         ",h);   for(l=0;l<nT;l++){h=0;for(p=0;p<nPV;p++) h+= byT[l][p][i]; printf(" %8u",h);} printf("      ");   for(p=0;p<nPV;p++){h=0;for(l=0;l<nT;l++) h+= byT[l][p][i]; printf(" %8u",h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8u",byT[l][p][i]); printf("  ");}
    printf("\n Replaced Orders  "); i=7;  h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++) h+= byT[l][p][i];} printf(" %9u         ",h);   for(l=0;l<nT;l++){h=0;for(p=0;p<nPV;p++) h+= byT[l][p][i]; printf(" %8u",h);} printf("      ");   for(p=0;p<nPV;p++){h=0;for(l=0;l<nT;l++) h+= byT[l][p][i]; printf(" %8u",h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8u",byT[l][p][i]); printf("  ");}
    printf("\n Limits get CS<0  "); i=8;  h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++) h+= byT[l][p][i];} printf(" %9u         ",h);   for(l=0;l<nT;l++){h=0;for(p=0;p<nPV;p++) h+= byT[l][p][i]; printf(" %8u",h);} printf("      ");   for(p=0;p<nPV;p++){h=0;for(l=0;l<nT;l++) h+= byT[l][p][i]; printf(" %8u",h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8u",byT[l][p][i]); printf("  ");}
    printf("\n Limits execute   "); i=9;  h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++) h+= byT[l][p][i];} printf(" %9u         ",h);   for(l=0;l<nT;l++){h=0;for(p=0;p<nPV;p++) h+= byT[l][p][i]; printf(" %8u",h);} printf("      ");   for(p=0;p<nPV;p++){h=0;for(l=0;l<nT;l++) h+= byT[l][p][i]; printf(" %8u",h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8u",byT[l][p][i]); printf("  ");}
    printf("\n Trembles         "); i=10; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++) h+= byT[l][p][i];} printf(" %9u         ",h);   for(l=0;l<nT;l++){h=0;for(p=0;p<nPV;p++) h+= byT[l][p][i]; printf(" %8u",h);} printf("      ");   for(p=0;p<nPV;p++){h=0;for(l=0;l<nT;l++) h+= byT[l][p][i]; printf(" %8u",h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8u",byT[l][p][i]); printf("  ");}
    printf("\n pre-tremble:");
    printf("\n p-E(v), 1st order"); i=14; k=15; u=0.0; v=0.0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){ u+=CS[l][p][i]; v+=CS[l][p][k];}} printf(" %9.5f         ",u/v);   for(l=0;l<nT;l++){u=0.0;v=0.0;for(p=0;p<nPV;p++) {u+=CS[l][p][i]; v+=CS[l][p][k];} printf(" %8.4f",u/v);} printf("      ");   for(p=0;p<nPV;p++){u=0.0;v=0.0;for(l=0;l<nT;l++) {u+=CS[l][p][i]; v+=CS[l][p][k];} printf(" %8.4f",u/v);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",CS[l][p][i]/CS[l][p][k]); printf("  ");}
    printf("\n p-E(v), any order"); i=16; k=17; u=0.0; v=0.0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){ u+=CS[l][p][i]; v+=CS[l][p][k];}} printf(" %9.5f         ",u/v);   for(l=0;l<nT;l++){u=0.0;v=0.0;for(p=0;p<nPV;p++) {u+=CS[l][p][i]; v+=CS[l][p][k];} printf(" %8.4f",u/v);} printf("      ");   for(p=0;p<nPV;p++){u=0.0;v=0.0;for(l=0;l<nT;l++) {u+=CS[l][p][i]; v+=CS[l][p][k];} printf(" %8.4f",u/v);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",CS[l][p][i]/CS[l][p][k]); printf("  ");}
    printf("\n");
    for(i=21;i<21+nPlim;i++) {
      printf("\n bid aggress =%3d ",i-21-(nPlim-1)/2); h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++) h+= byT[l][p][i];} printf(" %9u         ",h);   for(l=0;l<nT;l++){h=0;for(p=0;p<nPV;p++) h+= byT[l][p][i]; printf(" %8u",h);} printf("      ");   for(p=0;p<nPV;p++){h=0;for(l=0;l<nT;l++) h+= byT[l][p][i]; printf(" %8u",h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8u",byT[l][p][i]); printf("  ");}
    } printf("\n");
    for(k=21;k<21+nPlim;k++) {
      printf("\n exec by agg =%3d ",k-21-(nPlim-1)/2);       i=k+nPlim;  u=0.0; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){h+= byT[l][p][k]; u+=byT[l][p][i];}} printf(" %9.5f         ",u/h);      for(l=0;l<nT;l++){ h=0;u=0.0; for(p=0;p<nPV;p++){ h+= byT[l][p][k]; u+=byT[l][p][i];} printf(" %8.4f",u/h     );} printf("      ");   for(p=0;p<nPV;p++){ h=0;u=0.0; for(l=0;l<nT;l++){ h+= byT[l][p][k]; u+=byT[l][p][i];} printf(" %8.4f",u/h);}       printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",byT[l][p][i]/(double)byT[l][p][k]); printf("  ");}
    } printf("\n");
    for(k=21+nPlim;k<21+nPlim+nPlim;k++) {  /* p-v is summed as  PVmu+ p-v since byT is unsigned int */
      printf("\n p-v  by agg =%3d ",k-nPlim-21-(nPlim-1)/2); i=k+nPlim;  u=0.0; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){h+= byT[l][p][k]; u+=byT[l][p][i];}} printf(" %9.5f         ",u/h-PVmu); for(l=0;l<nT;l++){ h=0;u=0.0; for(p=0;p<nPV;p++){ h+= byT[l][p][k]; u+=byT[l][p][i];} printf(" %8.4f",u/h-PVmu);} printf("      ");   for(p=0;p<nPV;p++){ h=0;u=0.0; for(l=0;l<nT;l++){ h+= byT[l][p][k]; u+=byT[l][p][i];} printf(" %8.4f",u/h-PVmu);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",byT[l][p][i]/(double)byT[l][p][k]-PVmu); printf("  ");}
    } /*
    for(i=21+nPlim;i<21+nPlim+nPlim;i++) {
      printf("\n exec by agg = %2d ",i-21-nPlim-(nPlim-1)/2); h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++) h+= byT[l][p][i];} printf(" %9u         ",h);   for(l=0;l<nT;l++){h=0;for(p=0;p<nPV;p++) h+= byT[l][p][i]; printf(" %8u",h);} printf("      ");   for(p=0;p<nPV;p++){h=0;for(l=0;l<nT;l++) h+= byT[l][p][i]; printf(" %8u",h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8u",byT[l][p][i]); printf("  ");}
      } */
    printf("\n");
    printf("\n |E(v) rel lastv| "); i=10; k=1; u=0.0; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){h+= byT[l][p][k]; u+=CS[l][p][i];}} printf(" %9.5f         ",u/h);   for(l=0;l<nT;l++){ h=0;u=0.0; for(p=0;p<nPV;p++){ h+= byT[l][p][k]; u+=CS[l][p][i];} printf(" %8.4f",u/h);} printf("      ");   for(p=0;p<nPV;p++){ h=0;u=0.0; for(l=0;l<nT;l++){ h+= byT[l][p][k]; u+=CS[l][p][i];} printf(" %8.4f",u/h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",CS[l][p][i]/byT[l][p][k]); printf("  ");}
    printf("\n |E(v)+lastv|     "); i=11; k=1; u=0.0; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){h+= byT[l][p][k]; u+=CS[l][p][i];}} printf(" %9.5f         ",u/h);   for(l=0;l<nT;l++){ h=0;u=0.0; for(p=0;p<nPV;p++){ h+= byT[l][p][k]; u+=CS[l][p][i];} printf(" %8.4f",u/h);} printf("      ");   for(p=0;p<nPV;p++){ h=0;u=0.0; for(l=0;l<nT;l++){ h+= byT[l][p][k]; u+=CS[l][p][i];} printf(" %8.4f",u/h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",CS[l][p][i]/byT[l][p][k]); printf("  ");}
    printf("\n  E(v)+lastv  =0? "); i=12; k=1; u=0.0; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){h+= byT[l][p][k]; u+=CS[l][p][i];}} printf(" %9.5f         ",u/h);   for(l=0;l<nT;l++){ h=0;u=0.0; for(p=0;p<nPV;p++){ h+= byT[l][p][k]; u+=CS[l][p][i];} printf(" %8.4f",u/h);} printf("      ");   for(p=0;p<nPV;p++){ h=0;u=0.0; for(l=0;l<nT;l++){ h+= byT[l][p][k]; u+=CS[l][p][i];} printf(" %8.4f",u/h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",CS[l][p][i]/byT[l][p][k]); printf("  ");}
    printf("\n");
    printf("\n RT per No Trem.  "); i=13;k=13;u=0.0; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){h+= byT[l][p][k]; u+=CS[l][p][i];}} printf(" %9.5f         ",u/h);   for(l=0;l<nT;l++){ h=0;u=0.0; for(p=0;p<nPV;p++){ h+= byT[l][p][k]; u+=CS[l][p][i];} printf(" %8.4f",u/h);} printf("      ");   for(p=0;p<nPV;p++){ h=0;u=0.0; for(l=0;l<nT;l++){ h+= byT[l][p][k]; u+=CS[l][p][i];} printf(" %8.4f",u/h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",CS[l][p][i]/byT[l][p][k]); printf("  ");}
    printf("\n CS per No Trem.  "); i=7; k=13;u=0.0; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){h+= byT[l][p][k]; u+=CS[l][p][i];}} printf(" %9.5f         ",u/h);   for(l=0;l<nT;l++){ h=0;u=0.0; for(p=0;p<nPV;p++){ h+= byT[l][p][k]; u+=CS[l][p][i];} printf(" %8.4f",u/h);} printf("      ");   for(p=0;p<nPV;p++){ h=0;u=0.0; for(l=0;l<nT;l++){ h+= byT[l][p][k]; u+=CS[l][p][i];} printf(" %8.4f",u/h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",CS[l][p][i]/byT[l][p][k]); printf("  ");}
    printf("\n        std dev.  "); i=7; k=13;u=0.0; v=0.0; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){h+= byT[l][p][k]; u+=CS[l][p][i]; v+=CS[l][p][9];}} printf(" %9.5f         ",sqrt( v/h-(u/h)*(u/h)   ));  for(l=0;l<nT;l++){ h=0;u=0.0;v=0.0; for(p=0;p<nPV;p++){ h+= byT[l][p][k]; u+=CS[l][p][i];v+=CS[l][p][9];} printf(" %8.4f",sqrt( v/h-(u/h)*(u/h)   ));} printf("      ");   for(p=0;p<nPV;p++){ h=0;u=0.0;v=0.0; for(l=0;l<nT;l++){ h+= byT[l][p][k]; u+=CS[l][p][i];v+=CS[l][p][9];} printf(" %8.4f",sqrt( v/h-(u/h)*(u/h)   ));}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++){ h=byT[l][p][k]; u=CS[l][p][i]; printf(" %8.4f",sqrt(  CS[l][p][9]/h- (u/h)*(u/h)    ));} printf("  ");}
    printf("\n        std err.  "); i=7; k=13;u=0.0; v=0.0; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){h+= byT[l][p][k]; u+=CS[l][p][i]; v+=CS[l][p][9];}} printf(" %9.5f         ",sqrt((v/h-(u/h)*(u/h))/h));  for(l=0;l<nT;l++){ h=0;u=0.0;v=0.0; for(p=0;p<nPV;p++){ h+= byT[l][p][k]; u+=CS[l][p][i];v+=CS[l][p][9];} printf(" %8.4f",sqrt((v/h-(u/h)*(u/h))/h));} printf("      ");   for(p=0;p<nPV;p++){ h=0;u=0.0;v=0.0; for(l=0;l<nT;l++){ h+= byT[l][p][k]; u+=CS[l][p][i];v+=CS[l][p][9];} printf(" %8.4f",sqrt((v/h-(u/h)*(u/h))/h));}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++){ h=byT[l][p][k]; u=CS[l][p][i]; printf(" %8.4f",sqrt( (CS[l][p][9]/h- (u/h)*(u/h))/h ));} printf("  ");}
    printf("\n CS per Do Trem.  "); i=8; k=14;u=0.0; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){h+= byT[l][p][k]; u+=CS[l][p][i];}} printf(" %9.5f         ",u/h);   for(l=0;l<nT;l++){ h=0;u=0.0; for(p=0;p<nPV;p++){ h+= byT[l][p][k]; u+=CS[l][p][i];} printf(" %8.4f",u/h);} printf("      ");   for(p=0;p<nPV;p++){ h=0;u=0.0; for(l=0;l<nT;l++){ h+= byT[l][p][k]; u+=CS[l][p][i];} printf(" %8.4f",u/h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",CS[l][p][i]/byT[l][p][k]); printf("  ");}
    printf("\n CS per tt disc   ");      k=0; u=0.0; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){h+= byT[l][p][k]; u+=CS[l][p][0]+CS[l][p][1];}} printf(" %9.5f         ",u/h);  for(l=0;l<nT;l++){ h=0;u=0.0; for(p=0;p<nPV;p++){ h+= byT[l][p][k]; u+=CS[l][p][0]+CS[l][p][1];} printf(" %8.4f",u/h);} printf("      ");  for(p=0;p<nPV;p++){ h=0;u=0.0; for(l=0;l<nT;l++){ h+= byT[l][p][k]; u+=CS[l][p][0]+CS[l][p][1];} printf(" %8.4f",u/h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",(CS[l][p][0]+CS[l][p][1])/byT[l][p][k]); printf("  ");}
    printf("\n         undisc   ");      k=0; u=0.0; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){h+= byT[l][p][k]; u+=CS[l][p][4]+CS[l][p][5];}} printf(" %9.5f         ",u/h);  for(l=0;l<nT;l++){ h=0;u=0.0; for(p=0;p<nPV;p++){ h+= byT[l][p][k]; u+=CS[l][p][4]+CS[l][p][5];} printf(" %8.4f",u/h);} printf("      ");  for(p=0;p<nPV;p++){ h=0;u=0.0; for(l=0;l<nT;l++){ h+= byT[l][p][k]; u+=CS[l][p][4]+CS[l][p][5];} printf(" %8.4f",u/h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",(CS[l][p][4]+CS[l][p][5])/byT[l][p][k]); printf("  ");}
    printf("\n");
    printf("\n CS per transact  ");      k=4; u=0.0; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){h+= byT[l][p][k]; u+=CS[l][p][0]+CS[l][p][1];}} printf(" %9.5f",u/h);
    printf("\n CS per realtime  ");      k=0; u=0.0; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){                  u+=CS[l][p][0]+CS[l][p][1];}} printf(" %9.5f",u/realtime);
    printf("\n");
    printf("\n CS per mkt, disc "); i=1; k=4; u=0.0; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){h+= byT[l][p][k]; u+=CS[l][p][i];}} printf(" %9.5f         ",u/h);   for(l=0;l<nT;l++){ h=0;u=0.0; for(p=0;p<nPV;p++){ h+= byT[l][p][k]; u+=CS[l][p][i];} printf(" %8.4f",u/h);} printf("      ");   for(p=0;p<nPV;p++){ h=0;u=0.0; for(l=0;l<nT;l++){ h+= byT[l][p][k]; u+=CS[l][p][i];} printf(" %8.4f",u/h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",CS[l][p][i]/byT[l][p][k]); printf("  ");}
    printf("\n ..........undisc "); i=5; k=4; u=0.0; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){h+= byT[l][p][k]; u+=CS[l][p][i];}} printf(" %9.5f         ",u/h);   for(l=0;l<nT;l++){ h=0;u=0.0; for(p=0;p<nPV;p++){ h+= byT[l][p][k]; u+=CS[l][p][i];} printf(" %8.4f",u/h);} printf("      ");   for(p=0;p<nPV;p++){ h=0;u=0.0; for(l=0;l<nT;l++){ h+= byT[l][p][k]; u+=CS[l][p][i];} printf(" %8.4f",u/h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",CS[l][p][i]/byT[l][p][k]); printf("  ");}
    printf("\n RT per mkt order "); i=3; k=4; u=0.0; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){h+= byT[l][p][k]; u+=CS[l][p][i];}} printf(" %9.5f         ",u/h);   for(l=0;l<nT;l++){ h=0;u=0.0; for(p=0;p<nPV;p++){ h+= byT[l][p][k]; u+=CS[l][p][i];} printf(" %8.4f",u/h);} printf("      ");   for(p=0;p<nPV;p++){ h=0;u=0.0; for(l=0;l<nT;l++){ h+= byT[l][p][k]; u+=CS[l][p][i];} printf(" %8.4f",u/h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",CS[l][p][i]/byT[l][p][k]); printf("  ");}
    printf("\n Trans  Cost  ave "); i=6; k=4; u=0.0; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){h+= byT[l][p][k]; u+=CS[l][p][i];}} printf(" %9.5f         ",u/h);   for(l=0;l<nT;l++){ h=0;u=0.0; for(p=0;p<nPV;p++){ h+= byT[l][p][k]; u+=CS[l][p][i];} printf(" %8.4f",u/h);} printf("      ");   for(p=0;p<nPV;p++){ h=0;u=0.0; for(l=0;l<nT;l++){ h+= byT[l][p][k]; u+=CS[l][p][i];} printf(" %8.4f",u/h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",CS[l][p][i]/byT[l][p][k]); printf("  ");}
    printf("\n");  /* Trans Cost ave + undisc.CS  =  |PV| ONLY IF always sell, or always buy, or PV = 0 */
    printf("\n CS lim subm disc "); i=0; k=5; u=0.0; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){h+= byT[l][p][k]; u+=CS[l][p][i];}} printf(" %9.5f         ",u/h);   for(l=0;l<nT;l++){ h=0;u=0.0; for(p=0;p<nPV;p++){ h+= byT[l][p][k]; u+=CS[l][p][i];} printf(" %8.4f",u/h);} printf("      ");   for(p=0;p<nPV;p++){ h=0;u=0.0; for(l=0;l<nT;l++){ h+= byT[l][p][k]; u+=CS[l][p][i];} printf(" %8.4f",u/h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",CS[l][p][i]/byT[l][p][k]); printf("  ");}
    printf("\n ..........undisc "); i=4; k=5; u=0.0; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){h+= byT[l][p][k]; u+=CS[l][p][i];}} printf(" %9.5f         ",u/h);   for(l=0;l<nT;l++){ h=0;u=0.0; for(p=0;p<nPV;p++){ h+= byT[l][p][k]; u+=CS[l][p][i];} printf(" %8.4f",u/h);} printf("      ");   for(p=0;p<nPV;p++){ h=0;u=0.0; for(l=0;l<nT;l++){ h+= byT[l][p][k]; u+=CS[l][p][i];} printf(" %8.4f",u/h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",CS[l][p][i]/byT[l][p][k]); printf("  ");}
    printf("\n CS lim exec disc "); i=0; k=9; u=0.0; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){h+= byT[l][p][k]; u+=CS[l][p][i];}} printf(" %9.5f         ",u/h);   for(l=0;l<nT;l++){ h=0;u=0.0; for(p=0;p<nPV;p++){ h+= byT[l][p][k]; u+=CS[l][p][i];} printf(" %8.4f",u/h);} printf("      ");   for(p=0;p<nPV;p++){ h=0;u=0.0; for(l=0;l<nT;l++){ h+= byT[l][p][k]; u+=CS[l][p][i];} printf(" %8.4f",u/h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",CS[l][p][i]/byT[l][p][k]); printf("  ");}
    printf("\n ..........undisc "); i=4; k=9; u=0.0; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){h+= byT[l][p][k]; u+=CS[l][p][i];}} printf(" %9.5f         ",u/h);   for(l=0;l<nT;l++){ h=0;u=0.0; for(p=0;p<nPV;p++){ h+= byT[l][p][k]; u+=CS[l][p][i];} printf(" %8.4f",u/h);} printf("      ");   for(p=0;p<nPV;p++){ h=0;u=0.0; for(l=0;l<nT;l++){ h+= byT[l][p][k]; u+=CS[l][p][i];} printf(" %8.4f",u/h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",CS[l][p][i]/byT[l][p][k]); printf("  ");}
    printf("\n RT per lim  exec "); i=2; k=9; u=0.0; h=0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){h+= byT[l][p][k]; u+=CS[l][p][i];}} printf(" %9.5f         ",u/h);   for(l=0;l<nT;l++){ h=0;u=0.0; for(p=0;p<nPV;p++){ h+= byT[l][p][k]; u+=CS[l][p][i];} printf(" %8.4f",u/h);} printf("      ");   for(p=0;p<nPV;p++){ h=0;u=0.0; for(l=0;l<nT;l++){ h+= byT[l][p][k]; u+=CS[l][p][i];} printf(" %8.4f",u/h);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",CS[l][p][i]/byT[l][p][k]); printf("  ");}
    printf("\n");
    printf("\n #Mkts spread = 1 "); i=1; u=0.0; v=0.0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){v+= TC[l][p][0][i];                   }} printf(" %9.0f         ",  v);   for(l=0;l<nT;l++){ v=0.0;u=0.0; for(p=0;p<nPV;p++){ v+= TC[l][p][0][i];                   } printf(" %8.0f",  v);} printf("      ");   for(p=0;p<nPV;p++){ v=0.0;u=0.0; for(l=0;l<nT;l++){ v+= TC[l][p][0][i];                   } printf(" %8.0f"  ,v);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.0f",(double)TC[l][p][0][i]); printf("  ");}
    printf("\n #Mkts spread = 2 "); i=2; u=0.0; v=0.0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){v+= TC[l][p][0][i];                   }} printf(" %9.0f         ",  v);   for(l=0;l<nT;l++){ v=0.0;u=0.0; for(p=0;p<nPV;p++){ v+= TC[l][p][0][i];                   } printf(" %8.0f",  v);} printf("      ");   for(p=0;p<nPV;p++){ v=0.0;u=0.0; for(l=0;l<nT;l++){ v+= TC[l][p][0][i];                   } printf(" %8.0f"  ,v);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.0f",(double)TC[l][p][0][i]); printf("  ");}
    printf("\n #Mkts spread = 3 "); i=3; u=0.0; v=0.0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){v+= TC[l][p][0][i];                   }} printf(" %9.0f         ",  v);   for(l=0;l<nT;l++){ v=0.0;u=0.0; for(p=0;p<nPV;p++){ v+= TC[l][p][0][i];                   } printf(" %8.0f",  v);} printf("      ");   for(p=0;p<nPV;p++){ v=0.0;u=0.0; for(l=0;l<nT;l++){ v+= TC[l][p][0][i];                   } printf(" %8.0f"  ,v);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.0f",(double)TC[l][p][0][i]); printf("  ");}
    printf("\n #Mkts def. s > 3 "); i=4; u=0.0; v=0.0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){v+= TC[l][p][0][i];                   }} printf(" %9.0f         ",  v);   for(l=0;l<nT;l++){ v=0.0;u=0.0; for(p=0;p<nPV;p++){ v+= TC[l][p][0][i];                   } printf(" %8.0f",  v);} printf("      ");   for(p=0;p<nPV;p++){ v=0.0;u=0.0; for(l=0;l<nT;l++){ v+= TC[l][p][0][i];                   } printf(" %8.0f"  ,v);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.0f",(double)TC[l][p][0][i]); printf("  ");}
    printf("\n #Mkts      s  na "); i=0; u=0.0; v=0.0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){v+= TC[l][p][0][i];                   }} printf(" %9.0f         ",  v);   for(l=0;l<nT;l++){ v=0.0;u=0.0; for(p=0;p<nPV;p++){ v+= TC[l][p][0][i];                   } printf(" %8.0f",  v);} printf("      ");   for(p=0;p<nPV;p++){ v=0.0;u=0.0; for(l=0;l<nT;l++){ v+= TC[l][p][0][i];                   } printf(" %8.0f"  ,v);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.0f",(double)TC[l][p][0][i]); printf("  ");}
    printf("\n TransCost  s = 1 "); i=1; u=0.0; v=0.0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){v+= TC[l][p][0][i]; u+=TC[l][p][1][i];}} printf(" %9.5f         ",u/v);   for(l=0;l<nT;l++){ v=0.0;u=0.0; for(p=0;p<nPV;p++){ v+= TC[l][p][0][i]; u+=TC[l][p][1][i];} printf(" %8.4f",u/v);} printf("      ");   for(p=0;p<nPV;p++){ v=0.0;u=0.0; for(l=0;l<nT;l++){ v+= TC[l][p][0][i]; u+=TC[l][p][1][i];} printf(" %8.4f",u/v);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",(double)TC[l][p][1][i]/TC[l][p][0][i]); printf("  ");}
    printf("\n TransCost  s = 2 "); i=2; u=0.0; v=0.0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){v+= TC[l][p][0][i]; u+=TC[l][p][1][i];}} printf(" %9.5f         ",u/v);   for(l=0;l<nT;l++){ v=0.0;u=0.0; for(p=0;p<nPV;p++){ v+= TC[l][p][0][i]; u+=TC[l][p][1][i];} printf(" %8.4f",u/v);} printf("      ");   for(p=0;p<nPV;p++){ v=0.0;u=0.0; for(l=0;l<nT;l++){ v+= TC[l][p][0][i]; u+=TC[l][p][1][i];} printf(" %8.4f",u/v);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",(double)TC[l][p][1][i]/TC[l][p][0][i]); printf("  ");}
    printf("\n TransCost  s = 3 "); i=3; u=0.0; v=0.0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){v+= TC[l][p][0][i]; u+=TC[l][p][1][i];}} printf(" %9.5f         ",u/v);   for(l=0;l<nT;l++){ v=0.0;u=0.0; for(p=0;p<nPV;p++){ v+= TC[l][p][0][i]; u+=TC[l][p][1][i];} printf(" %8.4f",u/v);} printf("      ");   for(p=0;p<nPV;p++){ v=0.0;u=0.0; for(l=0;l<nT;l++){ v+= TC[l][p][0][i]; u+=TC[l][p][1][i];} printf(" %8.4f",u/v);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",(double)TC[l][p][1][i]/TC[l][p][0][i]); printf("  ");}
    printf("\n TransCost  s > 3 "); i=4; u=0.0; v=0.0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){v+= TC[l][p][0][i]; u+=TC[l][p][1][i];}} printf(" %9.5f         ",u/v);   for(l=0;l<nT;l++){ v=0.0;u=0.0; for(p=0;p<nPV;p++){ v+= TC[l][p][0][i]; u+=TC[l][p][1][i];} printf(" %8.4f",u/v);} printf("      ");   for(p=0;p<nPV;p++){ v=0.0;u=0.0; for(l=0;l<nT;l++){ v+= TC[l][p][0][i]; u+=TC[l][p][1][i];} printf(" %8.4f",u/v);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",(double)TC[l][p][1][i]/TC[l][p][0][i]); printf("  ");}
    printf("\n TransCost  s  na "); i=0; u=0.0; v=0.0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++){v+= TC[l][p][0][i]; u+=TC[l][p][1][i];}} printf(" %9.5f         ",u/v);   for(l=0;l<nT;l++){ v=0.0;u=0.0; for(p=0;p<nPV;p++){ v+= TC[l][p][0][i]; u+=TC[l][p][1][i];} printf(" %8.4f",u/v);} printf("      ");   for(p=0;p<nPV;p++){ v=0.0;u=0.0; for(l=0;l<nT;l++){ v+= TC[l][p][0][i]; u+=TC[l][p][1][i];} printf(" %8.4f",u/v);}  printf("          "); for(l=0;l<nT;l++){for(p=0;p<nPV;p++) printf(" %8.4f",(double)TC[l][p][1][i]/TC[l][p][0][i]); printf("  ");}

#if use_w0sell  /* since using N0sell[] as temp storage, but N0sell[] not defined if w0adjust <= 0.0 */
    if (zmax) { /* temp usage of double N0sell[nTmax][nPmax][nPmax][nPmax][nPV] requires nPmax >= zmax, nPmax >=4.  ALSO requires nPmax >= nP+1, which will always be case if nP is odd */
      printf("\n\n NOTES: if LARGE trader, ultimate realized outcomes EXCLUDE mkt orders from INITIAL period since such orders NOT part of initial CONTINUATION state.  Hence, may find: CS per Do Trem > CS per No Trem.");

      printf("\n        zpq tables are Post tz->tremble==2 (could change to NEVER tremble).   zpq[] converts Large sellers to buyers (assumes ALL large traders have same |PV|).  tz->q is shares still available to trade.\n");
      printf("\n   (p=nP is missing ask)   p :tz->q= 0");   for(k=1;k<=zmax;k++) printf(" %7d", k);   printf("  col_sum");
      printf("\n                          ==  ");           for(k=0;k<=zmax;k++) printf(" =======");  printf("  =======");
      for(k=0;k<=zmax;k++) {             /* zmax <= nPmax REQUIRED or seg fault */
	N0sell[0][0][k][2][0] = 0.0;  /* temp store of total asks, by z togo */ 
	N0sell[0][0][k][3][0] = 0.0;} /* temp store of total buys, by z togo */ 
      for(p=0;p<nPx1;p++) {
	N0sell[0][0][p][0][0] = 0.0;  /* temp store of total asks, by price  */
	N0sell[0][0][p][1][0] = 0.0;  /* temp store of total buys, by price  */
	for(k=0;k<=zmax;k++) {
	  N0sell[0][0][p][0][0] += (double)zpq[0][p][k];  /* total asks, by price  */
	  N0sell[0][0][p][1][0] += (double)zpq[1][p][k];  /* total buys, by price  */
	  N0sell[0][0][k][2][0] += (double)zpq[0][p][k];  /* total asks, by z togo */
	  N0sell[0][0][k][3][0] += (double)zpq[1][p][k];  /* total buys, by z togo */  /* differs from #tz since post-tremble */
	} }
      for(p=0;p<nPx1;p++) if( N0sell[0][0][p][0][0] ) { printf("\n         #asks     (p,z): %2d :", p); for(k=0;k<=zmax;k++) printf(" %7u", zpq[0][p][k] );  printf(" %8.0f",N0sell[0][0][p][0][0]); } printf("\n         #asks     (.,z):    :"); for(k=0;k<=zmax;k++) printf("%8.0f",N0sell[0][0][k][2][0]); printf("\n");
      for(p=0;p<nPx1;p++) if( N0sell[0][0][p][0][0] ) { printf("\n #asks / totalasks (.,z): %2d :", p); for(k=0;k<=zmax;k++) printf(" %7.3f", zpq[0][p][k] / N0sell[0][0][k][2][0] );                } printf("  rows sum to 1\n");

      for(p=0;p<nPx1;p++) if( N0sell[0][0][p][1][0] ) { printf("\n         #buys     (p,z): %2d :", p); for(k=0;k<=zmax;k++) printf(" %7u", zpq[1][p][k] );  printf(" %8.0f",N0sell[0][0][p][1][0]); } printf("\n         #buys     (.,z):    :"); for(k=0;k<=zmax;k++) printf("%8.0f",N0sell[0][0][k][3][0]); printf("\n");

      printf("\n         ave price (.,z):    :        ");  v1 = 0.0;  u= 0.0;  /* next 3 for() print ave price,its stderr, and std price for each tz->q value */
      for(k=1;k<=zmax;k++) {
	v = 0.0; 
	for(p=0;p<nP;p++)  v+= (double)p*zpq[1][p][k];
	printf(" %7.3f", v/N0sell[0][0][k][3][0]);
	v1 += v;      u += N0sell[0][0][k][3][0]; }     printf("      ave = %7.3f",v1/u);
      printf("\n  stderr ave price (.,z):    :        ");  v1 = 0.0;  u= 0.0;  v_= 0.0;
      for(k=1;k<=zmax;k++) {
	v = 0.0;  v2 = 0.0;
	for(p=0;p<nP;p++) { v+= (double)p*zpq[1][p][k];  v2+= (double)p*p*zpq[1][p][k]; }
	v0 = N0sell[0][0][k][3][0];  printf(" %7.3f", sqrt( ( v2/v0 - v/v0 * v/v0 )/ v0 ));
	v1 += v;  u += v0;  v_ += v2; }    printf("   stderr = %7.3f",sqrt( (v_/u- v1/u * v1/u) /u ));
      printf("\n         std price (.,z):    :        ");  v1 = 0.0;  u= 0.0;  v_= 0.0;
      for(k=1;k<=zmax;k++) {
	v = 0.0;  v2 = 0.0;
	for(p=0;p<nP;p++) { v+= (double)p*zpq[1][p][k];  v2+= (double)p*p*zpq[1][p][k]; }
	v0 = N0sell[0][0][k][3][0];  printf(" %7.3f", sqrt( ( v2/v0 - v/v0 * v/v0 )     ));
	v1 += v;  u += v0;  v_ += v2; }    printf("      std = %7.3f\n",sqrt( (v_/u- v1/u * v1/u)    ));
      
      printf("\n    ave RT between (.,z):    :        ");  for(k=1;k<=zmax;k++) { if(k==zmax) v0=0.0;  else v0=zRT[0][k+1] / N0sell[0][0][k+1][3][0];  printf(" %7.3f", zRT[0][k] / N0sell[0][0][k][3][0] -v0); }
      printf("\n        ave since0 (.,z):    :        ");  for(k=1;k<=zmax;k++) {                                printf(" %7.3f", zRT[0][k] / N0sell[0][0][k][3][0]); }
      printf("\n stderr ave since0 (.,z):    :        ");  for(k=1;k<=zmax;k++) { v0 = N0sell[0][0][k][3][0]; printf(" %7.3f", sqrt( (zRT[1][k] / v0  - zRT[0][k] / v0 * zRT[0][k] / v0 ) /v0)); }
      printf("\n        std since0 (.,z):    :        ");  for(k=1;k<=zmax;k++) { v0 = N0sell[0][0][k][3][0]; printf(" %7.3f", sqrt( (zRT[1][k] / v0  - zRT[0][k] / v0 * zRT[0][k] / v0 )    )); }
      printf("   UPDATE_s0 = %d\n", UPDATE_s0);  /* matlab: plot(1:zmax,p-15,'bo-', 1:zmax,rt,'k*-');  legend('ave price-15','RT between orders',0);  xlabel('shares still available to trade');  title('z8b1  6/4/04'); */

      for(p=0;p<nPx1;p++) if( N0sell[0][0][p][0][0] ) { printf("\n #buys / #asks     (p,z): %2d :", p); for(k=0;k<=zmax;k++) printf(" %7.3f", (double)zpq[1][p][k] / (double)zpq[0][p][k]     ); }  printf("\n");
      for(p=0;p<nPx1;p++) if( N0sell[0][0][p][1][0] ) { printf("\n #buys / totalbuys (p,.): %2d :", p); for(k=0;k<=zmax;k++) printf(" %7.3f", (double)zpq[1][p][k] / N0sell[0][0][p][1][0] ); }  printf("  cols sum to 1\n");
      for(p=0;p<nPx1;p++) if( N0sell[0][0][p][1][0] ) { printf("\n #buys / totalbuys (.,z): %2d :", p); for(k=0;k<=zmax;k++) printf(" %7.3f", (double)zpq[1][p][k] / N0sell[0][0][k][3][0] ); }  printf("  rows sum to 1\n");
    }
#endif    
    u = 0.0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++) u+= (double)byT[l][p][0];}     /* u = total #shares simulated */
    v = 0.0; for(l=0;l<nT;l++){for(p=0;p<nPV;p++) v+= (double)byT[l][p][5];}     /* v = total #limits submitted */
    h = 0;  v1= 0.0;  v2= 0.0;
    for(p=0;p<nP;p++) {
      h += Tcount[p][0] + Tcount[p][1];   /* h = total trades NOT involving FRINGE. Trades WITH Fringe in Tcount[nP:nP+3] = sum(sum(byT[][][4])) */
      v1+= Bcount[p];   v2+= Acount[p];   /* v1,v2 = total limit buys,sells  (bids,asks) */
    }
    printf("\n\n  Next Table is Post-Tremble");
    printf("\n    Bt           /#trade      /#trade     /#trade    /#limits    /#limits     /nJ         /nJ        /nJ         /nJ ");
    printf("\n  book  tick    Pr(trade)   Pr(mkt s)   Pr(mkt b)     Pr(bid)     Pr(ask)   Pr(HiBid)   Pr(LoAsk)  Pr(Spread)  Pr(SpreaF)  jj= %u  clock=%7.2f\n", jj, ((float)(clock()-clock1))/CLOCKS_PER_SEC);
    printf("       %4d                                                                                                     %9.6f  realtime= %4.1f  per decision= %5.3f\n", nPx1, (double) SpreaF[nPx1]/nJ,realtime,realtime/nJ);
    printf("       %4d    %6u                               %6u po  %6u mkt                                       %9.6f\n", nP, Tcount[nPx1][1]+Tcount[nP][1], Tcount[nP][1], Tcount[nPx1][1], (double) SpreaF[nP]/nJ);
    for(p=nP_1; p>=0; p--) {
      if (p == nP_1/2) printf("\n");
      printf(" %4d  %4d     %9.6f   %9.6f   %9.6f   %9.6f   %9.6f   %9.6f   %9.6f   %9.6f   %9.6f \n", Bt[p], p, ((double)Tcount[p][0]+(double)Tcount[p][1])/h, (double)Tcount[p][0]/h, (double)Tcount[p][1]/h, (double)Bcount[p]/v, (double)Acount[p]/v, (double)Bid[p]/nJ, (double)Ask[p]/nJ, (double)Spread[p]/nJ, (double)SpreaF[p]/nJ );
      if (p == nP_1/2) printf("\n");
    }
    printf("         -1    %6u                               %6u po  %6u mkt\n", Tcount[nPx1][0]+Tcount[nP][0], Tcount[nP][0], Tcount[nPx1][0]);
    printf("\n");
    printf("Pr(limit:   buy ~ sell)~ #       = %9.6f %9.6f %9.0f %9.0f\n", v1/nJ, v2/nJ, v1,v2 );
    printf("Pr(market:  buy ~ sell ~ either) = %9.6f %9.6f %9.6f \n", (double) Bcount[nP]/u, (double) Acount[nP]/u, (double)h/u );
    printf("Pr(No:Bid ~ Ask ~ Either ~ Both) = %9.6f %9.6f %9.6f %9.6f\n", (double) Bid[nP]/nJ, (double) Ask[nP]/nJ, (double) Spread[nP]/nJ, (double) SpreaF[nPx1]/nJ);
    printf("Pr(NoAction ~ NonOrder |New) ~ # = %9.6f %9.6f %9u %9u\n", Bcount[nPx1]/u, Acount[nPx1]/u, Bcount[nPx1],Acount[nPx1] );  /* 1-share ONLY */
    k=0;  for(p=0;p<=TAU;p++)  k += (short int) fabs((double)Ttau[p]);   /* k = #transactions last TAU periods, not yet having effective spread */
    printf("Trade wgtd: effective ~ realized = %9.6f %9.6f\n", (double) Spread[nP+2]/h, (double) RealSprd/(h-k));
    printf("     depth_max ~ q_max ~ # q>=nQ = %9u %9d %9u\n", N_s[3], qmax,  N_s[5] );
    printf("           max ~ ave:   sum(|B|) = %9u %9.4f  non:%4u %9.4f \n",   N_s[4], qave, N_s[6], non_ave );
    printf(" list lengths: Jumps ~ MktOrders = %9u %9u\n",    N_s[10], N_s[11] );
    printf("     last  observed v: min ~ max = %9ld %9ld                     #times NO transactions in most recent history = %u \n", vminmax[0][0], vminmax[1][0], Ntremble[22] );
    for(p=0;p<nRT;p++)   printf(" jumps in RTbin %d <~>%2d= H_jumps = %9ld %9ld %9.6f <-- sum/nJ\n", p, H_jumps, vminmax[2][p], vminmax[3][p],(vminmax[2][p]+vminmax[3][p])/nJ);
    printf("  informed last p <~> H_min~maxp = %9.6f %9.6f                     max #decisions = %u\n", vminmax[4][0]/nJ, vminmax[5][0]/nJ, Ntremble[21]);
    printf("uninformed last p <~> H_min~maxp = %9.6f %9.6f                     frequency more than 1 limit optimal= %8.1e\n", vminmax[6][0]/nJ, vminmax[7][0]/nJ, Ntremble[20]/nJ);
    printf("  NEW s->E(v) share   N<10 ~ N=1 = %9.6f %9.6f %9u           #orders updated= %u\n", (double)sNew[0]/(sNew[2]*nJ), (double)sNew[1]/(sNew[2]*nJ), sNew[2], N_s[24]);
    printf("  NEW s cnsd.ret N=1 Update~ not = %9.0u %9.0u %9.6f %9.0u %9.0u %9.6f \n", sNew[3],sNew[4],(double)sNew[3]/sNew[4], sNew[5],sNew[ 6],(double)sNew[5]/sNew[ 6]);
    printf("  NEW s subm.lim N=1 Update~ not = %9.0u %9.0u %9.6f %9.0u %9.0u %9.6f \n", sNew[7],sNew[8],(double)sNew[7]/sNew[8], sNew[9],sNew[10],(double)sNew[9]/sNew[10]);
    printf("  NEW s: w0sell ~ eUB ~ sx ~ non = %9u %9u %9u %9u  Total= %9u  New E(v) = %u\n", N_s[12],N_s[13],N_s[14], N_s[20], N_s[0], N_s[18]);
    printf("CHECK s: w0sell ~ eUB ~ sx ~ non = %9u %9u %9u %9u  Total= %9u  w0sell[bid=0=ask] check||new= %u \n", N_s[15],N_s[16],N_s[17], N_s[21], N_s[1], N_s[19]);
    printf("CHECK s: prev line / nJ          = %9.6f %9.6f %9.6f %9.6f  Total= %9.6f  #OLD s /nJ = %5.2f \n", 
	   (double)N_s[15]/nJ,(double)N_s[16]/nJ,(double)N_s[17]/nJ,(double)N_s[21]/nJ, (double)N_s[1]/nJ, (double)N_s[23]/nJ);
    printf("N drops when PV jumps up ~ down  = %9u %9u %9.6f <-- #Drop/#limits \n", Jcount[0],Jcount[1], ((double)Jcount[0]+Jcount[1])/v );
    printf("N exec. when PV jumps up ~ down  = %9u %9u %9u <-- #limit tp off-grid\n", Tcount[nP][0],Tcount[nP][1], N_s[7] );
    printf("N PV jumps up ~ down, /realtime  = %9u %9u %9.6f \n", Jcount[2],Jcount[3],(double)(Jcount[2]+Jcount[3])/realtime);
    printf("N adjust s->w for:  Non~Retained = %9u %9u %9u <-- #cancel/resubmit at same price\n",N_s[8],N_s[9], Ntremble[10]);   /* next line v= #limits */
    printf("N New Trembles: buy ~ sell ~ non = %9u %9u %9u     #tremble_limits / #limits = %9.6f\n",Ntremble[0], Ntremble[1], Ntremble[2], ((double)Ntremble[0]+Ntremble[1])/v);
    printf("N Ret.Trembles: buy ~ sell       = %9u %9u %9u <-- #no valid trembles    Trembles to NEW s= %u \n", Ntremble[4], Ntremble[3], Ntremble[15], Ntremble[19] );
    /* printf("N Ret with    : buy ~ sell ~ non = %9u %9u %9u \n", Ntremble[18], Ntremble[17], Ntremble[16] ); */
    printf("N New     1st-2nd v < .01 ~ 1e-4 = %9u %9u %9.0f <-- #Total New Arrivals\n", Ntremble[12], Ntremble[14], u);    /* u = #shares simulated */
    printf("N Ret.    1st-2nd v < .01 ~ 1e-4 = %9u %9u %9.0f <-- #Total Returning tj\n", Ntremble[11], Ntremble[13], nJ-u); /*   = #arrivals since 1-share only */
    printf("N New Rand <.99 ~ chose 2nd best = %9u %9u %9.1e <-- SCALE_v_v, UPDATE_1st = %d\n", Ntremble[7], Ntremble[9], SCALE_v_v, UPDATE_1st);
    printf("N Ret.Rand <.99 ~ chose 2nd best = %9u %9u %9u <-- Trembles from E(CS|execute)<0 to NonOrder w/ Prob ForceNon= %4.2f, losing ave~max of %6.4f %5.3f\n", Ntremble[6], Ntremble[8], Ntremble[5], ForceNon, wCancel[6]/Ntremble[5], wCancel[7]);
    printf("N wCancel ~ w ~ actual v1 ~ v1>w = %9.0f %9.6f %9.6f %5.3f    mean~max |w-v1| = %9.6f %9.6f \n", wCancel[4], wCancel[0]/wCancel[4], wCancel[1]/wCancel[4], wCancel[5]/wCancel[4], wCancel[2]/wCancel[4], wCancel[3]); 
    printf("N AB%7u AB|AB %7u %7.4f  AS|AB %7u %7.4f\n", ABAS[0][1],ABAS[0][2],(double)ABAS[0][2]/ABAS[0][1],ABAS[0][3],(double)ABAS[0][3]/ABAS[0][1]);
    printf("N AS%7u AB|AS %7u %7.4f  AS|AS %7u %7.4f\n", ABAS[1][1],ABAS[1][2],(double)ABAS[1][2]/ABAS[1][1],ABAS[1][3],(double)ABAS[1][3]/ABAS[1][1]);
#ifdef Type2_No_limitorders
    printf("ave Speculator NonOrder s->w  w/ ~ w/out limitorder option=   %7.4f  %7.4f  N= %7.0f\n", wNon[0]/wNon[2], wNon[1]/wNon[2], wNon[2]);
    wNon[0] = 0.0;  wNon[1]= 0.0; wNon[2]= 0.0;  /* to compare s->w for NonOrders of Speculators with and without limitorder option */
#endif
    fflush(stdout);       N_s[24] = 0; /* reset #updated orders */

    printf("\n   traders[]:(New Arrival in tt->again=%7.3f ) \n", tt->again);
    for(p=0;p<nPx1;p++) {
      t= traders[p]; k=0;
      while(t!=NULL) {
	k++; printf("p= %2d %2d  Bt= %3d  k=%3d  q= %2d  again=%7.3f  since=%7.3f  since0=%7.3f  rtime0= %9.1f  s= %p  t= %p  next= %p \n",
		    p, t->p, Bt[p], k, t->q, t->again, t->since, t->since0, t->rtime0, t->s, t, t->next);
	t->rtime0 = -t->since0; /* THIS IS IMPORTANT since reset realtime = 0.0 at top of j loop */
	t= t->next;
      } }    if (tz->q)   tz->rtime0 = -tz->since0;
    J = J1;  while(J!=NULL) { J->RT -= realtime;  J = J->next; }  /* IMPORTANT since reset realtime = 0.0 at top of j loop */
    M = M1;  while(M!=NULL) { M->RT -= realtime;  M = M->next; }

    InsertNewState = 'n' ;      /* GLOBAL.  do not insert new states encountered */

    if (SYMM==0) printf("\n Started sequential (non-threaded) calls to WriteFiles()...\n"); 
    
    clock1= clock();  i=0;  WriteFiles(i);    /* i=0 seller thread.  k=1 buyer thread. */
    if (SYMM==0)    { i=1;  WriteFiles(i); }  /* Last version to retain pthreads was GetEq_NoFringe_code.c */

#if 0                           /* MODIFY if reinstate purges */
    if ( N_s[2] < N_s[1] ) {    /* purged some states.              See end of WriteFiles() */
      for (p=0; p<nPx1; p++) {  /* May have deleted states at which current trader visiting.*/
	t = traders[p];  k=0;   /* Need empty book since t->s or t->s0 may have been purged.*/
	while (t!=NULL) { 
	  tx = t;   t = t->next;
	  k++; printf("p= %2d %2d  k=%3d  q= %2d  next= %p \n",p,t->p,k,t->q, t->next);
	  tx->next= traders[nPx1];  traders[nPx1]=tx;  tx=NULL;  /* tx to head trader memory heap */
	} traders[p] = NULL;      Bt[p]= 0;  Bt[nP]= nP; Bt[nPx1]= -1;
      } }
#endif

    outCS=NULL;
#if UPDATE_s0  /* if UPDATE_s0 == 0 (as when simulating converged model for outM) then discounting of CS is back to initial entry. */
    outCS = fopen(TMP "GetEq.CS",  "wb");    printf("\n");
    for(m=0;m<nPV;m++) 
      for(l=0;l<nT;l++) {      /* ignore seeming asymmetry in pre-updated w0sell[nonorders].  SYMM=1 --> only update pvtype's 0,1,2 (if nPV=5), and updates tend to lower s->w */
	if (byT[l][m][13]) { 
	  printf(" Updating  CS_non[pvtype=%2d][type=%2d] = %8.4f to %8.4f  ( CS per NoTrem (%8.4f)  * exp( rhoT[type]*Eagain_non) )\n",
		 m, l,  CS_non[m][l],  exp( rhoT[l]*Eagain_non ) * CS[l][m][7]/byT[l][m][13], CS[l][m][7]/byT[l][m][13] );
	  CS_non[m][l] = exp( rhoT[l]*Eagain_non ) * CS[l][m][7]/byT[l][m][13];
	}
	fflush(stdout);  fwrite( (char *) &CS_non[m][l], 1, 8, outCS ); /* so can read in instead of having to always update manually */
      }
	
    
#endif
    
    printf("\n Updating  Pe[bid_aggress][type]  and  p_v[bid_aggress][type]  which are used for intial beliefs (initially hardcoded in setup file)\n");
    for(m=0;m<nPlim;m++) {   /* m is bid aggressiveness.       Want Pe & p_v to average over PV to minimize the endogeneity effect */
      i = 21+m;                 printf("\n bid aggress= %2d (%3d rel.to v) : ", m, m - (nPlim-1)/2 );
      for(l=0;l<nT;l++)  {   /* l is type                      ie, beliefs that average across all states, not just states in which given aggressiveness taken */
	h = 0; for(p=0;p<nPV;p++) h+= byT[l][p][i];               /* #orders submitted w/ aggressiveness of m */
	if(h > 100) {                                             /* arbitrary minimum #orders for an update  */
	  u = 0.0;  v= 0.0;  
	  for(p=0;p<nPV;p++) { u+= byT[l][p][i+ nPlim];  v+= byT[l][p][i+ 2*nPlim]; }  /* u= #executions      */  
	  Pe[m][l] = u/h;   if(u>10) p_v[m][l] = v/u -PVmu;                            /* v= sum( Price - v ) */
	}                                                                              /* init s->w are for BUYS in next printf()  */
	printf(" type %2d:  #=%8u  Pe= %6.4f  p_v=%8.4f  w:8~4~0:%6.2f %6.2f %6.2f   ",
	       l, h, Pe[m][l], p_v[m][l], Pe[m][l]*(PV[0]-p_v[m][l]) + (1.0-Pe[m][l])*CS_non[0][l], Pe[m][l]*(PV[1]-p_v[m][l]) + (1.0-Pe[m][l])*CS_non[1][l], Pe[m][l]*(PV[2]-p_v[m][l]) + (1.0-Pe[m][l])*CS_non[2][l] );
	if(outCS) {
	  fwrite( (char *)  &Pe[m][l], 1, 8, outCS ); /* so can read in instead of having to always update manually */
	  fwrite( (char *) &p_v[m][l], 1, 8, outCS );
	}
      } }

    if (outCS !=NULL) fclose(outCS);      /* reset these files every jj */
    
    /* Pakes-McGuire convergence criteria : weighted corr > 0.995, weighted means within .01 */
    /* see end of  GetEq_NoFringe_code.c  for 60 or so lines that computed correlations */
    /* see WriteFiles() for writing of GetEq_Ee  GetEq_RC  GetEq_OC  that were read to compute these correlations */
    
    printf("\n\n jj= %u DONE  clock(to write)= %5.2f   Restarting another nJ= %7.1e simulations ...\n\n", jj-1, ((float)(clock()-clock1))/CLOCKS_PER_SEC, nJ);   fflush(stdout);

#if usePN>0
    if(SS!=NULL) {
      printf(" UPDATE_s0= %d.  if 1, then upon return payoff is regardless of action, if 0 then only MKT payoff.   * is the problem, # match well, x ultimately must match\n\n", UPDATE_s0);
      p=1; printf("SSEw for  1st  action : Ew~NN:  exec.limit = %8.5f#%7.0f       Upon Return (tj): %8.5f %7.0f   Either: %8.5f %7.0f   Upon Return: Mkt: %8.5f#%7.0f  Lim: %8.5f %7.0f *\n",
		  SSEw[0][p]/SSEw[1][p], SSEw[1][p], SSEw[2][p]/SSEw[3][p], SSEw[3][p], (SSEw[0][p] + SSEw[2][p]) / (SSEw[1][p]+SSEw[3][p]), SSEw[1][p]+SSEw[3][p], SSEw[4][p]/SSEw[5][p], SSEw[5][p], SSEw[6][p]/SSEw[7][p], SSEw[7][p] );
      p=0; printf("SSEw for later action : Ew~NN:  exec.limit = %8.5f %7.0f       Upon Return (tj): %8.5f %7.0f   Either: %8.5f %7.0f   Upon Return: Mkt: %8.5f %7.0f  Lim: %8.5f %7.0f\n",
		  SSEw[0][p]/SSEw[1][p], SSEw[1][p], SSEw[2][p]/SSEw[3][p], SSEw[3][p], (SSEw[0][p] + SSEw[2][p]) / (SSEw[1][p]+SSEw[3][p]), SSEw[1][p]+SSEw[3][p], SSEw[4][p]/SSEw[5][p], SSEw[5][p], SSEw[6][p]/SSEw[7][p], SSEw[7][p] );
      p=0; printf("SSEw for  ALL  action : Ew~NN:  exec.limit = %8.5f %7.0f       Upon Return (tj): %8.5f %7.0f   Either: %8.5fx%7.0f   Upon Return: Mkt: %8.5f %7.0f  Lim: %8.5f %7.0f \n\n",
		  (SSEw[0][p]+SSEw[0][1]) / (SSEw[1][p]+SSEw[1][1]), SSEw[1][p]+SSEw[1][1],
		  (SSEw[2][p]+SSEw[2][1]) / (SSEw[3][p]+SSEw[3][1]), SSEw[3][p]+SSEw[3][1],
		  (SSEw[0][p]+SSEw[0][1] + SSEw[2][p]+SSEw[2][1]) / (SSEw[1][p]+SSEw[1][1] + SSEw[3][p]+SSEw[3][1]), SSEw[1][p]+SSEw[1][1] + SSEw[3][p]+SSEw[3][1],
		  (SSEw[4][p]+SSEw[4][1]) / (SSEw[5][p]+SSEw[5][1]), SSEw[5][p]+SSEw[5][1],
		  (SSEw[6][p]+SSEw[6][1]) / (SSEw[7][p]+SSEw[7][1]), SSEw[7][p]+SSEw[7][1] );
      
      p=1; printf("SiEw for  1st  action : Ew~NN:  exec.limit = %8.5f#%7.0f   Mkt Upon Return (tj): %8.5f#%7.0f   Either: %8.5f %7.0f\n",
		  SiEw[0][p]/SiEw[1][p], SiEw[1][p], SiEw[2][p]/SiEw[3][p], SiEw[3][p], (SiEw[0][p] + SiEw[2][p]) / (SiEw[1][p]+SiEw[3][p]), SiEw[1][p]+SiEw[3][p] );
      p=0; printf("SiEw for later action : Ew~NN:  exec.limit = %8.5f %7.0f   Mkt Upon Return (tj): %8.5f %7.0f   Either: %8.5f %7.0f *\n",
		  SiEw[0][p]/SiEw[1][p], SiEw[1][p], SiEw[2][p]/SiEw[3][p], SiEw[3][p], (SiEw[0][p] + SiEw[2][p]) / (SiEw[1][p]+SiEw[3][p]), SiEw[1][p]+SiEw[3][p] );
      p=0; printf("SiEw for  ALL  action : Ew~NN:  exec.limit = %8.5f %7.0f   Mkt Upon Return (tj): %8.5f %7.0f   Either: %8.5fx%7.0f\n\n",
		  (SiEw[0][p]+SiEw[0][1]) / (SiEw[1][p]+SiEw[1][1]), SiEw[1][p]+SiEw[1][1],
		  (SiEw[2][p]+SiEw[2][1]) / (SiEw[3][p]+SiEw[3][1]), SiEw[3][p]+SiEw[3][1],
		  (SiEw[0][p]+SiEw[0][1] + SiEw[2][p]+SiEw[2][1]) / (SiEw[1][p]+SiEw[1][1] + SiEw[3][p]+SiEw[3][1]), SiEw[1][p]+SiEw[1][1] + SiEw[3][p]+SiEw[3][1] );
      /* return(0); */   /* debugging by printing stuff about 1 hardcoded state */
      p=0;  SSEw[0][p] = 0.0;  SSEw[1][p] = 0.0;  SSEw[2][p] = 0.0;  SSEw[3][p] = 0.0;  SSEw[4][p] = 0.0;  SSEw[5][p] = 0.0;  SSEw[6][p] = 0.0;  SSEw[7][p] = 0.0;
      p=1;  SSEw[0][p] = 0.0;  SSEw[1][p] = 0.0;  SSEw[2][p] = 0.0;  SSEw[3][p] = 0.0;  SSEw[4][p] = 0.0;  SSEw[5][p] = 0.0;  SSEw[6][p] = 0.0;  SSEw[7][p] = 0.0;
      p=0;  SiEw[0][p] = 0.0;  SiEw[1][p] = 0.0;  SiEw[2][p] = 0.0;  SiEw[3][p] = 0.0;
      p=1;  SiEw[0][p] = 0.0;  SiEw[1][p] = 0.0;  SiEw[2][p] = 0.0;  SiEw[3][p] = 0.0;
    }
#endif

    if (mJ>0 || WRITE==0) { printf("\n\n Exiting since finished writing  mJ= %d  decisions to outM\n\n",mJ);  exit(1); }  /* use this if only want 1st set of mJ sims */

  }    /* while (1) {} which is the old jj loop */
  if (outNew!=NULL) fclose(outNew);

  return(0);
}
