package com.PisangHitam.InstaFashion

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import com.rain.adopets.singletonData
import dev.jorgecastillo.androidcolorx.library.asHex
import kotlinx.android.synthetic.main.activity_oa_pet_pic.*
import kotlinx.android.synthetic.main.activity_oa_pet_pic.photo
import kotlinx.android.synthetic.main.recycler_tracker_productlist.*

class OA_petPic : AppCompatActivity() {

    fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(bitmap).maximumColorCount(16).generate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oa_pet_pic)
        singletonData.OASession = classOASession()

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.petAnalyzerTitle)

        openCamera.setOnClickListener {
            displayCam()
        }

        openGallery.setOnClickListener {
            fromGallery()
        }

        toStep2.setOnClickListener {
            if(singletonData.OASession.insertedPet){
                var intent = Intent(this, OA_outfitPic::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this,getString(R.string.need_pet_pic),Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun displayCam() {
        var takeAPic = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takeAPic.resolveActivity(packageManager) != null){
            startActivityForResult(takeAPic,  REQUEST_CAMERA) //[NOTE PLEASE : requestCode]
        }
    }

    fun fromGallery(){
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.setType("image/jpeg,image/png,image/bmp")
        startActivityForResult(intent, REQUEST_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK && data != null ){
            var thumbnail = data.extras
            var bitmap = thumbnail?.get("data") as Bitmap
            bitmap = singletonData.cropThis(bitmap)

            singletonData.OASession.petPic = bitmap
            photo.setImageBitmap(bitmap)

            var color = createPaletteSync(bitmap)
            var dominant = color.dominantSwatch!!.rgb

            colorShow.setBackgroundColor(dominant)
            singletonData.OASession.petHex = dominant.asHex()
            singletonData.OASession.insertedPet = true
        }

        if(requestCode == REQUEST_GALLERY && data != null && resultCode == Activity.RESULT_OK){
            val selectedImageUri: Uri? = data?.data
            var bitmap : Bitmap
            if (null != selectedImageUri) {
                //Kode disesuaikan dengan versi SDK yang digunakan
                //Versi sebelum Android Pie mungkin tidak bisa menjalankan ImageDecoder

                bitmap = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                    ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.contentResolver, selectedImageUri))
                }
                else{
                    MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImageUri)
                }

                //Menggunakan config RGBA_F16 atau ARGB_8888 untuk menggantikan HARDWARE config.
                //HARDWARE config tertentu bisa crash..
                var bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    bitmap.copy(Bitmap.Config.RGBA_F16, true)
                } else {
                    bitmap.copy(Bitmap.Config.ARGB_8888, true)
                }

                bitmap = singletonData.cropThis(bitmap)

                singletonData.OASession.petPic = bitmap
                photo.setImageBitmap(bitmap)

                var color = createPaletteSync(bitmap)
                var dominant = color.dominantSwatch!!.rgb

                colorShow.setBackgroundColor(dominant)
                singletonData.OASession.petHex = dominant.asHex()

                singletonData.OASession.insertedPet = true
            }
        }
    }

    private fun getAllPermission(){
        var permissions = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.CALL_PHONE)
        var needPermission : ArrayList<String> = ArrayList()
        for (i in permissions){
            if(ContextCompat.checkSelfPermission(this, i) != PackageManager.PERMISSION_GRANTED){
                needPermission.add(i)
            }
        }
        if (!needPermission.isEmpty()){
            ActivityCompat.requestPermissions(this,
                needPermission.toArray(arrayOfNulls(needPermission.size)),100)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onStart() {
        super.onStart()
        getAllPermission()
    }
}