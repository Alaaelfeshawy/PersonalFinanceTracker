package example.app.currency_conversion.domain.qualifiers

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.BINARY

@Qualifier
@Retention(BINARY)
annotation class MainDispatcher
