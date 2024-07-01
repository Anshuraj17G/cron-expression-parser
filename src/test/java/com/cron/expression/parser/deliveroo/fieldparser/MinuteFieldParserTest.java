package com.cron.expression.parser.deliveroo.fieldparser;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MinuteFieldParserTest {
    FieldParser fieldParser;
    List<Integer> valueList;

    @Before
    public void setup() {
        fieldParser = new MinuteFieldParser();
    }

    @Test
    public void validInput_correctMinAndMax_valueInRange() {
        assertEquals(0, fieldParser.getMin());
        assertEquals(59, fieldParser.getMax());
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
    public void invalidInput_numberBelowMin_shouldThrowException() {
        fieldParser.parseField("-1");
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
        assertEquals(valueList, fieldParser.parseField("0-59"));
    }

    @Test
    public void validInput_onlyStep_valueMatch() {
        valueList = Arrays.asList(1, 11, 21, 31, 41, 51);
        assertEquals(valueList, fieldParser.parseField("1/10"));
    }

    @Test
    public void validInput_onlyStepWithAsterisk_valueMatch() {
        valueList = Arrays.asList(0, 20, 40);
        assertEquals(valueList, fieldParser.parseField("*/20"));
    }

    @Test
    public void validInput_onlyList_valueMatch() {
        valueList = Arrays.asList(0, 4, 59);
        assertEquals(valueList, fieldParser.parseField("0,4,59"));
    }

    @Test
    public void validInput_listRangeAndStep_valueMatch() {
        valueList = Arrays.asList(0, 4, 10, 11, 12, 0, 10, 20, 30, 40, 50, 51, 52, 53, 20, 40);
        assertEquals(valueList, fieldParser.parseField("0,4,10-12,0/10,51-53,20/20"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_extraAsterisk_shouldThrowException() {
        fieldParser.parseField("*,4,10-12,*/10,51-53,20/20");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_withAsteriskInListField_shouldThrowException() {
        fieldParser.parseField("*,4,10-12,0/10,51-53,20/20");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_invalidLessThanMinInRangeField_shouldThrowException() {
        fieldParser.parseField("-1-40");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_invalidLessThanMinInStepField_shouldThrowException() {
        fieldParser.parseField("-1/3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_divideByZeroInStepField_shouldThrowException() {
        fieldParser.parseField("30/0");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_negativeStepInStepField_shouldThrowException() {
        fieldParser.parseField("30/-1");
    }

    @Test
    public void validInput_moreThanMaxStepStepInStepField_shouldThrowException() {
        valueList = Arrays.asList(5);
        assertEquals(valueList, fieldParser.parseField("5/100"));
    }
}