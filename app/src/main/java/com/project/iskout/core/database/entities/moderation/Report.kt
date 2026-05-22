package com.project.iskout.core.database.entities.moderation

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.project.iskout.core.database.entities.users.User
import java.util.UUID

@Entity(
    tableName = "reports",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["user_id"], childColumns = ["reported_by"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = User::class, parentColumns = ["user_id"], childColumns = ["reviewed_by"], onDelete = ForeignKey.SET_NULL)
    ],
    indices = [
        Index(value = ["reported_by"]),
        Index(value = ["reviewed_by"])
    ]
)
data class Report(
    @PrimaryKey val report_id: String = UUID.randomUUID().toString(),
    val reported_by: String,
    val reviewed_by: String?,
    val target_type: String, // 'popup' or 'establishment'
    val target_id: String,
    val reason: String,
    val details: String?,
    val status: String = "pending",
    val reported_at: Long = System.currentTimeMillis(),
    val reviewed_at: Long? = null
)