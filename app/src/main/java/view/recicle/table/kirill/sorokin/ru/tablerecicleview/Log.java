package view.recicle.table.kirill.sorokin.ru.tablerecicleview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.Arrays;

/**
 * Created by kirill on 15.06.16.
 */
public final class Log {
    public static String TAG = "tablerecicleview";
    static int magicNumber = -1;

    private static final int MAX_SIZE_LOG = 4000; //исследование показало, что максимум 4096 символов

    private enum TypeLog {
        VERBOSE,
        DEBUG,
        INFO,
        WARN,
        ERROR
    }

    public static String intentToString(Intent intent) {
        if (intent == null) {
            return null;
        }

        return intent.toString() + " " + bundleToString(intent.getExtras());
    }

    public static String bundleToString(Bundle bundle) {
        StringBuilder out = new StringBuilder("Bundle[");

        if (bundle == null) {
            out.append("null");
        } else {
            boolean first = true;
            for (String key : bundle.keySet()) {
                if (!first) {
                    out.append("\n");
                } else {
                    first = false;
                }
                out.append(key).append('=');
                Object value = bundle.get(key);
                if (value instanceof int[]) {
                    out.append(Arrays.toString((int[]) value));
                } else if (value instanceof byte[]) {
                    out.append(Arrays.toString((byte[]) value));
                } else if (value instanceof boolean[]) {
                    out.append(Arrays.toString((boolean[]) value));
                } else if (value instanceof short[]) {
                    out.append(Arrays.toString((short[]) value));
                } else if (value instanceof long[]) {
                    out.append(Arrays.toString((long[]) value));
                } else if (value instanceof float[]) {
                    out.append(Arrays.toString((float[]) value));
                } else if (value instanceof double[]) {
                    out.append(Arrays.toString((double[]) value));
                } else if (value instanceof String[]) {
                    out.append(Arrays.toString((String[]) value));
                } else if (value instanceof CharSequence[]) {
                    out.append(Arrays.toString((CharSequence[]) value));
                } else if (value instanceof Parcelable[]) {
                    out.append(Arrays.toString((Parcelable[]) value));
                } else if (value instanceof Bundle) {
                    out.append(bundleToString((Bundle) value));
                } else {
                    out.append(value);
                }
            }
        }
        out.append("]");
        return out.toString();
    }


    private static void print(TypeLog typeLog, String location, String tag, String message, Throwable e) {
        if (message != null) {
            for (int i = 0; i <= message.length() / MAX_SIZE_LOG; i++) {
                int start = i * MAX_SIZE_LOG;
                int end = (i + 1) * MAX_SIZE_LOG;
                end = end > message.length() ? message.length() : end;
                switch (typeLog) {
                    case VERBOSE: {
                        if (e != null) {
                            android.util.Log.v(tag, location + (message.substring(start, end)), e);
                        } else {
                            android.util.Log.v(tag, location + (message.substring(start, end)));
                        }
                        break;
                    }
                    case DEBUG: {
                        if (e != null) {
                            android.util.Log.d(tag, location + (message.substring(start, end)), e);
                        } else {
                            android.util.Log.d(tag, location + (message.substring(start, end)));
                        }
                        break;
                    }
                    case INFO: {
                        if (e != null) {
                            android.util.Log.i(tag, location + (message.substring(start, end)), e);
                        } else {
                            android.util.Log.i(tag, location + (message.substring(start, end)));
                        }
                        break;
                    }
                    case WARN: {
                        if (e != null) {
                            android.util.Log.w(tag, location + (message.substring(start, end)), e);
                        } else {
                            android.util.Log.w(tag, location + (message.substring(start, end)));
                        }
                        break;
                    }
                    case ERROR: {
                        if (e != null) {
                            android.util.Log.e(tag, location + (message.substring(start, end)), e);
                        } else {
                            android.util.Log.e(tag, location + (message.substring(start, end)));
                        }
                        break;
                    }
                }
            }
        } else {
            switch (typeLog) {
                case VERBOSE: {
                    if (e != null) {
                        android.util.Log.v(tag, location, e);
                    } else {
                        android.util.Log.v(tag, location);
                    }
                    break;
                }
                case DEBUG: {
                    if (e != null) {
                        android.util.Log.d(tag, location, e);
                    } else {
                        android.util.Log.d(tag, location);
                    }
                    break;
                }
                case INFO: {
                    if (e != null) {
                        android.util.Log.i(tag, location, e);
                    } else {
                        android.util.Log.i(tag, location);
                    }
                    break;
                }
                case WARN: {
                    if (e != null) {
                        android.util.Log.w(tag, location, e);
                    } else {
                        android.util.Log.w(tag, location);
                    }
                    break;
                }
                case ERROR: {
                    if (e != null) {
                        android.util.Log.e(tag, location, e);
                    } else {
                        android.util.Log.e(tag, location);
                    }
                    break;
                }
            }
        }


    }

