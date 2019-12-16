package com.melek.myto_dolist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "sqllite_database";//database adı

    private static final String TABLE_NAME = "to_dos";
    private static String TODO_ID="to_do_id";
    private static String TODO_NAME="to_do";
    private static String TODO_CAT_ID = "category";
    private static String TODO_PRIO_ID = "priority";
    private static String TODO_STATUS="status";

    private static final String CAT_TABLE_NAME="categories";
    private static String CAT_ID="cat_id";
    private static String CAT_NAME="cat_name";

    private static final String PRIO_TABLE_NAME="priority";
    private static String PRIO_ID="prio_id";
    private static String PRIO_NAME="prio_name";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + TODO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TODO_NAME + " TEXT NOT NULL, "
                + TODO_CAT_ID + " INTEGER NOT NULL, "
                + TODO_PRIO_ID + " INTEGER NOT NULL, "
                + TODO_STATUS+" BOOLEAN NOT NULL );";

        String CREATE_CAT_TABLE =  "CREATE TABLE " + CAT_TABLE_NAME + "("
                + CAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CAT_NAME + " TEXT NOT NULL );";
        String CREATE_PRIO_TABLE =  "CREATE TABLE " + PRIO_TABLE_NAME + "("
                + PRIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PRIO_NAME + " TEXT NOT NULL );";
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_CAT_TABLE);
        db.execSQL(CREATE_PRIO_TABLE);


    }
    public void deleteTODO(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, TODO_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }
    public void deleteCategory(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CAT_TABLE_NAME, CAT_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }
    public void deletePriority(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PRIO_TABLE_NAME, PRIO_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }
    public void addTODO(ToDos todo){

        SQLiteDatabase db = this.getWritableDatabase();
      /*  ContentValues values = new ContentValues();
        values.put(TEST_DERS_ADI, dersAdi);
        values.put(TEST_KONU_ADI, konuAdi);
        values.put(TEST_KONU_SS, ss);
        values.put(TEST_KONU_DS, ds);
        values.put(TEST_KONU_YS, ys);
        values.put(TEST_KONU_BS, bs);
        values.put(TEST_KONU_NET, net);
*/
        String sorgu="INSERT INTO "+TABLE_NAME+"("+TODO_NAME+","+TODO_CAT_ID+","+TODO_PRIO_ID+","+TODO_STATUS+") VALUES('"+todo.getToDo()+"','"+todo.getCategory()+"','"+todo.getPriority()+"','"+todo.getStatus()+"')";

        db.execSQL(sorgu);
        /* db.insert(TABLE_NAME, null, values);*/
        db.close(); //Database Bağlantısını kapattık
    }
    public void addCAT(String category){

        SQLiteDatabase db = this.getWritableDatabase();

        String sorgu="INSERT INTO "+CAT_TABLE_NAME+"("+CAT_NAME+") VALUES('"+category+"')";
        db.execSQL(sorgu);
        /* db.insert(TABLE_NAME, null, values);*/
        db.close(); //Database Bağlantısını kapattık
    }
    public void addPRIO(String priority){

        SQLiteDatabase db = this.getWritableDatabase();

        String sorgu="INSERT INTO "+PRIO_TABLE_NAME+"("+PRIO_NAME+") VALUES('"+priority+"')";
        db.execSQL(sorgu);
        /* db.insert(TABLE_NAME, null, values);*/
        db.close(); //Database Bağlantısını kapattık
    }
    public ToDos TODOs(int id){
        //Databeseden id si belli olan row u çekmek için.
        //Bu methodda sadece tek row değerleri alınır.
        ToDos todo = new ToDos();
        String selectQuery = "SELECT * FROM " + TABLE_NAME+ " WHERE id="+id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            todo.setToDo(cursor.getString(1));
            todo.setCategory(cursor.getInt(2));
            todo.setPriority(cursor.getInt(3));
            todo.setStatus(cursor.getInt(4));
          }
        cursor.close();
        db.close();
        // return konu
        return todo;
    }
    public ArrayList<ToDos> todos(){

        //Bu methodda ise tablodaki tüm değerleri alıyoruz
        //olusturdugumuz tüm nesneleri ArrayList e atıp geri dönüyoruz(return).

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME+" WHERE "+TODO_STATUS+"="+0;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<ToDos> toDosArrayList = new ArrayList<ToDos>();

        if (cursor.moveToFirst()) {
            do {
                ToDos td = new ToDos();
                td.setId(cursor.getInt(0));
                td.setToDo(cursor.getString(1));
                td.setCategory(cursor.getInt(2));
                td.setPriority(cursor.getInt(3));
                td.setStatus(cursor.getInt(4));
                toDosArrayList.add(td);
            } while (cursor.moveToNext());
        }
        db.close();

        return toDosArrayList;
    }
    public ArrayList<ToDos> todosDone(){

        //Bu methodda ise tablodaki tüm değerleri alıyoruz
        //olusturdugumuz tüm nesneleri ArrayList e atıp geri dönüyoruz(return).

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME+" WHERE "+TODO_STATUS+"="+1;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<ToDos> toDosArrayList = new ArrayList<ToDos>();

        if (cursor.moveToFirst()) {
            do {
                ToDos td = new ToDos();
                td.setId(cursor.getInt(0));
                td.setToDo(cursor.getString(1));
                td.setCategory(cursor.getInt(2));
                td.setPriority(cursor.getInt(3));
                td.setStatus(cursor.getInt(4));
                toDosArrayList.add(td);
            } while (cursor.moveToNext());
        }
        db.close();

        return toDosArrayList;
    }
    public ArrayList<String> getAllCategories(){

        //Bu methodda ise tablodaki tüm değerleri alıyoruz
        //olusturdugumuz tüm nesneleri ArrayList e atıp geri dönüyoruz(return).

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + CAT_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<String> categoriesArrayList = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            do {
                categoriesArrayList.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        db.close();
        return categoriesArrayList;
    }
    public ArrayList<String> getAllPriorities(){

        //Bu methodda ise tablodaki tüm değerleri alıyoruz
        //olusturdugumuz tüm nesneleri ArrayList e atıp geri dönüyoruz(return).

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + PRIO_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<String> prioritiesArrayList = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            do {
                prioritiesArrayList.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        db.close();
        return prioritiesArrayList;
    }
    public ArrayList<Integer> idler(){
        SQLiteDatabase db=this.getWritableDatabase();
        String selectQuery="SELECT * FROM "+TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<Integer> idler=new ArrayList<Integer>();
        if (cursor.moveToFirst()) {
            do {
                idler.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }
        db.close();
        return idler;
    }
    public void updateToDo(ToDos toDos) {
        SQLiteDatabase db = this.getWritableDatabase();

        String update_query="UPDATE "+TABLE_NAME+" SET "+TODO_NAME+"='"+ toDos.getToDo()+"' ,"+TODO_CAT_ID+"="+toDos.getCategory()+", "+TODO_PRIO_ID+"="+toDos.getPriority()+" ,"
                +TODO_STATUS+"="+toDos.getStatus()+" WHERE "+TODO_ID+"="+toDos.getId();

        db.execSQL(update_query);
        db.close();
    }
    public void updateCat(String cat,int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String update_query="UPDATE "+CAT_TABLE_NAME+" SET "+CAT_NAME+"='"+ cat+"' WHERE "+CAT_ID+"="+id;
        db.execSQL(update_query);
        db.close();
    }
    public void updatePrio(String prio,int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String update_query="UPDATE "+PRIO_TABLE_NAME+" SET "+PRIO_NAME+"='"+ prio+"' WHERE "+PRIO_ID+"="+id;
        db.execSQL(update_query);
        db.close();
    }
    public int getRowCount() {
        // Bu method bu uygulamada kullanılmıyor.Tablodaki row sayısını geri döner.
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        // return row count
        return rowCount;
    }
    public void delete(int id) {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE "+TODO_ID+"="+id);
        //db.delete(TABLE_NAME,"id=?",new String[] { Integer.toString(id) });
    }
    public void resetTables(){
        //Bunuda uygulamada kullanmıyoruz. Tüm verileri siler. tabloyu resetler.
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
