package mchehab.com.firebasedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

public class EditPersonActivity extends AppCompatActivity {

    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextAge;
    private Button button;

    private DatabaseReference databaseReference;

    private Person person = new Person();

    private boolean edit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_person);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        initUI();
        setButtonOnClickListener();
        handleBundle();
        initUIFromPerson();
    }

    private void initUI(){
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextAge = findViewById(R.id.editTextAge);
        button = findViewById(R.id.button);
    }

    private void initUIFromPerson(){
        editTextFirstName.setText(person.getFirstName());
        editTextLastName.setText(person.getLastName());
        editTextAge.setText(person.getAge() + "");
    }

    private void setButtonOnClickListener(){
        button.setOnClickListener(e -> {
            String firstName = editTextFirstName.getText().toString();
            String lastName = editTextLastName.getText().toString();
            int age = Integer.parseInt(editTextAge.getText().toString());

            person.setFirstName(firstName);
            person.setLastName(lastName);
            person.setAge(age);

            if(edit){
                databaseReference.child(person.getKey()).setValue(person);
            }else{
                String key = databaseReference.push().getKey();
                person.setKey(key);
                databaseReference.child(key).setValue(person);
            }
            finish();
        });
    }

    private void handleBundle(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            edit = bundle.getBoolean("edit");
            if(edit){
                person = Parcels.unwrap(bundle.getParcelable("person"));
            }
        }
    }
}