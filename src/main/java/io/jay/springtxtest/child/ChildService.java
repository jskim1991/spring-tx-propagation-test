package io.jay.springtxtest.child;

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
        run();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void runWithRequiresNew() {
        run();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void runWithMandatory() {
        run();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void runWithSupports() {
        run();
    }

    @Transactional(propagation = Propagation.NEVER)
    public void runWithNever() {
        run();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void runWithNotSupported() {
        run();
    }

    @Transactional(propagation = Propagation.NESTED)
    public void runWithNested() {
        run();
    }

    private void run() {
        System.out.println("** Start of Child Transaction");
        repository.save(new ChildEntity());
        throw new RuntimeException("error");
    }
}
