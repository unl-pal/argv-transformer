if 0;
  nS  = 22962648 ;    %% number of states written by C
  nP  = 7;         %% number of prices in book
  fid = fopen('GetEq.e.sell.even','r');  e = fread(fid, nS,   'double');  fclose(fid);
  fid = fopen('GetEq.N.sell.odd' ,'r');  N = fread(fid, nS*2, 'uint32');  fclose(fid);  N = reshape(N, 2, nS);   %% row 1 is N , row 2 is NN 
  fid = fopen('GetEq.B.sell.even','r');  B = fread(fid, nS*(5+nP), 'int16=>int8');  fclose(fid); 
  B = reshape(B, 5+nP, nS)';   %% each state has : trader's (PV index, p index, queue) followed by the book (orders at each price), low ask, high bid
end;

nRT = 1;  %% #bins into which history timeline discretized.  each bin has  5  columns

nP = 63 ;                %% number of ticks
nK = nP + 32 + nRT*5;    %% number of variables in GetEq_nJsim file
nBytes = 828804800 ;  %% sim  file size in bytes
nBytes = 800000000 ;  %% sim  file size in bytes
nShare = nBytes/(8*nK);  %% number of shares processed.  = nJ if no multishare traders.

%%%%%%%%   xfile   is the directory holding the files for the specific model run
% xfile = '840v0_j2b6';  %% set nP= 63
 xfile =   '0v0_j2b6';  %% set nP= 63
% xfile =   '0v0_j1b6';  %% set nP= 63
%xfile = '840v0_j1b6';  %% set nP= 63

%  xfile = 'MM_z8_840v0'; %% set nP= 63
% xfile = 'MM_z8_0v0'; %% set nP= 63

% xfile = 'z0b0v0_z10b0v0';  %% large

projx= 'proj2';  %%   'proj'  or  'proj2'  depending on where  xfile  resides

disp(' ');  unix(['ls -l ~/limitorder/' projx '/' xfile '/']);
x = ['~/limitorder/' projx '/' xfile '/mJsim' ];
disp(' ');  disp(['loading file:  '  x]);  disp(' ');  fid = fopen( x, 'r');

TQ = load(['~/limitorder/' projx '/' xfile '/mJ_TQ']);

x  = fread(fid, nShare*nK, 'double');  fclose(fid);
x  = reshape(x,nK,nShare)';

Bt =  x(:,21:20+nP);

xx = [x(:, 1:20) x(:,(21+nP):nK)];   %% strips Bt from xx

Ht = xx(:, 29:28 + 5*nRT);           %% 5*nRT History columns AVAILABLE for conditioning, though not nec. used by traders

xx = [xx(:, 1:28) xx(:, 28 + 5*nRT + (1:4)) ];   %% strips Ht from xx

clear x;

disp('  period #   period0    rtime0     rtime     since        PV    last v      type      book      vlag   z still       nHT       rho  cumjumps')
disp('     col 1     col 2     col 3     col 4     col 5     col 6     col 7     col 8     col 9    col 10    col 11    col 12    col 13    col 14')
disp('  ========  ========  ========  ========  ========  ========  ========  ========  ========  ========  ========  ========  ========  ========')
quantile(xx(:, 1:14));

disp('       ask       bid  askdepth  biddepth    # asks    # bids    w keep    p keep    q keep   j since   tremble   action1  w=payoff      E(v)');
disp('    col 15    col 16    col 17    col 18    col 19    col 20    col 21    col 22    col 23    col 24    col 25    col 26    col 27    col 28');
disp('  ========  ========  ========  ========  ========  ========  ========  ========  ========  ========  ========  ========  ========  ========');
quantile(xx(:,15:28));

disp('  -------  1st share processed  --------'); %%  -------  2nd share processed  --------');
disp('  ========  ========  ========  ========'); %%  ========  ========  ========  ========');
quantile(xx(:,29:32));


disp('  -- History Avail. for Conditioning ---  5 columns per RT bin ...');
disp('  RT endpt  netjumps   last p   net buys last sign  RT endpt  netjumps   last p   net buys last sign');
disp('  ========  ========  ========  ========  ========  ========  ========  ========  ========  ========');
quantile(Ht);

if nP<64;  %% else too big for quantile()
  disp('  Book at each tick (0 to nP-1) : ');  disp(' ');
  disp(sprintf( repmat('   tick %2d',1,nP), [0:nP-1]));
  disp(sprintf( repmat('   =======' ,1,nP) ));
  quantile( Bt );
end;

