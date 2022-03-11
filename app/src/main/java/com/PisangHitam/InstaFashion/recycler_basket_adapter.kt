package com.PisangHitam.InstaFashion

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.PisangHitam.InstaFashion.classItemBasket
import com.squareup.picasso.Picasso

class recycler_basket_adapter(val context : Context,
                              data : MutableList<classItemBasket>,
                              val update: () -> Unit ) : RecyclerView.Adapter<recycler_basket_adapter.myHolder>(){

    private var myData = data
    class myHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val nama: TextView = itemView.findViewById<TextView>(R.id.name)
        val harga: TextView = itemView.findViewById<TextView>(R.id.price)
        val gambar: ImageView = itemView.findViewById<ImageView>(R.id.photo)
        val jumlah: TextView = itemView.findViewById<TextView>(R.id.integer_number)
        val incr: TextView = itemView.findViewById<TextView>(R.id.increase)
        val decr: TextView = itemView.findViewById<TextView>(R.id.decrease)
        val deleter: ImageButton = itemView.findViewById<ImageButton>(R.id.delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recycler_basket_adapter.myHolder {
        val inflate = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_basket,parent,false)
        return recycler_basket_adapter.myHolder(inflate)
    }

    override fun onBindViewHolder(holder: recycler_basket_adapter.myHolder, position: Int) {
        holder.nama.setText(myData.get(position).namaProduk)
        holder.harga.setText("Rp." + singletonData.formatHarga(myData.get(position).hargaProduk))
        Picasso.get().load(myData[position].urlGambarProduk).into(holder.gambar)
        holder.jumlah.setText(myData[position].quantity.toString())

        holder.incr.setOnClickListener{
            val before = holder.jumlah.text.toString().toInt()
            var after = before + 1
            myData[position].quantity = after
            holder.jumlah.setText(myData[position].quantity.toString())
            update()
        }

        holder.decr.setOnClickListener{
            val before = holder.jumlah.text.toString().toInt()
            if(before > 1) {
                var after = before - 1
                myData[position].quantity = after
                holder.jumlah.setText(myData[position].quantity.toString())
                update()
            }
        }

        holder.deleter.setOnClickListener{
            var dialog = AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.deleteFromBasketPrompt))
                .setMessage(context.getString(R.string.confirmDeleteFromBasket, myData[position].namaProduk))
                .setPositiveButton(context.getString(R.string.OK), DialogInterface.OnClickListener{ dialogInterface, i ->
                    myData.removeAt(position)
                    update()
                })
                .setNegativeButton(context.getString(R.string.cancel), DialogInterface.OnClickListener{ dialogInterface, i ->
                })
            dialog.show()
        }
    }

    override fun getItemCount() : Int = myData.size

}