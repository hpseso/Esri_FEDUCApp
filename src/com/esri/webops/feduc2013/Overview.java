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
	Button trans_locate1_btn,trans_link1_btn,trans_call1_btn,trans_call2_btn;
	
	@ViewById
	Button trans_locate2_btn,trans_link2_btn,trans_call3_btn;
	
	@ViewById
	Button trans_locate3_btn,trans_link3_btn,trans_call4_btn;
	
	@ViewById
	LinearLayout home_container,transportation_container,venue_container,contact_container;
	
	@AfterViews
	void loadView () {
		
//		Intent intent = new Intent(Intent.ACTION_EDIT);
//        intent.setType("vnd.android.cursor.item/event");
//        intent.putExtra("beginTime", Calendar.getInstance().getTimeInMillis());
//        intent.putExtra("allDay", false);
//        intent.putExtra("rrule", "FREQ=YEARLY");
//        intent.putExtra("endTime", Calendar.getInstance().getTimeInMillis()+60*1000);
//        intent.putExtra("title", "Hetal's programming event");
//        startActivity(intent);
        
		adImageSwitcher = (ImageSwitcher) findViewById(R.id.adImageSwitcher);
		initImageSwithcer();
		
		if (menuGallery != null) {
			isDevice = true;
			
			ArrayList<String> list = new ArrayList<String>();
			list.add("Welcome");
			list.add("Contact Esri");
			list.add("Venue/Hotel");
			list.add("Transportation");
			
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
		blog_btn.setTag("http://www.esri.com/pugiosappblog");
		
		in_btn.setOnClickListener(linkListener);
		in_btn.setTag("http://www.esri.com/pugiosappLinkedin");
		
		fb_btn.setOnClickListener(linkListener);
		fb_btn.setTag("http://www.esri.com/pugiosappfacebook");
		
		tweet_btn.setOnClickListener(linkListener);
		tweet_btn.setTag("http://www.esri.com/pugiosapptwitter");
		
		// CONTACT
		contact_call_btn.setOnClickListener(callListener);
		contact_call_btn.setTag("9097932853");
		
		contact_email_btn.setOnClickListener(emailListener);
		contact_email_btn.setTag("pug@esri.com");
		
		call_txvw.setOnClickListener(callListener);
		call_txvw.setTag("9097932853");
		
		email_txvw.setOnClickListener(emailListener);
		email_txvw.setTag("pug@esri.com");
		
		contact_locate_btn.setTag("1");
		contact_locate_btn.setOnClickListener(mapListener);
		
		// VENUE
		venue_call1_btn.setOnClickListener(callListener);
		venue_call1_btn.setTag("7138538000");
		
		venue_link1_btn.setOnClickListener(linkListener);
		venue_link1_btn.setTag("http://www.esri.com/pugiosappvenue");
		
		venue_call2_btn.setOnClickListener(callListener);
		venue_call2_btn.setTag("7137938000");
		
		venue_link2_btn.setOnClickListener(linkListener);
		venue_link2_btn.setTag("http://www.esri.com/pugiosapphotel");
		
		venue_call1_txvw.setOnClickListener(callListener);
		venue_call1_txvw.setTag("7138538000");
		
		venue_link1_txvw.setOnClickListener(linkListener);
		venue_link1_txvw.setTag("http://www.esri.com/pugiosappvenue");
		
		venue_call2_txvw.setOnClickListener(callListener);
		venue_call2_txvw.setTag("7137938000");
		
		venue_link2_txvw.setOnClickListener(linkListener);
		venue_link2_txvw.setTag("http://www.esri.com/pugiosapphotel");
		
		venue_locate_address1_btn.setOnClickListener(mapListener);
		venue_locate_address1_btn.setTag("2");
		
		venue_locate_address2_btn.setOnClickListener(mapListener);
		venue_locate_address2_btn.setTag("3");
		
		venue_locate_address1_btn.setOnClickListener(mapListener);
		venue_locate_address1_btn.setTag(2);
        venue_locate_address2_btn.setOnClickListener(mapListener);
        venue_locate_address2_btn.setTag(3);
		
		// Tranportation
		trans_call1_btn.setOnClickListener(callListener);
		trans_call1_btn.setTag("8006542240");
		
		trans_call2_btn.setOnClickListener(callListener);
		trans_call2_btn.setTag("4057494434");
		
		trans_call3_btn.setOnClickListener(callListener);
		trans_call3_btn.setTag("8003311600");
		
		trans_call4_btn.setOnClickListener(callListener);
		trans_call4_btn.setTag("18007368222");
		
		trans_link1_btn.setOnClickListener(linkListener);
		trans_link1_btn.setTag("http://www.esri.com/pugiosapphertz");
		
		trans_link2_btn.setOnClickListener(linkListener);
		trans_link2_btn.setTag("http://www.esri.com/pugiosappAvis");
		
		trans_link3_btn.setOnClickListener(linkListener);
		trans_link3_btn.setTag("http://www.esri.com/pugiosappenterprise");
		
		trans_locate1_btn.setOnClickListener(mapListener);
		trans_locate1_btn.setTag("4");
		
		trans_locate2_btn.setOnClickListener(mapListener);
		trans_locate2_btn.setTag("5");
		
		trans_locate3_btn.setOnClickListener(mapListener);
		trans_locate3_btn.setTag("6");
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
            	i.setLayoutParams(new Gallery.LayoutParams(200, 50));
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
	
	OnClickListener mapListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			loadMap(Integer.parseInt(v.getTag().toString()));
		}
	};
	
}