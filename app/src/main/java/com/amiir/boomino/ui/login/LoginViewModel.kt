package com.amiir.boomino.ui.login

import androidx.lifecycle.*
import com.core.base.BaseViewModel
import com.core.dto.LoginRequestDto
import com.core.dto.NetworkState
import com.core.repository.AccountRepository
import com.core.utils.SettingManager

/**
 * Created by aMiir on 1/31/21
 * Drunk, Fix Later
 */

abstract class LoginViewModel :
    BaseViewModel() {

    abstract fun getParentLoginResponse(): LiveData<String>

    abstract fun requestParentLogin(hashValue: String, mobile: String)

}

class LoginViewModelImpl(
    private var settingManager: SettingManager,
    private var accountRepository: AccountRepository,

    ) : LoginViewModel() {


    private val parentLogin: MutableLiveData<LoginRequestDto> = MutableLiveData()

    private val parentLoginRepo = Transformations.map(parentLogin) {
        accountRepository.parentLogin(it)
    }

    override fun getNetworkStatus(): LiveData<NetworkState> =
        MediatorLiveData<NetworkState>().apply {
            this.addSource(Transformations.switchMap(parentLoginRepo) { it.networkState }) {
                this.postValue(it)
            }
        }


    override fun getParentLoginResponse(): LiveData<String> =
        Transformations.switchMap(parentLoginRepo) {
            it.onSuccess
        }

    override fun requestParentLogin(username: String, password: String) {
        parentLogin.postValue(LoginRequestDto(username, password))
    }
}

class LoginViewModelFactory(
    private var settingManager: SettingManager,
    private var accountRepository: AccountRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return LoginViewModelImpl(settingManager, accountRepository) as T
    }
}