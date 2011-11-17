package com.insidehighered.daily.apk;

import com.adwhirl.AdWhirlLayout;
import com.adwhirl.AdWhirlManager;
import com.insidehigheredapp.daily.apk.R;

import android.app.Dialog;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class Tabhub extends TabActivity {
	public void onCreate(Bundle icicle) {
	    super.onCreate(icicle);
	    setContentView(R.layout.main);

	    Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab

	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, Tab1.class);

	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec("artists").setIndicator( res.getText(R.string.tab1_name),
	                      res.getDrawable(R.drawable.ic_tab_artists))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    // Do the same for the other tabs
	    intent = new Intent().setClass(this, Tab2.class);
	    spec = tabHost.newTabSpec("albums").setIndicator(res.getText(R.string.tab2_name),
	                      res.getDrawable(R.drawable.ic_tab_albums))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, Tab3.class);
	    spec = tabHost.newTabSpec("songs").setIndicator(res.getText(R.string.tab3_name),
	                      res.getDrawable(R.drawable.ic_tab_songs))
	                  .setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, Tab4.class);
	    spec = tabHost.newTabSpec("linda").setIndicator(res.getText(R.string.tab4_name),
	                      res.getDrawable(R.drawable.ic_tab_linda))
	                  .setContent(intent);
	    tabHost.addTab(spec);
	    
	    /*
	    intent = new Intent().setClass(this, Tab5.class);
	    spec = tabHost.newTabSpec("amor").setIndicator(res.getText(R.string.tab5_name),
	                      res.getDrawable(R.drawable.ic_tab_amor))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    
	    intent = new Intent().setClass(this, Tab6.class);
	    spec = tabHost.newTabSpec("pupa").setIndicator(res.getText(R.string.tab6_name),
	                      res.getDrawable(R.drawable.ic_tab_pupa))
	                  .setContent(intent);
	    tabHost.addTab(spec);	    
		*/    
	    
	    tabHost.setCurrentTab(0);
	   
        try{
            LinearLayout layout = (LinearLayout)findViewById(R.id.layout_ad);
     	    AdWhirlManager.setConfigExpireTimeout(1000 * 60 * 5);
            AdWhirlLayout adWhirlLayout = new AdWhirlLayout(this, res.getString(R.string.ads_key));
            final int DIP_WIDTH = 320;
            final int DIP_HEIGHT = 52;
            final float DENSITY = getResources().getDisplayMetrics().density;
            int scaledWidth = (int) (DENSITY * DIP_WIDTH + 0.5f);
            int scaledHeight = (int) (DENSITY * DIP_HEIGHT + 0.5f);
            RelativeLayout.LayoutParams adWhirlLayoutParams =
            new RelativeLayout.LayoutParams(scaledWidth, scaledHeight);
            layout.addView(adWhirlLayout, adWhirlLayoutParams);
            layout.invalidate();
        }catch(Exception e){
            Log.e("Test", "Unable to create AdWhirlLayout", e);
        }
	    
	}
	
    public boolean onCreateOptionsMenu(Menu menu){
    	menu.add(0,1,0,"Refresh").setIcon(R.drawable.refresh);
    	menu.add(0,2,0,"About").setIcon(R.drawable.info);
    	menu.add(0,3,0,"Exit").setIcon(R.drawable.exit);
    	return true;
    	}
    
    public boolean onOptionsItemSelected (MenuItem item){
    	switch (item.getItemId()){
	    	case 1:
			int INPUT_REQUEST = 0;
			startActivityForResult(new Intent(Tabhub.this,Home2.class), INPUT_REQUEST);
			finish();
	    	return true;
	    	case 2 :
                //set up dialog
                final Dialog dialog = new Dialog(Tabhub.this);
                dialog.setContentView(R.layout.maindialog);
                dialog.setCancelable(true);
                //dialog.setTitle(Tabhub.this.getString(R.string.about_title));
                //set up image view
                ImageView img = (ImageView) dialog.findViewById(R.id.ImageView01);
                img.setImageResource(R.drawable.about);
                //set up text
                TextView text = (TextView) dialog.findViewById(R.id.TextView01);
                text.setText(Tabhub.this.getString(R.string.about_text));
                //set up button
                Button button = (Button) dialog.findViewById(R.id.Button01);
                button.setOnClickListener(new OnClickListener() {
                public void onClick(View Tubhub) {
                        dialog.cancel();
                    }
                });
                //now that the dialog is set up, it's time to show it    
                dialog.show();
	    	return true;
	    	case 3 :
	    	finish();
	    	return true;
	    	}
    	return false;
    	}
}
