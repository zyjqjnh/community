package life.majiang.community.mapper;

import life.majiang.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface QuestionMapper {

  @Insert("insert into question (title, description, gmt_create, gmt_modified, creator, tag) values (#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{tag})")
  int insert(Question record);

  @Select("select * from question limit #{offset}, #{size}")
  List<Question> select(@Param("offset") int offset, @Param("size") int size);

  @Select("select count(1) from question")
  Integer count();

  @Select("select * from question where creator = ${userId} limit #{offset}, #{size}")
  List<Question> selectByUserId(@Param("userId") Long userId, @Param("offset") int offset, @Param("size") int size);

  @Select("select count(1) from question where creator = #{userId}")
  Integer countByUserId(Long userId);

  @Select("select * from question where id = #{id}")
  Question selectByPrimaryKey(Long id);
}
