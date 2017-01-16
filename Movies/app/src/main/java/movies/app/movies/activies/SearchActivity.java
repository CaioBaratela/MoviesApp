package movies.app.movies.activies;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;

import movies.app.movies.R;
import movies.app.movies.adapter.MovieSearchAdapter;
import movies.app.movies.constants.Constants;
import movies.app.movies.dao.Movies;
import movies.app.movies.databinding.ActivitySearchBinding;
import movies.app.movies.task.MovieTask;
import movies.app.movies.utils.AppUtil;

public class SearchActivity extends AppCompatActivity {

    private final Context context = this;

    private Movies movies;

    private ActivitySearchBinding searchBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**Atribui o data bind para poder utilizar os componentes do layout*/
        this.searchBinding = DataBindingUtil.setContentView(this,R.layout.activity_search);

        if(getSupportActionBar() != null) {
            AppUtil.configActionBar(getSupportActionBar(),"Buscar Filmes",false);
        }

        setListListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_activity,menu);

        final SearchView searchMovie = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.btn_search_movie));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchMovie.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        setSearchListener(searchMovie);


        return super.onCreateOptionsMenu(menu);
    }

    /**Listener que vai para a tela de adicionar filme*/
    private void setListListener(){
        this.searchBinding.lvSearchMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (movies != null) {
                    final Intent intent = new Intent(context, DetailMovieActivity.class);
                    final Bundle bundle = new Bundle();
                    bundle.putParcelable(Constants.MOVIE, movies);
                    bundle.putString(Constants.ACTIVITY_NAME,Constants.SEARCH_ACTIVITY);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    /**Faz o listener da busca
     * @param searchMovie view responsavel pela busca*/
    private void setSearchListener(final SearchView searchMovie){
        searchMovie.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 1){
                    new GetMoviesByNameTask(query,searchBinding).execute();
                    return true;
                }else{
                    return false;
                }
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length() > 1){
                    new GetMoviesByNameTask(newText,searchBinding).execute();
                    return true;
                }else{
                    return false;
                }
            }
        });
    }

    /**Task que busco o filme no servidor OMDB*/
    private class GetMoviesByNameTask extends MovieTask{

        final ActivitySearchBinding searchBinding;

        private GetMoviesByNameTask(final String movieName,final ActivitySearchBinding searchBinding) {
            super(movieName);
            this.searchBinding = searchBinding;
        }

        @Override
        protected void onPostExecute(final Movies movie) {
            super.onPostExecute(movie);
            if(movie != null){
                movies = movie;
                final MovieSearchAdapter movieSearchAdapter = new MovieSearchAdapter(context, movie);
                this.searchBinding.lvSearchMovies.setAdapter(movieSearchAdapter);
            }
        }
    }
}
