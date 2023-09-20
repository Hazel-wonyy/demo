package com.timely.demo.product;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.timely.demo.common.entity.*;
import com.timely.demo.common.entity.QMytoolEntity;
import com.timely.demo.common.entity.QProductEntity;
import com.timely.demo.common.entity.QProductItemEntity;
import com.timely.demo.common.entity.QProductPlaceholderEntity;
import com.timely.demo.common.entity.QProductSpanEntity;
import com.timely.demo.common.entity.QUserEntity;
import com.timely.demo.product.model.ProductVO;
import com.timely.demo.product.model.ToolVO;
import com.timely.demo.repository.MytoolRepository;
import com.timely.demo.repository.UserToolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.timely.demo.common.entity.QUserEntity.userEntity;
import static com.timely.demo.common.entity.QUserToolEntity.userToolEntity;
import static com.timely.demo.common.entity.QMytoolEntity.mytoolEntity;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final JPAQueryFactory jpaQueryFactory;
    private final QMytoolEntity mt = QMytoolEntity.mytoolEntity;
    private final QProductEntity p = QProductEntity.productEntity;
    private final QProductItemEntity i = QProductItemEntity.productItemEntity;
    private final com.timely.demo.common.entity.QUserEntity u = userEntity;
    private final QProductSpanEntity s = QProductSpanEntity.productSpanEntity;
    private final QProductPlaceholderEntity ph = QProductPlaceholderEntity.productPlaceholderEntity;

    private final MytoolRepository mytoolRepository;
    private final UserToolRepository userToolRepository;
//    private final

//    private final ProductMapper MAPPER;

    //product, product_item 출력
    /*
        select p.pid, i.item_id, p.value, p.label
        from ge_gpt_product p
        join te_gpt_product_item i
        on p.pid = i.pid
        where p.state = 1 and i.state = 1
            i.itme_id = #{id}
    */
//    public ProductVO selProd(Long itmeId) {
//        ProductVO vo = jpaQueryFactory.select(Projections.constructor
//                        (ProductVO.class
//                                , p.pid
//                                , p.value
//                                , p.label
//                                , i.itemId
//                                , i.type
//                                , i.title
//                                , i.sub
//                        ))
//                .from(p).join(i)
//                .on(p.productEntity.pid.eq(i.productItemEntity.pid))
//                .where(p.state.eq(1))
//                .and(i.state.eq(1))
//                .and(i.itemId.eq(itmeId))
//                .orderBy(i.itemId.desc())
//                .fetch();
//        return vo;
//    }


    //placeholder 출력
    /*
        select *
        from ge_gpt_product p
        join te_gpt_product_item i
        on p.pid = i.pid
        join te_gpt_product_placeholder h
        on i.item_id = h.item_id
        where h.state = 1
        and i.item_id = #{itemId}
    */



    //span 출력
    /*
         select *
         from ge_gpt_product p
         join te_gpt_product_item i
         on p.pid = i.pid
         join te_gpt_product_span s
         on i.item_id = s.item_id
         where h.state = 1
         and i.item_id =1
     */


    //사용자가 만든 tool 조건 리스트
//    public ToolVO selMytool(int userId) {
//        return jpaQueryFactory.selectFrom(mt)
//                .where(mt.userId.eq(userId))
//                .fetchOne();
//    }

    //나의 AI 마법사 만들기 생성 결과 insert
//    @Transactional
    public long insResult(ToolVO vo){
        UserToolEntity entity = UserToolEntity.builder()
                .resultId(vo.getResultId())
                .mytoolEntity(new MytoolEntity())
                .userEntity(new UserEntity())
                .body(vo.getBody())
                .cts(LocalDateTime.now())
                .state(1)
                .build();
        userToolRepository.save(entity);
        return vo.getResultId();
    }

    //gpt 검색 결과 저장.
    public List<ToolVO> getResult(long userId) {
        userToolRepository.findAllByUserId(userId);

        List<ToolVO> result = jpaQueryFactory
                .select(Projections.constructor(
                        ToolVO.class,
                        userToolEntity.resultId,
                        mytoolEntity.toolId,
                        userEntity.userId,
                        userToolEntity.body,
                        userToolEntity.cts,
                        userToolEntity.state))
                .from(userToolEntity)
                .join(userEntity).on(userToolEntity.userEntity.userId.eq(userEntity.userId))
                .join(mytoolEntity).on(userToolEntity.mytoolEntity.toolId.eq(mytoolEntity.toolId))
//                .join(userEntity).on(userToolEntity.userId.eq(userEntity.userId))
//                .join(mytoolEntity).on(userToolEntity.toolId.eq(mytoolEntity.toolId))
//                .join(userEntity).on(userEntity.userId.eq(userEntity.userId))
//                .join(mytoolEntity).on(userToolEntity.toolId.eq(mytoolEntity.toolId.intValue()))
                .where(userEntity.userId.eq(userId))
                .orderBy(userToolEntity.cts.desc()) // Corrected orderBy clause
                .fetch();
        return result;
    }


    //나의 AI 마법사 만들기 생성 조건 insert
//    @Transactional
    public long insMytool(ToolVO vo){
        MytoolEntity entity = MytoolEntity.builder()
                .toolId(vo.getToolId())
                .userEntity(new UserEntity())
                .title(vo.getTitle())
                .placeholder(vo.getPlaceholer())
                .span1(vo.getSpan1())
                .span2(vo.getSpan2())
                .span3(vo.getSpan3())
                .span4(vo.getSpan4())
                .span5(vo.getSpan5())
                .cts(LocalDateTime.now())
                .build();
        mytoolRepository.save(entity);
        return entity.getToolId();
    }
}
