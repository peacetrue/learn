package com.github.peacetrue.raws;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * @author xiayx
 */
public interface UserRepository extends CrudRepository<User, Long>, JpaRepository<User, Long> {

}
