package wordmemory.idalavye.com.wordmemory.models

import java.util.*

data class WordListItemModel(
        var word: String? = null,
        var meaning: String? = null,
        var image: Int = -1,
        var uuid: String? = null,
        var _createdAt: String? = null,
        var word_progress: Int = 0,
        var createDate: Date? = null,
        var updateDate: Date? = null)