package quru.qa.country.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quru.qa.country.ex.NotFoundException;
import quru.qa.country.model.Country;
import quru.qa.country.service.CountryService;

import java.util.List;

@RestController
@RequestMapping("/country")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Country> allCountries() {
        return countryService.allCountries();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Country addCountry(@RequestBody Country country) {
        return countryService.addCountry(country);
    }

    @GetMapping("/{countryCode}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Country> getCountryByCode(@PathVariable String countryCode) {
        Country country = countryService.getCountry(countryCode);
        return ResponseEntity.ok(country);
    }

    @PatchMapping("/update/{countryCode}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Country> updateCountry(@PathVariable String countryCode, @RequestBody Country country) {
        Country updatedCountry = countryService.updateCountry(countryCode, country);
        return ResponseEntity.ok(updatedCountry);
    }

    @DeleteMapping("/delete/{countryCode}")
    public ResponseEntity<String> deleteCountry(@PathVariable String countryCode) {
        countryService.deleteByCode(countryCode);
        return ResponseEntity.ok("Item deleted successfully");
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
