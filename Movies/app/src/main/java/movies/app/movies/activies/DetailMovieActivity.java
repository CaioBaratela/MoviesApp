package movies.app.movies.activies;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

import movies.app.movies.R;
import movies.app.movies.constants.Constants;
import movies.app.movies.dao.Movies;
import movies.app.movies.databinding.ActivityDetailMovieBinding;
import movies.app.movies.utils.AppUtil;
import movies.app.movies.utils.MoviesUtils;

public class DetailMovieActivity extends AppCompatActivity {

    private Movies movies;
    private String activityName;
    private final Context context = this;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private ActivityDetailMovieBinding activityDetailMovieBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**Atribui o data bind para poder utilizar os componentes do layout*/
        this.activityDetailMovieBinding = DataBindingUtil.setContentView(this,R.layout.activity_detail_movie);

        /**Pega o informações que foram passado pela SearchActivity ou pela MovieActivity*/
        final Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            this.movies = bundle.getParcelable(Constants.MOVIE);
            this.activityName = bundle.getString(Constants.ACTIVITY_NAME);
        }

        setComponents(movies);

        if (getSupportActionBar() != null) {
            AppUtil.configActionBar(getSupportActionBar(), movies.getTitle(), false);
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**Atribui os valores para os componentes */
    public void setComponents(final Movies movies) {

        if (movies != null) {

            if (!movies.getPoster().equals(Constants.N_A)) {
                Picasso.with(this).load(movies.getPoster()).into(this.activityDetailMovieBinding.imgPosterDetail);
            } else {
                this.activityDetailMovieBinding.imgPosterDetail.setImageDrawable(this.getResources().getDrawable(R.mipmap.ic_launcher));
            }

            this.activityDetailMovieBinding.txtDetailTitle.setText(movies.getTitle());
            this.activityDetailMovieBinding.txtGenreDetail.setText(movies.getGenre());
            this.activityDetailMovieBinding.txtImdbDetail.setText("Imdb: " + movies.getImdbRating());
            this.activityDetailMovieBinding.txtRatedDetail.setText("Classif.: " + movies.getRated());
            this.activityDetailMovieBinding.txtLanguageDetail.setText(movies.getLanguage());
            this.activityDetailMovieBinding.txtDurationDetail.setText(movies.getRuntime());
            this.activityDetailMovieBinding.txtDateDetail.setText(movies.getReleased());
            this.activityDetailMovieBinding.txtPlot.setText(movies.getPlot());
            this.activityDetailMovieBinding.txtDirectorName.setText(movies.getDirector());
            this.activityDetailMovieBinding.txtWritersName.setText(movies.getWriter());
            this.activityDetailMovieBinding.txtStars.setText(movies.getActors());

            setSaveListener(this.activityDetailMovieBinding);
        }
    }

    /**Cria o listener para o floatButton
     * @param activityDetailMovieBinding*/
    public void setSaveListener(final ActivityDetailMovieBinding activityDetailMovieBinding) {

        if (this.activityName.equals(Constants.SEARCH_ACTIVITY)) {
            saveMovie(activityDetailMovieBinding);
        } else {
            deleteMovie(activityDetailMovieBinding);
        }
    }

    /**Salva o filme selecionado
     * @param activityDetailMovieBinding*/
    public void saveMovie(ActivityDetailMovieBinding activityDetailMovieBinding) {
        activityDetailMovieBinding.fabAdd.setVisibility(View.VISIBLE);
        activityDetailMovieBinding.fabRemove.setVisibility(View.GONE);

        activityDetailMovieBinding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (movies != null) {

                    new AlertDialog.Builder(context)
                            .setTitle("Sim")
                            .setMessage("Deseja adicionar este filme em sua lista?")
                            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    MoviesUtils.addMovie(movies);
                                    final Intent intent = new Intent(context, MoviesActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("Não", null)
                            .show();

                }
            }
        });
    }

    /**Deleta o filme selecionado
     * @param activityDetailMovieBinding*/
    public void deleteMovie(final ActivityDetailMovieBinding activityDetailMovieBinding) {
        activityDetailMovieBinding.fabAdd.setVisibility(View.GONE);
        activityDetailMovieBinding.fabRemove.setVisibility(View.VISIBLE);

        activityDetailMovieBinding.fabRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (movies != null) {

                    new AlertDialog.Builder(context)
                            .setTitle("Deletar")
                            .setMessage("Deseja remover este filme de sua lista?")
                            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    MoviesUtils.removeMovie(movies);
                                    final Intent intent = new Intent(context, MoviesActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("Não", null)
                            .show();

                }
            }
        });
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("DetailMovie Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
