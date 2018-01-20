package mchehab.com.kotlinapp

import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.AdapterView
import com.google.firebase.FirebaseApp

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.parceler.Parcels

import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference

    private lateinit var listViewAdapter: ListViewAdapter
    private val listPerson = ArrayList<Person>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseReference = FirebaseDatabase.getInstance().reference

        setListViewAdapter()

        addSingleEventListener()
        addChildEventListener()

        setFabClickListener()
        setListViewItemListener()
        setListViewLongClickListener()
    }

    private fun setListViewAdapter(){
        listViewAdapter = ListViewAdapter(this, listPerson)
        listView.adapter = listViewAdapter
    }

    private fun addSingleEventListener(){
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                progressBar.visibility = View.GONE
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun addChildEventListener(){
        databaseReference.addChildEventListener(object: ChildEventListener{
            override fun onCancelled(dataSnapshot: DatabaseError?) {}

            override fun onChildMoved(dataSnapshot: DataSnapshot?, p1: String?) {}

            override fun onChildChanged(dataSnapshot: DataSnapshot?, p1: String?) {
                val key = dataSnapshot?.key
                if(key != null){
                    val person = dataSnapshot.getValue<Person>(Person::class.java)!!
                    listPerson.forEach {
                        if(it.key == key){
                            it.copy(person)
                            listViewAdapter.notifyDataSetChanged()
                            return
                        }
                    }
                }
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot?, p1: String?) {
                val person = dataSnapshot?.getValue<Person>(Person::class.java)
                if(person != null){
                    listPerson.add(person)
                    listViewAdapter.notifyDataSetChanged()
                }
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot?) {
                val person = dataSnapshot?.getValue<Person>(Person::class.java)
                if(person != null){
                    listPerson.remove(person)
                    listViewAdapter.notifyDataSetChanged()
                }
            }
        })
    }

    private fun setFabClickListener(){
        fab.setOnClickListener { startActivity(Intent(this, EditPersonActivity::class.java)) }
    }

    private fun setListViewItemListener(){
        listView.setOnItemClickListener { _, _, i, _ ->
            val bundle = Bundle()
            bundle.putBoolean("edit", true)
            bundle.putParcelable("person", Parcels.wrap(listPerson[i]))
            val intent = Intent(this, EditPersonActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    private fun setListViewLongClickListener() {
        listView.setOnItemLongClickListener{ _: AdapterView<*>, _: View, i: Int, _: Long ->
            val person = listPerson[i]
            AlertDialog.Builder(this)
                    .setTitle("Delete ${person.firstName} ${person.lastName}")
                    .setMessage("Do you want to delete the selected record?")
                    .setPositiveButton("Delete") { _, _ -> databaseReference.child(person.key).removeValue() }
                    .setNegativeButton("Cancel") { dialogInterface, _ -> dialogInterface.dismiss() }
                    .create()
                    .show()
            return@setOnItemLongClickListener true
        }
    }
}