package conrado.gabriel.ragdoll.data.source.room

import androidx.annotation.Nullable
import androidx.room.*
import conrado.gabriel.ragdoll.data.Towel

@Dao
interface TowelDao {

    @Query("select * from towel")
    fun getAll(): List<Towel>

    @Query("select * from  towel where id = :id")
    @Nullable
    fun getById(id: String): Towel?

    @Insert
    fun insertAll(vararg towels: Towel)

    @Delete
    fun delete(towel: Towel)

    @Update
    fun update(vararg towels: Towel)
}