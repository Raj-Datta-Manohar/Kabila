package com.android.kabila;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.nikartm.support.BadgePosition;
import ru.nikartm.support.ImageBadgeView;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    private NavigationView nv;
    private Toolbar toolbar;
    private SearchView searchView;
    private RecyclerView popularCategories, mostBoughtProducts;
    private RecyclerView.Adapter pcAdapter, mbpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dl = findViewById(R.id.drawer_layout);
        nv = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.main_toolbar);
        searchView = findViewById(R.id.main_toolbar_search);
        popularCategories = findViewById(R.id.categories);
        mostBoughtProducts = findViewById(R.id.mostboughtitems);
        final TextView appName = findViewById(R.id.toolbar_app_name);
        final ImageBadgeView notifications = findViewById(R.id.main_toolbar_notifications);
        final ImageBadgeView cart = findViewById(R.id.main_toolbar_cart);
        final ImageBadgeView wishlist = findViewById(R.id.main_toolbar_wishlist);
        View headerView = nv.getHeaderView(0);
        final TextView msm_uname = (TextView) headerView.findViewById(R.id.msm_username);

        final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("KabilaPreferences",MODE_PRIVATE);

        String un = sharedPreferences.getString("mobile", "User");
        msm_uname.setText("Hello "+un);

        toolbar.setNavigationIcon(R.drawable.menu_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl.openDrawer(GravityCompat.START);
            }
        });
        abdt = new ActionBarDrawerToggle(this, dl, R.string.msm_open, R.string.msm_close);
        dl.addDrawerListener(abdt);
        abdt.syncState();

        int cartCount = sharedPreferences.getInt("cartCount",0);
        if(cartCount>0) {
            cart.setBadgeValue(cartCount)
                    .setBadgeOvalAfterFirst(true)
                    .setBadgeTextSize(8)
                    .setMaxBadgeValue(999)
                    .setBadgeBackground(getResources().getDrawable(R.drawable.rectangle_rounded))
                    .setBadgePosition(BadgePosition.TOP_RIGHT)
                    .setBadgeTextStyle(Typeface.NORMAL)
                    .setShowCounter(true)
                    .setBadgePadding(4);
        }

        int wishlistCount = sharedPreferences.getInt("wishlistCount",0);
        if(wishlistCount>0) {
            wishlist.setBadgeValue(cartCount)
                    .setBadgeOvalAfterFirst(true)
                    .setBadgeTextSize(8)
                    .setMaxBadgeValue(999)
                    .setBadgeBackground(getResources().getDrawable(R.drawable.rectangle_rounded))
                    .setBadgePosition(BadgePosition.TOP_RIGHT)
                    .setBadgeTextStyle(Typeface.NORMAL)
                    .setShowCounter(true)
                    .setBadgePadding(4);
        }

        int notificationsCount = sharedPreferences.getInt("notificationsCount",5);
        if(notificationsCount>0) {
            notifications.setBadgeValue(cartCount)
                    .setBadgeOvalAfterFirst(true)
                    .setBadgeTextSize(8)
                    .setMaxBadgeValue(999)
                    .setBadgeBackground(getResources().getDrawable(R.drawable.rectangle_rounded))
                    .setBadgePosition(BadgePosition.TOP_RIGHT)
                    .setBadgeTextStyle(Typeface.NORMAL)
                    .setShowCounter(true)
                    .setBadgePadding(4);
        }

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    appName.setVisibility(View.GONE);
                    notifications.setVisibility(View.GONE);
                    cart.setVisibility(View.GONE);
                    wishlist.setVisibility(View.GONE);
                } else {
                    searchView.setQuery("", false);
                    searchView.clearFocus();
                    searchView.setIconified(true);
                    appName.setVisibility(View.VISIBLE);
                    notifications.setVisibility(View.VISIBLE);
                    cart.setVisibility(View.VISIBLE);
                    wishlist.setVisibility((View.VISIBLE));
                }
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Cart.class));
            }
        });

        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Wishlist.class));
            }
        });

        popularCategories.setHasFixedSize(true);
        popularCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //ArrayList<>

        nv.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        int gid = menuItem.getGroupId();
        if(id==R.id.msm_cart) {
            startActivity(new Intent(getApplicationContext(), Cart.class));
        } else if(id==R.id.msm_orders) {
            startActivity(new Intent(getApplicationContext(), Orders.class));
        } else if(id==R.id.msm_wishlist) {
            startActivity(new Intent(getApplicationContext(), Wishlist.class));
        } else if(id==R.id.msm_seller_account) {
            startActivity(new Intent(getApplicationContext(), Seller.class));
        } else if(id==R.id.msm_coupons) {
            startActivity(new Intent(getApplicationContext(), Coupons.class));
        } else if(gid==R.id.g2) {
            Intent i = new Intent(getApplicationContext(), SearchActivity.class);
            i.putExtra("id",id);
            startActivity(i);
        } else if(id==R.id.msm_faq) {
            startActivity(new Intent(getApplicationContext(), Faq.class));
        } else if(id==R.id.msm_language) {
            startActivity(new Intent(getApplicationContext(), Language.class));
        } else if(id==R.id.msm_settings) {
            startActivity(new Intent(getApplicationContext(), Settings.class));
        } else if(id==R.id.msm_tandc) {
            startActivity(new Intent(getApplicationContext(), Legal.class));
        } else if(id==R.id.msm_contact) {
            startActivity(new Intent(getApplicationContext(), Contact.class));
        }
        return false;
    }
}
