package com.core.base


import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.MutableLiveData
import com.core.dto.ErrorType
import com.core.dto.NetworkState
import com.core.dto.ResultDto
import com.google.gson.JsonSyntaxException
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import timber.log.Timber
import java.io.EOFException
import java.io.File
import java.io.IOException
import java.net.UnknownServiceException
import java.util.concurrent.Executors

/**
 * Created by aMiir on 1/31/21
 * Drunk, Fix Later
 */
interface BaseObserver {

    companion object {

        private val disposables = CompositeDisposable()

        val networkStatus: MutableLiveData<NetworkState> = MutableLiveData()

        private val ioThreads =
            Executors.newFixedThreadPool((Runtime.getRuntime().availableProcessors() * 2))
    }

    open fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    open fun disposeDisposables() {
        //disposables.clear()
    }

    open fun showProgressAction(tag: String): NetworkState {
        val network = NetworkState.loading(tag)
        networkStatus.postValue(network)
        return network
    }

    open fun hideProgressAction(tag: String): NetworkState {
        val network = NetworkState.loaded(tag)
        networkStatus.postValue(network)
        return network
    }

    open fun File.toMultiPart(name: String = "file"): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            name,
            this.name,
            this.asRequestBody("image/*".toMediaType())
        )
    }

    fun <T> addExecutorThreads(
        observable: Maybe<T>,
        onSuccess: ((T) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null
    ) {
        addDisposable(
            observable
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.from(ioThreads))
                .subscribe({ result ->
                    onSuccess?.let {
                        onSuccess(result)
                    }
                }, { throwable ->
                    Timber.e(throwable)
                    onError?.let {
                        onError(throwable)
                    }
                })
        )
    }

    fun <T> addExecutorThreads(
        observable: Single<T>,
        onSuccess: ((T) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null
    ) {
        addDisposable(
            observable
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.from(ioThreads))
                .subscribe({ result ->
                    onSuccess?.let {
                        onSuccess(result)
                    }
                }, { throwable ->
                    Timber.e(throwable)
                    onError?.let {
                        onError(throwable)
                    }
                })
        )
    }

    fun <T> addExecutorThreads(
        observable: Observable<T>,
        onSuccess: ((T) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null
    ) {
        addDisposable(
            observable
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.from(ioThreads))
                .subscribe({ result ->
                    onSuccess?.let {
                        onSuccess(result)
                    }
                }, { throwable ->
                    Timber.e(throwable)
                    onError?.let {
                        onError(throwable)
                    }
                })
        )
    }

    fun addExecutorThreads(
        observable: Completable,
        onSuccess: (() -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null
    ) {
        addDisposable(
            observable
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.from(ioThreads))
                .subscribe({
                    onSuccess?.let {
                        onSuccess()
                    }
                }, { throwable ->
                    Timber.e(throwable)
                    onError?.let {
                        onError(throwable)
                    }
                })
        )
    }


    fun <T> handlerResultAsync(
        tag: String,
        result: ResultDto<T>,
        onSuccess: (T) -> Unit
    ): NetworkState = handlerResultAsync(tag, result, onSuccess, null)

    fun <T> handlerResultAsync(
        tag: String,
        result: ResultDto<T>,
        onSuccess: (T) -> Unit,
        onError: ((Throwable) -> Unit)? = null
    ): NetworkState {
        return if (result.code == 200) {
            val data = result.data
            if (data != null) {
                onSuccess(data)
                hideProgressAction(tag)
            } else {
                NetworkState.error(ErrorType.NullPointException, tag = tag, result.message ?: "")
                    .also {
                        onError?.let { it(NullPointerException()) }
                        networkStatus.postValue(it)
                    }
            }
        } else {
            if (!result.message.isNullOrEmpty()) {
                val network =
                    NetworkState.error(ErrorType.Undefine, tag = tag, result.message ?: "")
                onError?.invoke(UnknownServiceException())
                networkStatus.postValue(network)
                network
            } else {
                val network: NetworkState = when (result.code) {
                    401 -> NetworkState.error(ErrorType.Authorization, tag = tag)
                    403 -> NetworkState.error(ErrorType.Forbidden, tag = tag)
                    else -> NetworkState.error(ErrorType.Undefine, tag = tag)
                }
                onError?.invoke(UnknownServiceException())
                networkStatus.postValue(network)
                network
            }
        }
    }


    fun <T> handlerResult(tag: String, result: ResultDto<T>): Response<T?> {
        if (result.code == 200) {
            val data = result.data
            return if (data != null) {
                hideProgressAction(tag)
                Response(result.data, NetworkState.loaded(tag))
            } else {
                Response(
                    result.data,
                    NetworkState.error(
                        ErrorType.NullPointException,
                        tag = tag,
                        result.message ?: ""
                    )
                )
            }
        } else {
            return Response(
                result.data, when (result.code) {
                    401 -> NetworkState.error(ErrorType.Authorization, tag = tag)
                    403 -> NetworkState.error(ErrorType.Forbidden, tag = tag)
                    else -> NetworkState.error(
                        ErrorType.Undefine,
                        tag = tag,
                        msg = result.message ?: ""
                    )
                }
            )
        }
    }

    fun handleError(tag: String, t: Throwable?): NetworkState {
        hideProgressAction(tag)
        val network: NetworkState = when (t) {
            is EOFException -> NetworkState.error(ErrorType.EOFException, tag = tag)
            is IOException -> NetworkState.error(ErrorType.IOException, tag = tag)
            is SQLiteConstraintException -> NetworkState.error(
                ErrorType.SQLiteException,
                tag = tag
            )
            is HttpException -> when {
                t.code() == 401 -> NetworkState.error(ErrorType.Authorization, tag = tag)
                t.code() == 403 -> NetworkState.error(ErrorType.Forbidden, tag = tag)
                else -> NetworkState.error(ErrorType.Undefine, tag = tag)
            }
            is JsonSyntaxException -> NetworkState.error(
                ErrorType.JsonSyntaxException,
                tag = tag
            )
            else -> NetworkState.error(ErrorType.Undefine, tag = tag)
        }
        networkStatus.postValue(network)
        return network
    }
}