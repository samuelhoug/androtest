package com.example.hsamuel.midifood

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.food_item.view.*


class FoodList : AppCompatActivity() {


    private lateinit var rAdapter: RecyclerView

    lateinit var mDataReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_list)

        mDataReference = FirebaseDatabase.getInstance().getReference("Agences")

        rAdapter = findViewById(R.id.recycler)
        rAdapter.layoutManager = LinearLayoutManager(this)

        loadData()
    }

    private fun loadData() {

        val options: FirebaseRecyclerOptions<Pictures> = FirebaseRecyclerOptions.Builder<Pictures>()
                .setQuery(mDataReference, Pictures::class.java)
                .setLifecycleOwner(this)
                .build()

        val firebaseRecycler = object : FirebaseRecyclerAdapter<Pictures, AgencyViewHolder>(options) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgencyViewHolder {
                val inflatedView = parent.inflate(R.layout.food_item, false)
                return AgencyViewHolder(inflatedView)

            }

            override fun onBindViewHolder(holder: AgencyViewHolder, position: Int, model: Pictures) {
                holder.bindPicture(Pictures())

            }
        }
        rAdapter.Recycler()
        rAdapter.adapter = firebaseRecycler
    }

   class AgencyViewHolder(var view: View) : RecyclerView.ViewHolder(view){
       fun bindPicture(model: Pictures){
           Picasso.get().load(model.imglink).into(itemView.img_agency)
           view.Name_agency.text = model.nom
           view.offers.text = model.offres

       }
   }

}
