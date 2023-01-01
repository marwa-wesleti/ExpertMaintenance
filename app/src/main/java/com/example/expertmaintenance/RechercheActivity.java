package com.example.expertmaintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class RechercheActivity extends AppCompatActivity {
    private EditText edSearchNom, edRechNom, edRechId,edRechEmail;
    private ImageButton imgRech, Rechfirst, Rechprevious, Rechnext, Rechlast;
    private Button btnValider, btnCancel;
    private SQLiteDatabase db;
    private Cursor cu,cu1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);
        edSearchNom = (EditText) findViewById(R.id.edSearchNom);
        edRechNom = (EditText) findViewById(R.id.edRechNom);
        edRechId = (EditText) findViewById(R.id.edRechId);
        edRechEmail = (EditText) findViewById(R.id.edRechEmail);
        imgRech = (ImageButton) findViewById(R.id.imgRech);
        Rechfirst = (ImageButton) findViewById(R.id.Rechfirst);
        Rechprevious = (ImageButton) findViewById(R.id.Rechprevious);
        Rechnext = (ImageButton) findViewById(R.id.Rechnext);
        Rechlast = (ImageButton) findViewById(R.id.Rechlast);
        btnValider = (Button) findViewById(R.id.btnValider);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        db = openOrCreateDatabase("EXPERT_MAINTENANCE", MODE_PRIVATE, null);

        imgRech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RechercherNomClient(db);
            }
        });
        Rechfirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RechFirst();
            }
        });

        Rechprevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rechprevious();
            }
        });
        Rechnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rechnext();
            }
        });

        Rechlast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rechlast();
            }
        });

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valider();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });




    }

    public void RechercherNomClient(SQLiteDatabase db) {
        cu = db.rawQuery("select id,nom,email from client where nom like ?  ", new String[]{"%"+edSearchNom.getText().toString()+"%"});
        cu.moveToFirst();
        Log.d("nom",cu.getString(0));
        try {
            cu.moveToFirst();
            Log.d("nom",cu.getString(1));
            edRechId.setText(cu.getString(0));
            edRechNom.setText(cu.getString(1));
            edRechEmail.setText(cu.getString(2));
            if (cu.getCount() == 1) {
                Rechfirst.setVisibility(View.INVISIBLE);
                Rechnext.setVisibility(View.INVISIBLE);
                Rechprevious.setVisibility(View.INVISIBLE);
                Rechlast.setVisibility(View.INVISIBLE);
            } else {
                Rechfirst.setVisibility(View.VISIBLE);
                Rechnext.setVisibility(View.VISIBLE);
                Rechprevious.setVisibility(View.VISIBLE);
                Rechlast.setVisibility(View.VISIBLE);
                Rechprevious.setEnabled(false);
                Rechnext.setEnabled(true);
            }
        }catch(Exception e){
                e.printStackTrace();
            Toast.makeText(this, "aucun resultat", Toast.LENGTH_SHORT).show();
            edRechId.setText("");
            edRechNom.setText("");
            edRechEmail.setText("");
            Rechfirst.setVisibility(View.INVISIBLE);
            Rechnext.setVisibility(View.INVISIBLE);
            Rechprevious.setVisibility(View.INVISIBLE);
            Rechlast.setVisibility(View.INVISIBLE);
            }
    }
    public void RechFirst(){
        try {
            cu.moveToFirst();
            edRechId.setText(cu.getString(0));
            edRechNom.setText(cu.getString(1));
            edRechEmail.setText(cu.getString(2));
            Rechprevious.setEnabled(false);
            Rechnext.setEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"aucun client n'est existant.",Toast.LENGTH_SHORT).show();
        }

    }
    public void Rechnext(){
        try {
            cu.moveToNext();
            edRechId.setText(cu.getString(0));
            edRechNom.setText(cu.getString(1));
            edRechEmail.setText(cu.getString(2));
            Rechprevious.setEnabled(true);
            if (cu.isLast()){
                Rechnext.setEnabled(false);
            }

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }
    public  void Rechprevious(){
        try {
            cu.moveToPrevious();
            edRechId.setText(cu.getString(0));
            edRechNom.setText(cu.getString(1));
            edRechEmail.setText(cu.getString(2));
            Rechnext.setEnabled(true);
            if (cu.isLast()){
                Rechprevious.setEnabled(false);
            }

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Rechlast(){
        try {
            cu.moveToLast();
            edRechId.setText(cu.getString(0));
            edRechNom.setText(cu.getString(1));
            edRechEmail.setText(cu.getString(2));
            Rechprevious.setEnabled(true);
            Rechnext.setEnabled(false);

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void valider(){

            String nom=edRechNom.getText().toString();
            String id=edRechId.getText().toString();
            String email=edRechEmail.getText().toString();
            Intent intent=new Intent();
            intent.putExtra("nom",nom);
            intent.putExtra("id",id);
            intent.putExtra("email",email);
            setResult(RESULT_OK,intent);
            finish();


    }

    public void cancel() {
        Intent intent = new Intent();
        intent.putExtra("nom", "");
        intent.putExtra("id", "");
        intent.putExtra("tel", "");

        setResult(RESULT_OK, intent);
        finish();
    }






}