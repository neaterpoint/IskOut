package com.project.iskout.core.database.entities.popups

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.project.iskout.core.database.entities.users.User
import java.util.UUID

@Entity(
    tableName = "popup_reviews",
    foreignKeys = [
        ForeignKey(entity = StudentPopup::class, parentColumns = ["popup_id"], childColumns = ["popup_id"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = User::class, parentColumns = ["user_id"], childColumns = ["reviewer_id"], onDelete = ForeignKey.CASCADE)
    ],
    indices = [
        Index(value = ["popup_id"]),
        Index(value = ["reviewer_id"])
    ]
)
data class PopupReview(
    @PrimaryKey val review_id: String = UUID.randomUUID().toString(),
    val popup_id: String,
    val reviewer_id: String,
    val rating: Int,
    val comment: String?,
    val content_status: String = "pending_review",
    val created_at: Long = System.currentTimeMillis()
)