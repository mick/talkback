package com.talkback.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * @author Thanavath Jaroenvanit (thanavath@graphicly.com)
 * Helper class for ChatRoom table, hold column names for easy access
 */
public class ChatRoom {

	public static final String table_name = "chatrooms";
	
	private static String table_column(String column){
		return table_name + "." + column;
	}
	
	/*-----column names-----*/
	public static final String _id = "_id";
	public static final String _name = "name";
	public static final String _password = "password";
	public static final String _owner_jid = "owner_jid";
	public static final String _nickname = "nickname";
	public static final String _last_login = "last_login";
	public static final String id = table_column(_id);
	public static final String name = table_column(_name);
	public static final String password = table_column(_password);
	public static final String owner_jid = table_column(_owner_jid);
	public static final String nickname = table_column(_nickname);
	public static final String last_login = table_column(_last_login);

	/*-----table creation string-----*/
	public static final String creation_string = "CREATE TABLE " +
					table_name + " (" +
					_id + " integer primary key, " +
					_name + " text, " +
					_password + " text, " +
					_owner_jid + " text, " +
					_nickname + " text, " +
					_last_login +  " long);";
	
	public static Cursor getAll(Context context){
		ContentResolver cr = context.getContentResolver();
		return cr.query(TalkBackContentProvider.CHATROOMS_CONTENT_URI, null, null, null, null);
	}
	
	public static Cursor get(Context context, int position){
		ContentResolver cr = context.getContentResolver();
		return cr.query(Uri.withAppendedPath(TalkBackContentProvider.CHATROOMS_CONTENT_URI, position + ""), null, null, null, null);
	}
	
	public static boolean insert(Context context, String chatroom_name, String chatroom_password, String nickname,
			String chatroom_owner_jid){
		ContentResolver cr = context.getContentResolver();
		//make sure the room does not exist
		String w = _name + " = '" + chatroom_name + "'";
		Cursor getChatRoom =  cr.query(TalkBackContentProvider.CHATROOMS_CONTENT_URI, null, w, null, null);
		int count = (getChatRoom != null) ? getChatRoom.getCount() : 0;
		if(getChatRoom != null) getChatRoom.close();
		if(count == 0){
			ContentValues values = new ContentValues();
			values.put(_name, chatroom_name);
			if(!chatroom_password.equals(""))
				values.put(_password, chatroom_password);
			if(!_owner_jid.equals(""))
				values.put(_owner_jid, chatroom_owner_jid);
			values.put(_nickname, nickname);
			cr.insert(TalkBackContentProvider.CHATROOMS_CONTENT_URI, values);
			return true;
		}
		return false;
	}
	
}
