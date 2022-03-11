package com.PisangHitam.InstaFashion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.PisangHitam.InstaFashion.classProduk
import com.squareup.picasso.Picasso

class recycler_products_adapter(data : List<classProduk>,
                                private val clickListener: (classProduk) -> Unit) : RecyclerView.Adapter<recycler_products_adapter.myHolder>() {

    private var myData = data
    class myHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val nama: TextView = itemView.findViewById<TextView>(R.id.title)
        val harga: TextView = itemView.findViewById<TextView>(R.id.price)
        val gambar: ImageView = itemView.findViewById<ImageView>(R.id.photo)
        val area : CardView = itemView.findViewById<CardView>(R.id.areaitem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myHolder {
        val inflate = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_products,parent,false)
        return myHolder(inflate)
    }

    override fun onBindViewHolder(holder: myHolder, position: Int) {
        holder.nama.setText(myData.get(position).namaProduk)
        holder.harga.setText("Rp." + singletonData.formatHarga(myData.get(position).hargaProduk))
        Picasso.get().load(myData[position].urlGambarProduk).into(holder.gambar)
        holder.itemView.setOnClickListener{ v ->
            clickListener(myData[position])
        }
    }

    override fun getItemCount(): Int = myData.size

}