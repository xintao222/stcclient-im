package stc.client.im;

import stc.bean.AuthRequest;
import stc.foundation.codec.CodecHelper;
import stc.foundation.codec.bean.tlv.annotation.TLVAttribute;
import stc.foundation.endpoint.SsipEndpoint.EP_STAT;
import stc.foundation.endpoint.SsipOverTCPEndpoint;
import stc.foundation.endpoint.SsipReceiver;
import stc.foundation.util.ByteUtils;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

import java.lang.Runnable;

public class MainActivity extends Activity implements SsipReceiver{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		new Thread(new Runnable(){

			@Override
			public void run() {
				AuthRequest req = new AuthRequest();
				req.setToken("123456");
				req.setSource("ssss");
				Log.i("activity", "send :"+req);
				
				SsipOverTCPEndpoint endpoint = new SsipOverTCPEndpoint();
				endpoint.init(MainActivity.this, "42.121.109.124", 20000);
				
				while (true) {
					endpoint.send(req);
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}}).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void messageReceived(Object msg) {
		// TODO Auto-generated method stub
		Log.i("activity", "messageReceived:"+msg);
	}

	@Override
	public void messageSent(Object msg) {
		// TODO Auto-generated method stub
		Log.i("activity", "messageSent:"+msg);
		
	}

	@Override
	public void messageFailed(Object msg) {
		// TODO Auto-generated method stub
		Log.i("activity", "messageFailed:"+msg);
		
	}

	@Override
	public void statusChanged(EP_STAT stat) {
		// TODO Auto-generated method stub
		
	}

}
