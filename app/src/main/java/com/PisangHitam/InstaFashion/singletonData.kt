package com.PisangHitam.InstaFashion

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.media.ThumbnailUtils
import androidx.room.Room

import com.PisangHitam.InstaFashion.Room.roomHelper
import com.PisangHitam.InstaFashion.classAccount
import com.PisangHitam.InstaFashion.classItemBasket
import com.PisangHitam.InstaFashion.classOASession
import com.PisangHitam.InstaFashion.classProduk
import com.PisangHitam.InstaFashion.realtimeDB.fbDatabase
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.gson.Gson
import java.text.DecimalFormat
import java.text.NumberFormat

object singletonData {

    //1. DATA + METHOD FITUR =============================================

    // LOGIN SIGNUP + BASKET [sudah tidak digunakan]
    var accList : MutableList<classAccount> = mutableListOf(
        classAccount(
            0,
            "TEST",
            "TEST",
            "Test Account",
            "testaccount@gmail.com",
            mutableListOf(),
            mutableListOf("","","",""),
            "",
            mutableListOf(),
            mutableListOf()
        )
    )

    var currentAccId : Int = 0

    fun subtotalInCart(cartContent: MutableList<classItemBasket>) : Int{
        var total = 0
        for (i : classItemBasket in cartContent){
            total += i.hargaProduk * i.quantity
        }
        return total
    }

    //TOTAL + 5000 (sebagai ongkos kirim)
    fun totalInCart(cartContent: MutableList<classItemBasket>) : Int = subtotalInCart(cartContent) + 5000

    // SHOP PRODUCTS
    var outfitList : MutableList<classProduk> = mutableListOf(
        classProduk(
            1,
            "Original levis shirt grid pattern",
            188000,
            "https://media.karousell.com/media/photos/products/2018/12/21/levis_original_for_women__not_uniqlo_second_kemeja_second_lecis_second_zara_second_gucci_second_foss_1545358303_c94dcaae.jpg",
            "shirt",
            "Original levis shirt with grid pattern, used once or twice for attending bussiness. \nColor : Dark blue \nsize : XL"
        ),
        classProduk(
            2,
            "Original supreme T-shirts Futura Logo Black Tee - M",
            300000,
            "https://images.tokopedia.net/img/cache/900/VqbcmM/2020/12/18/7be9e0a1-b70e-4f97-b06a-0d92111671f4.jpg",
            "shirt",
            "Original supreme T-shirts with Futura Logo Black Tee. \nColor : Black \nSize : M."
        ),
        classProduk(
            3,
            "Premium jeans slim fit blue original",
            150000,
            "https://images.tokopedia.net/img/cache/900/product-1/2020/2/21/2288057/2288057_ec16ca5b-3a3d-46ba-9f31-2afda5ba5e11_600_600",
            "trousers",
            "Premium jeans slim fit blue original. \nColor : Blue \nsize : 29 (74x78x97)cm"
        ),
        classProduk(
            4,
            "Adidas Response Run Men's Running Shoes - Black - Black, UK 9",
            400000,
            "https://images.tokopedia.net/img/cache/900/hDjmkQ/2021/9/13/95fab2a9-4104-4d99-9d52-f245002abd18.jpg",
            "shoes",
            "Adidas Response Run Men's Running Shoes. \nColor : Black \nsize : UK - 9 (27.5cm)"
        ),
        classProduk(
            5,
            "Topi RVCA Flexfit Original Shane Flexfit Cap Stone - S",
            170000,
            "https://images.tokopedia.net/img/cache/900/VqbcmM/2022/3/7/26bf641e-d78b-4ef6-a6a7-6af1fc645170.jpg",
            "access",
            "RVCA Flexfit Original Shane Flexfit Cap Stone. \nMaterial: 70% Polyester; 25% Viscose; 5% Elastane. \nColor : Gray, size : S(54cm)"
        ),

        classProduk(
            6,
            "Hoodie man anime tokyo revenger Black , L",
            50000,
            "https://images.tokopedia.net/img/cache/900/VqbcmM/2021/8/19/ea348664-aae6-401b-ad2a-f2c1063ce913.jpg",
            "jacket",
            "Hoodie man anime tokyo revenger. \nColor : black, \nsize : L(62x53cm)"
        ),
        classProduk(
            7,
            "Jacket sword art online kirito SAO nerve gear hoodie",
            100000,
            "https://images.tokopedia.net/img/cache/900/product-1/2018/8/5/2706762/2706762_73825b99-6534-4374-8fd4-272f09ca3097_700_700.jpg",
            "jacket",
            "Jacket sword art online kirito SAO nerve gear hoodie for male. Color : Black, size : M"
        ),
        classProduk(
            8,
            "Sweater Hoodie Hokage Anime Male Naruto Akatsuki Uchiha Unisex Hokage",
            120000,
            "https://images.tokopedia.net/img/cache/900/product-1/2021/4/1/419891/419891_7bbb101e-5a69-4900-bf1d-64aeae4dcfa8.jpg",
            "jacket",
            "Sweater Hoodie Hokage Anime Male Naruto Akatsuki Uchiha Unisex Hokage. Color : White, size : M"
        ),
        classProduk(
            9,
            "Shorts male exercise blue M",
            20000,
            "https://images.tokopedia.net/img/cache/900/VqbcmM/2021/12/6/b01d4b3f-73f0-4c8e-9937-796a88be26fc.jpg",
            "trousers",
            "Shorts male exercise blue for indoor and outdoor. Color : Blue, size : XL"
        ),
        classProduk(
            10,
            "Ray-Ban Frame Kacamata Rayban Round Acetate RX 5380F 2034 Black",
            80000,
            "https://images.tokopedia.net/img/cache/900/hDjmkQ/2021/2/11/2b1f95eb-4352-4f0b-9a69-96e230feea40.png",
            "access",
            "Ray-Ban Frame Kacamata Rayban Round Acetate RX 5380F Fake. Color : Black, size : -"
        ),
    )

