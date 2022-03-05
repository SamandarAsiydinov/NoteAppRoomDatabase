package uz.context.noteapproomdatabase.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import uz.context.noteapproomdatabase.R
import uz.context.noteapproomdatabase.data.User
import uz.context.noteapproomdatabase.fragments.ListFragmentDirections

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var userList = emptyList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = userList[position]

        holder.apply {
            textId.text = "${position.plus(1)}"
            textLastName.text = user.lastName
            textName.text = user.firstName
            textAge.text = user.age.toString()

            rowLayout.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(user)
                itemView.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textId: TextView = itemView.findViewById(R.id.id_txt)
        val textName: TextView = itemView.findViewById(R.id.firstNameTxt)
        val textLastName: TextView = itemView.findViewById(R.id.lastNameTxt)
        val textAge: TextView = itemView.findViewById(R.id.ageText)
        val rowLayout: MaterialCardView = itemView.findViewById(R.id.rowLayout)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(user: List<User>) {
        this.userList = user
        notifyDataSetChanged()
    }
}