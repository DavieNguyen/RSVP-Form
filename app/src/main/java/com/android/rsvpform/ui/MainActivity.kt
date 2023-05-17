package com.android.rsvpform.ui

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.rsvpform.data.api.RetrofitService
import com.android.rsvpform.data.repository.MainRepository
import com.android.rsvpform.databinding.ActivityMainBinding
import com.android.rsvpform.utils.MyViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        afterViewCreated()
        initListeners()
        setStatusBarColor(android.R.color.holo_red_light)
    }

    private fun setStatusBarColor(@ColorRes color: Int) {
        window.run {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = getColor(color)
        }
    }

    private fun afterViewCreated() {
        // init retrofit
        val retrofitService = RetrofitService.getInstance()
        val repository = MainRepository(retrofitService)
        viewModel = ViewModelProvider(
            this@MainActivity,
            MyViewModelFactory(repository)
        )[MainViewModel::class.java]
    }

    private fun initListeners() {
        binding.run {
            btnSubmit.setOnClickListener {
                viewModel.submitForm(
                    edtFirstName.text.toString(),
                    edtLastName.text.toString(),
                    edtContactNumber.text.toString(),
                    edtEmail.text.toString()
                )
            }

            viewModel.loading.observe(this@MainActivity) {
                if (it) {
                    progressDialog.visibility = View.VISIBLE
                } else {
                    progressDialog.visibility = View.GONE
                }
            }

            viewModel.result.observe(this@MainActivity) {
                MainDialog.newInstance(it.message, it.isSuccess)
                    .apply { onOkClicked = { clearForm() } }
                    .show(supportFragmentManager, MainDialog::class.java.name)
            }
        }
    }

    private fun clearForm() {
        binding.run {
            edtFirstName.text.clear()
            edtLastName.text.clear()
            edtContactNumber.text.clear()
            edtEmail.text.clear()
        }
    }

    // Handle hide keyboard when click outside edittext
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRec = Rect()
                v.getGlobalVisibleRect(outRec)
                if (!outRec.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}
