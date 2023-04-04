package com.nguyencodervn.klad10_guesswordgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

class GameFragment : Fragment(R.layout.fragment_game) {
    private lateinit var displayTv: TextView
    private lateinit var liveTv: TextView
    private lateinit var wrongTv: TextView
    private lateinit var guessEt: EditText
    private lateinit var guessBt: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.fragment_game,
            container,
            false
        )
        initView(view)
        return view
    }

    private fun initView(view: View) {
        displayTv = view.findViewById(R.id.displayTv)
        liveTv = view.findViewById(R.id.liveTv)
        wrongTv = view.findViewById(R.id.wrongTv)
        guessEt = view.findViewById(R.id.guessEt)
        guessBt = view.findViewById(R.id.guessBt)
    }
}