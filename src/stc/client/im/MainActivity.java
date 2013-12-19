package stc.client.im;

import stc.bean.AuthRequest;
import stc.foundation.codec.CodecHelper;
import stc.foundation.codec.bean.tlv.annotation.TLVAttribute;
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
		req.setToken("123456");
		req.setSource("ssss");
		
		byte[] buf = CodecHelper.encodeSsip(req);
		Log.e("test1", ByteUtils.bytesAsHexString(buf, 1024));		
		
		Log.e("test2", ByteUtils.bytesAsHexString(CodecHelper.encodeSsip(CodecHelper.decodeSsip(buf)), 1024));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
