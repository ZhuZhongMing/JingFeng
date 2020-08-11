package org.jeecg.modules.jf.controller;

import java.util.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.micrometer.core.instrument.util.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.jf.entity.*;
import org.jeecg.modules.jf.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.jf.vo.BomVO;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description: 物料BOM
 * @Author: jeecg-boot
 * @Date: 2019-11-10
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "物料BOM")
@RestController
@RequestMapping("/system/mBom")
public class MBomController {
    @Autowired
    private IMBomService mBomService;
    @Autowired
    private IMMaterialService iMMaterialService;
    @Autowired
    private IMOrderlistService iMOrderListService;
    @Autowired
    private IMProductMapService iMProductMapService;
    @Autowired
    private IMProductTaskOrderService iMProductTaskOrderService;

    /**
     * 根据订单号查询产品及其对应BOM
     *
     * @param id  订单编号
     * @param req
     * @return
     */
    @GetMapping(value = "/BomByMapId")
    public Result<List<MBom>> BomByMapId(String id, HttpServletRequest req) {
        Result<List<MBom>> result = new Result<List<MBom>>();

        //List<BomVO> arList = new ArrayList<BomVO>();

        QueryWrapper<MBom> queryWrapper = new QueryWrapper<MBom>();
        queryWrapper.eq("parentid", id);
        List<MBom> list = mBomService.list(queryWrapper);//产品组成BOM
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }


