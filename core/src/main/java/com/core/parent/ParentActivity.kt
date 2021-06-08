package com.core.parent

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.core.base.BaseActivity
import com.core.base.BaseViewModel
import com.core.dto.Status
import com.core.widget.TextInputLayoutCustom
import java.lang.ref.WeakReference


/**
 * Created by aMiir on 1/31/21
 * Drunk, Fix Later
 */
abstract class ParentActivity<T : BaseViewModel, E : ViewDataBinding> : BaseActivity<E>() {

    lateinit var viewModel: T

    abstract fun getViewModelClass(): Class<T>

    abstract fun getFactory(): ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        viewModel = ViewModelProvider(this, getFactory()).get(getViewModelClass())
        viewModel.getNetworkStatus().observe(this, {
            when (it.status) {
                Status.RUNNING -> showProgress(it.tag)
                Status.SUCCESS -> {
                    Handler(Looper.getMainLooper()).postDelayed({
                        hideProgress(it.tag)
                    }, 500)
                }
                else -> {
                    handleFailureStatus(it) { error ->
                        showError(it.tag, error)
                    }
                }
            }
        })
        viewModel.getLocationState().observe(this, {
            locationState(it)
        })
        viewModel.onLocationUpdate().observe(this, {
            locationUpdate(it)
        })
        viewModel.locationPermissionRequired().observe(this, {
            permissionsRequest(it.toTypedArray(), {
                requestStartUpdatingLocation()
            }, {
                /**
                 * cannot update location
                 */
            })
        })
        lifecycle.addObserver(viewModel)
        viewModel.onCreated()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    open fun requestStartUpdatingLocation(requestEnableSetting: Boolean? = true) {
        viewModel.requestStartUpdatingLocation(WeakReference(this), requestEnableSetting)
    }

    open fun requestStopUpdatingLocation() {
        viewModel.requestStopUpdatingLocation()
    }

    open fun onLocationPermissionGranted() {
        viewModel.onPermissionGranted(WeakReference(this))
    }

    //(app as App).restartAndLogoutApplication(app.baseContext)

}