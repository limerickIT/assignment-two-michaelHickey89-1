package com.sd4.repository;

import com.sd4.model.Beer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mickh
 */
@Repository
public interface BeerRepository extends PagingAndSortingRepository<Beer, Long> {
}
