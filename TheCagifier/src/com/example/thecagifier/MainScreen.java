package com.example.thecagifier;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
//import android.hardware.Camera.Face;
import android.media.FaceDetector;
import android.media.FaceDetector.Face;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.thecagifier.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class MainScreen extends Activity {
	
	//Initializes the picture we're going to take
	ImageView Picture;
	
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;
    
    private void dispatchTakePictureIntent(int actionCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, actionCode);
    }
    
    public static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }
    
    private void handleSmallCameraPhoto(Intent intent) {
        Bundle extras = intent.getExtras();
        Bitmap mImageBitmap = (Bitmap) extras.get("data");
        Picture.setImageBitmap(mImageBitmap);
    }
    
//    private File createImageFile() throws IOException {
//        // Create an image file name
//    	File storageDir = new File(
//				Environment.getExternalStoragePublicDirectory(
//			        Environment.DIRECTORY_PICTURES
//			    ), "content://media/internal/images/media");
//        String timeStamp = 
//            new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "IMG_" + timeStamp + "_";
//        File image = File.createTempFile(
//            imageFileName, 
//            ".jpg", 
//            storageDir
//        );
//        String mCurrentPhotoPath = image.getAbsolutePath();
//        return image;
//    }
//    
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

        File image = File.createTempFile(
                imageFileName, 
                ".jpg", 
                storageDir
            );
        Bitmap theImage = (Bitmap) data.getExtras().get("data");
        MediaStore.Images.Media.insertImage(getContentResolver(), theImage, image.getName() , "Made by Cagifier");

        /*
        //Append the file name to the intent
		File f = image;
        Uri contentUri = Uri.fromFile(f);
        data.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);*/
    }
    


    @SuppressLint("CutPasteId") 
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mainscreen);
        Picture = (ImageView) findViewById(R.id.imageView); // Austin : set image in background to photo

        //This where the various buttons are assigned to buttons on the UI 
        ImageButton takephoto = (ImageButton) findViewById(R.id.takepicture);
        ImageButton openGallery = (ImageButton) findViewById(R.id.gallery);
        ImageButton info = (ImageButton) findViewById(R.id.info);
        
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
				//Android has built-in things for taking pictures
				//Intent TakePictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); // Austin : Sets the action to take a picture
				//startActivityForResult(TakePictureIntent, 0);	
				dispatchTakePictureIntent(0);
//				Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); // Austin : Sets the action to take a picture
//				startActivityForResult(intent, 0);		

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
       
        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content_controls);

        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider.show();
        /*mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });*/

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        //findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }

    //Written by Austin

    //Written by Austin

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {    	
    	if(requestCode == 0)
    	{
    		Bitmap theImage = (Bitmap) data.getExtras().get("data"); // Austin : Set Background to the photo
    		Picture.setImageBitmap(theImage);
    		try {
				galleryAddPic(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		
    		FacialRecognition(theImage);
    	}
    	
    	//Everett: The request code for opening the gallery and selecting an image
    	if(requestCode == 1)
    	{
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(
                                   selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();


                //sets theImage to the one of the selected path, and then sets the 
                //background Picture to that theImage
                Bitmap theImage = BitmapFactory.decodeFile(filePath);
                Picture.setImageBitmap(theImage);
                FacialRecognition(theImage);
        }
    }
    

	protected void FacialRecognition(Bitmap theImage)
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
                         
                        canvas.drawRect((int)midPoint.x - eyeDistance ,
                                                        (int)midPoint.y - eyeDistance ,
                                                        (int)midPoint.x + eyeDistance,
                                                        (int)midPoint.y + eyeDistance*2, drawPaint);
                }
        }
        
        String filepath = Environment.getExternalStorageDirectory() + "/facedetect" + System.currentTimeMillis() + ".jpg";
        
        try {
                FileOutputStream fos = new FileOutputStream(filepath);
                 
                bitmap565.compress(CompressFormat.JPEG, 90, fos);
                 
                fos.flush();
                fos.close();
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }
         
       // ImageView imageView = (ImageView)findViewById(R.id.image_view);
         
        Picture.setImageBitmap(bitmap565);
    }
	protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
