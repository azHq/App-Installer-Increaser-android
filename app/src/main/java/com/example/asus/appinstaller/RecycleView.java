package com.example.asus.appinstaller;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleView extends AppCompatActivity {


    RecyclerView recyclerView;
    ViewAdapter viewAdapter;
    List<PostDetails> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);

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
