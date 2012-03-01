package com.talkback.client;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import com.talkback.R;
import com.talkback.provider.ChatRoom;

/**
 * @author Thanavath Jaroenvanit (thanavath@graphicly.com)
 *
 */
public class ChatRoomsActivity extends Activity {

	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.chatrooms);
	     
	     Context appContext = getApplicationContext();
	     Cursor chatrooms_cursor = ChatRoom.getAll(appContext);
	     startManagingCursor(chatrooms_cursor);
	     ((ListView)findViewById(R.id.chatrooms_listview)).setAdapter(
	    		 new ChatRoomsAdapter(appContext, chatrooms_cursor));
	 }
	 
}
