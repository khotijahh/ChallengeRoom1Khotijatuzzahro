package com.example.challengeroom1khotijatuzzahro.Room

import androidx.room.*

@Dao
interface tbSiswaDAO {
    @Insert
    suspend fun addtbSiswa (tbSis: tbSiswa)

    @Update
    suspend fun updatetbSiswa (tbSis: tbSiswa)

    @Delete
    suspend fun deletetbSiswa (tbSis: tbSiswa)

    @Query("SELECT * FROM tbSiswa")
    suspend fun gettbSiswa():List<tbSiswa>

    @Query("SELECT * FROM tbSiswa WHERE nis=:tbSiswa_nis")
    suspend fun gettbSiswa(tbSiswa_nis: Int): List<tbSiswa>


}