    //Outfit Analyzer (Untuk memudahkan resetting aja)
    lateinit var OASession : classOASession
    var allImageProcessed = false

    //2. UNIVERSAL METHOD + LIBRARY INIT ============================================

    fun formatHarga(harga : Int) : String{
        var formatter : DecimalFormat = NumberFormat.getInstance() as DecimalFormat
        formatter.applyPattern("#,###")
        return formatter.format(harga)
    }

    fun formatAlamat(alamat : MutableList<String>) = "${alamat[0]}, ${alamat[1]}, ${alamat[2]}, ID ${alamat[3]}"

    //Ini dipakai ketika mau ubah objek jadi JSON biar pengiriman data ke activity lain lebih gampang.
    val toJson : Gson = Gson()

    //Image Cropper. Utk bagian Outfit Analyzer
    fun cropThis(pic : Bitmap) : Bitmap{
        var w = pic.width
        var h = pic.height

        var wResult = w/2
        var hResult = h/2

        var dimension = Math.min(w/2, h/2)

        return ThumbnailUtils.extractThumbnail(pic, dimension, dimension)
    }

    //BR - Cek network
    var nw_receiver = BR_networkCheck()
    var nw_filter = IntentFilter()

    //BR - Cek internet
    var inet_receiver = BR_inetCheck()
    var inet_filter = IntentFilter()

    //BR - Notifikasi Recommender
    var mAlarmManager : AlarmManager? = null
    var mPendingIntent : PendingIntent? = null

    //JobService - Geo JSON
    var jsonStringGeo : String? = null
    var geoRes : MutableList<String>? = null

    //ROOM DATABASE
    fun getRoomHelper(context : Context) : roomHelper{
        val db = Room.databaseBuilder(context, roomHelper::class.java, "InstaFashionRoom.db" )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
        return db
    }

    fun clearAllTable(context: Context){
        getRoomHelper(context).clearAllTables()
    }

    fun getCurUserObj(context: Context) : classAccount?{
        if(getRoomHelper(context).daoAccount().getAccById(currentAccId).isNotEmpty()){
            return getRoomHelper(context).daoAccount().getAccById(currentAccId)[0]
        }
        return null
    }

    // MEDIA PLAYER
    var myMediaPlayer : MediaPlayer? = null
    var playerPaused = false
    var playerCreated = false


    // INTERSTITIAL LOAD
    var mInterstitialAd: InterstitialAd? = null
    fun loadInterstitial(context: Context){
        var adRequires = AdRequest.Builder()
            .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            .build()
        mInterstitialAd = InterstitialAd(context).apply {
            adUnitId = "ca-app-pub-3940256099942544/1033173712"
        }
        mInterstitialAd!!.loadAd(adRequires)
    }

    // REWARDED LOAD
    var mRewardedVideoAd: RewardedVideoAd? = null

    //FIREBASE REALTIME
    var fbDb : fbDatabase? = null
    var fbUpdated : MutableMap<String,List<String>> = mutableMapOf()


}