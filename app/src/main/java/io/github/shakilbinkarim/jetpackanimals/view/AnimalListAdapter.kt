package io.github.shakilbinkarim.jetpackanimals.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import io.github.shakilbinkarim.jetpackanimals.R
import io.github.shakilbinkarim.jetpackanimals.databinding.ItemAnimalBinding
import io.github.shakilbinkarim.jetpackanimals.model.Animal
import kotlinx.android.synthetic.main.item_animal.view.*

class AnimalListAdapter(private val animalList: ArrayList<Animal>) :
    RecyclerView.Adapter<AnimalListAdapter.AnimalListViewHolder>(), AnimalClickListener {

    private lateinit var binding: ItemAnimalBinding
    private var map: HashMap<String, Int> = HashMap()

    fun updateAnimalList(newAnimalList: List<Animal>) {
        setupDataStructures(newAnimalList)
        notifyDataSetChanged()
    }

    private fun setupDataStructures(newAnimalList: List<Animal>) {
        animalList.clear()
        map.clear()
        newAnimalList.forEachIndexed { index, animal ->
            animal.name?.let {
                map[it] = index
                animalList.add(animal)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.item_animal, parent, false)
        return AnimalListViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: AnimalListViewHolder, index: Int) {
        viewHolder.binding.animal = animalList[index]
        viewHolder.binding.clickListener = this
    }

    override fun getItemCount(): Int = animalList.size

    override fun onClick(view: View) {
        val index = map[view.tag]
        index?.let {
            val action = AnimalListFragmentDirections.actionAnimalDetails(animalList[index])
            Navigation.findNavController(view).navigate(action)
        }
    }

    class AnimalListViewHolder(var binding: ItemAnimalBinding) :
        RecyclerView.ViewHolder(binding.root)

}