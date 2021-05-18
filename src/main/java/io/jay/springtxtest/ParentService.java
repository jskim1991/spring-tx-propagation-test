package io.jay.springtxtest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParentService {

    private final ParentRepository repository;
    private final ChildService childService;

    public ParentService(ParentRepository repository, ChildService childService) {
        this.repository = repository;
        this.childService = childService;
    }

    @Transactional
    public void invokeChildWithRequired() {
        // Creating new transaction with name [io.jay.springtxtest.ParentService.invokeChildWithRequired]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
        repository.save(new ParentEntity());
        try {
            // Participating in existing transaction
            childService.runWithRequired();
            // Participating transaction failed - marking existing transaction as rollback-only
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Transactional
    public void invokeChildWithRequiresNew() {
        // Creating new transaction with name [io.jay.springtxtest.ParentService.invokeChildWithRequiresNew]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
        repository.save(new ParentEntity());
        try {
            // Suspending current transaction, creating new transaction with name [io.jay.springtxtest.ChildService.runWithRequiresNew]
            childService.runWithRequiresNew();
            // Initiating transaction rollback
            // Resuming suspended transaction after completion of inner transaction
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void invokeChildWithMandatoryWithoutTransaction() {
        // Creating new transaction with name [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
        repository.save(new ParentEntity());
        try {
            // No existing transaction found for transaction marked with propagation 'mandatory'
            childService.runWithMandatory();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Transactional
    public void invokeChildWithMandatory() {
        // Creating new transaction with name [io.jay.springtxtest.ParentService.invokeChildWithMandatory]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
        repository.save(new ParentEntity());
        try {
            // Participating in existing transaction
            childService.runWithMandatory();
            // Participating transaction failed - marking existing transaction as rollback-only
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Transactional
    public void invokeChildWithSupports() {
        // Creating new transaction with name [io.jay.springtxtest.ParentService.invokeChildWithSupports]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
        repository.save(new ParentEntity());
        try {
            // Participating in existing transaction
            childService.runWithSupports();
            // Participating transaction failed - marking existing transaction as rollback-only
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void invokeChildWithSupportsWithoutTransaction() {
        repository.save(new ParentEntity());
        try {
            childService.runWithSupports();
            // Resuming suspended transaction after completion of inner transaction
            // Should roll back transaction but cannot - no transaction available
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Transactional
    public void invokeChildWithNever() {
        // Creating new transaction with name [io.jay.springtxtest.ParentService.invokeChildWithNever]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
        repository.save(new ParentEntity());
        try {
            childService.runWithNever();
        } catch (Exception e) {
            // Existing transaction found for transaction marked with propagation 'never'
            System.out.println(e.getMessage());
        }
    }

    public void invokeChildWithNeverWithoutTransaction() {
         repository.save(new ParentEntity());
        try {
            childService.runWithNever();
            // Resuming suspended transaction after completion of inner transaction
            // Should roll back transaction but cannot - no transaction available
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Transactional
    public void invokeChildWithNotSupported() {
        // Creating new transaction with name [io.jay.springtxtest.ParentService.invokeChildWithNotSupported]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
        repository.save(new ParentEntity());
        try {
            // Suspending current transaction
            childService.runWithNotSupported();
            // Resuming suspended transaction after completion of inner transaction
            // Should roll back transaction but cannot - no transaction available
            // Resuming suspended transaction after completion of inner transaction
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void invokeChildWithNotSupportedWithoutTransaction() {
        repository.save(new ParentEntity());
        try {
            childService.runWithNotSupported();
            // Resuming suspended transaction after completion of inner transaction
            // Should roll back transaction but cannot - no transaction available
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Transactional
    public void invokeChildWithNested() {
        // Creating new transaction with name [io.jay.springtxtest.ParentService.invokeChildWithNested]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
        repository.save(new ParentEntity());
        try {
            // Creating nested transaction with name [io.jay.springtxtest.ChildService.runWithNested]
            childService.runWithNested();
        } catch (Exception e) {
            // JpaDialect does not support savepoints - check your JPA provider's capabilities
            System.out.println(e.getMessage());
        }
    }

    public void invokeChildWithNestedWithoutTransaction() {
        repository.save(new ParentEntity());
        try {
            // Creating new transaction with name [io.jay.springtxtest.ChildService.runWithNested]: PROPAGATION_NESTED,ISOLATION_DEFAULT
            childService.runWithNested();
            // Initiating transaction rollback
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
