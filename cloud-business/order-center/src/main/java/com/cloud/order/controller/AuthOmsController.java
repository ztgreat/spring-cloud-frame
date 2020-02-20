package com.cloud.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.common.annotation.IgnoreAuth;
import com.cloud.common.annotation.SysLog;
import com.cloud.common.constant.AllEnum;
import com.cloud.common.constant.ConstansValue;
import com.cloud.common.entity.oms.OmsCartItem;
import com.cloud.common.entity.oms.OmsOrder;
import com.cloud.common.entity.oms.OmsOrderItem;
import com.cloud.common.entity.oms.UmsMemberReceiveAddress;
import com.cloud.common.entity.ums.UmsMember;
import com.cloud.common.exception.ApiMallPlusException;
import com.cloud.common.feign.MemberFeignClient;
import com.cloud.common.utils.CommonResult;
import com.cloud.common.vo.CartParam;
import com.cloud.common.vo.OrderParam;
import com.cloud.order.mapper.UmsMemberReceiveAddressMapper;
import com.cloud.order.service.IOmsCartItemService;
import com.cloud.order.service.IOmsOrderItemService;
import com.cloud.order.service.IOmsOrderService;
import com.cloud.order.service.IUmsMemberReceiveAddressService;
import com.cloud.order.vo.ConfirmOrderResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: shenzhuan
 * @Date: 2019/4/2 15:02
 * @Description:
 */
@Slf4j
@RestController
@Api(tags = "AuthOmsController", description = "订单管理系统")
@RequestMapping("/auth")
public class AuthOmsController {

    @Autowired
    private IOmsCartItemService cartItemService;
    @Resource
    private IOmsOrderService orderService;
    @Resource
    private IOmsOrderItemService orderItemService;
    @Resource
    private MemberFeignClient memberFeignClient;
    @Resource
    private UmsMemberReceiveAddressMapper addressMapper;
    @Autowired
    private IUmsMemberReceiveAddressService memberReceiveAddressService;

    @ApiOperation("获取订单详情:订单信息、商品信息、操作记录")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public Object detail(@RequestParam(value = "id", required = false) Long id) {
        OmsOrder orderDetailResult = null;
        orderDetailResult = orderService.getById(id);
        OmsOrderItem query = new OmsOrderItem();
        query.setOrderId(id);
        List<OmsOrderItem> orderItemList = orderItemService.list(new QueryWrapper<>(query));
        orderDetailResult.setOrderItemList(orderItemList);
        UmsMember member = memberFeignClient.findById(orderDetailResult.getMemberId());
        if(member!=null){
            orderDetailResult.setBlance(member.getBlance());
        }
        return new CommonResult().success(orderDetailResult);
    }

    @IgnoreAuth
    @SysLog(MODULE = "oms", REMARK = "查询订单列表")
    @ApiOperation(value = "查询订单列表")
    @GetMapping(value = "/order/list")
    public Object orderList(OmsOrder order,
                            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        IPage<OmsOrder> page = null;
        if (order.getStatus()!=null && order.getStatus()==0){
            page = orderService.page(new Page<OmsOrder>(pageNum, pageSize), new QueryWrapper<OmsOrder>().eq("member_id", order.getMemberId()).orderByDesc("create_time").select(ConstansValue.sampleOrderList));
        }else {
            order.setMemberId(order.getMemberId());
            page = orderService.page(new Page<OmsOrder>(pageNum, pageSize), new QueryWrapper<>(order).orderByDesc("create_time").select(ConstansValue.sampleOrderList)) ;

        }
        for (OmsOrder omsOrder : page.getRecords()){
            List<OmsOrderItem> itemList = orderItemService.list(new QueryWrapper<OmsOrderItem>().eq("order_id",omsOrder.getId()).eq("type", AllEnum.OrderItemType.GOODS.code()));
            omsOrder.setOrderItemList(itemList);
        }
        return new CommonResult().success(page);
    }


