package com.core.base

import androidx.lifecycle.LiveData
import com.core.dto.NetworkState
import com.core.utils.SingleLiveEvent

data class ResponseAsync<T>(val onSuccess: SingleLiveEvent<T>, val networkState: LiveData<NetworkState>)

data class Response<T>(val onSuccess: T, val networkState: NetworkState)

data class ResponseLive<T>(val onSuccess: LiveData<T>, val networkState: LiveData<NetworkState>)