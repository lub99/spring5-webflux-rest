package guru.springframework.spring5webfluxrest.bootstrap;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repository.CategoryRepository;
import guru.springframework.spring5webfluxrest.repository.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final VendorRepository vendorRepository;
    private final CategoryRepository categoryRepository;

    public Bootstrap(VendorRepository vendorRepository, CategoryRepository categoryRepository) {
        this.vendorRepository = vendorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (vendorRepository.count().block() == 0){
            loadVendors();
        }

        if (categoryRepository.count().block() == 0){
            loadCategories();
        }
    }

    private void loadCategories() {
        Category category = new Category();
        category.setDescription("Desc 1");

        Category category2 = new Category();
        category2.setDescription("Desc 1");

        Category category3 = new Category();
        category3.setDescription("Desc 1");

        categoryRepository.save(category).block();
        categoryRepository.save(category2).block();
        categoryRepository.save(category3).block();

        System.out.println("Categories loaded. Count is " + categoryRepository.count().block());
    }

    private void loadVendors() {
        Vendor vendor = new Vendor();
        vendor.setFirstname("Vendor 1");

        Vendor vendor2 = new Vendor();
        vendor2.setFirstname("Vendor 2");

        Vendor vendor3 = new Vendor();
        vendor3.setFirstname("Vendor 3");


        vendorRepository.save(vendor).block();
        vendorRepository.save(vendor2).block();
        vendorRepository.save(vendor3).block();

        System.out.println("Vendors loaded. Vendors count is " + vendorRepository.count().block());
    }
}
