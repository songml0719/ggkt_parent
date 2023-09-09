package com.atguigu.ggkt.vod.service.impl;


import com.atguigu.ggkt.model.vod.VideoVisitor;
import com.atguigu.ggkt.vo.vod.VideoVisitorCountVo;
import com.atguigu.ggkt.vod.mapper.VideoVisitorMapper;
import com.atguigu.ggkt.vod.service.VideoVisitorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 视频来访者记录表 服务实现类
 * </p>
 *
 * @author songml
 * @since 20230401
 */
@Service
public class VideoVisitorServiceImpl extends ServiceImpl<VideoVisitorMapper, VideoVisitor> implements VideoVisitorService {

    @Override
    public Map<String, Object> findCount(Long courseId, String startDate, String endDate) {
        List<VideoVisitorCountVo> videoVisitorVoList = baseMapper.findCount(courseId, startDate, endDate);
        Map<String, Object> map = new HashMap<>();
        List<String> dateList = videoVisitorVoList.stream().map(VideoVisitorCountVo::getJoinTime).collect(Collectors.toList());
        List<Integer> countList = videoVisitorVoList.stream().map(VideoVisitorCountVo::getUserCount).collect(Collectors.toList());
        map.put("xData", dateList);
        map.put("yData", countList);
        return map;
    }
}
