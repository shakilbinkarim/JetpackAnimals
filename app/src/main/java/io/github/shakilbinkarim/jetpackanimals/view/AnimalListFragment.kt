package io.github.shakilbinkarim.jetpackanimals.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import io.github.shakilbinkarim.jetpackanimals.R
import io.github.shakilbinkarim.jetpackanimals.model.Animal
import io.github.shakilbinkarim.jetpackanimals.viewmodel.AnimalListViewModel
import kotlinx.android.synthetic.main.fragment_animal_list.*

class AnimalListFragment : Fragment() {

    private lateinit var viewModel: AnimalListViewModel
    private val listAdapter = AnimalListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_animal_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRecyclerView()
        setupRefreshLayout()
    }

    private fun setupRefreshLayout() {
        srl_animal_list.setOnRefreshListener {
            adjustChildrenViewsForListIsLoading()
            viewModel.refresh()
            srl_animal_list.isRefreshing = false
        }
    }

    private fun setupRecyclerView() = rv_animal_list.apply {
        layoutManager = GridLayoutManager(context, 2)
        adapter = listAdapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(AnimalListViewModel::class.java)
        viewModel.animals.observe(this, animalListDataObserver)
        viewModel.loading.observe(this, animalListLoadingObserver)
        viewModel.loadError.observe(this, animalListLoadErrorObserver)
        viewModel.refresh()
    }

    //<editor-fold desc="Observers"

    private val animalListDataObserver = Observer<List<Animal>> { list ->
        list?.let {
            adjustChildrenViewsForListLoaded()
            listAdapter.updateAnimalList(it)
        }
    }

    private val animalListLoadingObserver = Observer<Boolean> { isLoading ->
        if (isLoading) adjustChildrenViewsForListIsLoading()
        else pb_loading_animal_list.visibility = View.GONE
    }

    private val animalListLoadErrorObserver = Observer<Boolean> { error ->
        if (error) adjustChildrenViesForListLoadError()
        else tv_list_error.visibility = View.GONE
    }

    //</editor-fold>

    //<editor-fold desc="Children Views Adjusting Functions"

    private fun adjustChildrenViewsForListLoaded() {
        rv_animal_list.visibility = View.VISIBLE
        pb_loading_animal_list.visibility = View.GONE
        tv_list_error.visibility = View.GONE
    }

    private fun adjustChildrenViewsForListIsLoading() {
        pb_loading_animal_list.visibility = View.VISIBLE
        tv_list_error.visibility = View.GONE
        rv_animal_list.visibility = View.GONE
    }

    private fun adjustChildrenViesForListLoadError() {
        tv_list_error.visibility = View.VISIBLE
        pb_loading_animal_list.visibility = View.GONE
        rv_animal_list.visibility = View.GONE
    }

    //</editor-fold>

}