package Artinus.server.reply.repository;

import Artinus.server.comment.entity.Comment;
import Artinus.server.reply.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    @Query(value = "select * from reply where comment_id = :commentId", nativeQuery = true)
    Page<Reply> findByCommentId(long commentId, Pageable pageable);
}
