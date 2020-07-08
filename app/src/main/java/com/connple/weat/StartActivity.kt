package com.connple.weat
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_start.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class StartActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")

    var auth = FirebaseAuth.getInstance()


    lateinit var email:String
    lateinit var password:String
    lateinit var nickname:String

    private lateinit var Auth: FirebaseAuth

    // 마진 값 설정
    private fun View.setMargins(
        left: Int? = null,
        top: Int? = null,
        right: Int? = null,
        bottom: Int? = null
    ) {
        val lp = layoutParams as? ViewGroup.MarginLayoutParams
            ?: return

        lp.setMargins(
            left ?: lp.leftMargin,
            top ?: lp.topMargin,
            right ?: lp.rightMargin,
            bottom ?: lp.rightMargin
        )
        layoutParams = lp
    }

    fun sendEmailVerification(){

        if(FirebaseAuth.getInstance().currentUser!!.isEmailVerified){
            Toast.makeText(this, "이메일 인증이 이미 완료되었습니다", Toast.LENGTH_LONG).show()
            return
        }

        FirebaseAuth.getInstance().currentUser!!.sendEmailVerification().addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(this, "확인메일을 보냈습니다", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
        }
        }
    }

    // 뒤로가기 버튼
    fun backToLogin(btnText:String, tv_1Text:String, tv_2Text:String, goTo:Int):Int {
        btn_1.visibility = View.VISIBLE
        btn_1.text = btnText
        btn_1.setTextColor(Color.parseColor("#000000"))
        btn_1.setMargins(0,0,0,72)
        img_btn_1.visibility = View.GONE
        relative_1.visibility = View.VISIBLE
        tab_1.setImageResource(R.drawable.tab_icon2)
        tab_2.setImageResource(R.drawable.tab_icon)
        tab_3.setImageResource(R.drawable.tab_icon)
        btn_1.visibility = View.VISIBLE
        btn_2.text = "로그인"
        btn_2.visibility = View.GONE
        img_1.visibility = View.VISIBLE
        editText.visibility = View.GONE
        div_1.visibility = View.GONE
        tv_1.visibility = View.VISIBLE
        tv_1.text = tv_1Text
        tv_2.text = tv_2Text
        tv_2.visibility = View.VISIBLE
        tv_3.visibility = View.GONE
        tv_3.setTextColor(Color.parseColor("#000000"))
        tv_4.visibility = View.GONE
        tv_4.setTextColor(Color.parseColor("#C0C0C0"))
        btn_1.background = getDrawable(R.drawable.btn_next)
        editText.inputType = InputType.TYPE_CLASS_TEXT
        editText1.visibility = View.GONE
        editText2.visibility = View.GONE
        editText3.visibility = View.GONE
        editText4.visibility = View.GONE
        editText5.visibility = View.GONE
        editText6.visibility = View.GONE
        activity_start.setBackgroundColor(Color.parseColor("#FFFFFF"))
        div_1.setBackgroundColor(Color.parseColor("#e0e0e0"))
        img_1.setImageResource(R.drawable.start_1)
        return goTo
}
    fun isEmailValid(email: String): Boolean {
        val regExpn = ("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$")
        val inputStr: CharSequence = email
        val pattern: Pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(inputStr)
        return matcher.matches()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_start)
        super.onCreate(savedInstanceState)

        var i = 0
        var j = 0
        Auth = FirebaseAuth.getInstance()

        img_btn_1.setOnClickListener {
            var t = backToLogin("다음", "맛있는데, 할인까지?", "오늘 점심으로 먹은 메뉴를 리뷰하고, \n 할인도 받아보세요.", 0)
            i = t
            j = 0
        }

        // 초기 설정
        img_btn_1.visibility = View.GONE
        btn_2.visibility = View.GONE
        editText.visibility = View.GONE
        div_1.visibility = View.GONE
        tab_1.setImageResource(R.drawable.tab_icon2)
        tv_3.visibility = View.GONE
        tv_4.visibility = View.GONE
        editText1.visibility = View.GONE
        editText2.visibility = View.GONE
        editText3.visibility = View.GONE
        editText4.visibility = View.GONE
        editText5.visibility = View.GONE
        editText6.visibility = View.GONE

        // 버튼 1 클릭 액션
        btn_1.setOnClickListener {
            when (i) {
                0 -> {
                    img_1.setImageResource(R.drawable.charlieintro)
                    tv_1.text = "오늘은 뭐 먹고싶어?"
                    tv_2.text = "오늘 점심을 함께할 친구에게 메뉴와\n리뷰를 보내고, 맛있는 음식 함께 즐기세요!"
                    tab_1.setImageResource(R.drawable.tab_icon)
                    tab_2.setImageResource(R.drawable.tab_icon2)
                    i++
                }
                1 -> {
                    img_1.setImageResource(R.drawable.start_3)
                    tv_1.text = "시작해볼까요?"
                    tv_2.text = "모두가 행복한 \n 즐거운 푸드라이프, 위트!"
                    btn_1.text = "회원가입"
                    btn_1.setMargins(0,0,0,0)
                    btn_1.background = getDrawable(R.drawable.btn_purple)
                    btn_1.setTextColor(Color.parseColor("#FFFFFF"))
//                    btn_1.setTypeface(null, Typeface.BOLD)
                    btn_2.visibility = View.VISIBLE
                    tab_2.setImageResource(R.drawable.tab_icon)
                    tab_3.setImageResource(R.drawable.tab_icon2)
                    i++
                }
                2 -> {
                    j = 3
                    tv_1.visibility = View.GONE
                    tv_2.visibility = View.GONE
                    tv_3.visibility = View.VISIBLE
                    tv_3.text = "사용자 유형을 선택해주세요."
                    tv_4.visibility = View.VISIBLE
                    tv_4.text = "일반 유저가 사장님으로 회원가입을 하는 경우 불이익을 받게 됩니다."
                    img_btn_1.visibility = View.VISIBLE
                    relative_1.visibility = View.GONE
                    img_1.visibility = View.GONE
                    btn_1.text = "일반"
                    btn_2.text = "식당 사장님"
                    i++
                }
                3 -> {
                    btn_1.text = "이메일로 회원가입"
                    btn_2.text = "네이버"
                    tv_3.text = "일반 유저로 회원가입합니다."
                    tv_4.text = "리뷰를 작성하고 이벤트에 참여해 할인혜택 등을 받을 수 있습니다."
                    i++
                }
                4 -> {
                    btn_1.setTextColor(Color.parseColor("#000000"))
                    btn_1.background = getDrawable(R.drawable.btn_next)
                    btn_1.text = "메일 인증하기"
                    btn_1.setMargins(0,0,0,72)
                    btn_2.visibility = View.GONE
                    editText.visibility = View.VISIBLE
                    editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                    tv_3.text = "이메일을 입력해주세요."
                    tv_4.text = "회원가입에 필요합니다."
                    editText.hint = "welcome@weat.com"
                    div_1.visibility = View.VISIBLE
                    i++
                }
                5 -> {
                    if(
                        editText.text.toString().trim().isEmpty() || !isEmailValid(editText.text.toString())){
                        Toast.makeText(applicationContext, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
                        i = 5
                        editText.text = null
                        return@setOnClickListener
                    }
                    email = editText.text.toString()
                    // 이메일 인증 로직
                    // 인증 되면 통과 아니면 x
                    editText.text = null
                    div_1.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    btn_1.text = "인증완료"
                    tv_3.text = "이메일을 확인해주세요."
                    tv_4.text = "확인 메일을 보냈습니다.."
                    editText.visibility = View.GONE
                    i++
                }
                6 -> {
                    editText.text = null
                    div_1.setBackgroundColor(Color.parseColor("#e0e0e0"))
                    editText.visibility = View.VISIBLE
                    editText1.visibility = View.GONE
                    editText2.visibility = View.GONE
                    editText3.visibility = View.GONE
                    editText4.visibility = View.GONE
                    editText5.visibility = View.GONE
                    editText6.visibility = View.GONE
                    btn_1.text = "다음"
                    tv_3.text = "비밀번호를 입력해주세요."
                    tv_4.text = "8자 이상의 숫자 또는 영문을 입력해주세요."
                    editText.hint = "weat1234"
                    editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    i++
                }
                7 -> {
                    if(editText.text.toString().trim().isEmpty()){
                        Toast.makeText(applicationContext, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                        i = 7
                        editText.text = null
                        return@setOnClickListener
                    }
                    password = editText.text.toString()
                    editText.text = null
                    tv_3.text = "비밀번호를 다시 입력해주세요."
                    tv_4.text = "8자 이상의 숫자 또는 영문을 입력해주세요."
                    editText.hint = "weat1234"
                    i++
                }
                8 -> {
                    if(editText.text.toString().trim().isEmpty()){
                        Toast.makeText(applicationContext, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                        i = 8
                        editText.text = null
                        return@setOnClickListener
                    }
                    var password2: String
                    password2 = editText.text.toString()
                    if(password != password2){
                        Toast.makeText(applicationContext, "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show()
                        i = 8
                        editText.text = null
                        tv_3.text = "비밀번호를 다시 입력해주세요."
                        tv_4.text = "8자 이상의 숫자 또는 영문을 입력해주세요."
                        return@setOnClickListener
                    }

                    editText.text = null
                    tv_3.text = "닉네임을 입력해주세요."
                    tv_4.text = "다른 유저들에게 보여질 이름입니다."
                    btn_1.text = "시작하기"
                    btn_1.background = getDrawable(R.drawable.btn_purple)
                    btn_1.setTextColor(Color.parseColor("#FFFFFF"))
                    editText.hint = "이소담"
                    i++
                }
                9 -> {
                    // 이메일 로그인
                    Auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this){ task ->
                            if(task.isSuccessful) {
                                Log.d("StartActivity", "Sign Success")
                                nickname = editText.text.toString()

                                // 닉네임 저장
                                var user : FirebaseUser? = FirebaseAuth.getInstance().currentUser
                                if(user!=null){
                                    val profileUpdates = UserProfileChangeRequest.Builder()
                                        .setDisplayName(nickname)
                                        .build()
                                    user.updateProfile(profileUpdates)
                                        .addOnCompleteListener { task ->
                                            if(task.isSuccessful){
                                                Log.d("StartActivity", "유저 프로필 업데이트")
                                            } else {
                                                Log.d("StartActivity", "유저 프로필 업데이트 실패")
                                            }
                                        }
                                    Toast.makeText(this.applicationContext,"로그인 성공",Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this, MainActivity::class.java))
                                    finish()
                                } else {
                                    i = 8
                                    Toast.makeText(this.applicationContext,"로그인 실패1",Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                i = 4
                                Log.w("StartActivity", "Login Fail")
                                Toast.makeText(this.applicationContext, "이미 존재하는 이메일 혹은 비밀번호 입니다.", Toast.LENGTH_SHORT).show()
                            }
                        }

                    // 서버로 보내는 로직, 이미 있는 이름인지 확인

                    i = 0
                }
                else -> i = 0
            }
        }

        // 버튼 2 클릭 액션
        btn_2.setOnClickListener {
            when(j){
                0 -> {
                    div_1.visibility = View.VISIBLE
                    div_1.setBackgroundColor(Color.parseColor("#e0e0e0"))
                    img_btn_1.visibility = View.VISIBLE
                    relative_1.visibility = View.GONE
                    img_1.visibility = View.GONE
                    tv_1.visibility = View.GONE
                    tv_2.visibility = View.GONE
                    tv_3.visibility = View.VISIBLE
                    tv_4.visibility = View.VISIBLE
                    tv_3.text = "아이디를 입력해주세요."
                    tv_4.text = "이메일 주소 또는 사업자번호를 입력해주세요."
                    editText.hint = "이메일 주소 또는 사업자 번호"
                    editText.visibility = View.VISIBLE
                    editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                    btn_1.visibility = View.GONE
                    btn_2.text = "다음"
                    j++
                }
                1 -> {
                    if(editText.text.toString().trim().isEmpty() && isEmailValid(editText.text.toString())){
                        Toast.makeText(applicationContext, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
                        j=0
                        editText.text = null
                        return@setOnClickListener
                    }
                    email = editText.text.toString().trim()
                    editText.text = null
                    tv_3.text = "비밀번호를 입력해주세요."
                    tv_4.text = "8자 이상의 숫자 또는 영문을 입력해주세요."
                    editText.hint = "weat1234"
                    editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    btn_2.text = "로그인"
                    j++
                }
                2 -> {
                    j = 0
                    if(editText.text.toString().trim().isEmpty()){
                        Toast.makeText(applicationContext, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                        j=1
                        editText.text = null
                        return@setOnClickListener
                    }
                    password = editText.text.toString().trim()
                    Auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                Toast.makeText(this.applicationContext, "로그인 성공.", Toast.LENGTH_LONG).show()
                                startActivity(Intent(this,MainActivity::class.java))
                                finish()
                            } else{
                                Toast.makeText(this.applicationContext, "아이디 혹은 비밀번호가 잘못되었습니다..", Toast.LENGTH_LONG).show()
                            }
                        }
                }
                3 -> {
                    activity_start.setBackgroundColor(Color.parseColor("#9943FC"))
                    btn_1.visibility = View.GONE
                    btn_2.background = getDrawable(R.drawable.btn_next)
                    btn_2.setTextColor(Color.parseColor("#000000"))
                    tv_3.text = "식당 사장님으로 회원가입합니다."
                    tv_3.setTextColor(Color.parseColor("#FFFFFF"))
                    tv_4.text = "이벤트를 열어 내 가게를 홍보할 수 있습니다."
                    tv_4.setTextColor(Color.parseColor("#FFFFFF"))
                    btn_2.text = "다음"
                    btn_2.setTypeface(null, Typeface.BOLD)
                    j++
                }
                4 -> {
                    editText.text = null
                    editText.setTextColor(Color.parseColor("#FFFFFF"))
                    tv_3.text = "사업자 등록번호를 입력해주세요."
                    tv_4.text = "회원가입 후에 아이디로 사용됩니다."
                    editText.visibility = View.VISIBLE
                    div_1.visibility = View.VISIBLE
                    editText.setTextColor(Color.parseColor("#000000"))
                    div_1.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    j++
                }
                5 -> {
                    editText.text = null
                    editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    tv_3.text = "비밀번호를 입력해주세요."
                    tv_4.text = "8개 이상의 숫자 또는 영문을 입력해주세요."
                    editText.hint = "weat1234"
                    j++
                }
                6 -> {
                    editText.text = null
                    tv_3.text = "상호명을 입력해주세요."
                    editText.inputType = InputType.TYPE_CLASS_TEXT
                    tv_4.text = "다른 유저들에게 보여질 이름입니다."
                    btn_2.text = "시작하기"
                    editText.hint = "이소담"
                    j++
                }
                7 -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                else -> j = 0

            }
        }
    }
}

