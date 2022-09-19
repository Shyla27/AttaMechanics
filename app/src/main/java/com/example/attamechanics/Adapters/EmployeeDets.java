package com.example.attamechanics.Adapters;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class EmployeeDets  implements Serializable {

        private String employeename;
        private String employeeemail;
        private String employeenumber, id;
        private  String speciality;

        public  EmployeeDets() {
        }


    public EmployeeDets(String employeename, String employeeemail, String id, String employeenumber, String speciality) {
            this.employeenumber= employeenumber;
            this.employeename = employeename;
            this.employeeemail= employeeemail;
            this.speciality= speciality;
            this.id = id;


    }

    @Exclude
    public Map toMap () {
        HashMap result = new HashMap<>();
        result.put("employeename", employeename);
        result.put("emplyoyeenumber", employeenumber);
        result.put("employeeemail", employeeemail);
        result.put("speciality", speciality);
        result.put("id", id);

        return result;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeename() {
            return  employeename;
        }

        public void setEmployeename(String employeename) {
            this.employeename = employeename;
        }


    public String getEmployeeemail () {
            return  employeeemail;
        }
        public void setEmployeeemail(String employeeemail) {
            this.employeeemail = employeeemail;

        }

    public String getEmployeenumber() {
        return employeenumber;
    }

    public void setEmployeenumber(String employeenumber) {
        this.employeenumber = employeenumber;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public class MyViewHolder {
    }
}


