package movies.app.movies.task;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.springframework.http.ContentCodingType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOError;
import java.lang.reflect.Type;

import movies.app.movies.constants.Constants;
import movies.app.movies.dao.Movies;

/**@author
 * Created by Caio on 09/01/2017.
 */

public class MovieTask extends AsyncTask<Void,Void,Movies> {

    final String movieName;

    public MovieTask(final String movieName) {
        this.movieName = movieName;
    }

    @Override
    protected Movies doInBackground(Void... voids) {

        try {
          /***adiciona gzip Accept-Encoding header*/
            final HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);
            final HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

            /** cria um novo RestTemplate instance*/
            final RestTemplate restTemplate = new RestTemplate();

            /**adiciona o String message converter*/
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            final String uri = Constants.URL + String.format(Constants.GET_MOVIES, movieName);

            /*** faz a requisição HTTP GET */
            final ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);

            Log.e("GET_MOVIES","-----------");
            Log.e("RESPONSE", response.getBody());
            Log.e("STATUS CODE", response.getStatusCode().toString());

            final Type moviesType =new TypeToken<Movies>(){}.getType();

            final Movies movies = new Gson().fromJson(response.getBody(),moviesType);

            if(movies.getImdbID() != null){
                return movies;
            }

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }   catch (IOError ioError){
            return null;
       }
        return null;
    }
}

