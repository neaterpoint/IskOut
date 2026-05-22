package com.project.iskout.core.database.entities.users

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "verification_documents",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["user_id"], childColumns = ["user_id"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = User::class, parentColumns = ["user_id"], childColumns = ["reviewed_by"], onDelete = ForeignKey.SET_NULL)
    ]
)
data class VerificationDocument(
    @PrimaryKey val doc_id: String = UUID.randomUUID().toString(),
    val user_id: String,
    val reviewed_by: String?,
    val doc_type: String,
    val file_url: String,
    val status: String = "pending",
    val notes: String?,
    val submitted_at: Long = System.currentTimeMillis(),
    val reviewed_at: Long? = null
)