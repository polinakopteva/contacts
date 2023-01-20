package contacts

import java.time.LocalDateTime

class Action {
    fun add(contacts: MutableList<Contact>) {
        println("Enter the type (person, organization):")
        val contact = when (readLine()) {
            "person" -> createPersonContact()
            "organization" -> createOrganizationContact()
            else -> throw GameExitException()
        }
        contacts.add(contact)
        println("The record added.")
    }

    private fun createOrganizationContact(): Contact {
        val organizationContact = OrganizationContact()
        organizationContact.apply {
            println("Enter the organization name:")
            this.organizationName = readLine()
            println("Enter the address:")
            this.address = readLine()
            println("Enter the number:")
            this.number = readLine()
            if (this.number == NO_NUMBER) {
                println("Wrong number format!")
            }
            this.timeCreate = LocalDateTime.now().format(DATE_TIME_FORMATTER)
            this.timeEdit = LocalDateTime.now().format(DATE_TIME_FORMATTER)
        }
        return organizationContact
    }

    private fun createPersonContact(): Contact {
        val personContact = PersonContact()
        personContact.apply {
            println("Enter the name of the person:")
            this.name = readLine()
            println("Enter the surname of the person:")
            this.surname = readLine()
            println("Enter the birth date:")
            this.birthDate = readLine()
            if (this.birthDate == NO_DATA) {
                println("Bad birth date!")
            }
            println("Enter the gender (M, F):")
            this.gender = readLine()
            if (this.gender == NO_DATA) {
                println("Bad gender!")
            }
            println("Enter the number:")
            this.number = readLine()
            if (this.number == NO_NUMBER) {
                println("Wrong number format!")
            }
            this.timeCreate = LocalDateTime.now().format(DATE_TIME_FORMATTER)
            this.timeEdit = LocalDateTime.now().format(DATE_TIME_FORMATTER)
        }
        return personContact
    }

    fun count(contacts: MutableList<Contact>) {
        println("The Phone Book has ${contacts.size} records.")
    }

    fun list(contacts: MutableList<Contact>) {
        for (i in contacts.indices) {
            println("${i+1}. ${contacts[i].getDisplayName()}")
        }
        println("[list] Enter action ([number], back)")
        val input = readLine()
        val filteredContacts = contacts
        when (input) {
            "back" -> print("")
             else -> infoEdit(filteredContacts, input, contacts)
        }
    }

    private fun edit(contact: Contact) {
        println("Select a field (${contact.getFields()}):")
        val fieldName = readLine()
        println("Enter $fieldName")
        val value = readLine()
        contact.updateField(fieldName, value)
        println("Saved")
        println(contact.getInfo())
        println()
    }

    fun search(contacts: MutableList<Contact>) {
        var searchCommand = "again"
        var filteredContacts: MutableList<Contact> = mutableListOf()
        while (true) {
            when(searchCommand) {
                "again" -> {
                    println("[search] Enter search query:")
                    val searchQuery = readLine().lowercase()
                    filteredContacts = contacts.filter { it.appendFields().contains(searchQuery) }.toMutableList()
                    println("Found ${filteredContacts.size} results:")
                    list(filteredContacts)
                    println()
                }
                "back" -> {
                    break
                }
                else -> {
                    infoEdit(filteredContacts, searchCommand, contacts)
                    break
                }
            }
            println("[search] Enter action ([number], back, again):")
            searchCommand = readLine()
        }
    }

    private fun infoEdit(filteredContacts: MutableList<Contact>, searchAction: String, contacts: MutableList<Contact>) {
        val index = searchAction.toInt() - 1
        val editContact = filteredContacts[index]
        println(editContact.getInfo())
        println()
        while (true) {
            println("[record] Enter action (edit, delete, menu)")
            when (readLine()) {
                "edit" -> edit(editContact)
                "delete" -> contacts.remove(editContact)
                else -> break
            }
        }
    }
}
