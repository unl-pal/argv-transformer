# Tutorial: a toy run through filter and transform

This walks a two-file "repository" through the pipeline, skipping the `download` step,
and ends with a real SV-COMP benchmark.

## What you start with

`docs/tutorial/toy-repo/` is a stand-in for a downloaded GitHub project. It has
the directory layout of a normal Maven-ish Java repo and two classes:

- `com/example/Grading.java` — **suitable**. `letterCutoff(int, int)` takes only
  `int` parameters, does `int` arithmetic, and branches on an `int` condition.
  It also imports `com.example.missing.CurveTable`, a class that does not exist
  in the repo, so the file does *not* compile on its own. That is the normal
  case for real code and it is what the transformer exists to fix.
- `com/example/Greeter.java` — **not suitable**. `greet()` has no parameters and
  no operations on them, so the filter should drop it.

## Step 0: configuration

Every stage reads `config.properties` from its invocation directory by default, but
each also accepts `--config=<path>` to point at a different file instead.

```bash
./gradlew filter -Pargs="--config=docs/tutorial/tutorial.properties"
```

We'll point at `docs/tutorial/tutorial.properties` in the `filter`
and `transform` commands below.

The settings that matter here are `type=I` (integer expressions), `minExpr`,
`minIfStmt` and `minParams` (what the filter demands), and `minTypeExpr`,
`minTypeCond` and `minTypeParams` (transform's own, independent thresholds,
applied afterwards).

## Step 1: stage the repo where `download` would leave it

`filter` reads from `database/` and writes to `suitablePrgms/` by default.
These are hard-coded in `filter.Main`, and are gitignored.
We can also point at different directories for the input and output for
the filter and transform stage.

```bash
./gradlew filter -Pargs="<path/to/input> <path/to/output>"
```

Note that this can be combined with the `--config` option but must provide both
an input ad output path, in that order.

For the purpose of this tutorial, we will copy the toy repo into `database/`
and use the default pathing.

```bash
mkdir -p database
cp -r docs/tutorial/toy-repo database/
```

## Step 2: filter

Lets run the filter stage on the toy repo. If you haven't modified `config.properties`
then you do not need to point to the tutorial version at all and can just run:

```bash
./gradlew filter
```

Otherwise you can point to the tutorial version, and even add i/o paths if you would like.

```bash
./gradlew filter -Pargs="--config=docs/tutorial/tutorial.properties"
```

The output should look like this:

```
I 1 1 1
Processing repository: src
Grading.java
	letterCutoff	2	1	2
  Found 1 suitable files out of 2
Filtering complete.
```

The first line echoes `type minExpr minIfStmt minParams`. The indented line is
the suitable method and its counts — expressions, conditionals, parameters.
`Greeter.java` is gone, and `suitablePrgms/` now holds one file:

```
suitablePrgms/toy-repo/src/main/java/com/example/Grading.java
```

Note the original directory structure is preserved; the filter copies suitable
files rather than flattening them.

## Step 3: transform

```bash
./gradlew transform
```

```
INT 1 1 1
Current file name: Grading.java
Oper 2
Cond 2
Par 2
Suitable method letterCutoff
Suitable methods 1 in benchmarks/toy-repo/src/main/java/com/example/Grading.java
Found suitable MDecl
YAML file created successfully.
Moved and renamed: .../Grading.java -> .../Grading/Main.java
```

`Oper`/`Cond`/`Par` are the *post*-transformation counts, checked against
`minTypeExpr`/`minTypeCond`/`minTypeParams` — transform's own suitability check,
independent of filter's.

## Step 4: read the benchmark

```
benchmarks/toy-repo/src/main/java/com/example/Grading.yml
benchmarks/toy-repo/src/main/java/com/example/Grading/Main.java
```

Six distinct things happened, and each maps to a phase in `transform.Transformer`:

| Change | Why |
| --- | --- |
| `package com.example;` dropped | benchmarks are flat, single-class programs |
| `import com.example.missing.CurveTable;` dropped | unresolvable, and `DisallowedMethodAndFieldVisitor` removed everything depending on it |
| the `curve` field dropped | same, its type does not resolve |
| `curve.bonusFor(classSize)` → `Verifier.nondetInt()` | the symbolic-input substitution; an unresolvable `int`-valued call becomes an unconstrained `int` |
| `assert true` on each return path | SV-COMP needs reachable assertions as verification targets |
| class renamed to `Main`, `main()` synthesized, file moved into `Grading/` | SV-COMP expects an entry point that drives the method with symbolic arguments |

`Grading.yml` is the SV-COMP task definition naming the property files and the
expected verdict.

Clean up with `./gradlew reset`, which deletes `build`, `database`,
`suitablePrgms` and `benchmarks`.

## When a file gets discarded

A common outcome is cases that fail the transform:

```
Commenting out letterCutoff
Suitable methods 0 in benchmarks/.../Grading.java
No suitable methods after transformation. Discarding benchmarks/.../Grading.java
```

The transform step applies `minTypeExpr`/`minTypeCond`/`minTypeParams` thresholds.
Even when these are the same as filter's thresholds a method can become unsuitable
during transformation. If no method in the file survives, the whole file is
dropped. Read the `Oper`/`Cond`/`Par` line to see which threshold failed:

Also note: `./gradlew transform` **exits non-zero when no benchmark is
produced**, so a discarded file surfaces as `BUILD FAILED` with exit value 255,
not as a clean "nothing to do". That is expected behavior, not a crash.

Setting `debug=true` in `config.properties` transform section keeps files that
fail the final compile instead of deleting them, which is the fastest way
to see what the transformer actually emitted.

## What this tutorial skips

`download` is not exercised here; it clones from a RepoReaper-style CSV
(`dataset.csv`) and needs network access plus a long runtime. The two stages
above are what you iterate on when changing the tool.

`./gradlew full` is only a task-dependency chain over `download`, `filter` and
`transform`; see the note in the top-level `README.md`.
