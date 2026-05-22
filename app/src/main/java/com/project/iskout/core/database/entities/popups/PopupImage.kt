package com.project.iskout.core.database.entities.popups

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.project.iskout.core.database.entities.users.User
import java.util.UUID

@Entity(
    tableName = "popup_images",
    foreignKeys = [
        ForeignKey(entity = StudentPopup::class, parentColumns = ["popup_id"], childColumns = ["popup_id"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = User::class, parentColumns = ["user_id"], childColumns = ["uploaded_by"], onDelete = ForeignKey.CASCADE)
    ]
)
data class PopupImage(
    @PrimaryKey val image_id: String = UUID.randomUUID().toString(),
    val popup_id: String,
    val uploaded_by: String,
    val file_url: String,
    val caption: String?,
    val content_status: String = "pending_review",
    val uploaded_at: Long = System.currentTimeMillis()
)