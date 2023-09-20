package com.timely.demo.product;

import com.timely.demo.common.entity.UserToolEntity;
import com.timely.demo.product.model.ToolVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductContorller {
    //23.09.19(화)

    private final ProductService ps;

    //나의 AI 마법사 만들기 생성 조건 저장
    @PostMapping("/promp/instool")
    public long insMytool(@RequestBody ToolVO vo){
        return ps.insMytool(vo);
    }


    //나의 AI 마법사 만들기 생성 결과 저장
    @PostMapping("/promp/insresult")
    public long insResult(@RequestBody ToolVO vo){
        return ps.insResult(vo);
    }

    //ajax 비동기통신
    @PostMapping("/promp/myresult")
    @ResponseBody
    public List<ToolVO> getResult(long userId) {
        return ps.getResult(userId);
    }
}
