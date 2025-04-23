package lan.home.vector_store_loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VectorStoreLoaderApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(VectorStoreLoaderApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(VectorStoreLoaderApplication.class, args);
    }

}
