package usc.dcis.teacherattendancesystem

import android.R
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import usc.dcis.teacherattendancesystem.Upload
import kotlinx.android.synthetic.main.image_item.*

import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop


class ImageAdapter(private val mContext: Context, private val mUploads: List<Upload>) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {


        val v = LayoutInflater.from(mContext).inflate(usc.dcis.teacherattendancesystem.R.layout.image_item, parent, false)
        return ImageViewHolder(v)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val uploadCurrent = mUploads[position]
        holder.textViewName.text = uploadCurrent.getName()
        Picasso.get()
            .load(uploadCurrent.getImageUrl())
            .fit()
            .centerCrop()
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
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