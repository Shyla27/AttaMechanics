package com.example.attamechanics.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.attamechanics.Fragments.BottomSheetSortFragment;
import com.example.attamechanics.Fragments.BottomSheetSortLeaveFragment;
import com.example.attamechanics.Fragments.BottomSheetTaskFragment;
import com.example.attamechanics.Fragments.LeaveManagement;
import com.example.attamechanics.Fragments.MechDetails.WorkerDetails;
import com.example.attamechanics.R;
import com.example.attamechanics.Utils.SharedPref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class AdminDashboard extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Bundle workerSortBundle, taskSortBundle;
    private Bundle leaveSortBundle;
    private MenuItem menuItem;
    private LinearLayout linearLayout;
    NavigationView navigationView;
    private SharedPref sharedPref;
    private TextView nav_name, nav_email;
    private ImageView imageView;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        db = FirebaseFirestore.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        linearLayout = findViewById(R.id.ll_logout);
        sharedPref = new SharedPref(AdminDashboard.this);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        workerSortBundle = new Bundle();
        leaveSortBundle = new Bundle();
        taskSortBundle = new Bundle();
        menuItem = navigationView.getCheckedItem();
        View header = navigationView.getHeaderView(0);

header.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getBaseContext(), OwnWorkerProfileActivity.class);
        startActivity(intent);
    }
});
        nav_name = header.findViewById(R.id.adminuserName);
        nav_email = header.findViewById(R.id.adminEmailAddress);
        imageView = header.findViewById(R.id.profile_image);

        nav_name.setText(sharedPref.getNAME());
        nav_email.setText(sharedPref.getEMAIL());

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref sharedPref = new SharedPref(getBaseContext());
                sharedPref.logout();
                ExitActivity.exitApplicationAndRemoveFromRecent(AdminDashboard.this);
            }
        });
    }

    private void loadNavViewHeaderImage() {
        db.collection("Employees").document(sharedPref.getEMP_ID()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                if(!documentSnapshot.getData().containsKey("profile_image") || documentSnapshot.getString("profile_image").equals("")) {
                    ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
                    int color = colorGenerator.getRandomColor();
                    TextDrawable textDrawable = TextDrawable.builder().buildRect(String.valueOf(sharedPref.getNAME().charAt(0)),color);
                    imageView.setImageDrawable(textDrawable);
                }
                else {
                    Picasso.get().load(task.getResult().getString("profile_image")).fit().into(imageView);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.driver_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                if(navigationView.getMenu().findItem(R.id.nav_worker).isChecked())
                {
                    BottomSheetSortFragment bottomSheetFragment = new BottomSheetSortFragment();
                    workerSortBundle = initWorkerBundle();  // for remembering the sorting. Otherwise default sorting is always displayed not the selected one
                    bottomSheetFragment.setArguments(workerSortBundle);
                    bottomSheetFragment.show(getSupportFragmentManager(), "bssf");
                }
                else if (navigationView.getMenu().findItem(R.id.nav_leave_management).isChecked())
                {

                    BottomSheetSortLeaveFragment bottomSheetSortLeaveFragment = new BottomSheetSortLeaveFragment();
                    leaveSortBundle = initLeaveSortBundle();  // for remembering the sorting. Otherwise default sorting is always displayed not the selected one
                    bottomSheetSortLeaveFragment.setArguments(leaveSortBundle);
                    bottomSheetSortLeaveFragment.show(getSupportFragmentManager(), "bsslf");
                }
                else if(navigationView.getMenu().findItem(R.id.nav_sup_tasks).isChecked()) {
                    BottomSheetTaskFragment bottomSheetTaskFragment = new BottomSheetTaskFragment();
                    taskSortBundle = initTaskSortBundle();  // for remembering the sorting. Otherwise default sorting is always displayed not the selected one
                    bottomSheetTaskFragment.setArguments(taskSortBundle);
                    bottomSheetTaskFragment.show(getSupportFragmentManager(), "bstf");
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Bundle initTaskSortBundle() {
        if(taskSortBundle.isEmpty())
        {
            taskSortBundle.putBoolean("isSupervisor",true);
            taskSortBundle.putBoolean("dateChip",false);
            taskSortBundle.putBoolean("completedChip",false);
            taskSortBundle.putBoolean("incompleteChip",false);
            return taskSortBundle;
        }
        else
        {
            return taskSortBundle;
        }
    }

    private Bundle initLeaveSortBundle() {
        if(leaveSortBundle.isEmpty())
        {
            leaveSortBundle.putBoolean("nameChip",false);
            leaveSortBundle.putBoolean("dateChip",false);
            leaveSortBundle.putBoolean("maleChip",true);
            leaveSortBundle.putBoolean("femaleChip",true);
            leaveSortBundle.putBoolean("otherChip",true);
//          workerSortBundle.putString("role","all");
//          workerSortBundle.putString("shift","all");
            return leaveSortBundle;
        }
        else
        {
            return leaveSortBundle;
        }
    }

    private Bundle initWorkerBundle() {
        if(workerSortBundle.isEmpty())
        {
            workerSortBundle.putBoolean("nameChip",false);
            workerSortBundle.putBoolean("maleChip",true);
            workerSortBundle.putBoolean("femaleChip",true);
            workerSortBundle.putBoolean("otherChip",true);
//          workerSortBundle.putString("role","all");
//          workerSortBundle.putString("shift","all");
            return workerSortBundle;
        }
        else
        {
            return workerSortBundle;
        }
    }


    public void onWorkerDetailsSortingChanged(Bundle b) {
        workerSortBundle = (Bundle)b.clone();
        WorkerDetails.sort(getBaseContext(),workerSortBundle);
    }

    public void onLeaveSortingChanged(Bundle b) {

        leaveSortBundle = (Bundle)b.clone();
        LeaveManagement.sort(getBaseContext(),leaveSortBundle);
    }

    public void onAdminTaskSortChange(Bundle b) {
    }
}