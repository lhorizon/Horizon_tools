package com.antu.Onemap.Helper;
 
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;

import android.text.format.DateFormat;
import android.util.Log;
 
/**
 */
public class Loger {
    private static String TAG = "LOGER";
    private static File logFile = new File("/mnt/sdcard/onemap_log.txt");
    private static String fmt = "[%s] ";
    private static String df = "yyyy-MM-dd kk:mm:ss";
    private static FileWriter fw = null;
    private static BufferedWriter bw = null;
    private static StringWriter sw = null;
    private static PrintWriter pw = null;
    private static boolean log = true;
    private static boolean write = true;
 
    static {
        try {
            if (write) {
                if (!logFile.exists()) {
                    logFile.createNewFile();
                }
                if (fw == null) {
                    fw = new FileWriter(logFile, true);
                }
                if (bw == null) {
                    bw = new BufferedWriter(fw);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "初始化日志组建失败 ", e);
            write = false;
        }
    }
 
    public static void write(String logs) {
        String logString = String.format(fmt, DateFormat.format(df, System.currentTimeMillis()).toString()) + logs;
        try {
            bw.write(logString);
            bw.newLine();
            bw.flush();
            fw.flush();
        } catch (Exception e) {
            Log.e(TAG, "write() ", e);
            write = false;
        }
    }
 
    public static void write(String log, Throwable throwable) {
        sw = new StringWriter();
        pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        String error = sw.getBuffer().toString();
        String logString = String.format(fmt, DateFormat.format(df, System.currentTimeMillis()).toString()) + log + "==>" + error;
        try {
            bw.write(logString);
            bw.newLine();
            bw.flush();
            fw.flush();
        } catch (Exception e) {
            Log.e(TAG, "write() error", e);
            write = false;
        } finally {
            try {
                sw.close();
                pw.close();
            } catch (Exception e) {
                Log.e(TAG, "write() error", e);
                write = false;
            }
            sw = null;
            pw = null;
        }
    }
 
    public static void i(String msg) {
        i(TAG, msg);
    }
 
    public static void i(String tag, String msg) {
        if (log) {
            Log.i(tag, msg);
        }
        if (write) {
            write(msg);
        }
    }
 
    public static void v(String msg) {
        v(TAG, msg);
    }
 
    public static void v(String tag, String msg) {
        if (log) {
            Log.v(tag, msg);
        }
        if (write) {
            write(msg);
        }
    }
 
    public static void d(String msg) {
        d(TAG, msg);
    }
 
    public static void d(String tag, String msg) {
        if (log) {
            Log.d(tag, msg);
        }
        if (write) {
            write(msg);
        }
    }
 
    public static void w(String msg) {
        w(TAG, msg);
    }
 
    public static void w(String tag, String msg) {
        if (log) {
            Log.w(tag, msg);
        }
        if (write) {
            write(msg);
        }
    }
 
    public static void e(String msg) {
        e(TAG, msg);
    }
 
    public static void e(String tag, String msg) {
        if (log) {
            Log.e(tag, msg);
        }
        if (write) {
            write(msg);
        }
    }
 
    public static void e(String msg, Throwable e) {
        e(TAG, msg, e);
    }
 
    public static void e(String tag, String msg, Throwable e) {
        if (log) {
            Log.e(tag, msg, e);
        }
        if (write) {
            write(msg, e);
        }
    }
 
    //这个释放资源。若退出程序部在记录和打印日志则调用
    public static void close() {
        try {
            if (fw != null) {
                fw.close();
                fw = null;
            }
            if (bw != null) {
                bw.close();
                bw = null;
            }
            if (sw != null) {
                sw.close();
                sw = null;
            }
            if (pw != null) {
                pw.close();
                pw = null;
            }
        } catch (Exception e) {
            Log.e(TAG, "close() error  ", e);
            write = false;
        }
    }
}