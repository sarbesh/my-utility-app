package com.sarbesh.alarmgps.utils;

import android.util.Log;

public class BankingUtils {

    /**
     *
     * @param principal Fixed Deposit amount ex: 1,00,000
     * @param timePeriod    LockingPeriod ex: 5 years
     * @param interestRate  Interest on fixed deposit ex: 5.4
     * @param payOutPeriod  Annually(1),Quarterly(4),bi-annual(2)
     * @param period Day(1),Month(2),Year(3)
     * @return emi  100000(1+(5.1))
     */
    public long calculateFDInterest(long principal, double timePeriod, double interestRate,
                                      int payOutPeriod, int period){
        double emi=0;
        double durationPeriod = 0;
        if(period==1){
            durationPeriod = timePeriod/365;
//            Log.d("FD","Using days");
        } else if (period==2){
            durationPeriod = timePeriod/12;
//            Log.d("FD","Using Months");
        } else if(period==3){
            durationPeriod=timePeriod;
//            Log.d("FD","Using Years");
        }
//        Log.i("FD","timePeriod: "+timePeriod+", durationPeriod: "+durationPeriod);
        if(durationPeriod<0.5){
            emi = principal*interestRate*durationPeriod/100;
        } else {
            double partOne = (1+interestRate/(100*payOutPeriod));   //1.0135
            double partTwo = (payOutPeriod*durationPeriod); //20
            double partThree = Math.pow(partOne,partTwo);   //1.30760044
            emi=principal*partThree;    //130760 -> maturity amount, 30760 -> interest
//            Log.d("FD","EMI: "+emi);
        }
        return Math.round(emi);
    }
}
