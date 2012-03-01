package com.talkback.provider;

/**
 * @author Thanavath Jaroenvanit (thanavath@graphicly.com)
 *
 */
public class Roster {

	public static final String table_name = "rosters";
	
	private static String table_column(String column){
		return table_name + "." + column;
	}
	
	/*-----column names-----*/
	public static final String _id = "_id";
	public static final String _jid = "jid";
	public static final String _nickname = "nickname";
	public static final String _avatar_hash = "avatar_hash";
	public static final String _availability = "availability";
	public static final String id = table_column(_id);
	public static final String jid = table_column(_jid);
	public static final String nickname = table_column(_nickname);
	public static final String avatar_hash = table_column(_avatar_hash);
	public static final String availability = table_column(_availability);

	/*-----table creation string-----*/
	public static final String creation_string = "CREATE TABLE " +
					table_name + " (" +
					_id + " integer primary key, " +
					_jid + " text, " +
					_nickname + " text, " +
					_avatar_hash + " text, " +
					_availability + " integer" +
					");";
}