if 1;
  % disp('HEY:  should use regressions from  inspect_mkt.m  instead since restricts to NN>OftNN and counts each state only once.');
  Ev = xx(:,28);  Lv= xx(:,7)+(nP-1)/2;          %% xx(:,10)>0 if tt uninformed (tt->vlag>0)  
  i = find( xx(:,10)>0  & abs(Ht(:,3)-Lv)<10 );  %%   & abs(xx(:,6)-(nP-1)/2)==8,  to choose only certain |beta|
  i = find( xx(:,10)>0  & abs(Ht(:,3)-Lv)<10 & xx(:,15)<nP & xx(:,16)>-1 );  %% require defined quotes
  if 1 & length(i)>0;
    disp(' ');    disp(' This set of regressions uses  bid,ask,lastp  relative to LAST OBSERVED v !');    disp(' ');
    ols(Ev(i),[ones(length(i),1) xx(i,15)-Lv(i) xx(i,16)-Lv(i) xx(i,[ 19 20 ]) Ht(i,3)-Lv(i) Ht(i,4) Ht(i,5)], .05, strvcat('constant','ask','bid','total asks','total bids','lastP','netbuys','signed_order'), ['E(v), relative to last observed : ' xfile],1,0,0,0);
    ols(Ev(i),[ones(length(i),1) Ht(i,3)-Lv(i) Ht(i,5)], .05, strvcat('constant','lastP','signed_order'), 'E(v), relative to last observed',1,0,0,0);
    ols(Ev(i),[ones(length(i),1) xx(i,15)-Lv(i) xx(i,16)-Lv(i)], .05, strvcat('constant','ask','bid'), 'E(v), relative to last observed',1,0,0,0);
    ols(Ev(i),[ones(length(i),1)  Ht(i,3)-Lv(i) Ht(i,5) xx(i,15)-Lv(i) xx(i,16)-Lv(i)], .05, strvcat('constant','lastP','signed_order','ask','bid'), 'E(v), relative to last observed',1,0,0,0);
    ols(Ev(i),[ones(length(i),1) xx(i,15)-Lv(i) xx(i,16)-Lv(i) xx(i,[ 19 20 ]) ], .05, strvcat('constant','ask','bid','total asks','total bids'), 'E(v), relative to last observed',1,0,0,0);
  end;
  if 1 & length(i)>0;
    disp(' ');    disp(' This set of regressions uses  bid,ask,last_p,last_obs_v  relative to CURRENT v.  std(LHS) measures remaining uncertainty');    disp(' ');
    ols( xx(i,7)+xx(i,28), [ones(length(i),1) [Lv(i) (xx(i,15)+xx(i,16))./2 ] ]-(nP-1)/2, .05, strvcat('constant','last observed v ','midquote'), 'E(v) relative to current v',1,0,0,0);
    ols( xx(i,7)+xx(i,28), [ones(length(i),1) [Lv(i)  xx(i,15) xx(i,16)     ] ]-(nP-1)/2, .05, strvcat('constant','last observed v ','ask','bid'), 'E(v) relative to current v',1,0,0,0);
    ols( xx(i,7)+xx(i,28), [ones(length(i),1) [Lv(i) xx(i,15) xx(i,16) Ht(i,3)]-(nP-1)/2 ], .05, strvcat('constant','last observed v ','ask', 'bid', 'lastP'), 'E(v) relative to current v',1,0,0,0);
    ols( xx(i,7)+xx(i,28), [ones(length(i),1) [Lv(i) xx(i,15) xx(i,16) Ht(i,3)]-(nP-1)/2  Ht(i,5)  xx(i,[17 18 19 20 ]) ], .05, strvcat('constant','last observed v ','ask', 'bid', 'lastP','signed_order','ask depth','bid depth','total asks','total bids'), 'E(v) relative to current v',1,0,0,0);
  end;
end;
if 1;
  eval(['save  /home/urajan/limitorder/DATA/' xfile ' xx Bt Ht TQ;'])
  % eval(['save  /home/urajan/limitorder/DATA/' xfile '_book  Bt;'])
  disp(' '); disp(['saved   xx Bt Ht TQ   in file: /home/urajan/limitorder/DATA/: ' xfile '.mat' ]);  disp(' ');
