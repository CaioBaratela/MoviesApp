package movies.app.movies.Application;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Locale;

import de.greenrobot.dao.identityscope.IdentityScopeType;
import movies.app.movies.dao.DaoMaster;
import movies.app.movies.dao.DaoSession;
import movies.app.movies.helper.DbHelper;

/**@author
 * Created by Caio on 09/01/2017.
 */

public class MoviesApplication extends Application {

    public static final String DB_NAME = "movies-db";
    private static final Object lockObject = new Object();
    private static final String DEBUG_TAG = MoviesApplication.class.getSimpleName();
    private static Context context;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    private static MoviesApplication moviesApplication;


    public static Context getContext() {
        return context;
    }

    public static MoviesApplication getCurrent() {
        return (MoviesApplication) getContext().getApplicationContext();
    }

    public static Locale getLocale() {
        return context.getResources().getConfiguration().locale;
    }

    public static String getDatabasePath() {
        return context.getDatabasePath(DB_NAME).getAbsolutePath();
    }

    public static SQLiteDatabase getWriteableDatabase() {
        return SQLiteDatabase.openDatabase(getDatabasePath(), null, (SQLiteDatabase.OPEN_READWRITE));
    }

    public static SQLiteDatabase getReadonlyDatabase() {
        return SQLiteDatabase.openDatabase(getDatabasePath(), null, (SQLiteDatabase.OPEN_READONLY));
    }

    private static DaoMaster getDaoMaster() {

        if (daoMaster == null) {
            synchronized (lockObject) {
                if (daoMaster == null) {

                    daoMaster = new DaoMaster(getWriteableDatabase());
                }
            }
        }

        return daoMaster;
    }

    public static DaoSession getDaoSession() {

        if (daoSession == null) {
            synchronized (lockObject) {
                if (daoSession == null) {
                    daoSession = getDaoMaster().newSession(IdentityScopeType.None);
                }
            }
        }

        SQLiteDatabase db = daoSession.getDatabase();
        while (db.isDbLockedByCurrentThread()) {
            // mantenha em loop até o bd for destravado.
        }

        return daoSession;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        moviesApplication = this;

        prepareDatabase();
    }

    /**
     * Define o que fazer com as exceções não tratadas.
     */
    private void defineUncaughtExceptionHandler() {

        // Pega as Exceções não tratadas da thread GUI.
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            private static final String DOUBLE_LINE_SEP = "\n\n";
            private static final String SINGLE_LINE_SEP = "\n";
            private static final String LINE_SEPARATOR = "-------------------------------\n\n";

            @Override
            public void uncaughtException(Thread thread, Throwable ex) {

                StackTraceElement[] arr = ex.getStackTrace();
                final StringBuffer report = new StringBuffer(ex.toString());
                report.append(DOUBLE_LINE_SEP);
                report.append("--------- Stack trace ---------\n\n");
                for (int i = 0; i < arr.length; i++) {
                    report.append("    ");
                    report.append(arr[i].toString());
                    report.append(SINGLE_LINE_SEP);
                }
                report.append(LINE_SEPARATOR);

                // If the exception was thrown in a background thread inside
                // AsyncTask, then the actual exception can be found with getCause
                report.append("--------- Cause ---------\n\n");
                Throwable cause = ex.getCause();
                if (cause != null) {
                    report.append(cause.toString());
                    report.append(DOUBLE_LINE_SEP);
                    arr = cause.getStackTrace();
                    for (int i = 0; i < arr.length; i++) {
                        report.append("    ");
                        report.append(arr[i].toString());
                        report.append(SINGLE_LINE_SEP);
                    }
                }
                // Getting the Device brand,model and sdk verion details.
                report.append(LINE_SEPARATOR);
                report.append("--------- Device ---------\n\n");
                report.append("Brand: ");
                report.append(android.os.Build.BRAND);
                report.append(SINGLE_LINE_SEP);
                report.append("Device: ");
                report.append(android.os.Build.DEVICE);
                report.append(SINGLE_LINE_SEP);
                report.append("Model: ");
                report.append(android.os.Build.MODEL);
                report.append(SINGLE_LINE_SEP);
                report.append("Id: ");
                report.append(android.os.Build.ID);
                report.append(SINGLE_LINE_SEP);
                report.append("Product: ");
                report.append(android.os.Build.PRODUCT);
                report.append(SINGLE_LINE_SEP);
                report.append(LINE_SEPARATOR);
                report.append("--------- Firmware ---------\n\n");
                report.append("SDK: ");
                report.append(android.os.Build.VERSION.SDK_INT);
                report.append(SINGLE_LINE_SEP);
                report.append("Release: ");
                report.append(android.os.Build.VERSION.RELEASE);
                report.append(SINGLE_LINE_SEP);
                report.append("Incremental: ");
                report.append(android.os.Build.VERSION.INCREMENTAL);
                report.append(SINGLE_LINE_SEP);
                report.append(LINE_SEPARATOR);

                Log.e(DEBUG_TAG, "Fatal Error: " + report.toString(), ex);

                System.exit(-1);
            }
        });
    }

    private void prepareDatabase() {
        new DbHelper(this, DB_NAME, null);
    }

    public enum GoogleAnalyticsTracker {
        GLOBAL_ISCOOLAPP;
    }
}
