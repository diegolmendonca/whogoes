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

/**
 * Sample adapter, shows the posts and notifies when an item is clicked
 * Created by macastiblancot on 2/13/17.
 */
class PostsAdapter(var posts : List<Datum> = ArrayList<Datum>()) : RecyclerView.Adapter<PostViewHolder>(){

    fun setElements(elements : List<Datum>){
        posts = elements
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.title.text = post.id
        holder.body.text = post.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return PostViewHolder(view, view.findViewById<TextView>(R.id.title),
                view.findViewById<TextView>(R.id.body))
    }

    override fun getItemCount(): Int {
        return posts.size
    }


}


class PostViewHolder(val view: View, val title : TextView, val body: TextView) : RecyclerView.ViewHolder(view)
