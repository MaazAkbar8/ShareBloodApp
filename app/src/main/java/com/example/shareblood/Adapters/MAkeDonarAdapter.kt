package com.example.shareblood.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shareblood.DataModel.DataModelDonarList
import com.example.shareblood.R

    class MAkeDonarAdapter(val requiredContext:Context,private val ModelDonarList:ArrayList<DataModelDonarList>) :
    RecyclerView.Adapter<MAkeDonarAdapter.ModelDonarListViewHolder>(){

        class ModelDonarListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
               val Name:TextView=itemView.findViewById(R.id.Name)
            val City:TextView=itemView.findViewById(R.id.City)
            val BloodGroup:TextView=itemView.findViewById(R.id.BloodGroup)
            val age:TextView=itemView.findViewById(R.id.age)
           // val Call_btn:TextView=itemView.findViewById(R.id.call_btn)
                //  val share_btn:TextView=itemView.findViewById(R.id.share_btn)
        }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelDonarListViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.donar_list_item_layout,parent,false)
            return ModelDonarListViewHolder(view)
    }

    override fun getItemCount(): Int {
       return ModelDonarList.size
    }

    override fun onBindViewHolder(holder: ModelDonarListViewHolder, position: Int) {
        holder.Name.text=ModelDonarList[position].Name
        holder.City.text=ModelDonarList[position].City
        holder.BloodGroup.text=ModelDonarList[position].BloodGroup
        holder.age.text=ModelDonarList[position].age
      //  holder.Age.text= ModelDonarList[position].Age.toString()
      //  holder.call_btn.setOnClickListener {
            //for letter
      //  }

     // holder.share_btn.setOnClickListener {

          //for letter
     // }



    }
}