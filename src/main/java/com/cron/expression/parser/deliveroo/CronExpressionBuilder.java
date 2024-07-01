package com.cron.expression.parser.deliveroo;

import com.cron.expression.parser.deliveroo.fieldparser.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CronExpressionBuilder {
    private static final int CRON_FIELDS = 5;
    private String[] expressionFields;

    public CronExpressionBuilder(String[] expressionFields) {
        this.expressionFields = expressionFields;
    }

    public CronExpressionParser build() throws IllegalArgumentException {
        if (expressionFields == null || expressionFields.length <= CRON_FIELDS) {
            throw new IllegalArgumentException("Invalid number of field arguments");
        }

        List<Integer> minuteFieldSet = new MinuteFieldParser().parseField(expressionFields[0]);
        List<Integer> hourFieldSet = new HourFieldParser().parseField(expressionFields[1]);
        List<Integer> dayOfMonthFieldSet = new DayOfMonthFieldParser().parseField(expressionFields[2]);
        List<Integer> monthFieldSet = new MonthFieldParser().parseField(expressionFields[3]);
        List<Integer> dayOfWeekFieldSet = new DayOfWeekFieldParser().parseDayOfWeekField(expressionFields[4]);
        List<String> commandFieldList = new ArrayList<>(expressionFields.length - CRON_FIELDS);

        commandFieldList.addAll(Arrays.asList(expressionFields).subList(CRON_FIELDS, expressionFields.length));

        return new CronExpressionParser(minuteFieldSet, hourFieldSet, dayOfMonthFieldSet, monthFieldSet, dayOfWeekFieldSet, commandFieldList);
    }
}