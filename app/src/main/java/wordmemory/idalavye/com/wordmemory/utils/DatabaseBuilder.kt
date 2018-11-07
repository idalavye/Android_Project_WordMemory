package wordmemory.idalavye.com.wordmemory.utils

import com.google.firebase.database.DatabaseReference
import wordmemory.idalavye.com.wordmemory.models.WordListItemModel

/**
 * This object is for managerial purposes, not used in production.
 */
object DatabaseBuilder {

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
        databaseReference.child(key!!).setValue(wordItem)
    }
}