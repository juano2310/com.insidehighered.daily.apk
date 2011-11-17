package com.insidehighered.daily.apk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.insidehigheredapp.daily.apk.R;

public class Tab4 extends ListActivity {
	private List<Article> articles;
    private ArrayAdapter<Article> lista;
	private NewsDroidDB droidDB;
    static final ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();

    @Override
	protected void onCreate(Bundle icicle) {
			super.onCreate(icicle);
			droidDB = new NewsDroidDB(this);
			setContentView(R.layout.articles_list);
			
			list.clear();
			
		    SimpleAdapter adapter = new SimpleAdapter(
		    this, list, R.layout.article_row, new String[] {"title","info","date"},
		    new int[] {R.id.text1,R.id.text2, R.id.text3}
		    );
		   		    
		    articles = droidDB.getArticles(this.getString(R.string.tab4_url));  	
		    for (Article article : articles) {	
			    HashMap<String,String> temp = new HashMap<String,String>();
			    temp.put("title", article.title);
			    temp.put("info", article.info);
			    temp.put("date", article.date);
			    list.add(temp);
		    }  	    
		   
		    lista = new ArrayAdapter<Article>(this, R.layout.article_row, articles);
		    
			setListAdapter(adapter);
			final ListView lv = getListView();
			registerForContextMenu(lv); 
			lv.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener(){                
				public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {
			       onLongListItemClick(lv, v,pos,id);
			       return false;
			      }
			}); 	
	}

	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);              		
        Article articulo = lista.getItem(position);       
        String articleURL = articulo.url;
        Intent imdbIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(articleURL));      
        startActivity(imdbIntent);     
        }
	
    protected void onLongListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);   
        Article articulo = lista.getItem(position);       
        
		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, articulo.title + " at " + articulo.url);
		sendIntent.putExtra(Intent.EXTRA_SUBJECT, articulo.title);
		sendIntent.setType("text/plain");

		startActivity(Intent.createChooser(sendIntent, "Share via"));
        }
    }
	


