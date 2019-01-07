package ir.haftsang.moduleui.mvp;


import android.os.Bundle;

import ir.haftsang.core.network.NetworkCallBack;
import retrofit2.Call;

/**
 * Created by p.kokabi on 12/26/2017.
 */

public interface IBasePresenter {

    void bindIntent(Bundle bundle);

    <M> void addSubscribe(Call<M> retrofitCall, NetworkCallBack<M> retrofitCallBack);

    void detachView();


}