package com.project.iskout.core.database.entities.establishments

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "establishment_categories")
data class EstablishmentCategory(
    @PrimaryKey(autoGenerate = true) val category_id: Int = 0,
    val name: String
)