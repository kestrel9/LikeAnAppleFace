package com.atomickitten.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YANG-_-V on 2016-11-08.
 */

public class AppDBHelper extends SQLiteOpenHelper {
    //All Static variables
    //DB Version here
    private static final int DATABASE_VERSION = 1;

    //DB Name Here
    private static final String DATABASE_NAME = "applefaceManager";

    //table name Here
    private static final String TABLE_PRODUCT = "tb_product";

    //product table Colums names
    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "product_name";
    private static final String KEY_PIC_URI = "pic_uri";
    private static final String KEY_CATEGORY_FIRST = "product_category_first";
    private static final String KEY_CATEGORY_SECOND = "product_category_second";
    private static final String KEY_ENABLE = "enable";
    private static final String KEY_REGISTER_DATE = "register_date";
    private static final String KEY_EXPIRE_DATE = "expire_date";

    public AppDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT NOT NULL,"
                + KEY_PIC_URI + " TEXT, "
                + KEY_CATEGORY_FIRST + " TEXT NOT NULL,"
                + KEY_CATEGORY_SECOND + " TEXT, "
                + KEY_ENABLE + " INTEGER NOT NULL DEFAULT 1, "
                + KEY_REGISTER_DATE + " TIMESTAMP DEFAULT (datetime('now','localtime')), "
                + KEY_EXPIRE_DATE + " TEXT"
                + ")";
        db.execSQL(CREATE_PRODUCT_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);

        // Create tables again
        onCreate(db);
    }

    public void addItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.getProduct_name());
        values.put(KEY_PIC_URI, item.getPic_uri());
        values.put(KEY_CATEGORY_FIRST, item.getProduct_category_first());
        values.put(KEY_CATEGORY_SECOND, item.getProduct_category_second());
        values.put(KEY_EXPIRE_DATE, item.getExpire_date());

        try{
            db.beginTransaction();
            db.insert(TABLE_PRODUCT, null, values);
            db.setTransactionSuccessful();
        }catch (SQLException e){

        }finally {
            db.endTransaction();
            db.close();// Closing database connection

        }

    }

    public Item getItem(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUCT, new String[] { KEY_ID,
                        KEY_NAME, KEY_PIC_URI, KEY_CATEGORY_FIRST, KEY_CATEGORY_SECOND, KEY_EXPIRE_DATE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Item item = new Item(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),cursor.getString(5));
        // return item
        return item;

    }

    public List<Item> getAllItems(){
        List<Item> itemList = new ArrayList<Item>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT + " ORDER BY "+ KEY_REGISTER_DATE +" DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.set_id(Integer.parseInt(cursor.getString(0)));
                item.setProduct_name(cursor.getString(1));
                item.setPic_uri(cursor.getString(2));
                item.setProduct_category_first(cursor.getString(3));
                item.setProduct_category_second(cursor.getString(4));
                item.setEnable(Integer.parseInt(cursor.getString(5)));
                item.setRegister_date(cursor.getString(6));
                item.setExpire_date(cursor.getString(7));

                // Adding item to list
                itemList.add(item);
            } while (cursor.moveToNext());
        }

        // return item list
        return itemList;

    }


    public List<Item>getAllItems(String category){
        List<Item> itemList = new ArrayList<Item>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT + " WHERE " + KEY_CATEGORY_FIRST + " = '" + category + "' ORDER BY "+ KEY_REGISTER_DATE +" DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.set_id(Integer.parseInt(cursor.getString(0)));
                item.setProduct_name(cursor.getString(1));
                item.setPic_uri(cursor.getString(2));
                item.setProduct_category_first(cursor.getString(3));
                item.setProduct_category_second(cursor.getString(4));
                item.setEnable(Integer.parseInt(cursor.getString(5)));
                item.setRegister_date(cursor.getString(6));
                item.setExpire_date(cursor.getString(7));

                // Adding item to list
                itemList.add(item);
            } while (cursor.moveToNext());
        }

        // return item list
        return itemList;
    }

    public List<Item>getAllItemsOrderByExpireDate(String category){
        List<Item> itemList = new ArrayList<Item>();
        // Select All Query
        String selectQuery;
        if(category.equals(null)){
            selectQuery = "SELECT  * FROM " + TABLE_PRODUCT + " ORDER BY "+ KEY_EXPIRE_DATE +" ASC";
        }else {
            selectQuery = "SELECT  * FROM " + TABLE_PRODUCT + " WHERE " + KEY_CATEGORY_FIRST + " = '" + category + "' ORDER BY "+ KEY_EXPIRE_DATE +" ASC";
        }

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.set_id(Integer.parseInt(cursor.getString(0)));
                item.setProduct_name(cursor.getString(1));
                item.setPic_uri(cursor.getString(2));
                item.setProduct_category_first(cursor.getString(3));
                item.setProduct_category_second(cursor.getString(4));
                item.setEnable(Integer.parseInt(cursor.getString(5)));
                item.setRegister_date(cursor.getString(6));
                item.setExpire_date(cursor.getString(7));

                // Adding item to list
                itemList.add(item);
            } while (cursor.moveToNext());
        }

        // return item list
        return itemList;
    }

    public int getItemsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PRODUCT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();

    }

    public int updateItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.getProduct_name());
        values.put(KEY_PIC_URI, item.getPic_uri());
        values.put(KEY_CATEGORY_FIRST, item.getProduct_category_first());
        values.put(KEY_CATEGORY_SECOND, item.getProduct_category_second());
        values.put(KEY_EXPIRE_DATE, item.getExpire_date());

        // updating row
        return db.update(TABLE_PRODUCT, values, KEY_ID + " = ?",
                new String[] { String.valueOf(item.get_id()) });

    }

    public void deleteItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        try{
            db.beginTransaction();
            db.delete(TABLE_PRODUCT, KEY_ID + " = ?",
                    new String[] { String.valueOf(item.get_id()) });
            db.setTransactionSuccessful();
        }catch (SQLException e){

        }finally {
            db.endTransaction();
            db.close();// Closing database connection

        }

    }
}
