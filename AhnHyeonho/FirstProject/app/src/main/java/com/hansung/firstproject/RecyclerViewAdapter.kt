package com.hansung.firstproject

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_item.view.*

class RecyclerViewAdapter(private val movies: Movies) :
    RecyclerView.Adapter<RecyclerViewAdapter.MovieHolder>() {

    //영화 아이템의 갯수 가져오기
    override fun getItemCount() = movies.items.size

    // ViewHolder를 생성하고 View를 붙여주는 method
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieHolder(v)
    }

    //재활용 되는 View가 호출하여 실행되는 메소드, 뷰 홀더를 전달하고 Adapter는 position 의 데이터를 결합
    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bindItems(movies.items[position])
    }


    class MovieHolder(internal val movieView: View) : RecyclerView.ViewHolder(movieView) {
        @SuppressLint("SetTextI18n")
        fun bindItems(data: Movie) {
            //
            Glide.with(movieView.context).load(data.image)
                .apply(RequestOptions().override(300, 450))
                .apply(RequestOptions.centerCropTransform())
                .into(movieView.movie_image)

            itemView.movie_grade.rating = data.userRating.toFloat() / 2
            itemView.movie_releaseDate.text = data.pubDate
            itemView.movie_title.text = stripHtml(data.title)
            itemView.movie_director.text = "감독 ${data.director}"
            itemView.movie_actor.text = "출연 ${data.actor}"

            //클릭시 웹사이트 연결
            itemView.setOnClickListener {
                val webPage = Uri.parse(data.link)
                val webIntent = Intent(Intent.ACTION_VIEW, webPage)
                movieView.context.startActivity(webIntent)
            }
        }
    }
}

// 검색어와 일치하는 부분은 태그로 감싸져 오는 제목 String에서 HTML Tag를 제거하는 method
fun stripHtml(html: String): String { return Html.fromHtml(html).toString() }