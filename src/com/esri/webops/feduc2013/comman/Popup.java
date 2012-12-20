package com.esri.webops.feduc2013.comman;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;

public class Popup {
	
	protected Popup() {}

	public void showMessageDialog(Context context,String title,String message) {
		Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				
			}
		});
		builder.show();
	}
	
	public void showExceptionDialog(Context context,String title,Exception ex) {
		showMessageDialog(context, title, ex.getMessage());
	}
	
	public void showToast(Context context,String message,int length) {
		
	}	
}
