package com.performancelab.backend.dto;

import java.util.List;

public record PagedUsersResponse(
        List<UserDto> users,
        long total,
        int page,
        int size,
        int totalPages,
        boolean hasNext
) {
}
