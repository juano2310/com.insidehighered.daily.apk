package com.insidehighered.daily.apk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.insidehigheredapp.daily.apk.R;

public class  Home extends Activity {
	static NewsDroidDB droidDB;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.splash);

		final int welcomeScreenDisplay = 3000;
	
		Thread welcomeThread = new Thread() {

			int wait = 0;

			@Override
			public void run() {
				try {
					super.run();
					while (wait < welcomeScreenDisplay) {
						sleep(100);
						wait += 100;
					}
				} catch (Exception e) {
					System.out.println("EXc=" + e);
				} finally {
					int INPUT_REQUEST = 0;
					startActivityForResult(new Intent(Home.this,Home2.class), INPUT_REQUEST);
					//startActivity(new Intent(Home.this,Tabhub.class));
					finish();
				}
			}
		};
		welcomeThread.start();
        Toast.makeText(this, "Loading", Toast.LENGTH_LONG).show();
        }
	
	public void onReceiveResult(int resultCode, Bundle resultData) {
        if (resultCode == 1) {
        	startActivity(new Intent(Home.this,Tabhub.class));
        	//finish();
    }
	}
}	