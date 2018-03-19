package com.petrov.dimitar.zopa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class ZopaApplication implements CommandLineRunner {

	@Autowired
	private MarketParser marketParser;
	@Autowired
	private LendingService lendingService;

	public static void main(String[] args) {
		SpringApplication.run(ZopaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (args.length != 2) {
			System.out.println(String.format("Incorrect number of arguments: 2 required but %d given", args.length));
			System.exit(1);
		}

		String marketFile = args[0];
		int loanAmount = 0;
		try {
			loanAmount = Integer.parseInt(args[1]);
		} catch (NumberFormatException ex) {
			System.out.println("Offer amount must be an integer");
			System.exit(1);
		}

		if (loanAmount < 1000 || loanAmount > 1500 || loanAmount % 100 != 0) {
			System.out.println("Loan amount is invalid. Must be a £100 increment btw £1000 and £15000 inclusive");
			System.exit(1);
		}

		List<Offer> market = marketParser.parseFile(marketFile);
		if (market.isEmpty()) {
			System.out.println("Could not parse market file");
			System.exit(1);
		}

		Optional<Quote> qoute = lendingService.borrow(loanAmount, market);
		if (qoute.isPresent()) {
			System.out.println(qoute.get());
		} else {
			System.out.println("It is not possible to provide a quote at this time");
		}
	}

}
