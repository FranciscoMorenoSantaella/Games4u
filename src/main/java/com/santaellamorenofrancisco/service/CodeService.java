package com.santaellamorenofrancisco.service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santaellamorenofrancisco.model.Code;
import com.santaellamorenofrancisco.model.User;
import com.santaellamorenofrancisco.repository.CodeRepository;
import com.santaellamorenofrancisco.repository.UserRepository;

@Service
public class CodeService {

    @Autowired
    private CodeRepository repository;
    
    @Autowired
    private UserRepository userRepository;

    public Code generarCode(BigDecimal balance) {
        String Code = generarCodeUnico();
        Code nuevoCode = new Code();
        nuevoCode.setCode(Code);
        nuevoCode.setBalance(balance);
        return repository.save(nuevoCode);
    }

    private String generarCodeUnico() {
        return UUID.randomUUID().toString();
    }
    
    private Double getCodeBalance(String code) throws Exception {
    	if (code != null) {
			try {
				return repository.getCodeBalance(code);
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			} catch (Exception e) {
				throw new Exception(e);
			}
		} else {
			throw new NullPointerException("El codigo es nulo");
		}
    }
    
    @Transactional
	public Code redeemCode(Long user_id, String code) throws Exception, IllegalArgumentException, NullPointerException {
		if (user_id != null && code !=null) {
			try {
				Code thecode = repository.getCodeByCode(code);
				if(thecode.getUsed() == null || thecode.getUsed() == false) {
					int row = repository.redeemCode(user_id,code);
					if(row == 1) {
						Double balance = getCodeBalance(code);
						Optional<User> user = userRepository.findById(user_id);
						if(user.isPresent()) {
							balance += user.get().getBalance();
							user.get().setBalance(balance);
							User user2 = userRepository.save(user.get());
							thecode.setUser(user2);
							Code thecode2 = new Code();
							thecode2.setId(thecode.getId());
							thecode2.setUsed(thecode.getUsed());
							thecode2.setUser(thecode.getUser());
							thecode2.setCode(thecode.getCode());
							thecode2.setBalance(thecode.getBalance());
							thecode.setUsed(true);
							repository.save(thecode);
							if(user2 != null) {
								return thecode2;
							}
						}
					}
				}else {
					return thecode;
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			} catch (Exception e) {
				throw new Exception(e);
			}
		} else {
			throw new NullPointerException("El id es nulo");
		}
		return new Code();

	}
}
