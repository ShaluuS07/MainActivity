package com.example.mainactivity.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mainactivity.R
import com.example.mainactivity.databinding.ItemProfileCardBinding
import com.example.mainactivity.ui.ProfileUi

class ProfilePagerAdapter(
    private val onYes: (ProfileUi) -> Unit,
    private val onNo: (ProfileUi) -> Unit,
    private val onCardClick: (ProfileUi) -> Unit,
) : RecyclerView.Adapter<ProfilePagerAdapter.ProfileViewHolder>() {

    private var items: List<ProfileUi> = emptyList()

    fun submitList(list: List<ProfileUi>) {
        items = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val binding = ItemProfileCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ProfileViewHolder(
        private val binding: ItemProfileCardBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(profile: ProfileUi) {
            binding.profile = profile
            binding.imagePhoto.load(profile.photoPreviewUrl) {
                crossfade(true)
                placeholder(R.drawable.profile_placeholder_loading)
                error(R.drawable.profile_placeholder_error)
            }
            binding.executePendingBindings()
            binding.imagePhoto.setOnClickListener { onCardClick(profile) }
            binding.textDescription.setOnClickListener { onCardClick(profile) }
            binding.textName.setOnClickListener { onCardClick(profile) }
            binding.buttonYes.setOnClickListener { onYes(profile) }
            binding.buttonNo.setOnClickListener { onNo(profile) }
        }
    }
}
