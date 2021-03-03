package com.application.demo.repository;

import com.application.demo.model.Tutorial;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface MyCasssandraRepository extends CassandraRepository<Tutorial, UUID> {

}
