	package com.kitap.sqllitedatabase;
	
	import java.util.ArrayList;
	import java.util.HashMap;
	import com.kitap.sqlliteexample.R;
	import android.os.Bundle;
	import android.app.ActionBar;
	import android.app.Activity;
	import android.content.Intent;
	import android.view.Menu;
	import android.view.MenuItem;
	import android.view.View;
	import android.widget.AdapterView;
	import android.widget.ArrayAdapter;
	import android.widget.ListView;
	import android.widget.Toast;
	
	public class MainActivity extends Activity {
	
		ListView lv;
		ArrayAdapter<String> adapter;	
		ArrayList<HashMap<String, String>> kitap_liste;
		String kitap_adlari[];
		int kitap_idler[];
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			
			 ActionBar actionBar = getActionBar();
		     actionBar.setDisplayHomeAsUpEnabled(false);
		}
		
		public void onResume()
	    {   //neden onResume metodu kulland���m� ders i�inde anlatt�m.
	    super.onResume();
	    Database db = new Database(getApplicationContext()); // Db ba�lant�s� olu�turuyoruz. �lk seferde database olu�turulur.
	    kitap_liste = db.kitaplar();//kitap listesini al�yoruz
	    if(kitap_liste.size()==0){//kitap listesi bo�sa
	   	 Toast.makeText(getApplicationContext(), "Hen�z Kitap Eklenmemi�.\nYukar�daki + Butonundan Ekleyiniz", Toast.LENGTH_LONG).show();
	    }else{
		     kitap_adlari = new String[kitap_liste.size()]; // kitap adlar�n� tutucam�z string arrayi olusturduk.
		     kitap_idler = new int[kitap_liste.size()]; // kitap id lerini tutucam�z string arrayi olusturduk.
		     for(int i=0;i<kitap_liste.size();i++){
		    	 kitap_adlari[i] = kitap_liste.get(i).get("kitap_adi"); 
		    	 //kitap_liste.get(0) bize arraylist i�indeki ilk hashmap arrayini d�ner. Yani tablomuzdaki ilk sat�r de�erlerini
		    	 //kitap_liste.get(0).get("kitap_adi") //bize arraylist i�indeki ilk hashmap arrayin anahtar� kitap_adi olan value d�ner
		    	 
		    	 kitap_idler[i] = Integer.parseInt(kitap_liste.get(i).get("id"));
		    	//Yukar�daki ile ayn� tek fark� de�erleri integer a �evirdik.
		     }
		     //Kitaplar� Listeliyoruz ve bu listeye listener at�yoruz
		     lv = (ListView) findViewById(R.id.list_view);
			    
		     adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.kitap_adi, kitap_adlari);
		     lv.setAdapter(adapter);
		     
		     lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		 		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		 				long arg3) {
		 			//Listedeki her hangibir yere t�kland�g�nda t�klanan sat�r�n s�ras�n� al�yoruz.
		 			//Bu s�ra id arraydeki s�rayla ayn� oldugundan t�klanan sat�rda bulunan kitap�n id sini al�yor ve kitap detaya g�nderiyoruz.
		 			 Intent intent = new Intent(getApplicationContext(), KitapDetay.class);
		 			 intent.putExtra("id", (int)kitap_idler[arg2]);
		             startActivity(intent);
		 			
		 		}
		     });
	    }
	    
	    }
	
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			
			return super.onCreateOptionsMenu(menu);
		}
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    // Handle presses on the action bar items
		    switch (item.getItemId()) {
		        case R.id.ekle:
		        	KitapEkle();
		            return true;
		        default:
		            return super.onOptionsItemSelected(item);
		    }
		}
	
		 private void KitapEkle() {
		        Intent i = new Intent(MainActivity.this, KitapEkle.class);
		        startActivity(i);
		    }
	}
