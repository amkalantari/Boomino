package com.core.utils

interface SettingManager {

    fun setRegister(isRegister: Boolean)
    fun isRegister(): Boolean

    fun setOnBoardingDisplayed(value: Boolean)
    fun isOnBoardingDisplayed(): Boolean

    fun setLngSelected(value: String)
    fun getLngSelected(): String

    fun setAccessToken(value: String)
    fun getAccessToken(): String

    fun setRefreshToken(value: String)
    fun getRefreshToken(): String

    fun setCompleteProfile(value: Boolean)
    fun getCompleteProfile(): Boolean


}

class SettingManagerImpl(private val preference: Preference) : SettingManager {

    companion object {
        private const val isRegistered = "isRegistered"
        private const val onBoardingDisplay = "onBoardingDisplay"
        private const val CompleteProfile = "CompleteProfile"
        private const val lngSelected = "lngSelected"
        private const val accessToken = "accessToken"
        private const val refreshToken = "refreshToken"
    }

    override fun setRegister(isRegister: Boolean) {
        preference.put(isRegistered, isRegister)
    }

    override fun isRegister(): Boolean = preference.getBoolean(isRegistered)

    override fun setOnBoardingDisplayed(value: Boolean) {
        preference.put(onBoardingDisplay, value)
    }

    override fun isOnBoardingDisplayed(): Boolean = preference.getBoolean(onBoardingDisplay)

    override fun setLngSelected(value: String) {
        preference.put(lngSelected, value)
    }

    override fun getLngSelected(): String = preference.getString(lngSelected)

    override fun setAccessToken(value: String) {
        preference.put(accessToken, value)
    }

    override fun getAccessToken(): String = preference.getString(accessToken)

    override fun setRefreshToken(value: String) {
        preference.put(refreshToken, value)
    }

    override fun getRefreshToken(): String = preference.getString(refreshToken)

    override fun setCompleteProfile(value: Boolean) {
        preference.put(CompleteProfile, value)
    }

    override fun getCompleteProfile(): Boolean = preference.getBoolean(CompleteProfile)

}

