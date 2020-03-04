relative            = "REL" (relative-adjust / relative-fixed / relative-mixed)

relative-adjust     = 1*("ADJUST" (";" "SCOPE" "=" relative-adjust-scope))

relative-fixed      = 1*("FIXED" (";" "START" "=" duration) / (";" "END" "=" duration))

relative-mixed      = 1*("MIXED" (";" "DURATION" "=" duration) / (";" "SCOPE" "=" relative-adjust-scope))

relative-adjust-scope =  "YEAR" / "MONTH" / "WEEK" / "DAY"

duration            = (["+"] / "-") "A" (duration-date / duration-time / duration-week)

duration-date       = duration-day [duration-time]

druation-time       = "T" (duration-hour / duration-minute / duration-second)

duration-week       = 1*DIGIT "W"

duration-hour       = 1*DIGIT "H" [duration-minute]

duration-minute     = 1*DIGIT "M" [duration-second]

duration-second     = 1*DIGIT "S"

duration-day        = 1*DIGIT "D"
