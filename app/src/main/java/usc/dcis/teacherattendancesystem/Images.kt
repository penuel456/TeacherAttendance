package usc.dcis.teacherattendancesystem

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

//import java.util.ArrayList
//import java.util.List

import kotlinx.android.synthetic.main.activity_images.*
import kotlin.collections.ArrayList
import kotlin.collections.List

class Images : AppCompatActivity() {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: ImageAdapter

    private lateinit var mProgressCircle: ProgressBar

    private lateinit  var mDatabaseRef: DatabaseReference
    private lateinit var mUploads: MutableList<Upload>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images)

        mRecyclerView = findViewById(R.id.recycler_view)
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.layoutManager = LinearLayoutManager(this)

        mProgressCircle = findViewById(R.id.progress_circle)


        mUploads = mutableListOf<Upload>()
        var c:Int = 0
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads")
        //val data = FirebaseStorage.getInstance().getReference("uploads")
        mDatabaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    c+=1
                    Log.d("COUNTING:",c.toString())
                    val upload = postSnapshot.getValue(Upload::class.java)
                    mUploads!!.add(upload!!)

                }
                Log.d("MESSAGE:",mUploads.size.toString())
                mAdapter = ImageAdapter(this@Images, mUploads)

                mRecyclerView.setAdapter(mAdapter)
                mProgressCircle.setVisibility(View.INVISIBLE)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@Images, databaseError.message, Toast.LENGTH_SHORT).show()
                mProgressCircle.setVisibility(View.INVISIBLE)
            }
        })
    }
}
