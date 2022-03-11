package com.PisangHitam.InstaFashion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.PisangHitam.InstaFashion.classItemBasket
import com.squareup.picasso.Picasso

class recycler_shoptransaction_productlist_adapter(data : List<classItemBasket>) : RecyclerView.Adapter<recycler_shoptransaction_productlist_adapter.myHolder>(){

    private var myData = data
    class myHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var img = itemView.findViewById<ImageView>(R.id.photo)
        var name = itemView.findViewById<TextView>(R.id.name)
        var price = itemView.findViewById<TextView>(R.id.price)
        var quantity = itemView.findViewById<TextView>(R.id.QuantityItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recycler_shoptransaction_productlist_adapter.myHolder {
        val inflate = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_tracker_productlist,parent,false)
        return recycler_shoptransaction_productlist_adapter.myHolder(inflate)
    }

    override fun onBindViewHolder(holder: recycler_shoptransaction_productlist_adapter.myHolder, position: Int) {
        Picasso.get().load(myData[position].urlGambarProduk).into(holder.img)
        holder.name.setText(myData[position].namaProduk.toString())
        holder.price.setText("Rp. " + singletonData.formatHarga(myData[position].hargaProduk))
        holder.quantity.setText(myData[position].quantity.toString())
    }

    override fun getItemCount(): Int = myData.size
}