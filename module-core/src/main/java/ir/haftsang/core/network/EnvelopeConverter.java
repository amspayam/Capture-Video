package ir.haftsang.core.network;

import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import ir.haftsang.core.network.model.Envelope;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * This Converter implicate for whenever we have a structure
 * called Envelope to look for the next response body in structure
 * and convert it to whatever object needed in ApiService
 **/
public class EnvelopeConverter extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        Type envelopedType =
                TypeToken.getParameterized(Envelope.class, type)
                        .getType();

        Converter<ResponseBody, Envelope<?>> delegate = retrofit.nextResponseBodyConverter(this,
                envelopedType, annotations);

        return body -> {
            Envelope<?> envelope = delegate.convert(body);
            return envelope.isSuccess() ? envelope.getData() : envelope.getStatusMessage();
        };
    }
}