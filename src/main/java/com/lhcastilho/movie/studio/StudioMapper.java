package com.lhcastilho.movie.studio;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class StudioMapper {

    public static StudioResponse mapStudioError(String error) {
        return StudioResponse
            .build()
            .setError(error);
    }

    public static StudioResponse mapStudio(Studio studio) {
        return StudioResponse
            .build()
            .setId(studio.getId())
            .setName(studio.getName());
    }

    public static Set<StudioResponse> mapStudios(Set<Studio> studios) {
        Set<StudioResponse> response = new HashSet<>();
        return studios
            .stream()
            .map( studio -> mapStudio(studio) )
            .collect(Collectors.toSet());
    }

}
