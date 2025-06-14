package example.app.currency_conversion.domain.model

data class CurrenciesDomainModel(
    val id : Long?=null,
    val success: Boolean,
    val currencies: Map<String,String>,
)