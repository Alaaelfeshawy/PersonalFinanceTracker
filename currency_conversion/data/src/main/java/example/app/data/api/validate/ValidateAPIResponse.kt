package example.app.data.api.validate

import retrofit2.Response
import javax.inject.Inject


class ValidateAPIResponse @Inject constructor() : IValidateAPIResponse {
    override suspend fun <T : Any> validateResponse(api: Response<T>): ApiResult<T> {
        return try {
            val apiResponse = api.body()
            if (api.isSuccessful && apiResponse != null)
                ApiResult.Success(apiResponse)
            else {
                ApiResult.Error(
                    Exception("Invalid Response")

                )
            }
        } catch (e: Exception) {
           ApiResult.Error(
                exception = e
            )
        }
    }
}