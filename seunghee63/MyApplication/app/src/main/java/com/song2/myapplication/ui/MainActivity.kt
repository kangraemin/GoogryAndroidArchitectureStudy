package com.song2.myapplication.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.song2.myapplication.R
import com.song2.myapplication.adapter.MovieAdapter
import com.song2.myapplication.source.MovieData
import kotlinx.android.synthetic.main.activity_main.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), MainContract.View {

    private val imm: InputMethodManager by lazy { getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }
    private val movieAdapter by lazy { MovieAdapter() }
    private val presenter: MainContract.Presenter by lazy { MainPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setKeyboardFunc()
        setMovieRecyclerView()

        btn_main_act_search_btn.setOnClickListener {

            if (et_main_act_search.text.toString() != "") {
                movieAdapter.clearData()
                presenter.getMovie(et_main_act_search.text.toString())
            }
        }
    }

    override fun showGetMovieSuccess(movieDataList: List<MovieData>) {

        imm.hideSoftInputFromWindow(et_main_act_search.windowToken, 0)

        movieAdapter.addItem(movieDataList)
    }

    override fun showGetMovieFailure(e: Throwable) {
        Log.e("통신 실패", e.toString())
    }

    override fun setResultVisible() {
        tv_main_act_movie_list.visibility = View.GONE
    }

    override fun setResultGone() {
        tv_main_act_movie_list.visibility = View.VISIBLE
    }


    override fun getListCnt(): Int {
        return movieAdapter.itemCount
    }

    private fun setMovieRecyclerView() {

        rv_main_act_movie_list.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = movieAdapter
        }

        rv_main_act_movie_list.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!rv_main_act_movie_list.canScrollVertically(-1)) {
                    presenter.getMovie(et_main_act_search.text.toString())
                }
            }
        })
    }

    private fun setKeyboardFunc() {

        et_main_act_search.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent?): Boolean {
                when (actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        movieAdapter.clearData()
                        presenter.getMovie(et_main_act_search.text.toString())
                    }
                    else ->
                        return false
                }
                return true
            }
        })
    }
}