package com.testproject.ui.activity

import UserListRVAdapter
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.testproject.R
import com.testproject.UserApplication
import com.testproject.databinding.ActivityMainBinding
import com.testproject.model.User
import com.testproject.viewmodel.UserViewModel
import com.testproject.viewmodel.UserViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<UserViewModel>()

    private lateinit var dataBinding: ActivityMainBinding
    private lateinit var userAdapter: UserListRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initListeners()
        collectValidationFlow()
        dataBinding.apply {
            btnAdd.setOnClickListener {
                insertInToDB()
            }
            btnClear.setOnClickListener {
                viewModel.deleteAllUser()
            }
        }
        setupRecyclerView()
        lifecycleScope.launch {
            viewModel.allUsers.collectLatest {
                userAdapter.submitList(it)
            }
        }
    }

    private fun setupRecyclerView() {
        userAdapter = UserListRVAdapter()
        dataBinding.rvUsers.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun initListeners() {
        dataBinding.apply {
            etFirstName.addTextChangedListener {
                viewModel.setFirstName(it.toString())
            }
            etLastName.addTextChangedListener {
                viewModel.setLastName(it.toString())
            }
        }
    }

    private fun collectValidationFlow() {
        lifecycleScope.launch {
            viewModel.isBtnEnabled.collect { value ->
                dataBinding.btnAdd.isEnabled = value
            }
        }
    }

    private fun insertInToDB() {
        val firstName = dataBinding.etFirstName.text?.trim().toString()
        val lastName = dataBinding.etLastName.text?.trim().toString()
        val user = User(firstName = firstName, lastName = lastName)
        viewModel.insertUser(user)
    }
}