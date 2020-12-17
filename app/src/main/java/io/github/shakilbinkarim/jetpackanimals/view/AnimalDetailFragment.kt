package io.github.shakilbinkarim.jetpackanimals.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import io.github.shakilbinkarim.jetpackanimals.R
import io.github.shakilbinkarim.jetpackanimals.databinding.FragmentAnimalDetailBinding
import io.github.shakilbinkarim.jetpackanimals.model.Animal

class AnimalDetailFragment : Fragment() {

    var animal: Animal? = null

    private lateinit var binding: FragmentAnimalDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_animal_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            animal = AnimalDetailFragmentArgs.fromBundle(it).animal
            binding.animal = animal
        }
    }

}
