package ir.haftsang.moduleui.mvp;

import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ir.pkokabi.alertview.AlertView;
import ir.pkokabi.pdialog.NetworkDialog;

/**
 * Created by p.kokabi on 7/11/2017.
 */

public abstract class BaseActivity<B extends ViewDataBinding, P extends BasePresenter> extends AppCompatActivity implements IBaseView, View.OnClickListener {

    protected B binding;
    protected P presenter;

    protected abstract P createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = createPresenter();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    public void setup(int layoutId) {
        binding = DataBindingUtil.setContentView(this, layoutId);
        initialData();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isGranted = false;
        for (int item : grantResults) {
            isGranted = grantResults.length == permissions.length && item == PackageManager.PERMISSION_GRANTED;
        }
        if (isGranted) permissionGranted();
        else permissionNotGranted();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null)
            presenter.detachView();
    }

    @Override
    public void checkPermission(int requestCode, String[] permissionArray) {
        boolean isGranted = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String item : permissionArray)
                isGranted = ActivityCompat.checkSelfPermission(this, item) == PackageManager.PERMISSION_GRANTED;
            if (isGranted) permissionGranted();
            else requestPermissions(permissionArray, requestCode);
        } else permissionGranted();
    }

    @Override
    public void permissionGranted() {

    }

    @Override
    public void permissionNotGranted() {

    }

    @Override
    public void networkWarning() {
        new NetworkDialog
                .Builder(this)
                .isPersian(true)
                .build();
    }

    @Override
    public void showMessage(String message) {
        new AlertView(this, message, AlertView.STATE_SUCCESS);
    }

    @Override
    public void showError(String error) {
        new AlertView(this, error, AlertView.STATE_ERROR);
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void initialData() {
        initListeners();
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void onClickListeners(@Nullable View[] views) {
        if (views != null) {
            for (View view : views) {
                view.setOnClickListener(this);
            }
        }
    }

}
