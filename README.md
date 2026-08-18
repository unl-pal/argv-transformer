# ARG-V Automated Program Transformations

[![Build and Test](https://img.shields.io/github/actions/workflow/status/unl-pal/argv-transformer/buildAndRun.yml?branch=develop&label=build)](https://github.com/unl-pal/argv-transformer/actions/workflows/buildAndRun.yml)
[![Latest release](https://img.shields.io/github/v/release/unl-pal/argv-transformer?include_prereleases)](https://github.com/unl-pal/argv-transformer/releases)
[![License: Apache 2.0](https://img.shields.io/badge/license-Apache%202.0-blue)](LICENSE)
[![Java 8](https://img.shields.io/badge/runtime-Java%208%20JDK-orange)](#requirements)

## About

Welcome to ARG-V Automated Program Transformations.
This application automatically downloads, filters and transforms open source repositories into benchmarks for static analysis tools.

## Requirements

**A Java 8 JDK is required to _run_ ARG-V, not just to build it**

`build.gradle` declares a Java 8 toolchain, so Gradle will automatically find and use a JDK 8
already on your machine. Install a Java 8 JDK yourself, if necessary, and
Gradle should pick it up automatically. If auto-detection doesn't find it (e.g. it's installed
somewhere non-standard), create a `gradle.properties` file in the repo root (there isn't one by
default) and point at it explicitly:

```properties
org.gradle.java.installations.paths=/path/to/jdk8
```

## Setting Up the Project

Clone the repository, and run the gradle wrapper with no arguments:

```bash
git clone git@github.com:unl-pal/argv-transformer.git
./gradlew
```

## Running the Project

Run `./gradlew <task>` with any of the four pipeline tasks below. `filter` and `transform`
accept optional input/output arguments to point them somewhere other than their hard-coded
defaults; pass them through Gradle with `-Pargs"`:

```bash
./gradlew transform -Pargs="input/path output/path"
```

- `download` - clones repos using the `csv` defined in `config.properties`'s into `downloadDir` (default `database`). Takes no input/output args (`downloadDir` in `config.properties` is the only way to redirect its output).
- `filter` - filters Java files/methods based on `config.properties`: `database` → `suitablePrgms`
- `transform` - rewrites suitable Java files into compilable benchmarks: `suitablePrgms` → `benchmarks`
- `full` - runs `download`, then `filter`, then `transform`, chained through their hard-coded
  defaults (`database` → `suitablePrgms` → `benchmarks`). `full` itself is just a Gradle
  `dependsOn` chain, and will not handle input/output args.

`./gradlew tasks`, lists every available task with a description.

## Pipeline Stages

The tool takes a CSV of GitHub repository URLs as input (obtainable from RepoReaper, the BOA
tool, or similar) and turns matching repos into compilable benchmarks in three stages:

1. **`download`** clones repos from the CSV that meet `projectCount`/`minLoc`/`maxLoc` into `database`.
2. **`filter`** walks `database` and keeps only the `.java` files with at least one method
   meeting the configured suitability thresholds (parameter/expression counts, types, etc.),
   copying them into `suitablePrgms`.
3. **`transform`** rewrites each suitable file into a compilable benchmark in `benchmarks`,
   and applies its own, independent suitability checks. Each surviving file is renamed to `Main.java`,
   moved into a directory named after its original class, and paired with a `.yml` SV-COMP task definition.

For a worked example of `filter` → `transform`, see
[`docs/tutorial/README.md`](docs/tutorial/README.md).

### Configuring a run

Every dial above: which expression types count, how many are required, LOC bounds,
and so on, is a property in **`config.properties`**.

For example:

```properties
# Only count integer-typed expressions/parameters, and require at least 2 per method.
type=I
minExpr=2
```

By default, `filter`'s thresholds (`minExpr`/`minIfStmt`/`minParams`) are checked per method: a
file is kept if any single method clears the bar on its own. Setting `simplifyFilter=true`
switches to a file-wide mode instead, summing those counts across every method in the class
and, currently, ignoring the `type` dial entirely while doing so (it counts expressions/
conditionals/parameters of any type, not just `type`'s).

By default each stage looks for `config.properties` in the directory it's invoked from. Point
it at a different file with `--config=<path>`:

```bash
./gradlew transform -Pargs="--config=/path/to/config.properties"
```

Input/output args are for `filter`/`transform` only and must both be provided.
They can be provided in anyway along with the `--config` argument as long as input precedes output.
