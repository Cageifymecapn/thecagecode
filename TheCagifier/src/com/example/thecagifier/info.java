package com.example.thecagifier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import com.example.thecagifier.R;

public class info extends Activity{
	
@Override
    public void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
		
    	ImageButton back = (ImageButton) findViewById(R.id.back);        
        back.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		setContentView(R.layout.info);
        		startActivity(new Intent(info.this, MainScreen.class));
        	}
        });
	}
}
