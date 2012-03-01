package com.talkback.client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.talkback.R;
import com.talkback.provider.ChatRoom;

/**
 * @author Thanavath Jaroenvanit (thanavath@graphicly.com)
 * 
 */
public class ChatRoomsActivity extends Activity {

	private static String LOG_TAG = "TalkBack::ChatRoomsActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chatrooms);

		Context appContext = getApplicationContext();
		final Cursor chatrooms_cursor = ChatRoom.getAll(appContext);
		startManagingCursor(chatrooms_cursor);
		ListView chatrooms_listview = ((ListView) findViewById(R.id.chatrooms_listview));
		chatrooms_listview.setAdapter(new ChatRoomsAdapter(appContext,
				chatrooms_cursor));

		chatrooms_listview
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent(getApplicationContext(),
								ChatRoomActivity.class);
						chatrooms_cursor.moveToPosition(position);
						intent.putExtra("_id", chatrooms_cursor
								.getInt(chatrooms_cursor
										.getColumnIndex(ChatRoom._id)));
						ChatRoomsActivity.this.startActivity(intent);
					}
				});
	}

}
