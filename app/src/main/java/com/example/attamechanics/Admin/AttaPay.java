package com.example.attamechanics.Admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.attamechanics.MainActivity;
import com.example.attamechanics.Notifications;
import com.example.attamechanics.R;
import com.example.attamechanics.Users.NearbyGarages;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Objects;

import javax.security.auth.Subject;

public class AttaPay extends AppCompatActivity {

//    String[] mobileArray = {"A1","A2","B1","B2",
//            "C1","C2","D1","D2", "E1", "E2", "F1", "F2"};

    AppCompatButton aa, ab, ba, bc, cc , cb , da,db, ea, eb, fa, fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atta_pay);


        aa= findViewById(R.id.aa);
        ab= findViewById(R.id.ab);
        ba= findViewById(R.id.ba);
        bc= findViewById(R.id.bb);
        cc = findViewById(R.id.c1);
        cb = findViewById(R.id.c2);
        da = findViewById(R.id.da);
        db = findViewById(R.id.db);
        ea = findViewById(R.id.ea);
        eb = findViewById(R.id.eb);
        fa = findViewById(R.id.fa);
        fb = findViewById(R.id.fb);
        
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Car Types");
        aa.setOnClickListener(view -> showAlertDialog());
        ab.setOnClickListener(view -> showAlertDialogAB());
        ba.setOnClickListener(view -> showAlertDialogBA());

        bc.setOnClickListener(view -> showAlertDialogBB());
        cb.setOnClickListener(view -> showAlertDialogCB());
        cc.setOnClickListener(view -> showAlertDialogCA());
        db.setOnClickListener(view -> showAlertDialogDB());
        da.setOnClickListener(view -> showAlertDialogDA());
        eb.setOnClickListener(view -> showAlertDialogEB());
        ea.setOnClickListener(view -> showAlertDialogEA());
        fa.setOnClickListener(view -> showAlertDialogFA());
        fb.setOnClickListener(view -> showAlertDialogFB());
        
   }

    private void showAlertDialogEB() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AttaPay.this);
        alertDialog.setTitle("E2 type of cars");
        String[] items = {
                "Chevrolet Montana","Diahastu Hijet", "Ford Ranger","Ford Transit","Honda StepWagon","Mazda Bongo","Mazda Premacy/S","Nissan Elgrand","Nissan Caravan","Nissan Hardbody","Nissan Navara","Nissan NV 200/Vanette","Nissan Serena","Nissan Titan","Toyota Alphard",
                "Toyota Dyna","Toyota Estima","Toyota Hilux","Toyota Isis","Toyota Land Cruiser Pick-up","Toyota Noah","Toyota Regius Van","Toyota Sienta",
                "Toyota Succeed","Toyota Tacoma","Toyota TownAce","Toyota Toyoace","Toyota Tundra","Toyota Vellfire","Toyota TownAce/LightAce", "Toyota Voxy", "Toyota Wish",
                "Mitsubishi Delica","Mitsubishi L200/Triton", "Subaru WRX/STI","Toyota Supra"
        };
        alertDialog.setItems(items, (dialogInterface, i) -> {

        });

        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    private void showAlertDialogDA() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AttaPay.this);
        alertDialog.setTitle("D1 type of cars");
        String[] items = {
                "Mercedes Trucks"
        };
        alertDialog.setItems(items, (dialogInterface, i) -> {

        });

        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    private void showAlertDialogDB() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AttaPay.this);
        alertDialog.setTitle("D2 type of cars");
        String[] items = {
                "Hino Profia", "Hino Dutro", "Hino Ranger","Isuzu Buses","Isuzu F-Series/Lorry","Isuzu F-series/Foward","Isuzu Prime Mover/Giga",
"Mitsubishi Canter","Nissan Civilian","Toyota Coaster","UD Condor","UD croner","UD Kazet","UD Kuzer","UD Quester","UD Quon"
        };
        alertDialog.setItems(items, (dialogInterface, i) -> {

        });

        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    private void showAlertDialogCA() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AttaPay.this);
        alertDialog.setTitle("C1 type of cars");
        String[] items = {
                "Audi Q7",  "Audi Q8",  "BMW X5",  "BMW X6",  "BMW X7",  "Jeep Wagoneer",  "Jeep Wranger",  "Land Rover Defender",  "Land Rover Discovery",  "Land Rover Range Rover",
                "Lexus GX",  "Lexus LX",  "Mercedes Benz EQC",  "Mercedes Benz G-Class",  "Mercedes Benz GLE",  "Mercedes Benz GLS",  "Mercedes Benz X-Class",  "Porsche Cayenne",
                "Volkswagen Amarok",  "Volkswagen Toureg",  "Volvo XC90"
        };
        alertDialog.setItems(items, (dialogInterface, i) -> {

        });

        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    private void showAlertDialogCB() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AttaPay.this);
        alertDialog.setTitle("C2 type of cars");
        String[] items = {
                "Chevrolet cOLORADO/s-10", "Chevrolet Blazer", "Chevrolet Silverado", "Chevrolet Suburban", "Chevrolet Tahoe", "Chevrolet Trailblazer", "Chevrolet Traverse",
                "Ford Bronco","Ford Endeavour","Ford Everest","Ford Rapture","Ford Wildtrack","Ford Troller T4", "Isuzu mu-X", "Mazda CX-8", "Mazda CX-9",
                "Mitsubishi Pajero/Mini/Io","Nissan Patrol/ Safari", "Toyota 4Runner","Toyota FJ Cruiser", "Toyota Fortuner", "Toyota Land Cruiser Prado","Toyota Land Cruiser" ,"Toyota Sequoia",

        };
        alertDialog.setItems(items, (dialogInterface, i) -> {

        });

        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }


    private void showAlertDialogEA() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AttaPay.this);
        alertDialog.setTitle("E1 type of cars");
        String[] items = {
       "Mercedes Benz v-Class", "Mercedes Benz Sprinter","Volkswagen Transporter",
        };
        alertDialog.setItems(items, (dialogInterface, i) -> {

        });

        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    private void showAlertDialogFA() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AttaPay.this);
        alertDialog.setTitle("F2 type of cars");
        String[] items = {
                "Audi Q3", "Audi Q5", "BMW X1","BMW X2","BMW X3","BMW X4","Chrysler Pacifica","Chrysler Voyager Minivan","Jaguar E Pace","Jaguar F Pace","Jeep Compass","Jeep Gladiator",
                "Jeep Patriot","Jeep Renegade","Land Rover Range Rover","Land Lover Freelander","LandLover Discovery Sport","Lexus NX","Lexus RX","Lexus UX","Mercedes Benz GLA","Mercedes Benz GLB",
                "Mercedes Benz GLC",
               "Peugeot 3008","Peugeot 5008","Porsche Macan","Volkswagen Tiguan","Volvo C40", "Volvo XC40", "Volvo XC60","Volvo XC70"
        };
        alertDialog.setItems(items, (dialogInterface, i) -> {

        });

        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    private void showAlertDialogFB() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AttaPay.this);
        alertDialog.setTitle("F2 type of cars");
        String[] items = {
                "Chevrolet Captiva","Chevrolet Equinox","Chevrolet Orlando","Daihastu Rocky","Ford EcoSport", "Ford Endura", "Ford Escape", "Ford Figo", "Ford Ka", "Honda CRV",
                "Honda Vezel/HRV/XRV","Mazda CX-3", "Mazda CX-30","Mazda CX-5","Mazda MPV",
                "Mitsubishi Outlander", "Mitsubishi RVR","Nissan Dualis/Qashqai","Nissan Murano", "Nissan X-Trail",
                "Subaru Forester","Subaru Outback", "Subaru XV","Suzuki Escudo/ Vitara","Suzuki Jimny","Toyota C-HR", "Toyota Corolla Cross", "Toyota Harrier", "Toyota Highlander",
                "Toyota Kluger", "Toyota RAV 4" ,"Toyota Rush", "Toyota Vanguard", "Toyota Venza"
        };
        alertDialog.setItems(items, (dialogInterface, i) -> {

        });

        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }


    private void showAlertDialogBB() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AttaPay.this);
        alertDialog.setTitle("B2 type of cars");
        String[] items = {
                "Chevrolet Corvette","Ford Falcon","Ford Mustang","Mitsubishi Eclipse","Mitsubishi Evo", "Subaru WRX/STI","Toyota Supra"
        };
        alertDialog.setItems(items, (dialogInterface, i) -> {

        });

        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }
    private void showAlertDialogBA() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AttaPay.this);
        alertDialog.setTitle("B1 type of cars");
        String[] items = {
                "Audi A5", "Audi A7", "Audi R8","Audi TT", "BMW 4 series", "BMW 6 series", "BMW 8 series", "BMW i8", "BMW M2","BMW M3","BMW M4","BMW M5", "BMW M6","BMW Z4","Jaguar F Type",
                "Jaguar XK", "Lexus LC" , "Lexus RC", "Mercedes-Benz AMG GT", "Mercedes-Benz CLS" , "Mercedes-Benz SL", "Peugeot RCZ", "Porsche 718", "Porsche 911"
        };
        alertDialog.setItems(items, (dialogInterface, i) -> {

        });

        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    private void showAlertDialogAB() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AttaPay.this);
        alertDialog.setTitle("A2 type of cars");
        String[] abitems = { "Chevrolet Cavalier", "Chevrolet Sonic/Aveo" ,"Chevrolet Beat Hatchback", "Chevrolet Cruze", "Chevrolet Joy Plus", "Chevrolet Malibu", "Chevrolet Optra", "Chevrolet Sail",
                "Chevrolet Trax", "Daihastu Mira", "Daihastu Move", "Daihastu Terios", "Ford F-150", "Ford Fiesta", "Ford Focus", "Ford Mondeo", "Ford S-Max", "Honda Civic", "Honda Fit",
                "Honda Freed", "Honda Insight", "Honda Jade", "Honda Jazz", "Mazda Atenza/6", "Mazda Axela/3", "Mazda Demio", "Mazda Verisa", "Mitsubishi Colt Plus", "Mitsubishi LanceR", "Mitsubishi Mirage"
                , "Nissan AD Van", "Nissan Bluebird Saphyl", "Nissan Cube", "Nissan Fuga", "Nissan Juke","Nissan Lafesta","Nissan March","Nissan Note", "Nissan Skyline","Nissan Teana","Nissan Tiida"
                , "Nissan Wingroad", "Subaru Exiga", "Subaru Impreza", "Subaru Legacy", "Subaru Levorg", "Suzuki Baleno Hatchback", "Suzuki Ciaz", "Suzuki Swift", "Suzuki Wagon R", "Toyota 110", "Toyota Allion",
                "Toyota Auris", "Toyota Avensis", "Toyota bB", "Toyota Belta", "Toyota Blade","Toyota Camry","Toyota Corolla Rumion","Toyota Crown","Toyota Fielder", "Toyota IST", "Toyota Mark X", "Toyota Nze",
                "Toyota Passo", "Toyota Prius","Toyota Probox","Toyota Starlet", "Toyota Vios", "Toyota Vitz", "Toyota Yaris",

        };
        alertDialog.setItems(abitems, (dialogInterface, i) -> {

        });
        AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.setCanceledOnTouchOutside(false);
        alertDialog1.show();
    }


    private void showAlertDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AttaPay.this);
        alertDialog.setTitle("A1 type of cars");
        String[] items = {
                "Audi A3", "Audi A4", "Audi A6","Audi A8", "BMW 1 series", "BMW 2 series", "BMW 3 series", "BMW 7 series", "BMW i3", "Chrysler 300","Jaguar XF", "Jaguar S Type", "Jaguar X Type",
                "Lexus CT", "Lexus ES", "Lexus GS", "Lexus IS", "Lexus LS" , "Mercedes Benz  A-Class ", "Mercedes Benz  B-Class ", "Mercedes Benz  C-Class ","Mercedes Benz  CLA ", "Mercedes Benz E-class",
                "Mercedes Benz  S-Class ", "Peugeot 208", "Peugeot 308", "Peugeot 404", "Peugeot 405", "Peugeot 406", "Peugeot 504", "Peugeot 505",
                "Peugeot 506", "Porsche Panamera", "Volkswagen Golf", "Volkswagen Jetta", "Volkswagen Passat" , "Volkswagen Polo & Cross Polo", "Volkswagen Scirocco", "Volvo V90"
                , "Volvo S60", "Volvo V40", "Volvo V60"
        };
        alertDialog.setItems(items, (dialogInterface, i) -> {

        });

        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }
}