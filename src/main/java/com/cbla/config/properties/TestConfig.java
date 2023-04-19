package com.cbla.config.properties;

public class TestConfig {

    public static final long NORMAL_WAIT = 10l;
    public static final long LONG_WAIT = 60l;

    public static final long SHORT_WAIT = 1l;
    public static final long VIBRANT_ADS_WAIT = 3l;
    //Mail message configurations
    public static String server = "domain name";
    public static String from = "email address";
    public static String user = "";
    public static String password = "";
    public static String subject = "Test Execution Complete for ";


    //MYSQL DATABASE DETAILS
    //public static String mysqldriver="com.mysql.jdbc.Driver";
    public static String successSubject = "Success! ";
    public static String failedSubject = "Failed! ";
    public static String messageBodyPt1 = "Test Execution Complete for ";
    public static String messageBodyPt2 = "Please verify attached report.";
}
