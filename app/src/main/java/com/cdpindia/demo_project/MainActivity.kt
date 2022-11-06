package com.cdpindia.demo_project

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cdpindia.demo_project.DataModels.Course
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    lateinit var courseName:EditText
    lateinit var courseDescription:EditText
    lateinit var courseDuration:EditText
    lateinit var SubmitCourse:Button

    lateinit var course_Name:String
    lateinit var course_Description:String
    lateinit var course_Duration:String

    lateinit var db:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseAnalytics = Firebase.analytics

        // getting our instance
        // from Firebase Firestore.
        db = FirebaseFirestore.getInstance()

        courseName = findViewById(R.id.CourseName)
        courseDescription = findViewById(R.id.CourseDescription)
        courseDuration = findViewById(R.id.CourseDuration)
        SubmitCourse = findViewById(R.id.BtnSubmitCourse)



        SubmitCourse.setOnClickListener {

            course_Name = courseName.text.toString()
            course_Description = courseDescription.text.toString()
            course_Duration = courseDuration.text.toString()

            addDataToFirestore(course_Name,course_Description,course_Duration)
        }

        //Firebase
        var userUtils = UserUtils
        userUtils.queryContacts()
    }

    private fun addDataToFirestore(courseName: String, courseDescription: String, courseDuration: String) {

        // creating a collection reference
        // for our Firebase Firetore database.
        val dbCourses: CollectionReference = db.collection("Courses")

        // adding our data to our courses object class.
        // adding our data to our courses object class.
        val courses = Course(courseName,courseDescription, courseDuration)

        dbCourses.add(courses).addOnSuccessListener(OnSuccessListener {

            Toast.makeText(this,"Your Course has been added to Firebase Firestore",Toast.LENGTH_SHORT).show()

        }).addOnFailureListener(OnFailureListener {

            Toast.makeText(this,"Fail to add course $it",Toast.LENGTH_SHORT).show()

        })




    }
}