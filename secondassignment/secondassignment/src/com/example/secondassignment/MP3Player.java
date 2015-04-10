package com.example.secondassignment;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MP3Player extends Activity {

	// This is the main class for the mp3 player
	// This file and MP3 Service are used in this application
	private Activity main_activity;
	private MP3Service service = null;
	int currenttrack = 0;
	int listlength = 0;
	int seconds;
	int minutes;
	int song;
	boolean started;
	boolean playing;
	public static final int NOTIFICATION_ID = 1000;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mp3player);
		Resources res = getResources();
		final String stop = String.format(res.getString(R.string.stop));
		final String start = String.format(res.getString(R.string.start));
		final String play = String.format(res.getString(R.string.play));
		final String pause = String.format(res.getString(R.string.pause));
		final String next = String.format(res.getString(R.string.next));
		final String back = String.format(res.getString(R.string.back));
		final String empty = String.format(res.getString(R.string.empty));
		
		main_activity = this;

		final Button startbutton = (Button) findViewById(R.id.playbutton);
		final Button stopbutton = (Button) findViewById(R.id.stopbutton);
		final Button forwardbutton = (Button) findViewById(R.id.forwardbutton);
		final Button backbutton = (Button) findViewById(R.id.backbutton);
		final Button pausbutton = (Button) findViewById(R.id.infobutton);

		if (savedInstanceState != null) {
			try {
				song = savedInstanceState.getInt("song");
				seconds = savedInstanceState.getInt("seconds");
				minutes = savedInstanceState.getInt("minutes");
				started = savedInstanceState.getBoolean("started");
				if (started == true) {
					pausbutton.setText(play);
					forwardbutton.setText(next);
					backbutton.setText(back);
					stopbutton.setText(stop);
					startbutton.setText(empty);
					
					playing = savedInstanceState.getBoolean("playing");
					if (playing == true) {
						pausbutton.setText(pause);
					} else {
						pausbutton.setText(play);
					}
				}
				
				currenttrack = song;
				Intent intent = new Intent(main_activity, MP3Service.class);
				main_activity.bindService(intent, connection,
						Context.BIND_AUTO_CREATE); //

				Toast.makeText(
						getApplicationContext(),
						"Rotation made, seconds " + seconds + " minutes "
								+ minutes + " song " + song + " playing "
								+ playing + " started " + started,

						Toast.LENGTH_LONG).show();
			} catch (Exception e) {
			}
		}

		// Have used the startbutton here to bind with the service.
		// The paus button is used to play and pause the songs.
		startbutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(main_activity, MP3Service.class);
				main_activity.bindService(intent, connection,
						Context.BIND_AUTO_CREATE);
				started = true;
				pausbutton.setText(play);
				forwardbutton.setText(next);
				backbutton.setText(back);
				stopbutton.setText(stop);
				startbutton.setText(empty);

			}
		});

		stopbutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(main_activity, MP3Service.class);
				try {
					if (service != null) {
						main_activity.unbindService(connection);
						started = false;
						playing = false;
					}
				} catch (Exception e) {
					Log.d("in", "catch");
					Toast.makeText(
							getApplicationContext(),
							"You cant stop the connection, it is already stopped",
							Toast.LENGTH_SHORT).show();
				}
				Log.d("data", "stopstuff");
				pausbutton.setText(empty);
				stopbutton.setText(empty);
				backbutton.setText(empty);
				forwardbutton.setText(empty);
				startbutton.setText(start);

				// Remove notification when stop button is pressed
				((NotificationManager) getSystemService(NOTIFICATION_SERVICE))
						.cancel(MP3Player.NOTIFICATION_ID);
			}
		});

		forwardbutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					listlength = service.getListLength();

					Toast.makeText(getApplicationContext(),
							"listlength = " + listlength, Toast.LENGTH_SHORT)
							.show();
					service.forward();
					currenttrack = service.getNumber();
					song = currenttrack;
					Toast.makeText(getApplicationContext(),
							"currenttrack = " + currenttrack,
							Toast.LENGTH_SHORT).show();
					if (currenttrack < listlength) {
						service.killseconds();
						service.Play();
						playing = true;
						pausbutton.setText(pause);
					} else {
						Toast.makeText(
								getApplicationContext(),
								"You are at the end of the list, please go back",
								Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					Toast.makeText(
							getApplicationContext(),
							"You cant go forward right now. Make sure the connection is working",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		backbutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					currenttrack = service.getNumber();

					Toast.makeText(getApplicationContext(),
							"currenttrack = " + currenttrack,
							Toast.LENGTH_SHORT).show();
					if (currenttrack > 0) {
						service.back();
						service.getNumber();
						service.killseconds();
						service.Play();
						playing = true;
						pausbutton.setText(pause);
					} else {
						Toast.makeText(getApplicationContext(),
								"You cant go back from here" + currenttrack,
								Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					Toast.makeText(
							getApplicationContext(),
							"You cant go back right now. Make sure the connection is working",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		pausbutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					boolean plays = service.plays();
					if (plays == true) {
						service.pause();
						playing = false; // Playing is just for the rotation
											// stuff
						pausbutton.setText(play);
					} else {
						service.Play();
						playing = true;
						pausbutton.setText(pause);
					}
				} catch (Exception e) {

					Toast.makeText(
							getApplicationContext(),
							"You cant use this button right now. Make sure the connection is working",
							Toast.LENGTH_SHORT).show();

				}
			}
		});

	}

	// The connection and binding is more or less taken from the pdf-file
	private ServiceConnection connection = new ServiceConnection() {
		public void onServiceConnected(ComponentName cName, IBinder binder) {
			service = ((MP3Service.MP3Binder) binder).getService();
		}
		public void onServiceDisconnected(ComponentName cName) {
			service = null;
		}
	};

	@Override
	protected void onStop() {
		super.onStop();
		setNotification();
		Toast.makeText(
				getApplicationContext(),
				"music symbol should come up in notification bar. "
						+ "Press it to get to mp3 player", Toast.LENGTH_LONG)
				.show();
	}

	// More or less completely taken from the example
	public void setNotification() {
		Notification.Builder builder = new Notification.Builder(main_activity);
		if (started == true) {
			builder.setSmallIcon(R.drawable.music)
					.setWhen(System.currentTimeMillis()).setAutoCancel(false);
			builder.setContentTitle("MP3player")
					.setContentText("Click to get to player.")
					.setContentInfo("Click!");
			Intent intent = new Intent(main_activity, MP3Player.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			PendingIntent notifIntent = PendingIntent.getActivity(
					main_activity, 0, intent, 0);
			builder.setContentIntent(notifIntent);

			Notification notification = builder.build();
			String ns = Context.NOTIFICATION_SERVICE;
			NotificationManager notifManager = (NotificationManager) getSystemService(ns);
			notifManager.notify(NOTIFICATION_ID, notification);
		} else {
			Toast.makeText(getApplicationContext(),
					"No notification, since mp3 player is not in use",
					Toast.LENGTH_LONG).show();
		}
	}

	public void onSaveInstanceState(Bundle savedInstanceState) {

		try {
			savedInstanceState.putInt("song", song);
			savedInstanceState.putInt("minutes", minutes);
			savedInstanceState.putInt("seconds", seconds);
			savedInstanceState.putBoolean("playing", playing);
			savedInstanceState.putBoolean("started", started);
		}

		catch (Exception e) {

		}
		super.onSaveInstanceState(savedInstanceState);
	}

}
