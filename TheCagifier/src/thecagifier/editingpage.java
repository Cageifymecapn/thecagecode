package thecagifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.thecagifier.R;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
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
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;


public class editingpage extends Activity{
int sx, sy;
	Bitmap facemap;
	int coordinates[]={sx,sy};
	@Override
	public void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editor);

		//This gets the intent sent to create this Activity
		Intent intent = getIntent();
		String sentImagePath;
		Bitmap sentImageBitmap;
 {
			


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
			public void onClick(View arg0) {

				BitmapDrawable drawable = (BitmapDrawable) editorImageView.getDrawable();
			    Bitmap image_to_save = drawable.getBitmap();
			    String root = Environment.getExternalStorageDirectory().toString();
			    File myDir = new File(root + "/The_Cagifier");
			    myDir.mkdirs();
			    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
				String imageFileName = "IMG_" + timeStamp + ".jpg";
			    File image=new File(myDir,imageFileName);

			    boolean success = false;

			    // Encode the file as a JPG image.
			    FileOutputStream outStream;
			    try {

			        outStream = new FileOutputStream(image);
			        image_to_save.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
			        /* 100 to keep full quality of the image */

			        outStream.flush();
			        outStream.close();
			        success = true;
			    } catch (FileNotFoundException e) {
			        e.printStackTrace();
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
			    if (success)
			    {
			        Toast.makeText(getApplicationContext(), "Cagification Saved",
			                Toast.LENGTH_LONG).show();
			    } else
			    {
			        Toast.makeText(getApplicationContext(),
			                "Error during image saving", Toast.LENGTH_LONG).show();
			    }
			    sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
			            Uri.parse("file://" + Environment.getExternalStorageDirectory())));

			}
        });
	}
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

	    
	    
	    Drawable Face1 = getResources().getDrawable(R.drawable.cage1);
	    
	    
	   // Draw


	    if(facesFound > 0)
	    {
	    	
	        for(int index=0; index<facesFound; ++index){
	        	
	        	//int randomNum = 1 + (int)(Math.random()*6); 
	        	
	        	int[] morerandom= new int[420];
	    	    int scaledrandomnum= (int)((Math.random())*(morerandom.length));
	    	    int randomNum=scaledrandomnum/60;

	        	
		    	if(randomNum==1)
		    	{
		    		Face1 = getResources().getDrawable(R.drawable.cage1);
		    	}
		    	else if(randomNum==2)
		    	{
		    		Face1 = getResources().getDrawable(R.drawable.cage2);
		    	}
		    	else if(randomNum==3)
		    	{
		    		Face1 = getResources().getDrawable(R.drawable.cage3);
		    	}
		    	else if(randomNum==4)
		    	{
		    		Face1 = getResources().getDrawable(R.drawable.cage4);
		    	}
		    	else if(randomNum==5)
		    	{
		    		Face1 = getResources().getDrawable(R.drawable.cage5);
		    	}
		    	else if(randomNum==6)
		    	{
		    		Face1 = getResources().getDrawable(R.drawable.cage6);
		    	}
		    	else if(randomNum==7)
		    	{
		    		Face1 = getResources().getDrawable(R.drawable.cage6);
		    	}
		    	
	            faces[index].getMidPoint(midPoint);

	            //faces[index].
	            eyeDistance = faces[index].eyesDistance();
	            eyeDistance22 = (int) faces[index].eyesDistance();
	            confidence = faces[index].confidence();
	           int y =  (int) faces[index].pose(Face.EULER_Y);
	           int z = (int)(eyeDistance22)/4;
	            Log.i("FaceDetector",
	            		"Confidence: " + confidence +
	            		", Eye distance: " + eyeDistance +
	            		", Mid Point: (" + midPoint.x + ", " + midPoint.y + ")");

	            Face1.setBounds((int)midPoint.x - eyeDistance22,
	            		(int)midPoint.y - eyeDistance22,
	            		(int)midPoint.x + eyeDistance22,
	            		(int)midPoint.y + eyeDistance22*2-z);

	            Face1.draw(canvas);

	        }
	    }

	    Picture.setImageBitmap(bitmap565);
	}


}

