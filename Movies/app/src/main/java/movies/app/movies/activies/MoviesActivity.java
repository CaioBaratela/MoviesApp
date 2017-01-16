package movies.app.movies.activies;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Collections;
import java.util.List;

import movies.app.movies.R;
import movies.app.movies.adapter.MoviesAdapter;
import movies.app.movies.constants.Constants;
import movies.app.movies.constants.SignalEnum;
import movies.app.movies.dao.Movies;
import movies.app.movies.databinding.ActivityMoviesBinding;
import movies.app.movies.utils.AppUtil;
import movies.app.movies.utils.MoviesUtils;

/**@author
 * Created by Caio on 09/01/2017.
 */

public class MoviesActivity extends AppCompatActivity {

    final Context context = this;
    private ActivityMoviesBinding moviesBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**Atribui o data bind para poder utilizar os componentes do layout*/
        this.moviesBinding = DataBindingUtil.setContentView(this,R.layout.activity_movies);

        setComponent();

        if(getSupportActionBar() != null) {
            AppUtil.configActionBar(getSupportActionBar(),"Meus Filmes",false);
        }
    }

    /**Atribui os valores para os componentes */
    public void setComponent(){

        final List<Movies> movies = MoviesUtils.loadAllMovies();
        Collections.reverse(movies);

        final MoviesAdapter moviesAdapter = new MoviesAdapter(movies,context);
        this.moviesBinding.lvMovies.setAdapter(moviesAdapter);

        setListListener(movies);
    }

    /**Cria o listener que abre o filme*/
    public void setListListener(final List<Movies> movies){
        this.moviesBinding.lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(movies != null && !movies.isEmpty()){
                    final Intent intent = new Intent(context, DetailMovieActivity.class);
                    final Bundle bundle = new Bundle();
                    bundle.putParcelable(Constants.MOVIE, movies.get(i));
                    bundle.putString(Constants.ACTIVITY_NAME,Constants.MOVIES_ACTIVITY);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movies_activity,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.btn_add_movie:
                final Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;
            default:
                Log.e(SignalEnum.ERROR.toString(),"item não encontrado");
        }
        return super.onOptionsItemSelected(item);
    }
}