    /**
     * 根据订单号查询产品及其对应BOM
     *
     * @param id  订单编号
     * @param req
     * @return
     */
    @GetMapping(value = "/BomById")
    public Result<List<BomVO>> BomById(String id, HttpServletRequest req) {
        Result<List<BomVO>> result = new Result<List<BomVO>>();

        List<BomVO> arList = new ArrayList<BomVO>();

        /*根据订单号查出所有明细*/
        List<MOrderlist> ar = iMOrderListService.list(new QueryWrapper<MOrderlist>().eq("orderid", id));
        if (ar.size() <= 0) {
            /*未设置明细*/
            result.setSuccess(false);
        } else {
            try {

                for (MOrderlist orderlist : ar) {

                    if (StringUtils.isBlank(orderlist.getMaterialencode())) {
                        result.setSuccess(false);
                    } else {
						/*QueryWrapper<MBom> queryWrapper = new QueryWrapper<MBom>();
						queryWrapper.eq("materialencode",orderlist.getMaterialencode())
								.or()
								.eq("parentid",orderlist.getMaterialencode());
						List<MBom> list = mBomService.list(queryWrapper);//产品组成BOM*/

                        MProductMap mpm = iMProductMapService.getById(orderlist.getMaterialencode());//产品信息
                        mpm.setNumber(orderlist.getOrdernum());

                        /*上刀*/
                        MMaterial a1 = iMMaterialService.getById(mpm.getTopblade());
                        QueryWrapper<MBom> q1 = new QueryWrapper<MBom>();
                        //q1.eq("materialencode",orderlist.getMaterialencode()).and(q1.eq("parentid",mpm.getTopblade()));
                        q1.eq("parentid", orderlist.getMaterialencode()).and(wrapper -> wrapper.eq("materialencode", mpm.getTopblade()));
                        //log.info("ar1："+orderlist.getMaterialencode());
                        //log.info("ar2："+mpm.getTopblade());
                        List<MBom> list1 = mBomService.list(q1);//产品组成BOM
                        //log.info("ar："+list1);
                        a1.setNumber(list1.get(0).getNumber());//所需数量

                        /*下刀*/
                        MMaterial a2 = iMMaterialService.getById(mpm.getBottomblade());
                        QueryWrapper<MBom> q2 = new QueryWrapper<MBom>();
                       /* q2.eq("materialencode",orderlist.getMaterialencode());
                        q2.eq("parentid",mpm.getBottomblade());*/
                        q2.eq("parentid", orderlist.getMaterialencode()).and(wrapper -> wrapper.eq("materialencode", mpm.getBottomblade()));
                        List<MBom> list2 = mBomService.list(q2);//产品组成BOM
                        a2.setNumber(list2.get(0).getNumber());//所需数量

                        /*连接片*/
                        MMaterial a3 = iMMaterialService.getById(mpm.getConnectionblade());
                        QueryWrapper<MBom> q3 = new QueryWrapper<MBom>();
                        /*q3.eq("materialencode",orderlist.getMaterialencode());
                        q3.eq("parentid",mpm.getConnectionblade());*/
                        q3.eq("parentid", orderlist.getMaterialencode()).and(wrapper -> wrapper.eq("materialencode", mpm.getConnectionblade()));
                        List<MBom> list3 = mBomService.list(q3);//产品组成BOM
                        a3.setNumber(list3.get(0).getNumber());//所需数量

                        /*传动片*/
                        MMaterial a4 = iMMaterialService.getById(mpm.getDriverblade());
                        QueryWrapper<MBom> q4 = new QueryWrapper<MBom>();
                        /*q4.eq("materialencode",orderlist.getMaterialencode());
                        q4.eq("parentid",mpm.getDriverblade());*/
                        q4.eq("parentid", orderlist.getMaterialencode()).and(wrapper -> wrapper.eq("materialencode", mpm.getDriverblade()));
                        List<MBom> list4 = mBomService.list(q4);//产品组成BOM
                        a4.setNumber(list4.get(0).getNumber());//所需数量

                        /*铆钉*/
                        MMaterial a5 = iMMaterialService.getById(mpm.getRivet());
                        QueryWrapper<MBom> q5 = new QueryWrapper<MBom>();
                        /*q5.eq("materialencode",orderlist.getMaterialencode());
                        q5.eq("parentid",mpm.getRivet());*/
                        q5.eq("parentid", orderlist.getMaterialencode()).and(wrapper -> wrapper.eq("materialencode", mpm.getRivet()));
                        List<MBom> list5 = mBomService.list(q5);//产品组成BOM
                        a5.setNumber(list5.get(0).getNumber());//所需数量

                        Map<String, MMaterial> mList = new HashMap<String, MMaterial>();
                        mList.put("t", a1);
                        mList.put("b", a2);
                        mList.put("c", a3);
                        mList.put("d", a4);
                        mList.put("r", a5);

                        BomVO bom = new BomVO();
                        /*bom.setMBomList(list);*/
                        bom.setMProductMap(mpm);
                        bom.setMMaterialList(mList);

                        arList.add(bom);

                        result.setSuccess(true);
                    }

                }

                result.setResult(arList);

            } catch (Exception e) {
                result.setSuccess(false);
            }

        }

        return result;
    }


