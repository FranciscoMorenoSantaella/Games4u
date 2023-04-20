package com.santaellamorenofrancisco.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santaellamorenofrancisco.model.Platform;
import com.santaellamorenofrancisco.repository.PlatformRepository;

@Service
public class PlatformService {
	@Autowired
	PlatformRepository repository;
	
	public List<Platform> getAllPlatforms() throws Exception{
		try {
			List<Platform> platformlist = repository.findAll();
			return platformlist;
		} catch (Exception e) {
			throw new Exception("No hay carro de la compras en la base de datos");
		}
	}
	
	public Platform getPlatformById(Long id) throws Exception, IllegalArgumentException, NullPointerException {
		if (id != null) {
			try {
				Optional<Platform> platformlist = repository.findById(id);
				if (platformlist.isPresent()) {
					return platformlist.get();
				} else {
					throw new Exception("El carro de la compra no existe");
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
	
	public Platform createPlatform(Platform platform) throws Exception, NullPointerException {
		if (platform != null && platform.getId()==null) {
			try {
				return repository.save(platform);
			} catch (Exception e) {
				throw new Exception(e);
			}
		} else if (platform != null) {

			try {
				return updatePlatform(platform);
			} catch (Exception e) {
				throw new Exception(e);
			}
		}else {
			throw new NullPointerException("El carro de la compra es nulo");
		}
	}
	
	public Platform updatePlatform(Platform platform) throws Exception {
		if (platform != null) {
			try {
				return repository.save(platform);
			} catch (Exception e) {
				throw new Exception(e);
			}
		} else {
			throw new NullPointerException("El carro de la compra es nulo");
		}
	}
	
	public void deletePlatformById(Long id) throws NullPointerException, IllegalArgumentException, Exception {
		if (id != null) {
			Optional<Platform> platformlist;
			try {
				platformlist = Optional.ofNullable(getPlatformById(id));
				if (!platformlist.isEmpty()) {
					repository.deleteById(id);
				} else {
					throw new Exception("La plataforma no existe");
				}
			} catch (IllegalArgumentException e1) {
				throw new IllegalArgumentException("La plataforma no existe");
			} catch (NullPointerException e1) {
				throw new NullPointerException("La plataforma es nula");
			} catch (Exception e) {
				throw new Exception("La plataforma no existe", e);
			}
		} else {
			throw new NullPointerException("El id es nulo");
		}
	}
}
