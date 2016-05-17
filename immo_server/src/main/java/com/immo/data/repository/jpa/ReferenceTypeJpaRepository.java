package com.immo.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.immo.bean.jpa.ReferenceTypeEntity;

/**
 * Repository : ReferenceType.
 */
public interface ReferenceTypeJpaRepository extends PagingAndSortingRepository<ReferenceTypeEntity, Long> {

}
