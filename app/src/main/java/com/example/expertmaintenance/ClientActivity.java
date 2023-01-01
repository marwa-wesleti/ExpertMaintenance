package com.example.expertmaintenance;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class ClientActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private  EditText EdsearchClient,Ednom,Edprenom,Edadresse,Edtel,Edfax,Edemail;
    private ImageButton addClient,updateCient,deleteClient,first,previous,next,last;
    private ImageView searchClient;
    private Button saveClient,cancelClient;
    private Cursor cu,email;
    private String x;
    private int op;
    private ClientActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        EdsearchClient=(EditText) findViewById(R.id.EdsearchClient);
        Ednom=(EditText) findViewById(R.id.Ednom);
        Edprenom=(EditText) findViewById(R.id.Edprenom);
        Edadresse=(EditText) findViewById(R.id.Edadresse);
        Edtel=(EditText) findViewById(R.id.Edtel);
        Edfax=(EditText) findViewById(R.id.Edfax);
        Edemail=(EditText) findViewById(R.id.Edemail);
        addClient=(ImageButton)findViewById(R.id.addClient);
        updateCient=(ImageButton)findViewById(R.id.updateClient);
        deleteClient=(ImageButton)findViewById(R.id.deleteClient);
        saveClient=(Button)findViewById(R.id.saveClient);
        cancelClient=(Button)findViewById(R.id.cancelClient);
        searchClient=(ImageView)findViewById(R.id.searchClient);
        first=(ImageButton)findViewById(R.id.first);
        previous=(ImageButton)findViewById(R.id.previous);
        next=(ImageButton)findViewById(R.id.next);
        last=(ImageButton)findViewById(R.id.last);

        this.activity=this;

        Disable();

        db=openOrCreateDatabase("EXPERT_MAINTENANCE",MODE_PRIVATE,null);
        db.execSQL("create table if not exists client (" +
                "id integer primary key autoincrement NOT NULL , " +
                "nom varchar ," +
                "prenom varchar," +
                "adresse varchar," +
                "tel varchar , " +
                "fax varchar , " +
                "email varchar not null   )");



        addClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClient();
            }
        });
        searchClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchClient(db);
            }
        });

        updateCient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateClient();
            }
        });

        saveClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveClient(db);
            }
        });

        cancelClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelClient();
            }
        });
        deleteClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteClient(db,activity);
            }
        });

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstClient();
            }
        });

        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastClient();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextClient();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousClient();
            }
        });
    }












    void searchClient(SQLiteDatabase db)  {
        cu=db.rawQuery("select * from client where (nom like ? or adresse like ?)"
                ,new String[]{"%"+EdsearchClient.getText().toString()+"%","%"+EdsearchClient.getText().toString()+"%"});
        try {
            cu.moveToFirst();
            Ednom.setText(cu.getString(1));
            Edprenom.setText(cu.getString(2));
            Edadresse.setText(cu.getString(3));
            Edtel.setText(cu.getString(4));
            Edfax.setText(cu.getString(5));
            Edemail.setText(cu.getString(6));
            if (cu.getCount()==1){
               invisible();
            }
            else {
               visible();
                previous.setEnabled(false);
                next.setEnabled(true);
            }

        } catch (Exception e) {
            Toast.makeText(this, "aucun resultat", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            viderChamp();
            invisible();
        }
    }
    void firstClient(){
        try {
            cu.moveToFirst();
            Ednom.setText(cu.getString(1));
            Edprenom.setText(cu.getString(2));
            Edadresse.setText(cu.getString(3));
            Edtel.setText(cu.getString(4));
            Edfax.setText(cu.getString(5));
            Edemail.setText(cu.getString(6));
            previous.setEnabled(false);
            next.setEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"aucun client n'est existant.",Toast.LENGTH_SHORT).show();

        }
    }

    void lastClient(){
        try {
            cu.moveToLast();
            Ednom.setText(cu.getString(1));
            Edprenom.setText(cu.getString(2));
            Edadresse.setText(cu.getString(3));
            Edtel.setText(cu.getString(4));
            Edfax.setText(cu.getString(5));
            Edemail.setText(cu.getString(6));
            previous.setEnabled(true);
            next.setEnabled(false);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"aucun client n'est existant.",Toast.LENGTH_SHORT).show();

        }
    }

    void nextClient(){
        try {
            cu.moveToNext();
            Ednom.setText(cu.getString(1));
            Edprenom.setText(cu.getString(2));
            Edadresse.setText(cu.getString(3));
            Edtel.setText(cu.getString(4));
            Edfax.setText(cu.getString(5));
            Edemail.setText(cu.getString(6));
            previous.setEnabled(true);
            if (cu.isLast()){
                next.setEnabled(false);
            }


        } catch (Exception e) {
            e.printStackTrace();


        }
    }

    void previousClient(){
        try {
            cu.moveToPrevious();
            Ednom.setText(cu.getString(1));
            Edprenom.setText(cu.getString(2));
            Edadresse.setText(cu.getString(3));
            Edtel.setText(cu.getString(4));
            Edfax.setText(cu.getString(5));
            Edemail.setText(cu.getString(6));
            next.setEnabled(true);
            if (cu.isFirst()){
                previous.setEnabled(false);
            }


        } catch (Exception e) {
            e.printStackTrace();


        }
    }



    void addClient(){
        op=1;
        EdsearchClient.setVisibility(View.INVISIBLE);
        searchClient.setVisibility(View.INVISIBLE);
        updateCient.setVisibility(View.INVISIBLE);
        deleteClient.setVisibility(View.INVISIBLE);
        addClient.setEnabled(false);
        saveClient.setVisibility(View.VISIBLE);
        cancelClient.setVisibility(View.VISIBLE);
        viderChamp();
        enable();
       invisible();

    }

    void updateClient() {
        try {
            x = cu.getString(0);
            op = 2;
            EdsearchClient.setVisibility(View.INVISIBLE);
            searchClient.setVisibility(View.INVISIBLE);
            addClient.setVisibility(View.INVISIBLE);
            deleteClient.setVisibility(View.INVISIBLE);
            updateCient.setEnabled(false);
            enable();
            saveClient.setVisibility(View.VISIBLE);
            cancelClient.setVisibility(View.VISIBLE);
            invisible();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Selectionner un client puis le modifier", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    void saveClient(SQLiteDatabase db){
        if (op==1){
            SQLiteStatement r=db.compileStatement("select count(*) from client");
            double totalC=r.simpleQueryForLong();
                if (totalC>=1) {
                    if (!Edemail.getText().toString().isEmpty()) {
                        email = db.rawQuery("select email from client where email=?", new String[]{Edemail.getText().toString()});
                        email.moveToFirst();
                        if (email.getCount() > 0) {
                            if (!Edemail.getText().toString().equals(email.getString(0))) {
                                db.execSQL("insert into client (id,nom,prenom,adresse,tel,fax,email) values(?,?,?,?,?,?,?);", new String[]{
                                        null, Ednom.getText().toString(), Edprenom.getText().toString(), Edadresse.getText().toString(), Edtel.getText().toString(),
                                        Edfax.getText().toString(), Edemail.getText().toString()});
                                viderChamp();
                                Toast.makeText(this, "successfull", Toast.LENGTH_SHORT).show();
                                EdsearchClient.setVisibility(View.VISIBLE);
                                searchClient.setVisibility(View.VISIBLE);
                                addClient.setVisibility(View.VISIBLE);
                                updateCient.setVisibility(View.VISIBLE);
                                deleteClient.setVisibility(View.VISIBLE);
                                updateCient.setEnabled(true);
                                addClient.setEnabled(true);
                                saveClient.setVisibility(View.INVISIBLE);
                                cancelClient.setVisibility(View.INVISIBLE);
                                Disable();
                                searchClient.performClick();
                                invisible();
                                viderChamp();

                            } else {
                                Toast.makeText(this, "Client existant", Toast.LENGTH_SHORT).show();
                                EdsearchClient.setVisibility(View.VISIBLE);
                                searchClient.setVisibility(View.VISIBLE);
                                addClient.setVisibility(View.VISIBLE);
                                updateCient.setVisibility(View.VISIBLE);
                                deleteClient.setVisibility(View.VISIBLE);
                                updateCient.setEnabled(true);
                                addClient.setEnabled(true);
                                saveClient.setVisibility(View.INVISIBLE);
                                cancelClient.setVisibility(View.INVISIBLE);
                                Disable();
                                searchClient.performClick();
                                invisible();
                                viderChamp();
                            }
                        } else {
                            db.execSQL("insert into client (id,nom,prenom,adresse,tel,fax,email) values(?,?,?,?,?,?,?);", new String[]{
                                    null, Ednom.getText().toString(), Edprenom.getText().toString(), Edadresse.getText().toString(), Edtel.getText().toString(),
                                    Edfax.getText().toString(), Edemail.getText().toString()});
                            viderChamp();
                            Toast.makeText(this, "successfull", Toast.LENGTH_SHORT).show();
                            EdsearchClient.setVisibility(View.VISIBLE);
                            searchClient.setVisibility(View.VISIBLE);
                            addClient.setVisibility(View.VISIBLE);
                            updateCient.setVisibility(View.VISIBLE);
                            deleteClient.setVisibility(View.VISIBLE);
                            updateCient.setEnabled(true);
                            addClient.setEnabled(true);
                            saveClient.setVisibility(View.INVISIBLE);
                            cancelClient.setVisibility(View.INVISIBLE);
                            Disable();
                            searchClient.performClick();
                            invisible();
                            viderChamp();
                        }

                    }
                    else Toast.makeText(this, "Email obligatoire", Toast.LENGTH_SHORT).show();
                }
                else {
                    db.execSQL("insert into client (id,nom,prenom,adresse,tel,fax,email) values(?,?,?,?,?,?,?);", new String[]{
                            null, Ednom.getText().toString(), Edprenom.getText().toString(), Edadresse.getText().toString(), Edtel.getText().toString(),
                            Edfax.getText().toString(), Edemail.getText().toString()});
                    viderChamp();
                    Toast.makeText(this, "successfull", Toast.LENGTH_SHORT).show();
                    EdsearchClient.setVisibility(View.VISIBLE);
                    searchClient.setVisibility(View.VISIBLE);
                    addClient.setVisibility(View.VISIBLE);
                    updateCient.setVisibility(View.VISIBLE);
                    deleteClient.setVisibility(View.VISIBLE);
                    updateCient.setEnabled(true);
                    addClient.setEnabled(true);
                    saveClient.setVisibility(View.INVISIBLE);
                    cancelClient.setVisibility(View.INVISIBLE);
                    Disable();
                    searchClient.performClick();
                    invisible();
                    viderChamp();
                }
            }

        else if(op==2){

                db.execSQL("update client set nom=?,prenom=?, adresse=? , tel=? , fax=? , email=? where id=?;",
                        new String[]{Ednom.getText().toString(),Edprenom.getText().toString(),Edadresse.getText().toString(),Edtel.getText().toString(),
                                Edfax.getText().toString(),Edemail.getText().toString(),x});
                Toast.makeText(this, "successful", Toast.LENGTH_SHORT).show();
            EdsearchClient.setVisibility(View.VISIBLE);
            searchClient.setVisibility(View.VISIBLE);
            addClient.setVisibility(View.VISIBLE);
            updateCient.setVisibility(View.VISIBLE);
            deleteClient.setVisibility(View.VISIBLE);
            updateCient.setEnabled(true);
            addClient.setEnabled(true);
            saveClient.setVisibility(View.INVISIBLE);
            cancelClient.setVisibility(View.INVISIBLE);
            Disable();
            searchClient.performClick();
            invisible();
            viderChamp();

        }



    }

    void cancelClient(){
        EdsearchClient.setVisibility(View.VISIBLE);
        searchClient.setVisibility(View.VISIBLE);
        addClient.setVisibility(View.VISIBLE);
        deleteClient.setVisibility(View.VISIBLE);
        updateCient.setVisibility(View.VISIBLE);
        updateCient.setEnabled(true);
        addClient.setEnabled(true);
        saveClient.setVisibility(View.INVISIBLE);
        cancelClient.setVisibility(View.INVISIBLE);
        if (cu.getCount()!=0) {
            Ednom.setText(cu.getString(1));
            Edprenom.setText(cu.getString(2));
            Edadresse.setText(cu.getString(3));
            Edtel.setText(cu.getString(4));
            Edfax.setText(cu.getString(5));
            Edemail.setText(cu.getString(6));
        }
        else{
            viderChamp();
        }
        Disable();
        invisible();
    }

    void deleteClient(SQLiteDatabase db,ClientActivity activity){
        try {
            x=cu.getString(0);
            AlertDialog.Builder bul1=new AlertDialog.Builder(activity)
                    .setTitle("Confirmation")
                    .setMessage("Est ce que vous voulez supprimer ce contrat?")
                    .setIcon(R.drawable.validate)
                    .setPositiveButton("valider", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.execSQL("delete from client where id=?;",new String[]{cu.getString(0)});
                            viderChamp();
                            cu.close();
                            dialog.dismiss();
                           invisible();
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            bul1.show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Selectionner un client puis le supprimer ", Toast.LENGTH_SHORT).show();
        }
    }

    public void invisible(){
        first.setVisibility(View.INVISIBLE);
        next.setVisibility(View.INVISIBLE);
        previous.setVisibility(View.INVISIBLE);
        last.setVisibility(View.INVISIBLE);
    }
    public void visible(){
        first.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);
        previous.setVisibility(View.VISIBLE);
        last.setVisibility(View.VISIBLE);
    }
    public void viderChamp(){
        Ednom.setText("");
        Edprenom.setText("");
        Edadresse.setText("");
        Edtel.setText("");
        Edfax.setText("");
        Edemail.setText("");
    }
    public void Disable(){
        Ednom.setEnabled(false);
        Edprenom.setEnabled(false);
        Edadresse.setEnabled(false);
        Edtel.setEnabled(false);
        Edfax.setEnabled(false);
        Edemail.setEnabled(false);
    }
    public void enable(){
        Ednom.setEnabled(true);
        Edprenom.setEnabled(true);
        Edadresse.setEnabled(true);
        Edtel.setEnabled(true);
        Edfax.setEnabled(true);
        Edemail.setEnabled(true);
    }



}