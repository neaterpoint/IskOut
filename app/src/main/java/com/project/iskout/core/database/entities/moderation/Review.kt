package com.project.iskout.core.database.entities.moderation

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index // FIX: Added Index import
import androidx.room.PrimaryKey
import com.project.iskout.core.database.entities.establishments.Establishment
import com.project.iskout.core.database.entities.users.User
import java.util.UUID

@Entity(
    tableName = "reviews",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["reviewer_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Establishment::class,
            parentColumns = ["est_id"],
            childColumns = ["establishment_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["reviewer_id"]),
        Index(value = ["establishment_id"])
    ]
)
data class Review(
    @PrimaryKey val review_id: String = UUID.randomUUID().toString(),
    val reviewer_id: String,
    val establishment_id: String,
    val rating: Int,
    val comment: String?,
    val content_status: String = "pending_review",
    val created_at: Long = System.currentTimeMillis()
)