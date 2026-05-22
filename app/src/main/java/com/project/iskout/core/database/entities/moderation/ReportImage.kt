package com.project.iskout.core.database.entities.moderation

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.project.iskout.core.database.entities.users.User
import java.util.UUID

@Entity(
    tableName = "report_images",
    foreignKeys = [
        ForeignKey(entity = Report::class, parentColumns = ["report_id"], childColumns = ["report_id"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = User::class, parentColumns = ["user_id"], childColumns = ["uploaded_by"], onDelete = ForeignKey.CASCADE)
    ]
)
data class ReportImage(
    @PrimaryKey val image_id: String = UUID.randomUUID().toString(),
    val report_id: String,
    val uploaded_by: String,
    val file_url: String,
    val uploaded_at: Long = System.currentTimeMillis()
)