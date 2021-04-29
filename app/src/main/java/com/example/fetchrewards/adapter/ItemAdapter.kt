package com.example.fetchrewards.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchrewards.R
import com.example.fetchrewards.domain.ItemEntity

class ItemAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val ITEM_VIEW_TYPE = 0
    private val HEADER_VIEW_TYPE = 1

    inner class ItemViewHolder(view: View): RecyclerView.ViewHolder(view){
       val nameTextView: TextView = view.findViewById(R.id.itemNameTV)
    }

    inner class HeaderViewHolder(view: View): RecyclerView.ViewHolder(view){
        val listId : TextView = view.findViewById(R.id.layoutId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType){
            ITEM_VIEW_TYPE -> ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false))
            else ->  HeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.header_item, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            ITEM_VIEW_TYPE -> (holder as ItemViewHolder).nameTextView.text = (differ.currentList[position] as DataItem.Item).item.name
            HEADER_VIEW_TYPE -> (holder as HeaderViewHolder).listId.text = (differ.currentList[position] as DataItem.Header).listId.toString()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (differ.currentList[position]) {
            is DataItem.Header -> HEADER_VIEW_TYPE
            is DataItem.Item -> ITEM_VIEW_TYPE
        }
    }

    fun addHeaderAndList(list : List<ItemEntity>?){
        val items = list?.groupBy {
            it.listId
        }?.flatMap {
            (listId, name) ->
            listOf<DataItem>(DataItem.Header(listId)) + name.map { DataItem.Item(it) }
        }
        differ.submitList(items)
    }

    override fun getItemCount(): Int = differ.currentList.size

    private val differCallback = object: DiffUtil.ItemCallback<DataItem>(){
        override fun areItemsTheSame(oldItemEntity: DataItem, newItemEntity: DataItem): Boolean {
            return oldItemEntity.id == newItemEntity.id
        }

        override fun areContentsTheSame(oldItemEntity: DataItem, newItemEntity: DataItem): Boolean {
            return oldItemEntity == newItemEntity
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)
}
