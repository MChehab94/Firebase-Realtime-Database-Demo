package mchehab.com.kotlinapp

import org.parceler.Parcel
import org.parceler.ParcelConstructor
import org.parceler.ParcelProperty

/**
 * Created by muhammadchehab on 1/12/18.
 */
@Parcel
data class Person @ParcelConstructor
constructor(@ParcelProperty("key") var key: String,
            @ParcelProperty("firstName") var firstName: String,
            @ParcelProperty("lastName") var lastName: String,
            @ParcelProperty("age") var age: Int){
    constructor(): this("","","", 0)

    fun copy(person: Person){
        key = person.key
        firstName = person.firstName
        lastName = person.lastName
        age = person.age
    }

    @Override override fun equals(other: Any?): Boolean {
        if (other == null)
            return false
        if (!Person::class.java.isAssignableFrom(other.javaClass))
            return false
        val person = other as Person
        return person.key == key
    }
}