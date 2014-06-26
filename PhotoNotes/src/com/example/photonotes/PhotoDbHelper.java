package com.example.photonotes;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class PhotoDbHelper extends SQLiteOpenHelper {
	public static final String line_COLUMN = "line";
	public static final String CAPTION_COLUMN = "CAPTION_COLUMN";
	public static final String FILE_PATH_COLUMN = "FILE_PATH_COLUMN";
	
	public static final String DATABASE_TABLE = "Photo";
	public static final int DATABASE_VERSION = 1;
	private static final String DATABASE_CREATE = String.format(
			"CREATE TABLE %s (" +
			"  %s integer primary key autoincrement, " +
			"  %s text," +
			"  %s text)",
			DATABASE_TABLE, line_COLUMN, CAPTION_COLUMN, FILE_PATH_COLUMN);
	
	
	public PhotoDbHelper(Context context) {
		super(context, DATABASE_TABLE, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE);
		onCreate(db);	
	}

}
