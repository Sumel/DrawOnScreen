package com.example.drawonphoto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

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
		
		
		
		//setting up seekbar
		
		final SeekBar sk=(SeekBar) findViewById(R.id.seekBar1); 
		
		sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				drawingView.SetStroke(progress);
			}
		});      
		
		ListView listView = (ListView) findViewById(R.id.listView1);
		
		String[] arr = {"Red", "Blue", "Green"};
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_view_item,arr);
		
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {  
	    	   public void onItemClick(AdapterView parentView, View childView, 
	    	                                                         int position, long id) 
	    	   {  
	    		   TextView tv = (TextView)childView;
	    		   
	    		   String a = tv.getText().toString();
	    		   
	    		   if(a == "Red")
	    		   {
	    			  drawingView.SetColor(Color.RED); 
	    		   }
	    		   else if (a == "Blue")
	    		   {
	    			   drawingView.SetColor(Color.BLUE);
	    		   }
	    		   else if (a == "Green")
	    		   {
	    			   drawingView.SetColor(Color.GREEN);
	    		   }
	    	   }

	    	   public void onNothingSelected(AdapterView parentView) {  

	    	   }  
	    	});
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
	
	public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

		 final float densityMultiplier = context.getResources().getDisplayMetrics().density;        

		 int h= (int) (newHeight*densityMultiplier);
		 int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

		 photo=Bitmap.createScaledBitmap(photo, w, h, true);

		 return photo;
	 }
	
	public void ReturnPhoto(View v)
	{
		editedPhoto = drawingView.getBitmap();
		Intent resultIntent = new Intent();
		editedPhoto = scaleDownBitmap(editedPhoto, 300, this);
		resultIntent.putExtra("photo",editedPhoto);
		setResult(Activity.RESULT_OK, resultIntent);
		
		finish();
		return;
	}
	
	
}
