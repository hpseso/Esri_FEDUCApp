package com.esri.webops.feduc2013;

import java.util.logging.Level;
import java.util.logging.Logger;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.FeatureSet;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.tasks.ags.query.Query;
import com.esri.core.tasks.ags.query.QueryTask;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.map)
public class Map extends Activity {

	double kFeatureXOffset =500.0;
	double kFeatureYOffset = 1.5 * kFeatureXOffset;
	
	static final String WORLD_TOPO = "http://services.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer";
	static final String WORLD_TRANSPORTATION = "http://services.arcgisonline.com/ArcGIS/rest/services/Reference/World_Transportation/MapServer";
	static final String WORLD_BOUNDRIES = "http://services.arcgisonline.com/ArcGIS/rest/services/Reference/World_Boundaries_and_Places/MapServer";
	static final String IMAGERY_URL = "http://services.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer";
	
	static final String FLOOR_PLAN1 = "http://mwoapps.esri.com/ArcGIS/rest/services/pug/MapServer";
	static final String POI = "http://mwoapps.esri.com/ArcGIS/rest/services/PUG_POIs/MapServer/0";
	
	// map extent
	static final double xmin=-10615493.5817852,ymin=3471422.11536868,xmax=-10614871.8097083,ymax=3472043.88744556;
	// feature extent
	static final double fxmin=-10617113.977765437,fymin=3473111.652856817,fxmax=-10615948.313084058,fymax=3471649.794690824;
	static final 
	
	Envelope mapEnvelop = new Envelope(xmin, ymin, xmax, ymax);
	Envelope featureEnvelop = new Envelope(fxmin, fymin, fxmax,fymax);
	
	@Extra("MAP_TYPE")
	Integer loadMap = 0;
	
	@Extra("AGENDA_POINT")
	Point agendaPoint;
	
	@Extra("EXHIBIT_POINT")
	Point exhibitPoint;
	
	@ViewById(R.id.map)
	MapView mapView;
	
	@ViewById
	TextView world_topo_txvw,imagery_txvw,osm_txvw;
	
	@ViewById
	TextView map_label;
	
	@ViewById
	LinearLayout info_view,map_menu_container;
	
	@ViewById
	Button m_btn;
	
	ArcGISDynamicMapServiceLayer floorPlanLayer,worldTopoLayer;
	ArcGISTiledMapServiceLayer  worldImageryLayer,worldTransportationLayer,worldBoundryLayer;
	GraphicsLayer graphicsLayer;
	
	PictureMarkerSymbol greenMarker,greyMarker,brownMarker,redMarker,blueMarker;
	
	@SuppressWarnings("serial")
	@AfterViews
	void loadView() {
		
		
		
		greenMarker = new PictureMarkerSymbol(this.getResources().getDrawable(R.drawable.map_locator_green));
		greyMarker = new PictureMarkerSymbol(this.getResources().getDrawable(R.drawable.map_locator_grey));
		brownMarker = new PictureMarkerSymbol(this.getResources().getDrawable(R.drawable.map_locator_brown));
		redMarker = new PictureMarkerSymbol(this.getResources().getDrawable(R.drawable.map_locator_red));
		blueMarker = new PictureMarkerSymbol(this.getResources().getDrawable(R.drawable.map_locator_blue));
		
		worldTopoLayer = new ArcGISDynamicMapServiceLayer(WORLD_TOPO);
		worldTopoLayer.setName("Tiled Topo Layer");
		
		worldImageryLayer = new ArcGISTiledMapServiceLayer(IMAGERY_URL);
		worldImageryLayer.setName("Tiled Imagery Layer");
		
		worldTransportationLayer = new ArcGISTiledMapServiceLayer(WORLD_TRANSPORTATION);
		worldTransportationLayer.setName("Tiled Transportation Layer");
		
		worldBoundryLayer = new ArcGISTiledMapServiceLayer(WORLD_BOUNDRIES);
		worldBoundryLayer.setName("Tiled Boundary Layer");
		
		graphicsLayer = new GraphicsLayer();
		graphicsLayer.setName("Point Layer");
				
		if (loadMap > 1 ) {
			map_menu_container.setVisibility(View.VISIBLE);
//			floorPlanLayer.setVisible(false);
			worldTopoLayer.setVisible(true);
			worldImageryLayer.setVisible(false);
			worldBoundryLayer.setVisible(false);
			worldTransportationLayer.setVisible(false);
		}
		else {
			map_menu_container.setVisibility(View.GONE);
			worldTopoLayer.setVisible(false);
			worldImageryLayer.setVisible(false);
			worldTransportationLayer.setVisible(false);
			worldBoundryLayer.setVisible(false);
			
			floorPlanLayer = new ArcGISDynamicMapServiceLayer(FLOOR_PLAN1);
			floorPlanLayer.setName("Floor Plan");
			mapView.addLayer(floorPlanLayer);
		}
		
		
		mapView.addLayer(worldTopoLayer);
		mapView.addLayer(worldImageryLayer);
		mapView.addLayer(worldTransportationLayer);
		mapView.addLayer(worldBoundryLayer);
		mapView.addLayer(graphicsLayer);
		
		mapView.setOnStatusChangedListener(new OnStatusChangedListener() {
			
			@Override
			public void onStatusChanged(Object source, STATUS status) {
				Logger.getLogger("ESRI").info("Status changed:" + source.toString() + ":" + status);
				if (status == STATUS.INITIALIZED) {
					
					if (agendaPoint != null) 
						loadAgendaPoint();
					else if (exhibitPoint != null)
						loadExhibitsPoint();
					else
						loadPOI();
				}
			}
		});
		
	}
	
