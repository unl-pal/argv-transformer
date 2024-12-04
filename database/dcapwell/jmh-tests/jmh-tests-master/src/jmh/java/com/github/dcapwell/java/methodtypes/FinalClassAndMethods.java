package com.github.dcapwell.java.methodtypes;

import org.openjdk.jmh.annotations.Benchmark;

import java.util.concurrent.ThreadLocalRandom;

/**
 * There is the idea that final classes and final methods are faster than non-final versions.  This set of benchmarks
 * are to see if this is true.  Reading http://www.javaperformancetuning.com/tips/final.shtml alludes to that it may
 * (can be inline earlier), but most likely shouldn't be able to notice.
 *
 * TL;DR - for the most part, there isn't a noticeable difference.  After compiler phases, the same thing is generated
 * so its not clear when final would be inline before non-final.  Really, the main reason to use final should be for
 * inheritance safety and not performance.
 *
 * NOTICE - any benchmark that shows return of a sum is faster than return a constant has a benchmark bug in it ^_^
 *
 * Default JVM args:
 * <code>
 * Benchmark                                    Mode  Cnt          Score          Error  Units
 * FinalClassAndMethods.baseline                thrpt   50  373669309.636 ± 33779048.905  ops/s
 * FinalClassAndMethods.finalClass              thrpt   50  397438540.633 ±  3394016.689  ops/s
 * FinalClassAndMethods.finalClassFinalMethod   thrpt   50  399508384.665 ±  2726750.841  ops/s
 * FinalClassAndMethods.klass                   thrpt   50  396816660.134 ±  4070823.226  ops/s
 * FinalClassAndMethods.klassFinalMethod        thrpt   50  399513423.249 ±  2849232.456  ops/s
 * FinalClassAndMethods.finalInherent           thrpt   25  398192549.641 ±  1751790.955  ops/s
 * FinalClassAndMethods.finalInherentBottom     thrpt   25  400171172.955 ±  4347949.184  ops/s
 * FinalClassAndMethods.finalInherentTop        thrpt   25  397438864.338 ±  3917916.591  ops/s
 * FinalClassAndMethods.inherent                thrpt   25  398632444.801 ±  5864522.924  ops/s
 * FinalClassAndMethods.inherentBottom          thrpt   25  393243024.866 ± 16039657.076  ops/s
 * FinalClassAndMethods.inherentTop             thrpt   25  396937584.047 ±  4138529.505  ops/s
 * </code>
 *
 * With -XX:TieredStopAtLevel=1 (disable c2)
 *
 * <code>
 * Benchmark                                    Mode  Cnt          Score         Error  Units
 * FinalClassAndMethods.baseline                thrpt   25  306058741.554 ± 3814773.444  ops/s
 * FinalClassAndMethods.finalClass              thrpt   25  327349874.468 ± 5650802.721  ops/s
 * FinalClassAndMethods.finalClassFinalMethod   thrpt   25  333485443.008 ± 2099902.222  ops/s
 * FinalClassAndMethods.klass                   thrpt   25  331459992.362 ± 3611820.620  ops/s
 * FinalClassAndMethods.klassFinalMethod        thrpt   25  330344580.397 ± 4676674.398  ops/s
 * FinalClassAndMethods.finalInherent           thrpt   25  305157449.448 ± 3155825.018  ops/s
 * FinalClassAndMethods.finalInherentBottom     thrpt   25  306487373.955 ± 3399720.997  ops/s
 * FinalClassAndMethods.finalInherentTop        thrpt   25  306967419.248 ± 2958346.113  ops/s
 * FinalClassAndMethods.inherent                thrpt   25  307497527.692 ± 2640616.648  ops/s
 * FinalClassAndMethods.inherentBottom          thrpt   25  305436343.388 ± 2592518.126  ops/s
 * FinalClassAndMethods.inherentTop             thrpt   25  306167352.154 ± 2928546.624  ops/s
 * </code>
 *
 * With -Xint (only Interpreter)
 *
 * <code>
 * Benchmark                                    Mode  Cnt        Score        Error  Units
 * FinalClassAndMethods.baseline                thrpt   25  5950167.330 ±  89499.867  ops/s
 * FinalClassAndMethods.finalClass              thrpt   25  4271124.899 ±  38625.021  ops/s
 * FinalClassAndMethods.finalClassFinalMethod   thrpt   25  4243768.015 ± 110116.670  ops/s
 * FinalClassAndMethods.klass                   thrpt   25  4301121.940 ±  43435.968  ops/s
 * FinalClassAndMethods.klassFinalMethod        thrpt   25  4258390.492 ±  52250.845  ops/s
 * FinalClassAndMethods.finalInherent           thrpt   25  4183660.365 ±  34605.968  ops/s
 * FinalClassAndMethods.finalInherentBottom     thrpt   25  4166255.754 ±  49105.898  ops/s
 * FinalClassAndMethods.finalInherentTop        thrpt   25  4104791.234 ± 109498.569  ops/s
 * FinalClassAndMethods.inherent                thrpt   25  4140848.483 ±  59116.580  ops/s
 * FinalClassAndMethods.inherentBottom          thrpt   25  4232368.436 ±  31280.964  ops/s
 * FinalClassAndMethods.inherentTop             thrpt   25  4141947.126 ±  63138.209  ops/s
 * </code>
 */
public class FinalClassAndMethods {
    private static final int left = ThreadLocalRandom.current().nextInt();
    private static final int right = ThreadLocalRandom.current().nextInt();
    private static final InherentMath math = new Top();
    private static final InherentMath finalMath = new FinalTop();

    public static final class FinalMath {
    }

    public static class Math {
    }

    public interface InherentMath {
    }

    public static abstract class Bottom implements InherentMath {
    }

    public static class Top extends Bottom {
    }

    public final static class FinalTop extends Bottom {
    }
}
