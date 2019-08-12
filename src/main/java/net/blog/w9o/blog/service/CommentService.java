package net.blog.w9o.blog.service;

import net.blog.w9o.blog.dto.CommentDto;
import net.blog.w9o.blog.enums.CommentTypeEnum;
import net.blog.w9o.blog.enums.NotificationStatusEnum;
import net.blog.w9o.blog.enums.NotificationTypeEnum;
import net.blog.w9o.blog.exception.CustomizeErrorCode;
import net.blog.w9o.blog.exception.CustomizeException;
import net.blog.w9o.blog.mapper.*;
import net.blog.w9o.blog.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private NotificationMapper notificationMapper;
    @Transactional//同步事务
    public void insert(Comment comment, User commentator) {
        if (comment.getParentId()==null || comment.getParentId()==0){
            throw  new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType()==null || !CommentTypeEnum.isExist(comment.getType())){
            throw  new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType()==CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment primaryKey = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (primaryKey==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            Question question = questionMapper.selectByPrimaryKey(primaryKey.getParentId());
            if (question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            commentMapper.insert(comment);//写入评论
            //增加二级评论数
            Comment parentComment  = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentExtMapper.incCommentCount(parentComment);

            //回复评论的通知
            createNotify(comment,primaryKey.getCommentator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_COMMENT, question.getId());
        }else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            question.setCommentCount(1);
            commentMapper.insert(comment);//写入评论
            questionExtMapper.incCommentCount(question);//增加回复数
            //回复问题的通知
            createNotify(comment,question.getCreator(),commentator.getName(), question.getTitle(),NotificationTypeEnum.REPLY_QUESTION, question.getId());
        }
    }
    private void createNotify(Comment comment, Long recevire, String notifierName, String outerTitle, NotificationTypeEnum notificationType, Long outerId){
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());//时间
        notification.setType(notificationType.getType());//通知类型
        notification.setOuterid(outerId);//文章id
        notification.setNotifier(comment.getCommentator());//发送通知人
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());//阅读状态
        notification.setReceiver(recevire);//接收通知人
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }

    public List<CommentDto> listByTargetId(Long id, CommentTypeEnum type) {
        CommentExample example = new CommentExample();
        example.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(type.getType());
        example.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(example);
        if (comments.size()==0){
            return new ArrayList<>();
        }
        //获取去重的评论人 lambda表达式
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        //获取评论人转化为Map
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转换comment为commentDto
        List<CommentDto> commentDtos = comments.stream().map(comment -> {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(comment,commentDto);
            commentDto.setUser(userMap.get(comment.getCommentator()));
            return commentDto;
        }).collect(Collectors.toList());
        return commentDtos;
    }
}
