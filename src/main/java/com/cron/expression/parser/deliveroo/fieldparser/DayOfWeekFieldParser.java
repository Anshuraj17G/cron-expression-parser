package com.cron.expression.parser.deliveroo.fieldparser;

import java.util.ArrayList;
import java.util.List;

public class DayOfWeekFieldParser extends FieldParser {
    @Override
    protected int getMin() {
        return 0;
    }

    @Override
    protected int getMax() {
        return 6;
    }


    public List<Integer> parseDayOfWeekField(String field) {
        List<Integer> values = new ArrayList<>();

        if (field.equals("*")) {
            for (int i = 0; i <= 6; i++) {
                values.add(i);
            }
        } else if (field.contains(",")) {
            String[] parts = field.split(",");
            for (String part : parts) {
                values.addAll(parseDayOfWeekPart(part));
            }
        } else {
            values.addAll(parseDayOfWeekPart(field));
        }
        return values;
    }

    private List<Integer> parseDayOfWeekPart(String part){
        List<Integer> values = new ArrayList<>();
        if(part.contains("-")){
            String[] inc = part.split("-");
            int start = parseDayOfWeekValue(inc[0]);
            int end = parseDayOfWeekValue(inc[1]);
            for(int i=start; i<=end; i++){
                values.add(i);
            }
        }
        else{
            values.add(parseDayOfWeekValue(part));
        }
        return values;
    }

    private static int parseDayOfWeekValue(String part) {
        part = part.toUpperCase();
        switch (part) {
            case "SUN":
                return 0;
            case "MON":
                return 1;
            case "TUE":
                return 2;
            case "WED":
                return 3;
            case "THU":
                return 4;
            case "FRI":
                return 5;
            case "SAT":
                return 6;
            default:
                throw new IllegalArgumentException("Invalid day of week: " + part);
        }
    }
}
