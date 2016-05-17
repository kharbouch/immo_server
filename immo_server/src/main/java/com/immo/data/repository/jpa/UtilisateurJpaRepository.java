package com.immo.data.repository.jpa;



import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import com.immo.bean.jpa.UtilisateurEntity;

/**
 * Repository : Utilisateur.
 */
public interface UtilisateurJpaRepository extends PagingAndSortingRepository<UtilisateurEntity, Long> {

    @Async
    @Query("SELECT t FROM UtilisateurEntity t where t.login = :login and t.password = :pass") 
    List<UtilisateurEntity> findByUserNameAndPass(@Param("login") String login,@Param("pass") String pass );
    
    
}
