package com.tamizna.offlinenote.ui.folder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tamizna.offlinenote.R
import com.tamizna.offlinenote.database.models.Folder
import com.tamizna.offlinenote.databinding.ItemFolderBinding

class FolderAdapter(private val onItemClick: (Folder) -> Unit) :
    RecyclerView.Adapter<FolderViewHolder>() {

    private val listOfFolder : MutableList<Folder> = mutableListOf()
    private val drawableCardBackground = arrayOf(
        R.color.pink_pastel,
        R.color.tosca_pastel,
        R.color.yellow_pastel,
        R.color.blue_pastel,
        R.color.army_pastel
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFolderBinding.inflate(inflater, parent, false)
        return FolderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        holder.binding.txtFolderName.text = listOfFolder[position].judul
        holder.binding.folderBackground.setBackgroundResource(drawableCardBackground[(0..4).random()])
        holder.binding.folderBackground.setOnClickListener {
            onItemClick(listOfFolder[position])
        }
    }

    override fun getItemCount(): Int = listOfFolder.size

    fun loadData(data : List<Folder>) {
        listOfFolder.clear()
        listOfFolder.addAll(data)
        notifyDataSetChanged()
    }
}