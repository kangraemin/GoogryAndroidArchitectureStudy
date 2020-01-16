package com.song2.myapplication.adapter

import android.content.Intent
import android.net.Uri
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.song2.myapplication.source.MovieData
import kotlinx.android.synthetic.main.rv_movie_item.view.*


@Suppress("DEPRECATION")
class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

    lateinit var tempData: MovieData

    private val image: ImageView = view.iv_rv_movie_item_image
    private val title: TextView = view.tv_rv_movie_item_title
    private val rating: RatingBar = view.rb_rv_movie_item_rating
    private val director: TextView = view.tv_rv_movie_item_director
    private val pubDate: TextView = view.tv_rv_movie_item_pub_date
    private val actor: TextView = view.tv_rv_movie_item_actor


    override fun onClick(view: View?) {
        val pos = adapterPosition
        Log.e("position", pos.toString())

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(tempData.link))
        this.itemView.context.startActivity(intent)
    }

    //바인딩 될 때마다 호출 .. 리스너가 반복호출 될 필요는 없음
    fun onBind(movieData: MovieData) {
        tempData = movieData

        title.text = Html.fromHtml(movieData.title)
        pubDate.text = movieData.pubDate
        actor.text = movieData.actor
        director.text = movieData.director
        rating.rating = (movieData.userRating!! / 2).toFloat()

        Glide.with(itemView.context).load(movieData.image).into(image)

        itemView.setOnClickListener {
            onClick(itemView)
        }
    }
}