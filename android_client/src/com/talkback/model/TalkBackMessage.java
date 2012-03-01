package com.talkback.model;

import java.util.Date;

import org.jivesoftware.smack.packet.Message;

/**
 * @author Thanavath Jaroenvanit (thanavath@graphicly.com)
 *
 */
public class TalkBackMessage {
	public String content;
	public String from;
	public String to;
	public Date date;
	
	public TalkBackMessage(){
		
	}
	
	public TalkBackMessage(String to, String content){
		this.to = to;
		this.content = content;
	}
	
	public TalkBackMessage(String from, String to, String content){
		this.from = from;
		this.to = to;
		this.content = content;
	}
	
	public TalkBackMessage(Message msg){
		content = msg.getBody();
		from = msg.getFrom().substring(msg.getFrom().indexOf("/") + 1);
	}
}
