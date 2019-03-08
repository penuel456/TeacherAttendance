package usc.dcis.teacherattendancesystem.userDatabase

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "Users")
data class userDB(
    @PrimaryKey(autoGenerate = true) val userID: Int = 0,
    @ColumnInfo(name = "id_number") val idNumber: Int = 0,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "type") var type: String
)