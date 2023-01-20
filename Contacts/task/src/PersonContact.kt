package contacts

import java.time.LocalDateTime

class PersonContact: Contact() {
    private val birthDatePattern = Regex("""^(?:(?:31(\/|-|\.)(?:0?[13578]|1[02]))\1|(?:(?:29|30)(\/|-|\.)(?:0?[13-9]|1[0-2])\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})${'$'}|^(?:29(\/|-|\.)0?2\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))${'$'}|^(?:0?[1-9]|1\d|2[0-8])(\/|-|\.)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})${'$'}""")

    override val type: ContactType = ContactType.PersonType

    var name: String = ""

    var surname: String = ""

    var gender: String = ""
        set(value) {
            if (value == "F" || value == "M") {
                field = value
            } else {
                field = NO_DATA
            }
        }

    var birthDate: String = ""
        set(value) {
            if (birthDatePattern.matches(value)) {
                field = value
            } else {
                field = NO_DATA
            }
        }

    override fun getDisplayName(): String = "$name $surname"

    override fun getInfo(): String {
        return """Name: $name
            |Surname: $surname
            |Birth date: $birthDate
            |Gender: $gender
            |${super.getInfo()}
        """.trimMargin()
    }

    override fun getFields(): String = "name, surname, birth, gender, number"

    override fun updateField(fieldName: String, value: String) {
        when (fieldName) {
            "name" -> {
                this.name = value
            }
            "surname" -> {
                this.surname = value
            }
            "birth" -> {
                this.birthDate = value
            }
            "gender" -> {
                this.gender = value
            }
            "number" -> {
                this.number = value
            }
            else -> {
                println("Invalid option")
            }
        }
        this.timeEdit = LocalDateTime.now().format(DATE_TIME_FORMATTER)
    }

    override fun appendFields(): String {
        return "$name $surname $birthDate $gender $number".lowercase()
    }
}