package com.example.assignment1.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PresetDao {
    //In the case of an insert-conflict, the new item is ignored
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(preset: Preset)

    @Update
    suspend fun update(preset: Preset)

    @Delete
    suspend fun delete(preset: Preset)

    @Query("SELECT * from presets ORDER BY name ASC")
    fun getAllPresets(): Flow<List<Preset>>
}