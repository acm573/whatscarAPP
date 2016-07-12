package com.example.fortino.whatscarlolipop;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyUserCallback;
import com.kinvey.java.User;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.Validator;

import java.util.List;

/*Clase que permite el registro de usuarios a la aplicación.
* @Author: César Eduardo Celedonio García
* @Version:07/07/2016
* */
public class RegistrarActivity extends AppCompatActivity implements Validator.ValidationListener {
    @NotEmpty(message = "Debe introducir un nombre")
    private EditText etNomUsuario;
    @Email(message = "Debe introducir un correo valido, ej:alguien@ejemplo.com")
    private EditText etEmail;
    @NotEmpty(message = "Debe introducir un número de teléfono valido de 10 digitos")
    private EditText etTelefono;
    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS, message = "Contraseña invalida: Debe contener números, letras, símbolos, mayúsculas y minúsculas.")
    private EditText etContraseña;
    @ConfirmPassword(message = "Las contraseñas no coinciden")
    private EditText etReContraseña;
    Validator validator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        //Asignacion de controles a traves de su id.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        etNomUsuario = (EditText) findViewById(R.id.etRegNomUsuario);
        etEmail = (EditText) findViewById(R.id.etRegEmail);
        etTelefono = (EditText) findViewById(R.id.etRegTelefono);
        etContraseña = (EditText) findViewById(R.id.etRegContrasena);
        etReContraseña = (EditText) findViewById(R.id.etRegRepContrasena);

        setTitle("        Registro");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        validator = new Validator(this);
        validator.setValidationListener(this);
    }
//Metodo por el cual se activa el proceso de validación.
    public void Validar(View view) {
        if (etTelefono.length()==10){
            validator.validate();
        }else
            Toast.makeText(RegistrarActivity.this, "El telefono debe contener 10 digitos", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            overridePendingTransition(R.anim.right_in, R.anim.right_out);
        }
        return super.onOptionsItemSelected(item);
    }

    //Método que se invoca sí las validaciones son correctas.
    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this, "Datos ingresados correctamente", Toast.LENGTH_SHORT).show();
        processSignup();
    }

    //Método que se invoca en caso de que las validaciones sean erroneas.
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
    //Este método se encarga de registrar al usuario en KINVEY.
    public void processSignup() {
        Client client = new Client.Builder("kid_rkAKJwKL"//App_Id
                , "983991c098fa43ada25fa446a54b49f3"//App_Secret
                , getApplicationContext()).build();

        Toast.makeText(this, "Creating user...", Toast.LENGTH_SHORT).show();
        client.user().create(etTelefono.getText().toString(), etContraseña.getText().toString(), new KinveyUserCallback() {
            @Override
            public void onSuccess(User user) {
                CharSequence text = "Bienvenido, " + user.get("username") + ".  Tu cuenta ha sido registrada.  Por favor logueate para confirmar tus credenciales.";
                user.put("email", user.get("username"));
                user.put("firstname", etNomUsuario.getText().toString());
                user.put("lastname", etEmail.getText().toString());

                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
                RegistrarActivity.this.startActivity(new Intent(RegistrarActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                RegistrarActivity.this.finish();
            }

            public void onFailure(Throwable t) {
                CharSequence text = "No se pudo crear tu cuenta-> " + t.getMessage();
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}


