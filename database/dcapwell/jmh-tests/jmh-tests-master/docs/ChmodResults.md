# Results

These are the results for running across the different ways to do chmod.

Hardware:
OS  : OS X 10.9.2
CPU : 2.3 GHz Intel Core i7
Mem : 16 GB

## TL;DR

|        | 1st       | 2nd        |
|--------|-----------|------------|
| Java 6 | JNR - FFI | Reflection |
| Java 7 | JNR - FFI | NIO        |
| Java 8 | NIO       | JNR - FFI  |

## Oracle JDK 6

### JNA

```
# Benchmark mode: Throughput, ops/time
# Benchmark: com.github.dcapwell.java.file.operations.jna.JNAFileOperations.chmod
# Parameters: (mode = 0777)

Result: 74763.711 ±(99.9%) 587.673 ops/s [Average]
  Statistics: (min, avg, max) = (70384.049, 74763.711, 78686.495), stdev = 2488.244
  Confidence interval (99.9%): [74176.038, 75351.384]

c.g.d.j.f.o.jna.JNAFileOperations.chmod                0777  thrpt      200   74763.711 ± 587.673  ops/s

# Benchmark mode: Throughput, ops/time
# Benchmark: com.github.dcapwell.java.file.operations.jna.JNAFileOperations.chmod
# Parameters: (mode = 0755)

Result: 75158.776 ±(99.9%) 595.001 ops/s [Average]
  Statistics: (min, avg, max) = (68334.544, 75158.776, 78414.992), stdev = 2519.271
  Confidence interval (99.9%): [74563.776, 75753.777]

c.g.d.j.f.o.jna.JNAFileOperations.chmod                0755  thrpt      200   75158.776 ± 595.001  ops/s
```

### Fork

```
# Benchmark mode: Throughput, ops/time
# Benchmark: com.github.dcapwell.java.file.operations.fork.ForkFileOperations.chmod
# Parameters: (mode = 0777)

Result: 177.826 ±(99.9%) 0.858 ops/s [Average]
  Statistics: (min, avg, max) = (159.641, 177.826, 184.391), stdev = 3.631
  Confidence interval (99.9%): [176.969, 178.684]

c.g.d.j.f.o.fork.ForkFileOperations.chmod              0777  thrpt      200     177.826 ±   0.858  ops/s

# Benchmark mode: Throughput, ops/time
# Benchmark: com.github.dcapwell.java.file.operations.fork.ForkFileOperations.chmod
# Parameters: (mode = 0755)

Result: 176.938 ±(99.9%) 1.297 ops/s [Average]
  Statistics: (min, avg, max) = (162.310, 176.938, 192.520), stdev = 5.492
  Confidence interval (99.9%): [175.641, 178.236]

c.g.d.j.f.o.fork.ForkFileOperations.chmod              0755  thrpt      200     176.938 ±   1.297  ops/s
```

### Java 6 Reflection

```
# Benchmark mode: Throughput, ops/time
# Benchmark: com.github.dcapwell.java.file.operations.java6.JavaReflectFileOperations.chmod
# Parameters: (mode = 0777)

Result: 119339.092 ±(99.9%) 565.809 ops/s [Average]
  Statistics: (min, avg, max) = (114145.892, 119339.092, 124648.102), stdev = 2395.670
  Confidence interval (99.9%): [118773.283, 119904.901]

c.g.d.j.f.o.java6.JavaReflectFileOperations.chmod      0777  thrpt      200  119339.092 ± 565.809  ops/s

# Benchmark mode: Throughput, ops/time
# Benchmark: com.github.dcapwell.java.file.operations.java6.JavaReflectFileOperations.chmod
# Parameters: (mode = 0755)

Result: 120633.863 ±(99.9%) 549.694 ops/s [Average]
  Statistics: (min, avg, max) = (116393.157, 120633.863, 126097.537), stdev = 2327.438
  Confidence interval (99.9%): [120084.169, 121183.556]

c.g.d.j.f.o.java6.JavaReflectFileOperations.chmod      0755  thrpt      200  120633.863 ± 549.694  ops/s
```