end;
if 0;
disp('column in xx');
disp('------------');
disp('   1 = "period" counter: each period has ONE decision made by returning OR new trader');
disp('   2 = initial "period" of current trader making a decision');
disp('   3 = initial realtime of current trader');
disp('   4 = current realtime');
disp('   5 = realtime since last decision (0 if new arrival)');
disp('   6 = traders private value, centered around current v which is always at (#ticks-1)/2');
disp('   7 = last v observed, relative to current (<0 --> v jumped up)');
disp('   8 = trader type index (1,...,nTypes)');
disp('   9 = trader book access (0= full, 1= quotes w/depth, 2= quotes only)');
disp('  10 = trader vlag status (0 or nLag)');
disp('  11 = #remaining shares to trade (0 if small trader) ');
disp('  12 = #variables which trader conditions upon to infer current v (0= none, 1= jumps (informed only),  2= also trans.prices, 3= also NetCumBuys)');
disp('  13 = trader discount rate');
disp('  14 = cum jumps since period 0');
disp('  15 = ask  (infinite depth at nP), on current v grid');
disp('  16 = bid  (infinite depth at -1), on current v grid');
disp('  17 = ask depth ( <= 0 )');
disp('  18 = bid depth ( >= 0 )');
disp('  19 = total asks on book ( <= 0 )');
disp('  20 = total bids on book ( >= 0 )');
disp('  21 = E(payoff) of retaining order, -9 if new arrival, -99.9 if p out of range, or nonorder.  If LARGE then discounted CS thus far.');
disp('  22 = p of current order,           -9 if new arrival');  %% if submitted at p=PVmu and v up 4, then curr p is PVmu-4
disp('  23 = q of current order,           -9 if new arrival');  %% p when submitted is PVmu-4 + vjumps = PVmu
disp('  24 = j since last action,          -9 if new arrival.  Relative price when submitted = col22 + col24');
disp('  25 = 2 if trader JUST trembled, 1 if trader EVER trembled, 0 if NEVER trembled');
disp('  26 = action share 1 :  0 = No action,  <0 = sell at tick abs()-1  , >0 = buy at tick abs()-1, = nP+9 for Retain Current Order ');
disp('  27 = E(payoff) of action');
disp('  28 = E(v), relative to last observed.  E(v) on CURRENT v grid =  last obs.v + E(v, rel to last obs) = col 7 + col 28.  == 0 if fully revealing');
disp('');
disp('       processed share:  ( small traders only 1 SHARE.  large will report 1 share per row ) ');
disp('  29 = price');
disp('  30 = volume (-2:limit sell, -1:market sell, 1:market buy, 2:limit buy, -10-nP :no action, -20-nP :no share, 0 :Non-Order or inactive LARGE)');
disp('  31 = IF MARKET: period in which executed limit order trader first arrived');
disp('       IF LIMIT:  E( payoff )');
disp('  32 = IF MARKET: cum ACTUAL jumps for executed limit trader (can differ from OBSERVED jumps if vlag>0).');
disp('       IF LIMIT:  0.0 ');
disp('');
disp(' ');
disp('  Non-Orders (no action now, but maybe later) are coded as LIMIT ORDERS at price = nP (remember, ticks are enumerated 0,...,nP-1)');
disp('  Non-Orders have "volume" column (30) of 0, as opposed to -2,2 for limit sells,buys');

disp(' ');
disp(' History available to be used for conditioning.');
disp(['     Realtime cut into ' int2str(nRT) ' bins.  Each bin has following 4 variables reported:']);
disp(' col (per RT bin) in variable  HT ');
disp('----------------------------------');
disp('   1 = RealTime "bin" far end point (near end point is either 0.0 or previous bins far endpoint)');
disp('   2 = net jumps since  nLag  realtime units (i.e., jumps not observed by uninformed)');
disp('   3 = most recent transaction price (within this bin).  0 if no transaction (and 0 for signed order flow).');
disp('   4 = cum net buys (within this bin)');
disp('   5 = signed order flow of most recent transaction (within this bin).  0 if no transaction.');
disp(' ');

disp(' LARGE trader notes: ');
disp('   Col 11 > 0 signals action by LARGE trader.  Col 11 refers to #shares INCLUDING the share for that row (hence minimium is 1).');
disp('   The shares he starts with will be the max(Col 11).  All large start with same #shares to trade.');
disp(' ');
disp('   All book information (xx columns 15-20 & variable Bt) is PRIOR to execution of share for that row, but AFTER "earlier" shares at same instant.');
disp('   For example, if 2 shares execute at given instant, the 1st share book info is prior to the "combined order" being submitted,');
disp('   whereas the 2nd share book info is "intermediate" in that it reflects the state of the market AFTER the 1st share transacts.');
disp(' ');
disp('   Col 21 (retained order payoff for returning traders) reports, instead, discounted CS from all trades prior to current share.');
disp('   Col 27 is sum of  (CONTINUATION value  +  disc.CS) after ALL current decision trades executed).  This col. is SAME for all trades this instant.');
disp('   That is, Col 27 is the maximal value from the discrete optimization over #shares to buy/sell this instant.');
disp(' ');
disp('   Every decision by LARGE (in which shares remain after decision) will have a row with VOLUME(col.30)= 0 and -9 filler in col.29,31,32.');
disp('   Of course, #rows no longer equals #decisions.');
disp(' ');
disp(' ');
end;
