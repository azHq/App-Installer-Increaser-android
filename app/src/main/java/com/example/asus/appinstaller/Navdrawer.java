package com.example.asus.appinstaller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Navdrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    RecyclerView recyclerView;
    ViewAdapter viewAdapter;
    List<PostDetails> list=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navdrawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        recyclerView=(RecyclerView) findViewById(R.id.timeline);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        viewAdapter=new ViewAdapter(list);
        recyclerView.setAdapter(viewAdapter);

        preparedData();


    }


    public void preparedData(){

        PostDetails postDetails=new PostDetails("azaz","banglasdesh","12.1.2018","how are you");
        list.add(postDetails);
        postDetails=new PostDetails("bablu","banglasdesh","12.1.2018","what are you doing");
        list.add(postDetails);
        postDetails=new PostDetails("Rakib","banglasdesh","12.1.2018","how are you");
        list.add(postDetails);
        postDetails=new PostDetails("akib","banglasdesh","12.1.2018","whare are you going");
        list.add(postDetails);
        postDetails=new PostDetails("mizan","banglasdesh","12.1.2018","how are you");
        list.add(postDetails);
        postDetails=new PostDetails("hridoy","banglasdesh","12.1.2018","i love you");
        list.add(postDetails);

        viewAdapter.notifyDataSetChanged();






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
        getMenuInflater().inflate(R.menu.navdrawer, menu);
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
        else if(id == R.id.log_out){

            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();

            Intent tnt=new Intent(getApplicationContext(),Authentication.class);
            startActivity(tnt);


        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {

            Intent tnt=new Intent(this,My_Profile.class);
            startActivity(tnt);
        } else if (id == R.id.nav_app_upload) {

            Intent tnt=new Intent(this,AppUpload.class);
            startActivity(tnt);

        } else if (id == R.id.nav_earn) {

            Intent tnt=new Intent(this,Earn.class);
            startActivity(tnt);

        } else if (id == R.id.nav_myapps) {

            Intent tnt=new Intent(this,RecycleView.class);
            startActivity(tnt);


        } else if (id == R.id.nav_friends) {

        } else if (id == R.id.nav_developers) {

        } else if (id == R.id.nav_share) {

        }else if (id == R.id.nav_help) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    public  class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.MyViewHolder>{

        List<PostDetails> list;



        public class MyViewHolder extends RecyclerView.ViewHolder{


            View mView;
            public Button btShare,btComment,btLike;
            public TextView userName,countryName,date,details;
            public CircleImageView circleImageView;
            public ImageView circleImageForOnline;

            public MyViewHolder(View itemView) {
                super(itemView);

                //mView=itemView;

                btShare=(Button)itemView.findViewById(R.id.share);
                btComment=(Button)itemView.findViewById(R.id.comment);
                btLike=(Button)itemView.findViewById(R.id.like);

                userName=(TextView) itemView.findViewById(R.id.name);
                countryName=(TextView) itemView.findViewById(R.id.country);
                date=(TextView) itemView.findViewById(R.id.date);
                details=(TextView) itemView.findViewById(R.id.details);


                circleImageView=(CircleImageView) itemView.findViewById(R.id.circleImageView);





            }

            public void setUserName(String name){


                userName.setText(name);
            }

            public void setProfileImage(String image){


                if(image!=null&&!image.equals("default_image")) Picasso.get().load(image).placeholder(R.drawable.azaz12).into(circleImageView);
            }

            public void setActivity(boolean online){


                if(online){
                    circleImageForOnline.setVisibility(View.VISIBLE);
                }
                else {

                    circleImageForOnline.setVisibility(View.INVISIBLE);
                }
            }

        }

        public ViewAdapter(List<PostDetails> list) {

            this.list=list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View itemView =LayoutInflater.from(parent.getContext()).inflate(R.layout.time_line_item,parent,false);

            return new MyViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


            PostDetails postDetails=list.get(position);
            holder.userName.setText(postDetails.userName);
            holder.countryName.setText(postDetails.country);
            holder.date.setText(postDetails.date);
            holder.details.setText(postDetails.postDetails);



        }


        @Override
        public int getItemCount() {

            return list.size();
        }




    }
}
