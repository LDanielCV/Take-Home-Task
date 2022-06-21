package com.assigment.taskcurrencyapp.ui.views.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding
import com.assigment.taskcurrencyapp.R
import com.assigment.taskcurrencyapp.domain.repositories.BaseRepository
import com.assigment.taskcurrencyapp.ui.utils.ErrorDialog

abstract class BaseFragment<VB: ViewBinding> :
    Fragment(){

    protected lateinit var binding:VB

    //DialogFragment
    private val errorDialog:ErrorDialog = ErrorDialog()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFlow()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = getFragmentBinding(inflater,container)

        return binding.root
    }

    protected fun navigateTo(bundle: Bundle?, @IdRes actionId:Int){

        Navigation.findNavController(binding.root).navigate(actionId, bundle)
    }

    abstract fun initFlow()
    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?):VB


    protected fun handleError(errorMessage:String = getString(R.string.default_error_message)){
        if(!errorMessage.isEmpty() && !errorDialog.isVisible && !errorDialog.isAdded) {
            errorDialog.error = errorMessage
            errorDialog.show(requireActivity().supportFragmentManager, "")
        }
    }

}