package goes.who.whogoes.adapter

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import goes.who.whogoes.R
import goes.who.whogoes.activity.InputActivity
import goes.who.whogoes.model.DatumEvent
import java.text.SimpleDateFormat

class EventResponseAdapter(val act: Activity, var responseList: List<DatumEvent>, val faceToken:String) : RecyclerView.Adapter<EventResponseViewHolder>() {

    fun setElements(elements: List<DatumEvent>) {
        responseList = elements
    }

    override fun onBindViewHolder(holder: EventResponseViewHolder, position: Int) {
        val item = responseList[position]

        val incomingFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        val date = incomingFormat.parse(item.start_time)
        val outgoingFormat = SimpleDateFormat(" EEEE, dd MMMM yyyy")


        holder.attending.text = act.getString(R.string.attending).toUpperCase() + ":" + item.attending_count.toString()
        holder.interested.text = act.getString(R.string.interested).toUpperCase() + ":" + item.interested_count.toString()
        holder.declined.text = act.getString(R.string.declined).toUpperCase() + ":" + item.declined_count.toString()
        holder.date.text = outgoingFormat.format(date)
        holder.name.text = item.name
        Picasso.with(act.applicationContext).load(item.cover.source).into(holder.image)

        holder.bind(item)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventResponseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_response_item, parent, false)

        return EventResponseViewHolder(
                faceToken,
                act,
                view,
                view.findViewById<TextView>(R.id.name),
                view.findViewById<TextView>(R.id.date),
                view.findViewById<TextView>(R.id.attending),
                view.findViewById<TextView>(R.id.interested),
                view.findViewById<TextView>(R.id.declined),
                view.findViewById<ImageView>(R.id.cover))
    }

    override fun getItemCount(): Int {
        return responseList.size
    }

}

data class EventResponseViewHolder(
        val faceToken:String,
        val act: Activity,
        val view: View,
        val name: TextView,
        val date: TextView,
        val attending: TextView,
        val interested: TextView,
        val declined: TextView,
        val image: ImageView) : RecyclerView.ViewHolder(view) {

    fun bind(datumEvent: DatumEvent) {
        view.setOnClickListener(View.OnClickListener {
            val intent = Intent(view.context, InputActivity::class.java)
            intent.putExtra("datumEventId", datumEvent.id)
            intent.putExtra("datumEventName", datumEvent.name)
            intent.putExtra("FACEBOOK_TOKEN", faceToken)
            act.startActivity(intent)
        })
    }
}


