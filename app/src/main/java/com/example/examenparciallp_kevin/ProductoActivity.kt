package com.example.examenparciallp_kevin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProductoActivity : AppCompatActivity() {
    private lateinit var edNomProd : EditText
    private lateinit var edPrice : EditText
    private lateinit var edStock : EditText
    private lateinit var edCategory : EditText
    private lateinit var btnAdd : Button
    private lateinit var btnView : Button
    private lateinit var btnUpdate : Button


    private lateinit var sqliteHelper : SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter : ProductAdapter? = null
    private var prod : ProductModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto)
        initView()
        initRecyclerView()
        sqliteHelper = SQLiteHelper(this)

        btnAdd.setOnClickListener { addProduct() }
        btnView.setOnClickListener { getProducts() }
        adapter?.setOnClickItem {
            Toast.makeText(this, it.id.toString(), Toast.LENGTH_SHORT).show()

            edNomProd.setText(it.nomprod)
            edPrice.setText(it.price.toString())
            edStock.setText(it.stock.toString())
            edCategory.setText(it.category)
            prod = it



        }
        btnUpdate.setOnClickListener {
            updateProduct()
        }
        adapter?.setOnClickDeleteItem {
            deleteProduct(it.id)
        }


    }
    private fun updateProduct(){
        val product = edNomProd.text.toString()
        val price = edPrice.text.toString().toDouble()
        val stock = edStock.text.toString().toDouble()
        val category = edCategory.text.toString()

        if(product == prod?.nomprod && price == prod?.price && stock == prod?.stock && category == prod?.category){
            Toast.makeText(this, "Producto no Actualizado", Toast.LENGTH_SHORT).show()
            return
        }
        if(prod == null) return

        val prod = ProductModel(id = prod!!.id, nomprod =  product, price = price, stock = stock, category = category)
        val status = sqliteHelper.updateProducto(prod)
        if(status >-1 ){
            clearEditText()
            getProducts()
        }else{
            Toast.makeText(this,"Fallo en la actualización", Toast.LENGTH_SHORT).show()
        }



    }



    private fun addProduct(){
        val nomProd = edNomProd.text.toString()
        val  Price = edPrice.text.toString().toDouble()
        val Stock = edStock.text.toString().toDouble()
        val  Category = edCategory.text.toString()
        if(nomProd.isBlank() ||Price.isNaN() || Stock.isNaN() || Category.isBlank() ){
            Toast.makeText(this,  "Por favor, no deje en blanco los inputs", Toast.LENGTH_SHORT).show()
        }else{
            val prod = ProductModel(nomprod = nomProd,price = Price, stock = Stock,  category = Category )
            val status = sqliteHelper.insertProducto(prod)
            if(status > -1){
                Toast.makeText(this, "Producto Añadido", Toast.LENGTH_SHORT).show()
                clearEditText()
                getProducts()
            }else{
                Toast.makeText(this, "Producto NO Añadido", Toast.LENGTH_SHORT).show()

            }
        }


    }
    private fun getProducts(){
        val prodList = sqliteHelper.getAllProductos()
        Log.e("ppp", "${prodList.size}")
        adapter?.addItems(prodList)
    }
    private fun deleteProduct(id : Int) {
        if (id == null) return
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Está seguro de eliminar?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes" ){dialog, _ ->
            sqliteHelper.deleteProducto(id)
            getProducts()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") {dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()

    }

    private fun clearEditText(){
        edNomProd.setText("")
        edPrice.setText("")
        edStock.setText("")
        edCategory.setText("")

    }
    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ProductAdapter()
        recyclerView.adapter =adapter
    }


    private fun initView(){
        edNomProd = findViewById(R.id.edNomProd)
        edPrice = findViewById(R.id.edPrice)
        edStock = findViewById(R.id.edStock)
        edCategory = findViewById(R.id.edCategory)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recyclerView)


    }



}