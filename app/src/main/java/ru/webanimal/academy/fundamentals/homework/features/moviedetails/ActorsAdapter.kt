package ru.webanimal.academy.fundamentals.homework.features.moviedetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.webanimal.academy.fundamentals.homework.R
import ru.webanimal.academy.fundamentals.homework.data.models.Actor

class ActorsAdapter : RecyclerView.Adapter<ActorsAdapter.ActorsViewHolder>() {

    private var actorsList = mutableListOf<Actor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsViewHolder {
        return ActorsHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.item_movie_details_actors_list,
                parent,
                false
        ))
    }

    override fun onBindViewHolder(holder: ActorsViewHolder, position: Int) {
        (holder as ActorsHolder).onBind(actorsList[position])
    }

    override fun getItemCount(): Int {
        return actorsList.size
    }

    fun updateAdapter(newActors: List<Actor>) {
        actorsList = newActors.toMutableList()
        notifyDataSetChanged()
    }

    abstract class ActorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    private class ActorsHolder(itemView: View) : ActorsViewHolder(itemView) {
        private val nameView: TextView? = itemView.findViewById(R.id.tvMovieDetailsActorName)
        private val avatarImage: ImageView? = itemView.findViewById(R.id.ivMovieDetailsActorAvatar)

        fun onBind(actor: Actor) {
            nameView?.text = actor.name
            Picasso.get().load(actor.image)
                .placeholder(R.drawable.img_coming_soon_placeholder)
                .into(avatarImage)
        }
    }
}