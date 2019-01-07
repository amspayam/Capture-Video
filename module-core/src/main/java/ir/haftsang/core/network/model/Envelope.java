package ir.haftsang.core.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Envelope<T> {

    @Expose
    @SerializedName("statuesCode")
    private int statusCode;
    @Expose
    @SerializedName("statusMessage")
    private String statusMessage;
    @Expose
    @SerializedName("data")
    private T data;

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public T getData() {
        return data;
    }

    public boolean isSuccess() {
        return statusCode == 200 || statusCode != 401 && statusCode != 500;
    }

}