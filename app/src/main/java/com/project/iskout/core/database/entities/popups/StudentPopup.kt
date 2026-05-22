package com.project.iskout.core.database.entities.popups

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.project.iskout.core.database.entities.users.StudentProfile
import java.util.UUID

@Entity(
    tableName = "student_popups",
    foreignKeys = [
        ForeignKey(entity = StudentProfile::class, parentColumns = ["profile_id"], childColumns = ["student_id"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = PopupCategory::class, parentColumns = ["category_id"], childColumns = ["category_id"])
    ],
    indices = [
        Index(value = ["student_id"]),
        Index(value = ["category_id"])
    ]
)
data class StudentPopup(
    @PrimaryKey val popup_id: String = UUID.randomUUID().toString(),
    val student_id: String,
    val category_id: Int,
    val title: String,
    val description: String?,
    val latitude: Double,
    val longitude: Double,
    val location_note: String?,
    val closes_at: Long,
    val status: String = "active",
    val content_status: String = "pending_review",
    val created_at: Long = System.currentTimeMillis()
)