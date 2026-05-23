package com.project.iskout.deals

interface DealsContract {
    interface View {
        fun showDeals(deals: List<DealItem>)
    }
    interface Presenter {
        fun loadActiveDeals()
    }
}

data class DealItem(
    val id: String,
    val spotName: String,
    val dealDescription: String
)