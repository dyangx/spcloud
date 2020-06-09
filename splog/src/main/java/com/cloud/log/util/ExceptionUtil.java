package com.cloud.log.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @author: yangjie
 * @date: Created in 2020/6/9 10:47
 */
public class ExceptionUtil {

    public static String writeAsString(Throwable e){
        String exception = null;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            e.printStackTrace(new PrintStream(bos));
            exception = bos.toString();
        } catch (IOException ex) {
        }
        return exception;
    }

    public static void main(String[] args) {
        try {
            "122".substring(10);
        } catch (Throwable e){
            System.out.println(ExceptionUtil.writeAsString(e));
            System.out.println("\n");
            e.printStackTrace();
        }

    }
}
