package com.esri.webops.feduc2013;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ViewSwitcher.ViewFactory;

import com.esri.webops.feduc2013.comman.App;
import org.json.JSONObject;

@SuppressLint("HandlerLeak")
public class BaseActivity extends Activity  implements ViewFactory  {

	ImageSwitcher adImageSwitcher;
	ProgressDialog progressDialog;


	public void loadMap(int which) {
		Intent intent = new Intent(this,Map_.class);
		intent.putExtra("MAP_TYPE", which);
		startActivity(intent);
	}
	
	protected void setPref(String key, String val) {
		SharedPreferences.Editor pref = getSharedPreferences(App.Esri_Prefrences, MODE_PRIVATE).edit();
		pref.putString(key, val);
		pref.commit();
	}
	
	protected void setPref(String key, int val) {
		SharedPreferences.Editor pref = getSharedPreferences(App.Esri_Prefrences, MODE_PRIVATE).edit();
		pref.putInt(key, val);
		pref.commit();
	}
	
	protected String getStringPref(String key) {
		SharedPreferences pref = getSharedPreferences(App.Esri_Prefrences, MODE_PRIVATE);
		return pref.getString(key, "");
	}
	
	protected int getIntPref(String key, int def) {
		SharedPreferences pref = getSharedPreferences(App.Esri_Prefrences, MODE_PRIVATE);
		return pref.getInt(key, def);
	}
	
	public void call(String number) {
		Logger.getLogger("ESRI").info("Calling :" + number);
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
		startActivityForResult(intent, 101);
	}
	
