package com.talkback.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

/**
 * @author Thanavath Jaroenvanit (thanavath@graphicly.com)
 *
 */
public class TalkBackContentProvider extends ContentProvider{
	
	/*-----creating the database-----*/
	private SQLiteDatabase talkbackDB;

	private static final String TAG = "TalkBackContentProvider";
	private static final String DB_NAME = "talkback.db";
	private static final int DB_VERSION = 1;

	/*-----URIs-----*/
	private static final String CHATROOMS_URI_STR = "content://com.talkback.provider.chatrooms/chatrooms";
	public static final Uri CHATROOMS_CONTENT_URI = Uri.parse(CHATROOMS_URI_STR);
	private static final String CHATROOM_MESSAGES_URI_STR = "content://com.talkback.provider.chatrooms/messages";
	public static final Uri CHATROOM_MESSAGES_CONTENT_URI = Uri.parse(CHATROOM_MESSAGES_URI_STR);
	
	private static final int ALL_CHATROOMS = 1;
	private static final int SINGLE_CHATROOM = 2;
	private static final int ALL_CHATROOM_MESSAGES = 3;
	private static final int SINGLE_CHATROOM_MESSAGE = 4;
	
	private static final UriMatcher uriMatcher;
	static{
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI("com.talkback.provider.chatrooms", "chatrooms", ALL_CHATROOMS);
		uriMatcher.addURI("com.talkback.provider.chatrooms", "chatrooms/#", SINGLE_CHATROOM);
		uriMatcher.addURI("com.talkback.provider.chatrooms", "messages", ALL_CHATROOM_MESSAGES);
		uriMatcher.addURI("com.talkback.provider.chatrooms", "messages/#", ALL_CHATROOM_MESSAGES);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		switch(uriMatcher.match(uri)){
			case ALL_CHATROOMS:
			case ALL_CHATROOM_MESSAGES:
				return "vnd.talkback.cursor.dir/vnd.talkback.db.provider." + TAG;
			case SINGLE_CHATROOM:
			case SINGLE_CHATROOM_MESSAGE:
				return "vnd.talkback.cursor.item/vnd.talkback.db.provider." + TAG;
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long rowId;
		switch(uriMatcher.match(uri)){
			case ALL_CHATROOMS:
				rowId = talkbackDB.insert(ChatRoom.table_name, null, values);
				if(rowId > 0){
					uri = ContentUris.withAppendedId(CHATROOMS_CONTENT_URI, rowId);
					getContext().getContentResolver().notifyChange(uri, null);
					return uri;
				}
				return null;
			case ALL_CHATROOM_MESSAGES:
				rowId = talkbackDB.insert(Message.table_name, null, values);
				if(rowId > 0){
					uri = ContentUris.withAppendedId(CHATROOM_MESSAGES_CONTENT_URI, rowId);
					getContext().getContentResolver().notifyChange(uri, null);
					return uri;
				}
				return null;
		}
		return null;
	}

	@Override
	public boolean onCreate() {
		Context context = getContext();
		TalkBackSQLiteOpenHelper dbHelper = new TalkBackSQLiteOpenHelper(context, DB_NAME, null, DB_VERSION);
		talkbackDB = dbHelper.getWritableDatabase();
		return (talkbackDB == null) ? false : true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setDistinct(true);
		
		int matchedCode = uriMatcher.match(uri);
		
		switch(matchedCode){
			case SINGLE_CHATROOM:
				queryBuilder.appendWhere(ChatRoom.id + "=" + uri.getPathSegments().get(1));
			case ALL_CHATROOMS:
				queryBuilder.setTables(ChatRoom.table_name);
			break;
			case SINGLE_CHATROOM_MESSAGE:
				queryBuilder.appendWhere(Message.id + "=" + uri.getPathSegments().get(1));
			case ALL_CHATROOM_MESSAGES:
				queryBuilder.setTables(Message.table_name);
			break;
		}
		Cursor c = queryBuilder.query(talkbackDB, projection, selection, selectionArgs, null, null, sortOrder);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int count;
		String segment;
		switch(uriMatcher.match(uri)){
			case ALL_CHATROOMS:
				count = talkbackDB.update(ChatRoom.table_name, values, selection, selectionArgs);
			break;
			case SINGLE_CHATROOM:
				segment = uri.getPathSegments().get(1);
				count = talkbackDB.update(ChatRoom.table_name, values, ChatRoom.id + "=" + segment
						+ (!TextUtils.isEmpty(selection) ? " AND ("
						+ selection + ')' : ""), selectionArgs);
			break;
			case ALL_CHATROOM_MESSAGES:
				count = talkbackDB.update(Message.table_name, values, selection, selectionArgs);
			break;
			case SINGLE_CHATROOM_MESSAGE:
				segment = uri.getPathSegments().get(1);
				count = talkbackDB.update(Message.table_name, values, Message.id + "=" + segment
						+ (!TextUtils.isEmpty(selection) ? " AND ("
						+ selection + ')' : ""), selectionArgs);
			break;
			default: throw new IllegalArgumentException("Unknown URI " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}
	
}
