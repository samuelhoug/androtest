package com.example.hsamuel.midifood

import android.content.Intent
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.food_item.view.*

/**
 * Created by hsamuel on 21/09/18.
 */

class PicturesHolder {


}
//Kotlin extension for the RecyclerViewAdapter onCreatedViewHolder function
fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}