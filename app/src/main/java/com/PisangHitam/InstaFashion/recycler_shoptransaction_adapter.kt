package com.PisangHitam.InstaFashion

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.PisangHitam.InstaFashion.classTransaction
import kotlinx.android.synthetic.main.activity_shop_basket.*

class recycler_shoptransaction_adapter(val context : Context, data : MutableList<classTransaction>,
                                       val openDetails : (classTransaction) -> Unit) : RecyclerView.Adapter<recycler_shoptransaction_adapter.myHolder>(){

    private lateinit var adapter : recycler_shoptransaction_productlist_adapter
    private var myData = data
    class myHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var tampil = itemView.findViewById<RecyclerView>(R.id.produkTampil)
        var count = itemView.findViewById<TextView>(R.id.jumlahItem)
        var details = itemView.findViewById<Button>(R.id.details)
        var cancel = itemView.findViewById<Button>(R.id.batal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recycler_shoptransaction_adapter.myHolder {
        val inflate = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_tracker,parent,false)
        return recycler_shoptransaction_adapter.myHolder(inflate)
    }

    override fun onBindViewHolder(holder: recycler_shoptransaction_adapter.myHolder, position: Int) {
        adapter = recycler_shoptransaction_productlist_adapter(myData[position].items)
        holder.tampil.layoutManager = LinearLayoutManager(context)
        holder.tampil.adapter = adapter


        holder.count.setText(context.getString(R.string.productCount) + myData[position].items.size.toString())

        holder.cancel.setOnClickListener {
            var dialog = AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.cancelOrderDialogTitle))
                .setMessage(context.getString(R.string.cancelOrderExplain))
                .setPositiveButton(context.getString(R.string.OK), DialogInterface.OnClickListener{ dialogInterface, i ->
                    myData.removeAt(position)
                    notifyDataSetChanged()
                })
                .setNegativeButton(context.getString(R.string.cancel), DialogInterface.OnClickListener{ dialogInterface, i ->
                })
            dialog.show()
        }

        holder.details.setOnClickListener {
            openDetails(myData[position])
        }
    }

    override fun getItemCount(): Int = myData.size
}