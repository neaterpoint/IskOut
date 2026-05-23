package com.project.iskout.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.iskout.R
import com.project.iskout.profile.list.ReviewItem

class ReviewsAdapter(private val reviews: List<ReviewItem>) : RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>() {

    class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvSpotName: TextView = view.findViewById(R.id.tvReviewSpotName)
        val tvDate: TextView = view.findViewById(R.id.tvReviewDate)
        val tvRating: TextView = view.findViewById(R.id.tvReviewRating)
        val tvComment: TextView = view.findViewById(R.id.tvReviewComment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        holder.tvSpotName.text = review.spotName
        holder.tvDate.text = review.date
        holder.tvRating.text = "${review.rating}.0 / 5.0"
        holder.tvComment.text = review.comment
    }

    override fun getItemCount() = reviews.size
}