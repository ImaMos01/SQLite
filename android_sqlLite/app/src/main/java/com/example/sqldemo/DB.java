package com.example.sqldemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.contentcapture.DataShareWriteAdapter;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DB extends SQLiteOpenHelper {

    public static final String CUSTOMER_TABLE = "CUSTOMER_TABE";
    public static final String COLUMN_CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String COLUMN_CUSTOMER_AGE = "CUSTOMER_AGE";
    public static final String COLUMN_ACTIVE_CUSTOMER = "ACTIVE_CUSTOMER";
    public static final String COLUMN_ID = "ID";

    public DB(@Nullable Context context) {
        super(context,"customer.db", null, 1);
    }

    //la primera vez que accedemos a la base de datos, debe haber un codigo para crear un nuevo base de datos
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement= "CREATE TABLE " + CUSTOMER_TABLE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CUSTOMER_NAME + " TEXT," + COLUMN_CUSTOMER_AGE + " INT, " + COLUMN_ACTIVE_CUSTOMER + " BOOL)";
        sqLiteDatabase.execSQL(createTableStatement);
    }

    //lamamos si la base de datos es cambiada
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(CustomerModel customerModel){
        SQLiteDatabase Db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CUSTOMER_NAME, customerModel.getName());
        cv.put(COLUMN_CUSTOMER_AGE, customerModel.getAge());
        cv.put(COLUMN_ACTIVE_CUSTOMER, customerModel.isActive());
        cv.put(COLUMN_CUSTOMER_NAME, customerModel.getName());

        long insert = Db.insert(CUSTOMER_TABLE, null, cv);
        if(insert == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean deleteOne(CustomerModel customerModel){
        SQLiteDatabase Db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + CUSTOMER_TABLE + " WHERE "+COLUMN_ID + " = "+customerModel.getId();
        Cursor cursor = Db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }
    }

    public List<CustomerModel> getEveryone(){
        List<CustomerModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + CUSTOMER_TABLE;
        SQLiteDatabase Db = this.getReadableDatabase();

        Cursor cursor = Db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                int customerID = cursor.getInt(0);
                String customerName =  cursor.getString(1);
                int customerAge = cursor.getInt(2);
                boolean customerActive = cursor.getInt(3) == 1 ? true: false;

                CustomerModel newCustomer = new CustomerModel(customerID, customerName, customerAge, customerActive);
                returnList.add(newCustomer);

            } while(cursor.moveToNext());
        }
        else {

        }
        cursor.close();
        Db.close();
        return returnList;
    }
}
