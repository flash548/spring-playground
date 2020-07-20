package com.galvanize.demo;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

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
}
