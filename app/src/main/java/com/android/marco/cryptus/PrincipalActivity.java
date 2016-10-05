package com.android.marco.cryptus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.marco.cryptus.Dropbox.LoginDBoxActivity;
import com.android.marco.cryptus.Dropbox.MainDBoxActivity;
import com.dropbox.core.android.Auth;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by Marco Mancuso on 16/06/2016.
 */
public class PrincipalActivity extends Activity {

    class NavItem {
        String mTitle;
        String mSubtitle;
        int mIcon;


        public NavItem(String title, String subtitle, int icon) {
            mTitle = title;
            mSubtitle = subtitle;
            mIcon = icon;
        }
    }

    class DrawerListAdapter extends BaseAdapter {

        Context mContext;
        ArrayList<NavItem> mNavItems;

        public DrawerListAdapter(Context context, ArrayList<NavItem> navItems) {
            mContext = context;
            mNavItems = navItems;
        }

        @Override
        public int getCount() {
            return mNavItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mNavItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.drawer_item, null);
            }
            else {
                view = convertView;
            }

            TextView titleView = (TextView) view.findViewById(R.id.title);
            TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
            ImageView iconView = (ImageView) view.findViewById(R.id.icon);
            titleView.setText( mNavItems.get(position).mTitle );
            subtitleView.setText( mNavItems.get(position).mSubtitle );
            Picasso.with(this.mContext).load(R.drawable.ic_launcher_off).fit().centerCrop().into(iconView);

            return view;
        }
    }

    private static String TAG = PrincipalActivity.class.getSimpleName();

    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private TextView mTextViewP;
    private TextView mTextViewEM;
    private ImageView mImageView;
    private ImageView principal;
    private TextView instr;

    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        setContentView(R.layout.activity_principal);
        getWindow().setAllowEnterTransitionOverlap(false);
        mTextViewP = (TextView) findViewById(R.id.userName);
        mTextViewP.setText(LoginActivity.nome);
        mTextViewEM = (TextView) findViewById(R.id.desc);
        mTextViewEM.setText(LoginActivity.email);
        mImageView = (ImageView) findViewById(R.id.avatar);
        principal = (ImageView) findViewById(R.id.principalPic);
        instr = (TextView) findViewById(R.id.instructions);
        StringBuffer s = new StringBuffer("<--  ");
        s.append(instr.getText().toString());
        instr.setText(s);
        Picasso.with(this).load(LoginActivity.profileImage).into(mImageView);
        mNavItems.add(new NavItem("Password Center", "Create, browse and delete your passwords", R.drawable.ic_launcher_off));
        mNavItems.add(new NavItem("On-fly", "Generate one-shot password", R.drawable.ic_launcher_off));
        mNavItems.add(new NavItem("Strongness", "Check the strongness of your password", R.drawable.ic_launcher_off));
        mNavItems.add(new NavItem("Crack Station", "Has your password already been cracked? Take a look here", R.drawable.ic_launcher_off));
        mNavItems.add(new NavItem("DropBox", "Upload your passwords to your DropBox account", R.drawable.ic_launcher_off));
        mNavItems.add(new NavItem("Info", "Get some infos about the app", R.drawable.ic_launcher_off));
        mNavItems.add(new NavItem("Credits", "Get to know about me", R.drawable.ic_launcher_off));

        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        // Populate the Navigtion Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);
        mDrawerLayout.openDrawer(mDrawerPane);
        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }

            private void selectItemFromDrawer(int position) {
                final Explode explode;
                //mDrawerLayout.closeDrawer(mDrawerPane);
                explode = new Explode();
                getWindow().setExitTransition(explode);
                switch(position) {
                    case 0:
                        Intent myIntent0 = new Intent(PrincipalActivity.this, DbActivity.class);
                        startActivity(myIntent0);
                        finish();
                        break;
                    case 1:
                        Intent myIntent1 = new Intent(PrincipalActivity.this, HashActivity.class);
                        startActivity(myIntent1);
                        finish();
                        break;
                    case 2:
                        Intent myIntent2 = new Intent(PrincipalActivity.this, CheckStrongnessActivity.class);
                        startActivity(myIntent2);
                        finish();
                        break;
                    case 3:
                        Intent myIntent3 = new Intent(PrincipalActivity.this, WebActivity.class);
                        startActivity(myIntent3);
                        finish();
                        break;
                    case 4:
                        Intent myIntent4 = new Intent(PrincipalActivity.this, LoginDBoxActivity.class);
                        startActivity(myIntent4);
                        finish();
                        break;
                    case 5:
                        Intent myIntent5 = new Intent(PrincipalActivity.this, InfoActivity.class);
                        startActivity(myIntent5);
                        finish();
                        break;

                    case 6:
                        Intent myIntent6 = new Intent(PrincipalActivity.this, CreditsActivity.class);
                        startActivity(myIntent6);
                        finish();
                        break;
                }

            }
        });
    }


    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        mDrawerLayout.closeDrawer(mDrawerPane);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

}
