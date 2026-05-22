package com.project.iskout.core.database.entities.moderation

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.project.iskout.core.database.entities.users.User
import java.util.UUID

@Entity(
    tableName = "notifications",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["user_id"], childColumns = ["user_id"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Report::class, parentColumns = ["report_id"], childColumns = ["report_id"], onDelete = ForeignKey.SET_NULL)
    ],
    indices = [
        Index(value = ["user_id"]),
        Index(value = ["report_id"])
    ]
)
data class Notification(
    @PrimaryKey val notification_id: String = UUID.randomUUID().toString(),
    val user_id: String,
    val report_id: String?,
    val message_title: String,
    val message: String,
    val is_read: Boolean = false,
    val created_at: Long = System.currentTimeMillis()
)