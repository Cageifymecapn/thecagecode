package com.example.thecagifier;
import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.graphics.BitmapFactory;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
//import android.hardware.Camera.Face;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainScreen extends Activity implements SurfaceHolder.Callback {
	
	TextView testView;
	
	Camera camera;
	SurfaceView surfaceView;
	SurfaceHolder surfaceHolder;
	PictureCallback rawCallback;
	ShutterCallback shutterCallback;
	Camera.PictureCallback jpegCallback;
	PictureCallback postviewCallback;
	int currentCameraId=Camera.CameraInfo.CAMERA_FACING_BACK;
	
	private final String tag = "mainscreen";
	

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainscreen);
        
        surfaceView = (SurfaceView)findViewById(R.id.surfaceView1);
        surfaceHolder = surfaceView.getHolder();
        
        
        //pushed camera frames to screen
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        //Makes camera preview automatically come up on surface view
        surfaceView.setVisibility(0);
        
        ImageButton takephoto = (ImageButton) findViewById(R.id.takepicture);
        ImageButton openGallery = (ImageButton) findViewById(R.id.gallery);
        ImageButton info = (ImageButton) findViewById(R.id.info);
        ImageButton camswitch = (ImageButton) findViewById(R.id.imageButton1);
        
    
	    //Leads to Info Screen
		info.setOnClickListener(new OnClickListener() 
	    {
			@Override
			public void onClick(View arg0) {
				//IT WAS NICKS FAULT (jk Nick)
//				setContentView(R.layout.info);
				//^^^This just changes the layout of the current activity
				//It DOESN'T actually change the activity. 
				//That's why the other screens' onCreates aren't working right
				//The code below DOES, however
				startActivity(new Intent(MainScreen.this, info.class));
			}
	    });
		  
	    //Response to the takepicture button being pushed
	    takephoto.setOnClickListener(new OnClickListener() 
	    {
			@Override
			public void onClick(View arg0) {
		        camera.takePicture(null, null, jpegCallback);
		        
			}
	    });
	    
	    //Everett: Opens the gallery when Gallery button is pressed. 
	    //Got help for the Intent from http://stackoverflow.com/questions/18416122/open-gallery-app-in-android
	    openGallery.setOnClickListener(new OnClickListener()
	    {
	    	@Override
	    	public void onClick(View arg0) {
	    		//Android has something to open the gallery already
	    		Intent galleryIntent = new Intent(Intent.ACTION_PICK, 
	    				Uri.parse("content://media/internal/images/media"));
	    		startActivityForResult(galleryIntent, 1);
	    	}
	    });
	    
	    camswitch.setOnClickListener(new View.OnClickListener() 
	    {
	    	@Override
	    	public void onClick(View arg0) 
	    	{
	    	
	    	    camera.stopPreview();
	    	//NB: if you don't release the current camera before switching, you app will crash
	    	    camera.release();

	    	//swap the id of the camera to be used
	    	if(currentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK)
	    	{
	    	    currentCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
	    	}
	    	else {
	    	    currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
	    	}
	    	camera = Camera.open(currentCameraId);

	    	setCameraDisplayOrientation(MainScreen.this, currentCameraId, camera);
	    	try 
	    	{
	    	    camera.setPreviewDisplay(surfaceHolder);  	    
	    	} catch (IOException e) {
	    	    e.printStackTrace();
	    	}
	    	camera.startPreview();
	    	}
	    });
	    
    }
	    public static void setCameraDisplayOrientation(Activity activity,
	            int cameraId, android.hardware.Camera camera) {
	        android.hardware.Camera.CameraInfo info =
	                new android.hardware.Camera.CameraInfo();
	        android.hardware.Camera.getCameraInfo(cameraId, info);
	        int rotation = activity.getWindowManager().getDefaultDisplay()
	                .getRotation();
	        int degrees = 0;
	        switch (rotation) 
	        {
	            case Surface.ROTATION_0: degrees = 0; break;
	            case Surface.ROTATION_90: degrees = 90; break;
	            case Surface.ROTATION_180: degrees = 180; break;
	            case Surface.ROTATION_270: degrees = 270; break;
	        }

	        int result;
	        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) 
	        {
	            result = (info.orientation + degrees) % 360;
	            result = (360 - result) % 360;  // compensate the mirror
	        } else {  // back-facing
	            result = (info.orientation - degrees + 360) % 360;
	        }
	        camera.setDisplayOrientation(result);
	    }
    


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {    
    	if (requestCode==0)
    	{
//    		Bundle extras = data.getExtras();
//    		Bitmap pictureTakenBitmap = (Bitmap) extras.get("data");
//    		int width = pictureTakenBitmap.getWidth();
//    		int height = pictureTakenBitmap.getHeight();
//    		ImageView editorPicture = (ImageView) findViewById(R.id.imageView);
//            editorPicture.setImageBitmap(pictureTakenBitmap);
    		setContentView(R.layout.editor);
    		Uri selectedImageUri = data.getData();
            String selectedImagePath = getPath(selectedImageUri);
            //sets theImage to the one of the selected path, and then sets the 
            //background Picture to that theImage
            Bitmap selectedImage = BitmapFactory.decodeFile(selectedImagePath);
            ImageView editorPicture = (ImageView) findViewById(R.id.imageView);
            editorPicture.setImageBitmap(selectedImage);
    	}
    	
    	//Everett: The request code for opening the gallery and selecting an image
    	if(requestCode == 1)
    	{
    		Uri selectedImageUri = data.getData();
            String selectedImagePath = getPath(selectedImageUri);
            
            //This doesn't quite work
            //startActivity(new Intent(MainScreen.this, editingpage.class));
            //Need to send info with new Intent
            //Intent changeActivity = new Intent(MainScreen.this, editingpage.class);
            //changeActivity.putExtra("ImageBitmap", selectedImage);
            Intent changeActivity = new Intent(MainScreen.this, editingpage.class);
            changeActivity.putExtra("galleryImageUriString", selectedImagePath);
            MainScreen.this.startActivity(changeActivity);
            
            
    	}
    	if(requestCode == 2)
    	{
    		//This adds the photo to the gallery
    	}
    };

