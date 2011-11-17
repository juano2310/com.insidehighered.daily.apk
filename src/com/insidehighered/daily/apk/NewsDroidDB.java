package com.insidehighered.daily.apk;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.insidehigheredapp.daily.apk.R;

class NewsDroidDB extends SQLiteOpenHelper {
    	static final String TAG = "sambasync/DatabaseHelper";
        private static final String DATABASE_NAME = "articulos.db";
        public static final int DATABASE_VERSION = 1;
        public static final String TAB = "tab";        
        public static final String TITLE = "title";
        public static final String INFO = "info";
        public static final String DATE = "date";
        public static final String URL = "url";

        SQLiteDatabase db;
        
        NewsDroidDB(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);               
        }
        
        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(TAG, "DatabaseHelper onCreate called");
        	db.execSQL( "CREATE TABLE articles (id INTEGER PRIMARY KEY AUTOINCREMENT, tab TEXT, title TEXT, info TEXT, date TEXT, url TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        	db.execSQL( "DROP TABLE articles");
        	onCreate(db);
        }

        @Override
        public synchronized void close() {
                db.close();
                super.close();
        }            
        
	public void insertArticle(String tab, String title, String info, String date, String url) {
        Log.d(TAG, "insertNewConnection");
        db = getWritableDatabase();
	
		ContentValues values = new ContentValues();
		values.put(NewsDroidDB.TAB, tab);
		values.put(NewsDroidDB.TITLE, title);
		values.put(NewsDroidDB.INFO, info);
		values.put(NewsDroidDB.DATE, date);
		values.put(NewsDroidDB.URL, url);
		db.insertOrThrow("articles", NewsDroidDB.TAB, values);
        db.close();
	}
	
	public boolean deleteAricles(String tab) {
        Log.d(TAG, "deleteNewConnection");
        db = getWritableDatabase();
        
        return (db.delete("articles", "tab like " + "'%" + tab + "%'", null) > 0);
	}

	public boolean deleteallAricles() {
        Log.d(TAG, "deleteNewConnection");
        db = getWritableDatabase();
        
        return (db.delete("articles", null, null) > 0);
	}
	
	public List<Article> getArticles(String url) {
		ArrayList<Article> articles = new ArrayList<Article>();
        db = getReadableDatabase();
        
		try {
			
			Cursor c = db.query("articles", new String[] {"id", "tab", "title", "info", "date", "url"}, 
	                "tab like " + "'%" + url + "%'", null, null, null, null);

			c.moveToFirst();	
			while (c.isAfterLast() == false) {
				Article article = new Article();
				article.id = c.getInt(0);
				article.tab = c.getString(1);				
				article.title = c.getString(2);
				article.info = c.getString(3);
				article.date = c.getString(4);
				article.url = c.getString(5);
				articles.add(article);
				c.moveToNext();
			}
			c.close();
		} catch (SQLException e) {
			Log.e("NewsDroid", e.toString());
		} 

		return articles;
	}
}
