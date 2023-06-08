package web.projectTeam.busInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
	@Query(value="SELECT * FROM article", nativeQuery=true)
	Page<ArticleHeader> findArticleHeaders(Pageable pageable);
	
	//@Query(value="DELETE FROM article WHERE num=o", nativeQuery=true)
}
