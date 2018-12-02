package wordmemory.idalavye.com.wordmemory.controllers

import android.util.Log
import com.google.firebase.database.*
import wordmemory.idalavye.com.wordmemory.database.DatabaseRef
import wordmemory.idalavye.com.wordmemory.database.DatabaseRef.statisticsRef
import wordmemory.idalavye.com.wordmemory.models.StatisticModel
import wordmemory.idalavye.com.wordmemory.utils.Login

object StatisticController {

    private const val STATISTICS = "statistics"
    private val statisticsRef: DatabaseReference = DatabaseRef.statisticsRef
    private var firebaseData = FirebaseDatabase.getInstance().reference
    private val statisticsList: MutableList<StatisticModel> = mutableListOf()
    var statisticsForCurrentUser: StatisticModel = StatisticModel("0", "0", 0, 0, 0, 0)

    fun getUserStatistics() {
        val statisticListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var check: Boolean? = true
                if (dataSnapshot.exists()) {
                    statisticsList.clear()
                    dataSnapshot.children.mapNotNullTo(statisticsList) {
                        it.getValue<StatisticModel>(StatisticModel::class.java)
                    }

                    for (statistics in statisticsList) {
                        if (statistics._userId.equals(Login.getUserId())) {
                            check = false
                            statisticsForCurrentUser = statistics
                        }
                    }
                }

                if (check == true) {
                    //Kullanıcı yeni üye olmuşsa onun için yeni bir document oluşturuyoruz.
                    addStatisticForUser(statisticsForCurrentUser!!)
                }else{
                    updateStatisticsForCurrentUser(statisticsForCurrentUser!!)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Failed to read value
            }

            fun addStatisticForUser(statistic: StatisticModel) {
                val key = statisticsRef.push().key
                statistic.uuid = key
                statistic._userId = Login.getUserId()
                statistic.totalWord = WordListItemController.words.size
                statistic.totalLearnedWord = WordListItemController.words.filter { s -> s.word_progress == 100 }.size
                statistic.totalRepeated = 0
                statistic.totalCorrectRepeated = 0
                statisticsRef.child(key!!).setValue(statistic)
            }

            fun updateStatisticsForCurrentUser(statistic:StatisticModel){
                statistic.totalWord = WordListItemController.words.size
                statistic.totalLearnedWord = WordListItemController.words.filter { s -> s.word_progress == 100 }.size

                firebaseData.child(STATISTICS).child(statisticsForCurrentUser.uuid!!).setValue(statistic)
            }
        }
        statisticsRef!!.addValueEventListener(statisticListener)
    }

    fun updateStatisticsAfterExercise(type: String, value: Int) {
        firebaseData.child(STATISTICS).child(statisticsForCurrentUser.uuid!!).child(type).setValue(value)
    }
}