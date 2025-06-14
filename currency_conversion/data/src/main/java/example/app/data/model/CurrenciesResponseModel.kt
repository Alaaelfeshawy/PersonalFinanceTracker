package example.app.data.model


data class CurrenciesResponseModel(
    val success: Boolean?,
    val currencies: Map<String,String>?,
)