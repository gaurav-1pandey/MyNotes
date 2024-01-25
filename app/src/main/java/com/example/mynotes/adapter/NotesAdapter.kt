package com.example.mynotes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.database.Note
//import com.example.roomdbdemo.R
import java.util.*
import kotlin.collections.ArrayList

class NotesAdapter(private val context: Context,private val listner: NotesClickListner):RecyclerView.Adapter<myvh>() {
    var notelist=ArrayList<Note>()
    var fulllist=ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myvh {

        val inflater=LayoutInflater.from(parent.context)
        val listitem =inflater.inflate(R.layout.list_item,parent,false)
        return myvh(listitem)
    }

    override fun onBindViewHolder(holder: myvh, position: Int) {
        holder.note.text=notelist.get(position).note
        holder.title.text=notelist.get(position).title
        holder.date.text=notelist.get(position).date
        holder.card.setCardBackgroundColor(holder.view.resources.getColor(random(),null))

        holder.card.setOnClickListener {
            listner.onClickListner(notelist.get(position))

        }

        holder.card.setOnLongClickListener{
            listner.onLongClickListner(notelist.get(position),holder.card)
            true
        }
    }

    override fun getItemCount(): Int {
        return notelist.size
    }
    fun random():Int{
        var colors=ArrayList<Int>()
        colors.add(R.color.color1)
        colors.add(R.color.color2)
        colors.add(R.color.color3)
        colors.add(R.color.color4)
        colors.add(R.color.color5)
        colors.add(R.color.color6)
        colors.add(R.color.color7)

        var rndm= (0..colors.size-1).random()
        return colors.get(rndm)
    }
    fun filter(search :String){
        notelist.clear()
        for (item in fulllist ){
            if (item.title?.lowercase()?.contains(search.lowercase())==true ||
                item.note?.lowercase()?.contains(search.lowercase())==true){
                    notelist.add(item)
            }
        }
        notifyDataSetChanged()
    }
    fun updatelist(newList:List<Note>){
        fulllist.clear()
        fulllist.addAll(newList)

        notelist.clear()
        notelist.addAll(fulllist)
        notifyDataSetChanged()

    }
    interface NotesClickListner{
        fun onClickListner(note: Note)
        fun onLongClickListner(note: Note,cardView: CardView)
    }
}
class myvh(var view: View):RecyclerView.ViewHolder(view){
    val title=view.findViewById<TextView>(R.id.tv_title)
    val note=view.findViewById<TextView>(R.id.tv_note)
    val date=view.findViewById<TextView>(R.id.tv_date)
    val card = view.findViewById<CardView>(R.id.card_note)

}

