package wordmemory.idalavye.com.wordmemory.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.ImageScaleType
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer
import wordmemory.idalavye.com.wordmemory.R
import wordmemory.idalavye.com.wordmemory.models.Cards
import java.util.*

class CustomListAdapter(context: Context, private val mResource: Int, private val dataSet: ArrayList<Cards>)
    : ArrayAdapter<Cards>(context, mResource, dataSet) {
    private var lastPosition = -1
    private val defaultImage = context.resources.getIdentifier("@drawable/image_failed", null, context.packageName)
    private val displayImageOptions: DisplayImageOptions

    init {
        // Default display image options
        val defaultOptions = DisplayImageOptions
                .Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(FadeInBitmapDisplayer(300))
                .build()

        // Configuration for the global image loader singleton
        val config = ImageLoaderConfiguration
                .Builder(context)
                .diskCacheSize(100 * 1024 * 1024)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(WeakMemoryCache())
                .build()

        // Apply the configuration
        ImageLoader.getInstance().init(config)

        // Initialize the display image options to use with the views
        displayImageOptions = DisplayImageOptions
                .Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .showImageOnLoading(defaultImage)//display stub image until image is loaded
                .displayer(RoundedBitmapDisplayer(20))
                .build()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val views = convertView ?: run {
            val inflater = LayoutInflater.from(context)
            val views = inflater.inflate(mResource, parent, false)

            views.tag = ViewHolder(
                    views.findViewById<View>(R.id.textView) as TextView,
                    views.findViewById<View>(R.id.imageView) as ImageView
            )

            views
        }

        val animation = AnimationUtils.loadAnimation(
                context,
                if (position > lastPosition)
                    R.anim.load_down_anim
                else
                    R.anim.load_up_anim)
        views.startAnimation(animation)
        lastPosition = position

        val imageLoader = ImageLoader.getInstance()

        val cards = getItem(position) // Gets the persons information
        val viewHolder = views.tag as ViewHolder
        viewHolder.title.text = cards.title
        imageLoader.displayImage(cards.imgUrl, viewHolder.img, displayImageOptions)

        return views
    }

    override fun getItem(position: Int): Cards {
        return dataSet[position]
    }

    internal data class ViewHolder(
            val title: TextView,
            val img: ImageView
    )
}
