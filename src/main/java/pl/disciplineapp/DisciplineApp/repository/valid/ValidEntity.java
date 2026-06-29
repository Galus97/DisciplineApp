package pl.disciplineapp.DisciplineApp.repository.valid;

public interface ValidEntity {
    void throwIfIdIsNotValid (Long id, String errorMessage);
    void throwIfRequestIsNull(Object request, String errorMessage);
    Object getEntityOrThrowIfNotExist(Long id);
}
