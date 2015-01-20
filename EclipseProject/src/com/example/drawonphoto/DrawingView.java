package com.example.drawonphoto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class DrawingView extends ImageView {
	
	private Paint paint = new Paint();
	private Path path = new Path();
	
	private Bitmap bitMap;
	
	public DrawingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		paint.setAntiAlias(true);
		
		paint.setStrokeWidth(5);
		paint.setColor(Color.RED);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		//canvas = new Canvas(((BitmapDrawable)getDrawable()).getBitmap());
		//canvas.setBitmap(((BitmapDrawable)getDrawable()).getBitmap());
		if(bitMap!=null){
			Canvas singleCanvas = new Canvas();    
			singleCanvas.setBitmap(bitMap); 
			singleCanvas.drawPath(path,paint);
		}
		
		//if(bitMap!=null)
			//canvas.setBitmap(bitMap);
		
		super.onDraw(canvas);
		canvas.drawPath(path, paint);
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float eX = event.getX();
		float eY = event.getY();
		
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				path.moveTo(eX, eY);
				return true;
			case MotionEvent.ACTION_MOVE:
				path.lineTo(eX, eY);
				break;
			default:
				return false;
		}
		
		invalidate();
		return super.onTouchEvent(event);
	}
	
	@Override
	public void setImageBitmap(Bitmap bm) {
		super.setImageBitmap(bm);
		bitMap = bm;
	}
	
}
