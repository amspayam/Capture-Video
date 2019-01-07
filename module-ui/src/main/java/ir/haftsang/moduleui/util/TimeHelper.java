package ir.haftsang.moduleui.util;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeHelper {

    public static String getDate() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(new Date());
    }

    public static String replaceDash(String date) {
        return date.replace("/", "");
    }

    public static int convertDateToInt(String date) {
        return Integer.parseInt(replaceDash(date));
    }

    public static String convertDigitToDate(String date) {
        return date.substring(0, 4) +
                "/" +
                date.substring(4, 6) +
                "/" +
                date.substring(6, 8);
    }

    public static String convertToMinutes(long duration) {
        long hours = duration / 3600;
        long remainder = duration - hours * 3600;
        long minutes = remainder / 60;
        remainder = remainder - minutes * 60;
        long seconds = remainder;

        NumberFormat numberFormat = new DecimalFormat("00");

        return numberFormat.format(minutes) + ":" + numberFormat.format(seconds);
    }

    public static int getDay(String date) {
        return Integer.parseInt(date.substring(6, 8));
    }

    public static int getMonth(String date) {
        return Integer.parseInt(date.substring(4, 6)) - 1;
    }

    public static int getYear(String date) {
        return Integer.parseInt(date.substring(0, 4));
    }
}