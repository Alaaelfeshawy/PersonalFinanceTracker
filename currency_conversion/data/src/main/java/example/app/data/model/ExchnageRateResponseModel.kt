package example.app.data.model

import example.app.currency_conversion.domain.model.ExchangeRateDomainModel
import example.app.currency_conversion.domain.model.InfoDomainModel
import example.app.currency_conversion.domain.model.QueryDomainModel

data class ExchangeRateResponseModel(
    val info: Info?,
    val query: Query?,
    val result: Double?,
    val success: Boolean?,
)

data class Info(
    val quote: Double?,
    val timestamp: Int?
)

data class Query(
    val amount: Int?,
    val from: String?,
    val to: String?
)

fun ExchangeRateResponseModel.toDomain() : ExchangeRateDomainModel{
    return ExchangeRateDomainModel(
        info = info?.toDomain() ,
        query = query?.toDomain(),
        result = result ?: 0.0,
        success = success ?: false
    )
}

fun Info.toDomain() : InfoDomainModel{
    return InfoDomainModel(
        quote = quote ?: 0.0,
        timestamp =timestamp ?: 0
    )
}

fun Query.toDomain() : QueryDomainModel{
    return QueryDomainModel(
        amount = amount ?: 0,
        from = from.orEmpty(),
        to = to.orEmpty()
    )
}