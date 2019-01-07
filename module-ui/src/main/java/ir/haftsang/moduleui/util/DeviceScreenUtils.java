package ir.haftsang.moduleui.util;


import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Rasoul on 7/11/17.
 */

public class DeviceScreenUtils {

    private static int width;
    private static int height;
    private static int statusBarHeight;

    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    public static boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    private static void calculateSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (windowManager != null) windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
    }

    public static int height(Context context) {
        if (height == 0) {
            calculateSize(context);
        }
        return height;
    }

    public static int width(Context context) {
        if (width == 0) {
            calculateSize(context);
        }
        return width;
    }

    public static int statusbar(AppCompatActivity appCompatActivity) {
        if (statusBarHeight == 0) {
            Rect rectangle = new Rect();
            Window window = appCompatActivity.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
            statusBarHeight = rectangle.top;
        }
        return statusBarHeight;
    }


    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }


}
