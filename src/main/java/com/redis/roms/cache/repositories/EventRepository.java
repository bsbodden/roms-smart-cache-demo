package com.redis.roms.cache.repositories;

import com.redis.om.spring.repository.RedisDocumentRepository;
import com.redis.roms.cache.domain.Event;

public interface EventRepository extends RedisDocumentRepository<Event, String> {

}
