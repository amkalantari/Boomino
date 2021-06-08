package com.core.base

import android.app.Activity
import androidx.lifecycle.*
import com.core.dto.NetworkState
import com.core.dto.location.Coordinate
import com.core.dto.location.LocationState
import com.core.utils.LocationHelper
import com.core.utils.LocationHelperImpl
import java.lang.ref.WeakReference

/**
 * Created by aMiir on 1/31/21
 * Drunk, Fix Later
 */
abstract class BaseViewModel : ViewModel(), LifecycleObserver, BaseObserver {

    private var locationHelper: LocationHelper = LocationHelperImpl()

    private val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    open fun getLocationState(): MutableLiveData<LocationState> = locationHelper.getLocationState()

    open fun onLocationUpdate(): MutableLiveData<Coordinate> = locationHelper.onLocationUpdate()

    open fun locationPermissionRequired(): MutableLiveData<ArrayList<String>> =
        locationHelper.locationPermissionRequired()

    open fun getNetworkStatus(): LiveData<NetworkState> = networkState

    open fun requestStartUpdatingLocation(
        activity: WeakReference<Activity>,
        requestEnable: Boolean? = true,
        fastInterval: Long? = 1 * 1000,
        interval: Long? = 1 * 1000
    ) {
        locationHelper.requestStartUpdatingLocation(activity, requestEnable, fastInterval, interval)
    }

    open fun requestStopUpdatingLocation() {
        locationHelper.requestStopUpdatingLocation()
    }

    open fun onPermissionGranted(activity: WeakReference<Activity>) {
        locationHelper.onPermissionGranted(activity)
    }

    open fun showingProgress(tag: String) {
        networkState.postValue(NetworkState.loading(tag))
    }

    open fun hideProgress(tag: String) {
        networkState.postValue(NetworkState.loaded(tag))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onCreated() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy() {
        locationHelper.requestStopUpdatingLocation()
    }

}