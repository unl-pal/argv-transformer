clojureFileName = 'data-java.txt';

data = importdata(clojureFileName);

fittype = 'poly5';

num = data(:, 1);

java_quickInt = data(:, 2);
java_quickFloat = data(:, 3);
java_quickString = data(:, 4);

java_mergeInt = data(:, 5);
java_mergeFloat = data(:, 6);
java_mergeString = data(:, 7);

java_bubbleInt = data(:, 8);
java_bubbleFloat = data(:, 9);
java_bubbleString = data(:, 10);

javaQuickInt = fit(num, java_quickInt, fittype);
javaQuickFloat = fit(num, java_quickFloat, fittype);
javaQuickString = fit(num, java_quickString, fittype);

javaMergeInt = fit(num, java_mergeInt, fittype);
javaMergeFloat = fit(num, java_mergeFloat, fittype);
javaMergeString = fit(num, java_mergeString, fittype);

javaBubbleInt = fit(num, java_bubbleInt, fittype);
javaBubbleFloat = fit(num, java_bubbleFloat, fittype);
javaBubbleString = fit(num, java_bubbleString, fittype);

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

scalaFileName = 'data-scala.txt';

data2 = importdata(scalaFileName);

scala_quickInt = data2(:, 2);
scala_quickFloat = data2(:, 3);
scala_quickString = data2(:, 4);

scala_mergeInt = data2(:, 5);
scala_mergeFloat = data2(:, 6);
scala_mergeString = data2(:, 7);

scala_bubbleInt = data2(:, 8);
scala_bubbleFloat = data2(:, 9);
scala_bubbleString = data2(:, 10);

scalaQuickInt = fit(num, scala_quickInt, fittype);
scalaQuickFloat = fit(num, scala_quickFloat, fittype);
scalaQuickString = fit(num, scala_quickString, fittype);

scalaMergeInt = fit(num, scala_mergeInt, fittype);
scalaMergeFloat = fit(num, scala_mergeFloat, fittype);
scalaMergeString = fit(num, scala_mergeString, fittype);

scalaBubbleInt = fit(num, scala_bubbleInt, fittype);
scalaBubbleFloat = fit(num, scala_bubbleFloat, fittype);
scalaBubbleString = fit(num, scala_bubbleString, fittype);

%%%%%%%%%PLOT Quicksort Scala vs Java

maxY = 120;


plot(javaQuickInt, 'r-');
axis([0 10000 0 maxY]);
hold on;
plot(javaQuickFloat, 'b-');
axis([0 10000 0 maxY]);
plot(javaQuickString, 'm-');
axis([0 10000 0 maxY]);
plot(scalaQuickInt, 'k-');
axis([0 10000 0 maxY]);
plot(scalaQuickFloat, 'g-');
axis([0 10000 0 maxY]);
plot(scalaQuickString, 'y-');
axis([0 10000 0 maxY]);
title('QuickSort');
legend('Java (Integers)', 'Java (Floats)', 'Java (Strings)', 'Scala (Integers)', 'Scala (Floats)', 'Scala (Strings)', 'Location', 'northwest');
xlabel('Number of Items in List');
ylabel('Sorting Time (Milliseconds)');

%%%%%%%%%PLOT MergeSort Scala vs Java

figure();

maxY = 120;


plot(javaMergeInt, 'r-');
axis([0 10000 0 maxY]);
hold on;
plot(javaMergeFloat, 'b-');
axis([0 10000 0 maxY]);
plot(javaMergeString, 'm-');
axis([0 10000 0 maxY]);
plot(scalaMergeInt, 'k-');
axis([0 10000 0 maxY]);
plot(scalaMergeFloat, 'g-');
axis([0 10000 0 maxY]);
plot(scalaMergeString, 'y-');
axis([0 10000 0 maxY]);
title('MergeSort');
legend('Java (Integers)', 'Java (Floats)', 'Java (Strings)', 'Scala (Integers)', 'Scala (Floats)', 'Scala (Strings)', 'Location', 'northwest')
xlabel('Number of Items in List');
ylabel('Sorting Time (Milliseconds)');

%%%%%%%%%PLOT BubbleSort Scala vs Java

figure();

maxY = 6000;


plot(javaBubbleInt, 'r-');
axis([0 10000 0 maxY]);
hold on;
plot(javaBubbleFloat, 'b-');
axis([0 10000 0 maxY]);
plot(javaBubbleString, 'm-');
axis([0 10000 0 maxY]);
plot(scalaBubbleInt, 'k-');
axis([0 10000 0 maxY]);
plot(scalaBubbleFloat, 'g-');
axis([0 10000 0 maxY]);
plot(scalaBubbleString, 'y-');
axis([0 10000 0 maxY]);
title('BubbleSort');
legend('Java (Integers)', 'Java (Floats)', 'Java (Strings)', 'Scala (Integers)', 'Scala (Floats)', 'Scala (Strings)', 'Location', 'northwest')
xlabel('Number of Items in List');
ylabel('Sorting Time (Milliseconds)');


