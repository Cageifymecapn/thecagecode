package com.example.thecagifier;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.example.thecagifier.R;


public class info extends Activity  {

    ImageButton Undo=(ImageButton) findViewById(R.id.imageButton1);
    public void OnCreate (Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.info);
    

    Undo.setOnClickListener(new OnClickListener() 
    {
		@Override
		public void onClick(View arg0) {
			setContentView(R.layout.mainscreen);
		}
    });
}}
