package com.example.shareblood.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shareblood.DataModel.DataModelDonorList
import com.example.shareblood.R

class MAkeDonarAdapter(private val usersList:ArrayList<DataModelDonorList>):RecyclerView.Adapter<MAkeDonarAdapter.MyViewHolder>(){



        class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
               val Name:TextView=itemView.findViewById(R.id.Name)
            val City:TextView=itemView.findViewById(R.id.City)
            val bloodGroup:TextView=itemView.findViewById(R.id.bloodGroup)
            val age:TextView=itemView.findViewById(R.id.age)
           // val Call_btn:TextView=itemView.findViewById(R.id.call_btn)
                //  val share_btn:TextView=itemView.findViewById(R.id.share_btn)
        }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.donar_list_item_layout,parent,false)
            return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return usersList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.Name.text=usersList[position].name
        holder.City.text=usersList[position].city
        holder.bloodGroup.text=usersList[position].bloodGroup
        holder.age.text=usersList[position].age
      //  holder.Age.text= ModelDonarList[position].Age.toString()
      //  holder.call_btn.setOnClickListener {
            //for letter
      //  }

     // holder.share_btn.setOnClickListener {

          //for letter
     // }



    }
}