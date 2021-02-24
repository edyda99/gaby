package com.example.pagedlistexample_kotlin.Adapter

//import com.example.kotlingabywifiroom.Parent.Items
//import com.example.kotlingabywifiroom.Parent.Parent
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pagedlistexample_kotlin.Activity2.SingleSelectActivity
import com.example.pagedlistexample_kotlin.parentt.Item
import com.example.pagedlistexample_kotlin.R
import com.example.pagedlistexample_kotlin.databinding.ParentListItemBinding
import com.example.pagedlistexample_kotlin.util.NetworkState


public class ParentAdapter(val context: Context) :
    PagedListAdapter<Item, ParentAdapter.ParentViewHolder>(ParentDiffCall()) {
    val PARENT_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkState: NetworkState? = null
    override fun onCreateViewHolder(view: ViewGroup, viewType: Int): ParentViewHolder {
        val parentListItem: ParentListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(view.getContext()),
            R.layout.parent_list_item,
            view,
            false
        )
        val layoutInflater = LayoutInflater.from(view.context)
        val vieww = layoutInflater.inflate(R.layout.parent_list_item, view, false)

        return ParentViewHolder(parentListItem, vieww, context)
    }


    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        holder.parentListItem.item = getItem(position)

    }
    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }
    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {                             //hadExtraRow is true and hasExtraRow false
                notifyItemRemoved(super.getItemCount())    //remove the progressbar at the end
            } else {                                       //hasExtraRow is true and hadExtraRow false
                notifyItemInserted(super.getItemCount())   //add the progressbar at the end
            }
        } else if (hasExtraRow && previousState != newNetworkState) { //hasExtraRow is true and hadExtraRow true and (NetworkState.ERROR or NetworkState.ENDOFLIST)
            notifyItemChanged(itemCount - 1)       //add the network message at the end
        }

    }

    class ParentViewHolder(
        val parentListItem: ParentListItemBinding,
        view: View,
        context: Context
    ) :
        RecyclerView.ViewHolder(view) {

//      init
//        {
//    itemView.setOnClickListener{
//                    val intent = Intent(context, SingleSelectActivity::class.java)
//                    intent.putExtra("item", parentListItem.item)
//                    context.startActivity(intent)
//                }
//            }
    }
}

class ParentDiffCall : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }
}



