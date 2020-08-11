<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <!--<a-button type="primary" icon="download" @click="handleExportXls('车间计划摘要')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>-->
      <!--<a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>-->
    </div>

    <!-- table区域-begin -->
    <div>
      <!--<div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>-->

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">
        
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="图片不存在" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="uploadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                  <a @click="showModalLook($event,record)">计划详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <mShopplanabstract-modal ref="modalForm" @ok="modalFormOk" v-if="hackReset"></mShopplanabstract-modal>


    <a-modal
      title="计划详情"
      :visible="visiblePlanList"
      @cancel="handleClose"
      :footer="null"
      :width="1200"
    >


      <page-layout :title="title">
        <a-card :bordered="false">
          <detail-list title="车间计划详情">

            <detail-list-item term="计划编号">{{id}}</detail-list-item>

            <detail-list-item term="部门编号">{{datasource.deptid}}</detail-list-item>
            <detail-list-item term="订单号">{{datasource.orderid}}</detail-list-item>

            <detail-list-item term="创建人">{{datasource.createBy}}</detail-list-item>
            <detail-list-item term="创建时间">{{datasource.createTime}}</detail-list-item>
            <detail-list-item term="备注">{{datasource.disp}}</detail-list-item>

          </detail-list>

          <a-divider style="margin-bottom: 24px"/>

          <div class="title">车间计划明细</div>
          <!-- v-if="order.data.length > 0" -->
          <s-table
            v-if="hackReset && order.data.length > 0"
            style="margin-bottom: 24px"
            :columns="orderListColumns"
            :data="func">
          </s-table>

        </a-card>

      </page-layout>

<!--{{order.data}}-->

    </a-modal>



  </a-card>
</template>