### JNR-FFI

```
# Benchmark mode: Throughput, ops/time
# Benchmark: com.github.dcapwell.java.file.operations.jnr.ffi.JNRFFIFileOperations.chmod
# Parameters: (mode = 0777)

Result: 126086.347 ±(99.9%) 465.560 ops/s [Average]
  Statistics: (min, avg, max) = (120376.705, 126086.347, 130950.413), stdev = 1971.212
  Confidence interval (99.9%): [125620.786, 126551.907]

c.g.d.j.f.o.jnr.ffi.JNRFFIFileOperations.chmod         0777  thrpt      200  126086.347 ± 465.560  ops/s

# Benchmark mode: Throughput, ops/time
# Benchmark: com.github.dcapwell.java.file.operations.jnr.ffi.JNRFFIFileOperations.chmod
# Parameters: (mode = 0755)

Result: 125174.266 ±(99.9%) 629.872 ops/s [Average]
  Statistics: (min, avg, max) = (118595.301, 125174.266, 129706.006), stdev = 2666.918
  Confidence interval (99.9%): [124544.394, 125804.138]

c.g.d.j.f.o.jnr.ffi.JNRFFIFileOperations.chmod         0755  thrpt      200  125174.266 ± 629.872  ops/s
```

### Results

Numbers are from the param run that has the smallest stdev.

|       | JNR - FFI   | Java 6 Reflection | JNA        | Fork    |
|-------|-------------|-------------------|------------|---------|
| min   | 120,376.705 | 116,393.157       | 70,384.049 | 159.641 |
| avg   | 126,086.347 | 120,633.863       | 74,763.711 | 177.826 |
| max   | 130,950.413 | 126,097.537       | 78,686.495 | 184.391 |
| stdev |   1,971.212 |   2,327.438       |  2,488.244 |   3.631 |

## Oracle JDK 7

### JNA

```
Result: 73730.305 ±(99.9%) 1144.286 ops/s [Average]
  Statistics: (min, avg, max) = (55544.403, 73730.305, 78984.895), stdev = 4844.977
  Confidence interval (99.9%): [72586.019, 74874.591]


# Run complete. Total time: 00:08:05

Benchmark                                 Mode  Samples      Score      Error  Units
c.g.d.j.f.o.j.JNAFileOperations.chmod    thrpt      200  73730.305 ± 1144.286  ops/s
```

### Fork

```
Result: 366.856 ±(99.9%) 1.908 ops/s [Average]
  Statistics: (min, avg, max) = (329.887, 366.856, 378.315), stdev = 8.078
  Confidence interval (99.9%): [364.948, 368.764]


# Run complete. Total time: 00:16:07

Benchmark                                 (mode)   Mode  Samples    Score   Error  Units
c.g.d.j.f.o.f.ForkFileOperations.chmod      0777  thrpt      200  363.973 ± 2.370  ops/s
c.g.d.j.f.o.f.ForkFileOperations.chmod      0755  thrpt      200  366.856 ± 1.908  ops/s
```

### Java 7 NIO

```
Result: 123764.016 ±(99.9%) 2202.005 ops/s [Average]
  Statistics: (min, avg, max) = (83595.279, 123764.016, 132371.924), stdev = 9323.428
  Confidence interval (99.9%): [121562.010, 125966.021]


# Run complete. Total time: 00:08:03

Benchmark                                  Mode  Samples       Score      Error  Units
c.g.d.j.f.o.j.JavaFileOperations.chmod    thrpt      200  123764.016 ± 2202.005  ops/s
```

### Java 6 Reflection

```
Result: 120217.883 ±(99.9%) 1056.684 ops/s [Average]
  Statistics: (min, avg, max) = (95966.313, 120217.883, 126644.254), stdev = 4474.066
  Confidence interval (99.9%): [119161.200, 121274.567]


# Run complete. Total time: 00:16:06

Benchmark                                        (mode)   Mode  Samples       Score      Error  Units
c.g.d.j.f.o.j.JavaReflectFileOperations.chmod      0777  thrpt      200  121866.015 ±  323.911  ops/s
c.g.d.j.f.o.j.JavaReflectFileOperations.chmod      0755  thrpt      200  120217.883 ± 1056.684  ops/s
```

