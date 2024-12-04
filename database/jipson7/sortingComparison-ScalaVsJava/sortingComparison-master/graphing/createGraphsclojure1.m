clojureFileName = 'data-clojure.txt';

data = importdata(clojureFileName);

fittype = 'poly5';

num = 100:100:900;
num = num';

clojure_quickInt = data(:, 1);
clojure_quickFloat = data(:, 2);
clojure_quickString = data(:, 3);

clojure_mergeInt = data(:, 4);
clojure_mergeFloat = data(:, 5);
clojure_mergeString = data(:, 6);

clojure_bubbleInt = data(:, 7);
clojure_bubbleFloat = data(:, 8);
clojure_bubbleString = data(:, 9);

clojureQuickInt = fit(num, clojure_quickInt, fittype);
clojureQuickFloat = fit(num, clojure_quickFloat, fittype);
clojureQuickString = fit(num, clojure_quickString, fittype);

clojureMergeInt = fit(num, clojure_mergeInt, fittype);
clojureMergeFloat = fit(num, clojure_mergeFloat, fittype);
clojureMergeString = fit(num, clojure_mergeString, fittype);

clojureBubbleInt = fit(num, clojure_bubbleInt, fittype);
clojureBubbleFloat = fit(num, clojure_bubbleFloat, fittype);
clojureBubbleString = fit(num, clojure_bubbleString, fittype);

%%%%%%%%%PLOT clojure

maxY = 20000;
maxX = 1000;

plot(clojureQuickInt, 'y-');
axis([0 10000 0 maxY]);
hold on;
plot(clojureQuickFloat, 'm-');
axis([0 maxX 0 maxY]);
plot(clojureQuickString, 'c-');
axis([0 maxX 0 maxY]);
plot(clojureMergeInt, 'r-');
axis([0 maxX 0 maxY]);
plot(clojureMergeFloat, 'g-');
axis([0 maxX 0 maxY]);
plot(clojureMergeString, 'b-');
axis([0 10000 0 maxY]);
plot(clojureBubbleInt, 'r--');
axis([0 maxX 0 maxY]);
plot(clojureBubbleFloat, 'k-');
axis([0 maxX 0 maxY]);
plot(clojureBubbleString, 'm--');
axis([0 maxX 0 maxY]);
title('Clojure');
legend('Quick (Integers)', 'Quick (Floats)', 'Quick (Strings)', 'Merge (Integers)', 'Merge (Floats)', 'Merge (Strings)', 'Bubble (Integers)', 'Bubble (Floats)', 'Bubble (Strings)', 'Location', 'northeast');
xlabel('Number of Items in List');
ylabel('Sorting Time (Milliseconds)');

