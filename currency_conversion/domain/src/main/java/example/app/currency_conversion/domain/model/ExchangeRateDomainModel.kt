package example.app.currency_conversion.domain.model

data class ExchangeRateDomainModel(
    val info: InfoDomainModel?,
    val query: QueryDomainModel?,
    val result: Double,
    val success: Boolean,
)

data class InfoDomainModel(
    val quote: Double,
    val timestamp: Int
)

data class QueryDomainModel(
    val amount: Int,
    val from: String,
    val to: String
)