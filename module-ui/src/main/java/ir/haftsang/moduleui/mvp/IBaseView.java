package ir.haftsang.moduleui.mvp;


import android.support.annotation.Nullable;
import android.view.View;


/**
 * Created by p.kokabi on 7/19/2017.
 */

public interface IBaseView {

    void setup(int layoutId);

    void checkPermission(int requestCode, String[] permissionArray);

    void permissionGranted();

    void permissionNotGranted();

    void networkWarning();

    void showError(String error);

    void showMessage(String message);

    void initialData();

    void initListeners();

    void onClickListeners(@Nullable View[] views);

}