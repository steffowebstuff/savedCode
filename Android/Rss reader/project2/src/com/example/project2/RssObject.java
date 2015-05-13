package com.example.project2;

import java.net.URL;

public class RssObject {
	

	//The object that represents each item in the rss list
	private String url = null;
	private String content = null;
	private String title = null;
	private String time = null;
	private String source = null;
	private String link = null;
	
	public RssObject(String _title, String _content, String _url, String _time, String _source, String _link){
		url = _url;
		content = _content;
		title = _title;
		time = _time;
		source = _source;
		link = _link;
	}
	
	public String GetUrl(){
		return url;
	}
	
	public String GetTitle(){
		return title;
	}
	
	public String GetContent(){
		return content;
	}
	
	public String GetLink(){
		return link;
	}
	
	public String GetTime(){
		return time;
	}
	
	public String GetSource(){
		return source;
	}

}
