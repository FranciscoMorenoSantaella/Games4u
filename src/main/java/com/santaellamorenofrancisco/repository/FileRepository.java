package com.santaellamorenofrancisco.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.santaellamorenofrancisco.model.File;

/**
 * 
 * @author Francisco
 *
 */
@Repository
@Transactional
public interface FileRepository extends JpaRepository<File, Long> {
	/**
	 * Consulta que devuelve todas las filesnes de la base de datos
	 * @return una lista de filesnes
	 */
	@Query(value = "SELECT f.id,f.originalname,f.uniquename,f.url,f.game_id FROM files f", nativeQuery = true)
	public List<File> getFilesFromDatabase();

	/**
	 * Consulta que sirve para guardar la informacion de la filesn en la base de datos
	 * @param originalname es el nombre original de la filesn
	 * @param uniquename es el nombre unico autogenerado de la filesn
	 * @param url es el nombre de la carpeta en la que se guardan las filesnes y el nombre unico
	 * @param game_id es el id del producto al que esta vinculada la filesn
	 */
	@Modifying
	@Query(value = "insert into files (originalname,uniquename,url,game_id) VALUES (:originalname,:uniquename,:url,:game_id)", nativeQuery = true)
	void insertFile(@Param("originalname") String originalname, @Param("uniquename") String uniquename,
			@Param("url") String url, @Param("game_id") Long game_id);
	
	/**
	 * Consulta que nos trae una filesn segun el id del producto introducido
	 * @param id es el id de producto del que queremos traer su filesn
	 * @return todos los campos de filesn limitado a 1.
	 * 
	 * Cabe recalcar que por como esta disenada la api un producto podria tener varias filesnes y seria bastante sencillo hacer
	 * un carrucel de filesnes por producto pero he preferido simplificarlo por falta de tiempo
	 */
	@Query(nativeQuery = true, value = "SELECT f.* FROM files f WHERE f.game_id = ?1 ORDER BY f.game_id desc LIMIT 1")
	File getFileByGameId(@Param("game_id") Long id);
}
