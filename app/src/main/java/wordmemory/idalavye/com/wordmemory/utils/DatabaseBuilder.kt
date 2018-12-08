package wordmemory.idalavye.com.wordmemory.utils

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import wordmemory.idalavye.com.wordmemory.controllers.WordListItemController
import wordmemory.idalavye.com.wordmemory.models.WordListItemModel
import java.util.*

/**
 * This object is for managerial purposes, not used in production.
 */
object DatabaseBuilder {

    private const val WORDS = "words"
    private var firebaseData = FirebaseDatabase.getInstance().reference

    /**
     * Adds [wordItems] to [databaseReference] of the remote database
     *
     * @param databaseReference Parent database reference of the [wordItems]
     * @param wordItems The set to add to the [databaseReference]
     */
    fun addWordItems(databaseReference: DatabaseReference, wordItem: WordListItemModel) {
        val key = databaseReference.push().key
        wordItem.uuid = key
        wordItem.image = 2131165330
        wordItem.word_progress = 0
        wordItem._createdAt = Login.getUserId()
        wordItem.createDate = Date()
        wordItem.updateDate = Date()
        databaseReference.child(key!!).setValue(wordItem)
    }

    fun updateWordItem(wordItem: WordListItemModel, updateProperty: String) {
        firebaseData.child(WORDS).child(wordItem.uuid!!).child(updateProperty).setValue(wordItem.word_progress)
    }

    fun removeWordItem(wordItem: WordListItemModel) {
        firebaseData.child(WORDS).child(wordItem.uuid!!).removeValue()
        WordListItemController.words.remove(wordItem)
    }

    fun resetProgressWordItem(wordItem: WordListItemModel) {
        firebaseData.child(WORDS).child(wordItem.uuid!!).child("word_progress").setValue(0)

        WordListItemController.words.find { w -> w.uuid == wordItem.uuid }!!.word_progress = 0
    }
}