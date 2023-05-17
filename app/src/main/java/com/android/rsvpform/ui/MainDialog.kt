package com.android.rsvpform.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.android.rsvpform.R
import com.android.rsvpform.databinding.DialogMainBinding

class MainDialog : DialogFragment() {
    companion object {
        private const val KEY_DIALOG_TITLE: String = "KEY_DIALOG_TITLE"
        private const val IS_SUCCESS: String = "IS_SUCCESS"
        fun newInstance(title: String, isSuccess: Boolean = false) = MainDialog().apply {
            arguments = bundleOf(
                KEY_DIALOG_TITLE to title,
                IS_SUCCESS to isSuccess
            )
        }
    }

    private lateinit var binding: DialogMainBinding
    internal var onOkClicked: () -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            dialogDescription.text = arguments?.getString(KEY_DIALOG_TITLE, "") ?: ""
            btnOk.setOnClickListener {
                onOkClicked.invoke()
                this@MainDialog.dismiss()
            }
            if (arguments?.getBoolean(IS_SUCCESS, true) == true) {
                dialogTitle.text = getString(R.string.thank_you)
            } else {
                dialogTitle.text = getString(R.string.error)
            }
        }
    }
}