### JNR-FFI

```
Result: 128113.517 ±(99.9%) 1138.167 ops/s [Average]
  Statistics: (min, avg, max) = (99209.585, 128113.517, 135954.357), stdev = 4819.072
  Confidence interval (99.9%): [126975.349, 129251.684]


# Run complete. Total time: 00:16:10

Benchmark                                     (mode)   Mode  Samples       Score      Error  Units
c.g.d.j.f.o.j.f.JNRFFIFileOperations.chmod      0777  thrpt      200  130965.546 ±  551.339  ops/s
c.g.d.j.f.o.j.f.JNRFFIFileOperations.chmod      0755  thrpt      200  128113.517 ± 1138.167  ops/s
```

### Results

|       | JNR - FFI   | Java 7 NIO  | Java 6 Reflection | JNA        | Fork    |
|-------|-------------|-------------|-------------------|------------|---------|
| min   |  99,209.585 |  83,595.279 |  95,966.313       | 55,544.403 | 329.887 |
| avg   | 128,113.517 | 123,764.016 | 120,217.883       | 73,730.305 | 366.856 |
| max   | 135,954.357 | 132,371.924 | 126,644.254       | 78,984.895 | 378.315 |
| stdev |   4,819.072 |   9,323.428 |   4,474.066       |  4,844.977 |   8.078 |

## Oracle JDK 8

### JNA

```
# Benchmark mode: Throughput, ops/time
# Benchmark: com.github.dcapwell.java.file.operations.jna.JNAFileOperations.chmod
# Parameters: (mode = 0777)

Result: 87343.385 ±(99.9%) 1133.401 ops/s [Average]
  Statistics: (min, avg, max) = (71765.724, 87343.385, 93730.569), stdev = 4798.890
  Confidence interval (99.9%): [86209.984, 88476.786]

c.g.d.j.f.o.jna.JNAFileOperations.chmod                0777  thrpt      200   87343.385 ± 1133.401  ops/s

# Benchmark mode: Throughput, ops/time
# Benchmark: com.github.dcapwell.java.file.operations.jna.JNAFileOperations.chmod
# Parameters: (mode = 0755)

Result: 86638.794 ±(99.9%) 1070.762 ops/s [Average]
  Statistics: (min, avg, max) = (73207.869, 86638.794, 92129.627), stdev = 4533.673
  Confidence interval (99.9%): [85568.032, 87709.556]

c.g.d.j.f.o.jna.JNAFileOperations.chmod                0755  thrpt      200   86638.794 ± 1070.762  ops/s
```

### Fork

```
# Benchmark mode: Throughput, ops/time
# Benchmark: com.github.dcapwell.java.file.operations.fork.ForkFileOperations.chmod
# Parameters: (mode = 0777)

Result: 365.945 ±(99.9%) 1.711 ops/s [Average]
  Statistics: (min, avg, max) = (323.416, 365.945, 374.957), stdev = 7.244
  Confidence interval (99.9%): [364.234, 367.656]

c.g.d.j.f.o.fork.ForkFileOperations.chmod              0777  thrpt      200     365.945 ±    1.711  ops/s

# Benchmark mode: Throughput, ops/time
# Benchmark: com.github.dcapwell.java.file.operations.fork.ForkFileOperations.chmod
# Parameters: (mode = 0755)

Result: 370.897 ±(99.9%) 0.621 ops/s [Average]
  Statistics: (min, avg, max) = (361.824, 370.897, 376.926), stdev = 2.628
  Confidence interval (99.9%): [370.276, 371.518]

c.g.d.j.f.o.fork.ForkFileOperations.chmod              0755  thrpt      200     370.897 ±    0.621  ops/s
```

### Java 7 NIO

