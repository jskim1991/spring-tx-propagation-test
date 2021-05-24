package io.jay.springtxtest.child;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<ChildEntity, Long> {
}
