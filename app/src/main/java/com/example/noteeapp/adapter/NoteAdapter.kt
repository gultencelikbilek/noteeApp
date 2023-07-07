package com.example.noteeapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.noteeapp.databinding.NewLayoutAdapterBinding
import com.example.noteeapp.fragment.HomeFragmentDirections
import com.example.noteeapp.model.Note
import java.util.Random
import kotlin.random.Random as Random1

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {



    inner class NoteViewHolder( val itemBinding: NewLayoutAdapterBinding): RecyclerView.ViewHolder(itemBinding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
           return newItem == oldItem
        }

    }

     val differ = AsyncListDiffer(this,diffCallback)
    var noteList : List<Note>
    get() = differ.currentList
    set(value) {
        differ.submitList(value)
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(NewLayoutAdapterBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount() = noteList.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        var currentList = differ.currentList[position]

        holder.itemBinding.tvNoteTitle.text = currentList.noteTitle
        holder.itemBinding.tvNoteBody.text = currentList.noteBody

            val random = Random()
            val color = Color.argb(
                255,
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256))
            holder.itemBinding.viewColor.setBackgroundColor(color)

        holder.itemView.setOnClickListener {
            val direction = HomeFragmentDirections.actionHomeFragment3ToUpdateNoteFragment(currentList)
            it.findNavController().navigate(direction)

        }

    }
}