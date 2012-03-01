package com.talkback.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.talkback.R;
import com.talkback.model.TalkBackUser;

public class TalkBackActivity extends Activity {

	private static String LOG_TAG = "TalkBack::TalkBackActivity";

	private static int LOG_IN = 1;
	private static int LOG_IN_RESULT_FAILED = 0;
	private static int LOG_IN_RESULT_OK = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		String[] user_creds = ((TalkBackApplication) getApplication())
				.getSavedUserCredentials();
		if (user_creds != null) {
			TalkBackUser talkback_user = new TalkBackUser();
			if (talkback_user.connect(user_creds[0], user_creds[1])) {
				((TalkBackApplication) getApplication()).talkback_user = talkback_user;
				startActivity(new Intent(this.getApplicationContext(),
						ChatRoomsActivity.class));
			}else{
				//clear stored cred and launch login activity
				((TalkBackApplication) getApplication()).clearUserCredentials();
				startActivityForResult(new Intent(this.getApplicationContext(),
						LogInActivity.class), 1);
			}
		} else {
			startActivityForResult(new Intent(this.getApplicationContext(),
					LogInActivity.class), 1);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == LOG_IN) {
			if (resultCode == LOG_IN_RESULT_OK) {
				startActivity(new Intent(this.getApplicationContext(),
						ChatRoomsActivity.class));
			} else if (resultCode == LOG_IN_RESULT_FAILED) {

			}
		}
	}

}