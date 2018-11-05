package wordmemory.idalavye.com.wordmemory.controllers

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import wordmemory.idalavye.com.wordmemory.database.DatabaseRef.wordsRef
import wordmemory.idalavye.com.wordmemory.models.WordListItemModel

object WordListItemController {
    private var wordsMutable: MutableList<WordListItemModel>? = null
    val words get() = wordsMutable?.toList() as ArrayList?

    fun listWordItems() {
        wordsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                throw p0.toException()
            }

            override fun onDataChange(p0: DataSnapshot) {
                val words: MutableList<WordListItemModel> = mutableListOf()
                p0.children.mapNotNullTo(words) { it.getValue<WordListItemModel>(WordListItemModel::class.java) }

                this@WordListItemController.wordsMutable = words
            }
        })
    }
}