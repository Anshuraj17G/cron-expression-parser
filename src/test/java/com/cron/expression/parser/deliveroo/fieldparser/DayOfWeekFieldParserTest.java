package com.cron.expression.parser.deliveroo.fieldparser;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class DayOfWeekFieldParserTest {
    FieldParser fieldParser;
    List<Integer> valueList;

    @Before
    public void setup() {
        fieldParser = new DayOfWeekFieldParser();
    }

    @Test
    public void validInput_correctMinAndMax_valueInRange() {
        assertEquals(0, fieldParser.getMin());
        assertEquals(6, fieldParser.getMax());
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
        valueList = Collections.singletonList(5);
        assertEquals(valueList, fieldParser.parseField("5"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_numberBelowMin_shouldThrowException() {
        fieldParser.parseField("-1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_badNumberFormat_shouldThrowException() {
        fieldParser.parseField("0A");
    }

    @Test
    public void validInput_onlyRange_valueMatch() {
        valueList = new ArrayList<>();
        for(int i = fieldParser.getMin(); i <= fieldParser.getMax(); i++)
            valueList.add(i);
        assertEquals(valueList, fieldParser.parseField("0-6"));
    }

    @Test
    public void validInput_onlyStep_valueMatch() {
        valueList = Arrays.asList(1,3,5);
        assertEquals(valueList, fieldParser.parseField("1/2"));
    }

    @Test
    public void validInput_onlyStepWithAsterisk_valueMatch() {
        valueList = Arrays.asList(0, 3, 6);
        assertEquals(valueList, fieldParser.parseField("*/3"));
    }

    @Test
    public void validInput_onlyList_valueMatch() {
        valueList = Arrays.asList(1, 4, 5);
        assertEquals(valueList, fieldParser.parseField("1,4,5"));
    }

    @Test
    public void validInput_listRangeAndStep_valueMatch() {
        valueList = Arrays.asList(0, 1, 2, 3, 1, 5, 1, 6);
        assertEquals(valueList, fieldParser.parseField("0,1,2-3,1/10,5-5,1/5"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_extraAsterisk_shouldThrowException() {
        fieldParser.parseField("*,1,2-3,1/10,5-5,*/5");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_withAsteriskInListField_shouldThrowException() {
        fieldParser.parseField("*,1,2-3,1/10,5-5,1/5");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_invalidLessThanMinInRangeField_shouldThrowException() {
        fieldParser.parseField("-1-6");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_invalidLessThanMinInStepField_shouldThrowException() {
        fieldParser.parseField("-1/3");
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
        valueList = Collections.singletonList(5);
        assertEquals(valueList, fieldParser.parseField("5/100"));
    }
}