    /**
     * 根据任务单查询产品及其对应BOM
     *
     * @param id  任务单编号
     * @param req
     * @return
     */
    @GetMapping(value = "/BomByTaskid")
    public Result<List<BomVO>> listByTaskid(String id, HttpServletRequest req) {
        Result<List<BomVO>> result = new Result<List<BomVO>>();
        //QueryWrapper<MBom> queryWrapper = new QueryWrapper<MBom>();
        //queryWrapper.eq("taskid",mBom.getTaskid());
		/* List<MBom> pageList = mBomService.List(mBom.getTaskid());

		 List<BomVO> list = new ArrayList<BomVO>();
		 for (MBom n : pageList) {
			 BomVO v = new BomVO();
			 QueryWrapper<MMaterial> q = new QueryWrapper();
			 q.eq("materialtypeencode",n.getParentid());
			 List<MMaterial> pList = iMMaterialService.list(q);//父项
			 QueryWrapper<MMaterial> qq = new QueryWrapper();
			 qq.eq("materialtypeencode",n.getChildid());
			 List<MMaterial> cList = iMMaterialService.list(qq);//子项
			 //log.info("clist:"+cList);
			 v.setMbom(n);
			 v.setParentList(pList);
			 v.setChildList(cList);
			 list.add(v);
		 }*/

        List<BomVO> arList = new ArrayList<BomVO>();
        /*根据订单号查出所有明细*/
        //List<MOrderlist> ar = iMOrderListService.list(new QueryWrapper<MOrderlist>().eq("orderid",id));
        MProductTaskOrder taskorder = iMProductTaskOrderService.getById(id);

        try {

            if (StringUtils.isBlank(taskorder.getMaterialencodeid())) {
                result.setSuccess(false);
            } else {

                MProductMap mpm = iMProductMapService.getById(taskorder.getMaterialencodeid());//产品信息

                mpm.setNumber(taskorder.getProductnumber().intValue());
                log.info("id   " + mpm);

                /*上刀*/
                MMaterial a1 = iMMaterialService.getById(mpm.getTopblade());
                QueryWrapper<MBom> q1 = new QueryWrapper<MBom>();
                //q1.eq("materialencode",orderlist.getMaterialencode()).and(q1.eq("parentid",mpm.getTopblade()));
                q1.eq("parentid", taskorder.getMaterialencodeid()).and(wrapper -> wrapper.eq("materialencode", mpm.getTopblade()));
                //log.info("ar1："+orderlist.getMaterialencode());
                //log.info("ar2："+mpm.getTopblade());
                List<MBom> list1 = mBomService.list(q1);//产品组成BOM
                //log.info("ar："+list1);
                a1.setNumber(list1.get(0).getNumber());//所需数量

                /*下刀*/
                MMaterial a2 = iMMaterialService.getById(mpm.getBottomblade());
                QueryWrapper<MBom> q2 = new QueryWrapper<MBom>();
                       /* q2.eq("materialencode",orderlist.getMaterialencode());
                        q2.eq("parentid",mpm.getBottomblade());*/
                q2.eq("parentid", taskorder.getMaterialencodeid()).and(wrapper -> wrapper.eq("materialencode", mpm.getBottomblade()));
                List<MBom> list2 = mBomService.list(q2);//产品组成BOM
                a2.setNumber(list2.get(0).getNumber());//所需数量

                /*连接片*/
                MMaterial a3 = iMMaterialService.getById(mpm.getConnectionblade());
                QueryWrapper<MBom> q3 = new QueryWrapper<MBom>();
                        /*q3.eq("materialencode",orderlist.getMaterialencode());
                        q3.eq("parentid",mpm.getConnectionblade());*/
                q3.eq("parentid", taskorder.getMaterialencodeid()).and(wrapper -> wrapper.eq("materialencode", mpm.getConnectionblade()));
                List<MBom> list3 = mBomService.list(q3);//产品组成BOM
                a3.setNumber(list3.get(0).getNumber());//所需数量

                /*传动片*/
                MMaterial a4 = iMMaterialService.getById(mpm.getDriverblade());
                QueryWrapper<MBom> q4 = new QueryWrapper<MBom>();
                        /*q4.eq("materialencode",orderlist.getMaterialencode());
                        q4.eq("parentid",mpm.getDriverblade());*/
                q4.eq("parentid", taskorder.getMaterialencodeid()).and(wrapper -> wrapper.eq("materialencode", mpm.getDriverblade()));
                List<MBom> list4 = mBomService.list(q4);//产品组成BOM
                a4.setNumber(list4.get(0).getNumber());//所需数量

                /*铆钉*/
                MMaterial a5 = iMMaterialService.getById(mpm.getRivet());
                QueryWrapper<MBom> q5 = new QueryWrapper<MBom>();
                        /*q5.eq("materialencode",orderlist.getMaterialencode());
                        q5.eq("parentid",mpm.getRivet());*/
                q5.eq("parentid", taskorder.getMaterialencodeid()).and(wrapper -> wrapper.eq("materialencode", mpm.getRivet()));
                List<MBom> list5 = mBomService.list(q5);//产品组成BOM
                a5.setNumber(list5.get(0).getNumber());//所需数量

                Map<String, MMaterial> mList = new HashMap<String, MMaterial>();
                mList.put("t", a1);
                mList.put("b", a2);
                mList.put("c", a3);
                mList.put("d", a4);
                mList.put("r", a5);

                BomVO bom = new BomVO();
                /*bom.setMBomList(list);*/
                bom.setMProductMap(mpm);
                bom.setMMaterialList(mList);

                arList.add(bom);

                result.setSuccess(true);
            }


            result.setResult(arList);

        } catch (Exception e) {
            result.setSuccess(false);
        }


        return result;
    }