//    private void galleryAddPic(Intent data) throws IOException{
//	//Create an image file name
//    String timeStamp = 
//        new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//    String imageFileName = "IMG_" + timeStamp + "_";
//    
//	//Sets the storage directory
//    File storageDir = new File(
//		Environment.getExternalStoragePublicDirectory(
//		        Environment.DIRECTORY_PICTURES
//		    ), imageFileName);
//
//    File file = File.createTempFile(
//            imageFileName, 
//            ".jpg", 
//            storageDir
//        );
//    if(!file.exists())
//    	file.createNewFile();
//    //Append the file name to the intent
//	String fileString = file.toString();
//    Uri contentUri = Uri.fromFile(file);
//    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, contentUri);
//    startActivityForResult(mediaScanIntent, 2);

//  Bitmap theImage = (Bitmap) data.getExtras().get("data");
//  MediaStore.Images.Media.insertImage(getContentResolver(), theImage, file.getName() , "Made by Cagifier");


	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	     jpegCallback = new PictureCallback() {
	    	 public void onPictureTaken(byte[] data, Camera camera) {
	    		 //Save the image JPEG data to the SD card
	    		 FileOutputStream outStream = null;
	    		 try {
	    			 String path = Environment.getExternalStorageDirectory() +
	    					 "/test.jpg";
	    			 outStream = new FileOutputStream(path);
	    			 outStream.write(data);
	    			 outStream.close();
	    			 
	    			 //We send the path to the byte[] data
	    			 Intent changeActivity = new Intent(MainScreen.this, editingpage.class);
	    	         changeActivity.putExtra("takenImageUriString", path);
	    	         MainScreen.this.startActivity(changeActivity);	    			 
	    		 } catch (FileNotFoundException e) {
	    			 Log.e(tag, "File Note Found", e);
	    		 } catch (IOException e) {
	    			 Log.e(tag, "IO Exception", e);
	    		 }
	    		 //startActivity(new Intent(MainScreen.this, editingpage.class));
	    	 }
	     };
	     postviewCallback = new PictureCallback() {
	 		public void onPictureTaken(byte[] data, Camera camera) {			
	 	        //sets theImage to the one of the selected path, and then sets the 
	 	        //background Picture to that theImage
	 	        Bitmap pictureTaken = BitmapFactory.decodeByteArray(data, 0, data.length);
	 	        ImageView editorPicture = (ImageView) findViewById(R.id.imageView);
	 	        editorPicture.setImageBitmap(pictureTaken);	        
	 		}
	 	};
	 	//setContentView(R.layout.editor);
	}

    
	@Override
	public void surfaceCreated(SurfaceHolder surfaceholder) {
		try{
		     camera = Camera.open();
		     setCameraDisplayOrientation(MainScreen.this, currentCameraId, camera);
		    	try 
		    	{

		    	    camera.setPreviewDisplay(surfaceHolder);
		    	    
		    	} catch (IOException e) {
		    	    e.printStackTrace();
		    	}
		     
		}catch(RuntimeException e){
			Log.e(tag, "init_camera: " + e);
			return;
		}
		Camera.Parameters param;
		param = camera.getParameters();
		//modify parameter
		param.setPreviewFrameRate(30);
		int w = getResources().getDisplayMetrics().widthPixels;
        int h = getResources().getDisplayMetrics().heightPixels;
                try{
         param.setPictureSize(h, w);
         camera.setParameters(param);
         } catch (Exception e) {
         e.printStackTrace();
                }
                
//		camera.setDisplayOrientation(270);
		try {
			camera.setPreviewDisplay(surfaceHolder);
		} catch (Exception e) {
			Log.e(tag, "init_camera: " + e);
		    return;
		}
	    camera.startPreview();

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceholder) {
		camera.stopPreview();
		camera.release();
		camera = null;
	}


public String getPath(Uri uri) {
    // just some safety built in 
    if( uri == null ) {
        //perform some logging or show user feedback
        return null;
    }
    // try to retrieve the image from the media store first
    // this will only work for images selected from gallery
    String[] projection = { MediaStore.Images.Media.DATA };
    Cursor cursor = managedQuery(uri, projection, null, null, null);
    if( cursor != null ){
        int column_index = cursor
        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    // this is our fallback here
    return uri.getPath();
	}
}
