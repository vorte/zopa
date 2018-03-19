package com.petrov.dimitar.zopa;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class LendingServiceTest {

    private LendingService lendingService;

    @Before
    public void setUp() {
        lendingService = new LendingService();
    }

    @Test
    public void borrow() {
        List<Offer> market = new ArrayList<>(Arrays.asList(new Offer(100, 0.07),
                new Offer(100, 0.05), new Offer(200, 0.06)));

        Optional<Quote> quote = lendingService.borrow(200, market);

        assertThat(quote.isPresent(), is(true));
        assertThat(quote.get().toString(), equalTo("Requested amount: £200\n" +
                "Rate: 5.5%\n" +
                "Monthly repayment: £6.04\n" +
                "Total repayment: £217.44"));
    }


    @Test
    public void borrow_notEnoughMarketValue() {
        List<Offer> market = new ArrayList<>(Arrays.asList(new Offer(100, 0.07),
                new Offer(200, 0.05)));

        Optional<Quote> quote = lendingService.borrow(500, market);

        assertThat(quote.isPresent(), is(false));
    }
}