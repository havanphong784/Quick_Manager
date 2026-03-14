package com.quickmanager.debug;

public class Address {
    public static void printAddress() {

        StackTraceElement[] stack = Thread.currentThread().getStackTrace();

        // index = 2 là method đang chạy
        StackTraceElement info = stack[2];

        String fullClassName = info.getClassName(); // com.phongha.test.Demo
        String methodName = info.getMethodName();   // inThongTin


        System.out.println(" - Address: " + fullClassName + "." + methodName);
        System.out.println("");
    }

}
