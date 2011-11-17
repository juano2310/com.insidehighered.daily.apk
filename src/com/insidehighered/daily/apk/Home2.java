package com.insidehighered.daily.apk;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import com.insidehigheredapp.daily.apk.R;

public class Home2 extends Activity {
	static NewsDroidDB droidDB;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		if (isOnline(this)) {
			try {		
				droidDB = new NewsDroidDB(this);
				droidDB.deleteallAricles();
				URL url1 = new URL(this.getString(R.string.tab1_url));	
		        updateArticles(this, url1); 
				URL url2 = new URL(this.getString(R.string.tab2_url));	
				updateArticles(this, url2); 
				URL url3 = new URL(this.getString(R.string.tab3_url));	
				updateArticles(this, url3); 			
				URL url4 = new URL(this.getString(R.string.tab4_url));	
				updateArticles(this, url4);
			} catch (MalformedURLException e) {
				Log.e("NewsDroid",e.toString());
			}
		}

		startActivity(new Intent(Home2.this,Tabhub.class));
		Home2.this.finish();	
		finish();		
	}
	
	public void updateArticles(Context ctx, URL url) {
		try {
				droidDB = new NewsDroidDB(ctx);		
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(url.openStream());
				doc.getDocumentElement().normalize();
				
				NodeList nodeList = doc.getElementsByTagName("item");
	            
				for (int i = 0; i < nodeList.getLength(); i++) {
	
					Node node = nodeList.item(i);
					
					Element fstElmnt = (Element) node;
								
					NodeList nameList = fstElmnt.getElementsByTagName("title");
					Element nameElement = (Element) nameList.item(0);
					nameList = nameElement.getChildNodes();
					String title = new String (((Node) nameList.item(0)).getNodeValue());
					
					NodeList dateList = fstElmnt.getElementsByTagName("pubDate");
					Element dateElement = (Element) dateList.item(0);
					dateList = dateElement.getChildNodes();
					String date = new String (((Node) dateList.item(0)).getNodeValue().substring(0, 22));
					
					NodeList infoList = fstElmnt.getElementsByTagName("description");
					Element infoElement = (Element) infoList.item(0);
					infoList = infoElement.getChildNodes();
					String info1 = new String (((Node) infoList.item(0)).getNodeValue());
					String info;
					info1 = info1.replaceAll("more &raquo;","");
					info1 = info1.replaceAll("\\<.*?>","");					
					info1 = Html.fromHtml(info1).toString();
					if (info1.length() > 200) {
						String info2;
						if (title.length() > 110) {
							info2 = new String (info1.toString().substring(72, 272));
						} else {
							info2 = new String (info1.toString().substring(0, 200));
						}
						info = new String (info2 + " ...");
					} else {
						info = new String (info1);
					}
					
					NodeList arturlList = fstElmnt.getElementsByTagName("link");
					Element arturlElement = (Element) arturlList.item(0);
					arturlList = arturlElement.getChildNodes();
					String urlarticle = new String (((Node) arturlList.item(0)).getNodeValue());
					
					droidDB.insertArticle(url.toString(),title,info,date,urlarticle);
				}
			
			} catch (Exception e) {
				System.out.println("XML Pasing Excpetion = " + e);
			}
	}
	
	public static boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected() ) {
            Log.v("NetworkInfo","Connected"); 
			return true;
		} else {
            Log.v("NetworkInfo","Not Connected"); 
            return false;
		}
	}
}