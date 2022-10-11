package com.example.parliamentapp


import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.parliamentapp.network.ParliamentApiStatus
import com.example.parliamentapp.network.Party

/**
 * Author: Roope Laine
 * Student ID: 2114735
 * Date: 11.10.2022
 *
 * BindingAdapters called by the XML layouts
 */

// Get image of selected member using Glide library
@BindingAdapter("imageUrl")
fun loadImage(imgView: ImageView, imgUrl: String?) {
    val memberImageUrl = "https://avoindata.eduskunta.fi/$imgUrl"
    memberImageUrl.let {
        val imgUri = memberImageUrl.toUri().buildUpon().scheme("https").build()

        Glide.with(imgView.context).load(imgUri)
            .apply(RequestOptions().placeholder(R.drawable.loading_animation)).into(imgView)
    }
}

// Get party logos
@BindingAdapter("partyLogo")
fun ImageView.bindLogo(item: Party) {
    setImageResource(
        when (item.party) {
            "kesk" -> R.mipmap.ic_kesk_logo
            "kd" -> R.mipmap.ic_kd_logo
            "kok" -> R.mipmap.ic_kok_logo
            "liik" -> R.mipmap.ic_liik_logo
            "ps" -> R.mipmap.ic_ps_logo
            "r" -> R.mipmap.ic_r_logo
            "sd" -> R.mipmap.ic_sdp_logo
            "vas" -> R.mipmap.ic_vas_logo
            "vihr" -> R.mipmap.ic_vihr_logo
            "vkk" -> R.mipmap.ic_vkk_logo
            else -> R.drawable.ic_error_logo
        }
    )
}

// Convert shortened party names to full party names
@BindingAdapter("partyName")
fun TextView.bindPartyName(item: Party) {
    text = (when (item.party) {
        "sd" -> "Sosiaalidemokraatit"
        "vihr" -> "Vihreät"
        "kok" -> "Kokoomus"
        "ps" -> "Perussuomalaiset"
        "r" -> "RKP"
        "kesk" -> "Keskusta"
        "vas" -> "Vasemmistoliitto"
        "kd" -> "Kristillisdemokraatit"
        "liik" -> "Liike Nyt"
        "vkk" -> "Valta Kuuluu Kansalle"
        "wr" -> "Wille Rydman"
        else -> "Error"
    })
}

// Convert shortened party names to full party name
@BindingAdapter("partyNameMember")
fun TextView.bindPartyNameMember(item: String) {
    text = (when (item) {
        "sd" -> "Sosiaalidemokraatit"
        "vihr" -> "Vihreät"
        "kok" -> "Kokoomus"
        "ps" -> "Perussuomalaiset"
        "r" -> "RKP"
        "kesk" -> "Keskusta"
        "vas" -> "Vasemmistoliitto"
        "kd" -> "Kristillisdemokraatit"
        "liik" -> "Liike Nyt"
        "vkk" -> "Valta Kuuluu Kansalle"
        "wr" -> "Wille Rydman"
        else -> "Error"
    })
}

// Get ParliamentApiStatus
@BindingAdapter("parliamentApiStatus")
fun bindStatus(statusImageView: ImageView, status: ParliamentApiStatus?) {
    when (status) {
        ParliamentApiStatus.LOADING -> {
            Log.i("parliamentApiStatus", "Loading")
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        ParliamentApiStatus.DONE -> {
            Log.i("parliamentApiStatus", "Done")
            statusImageView.visibility = View.GONE
        }
        else -> {}
    }
}


