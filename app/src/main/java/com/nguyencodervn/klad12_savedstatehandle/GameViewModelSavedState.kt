package com.nguyencodervn.klad12_savedstatehandle

import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class GameViewModelSavedState(
    start: Int,
    handle: SavedStateHandle
) : ViewModel() {
    private var words = arrayOf("Php", "Java")

    var guessWord: String
    var display: String
    var live: Int
    var wrong: String
    private var correct: String

    init {
        val saveState = handle.get<Bundle>("saveState")
        if (saveState != null) {
            Log.i("MYTAG", "saveState recover ")
            guessWord = saveState.getString("guessWord", words.random().uppercase())
            display = saveState.getString("display", "")
            live = saveState.getInt("live", start)
            wrong = saveState.getString("wrong", "")
            correct = saveState.getString("correct", "")
        } else {
            guessWord = words.random().uppercase()
            display = ""
            live = start
            wrong = ""
            correct = ""

        }
        handle.setSavedStateProvider("saveState") {
            Log.i("MYTAG", "saveState processing ... ")
            val bundle = bundleOf(
                "guessWord" to guessWord,
                "display" to display,
                "live:" to live,
                "wrong" to wrong,
                "correct" to correct,
            )
            bundle
        }
    }

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