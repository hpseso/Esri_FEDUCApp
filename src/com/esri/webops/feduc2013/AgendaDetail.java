package com.esri.webops.feduc2013;

import java.util.Calendar;
import java.util.logging.Logger;

import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.esri.core.geometry.Point;
import com.esri.webops.feduc2013.db.AgendaAssetDB;
import com.esri.webops.feduc2013.db.AgendaDB;
import com.esri.webops.feduc2013.util.Util;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.agenda_detail)
public class AgendaDetail extends BaseActivity {

	@ViewById
	Button map_btn;

    @ViewById
    Button back_btn;
	
	@Extra("ID")
	Integer id =0;
	
	@ViewById
	TextView title_txvw,time_txvw,hall_txvw,label_txvw,description_txvw,asset_title_txvw,author_txvw,org_name_txvw;
	
	@ViewById
	Button locate_btn,cal_btn;
	
	@ViewById
	ScrollView detail_container;

    @ViewById
    LinearLayout  detail_sub_container;
	
	int day,month,year,hour,min;
	int eday,emonth,eyear,ehour,emin;
	
	double x,y;
    int floor;
	
	@AfterViews
	void loadView() {
		map_btn.setVisibility(View.GONE);
		
		
		if (id > 0) {
			AgendaDB db = new AgendaDB(this);
			Cursor cursor = db.get(id); 
			if (cursor !=null && cursor.moveToFirst()) {
				String title = cursor.getString(cursor.getColumnIndex("ZSESSIONTITLE"));
				String hall = cursor.getString(cursor.getColumnIndex("ZROOM"));
				String edes = cursor.getString(cursor.getColumnIndex("ZEVENTTYPEDESCRIPTION"));
				
				day = cursor.getInt(cursor.getColumnIndex("ZSTARTDAY"));
				month = cursor.getInt(cursor.getColumnIndex("ZSTARTMONTH"));
				year = cursor.getInt(cursor.getColumnIndex("ZSTARTYEAR"));
				hour = cursor.getInt(cursor.getColumnIndex("ZSTARTHOUR"));
				min = cursor.getInt(cursor.getColumnIndex("ZSTARTMINUTE"));
				
				eday = cursor.getInt(cursor.getColumnIndex("ZENDDAY"));
				emonth = cursor.getInt(cursor.getColumnIndex("ZENDMONTH"));
				eyear = cursor.getInt(cursor.getColumnIndex("ZENDYEAR"));
				ehour = cursor.getInt(cursor.getColumnIndex("ZENDHOUR"));
				emin = cursor.getInt(cursor.getColumnIndex("ZENDMINUTE"));
				
				x = cursor.getDouble(cursor.getColumnIndex("ZXPOINT"));
				y = cursor.getDouble(cursor.getColumnIndex("ZYPOINT"));

                floor = cursor.getInt(cursor.getColumnIndex("ZFLOOR"));
				
				title_txvw.setText(title);
				hall_txvw.setText(hall);
				time_txvw.setText(Util.getListDate(cursor));
				label_txvw.setText(edes);
				
				cursor.close();
			}
			
			AgendaAssetDB adb = new AgendaAssetDB(this);
			Cursor c = adb.get(id); 
			if (c !=null && c.moveToFirst()) {


                while (c.isAfterLast() == false) {

                    View view = getLayoutInflater().inflate(R.layout.cellview6,null);

                    String atitle = c.getString(c.getColumnIndex("ZSESSIONASSETTITLE"));
                    String description = c.getString(c.getColumnIndex("ZSESSIONASSETDESCRIPTION"));
                    String author = c.getString(c.getColumnIndex("ZSESSIONASSETAUTHOR"));
                    String orgName = c.getString(c.getColumnIndex("ZSESSIONASSETAUTHORORGANIZATIONNAME"));

                    if (atitle != null && atitle.length() > 0 && !atitle.trim().equalsIgnoreCase("null"))
                        ((TextView)view.findViewById(R.id.asset_title_txvw)).setText(atitle);
                    else
                        ((TextView)view.findViewById(R.id.asset_title_txvw)).setVisibility(View.GONE);

                    if (author != null && author.length() > 0 && !author.trim().equalsIgnoreCase("null"))
                        ((TextView)view.findViewById(R.id.author_txvw)).setText(author);
                    else
                        ((TextView)view.findViewById(R.id.author_txvw)).setVisibility(View.GONE);

                    if (orgName != null && orgName.length() > 0 && !orgName.trim().equalsIgnoreCase("null"))
                        ((TextView)view.findViewById(R.id.org_name_txvw)).setText(orgName);
                    else
                        ((TextView)view.findViewById(R.id.org_name_txvw)).setVisibility(View.GONE);

                    if (description != null && description.length() > 0 && !description.trim().equalsIgnoreCase("null"))
                        ((TextView)view.findViewById(R.id.description_txvw)).setText(description);
                    else
                        ((TextView)view.findViewById(R.id.description_txvw)).setVisibility(View.GONE);

                    detail_sub_container.addView(view);

                    c.moveToNext();
                }

				c.close();
			}
			else 
				detail_container.setVisibility(View.GONE);
		}
		else 
			finish();
	}
	
	@Click
	void cal_btn() {
		Calendar scal = Calendar.getInstance();
		scal.set(Calendar.DAY_OF_MONTH, day);
		scal.set(Calendar.MONTH, month-1);
		scal.set(Calendar.YEAR, year);
		scal.set(Calendar.HOUR_OF_DAY, hour);
		scal.set(Calendar.MINUTE, min);
		
		Calendar ecal = Calendar.getInstance();
		ecal.set(Calendar.DAY_OF_MONTH, eday);
		ecal.set(Calendar.MONTH, emonth-1);
		ecal.set(Calendar.YEAR, year);
		ecal.set(Calendar.HOUR_OF_DAY, ehour);
		ecal.set(Calendar.MINUTE, emin);
		
		
		Intent intent = new Intent(Intent.ACTION_EDIT);
		intent.setType("vnd.android.cursor.item/event");
		intent.putExtra("title", title_txvw.getText().toString());
		intent.putExtra("description", "Some description");
		intent.putExtra("beginTime", scal.getTimeInMillis());
		intent.putExtra("endTime", ecal.getTimeInMillis());
		startActivity(intent);
	}
	
	@Click
	void locate_btn() {
		Logger.getLogger("ESRI").info("Agenda Point:" + x +":"+ y);
		Point point = new Point(x, y);
        int tFloor = floor;
        Intent intent = new Intent(this,VenueMap_.class);
        intent.putExtra("AGENDA_POINT", point);

        if (floor == 6) {
            if (x == 0)
                tFloor = 1;
            else
                tFloor = floor;
        }
        else {
            if (x == 0)
                tFloor = 1;
            else
                tFloor = floor;
        }
        intent.putExtra("MAP_TYPE", Map.VENUE_MAP_TYPE);
        intent.putExtra("MARKER_COLOR", Map.MAP_MARKER_GRAY);
        intent.putExtra("FLOOR", tFloor);
        startActivity(intent);
	}
	
	@Click
	void back_btn() {
		finish();
	}
	
}
