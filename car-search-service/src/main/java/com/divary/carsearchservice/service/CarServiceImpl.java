package com.divary.carsearchservice.service;

import com.divary.carsearchservice.dto.request.CarSearchRequest;
import com.divary.carsearchservice.repository.CarRepository;
import com.divary.carsearchservice.dto.CarDto;
import com.divary.carsearchservice.model.Car;
import com.divary.exception.BadRequestException;
import com.divary.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CarServiceImpl implements CarService{
    private final CarRepository carRepository;
    private final ElasticsearchOperations elasticSearchTemplate;

    public CarServiceImpl(CarRepository carRepository, ElasticsearchOperations elasticSearchTemplate) {
        this.carRepository = carRepository;
        this.elasticSearchTemplate = elasticSearchTemplate;
    }

    @Override
    public void create(CarDto form) {
        Car car = carDtoToCar(form);
        carRepository.save(car);
        log.info("Create Car success, " + car);
    }

    @Override
    public CarDto findById(String id) {
        Query query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("id", id))
                .build();

        SearchHits<CarDto> carSearchHits = elasticSearchTemplate.search(query, CarDto.class, IndexCoordinates.of("car_rent.car_search_service.car"));

        return carSearchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Car Not Found"));
    }

    @Override
    public List<CarDto> search(CarSearchRequest form, Pageable pageable) {
        NativeSearchQuery searchQuery = getBoolSearchQuery(form, pageable);

        SearchHits<CarDto> carSearchHits = elasticSearchTemplate.search(searchQuery, CarDto.class, IndexCoordinates.of("car_rent.car_search_service.car"));

        return carSearchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    @Override
    public void update(String id, CarDto form) {
        Car car = carDtoToCar(form);
        carRepository.save(car);
        log.info("Update Car success, " + car);
    }

    @Override
    public void delete(String id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new NotFoundException("Car Not Found"));
        if (car.isDelete()){
            throw new BadRequestException("Cannot delete car, Because car is deleted");
        }
        car.setDelete(true);

        carRepository.save(car);
        log.info("Delete Car success, " + id);
    }

    private Car carDtoToCar(CarDto carDto){
        return Car.builder()
                .id(carDto.getId())
                .brand(carDto.getBrand())
                .model(carDto.getModel())
                .passenger(carDto.getPassenger())
                .engine(carDto.getEngine())
                .isDelete(carDto.isDelete())
                .build();
    }

    private NativeSearchQuery getBoolFindByIdQuery(String id) {
        QueryBuilder query = QueryBuilders.matchQuery("id", id);

        return new NativeSearchQueryBuilder()
                .withQuery(query)
                .withSearchType(SearchType.QUERY_THEN_FETCH)
                .build();
    }

    private NativeSearchQuery getBoolSearchQuery(CarSearchRequest form, Pageable pageable) {
        BoolQueryBuilder qb = QueryBuilders.boolQuery();

        getWildCardQuery(qb, "brand", form.getBrand());
        getWildCardQuery(qb, "model", form.getModel());
        if (null != form.getPassenger()){
            getMustPassengerQuery(qb, "passenger", form.getPassenger(), Operator.AND);
        }
        if (StringUtils.isNotBlank(form.getEngine())){
            getMustPassengerQuery(qb, "engine", form.getEngine(), Operator.AND);
        }
        getMustPassengerQuery(qb, "isDelete", form.isDelete(), Operator.AND);

        return new NativeSearchQueryBuilder()
                .withQuery(qb)
                .withPageable(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()))
                .withSort(pageable.getSort())
                .withSearchType(SearchType.QUERY_THEN_FETCH).build();
    }

    private void getMatchIdQuery(BoolQueryBuilder qb, String keyword) {
        qb.must(QueryBuilders.matchQuery("id", keyword).operator(Operator.AND).fuzziness(Fuzziness.ONE));
    }

    private void getWildCardQuery(BoolQueryBuilder qb, String name,String keyword) {
        qb.must(QueryBuilders.wildcardQuery(name, formatWildCardQuery(keyword)));
    }

    private void getMustPassengerQuery(BoolQueryBuilder qb, String name, String keyword, Operator operator) {
        qb.must(QueryBuilders.matchQuery(name, keyword).operator(operator));
    }

    private void getMustPassengerQuery(BoolQueryBuilder qb, String name, Integer keyword, Operator operator) {
        qb.must(QueryBuilders.matchQuery(name, keyword).operator(operator));
    }

    private void getMustPassengerQuery(BoolQueryBuilder qb, String name, boolean keyword, Operator operator) {
        qb.must(QueryBuilders.matchQuery(name, keyword).operator(operator));
    }

    private String formatWildCardQuery(String keyword){
        String format = "*%s*";
        return String.format(format, keyword);
    }
}
