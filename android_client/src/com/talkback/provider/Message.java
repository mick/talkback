package com.talkback.provider;

/**
 * @author Thanavath Jaroenvanit (thanavath@graphicly.com)
 *
 */
public class Message {

	public static final String table_name = "messages";
	
	private static String table_column(String column){
		return table_name + "." + column;
	}
	
	/*-----column names-----*/
	public static final String _id = "_id";
	public static final String _chatroom_id = "chatroom_id";
	public static final String _roster_to = "roster_to";
	public static final String _roster_from = "roster_from";
	public static final String _content = "content";
	public static final String _unread = "unread";
	public static final String id = table_column(_id);
	public static final String chatroom_id = table_column(_chatroom_id);
	public static final String roster_to = table_column(_roster_to);
	public static final String roster_from = table_column(_roster_from);
	public static final String content = table_column(_content);
	public static final String unread = table_column(_unread);

	/*-----table creation string-----*/
	public static final String creation_string = "CREATE TABLE " +
					table_name + " (" +
					_id + " integer primary key, " +
					_chatroom_id + " integer, " +
					_roster_to + " integer, " +
					_roster_from + " integer, " +
					_content + " text, " +
					_unread + " integer, " +
					"FOREIGN KEY (" + _chatroom_id + ") REFERENCES " + ChatRoom.table_name + "(_id), " +
					"FOREIGN KEY (" + _roster_to + ") REFERENCES " + Roster.table_name + "(_id), " +
					"FOREIGN KEY (" + _roster_from + ") REFERENCES " + Roster.table_name + "(_id)" +
					");";
}
