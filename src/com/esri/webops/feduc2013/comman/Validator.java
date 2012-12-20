package com.esri.webops.feduc2013.comman;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Validator {
	
	App app;
	protected Validator(App app) {
		this.app = app;
	}

	public boolean isValidEmail() {
		boolean flag = false;
		
		return flag;
	}
	
	public boolean validateEmail(Context context,View edtx,String errorTitle,String errorMessage){

		String regExpn = "^([0-9a-zA-Z]([-\\.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,9})$";
		Pattern patternObj = Pattern.compile(regExpn);

	    Matcher matcherObj = patternObj.matcher((((EditText)edtx).getText().toString()).trim());
	    if (matcherObj.matches())
	    	return true;
	    else {
	    	app.popup.showMessageDialog(context, errorTitle, errorMessage);
	    	return false;
	    }
	 }
	
	public boolean validateText(Context context,View comp, String errorTitle, String errorMessage) {
		
		boolean flag = false;
		try {
			if (comp instanceof EditText) {
				final EditText box = (EditText) comp;
				if (box.getText() != null && (box.getText().toString()).trim() != null && box.getText().toString().trim().length() > 0) {
					flag = true;
				}
				else {
					app.popup.showMessageDialog(context, errorTitle, errorMessage);
				}				
			} 
			else if (comp instanceof TextView) {
				final TextView box = (TextView) comp;
				if (box.getText() != null && box.getText().toString().trim() != null && box.getText().toString().trim().length() > 0)
					flag = true;
				else
					app.popup.showMessageDialog(context, errorTitle, errorMessage);
			} 
			else if (comp == null)
				app.popup.showMessageDialog(context, errorTitle, errorMessage);
		} 
		catch (Exception ex) {
			
		} 
		return flag;
	}
	
	public boolean validateuserid(Context context,View edtx,String errorTitle,String errorMessage)
	{
		boolean flag=true;
		String emailid=((EditText)edtx).getText().toString();
		String idparts[]=emailid.split("@");
		String restrictid[]={"info", "help", "admin", "biz", "bizdev", "support", "faq", "customerservice", "press", "sales", "webmaster", "abuse", "postmaster", "editor", "hostmaster", "investorrelations", "jobs", "marketing", "media", "noc", "remove", "request", "root", "security", "spam", "subscribe", "usenet", "users", "uucp", "www", "news", "enquiries", "service", "advertising", "marketing", "finance", "accounting", "billing", "legal", "jobs", "hr", "service", "investors", "board", "ventas"};

		for(int i=0;i<restrictid.length;i++){
			if(idparts[0].equalsIgnoreCase(restrictid[i])){
				app.popup.showMessageDialog(context, errorTitle, errorMessage);
				flag=false;
			}
		}
		return flag;
	}
	
	public boolean validateDomain(Context context,View edtx,String errorTitle,String errorMessage){
		
		boolean flag=true;
		String emailid=((EditText)edtx).getText().toString();
		String domain=emailid.substring(emailid.length()-3);
		
		if(domain.equalsIgnoreCase("con")){
			app.popup.showMessageDialog(context, errorTitle, errorMessage);
			flag=false;
		}
		return flag;
	}
	public boolean validName(Context context,View comp, String errorTitle, String errorMessage) {
		String regExpn = "^[a-zA-Z]+$";
		Pattern patternObj = Pattern.compile(regExpn);

	    Matcher matcherObj = patternObj.matcher(((EditText)comp).getText().toString().trim());
	    if (matcherObj.matches())
	    	return true;
	    else {
	    	app.popup.showMessageDialog(context, errorTitle, errorMessage);
	    	return false;
	    }
	}
}
