package com.santaellamorenofrancisco.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santaellamorenofrancisco.repository.UserRepository;
import com.santaellamorenofrancisco.model.User;


@Service
public class UserService {
	@Autowired
	UserRepository repository;
	
	public List<User> getAllUsers() throws Exception{
		try {
			List<User> userlist = repository.findAll();
			return userlist;
		} catch (Exception e) {
			throw new Exception("No hay usuarios en la base de datos");
		}
	}
	
	public User getUserById(Long id) throws Exception, IllegalArgumentException, NullPointerException {
		if (id != null) {
			try {
				Optional<User> userlist = repository.findById(id);
				if (userlist.isPresent()) {
					return userlist.get();
				} else {
					throw new Exception("El usuario no existe");
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			} catch (Exception e) {
				throw new Exception(e);
			}
		} else {
			throw new NullPointerException("El id es nulo");
		}
	}
	
	public User createUser(User user) throws Exception, NullPointerException {
		if (user != null && user.getId()==null) {
			try {
				return repository.save(user);
			} catch (Exception e) {
				throw new Exception(e);
			}
		} else if (user != null) {
			try {
				return updateUser(user);
			} catch (Exception e) {
				throw new Exception(e);
			}
		}else {
			throw new NullPointerException("El usuario es nulo");
		}
	}
	
	public User updateUser(User user) throws Exception {
		if (user != null) {
			try {
				return repository.save(user);
			} catch (Exception e) {
				throw new Exception(e);
			}
		} else {
			throw new NullPointerException("El usuario es nulo");
		}
	}
	
	public void deleteUserById(Long id) throws NullPointerException, IllegalArgumentException, Exception {
		if (id != null) {
			Optional<User> userlist;
			try {
				userlist = Optional.ofNullable(getUserById(id));
				if (!userlist.isEmpty()) {
					repository.deleteById(id);
				} else {
					throw new Exception("El usuario no existe");
				}
			} catch (IllegalArgumentException e1) {
				throw new IllegalArgumentException("El usuario no existe");
			} catch (NullPointerException e1) {
				throw new NullPointerException("El usuario es nulo");
			} catch (Exception e) {
				throw new Exception("El usuario no existe", e);
			}
		} else {
			throw new NullPointerException("El id es nulo");
		}
	}
}
