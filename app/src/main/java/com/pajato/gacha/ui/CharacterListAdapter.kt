package com.pajato.gacha.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pajato.gacha.R
import com.pajato.gacha.model.character.Character
import kotlinx.android.synthetic.main.layout_character.view.*

class CharacterListAdapter(private var list: List<Pair<Character, Int>>) : RecyclerView.Adapter<CharacterListAdapter.CharacterViewHolder>() {
    fun setItems(items: List<Pair<Character, Int>>) {
        list = items
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.layout_character, parent, false)
        return CharacterViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val (character, count) = list[position]
        ImageLoader.loadImage(character, holder.layout.characterImage, { holder.onBind() }, { holder.imageError() })
        holder.layout.characterName.text = character.charName
        val countText = "x$count"
        holder.layout.characterCount.text = countText
        holder.layout.cardViewRoot.setBackgroundResource(character.rarity.backgroundDrawableId)
    }

    inner class CharacterViewHolder(val layout: View) : RecyclerView.ViewHolder(layout) {
        fun onBind() {
            layout.characterImageProgressBar.visibility = View.INVISIBLE
            layout.characterImage.visibility = View.VISIBLE
        }

        fun imageError() {
            onBind()
            layout.characterImage.setImageResource(R.drawable.ic_photo_filter_black_24dp)
        }
    }
}