package com.esri.webops.feduc2013;

import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageSwitcher;
import android.widget.ListView;
import android.widget.TextView;

import com.esri.webops.feduc2013.adapter.ExhibitAdapter;
import com.esri.webops.feduc2013.db.ExhibitDB;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.exhibits)
public class ExhibitsAll extends BaseActivity {

	@ViewById
	ListView exhibitList;
	
	@ViewById
	TextView all_txvw,sponsor_txvw,hours_txvw;
	
	@ViewById
	TextView empty_txvw;
	
	@AfterViews
	void loadView() {
		
		adImageSwitcher = (ImageSwitcher) findViewById(R.id.adImageSwitcher);
		initImageSwithcer();
		
		exhibitList.setEmptyView(empty_txvw);
		
		ExhibitDB db = new ExhibitDB(this);
		Cursor c = db.fetchAllRow();
		ExhibitAdapter adapter = new ExhibitAdapter(this, c, true);
		exhibitList.setAdapter(adapter);
		exhibitList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?>  parent, View vw, int position, long id) {
				Intent intent = new Intent(ExhibitsAll.this,ExhibitsDetail_.class);
				intent.putExtra("ID", (int)id);
				View view = ExhibitsGroup.group.getLocalActivityManager()  
			            .startActivity("", intent  
			                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))  
			                    .getDecorView();  
				ExhibitsGroup.group.replaceView(view);
			}
		});
		
		
	}
	
	@Click
	void map_btn() {
		startActivity(new Intent(this,Map_.class));
	}
	
	
	@Click
	void sponsor_txvw() {
		Intent intent = new Intent(this,ExhibitsSponsor_.class);
		View view = ExhibitsGroup.group.getLocalActivityManager()  
	            .startActivity("", intent  
	                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))  
	                    .getDecorView();  
		ExhibitsGroup.group.replaceView(view);
	}
	
	@Click
	void hours_txvw() {
		Intent intent = new Intent(this,ExhibitsHours_.class);
		startActivity(intent);
	}
}
