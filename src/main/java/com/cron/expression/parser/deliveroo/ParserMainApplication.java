package com.cron.expression.parser.deliveroo;

public class ParserMainApplication {
    public static void main(String[] args) {
        try {
            CronExpressionParser cronExpression = new CronExpressionBuilder(args).build();
            cronExpression.printCronExpression();
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: Unable to parse expression due to " + ex.getMessage());
            System.exit(1);
        }
    }
}
