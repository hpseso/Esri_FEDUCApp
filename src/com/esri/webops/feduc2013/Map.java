package com.esri.webops.feduc2013;

import android.app.Activity;

import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.map)
public class Map extends Activity {

	static final String TILED_MAP_SERVICE_LAYER = "http://services.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer";

	@ViewById(R.id.map)
	MapView mapView;
	
	@AfterViews
	void loadView() {
		ArcGISTiledMapServiceLayer tiledMapService = new ArcGISTiledMapServiceLayer(TILED_MAP_SERVICE_LAYER);
		mapView.addLayer(tiledMapService);
	}
	
	@Click
	void close_btn () {
		finish();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mapView.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mapView.unpause();
	}
	
}
