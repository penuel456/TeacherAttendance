package usc.dcis.teacherattendancesystem

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.UploadTask

//import java.util.ArrayList
//import java.util.List

import kotlinx.android.synthetic.main.activity_images.*
import kotlin.collections.ArrayList
import kotlin.collections.List

class Images : AppCompatActivity() {
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: ImageAdapter? = null

    private var mProgressCircle: ProgressBar? = null

    private var mDatabaseRef: DatabaseReference? = null
    private lateinit var mUploads: List<Upload>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images)

        mRecyclerView = findViewById(R.id.recycler_view)
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.layoutManager

        mProgressCircle = findViewById(R.id.progress_circular)


        mUploads = arrayListOf()

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads")
/*
        mDatabaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    val upload = postSnapshot.getValue(Upload::class.java)
                    mUploads.add(upload)
                }

                mAdapter = ImageAdapter(this@ImagesActivity, mUploads)

                mRecyclerView.setAdapter(mAdapter)
                mProgressCircle.setVisibility(View.INVISIBLE)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@ImagesActivity, databaseError.message, Toast.LENGTH_SHORT).show()
                mProgressCircle.setVisibility(View.INVISIBLE)
            }
        })*/





    }





}
