package mchehab.com.firebasedemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    private FloatingActionButton fab;
    private ListView listView;
    private ListViewAdapter listViewAdapter;
    private List<Person> listPerson = new ArrayList<>();

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        initUI();
        setListViewAdapter();

        addSingleEventListener();
        addChildEventListener();

        setFabClickListener();
        setListViewItemListener();
        setListViewLongClickListener();
    }

    private void initUI(){
        progressBar = findViewById(R.id.progressBar);
        fab = findViewById(R.id.fab);
        listView = findViewById(R.id.listView);
    }

    private void setListViewAdapter(){
        listViewAdapter = new ListViewAdapter(this, listPerson);
        listView.setAdapter(listViewAdapter);
    }

    private void addChildEventListener() {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Person person = dataSnapshot.getValue(Person.class);
                if(person != null){
                    person.setKey(dataSnapshot.getKey());
                    listPerson.add(dataSnapshot.getValue(Person.class));
                    listViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Person person = dataSnapshot.getValue(Person.class);
                if(person != null){
                    String key = dataSnapshot.getKey();
                    for(int i=0;i<listPerson.size();i++){
                        Person person1 = listPerson.get(i);
                        if(person1.getKey().equals(key)){
                            listPerson.set(i, person);
                            listViewAdapter.notifyDataSetChanged();
                            return;
                        }
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                listPerson.remove(dataSnapshot.getValue(Person.class));
                listViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void addSingleEventListener(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void setListViewItemListener(){
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("edit", true);
            bundle.putParcelable("person", Parcels.wrap(listPerson.get(i)));
            Intent intent = new Intent(this, EditPersonActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    private void setListViewLongClickListener(){
        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Person person = listPerson.get(i);
            new AlertDialog.Builder(this)
                    .setTitle("Delete " + person.getFirstName() + " " + person.getLastName())
                    .setMessage("Do you want to delete the selected record?")
                    .setPositiveButton("Delete", (dialogInterface, i1) -> {
                        databaseReference.child(person.getKey()).removeValue();
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i12) -> {
                        dialogInterface.dismiss();
                    })
                    .create()
                    .show();
            return true;
        });
    }

    private void setFabClickListener() {
        fab.setOnClickListener(e -> {
            startActivity(new Intent(this, EditPersonActivity.class));
        });
    }
}