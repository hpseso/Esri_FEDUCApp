package com.esri.webops.feduc2013;

import android.app.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.esri.android.map.Callout;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.core.geometry.*;
import com.esri.core.map.FeatureSet;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.tasks.ags.query.Query;
import com.esri.core.tasks.ags.query.QueryTask;
import com.googlecode.androidannotations.annotations.*;
import android.view.ViewGroup.LayoutParams;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@EActivity(R.layout.map)
public class Map extends Activity {

    public static final int VENUE_MAP_TYPE = 1;
    public static final int AREA_MAP_TYPE = 2;

    public static final int MAP_MARKER_RED = 1;
    public static final int MAP_MARKER_BROWN = 2;
    public static final int MAP_MARKER_BLUE = 3;
    public static final int MAP_MARKER_GREEN = 4;
    public static final int MAP_MARKER_GRAY = 5;

    public static final int MAP_LEVEL1 = 1;
    public static final int MAP_LEVEL2 = 2;
    public static final int MAP_LEVEL3 = 3;


    static final String FLOOR_PLAN0 = "http://mwo-lb-885964707.us-east-1.elb.amazonaws.com/ArcGIS/rest/services/feduc_floorplan_0/MapServer";
    static final String FLOOR_PLAN1 = "http://mwo-lb-885964707.us-east-1.elb.amazonaws.com/ArcGIS/rest/services/feduc_floorplan_1/MapServer";
    static final String FLOOR_PLAN3 = "http://mwo-lb-885964707.us-east-1.elb.amazonaws.com/ArcGIS/rest/services/feduc_floorplan_3/MapServer";
    static final String POI = "http://mwo-lb-885964707.us-east-1.elb.amazonaws.com/ArcGIS/rest/services/POIs-nationwide/MapServer/0";

//    static final String FLOOR_PLAN0 = "http://ec2-23-21-114-109.compute-1.amazonaws.com/ArcGIS/rest/services/feduc_floorplan_0/MapServer";
//    static final String FLOOR_PLAN1 = "http://ec2-23-21-114-109.compute-1.amazonaws.com/ArcGIS/rest/services/feduc_floorplan_1/MapServer";
//    static final String FLOOR_PLAN3 = "http://ec2-23-21-114-109.compute-1.amazonaws.com/ArcGIS/rest/services/feduc_floorplan_3/MapServer";
//    static final String POI = "http://ec2-23-21-114-109.compute-1.amazonaws.com/ArcGIS/rest/services/POIs-nationwide/MapServer/0";

    static final String World_Imagery_Map_URL= "http://services.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer";
    static final String World_Transportation_Map_URL= "http://services.arcgisonline.com/ArcGIS/rest/services/Reference/World_Transportation/MapServer";
    static final String World_Boundaries_Map_URL= "http://services.arcgisonline.com/ArcGIS/rest/services/Reference/World_Boundaries_and_Places/MapServer";
    static final String World_Topo_Map_URL ="http://services.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer";

    @Extra("MAP_TYPE")
    Integer mapType = VENUE_MAP_TYPE;

    @Extra("MAP_POINT")
    Point mapPoint;

    @Extra("AGENDA_POINT")
    Point agendaPoint;

    @Extra("EXHIBIT_POINT")
    Point exhibitPoint;

    @Extra("MAP_LEVEL")
    Integer mapLevel = MAP_LEVEL1;

    @Extra("MARKER_COLOR")
    Integer markerColor = MAP_MARKER_BROWN;

    @Extra("FLOOR")
    Integer floor;

    @Extra("isShowCallout")
    Boolean isShowCallout = false;

    @ViewById(R.id.map)
	MapView mapView;

    @ViewById(R.id.map_menu_container)
    LinearLayout map_menu_container;

    @ViewById(R.id.floor_menu_container)
    LinearLayout floor_menu_container;

    @ViewById(R.id.level_one_txvw)
    TextView level_one_txvw;

    @ViewById(R.id.level_two_txvw)
    TextView level_two_txvw;

    @ViewById(R.id.level_three_txvw)
    TextView level_three_txvw;

    @ViewById(R.id.world_topo_txvw)
    TextView world_topo_txvw;

    @ViewById(R.id.imagery_txvw)
    TextView imagery_txvw;

