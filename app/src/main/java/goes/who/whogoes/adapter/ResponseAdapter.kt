package goes.who.whogoes.adapter

/**
 * Created by doma on 12.09.2017.
 */

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import goes.who.whogoes.R
import goes.who.whogoes.model.Datum

class ResponseAdapter(var responseList: List<Datum>) : RecyclerView.Adapter<ResponseViewHolder>(){

    fun setElements(elements : List<Datum>){
        responseList = elements
    }

    override fun onBindViewHolder(holder: ResponseViewHolder, position: Int) {
        val item = responseList[position]
        holder.title.text = item.id
        holder.body.text = item.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResponseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.response_item, parent, false)
        return ResponseViewHolder(view, view.findViewById<TextView>(R.id.title),
                view.findViewById<TextView>(R.id.body))
    }

    override fun getItemCount(): Int {
        return responseList.size
    }


}


data class ResponseViewHolder(val view: View, val title : TextView, val body: TextView) : RecyclerView.ViewHolder(view)
