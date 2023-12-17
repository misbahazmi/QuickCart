package com.misbah.quickcart.core.base

import com.misbah.quickcart.core.data.remote.APIResult
import com.nytimes.utils.AppLog
import com.misbah.quickcart.ui.utils.NoInternetException
import kotlinx.coroutines.CancellationException
import retrofit2.Response

/**
 * @author: Mohammad Misbah
 * @since: 15-Dec-2023
 * @sample: Technology Assessment for Sr. Android Role
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java/Kotlin||Flutter
 */
abstract class BaseDataSource {

    protected suspend fun <T> getResults(call: suspend ()->Response<T>) : APIResult<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return APIResult.success(body)
            }
            return error("${response.code()} : ${response.message()}")
        }
        catch (e: NoInternetException){
            return APIResult.error(message = e.message.toString())
        }
        catch (e: CancellationException){

            return error(e.message ?: e.toString())
        }
        catch (e: Exception){
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): APIResult<T> {
        AppLog.debugE(message)
        return APIResult.error(message = "Network call has failed due to the reason: $message" )
    }
}