    @ResponseBody
    @GetMapping("/submitPreview")
    public Object submitPreview(OrderParam orderParam) {
        try {
            ConfirmOrderResult result = orderService.submitPreview(orderParam);
            return new CommonResult().success(result);
        } catch (ApiMallPlusException e) {
            return new CommonResult().failed(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 提交订单
     *
     * @param orderParam
     * @return
     */
    @ApiOperation("根据购物车信息生成订单")
    @RequestMapping(value = "/generateOrder")
    @ResponseBody
    public Object generateOrder(OrderParam orderParam) {
        return orderService.generateOrder(orderParam);
    }

    @ApiOperation("发起拼团")
    @RequestMapping(value = "/addGroup")
    @ResponseBody
    public Object addGroup(OrderParam orderParam) {
        try {
            return new CommonResult().success(orderService.addGroup(orderParam));
        } catch (ApiMallPlusException e) {
            return new CommonResult().failed(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    @ApiOperation("添加商品到购物车")
    @RequestMapping(value = "/addCart")
    @ResponseBody
    public Object addCart(CartParam cartParam) {
        return orderService.addCart(cartParam);
    }

    @ApiOperation("获取某个会员的购物车列表")
    @RequestMapping(value = "/cartList", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam(value = "memberId", required = true) Long memberId) {

        List<OmsCartItem> cartItemList = cartItemService.list(memberId, null);
        return new CommonResult().success(cartItemList);

    }

    @ApiOperation("修改购物车中某个商品的数量")
    @RequestMapping(value = "/update/quantity", method = RequestMethod.GET)
    @ResponseBody
    public Object updateQuantity(@RequestParam Long id,
                                 @RequestParam Integer quantity,
                                 @RequestParam(value = "memberId", required = true) Long memberId) {
        int count = cartItemService.updateQuantity(id, memberId, quantity);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }



    @ApiOperation("删除收货地址")
    @RequestMapping(value = "/deleteAddress")
    @ResponseBody
    public Object delete(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        boolean count = memberReceiveAddressService.removeById(id);
        if (count) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

    @ApiOperation("修改收货地址")
    @RequestMapping(value = "/saveAddress")
    @ResponseBody
    public Object update(UmsMemberReceiveAddress address) {
        boolean count = false;
        if (address.getDefaultStatus() != null && address.getDefaultStatus() == 1) {
            addressMapper.updateStatusByMember(address.getMemberId());
        }
        if (address != null && address.getId() != null) {
            count = memberReceiveAddressService.updateById(address);
        } else {
            count = memberReceiveAddressService.save(address);
        }
        if (count) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

    @IgnoreAuth
    @ApiOperation("显示所有收货地址")
    @RequestMapping(value = "/listAddress", method = RequestMethod.GET)
    @ResponseBody
    public Object listAddress(@RequestParam(value = "memberId", required = false, defaultValue = "0") Long memberId) {

        List<UmsMemberReceiveAddress> addressList = memberReceiveAddressService.list(new QueryWrapper<UmsMemberReceiveAddress>().eq("member_id", memberId));
        return new CommonResult().success(addressList);

    }

    @IgnoreAuth
    @ApiOperation("显示所有收货地址")
    @RequestMapping(value = "/detailAddress", method = RequestMethod.GET)
    @ResponseBody
    public Object getItem(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        UmsMemberReceiveAddress address = memberReceiveAddressService.getById(id);
        return new CommonResult().success(address);
    }

    @IgnoreAuth
    @ApiOperation("显示默认收货地址")
    @RequestMapping(value = "/getItemDefautl", method = RequestMethod.GET)
    @ResponseBody
    public Object getItemDefautl(@RequestParam(value = "memberId", required = false, defaultValue = "0") Long memberId) {
        UmsMemberReceiveAddress address = memberReceiveAddressService.getDefaultItem(memberId);
        return new CommonResult().success(address);
    }

    /**
     * @param id
     * @return
     */
    @ApiOperation("设为默认地址")
    @RequestMapping(value = "/address-set-default")
    @ResponseBody
    public Object setDefault(@RequestParam(value = "id", required = false, defaultValue = "0") Long id
            , @RequestParam(value = "memberId", required = false, defaultValue = "0") Long memberId) {
        int count = memberReceiveAddressService.setDefault(id, memberId);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }
}
