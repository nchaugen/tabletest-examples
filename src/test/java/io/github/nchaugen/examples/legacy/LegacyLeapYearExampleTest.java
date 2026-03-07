package io.github.nchaugen.examples.legacy;

import io.github.nchaugen.tabletest.junit.TableTest;

import java.time.Year;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LegacyLeapYearExampleTest {

    @TableTest("""
        Scenario                              | Example years      | Is leap year?
        years not divisible by 4              | {2001, 2002, 2003} | false
        years divisible by 4                  | {2004, 2008, 2012} | true
        years divisible by 100 but not by 400 | {2100, 2200, 2300} | false
        years divisible by 400                | {2000, 2400, 2800} | true
        """)
    void shouldDetermineLeapYear(Year year, boolean expectedResult) {
        assertEquals(expectedResult, year.isLeap(), "Year " + year);
    }
}
