package com.lib.use;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lib.android.base.FixFragmentNavigator;
import com.lib.use.ui.dashboard.DashboardFragment;
import com.lib.use.ui.home.HomeFragment;
import com.lib.use.ui.notifications.NotificationsFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavGraphNavigator;
import androidx.navigation.Navigation;
import androidx.navigation.NavigatorProvider;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);

//        //获取页面容器NavHostFragment
//        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
//        //获取导航控制器
//        NavController navController = NavHostFragment.findNavController(fragment);
//        //创建自定义的Fragment导航器
//        FixFragmentNavigator fragmentNavigator =
//                new FixFragmentNavigator(this, fragment.getChildFragmentManager(), fragment.getId());
//        //获取导航器提供者
//        NavigatorProvider provider = navController.getNavigatorProvider();
//        //把自定义的Fragment导航器添加进去
//        provider.addNavigator(fragmentNavigator);
//        //手动创建导航图
//        NavGraph navGraph = initNavGraph(provider, fragmentNavigator);
//        //设置导航图
//        navController.setGraph(navGraph);
//        //底部导航设置点击事件
//        navView.setOnNavigationItemSelectedListener(item -> {
//            navController.navigate(item.getItemId());
//            return true;
//        });

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        FixFragmentNavigator fixFragmentNavigator = new FixFragmentNavigator(this, navHostFragment.getChildFragmentManager(), R.id.nav_host_fragment);
        navController.getNavigatorProvider().addNavigator("fixFragment", fixFragmentNavigator);
        navController.setGraph(R.navigation.mobile_navigation);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


    }

    //手动创建导航图，把3个目的地添加进来
    private NavGraph initNavGraph(NavigatorProvider provider, FixFragmentNavigator fragmentNavigator) {
        NavGraph navGraph = new NavGraph(new NavGraphNavigator(provider));

        //用自定义的导航器来创建目的地
        //home destination
        FragmentNavigator.Destination destination1 = fragmentNavigator.createDestination();
        destination1.setId(R.id.navigation_home);
        destination1.setClassName(HomeFragment.class.getCanonicalName());
        destination1.setLabel(getResources().getString(R.string.title_home));
        navGraph.addDestination(destination1);

        //dashboard destination
        FragmentNavigator.Destination destination2 = fragmentNavigator.createDestination();
        destination2.setId(R.id.navigation_dashboard);
        destination2.setClassName(DashboardFragment.class.getCanonicalName());
        destination2.setLabel(getResources().getString(R.string.title_dashboard));
        navGraph.addDestination(destination2);

        //notifications destination
        FragmentNavigator.Destination destination3 = fragmentNavigator.createDestination();
        destination3.setId(R.id.navigation_notifications);
        destination3.setClassName(NotificationsFragment.class.getCanonicalName());
        destination3.setLabel(getResources().getString(R.string.title_notifications));
        navGraph.addDestination(destination3);

        navGraph.setStartDestination(R.id.navigation_home);

        return navGraph;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}