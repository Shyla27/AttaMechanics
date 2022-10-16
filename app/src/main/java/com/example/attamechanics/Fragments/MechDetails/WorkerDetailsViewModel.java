package com.example.attamechanics.Fragments.MechDetails;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

import com.example.attamechanics.ViewModel.WorkerModel;

import java.util.ArrayList;

public class WorkerDetailsViewModel extends ViewModel {

    private ArrayList<WorkerModel> workerModels;

    public WorkerDetailsViewModel() {
        workerModels = new ArrayList<>();
        
        initData();
    }

    private void initData() {

        WorkerModel workerModel = new WorkerModel("Rohan gill","Designer","https://i.imgur.com/wnKtRoZ.png","emp123");
        workerModels.add(workerModel);

        workerModel = new WorkerModel("Harsh Saglani","UI/UX","https://i.imgur.com/wnKtRoZ.png","emp123");
        workerModels.add(workerModel);

        workerModel = new WorkerModel("Rohit Suthar","Developer","https://i.imgur.com/wnKtRoZ.png","emp133");
        workerModels.add(workerModel);

        workerModel = new WorkerModel("John Doe","Designer","https://i.imgur.com/wnKtRoZ.png","emp1223");
        workerModels.add(workerModel);

        workerModel = new WorkerModel("Akshay Kumar","DB Admin","https://i.imgur.com/wnKtRoZ.png","emp143");
        workerModels.add(workerModel);

        workerModel = new WorkerModel("Carry Minati","UI/UX","https://i.imgur.com/wnKtRoZ.png","emp143");
        workerModels.add(workerModel);

        workerModel = new WorkerModel("Raju Shriwastav","Architect","https://i.imgur.com/wnKtRoZ.png","emp143");
        workerModels.add(workerModel);

        workerModel = new WorkerModel("Aniket Pande ","SYS Admin","https://i.imgur.com/wnKtRoZ.png","emp143");
        workerModels.add(workerModel);
    }


    public ArrayList<WorkerModel> getWorkerModels() {
        return workerModels;
    }

    public void sort(Bundle b) {
        workerModels.remove(0);
    }
}