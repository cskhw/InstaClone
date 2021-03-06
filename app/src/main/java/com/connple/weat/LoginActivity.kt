package com.connple.weat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(),View.OnClickListener {


    //firebase Auth
    private lateinit var Auth: FirebaseAuth
    //google client
    private lateinit var googleSignInClient: GoogleSignInClient

    //private const val TAG = "GoogleActivity"
    private val RC_SIGN_IN = 99

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //btn_googleSignIn.setOnClickListener (this) // 구글 로그인 버튼
        google_login_button.setOnClickListener {signIn()}
        email_login_button.setOnClickListener {login()}

        //Google 로그인 옵션 구성. requestIdToken 및 Email 요청
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //firebase auth 객체
        Auth = FirebaseAuth.getInstance()
    }

    private fun login(){
        startActivity(Intent(this,MainActivity::class.java))
    }


    // onStart. 유저가 앱에 이미 구글 로그인을 했는지 확인
//    public override fun onStart() {
//        super.onStart()
//        moveMainPage(Auth?.currentUser)
//        val account = GoogleSignIn.getLastSignedInAccount(this)
//        if(account!==null){ // 이미 로그인 되어있을시 바로 메인 액티비티로 이동
//            moveMainPage(Auth.currentUser)
//        }
//    } //onStart End

    // onActivityResult
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                AuthWithGoogle(account!!)

            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("LoginActivity", "Google sign in failed", e)
            }
        }
    } // onActivityResult End

    // AuthWithGoogle
    private fun AuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("LoginActivity", "AuthWithGoogle:" + acct.id!!)

        //Google SignInAccount 객체에서 ID 토큰을 가져와서 Firebase Auth로 교환하고 Firebase에 인증
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        Auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.w("LoginActivity", "AuthWithGoogle 성공", task.exception)
                    moveMainPage(Auth?.currentUser)
                } else {
                    Log.w("LoginActivity", "AuthWithGoogle 실패", task.exception)
                    Snackbar.make(activity_login, "로그인에 실패하였습니다.", Snackbar.LENGTH_SHORT).show()
                }
            }
    }// AuthWithGoogle END


    // toMainActivity
    private fun moveMainPage(user: FirebaseUser?) {
        if(user !=null) { // MainActivity 로 이동
            Snackbar.make(activity_login, "로그인에 성공했습니다.", Snackbar.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    } // toMainActivity End

    // signIn
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    // signIn End

    override fun onClick(p0: View?) {
    }


    private fun signOut() { // 로그아웃
        // Firebase sign out
        Auth.signOut()

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this) {
            //updateUI(null)
        }
    }

    private fun revokeAccess() { //회원탈퇴
        // Firebase sign out
        Auth.signOut()
        googleSignInClient.revokeAccess().addOnCompleteListener(this) {

        }
    }
}