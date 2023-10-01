package org.main.automation.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BookingRequest {
    private String firstname;
    private String lastname;
    private int totalprice;
    private boolean depositpaid;
    @JsonProperty("bookingdates")
    private BookingDates bookingdates;
    private String additionalneeds;

    public BookingRequest() {
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public boolean isDepositpaid() {
        return depositpaid;
    }

    public BookingDates getBookingdates() {
        return bookingdates;
    }

    public void setBookingdates(BookingDates bookingdates) {
        this.bookingdates = bookingdates;
    }

    public String getAdditionalneeds() {
        return additionalneeds;
    }

    public BookingRequest(String firstname, String lastname, int totalprice, boolean depositpaid,
                          BookingDates bookingdates, String additionalneeds) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.totalprice = totalprice;
        this.depositpaid = depositpaid;
        this.bookingdates = bookingdates;
        this.additionalneeds = additionalneeds;
    }

    public static class BookingDates {
        @JsonProperty("checkin")
        private String checkin;
        @JsonProperty("checkout")
        private String checkout;

        public BookingDates(LocalDate checkin, LocalDate checkout) {
            this.checkin = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(checkin);
            this.checkout = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(checkout);
        }

        public BookingDates() {
        }

        public String getCheckin() {
            return checkin;
        }

        public String getCheckout() {
            return checkout;
        }
    }
}


