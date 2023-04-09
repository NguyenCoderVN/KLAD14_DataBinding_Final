package com.nguyencodervn.klad13_livedata

import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class GameViewModelSavedState(
    start: Int,
    handle: SavedStateHandle
) : ViewModel() {
    private var words = arrayOf("Php", "Java")

    var guessWord: String

    private val _display = MutableLiveData<String>() // Backing Field
    val display: LiveData<String> // Backing Property
        get() = _display

    private val _live = MutableLiveData<Int>()
    val live: LiveData<Int>
        get() = _live

    private val _wrong = MutableLiveData<String>()
    val wrong: LiveData<String>
        get() = _wrong

    private var correct: String

    init {
        val saveState = handle.get<Bundle>("saveState")
        if (saveState != null) {
            Log.i("MYTAG", "saveState recover ")
            guessWord = saveState.getString("guessWord", words.random().uppercase())
            _display.value = saveState.getString("display", "")
            _live.value = saveState.getInt("live", start)
            _wrong.value = saveState.getString("wrong", "")
            correct = saveState.getString("correct", "")
        } else {
            guessWord = words.random().uppercase()
            _display.value = ""
            _live.value = start
            _wrong.value = ""
            correct = ""

        }
        handle.setSavedStateProvider("saveState") {
            Log.i("MYTAG", "saveState processing ... ")
            val bundle = bundleOf(
                "guessWord" to guessWord,
                "display" to _display.value,
                "live:" to _live.value,
                "wrong" to _wrong.value,
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
        _display.value = str
    }

    fun checkGuessWord(letter: String) {
        if (letter.isNotEmpty()) {
            if (!correct.contains(letter) &&
                !_wrong.value?.contains(letter)!!
            ) {
                if (guessWord.contains(letter)) {
                    correct += letter
                } else {
                    _wrong.value += "$letter "
                    _live.value = _live.value?.minus(1)
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