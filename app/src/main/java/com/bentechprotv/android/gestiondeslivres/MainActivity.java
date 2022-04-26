package com.bentechprotv.android.gestiondeslivres;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Q1
    SQLiteDatabase bd;
    Button btnAjouterLivre;
    EditText txtISBN, txtTitreLivre ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtISBN = (EditText) findViewById(R.id.txtISBN);
        txtTitreLivre = (EditText) findViewById(R.id.txtTitreLivre);
        btnAjouterLivre = (Button) findViewById(R.id.btnAjouterLivre);


        //Q1
        bd = openOrCreateDatabase("BDLivres",Context.MODE_PRIVATE,null);
        //Q2
        bd.execSQL("CREATE TABLE IF NOT EXISTS livres (id integer primary key, ISBN VARCHAR,TITRE VARCHAR);");
        //Q3
        Cursor c=bd.rawQuery("SELECT * FROM Livres",null);
        if(c.getCount()==0)
        {
            Toast.makeText(getApplicationContext(),"Table Vide",
                    Toast.LENGTH_LONG).show();
            //Q6
            bd.execSQL("INSERT INTO Livres(id,isbn,titre) VALUES(1,'619-23654','Le langage C');");
            bd.execSQL("INSERT INTO Livres(id,isbn,titre) VALUES(2,'235-78965','Systèmes Temps Réel');");

            //   return;
        }

        btnAjouterLivre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Q7
                //récupérer le isbn de l’interface
                String userISBN = "";
                userISBN = txtISBN.getText().toString();
                //tester la présence du isbn dans la table Livres
                String req = "SELECT * FROM Livres where isbn ='" + userISBN + "'";
                Cursor c=bd.rawQuery(req,null);
                if(c.getCount()==0)
                {
                    // lancer l’insertion
                    // Afficher le nouveau id
                    Toast.makeText(getApplicationContext(),String.valueOf(NewID()),Toast.LENGTH_LONG).show();
                    //Q6 : insertion d'un nouveau produit
                    String requete = "INSERT INTO Livres(id,isbn,titre) VALUES(" +
                            String.valueOf(NewID()) + ",'" + txtISBN.getText().toString()
                            + "','" + txtTitreLivre.getText().toString()
                            + "');";
                    bd.execSQL(requete);
                }else{
                    // si le isbn existe (c.getCount() différent de zéro) : afficher Toast : ISBN Existe déjà.
                    Toast.makeText(getApplicationContext(),"ISBN Existe déjà.",
                            Toast.LENGTH_LONG).show();
                    return;
                }




            }
        });
    }

    //Q4
    public int NewID(){
        int max;
    //chercher le max
        Cursor c2=bd.rawQuery("SELECT MAX(id) FROM Livres",null);
        if(c2.moveToFirst()){
            max = c2.getInt(0);}
        else{
            max = 0;
        }
        //ajouter 1 au max
        max=max+1;
        //retouner le nouveau id
        return max;
    }
}

