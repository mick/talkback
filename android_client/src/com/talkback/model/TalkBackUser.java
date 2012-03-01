package com.talkback.model;

import java.util.Iterator;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.muc.MultiUserChat;

import android.content.Context;
import android.util.Log;

import com.talkback.connect.XMPPUtils;
import com.talkback.provider.ChatRoom;

/**
 * @author Thanavath Jaroenvanit (thanavath@graphicly.com)
 *
 */
public class TalkBackUser {
	
	private static String LOG_TAG = "TalkBack::TalkBackUser";
	
	public XMPPConnection connection;
	
	public TalkBackUser(){
		connection = XMPPUtils.startConnection();
	}
	
	public boolean connect(String username, String password){
		return connect(username, password, Presence.Type.available);
	}
	
	public boolean connect(String username, String password, Presence.Type status){
		try {
			connection.login(username, password);
            Log.i(LOG_TAG, "Logged in as " + connection.getUser());
            // Set the status to available
            Presence presence = new Presence(status);
            connection.sendPacket(presence);
            connection.addConnectionListener(new ConnectionListener() {
				
				public void reconnectionSuccessful() {
					Log.i(LOG_TAG, "reconnectionSuccessful");
				}
				
				public void reconnectionFailed(Exception arg0) {
					Log.i(LOG_TAG, "reconnectionFailed :: " + arg0);
				}
				
				public void reconnectingIn(int arg0) {
					Log.i(LOG_TAG, "reconnectingIn :: " + arg0);
				}
				
				public void connectionClosedOnError(Exception arg0) {
					Log.i(LOG_TAG, "connectionClosedOnError :: " + arg0);
				}
				
				public void connectionClosed() {
					Log.i(LOG_TAG, "connectionClosed");
				}
			});
            return true;
        } catch (Exception ex) {
            Log.e(LOG_TAG, "Failed to log in as " + username);
            Log.e(LOG_TAG, ex.toString());
            return false;
        }   
	}
	
	public void sendMessage(TalkBackMessage message){
		if(!connection.isConnected()){
			try {
				connection.connect();
			} catch (XMPPException e) {
				Log.e(LOG_TAG, e.toString());
				return;
			}
		}
		Log.i(LOG_TAG, "Sending text [" + message.content + "] to [" + message.to + "]");
        Message msg_obj = new Message(message.to, Message.Type.chat);
        msg_obj.setBody(message.content);
        connection.sendPacket(msg_obj);
	}
	
	public MultiUserChat joinChatRoom(Context context, String room, String nickname){
		if(!connection.isConnected()){
			try {
				connection.connect();
			} catch (XMPPException e) {
				Log.e(LOG_TAG, e.toString());
				return null;
			}
		}
		Log.i(LOG_TAG, "Joining " + room + " as " + nickname);
		MultiUserChat chat_room = new MultiUserChat(connection, room);
		try{
			chat_room.join(nickname);
			Log.i(LOG_TAG, "Joined " + room + " as " + nickname);
			ChatRoom.insert(context, room, "", nickname, "");
	    	return chat_room;
		}catch (Exception e) {
			Log.e(LOG_TAG, "Failed to join chat room " + room);
			Log.e(LOG_TAG, e.toString());
			return null;
		}
	}
	
	public MultiUserChat joinChatRoom(Context context, String room, String nickname, String password){
		if(!connection.isConnected()){
			try {
				connection.connect();
			} catch (XMPPException e) {
				Log.e(LOG_TAG, e.toString());
				return null;
			}
		}
		Log.i(LOG_TAG, "Joining " + room + " as " + nickname);
	    MultiUserChat chat_room = new MultiUserChat(connection, room);
	    try{
	    	chat_room.join(nickname, password);
	    	Log.i(LOG_TAG, "Joined " + room + " as " + nickname);
			ChatRoom.insert(context, room, "", nickname, "");
			
	    	return chat_room;
	    }catch (Exception e) {
	    	Log.e(LOG_TAG, "Failed to join chat room " + room);
	    	Log.e(LOG_TAG, e.toString());
			return null;
	    }
	}
	
	public void leaveRoom(MultiUserChat chatroom){
		if(!connection.isConnected()){
			try {
				connection.connect();
			} catch (XMPPException e) {
				Log.e(LOG_TAG, e.toString());
				return;
			}
		}
		chatroom.leave();
	}
}
