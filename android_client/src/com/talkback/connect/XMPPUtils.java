package com.talkback.connect;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import android.util.Log;

/**
 * @author Thanavath Jaroenvanit (thanavath@graphicly.com)
 *
 */
public class XMPPUtils {
	
	private static String LOG_TAG = "TalkBack::XMPPUtils";

	private static String talkback_service = "talkback.im";
	//hard coded
	private static String host = "talkback.im";
	private static int port = 5222;
	
	
	/**
	 * Make a connection to talkback service
	 * @return the XMPPConnection if successfully connected, null otherwise
	 */
	public static XMPPConnection startConnection(){
		ConnectionConfiguration connConfig =
                new ConnectionConfiguration(host, port, talkback_service);
        XMPPConnection connection = new XMPPConnection(connConfig);
		connConfig.setSASLAuthenticationEnabled(true);
        
        try {
            connection.connect();
            Log.d(LOG_TAG, "Connected to " + connection.getHost());
            return connection;
        } catch (XMPPException ex) {
            Log.d(LOG_TAG, "Failed to connect to " + connection.getHost());
            Log.d(LOG_TAG, ex.toString());
            return null;
        }
	}
	
}