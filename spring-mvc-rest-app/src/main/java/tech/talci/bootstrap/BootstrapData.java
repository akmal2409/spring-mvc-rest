package tech.talci.bootstrap;

import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tech.talci.domain.Category;
import tech.talci.domain.Customer;
import tech.talci.domain.Vendor;
import tech.talci.repositories.CategoryRepository;
import tech.talci.repositories.CustomerRepository;
import tech.talci.repositories.VendorRepository;

@Component
@Slf4j
public class BootstrapData implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    public BootstrapData(CategoryRepository categoryRepository, CustomerRepository customerRepository,
                         VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        loadCategories();
        loadCustomers();
        loadVendors();
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

    public void loadVendors(){
        Vendor amazon = new Vendor();
        Vendor walmart = new Vendor();
        Vendor newegg = new Vendor();

        amazon.setId(1L);
        amazon.setName("Amazon");
        walmart.setId(2L);
        walmart.setName("Walmart");
        newegg.setId(3L);
        newegg.setName("NewEgg");

        vendorRepository.save(amazon);
        vendorRepository.save(walmart);
        vendorRepository.save(newegg);

        log.debug("Vendors were loaded: " + vendorRepository.count());
    }
}
