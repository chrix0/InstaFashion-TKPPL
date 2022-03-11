package com.PisangHitam.InstaFashion

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.palette.graphics.Palette
import com.rain.adopets.OA_result
import com.rain.adopets.singletonData
import dev.jorgecastillo.androidcolorx.library.asHex
import kotlinx.android.synthetic.main.activity_oa_outfit_pic.*
import kotlinx.android.synthetic.main.activity_oa_pet_pic.*
import kotlinx.android.synthetic.main.activity_oa_pet_pic.openCamera
import kotlinx.android.synthetic.main.activity_oa_pet_pic.openGallery
import kotlinx.android.synthetic.main.activity_oa_pet_pic.photo
import kotlinx.android.synthetic.main.recycler_tracker_productlist.*

class OA_outfitPic : AppCompatActivity() {

    fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(bitmap).maximumColorCount(8).generate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oa_outfit_pic)

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.petAnalyzerTitle)
        actionbar.setDisplayHomeAsUpEnabled(true)

        openCamera.setOnClickListener {
            displayCam()
        }

        openGallery.setOnClickListener {
            fromGallery()
        }

        startAnalysis.setOnClickListener {
            if(singletonData.OASession.insertedOutfit) {
                var intent = Intent(this, OA_result::class.java)
                //Button Back nanti tidak bisa digunakan untuk kembali ke activity ini
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
            else{
                Toast.makeText(this,getString(R.string.need_outfit_pic), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun displayCam() {
        var takeAPic = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takeAPic.resolveActivity(packageManager) != null){
            startActivityForResult(takeAPic,  REQUEST_CAMERA)
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

            singletonData.OASession.outiftPic = bitmap
            photo.setImageBitmap(bitmap)

            var color = createPaletteSync(bitmap)
            var dominant = color.dominantSwatch!!.rgb

            colorShow2.setBackgroundColor(dominant)
            singletonData.OASession.outfitHex = dominant.asHex()

            singletonData.OASession.insertedOutfit = true
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

                var bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    bitmap.copy(Bitmap.Config.RGBA_F16, true)
                } else {
                    bitmap.copy(Bitmap.Config.ARGB_8888, true)
                }

                bitmap = singletonData.cropThis(bitmap)

                singletonData.OASession.outiftPic = bitmap
                photo.setImageBitmap(bitmap)

                var color = createPaletteSync(bitmap)
                var dominant = color.dominantSwatch!!.rgb

                colorShow2.setBackgroundColor(dominant)
                singletonData.OASession.outfitHex = dominant.asHex()

                singletonData.OASession.insertedOutfit = true
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}