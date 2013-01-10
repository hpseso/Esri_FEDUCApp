package com.esri.webops.feduc2013.adapter;


import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.esri.webops.feduc2013.BaseActivity;
import com.esri.webops.feduc2013.Other;
import com.esri.webops.feduc2013.R;

public class OtherAdapter extends PagerAdapter {
	
	Other activity;
	
	public OtherAdapter (Other activity) {
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
        		resId = R.layout.other_demo;
        		view = inflater.inflate(resId, null,false);
        		final ViewFlipper flipper = (ViewFlipper) view.findViewById(R.id.viewFlipper1);
        		final Button nextBtn = (Button) view.findViewById(R.id.next_btn);
        		final Button prevBtn = (Button) view.findViewById(R.id.prev_btn);
        		
        		nextBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						flipper.showNext();
						if (Integer.parseInt(flipper.getCurrentView().getTag().toString()) > 1)
							prevBtn.setVisibility(View.VISIBLE);
						else
							prevBtn.setVisibility(View.GONE);
						
						if (Integer.parseInt(flipper.getCurrentView().getTag().toString()) < 4)
							nextBtn.setVisibility(View.VISIBLE);
						else
							nextBtn.setVisibility(View.GONE);
					}
				});
        		
        		prevBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						flipper.showPrevious();
						if (Integer.parseInt(flipper.getCurrentView().getTag().toString()) > 1)
							prevBtn.setVisibility(View.VISIBLE);
						else
							prevBtn.setVisibility(View.GONE);
						
						if (Integer.parseInt(flipper.getCurrentView().getTag().toString()) < 4)
							nextBtn.setVisibility(View.VISIBLE);
						else
							nextBtn.setVisibility(View.GONE);
					}
				});
        		prevBtn.setVisibility(View.GONE);
        		
        		final Button no1Btn = (Button) view.findViewById(R.id.no1_btn);
        		final Button no2Btn = (Button) view.findViewById(R.id.no2_btn);
        		final Button no3Btn = (Button) view.findViewById(R.id.no3_btn);
        		
        		final TextView help1Txvw = (TextView) view.findViewById(R.id.help1_txvw);
        		final TextView help2Txvw = (TextView) view.findViewById(R.id.help2_txvw);
        		final TextView help3Txvw = (TextView) view.findViewById(R.id.help3_txvw);
        		
        		no1Btn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						
						help1Txvw.clearAnimation();
						help1Txvw.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_in));
						help1Txvw.setVisibility(View.VISIBLE);
						help2Txvw.setVisibility(View.GONE);
						help3Txvw.setVisibility(View.GONE);
					}
				});
        		
        		no2Btn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						help2Txvw.clearAnimation();
						help2Txvw.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_in));
						help2Txvw.setVisibility(View.VISIBLE);
						help1Txvw.setVisibility(View.GONE);
						help3Txvw.setVisibility(View.GONE);
					}
				});
        		
        		no3Btn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						help3Txvw.clearAnimation();
						help3Txvw.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_in));
						help3Txvw.setVisibility(View.VISIBLE);
						help1Txvw.setVisibility(View.GONE);
						help2Txvw.setVisibility(View.GONE);
					}
				});
        		
        		help1Txvw.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (help1Txvw.getVisibility() == View.VISIBLE)
							help1Txvw.setVisibility(View.GONE);
					}
				});
        		
        		help2Txvw.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (help2Txvw.getVisibility() == View.VISIBLE)
							help2Txvw.setVisibility(View.GONE);
					}
				});
        		help3Txvw.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (help3Txvw.getVisibility() == View.VISIBLE)
							help3Txvw.setVisibility(View.GONE);
					}
				});
        		
        		final Button no11Btn = (Button) view.findViewById(R.id.no11_btn);
        		final Button no12Btn = (Button) view.findViewById(R.id.no12_btn);
        		
        		final TextView help11Txvw = (TextView) view.findViewById(R.id.help11_txvw);
        		final TextView help12Txvw = (TextView) view.findViewById(R.id.help12_txvw);
        		
        		no11Btn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						help11Txvw.clearAnimation();
						help11Txvw.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_in));
						help11Txvw.setVisibility(View.VISIBLE);
						help12Txvw.setVisibility(View.GONE);
					}
				});
        		
        		no12Btn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						help12Txvw.clearAnimation();
						help12Txvw.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_in));
						help12Txvw.setVisibility(View.VISIBLE);
						help11Txvw.setVisibility(View.GONE);
					}
				});
        		
        		help11Txvw.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (help11Txvw.getVisibility() == View.VISIBLE)
							help11Txvw.setVisibility(View.GONE);
					}
				});
        		
        		help12Txvw.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (help12Txvw.getVisibility() == View.VISIBLE)
							help12Txvw.setVisibility(View.GONE);
					}
				});
        		
        		final Button no31Btn = (Button) view.findViewById(R.id.help21_btn);
        		final Button no32Btn = (Button) view.findViewById(R.id.help22_btn);
        		
        		final TextView help21Txvw = (TextView) view.findViewById(R.id.help21_txvw);
        		final TextView help22Txvw = (TextView) view.findViewById(R.id.help22_txvw);
        		
        		no31Btn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						help21Txvw.clearAnimation();
						help21Txvw.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_in));
						help21Txvw.setVisibility(View.VISIBLE);
						help22Txvw.setVisibility(View.GONE);
					}
				});
        		
        		no32Btn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						help22Txvw.clearAnimation();
						help22Txvw.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_in));
						help22Txvw.setVisibility(View.VISIBLE);
						help21Txvw.setVisibility(View.GONE);
					}
				});
        		
        		
        		help21Txvw.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (help21Txvw.getVisibility() == View.VISIBLE)
							help21Txvw.setVisibility(View.GONE);
					}
				});
        		
        		help21Txvw.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (help21Txvw.getVisibility() == View.VISIBLE)
							help21Txvw.setVisibility(View.GONE);
					}
				});
        		
        		break;
	        case 1:
	            resId = R.layout.other_survey;
	            view = inflater.inflate(resId, null,false);
	            ((Button) view.findViewById(R.id.survey_btn)).setTag("http://www.esri.com/pugiosappsurvey");
        		((Button) view.findViewById(R.id.survey_btn)).setOnClickListener(linkListener);
	            break;
	        case 2:
	            resId = R.layout.other_save_date;
	            view = inflater.inflate(resId, null,false);
	            ((Button) view.findViewById(R.id.sdate_cal1_btn)).setTag("1");
        		((Button) view.findViewById(R.id.sdate_cal1_btn)).setOnClickListener(calListener);
        		
        		((Button) view.findViewById(R.id.sdate_cal2_btn)).setTag("2");
        		((Button) view.findViewById(R.id.sdate_cal2_btn)).setOnClickListener(calListener);
	            break;
	        case 3:
	            resId = R.layout.other_privacy;
	            view = inflater.inflate(resId, null,false);
	            Button plink1_btn = (Button) view.findViewById(R.id.plink1_btn);
	            plink1_btn.setOnClickListener(linkListener);
				plink1_btn.setTag("http://www.esri.com/legal/index.html");
				
				Button plink2_btn = (Button) view.findViewById(R.id.plink2_btn);
				plink2_btn.setOnClickListener(linkListener);
				plink2_btn.setTag("http://resources.arcgis.com/content/arcgis-ios");
				
				Button plink3_btn = (Button) view.findViewById(R.id.plink3_btn);
				plink3_btn.setOnClickListener(linkListener);
				plink3_btn.setTag("http://resources.arcgis.com/content/community-maps/world-imagery-map");
				
				Button plink4_btn = (Button) view.findViewById(R.id.plink4_btn);
				plink4_btn.setOnClickListener(linkListener);
				plink4_btn.setTag("http://www.openstreetmap.org/");
				
				Button plink5_btn = (Button) view.findViewById(R.id.plink5_btn);
				plink5_btn.setOnClickListener(linkListener);
				plink5_btn.setTag("http://resources.arcgis.com/content/community-maps/world-topographic-map");
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
    
    OnClickListener linkListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			((BaseActivity)activity).loadBrowser(v.getTag().toString());
		}
	};
	
	OnClickListener calListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			((BaseActivity)activity).loadCalendar(Integer.parseInt(v.getTag().toString()));
		}
	};
}
