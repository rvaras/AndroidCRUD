package com.rvs.androidcrud.data

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.rvs.androidcrud.R
import com.rvs.androidcrud.model.DocAppointment


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
        return ViewHolder(view, context, list)
    }

    inner class ViewHolder(itemView: View,
                           context: Context,
                           list: ArrayList<DocAppointment>): RecyclerView.ViewHolder(itemView),
                           View.OnClickListener {

        var myContext = context
        var myList = list
        var appPlace = itemView.findViewById(R.id.placeID_Txt) as TextView
        var appDate = itemView.findViewById(R.id.dateID_Txt) as TextView
        var appTime = itemView.findViewById(R.id.timeID_Txt) as TextView
        var appDoctor = itemView.findViewById(R.id.doctorID_Txt) as TextView
        var appComments = itemView.findViewById(R.id.commentsID_Txt) as TextView
        var deleteButton = itemView.findViewById(R.id.deleteId_btn) as Button
        var editButton = itemView.findViewById(R.id.editId_btn) as Button

        fun bindViews(docAppointment: DocAppointment) {
            appPlace.text = docAppointment.appPlace
            appDate.text = docAppointment.appDate
            appTime.text = docAppointment.appTime
            appDoctor.text = docAppointment.appDoctor
            appComments.text = docAppointment.appComments

            deleteButton.setOnClickListener(this)
            editButton.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            var myPosition: Int = adapterPosition
            var docAppointment = myList[myPosition]

            Log.d("IDD: ", docAppointment.toString())

            when (v!!.id) {
                deleteButton.id -> {
                    deleteDocAppointment(docAppointment.appId!!)
                    myList.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }

                editButton.id -> {
                    editDocAppointment(docAppointment)
                }
            }


        }

        fun deleteDocAppointment(id: Int) {
            var db: DatabaseHandler = DatabaseHandler(myContext)
            db.deleteDocAppointment(id)
        }

        fun editDocAppointment(docAppointment: DocAppointment) {

            var dialogBuilder: AlertDialog.Builder?
            var dialog: AlertDialog?
            var dbHandler: DatabaseHandler = DatabaseHandler(context)

            var view = LayoutInflater.from(context).inflate(R.layout.popup, null)

            var appPlace = itemView.findViewById(R.id.popupDoctorAppointmentPlace) as TextView
            var appDate = itemView.findViewById(R.id.popupDoctorAppointmentDate) as TextView
            var appTime = itemView.findViewById(R.id.popupDoctorAppointmentTime) as TextView
            var appDoctor = itemView.findViewById(R.id.popupDoctorAppointmentDoctor) as TextView
            var appComments = itemView.findViewById(R.id.popupDoctorAppointmentComments) as TextView
            var saveButton = itemView.findViewById(R.id.popupSaveButton) as Button

            dialogBuilder = AlertDialog.Builder(context).setView(view)
            dialog = dialogBuilder!!.create()
            dialog?.show()

            saveButton.setOnClickListener {
                var appPlace = docAppointment.appPlace.toString().trim()
                var appDate = docAppointment.appDate.toString().trim()
                var appTime =  docAppointment.appTime.toString().trim()
                var appDoctor = docAppointment.appDoctor.toString().trim()
                var appComments = docAppointment.appComments.toString().trim()

                if (!TextUtils.isEmpty(appPlace)
                    && !TextUtils.isEmpty(appDate)
                    && !TextUtils.isEmpty(appTime)
                    && !TextUtils.isEmpty(appDoctor)
                    && !TextUtils.isEmpty(appComments)) {
                    // var chore = Chore()

                    docAppointment.appPlace= appPlace
                    docAppointment.appDate = appDate
                    docAppointment.appTime = appTime
                    docAppointment.appDoctor = appDoctor
                    docAppointment.appComments = appComments

                    dbHandler!!.updateDocAppointment(docAppointment)
                    notifyItemChanged(adapterPosition, docAppointment)

                    dialog!!.dismiss()

//                    startActivity(Intent(this, ChoreListActivity::class.java))
//                    finish()

                } else {

                }
            }

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}