package com.example.thecagifier;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;

import android.util.Log;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.media.FaceDetector.Face;
import android.net.Uri;
import android.os.Environment;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;

public class editorscreen extends Activity{
ImageView Picture;





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

}
