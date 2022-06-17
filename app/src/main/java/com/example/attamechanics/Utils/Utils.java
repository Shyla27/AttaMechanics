package com.example.attamechanics.Utils;

import android.app.Activity;

import com.example.attamechanics.R;
import com.example.attamechanics.objects.TypeObject;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Utils {

    public BigDecimal round(float amount, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(amount));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }


    public static ArrayList<TypeObject> getTypeList(Activity activity){
        ArrayList<TypeObject> typeArrayList = new ArrayList<>();


        typeArrayList.add(new TypeObject("type_1", activity.getResources().getString(R.string.type_1), activity.getResources().getDrawable(R.drawable.ic_type_1), 4));
        typeArrayList.add(new TypeObject("type_2", activity.getResources().getString(R.string.type_2), activity.getResources().getDrawable(R.drawable.ic_type_2), 7));
        typeArrayList.add(new TypeObject("type_3", activity.getResources().getString(R.string.type_3), activity.getResources().getDrawable(R.drawable.ic_type_3), 4));
        typeArrayList.add(new TypeObject("type_4", activity.getResources().getString(R.string.type_4), activity.getResources().getDrawable(R.drawable.ic_type_4), 1));

        return  typeArrayList;
    }

    public static int rideCostEstimate(double distance, double duration){
        double price;
        price = 36 + distance * 26 + duration * 0.001;
        return (int) price;
    }

    public static TypeObject getTypeById(Activity activity, String id){
        ArrayList<TypeObject> typeArrayList = getTypeList(activity);
        for(TypeObject mType : typeArrayList){
            if(mType.getId().equals(id)){
                return mType;
            }
        }
        return null;
    }
}
