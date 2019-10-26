package com.example.asus.appinstaller;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.library.bubbleview.BubbleTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyApps extends AppCompatActivity {


    public String currentUserId;
    public String requestType;
    public String idPair;
    public String otherPlayerId;
    Toolbar appbar;
    //private FirebaseListAdapter<ChatMessage> adapter;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    public ChildEventListener messValueListener;
    public ValueEventListener imageValueListener;
    public AlertDialog alertDialog;


    Apps apps;



    public ListView listOfMessage;
    ImageView smilebutton;
    EditText editText;
    ImageView imageView;
    Button messageSend;

    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference databaseReference=firebaseDatabase.getReference(),databaseReferenceForImage=firebaseDatabase.getReference(),messageReference;

    public List<Apps> list_chat = new ArrayList<>();
    public List<String> list = new ArrayList<>();
    public CustomAdapter2 adapter=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_apps);


        mAuth= FirebaseAuth.getInstance();
        currentUser= mAuth.getCurrentUser();
        if(currentUser!=null) currentUserId=currentUser.getUid();

        databaseReferenceForImage=databaseReference.child("Users").child(currentUserId).child("uploadApps");


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        messageReference.removeEventListener(messValueListener);
        databaseReferenceForImage.removeEventListener(imageValueListener);


    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void displayChatMessage() {

        messageReference=databaseReference.child("play").child(idPair).child(idPair).child("message");


        listOfMessage = (ListView)findViewById(R.id.list_of_message);





        messValueListener=messageReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                apps=dataSnapshot.getValue(Apps.class);


                list_chat.add(apps);


                adapter = new CustomAdapter2(list_chat);

                listOfMessage.setAdapter(adapter);


                listOfMessage.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
                listOfMessage.setItemsCanFocus(true);
                listOfMessage.setSelectionAfterHeaderView();
                listOfMessage.setSelection(listOfMessage.getCount()-1);




                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }








    public class CustomAdapter2 extends BaseAdapter {

        List<Apps> list=new ArrayList<>();

        public CustomAdapter2(List<Apps> list){
            this.list=list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {

            return list.get(i).name;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {



                view = getLayoutInflater().inflate(R.layout.activity_my_apps,null);



                String  appName=list.get(i).name;
                String image=list.get(i).iconImage;
                String totalInstall=list.get(i).totalInstalls;
                String uploaDdate=list.get(i).uploadDate;
                ImageView circleImageView=(ImageView) view.findViewById(R.id.imageView2);
                if(image!=null&&!image.equals("default_image")) Picasso.get().load(image).placeholder(R.drawable.azaz12).into(circleImageView);

                TextView appIcon=(TextView) view.findViewById(R.id.appName);
                TextView date=(TextView) view.findViewById(R.id.date);
                TextView installs=(TextView) view.findViewById(R.id.totalInstalls);





                return view;
        }


    }
}
