package com.kitap.sqllitedatabase;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "sqllite_database";//database adý

	private static final String TABLE_NAME = "kitap_listesi";
	private static String KITAP_ADI = "kitap_adi";
	private static String KITAP_ID = "id";
	private static String KITAP_YAZARI = "yazar";
	private static String KITAP_BASIM_YILI = "yil";
	private static String KITAP_FIYATI = "fiyat";
	
	public Database(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {  // Databesi oluþturuyoruz.Bu methodu biz çaðýrmýyoruz. Databese de obje oluþturduðumuzda otamatik çaðýrýlýyor.
		String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
				+ KITAP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ KITAP_ADI + " TEXT,"
				+ KITAP_YAZARI + " TEXT,"
				+ KITAP_BASIM_YILI + " TEXT,"
				+ KITAP_FIYATI + " TEXT" + ")";
		db.execSQL(CREATE_TABLE);
	}


	

	public void kitapSil(int id){ //id si belli olan row u silmek için
		
		 SQLiteDatabase db = this.getWritableDatabase();
		 db.delete(TABLE_NAME, KITAP_ID + " = ?",
		            new String[] { String.valueOf(id) });
		 db.close();
	}
	
	public void kitapEkle(String kitap_adi, String kitap_yazari,String kitap_basim_yili,String kitap_fiyat) { 
		//kitapEkle methodu ise adý üstünde Databese veri eklemek için 
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KITAP_ADI, kitap_adi);
		values.put(KITAP_YAZARI, kitap_yazari);
		values.put(KITAP_BASIM_YILI, kitap_basim_yili);
		values.put(KITAP_FIYATI, kitap_fiyat);

		db.insert(TABLE_NAME, null, values);
		db.close(); //Database Baðlantýsýný kapattýk*/
	}
	
	
	public HashMap<String, String> kitapDetay(int id){
		//Databeseden id si belli olan row u çekmek için.
		//Bu methodda sadece tek row deðerleri alýnýr.
		
		//HashMap bir çift boyutlu arraydir.anahtar-deðer ikililerini bir arada tutmak için tasarlanmýþtýr.
		//mesala map.put("x","300"); mesala burda anahtar x deðeri 300.
		
		HashMap<String,String> kitap = new HashMap<String,String>();
		String selectQuery = "SELECT * FROM " + TABLE_NAME+ " WHERE id="+id;
		 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
        	kitap.put(KITAP_ADI, cursor.getString(1));
        	kitap.put(KITAP_YAZARI, cursor.getString(2));
        	kitap.put(KITAP_BASIM_YILI, cursor.getString(3));
        	kitap.put(KITAP_FIYATI, cursor.getString(4));        	
        }
        cursor.close();
        db.close();
		// return kitap
		return kitap;
	}
	
	public  ArrayList<HashMap<String, String>> kitaplar(){
		
		//Bu methodda ise tablodaki tüm deðerleri alýyoruz
		//ArrayList adý üstünde Array lerin listelendiði bir Array.Burda hashmapleri listeleyeceðiz
		//Herbir satýrý deðer ve value ile hashmap a atýyoruz. Her bir satýr 1 tane hashmap arrayý demek.
		//olusturdugumuz tüm hashmapleri ArrayList e atýp geri dönüyoruz(return).
		
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		Cursor cursor = db.rawQuery(selectQuery, null);
	    ArrayList<HashMap<String, String>> kitaplist = new ArrayList<HashMap<String, String>>();
	    
	    if (cursor.moveToFirst()) {
	        do {
	            HashMap<String, String> map = new HashMap<String, String>();
	            for(int i=0; i<cursor.getColumnCount();i++)
	            {
	                map.put(cursor.getColumnName(i), cursor.getString(i));
	            }
	
	            kitaplist.add(map);
	        } while (cursor.moveToNext());
	    }
	    db.close();
	    // return kitap liste
	    return kitaplist;
	}
	
	public void kitapDuzenle(String kitap_adi, String kitap_yazari,String kitap_basim_yili,String kitap_fiyat,int id) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    //Bu methodda ise var olan veriyi güncelliyoruz(update)
	    ContentValues values = new ContentValues();
	    values.put(KITAP_ADI, kitap_adi);
		values.put(KITAP_YAZARI, kitap_yazari);
		values.put(KITAP_BASIM_YILI, kitap_basim_yili);
		values.put(KITAP_FIYATI, kitap_fiyat);
	 
	    // updating row
	    db.update(TABLE_NAME, values, KITAP_ID + " = ?",
	            new String[] { String.valueOf(id) });
	}
	
	public int getRowCount() { 
		// Bu method bu uygulamada kullanýlmýyor ama her zaman lazým olabilir.Tablodaki row sayýsýný geri döner.
		//Login uygulamasýnda kullanacaðýz
		String countQuery = "SELECT  * FROM " + TABLE_NAME;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		db.close();
		cursor.close();
		// return row count
		return rowCount;
	}
	

	public void resetTables(){ 
		//Bunuda uygulamada kullanmýyoruz. Tüm verileri siler. tabloyu resetler.
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, null, null);
		db.close();
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
