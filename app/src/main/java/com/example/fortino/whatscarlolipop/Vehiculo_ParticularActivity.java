package com.example.fortino.whatscarlolipop;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
/*Esta clase muestra las opciones para cada vehiculos que se muestra.
* @Author: César Eduardo Celedonio García
* @Version:07/07/2016
* */
public class Vehiculo_ParticularActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo__particular);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("        Vehículo particular");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void ControlarDispositivos_click(View view) {
        Intent i = new Intent(this, Controlar_DispositivosActivity.class );
        startActivity(i);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }
    public void Informacion_click(View view) {
        Intent i = new Intent(this, Monitorear_VehiculoActivity.class );
        startActivity(i);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
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
}

