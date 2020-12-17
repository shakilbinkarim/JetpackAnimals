package io.github.shakilbinkarim.jetpackanimals.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import io.github.shakilbinkarim.jetpackanimals.R
import io.github.shakilbinkarim.jetpackanimals.databinding.ItemAnimalBinding
import io.github.shakilbinkarim.jetpackanimals.model.Animal
import io.github.shakilbinkarim.jetpackanimals.utils.getProgressDrawable
import io.github.shakilbinkarim.jetpackanimals.utils.loadImage
import kotlinx.android.synthetic.main.item_animal.view.*

class AnimalListAdapter(private val animalList: ArrayList<Animal>) :
    RecyclerView.Adapter<AnimalListAdapter.AnimalListViewHolder>() {

    private lateinit var binding : ItemAnimalBinding

    fun updateAnimalList(newAnimalList: List<Animal>) {
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_animal, parent, false)
        return AnimalListViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: AnimalListViewHolder, index: Int) {
        viewHolder.view.tv_animal_name.text = animalList[index].name
        viewHolder.view.iv_animalImage.loadImage(
            animalList[index].imageUrl,
            getProgressDrawable(viewHolder.view.context)
        )
        viewHolder.view.cl_animal_item.setOnClickListener {
            val action = AnimalListFragmentDirections.actionAnimalDetails(animalList[index])
            Navigation.findNavController(viewHolder.view).navigate(action)
        }
    }

    override fun getItemCount(): Int = animalList.size

    class AnimalListViewHolder(var view: View) : RecyclerView.ViewHolder(view)

}