package com.talkback.client;

import com.talkback.R;
import com.talkback.provider.ChatRoom;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Thanavath Jaroenvanit (thanavath@graphicly.com)
 *
 */
public class ChatRoomsAdapter extends CursorAdapter {

	private final LayoutInflater inflater;

	/**
	 * @param context
	 * @param c
	 */
	public ChatRoomsAdapter(Context context, Cursor c) {
		super(context, c);
		inflater = LayoutInflater.from(context);
	}
	

	
	private class ViewHolder{
		TextView title_view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = (ViewHolder)view.getTag();
		
		holder.title_view.setText(cursor.getString(cursor.getColumnIndex(ChatRoom._name)));
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LinearLayout  newView = new LinearLayout(context);
		inflater.inflate(R.layout.chatroom_listview_item, newView, true);
        ViewHolder holder = new ViewHolder();
        holder.title_view = (TextView)newView.findViewById(R.id.chatroom_name);
        newView.setTag(holder);

        bindView(newView, context, cursor);
		
		return newView;
	}

}
