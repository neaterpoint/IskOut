package com.project.iskout.core.database.entities.users

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "users", indices = [Index("email", unique = true), Index(
    "username",
    unique = true
)])
data class User(
    @PrimaryKey val user_id: String = UUID.randomUUID().toString(),
    val email: String,
    val username: String,
    val password_hash: String,
    val full_name: String,
    val role: String,
    val account_status: String = "pending",
    val created_at: Long = System.currentTimeMillis()
)