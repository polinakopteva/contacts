package contacts

import java.time.format.DateTimeFormatter

val DATE_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
val NO_DATA: String = "[no data]"
val NO_NUMBER: String = "[no number]"

abstract class Contact {
    private val numberPattern = Regex("""^\+?(\w{1,}[\s-]?)?(\(?\w{2,}\)?)?([\s-])?(\w{2,})?([\s-])?(\w{2,})?([\s-])?(\w{2,})?$""")

    abstract val type: ContactType

    var timeCreate: String = ""

    var timeEdit: String = ""

    var number: String = "[no number]"
        set(value) {
            if (numberPattern.matches(value)) {
                field = value
            } else {
                field = NO_NUMBER
            }
        }

    open fun getDisplayName(): String {
        return ""
    }

    open fun getInfo(): String {
        return """Number: $number
            |Time created: $timeCreate
            |Time last edit: $timeEdit""".trimMargin()
    }

    abstract fun getFields(): String

    abstract fun updateField(fieldName: String, value: String)

    abstract fun appendFields(): String
}