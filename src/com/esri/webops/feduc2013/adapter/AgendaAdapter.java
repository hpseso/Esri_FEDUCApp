package com.esri.webops.feduc2013.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esri.core.geometry.Point;
import com.esri.webops.feduc2013.Agenda;
import com.esri.webops.feduc2013.Map_;
import com.esri.webops.feduc2013.R;
import com.esri.webops.feduc2013.util.Util;

public class AgendaAdapter extends CursorAdapter implements Filterable {

	LayoutInflater mInflater;
	Agenda activity;
	
	public AgendaAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		mInflater = LayoutInflater.from(context);
		activity = (Agenda) context;
	}

	@Override
	public void bindView(View view, final Context context, Cursor c) {
		final int id = c.getInt(c.getColumnIndex("_id"));
		String title = c.getString(c.getColumnIndex("ZSESSIONTITLE"));
		String lable3 = c.getString(c.getColumnIndex("ZROOM"));
		
		TextView text1 = (TextView) view.findViewById(R.id.label1_txvw);
		text1.setText(title);
		
		TextView text2 = (TextView) view.findViewById(R.id.label2_txvw);
		text2.setText(Util.getListDate(c));
		
		TextView text3 = (TextView) view.findViewById(R.id.label3_txvw);
		text3.setText(lable3);
		
		final double x = c.getDouble(c.getColumnIndex("ZXPOINT"));
		final double y = c.getDouble(c.getColumnIndex("ZYPOINT"));
		
		Button locate = (Button) view.findViewById(R.id.locate_btn);
		locate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Point point = new Point(x, y);
				
				Intent intent = new Intent(context,Map_.class);
				intent.putExtra("AGENDA_POINT", point);
				context.startActivity(intent);
			}
		});
		
		LinearLayout container = (LinearLayout) view.findViewById(R.id.container);
		container.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				activity.agendaItemClick(id);
			}
		});
	}

	@Override
	public View newView(Context context, Cursor c, ViewGroup parent) {
		return mInflater.inflate(R.layout.agenda_row, parent, false);
	}

	
}
