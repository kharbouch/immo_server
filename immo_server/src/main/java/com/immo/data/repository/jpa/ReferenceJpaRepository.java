package com.immo.data.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import com.immo.bean.jpa.ClientEntity;
import com.immo.bean.jpa.ReferenceEntity;

/**
 * Repository : Reference.
 */
public interface ReferenceJpaRepository extends PagingAndSortingRepository<ReferenceEntity, Long> {

	@Async
    @Query("SELECT r FROM ReferenceEntity r WHERE r.referenceType.id= :idRef") 
    List<ReferenceEntity> findReferenceByType(@Param("idRef") Long idRef);

}
