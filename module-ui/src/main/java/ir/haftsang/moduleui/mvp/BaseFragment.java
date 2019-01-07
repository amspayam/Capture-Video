package ir.haftsang.moduleui.mvp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import ir.pkokabi.alertview.AlertView;
import ir.pkokabi.pdialog.NetworkDialog;

public abstract class BaseFragment<B extends ViewDataBinding, P extends BasePresenter> extends Fragment implements IBaseView, View.OnClickListener {

    protected Context context;
    protected B binding;
    protected P presenter;
    protected FragmentActivity activity;

    protected abstract P createPresenter();

    @Override
    public void setup(int layoutId) {
        binding = DataBindingUtil.inflate(activity.getLayoutInflater(), layoutId, null, false);
        initialData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        presenter = createPresenter();
        activity = getActivity();
    }

    @Override
    public void onResume() {
        activity = getActivity();
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean isGranted = false;
        for (int item : grantResults) {
            isGranted = grantResults.length == permissions.length && item == PackageManager.PERMISSION_GRANTED;
        }
        if (isGranted) permissionGranted();
        else permissionNotGranted();
    }

    @Override
    public void checkPermission(int requestCode, String[] permissionArray) {
        boolean isGranted = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String item : permissionArray)
                isGranted = activity.checkSelfPermission(item) == PackageManager.PERMISSION_GRANTED;
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
                .Builder(activity)
                .isPersian(true)
                .build();
    }

    @Override
    public void showMessage(String message) {
        new AlertView(activity, message, AlertView.STATE_SUCCESS);
    }

    @Override
    public void showError(String error) {
        new AlertView(activity, error, AlertView.STATE_ERROR);
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
