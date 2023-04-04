package ph.stacktrek.novare.gamenovare.bricenio.par.snakeandladder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ph.stacktrek.novare.gamenovare.bricenio.par.snakeandladder.databinding.WinnersItemBinding


class ListOfWinnerAdapter(private val winners: List<String>) : RecyclerView.Adapter
    <ListOfWinnerAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: WinnersItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(winner: String) {
            binding.winnerName.text = winner
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = WinnersItemBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(winners[position])
    }

    override fun getItemCount(): Int = winners.size
}