package com.redis.roms.cache;



import com.redis.cache.RedisEnterpriseCacheManager;
import com.redis.om.spring.annotations.EnableRedisDocumentRepositories;
import com.redis.roms.cache.domain.Company;
import com.redis.roms.cache.domain.CompanyMeta;
import com.redis.roms.cache.domain.Person;
import com.redis.roms.cache.repositories.CompanyRepository;
import com.redis.roms.cache.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.util.Set;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
@EnableRedisDocumentRepositories
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
@EnableCaching
public class RomsSmartCacheDemoApplication {

  @Autowired
  CompanyRepository companyRepo;

  @Autowired
  PersonRepository personRepo;

  public static void main(String[] args) {
    SpringApplication.run(RomsSmartCacheDemoApplication.class, args);
  }

  @Bean
  CommandLineRunner loadTestData() {
    return args -> {
      companyRepo.deleteAll();
      Company redis = Company.of("Redis", "https://redis.com", new Point(-122.066540, 37.377690), 526, 2011,
          Set.of(CompanyMeta.of("Redis", 100, Set.of("RedisTag"))));
      redis.setTags(Set.of("fast", "scalable", "reliable"));

      Company microsoft = Company.of("Microsoft", "https://microsoft.com", new Point(-122.124500, 47.640160), 182268,
          1975, Set.of(CompanyMeta.of("MS", 50, Set.of("MsTag"))));
      microsoft.setTags(Set.of("innovative", "reliable"));

      companyRepo.save(redis);
      companyRepo.save(redis); // save again to test @LastModifiedDate
      companyRepo.save(microsoft);

      personRepo.deleteAll();
      personRepo.save(Person.of("Brian", "Sam-Bodden", "bsb@redis.com"));
      personRepo.save(Person.of("Guy", "Royse", "guy.royse@redis.com"));
      personRepo.save(Person.of("Guy", "Korland", "guy.korland@redis.com"));
    };
  }

  @Bean
  @Primary
  public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
    return RedisEnterpriseCacheManager.create(connectionFactory);
  }

}
