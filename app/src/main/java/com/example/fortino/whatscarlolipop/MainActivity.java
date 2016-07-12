package com.example.fortino.whatscarlolipop;

import android.accounts.AccountAuthenticatorActivity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyPingCallback;
import com.kinvey.android.callback.KinveyUserCallback;
import com.kinvey.java.User;

/*Clase principal en la cual el usuario puede realizar el proceso de logueo o dirigirse a la opción de registro.
* @Author: César Eduardo Celedonio García
* @Version:07/07/2016
* */
public class MainActivity extends AccountAuthenticatorActivity {

    private ProgressDialog mProgressDialog = null;

    //Declaración de controles.
    private EditText etTelefono;
    private EditText etContrasena;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Referencia de los controles a traves del Id
        etTelefono=(EditText)findViewById(R.id.etLogTelefono);
        etContrasena=(EditText)findViewById(R.id.etLogContrasena);
        final Client client = new Client.Builder("kid_rkAKJwKL"//App_Id
                ,"983991c098fa43ada25fa446a54b49f3"//App_Secret
                ,getApplicationContext()).build();
        //Ping para confirmar que realmente se esta realizando la conexión con KinveyDataMain.
          client.ping(new KinveyPingCallback() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                Toast.makeText(MainActivity.this,"Nuestro ping esta bien",Toast.LENGTH_SHORT).show();
                client.user().logout();
            }

            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(MainActivity.this,"Problemas con la conexión",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id, Bundle args) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Authenticating...");
        dialog.setIndeterminate(true);

        mProgressDialog = dialog;
        return dialog;
    }
    /**
     * Metodo que permite la auntentificación de usuarios.
     */
    public void login(View view) {
        final Client client = new Client.Builder("kid_rkAKJwKL"//App_Id
                ,"983991c098fa43ada25fa446a54b49f3"//App_Secret
                ,getApplicationContext()).build();
        client.user().login(etTelefono.getText().toString(), etContrasena.getText().toString(), new KinveyUserCallback() {
            @Override
            public void onSuccess(User user) {
                CharSequence text = "Bienvenido," + user.getUsername() + ".";
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                //Navega a la pantalla principal llamada nav_drawerActivity
                Intent intent = new Intent(MainActivity.this, nav_drawerActivity.class);
                MainActivity.this.finish();
                startActivity(intent);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                //Oculta la animación de validación y finaliza la pantalla de MainActivity

            }
            @Override
            public void onFailure(Throwable throwable) {
                CharSequence text = "Contraseña o usuario incorrectos.";
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO:  Fix ShowDialog
    @SuppressWarnings("deprecation")
    public void showProgress() {
        showDialog(0);
    }

    private void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    //// TODO: 08/07/2016 Metodos para el flujo de las pantallas
 //Navega a la pantalla de Cambiar Telefono.
    public void camTelefono_click(View view) {
        Intent i = new Intent(this, cambiarTelefonoActivity.class);
        startActivity(i);
        //Animación entre pantallas
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }
//Navega a la pantalla de Cambiar Contraseña.
    public void camContraseña_click(View view) {
        Intent i = new Intent(this, CambiarContrasenaActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }
//Navega a la pantalla de registrar.
    public void Registrar_click(View view) {
        Intent i = new Intent(this, RegistrarActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }
 }

