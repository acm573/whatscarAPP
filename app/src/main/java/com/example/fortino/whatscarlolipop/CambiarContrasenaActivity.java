package com.example.fortino.whatscarlolipop;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

    /*Clase que permite realizar el cambio de la contraseña
    * @Author: César Eduardo Celedonio García
    * @Version:07/07/2016
    * */

public class CambiarContrasenaActivity extends AppCompatActivity implements Validator.ValidationListener {
    @NotEmpty(message = "Debe introducir un número de teléfono valido de 10 digitos")
    private EditText etTelefono;
    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS, message = "Contraseña invalida: Debe contener números, letras, símbolos, mayúsculas y minúsculas.")
    private EditText etContrasena;
    @ConfirmPassword(message = "Las contraseñas no coinciden")
    private EditText etReContrasena;
    Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contrasena);
        //Asignación de controles a su Id.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        etTelefono=(EditText)findViewById(R.id.etConTelefono);
        etContrasena=(EditText)findViewById(R.id.etConContrasena);
        etReContrasena=(EditText)findViewById(R.id.etConReContrasena);

        setTitle("        Cambiar contraseña");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        validator = new Validator(this);
        validator.setValidationListener(this);
    }
    public void camCondicoAct_click(View view) {
       validator.validate();
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

    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this, "Datos ingresados correctamente", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, codigoActivacionActivity.class);
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
