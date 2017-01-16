package movies.app.movies.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import movies.app.movies.R;
import movies.app.movies.constants.Constants;
import movies.app.movies.dao.Movies;
import movies.app.movies.databinding.ListItemMoviesBinding;

/**@author
 * Created by Caio on 11/01/2017.
 */

public class MovieSearchAdapter  extends BaseAdapter{

    private final Movies movie;
    private final Context context;

    public MovieSearchAdapter(final Context context, final Movies movie) {
        this.context = context;
        this.movie = movie;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int i) {
        return movie;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ListItemMoviesBinding itemMoviesBinding;

        if (view == null) {
            /**infla o layout utilizando o data bind*/
            itemMoviesBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.list_item_movies,viewGroup,false);
            view = itemMoviesBinding.getRoot();
            view.setTag(itemMoviesBinding);
        } else{
            itemMoviesBinding = (ListItemMoviesBinding) view.getTag();
        }

        if(!movie.getPoster().equals(Constants.N_A)) {
            Picasso.with(context).load(movie.getPoster()).into(itemMoviesBinding.imgPoster);
        }else{
            itemMoviesBinding.imgPoster.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_launcher));
        }

        itemMoviesBinding.txtMovieName.setText("Filme: "+ movie.getTitle());
        itemMoviesBinding.txtDirector.setText("Diretor: "+movie.getDirector());
        itemMoviesBinding.txtImdbRating.setText("Nota imdb: "+movie.getImdbRating());

        return view;
    }
}
