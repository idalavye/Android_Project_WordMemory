package wordmemory.idalavye.com.wordmemory.controllers

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import wordmemory.idalavye.com.wordmemory.database.DatabaseRef
import wordmemory.idalavye.com.wordmemory.database.DatabaseRef.statisticsRef
import wordmemory.idalavye.com.wordmemory.models.StatisticModel
import wordmemory.idalavye.com.wordmemory.utils.Login

object StatisticController {

    private val statisticsList: MutableList<StatisticModel> = mutableListOf()
    var statisticsForCurrentUser: StatisticModel? = StatisticModel("0","0",0,0,0,0)

    fun getUserStatistics() {
        val statisticListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var check:Boolean? = true
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
                    addStatisticForUser(DatabaseRef.statisticsRef, statisticsForCurrentUser!!)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Failed to read value
            }

            fun addStatisticForUser(databaseReference: DatabaseReference, statistic: StatisticModel) {
                val key = databaseReference.push().key
                statistic.uuid = key
                statistic._userId = Login.getUserId()
                statistic.totalWord = 150
                statistic.totalLearnedWord = 54
                statistic.totalRepeated = 300
                statistic.totalCorrectRepeated = 285
                databaseReference.child(key!!).setValue(statistic)
            }
        }

        statisticsRef!!.addValueEventListener(statisticListener)
    }

}