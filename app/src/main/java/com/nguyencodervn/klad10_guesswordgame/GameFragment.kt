package com.nguyencodervn.klad10_guesswordgame

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class GameFragment : Fragment(R.layout.fragment_game) {
    private lateinit var displayTv: TextView
    private lateinit var liveTv: TextView
    private lateinit var wrongTv: TextView
    private lateinit var guessEt: EditText
    private lateinit var guessBt: Button
    private lateinit var words: Array<String>
    private lateinit var guessWord: String

    private var display = ""
    private var live = 8
    private var wrong = ""
    private var correct = ""

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
        words = requireActivity().resources.getStringArray(
            R.array.list_word
        )
        guessWord = words.random().uppercase()
        updateDisplay()
        updateUI()
        // convert to uppercase
        guessEt.filters = guessEt.filters + InputFilter.AllCaps()

        guessBt.setOnClickListener {
            val letter = guessEt.text
            if (letter.isNotEmpty()) {
                if (correct.contains(letter) ||
                    wrong.contains(letter)
                ) {
                    Toast.makeText(
                        context,
                        getString(R.string.guess_one, letter),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    if (guessWord.contains(letter)) {
                        correct += letter
                    } else {
                        wrong += "$letter "
                        live--
                    }
                    updateDisplay()
                    updateUI()
                    guessEt.text = null
                }

            }
            var transferTxt = ""
            if (live == 0) {
                transferTxt = getString(R.string.lost, guessWord)
            }
            if (display == guessWord) {
                transferTxt = getString(R.string.win, guessWord)
            }
            if (transferTxt.isNotBlank()) {
                val action = GameFragmentDirections
                    .actionGameFragmentToResultFragment(transferTxt)
                findNavController().navigate(action)
            }
        }
        return view
    }

    private fun initView(view: View) {
        displayTv = view.findViewById(R.id.displayTv)
        liveTv = view.findViewById(R.id.liveTv)
        wrongTv = view.findViewById(R.id.wrongTv)
        guessEt = view.findViewById(R.id.guessEt)
        guessBt = view.findViewById(R.id.guessBt)
    }

    private fun updateUI() {
        displayTv.text = display
        liveTv.text = getString(R.string.live, live.toString())
        wrongTv.text = getString(R.string.wrong, wrong)

    }

    private fun updateDisplay() {
        var str = ""
        guessWord.forEach {
            str += if (correct.contains(it.toString())) it.toString()
            else "_"
        }
        display = str
    }
}