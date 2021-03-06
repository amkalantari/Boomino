package com.core.api

import com.core.dto.ParentLoginResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by aMiir on 1/31/21
 * Drunk, Fix Later
 */
interface AccountApi {

    @GET("auth/?")
    fun parentLogin(
        @Query("username") username: String,
        @Query("password") password: String
    ): Observable<ParentLoginResponse>

}