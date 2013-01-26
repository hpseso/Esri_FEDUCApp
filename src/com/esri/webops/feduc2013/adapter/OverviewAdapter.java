package com.esri.webops.feduc2013.adapter;

import java.util.logging.Logger;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.esri.webops.feduc2013.BaseActivity;
import com.esri.webops.feduc2013.Overview;
import com.esri.webops.feduc2013.R;

public class OverviewAdapter extends PagerAdapter {

	Overview activity;
	
	public OverviewAdapter (Overview activity) {
		this.activity = activity;
	}
	
	public int getCount() {
        return 4;
    }
	
	public Object instantiateItem(final View collection, int position) {
        LayoutInflater inflater = (LayoutInflater) collection.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        int resId = 0;
        switch (position) {
        	case 0:
        		resId = R.layout.overview_top;
        		
        		view = inflater.inflate(resId, null,false);
        		((Button) view.findViewById(R.id.blog_btn)).setTag("http://www.esri.com/pugiosappblog");
        		((Button) view.findViewById(R.id.blog_btn)).setOnClickListener(linkListener);
        		((Button) view.findViewById(R.id.in_btn)).setTag("http://www.esri.com/pugiosappblog");
        		((Button) view.findViewById(R.id.in_btn)).setOnClickListener(linkListener);
        		((Button) view.findViewById(R.id.fb_btn)).setTag("http://www.esri.com/pugiosappblog");
        		((Button) view.findViewById(R.id.fb_btn)).setOnClickListener(linkListener);
        		((Button) view.findViewById(R.id.tweet_btn)).setTag("http://www.esri.com/pugiosappblog");
        		((Button) view.findViewById(R.id.tweet_btn)).setOnClickListener(linkListener);
        		
            break;
	        case 1:
	            resId = R.layout.overview_contact;
	            view = inflater.inflate(resId, null,false);
	            ((Button) view.findViewById(R.id.contact_call_btn)).setOnClickListener(callListener);
	            ((Button) view.findViewById(R.id.contact_call_btn)).setTag("9097932853");
	    		
	            ((Button) view.findViewById(R.id.contact_email_btn)).setOnClickListener(emailListener);
	            ((Button) view.findViewById(R.id.contact_email_btn)).setTag("pug@esri.com");
	            
	            ((Button) view.findViewById(R.id.contact_locate_btn)).setOnClickListener(mapListener);
	            ((Button) view.findViewById(R.id.contact_locate_btn)).setTag(1);
	            break;
	        case 2:
	            resId = R.layout.overview_venue;
	            view = inflater.inflate(resId, null,false);
	            ((Button) view.findViewById(R.id.venue_call1_btn)).setOnClickListener(callListener);
	            ((Button) view.findViewById(R.id.venue_call2_btn)).setOnClickListener(callListener);
	            ((Button) view.findViewById(R.id.venue_link1_btn)).setOnClickListener(linkListener);
	            ((Button) view.findViewById(R.id.venue_link2_btn)).setOnClickListener(linkListener);
	            
	            ((Button) view.findViewById(R.id.venue_locate_address1_btn)).setOnClickListener(mapListener);
	            ((Button) view.findViewById(R.id.venue_locate_address1_btn)).setTag(2);
	            ((Button) view.findViewById(R.id.venue_locate_address2_btn)).setOnClickListener(mapListener);
	            ((Button) view.findViewById(R.id.venue_locate_address2_btn)).setTag(3);
	            break;
	        case 3:
	            resId = R.layout.overview_tranportation;
	            view = inflater.inflate(resId, null,false);
	            ((Button) view.findViewById(R.id.trans_call1_btn)).setOnClickListener(callListener);
	            ((Button) view.findViewById(R.id.trans_call1_btn)).setTag("8006542240");
	    		
	            ((Button) view.findViewById(R.id.trans_call2_btn)).setOnClickListener(callListener);
	            ((Button) view.findViewById(R.id.trans_call2_btn)).setTag("4057494434");
	    		
	            ((Button) view.findViewById(R.id.trans_call3_btn)).setOnClickListener(callListener);
	            ((Button) view.findViewById(R.id.trans_call3_btn)).setTag("8003311600");
	    		
//	            ((Button) view.findViewById(R.id.trans_call4_btn)).setOnClickListener(callListener);
//	            ((Button) view.findViewById(R.id.trans_call4_btn)).setTag("18007368222");
	    		
	            ((Button) view.findViewById(R.id.trans_link1_btn)).setOnClickListener(linkListener);
	            ((Button) view.findViewById(R.id.trans_link1_btn)).setTag("http://www.esri.com/pugiosapphertz");
	    		
	            ((Button) view.findViewById(R.id.trans_link2_btn)).setOnClickListener(linkListener);
	    		((Button) view.findViewById(R.id.trans_link2_btn)).setTag("http://www.esri.com/pugiosappAvis");
	    		
	    		((Button) view.findViewById(R.id.trans_link3_btn)).setOnClickListener(linkListener);
	    		((Button) view.findViewById(R.id.trans_link3_btn)).setTag("http://www.esri.com/pugiosappenterprise");
	    		
	    		((Button) view.findViewById(R.id.trans_locate1_btn)).setOnClickListener(mapListener);
	            ((Button) view.findViewById(R.id.trans_locate1_btn)).setTag(4);
	            ((Button) view.findViewById(R.id.trans_locate2_btn)).setOnClickListener(mapListener);
	            ((Button) view.findViewById(R.id.trans_locate2_btn)).setTag(5);
	            ((Button) view.findViewById(R.id.trans_locate3_btn)).setOnClickListener(mapListener);
	            ((Button) view.findViewById(R.id.trans_locate3_btn)).setTag(6);
	    		
	            break;
        }
        ((ViewPager) collection).addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
    
    protected void loadMap(int which) {
		switch (which) {
		case 2:
			
			break;

		default:
			break;
		}
	}
	
	OnClickListener callListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			((BaseActivity)activity).call(v.getTag().toString());
		}
	};
	
	OnClickListener linkListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Logger.getLogger("ESRI").info("Load browser:" + v.getTag().toString());
			((BaseActivity)activity).loadBrowser(v.getTag().toString());
		}
	};
	
	OnClickListener mapListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			((BaseActivity)activity).loadMap(Integer.parseInt(v.getTag().toString()));
		}
	};
	
	OnClickListener emailListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			((BaseActivity)activity).email(v.getTag().toString());
		}
	};
}
