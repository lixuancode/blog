package net.blog.w9o.blog.service;

import net.blog.w9o.blog.dto.PaginationDto;
import net.blog.w9o.blog.dto.QuestionDto;
import net.blog.w9o.blog.mapper.QuestionMapper;
import net.blog.w9o.blog.mapper.UserMapper;
import net.blog.w9o.blog.model.Question;
import net.blog.w9o.blog.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    public PaginationDto list(Integer page, Integer size) {
        Integer totalPage;
        PaginationDto paginationDto = new PaginationDto();
        Integer totalCount = questionMapper.count();
        if (totalCount%size==0){
            totalPage=totalCount/size;
        }else{
            totalPage=totalCount/size+1;
        }

        if(page<1){
            page=1;
        }
        if (page>totalPage){
            page = totalPage;
        }
        paginationDto.setPagination(totalPage,page);
        Integer offset = size*(page-1);
        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDto> questionDtoList = new ArrayList<>();


        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
           questionDto.setUser(user);
           questionDtoList.add(questionDto);
        }
        paginationDto.setQuestions(questionDtoList);
        return paginationDto;
    }
    public PaginationDto list(Integer userId, Integer page, Integer size) {
        Integer totalPage;
        PaginationDto paginationDto = new PaginationDto();
        Integer totalCount = questionMapper.countByUserId(userId);

        if (totalCount%size==0){
            totalPage=totalCount/size;
        }else{
            totalPage=totalCount/size+1;
        }

        if(page<1){
            page=1;
        }
        if (page>totalPage){
            page = totalPage;
        }
        paginationDto.setPagination(totalPage,page);

        Integer offset = size*(page-1);
        List<Question> questions = questionMapper.listByUserId(userId,offset,size);
        List<QuestionDto> questionDtoList = new ArrayList<>();


        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        paginationDto.setQuestions(questionDtoList);
        return paginationDto;
    }

    public QuestionDto getById(Integer id) {
        Question question = questionMapper.getById(id);
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question,questionDto);
        User user = userMapper.findById(question.getCreator());
        questionDto.setUser(user);
        return questionDto;
    }

    public void createUpdate(Question question) {
        if (question.getId()==null){
            //首次创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else{
            //编辑
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }
}
