package com.example.nfcscan;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if(id == R.id.reader){
			Intent read=new Intent(this, nfcreader.class);
			this.startActivity(read);
		}else if(id == R.id.writer){
			Intent write=new Intent(this, nfcwriter.class);
			this.startActivity(write);
		}else if(id == R.id.exit){
			System.exit(0);
		}
		return super.onOptionsItemSelected(item);
	}
}
