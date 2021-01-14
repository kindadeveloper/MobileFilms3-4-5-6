package ua.kpi.comsys.iv7101.mobilefilms.ui.images

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ua.kpi.comsys.iv7101.mobilefilms.R


class ImagesListAdapter(private var list: MutableList<ImageSet>) : RecyclerView.Adapter<ImageSetViewHolder>() {

    var currentSetIdx = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ImageSetViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ImageSetViewHolder, position: Int) {
        val imageSet: ImageSet = list[position]
        holder.bind(imageSet)
    }

    override fun getItemCount(): Int = list.size

    fun addImage(image: Uri?) {
        val currentSet = list[currentSetIdx]
        if (currentSet.isFull()) {
            currentSetIdx++
            val newSet = ImageSet()
            newSet.addImage(image)
            list.add(newSet)
            notifyItemInserted(currentSetIdx)
        }
        else {
            currentSet.addImage(image)
            notifyItemChanged(currentSetIdx)
        }
    }
}

class ImageSetViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_images, parent, false)){

    private var imageViews = mutableListOf<ImageView>()
    init {
        imageViews.add(itemView.findViewById(R.id.imageView1))
        imageViews.add(itemView.findViewById(R.id.imageView2))
        imageViews.add(itemView.findViewById(R.id.imageView3))
        imageViews.add(itemView.findViewById(R.id.imageView4))
        imageViews.add(itemView.findViewById(R.id.imageView5))
        imageViews.add(itemView.findViewById(R.id.imageView6))
        imageViews.add(itemView.findViewById(R.id.imageView7))
        imageViews.add(itemView.findViewById(R.id.imageView8))
        imageViews.add(itemView.findViewById(R.id.imageView9))
        imageViews.add(itemView.findViewById(R.id.imageView10))
    }

    fun bind(imageSet: ImageSet) {
        for (i in 0 until imageViews.count())
        {
            val currentImage = imageSet.images[i]
            if (currentImage == null) {
                imageViews[i].setImageResource(android.R.color.transparent)
            } else {
                imageViews[i].setImageURI(currentImage)
            }
        }
    }
}