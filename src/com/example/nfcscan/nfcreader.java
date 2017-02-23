package com.example.nfcscan;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.nfc.Tag;
import android.nfc.NfcAdapter;
import android.nfc.tech.MifareClassic;
@SuppressLint("NewApi")
public class nfcreader extends Activity {
	TextView txt;
	NfcAdapter nfc;
	StringBuilder str=new StringBuilder();
	int nfcnum=NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_NFC_B | NfcAdapter.FLAG_READER_NFC_BARCODE 
			| NfcAdapter.FLAG_READER_NFC_F | NfcAdapter.FLAG_READER_NFC_V;
	Handler h=new Handler();
	Runnable runs=new Runnable(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			txt.setText(str);
			h.post(this);
		}
	};
	
	public static String bytesToHex(byte[] bytes) {
	    final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	    char[] hexChars = new char[bytes.length * 2];
	    int v;
	    for ( int j = 0; j < bytes.length; j++ ) {
	        v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	NfcAdapter.ReaderCallback nfcread=new NfcAdapter.ReaderCallback(){
		@Override
		public void onTagDiscovered(Tag tag) {
			// TODO Auto-generated method stub
			MifareClassic mif=MifareClassic.get(tag);
		    try {
		       mif.connect();
		       str.append("ID:"+bytesToHex(tag.getId())+"\n"+
						"Size:"+mif.getSize()+"\n"+
						"Sector:"+mif.getSectorCount()+"\n"+
				        "Block:"+mif.getBlockCount()+"\n"
				       );
			  if(mif.isConnected()){
				 str.append("Key Authenticate KeyA || Authenticate KeyB Successfly..."+"\n");
				 for(int i=0;i<mif.getBlockCount();i++){
		        	if(mif.authenticateSectorWithKeyA(mif.blockToSector(i), MifareClassic.KEY_MIFARE_APPLICATION_DIRECTORY)){
		        		 str.append("Key Found "+i+" ["+bytesToHex(mif.readBlock(i))+"]"+"\n");
					}else if(mif.authenticateSectorWithKeyA(mif.blockToSector(i), MifareClassic.KEY_DEFAULT)){
		        	     str.append("Key Found "+i+" ["+bytesToHex(mif.readBlock(i))+"]"+"\n");
					}else if(mif.authenticateSectorWithKeyA(mif.blockToSector(i),MifareClassic.KEY_NFC_FORUM)){
		        		 str.append("Key Found "+i+" ["+bytesToHex(mif.readBlock(i))+"]"+"\n");
					}else if(mif.authenticateSectorWithKeyB(mif.blockToSector(i),MifareClassic.KEY_MIFARE_APPLICATION_DIRECTORY)){
		        		 str.append("Key Found "+i+" ["+bytesToHex(mif.readBlock(i))+"]"+"\n");
					}else if(mif.authenticateSectorWithKeyB(mif.blockToSector(i),MifareClassic.KEY_DEFAULT)){
		        		 str.append("Key Found "+i+" ["+bytesToHex(mif.readBlock(i))+"]"+"\n");
					}else if(mif.authenticateSectorWithKeyB(mif.blockToSector(i),MifareClassic.KEY_NFC_FORUM)){
		        		 str.append("Key Found "+i+" ["+bytesToHex(mif.readBlock(i))+"]"+"\n");
					}
				 }
			  }
			  mif.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				 str.append("Key Authenticate KeyA || Authenticate KeyB Fail..."+"\n");
	    	     str.append("Key Not Found"+"\n");
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nfc_read);
		txt=(TextView)findViewById(R.id.textView1);
	    nfc=NfcAdapter.getDefaultAdapter(this);
	    nfc.enableReaderMode(nfcreader.this, nfcread, nfcnum, savedInstanceState);
	    txt.setText("NFC Wait....");
	    h.post(runs);
	}
}
