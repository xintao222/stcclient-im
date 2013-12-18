package stc.client.im;

import stc.bean.AuthRequest;
import stc.bean.AuthResponse;
import stc.foundation.codec.CodecManager;
import stc.foundation.util.ByteUtils;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		AuthRequest req = new AuthRequest(); 
		req.setSkyId(1000);
		req.setToken("123456");
		byte[] x = CodecManager.encode(req);
		Log.e("test", ByteUtils.bytesAsHexString(x, 65535));
		
		AuthRequest resp = (AuthRequest)CodecManager.decode(x, AuthRequest.class);
		Log.e("test", resp.toString());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
