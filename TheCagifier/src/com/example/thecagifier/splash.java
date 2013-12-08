package com.example.thecagifier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


public class splash extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.splash);
		
		IntentLauncher launcher = new IntentLauncher();
		launcher.start();
	}
		
	private class IntentLauncher extends Thread{
			public void run(){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		Intent intent = new Intent(splash.this, MainScreen.class);
		splash.this.startActivity(intent);
		splash.this.finish();
			}
		};
		
		
	
@Override
protected void onPause() {
	super.onPause();
}

}