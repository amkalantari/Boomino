package com.amiir.boomino.ui.login

import androidx.lifecycle.*
import com.core.base.BaseViewModel
import com.core.db.ChildDao
import com.core.dto.ChildDto
import com.core.dto.LoginRequestDto
import com.core.dto.NetworkState
import com.core.dto.ParentLoginResponse
import com.core.repository.AccountRepository

/**
 * Created by aMiir on 1/31/21
 * Drunk, Fix Later
 */

abstract class LoginViewModel :
    BaseViewModel() {

    abstract fun getParentLoginResponse(): LiveData<ParentLoginResponse>
    abstract fun requestParentLogin(username: String, password: String)

    abstract fun getChildList(): List<ChildDto>

}

class LoginViewModelImpl(
    private var childDao: ChildDao,
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


    override fun getParentLoginResponse(): LiveData<ParentLoginResponse> =
        Transformations.switchMap(parentLoginRepo) {
            it.onSuccess
        }

    override fun requestParentLogin(username: String, password: String) {
        parentLogin.postValue(LoginRequestDto(username, password))
    }

    override fun getChildList(): List<ChildDto> {
        return childDao.fetchAll()
    }
}

class LoginViewModelFactory(
    private var childDao: ChildDao,
    private var accountRepository: AccountRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return LoginViewModelImpl(childDao, accountRepository) as T
    }
}