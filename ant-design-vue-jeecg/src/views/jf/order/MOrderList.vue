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
      <!--<a-button type="primary" icon="download" @click="handleExportXls('销售订单')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
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

        <template slot="bomSlot" slot-scope="text, record">
          <a-button
            :ghost="true"
            type="primary"
            icon="search"
            size="small"
            @click="searchBOM(record)">
            查看BOM
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                  <a @click="showModalLook(record)">订单详情</a>
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

    <mOrder-modal ref="modalForm" @ok="modalFormOk"></mOrder-modal>

    <!-- 订单详情 -->
    <a-modal
      title="订单详情"
      :visible="visibleOrderList"
      @cancel="handleClose"
      :footer="null"
      :width="1200"
    >
      <m-order-list-info-modal v-if="id!=''" :id="id"></m-order-list-info-modal>
      <!--<my-plan-modal :id="id"></my-plan-modal>-->
    </a-modal>

    <!-- BOM -->
    <!--title="产品BOM表"-->
    <a-modal
      title="产品物料BOM"
      :visible="visibleBom"
      @cancel="handleCloseBom"
      :footer="null"
      :width="bomWidth"
    >
      <table>
        <thead>
          <tr>
            <td colspan="6"><b style="font-size: 20px">{{title}}</b></td>
          </tr>
        </thead>
        <tbody v-for="(i,index) in bom" :key="index">
          <tr>
            <td><b>产品名称</b></td>
            <td>{{i.mproductMap.productname}}</td>
            <td><b>产品型号</b></td>
            <td>{{i.mproductMap.producttype}}</td>
            <td><b>产品规格</b></td>
            <td>{{i.mproductMap.productsize}}</td>
          </tr>
          <tr>
            <td><b>产品颜色</b></td>
            <td>{{i.mproductMap.productcolor}}</td>
            <td><b>产品长度</b></td>
            <td>{{i.mproductMap.productlength}}  节</td>
            <td><b>计划数量</b></td>
            <td>{{i.mproductMap.number}}</td>
          </tr>
          <tr>
            <td><b>是否带反弹传动片</b></td>
            <td colspan="5" v-if="i.mproductMap.isresilient == 0 || i.mproductMap.isresilient == '0'">
              否
            </td>
            <td colspan="5" v-if="i.mproductMap.isresilient == 1 || i.mproductMap.isresilient == '1'">
              是
            </td>
          </tr>
          <tr>
            <td>部件</td>
            <td>物料编码</td>
            <td>物料名称</td>
            <td>颜色</td>
            <td>单位</td>
            <td>数量</td>
          </tr>
          <tr>
            <td><b>上刀</b></td>
            <td>{{i.mmaterialList.t.materialencode}}</td>
            <td>{{i.mmaterialList.t.materialname}}</td>
            <td>{{i.mmaterialList.t.color}}</td>
            <td>{{i.mmaterialList.t.purchaseorganize}}</td>
            <td>{{i.mmaterialList.t.number * i.mproductMap.number}}</td>
          </tr>
          <tr>
            <td><b>下刀</b></td>
            <td>{{i.mmaterialList.b.materialencode}}</td>
            <td>{{i.mmaterialList.b.materialname}}</td>
            <td>{{i.mmaterialList.b.color}}</td>
            <td>{{i.mmaterialList.b.purchaseorganize}}</td>
            <td>{{i.mmaterialList.b.number * i.mproductMap.number}}</td>
          </tr>
          <tr>
            <td><b>连接片</b></td>
            <td>{{i.mmaterialList.c.materialencode}}</td>
            <td>{{i.mmaterialList.c.materialname}}</td>
            <td>{{i.mmaterialList.c.color}}</td>
            <td>{{i.mmaterialList.c.purchaseorganize}}</td>
            <td>{{i.mmaterialList.c.number * i.mproductMap.number}}</td>
          </tr>
          <tr>
            <td><b>传动片</b></td>
            <td>{{i.mmaterialList.d.materialencode}}</td>
            <td>{{i.mmaterialList.d.materialname}}</td>
            <td>{{i.mmaterialList.d.color}}</td>
            <td>{{i.mmaterialList.d.purchaseorganize}}</td>
            <td>{{i.mmaterialList.d.number * i.mproductMap.number}}</td>
          </tr>
          <tr>
            <td><b>铆钉</b></td>
            <td>{{i.mmaterialList.r.materialencode}}</td>
            <td>{{i.mmaterialList.r.materialname}}</td>
            <td>{{i.mmaterialList.r.color}}</td>
            <td>{{i.mmaterialList.r.purchaseorganize}}</td>
            <td>{{i.mmaterialList.r.number * i.mproductMap.number}}</td>
          </tr>

          <tr v-if="(index+1) != bom.length">
            <!--<td colspan="6" style="color: rgba(0,0,0,0); background-color: rgba(125,128,115,0.7);">&nbsp;&nbsp;&nbsp;</td>-->
            <!--<td colspan="6" style="color: #b9fffd">&nbsp;&nbsp;&nbsp;</td>-->

            <td colspan="6" style="color:  rgba(0,0,0,0); background-color: #8cc8ff;">&nbsp;&nbsp;&nbsp;</td>
          </tr>
        </tbody>
      </table>
      <!--<table style="margin-top: 0px;border-top: 0px;">

      </table>-->
      <!--<page-layout :title="title">
        <a-card :bordered="false" v-for="(i,index) in bom" :key="index">
          <detail-list title="产品详情" :col="4">
            <detail-list-item term="产品名称">{{i.mproductMap.productname}}</detail-list-item>
            <detail-list-item term="产品型号">{{i.mproductMap.producttype}}</detail-list-item>
            <detail-list-item term="产品规格">{{i.mproductMap.productsize}}</detail-list-item>

            <detail-list-item term="产品颜色">{{i.mproductMap.productcolor}}</detail-list-item>
            <detail-list-item term="产品长度">{{i.mproductMap.productlength}}  节</detail-list-item>
            <detail-list-item term="计划数量">{{i.mproductMap.number}}</detail-list-item>
            <detail-list-item term="是否带反弹传动片" v-if="i.mproductMap.isresilient == 0 || i.mproductMap.isresilient == '0'">否</detail-list-item>
            <detail-list-item term="是否带反弹传动片" v-if="i.mproductMap.isresilient == 1 || i.mproductMap.isresilient == '1'">是</detail-list-item>
            &lt;!&ndash;<detail-list-item term="客户名称"><i style="color: #bbbbbb">暂未设置</i></detail-list-item>&ndash;&gt;
          </detail-list>
          <a-divider style="margin-bottom: 1px"/>
          <detail-list title="物料详情" :col="4">
            <detail-list-item term="名称">{{i.mmaterialList.t.materialname}}</detail-list-item>
            <detail-list-item term="颜色">{{i.mmaterialList.t.color}}</detail-list-item>
            <detail-list-item term="单位">{{i.mmaterialList.t.purchaseorganize}}</detail-list-item>
            <detail-list-item term="数量">{{i.mmaterialList.t.number * i.mproductMap.number}}</detail-list-item>

            <detail-list-item term="名称">{{i.mmaterialList.b.materialname}}</detail-list-item>
            <detail-list-item term="颜色">{{i.mmaterialList.b.color}}</detail-list-item>
            <detail-list-item term="单位">{{i.mmaterialList.b.purchaseorganize}}</detail-list-item>
            <detail-list-item term="数量">{{i.mmaterialList.b.number * i.mproductMap.number}}</detail-list-item>

            <detail-list-item term="名称">{{i.mmaterialList.c.materialname}}</detail-list-item>
            <detail-list-item term="颜色">{{i.mmaterialList.c.color}}</detail-list-item>
            <detail-list-item term="单位">{{i.mmaterialList.c.purchaseorganize}}</detail-list-item>
            <detail-list-item term="数量">{{i.mmaterialList.c.number * i.mproductMap.number}}</detail-list-item>

            <detail-list-item term="名称">{{i.mmaterialList.d.materialname}}</detail-list-item>
            <detail-list-item term="颜色">{{i.mmaterialList.d.color}}</detail-list-item>
            <detail-list-item term="单位">{{i.mmaterialList.d.purchaseorganize}}</detail-list-item>
            <detail-list-item term="数量">{{i.mmaterialList.d.number * i.mproductMap.number}}</detail-list-item>

            <detail-list-item term="名称">{{i.mmaterialList.r.materialname}}</detail-list-item>
            <detail-list-item term="颜色">{{i.mmaterialList.r.color}}</detail-list-item>
            <detail-list-item term="单位">{{i.mmaterialList.r.purchaseorganize}}</detail-list-item>
            <detail-list-item term="数量">{{i.mmaterialList.r.number * i.mproductMap.number}}</detail-list-item>

            <div v-if="i.mproductMap.isresilient == 1 || i.mproductMap.isresilient == '1'">
              <detail-list-item term="名称">{{i.mmaterialList.d.materialname}}</detail-list-item>
              <detail-list-item term="颜色">{{i.mmaterialList.d.color}}</detail-list-item>
              <detail-list-item term="单位">{{i.mmaterialList.d.purchaseorganize}}</detail-list-item>
              <detail-list-item term="数量">{{i.mmaterialList.d.number * i.mproductMap.number}}</detail-list-item>
            </div>
          </detail-list>
          <a-divider style="margin-bottom: 2px"/>
        </a-card>
      </page-layout>-->


    </a-modal>


  </a-card>

