package jp.ccube.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

public class BindService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// バインドメッセージ
		Toast.makeText(this, "Start Service[Bind]", Toast.LENGTH_LONG).show();
		// 戻り値はIBindServiceのStab
		return mBindService;
	}
	
	private final IBindService.Stub mBindService = new IBindService.Stub() {
		
		@Override
		public String setMessage(String message) throws RemoteException {
			// 受信したメッセージに追加して送信
			return "[Receive!!]" + message;
		}
	};
	
	public void onDestroy() {
		super.onDestroy();
		// 終了メッセージ
		Toast.makeText(this, "Stop Service[Bind]", Toast.LENGTH_LONG).show();
	};

}