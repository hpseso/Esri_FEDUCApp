package com.esri.webops.feduc2013;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;


@EActivity(R.layout.splash)
public class Splash extends Activity {
	
	
	@AfterViews
	void loadView() {
		Message msg = new Message();
		new SplashHandler(this).sendMessageDelayed(msg, 1000);
	}

	private static final class SplashHandler extends Handler {
		
		final Splash splashActivity;
		
		public SplashHandler(Splash splashActivity) {
			this.splashActivity = splashActivity;
		}
		
		@Override
		public void handleMessage(Message msg) {
			splashActivity.startActivity(new Intent(splashActivity,Home_.class));
			splashActivity.finish();
			super.handleMessage(msg);
		}
	}

}

