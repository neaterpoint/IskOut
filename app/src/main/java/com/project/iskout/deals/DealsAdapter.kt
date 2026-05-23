package com.project.iskout.deals

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.project.iskout.R

// Added onSaveClick callback
class DealsAdapter(
    private val deals: List<DealItem>,
    private val savedDeals: MutableSet<String>,
    private val onSaveClick: (DealItem) -> Unit
) : RecyclerView.Adapter<DealsAdapter.DealViewHolder>() {

    class DealViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvDealSpotName)
        val tvDetails: TextView = view.findViewById(R.id.tvDealDetails)
        val btnSave: MaterialButton = view.findViewById(R.id.btnSaveDeal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_deal, parent, false)
        return DealViewHolder(view)
    }

    override fun onBindViewHolder(holder: DealViewHolder, position: Int) {
        val deal = deals[position]
        holder.tvName.text = deal.spotName
        holder.tvDetails.text = deal.dealDescription

        // Check if this specific deal ID is in our 'saved' set
        val isSaved = savedDeals.contains(deal.id)

        if (isSaved) {
            holder.btnSave.text = "Saved"
            holder.btnSave.setBackgroundColor(Color.LTGRAY)
            holder.btnSave.isEnabled = false
        } else {
            holder.btnSave.text = "Save"
            holder.btnSave.setBackgroundColor(Color.parseColor("#3B82F6"))
            holder.btnSave.isEnabled = true
        }

        holder.btnSave.setOnClickListener {
            onSaveClick(deal)
        }
    }

    override fun getItemCount() = deals.size
}