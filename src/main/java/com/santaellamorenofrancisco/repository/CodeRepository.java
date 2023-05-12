package com.santaellamorenofrancisco.repository;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.santaellamorenofrancisco.model.Code;

@Repository
@Transactional
public interface CodeRepository extends JpaRepository<Code, Long> {
	
	@Modifying
	@Query(nativeQuery = true, value = "UPDATE codes SET user_id = ?1 WHERE code = ?2")
	public int redeemCode(@Param("user_id") Long user_id, @Param("code") String code);

	@Query(nativeQuery = true, value = "SELECT balance FROM codes WHERE code = ?1")
	public Double getCodeBalance(@Param("code") String code);
}
