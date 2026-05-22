package com.project.iskout.core.database.entities.users

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "student_profiles",
    foreignKeys = [ForeignKey(entity = User::class, parentColumns = ["user_id"], childColumns = ["user_id"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("user_id", unique = true)]
)
data class StudentProfile(
    @PrimaryKey val profile_id: String = UUID.randomUUID().toString(),
    val user_id: String,
    val school_name: String?,
    val student_number: String?,
    val community_rating: Double = 0.00
)