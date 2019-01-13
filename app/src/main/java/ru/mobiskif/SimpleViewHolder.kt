package ru.mobiskif

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class SimpleViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
    init { itemView.setOnClickListener(this) }

    override fun onClick(view: View) {
        if (adapterPosition != RecyclerView.NO_POSITION)
            Toast.makeText(view.context, "Это ViewHolder $adapterPosition", Toast.LENGTH_SHORT).show()
    }
}
