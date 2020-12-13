package ru.webanimal.academy.fundamentals.homework.features.moviedetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.webanimal.academy.fundamentals.homework.R
import ru.webanimal.academy.fundamentals.homework.data.models.Actor_legacy

class ActorsAdapter : RecyclerView.Adapter<ActorsAdapter.ActorsViewHolder>() {

    private var actorsList = mutableListOf<Actor_legacy>()

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

    fun updateAdapter(newActorLegacies: List<Actor_legacy>) {
        actorsList = newActorLegacies.toMutableList()
        notifyDataSetChanged()
    }

    abstract class ActorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    private class ActorsHolder(itemView: View) : ActorsViewHolder(itemView) {
        private val nameView: TextView? = itemView.findViewById(R.id.tvMovieDetailsActorName)
        private val avatarImage: ImageView? = itemView.findViewById(R.id.ivMovieDetailsActorAvatar)

        fun onBind(actorLegacy: Actor_legacy) {
            nameView?.text = actorLegacy.name
            avatarImage?.setImageResource(actorLegacy.imageId)
        }
    }
}