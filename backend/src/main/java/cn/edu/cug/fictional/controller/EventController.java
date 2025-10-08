package cn.edu.cug.fictional.controller;

import cn.edu.cug.fictional.dto.request.PublishEventRequest;
import cn.edu.cug.fictional.dto.response.ApiResponse;
import cn.edu.cug.fictional.dto.response.EventResponse;
import cn.edu.cug.fictional.dto.response.PageResponse;
import cn.edu.cug.fictional.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 大事记控制器
 */
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    /**
     * 查看所有大事记
     */
    @GetMapping
    public ApiResponse<PageResponse<EventResponse>> listEvents(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ApiResponse.success(eventService.listEvents(page, size));
    }

    /**
     * 发布大事记
     */
    @PostMapping
    public ApiResponse<Map<String, Object>> publishEvent(
            @Valid @RequestBody PublishEventRequest request,
            Authentication authentication) {
        Long publisherId = Long.parseLong(authentication.getName());
        return ApiResponse.success(eventService.publishEvent(request, publisherId));
    }
}

