package com.kamerlin.leon.todolist;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Toast;

import com.kamerlin.leon.todolist.db.Category;
import com.kamerlin.leon.todolist.db.DbService;
import com.kamerlin.leon.todolist.db.Task;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, TaskAdapter.OnItemClickListener {

    private static final int TASK_ACTIVITY_REQUEST_CODE = 10;
    public static final String TASK_EXTRA = "task_extra";
    private DbService mService;
    boolean mBound = false;
    private Menu mMenu;
    private RecyclerView mRecyclerView;
    private TaskAdapter mTaskAdapter;
    private FloatingActionButton mFab;

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, DbService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);


        mMenu = navigationView.getMenu();
        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(v -> startActivityForResult(new Intent(this, TaskActivity.class), TASK_ACTIVITY_REQUEST_CODE));


        navigationView.setNavigationItemSelectedListener(this);

        mRecyclerView = findViewById(R.id.recyclerView);
        mTaskAdapter = new TaskAdapter();

        mRecyclerView.setAdapter(mTaskAdapter);



        DbService.selectAllCategories(this);
        DbService.selectAllTasks(this);

    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @SuppressLint("CheckResult")
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            mBound = true;
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            DbService.LocalBinder binder = (DbService.LocalBinder) service;
            mService = binder.getService();

            /*MenuItem d = mMenu.add("All").setIcon(R.drawable.ic_menu_gallery);

            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_theme, null);
            d.setActionView(view);*/
         /*   mMenu.add("Personal").setIcon(R.drawable.ic_menu_gallery);
            mMenu.add("Shopping").setIcon(R.drawable.ic_menu_gallery);
            mMenu.add("Work").setIcon(R.drawable.ic_menu_gallery);
            mMenu.add("Errands").setIcon(R.drawable.ic_menu_gallery);
            mMenu.add("Sport").setIcon(R.drawable.ic_menu_gallery);
            mMenu.add("Movies to watch").setIcon(R.drawable.ic_menu_gallery);*/



          /*  mService.getCategorysObservable().subscribe(categories -> {
               for (Category category : categories) {
                   mMenu.add(category.getName());

               }
            });*/

            mService.getTasksObservable()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(tasks -> mTaskAdapter.addTasks(tasks));






         /*   SubMenu subMenu = mMenu.addSubMenu("Settings");

            subMenu.add("New Category");
            subMenu.add("Settings");*/


        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu_sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_priority:
                break;

            case R.id.menu_alphabetical_a_z:
                break;

            case R.id.menu_alphabetical_z_a:
                break;

            case R.id.menu_created_newest_first:
                break;

            case R.id.menu_created_oldest_first:
                break;

            case R.id.menu_reminder_newest_first:
                break;

            case R.id.menu_reminder_oldest_first:
                break;
        }


        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {




        if (item.getTitle().equals("New Category")) {

        } else if (item.getTitle().equals("Settings")) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else {
            //mTaskAdapter.clear();
            //vice.selectTasksByCategory(MainActivity.this, item.getTitle());

        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(View v, int position) {
        Task task = mTaskAdapter.getTask(position);
        Intent intent = new Intent(this, TaskActivity.class);
        intent.putExtra(TASK_EXTRA, task);
        startActivityForResult(intent, TASK_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onItemToggled(boolean active, int position) {

    }
}
