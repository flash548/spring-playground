package com.galvanize.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
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
}
