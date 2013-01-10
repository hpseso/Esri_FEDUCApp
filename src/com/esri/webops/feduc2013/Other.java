package com.esri.webops.feduc2013;


import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.other)
public class Other extends BaseActivity {
	
	boolean isDevice = false;

	@ViewById(R.id.menu_gallery)
	Gallery menuGallery;
	
	@ViewById
	Button survey_btn;
	
	@ViewById
	Button sdate_cal1_btn,sdate_cal2_btn;
	
	@ViewById
	Button plink1_btn,plink2_btn,plink3_btn,plink4_btn,plink5_btn;
	
	@ViewById
	TextView plink1_txvw,plink2_txvw,plink3_txvw,plink4_txvw,plink5_txvw;
	
	@ViewById
	Button no1_btn,no2_btn,no3_btn,no4_btn,no11_btn,no12_btn,no21_btn,no22_btn,no23_btn;
	
	@ViewById
	TextView help1_txvw,help2_txvw,help3_txvw,help4_txvw,help11_txvw,help12_txvw,help21_txvw,help22_txvw,help23_txvw;;
	
	@ViewById
	ViewFlipper viewFlipper1;
	
	@ViewById
	Button prev_btn,next_btn;
	
	@ViewById
	LinearLayout survey_container,sdate_container,privacy_container;
	
	@ViewById
	FrameLayout demo_container;
	
	@AfterViews
	void load() {
		adImageSwitcher = (ImageSwitcher) findViewById(R.id.adImageSwitcher);
		initImageSwithcer();
		if (menuGallery != null) {
			isDevice = true;
			
			ArrayList<String> list = new ArrayList<String>();
			list.add("App Demo");
			list.add("Survey");
			list.add("Save the Date");
			list.add("Privacy/Legal");
			
			menuGallery.setAdapter(new ImageAdapter(this, list));
			menuGallery.setCallbackDuringFling(false);
			menuGallery.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long id) {
					showView(position);
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {}
			});
			
