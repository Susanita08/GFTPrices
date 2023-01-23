package com.gft.es.prices.application.converter;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class Converter<T, U> {

    private final Function<T, U> fromModel;
    private final Function<U, T> fromEntity;

    public Converter(final Function<T, U> fromDto, final Function<U, T> fromEntity) {
        this.fromModel = fromDto;
        this.fromEntity = fromEntity;
    }

    public final U convertFromModel(final T dto) {
        if(nonNull(dto)){
            return fromModel.apply(dto);
        }
        return null;
    }

    public final T convertFromEntity(final U entity) {
        if(nonNull(entity)){
            return fromEntity.apply(entity);
        }
        return null;
    }

    public final List<U> createFromDtos(final Collection<T> dtos) {
        return dtos.stream().map(this::convertFromModel).collect(Collectors.toList());
    }

    public final List<T> createFromEntities(final Collection<U> entities) {
        return entities.stream().map(this::convertFromEntity).collect(Collectors.toList());
    }
}