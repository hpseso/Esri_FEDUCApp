package com.esri.webops.feduc2013;


import android.app.TabActivity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;

@EActivity(R.layout.home)
public class Home extends TabActivity {
	
	
    
	@AfterViews
	void loadView() {
		addTab("Overview", R.drawable.tab_overview, Overview_.class);
		addTab("Agenda", R.drawable.tab_agenda, AgendaGroup.class);
		
		//addTab("Calendar", R.drawable.tab_calendar, Map_.class);
		addTab("Exhibits", R.drawable.tab_exhibits, ExhibitsGroup.class);
		addTab("Other", R.drawable.tab_other, Other_.class);
		
		int numberOfTabs = getTabHost().getTabWidget().getChildCount();
	    for(int t=0; t<numberOfTabs; t++){
	    	getTabHost().getTabWidget().getChildAt(t).setOnTouchListener(new View.OnTouchListener() {
	            @Override
	            public boolean onTouch(View v, MotionEvent event) {
	                if(event.getAction()==MotionEvent.ACTION_UP){

	                    //String currentSelectedTag = Home.this.getTabHost().getCurrentTabTag();
	                   // String tag = Home.this.getTabHost().getCurrentTabView().getTag().toString();
	                   // Logger.getLogger("ESRI").info("Tags:" + currentSelectedTag + "," + tag);
	                }
	                return false;
	            }
	        });
	    } 
	}
	
	private void addTab(String labelId, int drawableId, Class<?> c) {
		TabHost tabHost = getTabHost();
		Intent intent = new Intent(this, c);
		TabHost.TabSpec spec = tabHost.newTabSpec("tab" + labelId);	
		
		View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
		TextView title = (TextView) tabIndicator.findViewById(R.id.title);
		title.setText(labelId);
		ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
		icon.setImageResource(drawableId);
		
		spec.setIndicator(tabIndicator);
		spec.setContent(intent);
		tabHost.addTab(spec);
	}
	
	
}
