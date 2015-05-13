package com.example.project2;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.util.Log;
import android.widget.Toast;

//The class that speaks with the RSS reader
public class RssHandler {

	public static ArrayList<RssObject> ReadRss(String topic, String polchecked,
			String ecochecked) throws IOException,
			ParserConfigurationException, SAXException {
		ArrayList<RssObject> array_list = new ArrayList<RssObject>();
		ArrayList<RssObject> eco_array_list = new ArrayList<RssObject>();

		// Feedzilla is used as the main source for the rss reader
		String rss_url = "http://news.feedzilla.com/en_us/headlines/top-news/world-news.rss?client_source=feed";
		if (polchecked.equals("true")) {
			rss_url = "http://news.feedzilla.com/en_us/headlines/video/politics.rss";
		}

		if (ecochecked.equals("true") == true) {
			rss_url = "http://news.feedzilla.com/en_us/headlines/video/politics.rss";
		}

		try {
			URL url = new URL(rss_url);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			InputStream inputStream = connection.getInputStream();
			array_list = processXML(inputStream, topic);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (ecochecked.equals("true")) {
			try {
				String eco_rss_url = "http://news.feedzilla.com/en_us/headlines/top-news/business.rss";
				URL url2 = new URL(eco_rss_url);
				HttpURLConnection connection2 = (HttpURLConnection) url2
						.openConnection();
				connection2.setRequestMethod("GET");
				InputStream inputStream = connection2.getInputStream();
				eco_array_list = processXML(inputStream, topic);
				array_list.addAll(eco_array_list);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return array_list;
	}

	// The xml parser
	public static ArrayList<RssObject> processXML(InputStream inputStream,
			String topic) throws ParserConfigurationException, SAXException,
			IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(inputStream);
		Element rootElement = document.getDocumentElement();
		NodeList items = rootElement.getElementsByTagName("item");
		NodeList itemChildren = null;
		Node myItem = null;
		Node itemChild = null;
		ArrayList<RssObject> rss_list = new ArrayList<RssObject>();

		for (int x = 0; x < items.getLength(); x++) {
			myItem = items.item(x);
			itemChildren = myItem.getChildNodes();

			for (int y = 0; y < itemChildren.getLength(); y++) {
				itemChild = itemChildren.item(y);

				if (itemChild.getNodeName().equalsIgnoreCase("title")) {
					String title_text = itemChild.getTextContent();
					
					// Method used to find the rss objects that contains the
					// particular topic that the user had chosen
					//When you have the object, than you extract the children from that parent.
					if (title_text.toLowerCase().contains(topic.toLowerCase())) {

						Node parent = itemChild.getParentNode();
						NodeList parentChildren = parent.getChildNodes();
						Node firstChild = parentChildren.item(1);
						Node secondChild = parentChildren.item(2);
						Node thirdChild = parentChildren.item(3);
						Node fifthChild = parentChildren.item(5);
						Node sixthChild = parentChildren.item(6);


						rss_list.add(new RssObject(thirdChild.getTextContent(),
								"", firstChild.getTextContent(), sixthChild
										.getTextContent(), fifthChild
										.getTextContent(), ""));

					}

				}

			}
		}
		return rss_list;
	}

}
