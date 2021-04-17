package com.hdrescuer.hdrescuer.retrofit;

import com.hdrescuer.hdrescuer.common.Constants;
import com.hdrescuer.hdrescuer.common.SharedPreferencesManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Clase que implementa la Interfaz Interceptor, que intercepta valga la redundancia una petición y le añade una cadena en el Header,
 * así no tenemos que estar escribiendo el token en cada petición.
 * @author Domingo Lopez
 */
public class AuthInterceptor implements Interceptor {

    /**
     * Método invocado para cada petición que se realice, añadiendo el TOKEN en las cabeceras
     * @author Domingo Lopez
     * @param chain
     * @return Response
     * @throws IOException
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
            String token = SharedPreferencesManager.getSomeStringValue(Constants.PREF_TOKEN);
            Request request = chain.request().newBuilder().addHeader("Authorization", "Bearer "+token).build();
            return chain.proceed(request);
    }
}
