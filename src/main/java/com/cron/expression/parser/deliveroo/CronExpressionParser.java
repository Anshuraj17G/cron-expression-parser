package com.cron.expression.parser.deliveroo;

import java.util.List;
import java.util.stream.Collectors;

public class CronExpressionParser {

    private List<Integer> minuteFieldList;
    private List<Integer> hourFieldList;
    private List<Integer> dayOfMonthFieldList;
    private List<Integer> monthFieldList;
    private List<Integer> dayOfWeekFieldList;
    private List<String> commandFieldList;

    public CronExpressionParser(List<Integer> minuteFieldSet, List<Integer> hourFieldSet, List<Integer> dayOfMonthFieldSet,
                                List<Integer> monthFieldSet, List<Integer> dayOfWeekFieldSet, List<String> commandFieldList) {
        this.minuteFieldList = minuteFieldSet;
        this.hourFieldList = hourFieldSet;
        this.dayOfMonthFieldList = dayOfMonthFieldSet;
        this.monthFieldList = monthFieldSet;
        this.dayOfWeekFieldList = dayOfWeekFieldSet;
        this.commandFieldList = commandFieldList;
    }

    private CronExpressionParser() {}

    public void printCronExpression() {
        System.out.printf("%-14s%s%n", "minute", minuteFieldList.stream().map(String::valueOf)
                .collect(Collectors.joining(" ")));
        System.out.printf("%-14s%s%n", "hour", hourFieldList.stream().map(String::valueOf)
                .collect(Collectors.joining(" ")));
        System.out.printf("%-14s%s%n", "day of month", dayOfMonthFieldList.stream().map(String::valueOf)
                .collect(Collectors.joining(" ")));
        System.out.printf("%-14s%s%n", "month" , monthFieldList.stream().map(String::valueOf)
                .collect(Collectors.joining(" ")));
        System.out.printf("%-14s%s%n", "day of week", dayOfWeekFieldList.stream().map(String::valueOf)
                .collect(Collectors.joining(" ")));
        System.out.printf("%-14s%s%n", "command", commandFieldList.stream().map(String::valueOf)
                .collect(Collectors.joining(" ")));
    }

    public List<Integer> getMinuteFieldList() {
        return minuteFieldList;
    }

    public void setMinuteFieldList(List<Integer> minuteFieldList) {
        this.minuteFieldList = minuteFieldList;
    }

    public List<Integer> getHourFieldList() {
        return hourFieldList;
    }

    public void setHourFieldList(List<Integer> hourFieldList) {
        this.hourFieldList = hourFieldList;
    }

    public List<Integer> getDayOfMonthFieldList() {
        return dayOfMonthFieldList;
    }

    public void setDayOfMonthFieldList(List<Integer> dayOfMonthFieldList) {
        this.dayOfMonthFieldList = dayOfMonthFieldList;
    }

    public List<Integer> getMonthFieldList() {
        return monthFieldList;
    }

    public void setMonthFieldList(List<Integer> monthFieldList) {
        this.monthFieldList = monthFieldList;
    }

    public List<Integer> getDayOfWeekFieldList() {
        return dayOfWeekFieldList;
    }

    public void setDayOfWeekFieldList(List<Integer> dayOfWeekFieldList) {
        this.dayOfWeekFieldList = dayOfWeekFieldList;
    }

    public List<String> getCommandFieldList() {
        return commandFieldList;
    }

    public void setCommandFieldList(List<String> commandFieldList) {
        this.commandFieldList = commandFieldList;
    }
}