</template>

<script>

  import PageLayout from '@/components/page/PageLayout'
  import STable from '@/components/table/'
  import DetailList from '@/components/tools/DetailList'
  import ABadge from "ant-design-vue/es/badge/Badge"
  const DetailListItem = DetailList.Item

  import {  getAction } from '@/api/manage'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import MOrderModal from './modules/MOrderModal'
  import MOrderListInfoModal from './modules/MOrderListInfoModal'
  import {initDictOptions} from '@/components/dict/JDictSelectUtil'
  export default {
    name: "MOrderList",
    mixins:[JeecgListMixin],
    components: {
      MOrderModal,
      MOrderListInfoModal,
      PageLayout,
      ABadge,
      DetailList,
      DetailListItem,
      STable
    },
    data () {
      return {
        title:'产品物料BOM清单',
        /*屏幕宽度*/
        bomWidth:window.screen.width * 0.7,
        visibleBom:false,
        /*查看详情的id主键*/
        id:'',
        /*弹窗状态*/
        visibleOrderList:false,
        description: '销售订单管理页面',
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
            title:'订单编号',
            align:"center",
            dataIndex: 'id'
          },
          {
            title:'合同编号',
            align:"center",
            dataIndex: 'contract'
          },
          /*{
            title:'客户编号',
            align:"center",
            dataIndex: 'customerid'
          },*/
          /*{
            title:'业务员',
            align:"center",
            dataIndex: 'salesman'
          },*/
          /*{
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
          },*/
          /*{
            title:'支付方式',
            align:"center",
            dataIndex: 'paymentid'
          },
          {
            title:'支付时间',
            align:"center",
            dataIndex: 'paymentdate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'递送方式',
            align:"center",
            dataIndex: 'deliverymethod'
          },
          {
            title:'递送费用',
            align:"center",
            dataIndex: 'deliveryfee'
          },
          {
            title:'审核人',
            align:"center",
            dataIndex: 'auditperson'
          },
          {
            title:'审核时间',
            align:"center",
            dataIndex: 'audittime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },*/
          {
            title:'备注',
            align:"center",
            dataIndex: 'disp'
          },
          {
            title:'合同附件',
            align:"center",
            dataIndex: 'accessory',
            scopedSlots: {customRender: "fileSlot"}
          },
          {
            title:'BOM',
            align:"center",
            dataIndex: 'bomSlot',
            scopedSlots: {customRender: "bomSlot"}
          },

          /*{
            title:'结算货币',
            align:"center",
            dataIndex: 'currency'
          },
          {
            title:'订单类别',
            align:"center",
            dataIndex: 'ordertype'
          },*/
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        bom:[],
        url: {
          list: "/system/mOrder/list",
          delete: "/system/mOrder/delete",
          deleteBatch: "/system/mOrder/deleteBatch",
          exportXlsUrl: "/system/mOrder/exportXls",
          importExcelUrl: "system/mOrder/importExcel",
        },
        dictOptions:{
         deptid:[],
        } 
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
      /*bom : function() {

      },*/
    },
    methods: {
      /*查看BOM*/
      searchBOM (record) {
        getAction('/system/mBom/BomById',{'id':record.id}).then((res) => {
          if (res.success) {
            //this.$set(this.dictOptions, 'deptid', res.result)
            this.bom = res.result
            this.visibleBom = true
          } else {
            this.$message.warning('请先设置明细')
          }
        })
      },
      /* 查看订单详情 */
      showModalLook (record) {
        this.id = record.id
        //console.log("record:"+JSON.stringify(record))
        this.visibleOrderList = true;
      },
      /*关闭弹窗*/
      handleClose(e) {
        this.visibleOrderList = false;
        this.id = ''
      },
      handleCloseBom(e) {
        this.visibleBom = false;
        setTimeout(() => {
          this.bom.splice(0)
        },500);

      },
      initDictConfig(){
        initDictOptions('sys_depart,depart_name,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'deptid', res.result)
          }
        })
      },
       
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';

  table,tr,td {
    border: 1px solid black;
  }
  table {
    width: 800px;
    margin: 0px auto;
    text-align: center;
  }
  td {
    line-height: 30px;
  }
</style>