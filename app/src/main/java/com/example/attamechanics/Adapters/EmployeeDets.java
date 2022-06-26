package com.example.attamechanics.Adapters;

public class EmployeeDets {

        private String employeename;
        private String employeeemail;
        private String imgUrl;
        private String employeeContactNumber;
        private String employeeSpecialty;
        public  EmployeeDets() {
        }

        public EmployeeDets(String employeename, String imgUrl){
            this.employeename = employeename;
            this.imgUrl = imgUrl;
        }

        public String getEmployeename() {
            return  employeename;
        }

        public void setEmployeename(String employeename) {
            this.employeename = employeename;
        }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getEmployeeemail () {
            return  employeeemail;
        }
        public void setEmployeeemail(String employeeemail) {
            this.employeeemail = employeeemail;

        }
        public String getEmployeeContactNumber() {
            return employeeContactNumber;
        }

        public void setEmployeeContactNumber(String employeeContactNumber) {
            this.employeeContactNumber = employeeContactNumber;
        }
        public String getEmployeeSpecialty() {
            return employeeSpecialty;
        }

        public void setEmployeeSpecialty(String employeeSpecialty) {
            this.employeeSpecialty = employeeSpecialty;
        }
    }


