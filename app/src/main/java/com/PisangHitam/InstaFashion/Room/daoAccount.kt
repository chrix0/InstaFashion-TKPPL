package com.PisangHitam.InstaFashion.Room

import androidx.room.*
import com.PisangHitam.InstaFashion.classAccount
import com.PisangHitam.InstaFashion.classProduk

@Dao
interface daoAccount {
    //SELECT (SETELAH OPTIMASI)
    @Transaction @Query("Select * from classAccount")
    fun getAllAcc() : List<classAccount>
    @Query("Select * from classAccount where COLUMN_USERNAME = :username and COLUMN_PASS = :password")
    fun getAcc(username : String, password : String) : List<classAccount>

    @Query("Select * from classAccount where COLUMN_USERNAME = :username")
    fun getAccUserCheck(username : String) : List<classAccount>

    @Query("Select * from classAccount where id = :id")
    fun getAccById(id: Int) : List<classAccount>
    //INSERT
    @Insert
    fun newAcc(acc : classAccount)

    //UPDATE
    @Update
    fun updateAcc(acc : classAccount)

    //INSERT (SETELAH OPTIMASI)
    @Transaction
    fun addAllAcc(list : List<classAccount>){
        for(i in list){
            newAcc(i)
        }
    }

    // UPDATE (SETELAH OPTIMASI)
    @Transaction
    fun updateAllAcc(list : List<classAccount>){
        for(i in list){
            updateAcc(i)
        }
    }

}