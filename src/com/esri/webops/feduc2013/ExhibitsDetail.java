package com.esri.webops.feduc2013;

import java.util.logging.Level;
import java.util.logging.Logger;

import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.esri.webops.feduc2013.db.ExhibitDB;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.exhibitor_detail)
public class ExhibitsDetail extends BaseActivity {

	@ViewById
	Button map_btn,back_btn;
	
	@Extra("ID")
	Integer id =0;
	
	@ViewById
	TextView partner_txvw,title_txvw,booth_txvw,address1_txvw,address2_txvw,phone_txvw,email_txvw,description_txvw;
	
	@ViewById
	Button locate_btn,link_btn,email_btn,call_btn;
	
	@ViewById
	ImageView logo_imgvw;
	
	Double x,y;
	
	@AfterViews
	void loadView() {
		map_btn.setVisibility(View.GONE);
		
		
		if (id > 0) {
			
			ExhibitDB db = new ExhibitDB(this);
			Cursor cursor = db.get(id); 
			if (cursor !=null && cursor.moveToFirst()) {
				
				int type = cursor.getInt(cursor.getColumnIndex("ZSPONSORTYPE"));
				String title = cursor.getString(cursor.getColumnIndex("ZEXHIBITORNAME"));
				String booth = cursor.getString(cursor.getColumnIndex("ZBOOTHNUMBER"));
				String banner = cursor.getString(cursor.getColumnIndex("ZADBANNER"));
				String street = cursor.getString(cursor.getColumnIndex("ZEXHIBITORSTREET"));
				String city = cursor.getString(cursor.getColumnIndex("ZEXHIBITORCITY"));
				String state = cursor.getString(cursor.getColumnIndex("ZEXHIBITORSTATE"));
				String zip = cursor.getString(cursor.getColumnIndex("ZEXHIBITORPOSTALCODE"));
				String phone = cursor.getString(cursor.getColumnIndex("ZEXHIBITORPHONE"));
				String email = cursor.getString(cursor.getColumnIndex("ZEXHIBITOREMAIL"));
				String description = cursor.getString(cursor.getColumnIndex("ZEXHIBITORDESCRIPTION"));
				String url = cursor.getString(cursor.getColumnIndex("ZSPONSORURL"));
				
				try {
					x = Double.parseDouble(cursor.getString(cursor.getColumnIndex("ZXPOINT")));
					y = Double.parseDouble(cursor.getString(cursor.getColumnIndex("ZYPOINT")));
				}
				catch(Exception ex){
					x=0d;y=0d;
					locate_btn.setVisibility(View.GONE);
				}
				
				if (type == 1) {
					partner_txvw.setTextColor(0xff7f8083);
					partner_txvw.setText("Platinum Sponsor");
				}
				else if (type == 2) {
					partner_txvw.setTextColor(0xfff5e20b);
					partner_txvw.setText("Gold Sponsor");
				}
				else if (type == 3) {
					partner_txvw.setTextColor(0xffadb7bc);
					partner_txvw.setText("Social Sponsor");
				}
				else 
					partner_txvw.setVisibility(View.GONE);
				
				
				if (banner != null && banner.length() > 0 ) {
					try {
						banner = banner.replaceAll("-", "_");
						banner += "_other";
						
						int resID = getResources().getIdentifier(banner , "drawable", getPackageName());
						Logger.getLogger("ESRI").info("File:" + banner + "," + getPackageName() + "," + resID);
						
						logo_imgvw.setImageResource(resID);
						logo_imgvw.setVisibility(View.VISIBLE);
					}
					catch(Exception ex) {
						Logger.getLogger("ESRI").log(Level.INFO,"",ex);
					}
				}
				else 
					logo_imgvw.setVisibility(View.GONE);
				
				title_txvw.setText(title);
				booth_txvw.setText("Booth " + booth);
				
				if (street != null)
					address1_txvw.setText(street);
				else
					address1_txvw.setVisibility(View.GONE);
				
				if (city != null)
					address2_txvw.setText(city + " ");
				else 
					address2_txvw.setText(" ");
				
				if (state != null)
					address2_txvw.setText(address2_txvw.getText().toString() + state + " " );
				
				if (zip != null)
					address2_txvw.setText(address2_txvw.getText().toString() + zip );
				
				if (email != null) {
					email_txvw.setText("Email: " + email);
					email_btn.setTag(email);
				}
				else {
					email_btn.setVisibility(View.GONE);
					email_txvw.setVisibility(View.GONE);
				}
				
				if (phone != null) {
					phone_txvw.setText("Phone: " + phone);
					call_btn.setTag(phone);
				}
				else {
					call_btn.setVisibility(View.GONE);
					phone_txvw.setVisibility(View.GONE);
				}
				
				if (description != null)
					description_txvw.setText(description);
				
				if (url != null)
					link_btn.setTag(url);
				
				cursor.close();
			}
		}
		else 
			finish();
	}
	
	@Click
	void call_btn() {
		call(call_btn.getTag().toString());
	}
	
	@Click
	void link_btn() {
		loadBrowser(link_btn.getTag().toString());
	}
	
	@Click
	void email_btn() {
		email(email_btn.getTag().toString());
	}
	
	@Click
	void locate_btn() {
		Logger.getLogger("ESRI").info("Exhibits Point:" + x + "," + y);
//		Point point = new Point(x, y);
		Intent intent = new Intent(this,Map_.class);
//		intent.putExtra("EXHIBIT_POINT", point);
		startActivity(intent);
	}
	
	@Click
	void back_btn() {
		Intent intent = new Intent(this,ExhibitsAll_.class);
		View view = ExhibitsGroup.group.getLocalActivityManager()  
	            .startActivity("", intent  
	                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))  
	                    .getDecorView();  
		ExhibitsGroup.group.replaceView(view);
	}
}
