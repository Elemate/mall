package com.wq.content.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wq.common.jedis.JedisClient;
import com.wq.common.pojo.EasyuiDataGridResult;
import com.wq.common.utils.JsonUtils;
import com.wq.common.utils.TaotaoResult;
import com.wq.content.service.ContentService;
import com.wq.mapper.TbContentMapper;
import com.wq.pojo.TbContent;
import com.wq.pojo.TbContentExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;
    @Autowired
    private JedisClient jedisClient;

    @Value("${CONTENT_LIST}")
    private String CONTENT_LIST;

    @Override
    public EasyuiDataGridResult getContentListByContentCatId(int page, int rows, long categoryId) {
        PageHelper.startPage(page, rows);

        TbContentExample example = new TbContentExample();
        example.createCriteria().andCategoryIdEqualTo(categoryId);
        List<TbContent> tbContents = contentMapper.selectByExample(example);
        PageInfo info = new PageInfo(tbContents);

        //将返回的数据包装到结果集
        EasyuiDataGridResult result = new EasyuiDataGridResult();
        result.setTotal(info.getTotal());
        result.setRows(tbContents);
        return result;
    }

    @Override
    public TaotaoResult addContent(TbContent tbcontent) {
        TaotaoResult result = new TaotaoResult();
        try {
            tbcontent.setUpdated(new Date());
            tbcontent.setCreated(new Date());
            contentMapper.insert(tbcontent);
            //新增完内容之后，删除缓存
            jedisClient.hdel(CONTENT_LIST,tbcontent.getCategoryId()+"");
        } catch (Exception e){
            e.printStackTrace();
            result.setMsg("新增失败");
            return result;
        }
        result.setStatus(200);
        return result;

    }

    @Override
    public TaotaoResult updateContent(TbContent tbContent) {

        TaotaoResult result = new TaotaoResult();
        try {
            TbContent content  = contentMapper.selectByPrimaryKey(tbContent.getId());
            tbContent.setCreated(content.getCreated());
            tbContent.setUpdated(new Date());
            contentMapper.updateByPrimaryKeyWithBLOBs(tbContent);


        } catch (Exception e){
            e.printStackTrace();
            result.setMsg("更新失败");
            return result;
        }
        result.setStatus(200);
        return result;
    }

    @Override
    public TaotaoResult deleteContent(String ids) {
        TaotaoResult result = new TaotaoResult();
        try {
            String[] idArray = ids.split(",");
            for (String id : idArray){
                contentMapper.deleteByPrimaryKey(Long.parseLong(id));
            }

        } catch (Exception e){
            e.printStackTrace();
            result.setMsg("删除失败");
        }
        result.setStatus(200);
        return result;
    }

    @Override
    public List<TbContent> getContentListByCid(long cid) {

        //先从redis缓存中查找
        try {
            String resultList = jedisClient.hget(CONTENT_LIST,cid+"");
            if (StringUtils.isNoneBlank(resultList)){
                //有的话返回给web层
                List<TbContent> tbContents = JsonUtils.jsonToList(resultList,TbContent.class);
                return tbContents;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        //没有查找数据库
        TbContentExample example = new TbContentExample();
        example.createCriteria().andCategoryIdEqualTo(cid);
        List<TbContent> tbContents = contentMapper.selectByExampleWithBLOBs(example);
        try {
            //然后将结果添加到缓存中
            jedisClient.hset(CONTENT_LIST,cid+"",JsonUtils.objectToJson(tbContents));
        } catch (Exception e){
            e.printStackTrace();
        }
        return tbContents;
    }
}
