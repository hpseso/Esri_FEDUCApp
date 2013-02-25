//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package com.esri.webops.feduc2013;

import java.io.Serializable;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.esri.webops.feduc2013.R.layout;

public final class ExhibitsDetail_
    extends ExhibitsDetail
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.exhibitor_detail);
    }

    private void init_(Bundle savedInstanceState) {
        injectExtras_();
    }

    private void afterSetContentView_() {
        map_btn = ((Button) findViewById(com.esri.webops.feduc2013.R.id.map_btn));
        address2_txvw = ((TextView) findViewById(com.esri.webops.feduc2013.R.id.address2_txvw));
        call_btn = ((Button) findViewById(com.esri.webops.feduc2013.R.id.call_btn));
        title_txvw = ((TextView) findViewById(com.esri.webops.feduc2013.R.id.title_txvw));
        address1_txvw = ((TextView) findViewById(com.esri.webops.feduc2013.R.id.address1_txvw));
        phone_txvw = ((TextView) findViewById(com.esri.webops.feduc2013.R.id.phone_txvw));
        booth_txvw = ((TextView) findViewById(com.esri.webops.feduc2013.R.id.booth_txvw));
        link_btn = ((Button) findViewById(com.esri.webops.feduc2013.R.id.link_btn));
        description_txvw = ((TextView) findViewById(com.esri.webops.feduc2013.R.id.description_txvw));
        logo_imgvw = ((ImageView) findViewById(com.esri.webops.feduc2013.R.id.logo_imgvw));
        email_txvw = ((TextView) findViewById(com.esri.webops.feduc2013.R.id.email_txvw));
        partner_txvw = ((TextView) findViewById(com.esri.webops.feduc2013.R.id.partner_txvw));
        email_btn = ((Button) findViewById(com.esri.webops.feduc2013.R.id.email_btn));
        locate_btn = ((Button) findViewById(com.esri.webops.feduc2013.R.id.locate_btn));
        back_btn = ((Button) findViewById(com.esri.webops.feduc2013.R.id.back_btn));
        {
            View view = findViewById(com.esri.webops.feduc2013.R.id.email_btn);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ExhibitsDetail_.this.email_btn();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(com.esri.webops.feduc2013.R.id.back_btn);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ExhibitsDetail_.this.back_btn();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(com.esri.webops.feduc2013.R.id.locate_btn);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ExhibitsDetail_.this.locate_btn();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(com.esri.webops.feduc2013.R.id.link_btn);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ExhibitsDetail_.this.link_btn();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(com.esri.webops.feduc2013.R.id.call_btn);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ExhibitsDetail_.this.call_btn();
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

    public static ExhibitsDetail_.IntentBuilder_ intent(Context context) {
        return new ExhibitsDetail_.IntentBuilder_(context);
    }

    @SuppressWarnings("unchecked")
    private<T >T cast_(Object object) {
        return ((T) object);
    }

    private void injectExtras_() {
        Intent intent_ = getIntent();
        Bundle extras_ = intent_.getExtras();
        if (extras_!= null) {
            if (extras_.containsKey("ID")) {
                try {
                    id = cast_(extras_.get("ID"));
                } catch (ClassCastException e) {
                    Log.e("ExhibitsDetail_", "Could not cast extra to expected type, the field is left to its default value", e);
                }
            }
        }
    }

    @Override
    public void setIntent(Intent newIntent) {
        super.setIntent(newIntent);
        injectExtras_();
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, ExhibitsDetail_.class);
        }

        public Intent get() {
            return intent_;
        }

        public ExhibitsDetail_.IntentBuilder_ flags(int flags) {
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

        public ExhibitsDetail_.IntentBuilder_ id(Integer id) {
            intent_.putExtra("ID", ((Serializable) id));
            return this;
        }

    }

}
