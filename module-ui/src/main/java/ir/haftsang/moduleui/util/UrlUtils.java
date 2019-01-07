package ir.haftsang.moduleui.util;


/**
 * Created by Rasoul Miri on 11/11/18.
 */
public class UrlUtils {

    public static String normalUrl(Object str) {
        String textSpace = String.valueOf(str);
        String firstFilter = textSpace.replace(" ", "-");
        String secondFilter = firstFilter.replace("(", "");
        String thirdFilter = secondFilter.replace(":", "");
        return thirdFilter.replace(")", "");
    }
}
