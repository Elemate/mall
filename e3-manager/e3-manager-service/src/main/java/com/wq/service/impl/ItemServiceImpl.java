package com.wq.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wq.common.jedis.JedisClient;
import com.wq.common.pojo.EasyuiDataGridResult;
import com.wq.common.utils.IDUtils;
import com.wq.common.utils.JsonUtils;
import com.wq.common.utils.TaotaoResult;
import com.wq.mapper.TbItemDescMapper;
import com.wq.mapper.TbItemMapper;
import com.wq.mapper.TbItemParamItemMapper;
import com.wq.pojo.*;
import com.wq.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Autowired
    private JmsTemplate jmsTemplate;            //根据类型装配
    @Resource
    private Destination topicDestination;       //根据spring中bean的id装配

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_ITEM_PRE}")
    private String REDIS_ITEM_PRE;  //redis缓存商品详情信息的key前缀
    @Value("${ITEM_CACHE_EXPIRE}")  //过期时间
    private int ITEM_CACHE_EXPIRE;


    @Override
    public TbItem getItemById(long id) {
        //先在redis查找，ture就返回，没有查找数据库

        try {
            String tbItem = jedisClient.get(REDIS_ITEM_PRE+":"+id+":BASE");
            if (StringUtils.isNotBlank(tbItem)){
                TbItem tbItem1 = JsonUtils.jsonToPojo(tbItem,TbItem.class);
                return tbItem1;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        TbItem item = itemMapper.selectByPrimaryKey(id);

        try {
            //向redis添加缓存
            jedisClient.set(REDIS_ITEM_PRE+":"+id+":BASE",JsonUtils.objectToJson(item));
            //设置过期时间
            jedisClient.expire(REDIS_ITEM_PRE+":"+id+":BASE",ITEM_CACHE_EXPIRE);
        } catch (Exception e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public TbItemDesc getItemDescByItemId(long itemId) {

        try {
            String itemDescString = jedisClient.get(REDIS_ITEM_PRE+":"+itemId+":DESC");
            if (StringUtils.isNotBlank(itemDescString)){
                TbItemDesc itemDesc = JsonUtils.jsonToPojo(itemDescString,TbItemDesc.class);
                return itemDesc;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);

        try {
            jedisClient.set(REDIS_ITEM_PRE+":"+itemId+":DESC",JsonUtils.objectToJson(itemDesc));
            jedisClient.expire(REDIS_ITEM_PRE+":"+itemId+":DESC",ITEM_CACHE_EXPIRE);
        }catch (Exception e){
            e.printStackTrace();
        }

        return itemDesc;
    }

    @Override
    public EasyuiDataGridResult getItemList(int page, int rows) {

        PageHelper.startPage(page,rows);
        TbItemExample example = new TbItemExample();
        List<TbItem> list = itemMapper.selectByExample(example);

        //获取分页信息
        PageInfo info = new PageInfo(list);

        //创建返回数据
        EasyuiDataGridResult result = new EasyuiDataGridResult();
        result.setRows(list);
        result.setTotal(info.getTotal());
        return result;
    }

    @Override
    public TaotaoResult addItem(TbItem tbItem, String desc) {
        Date date = new Date();
        //生成商品id
        final long itemId = IDUtils.getItemId();
        tbItem.setId(itemId);
        //设置商品状态 '商品状态，1-正常，2-下架，3-删除',
        tbItem.setStatus((byte) 1);
        tbItem.setCreated(date);
        tbItem.setUpdated(date);
        itemMapper.insert(tbItem);

        //添加商品描述信息对象
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        itemDescMapper.insert(itemDesc);

        //向search服务发送商品添加的消息
        jmsTemplate.send(topicDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(itemId + "");
                return textMessage;
            }
        });

        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult getItemDesc(long id) {
        TbItemDescExample example = new TbItemDescExample();
        example.createCriteria().andItemIdEqualTo(id);
        List<TbItemDesc> itemDesc = itemDescMapper.selectByExampleWithBLOBs(example);
        TaotaoResult result = new TaotaoResult(itemDesc.get(0).getItemDesc());
        result.setStatus(200);
        return result;
    }

    /*
    获取商品规格描述
    */
    @Override
    public TaotaoResult getItemParamData(long itemId) {
        TaotaoResult result = null;
        try {
            TbItemParamItemExample example = new TbItemParamItemExample();
            example.createCriteria().andItemIdEqualTo(itemId);
            List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
            if (list!=null && list.size()>0) {
                result = new TaotaoResult(list.get(0));
                result.setStatus(200);
                return result;
            }
        }catch (Exception e){
            result.setStatus(500);
        }
        return result;
    }

    /*
        更新商品信息
    */
    @Override
    public TaotaoResult updateItem(TbItem item, String desc, String itemParamItem, long itemParamID) {
        TaotaoResult result = null;
        try {
            //获取原始Item对象
            TbItem orginItem = itemMapper.selectByPrimaryKey(item.getId());
            item.setCreated(orginItem.getCreated());
            item.setUpdated(new Date());
            item.setStatus((byte) 1);
            itemMapper.updateByPrimaryKey(item);

            //根据商品id获取商品描述表,并修改商品描述表
            TbItemDescExample itemDescExample = new TbItemDescExample();
            itemDescExample.createCriteria().andItemIdEqualTo(item.getId());
            List<TbItemDesc> itemDescs = itemDescMapper.selectByExampleWithBLOBs(itemDescExample);
            TbItemDesc itemDesc = itemDescs.get(0);
            itemDesc.setItemDesc(desc);
            itemDescMapper.updateByExample(itemDesc,itemDescExample);

            //修改商品规格表
            TbItemParamItem itemParamItem1 = itemParamItemMapper.selectByPrimaryKey(itemParamID);
            itemParamItem1.setParamData(itemParamItem);
            itemParamItemMapper.updateByPrimaryKeyWithBLOBs(itemParamItem1);

        } catch (Exception e){
            e.printStackTrace();
            result.setStatus(500);
            result.setMsg("商品更新失败");

        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteItem(String ids) {
        String[] idArray =  ids.split(",");
       try {
           for (String id : idArray){
               //删除商品信息
               itemMapper.deleteByPrimaryKey(Long.parseLong(id));

               //删除商品描述表
               TbItemDescExample itemDescExample = new TbItemDescExample();
               itemDescExample.createCriteria().andItemIdEqualTo(Long.parseLong(id));
               itemDescMapper.deleteByExample(itemDescExample);

               //删除商品规格表
               TbItemParamItemExample itemParamItemExample = new TbItemParamItemExample();
               itemParamItemExample.createCriteria().andItemIdEqualTo(Long.parseLong(id));
               itemParamItemMapper.deleteByExample(itemParamItemExample);
           }
       } catch (Exception e){
           e.printStackTrace();
           TaotaoResult result = new TaotaoResult();
           result.setStatus(500);
           return result;
       }
        return TaotaoResult.ok();
    }


}
