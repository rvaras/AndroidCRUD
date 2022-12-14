package com.rvs.androidcrud.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rvs.androidcrud.R
import com.rvs.androidcrud.data.DatabaseHandler
import com.rvs.androidcrud.data.DocAppointmentListAdapter
import com.rvs.androidcrud.model.DocAppointment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var adapter: DocAppointmentListAdapter? = null
    private var docAppointmentList: ArrayList<DocAppointment>? = null
    private var docAppointmentListItems: ArrayList<DocAppointment>? = null
    private var dialogBuilder: AlertDialog.Builder? = null
    private var dialog: AlertDialog? = null

    private var curSize: Int? = null

    private var layoutManager: RecyclerView.LayoutManager? = null
    var dbHandler: DatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler = DatabaseHandler(this)

        docAppointmentList = ArrayList<DocAppointment>()
        docAppointmentListItems = ArrayList()
        layoutManager = LinearLayoutManager(this)
        adapter = DocAppointmentListAdapter(docAppointmentListItems!!, this)

        curSize = adapter!!.itemCount

        if (curSize == 0) {
            Toast.makeText(this, "No items to show", Toast.LENGTH_SHORT).show()

        }

        // setup list = recyclerview
        recyclerViewId.layoutManager = layoutManager
        recyclerViewId.adapter = adapter

        //Load our chores
        docAppointmentList = dbHandler!!.readMultipleDocAppointments()
        docAppointmentList!!.reverse()

        for (c in docAppointmentList!!.iterator()) {
            val docAppointment = DocAppointment()
            docAppointment.appId = c.appId
            var appPlace: String? = c.appPlace
            var appDate: String? = c.appDate
            var appTime: String? = c.appTime
            var appDoctor: String? = c.appDoctor
            var appComments: String? = c.appComments

//            Log.d("====ID=====", c.id.toString())
//            Log.d("====Name=====", c.choreName)
//            Log.d("====Date=====", chore.showHumanDate(c.timeAssigned!!))
//            Log.d("====aTo=====", c.assignedTo)
//            Log.d("====aBy=====", c.assignedTo)
            docAppointmentListItems!!.add(docAppointment)
        }
        adapter!!.notifyDataSetChanged()

        val fab: View = findViewById(R.id.addDoctorAppointment_button)
        fab.setOnClickListener { view ->
            val intent = Intent(this, NewAppointmentActivity::class.java)
            startActivity(intent)
        }

    }
}