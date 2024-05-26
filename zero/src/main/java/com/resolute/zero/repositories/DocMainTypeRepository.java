package com.resolute.zero.repositories;

import com.resolute.zero.domains.DocMainType;
import com.resolute.zero.domains.DocSubType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocMainTypeRepository extends JpaRepository<DocMainType,Integer> {

    @Query("select d from DocMainType d where d.mainType = ?1")
    Optional<DocMainType> findByMainType(@NonNull String mainType);

    @Query("select d from DocMainType d where d.code = ?1")
    Optional<DocMainType> findByCode(@NonNull String code);

}
