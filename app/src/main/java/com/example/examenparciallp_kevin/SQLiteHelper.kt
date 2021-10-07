package com.example.examenparciallp_kevin

import android.content.*;
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper;
import java.lang.Exception
class SQLiteHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object {
        private const val DATABASE_VERSION = 2;
        private const val DATABASE_NAME = "ventas.db";
        private const val TB_Producto = "producto";
        private const val IDP = "idproducto";
        private const val NOMPROD = "nomprod";
        private const val PRICE = "price";
        private const val STOCK = "stock";
        private const val CATEGORY = "category";
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTbProducto =
            ("CREATE TABLE  $TB_Producto( $IDP INTEGER PRIMARY KEY,$NOMPROD TEXT, $PRICE REAL, $STOCK INTEGER, $CATEGORY TEXT)")
        db?.execSQL(createTbProducto)

    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TB_Producto")
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS $TB_Producto")
        onCreate(db)
    }

    fun insertProducto(product: ProductModel): Long {
        val db = this.writableDatabase;
        val contentValues = ContentValues()
        contentValues.put(NOMPROD, product.nomprod)
        contentValues.put(PRICE, product.price)
        contentValues.put(STOCK, product.stock)
        contentValues.put(CATEGORY, product.category)

        val success = db.insert(TB_Producto, null, contentValues)
        db.close()
        return success
    }

    fun getAllProductos(): ArrayList<ProductModel> {
        val db = this.readableDatabase
        val productList: ArrayList<ProductModel> = ArrayList();
        val cursor: Cursor?
        val selectQuery = "SELECT * FROM  $TB_Producto";

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery);
            return ArrayList();
        }
        var id : Int
        var nomprod : String
        var price : Double
        var stock : Double
        var category : String
        if (cursor.moveToFirst()) {
            do {
                  id = cursor.getInt(cursor.getColumnIndex("idproducto"))
                  nomprod   = cursor.getString(cursor.getColumnIndex("nomprod"))
                price   = cursor.getDouble(cursor.getColumnIndex("price"))
                stock   = cursor.getDouble(cursor.getColumnIndex("stock"))
                category   = cursor.getString(cursor.getColumnIndex("category"))
                val prod = ProductModel(id = id, nomprod = nomprod, price = price, stock = stock, category = category )
                productList.add(prod)
            } while (cursor.moveToNext())
        }
        db.close()
        return productList
    }

    fun updateProducto(product: ProductModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NOMPROD, product.nomprod)
        contentValues.put(PRICE, product.price)
        contentValues.put(STOCK, product.stock)
        contentValues.put(CATEGORY, product.category)
        val successCode = db.update(TB_Producto, contentValues, "$IDP = ${product.id}", null)
        db.close()
        return successCode
    }
    fun deleteProducto(idproducto:Int):Int{
        val db = this.writableDatabase
        val contentValue = ContentValues();
        contentValue.put(IDP,idproducto)

        val success = db.delete(TB_Producto,"$IDP = $idproducto",null)
        db.close()
        return success
    }



}