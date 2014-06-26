package com.example.photonotes;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	List<String> notesArray;
	ArrayAdapter<String> adapter;
	SQLiteDatabase db;
	static final int REQUEST_CODE = 1;
	public String mCurrentPhotoPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		notesArray = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, notesArray);
		
		ListView lv = (ListView)findViewById(R.id.listView);
		lv.setAdapter(adapter);
		
		adapter.add("caption1");
		adapter.add("caption2");
		//query db and load captions to adapter

		
	}
	
	public void takeAPhoto() {
		Toast.makeText(MainActivity.this, "take a photo", Toast.LENGTH_LONG).show();
		Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (i.resolveActivity(getPackageManager()) != null) {
			File photoFile = null;
	        try {
	            photoFile = createImageFile();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        // Continue only if the File was successfully created
	        if (photoFile != null) {
	            i.putExtra(MediaStore.EXTRA_OUTPUT,
	                    Uri.fromFile(photoFile));
	            startActivityForResult(i, REQUEST_CODE);
	        }
		}
	}
	
	
	public File createImageFile() throws IOException {
		Toast.makeText(MainActivity.this, "create image file", Toast.LENGTH_LONG).show();
	    // Create an image file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String imageFileName = "JPEG_" + timeStamp + "_";
	    File storageDir = Environment.getExternalStoragePublicDirectory(
	            Environment.DIRECTORY_PICTURES);
	    File image = File.createTempFile(
	        imageFileName,  /* prefix */
	        ".jpg",         /* suffix */
	        storageDir      /* directory */
	    );

	    // Save a file: path for use with ACTION_VIEW intents
	    mCurrentPhotoPath = image.getAbsolutePath();
	    Toast.makeText(MainActivity.this, mCurrentPhotoPath, Toast.LENGTH_LONG).show();
	    return image;
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
	    	Bundle extras = data.getExtras();
	    	Bitmap imageBitmap = (Bitmap) extras.get("data");
	    	BitmapFactory.Options options = new BitmapFactory.Options();
	    	options.inSampleSize = 8;  
	    	Bitmap b = BitmapFactory.decodeFile(mCurrentPhotoPath, options);
	    
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		
		if (id == R.id.action_add) {
			
			takeAPhoto();
			
//			Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//			if (i.resolveActivity(getPackageManager()) != null) {
//				startActivityForResult(i, REQUEST_CODE);
//			}
			
//			adapter.add("caption3");
//			
//			db = new PhotoDbHelper(this).getWritableDatabase();
//			ContentValues contentValues = new ContentValues();
//			contentValues.put(PhotoDbHelper.CAPTION_COLUMN, "caption3");
//			contentValues.put(PhotoDbHelper.FILE_PATH_COLUMN, "imageFilePath3");
//			db.insert(PhotoDbHelper.DATABASE_TABLE, null, contentValues);
			
			
			//Toast.makeText(MainActivity.this, "inserted to db", Toast.LENGTH_LONG).show();
			return true;
		}
		else {
			return super.onOptionsItemSelected(item);
		}
	}

}
