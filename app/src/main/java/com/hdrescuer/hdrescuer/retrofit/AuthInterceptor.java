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
     * @return
     * @throws IOException
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
            //obtenemos el token
            String token = SharedPreferencesManager.getSomeStringValue(Constants.PREF_TOKEN);
            //creamos una request genérica y asignamos a su "chain" el Header Authorization Bearer token
            Request request = chain.request().newBuilder().addHeader("Authorization", "Bearer "+token).build();
            return chain.proceed(request);
    }
}
