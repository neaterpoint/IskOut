package com.project.iskout.core.database.entities.users

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID


@Entity(
    tableName = "sessions",
    foreignKeys = [ForeignKey(entity = User::class, parentColumns = ["user_id"], childColumns = ["user_id"], onDelete = ForeignKey.CASCADE)],
    // ADDED: Index for user_id
    indices = [Index(value = ["user_id"])]
)
data class Session(
    @PrimaryKey val session_id: String = UUID.randomUUID().toString(),
    val user_id: String,
    val token_hash: String,
    val created_at: Long = System.currentTimeMillis(),
    val expires_at: Long,
    val revoked_at: Long? = null
)