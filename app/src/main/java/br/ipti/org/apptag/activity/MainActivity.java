package br.ipti.org.apptag.activity;


/**
 * Created by AdrianoDias on 10/03/2016.
 */


import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import br.ipti.org.apptag.fragment.MainFragment;
import ipti.apptag.R;

public class MainActivity extends AppCompatActivity {

    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private TextView textUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //receiving intent data
        String username = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            if(extras.containsKey("username"))
                username = extras.getString("username");
        }





        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);


        //Dado artificial
        if(username.equals("Professor")){
            setProperMenu(navigationView, 1);
        }else{
            setProperMenu(navigationView, 2);
        }



        //setting Username TextView
        View header = navigationView.getHeaderView(0);
        textUser = (TextView) header.findViewById(R.id.user);
        textUser.setText(username);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.cadnota:
                        Toast.makeText(getApplicationContext(),"Cadastrar Nota Selecionado",Toast.LENGTH_SHORT).show();
                        MainFragment fragment = new MainFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame,fragment);
                        fragmentTransaction.commit();
                        return true;

                    // For rest of the options we just show a toast on click

                    case R.id.boletim:
                        Toast.makeText(getApplicationContext(),"Boletim Selecionado",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.cadfreq:
                        Toast.makeText(getApplicationContext(),"Cadastrar Frequencia Selecionado",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.listfreq:
                        Toast.makeText(getApplicationContext(),"Listar Frequencia Selecionado",Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(),"Algo errado",Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setProperMenu(NavigationView navigationView, int code){

        final Menu menu = navigationView.getMenu();

        final SubMenu notas = menu.addSubMenu(0, R.id.nota, 1, "Notas");
        final SubMenu frequencia = menu.addSubMenu(0, R.id.frequencia, 2, "Frequência");

        if(code == 1) {
            notas.add(0, R.id.cadnota, 1, "Cadastrar");
            frequencia.add(0, R.id.cadfreq, 1, "Cadastrar");
        }
        notas.add(0, R.id.boletim, 2, "Boletim");
        frequencia.add(0, R.id.listfreq, 2, "Listar");
    }
}