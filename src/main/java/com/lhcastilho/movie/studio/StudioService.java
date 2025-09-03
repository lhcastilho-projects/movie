package com.lhcastilho.movie.studio;

import static com.lhcastilho.movie.studio.StudioMapper.mapStudio;
import static com.lhcastilho.movie.studio.StudioMapper.mapStudioError;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class StudioService {

    @Inject
    private StudioRepository studioRepo;

    public StudioResponse createStudio(String name) {

        if (name == null || name.length() == 0) {
            String error = "O nome do estúdio está vazio.";
            return mapStudioError(error);
        }

        if (studioRepo.alreadyExists(name)) {
            String error = "Um estúdio com esse nome já existe.";
            return mapStudioError(error);
        }

        Studio newStudio = new Studio(name);
        Studio studioCreated = studioRepo.create(newStudio);
        if (studioCreated == null) {
            throw new RuntimeException("Erro ao criar um estúdio.");
        }

        return mapStudio(studioCreated);
    }

    public StudioResponse readStudio(int id) {
        if (id <= 0) {
            String error = "O id do estúdio é um número inválido.";
            return mapStudioError(error);
        }

        Studio studio = studioRepo.read(id);
        if (studio == null) {
            return null;
        }

        return mapStudio(studio);
    }

    public StudioResponse updateStudio(int id, String name) {
        if (name == null || name.length() == 0) {
            String error = "O nome do estúdio está vazio.";
            return mapStudioError(error);
        }

        if (studioRepo.alreadyExists(name)) {
            String error = "Um estúdio com esse nome já existe.";
            return mapStudioError(error);
        }

        Studio studioToUpdate = studioRepo.read(id);
        if (studioToUpdate == null) {
            String error = "Estúdio não existe.";
            return mapStudioError(error);
        }

        studioToUpdate.setName(name);
        Studio studioUpdated = studioRepo.update(studioToUpdate);
        if (studioUpdated == null) {
            throw new RuntimeException("Erro ao atualizar o estúdio.");
        }
        return mapStudio(studioUpdated);
    }

    public StudioResponse deleteStudio(int id) {
        Studio toDelete = studioRepo.read(id);
        if (toDelete == null) {
            String error = "Estúdio não existe.";
            return mapStudioError(error);
        }

        if (toDelete.getMovies().size() > 0) {
            String error = "O Estúdio possui filmes associados a ele.";
            return mapStudioError(error);
        }

        if (studioRepo.delete(id)) {
            return mapStudio(toDelete);
        } else {
            throw new RuntimeException("Erro ao deletar o estúdio.");
        }
    }
}
