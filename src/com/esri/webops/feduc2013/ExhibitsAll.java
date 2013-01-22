package com.esri.webops.feduc2013;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.logging.Logger;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageSwitcher;
import android.widget.ListView;
import android.widget.TextView;

import com.esri.webops.feduc2013.adapter.ExhibitAdapter;
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

@EActivity(R.layout.exhibits)
public class ExhibitsAll extends BaseActivity {

    private static final String LIST_STATE_KEY = "listState";
    private static final String LIST_POSITION_KEY = "listPosition";
    private static final String ITEM_POSITION_KEY = "itemPosition";

    private Parcelable mListState = null;
    private int mListPosition = 0;
    private int mItemPosition = 0;

	@ViewById
	ListView exhibitList;
	
	@ViewById
	TextView all_txvw,sponsor_txvw,hours_txvw;
	
	@ViewById
	TextView empty_txvw;
	
	@AfterViews
	void loadView() {
		
		adImageSwitcher = (ImageSwitcher) findViewById(R.id.adImageSwitcher);
		initImageSwithcer();
		
		exhibitList.setEmptyView(empty_txvw);
		
		loadData();
		refresh_btn();
		
		
	}

    @Override
    public void onResume() {
        super.onResume();

        // Load data from DB and put it onto the list
        loadData();

        // Restore list state and list/item positions

        if (mListState != null)
            exhibitList.onRestoreInstanceState(mListState);
        exhibitList.setSelectionFromTop(mListPosition, mItemPosition);
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
        mListState = exhibitList.onSaveInstanceState();
        state.putParcelable(LIST_STATE_KEY, mListState);

        // Save position of first visible item
        mListPosition = exhibitList.getFirstVisiblePosition();
        state.putInt(LIST_POSITION_KEY, mListPosition);

        // Save scroll position of item
        View itemView = exhibitList.getChildAt(0);
        mItemPosition = itemView == null ? 0 : itemView.getTop();
        state.putInt(ITEM_POSITION_KEY, mItemPosition);
    }
	
	private void loadData() {
		ExhibitDB db = new ExhibitDB(this);
		Cursor c = db.fetchAllRow();
		ExhibitAdapter adapter = new ExhibitAdapter(this, c, true);
		exhibitList.setAdapter(adapter);
		exhibitList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?>  parent, View vw, int position, long id) {
				Intent intent = new Intent(ExhibitsAll.this,ExhibitsDetail_.class);
				intent.putExtra("ID", (int)id);
				View view = ExhibitsGroup.group.getLocalActivityManager()  
			            .startActivity("", intent  
			                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))  
			                    .getDecorView();  
				ExhibitsGroup.group.replaceView(view);
			}
		});
	}
	
	@Click
	void map_btn() {
		startActivity(new Intent(this,Map_.class));
	}
	
	private Context getAvailableContext() {
		if (getParent() != null)
			return getParent();
		else
			return this;
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
                str = "?where=" + URLEncoder.encode("{\"updatedAt\":{\"$gte\":{\"__type\":\"Date\",\"iso\":\"" + date + "\"}}}");
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
                    Logger.getLogger("Esri").info("Update result is:" + result);
                    if (result == 0) {
                        result = adb.insert(session);
                        Logger.getLogger("Esri").info("Insert result is:" + result);
                    }
                }
            }

            date = "";
            AgendaAssetDB asdb = new AgendaAssetDB(this);
            date = asdb.getLastUpdatedDate();
            Logger.getLogger("Esri").info("Date For Asset :" + date);
            str = "";
            if (date != null && date.trim().length() > 0)
                str = "?where=" + URLEncoder.encode("{\"updatedAt\":{\"$gte\":{\"__type\":\"Date\",\"iso\":\"" + date + "\"}}}");
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
                    Logger.getLogger("Esri").info("Session Asset Update result is:" + result);
                    if (result == 0) {
                        result = asdb.insert(session);
                        Logger.getLogger("Esri").info("Session Asset Insert result is:" + result);
                    }
                }
            }


            date = "";
            ExhibitDB edb = new ExhibitDB(this);
            date = edb.getLastUpdatedDate();
            Logger.getLogger("Esri").info("Date For Exhibitor :" + date);
            str = "";
            if (date != null && date.trim().length() > 0)
                str = "?where=" + URLEncoder.encode("{\"updatedAt\":{\"$gte\":{\"__type\":\"Date\",\"iso\":\"" + date + "\"}}}");
            else
                str = "?limit=1000";

            Logger.getLogger("Esri").info("Requesting url for Exhibitor:" + App.EXHIBITOR_URL +str);
            is =  makeWebPost(App.EXHIBITOR_URL +str);
            response = parseResponseToString(is);
            Logger.getLogger("Esri").info("Response for Exhibitor is:" + response);

            ExhibitorParser eparser = new Gson().fromJson(response, ExhibitorParser.class);
            if (eparser != null && eparser.exhibitorList != null && eparser.exhibitorList.size() > 0) {
                for (ExhibitorParser.Exhibitor session : eparser.exhibitorList) {
                    long result = edb.update(session);
                    Logger.getLogger("Esri").info("Exhibitor Update result is:" + result);
                    if (result == 0) {
                        result = edb.insert(session);
                        Logger.getLogger("Esri").info("Exhibitor Insert result is:" + result);
                    }
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
		loadData();
	}
	
	
	@Click
	void sponsor_txvw() {
		Intent intent = new Intent(this,ExhibitsSponsor_.class);
		View view = ExhibitsGroup.group.getLocalActivityManager()  
	            .startActivity("", intent  
	                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))  
	                    .getDecorView();  
		ExhibitsGroup.group.replaceView(view);
	}
	
	@Click
	void hours_txvw() {
		Intent intent = new Intent(this,ExhibitsHours_.class);
		startActivity(intent);
	}
}