    @ViewById(R.id.current_map_txvw)
    TextView current_map_txvw;

    @ViewById (R.id.m_btn)
    Button m_btn;

    @ViewById
    LinearLayout info_view;

    // feature extent
    static final double fxmin=-8583970.077358,fymin=4699058.502556,fxmax=-8572076.702961,fymax=4717790.567231;
    Envelope featureEnvelop = new Envelope(fxmin, fymin, fxmax,fymax);

    ArcGISDynamicMapServiceLayer floor1Layer,floor2Layer,floor3Layer;
    GraphicsLayer graphicsLayer;

    ArcGISTiledMapServiceLayer tiledTopoLayer,tiledImageryLayer,transportationLayer,boundryMapLayer;

    PictureMarkerSymbol greenMarker,greyMarker,brownMarker,redMarker,blueMarker;

    Callout callout;
    View content;
    final static int TITLE_ID = 1;
    final static int ADDRESS_ID = 2;

	@AfterViews
	void loadView() {

        callout = mapView.getCallout();
        callout.setStyle(R.xml.calloutstyle);
        callout.setMaxWidth(600);
        content = createContent();

        greenMarker = new PictureMarkerSymbol(this.getResources().getDrawable(R.drawable.map_locator_green));
        greenMarker.setOffsetY(16);
        greyMarker = new PictureMarkerSymbol(this.getResources().getDrawable(R.drawable.map_locator_grey));
        greyMarker.setOffsetY(16);
        brownMarker = new PictureMarkerSymbol(this.getResources().getDrawable(R.drawable.map_locator_brown));
        brownMarker.setOffsetY(16);
        redMarker = new PictureMarkerSymbol(this.getResources().getDrawable(R.drawable.map_locator_red));
        redMarker.setOffsetY(16);
        blueMarker = new PictureMarkerSymbol(this.getResources().getDrawable(R.drawable.map_locator_blue));
        blueMarker.setOffsetY(16);


        tiledTopoLayer = new ArcGISTiledMapServiceLayer(World_Topo_Map_URL);
        tiledTopoLayer.setName("Tiled Topo Layer");

        tiledImageryLayer = new ArcGISTiledMapServiceLayer(World_Imagery_Map_URL);
        tiledImageryLayer.setName("Tiled Imagery Layer");

        transportationLayer = new ArcGISTiledMapServiceLayer(World_Transportation_Map_URL);
        transportationLayer.setName("Tiled Transportation Layer");

        boundryMapLayer = new ArcGISTiledMapServiceLayer(World_Boundaries_Map_URL);
        boundryMapLayer.setName("Boundry Map Layer");

        floor1Layer = new ArcGISDynamicMapServiceLayer(FLOOR_PLAN0);
        floor1Layer.setName("Background Layer");

        floor2Layer = new ArcGISDynamicMapServiceLayer(FLOOR_PLAN1);
        floor2Layer.setName("Floor Two Layer");

        floor3Layer = new ArcGISDynamicMapServiceLayer(FLOOR_PLAN3);
        floor3Layer.setName("Floor Three Layer");

        graphicsLayer = new GraphicsLayer();
        graphicsLayer.setName("Point Layer");

        if (mapType == VENUE_MAP_TYPE) {
            mapView.addLayer(floor1Layer);
            mapView.addLayer(floor2Layer);
            mapView.addLayer(floor3Layer);

            Logger.getLogger("Esri").info("Loading Venue Map:");
            showVenueMap();
        }
        else {
            mapView.addLayer(tiledTopoLayer);
            mapView.addLayer(tiledImageryLayer);
            mapView.addLayer(transportationLayer);
            mapView.addLayer(boundryMapLayer);
            showAreaMap();
            loadPOI();
        }

        mapView.addLayer(graphicsLayer);

        mapView.setOnStatusChangedListener(new OnStatusChangedListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void onStatusChanged(Object source, STATUS status) {
                Logger.getLogger("Esri").info("Status changed:" + source.toString() + ":" + status);

                if (OnStatusChangedListener.STATUS.INITIALIZED == status && source == mapView) {
                    //if (mapType == VENUE_MAP_TYPE)
                    //showVenueMap();
                }
            }
        });

