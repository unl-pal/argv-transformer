function [B,BINT,R,RINT,STATS] = ols(y,x,alph,xnames,yname,doprint,dowhite,do2,doHet)
%        [B,BINT,R,RINT,STATS] = ols(y,x,alph,xnames,yname,doprint,dowhite,do2,doHet)
% simply provides nicer output of values returned by regress(y,x,alph)
% alph  and  xnames  are optional parameters
% BINT contains T-stats in 3rd column
% if doprint, then provide print-out  (default = 1)
% if dowhite, then also report White SE and t-stats
% if do2,     then report 2nd regression using only variables with t>1.65 in first regression.
% if doHet>0, then report conditional heterscedasticity regression using  resid^2
% if doHet= -1, then report conditional heteroscedasticity regression using log(resid^2) (guarantees pred variance > 0)
% if doHet= -2, then report conditional heteroscedasticity regression using |resid|

[r,c] = size(x);
if ~exist('doprint'); doprint = 1;    end;
if ~exist('dowhite'); dowhite = 1;    end;
if ~exist('alph');       alph = 0.05; end;
if ~exist('do2');         do2 = 0;    end;
if ~exist('doHet');     doHet = 0;    end;
if ~exist('xnames')
  xnames = ones(c,13)*32;
  for i=1:c
    xnames(i,1:10+floor(log10(i))) = ['variable ' int2str(i)];
  end
end
if ~exist('yname');  yname='NA';  end;
[i k] = size(xnames);
if i~=c
  disp('ERROR: columns of x not same as rows of xnames ... using generic names')
  xnames = ones(c,13)*32;
  for i=1:c
    xnames(i,1:10+floor(log10(i))) = ['variable ' int2str(i)];
  end
end
if k<13;
  xnames = [xnames ones(i,13-k)*32];
end

s2 = warning(   'off' , 'MATLAB:divideByZero');  [R,B] = corrcoef([y x]);  mcp = [mean(x); R(2:c+1,1)'; B(2:c+1,1)'];  %% 3 by c : mean, correlation with y, corr P-values 
s2 = warning( s2.state, 'MATLAB:divideByZero');  clear s2;

