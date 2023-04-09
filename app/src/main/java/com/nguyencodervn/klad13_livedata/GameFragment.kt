package com.nguyencodervn.klad13_livedata

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nguyencodervn.klad13_livedata.databinding.FragmentGameBinding

class GameFragment : Fragment(R.layout.fragment_game) {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GameViewModelSavedState by viewModels {
        GameViewModelSavedStateFactory(5)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(
            inflater, container, false
        )

        val view = binding.root
        viewModel.updateDisplay()
        updateUI()
        binding.apply {
            viewModel.apply {

                guessEt.filters = guessEt.filters + InputFilter.AllCaps()

                guessBt.setOnClickListener {
                    val letter = guessEt.text.toString()
                    checkGuessWord(letter)
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
                    updateUI()
                }
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUI() {
        binding.apply {
            viewModel.apply {
                displayTv.text = display
                liveTv.text = getString(R.string.live, live.toString())
                wrongTv.text = getString(R.string.wrong, wrong)
                guessEt.text = null
            }
        }
    }
}