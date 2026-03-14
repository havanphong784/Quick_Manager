package com.quickmanager.debug;

public class Address {
    public static void printAddress() {

        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        StackTraceElement info = stack[2];
        String fullClassName = info.getClassName();
        String methodName = info.getMethodName();

        System.out.println(" - Address: " + fullClassName + "." + methodName);
        System.out.println("");
    }

}
