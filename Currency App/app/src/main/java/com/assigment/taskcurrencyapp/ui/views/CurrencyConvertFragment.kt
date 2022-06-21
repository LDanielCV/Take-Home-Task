package com.assigment.taskcurrencyapp.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.assigment.taskcurrencyapp.R
import com.assigment.taskcurrencyapp.databinding.CurrencyConvertFragmentBinding
import com.assigment.taskcurrencyapp.ui.adapter.CurrencyAdapter
import com.assigment.taskcurrencyapp.ui.utils.Constants.DEFAULT_DELAY
import com.assigment.taskcurrencyapp.ui.utils.collectFlow
import com.assigment.taskcurrencyapp.ui.utils.getQueryTextChangeStateFlow
import com.assigment.taskcurrencyapp.ui.viewmodels.CurrencyConvertViewModel
import com.assigment.taskcurrencyapp.ui.views.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CurrencyConvertFragment :
    BaseFragment<CurrencyConvertFragmentBinding>() {


    private var base = ""

    private lateinit var adapter: CurrencyAdapter

    private lateinit var searchView: SearchView


    private val viewModel: CurrencyConvertViewModel by viewModels()

    private fun searchViewFlow() = searchView.getQueryTextChangeStateFlow()
        .debounce(DEFAULT_DELAY)
        .flatMapLatest { value ->
            viewModel.convertValue(value, base)
        }.flowOn(Dispatchers.Main)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = CurrencyAdapter()
        binding.adapter = adapter
        searchView = binding.valueEditText

        arguments?.let {
            base = it.getString("base", "")
        }

        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    override fun initFlow() {

        lifecycleScope.launch {
            viewModel.getRates(base)
        }

        lifecycleScope.collectFlow(viewModel.rates) {
            adapter.submitList(it)
        }

        lifecycleScope.collectFlow(viewModel.errorMessage) {
            handleError(it)
        }

        lifecycleScope.collectFlow(searchViewFlow()) {
            adapter.submitList(it)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): CurrencyConvertFragmentBinding =
        DataBindingUtil.inflate(inflater, R.layout.currency_convert_fragment, container, false)
}