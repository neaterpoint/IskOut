package com.project.iskout.homepage.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.iskout.R

// Added the (SpotListItem) -> Unit callback
class SpotsAdapter(
    private val spots: List<SpotListItem>,
    private val onSpotClick: (SpotListItem) -> Unit
) : RecyclerView.Adapter<SpotsAdapter.SpotViewHolder>() {

    class SpotViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvRank: TextView = view.findViewById(R.id.tvRank)
        val tvName: TextView = view.findViewById(R.id.tvSpotName)
        val tvCategory: TextView = view.findViewById(R.id.tvSpotCategory)
        val tvRating: TextView = view.findViewById(R.id.tvSpotRating)
        val tvPrice: TextView = view.findViewById(R.id.tvSpotPrice)
        val tvStatus: TextView = view.findViewById(R.id.tvSpotStatus)
        val tvDealTag: TextView = view.findViewById(R.id.tvDealTag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpotViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_spot, parent, false)
        return SpotViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpotViewHolder, position: Int) {
        val spot = spots[position]
        holder.tvRank.text = spot.rank.toString()
        holder.tvName.text = spot.name
        holder.tvCategory.text = spot.categoryDistance
        holder.tvRating.text = "${spot.rating} (${spot.reviewCount})"
        holder.tvPrice.text = spot.priceText
        holder.tvStatus.text = spot.statusText

        if (spot.dealText != null) {
            holder.tvDealTag.visibility = View.VISIBLE
            holder.tvDealTag.text = spot.dealText
        } else {
            holder.tvDealTag.visibility = View.GONE
        }

        // Handle the click
        holder.itemView.setOnClickListener { onSpotClick(spot) }
    }

    override fun getItemCount() = spots.size
}