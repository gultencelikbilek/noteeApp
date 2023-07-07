package com.example.noteeapp.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteeapp.MainActivity
import com.example.noteeapp.R
import com.example.noteeapp.adapter.NoteAdapter
import com.example.noteeapp.databinding.FragmentHomeBinding
import com.example.noteeapp.model.Note
import com.example.noteeapp.viewmodel.NoteViewModel


class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener{

    private var _binding : FragmentHomeBinding ?= null
    private val binding get() = _binding!!
    private lateinit var noteViewModel : NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel =(activity as MainActivity).noteViewModel

        setUpRecyclerView()

        binding.fabHome.setOnClickListener {mView ->
            mView.findNavController().navigate(R.id.action_homeFragment3_to_newNoteFragment)
        }
    }

    private fun setUpRecyclerView(){
        noteAdapter = NoteAdapter()

        binding.recyclerviewHome.apply {
            layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)
            adapter = noteAdapter
        }

        activity?.let {
            noteViewModel.getAllNotes().observe(viewLifecycleOwner,{
                noteAdapter.differ.submitList(it)

                updateUI(it)

            }) }
    }

    private fun updateUI(note: List<Note>?) {

        if (note!!.isNotEmpty()){
            binding.recyclerviewHome.visibility = View.VISIBLE
            binding.tvNoNotesAvaliable.visibility = View.GONE
        }else{
            binding.recyclerviewHome.visibility = View.GONE
            binding.tvNoNotesAvaliable.visibility = View.VISIBLE

        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.home_menu,menu)

        val mMenuSearch = menu.findItem(R.id.app_bar_search).actionView as androidx.appcompat.widget.SearchView
        mMenuSearch.isSubmitButtonEnabled = true
        mMenuSearch.setOnQueryTextListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null){
            searchNotes(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null){
            searchNotes(newText)
        }
        return true
    }

    private fun searchNotes(query: String?){
        val searchQuery = "%$query%"
        noteViewModel.searchNote(searchQuery).observe(this,{
            noteAdapter.differ.submitList(it)
        })
    }
}