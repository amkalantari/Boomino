package com.amiir.boomino.util

import android.content.Context
import android.content.pm.PackageInfo
import com.core.dto.PackageDto


object PackageHelper {

    @JvmStatic
    fun getPackages(context: Context, blockList: List<String>): ArrayList<PackageDto> {
        return getInstalledApps(false, context, blockList) /* false = no system packages */
    }

    @JvmStatic
    fun getInstalledApps(
        getSysPackages: Boolean,
        context: Context,
        blockList: List<String>
    ): ArrayList<PackageDto> {
        val res: ArrayList<PackageDto> = ArrayList()
        val packs: List<PackageInfo> = context.packageManager.getInstalledPackages(0)
        for (i in packs.indices) {
            val p = packs[i]
            if (!getSysPackages && p.versionName == null) {
                continue
            }
            val newInfo = PackageDto(
                p.applicationInfo.loadLabel(context.packageManager).toString(),
                p.packageName,
                p.versionName,
                p.versionCode,
                p.applicationInfo.loadIcon(context.packageManager)
            )
            var isBlock = false
            blockList.forEach {
                if (it == newInfo.appname){
                    isBlock = true
                }
            }
            if (!isBlock)
                res.add(newInfo)
        }
        return res
    }

}

