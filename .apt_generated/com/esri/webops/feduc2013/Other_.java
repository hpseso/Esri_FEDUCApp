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
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.esri.webops.feduc2013.R.id;
import com.esri.webops.feduc2013.R.layout;
import com.googlecode.androidannotations.api.SdkVersionHelper;

public final class Other_
    extends Other
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.other);
    }

    private void init_(Bundle savedInstanceState) {
    }

    private void afterSetContentView_() {
        help3_txvw = ((TextView) findViewById(id.help3_txvw));
        no22_btn = ((Button) findViewById(id.no22_btn));
        next_btn = ((Button) findViewById(id.next_btn));
        no12_btn = ((Button) findViewById(id.no12_btn));
        help22_txvw = ((TextView) findViewById(id.help22_txvw));
        plink5_txvw = ((TextView) findViewById(id.plink5_txvw));
        no11_btn = ((Button) findViewById(id.no11_btn));
        demo_container = ((FrameLayout) findViewById(id.demo_container));
        no21_btn = ((Button) findViewById(id.no21_btn));
        help23_txvw = ((TextView) findViewById(id.help23_txvw));
        no3_btn = ((Button) findViewById(id.no3_btn));
        no1_btn = ((Button) findViewById(id.no1_btn));
        plink4_txvw = ((TextView) findViewById(id.plink4_txvw));
        plink3_txvw = ((TextView) findViewById(id.plink3_txvw));
        help2_txvw = ((TextView) findViewById(id.help2_txvw));
        help21_txvw = ((TextView) findViewById(id.help21_txvw));
        no4_btn = ((Button) findViewById(id.no4_btn));
        plink2_txvw = ((TextView) findViewById(id.plink2_txvw));
        viewFlipper1 = ((ViewFlipper) findViewById(id.viewFlipper1));
        plink3_btn = ((Button) findViewById(id.plink3_btn));
        prev_btn = ((Button) findViewById(id.prev_btn));
        sdate_cal2_btn = ((Button) findViewById(id.sdate_cal2_btn));
        plink1_btn = ((Button) findViewById(id.plink1_btn));
        sdate_cal1_btn = ((Button) findViewById(id.sdate_cal1_btn));
        menuGallery = ((Gallery) findViewById(id.menu_gallery));
        privacy_container = ((LinearLayout) findViewById(id.privacy_container));
        help4_txvw = ((TextView) findViewById(id.help4_txvw));
        plink5_btn = ((Button) findViewById(id.plink5_btn));
        no23_btn = ((Button) findViewById(id.no23_btn));
        plink2_btn = ((Button) findViewById(id.plink2_btn));
        survey_btn = ((Button) findViewById(id.survey_btn));
        help11_txvw = ((TextView) findViewById(id.help11_txvw));
        plink1_txvw = ((TextView) findViewById(id.plink1_txvw));
        help1_txvw = ((TextView) findViewById(id.help1_txvw));
        sdate_container = ((LinearLayout) findViewById(id.sdate_container));
        no2_btn = ((Button) findViewById(id.no2_btn));
        help12_txvw = ((TextView) findViewById(id.help12_txvw));
        plink4_btn = ((Button) findViewById(id.plink4_btn));
        survey_container = ((LinearLayout) findViewById(id.survey_container));
        {
            View view = findViewById(id.no21_btn);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    public void onClick(View view) {
                        no21_btn();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.no3_btn);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    public void onClick(View view) {
                        no3_btn();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.no4_btn);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    public void onClick(View view) {
                        no4_btn();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.help3_txvw);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    public void onClick(View view) {
                        help3_txvw();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.help1_txvw);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    public void onClick(View view) {
                        help1_txvw();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.help4_txvw);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    public void onClick(View view) {
                        help4_txvw();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.help2_txvw);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    public void onClick(View view) {
                        help2_txvw();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.help22_txvw);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    public void onClick(View view) {
                        help22_txvw();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.no2_btn);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    public void onClick(View view) {
                        no2_btn();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.no23_btn);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    public void onClick(View view) {
                        no23_btn();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.no1_btn);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    public void onClick(View view) {
                        no1_btn();
                    }

                }
                );
            }
        }
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
        {
            View view = findViewById(id.help21_txvw);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    public void onClick(View view) {
                        help21_txvw();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.help23_txvw);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    public void onClick(View view) {
                        help23_txvw();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.help12_txvw);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    public void onClick(View view) {
                        help12_txvw();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.no12_btn);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    public void onClick(View view) {
                        no12_btn();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.no11_btn);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    public void onClick(View view) {
                        no11_btn();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.prev_btn);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    public void onClick(View view) {
                        prev_btn();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.no22_btn);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    public void onClick(View view) {
                        no22_btn();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.help11_txvw);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    public void onClick(View view) {
                        help11_txvw();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.next_btn);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    public void onClick(View view) {
                        next_btn();
                    }

                }
                );
            }
        }
        load();
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

    public static Other_.IntentBuilder_ intent(Context context) {
        return new Other_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, Other_.class);
        }

        public Intent get() {
            return intent_;
        }

        public Other_.IntentBuilder_ flags(int flags) {
            intent_.setFlags(flags);
            return this;
        }

        public void start() {
            context_.startActivity(intent_);
        }

    }

}
