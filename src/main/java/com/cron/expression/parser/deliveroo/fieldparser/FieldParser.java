package com.cron.expression.parser.deliveroo.fieldparser;

import java.util.ArrayList;
import java.util.List;

abstract public class FieldParser {
    protected static final String ALL_CHAR = "*";
    protected static final String RANGE_CHAR = "-";
    protected static final String STEP_CHAR = "/";
    protected static final String LIST_CHAR = ",";

    /**
     * This function parse single filed (minute, hour, doy of week, month, and day of month) of cron expression.
     *
     * @param fieldExpression filed expression may be including special chars
     * @return Set of allowed values on which cron may trigger
     * @throws IllegalArgumentException In case of illegal input
     */
    public List<Integer> parseField(String fieldExpression) throws IllegalArgumentException {
        if (fieldExpression == null || fieldExpression.length() == 0) {
            throw new IllegalArgumentException("Field expression cannot be null or empty");
        }
        List<Integer> parsedResult = new ArrayList<>();
        if (fieldExpression.length() == 1 && fieldExpression.contains(ALL_CHAR)) {
            parseAllNumbers(parsedResult);
            return parsedResult;
        }

        // Split expression with LIST_CHAR to handle list at beginning
        String[] subExpressions = fieldExpression.split(LIST_CHAR);
        for (String subExpression : subExpressions) {
            if (subExpression.contains(RANGE_CHAR)) {
                parseRange(subExpression, parsedResult);
            } else if (subExpression.contains(STEP_CHAR)) {
                parseStep(subExpression, parsedResult);
            } else {
                parseNumber(subExpression, parsedResult);
            }
        }
        return parsedResult;
    }

    // Parse range expression
    protected void parseRange(String range, List<Integer> numberList) throws IllegalArgumentException {
        String[] rangeNumbers = range.split(RANGE_CHAR);
        if (rangeNumbers.length != 2) {
            throw new IllegalArgumentException("Illegal range field");
        }
        int start = parseNumber(rangeNumbers[0]);
        int end = parseNumber(rangeNumbers[1]);
        for (int i = start; i <= end; i++) {
            numberList.add(i);
        }
    }

    // Parse step expression
    protected void parseStep(String stepStr, List<Integer> numberList) throws IllegalArgumentException {
        String[] stepNumbers = stepStr.split(STEP_CHAR);
        if (stepNumbers.length != 2) {
            throw new IllegalArgumentException("Illegal step field");
        }
        int start = getMin();
        if (!(stepNumbers[0].length() == 1 && stepNumbers[0].contains(ALL_CHAR))) {
            start = parseNumber(stepNumbers[0]);
        }
        int step = parseNumber(stepNumbers[1]);
        if (step == 0) {
            throw new IllegalArgumentException("Divide by zero, invalid step value");
        }
        for (int i = start; i <= getMax(); i += step) {
            numberList.add(i);
        }
    }

    // To get all the allowed number due to asterisk
    protected void parseAllNumbers(List<Integer> numberList) {
        for (int i = getMin(); i <= getMax(); i++) {
            numberList.add(i);
        }
    }

    // Parse number and add to number list
    protected void parseNumber(String numStr, List<Integer> numberList) throws IllegalArgumentException {
        numberList.add(parseNumber(numStr));
    }

    // Parse number according to number type
    protected int parseNumber(String numStr) throws IllegalArgumentException {
        try {
            return Integer.parseInt(numStr);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    protected abstract int getMin();

    protected abstract int getMax();

}
