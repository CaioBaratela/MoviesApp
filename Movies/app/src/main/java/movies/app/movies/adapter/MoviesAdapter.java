package movies.app.movies.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import movies.app.movies.R;
import movies.app.movies.constants.Constants;
import movies.app.movies.dao.Movies;
import movies.app.movies.databinding.ListItemMoviesBinding;

/**@author
 * Created by Caio on 11/01/2017.
 */

public class MoviesAdapter extends BaseAdapter {

    final List<Movies> moviesList;
    final Context context;

    public MoviesAdapter(List<Movies> moviesList, Context context) {
        this.moviesList = moviesList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return moviesList.size();
    }

    @Override
    public Movies getItem(int i) {
        return moviesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final Movies movies = getItem(i);

        ListItemMoviesBinding itemMoviesBinding;

        if (view == null) {
            /**infla o layout utilizando o data bind*/
            itemMoviesBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.list_item_movies,viewGroup,false);
            view = itemMoviesBinding.getRoot();
            view.setTag(itemMoviesBinding);
        } else{
            itemMoviesBinding = (ListItemMoviesBinding) view.getTag();
        }

        if(!movies.getPoster().equals(Constants.N_A)) {
            Picasso.with(context).load(movies.getPoster()).into(itemMoviesBinding.imgPoster);
        }else{
            itemMoviesBinding.imgPoster.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_launcher));
        }

        itemMoviesBinding.txtMovieName.setText("Filme: "+ movies.getTitle());
        itemMoviesBinding.txtDirector.setText("Diretor: "+movies.getDirector());
        itemMoviesBinding.txtImdbRating.setText("Nota imdb: "+movies.getImdbRating());

        return view;
    }
}