[B,BINT,R,RINT,STATS] = regress(y,x,alph);    STATS(1) = 1- (R'*R) / ( ( y-mean(y) )'*( y -mean(y)) );  %% R-square WRONG in regress
s2     = R'*R/(length(y)-length(B));
xxinv  = inv(x'*x);
stderr = sqrt(diag(s2*xxinv));
tstat  = abs(B)./stderr;
tprob  = 2*(1-tcdf(tstat,r-c));
if dowhite;
  white  = xxinv*(x'*diag(R.*R)*x)*xxinv ;   %% White's heteroscedasticity consistent estimator
  whitet = abs(B)./sqrt(diag(white)) ;       %% accuracy of my code confirmed by same results as JPL library
else;
  white = zeros(c,c);
  whitet= zeros(c,1);
end;
BINT    = [ BINT tstat whitet];

if doprint;
  xspace = setstr(32*ones(1, max(1, length(xnames(1,:)) - 12 )));
  disp(' ');
  disp(['*************   OLS  with  DepVar:  ' yname '  (mean~std= ' num2str( mean(y)) ' ' num2str( std(y)) ')' ]);
  disp(['variable name' xspace '  estimate     stderr     t-stat     t-prob   White SE    White t   ' num2str(1-alph) ' CI (nonwhite)        mean   corr     P']);
  disp(['=============' xspace ' =========  =========  =========  =========  =========  =========  ====================  =========  ======  ====='])
  for i=1:c
    disp(sprintf([xnames(i,:) ' %10.4f %10.4f %10.4f %10.4f %10.4f %10.4f %10.4f %10.4f %10.4f  %6.3f %6.3f'], [B(i) stderr(i) tstat(i) tprob(i) sqrt(white(i,i)) whitet(i) BINT(i,1:2) mcp(:,i)'] ))
  end
  disp('_');  %% matlab 6.5 returned 3-tuple in STATS, matlab 7 returns 4-tuple (adding sigma^2
  disp(sprintf('R-sq = %6.4f   R-sq = %6.4f   F = %8.4f   P = %6.4f  N = %6.0f  Sigma^2 = %8.4f  Sigma = %8.4f', [ (1-(r-1)/(r-c)*(1-STATS(1))) STATS(1:3) r s2 sqrt(s2)]))
  disp(' ');

  if do2;
    ii = find( tstat > 1.0 );  %% > 1.65
  
    if 1 & c>5 & (c-length(ii)) > 4 & ( length(ii) < .7*c );  %% fewer than .7 are (marginally) significant
      ii     = unique([1; ii]);  %% use the constant, even if it was insignificant
      xx     = x(:,ii);
      [Bx,BINTx,Rx,RINTx,STATSx] = regress(y, xx, alph);    STATSx(1) = 1- (Rx'*Rx) / ( ( y-mean(y) )'*( y -mean(y)) );  %% R-square WRONG in regress
      s2     = Rx'*Rx/(length(y)-length(Bx));
      xxinv2 = inv(xx'*xx);
      stderr = sqrt(diag(s2*xxinv2));
      tstat  = abs(Bx)./stderr;
      tprob  = 2*(1-tcdf(tstat,r-c));
      if dowhite;
	white  = xxinv2*(xx'*diag(Rx.*Rx)*xx)*xxinv2 ;   %% White's heteroscedasticity consistent estimator
	whitet = abs(Bx)./sqrt(diag(white)) ; 
      end;
      clear xxinv2;
      disp(' ');
      disp([ xspace   '              ---------  Same Regression BUT using ONLY regressors with   t > 1.0  ----------']);
      disp(['variable name' xspace '  estimate     stderr     t-stat     t-prob   White SE    White t   ' num2str(1-alph) ' CI (nonwhite)']);
      disp(['=============' xspace ' =========  =========  =========  =========  =========  =========  ===================='])
      for i=1:length(Bx)
	disp(sprintf([xnames(ii(i),:) ' %10.4f %10.4f %10.4f %10.4f %10.4f %10.4f %10.4f %10.4f'], [Bx(i) stderr(i) tstat(i) tprob(i) sqrt(white(i,i)) whitet(i) BINTx(i,1:2)]))
      end
      disp('_');
      disp(sprintf('R-sq = %6.4f   R-sq = %6.4f   F = %8.4f   P = %6.4f  N = %6.0f  Sigma^2 = %8.4f  Sigma = %8.4f', [ (1-(r-1)/(r-length(Bx))*(1-STATSx(1))) STATSx(1:3) r s2 sqrt(s2)]))
      disp(' ');
    end;   %% do2
    if 0;
      yhat= x*B;
      RSS = norm(yhat-mean(y))^2;  % Residual sum of squares.
      TSS = norm(y-mean(y))^2;     % Total sum of squares.
      
      yhat= xx*Bx;
      RSSx= norm(yhat-mean(y))^2;  % Residual sum of squares.
      TSSx= norm(y-mean(y))^2;     % Total sum of squares.
      keyboard;
    end;
  end;  %% do2
  
  if doHet;
      disp(' ');
      if doHet>0;                  %  dohet = -1 guarantees predicted sigma^2 > 0, and addresses skewness in resid^2
	y = R.*R;       disp([ xspace   '              ---------  Conditional Heteroscedasticity OLS: LHS =   resid^2    ---------']);
      elseif doHet==-2;
	y = abs(R);     disp([ xspace   '              ---------  Conditional Heteroscedasticity OLS: LHS =   |resid|    ---------']);
      else;
	y = log(R.*R);  disp([ xspace   '              ---------  Conditional Heteroscedasticity OLS: LHS = log(resid^2) ---------']);
      end;
      disp(['variable name' xspace '  estimate     stderr     t-stat     t-prob   White SE    White t   ' num2str(1-alph) ' CI (nonwhite)']);
      disp(['=============' xspace ' =========  =========  =========  =========  =========  =========  ===================='])
      [hB,hBINT,hR,hRINT,hSTATS] = regress(y,x,alph);    hSTATS(1) = 1- (hR'*hR) / ( ( y-mean(y) )'*( y -mean(y)) );  %% R-square WRONG in regress
      hs2     = hR'*hR/(length(y)-length(hB));
      % xxinv = inv(x'*x);              %% same as already computed above for primary regression
      hstderr = sqrt(diag(hs2*xxinv));
      htstat  = abs(hB)./hstderr;
      htprob  = 2*(1-tcdf(htstat,r-c));
      if dowhite;
	hwhite  = xxinv*(x'*diag(hR.*hR)*x)*xxinv ;   %% White's heteroscedasticity consistent estimator
	hwhitet = abs(hB)./sqrt(diag(hwhite)) ; 
      else;
	hwhite = zeros(c,c);
	hwhitet= zeros(c,1);
      end;
      for i=1:c
	disp(sprintf([xnames(i,:) ' %10.4f %10.4f %10.4f %10.4f %10.4f %10.4f %10.4f %10.4f'], [hB(i) hstderr(i) htstat(i) htprob(i) sqrt(hwhite(i,i)) hwhitet(i) hBINT(i,1:2)]))
      end
      disp('_');
      disp(sprintf('R-sq = %6.4f   R-sq = %6.4f   F = %8.4f   P = %6.4f  N = %6.0f  Sigma^2 = %8.4f  Sigma = %8.4f', [ (1-(r-1)/(r-c)*(1-hSTATS(1))) hSTATS(1:3) r hs2 sqrt(hs2)]))
      disp(' ');
    
  end;
  
end;    %% doprint
