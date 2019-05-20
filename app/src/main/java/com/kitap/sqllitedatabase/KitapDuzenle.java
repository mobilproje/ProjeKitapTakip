package com.kitap.sqllitedatabase;

import java.util.HashMap;

import com.kitap.sqlliteexample.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class KitapDuzenle extends Activity {
	Button b1;
	EditText e1,e2,e3,e4;
	int id;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kitapduzenle);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("Kitap Detay");
		
		b1 = (Button)findViewById(R.id.button1);
		e1 = (EditText)findViewById(R.id.editText1);
		e2 = (EditText)findViewById(R.id.editText2);
		e3 = (EditText)findViewById(R.id.editText3);
		e4 = (EditText)findViewById(R.id.editText4);
		
		Intent intent=getIntent();
		id = intent.getIntExtra("id", 0);
		
		Database db = new Database(getApplicationContext());
		HashMap<String, String> map = db.kitapDetay(id);
		
		e1.setText(map.get("kitap_adi"));
		e2.setText(map.get("yazar").toString());
		e3.setText(map.get("yil").toString());
		e4.setText(map.get("fiyat").toString());
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				String adi,yazari,yili,fiyati;
				adi = e1.getText().toString();
				yazari = e2.getText().toString();
				yili = e3.getText().toString();
				fiyati = e4.getText().toString();
				if(adi.matches("") || yazari.matches("") || yili.matches("") || fiyati.matches("")  ){
				    Toast.makeText(getApplicationContext(), "T�m Bilgileri Eksiksiz Doldurunuz", Toast.LENGTH_LONG).show();
				}else{
					Database db = new Database(getApplicationContext());
					db.kitapDuzenle(adi, yazari, yili, fiyati,id);//g�nderdi�imiz id li kitab�n de�perlerini g�ncelledik.
					db.close();
				    Toast.makeText(getApplicationContext(), "Kitab�n�z Ba�ar�yla D�zenlendi.", Toast.LENGTH_LONG).show();
				    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
	                startActivity(intent);
	                finish();
				}
				
				
			}
		});
		
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case android.R.id.home:
	        finish();
	        return true;
	    default: return super.onOptionsItemSelected(item);  
	    }
	}


}
