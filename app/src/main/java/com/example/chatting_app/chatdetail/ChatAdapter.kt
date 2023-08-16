package com.example.chatting_app.chatdetail

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chatting_app.databinding.ItemChatBinding
import com.example.chatting_app.userList.UserItem

class ChatAdapter : ListAdapter<ChatItem, ChatAdapter.ViewHolder>(differ) {

    var otherUserItem : UserItem? = null

    inner class ViewHolder(private val binding : ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : ChatItem) {
            if(item.userId == otherUserItem?.userId) {
                binding.messageTextView.isVisible = true
                binding.usernameTextView.text = otherUserItem?.username
                binding.messageTextView.text = item.message
                binding.messageTextView.gravity = Gravity.START
            }else {
                binding.messageTextView.isVisible = false
                binding.messageTextView.text = item.message
                binding.messageTextView.gravity = Gravity.END
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemChatBinding.inflate(
            LayoutInflater.from(parent.context),
                parent,
                false
                )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val differ = object :  DiffUtil.ItemCallback<ChatItem>() {
            override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
                return oldItem.chatId == newItem.chatId
            }

            override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
                return oldItem == newItem
            }

        }
    }


}