package com.sarbesh.alarmgps.utils;

public class LicUtils {

    BankingUtils bankingUtils = new BankingUtils();

    public long getInvestment(int termPeriod, int payPeriod, int firstPay, int secondPay,
                              double bankAnnualInterest, double bank5yearInterest){

        long investmentReturn=0;
        int payOutPeriod = 4;
        int period=3;


        if(termPeriod>5){
            investmentReturn += bankingUtils
                    .calculateFDInterest(firstPay,termPeriod,bank5yearInterest,payOutPeriod,period);

        } else {
            investmentReturn += bankingUtils
                    .calculateFDInterest(firstPay,termPeriod,bankAnnualInterest,payOutPeriod,period);
        }

        for (int i = 1; i < payPeriod; i++) {
            int netPeriod = termPeriod - i;
            if(netPeriod<5){
                investmentReturn += bankingUtils
                        .calculateFDInterest(secondPay,netPeriod,bankAnnualInterest,payOutPeriod,period);
            } else {
                investmentReturn += bankingUtils
                        .calculateFDInterest(secondPay,netPeriod,bank5yearInterest,payOutPeriod,period);
            }

        }

        return investmentReturn;
    }

    public long investment(int period,int firstPay, int secondPay){
        long sum =firstPay;
        sum += (long) secondPay *(period-1);
        return sum;
    }
}
