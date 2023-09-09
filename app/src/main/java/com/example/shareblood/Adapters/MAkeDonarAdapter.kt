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
import com.example.shareblood.DataModel.DataModelDonorList
import com.example.shareblood.R


class MAkeDonarAdapter(val context: Context, private val usersList: List<DataModelDonorList>):RecyclerView.Adapter<MAkeDonarAdapter.MyViewHolder>() {



    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {



        val name: TextView = itemView.findViewById(R.id.Name)
        val city: TextView = itemView.findViewById(R.id.City)
        var bloodGroup: TextView = itemView.findViewById(R.id.bloodGroup)
        var age: TextView = itemView.findViewById(R.id.age)
        val phoneicon: ImageView = itemView.findViewById(R.id.call_btn)
        val whatap: ImageView = itemView.findViewById(R.id.whatsp_btn)


        //*******************************************************************************************************************************************************************
        @SuppressLint("QueryPermissionsNeeded")
        // simple function binding
        fun bind(donar: DataModelDonorList) {

            // phone onclicked

            phoneicon.setOnClickListener {
                val phonenumber = donar.mobilenumber



                val intent =
                    Intent(Intent.ACTION_DIAL, Uri.parse("tel:${phonenumber}"))// phonenumber
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
                    if (phonenumber != null) {
                        initiatePhoneCall(context,phonenumber)


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



        //*******************************************************************************************************************
        // whatsp_btn onclicked
        fun bind2(donar2: DataModelDonorList) {


            whatap.setOnClickListener {
                val phonenumber2 = donar2.mobilenumber

                /* val i = Intent(Intent.ACTION_SENDTO)
                i.data = Uri.parse("smsto:${phonenumber2}")
               i.putExtra("sms_body", "Hello, let's chat on WhatsApp!")
                i.setPackage("com.whatsapp")

                if (i.resolveActivity(context.packageManager) != null) {
                   context.startActivity(i)
                } else {
                    Toast.makeText(context, "whatsp is not available", Toast.LENGTH_SHORT).show()
                }


           }*/

                if (phonenumber2 != null) {
                    whatsp(phonenumber2)
                }
            }

        }



    }


fun whatsp(phonenumber2: String){
    val uri = Uri.parse("https://api.whatsapp.com/send?phone=$phonenumber2")
    val intent = Intent(Intent.ACTION_VIEW, uri)
    context.startActivity(intent)
}



//********************************************************************************************************************************

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.donar_list_item_layout, parent, false)
        return MyViewHolder(view)

    }

    //*********************************************************************************************************************************
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //newlyworks // set data position

        val item = usersList[position]// usersList
        // holder.name.append("${item.name}")
        holder.name.text = item.name
        // holder.city.append("${item.city}")
        holder.city.text = item.city
        // holder.bloodGroup.append("${item.bloodGroup}")
        holder.bloodGroup.text = item.bloodGroup
        //holder.age.append(" ${item.age}")
        holder.age.text = item.age
        //  holder.phoneicon.append("${item.city}")
        // new only donar
        holder.bind(item)
        holder.bind2(item)



    }

//**************************************************************************************************************

    // these function specially used for if user click the  phone icon to go to diled through intent .

   private fun initiatePhoneCall( context: Context,phonenumber: String) {


   }
//*********************************************************************************************************************************


    override fun getItemCount(): Int {

        return usersList.size // usersList
    }

    // return usersList.size









}




























