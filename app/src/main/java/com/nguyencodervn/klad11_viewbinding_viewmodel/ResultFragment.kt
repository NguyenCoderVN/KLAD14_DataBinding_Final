package com.nguyencodervn.klad11_viewbinding_viewmodel

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class ResultFragment : Fragment(R.layout.fragment_result) {
    private lateinit var textView: TextView
    private lateinit var button: Button
    private val args : ResultFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView = view.findViewById(R.id.resultTv)
        button = view.findViewById(R.id.newGameBt)

        textView.text = args.result
        button.setOnClickListener {
            findNavController().navigate(R.id.gameFragment)
        }

    }
}
