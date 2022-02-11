package com.sarbesh.alarmgps.utils;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LicActivityUtilsTest {

    double bankInterest = 5.0;
    double bank5Interest = 5.3;

    LicUtils licUtils = new LicUtils();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getInvestment_anand() {
        long investment = licUtils.getInvestment(26, 25, 21312,
                20853, bankInterest,bank5Interest);

        System.out.println(investment);
    }
}