package com.venkat.microservices.currencyconversion;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {

    @GetMapping("/currency-conversion/{from}/to/{to}/quantity/{value}")
    public CurrencyExchange getCurrencyExchange(@PathVariable String from, @PathVariable String to,
                                                @PathVariable BigDecimal value){


        HashMap<String,String> uRiVariable = new HashMap<>();
        uRiVariable.put("from",from);
        uRiVariable.put("to",to);
        ResponseEntity<CurrencyExchange> forEntity = new RestTemplate().getForEntity("http://localhost:8001/currency-exchange/from/USD/to/INR/",
                CurrencyExchange.class, uRiVariable);

        CurrencyExchange currencyExchange = forEntity.getBody();

        return new CurrencyExchange(currencyExchange.getId(), currencyExchange.getFrom(),currencyExchange.getTo(),
                value,currencyExchange.getConversionMultiple(),
                value.multiply(currencyExchange.getConversionMultiple()),currencyExchange.getEnvironment());

    }
}
