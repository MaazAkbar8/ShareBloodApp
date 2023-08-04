package com.example.shareblood.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shareblood.DataModel.DataModelDonorList
import com.example.shareblood.R
import com.google.firebase.firestore.auth.User
import java.util.*


class MAkeDonarAdapter(private val usersList:List<DataModelDonorList>):RecyclerView.Adapter<MAkeDonarAdapter.MyViewHolder>() {


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.Name)
        val city: TextView = itemView.findViewById(R.id.City)
        var bloodGroup: TextView = itemView.findViewById(R.id.bloodGroup)
        var age: TextView = itemView.findViewById(R.id.age)




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MAkeDonarAdapter.MyViewHolder { val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.donar_list_item_layout, parent, false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //newlyworks // set data position

         val item = usersList[position]
         holder.name.text = item.name
         holder.city.text = item.city
         holder.bloodGroup.text=item.bloodGroup
         holder.age.text=item.age

        /* holder.name.text = usersList[position].name
    holder.city.text = usersList[position].city

    holder.bloodgroup .text = usersList[position].bloodGroup
    holder.age .text = usersList[position].age*/


    }

    override fun getItemCount(): Int {
        return usersList.size
    }




//newly tody



}





















