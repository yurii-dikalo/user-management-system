package ums.service.mapper;

public interface DtoRequestMapper<D, M> {
    M toModel(D dto);
}