    public static void v(String message) {
        print(TypeLog.VERBOSE, getLocation(), TAG, message, null);
    }

    public static void w(String message) {
        print(TypeLog.WARN, getLocation(), TAG, message, null);
    }

    public static void e(String tagEx, String message) {
        print(TypeLog.ERROR, getLocation(), tagEx, message, null);
    }

    public static void e(String tagEx, String message, Throwable e) {
        print(TypeLog.ERROR, getLocation(), tagEx, message, e);
    }

    public static void e(String message, Throwable e) {
        print(TypeLog.ERROR, getLocation(), TAG, message, e);
    }

    public static void e(String message) {
        print(TypeLog.ERROR, getLocation(), TAG, message, null);
    }

    public static void i(String tagEx, String message) {
        print(TypeLog.INFO, getLocation(), tagEx, message, null);
    }

    public static void i(String message) {
        print(TypeLog.INFO, getLocation(), TAG, message, null);
    }

    public static void d(String exTag, String message) {
        print(TypeLog.DEBUG, getLocation(), exTag, message, null);
    }

    public static void d(String message) {
        print(TypeLog.DEBUG, getLocation(), TAG, message, null);
    }

    public static void v() {
        print(TypeLog.VERBOSE, getLocation(), TAG, "", null);
    }

    public static void w() {
        print(TypeLog.WARN, getLocation(), TAG, "", null);
    }

    public static void e() {
        print(TypeLog.ERROR, getLocation(), TAG, "", null);
    }

    public static void i() {
        print(TypeLog.INFO, getLocation(), TAG, "", null);
    }

    public static void d() {
        print(TypeLog.DEBUG, getLocation(), TAG, "", null);
    }

    public static String getLocation() {
        if (magicNumber > -1) {
            return getLocationFast();
        }
        return getLocationSlow();
    }

    private static String getLocationSlow() {
        final String className = Log.class.getName();
        final StackTraceElement[] traces = Thread.currentThread().getStackTrace();
        boolean found = false;
        for (int i = 0; i < traces.length; i++) {
            StackTraceElement trace = traces[i];
            try {
                if (found) {
                    if (!trace.getClassName().startsWith(className)) {
                        magicNumber = i;
                        Class<?> clazz = Class.forName(trace.getClassName());
                        return "[" + getClassName(clazz) + ":" + trace.getMethodName() + ":" + trace.getLineNumber() + "]: ";
                    }
                } else {
                    if (trace.getClassName().startsWith(className)) {
                        found = true;
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return "[]: ";
    }

    private static String getLocationFast() {
        final StackTraceElement[] traces = Thread.currentThread().getStackTrace();
        StackTraceElement trace = traces[magicNumber];
        try {
            Class<?> clazz = Class.forName(trace.getClassName());
            return "[" + getClassName(clazz) + ":" + trace.getMethodName() + ":" + trace.getLineNumber() + "]: ";
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "[]: ";
    }


    private static String getClassName(Class<?> clazz) {
        if (clazz != null) {
            if (!TextUtils.isEmpty(clazz.getSimpleName())) {
                return clazz.getSimpleName();
            }
            return getClassName(clazz.getEnclosingClass());
        }
        return "";
    }
}
