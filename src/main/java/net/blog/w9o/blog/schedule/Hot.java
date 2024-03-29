package net.blog.w9o.blog.schedule;

import lombok.extern.slf4j.Slf4j;
import net.blog.w9o.blog.mapper.QuestionMapper;
import net.blog.w9o.blog.model.Question;
import net.blog.w9o.blog.model.QuestionExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class Hot {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private HotTagche hotTagche;
    @Scheduled(fixedRate = 1000*60*3)
    public void reportCurrentTime(){
        int offset = 0;
        int limit = 20;
        log.info("start Time: {}", new Date());
        List<Question> list = new ArrayList<>();
        Map<String,Integer> priorities = new HashMap<>();
        while (offset ==0 || list.size() ==limit){
            list = questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(offset,limit));
            for (Question question : list) {
                String[] tags = StringUtils.split(question.getTag(),",");
                for (String tag : tags) {
                    Integer priority = priorities.get(tag);
                    if (priority!=null){
                        priorities.put(tag,priority+5+question.getCommentCount());
                    }else {
                        priorities.put(tag,5+question.getCommentCount());
                    }
                }
            }
            offset += limit;
        }
        hotTagche.updateTags(priorities);
        log.info("stop Time : {}", new Date());
    }
}
