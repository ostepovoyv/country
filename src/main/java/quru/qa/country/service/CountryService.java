package quru.qa.country.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quru.qa.country.data.CountryEntity;
import quru.qa.country.data.CountryRepository;
import quru.qa.country.ex.NotFoundException;
import quru.qa.country.model.Country;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> allCountries() {
        return countryRepository.findAll()
                .stream()
                .map(Country::fromEntity)
                .collect(Collectors.toList());
    }

    public Country getCountry(String countryCode) {
        CountryEntity countryEntity = countryRepository.findCountryByCode(countryCode);
        if (countryEntity == null) {
            throw new NotFoundException("Country with code: '" + countryCode + "' is not found");
        }
        return Country.fromEntity(countryEntity);
    }

    public Country addCountry(Country country) {
        return Country.fromEntity(countryRepository.save(CountryEntity.fromJson(country)));
    }

    public Country updateCountry(String countryCode, Country country) {
        CountryEntity countryEntity = countryRepository.findCountryByCode(countryCode);
        if (countryEntity != null) {
            countryEntity.setName(country.name());
        } else {
            throw new NotFoundException("Country with code: '" + countryCode + "' is not found");
        }
        return Country.fromEntity(countryRepository.save(countryEntity));
    }

    public void deleteByCode(String countryCode) {
        CountryEntity countryEntity = countryRepository.findCountryByCode(countryCode);
        if (countryEntity == null) {
            throw new NotFoundException("Country with code: '" + countryCode + "' is not found");
        }
        countryRepository.deleteById(countryEntity.getId());
    }
}
