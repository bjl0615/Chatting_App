package com.example.chatting_app.chatdetail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatting_app.Key
import com.example.chatting_app.databinding.ActivityChatderailBinding
import com.example.chatting_app.userList.UserItem
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChatderailBinding

    private var chatRoomId : String = ""
    private var otherUserId : String = ""
    private var myUserId : String = ""

    private val chatItemList = mutableListOf<ChatItem>()
    // chatRoomID putExtra
    // otherUserId

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatderailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chatRoomId = intent.getStringExtra("chatRoomId") ?: return
        otherUserId = intent.getStringExtra("otherUserId") ?: return
        myUserId = Firebase.auth.currentUser?.uid ?: ""
        val chatAdapter = ChatAdapter()

        Firebase.database.reference.child(Key.DB_USERS).child(myUserId).get()
            .addOnSuccessListener {
                val myUserItem = it.getValue(UserItem::class.java)
                val myUserName = myUserItem?.userId
            }
        Firebase.database.reference.child(Key.DB_USERS).child(otherUserId).get()
            .addOnSuccessListener {
                val otherUserItem = it.getValue(UserItem::class.java)

                chatAdapter.otherUserItem = otherUserItem
            }


        Firebase.database.reference.child(Key.DB_CHATS).child(chatRoomId).addChildEventListener( object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatItem = snapshot.getValue(ChatItem::class.java)
                chatItem ?: return

                chatItemList.add(chatItem)
                chatAdapter.submitList(chatItemList)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}

        }
        )

        binding.chatRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = chatAdapter
        }
    }
}