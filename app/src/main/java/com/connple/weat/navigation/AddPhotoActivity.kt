package com.connple.weat.navigation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.connple.weat.R
import com.connple.weat.navigation.model.ContentDTO
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_add_photo.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.PI


class AddPhotoActivity : AppCompatActivity() {
    var PICK_IMAGE_FROM_ALBUM = 0
    var storage: FirebaseStorage? = null
    var photoUri: Uri? = null
    var auth : FirebaseAuth? = null
    var firestore : FirebaseFirestore? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo)

        //스토리지 초기화
        storage = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        var photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, PICK_IMAGE_FROM_ALBUM)

        addphoto_btn_upload.setOnClickListener {
            contentUpload()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            if(resultCode == Activity.RESULT_OK){
                //이미지 선택
                photoUri = data?.data
                addphoto_image.setImageURI(photoUri)
            }
            else{
                //이미지 선택 없이 종료
                finish()
            }
    }
    private fun contentUpload(){
        //파일 네임 설정
        var timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var imageFileName = "IMAGE_" + timestamp + "_.png"

        var storageRef = storage?.reference?.child("image")?.child(imageFileName)

        //Promise 함수
        storageRef?.putFile(photoUri!!)?.continueWithTask { task: Task<UploadTask.TaskSnapshot> ->
            return@continueWithTask storageRef.downloadUrl
        }?.addOnSuccessListener { uri ->
            var contentDTO = ContentDTO()

            //이미지 다운로드 url 삽입
            contentDTO.imageUrl = uri.toString()

            // 유저의 uid 삽입
            contentDTO.uid = auth?.currentUser?.uid

            // 유저id 삽입
            contentDTO.userId = auth?.currentUser?.email

            // 콘텐츠 설명 삽입
            contentDTO.explain = addphoto_edit_explain.text.toString()

            // 타임스탬프 삽입
            contentDTO.timestamp = System.currentTimeMillis()

            firestore?.collection("images")?.document()?.set(contentDTO)

            setResult(Activity.RESULT_OK)

            finish()
        }

//        //콜백 함수
//        storageRef?.putFile(photoUri!!)?.addOnSuccessListener{
//            storageRef.downloadUrl.addOnSuccessListener { uri ->
//                var contentDTO = ContentDTO()
//
//                //이미지 다운로드 url 삽입
//                contentDTO.imageUrl = uri.toString()
//
//                // 유저의 uid 삽입
//                contentDTO.uid = auth?.currentUser?.uid
//
//                // 유저id 삽입
//                contentDTO.userId = auth?.currentUser?.email
//
//                // content 설명 삽입
//                contentDTO.timestamp = System.currentTimeMillis()
//
//                firestore?.collection("images")?.document()?.set(contentDTO)
//
//                setResult(Activity.RESULT_OK)
//
//                finish()
//            }
//        }
    }

}

