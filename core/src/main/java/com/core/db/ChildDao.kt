package com.core.db

import androidx.room.*
import com.core.dto.ChildDto
import com.core.utils.SingleLiveEvent


@Dao
interface ChildDao {

    @Query("delete from childTable")
    fun removeChild()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertChild(config: ChildDto): Long

    @Update
    fun updateChild(config: ChildDto)

    @Transaction
    fun insert(config: ChildDto): Long {
        return insertChild(config)
    }

    @Query("select * from childTable")
    fun fetchAll(): List<ChildDto>

    @Query("select * from childTable")
    fun fetchUserBlockList(name: String): SingleLiveEvent<List<String>> {
        var a = fetchAll()
        val data = SingleLiveEvent<List<String>>()
        a.forEach {
            if (it.userName == name) {
                data.postValue(it.blockList)
            }
        }
        return data
    }

}