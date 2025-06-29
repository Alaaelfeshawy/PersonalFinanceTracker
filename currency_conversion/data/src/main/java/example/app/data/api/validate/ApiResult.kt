package example.app.data.api.validate

sealed class ApiResult<out T> {

    data class Success<out T>(val data: T) : ApiResult<T>()

    data class Error(val exception: Exception) : ApiResult<Nothing>()
}