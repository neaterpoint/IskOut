package com.project.iskout.homepage.list

interface SpotsListContract {
    interface View {
        fun showSpots(spots: List<SpotListItem>)
        fun updateFilterUI(selectedFilter: String) // NEW: Updates the active chip colors
        fun showError(message: String)
    }

    interface Presenter {
        fun loadSpots()
        fun onFilterClicked(filterType: String) // NEW: Handles the sorting logic
        fun onSpotClicked(spotId: String)
        fun onDestroy()
    }
}

// Localized Model for List View
data class SpotListItem(
    val id: String,
    val rank: Int,
    val name: String,
    val categoryDistance: String,
    val rating: Double,
    val reviewCount: Int,
    val priceText: String,
    val statusText: String,
    val dealText: String?
)