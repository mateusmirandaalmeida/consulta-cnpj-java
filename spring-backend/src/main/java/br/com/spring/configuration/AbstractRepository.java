package br.com.spring.configuration;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 *
 * @author spring-boot
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public interface AbstractRepository<T extends Object, ID extends Serializable> extends JpaRepository<T, ID>, CrudRepository<T,ID>{
    
}
