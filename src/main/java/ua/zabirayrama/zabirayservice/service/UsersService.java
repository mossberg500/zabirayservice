package ua.zabirayrama.zabirayservice.service;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import ua.zabirayrama.zabirayservice.domain.Location;
import ua.zabirayrama.zabirayservice.domain.Users;
import ua.zabirayrama.zabirayservice.repo.LocationRepository;
import ua.zabirayrama.zabirayservice.repo.UsersRepository;

import java.util.List;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final LocationRepository locationRepository;

    public UsersService(UsersRepository usersRepository, LocationRepository locationRepository) {
        this.usersRepository = usersRepository;
        this.locationRepository = locationRepository;
    }

    public List<Users> findAll() {
        List<Users> usersList = usersRepository.findAll();
        for(Users user : usersList) {
            user.setLocation(locationRepository.findbyUsers(user.getId()));
        }
        System.out.println("usersList     " + usersList.toString());
       return usersList;
    }

    public Users findById(Long id) {

        return usersRepository.findById(id).get();
    }

    public Users add(Users users) {
        return usersRepository.save(users);
    }
    public Users update(Users users) {
        return usersRepository.save(users);
    }

    public void deleteById(Long id) {
        usersRepository.deleteById(id);
    }

}