	public void loadBrowser(String url) {
		Logger.getLogger("ESRI").info("Loading :" + url);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(url));
		startActivityForResult(intent, 101);
	}
	
	public void email(String email) {
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {email});
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Inquiry from the FEDUC 2013 Conference App");
		emailIntent.setType("plain/text");
		startActivity(emailIntent);
	}
	
	public void loadCalendar(int type) {
		String title = "";
		long startTime = Calendar.getInstance().getTimeInMillis();
		long endTime = Calendar.getInstance().getTimeInMillis() + (1000*60);
		switch (type) {
			case 1: {
				title = "Esri Partner Conference";
				
				Calendar scal = Calendar.getInstance();
				scal.set(Calendar.DAY_OF_MONTH, 23);
				scal.set(Calendar.MONTH, Calendar.MARCH);
				scal.set(Calendar.YEAR, 2013);
				startTime = scal.getTimeInMillis();
				
				Calendar ecal = Calendar.getInstance();
				ecal.set(Calendar.DAY_OF_MONTH, 27);
				ecal.set(Calendar.MONTH, Calendar.MARCH);
				ecal.set(Calendar.YEAR, 2013);
				endTime = ecal.getTimeInMillis();
			}
			break;
			
			case 2: {
				title = "Esri Developer Summit";
				
				Calendar scal = Calendar.getInstance();
				scal.set(Calendar.DAY_OF_MONTH, 25);
				scal.set(Calendar.MONTH, Calendar.MARCH);
				scal.set(Calendar.YEAR, 2013);
				startTime = scal.getTimeInMillis();
				
				Calendar ecal = Calendar.getInstance();
				ecal.set(Calendar.DAY_OF_MONTH, 29);
				ecal.set(Calendar.MONTH, Calendar.MARCH);
				ecal.set(Calendar.YEAR, 2013);
				endTime = ecal.getTimeInMillis();
			}
			break;
			
			case 3: {
				title = "Esri International User Conference";
				
				Calendar scal = Calendar.getInstance();
				scal.set(Calendar.DAY_OF_MONTH, 8);
				scal.set(Calendar.MONTH, Calendar.JULY);
				scal.set(Calendar.YEAR, 2013);
				startTime = scal.getTimeInMillis();
				
				Calendar ecal = Calendar.getInstance();
				ecal.set(Calendar.DAY_OF_MONTH, 13);
				ecal.set(Calendar.MONTH, Calendar.JULY);
				ecal.set(Calendar.YEAR, 2013);
				endTime = ecal.getTimeInMillis();
			}
			break;
		}
		
		Intent intent = new Intent(Intent.ACTION_EDIT);
		intent.setType("vnd.android.cursor.item/event");
		intent.putExtra("title", title);
		intent.putExtra("description", "Some description");
		intent.putExtra("beginTime", startTime);
		intent.putExtra("endTime", endTime);
		startActivity(intent);
	}
	
	protected void initImageSwithcer() {
		adImageSwitcher.setFactory(BaseActivity.this);
		adImageSwitcher.setAnimation(AnimationUtils.loadAnimation(BaseActivity.this, android.R.anim.fade_in));
		adImageSwitcher.setAnimation(AnimationUtils.loadAnimation(BaseActivity.this, android.R.anim.fade_out));
	}
	
	protected Handler adHandler = new Handler() {
		
		@Override
		public void handleMessage(Message msg) {
			
			if (adImageSwitcher != null) {
				
				SharedPreferences.Editor prefEdit = getSharedPreferences("ESRI_PREF",MODE_PRIVATE).edit();
				SharedPreferences prefRead = getSharedPreferences("ESRI_PREF",MODE_PRIVATE);
				
				int imageNo = prefRead.getInt("LAST_IMAGE", 0);
				
				if (imageNo >= 5)
					imageNo = 0;
				else
					imageNo +=1;
				
				adImageSwitcher.setImageResource(sponsorImages[imageNo]);
				adHandler.sendEmptyMessageDelayed(0, 10000);
				
				prefEdit.putInt("LAST_IMAGE", imageNo);
				prefEdit.commit();
			}
			super.handleMessage(msg);
		}
	};
	
	@Override
	public void onResume() {
		super.onResume();
		Logger.getLogger("ESRI").info("Sending signal to change image");
		adHandler.sendEmptyMessageDelayed(0, 0);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		Logger.getLogger("ESRI").info("Sending signal to quit");
		adHandler.removeMessages(0);
	}
	
	@Override
	public View makeView() {
		ImageView iView = new ImageView(this);
        iView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageSwitcher.LayoutParams params = new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);

        iView.setLayoutParams(params);
        iView.setBackgroundColor(0xFFFFFFFF);
        iView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				SharedPreferences prefRead = getSharedPreferences("ESRI_PREF",MODE_PRIVATE);
				int imageNo = prefRead.getInt("LAST_IMAGE", 0);
				loadBrowser(sponsorURLS[imageNo]);
			}
		});
        
        return iView;
	}
	
	private Integer[] sponsorImages = {
			R.drawable.exelis_logo,R.drawable.tomtom_logo,
			R.drawable.ibm_logo,R.drawable.terrago_logo,
			R.drawable.baker_logo,R.drawable.hdr_logo,
	};
	
	private String[] sponsorURLS = {
            "http://www.exelisvis.com","http://www.tomtom.com/licensing",
            "http://www.ibm.com","http://www.terragotech.com/",
            "http://www.mbakercorp.com","http://www.hdrinc.com"
	};
	
	
	protected InputStream makeWebPost(String url) throws Exception{

		HttpParams httpParameters = new BasicHttpParams();
		int timeoutConnection = 20000;
        HttpProtocolParams.setVersion(httpParameters, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(httpParameters, HTTP.UTF_8);
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		int timeoutSocket = 20000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		HttpClient client = getNewHttpClient(httpParameters);
		
		//HttpPost  request = new HttpPost (url);
		
		HttpGet request = new HttpGet(url);
		
		//request.addHeader("Content-Type","application/x-www-form-urlencoded");
		request.addHeader("X-Parse-Application-Id","Z9KV6PcJMRRb0lGxPqqIrRKxA3d2WN8D4cSwEmqw");
		request.addHeader("X-Parse-REST-API-Key","WGCdl9usfxVLpu7UyiTRK2I1fClVfSZLXkmc5nao");

		HttpResponse response;
        response = client.execute(request);
        return response.getEntity().getContent();
	}
	
	protected HttpClient getNewHttpClient(HttpParams params) {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }
	
	protected class MySSLSocketFactory extends SSLSocketFactory {
        SSLContext sslContext = SSLContext.getInstance("TLS");

        public MySSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
            super(truststore);

            TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };

            sslContext.init(null, new TrustManager[] { tm }, null);
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
            return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }
    }
	
	protected String parseResponseToString (InputStream stream) throws Exception {
    	StringBuffer buf = new StringBuffer();
    		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	     	   buf.append(line);
	        }
    	return buf.toString();
    }
}
