package io.jay.springtxtest;

import io.jay.springtxtest.child.ChildRepository;
import io.jay.springtxtest.parent.ParentRepository;
import io.jay.springtxtest.parent.ParentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.UnexpectedRollbackException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = "logging.level.org.springframework.orm.jpa=debug")
@AutoConfigureDataJpa
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TransactionTests {

    @Autowired
    private ParentService parentService;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private ParentRepository parentRepository;

    /**
     * Child with REQUIRED appends to parent transaction
     */
    @Test
    void test_parentWithRequired_and_childWithRequired_throwsUnexpectedRollbackException() {
        assertThrows(UnexpectedRollbackException.class, () ->
                parentService.invokeChildWithRequired());
        assertThat(parentRepository.findAll().size(), equalTo(0));
        assertThat(childRepository.findAll().size(), equalTo(0));
    }

    /**
     * Child with REQUIRES_NEW suspends parent transaction and creates a new transaction
     */
    @Test
    void test_parentWithRequired_and_childWithRequiresNew_savesParentTransactionOnly() {
        parentService.invokeChildWithRequiresNew();
        assertThat(parentRepository.findAll().size(), equalTo(1));
        assertThat(childRepository.findAll().size(), equalTo(0));
    }

    /**
     * Child with MANDATORY does not perform due to:
     * No existing transaction found for transaction marked with propagation 'mandatory' without parent transaction
     */
    @Test
    void test_parentWithoutRequired_and_childWithMandatory_savesParentTransactionOnly() {
        parentService.invokeChildWithMandatoryWithoutTransaction();
        assertThat(parentRepository.findAll().size(), equalTo(1));
        assertThat(childRepository.findAll().size(), equalTo(0));
    }

    /**
     * Child with MANDATORY appends to parent transaction
     */
    @Test
    void test_parentWithRequired_and_childWithMandatory_throwsUnexpectedRollbackException() {
        assertThrows(UnexpectedRollbackException.class, () ->
                parentService.invokeChildWithMandatory());
        assertThat(parentRepository.findAll().size(), equalTo(0));
        assertThat(childRepository.findAll().size(), equalTo(0));
    }

    /**
     * Child with SUPPORTS appends to parent transaction
     */
    @Test
    void test_parentWithRequired_and_childWithSupports_throwsUnexpectedRollbackException() {
        assertThrows(UnexpectedRollbackException.class, () ->
                parentService.invokeChildWithSupports());
        assertThat(parentRepository.findAll().size(), equalTo(0));
        assertThat(childRepository.findAll().size(), equalTo(0));
    }

    /**
     * Child with SUPPORTS runs non-transactional if parent transaction does not exist
     */
    @Test
    void test_parentWithoutRequired_and_childWithSupports_doesNotRunInTransaction() {
        parentService.invokeChildWithSupportsWithoutTransaction();
        assertThat(parentRepository.findAll().size(), equalTo(1));
        assertThat(childRepository.findAll().size(), equalTo(1));
    }

    /**
     * Child with NEVER does not perform due to:
     * Existing transaction found for transaction marked with propagation NEVER
     */
    @Test
    void test_parentWithRequired_and_childWithNever_throwsExceptionInChildTransaction() {
        parentService.invokeChildWithNever();
        assertThat(parentRepository.findAll().size(), equalTo(1));
        assertThat(childRepository.findAll().size(), equalTo(0));
    }

    /**
     * Child with NEVER runs non-transactional if parent transaction does not exist
     */
    @Test
    void test_parentWithoutRequired_and_childWithNever_doesNotRunInTransaction() {
        parentService.invokeChildWithNeverWithoutTransaction();
        assertThat(parentRepository.findAll().size(), equalTo(1));
        assertThat(childRepository.findAll().size(), equalTo(1));
    }

    /**
     * Child with NOT_SUPPORTED suspends parent transaction and run non-transactional
     */
    @Test
    void test_parentWithRequired_and_childWithNotSupported_childRunsNonTransactional() {
        parentService.invokeChildWithNotSupported();
        assertThat(parentRepository.findAll().size(), equalTo(1));
        assertThat(childRepository.findAll().size(), equalTo(1));
    }

    /**
     * Child with NOT_SUPPORTED runs non-transactional if parent transaction does not exist
     */
    @Test
    void test_parentWithoutRequired_and_childWithNotSupported_doesNotRunInTransaction() {
        parentService.invokeChildWithNotSupportedWithoutTransaction();
        assertThat(parentRepository.findAll().size(), equalTo(1));
        assertThat(childRepository.findAll().size(), equalTo(1));
    }

    /**
     * Child with NESTED works as a savepoint with parent transaction
     */
    @Test
    void test_parentWithRequired_and_childWithNested_throwsNestedTransactionNotSupportedExceptionInChildTransaction() {
        parentService.invokeChildWithNested();
        assertThat(parentRepository.findAll().size(), equalTo(1));
        assertThat(childRepository.findAll().size(), equalTo(0));
    }

    /**
     * Child with NESTED creates a new transaction without parent transaction
     */
    @Test
    void test_parentWithoutRequired_and_childWithNested_createsChildTransaction() {
        parentService.invokeChildWithNestedWithoutTransaction();
        assertThat(parentRepository.findAll().size(), equalTo(1));
        assertThat(childRepository.findAll().size(), equalTo(0));
    }
}
