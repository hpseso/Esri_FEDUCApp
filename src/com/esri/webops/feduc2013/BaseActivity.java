package com.esri.webops.feduc2013;

import java.util.Calendar;
import java.util.logging.Logger;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ViewSwitcher.ViewFactory;

public class BaseActivity extends Activity  implements ViewFactory  {

	ImageSwitcher adImageSwitcher;
	
	public void loadMap(int which) {
		Intent intent = new Intent(this,Map_.class);
		intent.putExtra("MAP_TYPE", which);
		startActivity(intent);
	}
	
	public void call(String number) {
		Logger.getLogger("ESRI").info("Calling :" + number);
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
		startActivityForResult(intent, 101);
	}
	
	public void loadBrowser(String url) {
		Logger.getLogger("ESRI").info("Loading :" + url);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(url));
		startActivityForResult(intent, 101);
	}
	
	public void email(String email) {
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {email});
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Inquiry from the PUG 2012 Conference App");
		emailIntent.setType("plain/text");
		startActivity(emailIntent);
	}
	
	public void loadCalendar(int type) {
		String title = "";
		long startTime = Calendar.getInstance().getTimeInMillis();
		long endTime = Calendar.getInstance().getTimeInMillis() + (1000*60);
		switch (type) {
			case 1: {
				title = "Esri International User Conference 2012";
				
				Calendar scal = Calendar.getInstance();
				scal.set(Calendar.DAY_OF_MONTH, 23);
				scal.set(Calendar.MONTH, Calendar.JULY);
				scal.set(Calendar.YEAR, 2012);
				startTime = scal.getTimeInMillis();
				
				Calendar ecal = Calendar.getInstance();
				ecal.set(Calendar.DAY_OF_MONTH, 27);
				ecal.set(Calendar.MONTH, Calendar.JULY);
				ecal.set(Calendar.YEAR, 2012);
				startTime = ecal.getTimeInMillis();
			}
			break;
			
			case 2: {
				title = "Esri Partner Conference 2013";
				
				Calendar scal = Calendar.getInstance();
				scal.set(Calendar.DAY_OF_MONTH, 23);
				scal.set(Calendar.MONTH, Calendar.MARCH);
				scal.set(Calendar.YEAR, 2013);
				startTime = scal.getTimeInMillis();
				
				Calendar ecal = Calendar.getInstance();
				ecal.set(Calendar.DAY_OF_MONTH, 26);
				ecal.set(Calendar.MONTH, Calendar.MARCH);
				ecal.set(Calendar.YEAR, 2013);
				startTime = ecal.getTimeInMillis();
			}
			break;
		}
		
		Intent intent = new Intent(Intent.ACTION_EDIT);
		intent.setType("vnd.android.cursor.item/event");
		intent.putExtra("title", title);
		intent.putExtra("description", "Some description");
		intent.putExtra("beginTime", startTime);
		intent.putExtra("endTime", endTime);
		startActivity(intent);
	}
	
	protected void initImageSwithcer() {
		adImageSwitcher.setFactory(BaseActivity.this);
		adImageSwitcher.setAnimation(AnimationUtils.loadAnimation(BaseActivity.this, android.R.anim.fade_in));
		adImageSwitcher.setAnimation(AnimationUtils.loadAnimation(BaseActivity.this, android.R.anim.fade_out));
	}
	
	protected Handler adHandler = new Handler() {
		
		@Override
		public void handleMessage(Message msg) {
			
			if (adImageSwitcher != null) {
				
				SharedPreferences.Editor prefEdit = getSharedPreferences("ESRI_PREF",MODE_PRIVATE).edit();
				SharedPreferences prefRead = getSharedPreferences("ESRI_PREF",MODE_PRIVATE);
				
				int imageNo = prefRead.getInt("LAST_IMAGE", 0);
				
				
				adImageSwitcher.setImageResource(sponsorImages[imageNo]);
				adHandler.sendEmptyMessageDelayed(0, 10000);
				
				if (imageNo == 14)
					imageNo = 0;
				else
					imageNo +=1;
				
				prefEdit.putInt("LAST_IMAGE", imageNo);
				prefEdit.commit();
			}
			super.handleMessage(msg);
		}
	};
	
	@Override
	public void onResume() {
		super.onResume();
		Logger.getLogger("ESRI").info("Sending signal to change image");
		adHandler.sendEmptyMessageDelayed(0, 0);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		Logger.getLogger("ESRI").info("Sending signal to quit");
		adHandler.removeMessages(0);
	}
	
	@Override
	public View makeView() {
		ImageView iView = new ImageView(this);
        iView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageSwitcher.LayoutParams params = new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);

        iView.setLayoutParams(params);
        iView.setBackgroundColor(0xFFFFFFFF);
        iView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				SharedPreferences prefRead = getSharedPreferences("ESRI_PREF",MODE_PRIVATE);
				int imageNo = prefRead.getInt("LAST_IMAGE", 0);
				loadBrowser(sponsorURLS[imageNo]);
			}
		});
        
        return iView;
	}
	
	private Integer[] sponsorImages = {
			R.drawable.cartopac_survey_logo_shadow,R.drawable.coler_colantonio_logo_shadow,
			R.drawable.digital_globe_logo_shadow,R.drawable.eagle_information_mapping_logo_shadow,
			R.drawable.geofields_logo_shadow,R.drawable.geologic_logo_shadow,
			R.drawable.global_information_systems_logo_shadow,R.drawable.inner_corridor_logo_shadow,
			R.drawable.latitude_geographics_logo_shadow,R.drawable.neuralog_logo_shadow,
			R.drawable.new_century_logo_shadow,R.drawable.petris_logo_shadow,
			R.drawable.petrosys_logo_shadow,R.drawable.petroweb_logo_shadow,
			R.drawable.valtus_logo_shadow
	};
	
	private String[] sponsorURLS = {
			"http://www.cartopac.com","http://www.col-col-geospatial.com",
			"http://www.digitalglobe.com","http://www.eaglemap.com",
			"http://www.geofields.com","http://www.geologic.com",
			"http://www.globalinformationsystems.com","http://www.teachmegis.com",
			"http://www.latitudegeo.com","http://www.neuralog.com",
			"http://www.newcenturysoftware.com","http://www.Petris.com",
			"http://www.petrosys.com.au","http://www.petroweb.com",
			"http://www.valtus.com"
	};
}
