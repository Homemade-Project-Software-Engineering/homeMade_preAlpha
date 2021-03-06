package br.ufc.dc.es.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.ufc.dc.es.model.Login;

public class LoginDAO extends SQLiteOpenHelper {

    private static final String DATABASE = "bd_login";
    private static final int VERSAO = 1;
    private static final String TABELA = "Login";

    public LoginDAO(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE " + TABELA + "(" +
                "id INTEGER PRIMARY KEY, "+
                "email TEXT," +
                "name TEXT NOT NULL, "+
                "crypted_password TEXT, "+
                "salt TEXT, "+
                "created_at TEXT, "+
                "updated_at REAL"+
                ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS " + TABELA;
        db.execSQL(sql);
        onCreate(db);
    }

    public void insert(Login login) {

        ContentValues cv = new ContentValues();
        cv.put("name", login.getName());
        cv.put("email", login.getEmail());
        cv.put("crypted_password", login.getCrypted_password());
        cv.put("salt", login.getSalt());
        cv.put("created_at", login.getCreated_at());
        cv.put("updated_at", login.getUpdated_at());

        getWritableDatabase().insert(TABELA, null, cv);
    }

    //implementar no código atualizar e deletar contas
    /*public void delete(Login login) {

        String args[] = {String.valueOf(login.getEmail())};
        getWritableDatabase().delete(TABELA, "email=?", args);
    }*

    public void update(Login login) {

        ContentValues cv = new ContentValues();
        cv.put("name", login.getName());
        cv.put("email", login.getEmail());
        cv.put("crypted_password", login.getCrypted_password());
        cv.put("salt", login.getSalt());
        cv.put("created_at", login.getCreated_at());
        cv.put("updated_at", login.getUpdated_at());

        String args[] = {String.valueOf(login.getEmail())};
        getWritableDatabase().update(TABELA, cv, "email=?", args);
    }*/

    public boolean fazerLogin(Login login) {

        String sql = "SELECT * FROM " + TABELA + " WHERE EMAIL = ? AND crypted_password = ?;";
        String[] args = {login.getEmail(),login.getCrypted_password()};
        final Cursor cursor = getReadableDatabase().rawQuery(sql, args);

        if(cursor.moveToFirst()){
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }
    }

    public Login getInformacoes(Login login) {

        String sql = "SELECT * FROM " + TABELA + " WHERE EMAIL = ? AND crypted_password = ?;";
        String[] args = {login.getEmail(),login.getCrypted_password()};
        final Cursor cursor = getReadableDatabase().rawQuery(sql, args);

        while(cursor.moveToNext()){
            login.setId(cursor.getInt(cursor.getColumnIndex("id")));
            login.setName(cursor.getString(cursor.getColumnIndex("name")));
        }
        cursor.close();

        return login;
    }
}
