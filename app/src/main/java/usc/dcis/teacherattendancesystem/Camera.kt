@file:Suppress("DEPRECATION")

package usc.dcis.teacherattendancesystem

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.annotation.TargetApi
import android.app.Activity
import android.app.ProgressDialog
import android.app.ProgressDialog.show
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build

import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.support.annotation.NonNull
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.content.FileProvider
import android.util.Log
import com.google.android.gms.tasks.OnSuccessListener
import android.view.View
import android.view.WindowManager
import android.webkit.MimeTypeMap
import android.widget.*
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnFailureListener
import com.google.common.io.Files.getFileExtension

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.*
import com.squareup.picasso.Picasso


import java.io.File
import java.io.IOException
import java.lang.Exception
import java.nio.file.Files.createFile
import java.text.SimpleDateFormat
import java.util.*;

class Camera : AppCompatActivity() {
    private var PICK_IMAGE_REQUEST = 2


    private lateinit var imageView: ImageView
    private lateinit var captureButton: Button
    private lateinit var mButtonChooseImage: Button
    private lateinit var  mButtonUpload: Button
    private lateinit var mTextViewShowUploads: TextView
    private lateinit var mEditTextFileName: EditText
    private lateinit var  mImageView: ImageView
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mImageUri: Uri
    val firebaseStorage = FirebaseStorage.getInstance()
    val firebaseStorageRef = firebaseStorage.reference
    private val ACTIVITY_RESULT_CONST = 12345


    lateinit var filePath : Uri

    private lateinit var mStorageRef: StorageReference
    private lateinit var mDatabaseRef: DatabaseReference

    private lateinit var mUploadTask : StorageTask<*>

    val REQUEST_IMAGE_CAPTURE = 1


    private val PERMISSION_REQUEST_CODE: Int = 101

    private var mCurrentPhotoPath: String? = null

    @TargetApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        mButtonChooseImage = findViewById(R.id.button_choose_image)
        mButtonUpload = findViewById(R.id.button_upload)
        mTextViewShowUploads = findViewById(R.id.text_view_show_uploads)
        mEditTextFileName = findViewById(R.id.edit_text_file_name)
        mImageView = findViewById(R.id.image_view)
        mProgressBar = findViewById(R.id.progress_bar)
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads")
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mButtonChooseImage.setOnClickListener( View.OnClickListener {
            openFileChooser()
        } )
        imageView = findViewById(R.id.image_view)
        captureButton = findViewById(R.id.btn_capture)
        captureButton.setOnClickListener(View.OnClickListener {
            if (checkPersmission()) takePicture() else requestPermission()
        })


        mButtonUpload.setOnClickListener (View.OnClickListener{

                uploadFile()

        })
        mTextViewShowUploads.setOnClickListener(View.OnClickListener{
            openImagesActivity()
        })




    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {

                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    takePicture()

                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }

            else -> {

            }
        }
    }
    fun openFileChooser(){
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(intent, "Select a file"), PICK_IMAGE_REQUEST)
    }
    private fun takePicture() {

        val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file: File = createFile()

        val uri: Uri = FileProvider.getUriForFile(
            this,
            "com.example.android.fileprovider",
            file
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
            && data != null && data.data != null) {
            mImageUri = data.data
            Log.d("adfsf", "Naaabot sya dri sa onactivity")
            Picasso.get().load(mImageUri).into(mImageView)
        }
       if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {

            //To get the File for further usage
            val auxFile = File(mCurrentPhotoPath)
            var bitmap: Bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath)
            imageView.setImageBitmap(bitmap)

        }

    }

    private fun checkPersmission(): Boolean {
        return (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(READ_EXTERNAL_STORAGE, CAMERA), PERMISSION_REQUEST_CODE)
    }

    @Throws(IOException::class)
    private fun createFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = absolutePath
        }
    }
    private fun getFileExtension(uri:Uri):String {
        val cR = getContentResolver()
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }
    /*private fun uploadFile(){
        if(mImageUri != null){
            var fileReference = mStorageRef.child(System.currentTimeMillis().toString()+"."+ getFileExtension(mImageUri))

            mUploadTask = fileReference.putFile(mImageUri)
                .addOnSuccessListener( OnSuccessListener<UploadTask.TaskSnapshot>() {
                    fun onSuccess(taskSnapshot: UploadTask.TaskSnapshot) {
                        var handler = Handler()
                        handler.postDelayed(Runnable() {
                            fun run() {
                                mProgressBar.setProgress(0)
                            }
                        }, 500)

                        Toast.makeText(this, "Upload Successful!", Toast.LENGTH_LONG).show()
                        var upload :Upload
                        upload = Upload(mEditTextFileName.getText().toString().trim(),
                            taskSnapshot.metadata?.reference?.getDownloadUrl().toString())
                        var uploadId:String
                        uploadId = mDatabaseRef.push().key.toString()
                        mDatabaseRef.child(uploadId).setValue(upload)
                    }
                })
                .addOnFailureListener(OnFailureListener(){
                     fun onFailure(@NonNull e:Exception){
                         Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                     }
                })
                .addOnProgressListener(OnProgressListener<UploadTask.TaskSnapshot>(){
                    fun onProgress(taskSnapshot :UploadTask.TaskSnapshot){
                        var progress =(100.0 * taskSnapshot.bytesTransferred/taskSnapshot.totalByteCount)
                        mProgressBar.setProgress(progress.toInt())

                    }
                })
        }else{
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show()
        }


        }*/
    private fun uploadFile() {
        val progress = ProgressDialog(this).apply {
            setTitle("Uploading Picture....")
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            show()
        }

        val data = FirebaseStorage.getInstance()
        var value = 0.0

        var storage = mStorageRef.child(mEditTextFileName.getText().toString()+"."+ getFileExtension(mImageUri))//.putFile(mImageUri)
           mUploadTask = storage.putFile(mImageUri)
            .addOnProgressListener { taskSnapshot ->
                value = (100.0 * taskSnapshot.bytesTransferred) / taskSnapshot.totalByteCount
                Log.v("value","value=="+value)
                progress.setMessage("Uploaded.. " + value.toInt() + "%")
            }
            .addOnSuccessListener{ taskSnapshot -> progress.dismiss()
                storage.downloadUrl.addOnCompleteListener(){ taskSnapshot->
                    var uri =taskSnapshot.result.toString()
                    println("url =" + uri.toString ())
                    val imageName = mEditTextFileName.text.toString().trim()
                    val upload = Upload(
                        imageName, uri
                    )
                    val uploadId = mDatabaseRef.push().key
                    mDatabaseRef.child(uploadId.toString()).setValue(upload)

                    Log.d("Download File","File.." +uri)
                    Glide.with(this@Camera).load(uri).into(mImageView)
                }
               /* val imageName = mEditTextFileName.text.toString().trim()
                //val uri = storage.downloadUrl!!.toString()/*mStorageRef.child(mEditTextFileName.getText().toString()+"."+ getFileExtension(mImageUri)).downloadUrl.toString()*///taskSnapshot.metadata?.reference?.downloadUrl.toString()
                val upload = Upload(
                    imageName, uri
                )
                val uploadId = mDatabaseRef.push().key
                mDatabaseRef.child(uploadId.toString()).setValue(upload)

                Log.d("Download File","File.." +uri)
                Glide.with(this@Camera).load(uri).into(mImageView)*/
            }
            .addOnFailureListener{
                    exception -> exception.printStackTrace()
            }


    }
     fun  openImagesActivity(){
         val activity = Intent(this, Images::class.java)
         startActivity(activity)
     }


}
