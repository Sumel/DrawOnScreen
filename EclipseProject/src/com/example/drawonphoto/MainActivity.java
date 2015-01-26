package com.example.drawonphoto;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

	public static final int REQUEST_IMAGE_CAPTURE = 1;
	public static final int REQUEST_EDIT_PHOTO = 2;
	
	Bitmap rawPhoto = null;
	Bitmap editedPhoto = null;
	ImageView imageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		imageView = (ImageView) findViewById(R.id.imageView1);
		
		if (savedInstanceState != null){
			rawPhoto = (Bitmap) savedInstanceState.getParcelable("rawImage");
			editedPhoto = (Bitmap) savedInstanceState.getParcelable("editedImage");
			if(editedPhoto!=null){
				imageView.setImageBitmap(editedPhoto);
			}
		}
	}
	
	public void photoClick(View v){
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if(intent.resolveActivity(getPackageManager()) != null){
			startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
		}
	}
	
	public void saveClick(View v){
		String path = Environment.getExternalStorageDirectory().toString();
		path += "/Smieci/DrawOnPhoto";
	
		
		File f = new File(path);
		
		if(!f.exists() || !f.isDirectory()){
			f.mkdirs();
		}
		
		File children[] = f.listFiles();
		
		int counter = 0;
		if(children != null)
		{
			counter = children.length;
		}
		File file = new File(path, "ProfessionalPhoto"+ counter +".png"); // the File to save to
		
		FileOutputStream out = null;
		try {
		    out = new FileOutputStream(file);
		    editedPhoto.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
		    // PNG is a lossless format, the compression factor (100) is ignored
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    try {
		        if (out != null) {
		            out.close();
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
		
	}
	
	public void editClick(View v){
		
		Intent intent = new Intent(this,EditPhotoActivity.class);
    	intent.putExtra(EditPhotoActivity.keyPhoto, editedPhoto);
    	if(intent.resolveActivity(getPackageManager()) != null){
    		startActivityForResult(intent, REQUEST_EDIT_PHOTO);
    	}
    	/*
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.
		if(intent.resolveActivity(getPackageManager()) != null){
			startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
		}
		*/
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
			Bundle extras = data.getExtras();
			rawPhoto = editedPhoto = (Bitmap) extras.get("data");
			ImageView imageView = (ImageView) findViewById(R.id.imageView1);
			imageView.setImageBitmap(editedPhoto);
		}
		else if(requestCode == REQUEST_EDIT_PHOTO && resultCode == RESULT_OK){
			Bundle extras = data.getExtras();
			editedPhoto = (Bitmap) extras.get("photo");
			ImageView imageView = (ImageView) findViewById(R.id.imageView1);
			imageView.setImageBitmap(editedPhoto);
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putParcelable("editedImage", editedPhoto);
		outState.putParcelable("rawImage", rawPhoto);
		
	}
	
	
}
