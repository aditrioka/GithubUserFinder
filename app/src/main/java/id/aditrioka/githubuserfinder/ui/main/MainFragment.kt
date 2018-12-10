package id.aditrioka.githubuserfinder.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.getSystemService
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import id.aditrioka.githubuserfinder.R
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()

        private val TAG = MainFragment::class.java.simpleName
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        etSearch.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideIme()
//                updateUserListFromInput()
                Log.v(TAG, "ime action search")
                true
            } else {
                false
            }
        }

        etSearch.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                hideIme()
//                updateUserListFromInput()
                Log.v(TAG, "action down & keycode enter")
                true
            } else {
                false
            }
        }
    }

//    private fun updateUserListFromInput() {
//        etSearch.text.trim().let {
//            if (it.isNotEmpty()) {
//                recyclerView.scrollTo(0)
//                viewModel.searchUser(it.toString())
//            }
//        }
//    }

    private fun hideIme() {
        val inputMethodManager = activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(etSearch.windowToken, 0)
    }

}
