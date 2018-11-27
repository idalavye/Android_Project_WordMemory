package wordmemory.idalavye.com.wordmemory.models

data class StatisticModel(
        var uuid: String? = null,
        var _userId: String? = null,
        var totalWord: Int = 0,
        var totalLearnedWord: Int = 0,
        var totalRepeated: Int = 0,
        var totalCorrectRepeated: Int = 0)