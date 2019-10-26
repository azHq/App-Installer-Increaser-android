package com.example.asus.appinstaller;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllDevelopers extends AppCompatActivity {


    private Toolbar toolbar;
    private View myMainView;
    public RecyclerView friendList;
    private String currentUsersId;

    FirebaseAuth mAuth;
    DatabaseReference databaseReference,requestReference,notificationReference,requestListenerReference,userReference;
    public ValueEventListener userReferenceListener,requestListener;
    FirebaseUser firebaseUser;
    public  int currentUserPosition;
    private ProgressDialog loadingbar,gameStart,requestAccept;

    public String senderName,receiverName;
    public String id;
    public boolean notificationDisable;
    public int removeItem=0,count=0,count2=0;
    public boolean button=false;
    int currentVisiblePosition=0;

    private final String RECYCLER_POSITION_KEY = "recycler_position";
    private int mPosition = RecyclerView.NO_POSITION;
    private LinearLayoutManager mLayoutManager;
    private static Bundle mBundleState;

    Parcelable state;

    public AllDevelopers() {

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_developers);

        friendList=(RecyclerView) findViewById(R.id.friend);

        mAuth=FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser();
        if(firebaseUser!=null) currentUsersId=firebaseUser.getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");
        notificationReference=FirebaseDatabase.getInstance().getReference().child("Notifications");
        requestReference=FirebaseDatabase.getInstance().getReference();

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        friendList.setLayoutManager(mLayoutManager);



        loadingbar=new ProgressDialog(getApplicationContext());

        gameStart=new ProgressDialog(getApplicationContext());

        if(savedInstanceState==null){

            startView();

        }
        else{

        }









    }


    public void startView() {





        final FirebaseRecyclerAdapter<Friends,FriendsViewHolder> friendsView=
                new FirebaseRecyclerAdapter<Friends, FriendsViewHolder>
                        (

                                Friends.class,
                                R.layout.all_users_display2,
                                FriendsViewHolder.class,
                                databaseReference

                        )
                {
                    @Override
                    public int getItemViewType(int position) {
                        return super.getItemViewType(position);
                    }

                    @Override
                    protected void populateViewHolder(FriendsViewHolder viewHolder, final Friends model, final int position) {

                        //boolean profile=true;







                        //if(model.getThumb_image()!=null&&model.getThumb_image().equalsIgnoreCase("default_image")) profile=false;
                        /* if(!currentUsersId.equals(getRef(position).getKey())){*/

                        viewHolder.setUserName(model.getUserName());
                        String image=model.getThumb_image();


                        if(image!=null&&!image.equals("default_image")) Picasso.get().load(image).placeholder(R.drawable.azaz12).into(viewHolder.circleImageView);
                        viewHolder.setActivity(model.isActivity());

              /* }
                else if(removeItem==0&&currentUsersId.equals(getRef(position).getKey())){

                    viewHolder.setUserName(model.getUserName());
                    String image=model.getThumb_image();

                    if(image!=null&&!image.equals("default_image")) Picasso.get().load(image).placeholder(R.drawable.azaz12).into(viewHolder.circleImageView);
                    viewHolder.setActivity(model.isActivity());

                    viewHolder.mView.setVisibility(View.GONE);
                    viewHolder.mView.setVisibility(View.INVISIBLE);
                    viewHolder.mView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                    currentUserPosition=position;
                    removeItem=1;





                }
                else if(currentUsersId.equals(getRef(position).getKey())){

                    viewHolder.setUserName(model.getUserName());
                    String image=model.getThumb_image();

                    if(image!=null&&!image.equals("default_image")) Picasso.get().load(image).placeholder(R.drawable.azaz12).into(viewHolder.circleImageView);
                    viewHolder.setActivity(model.isActivity());

                }*/


                        viewHolder.viewProfile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                id=getRef(position).getKey();

                                Intent t=new Intent(getApplicationContext(),UsersProfile.class);
                                t.putExtra("ID",id);
                                startActivity(t);
                            }
                        });



                        viewHolder.sendRequest.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                id=getRef(position).getKey();

                                button=true;




                                count=0;
                                count2=0;

                                userReference=requestReference.child("Users");
                                userReferenceListener=requestReference.child("Users").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        senderName=dataSnapshot.child(currentUsersId).child("userName").getValue().toString();
                                        receiverName=dataSnapshot.child(id).child("userName").getValue().toString();





                                        if (dataSnapshot.child(currentUsersId).child("disableNotification").child(id).getValue() != null) {
                                            notificationDisable =false;



                                        } else {

                                            notificationDisable =true;
                                        }













                                        if(notificationDisable&&button&&!id.equalsIgnoreCase(currentUsersId)){

                                            button=false;

                                            loadingbar.setTitle("Sending friend request");
                                            loadingbar.setMessage("Please wait.......");
                                            loadingbar.show();

                                            requestReference.child("Allrequest").removeValue();
                                            requestReference.child("Allrequest").child(currentUsersId).child(id).child("request_type").setValue("send")
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {


                                                            if(task.isSuccessful()){
                                                                requestReference.child("Allrequest").child(id).child(currentUsersId).child("request_type").setValue("receive")
                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                                if(task.isSuccessful()){

                                                                                    requestReference.child("Allrequest").child(id).child("sender").setValue(currentUsersId);

                                                                                    HashMap<String,String> notificationsData=new HashMap<String,String>();
                                                                                    notificationsData.put("from",currentUsersId);
                                                                                    notificationsData.put("type","request");

                                                                                    if(notificationReference.child(id)!=null) notificationReference.child(id).removeValue();
                                                                                    notificationReference.child(id).push().setValue(notificationsData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {

                                                                                            if(task.isSuccessful()){

                                                                                                loadingbar.dismiss();



                                                                                                if(getApplicationContext()!=null) Toast.makeText(getApplicationContext(),"Request sent successfully",Toast.LENGTH_LONG).show();

                                                                                                // if(requestReference.child("play").child(currentUsersId+":"+id).child("state")!=null) requestReference.child("play").child(currentUsersId+":"+id).child("state").removeValue();
                                                                                                requestReference.child("play").child(currentUsersId+":"+id).child("state").setValue("pending");
                                                                                                requestReference.child("play").child(currentUsersId+":"+id).child("time").setValue(System.currentTimeMillis());





                                                                                                requestListenerReference=requestReference.child("play");
                                                                                                requestListener=requestReference.child("play").addValueEventListener(new ValueEventListener() {
                                                                                                    @Override
                                                                                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                                                                                        String state=" ";
                                                                                                        if(dataSnapshot.child(currentUsersId+":"+id).child("state").getValue()!=null) state=dataSnapshot.child(currentUsersId+":"+id).child("state").getValue().toString();




                                                                                                        if(state.equals("accept")&&count==0){

                                                                                                            count=1;



                                                                                                            if(getApplicationContext()!=null) {
                                                                                                                AlertDialog.Builder dialong9 = new AlertDialog.Builder(getApplicationContext());

                                                                                                                dialong9.setMessage(receiverName + " accept your request");
                                                                                                                dialong9.setPositiveButton("Play", new DialogInterface.OnClickListener() {
                                                                                                                    @Override
                                                                                                                    public void onClick(DialogInterface dialogInterface, int e) {

                                                                                                                        requestReference.child("play").child(currentUsersId + ":" + id).child("state").setValue("start");
                                                                                                                        requestReference.child("play").child(currentUsersId + ":" + id).child("time").setValue(System.currentTimeMillis());

                                                                                                                        Toast.makeText(getApplicationContext(), "Request accept", Toast.LENGTH_LONG).show();



                                                                                                                    }
                                                                                                                });

                                                                                                                dialong9.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                                                                                    @Override
                                                                                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                                                                                        dialogInterface.dismiss();

                                                                                                                    }
                                                                                                                }).create();

                                                                                                                dialong9.show();
                                                                                                            }


                                                                                                        }





                                                                                                        else if(state.equals("cancel")&&count2==0){

                                                                                                            count2=1;

                                                                                                            if(getApplicationContext()!=null) {

                                                                                                                AlertDialog.Builder dialong14 = new AlertDialog.Builder(getApplicationContext());
                                                                                                                dialong14.setMessage(receiverName + " cancel your request.");
                                                                                                                dialong14.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                                                                    @Override
                                                                                                                    public void onClick(DialogInterface dialogInterface, int e) {

                                                                                                                        dialogInterface.dismiss();

                                                                                                                    }
                                                                                                                }).create();

                                                                                                                dialong14.show();

                                                                                                                Toast.makeText(getApplicationContext(), "Request is not accept", Toast.LENGTH_LONG).show();
                                                                                                                requestReference.child("play").child(currentUsersId + ":" + id).child("state").setValue("close");
                                                                                                                requestReference.child("play").child(currentUsersId+":"+id).child("time").setValue(System.currentTimeMillis());
                                                                                                            }
                                                                                                        }




                                                                                                    }

                                                                                                    @Override
                                                                                                    public void onCancelled(DatabaseError databaseError) {

                                                                                                    }
                                                                                                });




                                                                                            }
                                                                                            else {
                                                                                                loadingbar.dismiss();
                                                                                            }

                                                                                        }
                                                                                    });




                                                                                }
                                                                                else{

                                                                                    loadingbar.dismiss();
                                                                                }

                                                                            }
                                                                        });
                                                            }
                                                        }
                                                    });


                                        }
                                        else if(button){

                                            button=false;

                                            loadingbar.dismiss();



                                            System.out.println("receiver33 "+receiverName+" notification "+notificationDisable);

                                            android.app.AlertDialog.Builder builder9 = new android.app.AlertDialog.Builder(getApplicationContext());
                                            builder9.setMessage( "You can't send requset to "+receiverName);
                                            builder9.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {




                                                    dialogInterface.dismiss();



                                                }

                                            });

                                            builder9.create();
                                            builder9.show();

                                        }


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }

                                });


                            }
                        });

                    }
                };





        friendsView.notifyItemRemoved(currentUserPosition);
        friendsView.notifyDataSetChanged();
        friendsView.setHasStableIds(true);
        friendList.hasFixedSize();
        friendList.setAdapter(friendsView);





    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(RECYCLER_POSITION_KEY,  mLayoutManager.findFirstCompletelyVisibleItemPosition());

        super.onSaveInstanceState(outState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {


        if (savedInstanceState.containsKey(RECYCLER_POSITION_KEY)) {
            mPosition = savedInstanceState.getInt(RECYCLER_POSITION_KEY);
            if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
            // Scroll the RecyclerView to mPosition
            friendList.smoothScrollToPosition(mPosition);
        }
    }

    @Override
    public void onResume() {
        super.onResume();


        if (mBundleState != null) {
            mPosition = mBundleState.getInt(RECYCLER_POSITION_KEY);
            if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
            // Scroll the RecyclerView to mPosition
            friendList.smoothScrollToPosition(mPosition);
        }


    }

    @Override
    public void onPause() {
        super.onPause();
        mBundleState = new Bundle();
        mPosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
        mBundleState.putInt(RECYCLER_POSITION_KEY, mPosition);


    }



    public static class FriendsViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        public Button sendRequest,viewProfile;
        public TextView userName;
        public CircleImageView circleImageView;
        public ImageView circleImageForOnline;

        public FriendsViewHolder(View itemView) {
            super(itemView);

            mView=itemView;

            sendRequest=(Button)mView.findViewById(R.id.sendRequest);
            viewProfile=(Button)mView.findViewById(R.id.viewProfile);
            sendRequest=(Button)mView.findViewById(R.id.sendRequest);
            viewProfile=(Button)mView.findViewById(R.id.viewProfile);
            userName=(TextView) mView.findViewById(R.id.username);
            circleImageView=(CircleImageView) mView.findViewById(R.id.allUsersProfileImage);
            circleImageForOnline=(ImageView) mView.findViewById(R.id.onlineStatus);





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




    @Override
    public void onDestroy() {
        super.onDestroy();

        if(requestListenerReference!=null&&requestListener!=null)requestListenerReference.removeEventListener(requestListener);
        if(userReference!=null&&userReferenceListener!=null) userReference.removeEventListener(userReferenceListener);
    }


}
