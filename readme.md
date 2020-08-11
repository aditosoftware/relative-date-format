# Relative Date Format
![Gradle Build](https://github.com/aditosoftware/relative-date-format/workflows/Gradle%20Build/badge.svg)

This library provides a format which allows you to express relative date timespans.
The reference implementation is written in Java.

## Usage
As the reference implementation is written in Java, here are some usage examples in Java:

```java
// Given the following input: "REL=ADJUSTED;UNIT=YEAR"

// Tokenize the string input into an IExpression:
IExpression expression = RelativeDateTokenizer.get().tokenize("REL=ADJUSTED;UNIT=YEAR");

// Calculate the timespan relative to now (assume now is "2020-03-16T12:00:00"):
RelativeDateResult result = RelativeDateEngine.get().resolve(expression, LocalDateTime.now());

// Calculate the timespan relativ to now with a custom first day of week:
IExpression weekExpression = RelativeDateTokenizer.get().tokenize("REL=ADJUSTED;UNIT=WEEK");
RelativeDateResult result = RelativeDateEngine.get().resolve(weekExpression, LocalDateTime.now(),
            new RelativeDateEngineProperties(DayOfWeek.SUNDAY));

// The result of the calculation:
result.getStart(); // Will print "2020-01-01T00:00:00"
result.getEnd(); // Will print "2020-12-31T23:59:59"
```  


## Format
The format currently offers 2 different modes:

### Adjusted
With the adjusted mode, it's possible to get the timespan of a year, month, week and day. 

Example: `REL=ADJUSTED;UNIT=YEAR`

The following UNIT values are possible: `YEAR`, `MONTH`, `WEEK`, `DAY`

### Fixed
With the fixed mode, it's possible to define the start and the end as a period.
This means you can express for example a timespan for the last 2 weeks relative to a given date.

Example 1: `REL=FIXED;START=P-14D` (Last 2 weeks)

Example 2: `REL=FIXED;START=P-14D;END=P14D` (Last 2 weeks until next 2 weeks)
