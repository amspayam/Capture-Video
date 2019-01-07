package ir.haftsang.moduleui.mvp;


import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import ir.haftsang.core.network.NetworkCallBack;
import retrofit2.Call;

public class BasePresenter<V extends IBaseView> implements IBasePresenter {

    protected V view;
    private List<Call> apiList;

    protected void attachView(V view) {
        this.view = view;
        apiList = new ArrayList<>();
    }

    @Override
    public void bindIntent(Bundle bundle) {

    }

    @Override
    public <M> void addSubscribe(Call<M> retrofitCall, NetworkCallBack<M> networkCallBack) {
        apiList.add(retrofitCall);
        networkCallBack.setCall(retrofitCall);
    }

    @Override
    public void detachView() {
        this.view = null;
        if (apiList.size() > 0)
            for (Call item : apiList)
                item.cancel();
    }

}