package com.immo.data.repository.jpa;


import org.springframework.data.repository.PagingAndSortingRepository;



import com.immo.bean.jpa.TrancheEntity;


/**
 * Repository : Tranche.
 */
public interface TrancheJpaRepository extends PagingAndSortingRepository<TrancheEntity, Long> {


	
}
