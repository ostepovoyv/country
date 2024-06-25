package quru.qa.country.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import quru.qa.country.data.CountryEntity;

import java.util.Date;
import java.util.UUID;

public record Country(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("name")
        String name,
        @JsonProperty("code")
        String code,
        @JsonProperty("dateOfIndependent")
        Date dateOfIndependent) {

        public static Country fromEntity(CountryEntity countryEntity) {
                return new Country(
                        countryEntity.getId(),
                        countryEntity.getName(),
                        countryEntity.getCode(),
                        countryEntity.getDateOfIndependent()
                );
        }
}
