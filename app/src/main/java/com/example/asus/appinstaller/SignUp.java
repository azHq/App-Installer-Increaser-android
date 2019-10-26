package com.example.asus.appinstaller;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.github.library.bubbleview.BubbleTextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.security.AccessController.getContext;

public class SignUp extends AppCompatActivity {

    Toolbar appbar;
    String userName,password,email,userId;
    EditText etUserName,etPassword,etEmail;
    ProgressDialog progressDialog;
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    public Speak speak;
    boolean flag=true,flag2=true;

    String thumbDownloadUrl;
    String currentUserUID;





    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference databaseReference=firebaseDatabase.getReference(),rankingReference;
    StorageReference storageReference,storageRefForThum;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        appbar=(Toolbar)findViewById(R.id.app_bar);

        speak=new Speak(getApplicationContext());

        appbar.setTitle("Sign Up");
        etUserName=(EditText)findViewById(R.id.editText2);
        etPassword=(EditText)findViewById(R.id.pass);
        etEmail=(EditText)findViewById(R.id.editText);
        progressDialog=new ProgressDialog(this);

        etUserName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(flag){
                    etUserName.setText("");
                    flag=false;

                }


                return false;
            }

        });


        etEmail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(flag2){
                    etEmail.setText("");
                    flag2=false;

                }


                return false;
            }
        });

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mAuth=FirebaseAuth.getInstance();


        storageRefForThum= FirebaseStorage.getInstance().getReference().child("thumb_Image");



    }

    public void signUp(View v){

        userName=etUserName.getText().toString();
        email=etEmail.getText().toString();
        password=etPassword.getText().toString();

        if(userName.length()!=0&&email.length()!=0&&password.length()!=0){



            progressDialog.setTitle("please wait.......!");
            progressDialog.show();
            registration(userName,email,password);


        }
        else {



            if(getContext()!=null&&getApplicationContext()!=null) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SignUp.this);


                builder.setTitle("Authentication failed.");
                builder.setMessage("Please fillup the requirements.Press ok to exit");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();

                    }

                }).create();

                builder.show();
            }
        }


    }

    public void registration(final String userName, final String email, final String password){


        System.out.println(userName+email+password);



        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            progressDialog.dismiss();

                            speak.toSpeak(userName+" You are welcome to my game");

                            if(getContext()!=null&&getApplicationContext()!=null) {

                                AlertDialog.Builder builder2 = new AlertDialog.Builder(SignUp.this);
                                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View view = inflater.inflate(R.layout.welcomeview, null);
                                BubbleTextView text_message = (BubbleTextView) view.findViewById(R.id.text_message);
                                CircleImageView circleImageView = (CircleImageView) view.findViewById(R.id.circleImageView);

                                text_message.setText(userName + " You are welcome to my game");
                                builder2.setView(view);

                                final AlertDialog alertDialog = builder2.create();
                                flag=true;
                                flag2=true;

                                alertDialog.show();

                                final Timer timer2 = new Timer();
                                timer2.schedule(new TimerTask() {
                                    public void run() {
                                        alertDialog.dismiss();
                                        timer2.cancel();

                                        newActivity();
                                    }
                                }, 3000);
                            }









                        } else {


                            /*etUserName.setText("");
                            etEmail.setText("");
                            etPassword.setText("");*/
                            progressDialog.dismiss();

                            if(getContext()!=null&&getApplicationContext()!=null) {
                                Log.w("new user", "createUserWithEmail:failure", task.getException());
                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SignUp.this);

                                builder.setTitle("Authentication failed.");
                                builder.setMessage("Invalid email_id or passwod");
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                            /*Intent t=new Intent(getApplicationContext(),SholoGuti.class);
                            startActivity(t);*/
                                        // finish();

                                    }

                                }).create();

                                builder.show();
                            }
                        }


                    }
                });


    }


    public void newActivity(){

        Log.d("new user", "createUserWithEmail:success");
        FirebaseUser currentUser= mAuth.getCurrentUser();

        Bitmap bmp =  BitmapFactory.decodeResource(getResources(), R.drawable.azaz12);//your image
        ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, bYtE);
        bmp.recycle();
        byte[] byteArray = bYtE.toByteArray();
        //String imageFile = Base64.encodeToString(byteArray, Base64.DEFAULT);


        currentUserUID=currentUser.getUid();
        userId=currentUser.getUid();
        //StorageReference filePath=storageReference.child(currentUserUID+".jpg");
        final StorageReference thumbPath=storageRefForThum.child(currentUserUID+".jpg");

        databaseReference.child("Users").child(userId).child("thumb_image").setValue("default_image");



        // final byte[] thumByte=byteArray.toByteArray();


        UploadTask uploadTask = thumbPath.putBytes(byteArray);


        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {


            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> thumb_task) {




                thumbPath.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        thumbDownloadUrl=task.getResult().toString();

                        DatabaseReference imageReferenceforThumb = databaseReference.child("Users").child(currentUserUID).child("thumb_image");

                        imageReferenceforThumb.setValue(thumbDownloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {



                                }

                            }
                        });

                    }
                });




            }
        });










        String deviceToker= FirebaseInstanceId.getInstance().getToken();
        databaseReference.child("Users").child(userId).child("userName").setValue(userName);
        databaseReference.child("Users").child(userId).child("email").setValue(email);
        databaseReference.child("Users").child(userId).child("password").setValue(password);
        databaseReference.child("Users").child(userId).child("Activity").setValue(true);
        databaseReference.child("Users").child(userId).child("profile_image").setValue("default_image");
        // databaseReference.child("Users").child(userId).child("thumb_image").setValue("default_image");
        databaseReference.child("Users").child(userId).child("installs").setValue(0);
        databaseReference.child("Users").child(userId).child("uninstalls").setValue(0);
        databaseReference.child("Users").child(userId).child("earn").setValue(0);
        databaseReference.child("Users").child(userId).child("country").setValue("bangladesh");





        Intent t=new Intent(getApplicationContext(),MainView.class);
        startActivity(t);
        finish();


    }
}
