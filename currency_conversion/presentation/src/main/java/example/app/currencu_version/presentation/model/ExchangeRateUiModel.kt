package example.app.currencu_version.presentation.model

import example.app.currency_conversion.domain.model.ExchangeRateDomainModel
import example.app.currency_conversion.domain.model.InfoDomainModel
import example.app.currency_conversion.domain.model.QueryDomainModel

data class ExchangeRateUiModel(
    val info: InfoUiModel?,
    val query: QueryUiModel?,
    val result: Double,
    val success: Boolean,
)

data class InfoUiModel(
    val quote: Double,
    val timestamp: Int
)

data class QueryUiModel(
    val amount: Int,
    val from: String,
    val to: String
)


fun ExchangeRateDomainModel.toUI() : ExchangeRateUiModel {
    return ExchangeRateUiModel(
        info = info?.toDomain(),
        query = query?.toDomain(),
        result = result,
        success = success
    )
}

fun InfoDomainModel.toDomain() : InfoUiModel {
    return InfoUiModel(
        quote = quote,
        timestamp = timestamp
    )
}

fun QueryDomainModel.toDomain() : QueryUiModel {
    return QueryUiModel(
        amount = amount,
        from = from,
        to = to
    )
}