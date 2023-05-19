package com.jjewuz.justnotes

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NotesFragment : Fragment(), NoteClickInterface, NoteLongClickInterface {
    lateinit var viewModal: NoteViewModal
    lateinit var notesRV: RecyclerView
    lateinit var addFAB: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_notes, container, false)

        notesRV = v.findViewById(R.id.notes)
        addFAB = v.findViewById(R.id.idFAB)

        notesRV.layoutManager = LinearLayoutManager(requireActivity())

        val noteRVAdapter = NoteRVAdapter(requireActivity(), this, this)

        notesRV.adapter = noteRVAdapter

        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(NoteViewModal::class.java)


        viewModal.allNotes.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                noteRVAdapter.updateList(it)
            }
        })


        addFAB.setOnClickListener {
            val intent = Intent(requireActivity(), AddEditNoteActivity::class.java)
            startActivity(intent)
        }

        return v
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(requireActivity(), AddEditNoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.noteTitle)
        intent.putExtra("noteDescription", note.noteDescription)
        intent.putExtra("noteId", note.id)
        startActivity(intent)
    }


    override fun onNoteLongClick(note: Note) {
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle(R.string.delWarn)
            .setNegativeButton(resources.getString(R.string.neg)) { dialog, which ->
            }
            .setPositiveButton(R.string.pos) { dialog, which ->
                viewModal.deleteNote(note)
                Toast.makeText(requireActivity(), R.string.deleted, Toast.LENGTH_LONG).show()
            }
            .show()
    }

}