package com.esri.webops.feduc2013;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.logging.Logger;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageSwitcher;
import android.widget.ListView;
import android.widget.TextView;

import com.esri.webops.feduc2013.adapter.AgendaAdapter;
import com.esri.webops.feduc2013.comman.App;
import com.esri.webops.feduc2013.db.AgendaAssetDB;
import com.esri.webops.feduc2013.db.AgendaDB;
import com.esri.webops.feduc2013.db.ExhibitDB;
import com.esri.webops.feduc2013.parser.ExhibitorParser;
import com.esri.webops.feduc2013.parser.SessionAssetParser;
import com.esri.webops.feduc2013.parser.SessionParser;
import com.google.gson.Gson;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.agenda)
public class Agenda extends BaseActivity {

    private static final String LIST_STATE_KEY = "listState";
    private static final String LIST_POSITION_KEY = "listPosition";
    private static final String ITEM_POSITION_KEY = "itemPosition";

    private Parcelable mListState = null;
    private int mListPosition = 0;
    private int mItemPosition = 0;


    @ViewById
	ListView agendaList;
	
	@ViewById
	Button calBtn;
	
	@ViewById
	TextView session_txvw;
	
	@ViewById
	TextView date_txvw;
	
	@ViewById
	TextView empty_txvw;
	
	int d = 25, m=2,y=2013;
	AgendaAdapter adapter;
	
	CharSequence[] sessionList = new CharSequence[] {
		"Meals",
		"General Session",
		"GIS Solutions EXPO",
		"Break",
		"Social Event",
		"Special Interest Group",
		"User Presentations",
		"Technical Sessions",
		"Conference Activity",
		"Registration",
		"Demo Theater Presentation",
		"User Lightning Talks",
		"Special Interest Group Meeting"
	};
	
	int [] sessionIndex =new  int []{1,2,3,4,5,6,7,8,9,10,11,12,13};
	
	int selectedSession = 0;
	
	@AfterViews
	void loadView() {
		adImageSwitcher = (ImageSwitcher) findViewById(R.id.adImageSwitcher);
		agendaList.setEmptyView(empty_txvw);
		initImageSwithcer();
		
		d = getIntPref(App.AGENDA_DAY, 25);
		m = getIntPref(App.AGENDA_MONTH, 2);
		y = getIntPref(App.AGENDA_YEAR, 2013);
		
		filterList();
		populateCalBtn();
		
		refresh_btn();
	}

    @Override
    public void onResume() {
        super.onResume();

        d = getIntPref(App.AGENDA_DAY, 25);
        m = getIntPref(App.AGENDA_MONTH, 2);
        y = getIntPref(App.AGENDA_YEAR, 2013);


        // Load data from DB and put it onto the list
        filterList();

        // Restore list state and list/item positions

        if (mListState != null)
            agendaList.onRestoreInstanceState(mListState);
        agendaList.setSelectionFromTop(mListPosition, mItemPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);

        // Retrieve list state and list/item positions
        mListState = state.getParcelable(LIST_STATE_KEY);
        mListPosition = state.getInt(LIST_POSITION_KEY);
        mItemPosition = state.getInt(ITEM_POSITION_KEY);
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        // Save list state
        mListState = agendaList.onSaveInstanceState();
        state.putParcelable(LIST_STATE_KEY, mListState);

        // Save position of first visible item
        mListPosition = agendaList.getFirstVisiblePosition();
        state.putInt(LIST_POSITION_KEY, mListPosition);

        // Save scroll position of item
        View itemView = agendaList.getChildAt(0);
        mItemPosition = itemView == null ? 0 : itemView.getTop();
        state.putInt(ITEM_POSITION_KEY, mItemPosition);
    }
	
	@Click
	void refresh_btn () {
		progressDialog = new ProgressDialog(getAvailableContext());
		progressDialog.setMessage("Loading ... please wait");
		progressDialog.show();
		loadDataFromWeb();
	}

