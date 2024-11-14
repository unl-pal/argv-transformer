clojureFileName = 'data-clojure2.txt';

data = importdata(clojureFileName);

fittype = 'poly5';

num = 100:100:9900;
num = num';

clojure_quickInt = data(:, 1);
disp(max(clojure_quickInt));
clojure_quickFloat = data(:, 2);
disp(max(clojure_quickFloat));
clojure_quickString = data(:, 3);
disp(max(clojure_quickString));

clojure_mergeInt = data(:, 4);
clojure_mergeFloat = data(:, 5);
clojure_mergeString = data(:, 6);

clojureQuickInt = fit(num, clojure_quickInt, fittype);
clojureQuickFloat = fit(num, clojure_quickFloat, fittype);
clojureQuickString = fit(num, clojure_quickString, fittype);

clojureMergeInt = fit(num, clojure_mergeInt, fittype);
clojureMergeFloat = fit(num, clojure_mergeFloat, fittype);
clojureMergeString = fit(num, clojure_mergeString, fittype);


%%%%%%%%%PLOT clojure

maxY = 170;
maxX = 10000;

plot(clojureQuickInt, 'y-');
axis([0 10000 0 maxY]);
hold on;
plot(clojureQuickFloat, 'm-');
axis([0 10000 0 maxY]);
plot(clojureQuickString, 'c-');
axis([0 10000 0 maxY]);
plot(clojureMergeInt, 'r-');
axis([0 10000 0 maxY]);
plot(clojureMergeFloat, 'g-');
axis([0 10000 0 maxY]);
plot(clojureMergeString, 'b-');
axis([0 10000 0 maxY]);

title('Clojure');
legend('Quick (Integers)', 'Quick (Floats)', 'Quick (Strings)', 'Merge (Integers)', 'Merge (Floats)', 'Merge (Strings)', 'Location', 'northeast');
xlabel('Number of Items in List');
ylabel('Sorting Time (Milliseconds)');

