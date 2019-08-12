package net.blog.w9o.blog.cache;

import net.blog.w9o.blog.dto.TagDto;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Tagcache {
    public static List<TagDto> get(){
        List<TagDto>  tagDtos = new ArrayList<>();
        TagDto program = new TagDto();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("JavaScript","Java","PhP","Css","html","node.js","Python","C++","C","goLang","Objective-c","TypeScript","Shell","Swift","C#","Sass","Ruby","Bash","Less","asp.net","Lua","scala","coffeeScript","Rust","ErLang","Perl"));
        tagDtos.add(program);

        TagDto framework = new TagDto();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("Spring","yii","laraVel","express","django","flask","ruby-on-rails","tornado","koa","struts"));
        tagDtos.add(framework);

        TagDto server = new TagDto();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("Linux","nginx","docker","apache","ubuntu","centOs","tomcat","unix","hadoop","windows-server"));
        tagDtos.add(server);

        TagDto dataBase = new TagDto();
        dataBase.setCategoryName("数据库");
        dataBase.setTags(Arrays.asList("MySql","redis","mongodb","sql","oracle","sqlServer","menCached","noSql","postGreSql"));
        tagDtos.add(dataBase);

        TagDto tool = new TagDto();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git","github","visual-studio-code","vim","eclipse","ide","intellij-idea","textMate","hg"));
        tagDtos.add(tool);

        return tagDtos;
    }
    public static String filterInvalid(String tags){
        String[] split = StringUtils.split(tags, ",");
        List<TagDto> tagDtos = get();
        List<String> tagList = tagDtos.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}
