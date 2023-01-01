package com.example.expertmaintenance;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ContratActivity extends AppCompatActivity {
    private EditText  edSearch,edDateD, edDateF, edRedevance,EdemailCl,txtNomC;
    private ImageButton  first, previous, add, update, delete, next, last,RechC;
    private ImageView imgSearch;
    private Button save, cancel;
    private SQLiteDatabase db;
    private Cursor cu , cu1;
    private  int op;
    private ContratActivity activity;
    private String x;
    private TextView idClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrat);

        db=openOrCreateDatabase("EXPERT_MAINTENANCE",MODE_PRIVATE,null);
        db.execSQL("create table if not exists contrat (" +
                "id integer primary key autoincrement NOT NULL , " +
                "dateDebut varchar NOT NULL, " +
                "dateFin varchar," +
                "redevance decimal," +
                "idCl integer,"+
                "FOREIGN KEY (idCl) REFERENCES client(id)) ");
        this.activity=this;
        edSearch=(EditText)findViewById(R.id.edSearch);
        edDateD = (EditText) findViewById(R.id.edDateD);
        edDateF = (EditText) findViewById(R.id.edDateF);
        edRedevance = (EditText) findViewById(R.id.edRedevance);
        EdemailCl = (EditText) findViewById(R.id.EdemailCl);
        imgSearch = (ImageView) findViewById(R.id.search);
        first = (ImageButton) findViewById(R.id.first);
        previous = (ImageButton) findViewById(R.id.previous);
        next = (ImageButton) findViewById(R.id.next);
        last = (ImageButton) findViewById(R.id.last);
        add=(ImageButton)findViewById(R.id.add);
        update=(ImageButton)findViewById(R.id.update);
        delete=(ImageButton)findViewById(R.id.delete);
        save=(Button)findViewById(R.id.bntSave);
        cancel=(Button)findViewById(R.id.bntCancel2);
        txtNomC=(EditText)findViewById(R.id.EdnomCl);
        idClient=(TextView)findViewById(R.id.idClient);
        RechC=(ImageButton) findViewById(R.id.btnSearchCl);
        edDateD.setEnabled(false);
        edDateF.setEnabled(false);
        edRedevance.setEnabled(false);
        txtNomC.setEnabled(false);
        EdemailCl.setEnabled(false);


        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rechercheContrat(db);
            }
        });
        RechC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rechercherContratClient();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ajouterContrat();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateContrat();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveContrat(db);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteContrat(db,activity);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cancelContrat();
            }
        });
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirstContrat();
            }
        });
        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lastContrat();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nextContrat();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                previousContrat();
            }
        });




    }


   void rechercheContrat(SQLiteDatabase db){
       cu=db.rawQuery("select C.dateDebut,C.dateFin,C.redevance,Cl.nom,Cl.email,C.id ,CL.id  from contrat C,client Cl where " +
               "C.idCl=CL.id and (C.dateDebut like ? or Cl.nom like ? )",new String[]{"%"+edSearch.getText().toString()+"%",
                "%"+edSearch.getText().toString()+"%"});
       try {
           cu.moveToFirst();
           edDateD.setText(cu.getString(0));
           edDateF.setText(cu.getString(1));
           edRedevance.setText(cu.getString(2));
           txtNomC.setText(cu.getString(3));
           EdemailCl.setText(cu.getString(4));
           idClient.setText(cu.getString(6));

           if (cu.getCount()==1){
               first.setVisibility(View.INVISIBLE);
               next.setVisibility(View.INVISIBLE);
               previous.setVisibility(View.INVISIBLE);
               last.setVisibility(View.INVISIBLE);
           }
           else {
               first.setVisibility(View.VISIBLE);
               next.setVisibility(View.VISIBLE);
               previous.setVisibility(View.VISIBLE);
               last.setVisibility(View.VISIBLE);
               previous.setEnabled(false);
               next.setEnabled(true);
           }

       } catch (Exception e) {
           Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
           e.printStackTrace();
           viderChamp();


         invisible();
       }
   }


    void rechercherContratClient() {
       startActivityForResult(new Intent(getApplicationContext(),RechercheActivity.class),1);

    }
    void FirstContrat(){
        try {
            cu.moveToFirst();
            edDateD.setText(cu.getString(0));
            edDateF.setText(cu.getString(1));
            edRedevance.setText(cu.getString(2));
            txtNomC.setText(cu.getString(3));
            EdemailCl.setText(cu.getString(4));
            idClient.setText(cu.getString(6));
            visible();
            previous.setEnabled(false);
            next.setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(activity, "aucun resultat", Toast.LENGTH_SHORT).show();
        }

    }
    void lastContrat(){
        try {
           cu.moveToLast();
            edDateD.setText(cu.getString(0));
            edDateF.setText(cu.getString(1));
            edRedevance.setText(cu.getString(2));
            txtNomC.setText(cu.getString(3));
            EdemailCl.setText(cu.getString(4));
            idClient.setText(cu.getString(6));
           visible();
            previous.setEnabled(true);
            next.setEnabled(false);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(activity, "aucun resultat", Toast.LENGTH_SHORT).show();
        }
    }
    void nextContrat(){
        try {
            cu.moveToNext();
            edDateD.setText(cu.getString(0));
            edDateF.setText(cu.getString(1));
            edRedevance.setText(cu.getString(2));
            txtNomC.setText(cu.getString(3));
            EdemailCl.setText(cu.getString(4));
            idClient.setText(cu.getString(6));
            previous.setEnabled(true);
                if (cu.isLast()){
                    next.setEnabled(false);
                }


            } catch (Exception e) {
                e.printStackTrace();


            }
        }

        void previousContrat(){
            try {
                cu.moveToPrevious();
                edDateD.setText(cu.getString(0));
                edDateF.setText(cu.getString(1));
                edRedevance.setText(cu.getString(2));
                txtNomC.setText(cu.getString(3));
                EdemailCl.setText(cu.getString(4));
                idClient.setText(cu.getString(6));
                next.setEnabled(true);
                if (cu.isFirst()){
                    previous.setEnabled(false);
                }


            } catch (Exception e) {
                e.printStackTrace();


            }
        }

    void ajouterContrat() {
        op = 1;

        imgSearch.setVisibility(View.INVISIBLE);
        update.setVisibility(View.INVISIBLE);
        delete.setVisibility(View.INVISIBLE);
        save.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.VISIBLE);
        add.setEnabled(false);
        viderChamp();
        enable();
        invisible();


    }

    void updateContrat() {
        try {
            x=cu.getString(5);
            op = 2;
            Log.d("id",cu.getString(5));
            imgSearch.setVisibility(View.VISIBLE);
            add.setVisibility(View.INVISIBLE);
            delete.setVisibility(View.INVISIBLE);
            save.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
            update.setEnabled(false);
            enable();
            invisible();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Sélectionner un contrat puis le modifier", Toast.LENGTH_SHORT).show();
        }


    }

    void saveContrat(SQLiteDatabase db) {
        if (op == 1) {
            Log.d("id",idClient.getText().toString());
            db.execSQL("insert into contrat (id,dateDebut,dateFin,redevance,idCl) values (?,?,?,?,?);"
                    , new String[]{null,edDateD.getText().toString(), edDateF.getText().toString(), edRedevance.getText().toString(),idClient.getText().toString()});
            Toast.makeText(this, "successfull ", Toast.LENGTH_SHORT).show();
            viderChamp();


        } else if (op == 2) {
            Log.d("i",x);
            db.execSQL("update contrat set dateDebut=?,dateFin=?,redevance=? where id=?", new String[]{edDateD.getText().toString(), edDateF.getText().toString(),
                    edRedevance.getText().toString(),x});
            Toast.makeText(this, "successfull", Toast.LENGTH_SHORT).show();
            viderChamp();
        }

        imgSearch.setVisibility(View.VISIBLE);
        update.setVisibility(View.VISIBLE);
        delete.setVisibility(View.VISIBLE);
        save.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.INVISIBLE);
        add.setEnabled(true);
        update.setEnabled(true);
        add.setVisibility(View.VISIBLE);
        disable();
        invisible();





    }

    void cancelContrat(){

        imgSearch.setVisibility(View.VISIBLE);
        update.setVisibility(View.VISIBLE);
        delete.setVisibility(View.VISIBLE);
        save.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.INVISIBLE);
        add.setEnabled(true);
        update.setEnabled(true);
        add.setVisibility(View.VISIBLE);
       disable();
        invisible();
       viderChamp();



    }

    void deleteContrat(SQLiteDatabase db,ContratActivity activity){
        try {
            x=cu.getString(5);
            AlertDialog.Builder bul=new AlertDialog.Builder(activity)
                    .setTitle("Confirmation")
                    .setMessage("Est ce que vous voulez supprimer ce contrat?")
                    .setIcon(R.drawable.validate)
                    .setPositiveButton("valider", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.execSQL("delete from contrat where id=?;",new String[]{cu.getString(5)});
                            viderChamp();
                            invisible();
                            cu.close();
                            dialog.dismiss();

                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            bul.show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(activity, "Selectionner un contrat puis le supprimer ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if (requestCode == 1) {

                String nom = data.getStringExtra("nom");
                String id = data.getStringExtra("id");
                String email=data.getStringExtra("email");
                txtNomC.setText(nom);
                idClient.setText(id);
                EdemailCl.setText(email);



            }
        }
    }
    public void disable(){
        edDateD.setEnabled(false);
        edDateF.setEnabled(false);
        edRedevance.setEnabled(false);
    }
    public void enable(){
        edDateD.setEnabled(true);
        edDateF.setEnabled(true);
        edRedevance.setEnabled(true);
    }
    public void viderChamp(){
        edDateD.setText("");
        edDateF.setText("");
        edRedevance.setText("");
        txtNomC.setText("");
        idClient.setText("");
        EdemailCl.setText("");
    }
    public void visible(){
        first.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);
        previous.setVisibility(View.VISIBLE);
        last.setVisibility(View.VISIBLE);
    }
    public void invisible(){
        first.setVisibility(View.INVISIBLE);
        next.setVisibility(View.INVISIBLE);
        previous.setVisibility(View.INVISIBLE);
        last.setVisibility(View.INVISIBLE);
    }
}