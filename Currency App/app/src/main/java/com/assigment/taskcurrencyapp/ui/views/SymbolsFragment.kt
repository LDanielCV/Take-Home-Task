package com.assigment.taskcurrencyapp.ui.views

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.assigment.taskcurrencyapp.R
import com.assigment.taskcurrencyapp.domain.models.CurrencySymbols
import com.assigment.taskcurrencyapp.databinding.SymbolsFragmentBinding
import com.assigment.taskcurrencyapp.ui.adapter.CurrencyAdapter
import com.assigment.taskcurrencyapp.ui.utils.Constants.DEFAULT_DELAY
import com.assigment.taskcurrencyapp.ui.utils.RecyclerViewEvents
import com.assigment.taskcurrencyapp.ui.utils.collectFlow
import com.assigment.taskcurrencyapp.ui.utils.getQueryTextChangeStateFlow
import com.assigment.taskcurrencyapp.ui.viewmodels.SymbolsViewModel
import com.assigment.taskcurrencyapp.ui.views.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*

@AndroidEntryPoint
class SymbolsFragment :
    BaseFragment<SymbolsFragmentBinding>(),
    RecyclerViewEvents {

    private lateinit var adapter: CurrencyAdapter
    private lateinit var searchView: SearchView

    val viewModel: SymbolsViewModel by viewModels()

    private fun searchViewFlow() = searchView.getQueryTextChangeStateFlow()
        .debounce(DEFAULT_DELAY)
        .flatMapLatest { query ->
            viewModel.filterData(query)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        adapter = CurrencyAdapter(this)
        binding.adapter = adapter

        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        val searchMenu = menu.findItem(R.id.app_bar_search)
        searchView = searchMenu.actionView as SearchView
        setUpSearchView()
    }

    override fun initFlow() {

        lifecycleScope.collectFlow(viewModel.currencyCountries) {
            adapter.submitList(it)
        }

        lifecycleScope.collectFlow(viewModel.errorMessage) {
            handleError(it)
        }

    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): SymbolsFragmentBinding =
        DataBindingUtil.inflate(inflater, R.layout.symbols_fragment, container, false)

    override fun onItemClick(view: View?, item: Any, itemPosition: Int) {
        val country = item as CurrencySymbols
        val bundle = bundleOf("base" to country.currencySymbols)

        navigateTo(bundle, R.id.toCurrencyConverter)
    }


    private fun setUpSearchView() {
        lifecycleScope.collectFlow(
            searchViewFlow()
        ) {
            adapter.submitList(it)
        }
    }
}

