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
import android.widget.LinearLayout;
import android.widget.TextView;
import com.esri.android.map.MapView;
import com.esri.core.geometry.Point;
import com.esri.webops.feduc2013.R.id;
import com.esri.webops.feduc2013.R.layout;

public final class VenueMap_
    extends VenueMap
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.map);
    }

    private void init_(Bundle savedInstanceState) {
        injectExtras_();
    }

    private void afterSetContentView_() {
        m_btn = ((Button) findViewById(id.m_btn));
        level_three_txvw = ((TextView) findViewById(id.level_three_txvw));
        mapView = ((MapView) findViewById(id.map));
        current_map_txvw = ((TextView) findViewById(id.current_map_txvw));
        level_two_txvw = ((TextView) findViewById(id.level_two_txvw));
        level_one_txvw = ((TextView) findViewById(id.level_one_txvw));
        map_menu_container = ((LinearLayout) findViewById(id.map_menu_container));
        floor_menu_container = ((LinearLayout) findViewById(id.floor_menu_container));
        info_view = ((LinearLayout) findViewById(id.info_view));
        {
            View view = findViewById(id.m_btn);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        VenueMap_.this.m_btn();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.link1_txvw);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        VenueMap_.this.link1_txvw();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.info_close_btn);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        VenueMap_.this.info_close_btn();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.link7_txvw);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        VenueMap_.this.link7_txvw();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.info_btn);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        VenueMap_.this.info_btn();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.link3_txvw);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        VenueMap_.this.link3_txvw();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.level_three_txvw);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        VenueMap_.this.level_three_txvw();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.level_one_txvw);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        VenueMap_.this.level_one_txvw();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.link8_txvw);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        VenueMap_.this.link8_txvw();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.link2_txvw);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        VenueMap_.this.link2_txvw();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.close_btn);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        VenueMap_.this.close_btn();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.link6_txvw);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        VenueMap_.this.link6_txvw();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.link5_txvw);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        VenueMap_.this.link5_txvw();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.link4_txvw);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        VenueMap_.this.link4_txvw();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.level_two_txvw);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        VenueMap_.this.level_two_txvw();
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

    public static VenueMap_.IntentBuilder_ intent(Context context) {
        return new VenueMap_.IntentBuilder_(context);
    }

    @SuppressWarnings("unchecked")
    private<T >T cast_(Object object) {
        return ((T) object);
    }

    private void injectExtras_() {
        Intent intent_ = getIntent();
        Bundle extras_ = intent_.getExtras();
        if (extras_!= null) {
            if (extras_.containsKey("MARKER_COLOR")) {
                try {
                    markerColor = cast_(extras_.get("MARKER_COLOR"));
                } catch (ClassCastException e) {
                    Log.e("VenueMap_", "Could not cast extra to expected type, the field is left to its default value", e);
                }
            }
            if (extras_.containsKey("FLOOR")) {
                try {
                    floor = cast_(extras_.get("FLOOR"));
                } catch (ClassCastException e) {
                    Log.e("VenueMap_", "Could not cast extra to expected type, the field is left to its default value", e);
                }
            }
            if (extras_.containsKey("EXHIBIT_POINT")) {
                try {
                    exhibitPoint = cast_(extras_.get("EXHIBIT_POINT"));
                } catch (ClassCastException e) {
                    Log.e("VenueMap_", "Could not cast extra to expected type, the field is left to its default value", e);
                }
            }
            if (extras_.containsKey("MAP_LEVEL")) {
                try {
                    mapLevel = cast_(extras_.get("MAP_LEVEL"));
                } catch (ClassCastException e) {
                    Log.e("VenueMap_", "Could not cast extra to expected type, the field is left to its default value", e);
                }
            }
            if (extras_.containsKey("AGENDA_POINT")) {
                try {
                    agendaPoint = cast_(extras_.get("AGENDA_POINT"));
                } catch (ClassCastException e) {
                    Log.e("VenueMap_", "Could not cast extra to expected type, the field is left to its default value", e);
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
            intent_ = new Intent(context, VenueMap_.class);
        }

        public Intent get() {
            return intent_;
        }

        public VenueMap_.IntentBuilder_ flags(int flags) {
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

        public VenueMap_.IntentBuilder_ markerColor(Integer markerColor) {
            intent_.putExtra("MARKER_COLOR", ((Serializable) markerColor));
            return this;
        }

        public VenueMap_.IntentBuilder_ floor(Integer floor) {
            intent_.putExtra("FLOOR", ((Serializable) floor));
            return this;
        }

        public VenueMap_.IntentBuilder_ exhibitPoint(Point exhibitPoint) {
            intent_.putExtra("EXHIBIT_POINT", ((Serializable) exhibitPoint));
            return this;
        }

        public VenueMap_.IntentBuilder_ mapLevel(Integer mapLevel) {
            intent_.putExtra("MAP_LEVEL", ((Serializable) mapLevel));
            return this;
        }

        public VenueMap_.IntentBuilder_ agendaPoint(Point agendaPoint) {
            intent_.putExtra("AGENDA_POINT", ((Serializable) agendaPoint));
            return this;
        }

    }

}
