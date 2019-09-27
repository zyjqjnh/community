package life.majiang.community.service;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
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

  public PaginationDTO list(Integer page, Integer size) {

    PaginationDTO paginationDTO = new PaginationDTO();
    Integer totalPage;

    Integer totalCount = questionMapper.count();

    if (totalCount % size == 0) {
      totalPage = totalCount / size;
    } else {
      totalPage = totalCount / size + 1;
    }

    if (page < 1) {
      page = 1;
    }
    if (page > totalPage) {
      page = totalPage;
    }

    paginationDTO.setPagination(totalPage, page);
    Integer offset = page < 1 ? 0 : size * (page - 1);

    List<Question> questions = questionMapper.select(offset, size);
    List<QuestionDTO> questionDTOList = new ArrayList<>();

    for (Question question : questions) {
      User user = userMapper.selectByPrimaryKey(question.getCreator());
      QuestionDTO questionDTO = new QuestionDTO();
      BeanUtils.copyProperties(question, questionDTO);
      questionDTO.setUser(user);
      questionDTOList.add(questionDTO);
    }

    paginationDTO.setData(questionDTOList);

    return  paginationDTO;
  }

  public PaginationDTO list(Long userId, Integer page, Integer size) {
    PaginationDTO paginationDTO = new PaginationDTO();

    Integer totalPage;

    Integer totalCount = questionMapper.countByUserId(userId);

    totalPage = setTotalPage(totalCount, size);

    if (page < 1) {
      page = 1;
    }
    if (page > totalPage) {
      page = totalPage;
    }

    paginationDTO.setPagination(totalPage, page);
    Integer offset = page < 1 ? 0 : size * (page - 1);

    List<Question> questions = questionMapper.selectByUserId(userId, offset, size);
    List<QuestionDTO> questionDTOList = new ArrayList<>();

    for (Question question : questions) {
      User user = userMapper.selectByPrimaryKey(question.getCreator());
      QuestionDTO questionDTO = new QuestionDTO();
      BeanUtils.copyProperties(question, questionDTO);
      questionDTO.setUser(user);
      questionDTOList.add(questionDTO);
    }

    paginationDTO.setData(questionDTOList);

    return paginationDTO;
  }

  private Integer setTotalPage(Integer totalCount, Integer size) {
    int totalPage;
    if (totalCount % size == 0) {
      totalPage = totalCount / size;
    } else {
      totalPage = totalCount / size + 1;
    }
    return totalPage;
  }
}
