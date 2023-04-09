package com.nguyencodervn.klad13_livedata

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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

        viewModel.display.observe(viewLifecycleOwner) { display ->
            binding.displayTv.text = display
        }

        viewModel.live.observe(viewLifecycleOwner) { live ->
            binding.liveTv.text = getString(R.string.live, live.toString())
        }

        viewModel.wrong.observe(viewLifecycleOwner) { wrong ->
            binding.wrongTv.text = getString(R.string.wrong, wrong)
        }

        viewModel.win.observe(viewLifecycleOwner) {
            if (it != "") {
                val txt = getString(R.string.win, it)
                val action = GameFragmentDirections
                    .actionGameFragmentToResultFragment(txt)
                findNavController().navigate(action)
            }
        }

        viewModel.lost.observe(viewLifecycleOwner) {
            if (it != "") {
                val txt = getString(R.string.lost, it)
                val action = GameFragmentDirections
                    .actionGameFragmentToResultFragment(txt)
                findNavController().navigate(action)
            }
        }

        binding.apply {
            guessEt.filters = guessEt.filters + InputFilter.AllCaps()
            guessBt.setOnClickListener {
                val letter = guessEt.text.toString()
                viewModel.checkGuessWord(letter)
                guessEt.text = null
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}