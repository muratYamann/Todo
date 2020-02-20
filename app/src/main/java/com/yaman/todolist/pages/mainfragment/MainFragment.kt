package com.yaman.todolist.pages.mainfragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.yaman.core.networking.Result
import com.yaman.core.utils.SwipeToDeleteCallback
import com.yaman.todolist.ComponentManager
import com.yaman.todolist.R
import com.yaman.todolist.pages.mainfragment.adapter.MainFragmentListAdapter
import com.yaman.todolist.pages.mainfragment.viewmodel.MainFragmentViewModel
import com.yaman.todolist.pages.mainfragment.viewmodel.MainFragmentViewModelFactory
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject


class MainFragment : Fragment(), MainFragmentListAdapter.MainFragmnetItemInteractor {


    @Inject
    lateinit var viewModelFactory: MainFragmentViewModelFactory

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mainFragmentListAdapter: MainFragmentListAdapter

    private val component by lazy { ComponentManager.mainFragmentComponent(this.activity!!) }

    private val viewModel: MainFragmentViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)
                .get(MainFragmentViewModel::class.java)
    }

    private var selectedColor: Color? = null

    private var listener: MainFragmentListAdapter.MainFragmnetItemInteractor? = null

    fun setListener(listener: MainFragmentListAdapter.MainFragmnetItemInteractor) {
        this.listener = listener
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.inject(this)
        observeViewModel()
        viewListener()
        viewModel.getAllwordsResult()

        recyclerviewTodo.adapter = mainFragmentListAdapter
        recyclerviewTodo.layoutManager = linearLayoutManager
        enableSwipeToDeleteAndUndo(mainFragmentListAdapter)

    }

    private fun viewListener() {

        fab.setOnClickListener {
            it.findNavController()
                    .navigate(MainFragmentDirections.actionMainFragmentToNewTaskFragment())
        }

    }


    private fun observeViewModel() {

        viewModel.getAllwordsResult.observe(viewLifecycleOwner, Observer {
            when (it) {

                is Result.Success -> {
                    if (it.data.isEmpty()) {
                        empty_ph.visibility = View.VISIBLE
                    } else {
                        empty_ph.visibility = View.INVISIBLE
                        it.data?.let {
                            mainFragmentListAdapter.setWords(it, activity, viewModel, recyclerviewTodo)
                        }
                    }
                }


                is Result.Failure -> {
                    empty_ph.visibility = View.VISIBLE
                }
            }
        })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return view?.let { Navigation.findNavController(it) }?.let {
            NavigationUI.onNavDestinationSelected(
                    item,
                    it
            )
        }!! || super.onOptionsItemSelected(item)
    }

    private fun enableSwipeToDeleteAndUndo(mainFragmentListAdapter: MainFragmentListAdapter) {
        val swipeToDeleteCallback = object : SwipeToDeleteCallback(context) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {

                val position = viewHolder.adapterPosition
                val item = mainFragmentListAdapter.getList()[position]

                mainFragmentListAdapter.removeitem(position)

                val snackbar = Snackbar
                        .make(coodLayout,
                                "Item was removed from the list.",
                                Snackbar.LENGTH_LONG
                        )
                snackbar.setAction("UNDO") {
                    mainFragmentListAdapter.restoreItem(item, position)
                    recyclerviewTodo.scrollToPosition(position)
                }
                snackbar.setActionTextColor(Color.YELLOW)
                snackbar.show()

            }
        }

        val itemTouchhelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchhelper.attachToRecyclerView(recyclerviewTodo)
    }

    override fun onItemClick1() {

    }


}
