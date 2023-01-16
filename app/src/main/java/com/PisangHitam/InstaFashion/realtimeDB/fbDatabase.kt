package com.PisangHitam.InstaFashion.realtimeDB

import android.content.Context
import android.widget.Toast
import com.PisangHitam.InstaFashion.classAccount
import com.PisangHitam.InstaFashion.classAccountTemp
import com.PisangHitam.InstaFashion.singletonData
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class fbDatabase(context: Context){
    private var ref : DatabaseReference = FirebaseDatabase
        .getInstance("https://instafashion-bdf78-default-rtdb.asia-southeast1.firebasedatabase.app/")
        .getReference("account");
    private var context = context;

    init {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                if(p0!!.exists() and (p0.children.count() > 0)){
                    for(data in p0.children) {
                        val user = data.getValue<classAccountTemp>()
                        user?.let { singletonData.fbUpdated.put(data.key.toString(), listOf(it.userName!!, it.password!!)) }
                    }
                }
            }
        })
    }

    fun saveAcc(userName : String, password: String) : Boolean {
        val userID = ref.push().key.toString()
        val userData = classAccountTemp(userName, password)
        var status = false
        ref.child(userID).setValue(userData).apply {
            addOnCompleteListener {
                status = true
            }
            addOnFailureListener {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
            }
        }
        return status
    }

    fun foundAcc(list : MutableMap<String,List<String>>, userName : String, password : String) : Boolean{
        for (i in list){
            if (i.value == listOf(userName, password))
                return true
        }
        return false
    }


}

