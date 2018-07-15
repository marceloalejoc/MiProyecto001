package tk.macx.miproyecto001.entidades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tk.macx.miproyecto001.servicios.Message;

/**
 * Created by usuario on 14-07-18.
 */

public class myDbAdapter {
    public myDbHelper myhelper;
    public myDbAdapter(Context context) {
        myhelper = new myDbHelper(context, null);
    }

    public long insertData(String usuario, String contrasenia) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put("usuario", usuario);
        data.put("contrasenia", contrasenia);
        return db.insert("usuario",null, data);;
    }

    public JSONObject getData(long id) throws JSONException {
        SQLiteDatabase db = myhelper.getReadableDatabase();
        String[] columns = {"id","usuario","contrasenia"};
        Cursor cursor = db.query(true,
                "usuario",
                columns,
                "id=?",
                new String[]{""+id},
                null,null,null, "1");
        JSONObject data = new JSONObject();
        while (cursor.moveToNext())
        {
            data.put("id", cursor.getInt(cursor.getColumnIndex("id")));
            data.put("usuario", cursor.getString(cursor.getColumnIndex("usuario")));
            data.put("contrasenia", cursor.getString(cursor.getColumnIndex("contrasenia")));
        }
        cursor.close();
        return data;
    }

    public JSONArray getData() throws JSONException {
        SQLiteDatabase db = myhelper.getReadableDatabase();
        String[] columns = {"id","usuario","contrasenia"};
        Cursor cursor = db.query(true,
                "usuario", columns,
                null,null,
                null,null,null, null);
        JSONArray data = new JSONArray();
        while (cursor.moveToNext())
        {
            JSONObject item = new JSONObject();
            item.put("id", cursor.getInt(cursor.getColumnIndex("id")));
            item.put("usuario", cursor.getString(cursor.getColumnIndex("usuario")));
            item.put("contrasenia", cursor.getString(cursor.getColumnIndex("contrasenia")));
            data.put(item);
        }
        cursor.close();
        return data;
    }

    static class myDbHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "miDatabase001"; // Database Name
        private static final int DATABASE_Version = 1; // Database Version
        private Context context;

        public myDbHelper(Context context, SQLiteDatabase.CursorFactory factory) {
            super(context, DATABASE_NAME, factory, DATABASE_Version);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                String sql = "CREATE TABLE usuario(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "usuario VARCHAR(64)," +
                        "contrasenia VARCHAR(64)" +
                        ")";
                db.execSQL(sql);
            } catch (Exception e) {
                Message.toast(context,""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                String sql = "DROP TABLE IF EXISTS usuario";
                Message.toast(context,"OnUpgrade");
                db.execSQL(sql);
                onCreate(db);
            }catch (Exception e) {
                Message.toast(context,""+e);
            }
        }
    }
}
