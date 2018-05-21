package com.example.erajd_2018;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.erajd_2018.EmailPasswordActivity.CHANNEL_ID;
import static com.example.erajd_2018.TestActivity.PICK_IMAGE;

public class ChallengeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private TextView mMailTextView;
    private NavigationView navigationView;
    private FirebaseDatabase database;
    private StorageReference mStorageRef;
    private int lastPosition = -1;
    private Uri picture;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, String> listDataChild;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        DatabaseReference myRef = database.getReference("czelendże");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        mMailTextView = (TextView) header.findViewById(R.id.mailTextView);
        mMailTextView.setText(mAuth.getCurrentUser().getEmail());


        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastPosition != -1
                        && groupPosition != lastPosition) {
                    expListView.collapseGroup(lastPosition);
                }
                lastPosition = groupPosition;
            }
        });


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listDataHeader = new ArrayList<String>();
                listDataChild = new HashMap<String, String>();
                for(DataSnapshot child : dataSnapshot.getChildren() ){
                    listDataHeader.add(child.getKey());
                    listDataChild.put(child.getKey(), child.getValue().toString());
                    listAdapter = new ExpandableListAdapter(getApplicationContext(), listDataHeader, listDataChild, ChallengeActivity.this);
                    expListView.setAdapter(listAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }



    @Override
    public void onResume() {
        int size = navigationView.getMenu().size();
        for (int i = 0; i < size; i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }
        navigationView.getMenu().getItem(1).setChecked(true);
        super.onResume();
    }


    public void selectPicture(){
//        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
//        getIntent.setType("image/* video/*");
//
//        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        pickIntent.setType("image/* video/*");
//
//        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
//        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
//
//        startActivityForResult(chooserIntent, PICK_IMAGE);

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[] {"image/*", "video/*"});
        startActivityForResult(intent, PICK_IMAGE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Cursor returnCursor = getContentResolver().query(selectedImage, null, null, null, null);
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            String fileName = returnCursor.getString(nameIndex);
            picture = selectedImage;



        }
    }

    public void uploadPicture(String teamName){
        if(lastPosition!=-1){
            final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
            mBuilder.setContentTitle("Wysyłanie")
                    .setContentText("Trwa wysyłanie pliku")
                    .setSmallIcon(R.drawable.ic_erajd_logo_simple)
                    .setPriority(NotificationCompat.PRIORITY_LOW);

            StorageMetadata metadata = new StorageMetadata.Builder()
                    .setCustomMetadata("eMail", mAuth.getCurrentUser().getEmail())
                    .build();

            // Issue the initial notification with zero progress
            final int PROGRESS_MAX = 100;
            mBuilder.setProgress(PROGRESS_MAX, 0, false);
            notificationManager.notify(1, mBuilder.build());

            StorageReference storageRef = mStorageRef.child(teamName + "/" + listDataHeader.get(lastPosition));


            storageRef.putFile(picture, metadata)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(), "Wysłano plik", Toast.LENGTH_SHORT).show();
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getApplicationContext(), "nie udało się", Toast.LENGTH_SHORT).show();
                            // Handle unsuccessful uploads
                            // ...
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            int currentProgress = (int) progress;
                            mBuilder.setProgress(PROGRESS_MAX, currentProgress, false);
                            notificationManager.notify(1, mBuilder.build());

                            if(currentProgress==PROGRESS_MAX){
                                mBuilder.setContentText("Zakończono wysyłanie pliku")
                                        .setProgress(0,0,false);
                                notificationManager.notify(1, mBuilder.build());
                            }

                        }
                    }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                    System.out.println("Upload is paused");
                }
            });
            expListView.collapseGroup(lastPosition);
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(this, LoggedActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global, menu);
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
        } else if (id == R.id.action_signOut) {
            mAuth.signOut();
            Intent intent = new Intent(this, EmailPasswordActivity.class);;
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_plan) {
            Intent intent = new Intent(this, PlanActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        } else if (id == R.id.nav_challenge) {
            Intent intent = new Intent(this, ChallengeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        } else if (id == R.id.nav_volleyball) {
            Intent intent = new Intent(this, VolleyballActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        } else if (id == R.id.nav_football) {
            Intent intent = new Intent(this, FootballActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        } else if (id == R.id.nav_contact) {
            Intent intent = new Intent(this, ContactActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        } else if (id == R.id.nav_location) {
            Intent intent = new Intent(this, LocationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
