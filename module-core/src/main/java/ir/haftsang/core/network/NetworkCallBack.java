package ir.haftsang.core.network;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;


public abstract class NetworkCallBack<M> implements Callback<M> {

    private static final String TAG = NetworkCallBack.class.getName();

    public NetworkCallBack() {
    }

    public NetworkCallBack(Call<M> call) {
        call.enqueue(this);
    }

    public void setCall(Call<M> call) {
        call.enqueue(this);
    }

    public abstract void onSuccess(M model);

    public abstract void onFailure(String error);

    public abstract void onNetworkFailure();

    @Override
    public void onResponse(Call<M> call, Response<M> response) {
        onSuccess(response.body());
    }

    @Override
    public void onFailure(Call<M> call, Throwable t) {
        try {
            if (t instanceof HttpException) {
                onFailure(t.getMessage());
            } else if (t instanceof SocketTimeoutException) {
                onFailure(t.getMessage());
            } else if (t instanceof UnknownHostException) {
                onNetworkFailure();
            } else if (t instanceof NullPointerException) {
                onFailure(t.getMessage());
            } else {
                onFailure(t.getMessage());
            }
            Log.e(TAG, t.getMessage());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
