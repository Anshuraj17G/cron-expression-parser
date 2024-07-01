package com.cron.expression.parser.deliveroo;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class CronExpressionBuilderTest {

    @Test
    public void correctCronExpression_shouldCreateObject_noException() {
        String[] expression = {"*/15", "0", "1,15", "*", "1-5", "/user/bin/find"};
        CronExpressionParser cronExpression = new CronExpressionBuilder(expression).build();
        Assert.assertNotNull(cronExpression);
        Assert.assertEquals(Arrays.asList(0, 15, 30, 45), cronExpression.getMinuteFieldList());
        Assert.assertEquals(Collections.singletonList(0), cronExpression.getHourFieldList());
        Assert.assertEquals(Arrays.asList(1, 15), cronExpression.getDayOfMonthFieldList());
        Assert.assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), cronExpression.getMonthFieldList());
        Assert.assertEquals(Collections.singletonList(0), cronExpression.getHourFieldList());
        Assert.assertEquals(Collections.singletonList("/user/bin/find"), cronExpression.getCommandFieldList());
    }

    @Test
    public void manyArgumentToCommand_shouldCreateObject_noException() {
        String[] expression = {"*/15", "0", "1,15", "*", "1-5", "/user/bin/find", "test1", "test2"};
        CronExpressionParser cronExpression = new CronExpressionBuilder(expression).build();
        Assert.assertNotNull(cronExpression);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyCommandField_shouldThrowException_IllegalArgumentException() {
        String[] expression = {"*/15", "24", "1,15", "*", "1-5"};
        CronExpressionParser cronExpression = new CronExpressionBuilder(expression).build();
        Assert.assertNull(cronExpression);
    }

    @Test(expected = IllegalArgumentException.class)
    public void lessNumberOfArgument_shouldThrowException_IllegalArgumentException() {
        String[] expression = {"*/15", "2", "1,15", "*", "1-5"};
        CronExpressionParser cronExpression = new CronExpressionBuilder(expression).build();
        Assert.assertNull(cronExpression);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullArgument_shouldThrowException_IllegalArgumentException() {
        CronExpressionParser cronExpression = new CronExpressionBuilder(null).build();
        Assert.assertNull(cronExpression);
    }
}