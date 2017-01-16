package movies.app.movies.utils;


import android.support.v7.app.ActionBar;

/**@author
 * Created by Caio on 12/01/2017.
 */

public abstract class AppUtil {

    /**Muda as configurações da action bar*/
    public static void configActionBar(final ActionBar actionBar, final String title,final boolean enable){
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(enable);
            actionBar.setDisplayShowHomeEnabled(enable);
        }
    }
}
