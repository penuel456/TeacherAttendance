package usc.dcis.teacherattendancesystem.userDatabase

import android.arch.persistence.room.*
import java.util.*


@Dao
interface UserDAO {
    @Insert
    fun insertUser(vararg userDB: userDB)

    @Update
    fun updateUser(vararg userDB: userDB)

    @Query("SELECT * FROM Users WHERE id_number = :idNumber AND password = :password")
    fun getUserOnLogin(idNumber: Int, password: String): userDB

    @Query("SELECT COUNT(*) FROM Users WHERE id_number = :idNumber AND password = :password")
    fun getUserCountOnLogin(idNumber: Int, password: String): Int

    @Query("SELECT * FROM Users")
    fun getAllUsers(): List<userDB>

}