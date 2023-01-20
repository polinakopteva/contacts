package contacts

import java.io.File
import com.squareup.moshi.*
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

fun main(args: Array<String>) {
    val action = Action()

    val contactsAdapter = createContactsAdapter()

    val fileName = args.firstOrNull()
    val contacts = readContactsFromFile(fileName, contactsAdapter)

    loop@while (true) {
        try {
            println("[menu] Enter action (add, list, search, count, exit):")
            when (readLine()) {
                "add" -> {
                    action.add(contacts)
                    updateFile(fileName, contactsAdapter, contacts)
                    println()
                    continue
                }
                "search" -> {
                    action.search(contacts)
                    println()
                }
                "count" -> {
                    action.count(contacts)
                    println()
                    continue
                }
                "list" -> {
                    action.list(contacts)
                    println()
                    continue
                }
                else -> {
                    println("Unknown action")
                    println()
                    continue
                }
            }
        } catch (e: GameExitException) {
            break@loop
        }
    }
}

fun updateFile(fileName: String?, contactsAdapter: JsonAdapter<MutableList<Contact>>, contacts: MutableList<Contact>) {
    if (fileName != null) {
        val file = File(fileName)
        file.writeText(contactsAdapter.toJson(contacts))
    }
}

fun createContactsAdapter(): JsonAdapter<MutableList<Contact>> {
    val moshi: Moshi = Moshi.Builder()
        .add(
            PolymorphicJsonAdapterFactory.of(
                Contact::class.java, "type")
                .withSubtype(
                    PersonContact::class.java, ContactType.PersonType.name)
                .withSubtype(
                    OrganizationContact::class.java, ContactType.OrganizationType.name))
        .add(KotlinJsonAdapterFactory()).build()
    val type = Types.newParameterizedType(MutableList::class.java, Contact::class.java)
    return moshi.adapter<MutableList<Contact>>(type)
}

fun readContactsFromFile(fileName: String?, adapter: JsonAdapter<MutableList<Contact>>): MutableList<Contact> {
    if (fileName == null) {
        return mutableListOf()
    }
    val file = File(fileName)
    if (file.exists()) {
        val documentContact = file.readText()
        println("open $fileName")
        return adapter.fromJson(documentContact)!!
    }
    return mutableListOf()
}

