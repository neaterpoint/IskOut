package com.project.iskout.core.database.entities.popups

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popup_categories")
data class PopupCategory(
    @PrimaryKey(autoGenerate = true) val category_id: Int = 0,
    val name: String
)