			menuGallery.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {	}
			});
		}
		
		survey_btn.setOnClickListener(linkListener);
		survey_btn.setTag("http://www.esri.com/pugiosappsurvey");
		
		sdate_cal1_btn.setTag("1");
		sdate_cal1_btn.setOnClickListener(calListener);
		
		sdate_cal2_btn.setTag("2");
		sdate_cal2_btn.setOnClickListener(calListener);
		
		plink1_btn.setOnClickListener(linkListener);
		plink1_btn.setTag("http://www.esri.com/legal/index.html");
		
		plink2_btn.setOnClickListener(linkListener);
		plink2_btn.setTag("http://resources.arcgis.com/content/arcgis-ios");
		
		plink3_btn.setOnClickListener(linkListener);
		plink3_btn.setTag("http://resources.arcgis.com/content/community-maps/world-imagery-map");
		
		plink4_btn.setOnClickListener(linkListener);
		plink4_btn.setTag("http://www.openstreetmap.org/");
		
		plink5_btn.setOnClickListener(linkListener);
		plink5_btn.setTag("http://resources.arcgis.com/content/community-maps/world-topographic-map");
		
		plink1_txvw.setOnClickListener(linkListener);
		plink1_txvw.setTag("http://www.esri.com/legal/index.html");
		
		plink2_txvw.setOnClickListener(linkListener);
		plink2_txvw.setTag("http://resources.arcgis.com/content/arcgis-ios");
		
		plink3_txvw.setOnClickListener(linkListener);
		plink3_txvw.setTag("http://resources.arcgis.com/content/community-maps/world-imagery-map");
		
		plink4_txvw.setOnClickListener(linkListener);
		plink4_txvw.setTag("http://www.openstreetmap.org/");
		
		plink5_txvw.setOnClickListener(linkListener);
		plink5_txvw.setTag("http://resources.arcgis.com/content/community-maps/world-topographic-map");
	}
	
	@Click
	void map_btn() {
		startActivity(new Intent(this,Map_.class));
	}
	
	void showView(int view) {
		switch (view) {
			case 0:
				demo_container.setVisibility(View.VISIBLE);
				survey_container.setVisibility(View.GONE);
				sdate_container.setVisibility(View.GONE);
				privacy_container.setVisibility(View.GONE);
			break;
			case 1:
				survey_container.setVisibility(View.VISIBLE);
				demo_container.setVisibility(View.GONE);
				sdate_container.setVisibility(View.GONE);
				privacy_container.setVisibility(View.GONE);
			break;
			case 2:
				sdate_container.setVisibility(View.VISIBLE);
				survey_container.setVisibility(View.GONE);
				demo_container.setVisibility(View.GONE);
				privacy_container.setVisibility(View.GONE);
			break;
			case 3:
				privacy_container.setVisibility(View.VISIBLE);
				survey_container.setVisibility(View.GONE);
				sdate_container.setVisibility(View.GONE);
				demo_container.setVisibility(View.GONE);
			break;

		default:
			break;
		}
	}
	
	public class ImageAdapter extends BaseAdapter {
        int mGalleryItemBackground;
        private Context mContext;
        private ArrayList<String> mImageIds;
        public ImageAdapter(Context c, ArrayList<String> items) {
            mContext = c;
            this.mImageIds = items;
        }

        public int getCount() {
            return mImageIds.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
        	Display display = getWindowManager().getDefaultDisplay();
        	
            TextView i = new TextView(mContext);
            i.setTextAppearance(getApplicationContext(), R.style.boldText);
            i.setText(mImageIds.get(position));
            if (display.getWidth() > 700) 
            	i.setLayoutParams(new Gallery.LayoutParams(300, 50));
            else
            	i.setLayoutParams(new Gallery.LayoutParams(200, 50));
            i.setPadding(6, 6, 6, 6);
            i.setGravity(Gravity.CENTER);
            return i;
        }
    }
	
	@Click
	void prev_btn() {
		viewFlipper1.showPrevious();
		if (Integer.parseInt(viewFlipper1.getCurrentView().getTag().toString()) > 1)
			prev_btn.setVisibility(View.VISIBLE);
		else
			prev_btn.setVisibility(View.GONE);
		
		if (Integer.parseInt(viewFlipper1.getCurrentView().getTag().toString()) < 2)
			next_btn.setVisibility(View.VISIBLE);
		else
			next_btn.setVisibility(View.GONE);
	}
	
	@Click
	void next_btn() {
		viewFlipper1.showNext();
		if (Integer.parseInt(viewFlipper1.getCurrentView().getTag().toString()) > 1)
			prev_btn.setVisibility(View.VISIBLE);
		else
			prev_btn.setVisibility(View.GONE);
		
		if (Integer.parseInt(viewFlipper1.getCurrentView().getTag().toString()) < 2)
			next_btn.setVisibility(View.VISIBLE);
		else
			next_btn.setVisibility(View.GONE);
	}
	
	@Click
	void help1_txvw() {
		if (help1_txvw.getVisibility() == View.VISIBLE)
			help1_txvw.setVisibility(View.GONE);
	}
	
	@Click
	void help2_txvw() {
		if (help2_txvw.getVisibility() == View.VISIBLE)
			help2_txvw.setVisibility(View.GONE);
	}
	
	@Click
	void help3_txvw() {
		if (help3_txvw.getVisibility() == View.VISIBLE)
			help3_txvw.setVisibility(View.GONE);
	}
	
	@Click
	void help4_txvw() {
		if (help4_txvw.getVisibility() == View.VISIBLE)
			help4_txvw.setVisibility(View.GONE);
	}
	
	@Click
	void help11_txvw() {
		if (help11_txvw.getVisibility() == View.VISIBLE)
			help11_txvw.setVisibility(View.GONE);
	}
	
	@Click
	void help12_txvw() {
		if (help12_txvw.getVisibility() == View.VISIBLE)
			help12_txvw.setVisibility(View.GONE);
	}
	
	@Click
	void help21_txvw() {
		if (help21_txvw.getVisibility() == View.VISIBLE)
			help21_txvw.setVisibility(View.GONE);
	}
	
	@Click
	void help22_txvw() {
		if (help22_txvw.getVisibility() == View.VISIBLE)
			help22_txvw.setVisibility(View.GONE);
	}
	
	@Click
	void help23_txvw() {
		if (help23_txvw.getVisibility() == View.VISIBLE)
			help23_txvw.setVisibility(View.GONE);
	}
	
	@Click
	void no1_btn() {
		help1_txvw.clearAnimation();
		help1_txvw.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
		help1_txvw.setVisibility(View.VISIBLE);
		help2_txvw.setVisibility(View.GONE);
		help3_txvw.setVisibility(View.GONE);
		
		if (help4_txvw != null) {
			help4_txvw.setVisibility(View.GONE);
			help11_txvw.setVisibility(View.GONE);
			help12_txvw.setVisibility(View.GONE);
			help21_txvw.setVisibility(View.GONE);
			help22_txvw.setVisibility(View.GONE);
			help23_txvw.setVisibility(View.GONE);
		}
	}
	
	@Click
	void no2_btn() {
		help2_txvw.clearAnimation();
		help2_txvw.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
		help2_txvw.setVisibility(View.VISIBLE);
		help1_txvw.setVisibility(View.GONE);
		help3_txvw.setVisibility(View.GONE);
		
		if (help4_txvw != null) {
			help4_txvw.setVisibility(View.GONE);
			help11_txvw.setVisibility(View.GONE);
			help12_txvw.setVisibility(View.GONE);
			help21_txvw.setVisibility(View.GONE);
			help22_txvw.setVisibility(View.GONE);
			help23_txvw.setVisibility(View.GONE);
		}
	}
	
	@Click
	void no3_btn() {
		help3_txvw.clearAnimation();
		help3_txvw.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
		help3_txvw.setVisibility(View.VISIBLE);
		help1_txvw.setVisibility(View.GONE);
		help2_txvw.setVisibility(View.GONE);
		if (help4_txvw != null) {
			help4_txvw.setVisibility(View.GONE);
			help11_txvw.setVisibility(View.GONE);
			help12_txvw.setVisibility(View.GONE);
			help21_txvw.setVisibility(View.GONE);
			help22_txvw.setVisibility(View.GONE);
			help23_txvw.setVisibility(View.GONE);
		}
	}
	
	@Click
	void no4_btn() {
		help4_txvw.clearAnimation();
		help4_txvw.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
		help4_txvw.setVisibility(View.VISIBLE);
		help3_txvw.setVisibility(View.GONE);
		help1_txvw.setVisibility(View.GONE);
		help2_txvw.setVisibility(View.GONE);
		help11_txvw.setVisibility(View.GONE);
		help12_txvw.setVisibility(View.GONE);
		help21_txvw.setVisibility(View.GONE);
		help22_txvw.setVisibility(View.GONE);
		help23_txvw.setVisibility(View.GONE);
	}
	
	@Click
	void no11_btn() {
		help11_txvw.clearAnimation();
		help11_txvw.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
		help11_txvw.setVisibility(View.VISIBLE);
		help1_txvw.setVisibility(View.GONE);
		help2_txvw.setVisibility(View.GONE);
		help3_txvw.setVisibility(View.GONE);
		help4_txvw.setVisibility(View.GONE);
		help12_txvw.setVisibility(View.GONE);
		help21_txvw.setVisibility(View.GONE);
		help22_txvw.setVisibility(View.GONE);
		help23_txvw.setVisibility(View.GONE);
	}
	
	@Click
	void no12_btn() {
		help12_txvw.clearAnimation();
		help12_txvw.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
		help12_txvw.setVisibility(View.VISIBLE);
		help1_txvw.setVisibility(View.GONE);
		help2_txvw.setVisibility(View.GONE);
		help3_txvw.setVisibility(View.GONE);
		help4_txvw.setVisibility(View.GONE);
		help11_txvw.setVisibility(View.GONE);
		help21_txvw.setVisibility(View.GONE);
		help22_txvw.setVisibility(View.GONE);
		help23_txvw.setVisibility(View.GONE);
	}
	
	@Click
	void no21_btn() {
		help21_txvw.clearAnimation();
		help21_txvw.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
		help21_txvw.setVisibility(View.VISIBLE);
		help1_txvw.setVisibility(View.GONE);
		help2_txvw.setVisibility(View.GONE);
		help3_txvw.setVisibility(View.GONE);
		help4_txvw.setVisibility(View.GONE);
		help11_txvw.setVisibility(View.GONE);
		help12_txvw.setVisibility(View.GONE);
		help22_txvw.setVisibility(View.GONE);
		help23_txvw.setVisibility(View.GONE);
	}
	
	@Click
	void no22_btn() {
		help22_txvw.clearAnimation();
		help22_txvw.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
		help22_txvw.setVisibility(View.VISIBLE);
		help1_txvw.setVisibility(View.GONE);
		help2_txvw.setVisibility(View.GONE);
		help3_txvw.setVisibility(View.GONE);
		help4_txvw.setVisibility(View.GONE);
		help11_txvw.setVisibility(View.GONE);
		help12_txvw.setVisibility(View.GONE);
		help21_txvw.setVisibility(View.GONE);
		help23_txvw.setVisibility(View.GONE);
	}
	
	@Click
	void no23_btn() {
		help23_txvw.clearAnimation();
		help23_txvw.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
		help23_txvw.setVisibility(View.VISIBLE);
		help1_txvw.setVisibility(View.GONE);
		help2_txvw.setVisibility(View.GONE);
		help3_txvw.setVisibility(View.GONE);
		help4_txvw.setVisibility(View.GONE);
		help11_txvw.setVisibility(View.GONE);
		help12_txvw.setVisibility(View.GONE);
		help21_txvw.setVisibility(View.GONE);
		help22_txvw.setVisibility(View.GONE);
	}
	
	
	OnClickListener linkListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			loadBrowser(v.getTag().toString());
		}
	};
	
	OnClickListener calListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			loadCalendar(Integer.parseInt(v.getTag().toString()));
		}
	};
}