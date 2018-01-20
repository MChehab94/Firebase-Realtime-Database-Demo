package mchehab.com.kotlinapp

import kotlinx.android.synthetic.main.activity_edit_person.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.parceler.Parcels

class EditPersonActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference

    var edit = false
    var person = Person()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_person)

        databaseReference = FirebaseDatabase.getInstance().reference

        setButtonOnClickListener()
        handleBundle()
        initUIFromPerson()
    }

    private fun setButtonOnClickListener(){
        button.setOnClickListener { e ->
            person.firstName = editTextFirstName.text.toString()
            person.lastName = editTextLastName.text.toString()
            person.age = Integer.parseInt(editTextAge.text.toString().trim())

            if (edit) {
                databaseReference.child(person.key).setValue(person)
            } else {
                person.key = databaseReference.push().key
                databaseReference.child(person.key).setValue(person)
            }
            finish()
        }
    }

    private fun handleBundle() {
        val bundle = intent.extras
        if (bundle != null) {
            edit = bundle.getBoolean("edit")
            if (edit) {
                person = Parcels.unwrap<Person>(bundle.getParcelable("person"))
            }
        }
    }

    private fun initUIFromPerson(){
        editTextFirstName.setText(person.firstName)
        editTextLastName.setText(person.lastName)
        editTextAge.setText("${person.age} ")
    }
}