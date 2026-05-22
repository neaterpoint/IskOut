package com.project.iskout.core.database.entities.establishments

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "discounts",
    foreignKeys = [ForeignKey(entity = Establishment::class, parentColumns = ["est_id"], childColumns = ["establishment_id"], onDelete = ForeignKey.CASCADE)],
    // ADDED: Index for establishment_id
    indices = [Index(value = ["establishment_id"])]
)
data class Discount(
    @PrimaryKey val discount_id: String = UUID.randomUUID().toString(),
    val establishment_id: String,
    val title: String,
    val details: String?,
    val discount_type: String,
    val discount_value: Double = 0.00,
    val valid_from: Long,
    val valid_until: Long,
    val is_active: Boolean = true
)