package com.PisangHitam.InstaFashion.Room

import androidx.room.*
import com.PisangHitam.InstaFashion.classAccount
import com.PisangHitam.InstaFashion.classProduk

@Dao
interface daoOutfitList {
    //SELECT ALL (SETELAH DIOPTIMASI)
    @Transaction @Query("Select * from classProduk")
    fun getAllOutfit() : List<classProduk>

//    SELECT WHERE CATEGORY (ALL)
    @Query("Select * from classProduk where COLUMN_CAT_PRODUK = :category")
    fun getAllOutfitOnCat(category: String) : List<classProduk>

//    SELECT LIMITED
    @Query("Select * from classProduk LIMIT :limit")
    fun getOutfitLimited(limit : Int) : List<classProduk>

//    SELECT WHERE CATEGORY LIMITED
    @Query("Select * from classProduk WHERE COLUMN_CAT_PRODUK = :category LIMIT :limit")
    fun getOutfitLimited(category: String, limit : Int) : List<classProduk>

//    SELECT WHERE LIKE..
//    https://stackoverflow.com/questions/44184769/android-room-select-query-with-like
//    https://stackoverflow.com/questions/46193356/limit-the-number-of-rows-in-a-room-database
//    Description juga dicari
    @Query("Select * from classProduk where COLUMN_NAMA_PRODUK like :name or COLUMN_DESC_PRODUK like :name")
    fun getOutfitLike(name: String) : List<classProduk> //Isi sendiri bagian name, misalnya "%yang dicari%"

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOutfit(item : classProduk)

//    UPDATE (SEBELUM OPTIMISASI)
    @Update
    fun updateOutfit(itemOutfit : classProduk)

//    INSERT (SETELAH OPTIMASI)
    @Transaction
    fun addAllOutfit(list : List<classProduk>){
        for(i in list){
            addOutfit(i)
        }
    }

//    UPDATE (SETELAH OPTIMASI)
    @Transaction
    fun updateAllOutfit(list : List<classProduk>){
        for(i in list){
            updateOutfit(i)
        }
    }

}

