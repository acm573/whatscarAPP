package com.example.fortino.whatscarlolipop;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.Validator;
import java.util.List;

/*Clase que permite que el usuario pueda modificar datos del vehiculo tales como marca, modelo, etc.
* @Author: César Eduardo Celedonio García
* @Version:07/07/2016
* */
public class Modificar_VehiculoActivity extends AppCompatActivity implements Validator.ValidationListener {
    @NotEmpty(message = "Debe introducir una marca")
    private EditText etMarca;
    @NotEmpty(message = "Debe introducir un modelo")
    private EditText etModelo;
    @NotEmpty(message = "Debe introducir un identificador de hadware")
    private EditText etId_Hardware;
    @NotEmpty(message = "Debe introducir un nombre")
    private EditText etNom_Vehiculo;
    @NotEmpty(message = "Debe introducir las placas")
    private EditText etPlacas;
    Validator validator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar__vehiculo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        etMarca=(EditText)findViewById(R.id.etMarca);
        etModelo=(EditText) findViewById(R.id.etModelo);
        etId_Hardware=(EditText) findViewById(R.id.etIdHarware);
        etNom_Vehiculo=(EditText) findViewById(R.id.etNomVehiculo);
        etPlacas=(EditText) findViewById(R.id.etPlacas);

        setTitle("        Modificar vehículo");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        validator = new Validator(this);
        validator.setValidationListener(this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id==android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
            overridePendingTransition(R.anim.right_in, R.anim.right_out);
        }
        return super.onOptionsItemSelected(item);
    }
    public void Validar_click(View view) {
        validator.validate();
    }
    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this, "Cambios realizados", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, nav_drawerActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}

