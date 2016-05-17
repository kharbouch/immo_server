package com.immo.data.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import com.immo.bean.jpa.BienEntity;
import com.immo.bean.jpa.ClientEntity;
import com.immo.bean.jpa.TrancheEntity;

/**
 * Repository : Client.
 */
public interface ClientJpaRepository extends PagingAndSortingRepository<ClientEntity, Long> {
	
	@Async
    @Query("SELECT c FROM ClientEntity c WHERE c.cinClient= :cin") 
    List<ClientEntity> findClientByCin(@Param("cin") String cin);


}
