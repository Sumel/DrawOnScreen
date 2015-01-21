package com.example.drawonphoto;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class EditPhotoActivity extends Activity {
	public static final String keyPhoto = "photo";
	Bitmap basePhoto;
	Bitmap editedPhoto;
	Canvas editCanvas;
	DrawingView drawingView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_photo);
		
		drawingView = (DrawingView) findViewById(R.id.drawingView1);
	
		if (savedInstanceState != null){
			
			basePhoto = (Bitmap) savedInstanceState.getParcelable("baseImage");
			editedPhoto = (Bitmap) savedInstanceState.getParcelable("editedImage");
			if(editedPhoto.isRecycled()){
				System.out.println("lol, recycled in activity");
			}
		}
		else{
			editedPhoto = basePhoto = getIntent().getParcelableExtra(keyPhoto);
		}
		
		drawingView.setImageBitmap(editedPhoto);
	}
	
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		//editedPhoto = ((BitmapDrawable)drawingView.getDrawable()).getBitmap();
		
		outState.putParcelable("editedImage", drawingView.getBitmap());
		outState.putParcelable("baseImage", basePhoto);
		//drawingView.get
		//drawingView.get
	}
	
	@Override
	protected void onPause() {
		editedPhoto = basePhoto = ((BitmapDrawable)drawingView.getDrawable()).getBitmap();
		super.onPause();
	}
	/*
	@Override
	public void finishActivity(int requestCode) {
		editedPhoto = drawingView.getBitmap();
		Intent resultIntent = new Intent();
		resultIntent.putExtra("photo",editedPhoto);
		setResult(Activity.RESULT_OK, resultIntent);
		
		
		super.finishActivity(requestCode);
	}
	*/
	//@Override
	//public void finish() {
		//finishActivity(MainActivity.REQUEST_EDIT_PHOTO);
		//super.finish();
	//}
	
	public void Cofam(View v)
	{
		editedPhoto = drawingView.getBitmap();
		Intent resultIntent = new Intent();
		resultIntent.putExtra("photo",editedPhoto);
		setResult(Activity.RESULT_OK, resultIntent);
		
		finish();
		return;
	}
	
	
}
