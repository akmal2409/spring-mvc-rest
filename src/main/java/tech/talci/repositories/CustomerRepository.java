package tech.talci.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.talci.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
