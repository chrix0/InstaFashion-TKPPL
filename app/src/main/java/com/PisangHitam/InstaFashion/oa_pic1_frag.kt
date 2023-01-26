package com.PisangHitam.InstaFashion

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.se.omapi.Session
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import dev.jorgecastillo.androidcolorx.library.asHex
import kotlinx.android.synthetic.main.fragment_oa_pic1_frag.*
import kotlinx.android.synthetic.main.fragment_oa_pic1_frag.colorShow
import kotlinx.android.synthetic.main.fragment_oa_pic1_frag.openCamera
import kotlinx.android.synthetic.main.fragment_oa_pic1_frag.openGallery
import kotlinx.android.synthetic.main.fragment_oa_pic1_frag.photo
import kotlinx.android.synthetic.main.fragment_oa_pic1_frag.toStep2
import kotlinx.android.synthetic.main.recycler_tracker_productlist.*

class oa_pic1_frag : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //Ketika menekan back dari fragment pic2 ke pic1, savedInstanceState diberi null
        //agar gambar yang tersimpan pada saveInstanceState sebelumnya tidak muncul.
        if(savedInstanceState != null){
            super.onCreate(null)
        }
        else{
            super.onCreate(savedInstanceState)
        }

        //restore savedInstanceState melalui onCreate
        if(savedInstanceState != null){
            session = savedInstanceState?.getParcelable<classOASession>(EXTRA_SAVE_SESSION)!!
            //Bisa saja savedInstanceState ditrigger ketika gambarnya belum diisi user
            if(session.pic1 != null){
                lastPhoto = session.pic1
            }
        }
    }
    private var session = classOASession()
    private var lastPhoto : Bitmap? = null
    private lateinit var v : View
    private lateinit var bitmap: Bitmap
    private val EXTRA_SAVE_SESSION = "SAVE_SESSION_1"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_oa_pic1_frag, container, false)
        return code(v)
    }

    private fun code(v: View): View {

        val openCamera = v.findViewById<Button>(R.id.openCamera)
        val openGallery = v.findViewById<Button>(R.id.openGallery)
        val toStep2 = v.findViewById<Button>(R.id.toStep2)
        val photo = v.findViewById<ImageView>(R.id.photo)

        openCamera.setOnClickListener {
            displayCam()
        }
        openGallery.setOnClickListener {
            fromGallery()
        }
        toStep2.setOnClickListener {
            if(session.insertedPic1){
                nextStep()
            }
            else{
                Toast.makeText(context,"Please take a picture of your outfit before continuing",Toast.LENGTH_SHORT).show()
            }
        }
        if(lastPhoto != null){
            photo.setImageBitmap(lastPhoto)
        }

        return v
    }

    fun nextStep(){
        val pic2frag = oa_pic2_frag()
        val fragManager = fragmentManager
        val fragTransaction = fragManager!!.beginTransaction()

        val bundle = Bundle()
        bundle.putParcelable(OA_NEXT_STEP, session)
        pic2frag.arguments = bundle

        fragTransaction.replace(R.id.container, pic2frag, "Step 2")
        fragTransaction.addToBackStack(null)
        fragTransaction.commit()
    }

    fun displayCam() {
        var takeAPic = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takeAPic.resolveActivity(requireActivity().packageManager) != null){
            startActivityForResult(takeAPic,  REQUEST_CAMERA)
        }
    }

    fun fromGallery(){
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.setType("image/jpeg,image/png,image/bmp")
        startActivityForResult(intent, REQUEST_GALLERY)
    }

    fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(bitmap).maximumColorCount(16).generate()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var photo = v.findViewById<ImageView>(R.id.photo)
        var colorShow = v.findViewById<TextView>(R.id.colorShow)

        if(requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK && data != null ){
            var thumbnail = data.extras
            bitmap = thumbnail?.get("data") as Bitmap
            bitmap = singletonData.cropThis(bitmap)

            session.pic1 = bitmap
            photo.setImageBitmap(bitmap)

            var color = createPaletteSync(bitmap)
            var dominant = color.dominantSwatch!!.rgb

            colorShow.setBackgroundColor(dominant)
            session.pic1Hex = dominant.asHex()
            session.insertedPic1 = true
        }

        if(requestCode == REQUEST_GALLERY && data != null && resultCode == Activity.RESULT_OK){
            val selectedImageUri: Uri? = data?.data
            if (null != selectedImageUri) {
                //Kode disesuaikan dengan versi SDK yang digunakan
                //Versi sebelum Android Pie mungkin tidak bisa menjalankan ImageDecoder

                bitmap = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                    ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireContext().contentResolver, selectedImageUri))
                }
                else{
                    MediaStore.Images.Media.getBitmap(requireContext().contentResolver, selectedImageUri)
                }

                //Menggunakan config RGBA_F16 atau ARGB_8888 untuk menggantikan HARDWARE config.
                //HARDWARE config tertentu bisa crash..
                var bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    bitmap.copy(Bitmap.Config.RGBA_F16, true)
                } else {
                    bitmap.copy(Bitmap.Config.ARGB_8888, true)
                }

                bitmap = singletonData.cropThis(bitmap)

                session.pic1 = bitmap
                photo.setImageBitmap(bitmap)

                var color = createPaletteSync(bitmap)
                var dominant = color.dominantSwatch!!.rgb

                colorShow.setBackgroundColor(dominant)
                session.pic1Hex = dominant.asHex()

                session.insertedPic1 = true
            }
        }
    }

    private fun getAllPermission(){
        var permissions = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.CALL_PHONE)
        var needPermission : ArrayList<String> = ArrayList()
        for (i in permissions){
            if(ContextCompat.checkSelfPermission(requireContext(), i) != PackageManager.PERMISSION_GRANTED){
                needPermission.add(i)
            }
        }
        if (!needPermission.isEmpty()){
            ActivityCompat.requestPermissions(requireActivity(),
                needPermission.toArray(arrayOfNulls(needPermission.size)),100)
        }
    }

    override fun onStart() {
        super.onStart()
        getAllPermission()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(EXTRA_SAVE_SESSION, session)
    }
}