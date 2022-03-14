package com.example.todo.data.db

import android.content.Context
import androidx.room.*
import com.example.todo.data.db.entities.ToDoItem
import java.util.*

@Database(entities = [ToDoItem::class], version = 1)
@TypeConverters(Converters::class)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun getToDoDAO(): ToDoDAO

    companion object {
        private const val DataBaseName = "ToDoDB"
        private val LOCK = Any()

        @Volatile
        private var myDB: ToDoDatabase? = null

        operator fun invoke(context: Context) = myDB ?: synchronized(lock = LOCK) {
            myDB ?: createDB(context).also { myDB = it }
        }

        private fun createDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ToDoDatabase::class.java,
                DataBaseName
            ).build()
    }
}
