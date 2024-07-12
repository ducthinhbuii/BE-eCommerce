package com.example.ecommerce.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.ecommerce.model.Address;

public interface AddressRepository extends MongoRepository<Address, String> {
    @Query("{street: ?0, city: ?1, state: ?2}")
    public Address isAddressExists(@Param("street") String street, 
                                    @Param("city") String city, 
                                    @Param("state") String state);
}
