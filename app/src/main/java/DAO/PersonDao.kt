package DAO

import Entity.Person
import androidx.room.*


@Dao
interface PersonDao {
    @Transaction
    @Query("select * from person")
    fun getAllPersons():List<Person>

    @Insert
    fun addPerson(person: Person)

    @Delete
    fun deletePerson(person: Person)
}