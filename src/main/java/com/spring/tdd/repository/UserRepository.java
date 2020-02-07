package com.spring.tdd.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.spring.tdd.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
