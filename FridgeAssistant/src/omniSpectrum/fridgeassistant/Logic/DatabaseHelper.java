package omniSpectrum.fridgeassistant.Logic;

import java.util.ArrayList;
import java.util.List;

import omniSpectrum.fridgeassistant.Models.Image;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	//Logcat Tag
	private static final String log = "DatabaseHelper";
	
	//Database Version
	private static final int DATABASE_VERSION = 1;
	
	//Database name
	private static final String DATABASE_NAME = "fridgeAssistantDB";
	
	//Table names
	private static final String TABLE_IMAGE = "images";
	private static final String TABLE_CATEGORY = "categories";
	private static final String TABLE_ITEMDEFINITION = "itemDefinitions";
	private static final String TABLE_GROCERYITEM = "groceryItems";
	private static final String TABLE_INVENTORYTRANSACTIONS = "inventoryTransactions";
	
	//Common column names
	private static final String KEY_ID = "id";
	private static final String KEY_NAMES = "names";
	private static final String FK_ITEMSDEFINITIONID = "itemId";
	
	//Category column names
	private static final String FK_CATEGORY_IMAGEID = "imgId";
	
	//Columns Image names
	private static final String KEY_PATH = "paths";
	
	//Column itemDefinitions
	public static final String FK_CATEGORYID = "categoryId";
	
	
	//Columns groceryItems
	private static final String KEY_QUANTITYTOBUY = "quantityToBuy";
	
	
	//Columns inventoryTransactions
	public static final String KEY_TRANSTYPES = "transTypes";
	public static final String KEY_QUANTITYCHANGES = "quantityChanges";
	
	//Table create statements
	//Image table create
	public static final String CREATE_TABLE_IMAGE = "CREATE TABLE " + TABLE_IMAGE + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + 
	KEY_PATH + " TEXT" + ")";
	
	//Create table category
	public static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY + "(" + KEY_ID + " INTEGER PRIMARY KEY, " +
			KEY_NAMES + " TEXT, "  + FK_CATEGORY_IMAGEID + " INTEGER, " + "FOREIGN KEY" + "(" + FK_CATEGORY_IMAGEID + ")" +
			" REFERENCES " + TABLE_IMAGE + "(" + KEY_ID + ")" + ")";
	
	//Create table ItemDefinitions
	public static final String CREATE_TABLE_ITEMSDEFINITIONS = "CREATE TABLE " + TABLE_ITEMDEFINITION + "(" + KEY_ID + " INTEGER PRIMARY KEY, " +
			KEY_NAMES + " TEXT, " + FK_CATEGORYID + " INTEGER, " + "FOREIGN KEY" + "(" + FK_CATEGORYID + ")" +
			" REFERENCES " + TABLE_CATEGORY + "(" + KEY_ID + ")" +")";
	
	//Create table groceryItems
	public static final String CREATE_TABLE_GROCERYITEMS = "CREATE TABLE " + TABLE_GROCERYITEM + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + 
			KEY_QUANTITYTOBUY + " INTEGER, " + FK_ITEMSDEFINITIONID + " INTEGER, " + "FOREIGN KEY" + "(" + FK_ITEMSDEFINITIONID + ")" +
			" REFERENCES " + TABLE_ITEMDEFINITION + "(" + KEY_ID + ")" + ")"; 
	
	//Create table inventoryTransactions
	public static final String CREATE_TABLE_INVENTORYTRANSACTIONS = "CREATE TABLE " + TABLE_INVENTORYTRANSACTIONS + "(" + KEY_ID +
			" INTEGER PRIMARY KEY, " + KEY_TRANSTYPES + " TEXT, " + KEY_QUANTITYCHANGES + " INTEGER, " + FK_ITEMSDEFINITIONID + " INTEGER, " +
			"FOREIGN KEY " + "(" + FK_ITEMSDEFINITIONID + ")" + " REFERENCES " + TABLE_ITEMDEFINITION + "(" + KEY_ID + ")" + ")";
			 
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_IMAGE);
		db.execSQL(CREATE_TABLE_CATEGORY);
		db.execSQL(CREATE_TABLE_ITEMSDEFINITIONS);
		db.execSQL(CREATE_TABLE_GROCERYITEMS);
		db.execSQL(CREATE_TABLE_INVENTORYTRANSACTIONS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVENTORYTRANSACTIONS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROCERYITEM);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMDEFINITION);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGE);
		
		//Create tables
		onCreate(db);
	}
	
	public long createImage(Image image){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_PATH, "Sample");
		
		long image_id = db.insert(TABLE_IMAGE, null, values);
		
		return image_id;
	}
	
	public List<Image> getAllImages(){
		List<Image> images = new ArrayList<Image>();
		String selectQuery = "SELECT * FROM " + TABLE_IMAGE;
		
		Log.e(log, selectQuery);
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if (c.moveToFirst()) {
			do {
				Image i = new Image();
				i.setId(c.getInt(c.getColumnIndex(KEY_ID)));
				i.setPath(c.getString(c.getColumnIndex(KEY_PATH)));
				
				images.add(i);
			} while (c.moveToNext());
		}
		
		return images;
	}
	
	public void closeDb(){
		SQLiteDatabase db = this.getReadableDatabase();
		if(db != null && db.isOpen()){
			db.close();
		}
	}

}
