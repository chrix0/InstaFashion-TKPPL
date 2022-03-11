package com.PisangHitam.InstaFashion
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.PopupMenu
import androidx.recyclerview.widget.GridLayoutManager
import com.PisangHitam.InstaFashion.classProduk
import kotlinx.android.synthetic.main.activity_shop_product_list.*

class shop_productList : AppCompatActivity() {
    private lateinit var adapter : recycler_products_adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_product_list)
        val intentData = intent
        var tempList : MutableList<classProduk> = mutableListOf()
        setTitle(intentData.getStringExtra(CHANGE_TITLE))
        val allData = intentData.getParcelableArrayListExtra<classProduk>(EXTRA_PRODUCT) as MutableList<classProduk>
        tempList.addAll(allData)

        //Adapter + on click event
        adapter = recycler_products_adapter(tempList){
            val info = Intent(this, shop_infoProduk::class.java)
            info.putExtra(SHOW_PRODUCT_INFO, it)
            info.putExtra(CHANGE_TITLE,getString(R.string.product_info_title))
            startActivity(info)
        }

        productList.layoutManager = GridLayoutManager(this, 2)
        productList.adapter = adapter

        search.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE){
                tempList.clear()
                tempList.addAll(filter(search.text.toString(),allData))
                adapter = recycler_products_adapter(tempList){
                    val info = Intent(this, shop_infoProduk::class.java)
                    info.putExtra(SHOW_PRODUCT_INFO, it)
                    info.putExtra(CHANGE_TITLE,getString(R.string.product_info_title))
                    startActivity(info)
                }
                productList.adapter = adapter
            }
            return@setOnEditorActionListener true
        }

        var popup = PopupMenu(this, sortButton)
        popup.menuInflater.inflate(R.menu.productlist_sort, popup.menu)
        sortButton.setOnClickListener{
            popup.show()
        }
        popup.setOnMenuItemClickListener {
            return@setOnMenuItemClickListener when(it.itemId){
                R.id.highest ->{
                    tempList.sortBy { it.hargaProduk }
                    tempList.reverse()
                    adapter = recycler_products_adapter(tempList){
                        val info = Intent(this, shop_infoProduk::class.java)
                        info.putExtra(SHOW_PRODUCT_INFO, it)
                        info.putExtra(CHANGE_TITLE,getString(R.string.product_info_title))
                        startActivity(info)
                    }
                    productList.adapter = adapter
                    true
                }
                R.id.lowest ->{
                    tempList.sortBy { it.hargaProduk }
                    adapter = recycler_products_adapter(tempList){
                        val info = Intent(this, shop_infoProduk::class.java)
                        info.putExtra(SHOW_PRODUCT_INFO, it)
                        info.putExtra(CHANGE_TITLE,getString(R.string.product_info_title))
                        startActivity(info)
                    }
                    productList.adapter = adapter
                    true
                }
                else -> false
            }
        }
    }

    private fun filter(searchText : String, data : MutableList<classProduk>) : MutableList<classProduk>{
        var newList : MutableList<classProduk> = mutableListOf()
        var text = searchText.trim().lowercase()
        if (text.equals("")){
            newList.addAll(data)
        }
        else{
            for(i : classProduk in data){
                if(i.namaProduk.lowercase().contains(text)){
                    newList.add(i)
                }
            }
        }

        return newList
    }

    private fun changeData() : Unit{

    }
}