```
# Benchmark mode: Throughput, ops/time
# Benchmark: com.github.dcapwell.java.file.operations.java7.NIOFileOperations.chmod
# Parameters: (mode = 0777)

Result: 142571.112 ±(99.9%) 459.251 ops/s [Average]
  Statistics: (min, avg, max) = (137750.333, 142571.112, 146470.899), stdev = 1944.499
  Confidence interval (99.9%): [142111.860, 143030.363]

c.g.d.j.f.o.java7.NIOFileOperations.chmod              0777  thrpt      200  142571.112 ±  459.251  ops/s

# Benchmark mode: Throughput, ops/time
# Benchmark: com.github.dcapwell.java.file.operations.java7.NIOFileOperations.chmod
# Parameters: (mode = 0755)

Result: 139128.632 ±(99.9%) 610.653 ops/s [Average]
  Statistics: (min, avg, max) = (131039.028, 139128.632, 143501.308), stdev = 2585.542
  Confidence interval (99.9%): [138517.979, 139739.284]

c.g.d.j.f.o.java7.NIOFileOperations.chmod              0755  thrpt      200  139128.632 ±  610.653  ops/s
```

### Java 6 Reflection

```
# Benchmark mode: Throughput, ops/time
# Benchmark: com.github.dcapwell.java.file.operations.java6.JavaReflectFileOperations.chmod
# Parameters: (mode = 0777)

Result: 117908.791 ±(99.9%) 371.980 ops/s [Average]
  Statistics: (min, avg, max) = (114049.902, 117908.791, 122723.562), stdev = 1574.986
  Confidence interval (99.9%): [117536.811, 118280.770]

c.g.d.j.f.o.java6.JavaReflectFileOperations.chmod      0777  thrpt      200  117908.791 ±  371.980  ops/s

# Benchmark mode: Throughput, ops/time
# Benchmark: com.github.dcapwell.java.file.operations.java6.JavaReflectFileOperations.chmod
# Parameters: (mode = 0755)

Result: 118241.793 ±(99.9%) 259.438 ops/s [Average]
  Statistics: (min, avg, max) = (115431.920, 118241.793, 120863.695), stdev = 1098.477
  Confidence interval (99.9%): [117982.355, 118501.231]

c.g.d.j.f.o.java6.JavaReflectFileOperations.chmod      0755  thrpt      200  118241.793 ±  259.438  ops/s
```

### JNR-FFI

```
# Benchmark mode: Throughput, ops/time
# Benchmark: com.github.dcapwell.java.file.operations.jnr.ffi.JNRFFIFileOperations.chmod
# Parameters: (mode = 0777)

Result: 131783.975 ±(99.9%) 465.531 ops/s [Average]
  Statistics: (min, avg, max) = (127609.852, 131783.975, 136323.269), stdev = 1971.087
  Confidence interval (99.9%): [131318.444, 132249.506]

c.g.d.j.f.o.jnr.ffi.JNRFFIFileOperations.chmod         0777  thrpt      200  131783.975 ±  465.531  ops/s

# Benchmark mode: Throughput, ops/time
# Benchmark: com.github.dcapwell.java.file.operations.jnr.ffi.JNRFFIFileOperations.chmod
# Parameters: (mode = 0755)

Result: 132850.674 ±(99.9%) 560.112 ops/s [Average]
  Statistics: (min, avg, max) = (115244.014, 132850.674, 136843.854), stdev = 2371.547
  Confidence interval (99.9%): [132290.562, 133410.786]

c.g.d.j.f.o.jnr.ffi.JNRFFIFileOperations.chmod         0755  thrpt      200  132850.674 ±  560.112  ops/s
```

### Results

Numbers are from the param run that has the smallest stdev.

|       | Java 7 NIO  | JNR - FFI   | Java 6 Reflection | JNA        | Fork    |
|-------|-------------|-------------|-------------------|------------|---------|
| min   | 137,750.333 | 127,609.852 | 115,431.920       | 73,207.869 | 361.824 |
| avg   | 142,571.112 | 131,783.975 | 118,241.793       | 86,638.794 | 370.897 |
| max   | 146,470.899 | 136,323.269 | 120,863.695       | 92,129.627 | 376.926 |
| stdev | 1,944.499   | 1,971.087   | 1,098.477         | 4,533.673  | 2.628   |
