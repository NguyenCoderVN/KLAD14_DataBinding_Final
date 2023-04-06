package com.nguyencodervn.klad11_viewbinding_viewmodel

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
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nguyencodervn.klad11_viewbinding_viewmodel.databinding.FragmentGameBinding

class GameFragment : Fragment(R.layout.fragment_game) {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    // Cách 01
    //private lateinit var viewModel: GameViewModel

    //Cách 02
//    private val viewModel: GameViewModel by lazy {
//        val viewModelFactory = GameViewModelFactory(7)
//        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
//    }

    // Cách 03
    // Chú ý thêm implementation 'androidx.fragment:fragment-ktx:1.5.6'
    // private val viewModel : GameViewModel by viewModels()

    // Android ViewModel
        private val viewModel: GameAndroidViewModel by lazy {
        ViewModelProvider(this)[GameAndroidViewModel::class.java]
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
                // convert to uppercase
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