	@Background
	protected void loadPOI() {
		Query query = new Query();
		query.setGeometry(featureEnvelop);
		query.setReturnGeometry(true);
		query.setInSpatialReference(SpatialReference.create(3857));
		query.setOutFields(new String[]{"ObjectID","Title","Address1","Description","Type","Phone1","Conference"});
		
		try {
			QueryTask task = new QueryTask(POI);
			loadPOIonMap(task.execute(query));
		}
		catch(Exception ex) {
			Logger.getLogger("ESRI").log(Level.INFO,"Error in POI",ex);
		}
	}
	
	@UiThread
	protected void loadPOIonMap(FeatureSet fset) {
		if (fset !=null) {
			
			graphicsLayer.removeAll();
			
			PictureMarkerSymbol blueMarker = new PictureMarkerSymbol(this.getResources().getDrawable(R.drawable.map_locator_blue));
			//blueMarker.setOffsetX(5);
			//blueMarker.setOffsetY(16);
			
			for (Graphic  featureGraphic : fset.getGraphics()) {
				
				Logger.getLogger("ESRI").info("Loading Feature:" + featureGraphic.getAttributeValue("Title") + ":" + featureGraphic.getAttributeValue("Type"));
				Logger.getLogger("ESRI").info("Geom:" + ((Point)featureGraphic.getGeometry()).getX() + ","+ ((Point)featureGraphic.getGeometry()).getX());
				
				Point point = (Point)GeometryEngine.project(featureGraphic.getGeometry(),SpatialReference.create(4326), mapView.getSpatialReference());
				if (featureGraphic.getAttributeValue("Type") != null &&  (featureGraphic.getAttributeValue("Type").toString().equalsIgnoreCase("Venue") || featureGraphic.getAttributeValue("Type").toString().equalsIgnoreCase("Hotel"))) {
					// Blue Marker
					graphicsLayer.addGraphic(new Graphic(point, blueMarker));
					Logger.getLogger("ESRI").info("Adding blue marker");
				}
				else if (featureGraphic.getAttributeValue("Type") != null &&  (featureGraphic.getAttributeValue("Type").toString().equalsIgnoreCase("Booth") || featureGraphic.getAttributeValue("Type").toString().equalsIgnoreCase("Exhibition"))) {
					// Green Marker
					graphicsLayer.addGraphic(new Graphic(point, greenMarker));
					Logger.getLogger("ESRI").info("Adding green marker");
				}
				else if (featureGraphic.getAttributeValue("Type") != null &&  featureGraphic.getAttributeValue("Type").toString().equalsIgnoreCase("Sessions") ) {
					// Grey Marker
					graphicsLayer.addGraphic(new Graphic(point, greyMarker));
					Logger.getLogger("ESRI").info("Adding grey marker");
				}
				else if (featureGraphic.getAttributeValue("Type") != null &&  (featureGraphic.getAttributeValue("Type").toString().equalsIgnoreCase("Room") || featureGraphic.getAttributeValue("Type").toString().equalsIgnoreCase("Registration"))) {
					// Brown Marker
					graphicsLayer.addGraphic(new Graphic(point, brownMarker));
					Logger.getLogger("ESRI").info("Adding brown marker");
				}
				else {
					// red
					graphicsLayer.addGraphic(new Graphic(point, redMarker));
					Logger.getLogger("ESRI").info("Adding red marker");
				}	
			}
			
			mapView.setExtent(featureEnvelop, 20);
			
			loadStaticMarkers();
		}
		else {
			Logger.getLogger("ESRI").info("FeatureSet null");
		}
		
		//loadMap();
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
	
	@Click
	void m_btn () {
		if (m_btn.getText().toString().equalsIgnoreCase("Floor Plan")) {
			
			info_view.setVisibility(View.GONE);
			
			m_btn.setText("Area Map");
			map_label.setText("Floor Plan");
			map_menu_container.setVisibility(View.INVISIBLE);
			
			floorPlanLayer.setVisible(true);
			worldTopoLayer.setVisible(false);
			worldImageryLayer.setVisible(false);
			worldBoundryLayer.setVisible(false);
			worldTransportationLayer.setVisible(false);
		}
		else {
			
			info_view.setVisibility(View.GONE);
			
			m_btn.setText("Floor Plan");
			map_label.setText("Area Map");
			map_menu_container.setVisibility(View.VISIBLE);
			
			mapView.setExtent(featureEnvelop);
			
			floorPlanLayer.setVisible(false);
			worldTopoLayer.setVisible(true);
			worldImageryLayer.setVisible(false);
			worldBoundryLayer.setVisible(false);
			worldTransportationLayer.setVisible(false);
		}
	}
	
	@Click
	void info_btn() {
		if (info_view.getVisibility() == View.VISIBLE)
			info_view.setVisibility(View.GONE);
		else
			info_view.setVisibility(View.VISIBLE);
	}
	
	@Click
	void info_close_btn() {
		info_view.setVisibility(View.GONE);
	}
	
	@Click
	void close_btn () {
		finish();
	}
	
	@Click
	void world_topo_txvw() {
		world_topo_txvw.setBackgroundResource(R.drawable.tab_left_selected);
		imagery_txvw.setBackgroundResource(R.drawable.tab_center_normal);
		osm_txvw.setBackgroundResource(R.drawable.tab_right_normal);
		
		int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
		world_topo_txvw.setPadding(padding, padding, padding, padding);
		imagery_txvw.setPadding(padding, padding, padding, padding);
		osm_txvw.setPadding(padding, padding, padding, padding);
		
		worldTopoLayer.setVisible(true);
		worldImageryLayer.setVisible(false);
		worldBoundryLayer.setVisible(false);
		worldTransportationLayer.setVisible(false);
	}
	
	@Click
	void imagery_txvw() {
		world_topo_txvw.setBackgroundResource(R.drawable.tab_left_normal);
		imagery_txvw.setBackgroundResource(R.drawable.tab_center_selected);
		osm_txvw.setBackgroundResource(R.drawable.tab_right_normal);
		
		int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
		world_topo_txvw.setPadding(padding, padding, padding, padding);
		imagery_txvw.setPadding(padding, padding, padding, padding);
		osm_txvw.setPadding(padding, padding, padding, padding);
		
		worldTopoLayer.setVisible(false);
		worldImageryLayer.setVisible(true);
		worldBoundryLayer.setVisible(true);
		worldTransportationLayer.setVisible(true);
	}
	
	@Click
	void osm_txvw() {
		world_topo_txvw.setBackgroundResource(R.drawable.tab_left_selected);
		imagery_txvw.setBackgroundResource(R.drawable.tab_center_normal);
		osm_txvw.setBackgroundResource(R.drawable.tab_right_selected);
		
		int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
		world_topo_txvw.setPadding(padding, padding, padding, padding);
		imagery_txvw.setPadding(padding, padding, padding, padding);
		osm_txvw.setPadding(padding, padding, padding, padding);
	}
	
	private void loadAgendaPoint() {
		Logger.getLogger("ESRI").info("Loading agenda point" + agendaPoint.getX() + ":" + agendaPoint.getY());
		Point point = (Point)GeometryEngine.project(agendaPoint,SpatialReference.create(3857), mapView.getSpatialReference());
		graphicsLayer.addGraphic(new Graphic(point, brownMarker));
		mapView.setExtent(point);
	}
	
	private void loadExhibitsPoint() {
		Logger.getLogger("ESRI").info("Loading ehibit point" + exhibitPoint.getX() + ":" + exhibitPoint.getY());
		Point point = (Point)GeometryEngine.project(exhibitPoint,SpatialReference.create(3857), mapView.getSpatialReference());
		graphicsLayer.addGraphic(new Graphic(point, greenMarker));
		mapView.setExtent(point);
	}
	
	private void loadStaticMarkers() {
		//Load static markers
		Logger.getLogger("ESRI").info("Loading Marker: " + loadMap);
		Point point = null;
		//contact
		if (loadMap == 1) {
			point = new Point(-10615235.847145, 3471758.651233 );
		}
		//venue
		else if (loadMap == 2) {
			point = new Point(-10615193.497429725, 3471873.7312971083 );
		}
		//Hotel
		else if (loadMap == 3) {
			point = new Point(-10615498.349796278, 3471794.905611953 );
		}
		//Hertz
		else if (loadMap == 4) {
			point = new Point(-10617009.620678838, 3471478.02916526 );
		}
		//Avis
		else if (loadMap == 5) {
			point = new Point(-10616749.480053235, 3471607.526515731 );
		}
		//Enterprise
		else if (loadMap == 6) {
			point = new Point(-10616411.060452262, 3472339.679012933);
		}
		//Hotel 2
		else if (loadMap == 7) {
			point = new Point(-12973538.895856, 4005163.500549 );
		}
		//Hotel 3
		else if (loadMap == 8) {
			point = new Point(-12973691.734053, 4004939.466324 );
		}
		//Hotel 4
		else if (loadMap == 9) {
			point = new Point(-12973936.311049, 4005476.734541 );
		}
		//Hotel 5
		else if (loadMap == 10) {
			point = new Point(-12973740.476519, 4008092.201996);
		}
		//Hotel 6
		else if (loadMap == 11) {
			point = new Point(-12973505.539314, 4005467.700570  );
		}
		//House of Blues
		else if (loadMap == 12) {
			point = new Point(-10615829.32160262, 3471952.5934399017 );
		}
		
		if (point != null) {
			point = (Point)GeometryEngine.project(point,SpatialReference.create(3857), mapView.getSpatialReference());
			graphicsLayer.addGraphic(new Graphic(point,blueMarker ));
			mapView.setExtent(point);
		}
	}
	
	@Click
	void link1_txvw() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("http://resources.arcgis.com/content/arcgis-ios"));
		startActivityForResult(intent, 101);
	}
	
	@Click
	void link2_txvw() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("http://services.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer"));
		startActivityForResult(intent, 101);
	}
	
	@Click
	void link3_txvw() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("http://www.openstreetmap.org/"));
		startActivityForResult(intent, 101);
	}
	
	@Click
	void link4_txvw() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("http://services.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer"));
		startActivityForResult(intent, 101);
	}
	
	@Click
	void link5_txvw() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("http://services.arcgisonline.com/ArcGIS/rest/services/Reference/World_Boundaries_and_Places/MapServer"));
		startActivityForResult(intent, 101);
	}
	
	@Click
	void link6_txvw() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("http://services.arcgisonline.com/ArcGIS/rest/services/Reference/World_Transportation/MapServer"));
		startActivityForResult(intent, 101);
	}
	
	@Click
	void link7_txvw() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("http://mwoapps.esri.com/ArcGIS/rest/services/POIs/MapServer"));
		startActivityForResult(intent, 101);
	}
	
	@Click
	void link8_txvw() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("http://mwoapps.esri.com/ArcGIS/rest/services/epc/MapServer"));
		startActivityForResult(intent, 101);
	}
}
