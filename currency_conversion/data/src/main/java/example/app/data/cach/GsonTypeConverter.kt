package example.app.data.cach

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

@ProvidedTypeConverter
class GsonTypeConverter(
    private val gson: Gson
){
    @TypeConverter
    fun fromMap(value: Map<String, String>): String = gson.toJson(value)

    @TypeConverter
    fun toMap(value: String): Map<String, String> = try {
        gson.fromJson(value, object : TypeToken<Map<String, String>>() {}.type)
    } catch (e: Exception) {
        emptyMap()
    }
}