package wordmemory.idalavye.com.wordmemory.controllers

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import wordmemory.idalavye.com.wordmemory.database.DatabaseRef.wordsRef
import wordmemory.idalavye.com.wordmemory.models.WordListItemModel
import wordmemory.idalavye.com.wordmemory.utils.Login

object WordListItemController {
    private var wordsMutable: MutableList<WordListItemModel>? = null
    val words get() = wordsMutable as ArrayList?
    private val listeners: MutableList<WordItemDataChangeListener> = mutableListOf()

    fun pullWordItems() {
        wordsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                throw p0.toException()
            }

            override fun onDataChange(p0: DataSnapshot) {
                val words: MutableList<WordListItemModel> = mutableListOf()
                var userWords: MutableList<WordListItemModel> = mutableListOf()
                p0.children.mapNotNullTo(words) { it.getValue<WordListItemModel>(WordListItemModel::class.java) }

                for (word in words){
                    if(word._createdAt.equals(Login.getUserId())){
                        userWords.add(word)
                    }
                }

                this@WordListItemController.wordsMutable = userWords
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