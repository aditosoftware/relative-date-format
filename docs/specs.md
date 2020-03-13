```
relative            = "REL" (relative-frame / relative-adjust / relative-fixed / relative-mixed)

relative-adjust     = 1*("ADJUST" (";" "UNIT" "=" relative-unit))

relative-fixed      = 1*("FIXED" (";" "START" "=" period) / (";" "END" "=" period) / (";" "UNIT" "=" relative-unit))

relative-mixed      = 1*("MIXED" (";" "PERIOD" "=" period) / (";" "UNIT" "=" relative-unit))

relative-unit      = "YEAR" / "MONTH" / "WEEK" / "DAY"
```
