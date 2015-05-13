package com.example.project2;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

//Method that contains the AsyncTask that is used when each ones of the widgets are talking to the rss reader
public class RssReader {

	public void ReceiveForecast2(IDone done, String topic, String pc, String ec) {
		try {
				AsyncTask task = new RssRetriever(done, pc, ec).execute(topic);
				Log.d("readerpc", pc);
				Log.d("readerec", ec);
				Log.d("task", " " + task);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public interface IDone {
		public void onDone(ArrayList<RssObject> rss_list);
	}

	private class RssRetriever extends AsyncTask<String, Void, ArrayList> {
		private IDone done;
		private String pc;
		private String ec;

		public RssRetriever(IDone done, String pc, String ec) {
			this.done = done;
			this.pc = pc;
			this.ec = ec;
		}

		protected ArrayList doInBackground(String... strings) {
			try {
				return RssHandler.ReadRss(strings[0], pc, ec);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		protected void onProgressUpdate(Void... progress) {
		}

		protected void onPostExecute(ArrayList result) {
			this.done.onDone(result);
			PrintReportToConsole();
		}

		private void PrintReportToConsole() {

		}
	}

}
