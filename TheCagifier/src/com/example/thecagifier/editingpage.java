package com.example.thecagifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.media.FaceDetector.Face;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.Drawable;


public class editingpage extends Activity{
	
	Bitmap facemap;
	
	@Override
	public void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editor);
		
		//This gets the intent sent to create this Activity
		Intent intent = getIntent();
		String sentImagePath;
		Bitmap sentImageBitmap;
		
	
		final ImageView editorImageView = (ImageView) findViewById(R.id.imageView);
		if(intent.getStringExtra("galleryImageUriString") != null)
		{
			sentImagePath = intent.getStringExtra("galleryImageUriString");
			sentImageBitmap = BitmapFactory.decodeFile(sentImagePath);
	        
			//sets the background Picture to that sentImageBitmap
	        editorImageView.setImageBitmap(sentImageBitmap);
	        facemap = sentImageBitmap;
	        FacialRecognition(sentImageBitmap, editorImageView);
	        
		}
		else if(intent.getStringExtra("takenImageUriString") != null)
		{
			sentImagePath = intent.getStringExtra("takenImageUriString");
			sentImageBitmap = BitmapFactory.decodeFile(sentImagePath);
			
			//This rotates the Bitmap 90 degrees
			Matrix matrix = new Matrix();
			matrix.postRotate(-90);
			Bitmap scaledBitmap = Bitmap.createScaledBitmap(sentImageBitmap,sentImageBitmap.getWidth(),sentImageBitmap.getHeight(), true);
			Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap , 0, 0, scaledBitmap .getWidth(), scaledBitmap .getHeight(), matrix, true);
			
	        editorImageView.setImageBitmap(rotatedBitmap);
	        facemap = rotatedBitmap;
	        FacialRecognition(rotatedBitmap, editorImageView);
		}
		
		editorImageView.setDrawingCacheEnabled(true);
		editorImageView.buildDrawingCache();
		
		
		
			
				
		//Bitmap imageViewBitmap = editorImageView.getDrawingCache();
		//FacialRecognition(imageViewBitmap, editorImageView);
        
        ImageButton undo = (ImageButton) findViewById(R.id.imageButton3);
        ImageButton download = (ImageButton) findViewById(R.id.imageButton1);
        
        editorImageView.setOnTouchListener(new OnTouchListener()
        {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				int x = (int)event.getX();
			    int y = (int)event.getY();
			    
				int action = event.getAction() & MotionEvent.ACTION_MASK;
	            switch (action) {
	            case MotionEvent.ACTION_UP: {
	                int count = event.getPointerCount();
	                Log.v("count >>", count + "");
	                if (count == 2) {
	                	
	                		FaceDetector detector = new FaceDetector(facemap.getWidth(), facemap.getHeight(),5);
	                		Face[] faces = new Face[5];
	                		Bitmap bitmap565 = Bitmap.createBitmap(facemap.getWidth(), facemap.getHeight(), Config.RGB_565);
	                		Drawable Face1 = getResources().getDrawable(R.drawable.cage1);
	                		Canvas facecanvas = new Canvas();
	                	    facecanvas.setBitmap(bitmap565);
	                	    
	                	    int facesFound = detector.findFaces(bitmap565, faces);
	                	    PointF midPoint = new PointF();
	                	    float eyeDistance = 0.0f;
	                	    float confidence = 0.0f;
	                	    
	                	    if(facesFound > 0)
	                	    { 	
	                	        for(int index=0; index<facesFound; ++index){
	                	            faces[index].getMidPoint(midPoint);
	                	            eyeDistance = faces[index].eyesDistance();
	                	            confidence = faces[index].confidence();
	                	             
	                	            Log.i("FaceDetector",
	                	            		"Confidence: " + confidence +
	                	            		", Eye distance: " + eyeDistance +
	                	            		", Mid Point: (" + midPoint.x + ", " + midPoint.y + ")");
	                	            Face1.draw(facecanvas);
//	                	            drawRect((float)midPoint.x - eyeDistance ,
	                	            		//(float)midPoint.y - eyeDistance ,
	                	            		//(float)midPoint.x + eyeDistance,
	                	            	//	(float)midPoint.y + eyeDistance*(x), drawPaint);
	                	        }
	                	    }
	                	    
	                	    editorImageView.setImageBitmap(facemap);
	                    }
	                else
	                {
	                    Log.v("count not equal to 2","not 2");
	                }
	                break;
	            }
	            }       
	            return true;
	            
			}
       });
        
        undo.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
        		startActivity(new Intent(editingpage.this, MainScreen.class));

			}
        });
        
        download.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View v) {
				final Bitmap savedImage = editorImageView.getDrawingCache();
				
				
				
			    String path2 = "content://media/internal/images/media" + ".jpg";
			    //String filepath = Environment.getExternalStorageDirectory() + "/facedetect" + System.currentTimeMillis() + ".jpg";
			    
			    try {
			            FileOutputStream fos = new FileOutputStream(path2);
			            savedImage.compress(CompressFormat.JPEG, 90, fos);
			            fos.flush();
			            fos.close();
			    } catch (FileNotFoundException e) {
			            e.printStackTrace();
			    } catch (IOException e) {
			            e.printStackTrace();
			    }
				
				
				
//				String timeStamp = "TIME"; //new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//				String imageFileName = "IMG_" + timeStamp + "_";
//				File storageDir = new File(imageFileName);
//				try {
//					FileOutputStream out = new FileOutputStream(storageDir);
//					savedImage.compress(Bitmap.CompressFormat.JPEG, 100, out);
//					out.close();
//				} catch (FileNotFoundException e) {
//					//Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					//Auto-generated catch block
//					e.printStackTrace();
//				}
//				Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//				Uri contentUri = Uri.fromFile(storageDir);
//			    mediaScanIntent.setData(contentUri);
//			    sendBroadcast(mediaScanIntent);
			}
        });
	}
		
	protected void FacialRecognition(Bitmap theImage, ImageView Picture)
	{
		int max = 5;
		int width = theImage.getWidth();
	    int height = theImage.getHeight();
	     
	    FaceDetector detector = new FaceDetector(width, height,5);
	    Face[] faces = new Face[max];
	    Bitmap bitmap565 = Bitmap.createBitmap(width, height, Config.RGB_565);
	    
	    Paint ditherPaint = new Paint();
	    Paint drawPaint = new Paint();
	     
	    ditherPaint.setDither(true);
	    drawPaint.setColor(Color.YELLOW);
	    drawPaint.setStyle(Paint.Style.STROKE);
	    drawPaint.setStrokeWidth((float) 1.5);
	    
	    Canvas canvas = new Canvas();
	    canvas.setBitmap(bitmap565);
	    canvas.drawBitmap(theImage, 0, 0, ditherPaint);
	     
	    int facesFound = detector.findFaces(bitmap565, faces);
	    PointF midPoint = new PointF();
	    float eyeDistance = 0.0f;
	    int eyeDistance22 = 0;
	    float confidence = 0.0f;
	    float x = 1.6f;
	    
	    Drawable Face1 = getResources().getDrawable(R.drawable.cage6);
	   // Draw
	    
	    
	    if(facesFound > 0)
	    { 	
	        for(int index=0; index<facesFound; ++index){
	            faces[index].getMidPoint(midPoint);
	            
	            //faces[index].
	            eyeDistance = faces[index].eyesDistance();
	            eyeDistance22 = (int) faces[index].eyesDistance();
	            confidence = faces[index].confidence();
	             
	            Log.i("FaceDetector",
	            		"Confidence: " + confidence +
	            		", Eye distance: " + eyeDistance +
	            		", Mid Point: (" + midPoint.x + ", " + midPoint.y + ")");
//	            canvas.drawRect((float)midPoint.x - eyeDistance ,
//	            		(float)midPoint.y - eyeDistance ,
//	            		(float)midPoint.x + eyeDistance,
//	            		(float)midPoint.y + eyeDistance*(x), drawPaint);
	            
	            Face1.setBounds((int)midPoint.x - eyeDistance22,
	            		(int)midPoint.y - eyeDistance22,
	            		(int)midPoint.x + eyeDistance22,
	            		(int)midPoint.y + eyeDistance22*2 - (int)eyeDistance22/2);
	            Face1.draw(canvas);
	            
	        }
	    }
//	    String path2 = "content://media/internal/images/media" + ".jpg";
//	    //String filepath = Environment.getExternalStorageDirectory() + "/facedetect" + System.currentTimeMillis() + ".jpg";
//	    
//	    try {
//	            FileOutputStream fos = new FileOutputStream(path2);
//	            bitmap565.compress(CompressFormat.JPEG, 90, fos);
//	            fos.flush();
//	            fos.close();
//	    } catch (FileNotFoundException e) {
//	            e.printStackTrace();
//	    } catch (IOException e) {
//	            e.printStackTrace();
//	    }
	   // ImageView imageView = (ImageView)findViewById(R.id.image_view);
	    Picture.setImageBitmap(bitmap565);
	}
}
	//Everett: Pretty sure this is useless...
