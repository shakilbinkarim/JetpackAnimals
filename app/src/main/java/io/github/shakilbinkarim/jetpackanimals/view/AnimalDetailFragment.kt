package io.github.shakilbinkarim.jetpackanimals.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import io.github.shakilbinkarim.jetpackanimals.R
import io.github.shakilbinkarim.jetpackanimals.model.Animal
import io.github.shakilbinkarim.jetpackanimals.utils.getProgressDrawable
import io.github.shakilbinkarim.jetpackanimals.utils.loadImage
import kotlinx.android.synthetic.main.fragment_animal_detail.*

class AnimalDetailFragment : Fragment() {

    var animal: Animal? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =// Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_animal_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            animal = AnimalDetailFragmentArgs.fromBundle(it).animal
        }
        fillOutAnimalDetailsInAppropriateViews()
        animal?.imageUrl?.let { setUpBackgroundColor(it) }
    }

    private fun setUpBackgroundColor(url: String?) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object: CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate { palette ->
                            val color = palette?.lightMutedSwatch?.rgb ?: 0
                            ll_animal_detail.setBackgroundColor(color)
                        }
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }

    private fun fillOutAnimalDetailsInAppropriateViews() {
        context?.let { iv_animal.loadImage(animal?.imageUrl, getProgressDrawable(it)) }
        tv_animal_name.text = animal?.name
        tv_animal_location.text = animal?.location
        tv_animal_lifespan.text = animal?.lifeSpan
        tv_animal_diet.text = animal?.diet
    }

}