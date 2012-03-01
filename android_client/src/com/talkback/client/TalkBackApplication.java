package com.talkback.client;

import android.app.Application;

import com.talkback.model.TalkBackUser;

/**
 * @author Thanavath Jaroenvanit (thanavath@graphicly.com)
 *
 */
public class TalkBackApplication extends Application {
	
	public TalkBackUser talkback_user;

	public void onCreate() {
		talkback_user = new TalkBackUser();
		talkback_user.connect("testuser1", "test44");
    }

    public void onTerminate() {
    }
    
}
