package org.example.repository;

import org.example.model.Selo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeloRepository extends JpaRepository<Selo, Integer> {}