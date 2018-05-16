package com.qa.seating;

import java.util.Arrays;

import seatsio.SeatsioClient;
import seatsio.charts.Chart;
import seatsio.events.Event;

public class SeatingPlan {

	SeatsioClient client = new SeatsioClient("8d36e669-5ed5-424c-9dec-98038f7b4807");

	public void bookTicket() {

		client.events.book("0d9ae045-65e2-4671-b2ee-32170c64c80e", Arrays.asList("V-1"));
	}

//	public void unbookTicket() {
//
//		client.events.release("0d9ae045-65e2-4671-b2ee-32170c64c80e", Arrays.asList("A-1", "A-2"));
//	}

//	public void holdTickets() {
//
//		client.events.hold("event1", Arrays.asList("D-5", "D-4"), "a33522e-41c3-4a9f-ac5f-fd68b3ffcd43");
//	}
	

}
