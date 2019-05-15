package usc.dcis.teacherattendancesystem

import android.R
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import usc.dcis.teacherattendancesystem.Upload
import kotlinx.android.synthetic.main.image_item.*

import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop


class ImageAdapter(private var mContext: Context, private var mUploads: List<Upload>) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {


        val v = LayoutInflater.from(mContext).inflate(usc.dcis.teacherattendancesystem.R.layout.image_item, parent, false)
        return ImageViewHolder(v)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val uploadCurrent = mUploads[position]
        //val upload = Upload()
        holder.textViewName.text = uploadCurrent.name
        Glide.with(mContext).load(uploadCurrent.imageUrl).into(holder.imageView)
        /*Picasso.get()
            .load(uploadCurrent.getImageUrl())
            .fit()
            .centerCrop()
            .into(holder.imageView)*/

    }

    override fun getItemCount(): Int {
        Log.d("Count:", mUploads.size.toString())
        return mUploads.size

    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewName: TextView
        var imageView: ImageView

        init {

            textViewName = itemView.findViewById(usc.dcis.teacherattendancesystem.R.id.text_view_name)
            imageView = itemView.findViewById(usc.dcis.teacherattendancesystem.R.id.image_view_upload)
        }
    }
}