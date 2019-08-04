package net.blog.w9o.blog.dto;



import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class PaginationDto {
    private List<QuestionDto> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;
    public void setPagination(Integer totalCount, Integer page, Integer size) {
        //获取总页数
        if (totalCount%size==0){
            totalPage=totalCount/size;
        }else{
            totalPage=totalCount/size+1;
        }

        if(page<1){
            page=1;
        }
        if(page>totalPage){
            page=totalPage;
        }

        this.page=page;
        pages.add(page);
        for (int i=1;i<=3;i++){
            if (page-i>0){
                pages.add(0,page-i);
            }
            if (page+i<=totalPage){
                pages.add(page+i);
            }
        }
        //是否展示上一页
        if (page==1){
            showPrevious=false;
        }else{
            showPrevious=true;
        }
        //是否展示下一页
        if (page==totalPage){
            showNext=false;
        }else{
            showNext=true;
        }
        //是否展示回首页
        if (pages.contains(1)){
            showFirstPage=false;
        }else{
            showFirstPage=true;
        }
        //是否展示尾页
        if (pages.contains(totalPage)){
            showEndPage=false;
        }else{
            showEndPage=true;

        }
    }
}
