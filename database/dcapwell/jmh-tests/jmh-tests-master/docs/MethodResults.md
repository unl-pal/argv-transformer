# Results

These are the results for running across different function/method types. They test to see if state is faster than param passing, and what are the different cost for each method type.

Hardware:
OS  : OS X 10.9.2
CPU : 2.3 GHz Intel Core i7
Mem : 16 GB

## TL;DR

Sorted by slowest to fastest (higher is better).

```
c.g.d.j.m.FunctionTypes.interfaceMethodsWithState    thrpt       20  331976677.818 ±  3064283.992  ops/s
c.g.d.j.m.FunctionTypes.abstractMethodsWithState     thrpt       20  348885224.463 ± 23635368.470  ops/s
c.g.d.j.m.FunctionTypes.interfaceMethods             thrpt       20  387554249.849 ±  1894026.212  ops/s
c.g.d.j.m.FunctionTypes.abstractMethods              thrpt       20  397957267.274 ±  3270332.174  ops/s
c.g.d.j.m.FunctionTypes.staticFunctionsWithState     thrpt       20  427921020.167 ±  3590708.149  ops/s
c.g.d.j.m.FunctionTypes.staticFunctions              thrpt       20  436709674.316 ±  2032973.039  ops/s
```

## Summary

Seems that when a method or function tries to access fields, this is slower than accessing the params given to them. This is most likely caused by the fact that local variables are less work than fields at lookup time.

A local variable knows the address at compile time, but field needs to find it by looking at `this` first, causing slightly more work.  This shows with method invocation, but shouldn't be the issue with static function state (since address should also have been fixed).
