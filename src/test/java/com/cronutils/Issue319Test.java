package com.cronutils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import org.junit.Ignore;
import org.junit.Test;

import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.model.time.generator.NoSuchValueException;
import com.cronutils.parser.CronParser;

public class Issue319Test {
    @Test
    // Daylightsaving change in EU is - 2018-03-25T02:00
    // - Bug319: endless loop/fails/hangs at 2018-03-25T02:00 and 2018-03-26T02:00
    public void testPreviousClosestMatchDailightSavingsChangeBug319_loop() throws NoSuchValueException {
        CronParser cronparser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.UNIX));
        ZonedDateTime date = ZonedDateTime.of(2018, 3, 25, 2, 0, 0, 0, ZoneId.of("Europe/Berlin"));
        Cron cron = cronparser.parse("0 2 * * *");
        ExecutionTime exectime = ExecutionTime.forCron(cron);
        exectime.lastExecution(date).get();
    }

    @Test
    public void testIsMatchDailightSavingsChangeBug319_loop() throws NoSuchValueException {
        CronParser cronparser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.UNIX));
        ZonedDateTime date = ZonedDateTime.of(2018, 8, 12, 2, 0, 0, 0, ZoneId.of("America/Santiago"));
        Cron cron = cronparser.parse("0 6 * * *");
        ExecutionTime exectime = ExecutionTime.forCron(cron);
        exectime.isMatch(date);
    }
}
