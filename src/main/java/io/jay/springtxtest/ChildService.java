package io.jay.springtxtest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChildService {

    private final ChildRepository repository;

    public ChildService(ChildRepository repository) {
        this.repository = repository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void runWithRequired() {
        System.out.println("** Start of Child Transaction");
        repository.save(new ChildEntity());
        throw new RuntimeException("error");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void runWithRequiresNew() {
        System.out.println("** Start of Child Transaction");
        repository.save(new ChildEntity());
        throw new RuntimeException("error");
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void runWithMandatory() {
        System.out.println("** Start of Child Transaction");
        repository.save(new ChildEntity());
        throw new RuntimeException("error");
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void runWithSupports() {
        System.out.println("** Start of Child Transaction");
        repository.save(new ChildEntity());
        throw new RuntimeException("error");
    }

    @Transactional(propagation = Propagation.NEVER)
    public void runWithNever() {
        System.out.println("** Start of Child Transaction");
        repository.save(new ChildEntity());
        throw new RuntimeException("error");
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void runWithNotSupported() {
        System.out.println("** Start of Child Transaction");
        repository.save(new ChildEntity());
        throw new RuntimeException("error");
    }

    @Transactional(propagation = Propagation.NESTED)
    public void runWithNested() {
        System.out.println("** Start of Child Transaction");
        repository.save(new ChildEntity());
        throw new RuntimeException("error");
    }
}