    /**
     * 分页列表查询
     *
     * @param mBom
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "物料BOM-分页列表查询")
    @ApiOperation(value = "物料BOM-分页列表查询", notes = "物料BOM-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<MBom>> queryPageList(MBom mBom,
                                             @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                             @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                             HttpServletRequest req) {
        Result<IPage<MBom>> result = new Result<IPage<MBom>>();
        QueryWrapper<MBom> queryWrapper = QueryGenerator.initQueryWrapper(mBom, req.getParameterMap());
        Page<MBom> page = new Page<MBom>(pageNo, pageSize);
        IPage<MBom> pageList = mBomService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加
     *
     * @param mBom
     * @return
     */
    @AutoLog(value = "物料BOM-添加")
    @ApiOperation(value = "物料BOM-添加", notes = "物料BOM-添加")
    @PostMapping(value = "/add")
    public Result<MBom> add(@RequestBody MBom mBom) {
        Result<MBom> result = new Result<MBom>();
        try {
            mBomService.save(mBom);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    /**
     * 编辑
     *
     * @param mBom
     * @return
     */
    @AutoLog(value = "物料BOM-编辑")
    @ApiOperation(value = "物料BOM-编辑", notes = "物料BOM-编辑")
    @PutMapping(value = "/edit")
    public Result<MBom> edit(@RequestBody MBom mBom) {
        //log.info("canshu:   "+mBom);
        Result<MBom> result = new Result<MBom>();
        MBom mBomEntity = mBomService.getById(mBom.getId());
        if (mBomEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = mBomService.updateById(mBom);
            //TODO 返回false说明什么？
            if (ok) {
                result.success("修改成功!");
            }
        }

        return result;
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "物料BOM-通过id删除")
    @ApiOperation(value = "物料BOM-通过id删除", notes = "物料BOM-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        try {
            mBomService.removeById(id);
        } catch (Exception e) {
            log.error("删除失败", e.getMessage());
            return Result.error("删除失败!");
        }
        return Result.ok("删除成功!");
    }


    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "物料BOM-批量删除")
    @ApiOperation(value = "物料BOM-批量删除", notes = "物料BOM-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<MBom> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<MBom> result = new Result<MBom>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.mBomService.removeByIds(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "物料BOM-通过id查询")
    @ApiOperation(value = "物料BOM-通过id查询", notes = "物料BOM-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<MBom> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<MBom> result = new Result<MBom>();
        MBom mBom = mBomService.getById(id);
        if (mBom == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(mBom);
            result.setSuccess(true);
        }
        return result;
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response) {
        // Step.1 组装查询条件
        QueryWrapper<MBom> queryWrapper = null;
        try {
            String paramsStr = request.getParameter("paramsStr");
            if (oConvertUtils.isNotEmpty(paramsStr)) {
                String deString = URLDecoder.decode(paramsStr, "UTF-8");
                MBom mBom = JSON.parseObject(deString, MBom.class);
                queryWrapper = QueryGenerator.initQueryWrapper(mBom, request.getParameterMap());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        List<MBom> pageList = mBomService.list(queryWrapper);
        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "物料BOM列表");
        mv.addObject(NormalExcelConstants.CLASS, MBom.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("物料BOM列表数据", "导出人:Jeecg", "导出信息"));
        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        return mv;
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<MBom> listMBoms = ExcelImportUtil.importExcel(file.getInputStream(), MBom.class, params);
                mBomService.saveBatch(listMBoms);
                return Result.ok("文件导入成功！数据行数:" + listMBoms.size());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return Result.error("文件导入失败:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.ok("文件导入失败！");
    }

}
