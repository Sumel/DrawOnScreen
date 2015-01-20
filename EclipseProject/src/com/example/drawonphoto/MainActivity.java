package com.example.drawonphoto;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

	static final int REQUEST_IMAGE_CAPTURE = 1;
	static final int REQUEST_EDIT_PHOTO = 2;
	
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
	
	public void editClick(View v){
		
		Intent intent = new Intent(this,EditPhotoActivity.class);
    	intent.putExtra(EditPhotoActivity.keyPhoto, editedPhoto);
    	startActivity(intent);

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
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putParcelable("editedImage", editedPhoto);
		outState.putParcelable("rawImage", rawPhoto);
		
	}
}
