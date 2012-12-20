package com.esri.webops.feduc2013.comman;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;


import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;

 public class Utility {

	App app;
	protected Utility(App app) {
		this.app = app;
	}
	
	public BitmapDrawable getImageFromAsset(String imageName) {
		BitmapDrawable bitmap = null;
        AssetManager mngr = app.getAssets();
        try {

            InputStream is = mngr.open(imageName);
            bitmap = new BitmapDrawable(BitmapFactory.decodeStream(is));

        } 
        catch (final IOException e) {
            ELogger.getLogger().log(Level.INFO,"Error in deocoding asset from bitmap",e);
        }
        return bitmap;
    }
	
	public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }
    
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
	    
    	Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
	        bitmap.getHeight(), Config.ARGB_8888);
	    Canvas canvas = new Canvas(output);
	 
	    final int color = 0xff424242;
	    final Paint paint = new Paint();
	    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
	    final RectF rectF = new RectF(rect);
	    final float roundPx = 6;
	 
	    paint.setAntiAlias(true);
	    canvas.drawARGB(0, 0, 0, 0);
	    paint.setColor(color);
	    canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
	    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
	    //paint.setXfermode(new PorterDuffXfermode(Mode.AVOID));
	    canvas.drawBitmap(bitmap, rect, rect, paint); 
	    return output;
	}
    
    public boolean isSDcardAvailable() {
    	String sdState=Environment.getExternalStorageState();
    	if(sdState.equals(Environment.MEDIA_MOUNTED))
    		return true;
    	return false;
    }
}
