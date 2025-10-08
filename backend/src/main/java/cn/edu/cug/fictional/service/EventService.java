package cn.edu.cug.fictional.service;

import cn.edu.cug.fictional.dto.request.PublishEventRequest;
import cn.edu.cug.fictional.dto.response.EventResponse;
import cn.edu.cug.fictional.dto.response.PageResponse;

import java.util.Map;

/**
 * 大事记服务接口
 */
public interface EventService {
    
    /**
     * 查看所有大事记
     */
    PageResponse<EventResponse> listEvents(Integer page, Integer size);
    
    /**
     * 发布大事记
     */
    Map<String, Object> publishEvent(PublishEventRequest request, Long publisherId);
}

