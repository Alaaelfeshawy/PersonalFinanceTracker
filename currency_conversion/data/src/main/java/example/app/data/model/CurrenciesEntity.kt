package example.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import example.app.currency_conversion.domain.model.CurrenciesDomainModel

@Entity(tableName = "currencies_table")
data class CurrenciesEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val success: Boolean,
    val currencies: Map<String,String>?,
)

fun CurrenciesDomainModel.toEntity() : CurrenciesEntity{
    return CurrenciesEntity(
        id = id ?: 0L,
        success = success,
        currencies = currencies
    )
}

fun CurrenciesEntity.toDomain() : CurrenciesDomainModel{
    return CurrenciesDomainModel(
        id = id,
        success = success,
        currencies = currencies ?: emptyMap()
    )
}