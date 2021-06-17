package com.amiir.boomino.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.core.base.BaseViewModel
import com.core.db.ChildDao
import com.core.dto.ChildDto

abstract class MainViewModel : BaseViewModel() {

    abstract fun getBlockList(username: String): LiveData<List<String>>

    abstract fun insertChild(childDto: ChildDto)

    abstract fun setUsername(username: String)
    abstract fun getUsername() : String

}

class MainViewModelImpl(
    private var childDao: ChildDao
) : MainViewModel() {

    var user : String = ""

    override fun getBlockList(username: String): LiveData<List<String>> {
        return childDao.fetchUserBlockList(username)
    }

    override fun insertChild(childDto: ChildDto) {
        childDao.insert(childDto)
    }

    override fun setUsername(username: String) {
        this.user =username
    }

    override fun getUsername(): String = user
}

class MainViewModelFactory(
    private var childDao: ChildDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MainViewModelImpl(childDao) as T
    }
}