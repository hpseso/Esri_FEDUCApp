package com.esri.webops.feduc2013;

import java.util.Calendar;
import java.util.logging.Logger;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageSwitcher;
import android.widget.ListView;
import android.widget.TextView;

import com.esri.webops.feduc2013.adapter.AgendaAdapter;
import com.esri.webops.feduc2013.db.AgendaDB;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.agenda)
public class Agenda extends BaseActivity {
	
	@ViewById
	ListView agendaList;
	
	@ViewById
	Button calBtn;
	
	@ViewById
	TextView session_txvw;
	
	@ViewById
	TextView empty_txvw;
	
	int d = 30, m=4,y=2012;
	AgendaAdapter adapter;
	CharSequence[] sessionList = new CharSequence[] {
		"Demo Theater Presentation",
		"General Session",
		"GIS Solutions EXPO",
		"Panel Discussion",
		"Social Event",
		"Technical Workshop",
		"User Group Meeting",
		"User Presentation",
		"Lightning Talk"
	};
	int selectedSession = 0;
	
	
	
	@AfterViews
	void loadView() {
		adImageSwitcher = (ImageSwitcher) findViewById(R.id.adImageSwitcher);
		agendaList.setEmptyView(empty_txvw);
		initImageSwithcer();
		filterList();
	}
	
	@Click
	void map_btn() {
		startActivity(new Intent(this,Map_.class));
	}
	
	@Click
	void next_btn() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, d);
		cal.set(Calendar.MONTH, m-1);
		cal.set(Calendar.YEAR, y);
		
		cal.add(Calendar.DAY_OF_MONTH, 1);
		
		
		d = cal.get(Calendar.DAY_OF_MONTH);
		m = cal.get(Calendar.MONTH) + 1;
		y = cal.get(Calendar.YEAR);
		
		filterList();
	}
	
	@Click
	void prev_btn() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, d);
		cal.set(Calendar.MONTH, m-1);
		cal.set(Calendar.YEAR, y);
		
		cal.add(Calendar.DAY_OF_MONTH, -1);
		
		
		d = cal.get(Calendar.DAY_OF_MONTH);
		m = cal.get(Calendar.MONTH) + 1;
		y = cal.get(Calendar.YEAR);
		
		filterList();
	}
	
	@Click
	void cal_btn() {
		final Dialog dialog = new Dialog(getParent());
        dialog.setContentView(R.layout.date_dialog);
        
        final DatePicker datePicker = (DatePicker)dialog.findViewById(R.id.datePicker);
        dialog.setTitle("Select Date");
        datePicker.init(y, m-1, d, null);
        ((Button)dialog.findViewById(R.id.cancel_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ((Button)dialog.findViewById(R.id.ok_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d = datePicker.getDayOfMonth();
                y = datePicker.getYear();
                m = datePicker.getMonth()+1;
                filterList();
                dialog.dismiss();
            }
        });
        dialog.show();
        
	}
	
	private void filterList() {
		
		if (sessionList == null )
			session_txvw.setText("All Sessions");
		else if (selectedSession == 0)
			session_txvw.setText("All Sessions");
		else
		session_txvw.setText(sessionList[selectedSession]);
		populateCalBtn();
		final AgendaDB db = new AgendaDB(Agenda.this);
		String filter = "";
		if (selectedSession == 0 )
			filter = "ZSTARTDAY=" + d + " AND ZSTARTMONTH=" + m + " AND ZSTARTYEAR=" + y;
		else
			filter = "ZSTARTDAY=" + d + " AND ZSTARTMONTH=" + m + " AND ZSTARTYEAR=" + y + " AND ZEVENTTYPEID='" + (selectedSession + 1) + "'";
			
		Logger.getLogger("ESRI").info("Fiter:" + filter);
		Cursor c = db.fetchAllRow(filter,"ZSTARTHOUR asc");
		adapter = new AgendaAdapter(this, c, true);
		agendaList.setAdapter(adapter);
	}
	
	public void agendaItemClick(int id) {
		Intent intent = new Intent(this,AgendaDetail_.class);
		intent.putExtra("ID", id);
		View view = AgendaGroup.group.getLocalActivityManager()  
	            .startActivity("", intent  
	                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))  
	                    .getDecorView();  
		AgendaGroup.group.replaceView(view);
	}
	
	@Click
	public void sort_btn() {
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(getParent());
		dialog.setTitle("Select option");
		dialog.setSingleChoiceItems(sessionList, selectedSession, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dlg, int index) {
				selectedSession = index;
				filterList();
				dlg.dismiss();
			}
		});
		dialog.show();
	}
	
	private void populateCalBtn() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, d);
		cal.set(Calendar.MONTH, m-1);
		cal.set(Calendar.YEAR, y);
		
		String dayStr= "";
		String monthStr = "";
		
		int day = cal.get(Calendar.DAY_OF_WEEK);
		int month = cal.get(Calendar.MONTH);
		
		switch (day) {
			case Calendar.SUNDAY:
				dayStr = "Sunday";
				break;
			case Calendar.MONDAY:
				dayStr = "Monday";
				break;
			case Calendar.TUESDAY:
				dayStr = "Tuesday";
				break;
			case Calendar.THURSDAY:
				dayStr = "Thursday";
				break;
			case Calendar.FRIDAY:
				dayStr = "Friday";
				break;
			case Calendar.SATURDAY:
				dayStr = "Saturday";
				break;
			case Calendar.WEDNESDAY:
				dayStr = "Wednesday";
				break;
		}
		
		switch (month) {
			case Calendar.JANUARY:
				monthStr = "January";
				break;
			case Calendar.FEBRUARY:
				monthStr = "February";
				break;
			case Calendar.MARCH:
				monthStr = "March";
				break;
			case Calendar.APRIL:
				monthStr = "April";
				break;
			case Calendar.MAY:
				monthStr = "May";
				break;
			case Calendar.JUNE:
				monthStr = "June";
				break;
			case Calendar.JULY:
				monthStr = "July";
				break;
			case Calendar.AUGUST:
				monthStr = "August";
				break;
			case Calendar.SEPTEMBER:
				monthStr = "September";
				break;
			case Calendar.OCTOBER:
				monthStr = "October";
				break;
			case Calendar.NOVEMBER:
				monthStr = "November";
				break;
			case Calendar.DECEMBER:
				monthStr = "December";
				break;
		}
		
		calBtn.setText(dayStr + " " + d + " " + monthStr + " " +y);
	}
}
