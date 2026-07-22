package com.example.fitnesstracker

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesstracker.databinding.ActivityChatbotBinding
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class ChatbotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatbotBinding
    private lateinit var chatAdapter: ChatAdapter
    private val messages = mutableListOf<Message>()

    // Using gemini-1.5-flash for the best AI experience
    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = "AIzaSyCUfVVsSr4Kt7Rt3P1zmYL-QP2zjZP1uko"
    )

    // Expanded Local Knowledge Base (10+ Questions) to guarantee answers
    private val localAnswers = mapOf(
        "How to lose weight?" to "To lose weight, you need a calorie deficit. Focus on eating high-protein foods, plenty of vegetables, and staying active with both cardio and strength training.",
        "Best cardio?" to "The best cardio is the one you enjoy! Running, swimming, cycling, and HIIT are all great for burning calories and improving heart health.",
        "Muscle gain tips?" to "For muscle gain, focus on progressive overload in your weight lifting, eat a protein-rich diet, and ensure you are in a slight calorie surplus.",
        "How much water to drink?" to "Aim for 3-4 liters of water a day. Proper hydration is essential for performance, recovery, and overall health.",
        "What to eat before workout?" to "A mix of complex carbs and some protein is ideal. Try oatmeal with fruit or a slice of whole-grain toast with peanut butter about 1-2 hours before training.",
        "How to reduce belly fat?" to "You cannot spot-reduce fat. To lose belly fat, focus on overall weight loss through a healthy diet and full-body exercises.",
        "Best time to exercise?" to "The best time is whenever you can be most consistent. Some prefer the morning for energy, while others prefer the evening to de-stress.",
        "Importance of sleep?" to "Sleep is when your body recovers and builds muscle. Aim for 7-9 hours of quality sleep every night for best fitness results.",
        "How to stay motivated?" to "Set small, achievable goals, track your progress, and find a workout buddy. Remember your 'why' when things get tough!",
        "What is BMI?" to "BMI (Body Mass Index) is a simple calculation using your height and weight to estimate body fatness, but it doesn't distinguish between muscle and fat.",
        "Best diet for weight loss?" to "A balanced diet with lean proteins, healthy fats, and complex carbohydrates while maintaining a calorie deficit is best.",
        "Daily water goal?" to "Drink at least 8-10 glasses (about 2.5-3 liters) daily, more if you are exercising heavily."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = ActivityChatbotBinding.inflate(layoutInflater)
            setContentView(binding.root)
            supportActionBar?.hide()

            chatAdapter = ChatAdapter(messages)
            binding.chatRecyclerView.adapter = chatAdapter
            binding.chatRecyclerView.layoutManager = LinearLayoutManager(this)

            binding.btnBack.setOnClickListener { finish() }

            binding.btnSend.setOnClickListener {
                val text = binding.messageInput.text.toString().trim()
                if (text.isNotEmpty()) {
                    sendMessage(text)
                    binding.messageInput.text.clear()
                }
            }

            // Predefined query buttons
            binding.query1.setOnClickListener { sendMessage("How to lose weight?") }
            binding.query2.setOnClickListener { sendMessage("Best cardio?") }
            binding.query3.setOnClickListener { sendMessage("Muscle gain tips?") }

            if (messages.isEmpty()) {
                addMessage("Hello! I'm your AI Fitness Buddy. Ask me any fitness question!", false)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun sendMessage(userText: String) {
        addMessage(userText, true)
        
        // Check local knowledge first for instant answers
        val normalizedText = userText.trim().lowercase()
        for ((question, answer) in localAnswers) {
            if (normalizedText.contains(question.lowercase().replace("?", ""))) {
                addMessage(answer, false)
                return
            }
        }
        
        // If not in local knowledge, try Gemini AI
        lifecycleScope.launch {
            try {
                addMessage("Thinking...", false)
                val response = generativeModel.generateContent(content { text(userText) })
                
                removeThinkingMessage()
                
                val botResponse = response.text ?: "I'm sorry, I couldn't process that. Could you try rephrasing?"
                addMessage(botResponse, false)
            } catch (e: Exception) {
                removeThinkingMessage()
                val errorMsg = e.localizedMessage ?: "Connection error"
                
                // If AI fails, give a friendly fitness tip instead of just an error
                if (errorMsg.contains("404") || errorMsg.contains("not found")) {
                    addMessage("I'm having a little trouble connecting to my advanced brain right now. In the meantime, remember that consistency is the key to all fitness goals!", false)
                } else {
                    addMessage("AI Buddy: It looks like I'm having some trouble. Try asking about weight loss, muscle gain, or cardio!", false)
                }
            }
        }
    }

    private fun removeThinkingMessage() {
        val index = messages.indexOfLast { it.text == "Thinking..." }
        if (index != -1) {
            messages.removeAt(index)
            chatAdapter.notifyItemRemoved(index)
        }
    }

    private fun addMessage(text: String, isUser: Boolean) {
        messages.add(Message(text, isUser))
        chatAdapter.notifyItemInserted(messages.size - 1)
        binding.chatRecyclerView.scrollToPosition(messages.size - 1)
    }

    class ChatAdapter(private val messages: List<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        companion object {
            private const val VIEW_TYPE_USER = 1
            private const val VIEW_TYPE_BOT = 2
        }

        override fun getItemViewType(position: Int): Int {
            return if (messages[position].isUser) VIEW_TYPE_USER else VIEW_TYPE_BOT
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return if (viewType == VIEW_TYPE_USER) {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message_user, parent, false)
                UserViewHolder(view)
            } else {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message_bot, parent, false)
                BotViewHolder(view)
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val message = messages[position]
            if (holder is UserViewHolder) {
                holder.textUser.text = message.text
                holder.textUser.setTextColor(Color.WHITE)
            } else if (holder is BotViewHolder) {
                holder.textBot.text = message.text
                holder.textBot.setTextColor(Color.WHITE)
            }
        }

        override fun getItemCount(): Int = messages.size

        class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textUser: TextView = view.findViewById(R.id.textUser)
        }

        class BotViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textBot: TextView = view.findViewById(R.id.textBot)
        }
    }
}
