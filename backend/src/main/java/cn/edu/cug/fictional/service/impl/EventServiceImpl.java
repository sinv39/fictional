package cn.edu.cug.fictional.service.impl;

import cn.edu.cug.fictional.dto.request.PublishEventRequest;
import cn.edu.cug.fictional.dto.response.EventResponse;
import cn.edu.cug.fictional.dto.response.PageResponse;
import cn.edu.cug.fictional.entity.Event;
import cn.edu.cug.fictional.entity.User;
import cn.edu.cug.fictional.mapper.EventMapper;
import cn.edu.cug.fictional.mapper.UserMapper;
import cn.edu.cug.fictional.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 大事记服务实现类
 */
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventMapper eventMapper;
    private final UserMapper userMapper;

    @Override
    public PageResponse<EventResponse> listEvents(Integer page, Integer size) {
        int offset = (page - 1) * size;
        List<Event> events = eventMapper.selectWithPage(offset, size);
        Long total = eventMapper.count();
        
        List<EventResponse> responseList = events.stream().map(event -> {
            EventResponse response = new EventResponse();
            response.setId(event.getId());
            response.setTitle(event.getTitle());
            response.setContent(event.getContent());
            response.setPublisher(event.getPublisherName());
            response.setCreateTime(event.getCreateTime());
            return response;
        }).collect(Collectors.toList());
        
        return new PageResponse<>(total, responseList);
    }

    @Override
    public Map<String, Object> publishEvent(PublishEventRequest request, Long publisherId) {
        // 获取发布者信息
        User user = userMapper.selectById(publisherId);
        
        // 创建大事记
        Event event = new Event();
        event.setTitle(request.getTitle());
        event.setContent(request.getContent());
        event.setPublisherId(publisherId);
        event.setPublisherName(user.getRealName());
        event.setStatus(1);
        
        eventMapper.insert(event);
        
        Map<String, Object> result = new HashMap<>();
        result.put("id", event.getId());
        result.put("title", event.getTitle());
        result.put("createTime", event.getCreateTime());
        return result;
    }
}

