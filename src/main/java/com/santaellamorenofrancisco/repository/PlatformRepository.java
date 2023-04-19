package com.santaellamorenofrancisco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.santaellamorenofrancisco.model.Order;
import com.santaellamorenofrancisco.model.Platform;

public interface PlatformRepository extends JpaRepository<Platform, Long> {

}
