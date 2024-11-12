/* Initial version written by Ron Goettler August, 2002 implementing model suggested by Uday Rajan and Christine Parlour. */
/* THIS FILE loads the real stuff from  GetEq_large_code.c     */
/* THIS FILE only sets the parameters via  #define  statements */

#define nJ         3e8     /* number iterations j loop, or MC draws */
#define mJ           0     /* number periods to write to outM       */
#define nQ           4     /* number of misc. state variables (ie, new, retained order, cancelled order, E(v) */  /* power of 2 for efficient (bit shift) indexing of pHT[][][][] */
#define nP          63     /* number prices tracked by the book, centered around current v */
#define nPmax       64     /* lowest power of 2 yielding nPmax <= nP+1 (+1 since non-orders at [nP]) */
#define nP_1        62     /* nP-1 */
#define nPx1        64     /* nP+1 */
#define LL          25     /* Lowest  allowed limitorder price.  LL + HL = nP-1 for allowed orders centered around E(v) */
#define HL          37     /* Highest allowed limitorder price. */
#define nPlim       13     /* number of ticks at which traders permitted to submit limitorders:  HL-LL+1 */
#define nPtj         0     /* additional ticks (on each side) at which returning traders permitted to RETAIN limitorders: */
#define nT           2     /* number of types of traders. Exact (not next power of 2) */
#define nT_1         1     /* nT-1 */
#define nPV          5     /* number of Private Values */
#define nPV_1        4     /* nPV-1 used often in GetState() if SYMM */
#define nPVmax       8     /* lowest power of 2 yielding   nPVmax >= nPv. Since declare based on nPVmax for efficient indexing */
#define nTmax        2     /* lowest power of 2 yielding    nTmax >=  nT. Since declare based on  nTmax for efficient indexing */
#define nB           6     /* size of s->B. bid,ask,  depth at bid,ask,  depth off bid,ask,  2nd most agg. bid/ask */
#define nL           0     /* #elements of lagged order to condition on for returning trader's replacement order.  if 3: price, bid, ask */
double      PV[nPV] = { -8, -4, 0.0, 4, 8 };    /* Private Values (tick deviations around concensus value) */
double   cdfPV[nPV] = { .15, .35, .65, .85, 1.0 };  /* CDF of PV, values beyond first 1.0 are ignored */
double cdfPVT[nPVmax][nT] = { /* CDF of Types, by PV, values beyond first 1.0 (of each row) are ignored */
  {.98, 1.0 },
  {.98, 1.0 },
  {.98, 1.0 },
  {.98, 1.0 },
  {.98, 1.0 } };
