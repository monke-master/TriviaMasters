package com.monke.triviamasters.ui.gameFeature

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.monke.triviamasters.App
import com.monke.triviamasters.R
import com.monke.triviamasters.di.components.GameComponent


class GameFragment : Fragment() {

    lateinit var gameComponent: GameComponent

    override fun onAttach(context: Context) {
        super.onAttach(context)
        gameComponent = (activity?.application as App).appComponent.gameComponent().create()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_game, container, false)
    }

}