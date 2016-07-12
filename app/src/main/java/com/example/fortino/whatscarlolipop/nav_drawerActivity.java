package com.example.fortino.whatscarlolipop;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import com.example.fortino.whatscarlolipop.SimpleGestureFilter.SimpleGestureListener;
import com.kinvey.android.Client;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

/*Clase de la que muestra los vehiculos registrados y despliega opciones como: eliminar, editar,
* @Author: César Eduardo Celedonio García
* @Version:07/07/2016
* */
public class nav_drawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SimpleGestureListener{


    private LinearLayoutCompat liyBotonEliminar;
    private LinearLayoutCompat liyBotonEditar;
    private LinearLayoutCompat liyFlecha;
    private LinearLayoutCompat liyFlecha2;
    private SimpleGestureFilter detector;

    public void Editar_click(View view) {
        Intent i = new Intent(this, Modificar_VehiculoActivity.class );
        startActivity(i);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }
    public void VehiculoParticular_click(View view) {
        Intent i = new Intent(this, Vehiculo_ParticularActivity.class );
        startActivity(i);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    //Metodo  que crea las opciones en el menu tales como la flacha de "atras"
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            overridePendingTransition(R.anim.right_in, R.anim.right_out);
        }


        //Agrega icono de "icono de vehiculo"

        switch (item.getItemId()) {
            case R.id.action_moto:
                Log.i("ActionBar", "Add Phrase!");
                Intent AddPhrase = new Intent(getApplicationContext(), Agregar_VehiculoActivity.class);
                startActivity(AddPhrase);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        liyBotonEliminar=(LinearLayoutCompat)findViewById(R.id.liyBotonera1);
        liyBotonEditar=(LinearLayoutCompat)findViewById(R.id.liyBotonera2);
        liyFlecha=(LinearLayoutCompat)findViewById(R.id.liyFlecha);
        liyFlecha2=(LinearLayoutCompat)findViewById(R.id.liyFlecha2);
        detector = new SimpleGestureFilter(this,this);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    //Método para  controlar las salidas de la aplicación.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Construccion del cliente
        final Client client = new Client.Builder("kid_rkAKJwKL"//App_Id
                ,"983991c098fa43ada25fa446a54b49f3"//App_Secret
                ,getApplicationContext()).build();
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Salir")
                    .setMessage("Estás seguro?")
                    .setNegativeButton(android.R.string.cancel, null)// sin listener
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {// un listener que al pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Salir: Finaliza el activity nav_drawer y el main activity.
                            nav_drawerActivity.this.finish();
                            //Cierra la sesión del usuario
                            client.user().logout();
                        }
                    }).show();


// Si el listener devuelve true, significa que el evento esta procesado, y nadie debe hacer nada mas
            return true;
        }
// para las demas cosas, se reenvia el evento al listener habitual
        return super.onKeyDown(keyCode, event);
    }


    //En este metodo se agregan las opciones al Navigation Drawer
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            Intent i = new Intent(this, Modificar_VehiculoActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent me){
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }
    public void mostrar(View view){
        Animation movimiento1;
        movimiento1= AnimationUtils.loadAnimation(this, R.anim.left_in);
        movimiento1.reset();
        liyBotonEliminar.startAnimation(movimiento1);
        liyBotonEditar.startAnimation(movimiento1);
        liyBotonEliminar.setVisibility(View.VISIBLE);
        liyBotonEditar.setVisibility(View.VISIBLE);
        liyFlecha.setVisibility(View.INVISIBLE);
        liyFlecha2.setVisibility(View.INVISIBLE);
    }
//Agrega el gesto de deslizar dedo sobre la pantalla
    @Override
    public void onSwipe(int direction) {
        String str = "";

        switch (direction) {

            case SimpleGestureFilter.SWIPE_RIGHT:
                str = "Deslice hacia la izquierda para ver opciones";
                Animation movimiento;
                movimiento= AnimationUtils.loadAnimation(this, R.anim.right_out);
                movimiento.reset();
                liyBotonEliminar.startAnimation(movimiento);
                liyBotonEditar.startAnimation(movimiento);
                liyBotonEliminar.setVisibility(View.INVISIBLE);
                liyBotonEditar.setVisibility(View.INVISIBLE);
                liyFlecha.setVisibility(View.VISIBLE);
                liyFlecha2.setVisibility(View.VISIBLE);

                break;
            case SimpleGestureFilter.SWIPE_LEFT:
                str = "Deslice hacia la derecha para ocultar";
                Animation movimiento1;
                movimiento1= AnimationUtils.loadAnimation(this, R.anim.left_in);
                movimiento1.reset();
                liyBotonEliminar.startAnimation(movimiento1);
                liyBotonEditar.startAnimation(movimiento1);
                liyBotonEliminar.setVisibility(View.VISIBLE);
                liyBotonEditar.setVisibility(View.VISIBLE);
                liyFlecha.setVisibility(View.INVISIBLE);
                liyFlecha2.setVisibility(View.INVISIBLE);
                break;
            case SimpleGestureFilter.SWIPE_DOWN:
                str = "";

                break;
            case SimpleGestureFilter.SWIPE_UP:
                str = "";

                break;
        }
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDoubleTap() {
        Toast.makeText(this, "Deslice hacia la izquierda para ver opciones", Toast.LENGTH_SHORT).show();
    }
}
