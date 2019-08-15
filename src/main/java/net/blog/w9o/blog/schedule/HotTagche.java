package net.blog.w9o.blog.schedule;

import lombok.Data;
import net.blog.w9o.blog.dto.HotTagDto;
import net.blog.w9o.blog.dto.QuestionDto;
import net.blog.w9o.blog.model.Question;
import net.blog.w9o.blog.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Data
@Component
public class HotTagche {
    @Autowired
    private QuestionService questionService;
    private List<String> hots = new ArrayList<>();
    private List<QuestionDto> hotsQuetion = new ArrayList<>();
    public void updateTags(Map<String, Integer> tags) {
        int max = 10;
        PriorityQueue<HotTagDto> priorityQueue = new PriorityQueue<>(max);
        tags.forEach((name, priority) -> {
            HotTagDto hotTagDto = new HotTagDto();
            hotTagDto.setName(name);
            hotTagDto.setPriority(priority);
            if (priorityQueue.size() < max) {
                priorityQueue.add(hotTagDto);
            } else {
                HotTagDto minHot = priorityQueue.peek();
                if (hotTagDto.compareTo(minHot) > 0) {
                    priorityQueue.poll();
                    priorityQueue.add(hotTagDto);
                }
            }
        });
         List<String> sortedTags = new ArrayList<>();
        HotTagDto poll = priorityQueue.poll();
        while (poll != null) {
            sortedTags.add(0, poll.getName());
            poll = priorityQueue.poll();
        }
        hots=sortedTags;
       // hotsQuetion  = questionService.hot();
    }
}
