package wordmemory.idalavye.com.wordmemory.controllers

import android.view.View
import com.google.firebase.database.*
import wordmemory.idalavye.com.wordmemory.database.DatabaseRef
import wordmemory.idalavye.com.wordmemory.models.StatisticModel
import wordmemory.idalavye.com.wordmemory.utils.Login

object StatisticController {

    private const val STATISTICS = "statistics"
    private val statisticsRef: DatabaseReference = DatabaseRef.statisticsRef
    private var firebaseData = FirebaseDatabase.getInstance().reference
    private val statisticsList: MutableList<StatisticModel> = mutableListOf()
    var statisticsForCurrentUser: StatisticModel = StatisticModel("0", "0", 0, 0, 0, 0)

    private val listeners: MutableList<StatisticsDataChangeListener> = mutableListOf()
    var loading:Boolean? = null

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
                        }
                    }

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
                loading = true
                statistic.totalWord = WordListItemController.words.size
                statistic.totalLearnedWord = WordListItemController.words.filter { s -> s.word_progress == 100 }.size

                firebaseData.child(STATISTICS).child(statisticsForCurrentUser.uuid!!).setValue(statistic)
            }
        }
        statisticsRef!!.addValueEventListener(statisticListener)
    }

    fun updateStatisticsWithRepeatedAndCorrectRepeated(value1: Int, value2: Int) {
        statisticsForCurrentUser.totalRepeated = value1
        statisticsForCurrentUser.totalCorrectRepeated = value2
        loading = true
        firebaseData.child(STATISTICS).child(statisticsForCurrentUser.uuid!!).setValue(statisticsForCurrentUser)
    }

    fun addListenerForStatisticItemDataChange(listener: StatisticsDataChangeListener) {
        listeners.add(listener)
    }

    interface StatisticsDataChangeListener {
        fun onStatisticsItemDataChange()
    }
}