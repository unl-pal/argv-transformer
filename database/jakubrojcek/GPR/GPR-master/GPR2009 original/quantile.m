function i = quantile(xmat,q,sformat)
%            quantile(xmat,q,sformat)
% xmat     is a matrix of data, each col of which will be quantiled.
% q        specifies the quantiles to be used, default = [0 10 50 90 100]
% sformat  is format string for sprintf(),     default =  '%10.4f'
%
% example:  quantile(xdata, [0 25 50 75 100], '%8.4f');
if ~exist('q');         q = [0 10 50 90 100];   end;
if ~exist('sformat');   sformat = '%10.4f' ;     end;
q = reshape(q,length(q),1); 		%% column vector
[i c] = size(xmat);
[i s] = size(sformat);
ff = ones(1, c*s)*32; 
ff = setstr(ff);
for i= 1:c
  ff(1,(i-1)*s+1:i*s)= sformat;
end;

%% make nobs a whole number.  unless accuracy > 9 digits, in which case nobs will be decimal but with, say 2 decimals instead of 12.
nn = ff;  i= find(nn=='.');  nn(i+1) = '0';

if 0; disp(' '); disp('cell (q,c) gives qth quantile of cth column of matrix.'); disp(' '); end;
disp(sprintf([ff '   %4.0f percentile\n'], [prctile(xmat,q) q]'))
disp(sprintf([ff '    mean      '], mean(xmat)))
disp(sprintf([ff '    mean(abs) '], mean(abs(xmat))))
disp(sprintf([ff '    std       '], std(xmat)))
disp(sprintf([ff '    range     '], range(xmat)))
disp(sprintf([ff '    share<0   '], mean(xmat<0)))
disp(sprintf([ff '    share=0   '], mean(xmat==0)))
disp(sprintf([ff '    share>0   '], mean(xmat>0)))
disp(sprintf([nn '    nobs      '], ones(length(xmat(1,:)),1)*length(xmat(:,1))))
disp(' ')

