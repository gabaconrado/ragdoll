package conrado.gabriel.ragdoll.data.source.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import conrado.gabriel.ragdoll.data.Towel

@Database(entities = [Towel::class], version = 1, exportSchema = false)
abstract class RagdollDatabase : RoomDatabase() {

    abstract fun towelDao(): TowelDao

    companion object {

        @Volatile
        private var INSTANCE: RagdollDatabase? = null

        fun getInstance(context: Context): RagdollDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            RagdollDatabase::class.java, "ragdoll-db.db"
        ).allowMainThreadQueries().build()

    }

}