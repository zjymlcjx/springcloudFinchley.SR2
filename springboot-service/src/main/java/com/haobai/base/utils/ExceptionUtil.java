package com.haobai.base.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * 异常信息工具类
 */
public class ExceptionUtil {

    /**
     * 获取异常详细信息以便保存
     */
    public static String getExceptionAllInfo(Exception ex) {
        ByteArrayOutputStream out = null;
        PrintStream pout = null;
        String ret = "";
        try {
            out = new ByteArrayOutputStream();
            pout = new PrintStream(out);
            ex.printStackTrace(pout);
            ret = new String(out.toByteArray());
            out.close();
        } catch (Exception e) {
            return ex.getMessage();
        } finally {
            if (pout != null) {
                pout.close();
            }
        }
        return ret;
    }
}
