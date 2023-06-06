[//]: # (title: Time)

The Kotlin standard library gives you the tools you need to be able to calculate and measure time in different units.
Being able to measure time accurately is important for activities like:
  * Managing threads or processes
  * Collecting statistics
  * Detecting timeouts
  * Debugging

By default, time is measured using a monotonic time source but other time sources can be configured.

## Durations

To represent an amount of time, the standard library has the [`Duration`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-duration/)
class. A `Duration` can be expressed in the following units from the [`DurationUnit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-duration-unit/)
enum class:
  * `NANOSECONDS`
  * `MICROSECONDS`
  * `MILLISECONDS`
  * `SECONDS`
  * `MINUTES`
  * `HOURS`
  * `DAYS`

`Durations` can also be zero, negative or infinite.

### Create duration

To create a `Duration`, use the [extension properties](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-duration/#companion-object-properties) 
available for `Int`, `Long`, and `Double` types: `nanoseconds`, `microseconds`, `milliseconds`, `seconds`, `minutes`, 
`hours`, and `days`.

> Days refer to periods of 24 hours. They are not calendar days.
> 
{type = "tip"}

For example:

```kotlin
import kotlin.time.*
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.Duration.Companion.minutes

fun main() {
    //sampleStart
    val fiveHundredMilliseconds: Duration = 500.milliseconds
    val fiveSeconds: Duration = 5.seconds
    val tenMinutes: Duration = 10.minutes

    println(fiveHundredMilliseconds) // 500ms
    println(fiveSeconds)             // 5s
    println(tenMinutes)              // 10m
    //sampleEnd
}
```
{kotlin-runnable="true" kotlin-min-compiler-version="1.3"}

You can also perform basic arithmetic with `Durations`:

```kotlin
import kotlin.time.*
import kotlin.time.Duration.Companion.seconds

fun main() {
    //sampleStart
    val fiveSeconds: Duration = 5.seconds
    val thirtySeconds: Duration = 30.seconds

    println(fiveSeconds + thirtySeconds)
    // 35s
    println(thirtySeconds -  fiveSeconds)
    // 25s
    println(fiveSeconds * 2)
    // 10s
    println(thirtySeconds / 2)
    // 15s
    println(thirtySeconds / fiveSeconds)
    // 6.0
    println(-thirtySeconds)
    // -30s
    println((-thirtySeconds).absoluteValue)
    // 30s
    //sampleEnd
}
```
{kotlin-runnable="true" kotlin-min-compiler-version="1.3"}

### Get string representation

It can be useful to have a string representation of a `Duration` so that you can print, serialize, transfer, or store it.

To get a string representation, use the `.toString()` function. By default, the time is reported using each unit that is
present. For example: `1h 0m 45.677s` or `-(6d 5h 5m 28.284s)`

To configure the output, use the `.toString()` function with your desired `Duration.Unit` and number of decimal places
as function parameters:

```kotlin
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

fun main() {
    //sampleStart
    // Print in seconds with 2 decimal places
    println(5887.milliseconds.toString(DurationUnit.SECONDS, 2))
    // 5.89s
    //sampleEnd
}
```
{kotlin-runnable="true" kotlin-min-compiler-version="1.3"}

To print the `Duration` as an [ISO-8601-compatible](https://en.wikipedia.org/wiki/ISO_8601) string, use the [`toIsoString()`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-duration/to-iso-string.html)
function:

```kotlin
import kotlin.time.Duration.Companion.seconds

fun main() {
    //sampleStart
    println(86420.seconds.toIsoString()) // PT24H0M20S
    //sampleEnd
}
```
{kotlin-runnable="true" kotlin-min-compiler-version="1.3"}

### Convert duration

To convert your `Duration` into a different `Duration.Unit`, you can use the properties:
* `inWholeNanoseconds`
* `inWholeMicroseconds`
* `inWholeSeconds`
* `inWholeMinutes`
* `inWholeHours`
* `inWholeDays`

For example:

```kotlin
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

fun main() {
    //sampleStart
    val thirtyMinutes: Duration = 30.minutes
    println(thirtyMinutes.inWholeSeconds)
    // 1800
    //sampleEnd
}
```
{kotlin-runnable="true" kotlin-min-compiler-version="1.3"}

Alternatively, you can use your desired `Duration.Unit` as a function parameter in the following extension functions:
* `toInt()`
* `toDouble()`
* `toLong()`

For example:

```kotlin
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

fun main() {
    //sampleStart
    println(270.seconds.toDouble(DurationUnit.MINUTES))
    // 4.5
    //sampleEnd
}
```
{kotlin-runnable="true" kotlin-min-compiler-version="1.3"}

### Compare duration

To check if `Durations` are equal, use the equality operator (`==`):

```kotlin
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

fun main() {
    //sampleStart
    val thirtyMinutes: Duration = 30.minutes
    val halfHour: Duration = 0.5.hours
    println(thirtyMinutes == halfHour)
    // true
    //sampleEnd
}
```
{kotlin-runnable="true" kotlin-min-compiler-version="1.3"}

To compare `Durations`, use the comparison operators (`<`, `>`):

```kotlin
import kotlin.time.Duration.Companion.microseconds
import kotlin.time.Duration.Companion.nanoseconds

fun main() {
    //sampleStart
    println(3000.microseconds < 25000.nanoseconds)
    // false
    //sampleEnd
}
```
{kotlin-runnable="true" kotlin-min-compiler-version="1.3"}

### Break duration into components

To break down a `Duration` into its time components and perform a further action, use the overload of the 
[`toComponents()`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-duration/to-components.html) function. 
Add your desired action as a function or lambda expression as a function parameter.

For example:

```kotlin
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

fun main() {
    //sampleStart
    val thirtyMinutes: Duration = 30.minutes
    println(thirtyMinutes.toComponents { hours, minutes, _, _ -> "${hours}h:${minutes}m" })
    // 0h:30m
    //sampleEnd
}
```
{kotlin-runnable="true" kotlin-min-compiler-version="1.3"}

In this example, the lambda expression has `hours` and `minutes` as function parameters with underscores (`_`) for the 
unused `seconds` and `nanoseconds` parameters. The expression returns a concatenated string using [string templates](strings.md#string-templates) 
to get the desired output format of `hours` and `minutes`.

## Time measurement

To track the passage of time, the standard library provides tools so that you can easily:
* Measure the time taken to execute some code with your desired time unit.
* Mark a moment in time.
* Compare and subtract two moments in time.
* Check how much time has passed since a specific moment in time.
* Check whether the current time has passed a specific moment in time.

### Measure code execution time

To measure the time taken to execute a block of code, use the [`measureTime`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/measure-time.html)
inline function:

```kotlin
import kotlin.time.measureTime

fun main() {
    //sampleStart
    val timeTaken = measureTime {
        Thread.sleep(100)
    }
    println(timeTaken) // e.g. 103 ms
    //sampleEnd
}
```
{kotlin-runnable="true" kotlin-min-compiler-version="1.3"}

To measure the time taken to execute a block of code **and** the elapsed time, use inline function [`measureTimedValue`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/measure-time.html).

For example:

```kotlin
import kotlin.time.measureTimedValue

fun main() {
    //sampleStart
    val (value, timeTaken) = measureTimedValue {
        Thread.sleep(100)
        42
    }
    println(value)     // 42
    println(timeTaken) // e.g. 103 ms
    //sampleEnd
}
```
{kotlin-runnable="true" kotlin-min-compiler-version="1.3"}

By default, both functions use a monotonic time source.

### Mark moments in time

To mark a specific moment in time, use the [`TimeSource`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-time-source/)
interface and the [`markNow()`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-time-source/mark-now.html) function
to create a [`TimeMark`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-time-mark/):

```kotlin
import kotlin.time.*
fun main() {
//sampleStart
   val timeSource = TimeSource.Monotonic
   val mark = timeSource.markNow()
//sampleEnd
}
```
{kotlin-runnable="true" kotlin-min-compiler-version="1.3"}

### Measure differences in time

To measure differences between `TimeMarks` from the same time source, use the subtraction operator (`-`).

To compare `TimeMarks` from the same time source, use the comparison operators (`<`, `>`).

For example:

```kotlin
import kotlin.time.*
fun main() {
//sampleStart
   val timeSource = TimeSource.Monotonic
   val mark1 = timeSource.markNow()
   Thread.sleep(500) // Sleep 0.5 seconds.
   val mark2 = timeSource.markNow()

   repeat(4) { n ->
       val mark3 = timeSource.markNow()
       val elapsed1 = mark3 - mark1
       val elapsed2 = mark3 - mark2

       println("Measurement 1.${n + 1}: elapsed1=$elapsed1, elapsed2=$elapsed2, diff=${elapsed1 - elapsed2}")
   }
   
   println(mark2 > mark1) // This is true, as mark2 was captured later than mark1.
   // true
//sampleEnd
}
```
{kotlin-runnable="true" kotlin-min-compiler-version="1.3"}

To check if a deadline has passed or a timeout has been reached, use the [`hasPassedNow()`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-time-mark/has-passed-now.html)
and [`hasNotPassedNow()`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-time-mark/has-not-passed-now.html) 
extension functions:

```kotlin
import kotlin.time.*
import kotlin.time.Duration.Companion.seconds

fun main() {
//sampleStart
   val timeSource = TimeSource.Monotonic
   val mark1 = timeSource.markNow()
   val fiveSeconds: Duration = 5.seconds
   val mark2 = mark1 + fiveSeconds

   // It hasn't been 5 seconds yet
   println(mark2.hasPassedNow())
   // false
  
   // Wait six seconds
   Thread.sleep(6000)
   println(mark2.hasPassedNow())
   // true

//sampleEnd
}
```
{kotlin-runnable="true" kotlin-min-compiler-version="1.3"}

## Monotonic time sources

This table explains the default source of monotonic time for each platform:

| Platform            | Source |
|---------------------|---|
| Kotlin/JVM          | `System.nanoTime()`|
| Kotlin/JS (Node.js) | `process.hrtime()`|
| Kotlin/JS (Browser) | `window.performance.now()` or `Date.now()`|
| Kotlin/Native| `std::chrono::high_resolution_clock` or `std::chrono::steady_clock`|

For more information about the `kotlin.time` package, see our [standard library API reference](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/).
