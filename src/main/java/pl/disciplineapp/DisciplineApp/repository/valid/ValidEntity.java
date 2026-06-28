package pl.disciplineapp.DisciplineApp.repository.valid;

public interface ValidEntity {
    void throwIfIdIsNotValid (Long id);
    void throwIfRequestIsNull(Object request);
    Object getEntityOrThrowIfNotExist(Long id);
}
