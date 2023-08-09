package com.example.shareblood.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.shareblood.DataModel.AddEmmbulncDataModel
import com.example.shareblood.DataModel.DataModelDonorList
import com.example.shareblood.R

class AddEmbulnceAdapter (  val context: Context,private val userslist2:List<AddEmmbulncDataModel>): RecyclerView.Adapter<AddEmbulnceAdapter.MyViewHolder>() {


   inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val city: TextView = itemView.findViewById(R.id.tvcity)
        val hospital: TextView = itemView.findViewById(R.id.tvhospital)
        var regNo: TextView = itemView.findViewById(R.id.tvRegNo)
        var phoneicon2: ImageView = itemView.findViewById(R.id.call_btn2)

//*************************************************************************************************************************************
        //bind  datamodel
        @SuppressLint("QueryPermissionsNeeded")
        fun bind2(ambulance: AddEmmbulncDataModel) {

            // phoneicon eventhandling newly
            phoneicon2.setOnClickListener {


                val phone2 = ambulance.number

                val intent =
                    Intent(Intent.ACTION_DIAL, Uri.parse("tel:${phone2}"))// phonenumber
                if (intent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(intent)
                } else {
                    Toast.makeText(
                        context,
                        "No app available to make a phone call",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    //phonenumber o dase k
                    if (phone2 != null) {
                        initiatemobileCall(context, phone2)

                    } else {
                        android.widget.Toast.makeText(
                            context,
                            "No app available to make a phone call",
                            android.widget.Toast.LENGTH_SHORT
                        )
                            .show()

                    }
                }
            }

        }


    }

//*************************************************************************************************************************************
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddEmbulnceAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ambulance_list_item, parent, false)
        return MyViewHolder(view)

    }
    //*********************************************************************************************************************************

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AddEmbulnceAdapter.MyViewHolder, position: Int) {
        //newlyworks // set data position  append used to after value is print but before value or works is printed like
        //

        val item = userslist2[position]
        //holder.city.append(" ${item.city}")
        holder.city.text = item.city
        // holder.hospital.append("${item.hospital}")
        holder.hospital.text = item.hospital
        //  holder.RegNo.append("${item.RegNo}")
        holder.regNo.text = item.regNo
        holder.bind2(item)
        //  holder.mobilenumber.append("${item.mobilenumber}")


    }

//**********************************************************************************************************************************
// phoneicon function
    private fun initiatemobileCall(context: Context, phone2: String) {


    }

//*************************************************************************************************************************************

    override fun getItemCount(): Int {
        return userslist2.size
    }

}
//********************************************************************************************************************************