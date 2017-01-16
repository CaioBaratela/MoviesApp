package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class DaoGen {

    private static final String PROJECT = "app\\src\\main\\java\\";

   public static void main(String[] args){
       final Schema schema = new Schema(1,"movies.app.movies.dao");

       schema.enableKeepSectionsByDefault();

       addMovies(schema);

       try{
           new DaoGenerator().generateAll(schema,PROJECT);
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

    private static void addMovies(final Schema schema){
        final Entity movies = schema.addEntity("Movies");

        movies.addStringProperty("imdbID").primaryKey().notNull().index();
        movies.addStringProperty("Title");
        movies.addStringProperty("Year");
        movies.addStringProperty("Rated");
        movies.addStringProperty("Released");
        movies.addStringProperty("Runtime");
        movies.addStringProperty("Genre");
        movies.addStringProperty("Director");
        movies.addStringProperty("Actors");
        movies.addStringProperty("Plot");
        movies.addStringProperty("Language");
        movies.addStringProperty("Awards");
        movies.addStringProperty("Poster");
        movies.addStringProperty("MetaScore");
        movies.addStringProperty("imdbRating");
        movies.addStringProperty("Type");
        movies.addStringProperty("Writer");
    }

}
