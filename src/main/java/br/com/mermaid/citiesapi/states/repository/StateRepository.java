package br.com.mermaid.citiesapi.states.repository;

import br.com.mermaid.citiesapi.states.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StateRepository extends JpaRepository<State, Long> {
}