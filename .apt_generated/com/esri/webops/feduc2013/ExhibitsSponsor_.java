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
import android.widget.ListView;
import android.widget.TextView;
import com.esri.webops.feduc2013.R.id;
import com.esri.webops.feduc2013.R.layout;
import com.googlecode.androidannotations.api.SdkVersionHelper;

public final class ExhibitsSponsor_
    extends ExhibitsSponsor
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.exhibits_sponsor);
    }

    private void init_(Bundle savedInstanceState) {
    }

    private void afterSetContentView_() {
        sponsor_txvw = ((TextView) findViewById(id.sponsor_txvw));
        hours_txvw = ((TextView) findViewById(id.hours_txvw));
        sponsorList = ((ListView) findViewById(id.sponsorList));
        all_txvw = ((TextView) findViewById(id.all_txvw));
        empty_txvw = ((TextView) findViewById(id.empty_txvw));
        {
            View view = findViewById(id.hours_txvw);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    public void onClick(View view) {
                        hours_txvw();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.all_txvw);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    public void onClick(View view) {
                        all_txvw();
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

    public static ExhibitsSponsor_.IntentBuilder_ intent(Context context) {
        return new ExhibitsSponsor_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, ExhibitsSponsor_.class);
        }

        public Intent get() {
            return intent_;
        }

        public ExhibitsSponsor_.IntentBuilder_ flags(int flags) {
            intent_.setFlags(flags);
            return this;
        }

        public void start() {
            context_.startActivity(intent_);
        }

    }

}
