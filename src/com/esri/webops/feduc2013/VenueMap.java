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
public class VenueMap extends Activity {


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

    @ViewById(R.id.map)
    MapView mapView;

    @ViewById(R.id.floor_menu_container)
    LinearLayout floor_menu_container;

    @ViewById(R.id.level_one_txvw)
    TextView level_one_txvw;

    @ViewById(R.id.level_two_txvw)
    TextView level_two_txvw;

    @ViewById(R.id.level_three_txvw)
    TextView level_three_txvw;

    @ViewById(R.id.current_map_txvw)
    TextView current_map_txvw;

    @ViewById (R.id.m_btn)
    Button m_btn;

    @ViewById
    LinearLayout info_view;

    @ViewById(R.id.map_menu_container)
    LinearLayout map_menu_container;



    ArcGISDynamicMapServiceLayer floor1Layer,floor2Layer,floor3Layer;
    GraphicsLayer graphicsLayer;

    PictureMarkerSymbol greenMarker,greyMarker,brownMarker,redMarker,blueMarker;

    @AfterViews
    void loadView() {

        map_menu_container.setVisibility(View.GONE);
        floor_menu_container.setVisibility(View.VISIBLE);
        current_map_txvw.setText("Venue Map");


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

        floor1Layer = new ArcGISDynamicMapServiceLayer(FLOOR_PLAN0);
        floor1Layer.setName("Background Layer");

        floor2Layer = new ArcGISDynamicMapServiceLayer(FLOOR_PLAN1);
        floor2Layer.setName("Floor Two Layer");

        floor3Layer = new ArcGISDynamicMapServiceLayer(FLOOR_PLAN3);
        floor3Layer.setName("Floor Three Layer");

        graphicsLayer = new GraphicsLayer();
        graphicsLayer.setName("Point Layer");

        floor1Layer.setVisible(true);
        floor2Layer.setVisible(false);
        floor3Layer.setVisible(false);

        mapView.addLayer(floor1Layer);
        mapView.addLayer(floor2Layer);
        mapView.addLayer(floor3Layer);


        mapView.addLayer(graphicsLayer);

        mapView.setOnStatusChangedListener(new OnStatusChangedListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void onStatusChanged(Object source, STATUS status) {
                Logger.getLogger("Esri").info("Status changed:" + source.toString() + ":" + status);

                if (OnStatusChangedListener.STATUS.INITIALIZED == status && source == mapView) {
                    //if (mapType == VENUE_MAP_TYPE)
                    showVenueMap();
                }
            }
        });
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

        if (agendaPoint != null)  {
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
    }

    @Click
    void m_btn() {
        startActivity(getIntent().setClass(this,AreaMap_.class));
        finish();
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
