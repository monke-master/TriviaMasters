package com.monke.triviamasters.ui.uiModels

import android.content.Context
import com.monke.triviamasters.R
import com.monke.triviamasters.domain.models.GameMode


data class GameModeUI(
    val mode: GameMode,
    val title: String,
    val onClick: (gameMode: GameMode, destination: Int) -> Unit,
    val destination: Int
)