package gamefield.yourdays.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import gamefield.yourdays.data.entity.Week

class Converters {

    @TypeConverter
    fun fromStringToWeekList(listOfWeeksGson: String): List<Week> {
        val type = object : TypeToken<List<Week>>() {}.type
        return Gson().fromJson(listOfWeeksGson, type)
    }

    @TypeConverter
    fun fromWeekListToString(listOfWeek: List<Week>): String = Gson().toJson(listOfWeek)

}