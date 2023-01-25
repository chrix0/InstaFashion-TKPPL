package com.PisangHitam.InstaFashion

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import dev.jorgecastillo.androidcolorx.library.asHex
import kotlinx.android.synthetic.main.fragment_oa_pic2_frag.*

class oa_pic2_frag : Fragment() {

    private lateinit var session : classOASession
    private lateinit var v : View
    private var lastPhoto : Bitmap? = null
    private lateinit var bitmap: Bitmap

    private val EXTRA_SAVE_SESSION = "SAVE_SESSION_2"

    override fun onCreate(savedInstanceState: Bundle?) {
        if(savedInstanceState != null){
            super.onCreate(null)
        }
        else{
            super.onCreate(savedInstanceState)
        }

        if(savedInstanceState != null){
            session = savedInstanceState?.getParcelable<classOASession>(EXTRA_SAVE_SESSION)!!
            //Bisa saja savedInstanceState ditrigger ketika gambarnya belum diisi user
            if(session.pic2 != null){
                lastPhoto = session.pic2
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_oa_pic2_frag, container, false)
        session = arguments?.getParcelable<classOASession>(OA_NEXT_STEP)!!
        return code(v)
    }

    fun displayCam() {
        var takeAPic = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takeAPic.resolveActivity(requireActivity().packageManager) != null){
            startActivityForResult(takeAPic,  REQUEST_CAMERA) //[NOTE PLEASE : requestCode]
        }
    }

    fun fromGallery(){
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.setType("image/jpeg,image/png,image/bmp")
        startActivityForResult(intent, REQUEST_GALLERY)
    }

    fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(bitmap).maximumColorCount(16).generate()

    private fun code(v: View): View {
        val openCamera = v.findViewById<Button>(R.id.openCamera)
        val openGallery = v.findViewById<Button>(R.id.openGallery)
        val startAnalysis = v.findViewById<Button>(R.id.startAnalysis)
        val photo = v.findViewById<ImageView>(R.id.photo)

        openCamera.setOnClickListener {
            displayCam()
        }

        openGallery.setOnClickListener {
            fromGallery()
        }

        startAnalysis.setOnClickListener {
            if(session.insertedPic2) {
                var intent = Intent(requireContext(), OA_result::class.java)
                //Button Back nanti tidak bisa digunakan untuk kembali ke activity ini
                singletonData.OASession = session
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
            else{
                Toast.makeText(requireContext(),"Please take a picture of your outfit before continuing", Toast.LENGTH_SHORT).show()
            }
        }

        if(lastPhoto != null){
            photo.setImageBitmap(lastPhoto)
        }

        return v
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var photo = v.findViewById<ImageView>(R.id.photo)
        var colorShow2 = v.findViewById<TextView>(R.id.colorShow2)

        if(requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK && data != null ){
            var thumbnail = data.extras
            var bitmap = thumbnail?.get("data") as Bitmap
            bitmap = singletonData.cropThis(bitmap)

            session.pic2 = bitmap
            photo.setImageBitmap(bitmap)

            var color = createPaletteSync(bitmap)
            var dominant = color.dominantSwatch!!.rgb

            colorShow2.setBackgroundColor(dominant)
            session.pic2Hex = dominant.asHex()

            session.insertedPic2 = true
        }

        if(requestCode == REQUEST_GALLERY && data != null && resultCode == Activity.RESULT_OK){
            val selectedImageUri: Uri? = data?.data
            var bitmap : Bitmap
            if (null != selectedImageUri) {
                //Kode disesuaikan dengan versi SDK yang digunakan
                //Versi sebelum Android Pie mungkin tidak bisa menjalankan ImageDecoder
                bitmap = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                    ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireActivity().contentResolver, selectedImageUri))
                }
                else{
                    MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, selectedImageUri)
                }

                var bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    bitmap.copy(Bitmap.Config.RGBA_F16, true)
                } else {
                    bitmap.copy(Bitmap.Config.ARGB_8888, true)
                }

                bitmap = singletonData.cropThis(bitmap)

                session.pic2 = bitmap
                photo.setImageBitmap(bitmap)

                var color = createPaletteSync(bitmap)
                var dominant = color.dominantSwatch!!.rgb

                colorShow2.setBackgroundColor(dominant)
                session.pic2Hex = dominant.asHex()

                session.insertedPic2 = true
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(EXTRA_SAVE_SESSION, session)
    }
}