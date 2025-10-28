package io.github.nchaugen.tickets;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser for relative timestamp syntax in the format "T-DdHhMmSs"
 * where D=days, H=hours, M=minutes, S=seconds.
 * <p>
 * Examples:
 * <ul>
 *   <li>T-60d = 60 days ago</li>
 *   <li>T-30d1s = 30 days and 1 second ago</li>
 *   <li>T-30d1h2m3s = 30 days, 1 hour, 2 minutes and 3 seconds ago</li>
 * </ul>
 */
public class RelativeTimestampParser {

    private static final Pattern T_MINUS_SYNTAX = Pattern.compile("T-(\\d*)d?(\\d*)h?(\\d*)m?(\\d*)s?");

    private final LocalDateTime referenceTime;

    public RelativeTimestampParser(LocalDateTime referenceTime) {
        this.referenceTime = referenceTime;
    }

    public LocalDateTime parse(String input) {
        Matcher matcher = T_MINUS_SYNTAX.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid syntax for T-timestamp: " + input);
        }
        return referenceTime
            .minusDays(intValue(matcher, TimeUnit.DAYS))
            .minusHours(intValue(matcher, TimeUnit.HOURS))
            .minusMinutes(intValue(matcher, TimeUnit.MINUTES))
            .minusSeconds(intValue(matcher, TimeUnit.SECONDS));
    }

    private static int intValue(Matcher matcher, TimeUnit unit) {
        String value = matcher.group(unit.group());
        return value.isEmpty() ? 0 : Integer.parseInt(value);
    }

    private enum TimeUnit {
        DAYS, HOURS, MINUTES, SECONDS;

        int group() {
            return ordinal() + 1;
        }
    }
}