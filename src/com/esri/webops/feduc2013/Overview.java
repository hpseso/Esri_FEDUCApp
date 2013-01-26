package com.esri.webops.feduc2013;


import java.util.ArrayList;
import java.util.logging.Logger;

import android.content.Context;
import android.content.Intent;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esri.core.geometry.Point;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.overview)
public class Overview extends BaseActivity {

	boolean isDevice = false;

	@ViewById(R.id.menu_gallery)
	Gallery menuGallery;
	
	@ViewById
	Button blog_btn,in_btn,fb_btn,tweet_btn;
	
	@ViewById
	Button contact_locate_btn,contact_call_btn,contact_email_btn;
	
	@ViewById
	TextView call_txvw,email_txvw;
	
	@ViewById
	Button venue_locate_address1_btn,venue_locate_address2_btn,venue_call1_btn,venue_link1_btn,venue_call2_btn,venue_link2_btn;
	
	@ViewById
	TextView venue_call1_txvw,venue_call2_txvw,venue_link1_txvw,venue_link2_txvw;


	@ViewById
	Button trans_locate1_btn,trans_link1_btn,trans_call1_btn;
	
	@ViewById
	Button trans_locate2_btn,trans_link2_btn,trans_call2_btn;
	
	@ViewById
	Button trans_locate3_btn,trans_link3_btn,trans_call3_btn;
	
//	@ViewById
//	Button trans_locate4_btn,trans_link4_btn,trans_call4_btn;
	
//	@ViewById
//	Button trans_locate5_btn,trans_link5_btn,trans_call5_btn;

	@ViewById
	LinearLayout home_container,transportation_container,venue_container,contact_container;
	
