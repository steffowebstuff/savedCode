package com.example.secondassignment;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import com.example.secondassignment.SlowCountService.SlowBinder;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.TrackInfo;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

//The service class for the mp3 player. Handles the music
public class MP3Service extends Service {

	MediaPlayer m_player = null;
	File file = Environment
			.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
	File[] files = file.listFiles();

	int filecount = files.length;
	int songnumber = 0;
	int currentseconds = 0;
	public boolean isPaused = false;
	public boolean killedseconds = false;
	public boolean started = false;
	public boolean bound = false;

	public void onCreate() {
		System.out.println("onCreate()");
		Log.d("service", "started");
		m_player = new MediaPlayer();
		m_player.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer a_mp) {
				forward();
				Play();
			}
		});
		started = false;
	}

	public void setSongNumber(int _songNumber) {
		songnumber = _songNumber;
	}

	public void setServiceString(String msg) {
		Log.d("back button clicked", msg);
	}

	public String getServiceString() {
		String teststring = "teststring";
		return teststring;
	}

	public int getCurrentSeconds() {
		return currentseconds;
	}

	public void setCurrentSeconds(int number) {
		currentseconds = 0;
		Log.d("currentseconds after set", " " + currentseconds);
	}

	public boolean plays() {
		if (m_player != null) {
			return m_player.isPlaying();
		} else {
			return false;
		}
	}

	public void pause() {
		m_player.pause();
	}

	public void Stop() {
		m_player.stop();
	}

	public void forward() {
		if (songnumber >= filecount) {
			return;
		}
		songnumber++;
		started = false;
		Log.d("songnumber after forward", " " + songnumber);
	}

	public void back() {
		if (songnumber < 1) {
			return;
		}
		songnumber--;
		started = false;
		Log.d("songnumber after forward", " " + songnumber);
	}

	public int getNumber() {
		int number = songnumber;
		return number;
	}

	public int getListLength() {
		return filecount;
	}

	public void killseconds() {
		this.killedseconds = true;
		this.m_player.seekTo(0);
	}

	public void Play() {

		try {
			// To avoid reset problems
			if (!started) {
				File file = Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
				File[] files = file.listFiles();
				String firstmusicpath = files[songnumber].getPath();
				m_player.reset();
				m_player.setDataSource(firstmusicpath);
				m_player.prepare();
				started = true;
			}
			m_player.start();
			
		} catch (IOException e) {
			Log.d("ex", "" + e);

		}
	}

	@Override
	public void onDestroy() {
		Log.d("service", "stoppped");
		if (m_player != null) {
			this.m_player.release();
			this.m_player = null;
		}
	}

	private final IBinder binder = new MP3Binder();

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	public class MP3Binder extends Binder {
		MP3Service getService() {
			return MP3Service.this;
		}
	}

	// Thread used to keep track of the seconds. Supposed to eventually be used
	// to get and set the right time, but could not fix the connection
	// right with the service in time.
	Runnable work = new Runnable() {
		public void run() {
			while (killedseconds == false) {
				currentseconds++;
				Log.d("currentseconds", "" + currentseconds);
				SystemClock.sleep(1000);
			}
		}
	};

}
