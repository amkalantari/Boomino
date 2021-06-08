package com.core.dto

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
@Entity(tableName = "test")
data class TestDto(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "local_id", index = true) @Keep val localId: Long = 0,
    @Keep var test: String,
) : Parcelable
