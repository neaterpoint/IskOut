package com.project.iskout.core.database.entities.moderation

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.project.iskout.core.database.entities.establishments.Establishment
import com.project.iskout.core.database.entities.users.User
import java.util.UUID

@Entity(
    tableName = "images",
    foreignKeys = [
        ForeignKey(entity = Establishment::class, parentColumns = ["est_id"], childColumns = ["establishment_id"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = User::class, parentColumns = ["user_id"], childColumns = ["uploaded_by"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Review::class, parentColumns = ["review_id"], childColumns = ["review_id"], onDelete = ForeignKey.SET_NULL)
    ]
)
data class Image(
    @PrimaryKey val image_id: String = UUID.randomUUID().toString(),
    val establishment_id: String,
    val uploaded_by: String,
    val review_id: String?,
    val file_url: String,
    val caption: String?,
    val content_status: String = "pending_review",
    val uploaded_at: Long = System.currentTimeMillis()
)