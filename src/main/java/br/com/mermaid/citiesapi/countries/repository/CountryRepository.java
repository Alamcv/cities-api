package br.com.mermaid.citiesapi.countries.repository;

import br.com.mermaid.citiesapi.countries.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
