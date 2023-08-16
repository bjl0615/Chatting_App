package com.example.chatting_app.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.chatting_app.LoginActivity
import com.example.chatting_app.R
import com.example.chatting_app.databinding.FragmentMypageBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MyPageFragment : Fragment(R.layout.fragment_mypage) {

    private lateinit var binding : FragmentMypageBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMypageBinding.bind(view)

        binding.applyButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val description = binding.descriptionEditText.text.toString()

            if(username.isEmpty() || description.isEmpty()) {
                Toast.makeText(context , "유저이름은 빈 값으로 둘 수 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }

        binding.signOutButton.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(context, LoginActivity::class.java))
            activity?.finish()
        }
    }

}