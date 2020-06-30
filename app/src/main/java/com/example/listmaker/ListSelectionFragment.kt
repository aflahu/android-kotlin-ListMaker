package com.example.listmaker

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.content_main.*
import java.lang.RuntimeException


class ListSelectionFragment : Fragment(), ListSelectionRecyclerViewAdapter.ListSelectionRecyclerViewClickListener {

    private var listener: OnListenerItemFragmentInteractionListener? = null

    lateinit var listDataManager: ListDataManager
    lateinit var listRecyclerView: RecyclerView

    interface OnListenerItemFragmentInteractionListener {
        fun onListItemClicked(list: TaskList)
    }

    companion object {

        @JvmStatic
        fun newInstance(): ListSelectionFragment {
            return ListSelectionFragment()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListenerItemFragmentInteractionListener) {
            listener = context
            listDataManager = ListDataManager(context)
        } else {
            throw RuntimeException("$context must implement OnListenerItemFragmentInteractionListener")
        }
    }

    override fun listItemClicked(list: TaskList) {
        listener?.onListItemClicked(list)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_selection, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val lists = listDataManager.readLists()
        view?.let {
            listRecyclerView = it.findViewById(R.id.lists_recyclerview)
            listRecyclerView.layoutManager = LinearLayoutManager(activity)
            listRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists, this)
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun addList(list: TaskList) {
        listDataManager.saveList(list)

        val recyclerAdapter = listRecyclerView.adapter as ListSelectionRecyclerViewAdapter
        recyclerAdapter.addList(list)
    }

    fun saveList(list: TaskList){
        listDataManager.saveList(list)
        updateLists()
    }

    private fun updateLists() {
        val lists = listDataManager.readLists()

        listRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists, this)
    }





}