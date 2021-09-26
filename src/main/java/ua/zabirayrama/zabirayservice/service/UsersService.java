package ua.zabirayrama.zabirayservice.service;

import org.springframework.stereotype.Service;
import ua.zabirayrama.zabirayservice.domain.Users;
import ua.zabirayrama.zabirayservice.repo.UsersRepository;

import java.util.List;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<Users> findAll() {
       return usersRepository.findAll();
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
