package ir.haftsang.core.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorModel {

    @Expose
    @SerializedName("errorCode")
    private int errorCode;
    @Expose
    @SerializedName("errorBody")
    private String errorBody;
    @Expose
    @SerializedName("requestUrl")
    private String requestUrl;
    @Expose
    @SerializedName("requestBody")
    private String requestBody;
    @Expose
    @SerializedName("deviceGUID")
    private String deviceGUID;

    public ErrorModel(int errorCode, String errorBody, String requestUrl, String requestBody, String deviceGUID) {
        this.errorCode = errorCode;
        this.errorBody = errorBody;
        this.requestUrl = requestUrl;
        this.requestBody = requestBody;
        this.deviceGUID = deviceGUID;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorBody() {
        return errorBody;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public String getDeviceGUID() {
        return deviceGUID;
    }
}