	@Background
	void loadDataFromWeb() {
		try {
			
			AgendaDB adb = new AgendaDB(this);
			String date = "";
			date = adb.getLastUpdatedDate();
			Logger.getLogger("Esri").info("Date:" + date);
			String str = "";
			if (date != null && date.trim().length() > 0)
				str = "?where=" + URLEncoder.encode("{\"updatedAt\":{\"$gte\":{\"__type\":\"Date\",\"iso\":\"" + date + "\"}}}") + "&limit=1000";
            else
                str = "?limit=1000";
			
			Logger.getLogger("Esri").info("Requesting url:" + App.SESSION_URL +str);
			InputStream is =  makeWebPost(App.SESSION_URL +str);
			String response = parseResponseToString(is);
			Logger.getLogger("Esri").info("Response is:" + response);
			

			SessionParser parser = new Gson().fromJson(response, SessionParser.class);
			if (parser != null && parser.sessionList != null && parser.sessionList.size() > 0) {
				for (SessionParser.Session session : parser.sessionList) {
					long result = adb.update(session);
//                    Logger.getLogger("Esri").info("Update result is:" + result);
					if (result == 0) {
                        result = adb.insert(session);
//                        Logger.getLogger("Esri").info("Insert result is:" + result);
                    }
				}
			}

            date = "";
			AgendaAssetDB asdb = new AgendaAssetDB(this);
			date = asdb.getLastUpdatedDate();
			Logger.getLogger("Esri").info("Date For Asset :" + date);
			str = "";
			if (date != null && date.trim().length() > 0)
				str = "?where=" + URLEncoder.encode("{\"updatedAt\":{\"$gte\":{\"__type\":\"Date\",\"iso\":\"" + date + "\"}}}")  + "&limit=1000";
            else
                str = "?limit=1000";
			
			Logger.getLogger("Esri").info("Requesting url for Asset:" + App.SESSION_ASSET_URL +str);
			is =  makeWebPost(App.SESSION_ASSET_URL +str);
			response = parseResponseToString(is);
			Logger.getLogger("Esri").info("Response for Asset is:" + response);
			

			SessionAssetParser sparser = new Gson().fromJson(response, SessionAssetParser.class);
			if (sparser != null && sparser.sessionAssetList != null && sparser.sessionAssetList.size() > 0) {
				for (SessionAssetParser.SessionAsset session : sparser.sessionAssetList) {
					long result = asdb.update(session);
//                    Logger.getLogger("Esri").info("Session Asset Update result is:" + result);
                    if (result == 0) {
                        result = asdb.insert(session);
//                        Logger.getLogger("Esri").info("Session Asset Insert result is:" + result);
                    }
				}
			}
			
			
			date = "";
			ExhibitDB edb = new ExhibitDB(this);
			date = edb.getLastUpdatedDate();
			Logger.getLogger("Esri").info("Date For Exhibitor :" + date);
			str = "";
			if (date != null && date.trim().length() > 0)
				str = "?where=" + URLEncoder.encode("{\"updatedAt\":{\"$gte\":{\"__type\":\"Date\",\"iso\":\"" + date + "\"}}}")  + "&limit=1000";
            else
                str = "?limit=1000";
			
			Logger.getLogger("Esri").info("Requesting url for Exhibitor:" + App.EXHIBITOR_URL +str);
			is =  makeWebPost(App.EXHIBITOR_URL +str);
			response = parseResponseToString(is);
			Logger.getLogger("Esri").info("Response for Exhibitor is:" + response);
//
			ExhibitorParser eparser = new Gson().fromJson(response, ExhibitorParser.class);
            String recordsToDelete = "";
			if (eparser != null && eparser.exhibitorList != null && eparser.exhibitorList.size() > 0) {
				for (ExhibitorParser.Exhibitor session : eparser.exhibitorList) {
					long result = edb.update(session);
//                    Logger.getLogger("Esri").info("Exhibitor Update result is:" + result);
                    if (result == 0) {
                        result = edb.insert(session);
//                        Logger.getLogger("Esri").info("Exhibitor Insert result is:" + result);
                        recordsToDelete += "'" +  session.objectId + "',";
                    }
				}

                if (recordsToDelete.trim().length() > 0) {
                    recordsToDelete = recordsToDelete.substring(0, recordsToDelete.length()-1);
                    Logger.getLogger("Esri").info("Records to delete : " + recordsToDelete);
                    edb.delete(recordsToDelete);
                }

			}
			
		}
		catch(Exception ex) {
//			Logger.getLogger("Esri").log(Level.INFO,"Error in webcall",ex);
		}
		finally {
			updateUI();
		}
	}
	
	@UiThread
	void updateUI() {
		progressDialog.dismiss();
		filterList();
	}
	
	@Click
	void map_btn() {
		startActivity(new Intent(this,Map_.class));
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
                
                setPref(App.AGENDA_DAY, d);
                setPref(App.AGENDA_MONTH, m);
                setPref(App.AGENDA_YEAR, y);
                
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
			session_txvw.   setText("All Sessions");
		else
		session_txvw.setText(sessionList[selectedSession]);
		populateCalBtn();
		final AgendaDB db = new AgendaDB(Agenda.this);
		String filter = "";
		if (selectedSession == 0 )
			filter = "ZSTARTDAY=" + d + " AND ZSTARTMONTH=" + m + " AND ZSTARTYEAR=" + y;
		else
			filter = "ZSTARTDAY=" + d + " AND ZSTARTMONTH=" + m + " AND ZSTARTYEAR=" + y + " AND ZEVENTTYPEID='" + (sessionIndex[selectedSession]) + "'";
			
//		Logger.getLogger("ESRI").info("Fiter:" + filter);
		Cursor c = db.fetchAllRow(filter,"ZSTARTDATE asc");
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
	public void all_txvw() {
		int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 7, getResources().getDisplayMetrics());
		findViewById(R.id.all_txvw).setBackgroundResource(R.drawable.tab_left_selected);
		findViewById(R.id.all_txvw).setPadding(padding, padding, padding, padding);
		findViewById(R.id.type_txvw).setBackgroundResource(R.drawable.tab_right_normal);
		findViewById(R.id.type_txvw).setPadding( padding, padding, padding, padding);
		
		selectedSession = 0;
		filterList();
	}

	@Click
	public void type_txvw() {
		int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 7, getResources().getDisplayMetrics());
		findViewById(R.id.all_txvw).setBackgroundResource(R.drawable.tab_left_normal);
		findViewById(R.id.all_txvw).setPadding(padding, padding, padding, padding);
		findViewById(R.id.type_txvw).setBackgroundResource(R.drawable.tab_right_selected);
		findViewById(R.id.type_txvw).setPadding(padding, padding, padding, padding);
		
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
		
		date_txvw.setText(dayStr + " " + d + " " + monthStr + " " +y);
	}
	
	private Context getAvailableContext() {
		if (getParent() != null)
			return getParent();
		else
			return this;
	}
}
