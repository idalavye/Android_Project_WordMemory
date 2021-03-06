package wordmemory.idalavye.com.wordmemory.database

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object DatabaseRef {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val wordsRef: DatabaseReference = database.getReference("words")
    val statisticsRef: DatabaseReference = database.getReference("statistics")
    val statisticsDailyRef: DatabaseReference = database.getReference("daily_use")
}