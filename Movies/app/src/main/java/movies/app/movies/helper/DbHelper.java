package movies.app.movies.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Environment;
import android.util.Log;


import java.io.File;
import java.sql.SQLException;

import movies.app.movies.Application.MoviesApplication;
import movies.app.movies.dao.DaoMaster;

/**@author
 * Created by Caio on 09/01/2017.
 */

public class DbHelper extends DaoMaster.OpenHelper {

    private static final String DEBUG_TAG = MoviesApplication.class.getSimpleName();
    public static int INVALID_SCHEMA_VERSION = -1;
    public static String BACKUP_DATABASE_DIR = "/iscoolapp/bkp/";
    @SuppressWarnings("unused")
    private Context context;
    @SuppressWarnings("unused")
    private String dbName;

    public DbHelper(Context context, String dbName, SQLiteDatabase.CursorFactory factory) {
        super(context, dbName, factory);
        this.context = context;
        this.dbName = dbName;

        try {
            // Cria o banco de dados, ou o atualiza caso já exista.
            prepareDb();

        } catch (Exception e) {
            e.printStackTrace();
            //throw new Error("Unable to create/update the database.", e);
        }
    }

    public static int getSchemaVersion() {

        SQLiteDatabase db = null;
        try {

            db = MoviesApplication.getReadonlyDatabase();
            return db.getVersion();

        } catch (SQLiteException e) {
                Log.e(DEBUG_TAG, "Database not found.", e);
        } finally {

            if (db != null) {
                db.close();
            }
        }
        return INVALID_SCHEMA_VERSION;
    }

    public static boolean setSchemaVersion(int schemaVersion) {


        SQLiteDatabase db = null;
        try {

            db = MoviesApplication.getWriteableDatabase();
            db.setVersion(schemaVersion);

            Log.w(DEBUG_TAG, String.format("Schema Version changed to %d.", schemaVersion));

            return true;

        } catch (SQLiteException e) {


                Log.e(DEBUG_TAG, "Database not found.", e);

        } finally {

            if (db != null) {
                db.close();
            }
        }
        return false;
    }

    public static boolean backupToExternalStorage(final boolean fromRestore) {


        File sourceDb = new File(MoviesApplication.getDatabasePath());

        File bkpDir = new File(Environment.getExternalStorageDirectory(), BACKUP_DATABASE_DIR);
        if (!bkpDir.exists()) {
            bkpDir.mkdirs();
        }

        if (!sourceDb.exists()) {

            Log.e(DEBUG_TAG, "Source DB not found!");
            return false;
        }

        String fileName = String.format("%s_%s%s",
                new java.util.Date(),
                fromRestore ? "r_" : "", MoviesApplication.DB_NAME);

        return true;
    }

    public static void executeSql(String sql) throws SQLException {
        MoviesApplication.getDaoSession().getDatabase().execSQL(sql);
    }

    public static boolean dbExists() {

        SQLiteDatabase db = null;
        try {

            db = MoviesApplication.getReadonlyDatabase();
            return true;

        } catch (SQLiteException e) {

                Log.e(DEBUG_TAG, "Database not found.", e);

        } finally {

            if (db != null) {
                db.close();
            }
        }
        return false;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("UPDATE_DB","UPDATING DATA BASE");
        switch (oldVersion) {
            case 1:
              onCreate(db);
        }

        db.setVersion(newVersion);
    }

    private void prepareDb() {

        if (!dbExists()) {

            // Banco de dados não existe, crie um novo.
            this.getReadableDatabase().close();

        } else {

            // Verifica se deve atualizar o banco de dados.
            final SQLiteDatabase db = MoviesApplication.getWriteableDatabase();

            try {

                if (db.getVersion() != DaoMaster.SCHEMA_VERSION) {
                    onUpgrade(db, db.getVersion(), DaoMaster.SCHEMA_VERSION);
                }

            } finally {

                if (db != null) {
                    db.close();
                }
            }
        }
    }

    private boolean columnExists(String table, String column) {

        SQLiteDatabase db = null;
        try {

            db = MoviesApplication.getReadonlyDatabase();

            final Cursor cursor = db.rawQuery(String.format("PRAGMA table_info(%s)", table), null);

            try {

                if (cursor.moveToFirst()) {

                    do {

                        if (column.equalsIgnoreCase(cursor.getString(1))) {
                            return true;
                        }

                    } while (cursor.moveToNext());
                }

            } finally {
                cursor.close();
            }

        } catch (SQLiteException e) {

                Log.e(DEBUG_TAG, "Database not found.", e);

        } finally {

            if (db != null) {
                db.close();
            }
        }
        return false;
    }

    private void addColumn(final SQLiteDatabase db, String tableName, String columnName,
                           COLUMN_TYPE columnType) {
        addColumn(db, tableName, columnName, columnType, null);
    }

    private void addColumn(final SQLiteDatabase db, String tableName, String columnName,
                           COLUMN_TYPE columnType, String defaultValueClause) {


        if (columnExists(tableName, columnName)) {
            return;
        }

        db.execSQL(String.format("ALTER TABLE '%s' ADD COLUMN '%s' %s;", tableName, columnName,
                columnType.name()));

        if (defaultValueClause != null) {

            db.execSQL(String.format("UPDATE '%s' SET %s;", tableName, defaultValueClause));
        }
    }

    public void recreateDatabase(SQLiteDatabase db, boolean closeDb) {

     try {

            DaoMaster.dropAllTables(db, true);
            onCreate(db);

        } finally {

            if (closeDb) {
                db.close();
            }
        }
    }

    private static enum COLUMN_TYPE {
        TEXT, INTEGER
    }
}
