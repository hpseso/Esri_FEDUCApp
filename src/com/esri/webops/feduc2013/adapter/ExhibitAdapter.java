package com.esri.webops.feduc2013.adapter;

import java.util.logging.Level;
import java.util.logging.Logger;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esri.core.geometry.Point;
import com.esri.webops.feduc2013.*;

public class ExhibitAdapter extends CursorAdapter implements Filterable {

	LayoutInflater mInflater;
	Resources resources;
	String packageName;
	
	public ExhibitAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		resources = context.getResources();
		mInflater = LayoutInflater.from(context);
		packageName = context.getPackageName();
	}

	@Override
	public void bindView(View view, final Context context, Cursor c) {
		
		final int id = c.getInt(c.getColumnIndex("_id"));
		String title = c.getString(c.getColumnIndex("ZEXHIBITORNAME"));
		String lable2 = c.getString(c.getColumnIndex("ZBOOTHNUMBER"));
		String banner = c.getString(c.getColumnIndex("ZADBANNER"));
		int type = c.getInt(c.getColumnIndex("ZSPONSORTYPE"));
		// 2 gold, 1 Platinum and 3 Social
		
		TextView text1 = (TextView) view.findViewById(R.id.label1_txvw);
		text1.setText(title);
		
		TextView text2 = (TextView) view.findViewById(R.id.label2_txvw);
		text2.setText("Booth " + lable2);
		
		TextView text3 = (TextView) view.findViewById(R.id.partner_txvw);
		text3.setVisibility(View.VISIBLE);
		
		if (type == 1) {
			text3.setTextColor(0xff7f8083);
			text3.setText("Platinum Sponsor");
		}
		else if (type == 2) {
			text3.setTextColor(0xfff5e20b);
			text3.setText("Gold Sponsor");
		}
		else if (type == 3) {
			text3.setTextColor(0xffadb7bc);
			text3.setText("Social Sponsor");
		}
		else 
			text3.setVisibility(View.GONE);
		
		final double x = c.getDouble(c.getColumnIndex("ZXPOINT"));
		final double y = c.getDouble(c.getColumnIndex("ZYPOINT"));
		
		Button locate = (Button) view.findViewById(R.id.locate_btn);
		locate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Point point = new Point(x, y);
				Intent intent = new Intent(context,Map_.class);
				intent.putExtra("EXHIBIT_POINT", point);
                intent.putExtra("MAP_TYPE", Map.VENUE_MAP_TYPE);
                intent.putExtra("MARKER_COLOR", Map.MAP_MARKER_GREEN);
                intent.putExtra("FLOOR", 0);
                context.startActivity(intent);
			}
		});
		
		LinearLayout container = (LinearLayout) view.findViewById(R.id.container);
		container.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context,ExhibitsDetail_.class);
				intent.putExtra("ID", id);
				View view = ExhibitsGroup.group.getLocalActivityManager()  
			            .startActivity("", intent  
			                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))  
			                    .getDecorView();  
				ExhibitsGroup.group.replaceView(view);
			}
		});
		
		ImageView imgvw = (ImageView) view.findViewById(R.id.logo_imgvw);
		if (banner != null && banner.length() > 0 ) {
			try {
				banner = banner.replaceAll("-", "_");
				banner += "";
				
				int resID = resources.getIdentifier(banner.toLowerCase() , "drawable", packageName);
				Logger.getLogger("ESRI").info("File:" + banner + "," + packageName + "," + resID);
				
				imgvw.setImageResource(resID);
				imgvw.setVisibility(View.VISIBLE);
			}
			catch(Exception ex) {
				Logger.getLogger("ESRI").log(Level.INFO,"",ex);
                imgvw.setVisibility(View.GONE);
			}
		}
		else 
			imgvw.setVisibility(View.GONE);
			
	}

	@Override
	public View newView(Context context, Cursor c, ViewGroup parent) {
		return mInflater.inflate(R.layout.exhibit_row, parent, false);
	}

	
}
