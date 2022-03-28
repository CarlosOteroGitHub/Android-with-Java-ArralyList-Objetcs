package com.example.myapplication;

/*

Author: ING. CARLOS OTERO RAMÍREZ

 */

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Clase1> lR = new ArrayList<Clase1>(); //Lista Alumnos Reprobados.
    ArrayList<Clase1> lA = new ArrayList<Clase1>(); //Lista Alumnos Aprobados.
    int contador = 0, contadorReprobados = 0, contadorAprobados = 0, totalAlumnos = 2, sumaCalReprobados = 0, sumaCalAprobados = 0;
    double promedioReprobados, promedioAprobados;

    private Button button1, button2, button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Calificación de Alumnos");

        button1 = findViewById(R.id.AM1_id1);
        button1.setOnClickListener(this);
        button2 = findViewById(R.id.AM1_id2);
        button2.setOnClickListener(this);
        button3 = findViewById(R.id.AM1_id3);
        button3.setOnClickListener(this);
    }

    public void dialogoAlerta(String titulo, String mensaje){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(mensaje)
                .setTitle(titulo)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int posicion) {
                        dialog.cancel();
                    }
                });
        dialog.show();
    }

    public void dialogoFormAlumno(){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.form_signin, null);

        final EditText editText1 = v.findViewById(R.id.FS_id1);
        final EditText editText2 = v.findViewById(R.id.FS_id2);
        editText1.setHint("Introduce el número de control");
        editText2.setHint("Introduce la calificación");

        dialog.setView(v)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int posicion) {
                        if(editText1.getText().toString().isEmpty() || editText2.getText().toString().isEmpty()){
                            dialogoAlerta("Mensaje de Información", "No deje los Campos de Texto Vacios");
                        } else {
                            if (Double.parseDouble(editText2.getText().toString()) < 70) {
                                lR.add(new Clase1(Integer.parseInt(editText1.getText().toString()), Double.parseDouble(editText2.getText().toString())));
                                sumaCalReprobados += Double.parseDouble(editText2.getText().toString());
                                contadorReprobados++;
                            } else {
                                lA.add(new Clase1(Integer.parseInt(editText1.getText().toString()), Double.parseDouble(editText2.getText().toString())));
                                sumaCalAprobados += Double.parseDouble(editText2.getText().toString());
                                contadorAprobados++;
                            }
                            contador++;
                        }
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int posicion) {
                        dialog.cancel();
                    }
                });
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.AM1_id1:
                if(contador == totalAlumnos){
                    dialogoAlerta("Mensaje de Error", "Ya capturaste todas las calificaciones");
                } else {
                    dialogoFormAlumno();
                }
                break;
            case R.id.AM1_id2:
                if(contador != totalAlumnos){
                    dialogoAlerta("Mensaje de Error", "Aún Falta de Capturar Alumnos");
                } else {
                    //Proceso de Resultados de los Alumnos Reprobados del Curso.
                    if (contadorReprobados > 0) {
                        String guardar1 = "";
                        promedioReprobados = sumaCalReprobados / contadorReprobados;
                        for (int i = 0; i < lR.size(); i++) {
                            System.out.println();
                            guardar1 += "No. Control: " + lR.get(i).getNoControl() + ", Obtubo una Calificación Final de: " + lR.get(i).getCalfFinal() + "\n";
                        }
                        dialogoAlerta("Mensaje de Información", "Hubo " + contadorReprobados + " reprobados" + "\n" + "Se obtuvo un puntaje total de: " + sumaCalReprobados + " y un Promedio de: " + promedioReprobados);
                    } else {
                        dialogoAlerta("Mensaje de Informacíon", "Ningun alumno inscrito reprobo el curso");
                    }

                    //Proceso de Resultados de los Alumnos Aprobados del Curso.
                    if (contadorAprobados > 0) {
                        String guardar2 = "";
                        promedioAprobados = sumaCalAprobados / contadorAprobados;
                        for (int i = 0; i < lA.size(); i++) {
                            System.out.println();
                            guardar2 += "No. Control: " + lA.get(i).getNoControl() + ", Obtubo una Calificación Final de: " + lA.get(i).getCalfFinal() + "\n";
                        }
                        dialogoAlerta("Mensaje de Información", "Hubo " + contadorAprobados + " aprobados" + "\n" + "Se obtuvo un puntaje total de: " + sumaCalAprobados + " y un Promedio de: " + promedioAprobados);
                    } else {
                        dialogoAlerta("Mensaje de Informacíon", "Ningun alumno inscrito aprobo el curso");
                    }
                }
                break;
            case R.id.AM1_id3:
                System.exit(0);
                break;
        }
    }
}