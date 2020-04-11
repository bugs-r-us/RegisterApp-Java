package edu.uark.registerapp.commands.products;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.models.api.Product;
import edu.uark.registerapp.models.entities.ProductEntity;
import edu.uark.registerapp.models.repositories.ProductRepository;

@Service
public class ProductsQuerySearch implements ResultCommandInterface<List<Product>> {
	@Override
	public List<Product> execute() {
		final LinkedList<Product> products = new LinkedList<Product>();

		for (final ProductEntity productEntity : productRepository.findAll()) {
            if (productEntity.getLookupCode().toLowerCase().contains(searchName.toLowerCase()))
            {
                products.addLast(new Product(productEntity));
            }
		}
		
		return products;
    }
    
    //properties
    private String searchName;
    public String getSearchName(){
        return this.searchName;
    }

    public ProductsQuerySearch setSearchName(final String searchName) {
        this.searchName = searchName;
        return this;
    }

	@Autowired
	ProductRepository productRepository;
}
