package wordmemory.idalavye.com.wordmemory.controllers

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import wordmemory.idalavye.com.wordmemory.database.DatabaseRef.wordsRef
import wordmemory.idalavye.com.wordmemory.models.WordListItemModel
import wordmemory.idalavye.com.wordmemory.utils.Login

object WordListItemController {
    val words: ArrayList<WordListItemModel> = arrayListOf()
    private val listeners: MutableList<WordItemDataChangeListener> = mutableListOf()

    fun pullWordItems() {
        wordsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                throw p0.toException()
            }

            override fun onDataChange(p0: DataSnapshot) {
                val result: MutableList<WordListItemModel> = mutableListOf()
                p0.children.mapNotNullTo(result) { it.getValue<WordListItemModel>(WordListItemModel::class.java) }

                for (word in result) {
                    if (word._createdAt.equals(Login.getUserId()) && !words.contains(word)) {
                        words.add(word)
                    }
                }

                for (listener in listeners) {
                    listener.onWordItemDataChange()
                }
            }
        })
    }

    fun addListenerForWordItemDataChange(listener: WordItemDataChangeListener) {
        listeners.add(listener)
    }

    interface WordItemDataChangeListener {
        fun onWordItemDataChange()
    }
}