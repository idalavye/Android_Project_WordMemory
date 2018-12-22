package wordmemory.idalavye.com.wordmemory.controllers

import android.view.View
import com.google.firebase.database.*
import wordmemory.idalavye.com.wordmemory.database.DatabaseRef
import wordmemory.idalavye.com.wordmemory.models.DailyStatistics
import wordmemory.idalavye.com.wordmemory.models.StatisticModel
import wordmemory.idalavye.com.wordmemory.utils.ExerciseCommon
import wordmemory.idalavye.com.wordmemory.utils.Login
import java.lang.Exception

object StatisticController {

    private const val STATISTICS = "statistics"
    private val statisticsRef: DatabaseReference = DatabaseRef.statisticsRef
    private var firebaseData = FirebaseDatabase.getInstance().reference
    private val statisticsList: MutableList<StatisticModel> = mutableListOf()
    var statisticsForCurrentUser: StatisticModel = StatisticModel("0", "0", 0, 0, 0, 0)

    private val listeners: MutableList<StatisticsDataChangeListener> = mutableListOf()
    var loading: Boolean? = null
    var totalRepeated: Int? = null
    var totalCorrectRepeated: Int? = null
    var dailyRepeat: Int? = null

    fun getUserStatistics() {
        loading = true
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
                            totalRepeated = statistics.totalRepeated
                            totalCorrectRepeated = statistics.totalCorrectRepeated
                        }
                    }

//                    if (statisticsForCurrentUser.dailyStatistics!![ExerciseCommon.getDateNow()] != null) {
//                        dailyRepeat = statisticsForCurrentUser.dailyStatistics!![ExerciseCommon.getDateNow()]!!.repeatedCount
//                    } else {
//                        var model: DailyStatistics? = DailyStatistics(ExerciseCommon.getDateNow(), 0, 0)
//                        firebaseData.child(STATISTICS)
//                                .child(statisticsForCurrentUser.uuid!!)
//                                .child("dailyStatistics")
//                                .child(ExerciseCommon.getDateNow())
//                                .setValue(model)
//                    }

                    loading = false
                    for (listener in listeners) {
                        listener.onStatisticsItemDataChange()
                    }
                }

                if (check == true) {
                    //Kullanıcı yeni üye olmuşsa onun için yeni bir document oluşturuyoruz.
                    addStatisticForUser(statisticsForCurrentUser!!)
                } else {
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

            fun updateStatisticsForCurrentUser(statistic: StatisticModel) {
                statistic.totalWord = WordListItemController.words.size
                statistic.totalLearnedWord = WordListItemController.words.filter { s -> s.word_progress == 100 }.size
                firebaseData.child(STATISTICS).child(statisticsForCurrentUser.uuid!!).setValue(statistic)
            }
        }
        statisticsRef!!.addValueEventListener(statisticListener)
    }

    fun updateStatisticsWithRepeatedAndCorrectRepeated(value1: Int, value2: Int) {
        statisticsForCurrentUser.totalRepeated = value1 + totalRepeated!!
        statisticsForCurrentUser.totalCorrectRepeated = value2 + totalCorrectRepeated!!
        loading = true
        firebaseData.child(STATISTICS).child(statisticsForCurrentUser.uuid!!).setValue(statisticsForCurrentUser)
    }

//    fun updateDailyActivity(value: Int, value1: Int) {
//        var model: DailyStatistics? = statisticsForCurrentUser.dailyStatistics!![ExerciseCommon.getDateNow()]!!
//        model!!.repeatedCount!!.plus(1)
//
//        firebaseData.child(STATISTICS)
//                .child(statisticsForCurrentUser.uuid!!)
//                .child("dailyStatistics")
//                .child(ExerciseCommon.getDateNow())
//                .setValue(model)
//
//    }

    fun addListenerForStatisticItemDataChange(listener: StatisticsDataChangeListener) {
        listeners.add(listener)
    }

    interface StatisticsDataChangeListener {
        fun onStatisticsItemDataChange()
    }
}