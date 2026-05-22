package com.project.iskout.core.database.entities.establishments

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.project.iskout.core.database.entities.users.MerchantProfile
import java.util.UUID

@Entity(
    tableName = "establishments",
    foreignKeys = [
        ForeignKey(entity = MerchantProfile::class, parentColumns = ["profile_id"], childColumns = ["merchant_id"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = EstablishmentCategory::class, parentColumns = ["category_id"], childColumns = ["category_id"])
    ],
    // ADDED: Indices for merchant_id and category_id
    indices = [
        Index(value = ["merchant_id"]),
        Index(value = ["category_id"])
    ]
)
data class Establishment(
    @PrimaryKey val est_id: String = UUID.randomUUID().toString(),
    val merchant_id: String,
    val category_id: Int,
    val name: String,
    val description: String?,
    val latitude: Double,
    val longitude: Double,
    val address: String?,
    val avg_price: Double = 0.00,
    val is_open: Boolean = true,
    val created_at: Long = System.currentTimeMillis()
)