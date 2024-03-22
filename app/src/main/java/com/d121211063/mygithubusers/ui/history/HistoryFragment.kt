package com.d121211063.mygithubusers.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.d121211063.mygithubusers.data.history.History
import com.d121211063.mygithubusers.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private lateinit var _binding: FragmentHistoryBinding
    private val historyViewModel by viewModels<HistoryViewModel>()
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val layoutManager = GridLayoutManager(this.context, 2)
        binding.rvUsers.layoutManager = layoutManager

        historyViewModel.listHistory.observe(viewLifecycleOwner) { listHistory ->
            setRHistoryData(listHistory)
        }

        return root
    }

    private fun setRHistoryData(listHistory: List<History>) {
        val adapter = HistoryAdapter()
        adapter.submitList(listHistory)
        binding.rvUsers.adapter = adapter
    }
}