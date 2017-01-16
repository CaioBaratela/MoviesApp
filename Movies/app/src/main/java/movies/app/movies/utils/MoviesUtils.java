package movies.app.movies.utils;

import java.util.List;
import java.util.UUID;

import movies.app.movies.Application.MoviesApplication;
import movies.app.movies.dao.Movies;

/**@author
 * Created by Caio on 11/01/2017.
 */

public abstract class MoviesUtils {

    /**Carrega todos os filmes*/
    public static List<Movies> loadAllMovies(){
        return MoviesApplication.getDaoSession().getMoviesDao().loadAll();
    }

    /**adiciona um filme*/
    public static void addMovie(final Movies movies){
        MoviesApplication.getDaoSession().getMoviesDao().insertOrReplace(movies);
    }

    /**remove um filme*/
    public static void removeMovie(final Movies movies){
        MoviesApplication.getDaoSession().getMoviesDao().delete(movies);
    }
}