double      rhoT[nT] = {-.05, -.05 }; /* -rho of each trader type. using -rho, since always negated : discount factor = exp(-rho*t) */
double        cT[nT] = {.0,.0 };   /* linear monitoring costs = cT[]* elapsed time */
short int  vlagT[nT] = { 0, 1 };   /* 0 or 1 for each trader type. if 1, then trader has vlag = nLag */
short int    nBT[nT] = { 6, 6 };   /* 2,4,6,8 for amount of book info;  2= quotes only, 4=quote depth, 6= off-quote depth, 8= 2nd most agg. bid/ask */
short int    nHT[nT] = { 2, 2 };  /* # of TransHist things to condition on to predict v (& LARGE tz).     valid values: 0, 1, 2, 3 */
short int     zT[nT] = { 0, 0 };  /* # shares. >0 --> LARGE trader.     nHT>1 for INFORMED should be SAME as UNINFORMED.  Also see H_jumps */
#define UPDATE_Ne    0     /* only use initially.  if >0 adjust returning traders order's  s->w *= (s->Ne+UPDATE_Ne) / (s->NN+UPDATE_Ne) */
#define UPDATE_s0    1     /* if 1 change t->s0 & s0->Ew+=,NN++ upon revisiting.  if 0, t->s0 is always initial state when FIRST arrived.  */
#define UPDATE_maxPVT .05  /* update s->w if Pr(type) <= UPDATE_maxPVT.  0.0 --> NO update.  1.0 --> update ALL.  0.1 --> update DEVIATORS */
#define UPDATE_sync  0     /* if 1 & UPDATE_e =0, SYNCHRONOUS update s->w in WriteFiles.  Weight via N, unless Ee_replace=1 */
#define Ns0          0     /* if >0, then update 1st Ns0 states visitied when UPDATE_s0 = 0 and UPDATE_maxPVT = 0 (ie, conv.check) */
#define zmax         0     /* max zT */
#define nH           2     /* # of Hist things to POSSIBLY condition on in GetState, 1= Jumps, 2= also trans.prices, 3= also NetCumBuys */
#define nRT          1     /* #bins in discretization of RT history.   If NO LARGE traders, nRT = 1 sufficient. */
#define nHnRT        2     /* nH*nRT */
#define H_signed     1     /* if 1 transaction prices are signed (<0 --> mkt sell).  else prices unsigned */
#define H_jumps     16     /* max net jumps within each RT bin that INFORMED traders condition on (>0 if other traders UNINFORMED) */
#define H_minp       0     /* min last trans.price for conditioning on via s->H by Informed & Uninformed */
#define H_maxp      62     /* max last trans.price for conditioning on via s->H by Informed & Uninformed */
double  RT[nRT] = { 16.0 };   /* endpoints of RT discretization bins.  bin 1 is (0,RT[0]], 2 is (RT[0],RT[1]] */
#define nLag        16     /* nLag = max lag = is RT[nRT-1] */
#define ExogBuy      0     /* if 0 trader CHOOSES to sell or to buy */
#define PVmu      31.0     /* mean PV in ticks = (nP-1)/2, (double) */
#define Ejump      8.0     /* E(time between v jumps).  <0 NoJumps  */
#define Eagain     4.0     /* E(time until revisit order)           */  /* can normalize either: Eagain Earrive Ejump _rho */
#define Eagain_non 4.0     /* E(time until revisit non-order)       */  /* gives advantage of non-orders over unaggressive limits */
#define Earrive    1.0     /* E(time between new arrivals)          */  /* (which increase state-space, slow converg) */
#define ForceNon   0.5     /* Probability of tremble to Non-Order from Least Aggressive limits, by NEW tt only */
#define PrTremble  0.01    /* Pr(1-tick more agg) = Pr(less agg)    */
#define TrembleNew 0.2     /* max v - s->w to tremble to NEW state  */
#define SCALE_v_v  1e0     /* if >10 RANDOMIZE:  Pr(1st)=exp((v-v_)*SCALE)/(1+exp()) = .525, .731, .99995, for (v-v_)*SCALE = .1, 1, 10 */
#define UPDATE_1st   1     /* if 1 use 1st best v (despite randomization) to update (returning) tj->s->w, else use actual choice's v    */
#define PVjN         2     /* N ticks jump when jump occurs         */
#define Prime    98317     /* large prime for Hash Function 98317, 205759 */  /* too high is costly, since pHT[][][][] creates MANY hash tables */
#define V0         0.0     /* value of NOT placing a limit order    */
#define delta      1.0     /* scale factor *rhoT[] for initial s->w */
#define seed         1     /* seed for srand                        */
#define TAU         10     /* for realized spread TAU periods ahead */
#define Nreset      -1     /* 0: NO reset.  1: N= 1 each jj.  >2: N= min(N,(jj+1)*Nreset).  <0 uses _xNN,  <0 Nreset_READ=0: no reset after read  */
#define Nreset_xNN  99     /* if >0, s->N = min(s->N, s->NN*Nreset_xNN).     =100 --> next NN encounters get approximately 1/100 weight  */
#define Nreset_READ  0     /* if >0 reset s->N = min(Nreset_READ,s->N) */                  /* READ_w_adj = 1.22 if decrease |rho| by .05 */
#define READ_w_adj  0.0    /* 0, or if change |rho| use  exp( (|rho_old|-|rho_new|) * Eagain)   s->w *= READ_w_adj  for limit/non orders */
#define w0adjust    .1     /* amount by which w0sell[]+= w2*w0adjust in WriteFiles */
#define use_w0sell   0     /* if >0, then use w0sell[], else not.  if ==2 then use CS_non[] in next line */
unsigned int jj0  = 0;     /* EVEN NUMBER.  Higher jj0 for LESS EXTREME Nreset. jj0 changed in main() to ODD if READ=2 */
#define N0           1     /* >0 new s->N= N0, <0 N= jj-N0, ==0 N=1 */
#define N0_v         1     /* >0 new sv->N= N0_v. if 0, in GetState_v() test conditioning by conditioning on current v itself (ie, -t->v) */
#define SYMM         1     /* if 1 use symmetry to reduce state-space in half.        See GetState() */
#define MAXSTATES 17e7     /* maximum number of states before purge */
#define OftNN      100     /* min s->NN used in jMODE=1 ChiSq test stat AND min NN to be included in Oft Class of UPDATE_sync conv test*/
#define eUBsimilar   0     /* if >0 use similar state to init belief.  sx->w used only if sx->N > eUBsimilar */
#define eUBforce     0     /* if 1 e=min(s->e,eUB) when WRITE files */
/* #define LimCost      .01 */   /* ifdef, then cost of submitting limitorder -- paid upfront, so not part of s->w */
#define e1v_e0else   0     /* if 1 e=1 at v_t (i.e, the mid-tick) and e=0 elsewhere --> all transactions at v_t. Must get code from GetEq_large_code.c */
#define TMP     "proj/840v0_j2b6/"

