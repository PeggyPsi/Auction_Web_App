package com.ted.auction_app.Repositories;

import com.ted.auction_app.Models.Location.LocationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
public interface LocationRepository extends CrudRepository<LocationEntity, Integer> {
}
