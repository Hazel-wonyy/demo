package com.timely.demo.product;

import com.timely.demo.product.model.ToolVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductContorller {
    //23.09.19(화)

    private final ProductService ps;

    @PostMapping("/promp/instool")
    public long insMytool(@RequestBody ToolVO vo){
        return ps.insMytool(vo);
    }

}
