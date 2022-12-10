package com.rvs.androidcrud.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rvs.androidcrud.R
import com.rvs.androidcrud.model.DocAppointment
import org.w3c.dom.Text

class DocAppointmentListAdapter(private val list: ArrayList<DocAppointment>,
                                private val context: Context):
                                RecyclerView.Adapter<DocAppointmentListAdapter.ViewHolder> () {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.bindViews(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //create our view from our xml file
        val view = LayoutInflater.from(context)
            .inflate(R.layout.list_row, parent, false)
        return ViewHolder(view, context,list)
    }

    inner class ViewHolder(itemView: View, context: Context,
                           list: ArrayList<DocAppointment>):
                           RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var myContext = context
        var myList = list
        var appPlace = itemView.findViewById(R.id.placeID_Txt) as TextView
        var appDate = itemView.findViewById(R.id.dateID_Txt) as TextView
        var appTime = itemView.findViewById(R.id.timeID_Txt) as TextView
        var appDoctor = itemView.findViewById(R.id.doctorID_Txt) as TextView
        var appComments = itemView.findViewById(R.id.commentsID_Txt) as TextView
        var deleteButton = itemView.findViewById(R.id.deleteId_btn) as Button

        fun bindViews(docAppointment: DocAppointment) {
            appPlace.text = docAppointment.appPlace
            appDate.text = docAppointment.appDate
            appTime.text = docAppointment.appTime
            appDoctor.text = docAppointment.appDoctor
            appComments.text = docAppointment.appComments

            deleteButton.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
           // var myPosition: Int = adapterPosition
           // var docAppointment: myList[myPosition]
        }

        fun deleteDocAppointment(id: Int) {
            var db: DatabaseHandler = DatabaseHandler(myContext)
            db.deleteDocAppointment(id)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


}