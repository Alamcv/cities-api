package br.com.mermaid.citiesapi.distances.service;

import br.com.mermaid.citiesapi.cities.entities.City;
import br.com.mermaid.citiesapi.cities.repository.CityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DistanceService {
    private final Double MILES = 1.609;
    private final CityRepository cityRepository;
    Logger log = LoggerFactory.getLogger(DistanceService.class);

    public DistanceService(final CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

//    public Double distanceByPointsInMiles(final Long city1, final Long city2) {
//        log.info("nativePostgresInMiles({}, {})", city1, city2);
//        return cityRepository.distanceByPoints(city1, city2);
//    }

//    public Double distanceByCubeInMeters(Long city1, Long city2) {
//        log.info("distanceByCubeInMeters({}, {})", city1, city2);
//        final List<City> cities = cityRepository.findAllById((Arrays.asList(city1, city2)));
//
//        Point p1 = cities.get(0).getLocation();
//        Point p2 = cities.get(1).getLocation();
//
//        return cityRepository.distanceByCube(p1.getX(), p1.getY(), p2.getX(), p2.getY());
//    }

    public String distanceByCube(Long city1, Long city2, String unit) {
        log.info("distanceByCube({}, {})", city1, city2);
        List<City> cities = cityRepository.findAllById((Arrays.asList(city1, city2)));

        Point p1 = cities.get(0).getLocation();
        Point p2 = cities.get(1).getLocation();

        Double distanceMeters = cityRepository.distanceByCube(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        Double distance = 0.00;

        String url = String.format("https://www.google.com/maps/dir/%s,%s/%s,%s",
                                    Double.toString(p1.getX()).replaceAll(",","."),
                                    Double.toString(p1.getY()).replaceAll(",","."),
                                    Double.toString(p2.getX()).replaceAll(",","."),
                                    Double.toString(p2.getY()).replaceAll(",","."));

        switch (unit) {
            case "mi":
                distance = (distanceMeters / 1000) / MILES;
                unit = "miles";
                break;
            case "m":
                distance = distanceMeters;
                unit = "meters";
                break;
            default:
                distance = (distanceMeters / 1000);
                unit = "kilometers";
                break;
        }
        return String.format("{\"content\":[{\"origin\":\"%s-%s\", \"destiny\":\"%s-%s\", \"distance\": \"%.2f\", \"unity\": \"%s\", \"url\":\"%s\"}]}",
                                cities.get(0).getName(),cities.get(0).getState().getUf(),
                                cities.get(1).getName(), cities.get(1).getState().getUf(),
                                distance, unit, url);

    }
}