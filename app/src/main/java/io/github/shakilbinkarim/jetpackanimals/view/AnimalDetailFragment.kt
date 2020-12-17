package io.github.shakilbinkarim.jetpackanimals.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
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
        }
        animal?.imageUrl?.let { setUpBackgroundColor(it) }
        binding.animal = animal
    }

    private fun setUpBackgroundColor(url: String?) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate { palette ->
                            val color = palette?.lightMutedSwatch?.rgb ?: 0
                            binding.llAnimalDetail.setBackgroundColor(color)
                        }
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }

}
