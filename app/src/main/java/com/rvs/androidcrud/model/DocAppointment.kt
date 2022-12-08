package com.rvs.androidcrud.model

class DocAppointment() {
    var appId: Int? = null
    var appPlace: String? = null
    var appDate: String? = null
    var appTime: String? = null
    var appDoctor: String? = null
    var appComments: String? = null

    constructor(Id: Int, Place: String, Date: String, Time: String, Doctor: String, Comments: String): this() {
        this.appId = Id
        this.appPlace = Place
        this.appDate = Date
        this.appTime = Time
        this.appDoctor = Doctor
        this.appComments = Comments
    }

}