<script>

  /*import { MyJeecgListMixin } from '@/mixins/MyJeecgListMixin'*/
  import MShopplanabstractModal from './modules/MShopplanabstractModal'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  import { filterObj } from '@/utils/util';
  import { deleteAction, getAction,downFile } from '@/api/manage'
  import Vue from 'vue'
  import { ACCESS_TOKEN } from "@/store/mutation-types"


  import pick from 'lodash.pick'
  import { FormTypes,getRefPromise } from '@/utils/JEditableTableUtil'
  import JDate from '@/components/jeecg/JDate'
  import JSelectDepart from '@/components/jeecgbiz/JSelectDepart'
  import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JEditableTable from '@/components/jeecg/JEditableTable'
  import { VALIDATE_NO_PASSED, validateFormAndTables } from '@/utils/JEditableTableUtil'
  import { httpAction,postAction } from '@/api/manage'

  import PageLayout from '@/components/page/PageLayout'
  import STable from '@/components/table/'
  import DetailList from '@/components/tools/DetailList'
  import ABadge from "ant-design-vue/es/badge/Badge"
  const DetailListItem = DetailList.Item

  export default {
    name: "MShopplanabstractList",
    /*mixins:[MyJeecgListMixin],*/
    components: {
      MShopplanabstractModal,
      JDate,
      JSelectDepart,
      JSelectUserByDep,
      JDictSelectTag,
      JEditableTable,
      PageLayout,
      ABadge,
      DetailList,
      DetailListItem,
      STable
    },
    data () {
      return {
        hackSet:false,

        order:{
          pageSize: 10,
          pageNo: 1,
          totalCount: 0,
          totalPage:0,
          data:[]
        },
        orderListColumns: [
          {
            title: '物料名称',
            dataIndex: 'productname',
            key: 'productname'
          },
          {
            title: '车间',
            dataIndex: 'wsname',
            key: 'wsname'
          },
          {
            title: '数量',
            dataIndex: 'number',
            key: 'number'
          },
          {
            title: '订单明细编号',
            dataIndex: 'orderid',
            key: 'orderid'
          },
        ],
        title: '计划详情',
        visible: false,
        confirmLoading: false,
        datasource:{},
        // 加载数据方法 必须为 Promise 对象
        func:()=> {
          return new Promise((resolve => {
            //this.request(this.id)
            //this.queryPageOrderList (this.orderid)
            //console.log("order："+JSON.stringify(this.order))
            resolve(this.order)
          })).then(res => {
            return res
          })
        },


        /*计划编号*/
        id:'',
        /*弹窗状态*/
        visiblePlanList:false,
        /*子标签状态*/
        hackReset: true,
        description: '车间计划摘要管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'计划编号',
            align:"center",
            dataIndex: 'id'
          },
          {
            title:'部门编号',
            align:"center",
            dataIndex: 'deptid',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['deptid'], text+"")
              }
            }
          },
          {
            title:'订单号',
            align:"center",
            dataIndex: 'orderid'
          },
          /*{
            title:'批准人',
            align:"center",
            dataIndex: 'personincharge'
          },
          {
            title:'批准日期',
            align:"center",
            dataIndex: 'inchargedate'
          },
          {
            title:'审核人',
            align:"center",
            dataIndex: 'auditor'
          },
          {
            title:'审核日期',
            align:"center",
            dataIndex: 'auditdate'
          },
          {
            title:'审核状态',
            align:"center",
            dataIndex: 'auditstatus',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['auditstatus'], text+"")
              }
            }
          },*/
          {
            title:'备注',
            align:"center",
            dataIndex: 'disp'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/system/mShopplanabstract/list",
          delete: "/system/mShopplanabstract/delete",
          deleteBatch: "/system/mShopplanabstract/deleteBatch",
          exportXlsUrl: "/system/mShopplanabstract/exportXls",
          importExcelUrl: "system/mShopplanabstract/importExcel",

          queryurl: "/system/mShopplanabstract/queryById",
          orderList: {
            list: '/system/mShopplanabstract/queryPageShopplanList'
          },

        },
        dictOptions:{
         deptid:[],
         auditstatus:[],
        },





        //token header
        tokenHeader: {'X-Access-Token': Vue.ls.get(ACCESS_TOKEN)},
        /* 查询条件-请不要在queryParam中声明非字符串值的属性 */
        queryParam: {},
        /* 数据源 */
        dataSource:[],
        /* 分页参数 */
        ipagination:{
          current: 1,
          pageSize: 10,
          pageSizeOptions: ['10', '20', '30'],
          showTotal: (total, range) => {
            return range[0] + "-" + range[1] + " 共" + total + "条"
          },
          showQuickJumper: true,
          showSizeChanger: true,
          total: 0
        },
        /* 排序参数 */
        isorter:{
          column: 'createTime',
          order: 'desc',
        },
        /* 筛选参数 */
        filters: {},
        /* table加载状态 */
        loading:false,
        /* table选中keys*/
        selectedRowKeys: [],
        /* table选中records*/
        selectionRows: [],
        /* 查询折叠 */
        toggleSearchStatus:false,
        /* 高级查询条件生效状态 */
        superQueryFlag:false,
        /* 高级查询条件 */
        superQueryParams:""


      }
    },
    created() {
      this.loadData();
      //初始化字典配置 在自己页面定义
      this.initDictConfig();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      /** 发起请求 */
      request (id) {
        getAction(this.url.queryurl,{'id':id}).then((res) => {
          if (res.success) {
            //this.$message.success(res.message)
            /*if (res.result.planstatusid == '0') {
              res.result.planstatusid = '等待确认'
            } else if (res.result.planstatusid == '1') {
              res.result.planstatusid = '正在进行'
            } else if (res.result.planstatusid == '1') {
              res.result.planstatusid = '计划完成'
            } else {
              res.result.planstatusid = ''
            }*/
            this.datasource = res.result
            var dept = filterMultiDictText(this.dictOptions['deptid'], res.result.deptid+"")
            this.datasource.deptid = dept
            /*查询订单明细*/

            this.queryPageOrderList (res.result.id)
          }
        })
      },
      /*计划详情*/
      queryPageOrderList (shopplanid) {
        console.log("orderid:"+shopplanid)
        getAction(this.url.orderList.list,{'shopplanid':shopplanid,'pageSize':this.order.pageSize,'pageNo':this.order.pageNo}).then((res) => {
          if (res.success) {
            //console.log("    :   "+JSON.stringify(res.result))
            //this.$message.success(res.message)
            this.order.data = res.result.records
            /* 总条数 */
            this.order.totalCount = res.result.total
            this.order.pageNo = res.result.current
            this.order.totalPage = res.result.pages
            this.order.pageSize = res.result.size
          }
        })
      },
      /*关闭弹窗*/
      handleClose(e) {
        this.visiblePlanList = false;
        this.id = ''
        this.order.data.splice(0)
        this.hackReset = false
        this.$nextTick(() => {
          this.hackReset = true
        })
      },
      /* 查看计划详情 */
      showModalLook (event,record) {
        console.log(event)
        this.id = record.id
        this.hackSet = true
        /*查询详情*/
        this.request(this.id)

        //console.log("record:"+JSON.stringify(record))
        setTimeout(() => {
          this.visiblePlanList = true;
        },600)

      },
      initDictConfig(){
        initDictOptions('sys_depart,depart_name,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'deptid', res.result)
          }
        })
        initDictOptions('AuditStatus').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'auditstatus', res.result)
          }
        })
      },
      rebuileComponents(value) {
        console.log("调用此方法"+"   "+value)
        // 销毁子标签
        this.hackReset = false;
        // 重新创建子标签
        this.$nextTick(() => {
          this.hackReset = true;
        });
      },









      loadData(arg) {
        if(!this.url.list){
          this.$message.error("请设置url.list属性!")
          return
        }
        //加载数据 若传入参数1则加载第一页的内容
        if (arg === 1) {
          this.ipagination.current = 1;
        }
        var params = this.getQueryParams();//查询条件
        this.loading = true;
        getAction(this.url.list, params).then((res) => {
          if (res.success) {
            this.dataSource = res.result.records;
            this.ipagination.total = res.result.total;
          }
          if(res.code===510){
            this.$message.warning(res.message)
          }
          this.loading = false;
        })
      },
      handleSuperQuery(arg) {
        //高级查询方法
        if(!arg){
          this.superQueryParams=''
          this.superQueryFlag = false
        }else{
          this.superQueryFlag = true
          this.superQueryParams=JSON.stringify(arg)
        }
        this.loadData()
      },
      getQueryParams() {
        //获取查询条件
        let sqp = {}
        if(this.superQueryParams){
          sqp['superQueryParams']=encodeURI(this.superQueryParams)
        }
        var param = Object.assign(sqp, this.queryParam, this.isorter ,this.filters);
        param.field = this.getQueryField();
        param.pageNo = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize;
        return filterObj(param);
      },
      getQueryField() {
        //TODO 字段权限控制
        var str = "id,";
        this.columns.forEach(function (value) {
          str += "," + value.dataIndex;
        });
        return str;
      },

      onSelectChange(selectedRowKeys, selectionRows) {
        this.selectedRowKeys = selectedRowKeys;
        this.selectionRows = selectionRows;
      },
      onClearSelected() {
        this.selectedRowKeys = [];
        this.selectionRows = [];
      },
      searchQuery() {
        this.loadData(1);
      },
      superQuery() {
        this.$refs.superQueryModal.show();
      },
      searchReset() {
        this.queryParam = {}
        this.loadData(1);
      },
      batchDel: function () {
        if(!this.url.deleteBatch){
          this.$message.error("请设置url.deleteBatch属性!")
          return
        }
        if (this.selectedRowKeys.length <= 0) {
          this.$message.warning('请选择一条记录！');
          return;
        } else {
          var ids = "";
          for (var a = 0; a < this.selectedRowKeys.length; a++) {
            ids += this.selectedRowKeys[a] + ",";
          }
          var that = this;
          this.$confirm({
            title: "确认删除",
            content: "是否删除选中数据?",
            onOk: function () {
              deleteAction(that.url.deleteBatch, {ids: ids}).then((res) => {
                if (res.success) {
                  that.$message.success(res.message);
                  that.loadData();
                  that.onClearSelected();
                } else {
                  that.$message.warning(res.message);
                }
              });
            }
          });
        }
      },
      handleDelete: function (id) {
        if(!this.url.delete){
          this.$message.error("请设置url.delete属性!")
          return
        }
        var that = this;
        // console.log("计划编号："+id)
        deleteAction(that.url.delete, {id: id}).then((res) => {
          if (res.success) {
            that.$message.success(res.message);
            that.loadData();
          } else {
            that.$message.warning(res.message);
          }
        });
      },
      handleEdit: function (record) {
        this.$refs.modalForm.edit(record);
        this.$refs.modalForm.title = "编辑";
        this.$refs.modalForm.disableSubmit = false;
      },
      handleAdd: function () {
        this.$refs.modalForm.add();
        this.$refs.modalForm.title = "新增";
        this.$refs.modalForm.disableSubmit = false;
      },
      handleTableChange(pagination, filters, sorter) {
        //分页、排序、筛选变化时触发
        //TODO 筛选
        if (Object.keys(sorter).length > 0) {
          this.isorter.column = sorter.field;
          this.isorter.order = "ascend" == sorter.order ? "asc" : "desc"
        }
        this.ipagination = pagination;
        this.loadData();
      },
      handleToggleSearch(){
        this.toggleSearchStatus = !this.toggleSearchStatus;
      },
      modalFormOk() {
        // 新增/修改 成功时，重载列表
        this.loadData();
      },
      handleDetail:function(record){
        this.$refs.modalForm.edit(record);
        this.$refs.modalForm.title="详情";
        this.$refs.modalForm.disableSubmit = true;
      },
      /* 导出 */
      handleExportXls2(){
        let paramsStr = encodeURI(JSON.stringify(this.getQueryParams()));
        let url = `${window._CONFIG['domianURL']}/${this.url.exportXlsUrl}?paramsStr=${paramsStr}`;
        window.location.href = url;
      },
      handleExportXls(fileName){
        if(!fileName || typeof fileName != "string"){
          fileName = "导出文件"
        }
        let param = {...this.queryParam};
        if(this.selectedRowKeys && this.selectedRowKeys.length>0){
          param['selections'] = this.selectedRowKeys.join(",")
        }
        console.log("导出参数",param)
        downFile(this.url.exportXlsUrl,param).then((data)=>{
          if (!data) {
            this.$message.warning("文件下载失败")
            return
          }
          if (typeof window.navigator.msSaveBlob !== 'undefined') {
            window.navigator.msSaveBlob(new Blob([data]), fileName+'.xls')
          }else{
            let url = window.URL.createObjectURL(new Blob([data]))
            let link = document.createElement('a')
            link.style.display = 'none'
            link.href = url
            link.setAttribute('download', fileName+'.xls')
            document.body.appendChild(link)
            link.click()
            document.body.removeChild(link); //下载完成移除元素
            window.URL.revokeObjectURL(url); //释放掉blob对象
          }
        })
      },
      /* 导入 */
      handleImportExcel(info){
        if (info.file.status !== 'uploading') {
          console.log(info.file, info.fileList);
        }
        if (info.file.status === 'done') {
          if(info.file.response.success){
            this.$message.success(`${info.file.name} 文件上传成功`);
            this.loadData();
          } else {
            this.$message.error(`${info.file.name} ${info.file.response.message}.`);
          }
        } else if (info.file.status === 'error') {
          this.$message.error(`文件上传失败: ${info.file.msg} `);
        }
      },
      /* 图片预览 */
      getImgView(text){
        if(text && text.indexOf(",")>0){
          text = text.substring(0,text.indexOf(","))
        }
        return window._CONFIG['imgDomainURL']+"/"+text
      },
      /* 文件下载 */
      uploadFile(text){
        if(!text){
          this.$message.warning("未知的文件")
          return;
        }
        if(text.indexOf(",")>0){
          text = text.substring(0,text.indexOf(","))
        }
        window.open(window._CONFIG['domianURL'] + "/sys/common/download/"+text);
      },



       
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>