#define CheckSEGV 1

/* #define Type2_No_limitorders 2 */
/* #define Print_Speculator_Unaggressive 1 */
/* #define EXOG_CANCEL  1 */     /* ifdef (and uncomment its use in code) then ALL returning traders are forced to CANCEL and REPLACE */

#define WRITE 1           /* if >0 write state to file if s->N >= WRITE (s->N is always at least 1) */
#define READ  2           /* if >0 read states & traders.  if ==2  use /tmp/blah.even  files, otherwise load from /tmp/blah.odd  */

#define PURGE_MODE  0     /* <0: via NC,N after READ  >0: ?? */
#define PURGE_NC    1     /* purge if s->NC <  PURGE_NC.  if 0 NC purges never occur.  min s->NC is 0, hence <  condition */
#define PURGE_N     0     /* purge if s->N  <= PURGE_N.   if 0  N purges never occur.  min s->N  is 1, hence <= condition */

/* These get updated within the code so SHOULD UPDATE  CS_non  Pe  p_v  based on last nJ reported before restarting (unless changes are minimal)  */
/* Pe[][] & p_v[][] defined using nPmax since power of 2, but only use first nPlim rows */

double CS_non[nPVmax][nT] = {  {-99.9, 6.0}, {3.3, 3.0}, {.46, 0.3}, {3.3,3.0}, {7.1,6.0} };   /* CS per No Trem.  for non-order bid=0=ask w0sell[].  -9999.9 to ignore */

double Pe[nPmax][nT]= {        /* Prob(execute) for nPlim valid limitorders centered around E(v) */
  { 0.0027, 0.0027 },   /* -6 */   /* Pe[] and p_v[] based on 1st column of "bid aggress" summary reported each nJ runs */
  { 0.0091, 0.0091 },   /* -5 */   /* These values are UPDATED after each nJ. */
  { 0.0155, 0.0155 },   /* -4 */
  { 0.0576, 0.0576 },   /* -3 */   /* initial beliefs for s->w based on these.   see code. */
  { 0.0625, 0.0625 },   /* -2 */
  { 0.4346, 0.4346 },   /* -1 */   /* If informed (so that tL = LL) the init value of a BUY is:     */
  { 0.5413, 0.5413 },   /*  0 */   /* s->w = Pe[p-LL] * ( PV - p_v[ p-LL ] ) + (1-Pe[p-LL])*CS_non[PV][type]  */
  { 0.7482, 0.7482 },   /*  1 */   
  { 0.6725, 0.6725 },   /*  2 */   /* <-- tick = E(v) which is 0 if informed */
  { 0.6750, 0.6750 },   /*  3 */
  { 0.7069, 0.7069 },   /*  4 */
  { 0.7801, 0.7801 },   /*  5 */   /*  Better to average over ALL trader types, so that endogeneity of order does not play as much of a role */   
  { 0.8816, 0.8816 } }; /*  6 */   /*  When using type differs across PV, using PV specific values will be unavoidable, since Pe,p_v should definitely be type specific */

double p_v[nPmax][nT]= {         /* E(transaction costs) for nPlim valid limitorders centered around E(v) */
  {  0.2303 , -0.0290 },   /* -6 LL=25 */
  { -0.3039 , -0.1976 },   /* -5     */
  {  0.2330 , -0.1381 },   /* -4     */
  { -0.3480 , -0.2823 },   /* -3     */
  {  0.3693 , -0.1052 },   /* -2     */
  { -0.5598 ,  0.1969 },   /* -1     */
  {  0.2768 ,  0.5187 },   /*  0 PVmu= 31  */
  {  1.0823 ,  1.2181 },   /*  1     */
  {  2.0981 ,  2.1935 },   /*  2     */
  {  3.0675 ,  3.1233 },   /*  3     */
  {  4.2195 ,  4.0286 },   /*  4     */
  {  4.9818 ,  3.6088 },   /*  5     */
  {  4.0594 ,  4.0594 } }; /*  6     */

#define CHECK_STATES 0

#include "GetEq_state-action_code.c"
