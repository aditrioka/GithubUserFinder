package id.aditrioka.githubuserfinder.ui.main

import androidx.lifecycle.ViewModelProviders
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import id.aditrioka.githubuserfinder.R
import id.aditrioka.githubuserfinder.model.User
import id.aditrioka.githubuserfinder.ui.UsersAdapter
import kotlinx.android.synthetic.main.main_fragment.*
import id.aditrioka.githubuserfinder.Injection

class MainFragment : androidx.fragment.app.Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = UsersAdapter(context!!)
        viewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(context!!)).get(MainViewModel::class.java)

        etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideIme()
                updateUserListFromInput()
                Log.v(TAG, "ime action search")
                true
            } else {
                false
            }
        }

        etSearch.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                hideIme()
                updateUserListFromInput()
                Log.v(TAG, "action down & keycode enter")
                true
            } else {
                false
            }
        }

        initAdapter()
    }

    private fun initAdapter() {
        recyclerView.adapter = adapter
        viewModel.repos.observe(this, Observer<PagedList<User>> {
            Log.d("Activity", "list: ${it?.size}")
            showEmptyList(it?.size == 0)
            adapter.submitList(it)
        })
        viewModel.networkErrors.observe(this, Observer<String> {
            Toast.makeText(activity, "\uD83D\uDE28 Wooops $it", Toast.LENGTH_LONG).show()
        })
    }

    private fun updateUserListFromInput() {
        etSearch.text.trim().let {
            if (it.isNotEmpty()) {
                recyclerView.scrollToPosition(0)
                viewModel.searchUser(it.toString())
            }
        }
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            emptyTextView.text = getString(R.string.no_data_text)
            emptyTextView.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyTextView.text = getString(R.string.no_data_text)
            emptyTextView.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    private fun hideIme() {
        val inputMethodManager = activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(etSearch.windowToken, 0)
    }

    companion object {
        fun newInstance() = MainFragment()

        private val TAG = MainFragment::class.java.simpleName
    }

}
