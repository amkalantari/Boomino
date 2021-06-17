package com.core.repository

import android.content.ContentValues.TAG
import com.core.api.AccountApi
import com.core.base.BaseObserver
import com.core.base.BaseObserver.Companion.networkStatus
import com.core.base.Response
import com.core.dto.LoginRequestDto
import com.core.dto.ParentLoginResponse
import com.core.utils.SingleLiveEvent


/**
 * Created by aMiir on 1/31/21
 * Drunk, Fix Later
 */
abstract class AccountRepository : BaseObserver {
    abstract fun parentLogin(loginRequestDto: LoginRequestDto): Response<ParentLoginResponse>
}

class AccountRepositoryImpl(private val accountApi: AccountApi) : AccountRepository() {

    override fun parentLogin(loginRequestDto: LoginRequestDto): Response<ParentLoginResponse> {
        val tag = "${this::class.java.simpleName}_parentLogin"
        val data = SingleLiveEvent<ParentLoginResponse>()
        showProgressAction(tag)
        addExecutorThreads(accountApi.parentLogin(
            loginRequestDto.username,
            loginRequestDto.password
        ), onSuccess = { result ->
            data.postValue(result)
            hideProgressAction(tag)
        }, onError = {
            hideProgressAction(TAG)
            handleError(TAG, it)
        })
        return Response(data, networkStatus)
    }

}