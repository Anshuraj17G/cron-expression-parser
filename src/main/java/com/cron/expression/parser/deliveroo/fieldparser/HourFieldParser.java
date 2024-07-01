package com.cron.expression.parser.deliveroo.fieldparser;

public class HourFieldParser extends FieldParser {
    @Override
    protected int getMin() {
        return 0;
    }

    @Override
    protected int getMax() {
        return 23;
    }
}
