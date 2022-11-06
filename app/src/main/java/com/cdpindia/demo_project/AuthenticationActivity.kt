package com.cdpindia.demo_project

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cdpindia.demo_project.DataModels.MobileNumber
import com.cdpindia.demo_project.DataModels.UserProfile
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.messaging.FirebaseMessaging
import java.util.concurrent.TimeUnit


class AuthenticationActivity : AppCompatActivity() {
    lateinit var mAuth:FirebaseAuth
    lateinit var PhoneNumber:EditText
    lateinit var BtnGetOtp:Button
    lateinit var EdtOtp:EditText
    lateinit var BtnVerify:Button
    lateinit var verificationId:String
    lateinit var uid:String
    lateinit var token:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        mAuth = FirebaseAuth.getInstance()

        PhoneNumber = findViewById(R.id.idEdtPhoneNumber)
        BtnGetOtp = findViewById(R.id.idBtnGetOtp)
        EdtOtp = findViewById(R.id.idEdtOtp)
        BtnVerify = findViewById(R.id.idBtnVerify)

        BtnGetOtp.setOnClickListener {
            if (TextUtils.isEmpty(PhoneNumber.text.toString())) {
                // when mobile number text field is empty
                // displaying a toast message.
                Toast.makeText(this,
                    "Please enter a valid phone number.",
                    Toast.LENGTH_SHORT).show()
            } else {
                // if the text field is not empty we are calling our
                // send OTP method for getting OTP from Firebase.
                val phone = "+91 ${PhoneNumber.text.toString()}"
                sendVerificationCode(phone)
            }
        }

        BtnVerify.setOnClickListener {
            if (TextUtils.isEmpty(EdtOtp.text.toString())){
                Toast.makeText(this,
                    "Please enter OPT.",
                    Toast.LENGTH_SHORT).show()
            }
            else{
                verifyCode(EdtOtp.text.toString())
            }
        }

    //Firebase
        FirebaseMessaging.getInstance().token.addOnCompleteListener {task ->
            if (!task.isSuccessful){
                return@addOnCompleteListener
            }
            if (task.result != null){
                token = task.result
                Log.e("priyanka","token = $token")
            }
        }

    }


    fun sendVerificationCode(phone: String) {

        // this method is used for getting
        // OTP on user phone number.
        // this method is used for getting
        // OTP on user phone number.
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(phone) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(mCallBack) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    // callback method is called on Phone auth provider.
    private val   // initializing our callbacks for on
    // verification callback method.
            mCallBack: OnVerificationStateChangedCallbacks = object : OnVerificationStateChangedCallbacks() {
            // below method is used when
            // OTP is sent from Firebase
            override fun onCodeSent(s: String, forceResendingToken: ForceResendingToken) {
                super.onCodeSent(s, forceResendingToken)
                // when we receive the OTP it
                // contains a unique id which
                // we are storing in our string
                // which we have already created.
                verificationId = s

                Log.e("priyanka","verificationId $verificationId")
                Log.e("priyanka","forceResendingToken $forceResendingToken")
            }

            // this method is called when user
            // receive OTP from Firebase.
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                // below line is used for getting OTP code
                // which is sent in phone auth credentials.
                val code: String = phoneAuthCredential.smsCode.toString()

                Log.e("priyanka","OTP $code")

                // checking if the code
                // is null or not.
                if (code != null) {
                    // if the code is not null then
                    // we are setting that code to
                    // our OTP edittext field.
                    EdtOtp.setText(code)

                    // after setting this code
                    // to OTP edittext field we
                    // are calling our verifycode method.
                    verifyCode(code)
                }
            }
// 9359309108
            // this method is called when firebase doesn't
            // sends our OTP code due to any error or issue.
            override fun onVerificationFailed(e: FirebaseException) {
                // displaying error message with firebase exception.
                Toast.makeText(this@AuthenticationActivity,e.message,Toast.LENGTH_SHORT).show()
                Log.e("priyanka","AuthenticationActivity ${e.message}")
            }
        }

    private fun verifyCode(otp: String) {

        // below line is used for getting
        // credentials from our verification id and code.
        // below line is used for getting
        // credentials from our verification id and code.
        val credential = PhoneAuthProvider.getCredential(verificationId, otp)

        // after getting credential we are
        // calling sign in method.

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential)
    }

    fun signInWithCredential(credential: PhoneAuthCredential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    uid = task.result.user?.uid.toString()
                    // if the code is correct and the task is successful
                    // we are sending our user to new activity.
                    insertUser(uid)
                    val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                    finish()
                } else {
                    // if the code is not correct then we are
                    // displaying an error message to the user.
                    Toast.makeText(this,
                        task.exception.toString(),
                        Toast.LENGTH_LONG)
                        .show()
                }
            }
    }

    private fun insertUser(uid:String) {
        val mobileNumber= MobileNumber("+91","8693876443")

        val profile = UserProfile(uid, userName = "Nilam", mobile = mobileNumber, token = token)
        val db = FirebaseFirestore.getInstance()
        db.collection("Users").document(profile.uId)
            .set(profile, SetOptions.merge())
            .addOnSuccessListener{
                Log.e("priyanka","New User added successfully")
            }
            .addOnFailureListener {
                Log.e("priyanka","User doesn't $it")
            }
    }


}
