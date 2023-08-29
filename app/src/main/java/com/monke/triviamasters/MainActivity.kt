package com.monke.triviamasters

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.monke.triviamasters.ui.components.QuestProgressView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val meineView = findViewById<QuestProgressView>(R.id.quest_progress)
//        meineView.currentQuestion = 1
//        meineView.questionsCount = 5
//
//        lifecycleScope.launch {
//            for (i in 1..meineView.questionsCount) {
//                meineView.currentQuestion = i
//                delay(1000)
//            }
//        }


    }
}