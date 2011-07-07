package jp.ccube.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener {
	// Bindサンプル用
	private EditText editBind;
	
	// Bindサービス
	IBindService bindService;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        findViewById(R.id.Button01).setOnClickListener(this);
        findViewById(R.id.Button02).setOnClickListener(this);
        
        editBind = (EditText)findViewById(R.id.EditText01);
        
        // バインド処理
        // TODO Intentコンストラクタ内の文字列は何に使うのか調査
        Intent bind = new Intent(IBindService.class.getName());
        bindService(bind, conn, BIND_AUTO_CREATE);
    }
    
    // ServiceConnectionインターフェースの実装クラス作成
    private ServiceConnection conn = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			bindService = IBindService.Stub.asInterface(service);
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Button01:
			// Intentを使ってサービス起動
			Intent simpleService = new Intent(this, SimpleService.class);
			startService(simpleService);
			break;
		case R.id.Button02:
			// Bindをつかってサービスと通信
			try {
				// サービスへメッセージ送信と受信
				String res = bindService.setMessage(editBind.getText().toString());
				// 表示
				Toast.makeText(getBaseContext(), res, Toast.LENGTH_LONG).show();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		// サービスをする
		unbindService(conn);
	}
}