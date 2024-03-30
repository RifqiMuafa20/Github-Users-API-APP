package com.d121211063.mygithubusers.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.d121211063.mygithubusers.R
import com.d121211063.mygithubusers.data.local.entity.UserVisited
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

        setHasOptionsMenu(true)

        val layoutManager = GridLayoutManager(this.context, 2)
        binding.rvUsers.layoutManager = layoutManager

        historyViewModel.getVisitedUsers()?.observe(viewLifecycleOwner) { listUser ->
            setRHistoryData(listUser)
        }

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                showConfirmationDialog()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Konfirmasi")
        builder.setMessage("Apakah Anda yakin ingin menghapus data?")
        builder.setPositiveButton("Ya") { dialog, which ->
            historyViewModel.deleteAllVisited()
        }
        builder.setNegativeButton("Tidak") { dialog, which ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun setRHistoryData(listUserVisited: List<UserVisited>) {
        val adapter = HistoryAdapter()
        adapter.submitList(listUserVisited)
        binding.rvUsers.adapter = adapter
    }

    companion object {
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_DELETE = 20
    }
}