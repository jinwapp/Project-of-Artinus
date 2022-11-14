package Artinus.server.comment.repository;

import Artinus.server.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select * from comment where board_id = :boardId", nativeQuery = true)
    Page<Comment> findByBoardId(long boardId, Pageable pageable);

}
