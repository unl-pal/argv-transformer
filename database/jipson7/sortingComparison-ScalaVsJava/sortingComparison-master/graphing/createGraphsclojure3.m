clojureFileName = 'data-clojure2.txt';

data = importdata(clojureFileName);

fittype = 'poly3';

num = 100:100:9900;
num = num';

clojure_quickInt = data(:, 1);
clojure_quickFloat = data(:, 2);
clojure_quickString = data(:, 3);

clojureQuickInt = fit(num, clojure_quickInt, fittype);
clojureQuickFloat = fit(num, clojure_quickFloat, fittype);
clojureQuickString = fit(num, clojure_quickString, fittype);

%%%%%%%%%PLOT clojure

maxY = 0.5;
maxX = 10000;

plot(clojureQuickInt, 'r-');
axis([0 10000 0 maxY]);
hold on;
plot(clojureQuickFloat, 'g-');
axis([0 10000 0 maxY]);
plot(clojureQuickString, 'k-');
axis([0 10000 0 maxY]);

title('Clojure');
legend('Quick (Integers)', 'Quick (Floats)', 'Quick (Strings)', 'Location', 'northeast');
xlabel('Number of Items in List');
ylabel('Sorting Time (Milliseconds)');

