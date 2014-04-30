package omniSpectrum.fridgeassistant.Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import omniSpectrum.fridgeassistant.Models.Category;
import omniSpectrum.fridgeassistant.Models.Image;
import omniSpectrum.fridgeassistant.Models.ItemDefinition;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
	private static final String KEY_NAME = "name";
	private static final String FK_ITEMSDEFINITIONID = "itemId";
	
	//Category column names
	private static final String FK_CATEGORY_IMAGEID = "imgId";
	
	//Columns Image names
	private static final String KEY_PATH = "paths";
	
	//Column itemDefinitions
	public static final String KEY_BALANCE = "balance";
	public static final String FK_CATEGORYID = "categoryId";
	
	//Columns groceryItems
	private static final String KEY_QUANTITYTOBUY = "quantityToBuy";
	
	//Columns inventoryTransactions
	public static final String KEY_TRANSTYPES = "transTypes";
	public static final String KEY_QUANTITYCHANGES = "quantityChanges";
	
	//Table create statements
	//Image table create
	public static final String CREATE_TABLE_IMAGE = "CREATE TABLE " + TABLE_IMAGE 
					+ "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
					+ KEY_PATH + " TEXT" + ")";
	
	//Create table category
	public static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY 
			+ "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			KEY_NAME + " TEXT, "  + FK_CATEGORY_IMAGEID + " INTEGER, " + "FOREIGN KEY" + "(" + FK_CATEGORY_IMAGEID + ")" +
			" REFERENCES " + TABLE_IMAGE + "(" + KEY_ID + ")" + ")";
	
	//Create table ItemDefinitions
	public static final String CREATE_TABLE_ITEMSDEFINITIONS = "CREATE TABLE " + TABLE_ITEMDEFINITION 
			+ "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			KEY_NAME + " TEXT, " + KEY_BALANCE + " INTEGER, "
			+ FK_CATEGORYID + " INTEGER, " + "FOREIGN KEY" + "(" + FK_CATEGORYID + ")" +
			" REFERENCES " + TABLE_CATEGORY + "(" + KEY_ID + ")" +")";
	
	//Create table groceryItems
	public static final String CREATE_TABLE_GROCERYITEMS = "CREATE TABLE " + TABLE_GROCERYITEM 
			+ "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
			KEY_QUANTITYTOBUY + " INTEGER, " + FK_ITEMSDEFINITIONID + " INTEGER, " + "FOREIGN KEY" + "(" + FK_ITEMSDEFINITIONID + ")" +
			" REFERENCES " + TABLE_ITEMDEFINITION + "(" + KEY_ID + ")" + ")"; 
	
	//Create table inventoryTransactions
	public static final String CREATE_TABLE_INVENTORYTRANSACTIONS = "CREATE TABLE " + TABLE_INVENTORYTRANSACTIONS 
			+ "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ KEY_TRANSTYPES + " TEXT, " + KEY_QUANTITYCHANGES + " INTEGER, " + FK_ITEMSDEFINITIONID + " INTEGER, " +
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
	
	// Image handle
	public long createImage(Image image){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_PATH, image.getPath());
		
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
		
		c.close();
		return images;
	}
	public Image getSingleImage(int id){
		Image image = null;
		String selectQuery = "SELECT * FROM " + TABLE_IMAGE 
				+ " WHERE " + KEY_ID + " = ?";
		
		Log.e(log, selectQuery);
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, new String[] { String.valueOf(id) });
		
		if (c.moveToFirst()) {
			image = new Image();
			image.setId(c.getInt(c.getColumnIndex(KEY_ID)));
			image.setPath(c.getString(c.getColumnIndex(KEY_PATH)));
		}
		
		c.close();
		return image;
	}
	
	//Category handle
	public long createCategory(Category cat){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, cat.getName());
		values.put(FK_CATEGORY_IMAGEID, cat.getImgId().getId());
		
		long cat_id = db.insert(TABLE_CATEGORY, null, values);
		
		return cat_id;
	}
	public List<Category> getAllCategories(){
		List<Category> categories = new ArrayList<Category>();
		String selectQuery = "SELECT C.*, I." + KEY_PATH
						+ " FROM " + TABLE_CATEGORY + " C"
						+ " JOIN " + TABLE_IMAGE + " I"
						+ " ON C." + FK_CATEGORY_IMAGEID + " = I." + KEY_ID;
		
		Log.e(log, selectQuery);
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if (c.moveToFirst()) {			
			do {
				Category cat = new Category();
				cat.setId(c.getInt(c.getColumnIndex(KEY_ID)));
				cat.setName(c.getString(c.getColumnIndex(KEY_NAME)));
				cat.setImgId(new Image(
						c.getInt(c.getColumnIndex(FK_CATEGORY_IMAGEID)),
						c.getString(c.getColumnIndex(KEY_PATH))));
				
				categories.add(cat);
			} while (c.moveToNext());
		}
		c.close();
		return categories;
	}

	//ItemDefinitions handle
	public long createItemDefinition(ItemDefinition item){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, item.getItemId());
		values.put(KEY_BALANCE, item.getBalance());
		values.put(FK_CATEGORYID, item.getItemCategory().getName());
		
		long cat_id = db.insert(TABLE_CATEGORY, null, values);
		
		return cat_id;
	}
	public List<ItemDefinition> getAllItemDefenitions(){		
		List<ItemDefinition> items = new ArrayList<ItemDefinition>();
		String selectQuery = "SELECT IT."+KEY_ID+", IT."+KEY_NAME + ","
						+ " C."+KEY_ID+", C."+KEY_NAME + ","
						+ " IM."+KEY_ID+", IM."+KEY_PATH
						+ " FROM " + TABLE_ITEMDEFINITION
						+ " JOIN " + TABLE_CATEGORY + " C ON"
						+ " IT." + FK_CATEGORYID + " = " + "C." + KEY_ID
						+ " JOIN " + TABLE_IMAGE + " IM ON"
						+ " C." + FK_CATEGORY_IMAGEID + " = " + "IM." + KEY_ID;		
//		String selectQuery = "SELECT * FROM " + TABLE_ITEMDEFINITION;
		Log.e(log, selectQuery);
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if (c.moveToFirst()) {			
			do {
				ItemDefinition i = new ItemDefinition();
				
				i.setItemId(c.getInt(c.getColumnIndex("IT."+KEY_ID)));
				i.setName(c.getString(c.getColumnIndex("IT."+KEY_NAME)));
				i.setBalance(c.getInt(c.getColumnIndex("IT."+KEY_BALANCE)));
				
				i.setItemCategory(new Category(
							c.getInt(c.getColumnIndex("C."+KEY_ID)),
							c.getString(c.getColumnIndex("C."+KEY_NAME)),
							new Image(
									c.getInt(c.getColumnIndex("IM."+KEY_ID)),
									c.getString(c.getColumnIndex("IM."+KEY_PATH))
								)
						));
				
//				i.setItemId(c.getInt(c.getColumnIndex(KEY_ID)));
//				i.setName(c.getString(c.getColumnIndex(KEY_NAME)));
//				i.setBalance(c.getInt(c.getColumnIndex(KEY_BALANCE)));
				
				Log.e(log, i.toString());
				items.add(i);
			} while (c.moveToNext());
		}
		c.close();
		return items;
	}
	
	public void populateTestData(){
		
		Image img1 = new Image("ic_launcher.png");
		img1.setId((int) createImage(img1));
		
		Category cat1 = new Category("Drinks", img1);
		cat1.setId((int) createCategory(cat1));
		
		Random myDummyAmount = new Random();
		ItemDefinition it1 = new ItemDefinition(cat1, "Milk");
		it1.setBalance(myDummyAmount.nextInt(20));
		ItemDefinition it2 = new ItemDefinition(cat1, "Cola");
		it2.setBalance(myDummyAmount.nextInt(20));
		ItemDefinition it3 = new ItemDefinition(cat1, "Beer");
		it3.setBalance(myDummyAmount.nextInt(20));
		
		createItemDefinition(it1);
		createItemDefinition(it2);
		createItemDefinition(it3);
	}
	
	public void closeDb(){
		SQLiteDatabase db = this.getReadableDatabase();
		if(db != null && db.isOpen()){
			db.close();
		}
	}
}
