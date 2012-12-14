package com.esri.webops.feduc2013;

import java.util.ArrayList;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AgendaGroup extends ActivityGroup {
	
	public static AgendaGroup group;
	private ArrayList<View> history;
	
	@Override  
    protected void onCreate(Bundle savedInstanceState) {  
          super.onCreate(savedInstanceState);  
          this.history = new ArrayList<View>();  
          group = this;  
  
          View view = getLocalActivityManager().startActivity("Home", new  
                                            Intent(this,Agenda_.class)  
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))  
                                            .getDecorView();  
          replaceView(view);  
	}  

    public void replaceView(View v) {  
        history.add(v);  
        setContentView(v);  
    }  

    public void back() {  
        if(history.size() > 0) {  
            history.remove(history.size()-1);  
            if(history.size() > 0) {
                 setContentView(history.get(history.size()-1));  
            }
            else {  
                finish();  
            } 
        }
        else {  
            finish();  
        }  
    }  

   @Override  
    public void onBackPressed() {  
	   AgendaGroup.group.back();  
	   return;  
    }
}
