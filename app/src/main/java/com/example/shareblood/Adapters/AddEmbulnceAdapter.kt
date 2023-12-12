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
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.shareblood.DataModel.AddEmmbulncDataModel
import com.example.shareblood.R

class AddEmbulnceAdapter (  val context: Context,private val userslist2:List<AddEmmbulncDataModel>,private var isNotActiveSelected: Boolean,private val currentUserId: String
): RecyclerView.Adapter<AddEmbulnceAdapter.MyViewHolder>() {

    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper



    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val city: TextView = itemView.findViewById(R.id.tvcity)
        val hospital: TextView = itemView.findViewById(R.id.tvhospital)
        var RegNo: TextView = itemView.findViewById(R.id.tvRegNo)
        var phoneicon2: ImageView = itemView.findViewById(R.id.call_btn2)
        var whatsapp: ImageView = itemView.findViewById(R.id.whatsp_btn)
        var Cardview:CardView=itemView.findViewById(R.id.ambulance_Cardv)

        //*************************************************************************************************************************************
        //bind  datamodel
        @SuppressLint("QueryPermissionsNeeded")
        fun bind2(ambulance: AddEmmbulncDataModel) {



            // phoneicon Onclicklistener
            phoneicon2.setOnClickListener {

                val phone = ambulance.number

                val intent =
                    Intent(Intent.ACTION_DIAL, Uri.parse("tel:${phone}"))// phonenumber
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
                    if (phone != null) {
                        initiatemobileCall(context, phone)

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
            whatsapp.setOnClickListener {
                val phone2 = ambulance.number // Replace with the desired phone number


                /* val i = Intent(Intent.ACTION_SENDTO)
         i.data = Uri.parse("smsto:${phone2}")
         i.putExtra("sms_body", "Hello, let's chat on WhatsApp!")
         i.setPackage("com.whatsapp")

         if (i.resolveActivity(context.packageManager) != null) {
             context. startActivity( i)
         } else {
             Toast.makeText(context, "whatsp is not available", Toast.LENGTH_SHORT).show()
         }*/
                if (phone2 != null) {
                    whatsapp(phone2)
                }
            }
        }
    }
        fun whatsapp(phonenumber2: String) {
            val uri = Uri.parse("https://api.whatsapp.com/send?phone=$phonenumber2")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)


    }
    //**************************************************************************************************************************
    //whatsapp btn Onclckelistener





//*************************************************************************************************************************************
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddEmbulnceAdapter.MyViewHolder {
    sharedPreferencesHelper = SharedPreferencesHelper(parent.context)
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
        holder.RegNo.text = item.RegNo
        holder.bind2(item)
       // holder.bind2(item)
        //  holder.mobilenumber.append("${item.mobilenumber}")
        if (isNotActiveSelected && item.userId1 == currentUserId) {
            holder.Cardview.visibility = View.GONE
        } else {
            holder.Cardview.visibility = View.VISIBLE
        }

    }

//**********************************************************************************************************************************
// phoneicon function
    private fun initiatemobileCall(context: Context, phone2: String) {


    }

//*************************************************************************************************************************************

    override fun getItemCount(): Int {
        return userslist2.size
    }

    fun updateVisibility(isNotActiveSelected : Boolean) {
        this.isNotActiveSelected = isNotActiveSelected
        notifyDataSetChanged()
        val currentUserPosition = userslist2.indexOfFirst { it.userId1 == currentUserId }
        if (currentUserPosition != -1) {
            notifyItemChanged(currentUserPosition)

        }


    }


}
//********************************************************************************************************************************