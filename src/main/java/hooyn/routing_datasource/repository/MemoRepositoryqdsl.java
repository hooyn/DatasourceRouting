package hooyn.routing_datasource.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hooyn.routing_datasource.domain.Memo;
import hooyn.routing_datasource.domain.QMemo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemoRepositoryqdsl {

    private final JPAQueryFactory queryFactory;

    public List<Memo> searchMemo(){
        return queryFactory
                .selectFrom(QMemo.memo)
                .fetch();
    }
}
