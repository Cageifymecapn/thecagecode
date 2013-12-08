package com.example.thecagifier;
import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;

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
PictureCallback jpegCallback;

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
        //Makes camera preview automaticallly come up on surface view
        surfaceView.setVisibility(0);
        
    ImageButton takephoto = (ImageButton) findViewById(R.id.takepicture);
    ImageButton openGallery = (ImageButton) findViewById(R.id.gallery);
    ImageButton info = (ImageButton) findViewById(R.id.info);
    
    
    //Leads to Info Screen
	info.setOnClickListener(new OnClickListener() 
    {
		@Override
		public void onClick(View arg0) {
			setContentView(R.layout.info);
		}
    });
    
    
    //Response to the takepicture button being pushed
    takephoto.setOnClickListener(new OnClickListener() 
    {
		@Override
		public void onClick(View arg0) {
	        takePicture();
	        setContentView(R.layout.editor);
		}
    });
    
    //Everett: Opens the gallery when Gallery button is pressed. 
    //Got help for the Intent from http://stackoverflow.com/questions/18416122/open-gallery-app-in-android
    openGallery.setOnClickListener(new OnClickListener()
    {
    	@Override
    	public void onClick(View arg0) {
    		//Android has something to open the gallery already
    		Intent galleryIntent = new Intent(Intent.ACTION_VIEW, 
    				Uri.parse("content://media/internal/images/media"));
    		startActivity(galleryIntent);
    		startActivityForResult(galleryIntent, 1);
    	}
    });
}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {    
    	if (requestCode==0)
    	{
    		takePicture();
    		}
    	
//    	if(requestCode == 0)
//    	{
//    		Bitmap theImage = (Bitmap) data.getExtras().get("data"); // Austin : Set Background to the photo
//    		Picture.setImageBitmap(theImage);
//    		try {
//				galleryAddPic(data);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//    		FacialRecognition(theImage);
//    	}
    	
    	//Everett: The request code for opening the gallery and selecting an image
    	if(requestCode == 1)
    	{
            setContentView(R.layout.editor);
    	    Uri selectedImageUri = data.getData();
            String selectedImagePath = getPath(selectedImageUri);
            //sets theImage to the one of the selected path, and then sets the 
            //background Picture to that theImage
            Bitmap selectedImage = BitmapFactory.decodeFile(selectedImagePath);
            ImageView editorPicture = (ImageView) findViewById(R.id.imageView);
            editorPicture.setImageBitmap(selectedImage);  
    	}   	
    };
    
    private void galleryAddPic(Intent data) throws IOException{
	//Create an image file name
    String timeStamp = 
        new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    String imageFileName = "IMG_" + timeStamp + "_";
    
	//Sets the storage directory
    File storageDir = new File(
		Environment.getExternalStoragePublicDirectory(
		        Environment.DIRECTORY_PICTURES
		    ), imageFileName);

    File file = File.createTempFile(
            imageFileName, 
            ".jpg", 
            storageDir
        );
    if(!file.exists())
    	file.createNewFile();
    //Append the file name to the intent
	String fileString = file.toString();
    Uri contentUri = Uri.fromFile(file);
    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, contentUri);
    sendBroadcast(mediaScanIntent);

//  Bitmap theImage = (Bitmap) data.getExtras().get("data");
//  MediaStore.Images.Media.insertImage(getContentResolver(), theImage, file.getName() , "Made by Cagifier");

  }
    
    
    @Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
		shutterCallback = new ShutterCallback() {
	        public void onShutter() {
	          // TODO Do something when the shutter closes.
	        }
	      };

	       rawCallback = new PictureCallback() {
	        public void onPictureTaken(byte[] data, Camera camera) {
	          // TODO Do something with the image RAW data.
	        }
	      };

	       jpegCallback = new PictureCallback() {
	        public void onPictureTaken(byte[] data, Camera camera) {
	          // Save the image JPEG data to the SD card
	          FileOutputStream outStream = null;
	          try {outStream.close();

	          } catch (FileNotFoundException e) {
	            Log.e(tag, "File Note Found", e);
	          } catch (IOException e) {
	            Log.e(tag, "IO Exception", e);
	          }
	        }
	        
	      };
	      
	}

    private void takePicture() {
        camera.takePicture(shutterCallback, rawCallback, jpegCallback);
      }
	@Override
	public void surfaceCreated(SurfaceHolder surfaceholder) {
		try{
		     camera = Camera.open();
		     }catch(RuntimeException e){
		     Log.e(tag, "init_camera: " + e);
		     return;
		     }
		     Camera.Parameters param;
		     param = camera.getParameters();
		     //modify parameter
		     param.setPreviewFrameRate(20);
		     param.setPreviewSize(1280, 720);
		     camera.setDisplayOrientation(270);
		     camera.setParameters(param);
		     try {
		camera.setPreviewDisplay(surfaceHolder);
		camera.startPreview();
		} catch (Exception e) {
		Log.e(tag, "init_camera: " + e);
		return;
		}
		  }
	

	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceholder) {
		// TODO Auto-generated method stub
		camera.stopPreview();
		camera.release();
		camera = null;
	}


public String getPath(Uri uri) {
    // just some safety built in 
    if( uri == null ) {
        // TODO perform some logging or show user feedback
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
