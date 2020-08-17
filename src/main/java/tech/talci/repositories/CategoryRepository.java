package tech.talci.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.talci.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
