package ru.mobiskif

import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView

class SimpleViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
    init { itemView.setOnClickListener(this) }

    override fun onClick(view: View) {
        val pos = adapterPosition
        if (pos != RecyclerView.NO_POSITION) {
            //val clickedDataItem = model.getDoctorList().value!![pos]
            //model.cdoctor.value=clickedDataItem
            Toast.makeText(view.context, "You clicked $pos", Toast.LENGTH_SHORT).show()
            //NavHostFragment.findNavController(navfragment).navigate(R.id.Fragment2)
        }
    }
}
