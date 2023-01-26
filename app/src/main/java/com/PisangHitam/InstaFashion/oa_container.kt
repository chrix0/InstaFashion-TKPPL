package com.PisangHitam.InstaFashion

import android.app.Fragment
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager

class oa_container : AppCompatActivity() {
    private val fragManager = supportFragmentManager
    private val fragmentTransaction = fragManager.beginTransaction()

    private val EXTRA_SAVE_FRAGMENT = "SAVE_FRAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oa_container)

        var actionbar = supportActionBar
        actionbar!!.title = "Outfit Analyzer"

        //Untuk mencegah dua fragment muncul secara bersamaan ketika onRestoreInstanceState dijalankan
        if(savedInstanceState == null){
            val frag = oa_pic1_frag()
            fragmentTransaction.add(R.id.container, frag, "Step 1")
            fragmentTransaction.commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //fragment yang sedang ditampilkan
        val curFrag = getCurFrag()
        fragManager.putFragment(outState, EXTRA_SAVE_FRAGMENT, curFrag!!)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val active = fragManager.getFragment(savedInstanceState, EXTRA_SAVE_FRAGMENT)
        fragmentTransaction.replace(R.id.container, active!!, active.tag)
        Log.w("saved", "berhasil direstore : ${active.javaClass.simpleName}")
    }

    private fun getCurFrag() : androidx.fragment.app.Fragment?{
        var frags : MutableList<androidx.fragment.app.Fragment> = fragManager.fragments
        for (i in frags){
            if (i != null && i.isVisible()){
                return i
            }
        }
        return null
    }
}