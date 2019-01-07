package ir.haftsang.moduleui.util;

public class DecimalUtil {

    public static Double convert(Double input) {
        long factor = (long) Math.pow(10, 2);
        input = input * factor;
        long tmp = Math.round(input);
        return (double) tmp / factor;
    }
}
