package gamefield.yourdays.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import gamefield.yourdays.data.dao.MonthDao
import gamefield.yourdays.data.entity.FirstDayOfWeek
import gamefield.yourdays.data.entity.Month

@Database(entities = [Month::class, FirstDayOfWeek::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun monthDao(): MonthDao

    companion object {

        private const val DATABASE_NAME = "emotion"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room
                .databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}
