package wordmemory.idalavye.com.wordmemory.data

import android.view.animation.Animation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.github.lzyzsd.circleprogress.ArcProgress

// This file contains commonly used view holder data classes those are used to to cache view
// components.
data class WordListViewHolder(
        val wordTextView: TextView,
        val wordMeanTextView: TextView,
        val dateTextView: TextView,
        val wordImage: ImageView,
        val progress: ArcProgress
)

data class AnimationViewHolder(
        val animation: Animation
)

data class ExpandItemsViewHolder(
        val edit: LinearLayout,
        val delete: LinearLayout,
        val reset: LinearLayout,
        val detail: LinearLayout
)
