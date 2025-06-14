package example.app.data.api.validate

import retrofit2.Response

interface IValidateAPIResponse {
    suspend fun <T : Any> validateResponse(api: Response<T>): ApiResult<T>
}