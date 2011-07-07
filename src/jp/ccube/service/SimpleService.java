package jp.ccube.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class SimpleService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		// 開始トースト
		Toast.makeText(this, "Start Service", Toast.LENGTH_LONG).show();
		
		// 10秒待つ
		Thread t = new Thread() {
			public void run() {
				try {
					Thread.sleep(10 * 1000);
					//自分を止める
					stopSelf();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
	}
	
	@Override
	public void onDestroy() {
		// 終了トースト
		Toast.makeText(this, "End Service", Toast.LENGTH_LONG).show();
	}

}
