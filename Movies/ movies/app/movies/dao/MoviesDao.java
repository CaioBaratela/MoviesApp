package  movies.app.movies.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import  movies.app.movies.dao.Movies;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MOVIES".
*/
public class MoviesDao extends AbstractDao<Movies, String> {

    public static final String TABLENAME = "MOVIES";

    /**
     * Properties of entity Movies.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Uuid = new Property(0, String.class, "uuid", true, "UUID");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property Year = new Property(2, Integer.class, "year", false, "YEAR");
        public final static Property Rated = new Property(3, String.class, "rated", false, "RATED");
        public final static Property Released = new Property(4, java.util.Date.class, "released", false, "RELEASED");
        public final static Property RunTime = new Property(5, Long.class, "runTime", false, "RUN_TIME");
        public final static Property Genre = new Property(6, String.class, "genre", false, "GENRE");
        public final static Property Director = new Property(7, String.class, "director", false, "DIRECTOR");
        public final static Property Actors = new Property(8, String.class, "actors", false, "ACTORS");
        public final static Property Plot = new Property(9, String.class, "plot", false, "PLOT");
        public final static Property Language = new Property(10, String.class, "language", false, "LANGUAGE");
        public final static Property Awards = new Property(11, String.class, "awards", false, "AWARDS");
        public final static Property Poster = new Property(12, String.class, "poster", false, "POSTER");
        public final static Property MetaScore = new Property(13, Long.class, "metaScore", false, "META_SCORE");
        public final static Property ImdbRating = new Property(14, Long.class, "imdbRating", false, "IMDB_RATING");
        public final static Property Type = new Property(15, String.class, "type", false, "TYPE");
    };


    public MoviesDao(DaoConfig config) {
        super(config);
    }
    
    public MoviesDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MOVIES\" (" + //
                "\"UUID\" TEXT PRIMARY KEY NOT NULL ," + // 0: uuid
                "\"TITLE\" TEXT," + // 1: title
                "\"YEAR\" INTEGER," + // 2: year
                "\"RATED\" TEXT," + // 3: rated
                "\"RELEASED\" INTEGER," + // 4: released
                "\"RUN_TIME\" INTEGER," + // 5: runTime
                "\"GENRE\" TEXT," + // 6: genre
                "\"DIRECTOR\" TEXT," + // 7: director
                "\"ACTORS\" TEXT," + // 8: actors
                "\"PLOT\" TEXT," + // 9: plot
                "\"LANGUAGE\" TEXT," + // 10: language
                "\"AWARDS\" TEXT," + // 11: awards
                "\"POSTER\" TEXT," + // 12: poster
                "\"META_SCORE\" INTEGER," + // 13: metaScore
                "\"IMDB_RATING\" INTEGER," + // 14: imdbRating
                "\"TYPE\" TEXT);"); // 15: type
        // Add Indexes
        db.execSQL("CREATE INDEX " + constraint + "IDX_MOVIES_UUID ON MOVIES" +
                " (\"UUID\");");
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MOVIES\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Movies entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getUuid());
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }
 
        Integer year = entity.getYear();
        if (year != null) {
            stmt.bindLong(3, year);
        }
 
        String rated = entity.getRated();
        if (rated != null) {
            stmt.bindString(4, rated);
        }
 
        java.util.Date released = entity.getReleased();
        if (released != null) {
            stmt.bindLong(5, released.getTime());
        }
 
        Long runTime = entity.getRunTime();
        if (runTime != null) {
            stmt.bindLong(6, runTime);
        }
 
        String genre = entity.getGenre();
        if (genre != null) {
            stmt.bindString(7, genre);
        }
 
        String director = entity.getDirector();
        if (director != null) {
            stmt.bindString(8, director);
        }
 
        String actors = entity.getActors();
        if (actors != null) {
            stmt.bindString(9, actors);
        }
 
        String plot = entity.getPlot();
        if (plot != null) {
            stmt.bindString(10, plot);
        }
 
        String language = entity.getLanguage();
        if (language != null) {
            stmt.bindString(11, language);
        }
 
        String awards = entity.getAwards();
        if (awards != null) {
            stmt.bindString(12, awards);
        }
 
        String poster = entity.getPoster();
        if (poster != null) {
            stmt.bindString(13, poster);
        }
 
        Long metaScore = entity.getMetaScore();
        if (metaScore != null) {
            stmt.bindLong(14, metaScore);
        }
 
        Long imdbRating = entity.getImdbRating();
        if (imdbRating != null) {
            stmt.bindLong(15, imdbRating);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(16, type);
        }
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Movies readEntity(Cursor cursor, int offset) {
        Movies entity = new Movies( //
            cursor.getString(offset + 0), // uuid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // title
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // year
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // rated
            cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)), // released
            cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5), // runTime
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // genre
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // director
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // actors
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // plot
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // language
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // awards
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // poster
            cursor.isNull(offset + 13) ? null : cursor.getLong(offset + 13), // metaScore
            cursor.isNull(offset + 14) ? null : cursor.getLong(offset + 14), // imdbRating
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15) // type
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Movies entity, int offset) {
        entity.setUuid(cursor.getString(offset + 0));
        entity.setTitle(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setYear(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setRated(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setReleased(cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)));
        entity.setRunTime(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
        entity.setGenre(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setDirector(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setActors(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setPlot(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setLanguage(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setAwards(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setPoster(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setMetaScore(cursor.isNull(offset + 13) ? null : cursor.getLong(offset + 13));
        entity.setImdbRating(cursor.isNull(offset + 14) ? null : cursor.getLong(offset + 14));
        entity.setType(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(Movies entity, long rowId) {
        return entity.getUuid();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(Movies entity) {
        if(entity != null) {
            return entity.getUuid();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
