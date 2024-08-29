package com.redis.roms.cache.service;


import com.redis.roms.cache.domain.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
  List<Event> searchByBeginDateBetween(LocalDateTime start, LocalDateTime end);
}
