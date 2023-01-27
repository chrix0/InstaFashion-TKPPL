package com.PisangHitam.InstaFashion

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_shop_product_list.*
import kotlinx.android.synthetic.main.fragment_wishlist__main_.*
import org.w3c.dom.Text

class Wishlist_Main_Frag : Fragment() {

    private lateinit var adapter : recycler_products_adapter
    private lateinit var user : classAccount
    private lateinit var wishList : RecyclerView
    private lateinit var nothingText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v : View = inflater.inflate(R.layout.fragment_wishlist__main_, container, false)
        return doSomething(v)
    }

    private fun doSomething(v: View): View {
        //RECYCLER VIEW
        wishList = v.findViewById<RecyclerView>(R.id.wishList)
        nothingText = v.findViewById<TextView>(R.id.nothing_text)

        var db = singletonData.getRoomHelper(requireContext())
        user = singletonData.getCurUserObj(requireContext())!!
        val wishList_List : List<classProduk> = user.wishlist

        if(wishList_List.isEmpty()){
            nothingText.visibility = TextView.VISIBLE
        }
        else{
            nothingText.visibility = TextView.GONE
        }

        adapter = recycler_products_adapter(wishList_List){
            val info = Intent(requireContext(), shop_infoProduk::class.java)
            info.putExtra(SHOW_PRODUCT_INFO, it)
            info.putExtra(CHANGE_TITLE,"Product Info")
            startActivity(info)
        }

        wishList.layoutManager = GridLayoutManager(requireContext(), 2)
        wishList.adapter = adapter

        return v
    }

    override fun onResume() {
        super.onResume()
        user = singletonData.getCurUserObj(requireContext())!!
        adapter = recycler_products_adapter(user.wishlist){
            val info = Intent(requireContext(), shop_infoProduk::class.java)
            info.putExtra(SHOW_PRODUCT_INFO, it)
            info.putExtra(CHANGE_TITLE,"Product Info")
            startActivity(info)
        }
        wishList.adapter = adapter

        if(user.wishlist.isEmpty()){
            nothingText.visibility = TextView.VISIBLE
        }
        else{
            nothingText.visibility = TextView.GONE
        }
    }
}