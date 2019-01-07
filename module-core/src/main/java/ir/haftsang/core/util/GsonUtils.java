package ir.haftsang.core.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;


/**
 * Created by p.kokabi on 3/10/2018.
 */

public class GsonUtils {

    private static final Gson gson;

    static {
        gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create();
    }

    public static Gson getGson() {
        return gson;
    }

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static <T> T fromJson(String body, Class<T> tClass) {
        return gson.fromJson(body, tClass);
    }

    public static <T> T fromJson(JsonObject body, Class<T> tClass) {
        return gson.fromJson(body.toString(), tClass);
    }

    public static <T> T fromJson(String body, TypeToken<T> typeToken) {
        return gson.fromJson(body, typeToken.getType());
    }

    public static <T> T fromJson(JsonArray body, TypeToken<T> typeToken) {
        return gson.fromJson(body.toString(), typeToken.getType());
    }

}