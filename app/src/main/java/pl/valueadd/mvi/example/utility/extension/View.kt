package pl.valueadd.mvi.example.utility.extension

import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.View
import android.widget.EditText
import androidx.annotation.ColorInt
import androidx.annotation.IdRes

fun View.setVisible(isVisible: Boolean, falseRes: Int = View.GONE) {

    val visibility = if (isVisible) View.VISIBLE else falseRes

    this.visibility = visibility
}

fun Menu.setTextColor(@IdRes idRes: Int, @ColorInt color: Int) {
    this.findItem(idRes)?.apply {
        val s = SpannableString(title)
        s.setSpan(ForegroundColorSpan(color), 0, s.length, 0)
        title = s
    }
}

fun Menu.show(@IdRes idRes: Int) {
    this.findItem(idRes)?.isVisible = true
}

fun EditText.applyTextChanges(text: String) {
    if (text != this.text.toString()) {
        this.setText(text)
        this.setSelection(this.length())
    }
}