        mapView.setOnSingleTapListener(new OnSingleTapListener() {

            private static final long serialVersionUID = 1L;

            public void onSingleTap(float x, float y) {
                callout.hide();
                if (isShowCallout) {
                    int[] graphicIDs = graphicsLayer.getGraphicIDs(x, y, 25);
                    if (graphicIDs != null && graphicIDs.length > 0) {
                        Graphic gr = graphicsLayer.getGraphic(graphicIDs[0]);
                        updateContent((String) gr.getAttributeValue("Title"),(String) gr.getAttributeValue("Address"),(String) gr.getAttributeValue("URL"));
                        Point location = (Point) gr.getGeometry();
                        callout.setOffset(0, -15);
                        callout.show(location, content);
                    }
                }
            }
        });
    }

    private void loadPoint() {

        Logger.getLogger("Esri").info("Loading marker:" + mapPoint.getX() + ":" + mapPoint.getY());
        Point point = (Point)GeometryEngine.project(mapPoint,SpatialReference.create(3857), mapView.getSpatialReference());

        if (markerColor == MAP_MARKER_BROWN)
            graphicsLayer.addGraphic(new Graphic(point,brownMarker ));
        else if (markerColor == MAP_MARKER_BLUE)
            graphicsLayer.addGraphic(new Graphic(point,blueMarker ));
        else if (markerColor == MAP_MARKER_RED)
            graphicsLayer.addGraphic(new Graphic(point,redMarker ));
        else if (markerColor == MAP_MARKER_GREEN)
            graphicsLayer.addGraphic(new Graphic(point,greenMarker ));
        else
            graphicsLayer.addGraphic(new Graphic(point,greyMarker ));

        Unit mapUnit = SpatialReference.create(3857).getUnit();
        double zoomWidth = Unit.convertUnits(10,Unit.create(LinearUnit.Code.METER),mapUnit);
        Envelope zoomExtent = new Envelope(mapPoint,zoomWidth, zoomWidth);
        mapView.setExtent(zoomExtent);
    }

    private void loadAgendaPoint() {

        Logger.getLogger("Esri").info("Loading marker:" + agendaPoint.getX() + ":" + agendaPoint.getY());
        Point point = (Point)GeometryEngine.project(agendaPoint,SpatialReference.create(3857), mapView.getSpatialReference());


        if (markerColor == MAP_MARKER_BROWN)
            graphicsLayer.addGraphic(new Graphic(point,brownMarker ));
        else if (markerColor == MAP_MARKER_BLUE)
            graphicsLayer.addGraphic(new Graphic(point,blueMarker ));
        else if (markerColor == MAP_MARKER_RED)
            graphicsLayer.addGraphic(new Graphic(point,redMarker ));
        else if (markerColor == MAP_MARKER_GREEN)
            graphicsLayer.addGraphic(new Graphic(point,greenMarker ));
        else
            graphicsLayer.addGraphic(new Graphic(point,greyMarker ));

        Unit mapUnit = SpatialReference.create(3857).getUnit();
        double zoomWidth = Unit.convertUnits(100,Unit.create(LinearUnit.Code.METER),mapUnit);
        Envelope zoomExtent = new Envelope(agendaPoint,zoomWidth, zoomWidth);
        mapView.setExtent(zoomExtent);

    }

    private void loadExhibitPoint() {

        Logger.getLogger("Esri").info("Loading marker:" + exhibitPoint.getX() + ":" + exhibitPoint.getY());
        Point point = (Point)GeometryEngine.project(exhibitPoint,SpatialReference.create(3857), mapView.getSpatialReference());
        if (markerColor == MAP_MARKER_BROWN)
            graphicsLayer.addGraphic(new Graphic(point,brownMarker ));
        else if (markerColor == MAP_MARKER_BLUE)
            graphicsLayer.addGraphic(new Graphic(point,blueMarker ));
        else if (markerColor == MAP_MARKER_RED)
            graphicsLayer.addGraphic(new Graphic(point,redMarker ));
        else if (markerColor == MAP_MARKER_GREEN)
            graphicsLayer.addGraphic(new Graphic(point,greenMarker ));
        else
            graphicsLayer.addGraphic(new Graphic(point,greyMarker ));

        Unit mapUnit = SpatialReference.create(3857).getUnit();
        double zoomWidth = Unit.convertUnits(200,Unit.create(LinearUnit.Code.METER),mapUnit);
        Envelope zoomExtent = new Envelope(exhibitPoint,zoomWidth, zoomWidth);
        mapView.setExtent(zoomExtent);

    }

    private void showVenueMap() {
        map_menu_container.setVisibility(View.GONE);
        floor_menu_container.setVisibility(View.VISIBLE);
        current_map_txvw.setText("Venue Map");

        tiledTopoLayer.setVisible(false);
        tiledImageryLayer.setVisible(false);
        transportationLayer.setVisible(false);
        boundryMapLayer.setVisible(false);

        floor1Layer.setVisible(true);
        floor2Layer.setVisible(false);
        floor3Layer.setVisible(false);

        if (mapPoint != null && mapType == VENUE_MAP_TYPE) {
            loadPoint();
            if (mapLevel == MAP_LEVEL1)
                level_one_txvw();
            else if (mapLevel  == MAP_LEVEL2)
                level_two_txvw();
            else if (mapLevel == MAP_LEVEL3)
                level_three_txvw();
        }
        else if (agendaPoint != null)  {
            if (floor == 0)
                level_one_txvw();
            else if (floor == 1)
                level_two_txvw();
            else
                level_three_txvw();
            loadAgendaPoint();
        }
        else if (exhibitPoint != null) {
            loadExhibitPoint();
        }
        else {
            Logger.getLogger("Esri").info("Loading default map with:" + floor1Layer.getFullExtent().getCenterX() + ":" + floor1Layer.getFullExtent().getCenterY());
            Unit mapUnit = SpatialReference.create(3857).getUnit();
            double zoomWidth = Unit.convertUnits(50,Unit.create(LinearUnit.Code.METER),mapUnit);
            Envelope zoomExtent = new Envelope(floor1Layer.getFullExtent().getCenter(),zoomWidth,zoomWidth);
            mapView.setExtent(floor1Layer.getFullExtent());
        }


        if (mapView.getLayers().length < 5 ) {
            mapView.addLayer(tiledTopoLayer);
            mapView.addLayer(tiledImageryLayer);
            mapView.addLayer(transportationLayer);
            mapView.addLayer(boundryMapLayer);

        }


    }

    private void showAreaMap() {

        tiledTopoLayer.setVisible(true);
        tiledImageryLayer.setVisible(false);
        transportationLayer.setVisible(false);
        boundryMapLayer.setVisible(false);

        floor1Layer.setVisible(false);
        floor2Layer.setVisible(false);
        floor3Layer.setVisible(false);

        map_menu_container.setVisibility(View.VISIBLE);
        floor_menu_container.setVisibility(View.GONE);
        current_map_txvw.setText("Area Map");
        m_btn.setText("Floor Plan");

        if (mapPoint != null) {
            loadPoint();
        }

        if (mapView.getLayers().length < 6) {
            mapView.addLayer(floor1Layer);
            mapView.addLayer(floor2Layer);
            mapView.addLayer(floor3Layer);
        }

    }

    @Click
    void m_btn() {
        if (m_btn.getText().toString().equalsIgnoreCase("Area Map")) {
            graphicsLayer.removeAll();
            m_btn.setText("Floor Plan");
            showAreaMap();

        }
        else {
            graphicsLayer.removeAll();
            m_btn.setText("Area Map");
            showVenueMap();

        }
    }
	
	@Click
	void close_btn () {
		finish();
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
    void world_topo_txvw() {
        tiledTopoLayer.setVisible(true);
        tiledImageryLayer.setVisible(false);
        transportationLayer.setVisible(false);
        boundryMapLayer.setVisible(false);

        world_topo_txvw.setBackgroundResource(R.drawable.tab_left_selected);
        imagery_txvw.setBackgroundResource(R.drawable.tab_right_normal);
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 7, getResources().getDisplayMetrics());
        imagery_txvw.setPadding(padding,padding,padding,padding);
        world_topo_txvw.setPadding(padding,padding,padding,padding);
    }

    @Click
    void imagery_txvw() {
        tiledTopoLayer.setVisible(false);
        tiledImageryLayer.setVisible(true);
        transportationLayer.setVisible(false);
        boundryMapLayer.setVisible(false);

        world_topo_txvw.setBackgroundResource(R.drawable.tab_left_normal);
        imagery_txvw.setBackgroundResource(R.drawable.tab_right_selected);
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 7, getResources().getDisplayMetrics());
        imagery_txvw.setPadding(padding,padding,padding,padding);
        world_topo_txvw.setPadding(padding,padding,padding,padding);
    }

    @Click
    void level_one_txvw() {
        floor1Layer.setVisible(true);
        floor2Layer.setVisible(false);
        floor3Layer.setVisible(false);

        level_one_txvw.setBackgroundResource(R.drawable.tab_left_selected);
        level_two_txvw.setBackgroundResource(R.drawable.tab_center_normal);
        level_three_txvw.setBackgroundResource(R.drawable.tab_right_normal);

        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 7, getResources().getDisplayMetrics());
        level_one_txvw.setPadding(padding,padding,padding,padding);
        level_two_txvw.setPadding(padding,padding,padding,padding);
        level_three_txvw.setPadding(padding,padding,padding,padding);

    }

    @Click
    void level_two_txvw() {
        floor1Layer.setVisible(false);
        floor2Layer.setVisible(true);
        floor3Layer.setVisible(false);

        level_one_txvw.setBackgroundResource(R.drawable.tab_left_normal);
        level_two_txvw.setBackgroundResource(R.drawable.tab_center_selected);
        level_three_txvw.setBackgroundResource(R.drawable.tab_right_normal);

        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 7, getResources().getDisplayMetrics());
        level_one_txvw.setPadding(padding,padding,padding,padding);
        level_two_txvw.setPadding(padding,padding,padding,padding);
        level_three_txvw.setPadding(padding,padding,padding,padding);
    }

    @Click
    void level_three_txvw() {
        floor1Layer.setVisible(false);
        floor2Layer.setVisible(false);
        floor3Layer.setVisible(true);

        level_one_txvw.setBackgroundResource(R.drawable.tab_left_normal);
        level_two_txvw.setBackgroundResource(R.drawable.tab_center_normal);
        level_three_txvw.setBackgroundResource(R.drawable.tab_right_selected);

        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 7, getResources().getDisplayMetrics());
        level_one_txvw.setPadding(padding,padding,padding,padding);
        level_two_txvw.setPadding(padding,padding,padding,padding);
        level_three_txvw.setPadding(padding,padding,padding,padding);
    }


    @Background
    protected void loadPOI() {
        Logger.getLogger("Esri").log(Level.INFO,"Loading  POI");
        Query query = new Query();
        Envelope zoomExtent = null;
        if (mapPoint != null) {
            Logger.getLogger("Esri").log(Level.INFO,"Loading  POI with Point extent:" + mapPoint.getX() + ":" + mapPoint.getY());
            Unit mapUnit = SpatialReference.create(3857).getUnit();
            double zoomWidth = Unit.convertUnits(1,Unit.create(LinearUnit.Code.MILE_US),mapUnit);
            zoomExtent = new Envelope(mapPoint,zoomWidth, zoomWidth);
            query.setGeometry(zoomExtent);
        }
        else {
            Logger.getLogger("Esri").log(Level.INFO,"Loading  POI with feature extent");
            query.setGeometry(featureEnvelop);
        }
        query.setReturnGeometry(true);
        query.setInSpatialReference(SpatialReference.create(3857));
        query.setOutFields(new String[]{"ObjectID","Title","Address","URL","poi_type","Phone1","Conference"});

        try {
            QueryTask task = new QueryTask(POI);
            if (zoomExtent != null)
                loadPOIonMap(task.execute(query),zoomExtent);
            else
                loadPOIonMap(task.execute(query),featureEnvelop);
        }
        catch(Exception ex) {
            Logger.getLogger("Esri").log(Level.INFO,"Error in POI",ex);
        }
    }

    @UiThread
    protected void loadPOIonMap(FeatureSet fset,Envelope env) {
        if (fset !=null) {

            graphicsLayer.removeAll();

            PictureMarkerSymbol blueMarker = new PictureMarkerSymbol(this.getResources().getDrawable(R.drawable.map_locator_blue));
            //blueMarker.setOffsetX(5);
            //blueMarker.setOffsetY(16);

            for (Graphic featureGraphic : fset.getGraphics()) {

                Logger.getLogger("Esri").info("Loading Feature:" + featureGraphic.getAttributeValue("Title") + ":" + featureGraphic.getAttributeValue("poi_type"));
                Logger.getLogger("Esri").info("Geom:" + ((Point)featureGraphic.getGeometry()).getX() + ","+ ((Point)featureGraphic.getGeometry()).getX());

                Point point = (Point) GeometryEngine.project(featureGraphic.getGeometry(), SpatialReference.create(3857), mapView.getSpatialReference());

                HashMap<String, Object> attrMap = new HashMap<String, Object>();
                attrMap.put("Title", featureGraphic.getAttributeValue("Title"));
                attrMap.put("URL", featureGraphic.getAttributeValue("URL"));
                attrMap.put("Address", featureGraphic.getAttributeValue("Address"));

                Logger.getLogger("Esri").info("Query points data:" + attrMap.toString());


                if (featureGraphic.getAttributeValue("poi_type") != null &&  (featureGraphic.getAttributeValue("poi_type").toString().equalsIgnoreCase("Venue") || featureGraphic.getAttributeValue("poi_type").toString().equalsIgnoreCase("Hotel"))) {
                    // Blue Marker
                    graphicsLayer.addGraphic(new Graphic(point, blueMarker,attrMap,null));
                    Logger.getLogger("Esri").info("Adding blue marker");
                }
                else if (featureGraphic.getAttributeValue("poi_type") != null &&  (featureGraphic.getAttributeValue("poi_type").toString().equalsIgnoreCase("Booth") || featureGraphic.getAttributeValue("poi_type").toString().equalsIgnoreCase("Exhibition"))) {
                    // Green Marker
                    graphicsLayer.addGraphic(new Graphic(point, greenMarker,attrMap,null));
                    Logger.getLogger("Esri").info("Adding green marker");
                }
                else if (featureGraphic.getAttributeValue("poi_type") != null &&  featureGraphic.getAttributeValue("poi_type").toString().equalsIgnoreCase("Sessions") ) {
                    // Grey Marker
                    graphicsLayer.addGraphic(new Graphic(point, greyMarker,attrMap,null));
                    Logger.getLogger("Esri").info("Adding grey marker");
                }
                else if (featureGraphic.getAttributeValue("poi_type") != null &&  (featureGraphic.getAttributeValue("poi_type").toString().equalsIgnoreCase("Room") || featureGraphic.getAttributeValue("poi_type").toString().equalsIgnoreCase("Registration"))) {
                    // Brown Marker
                    graphicsLayer.addGraphic(new Graphic(point, brownMarker,attrMap,null));
                    Logger.getLogger("Esri").info("Adding brown marker");
                }
                else {
                    // red
                    graphicsLayer.addGraphic(new Graphic(point, redMarker,attrMap,null));
                    Logger.getLogger("Esri").info("Adding red marker");
                }
            }

            mapView.setExtent(env);
            //mapView.centerAt(env.getCenter(),false);

//            loadStaticMarkers();
        }
        else {
            Logger.getLogger("Esri").info("FeatureSet null");
        }

        //loadMap();
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

    public View createContent() {
        // create linear layout for the entire view
        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        layout.setOrientation(LinearLayout.VERTICAL);

        // create TextView for the title
        TextView titleView = new TextView(this);
        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getTag() != null && view.getTag().toString() != null && view.getTag().toString().trim().length() > 0 ) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(view.getTag().toString()));
                    startActivityForResult(intent, 101);
                }
            }
        });
        titleView.setId(TITLE_ID);


        TextView addressView = new TextView(this);
        addressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        addressView.setId(ADDRESS_ID);

        // titleView.setText(title);
        titleView.setTextColor(Color.WHITE);
        addressView.setTextColor(Color.WHITE);
        layout.addView(titleView);
        layout.addView(addressView);

        return layout;
    }

    public void updateContent(String title,String address,String url) {
        if (content == null)
            return;

        TextView txt = (TextView) content.findViewById(TITLE_ID);
        txt.setText(title);
        txt.setTag(url);

        TextView txtv = (TextView) content.findViewById(ADDRESS_ID);
        if (address != null)
            txtv.setText(address);
        else
            txtv.setVisibility(View.GONE);
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
