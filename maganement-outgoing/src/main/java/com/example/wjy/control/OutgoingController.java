package com.example.wjy.control;

import com.example.wjy.pojo.OgInfo;
import com.example.wjy.pojo.OgInfoVo;
import com.example.wjy.service.OgInfoService;
import com.example.wjy.vo.Result;
import com.example.wjy.vo.params.ChangeParams;
import com.example.wjy.vo.params.DeleteParams;
import com.example.wjy.vo.params.OgInfoParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("outgoing")
public class OutgoingController {
    @Autowired
    OgInfoService ogInfoService;

    @PostMapping("application")
    public Result apply (@RequestBody OgInfoParams ogInfoParams){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String identityId = (String) request.getAttribute("identityId");
        int userId=Integer.parseInt(identityId);
        return ogInfoService.apply(ogInfoParams,userId);
    }
    @PostMapping("search")
    public Result search(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String identityId = (String) request.getAttribute("identityId");
        int userId=Integer.parseInt(identityId);
        List<OgInfoVo> ogInfoVos =ogInfoService.search(userId);
        return Result.success(ogInfoVos);
    }
    @PostMapping("modification")
    public Result change(@RequestBody ChangeParams changeParams){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String identityId = (String) request.getAttribute("identityId");
        int userId=Integer.parseInt(identityId);
        return ogInfoService.change(changeParams,userId);
    }
    @PostMapping("delete")
    public Result delete(@RequestParam DeleteParams deleteParams){
        return ogInfoService.delete(deleteParams.getIndexId());
    }

}
