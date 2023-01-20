package contacts

import java.time.LocalDateTime

class OrganizationContact: Contact() {
    var organizationName: String = ""
    var address: String = ""
    override val type: ContactType = ContactType.OrganizationType

    override fun getDisplayName(): String = organizationName

    override fun getInfo(): String {
        return """Organization name: $organizationName
        |Address: $address
        |${super.getInfo()}
        """.trimMargin()
    }

    override fun getFields(): String = "name, address, number"

    override fun updateField(fieldName: String, value: String) {
        when (fieldName) {
            "name" -> {
                this.organizationName = value
            }
            "address" -> {
                this.address = value
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
        return "$organizationName $address $number".lowercase()
    }
}