package com.cron.expression.parser.deliveroo.fieldparser;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MonthFieldParserTest {
    FieldParser fieldParser;
    List<Integer> valueList;

    @Before
    public void setup() {
        fieldParser = new MonthFieldParser();
    }

    @Test
    public void validInput_correctMinAndMax_valueInRange() {
        assertEquals(1, fieldParser.getMin());
        assertEquals(12, fieldParser.getMax());
    }

    @Test
    public void validInput_onlyAsterisk_allValues() {
        valueList = new ArrayList<>();
        for(int i = fieldParser.getMin(); i <= fieldParser.getMax(); i++)
            valueList.add(i);
        assertEquals(valueList, fieldParser.parseField("*"));
    }

    @Test
    public void validInput_onlyNumber_valueMatch() {
        valueList = Arrays.asList(5);
        assertEquals(valueList, fieldParser.parseField("5"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_badNumberFormat_shouldThrowException() {
        fieldParser.parseField("A");
    }

    @Test
    public void validInput_onlyRange_valueMatch() {
        valueList = new ArrayList<>();
        for(int i = fieldParser.getMin(); i <= fieldParser.getMax(); i++)
            valueList.add(i);
        assertEquals(valueList, fieldParser.parseField("1-12"));
    }

    @Test
    public void validInput_onlyStep_valueMatch() {
        valueList = Arrays.asList(1, 11);
        assertEquals(valueList, fieldParser.parseField("1/10"));
    }

    @Test
    public void validInput_onlyStepWithAsterisk_valueMatch() {
        valueList = Arrays.asList(1, 5, 9);
        assertEquals(valueList, fieldParser.parseField("*/4"));
    }

    @Test
    public void validInput_onlyList_valueMatch() {
        valueList = Arrays.asList(1, 4, 12);
        assertEquals(valueList, fieldParser.parseField("1,4,12"));
    }

    @Test
    public void validInput_listRangeAndStep_valueMatch() {
        valueList = Arrays.asList(1, 4, 10, 11, 12, 1, 11, 5, 6, 7, 9);
        assertEquals(valueList, fieldParser.parseField("1,4,10-12,1/10,5-7,9/20"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_extraAsterisk_shouldThrowException() {
        fieldParser.parseField("*,4,10-12,1/10,5-7,*/20");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_withAsteriskInListField_shouldThrowException() {
        fieldParser.parseField("*,4,10-12,1/10,5-7,1/20");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_divideByZeroInStepField_shouldThrowException() {
        fieldParser.parseField("2/0");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_negativeStepInStepField_shouldThrowException() {
        fieldParser.parseField("2/-1");
    }

    @Test
    public void validInput_moreThanMaxStepStepInStepField_shouldThrowException() {
        valueList = Arrays.asList(5);
        assertEquals(valueList, fieldParser.parseField("5/100"));
    }
}