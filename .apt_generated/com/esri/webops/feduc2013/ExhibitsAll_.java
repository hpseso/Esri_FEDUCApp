//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package com.esri.webops.feduc2013;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import com.esri.webops.feduc2013.R.id;
import com.esri.webops.feduc2013.R.layout;
import com.googlecode.androidannotations.api.BackgroundExecutor;

public final class ExhibitsAll_
    extends ExhibitsAll
{

    private Handler handler_ = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.exhibits);
    }

    private void init_(Bundle savedInstanceState) {
    }

    private void afterSetContentView_() {
        empty_txvw = ((TextView) findViewById(id.empty_txvw));
        exhibitList = ((ListView) findViewById(id.exhibitList));
        sponsor_txvw = ((TextView) findViewById(id.sponsor_txvw));
        all_txvw = ((TextView) findViewById(id.all_txvw));
        hours_txvw = ((TextView) findViewById(id.hours_txvw));
        {
            View view = findViewById(id.hours_txvw);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ExhibitsAll_.this.hours_txvw();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.map_btn);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ExhibitsAll_.this.map_btn();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.sponsor_txvw);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ExhibitsAll_.this.sponsor_txvw();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.refresh_btn);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ExhibitsAll_.this.refresh_btn();
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

    public static ExhibitsAll_.IntentBuilder_ intent(Context context) {
        return new ExhibitsAll_.IntentBuilder_(context);
    }

    @Override
    public void updateUI() {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                try {
                    ExhibitsAll_.super.updateUI();
                } catch (RuntimeException e) {
                    Log.e("ExhibitsAll_", "A runtime exception was thrown while executing code in a runnable", e);
                }
            }

        }
        );
    }

    @Override
    public void loadDataFromWeb() {
        BackgroundExecutor.execute(new Runnable() {


            @Override
            public void run() {
                try {
                    ExhibitsAll_.super.loadDataFromWeb();
                } catch (RuntimeException e) {
                    Log.e("ExhibitsAll_", "A runtime exception was thrown while executing code in a runnable", e);
                }
            }

        }
        );
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, ExhibitsAll_.class);
        }

        public Intent get() {
            return intent_;
        }

        public ExhibitsAll_.IntentBuilder_ flags(int flags) {
            intent_.setFlags(flags);
            return this;
        }

        public void start() {
            context_.startActivity(intent_);
        }

        public void startForResult(int requestCode) {
            if (context_ instanceof Activity) {
                ((Activity) context_).startActivityForResult(intent_, requestCode);
            } else {
                context_.startActivity(intent_);
            }
        }

    }

}
