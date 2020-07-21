package com.galvanize.demo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.geom.Area;
import java.util.*;

@RestController
public class HelloController {


    public class AreaParams {

        private String type="None";
        private String height="None";
        private String width="None";
        private String radius="None";

        public String getType() { return this.type; }
        public String getHeight() { return this.height; }
        public String getWidth() { return this.width; }
        public String getRadius() { return this.radius; }

        public void setType(String t) { this.type = t; }
        public void setHeight(String h) { this.height = h; }
        public void setWidth(String w) { this.width = w; }
        public void setRadius(String r) { this.radius = r; }
    }

    @GetMapping("/math/pi")
    public String getPI() {
        return "3.141592653589793";
    }

    @GetMapping("/math/calculate")
    public String getCalc(
            @RequestParam(value="x", required=true) double x,
            @RequestParam(value="y", required=true) double y,
            @RequestParam(value="operation", required=true) String op
    ) {
        switch(op) {
            case "add": return String.valueOf(x+y);
            case "multiply": return String.valueOf(x*y);
            case "subtract": return String.valueOf(x-y);
            case "divide": return String.valueOf(x/y);
            default: return "";
        }
    }

    @PostMapping("/math/sum")
    public String postSum(@RequestParam(value="n", required=true) double[] nums) {
        double accum = 0.0f;
        for (double x : nums) { accum += x; }
        return String.valueOf(accum);
    }

    @GetMapping("/math/volume/{x}/{y}/{z}")
    public String postSum(@PathVariable double x, @PathVariable double y, @PathVariable double z) {
        return "The volume of a " + x + "x" + y + "x" + z + " rectangle is " + String.valueOf(x*y*z);
    }

    @PostMapping("/math/area")
    public String getArea(AreaParams params) {
        switch(params.type) {
            case "rectangle":
                if (!params.height.equals("None") && !params.width.equals("None"))
                    return "Area of a " + params.width + "x" + params.height + " rectangle is " + String.valueOf(Double.parseDouble(params.width) * Double.parseDouble(params.height));
                else
                    return "Invalid";
            case "circle":
                if (!params.radius.equals("None"))
                    return "Area of a circle with radius of " + params.radius + " is " + String.valueOf(Math.PI * Math.pow(Double.parseDouble(params.radius), 2));
                else
                    return "Invalid";
            default: return "Invalid";
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Passenger {
        String firstName;
        String lastName;

        public Passenger(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        @JsonProperty(value="FirstName")
        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        @JsonProperty("LastName")
        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }

    public static class Ticket {
        Passenger passenger;
        double price;

        public Ticket(Passenger passenger, double price) {
            this.passenger = passenger;
            this.price = price;
        }

        @JsonProperty(value="Passenger")
        public Passenger getPassenger() {
            return passenger;
        }

        public void setPassenger(Passenger passenger) {
            this.passenger = passenger;
        }

        @JsonProperty(value="Price")
        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }

    public static class Flight {
        Date date;
        List<Ticket> tickets;

        public Flight(Date date, List<Ticket> tickets) {
            this.date = date;
            this.tickets = tickets;
        }

        @JsonFormat(pattern="yyyy-MM-dd HH:mm")
        @JsonProperty(value="Departs")
        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        @JsonProperty(value="Tickets")
        public List<Ticket> getTickets() {
            return tickets;
        }

        public void setTickets(List<Ticket> tickets) {
            this.tickets = tickets;
        }
    }

    private Flight testFlight = new Flight(new Date(2017-1900, 03, 21, 14, 34),
                                Arrays.asList(new Ticket(new Passenger("Bill", "Smith"), 200)));

    private Flight testFlight2 = new Flight(new Date(2017-1900, 03, 21, 14, 34),
            Arrays.asList(new Ticket(new Passenger("John", null), 400)));

    @GetMapping("/flights/flight")
    public Flight getFlight() {
        return testFlight;
    }

    @GetMapping("/flights")
    public List<Flight> getFlights() {
        return Arrays.asList(testFlight, testFlight2);
    }

}
