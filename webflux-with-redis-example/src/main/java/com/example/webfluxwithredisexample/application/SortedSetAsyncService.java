package com.example.webfluxwithredisexample.application;

import com.example.webfluxwithredisexample.domain.SortedSetModel;
import com.example.webfluxwithredisexample.infrastructure.repository.SortedSetRepository;
import com.example.webfluxwithredisexample.presentation.router.sortedset.SortedSetRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SortedSetAsyncService {
    private final SortedSetRepository redis;

    public Mono<Void> setSortedSet(SortedSetRequest req) {
        SortedSetModel model = new SortedSetModel(req.name(), req.score());
        return redis.addToSortedSet(req.baseRequest().key(), model, req.score())
                .then();
    }

    public Mono<Set<SortedSetModel>> getSetDataByRange(String key, Double min, Double max) {
        return redis.rangeByScore(key, min, max, SortedSetModel.class)
                .collectList()
                .map(HashSet::new);
    }

    public Mono<List<SortedSetModel>> getTopN(String key, Integer n) {
        return redis.getTopNFromSortedSet(key, n, SortedSetModel.class)
                .collectList();
    }
}

