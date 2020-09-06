package com.cermati.putu.ui.main

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.cermati.putu.R
import com.cermati.putu.data.models.user.DataUser
import com.cermati.putu.ui.shared.View
import com.cermati.putu.utils.EndlessRecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_basic.*
import kotlinx.android.synthetic.main.layout_empty.*
import kotlinx.android.synthetic.main.loading_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.view.View as system


class MainActivity : View() {
    private val viewModel: MainViewModel by viewModel()
    private var lastKeywordSearch: String = ""
    private val firstPage: Int = 1
    private var page: Int = firstPage
    private var totalRecord: Long = 0

    private val mainAdapter: MainAdapter by lazy {
        MainAdapter()
    }

    private val layoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private val scrollListener: EndlessRecyclerView by lazy {
        EndlessRecyclerView(layoutManager as LinearLayoutManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeData()
        listenEdiTextSearch()
        showBasicLayout()
        initAdapter()
        listenScrollEvent()

    }

    private fun listenScrollEvent() {
        scrollListener.setOnLoadMoreListener(object : EndlessRecyclerView.OnLoadMoreListener {
            override fun onLoadMore() {
                if (lastKeywordSearch.isNotEmpty() && !scrollListener.getLoaded() && mainAdapter.itemCount < totalRecord) {
                    search(lastKeywordSearch, page)
                }
            }

        })
    }

    private fun initAdapter() {
        rvUser.also {
            it.adapter = mainAdapter
            it.layoutManager = layoutManager
            it.addOnScrollListener(scrollListener)
        }
    }

    private fun listenEdiTextSearch() {
        etSearch.setOnEditorActionListener(OnEditorActionListener { view, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    if (etSearch.text.toString() != lastKeywordSearch) {
                        page = firstPage
                        mainAdapter.removeUsers()
                    }
                    if (etSearch.text.toString().isNotEmpty()) {
                        lastKeywordSearch = etSearch.text.toString()
                        search(
                            lastKeywordSearch,
                            page
                        )
                    } else {
                        page = firstPage
                        mainAdapter.removeUsers()
                        showBasicLayout()
                        hideEmptyLayout()
                        hideListUser()
                        hideShimmerLoading()
                    }

                    hideSoftKeyboard(view)

                    return@OnEditorActionListener true
                }
            }
            false
        })
    }

    private fun hideSoftKeyboard(view: TextView) {
        view.let { v ->
            val imm =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    private fun observeData() {
        viewModel.state.observe(this, Observer { observer ->
            observer?.let {
                when (it) {
                    is MainViewState.Loading -> {
                        if (mainAdapter.itemCount > 0) {
                            mainAdapter.showLoading()
                        } else {
                            showShimmerLoading()
                            hideBasicLayout()
                            hideEmptyLayout()
                            hideListUser()
                        }
                    }

                    is MainViewState.Result -> {
                        hideListUser()
                        hideEmptyLayout()
                        hideBasicLayout()
                        hideShimmerLoading()

                        when {
                            it.userResponse.items.isNotEmpty() -> {
                                page++
                                totalRecord = it.userResponse.totalCount
                                showListUser(it.userResponse.items)
                            }
                            mainAdapter.itemCount > 0 && it.userResponse.items.isEmpty() -> {
                                showListUser(mutableListOf())
                            }
                            else -> {
                                showEmptyLayout()
                            }
                        }
                        scrollListener.setLoaded()
                    }

                    is MainViewState.Error -> {
                        hideShimmerLoading()
                        mainAdapter.hideLoading()
                        scrollListener.setLoaded()
                        if (mainAdapter.itemCount <= 0) showBasicLayout()
                        Toast.makeText(
                            applicationContext,
                            it.throwable.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }

    private fun showShimmerLoading() {
        shUser.startShimmerAnimation();
        shUser.visibility = system.VISIBLE
    }

    private fun hideShimmerLoading() {
        shUser.stopShimmerAnimation()
        shUser.visibility = system.GONE
    }

    private fun showEmptyLayout() {
        layoutEmpty.visibility = system.VISIBLE
    }

    private fun hideEmptyLayout() {
        layoutEmpty.visibility = system.GONE
    }

    private fun showListUser(users: List<DataUser>) {
        if (page <= firstPage) mainAdapter.removeUsers()
        mainAdapter.hideLoading()
        mainAdapter.addUsers(users)
        rvUser.visibility = system.VISIBLE
        scrollListener.setLoaded()
    }

    private fun hideListUser() {
        rvUser.visibility = system.GONE
    }

    private fun showBasicLayout() {
        layoutBasic.visibility = system.VISIBLE
    }

    private fun hideBasicLayout() {
        layoutBasic.visibility = system.GONE
    }

    private fun search(keyword: String, page: Int) {
        viewModel.searchUser(keyword, page)
    }
}