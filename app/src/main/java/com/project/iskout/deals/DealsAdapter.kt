package com.project.iskout.deals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.iskout.R

class DealsAdapter(private val deals: List<DealItem>) : RecyclerView.Adapter<DealsAdapter.DealViewHolder>() {

    class DealViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvDealSpotName)
        val tvDetails: TextView = view.findViewById(R.id.tvDealDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_deal, parent, false)
        return DealViewHolder(view)
    }

    override fun onBindViewHolder(holder: DealViewHolder, position: Int) {
        val deal = deals[position]
        holder.tvName.text = deal.spotName
        holder.tvDetails.text = deal.dealDescription
    }

    override fun getItemCount() = deals.size
}