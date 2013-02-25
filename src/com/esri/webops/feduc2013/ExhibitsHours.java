package com.esri.webops.feduc2013;


import android.app.Activity;
import android.view.View;
import android.widget.Button;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.exhibits_hours)
public class ExhibitsHours extends Activity {

	@ViewById
	Button map_btn;
	
	@AfterViews
	void loadView() {
        if (map_btn != null)
		    map_btn.setVisibility(View.GONE);
	}
	
}
