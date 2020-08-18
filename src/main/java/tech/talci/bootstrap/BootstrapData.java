package tech.talci.bootstrap;

import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tech.talci.domain.Category;
import tech.talci.domain.Customer;
import tech.talci.repositories.CategoryRepository;
import tech.talci.repositories.CustomerRepository;

@Component
@Slf4j
public class BootstrapData implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;

    public BootstrapData(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        loadCategories();
        loadCustomers();
    }

    public void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        log.debug("Categories were loaded: " + categoryRepository.count());
    }

    public void loadCustomers() {
        Customer jack = new Customer();
        Customer fiona = new Customer();
        Customer akmal = new Customer();

        jack.setId(1L);
        jack.setFirstName("Jack");
        jack.setFirstName("Barker");
        fiona.setId(2L);
        fiona.setFirstName("Fiona");
        fiona.setLastName("Shrek");
        akmal.setId(3L);
        akmal.setFirstName("Aki");
        akmal.setLastName("Test");

        customerRepository.save(jack);
        customerRepository.save(fiona);
        customerRepository.save(akmal);

        log.debug("Customers were loaded: " + customerRepository.count());
    }
}
