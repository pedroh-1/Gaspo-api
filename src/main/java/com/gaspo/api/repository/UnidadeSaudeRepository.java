package com.gaspo.api.repository;

import com.gaspo.api.model.UnidadeSaudeModel;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class UnidadeSaudeRepository implements JpaRepository<UnidadeSaudeModel, Long> {


    @Override
    public void flush() {

    }

    @Override
    public <S extends UnidadeSaudeModel> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends UnidadeSaudeModel> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<UnidadeSaudeModel> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public UnidadeSaudeModel getOne(Long aLong) {
        return null;
    }

    @Override
    public UnidadeSaudeModel getById(Long aLong) {
        return null;
    }

    @Override
    public UnidadeSaudeModel getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends UnidadeSaudeModel> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends UnidadeSaudeModel> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends UnidadeSaudeModel> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends UnidadeSaudeModel> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends UnidadeSaudeModel> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends UnidadeSaudeModel> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends UnidadeSaudeModel, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends UnidadeSaudeModel> S save(S entity) {
        return null;
    }

    @Override
    public <S extends UnidadeSaudeModel> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<UnidadeSaudeModel> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<UnidadeSaudeModel> findAll() {
        return List.of();
    }

    @Override
    public List<UnidadeSaudeModel> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(UnidadeSaudeModel entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends UnidadeSaudeModel> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<UnidadeSaudeModel> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<UnidadeSaudeModel> findAll(Pageable pageable) {
        return null;
    }
}
