package tech.talci.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.talci.domain.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
