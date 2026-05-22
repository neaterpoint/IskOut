package com.project.iskout.core.database.entities.establishments

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "menu_items",
    foreignKeys = [ForeignKey(entity = Establishment::class, parentColumns = ["est_id"], childColumns = ["establishment_id"], onDelete = ForeignKey.CASCADE)],
    // ADDED: Index for establishment_id
    indices = [Index(value = ["establishment_id"])]
)
data class MenuItem(
    @PrimaryKey val item_id: String = UUID.randomUUID().toString(),
    val establishment_id: String,
    val name: String,
    val description: String?,
    val price: Double,
    val is_available: Boolean = true
)