	@AfterViews
	void loadView () {
		        
		adImageSwitcher = (ImageSwitcher) findViewById(R.id.adImageSwitcher);
		initImageSwithcer();
		
		if (menuGallery != null) {
			isDevice = true;
			
			ArrayList<String> list = new ArrayList<String>();
			list.add("Welcome");
			list.add("Contact Esri");
			list.add("Venue/Hotel");
			list.add("Local Attractions");
			
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
		
		// HOME
		blog_btn.setOnClickListener(linkListener);
		blog_btn.setTag("http://blogs.esri.com/esri/esri-insider/");
		
		in_btn.setOnClickListener(linkListener);
		in_btn.setTag("http://www.linkedin.com/company/esri");
		
		fb_btn.setOnClickListener(linkListener);
		fb_btn.setTag("https://www.facebook.com/esrigis");
		
		tweet_btn.setOnClickListener(linkListener);
		tweet_btn.setTag("https://twitter.com/EsriFederalGovt");
		
		// CONTACT
		contact_call_btn.setOnClickListener(callListener);
		contact_call_btn.setTag("9097932853");
		
		contact_email_btn.setOnClickListener(emailListener);
		contact_email_btn.setTag("feduc@esri.com");
		
		call_txvw.setOnClickListener(callListener);
		call_txvw.setTag("9097932853");
		
		email_txvw.setOnClickListener(emailListener);
		email_txvw.setTag("feduc@esri.com");
		
		contact_locate_btn.setTag("-8574190.190500,4707877.731200");
		contact_locate_btn.setOnClickListener(venueMapListener);
		
		// VENUE
		venue_call1_btn.setOnClickListener(callListener);
		venue_call1_btn.setTag("2022493000");
		
		venue_link1_btn.setOnClickListener(linkListener);
		venue_link1_btn.setTag("http://www.dcconvention.com");
		
		venue_call1_txvw.setOnClickListener(callListener);
		venue_call1_txvw.setTag("2022493000");
		
		venue_link1_txvw.setOnClickListener(linkListener);
		venue_link1_txvw.setTag("http://www.dcconvention.com");
		
		
		venue_call2_btn.setOnClickListener(callListener);
		venue_call2_btn.setTag("(2028981100");
		
		venue_link2_btn.setOnClickListener(linkListener);
		venue_link2_btn.setTag("http://www.marriott.com/residence-inn/travel.mi");

		venue_call2_txvw.setOnClickListener(callListener);
		venue_call2_txvw.setTag("2028981100");
		
		venue_link2_txvw.setOnClickListener(linkListener);
		venue_link2_txvw.setTag("http://www.marriott.com/residence-inn/travel.mi");

        /*
		venue_call3_btn.setOnClickListener(callListener);
		venue_call3_btn.setTag("2025821234");
		
		venue_link3_btn.setOnClickListener(linkListener);
		venue_link3_btn.setTag("http://grandwashington.hyatt.com");
		
		venue_call3_txvw.setOnClickListener(callListener);
		venue_call3_txvw.setTag("2025821234");
		
		venue_link3_txvw.setOnClickListener(linkListener);
		venue_link3_txvw.setTag("http://grandwashington.hyatt.com");
		  */
		
		venue_locate_address1_btn.setOnClickListener(areaMapListener);
		venue_locate_address1_btn.setTag("-8574157.51319161,4708183.10803689");
		
		venue_locate_address2_btn.setOnClickListener(areaMapListener);
		venue_locate_address2_btn.setTag("-8574968.33729335,4708472.06466857");

        /*
		venue_locate_address3_btn.setOnClickListener(areaMapListener);
		venue_locate_address3_btn.setTag("-8574537.4322191,4707273.05898016");
		  */
		
		// Tranportation
		trans_call1_btn.setOnClickListener(callListener);
		trans_call1_btn.setTag("2026338300");
		
		trans_call2_btn.setOnClickListener(callListener);
		trans_call2_btn.setTag("2026331000");
		
		trans_call3_btn.setOnClickListener(callListener);
		trans_call3_btn.setTag("2026332922");
		
//		trans_call4_btn.setOnClickListener(callListener);
//		trans_call4_btn.setTag("3013922400");
//
//		trans_call5_btn.setOnClickListener(callListener);
//		trans_call5_btn.setTag("2024792426");

		trans_link1_btn.setOnClickListener(linkListener);
		trans_link1_btn.setTag("http://www.npg.si.edu");
		
		trans_link2_btn.setOnClickListener(linkListener);
		trans_link2_btn.setTag("http://airandspace.si.edu/");
		
		trans_link3_btn.setOnClickListener(linkListener);
		trans_link3_btn.setTag("http://nationalzoo.si.edu/Contact/");
		
//		trans_link4_btn.setOnClickListener(linkListener);
//		trans_link4_btn.setTag("http://www.ccm.org");
//
//		trans_link5_btn.setOnClickListener(linkListener);
//		trans_link5_btn.setTag("http://www.tidalbasinpaddleboats.com");
		
		trans_locate1_btn.setOnClickListener(areaMapRedListener);
		trans_locate1_btn.setTag("-8574148.90267279,4707058.92430468");
		
		trans_locate2_btn.setOnClickListener(areaMapRedListener);
		trans_locate2_btn.setTag("-8573827.359297,4705591.670758");
		
		trans_locate3_btn.setOnClickListener(areaMapRedListener);
		trans_locate3_btn.setTag("-8577731.600718,4711621.436788");
		
//		trans_locate4_btn.setOnClickListener(areaMapRedListener);
//		//trans_locate4_btn.setTag("-8574512.797641,4705238.627470");
//        trans_locate4_btn.setTag("-8573030.8887,4690714.3494");
//
//		trans_locate5_btn.setOnClickListener(areaMapRedListener);
//		trans_locate5_btn.setTag("-8575387.991478,4705204.734242");
	}
	
	@Click
	void map_btn() {
		startActivity(new Intent(this,Map_.class));
	}
	
	void showView(int view) {
		switch (view) {
			case 0:
				home_container.setVisibility(View.VISIBLE);
				contact_container.setVisibility(View.GONE);
				venue_container.setVisibility(View.GONE);
				transportation_container.setVisibility(View.GONE);
			break;
			case 1:
				contact_container.setVisibility(View.VISIBLE);
				home_container.setVisibility(View.GONE);
				venue_container.setVisibility(View.GONE);
				transportation_container.setVisibility(View.GONE);
			break;
			case 2:
				venue_container.setVisibility(View.VISIBLE);
				contact_container.setVisibility(View.GONE);
				home_container.setVisibility(View.GONE);
				transportation_container.setVisibility(View.GONE);
			break;
			case 3:
				transportation_container.setVisibility(View.VISIBLE);
				contact_container.setVisibility(View.GONE);
				venue_container.setVisibility(View.GONE);
				home_container.setVisibility(View.GONE);
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
        	Logger.getLogger("Esri").info("ImageAdapter widht" + display.getWidth());
            TextView i = new TextView(mContext);
            i.setTextAppearance(getApplicationContext(), R.style.boldText);
            i.setText(mImageIds.get(position));
            if (display.getWidth() > 700) 
            	i.setLayoutParams(new Gallery.LayoutParams(300, 50));
            else
            	i.setLayoutParams(new Gallery.LayoutParams(210, 50));
            i.setPadding(6, 6, 6, 6);
            i.setGravity(Gravity.CENTER);
            return i;
        }
    }
	
	OnClickListener callListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			call(v.getTag().toString());
		}
	};
	
	OnClickListener linkListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			loadBrowser(v.getTag().toString());
		}
	};
	
	OnClickListener emailListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			email(v.getTag().toString());
		}
	};
	
	OnClickListener venueMapListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
            String xy = v.getTag().toString();
            if (xy != null && xy.length() > 0) {
                String arr[] = xy.split(",");
                if (arr != null && arr.length == 2) {
                    Intent intent = new Intent(Overview.this,Map_.class);
                    intent.putExtra("MAP_TYPE", Map.VENUE_MAP_TYPE);
                    Point point = new Point(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]));
                    intent.putExtra("MAP_LEVEL", Map.MAP_LEVEL2);
                    intent.putExtra("MAP_POINT", point);
                    intent.putExtra("MARKER_COLOR", Map.MAP_MARKER_BROWN);
                    startActivity(intent);
                }
            }
			//loadMap(Integer.parseInt(v.getTag().toString()));
		}
	};


    OnClickListener areaMapListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            String xy = v.getTag().toString();
            if (xy != null && xy.length() > 0) {
                String arr[] = xy.split(",");
                if (arr != null && arr.length == 2) {
                    Intent intent = new Intent(Overview.this,Map_.class);
                    intent.putExtra("MAP_TYPE", Map.AREA_MAP_TYPE);
                    Point point = new Point(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]));
                    intent.putExtra("MAP_POINT", point);
                    intent.putExtra("MARKER_COLOR", Map.MAP_MARKER_BLUE);
                    startActivity(intent);
                }
            }
            //loadMap(Integer.parseInt(v.getTag().toString()));
        }
    };


    OnClickListener areaMapRedListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            String xy = v.getTag().toString();
            if (xy != null && xy.length() > 0) {
                String arr[] = xy.split(",");
                if (arr != null && arr.length == 2) {
                    Intent intent = new Intent(Overview.this,Map_.class);
                    intent.putExtra("MAP_TYPE", Map.AREA_MAP_TYPE);
                    Point point = new Point(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]));
                    intent.putExtra("MAP_POINT", point);
                    intent.putExtra("MARKER_COLOR", Map.MAP_MARKER_RED);
                    startActivity(intent);
                }
            }
            //loadMap(Integer.parseInt(v.getTag().toString()));
        }
    };

}