package com.talkback.client;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.talkback.R;
import com.talkback.model.TalkBackUser;

/**
 * @author Thanavath Jaroenvanit (thanavath@graphicly.com)
 *
 */
public class LogInActivity extends Activity {

	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.login);
	     
	     findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String username = ((EditText)findViewById(R.id.username_input)).getText().toString();
				String password = ((EditText)findViewById(R.id.password_input)).getText().toString();
				TalkBackUser talkback_user = new TalkBackUser();
				if(talkback_user.connect(username, password)){
					((TalkBackApplication)getApplication()).saveUserCredentials(username, password);
					((TalkBackApplication)getApplication()).talkback_user = talkback_user;
					LogInActivity.this.setResult(1);
					LogInActivity.this.finish();
				}else{
					LogInActivity.this.setResult(0);
					((TextView)findViewById(R.id.error_msg)).setText("Can't log you in!");
				}
			}
		});
	 }
}
