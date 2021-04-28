package com.hdrescuer.hdrescuer.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.hdrescuer.hdrescuer.R;
import com.hdrescuer.hdrescuer.data.SessionsListViewModel;
import com.hdrescuer.hdrescuer.data.UserListViewModel;

import androidx.core.view.MenuItemCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * Clase Home de la aplicación. Pantalla de inicio
 * @author Domingo Lopez
 */
public class HomeActivity extends AppCompatActivity {

    UserListViewModel userListViewModel;
    SessionsListViewModel sessionsListViewModel;

    private AppBarConfiguration mAppBarConfiguration;

    private ImageView sessions_not_registered;

    /**
     * Inicia la actividad HomeActivity, cargando los fragmentos necesarios al inicio y el userListViewModel
     * @author Domingo Lopez
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_users, R.id.nav_sessions,R.id.nav_config, R.id.nav_support)
                .setDrawerLayout(drawer)
                .build();

        //Añadir badge que indica que hay sesiones locales pendientes sin subir
        this.sessions_not_registered = (ImageView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.nav_sessions));
        //En el onResume inicializa el badge

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //INICIAMOS VIEWMODELS QUE USARÁN LOS DISTINTOS FRAGMENTS DEL MENÚ PRINCIPAL

        //ViewModel para el fragmento de los usuarios
        this.userListViewModel = new UserListViewModel(getApplication());
        //Viewmodel para el fragmento de las sesiones sin registrar
        this.sessionsListViewModel = new SessionsListViewModel(getApplication());

    }

    /**
     * Inflate the menu; this adds items to the action bar if it is present.
     * @author Domingo Lopez
     * @param menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    /**
     * Inicia barra de navegación para soporte
     * @author Domingo Lopez
     * @return boolean
     */
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    protected void onResume() {
        super.onResume();
        initializeCountBadge();

    }

    public void initializeCountBadge(){

        FrameLayout.LayoutParams imageParams;
        imageParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);

        imageParams.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        this.sessions_not_registered.setLayoutParams(imageParams);

        this.sessions_not_registered.setBackgroundResource(R.drawable.ic_baseline_warning_24);
    }
}