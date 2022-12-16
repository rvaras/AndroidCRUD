package com.rvs.androidcrud.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.rvs.androidcrud.R
import com.rvs.androidcrud.data.DatabaseHandler
import com.rvs.androidcrud.model.DocAppointment
import kotlinx.android.synthetic.main.activity_new_appointment.*

class NewAppointmentActivity : AppCompatActivity() {
    var dbHandler: DatabaseHandler? = null
    //var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_appointment)

        dbHandler = DatabaseHandler(this)

        done_btn.setOnClickListener {
            if (!TextUtils.isEmpty(enter_appointment_place_txt.text.toString()) &&
                !TextUtils.isEmpty(enter_appointment_date_txt.text.toString()) &&
                !TextUtils.isEmpty(enter_appointment_time_txt.text.toString()) &&
                !TextUtils.isEmpty(enter_appointment_doctor_txt.text.toString()) &&
                !TextUtils.isEmpty(enter_appointment_comments_txt.text.toString()) ) {

                // Save to database
                var docAppointment = DocAppointment()

                docAppointment.appPlace = enter_appointment_place_txt.text.toString()
                docAppointment.appDate = enter_appointment_date_txt.text.toString()
                docAppointment.appTime = enter_appointment_time_txt.text.toString()
                docAppointment.appDoctor = enter_appointment_doctor_txt.text.toString()
                docAppointment.appComments = enter_appointment_comments_txt.text.toString()

                //Log.d("====ID========", c.id.toString())
                Log.d("====PLACE=====", enter_appointment_place_txt.text.toString())
                Log.d("====DATE======", enter_appointment_date_txt.text.toString())
                Log.d("====TIME======", enter_appointment_time_txt.text.toString())
                Log.d("====DOCTOR====", enter_appointment_doctor_txt.text.toString())
                Log.d("====COMMENTS==", enter_appointment_comments_txt.text.toString())

                saveToDB(docAppointment)

                startActivity(Intent(this, MainActivity::class.java))
                finish()

            } else {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_LONG).show()
            }

        }


    }

    /*
    fun checkDB() {
        if (dbHandler!!.getDocAppointmentCount() > 0) {
            startActivity(Intent(this, ChoreListActivity::class.java))

        }
    }
     */

    fun saveToDB(docAppointment: DocAppointment) {
        dbHandler!!.createDocAppointment(docAppointment)
    }
}