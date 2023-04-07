package com.nguyencodervn.klad12_savedstatehandle

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel(start: Int) : ViewModel() {
    private var words = arrayOf("Php", "Java")
    var guessWord = words.random().uppercase()

    var display = ""
    var live = start
    var wrong = ""
    private var correct = ""

    fun updateDisplay() {
        var str = ""
        guessWord.forEach {
            str += if (correct.contains(it.toString())) it.toString()
            else "_"
        }
        display = str
    }

    fun checkGuessWord(letter: String) {
        if (letter.isNotEmpty()) {
            if (!correct.contains(letter) &&
                !wrong.contains(letter)
            ) {
                if (guessWord.contains(letter)) {
                    correct += letter
                } else {
                    wrong += "$letter "
                    live--
                }
                updateDisplay()
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }
}