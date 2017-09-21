package goes.who.whogoes.adapter

/**
 * Created by doma on 12.09.2017.
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

class AttendeesResponseAdapter(val act: Activity, var responseList: List<Datum>) : RecyclerView.Adapter<ResponseViewHolder>(){

    fun setElements(elements : List<Datum>){
        responseList = elements
    }

    override fun onBindViewHolder(holder: ResponseViewHolder, position: Int) {
        val item = responseList[position]

        when(item.status) {
            "attending/" -> holder.title.setTextColor(Color.GREEN)
            "interested/" -> holder.title.setTextColor(Color.BLUE)
            "declined/" -> holder.title.setTextColor(Color.RED)
        }
        holder.title.text = item.status.substring(0, item.status.length-1).toUpperCase()
        holder.body.text = item.name
        holder.body.setTextColor(Color.BLACK)
        Picasso.with(act.applicationContext).load(item.picture.data.url).into(holder.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResponseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.response_item, parent, false)
        return ResponseViewHolder(view,
                view.findViewById<TextView>(R.id.title),
                view.findViewById<TextView>(R.id.body),
                view.findViewById<ImageView>(R.id.profile))
    }

    override fun getItemCount(): Int {
        return responseList.size
    }
}

data class ResponseViewHolder(val view: View, val title : TextView, val body: TextView, val image: ImageView) : RecyclerView.ViewHolder(view)
