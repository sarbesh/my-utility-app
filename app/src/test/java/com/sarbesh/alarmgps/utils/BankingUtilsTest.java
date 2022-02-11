package com.sarbesh.alarmgps.utils;

import static org.junit.Assert.*;

import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

public class BankingUtilsTest {

    BankingUtils bankingUtils = new BankingUtils();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void calculateFDInterest_annual() {
        long principal = 100000;
        double interest = 5.4;
        double timePeriod =5;
        int payoutPeriod=4;
        int period =3;
        long calculated = bankingUtils.calculateFDInterest(principal, timePeriod, interest, payoutPeriod, period);
        assertEquals(130760,calculated);
    }

    @Test
    public void calculateFDInterest_day() {
        long principal = 100000;
        double interest = 5.1;
        double timePeriod =5;
        int payoutPeriod=4;
        int period =1;
        long calculated = bankingUtils.calculateFDInterest(principal, timePeriod, interest, payoutPeriod, period);
        assertEquals(70,calculated);
    }

    @Test
    public void calculateFDInterest_test() {
        long principal = 100000;
        double interest = 5.4;
        double timePeriod =5;
        int payoutPeriod=4;
        int period =3;
        long calculated = bankingUtils.calculateFDInterest(principal, timePeriod, interest, payoutPeriod, period);
        long calculated2 = bankingUtils.calculateFDInterest(principal, timePeriod*3-2, interest, payoutPeriod, period);
        assertEquals(130760,calculated);
        System.out.println(calculated2);
    }

}