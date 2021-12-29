package ma.gstcmd.product.services;

import com.google.common.io.Files;
import ma.gstcmd.product.dtos.ProductDto;
import ma.gstcmd.product.dtos.ProductDto1;
import ma.gstcmd.product.entities.ProductEntity;
import ma.gstcmd.product.exceptions.ProductException;
import ma.gstcmd.product.mappers.Mapper;
import ma.gstcmd.product.repositories.ProductRepository;
import ma.gstcmd.product.requests.ProductRequest;
import ma.gstcmd.product.shareds.Toll;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import java.io.*;
import java.util.Date;

@Service
public class ProductService implements IProductService {
    private ProductRepository productRepository;
    private Mapper mapper;
    private Toll toll;
    private EntityManager entityManager;
    private ServletContext context;


    @Autowired
    public ProductService(ProductRepository productRepository, Mapper mapper, Toll toll, EntityManager entityManager, ServletContext context) {
        this.productRepository = productRepository;
        this.mapper = mapper;
        this.toll = toll;
        this.entityManager = entityManager;
        this.context = context;
    }

    @Override
    public ProductDto1 getProducts(int page) {
        page = (page >0)? page-1 : page;
        Page<ProductEntity> entities = productRepository.findAll(PageRequest.of(page, 5));
        ProductDto1 dto = new ProductDto1();
        dto.setDtos(mapper.EntityToDto(entities.getContent()));
        dto.setPageSizes(entities.getTotalPages());
        return dto;
        //return mapper.EntityToDto((List<ProductEntity>) entities);
//        if(nullable){
//            Session session = entityManager.unwrap(Session.class);
//            session.enableFilter("FilterData");
//            Iterable<ProductEntity> entities = productRepository.findAll(Sort.by("productPrice").ascending());
//            session.disableFilter("FilterData");
//            return mapper.EntityToDto((List<ProductEntity>) entities);
//        }else {
//            Iterable<ProductEntity> entities = productRepository.findAll();
//            return mapper.EntityToDto((List<ProductEntity>) entities);
//        }
    }

    @Override
    public ProductDto getProduct(String productId) {
        if(productRepository.existsByProductId(productId)){
            ProductEntity productEntity = productRepository.findByProductId(productId);
            return mapper.EntityToDto(productEntity);
        }else {
            throw new ProductException("ce produit n'éxiste pas.");
        }

    }

    @Override
    public byte[] getProductImage(String imageName) throws IOException {
        InputStream in = getClass().getResourceAsStream("/Images/"+imageName);
        if(in == null) throw new ProductException("cette image n'éxiste pas.");
        return IOUtils.toByteArray(in);
    }

    @Override
    public Boolean existProduct(String productId) {
        return productRepository.existsByProductId(productId);
    }

    @Override
    public Boolean existProductQuantity(String productId, int orderedQuantity) {
        ProductDto dto = getProduct(productId);
        if(dto.getProductQuantity() >= orderedQuantity){
            updateProductQuantity(productId, orderedQuantity);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public ProductDto addProduct(ProductRequest request, MultipartFile file) {
        String newFileName = request.getProductName()+toll.generateImageSalt(5)+"."+ Files.getFileExtension(file.getOriginalFilename());
        File savedFile = new File("./src/main/resources/Images/"+newFileName);
        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(savedFile));
            stream.write(file.getBytes());
            stream.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        ProductEntity productEntity = mapper.RequestToEntity(request);
        productEntity.setProductId(toll.generateProductId(20));
        productEntity.setProductImage(newFileName);
        ProductEntity savedProduct = productRepository.save(productEntity);
        return mapper.EntityToDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(String productId, ProductRequest request) {
        if(productRepository.existsByProductId(productId)) {
            ProductEntity productEntity = productRepository.findByProductId(productId);
            productEntity.setProductName(request.getProductName());
//            productEntity.setProductImage(request.getProductImage());
            productEntity.setProductPrice(request.getProductPrice());
            productEntity.setProductQuantity(request.getProductQuantity());
            productEntity.setModifiedAt(new Date());
            ProductEntity savedProduct = productRepository.save(productEntity);
            return mapper.EntityToDto(savedProduct);
        }else {
            throw new ProductException("ce produit n'éxiste pas.");
        }
    }

    @Override
    public Boolean updateProductQuantity(String productId, int orderedQuantity) {
        if(productRepository.existsByProductId(productId)){
            ProductEntity productEntity = productRepository.findByProductId(productId);
            productEntity.setProductQuantity(productEntity.getProductQuantity() - orderedQuantity);
            productEntity.setModifiedAt(new Date());
            productRepository.save(productEntity);
            return true;
        }else {
            throw new ProductException("ce produit n'éxiste pas.");
        }
    }

    @Override
    public void deleteProduct(String productId) {
        if(productRepository.existsByProductId(productId)){
            productRepository.deleteById(productRepository.findByProductId(productId).getId());
        }else {
            throw new ProductException("ce produit n'éxiste pas.");
        }
    }


}
