package ua.zabirayrama.zabirayservice.service;

import org.springframework.stereotype.Service;
import ua.zabirayrama.zabirayservice.domain.Location;
import ua.zabirayrama.zabirayservice.repo.LocationRepository;

import java.util.List;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> findAll() {
       return locationRepository.findAll();
    }

    public Location findById(Long id) {

        return locationRepository.findById(id).get();
    }

    public Location add(Location location) {
        return locationRepository.save(location);
    }
    public Location update(Location location) {
        return locationRepository.save(location);
    }

    public void deleteById(Long id) {
        locationRepository.deleteById(id);
    }

}
