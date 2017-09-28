package goes.who.whogoes.adapter

/**
 * Created by Diego Mendonca on 12.09.2017.
 */

import android.app.Activity
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import goes.who.whogoes.R
import goes.who.whogoes.model.Datum
import goes.who.whogoes.model.Declined
import goes.who.whogoes.model.Interested

class AttendeesResponseAdapter(val act: Activity, var responseList: List<Datum>) : RecyclerView.Adapter<ResponseViewHolder>() {

    fun setElements(elements: List<Datum>) {
        responseList = elements
    }

    fun getElements(): List<Datum> {
        return responseList
    }

    override fun onBindViewHolder(holder: ResponseViewHolder, position: Int) {
        val item = responseList[position]

        holder.title.setTextColor(item.status.color())

        val message = when (item.status) {
            is Interested -> act.getString(R.string.interested)
            is Declined -> act.getString(R.string.declined)
            else -> act.getString(R.string.attending)

        }

        holder.title.text = message.toUpperCase()
        holder.body.text = item.name
        holder.body.setTextColor(Color.BLACK)
        Picasso.with(act.applicationContext).load(item.picture.data.url).into(holder.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResponseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.response_item, parent, false)
        return ResponseViewHolder(view,
                view.findViewById(R.id.title) as TextView,
                view.findViewById(R.id.body) as TextView,
                view.findViewById(R.id.profile) as ImageView)
    }

    override fun getItemCount(): Int {
        return responseList.size
    }
}

data class ResponseViewHolder(val view: View, val title: TextView, val body: TextView, val image: ImageView) : RecyclerView.ViewHolder(view)
