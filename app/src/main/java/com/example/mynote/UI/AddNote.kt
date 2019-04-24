package com.example.mynote.UI


import android.app.AlertDialog
import android.os.AsyncTask
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.Navigation

import com.example.mynote.R
import com.example.mynote.db.Note
import com.example.mynote.db.NoteDatabase
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.coroutines.launch


class AddNote : BaseFragment() {

    private var note:Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {

            note = AddNoteArgs.fromBundle(it).note

            edittext_title.setText(note?.title)
            edittext_note.setText(note?.note)
        }

        button_save.setOnClickListener {view ->

            val noteTitle = edittext_title.text.toString().trim()
            val noteBody = edittext_note.text.toString().trim()

            if(noteTitle.isEmpty()){

                edittext_title.error = "Title Required"
                edittext_title.requestFocus()
                return@setOnClickListener
            }

            if(noteTitle.isEmpty()){

                edittext_title.error = "Note Required"
                edittext_title.requestFocus()
                return@setOnClickListener
            }

            launch {



                context?.let {
                    val mNote = Note(noteTitle, noteBody)

                    if(note == null){
                        NoteDatabase(it).getNoteDAO().addNote(mNote)
                        it.toast("Note Saved")

                    }else{
                        mNote.id = note!!.id
                        NoteDatabase(it).getNoteDAO().updateNote(mNote)
                        it.toast("Note Updated")
                    }


                    val action = AddNoteDirections.actionSaveNote()
                    Navigation.findNavController(view).navigate(action)
                }
            }



        }

    }

    private fun deleteNote(){

        AlertDialog.Builder(context).apply {

            setTitle("Are you sure?")

            setMessage("You cannot undo this operation")

            setPositiveButton("Yes"){ _, _ ->

                launch {

                    NoteDatabase(context).getNoteDAO().deleteNote(note!!)
                    val action = AddNoteDirections.actionSaveNote()
                    Navigation.findNavController(view!!).navigate(action)
                }


            }
            setNegativeButton("No"){ _, _ ->


            }
        }.create().show()


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       when(item.itemId){

           R.id.delete -> if(note != null) deleteNote() else context?.toast("cannot Delete")
       }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu, menu)
    }




}
