package com.d121211063.mygithubusers.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.d121211063.mygithubusers.data.local.history.History
import com.d121211063.mygithubusers.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private lateinit var _binding: FragmentHistoryBinding
    private val historyViewModel by viewModels<HistoryViewModel>()
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val layoutManager = GridLayoutManager(this.context, 2)
        binding.rvUsers.layoutManager = layoutManager

        historyViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        historyViewModel.listHistory.observe(viewLifecycleOwner) { listHistory ->
            setRHistoryData(listHistory)
        }

        historyViewModel.isError.observe(viewLifecycleOwner) {
            showToastError(it)
        }

        return root
    }

    private fun setRHistoryData(listHistory: List<History>) {
        val adapter = HistoryAdapter()
        adapter.submitList(listHistory)
        binding.rvUsers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToastError(isError: Boolean) {
        if (isError) Toast.makeText(this.context, "Terjadi kesalahan!! Mohon Bersabar", Toast.LENGTH_SHORT).show()
    }
}