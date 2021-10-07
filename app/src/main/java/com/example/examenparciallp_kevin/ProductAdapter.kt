package com.example.examenparciallp_kevin

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter: RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){
    private var prodList : ArrayList<ProductModel> = ArrayList()
    private var onClickItem : ((ProductModel)-> Unit)? = null
    private var onClickDeleteItem :  ((ProductModel)-> Unit)? = null

    fun addItems(items : ArrayList<ProductModel>){
        this.prodList = items
        notifyDataSetChanged()
    }
    fun setOnClickItem(callback: (ProductModel) -> Unit){
        this.onClickItem = callback
    }
    fun setOnClickDeleteItem(callback: (ProductModel) -> Unit){
        this.onClickDeleteItem = callback
    }


    class ProductViewHolder(var view: View) : RecyclerView.ViewHolder(view){
        private var nomprod = view.findViewById<TextView>(R.id.tvName)
        private var price = view.findViewById<TextView>(R.id.tvPrice)
        private var stock = view.findViewById<TextView>(R.id.tvStock)
        private var category = view.findViewById<TextView>(R.id.tvCategory)
        var btnDelete = view.findViewById<Button>(R.id.btnDelete)


        fun bindView(prod: ProductModel){
            nomprod.text = prod.nomprod
            price.text = prod.price.toString()
            stock.text = prod.stock.toString()
            category.text = prod.category


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_prod,parent, false)
    )

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
       val prod = prodList[position]
        holder.bindView(prod)
        holder.itemView.setOnClickListener { onClickItem?.invoke(prod)  }
        holder.btnDelete.setOnClickListener { onClickDeleteItem?.invoke(prod) }
    }

    override fun getItemCount(): Int {
        return prodList.size
    }


}