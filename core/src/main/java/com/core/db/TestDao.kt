package com.core.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.core.dto.TestDto


@Dao
interface TestDao {

    @Query("delete from test")
    fun removeTests()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTests(config: TestDto): Long

    @Update
    fun updateTests(config: TestDto)

    @Transaction
    fun insert(config: TestDto): Long {
        removeTests()
        return insertTests(config)
    }

    @Query("select * from test")
    fun fetchAll(): List<TestDto>

    @Query("select * from test limit 1")
    fun fetchTest(): LiveData<TestDto>
}