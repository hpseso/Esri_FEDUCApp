//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package com.esri.webops.feduc2013;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.esri.webops.feduc2013.R.id;
import com.esri.webops.feduc2013.R.layout;
import com.googlecode.androidannotations.api.SdkVersionHelper;

public final class Overview_
    extends Overview
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.overview);
    }

    private void init_(Bundle savedInstanceState) {
    }

    private void afterSetContentView_() {
        venue_call2_btn = ((Button) findViewById(id.venue_call2_btn));
        in_btn = ((Button) findViewById(id.in_btn));
        contact_call_btn = ((Button) findViewById(id.contact_call_btn));
        venue_call1_btn = ((Button) findViewById(id.venue_call1_btn));
        contact_email_btn = ((Button) findViewById(id.contact_email_btn));
        trans_link1_btn = ((Button) findViewById(id.trans_link1_btn));
        venue_locate_address1_btn = ((Button) findViewById(id.venue_locate_address1_btn));
        trans_call3_txvw = ((TextView) findViewById(id.trans_call3_txvw));
        trans_link3_btn = ((Button) findViewById(id.trans_link3_btn));
        fb_btn = ((Button) findViewById(id.fb_btn));
        trans_link2_btn = ((Button) findViewById(id.trans_link2_btn));
        trans_call2_btn = ((Button) findViewById(id.trans_call2_btn));
        venue_call2_txvw = ((TextView) findViewById(id.venue_call2_txvw));
        venue_call1_txvw = ((TextView) findViewById(id.venue_call1_txvw));
        trans_locate2_btn = ((Button) findViewById(id.trans_locate2_btn));
        venue_link2_btn = ((Button) findViewById(id.venue_link2_btn));
        trans_call5_txvw = ((TextView) findViewById(id.trans_call5_txvw));
        trans_locate5_btn = ((Button) findViewById(id.trans_locate5_btn));
        trans_locate4_btn = ((Button) findViewById(id.trans_locate4_btn));
        trans_call1_btn = ((Button) findViewById(id.trans_call1_btn));
        trans_call4_btn = ((Button) findViewById(id.trans_call4_btn));
        email_txvw = ((TextView) findViewById(id.email_txvw));
        venue_container = ((LinearLayout) findViewById(id.venue_container));
        venue_link1_btn = ((Button) findViewById(id.venue_link1_btn));
        trans_link5_btn = ((Button) findViewById(id.trans_link5_btn));
        blog_btn = ((Button) findViewById(id.blog_btn));
        venue_link2_txvw = ((TextView) findViewById(id.venue_link2_txvw));
        venue_locate_address2_btn = ((Button) findViewById(id.venue_locate_address2_btn));
        contact_locate_btn = ((Button) findViewById(id.contact_locate_btn));
        venue_link3_btn = ((Button) findViewById(id.venue_link3_btn));
        tweet_btn = ((Button) findViewById(id.tweet_btn));
        trans_locate3_btn = ((Button) findViewById(id.trans_locate3_btn));
        trans_locate1_btn = ((Button) findViewById(id.trans_locate1_btn));
        trans_call5_btn = ((Button) findViewById(id.trans_call5_btn));
        venue_call3_txvw = ((TextView) findViewById(id.venue_call3_txvw));
        transportation_container = ((LinearLayout) findViewById(id.transportation_container));
        call_txvw = ((TextView) findViewById(id.call_txvw));
        trans_link4_btn = ((Button) findViewById(id.trans_link4_btn));
        venue_call3_btn = ((Button) findViewById(id.venue_call3_btn));
        home_container = ((LinearLayout) findViewById(id.home_container));
        venue_locate_address3_btn = ((Button) findViewById(id.venue_locate_address3_btn));
        trans_call4_txvw = ((TextView) findViewById(id.trans_call4_txvw));
        menuGallery = ((Gallery) findViewById(id.menu_gallery));
        trans_call3_btn = ((Button) findViewById(id.trans_call3_btn));
        contact_container = ((LinearLayout) findViewById(id.contact_container));
        trans_call1_txvw = ((TextView) findViewById(id.trans_call1_txvw));
        venue_link3_txvw = ((TextView) findViewById(id.venue_link3_txvw));
        trans_call2_txvw = ((TextView) findViewById(id.trans_call2_txvw));
        venue_link1_txvw = ((TextView) findViewById(id.venue_link1_txvw));
        {
            View view = findViewById(id.map_btn);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    public void onClick(View view) {
                        map_btn();
                    }

                }
                );
            }
        }
        loadView();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        afterSetContentView_();
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        afterSetContentView_();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        afterSetContentView_();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (((SdkVersionHelper.getSdkInt()< 5)&&(keyCode == KeyEvent.KEYCODE_BACK))&&(event.getRepeatCount() == 0)) {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    public static Overview_.IntentBuilder_ intent(Context context) {
        return new Overview_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, Overview_.class);
        }

        public Intent get() {
            return intent_;
        }

        public Overview_.IntentBuilder_ flags(int flags) {
            intent_.setFlags(flags);
            return this;
        }

        public void start() {
            context_.startActivity(intent_);
        }

    }

}
