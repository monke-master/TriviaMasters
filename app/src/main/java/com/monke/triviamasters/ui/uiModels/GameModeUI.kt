package com.monke.triviamasters.ui.uiModels

import android.content.Context
import com.monke.triviamasters.R
import com.monke.triviamasters.domain.models.GameMode


data class GameModeUI(
    val mode: GameMode,
    val title: String,
    val onClick: (gameMode: GameMode) -> Unit,
) {

    companion object {

        fun getTitle(mode: GameMode, context: Context) =
            when (mode) {
                GameMode.OwnGame -> context.getString(R.string.own_game)
                GameMode.Category -> context.getString(R.string.search_categories)
                GameMode.FullyRandom -> context.getString(R.string.fully_random)
                GameMode.ExtraHard -> context.getString(R.string.extra_hard)
            }

        fun getDescription(mode: GameMode, context: Context) =
            when (mode) {
                GameMode.OwnGame -> context.getString(R.string.own_game_description)
                GameMode.Category -> context.getString(R.string.search_categories_description)
                GameMode.FullyRandom -> context.getString(R.string.fully_random_description)
                GameMode.ExtraHard -> context.getString(R.string.extra_hard_description)
            }
    }

}
