package com.talkback.client;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.talkback.R;
import com.talkback.model.TalkBackMessage;

/**
 * @author Thanavath Jaroenvanit (thanavath@graphicly.com)
 *
 */
public class MessagesListAdapter extends ArrayAdapter<TalkBackMessage> {
	
	private final LayoutInflater inflater;
	
	public MessagesListAdapter(Context context, List<TalkBackMessage> messages) {
		super(context, 0, messages);
		inflater = LayoutInflater.from(context);
	}
	
	private class ViewHolder{
		TextView from;
		TextView body;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View view = convertView;
		ViewHolder holder;
		
		if(view == null){
			view = new LinearLayout(getContext());
			view = inflater.inflate(R.layout.message_view, (LinearLayout)view, true);
			
	        holder = new ViewHolder();
	        holder.from = (TextView)view.findViewById(R.id.msg_from);
	        holder.body = (TextView)view.findViewById(R.id.msg_body);
	        view.setTag(holder);
		}else{
			holder = (ViewHolder)view.getTag();
		}
		
		TalkBackMessage msg = getItem(position);
		
		holder.from.setText(msg.from);
		holder.body.setText(msg.content);
		
		return view;
	}
}
