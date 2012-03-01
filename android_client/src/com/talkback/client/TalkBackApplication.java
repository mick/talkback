package com.talkback.client;

import android.app.Application;
import android.content.SharedPreferences;

import com.talkback.model.TalkBackUser;

/**
 * @author Thanavath Jaroenvanit (thanavath@graphicly.com)
 * 
 */
public class TalkBackApplication extends Application {

	public TalkBackUser talkback_user;
	//("testuser1", "test44")
	
	public void onCreate() {
	}

	public void onTerminate() {
	}

	private static final String user_creds_pref_file = "users_creds";
	
	public String[] getSavedUserCredentials() {
		SharedPreferences user_creds_file = this.getSharedPreferences(user_creds_pref_file, MODE_PRIVATE);
		String[] result = {user_creds_file.getString("username", ""), user_creds_file.getString("password", "")};
		return (result[0].equals("") ? null : result);
	}
	
	public void saveUserCredentials(String username, String password) {
		SharedPreferences user_creds_file = this.getSharedPreferences(user_creds_pref_file, MODE_PRIVATE);
		SharedPreferences.Editor editor = user_creds_file.edit();
		editor.putString("username", username);
		editor.putString("password", password);
		editor.commit();
	}
	
	public void clearUserCredentials(){
		SharedPreferences user_creds_file = this.getSharedPreferences(user_creds_pref_file, MODE_PRIVATE);
		SharedPreferences.Editor editor = user_creds_file.edit();
		editor.clear();
		editor.commit();
	}

}
