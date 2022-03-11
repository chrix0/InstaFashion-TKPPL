package com.PisangHitam.InstaFashion

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.PisangHitam.InstaFashion.OA_petPic
import com.PisangHitam.InstaFashion.classProduk
import io.github.yavski.fabspeeddial.FabSpeedDial
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter
import kotlinx.android.synthetic.main.fragment_shop__main_.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Shop_Main_Frag : Fragment() {

    private lateinit var adapterFood : recycler_products_adapter
    private lateinit var adapterUtensils : recycler_products_adapter
    private lateinit var adapterOutfit : recycler_products_adapter

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val v : View = inflater.inflate(R.layout.fragment_shop__main_, container, false)
        return doSomething(v)
    }

    private fun doSomething(v: View): View {
        //RECYCLER VIEW
        val foodList = v.findViewById<RecyclerView>(R.id.foodList)
        val utensilsList = v.findViewById<RecyclerView>(R.id.utensilsList)
        val outfitList = v.findViewById<RecyclerView>(R.id.outfitList)

        val first5food : List<classProduk> = singletonData.petFoodList.take(5)
        val first5uten : List<classProduk> = singletonData.petUtensilList.take(5)
        val first5outfit : List<classProduk> = singletonData.petOutfitList.take(5)

        adapterFood = recycler_products_adapter(first5food){
            val info = Intent(requireContext(), shop_infoProduk::class.java)
            info.putExtra(SHOW_PRODUCT_INFO, it)
            info.putExtra(CHANGE_TITLE,requireContext().getString(R.string.product_info_title))
            startActivity(info)
        }
        foodList.adapter = adapterFood
        foodList.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false)

        adapterUtensils = recycler_products_adapter(first5uten){
            val info = Intent(requireContext(), shop_infoProduk::class.java)
            info.putExtra(SHOW_PRODUCT_INFO, it)
            info.putExtra(CHANGE_TITLE,requireContext().getString(R.string.product_info_title))
            startActivity(info)
        }
        utensilsList.adapter = adapterUtensils
        utensilsList.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false)

        adapterOutfit = recycler_products_adapter(first5outfit){
            val info = Intent(requireContext(), shop_infoProduk::class.java)
            info.putExtra(SHOW_PRODUCT_INFO, it)
            info.putExtra(CHANGE_TITLE,requireContext().getString(R.string.product_info_title))
            startActivity(info)
        }
        outfitList.adapter = adapterOutfit
        outfitList.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false)

        //MORE BUTTON
        val moreFood = v.findViewById<Button>(R.id.moreFood)
        val moreUtensils = v.findViewById<Button>(R.id.moreUtensils)
        val moreOutfit = v.findViewById<Button>(R.id.moreOutfit)

        moreFood.setOnClickListener {
            val more = Intent(requireContext(), shop_productList::class.java)
            more.putExtra(EXTRA_PRODUCT,singletonData.petFoodList as ArrayList<classProduk>)
            more.putExtra(CHANGE_TITLE, requireContext().getString(R.string.pet_foods_title))
            startActivity(more)
        }
        moreUtensils.setOnClickListener {
            val more = Intent(requireContext(), shop_productList::class.java)
            more.putExtra(EXTRA_PRODUCT,singletonData.petUtensilList as ArrayList<classProduk>)
            more.putExtra(CHANGE_TITLE, requireContext().getString(R.string.pet_tools_title))
            startActivity(more)
        }

        moreOutfit.setOnClickListener {
            val more = Intent(requireContext(), shop_productList::class.java)
            more.putExtra(EXTRA_PRODUCT,singletonData.petOutfitList as ArrayList<classProduk>)
            more.putExtra(CHANGE_TITLE, requireContext().getString(R.string.pet_outfit_title))
            startActivity(more)
        }

        val speedDial : FabSpeedDial = v.findViewById(R.id.speedDial)

        speedDial.setMenuListener(object : SimpleMenuListenerAdapter() {
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId){
                    R.id.basket -> {
                        var keBasket = Intent(requireContext(), shop_basket::class.java)
                        startActivity(keBasket)
                    }
                    R.id.history -> {
                        var keHistory = Intent(requireContext(), shop_tracker::class.java)
                        startActivity(keHistory)
                    }
                }
                return false
            }
        })

        val analyzer = v.findViewById<Button>(R.id.analyzer)
        analyzer.setOnClickListener {
            var intent = Intent(requireContext(), OA_petPic::class.java)
            startActivity(intent)
        }

        /*
        toHistory.setMenuListener(object : SimpleMenuListenerAdapter() {
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                var keHistory = Intent(requireContext(), shop_tracker::class.java)
                startActivity(keHistory)
                return false
            }
        })*/

        /*
        val fab = v.findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fabBasket)
        fab.setOnClickListener {
            var keBasket = Intent(requireContext(), shop_basket::class.java)
            startActivity(keBasket)
        }
        */
        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Shop_Main_Frag.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
                Shop_Main_Frag().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}