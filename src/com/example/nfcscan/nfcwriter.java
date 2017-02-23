package com.example.nfcscan;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.tech.Ndef;
import android.widget.EditText;
import android.widget.TextView;
public class nfcwriter extends Activity {
	EditText ed;
	TextView txt2;
	Handler h=new Handler();
	StringBuilder str=new StringBuilder();
	@SuppressLint("InlinedApi")
	int nfcnum=NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_NFC_B | NfcAdapter.FLAG_READER_NFC_BARCODE 
			| NfcAdapter.FLAG_READER_NFC_F | NfcAdapter.FLAG_READER_NFC_V;
	Runnable runs=new Runnable(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			txt2.setText(str);
			h.post(this);
		}
	};
	@SuppressLint("NewApi")
	NfcAdapter.ReaderCallback write=new NfcAdapter.ReaderCallback() {
		@Override
		public void onTagDiscovered(Tag tag) {
			// TODO Auto-generated method stub
			Ndef ndef=Ndef.get(tag);
			try {
				ndef.connect();
				if(ndef.isConnected()){
					 NdefRecord record = NdefRecord.createTextRecord("UTF-8", ed.getText().toString());
				     NdefMessage msg = new NdefMessage(new NdefRecord[]{record});
				     ndef.writeNdefMessage(msg);
				     if(ndef.isWritable()){
					     str.append("Write Successfly....."+"\n");
				     }else{
					     str.append("Write Fail....."+"\n");
				     }
				}
				ndef.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				str.append(e.toString());
			}
		}
	};
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nfc_wirte);
		ed=(EditText)findViewById(R.id.editText1);
		txt2=(TextView)findViewById(R.id.textView2);
		NfcAdapter nfc=NfcAdapter.getDefaultAdapter(this);
		nfc.enableReaderMode(this, write, nfcnum, savedInstanceState);
		h.post(runs);
	}
}