//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package com.esri.webops.feduc2013;

import java.io.Serializable;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import com.esri.webops.feduc2013.R.layout;
import com.googlecode.androidannotations.api.SdkVersionHelper;

public final class AgendaDetail_
    extends AgendaDetail
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.agenda_detail);
    }

    private void init_(Bundle savedInstanceState) {
        Bundle extras_ = this.getIntent().getExtras();
        if (extras_!= null) {
            if (extras_.containsKey("ID")) {
                try {
                    id = cast_(extras_.get("ID"));
                } catch (ClassCastException e) {
                    Log.e("AgendaDetail_", "Could not cast extra to expected type, the field is left to its default value", e);
                }
            }
        }
    }

    private void afterSetContentView_() {
        cal_btn = ((Button) findViewById(com.esri.webops.feduc2013.R.id.cal_btn));
        time_txvw = ((TextView) findViewById(com.esri.webops.feduc2013.R.id.time_txvw));
        hall_txvw = ((TextView) findViewById(com.esri.webops.feduc2013.R.id.hall_txvw));
        label_txvw = ((TextView) findViewById(com.esri.webops.feduc2013.R.id.label_txvw));
        author_txvw = ((TextView) findViewById(com.esri.webops.feduc2013.R.id.author_txvw));
        description_txvw = ((TextView) findViewById(com.esri.webops.feduc2013.R.id.description_txvw));
        map_btn = ((Button) findViewById(com.esri.webops.feduc2013.R.id.map_btn));
        org_name_txvw = ((TextView) findViewById(com.esri.webops.feduc2013.R.id.org_name_txvw));
        detail_container = ((ScrollView) findViewById(com.esri.webops.feduc2013.R.id.detail_container));
        title_txvw = ((TextView) findViewById(com.esri.webops.feduc2013.R.id.title_txvw));
        back_btn = ((Button) findViewById(com.esri.webops.feduc2013.R.id.back_btn));
        locate_btn = ((Button) findViewById(com.esri.webops.feduc2013.R.id.locate_btn));
        asset_title_txvw = ((TextView) findViewById(com.esri.webops.feduc2013.R.id.asset_title_txvw));
        {
            View view = findViewById(com.esri.webops.feduc2013.R.id.cal_btn);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    public void onClick(View view) {
                        cal_btn();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(com.esri.webops.feduc2013.R.id.locate_btn);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    public void onClick(View view) {
                        locate_btn();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(com.esri.webops.feduc2013.R.id.back_btn);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    public void onClick(View view) {
                        back_btn();
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

    public static AgendaDetail_.IntentBuilder_ intent(Context context) {
        return new AgendaDetail_.IntentBuilder_(context);
    }

    @SuppressWarnings("unchecked")
    private<T >T cast_(Object object) {
        return ((T) object);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, AgendaDetail_.class);
        }

        public Intent get() {
            return intent_;
        }

        public AgendaDetail_.IntentBuilder_ flags(int flags) {
            intent_.setFlags(flags);
            return this;
        }

        public void start() {
            context_.startActivity(intent_);
        }

        public AgendaDetail_.IntentBuilder_ id(Integer id) {
            intent_.putExtra("ID", ((Serializable) id));
            return this;
        }

    }

}
