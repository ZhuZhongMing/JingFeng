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
      <!--<a-button type="primary" icon="download" @click="handleExportXls('生产任务单')">导出</a-button>
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

      <!--:scroll="{ x: 1408 }"-->
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
        <!-- 物料BOM -->
        <template slot="bom" slot-scope="text, record">
          <a-button
            :ghost="true"
            type="primary"
            icon="search"
            size="small"
            @click="showModel(record)">
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
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <mProductTaskOrder-modal ref="modalForm" @ok="modalFormOk"></mProductTaskOrder-modal>

    <!-- model --- bom -->
    <!-- :title="modelTitle"title="产品物料BOM清单" -->
    <a-modal
      title="产品物料BOM"
      :visible="modelVisible"
      :footer="false"
      :width="widthOfModel"
      :maskClosable="true"
      @cancel="handleCancelMOdel"
    >
      <table>
        <thead>
        <tr>
          <td colspan="6"><b style="font-size: 20px">{{modelTitle}}</b></td>
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
      <!--<page-layout :title="modelTitle">
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
      <!--<a-card :bordered="false">
        <div>
       <detail-list title="" :col="3" v-if="modelData.length != 0">

         &lt;!&ndash;<detail-list-item term="物料编码">{{modelData.materialencode}}</detail-list-item>&ndash;&gt;
         <detail-list-item term="产品名称">{{modelData.materialname}}</detail-list-item>
         <detail-list-item term="数量">{{modelData.number}}</detail-list-item>
         <detail-list-item term="创建人">{{modelData.createBy}}</detail-list-item>
         <detail-list-item term="创建时间">{{modelData.createTime}}</detail-list-item>

       </detail-list>
       <a-divider style="margin-bottom: 24px"/>
        </div>

        <div v-if="cList.length > 0">

          &lt;!&ndash;  +(index+1) &ndash;&gt;
          <detail-list :title="'组成'" :col="4" v-if="modelData.length != 0">
            <div v-for="(ss,index) in cList" :key="ss.id" >
              <detail-list-item term="物料编码">{{ss[index][0].materialencode}}</detail-list-item>
              <detail-list-item term="物料名称">{{ss[index][0].materialname}}</detail-list-item>
              <detail-list-item term="规格">{{ss[index][0].special}}</detail-list-item>
              <detail-list-item term="单位">{{ss[index][0].purchaseorganize}}</detail-list-item>
            </div>
          </detail-list>
          <a-divider style="margin-bottom: 24px"/>

        </div>
      </a-card>-->




    </a-modal>

  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import MProductTaskOrderModal from './modules/MProductTaskOrderModal'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  import { getAction } from '@/api/manage'


  import DetailList from '@/components/tools/DetailList'
  const DetailListItem = DetailList.Item

  export default {
    name: "MProductTaskOrderList",
    mixins:[JeecgListMixin],
    components: {
      MProductTaskOrderModal
    },
    data () {
      return {
        /*bom数据*/
        bom:[],
        modelTitle:'产品物料BOM清单',
        modelVisible:false,
        widthOfModel:document.body.clientWidth * 0.7,
        width:document.body.clientWidth * 0.6,
        description: '生产任务单管理页面',
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
            title:'产品编码',
            align:"center",
            dataIndex: 'productname'
          },
          {
            title:'生产车间编号',
            align:"center",
            dataIndex: 'wsname'
          },
          {
            title:'设备编号',
            align:"center",
            dataIndex: 'equipname'
          },
          {
            title:'数量',
            align:"center",
            dataIndex: 'productnumber'
          },
          {
            title:'已完成数量',
            align:"center",
            dataIndex: 'finishednumber'
          },
          {
            title:'计划开始时间',
            align:"center",
            dataIndex: 'planstarttime'
          },
          {
            title:'计划结束时间',
            align:"center",
            dataIndex: 'planendtime'
          },
          {
            title:'实际开始时间',
            align:"center",
            dataIndex: 'realstarttime'
          },
          {
            title:'实际结束时间',
            align:"center",
            dataIndex: 'realendtime'
          },
          /*{
            title:'需要工时',
            align:"center",
            dataIndex: 'needworkhour'
          },*/
          {
            title:'操作员',
            align:"center",
            dataIndex: 'salesman',
            /*customRender:(text)=>{
              if(!text){
                return '是否外协'
              }else{
                return filterMultiDictText(this.dictOptions['isoutsource'], text+"")
              }
            }*/
          },
          /*{
            title:'备注',
            align:"center",
            dataIndex: 'disp'
          },*/
          {
            title: '物料BOM',
            dataIndex: 'bom',
            align:"center",
            scopedSlots: { customRender: 'bom' },
            /*fixed: 'right',*/
            width: 100
          },
          {
            title:'状态',
            align:"center",
            dataIndex: 'state',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['state'], text+"")
              }
            }
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
            /*fixed: 'right'*/
          }
        ],
        url: {
          list: "/system/mProductTaskOrder/queryList",
          delete: "/system/mProductTaskOrder/delete",
          deleteBatch: "/system/mProductTaskOrder/deleteBatch",
          exportXlsUrl: "/system/mProductTaskOrder/exportXls",
          importExcelUrl: "/system/mProductTaskOrder/importExcel",
        },
        dictOptions:{
         isoutsource:[],
         state:[],
        } 
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      /*关闭model*/
      handleCancelMOdel(e) {
        //console.log('Clicked cancel button');
        this.modelVisible = false;
        setTimeout(() => {
          this.bom.splice(0)
        },500);
      },
      /*查看bom*/
      showModel(record) {
        let that = this
        //alert(record.id+"   "+record.materialencodeid)
        //this.modelTitle = '生产任务'+record.id//+","+record.materialname+"BOM表"
        getAction('/system/mBom/BomByTaskid',{'id':record.id}).then((res)=>{
          if (res.success) {
            //this.$set(this.dictOptions, 'deptid', res.result)
            this.bom = res.result
            this.modelVisible = true
          } else {
            this.$message.warning('请先设置明细')
          }
          /*if (res.success) {
            //alert("结果："+JSON.stringify(res.result))
            that.cList.splice(0)
            that.pList.splice(0)
            that.modelData = res.result[0].mbom
            //that.cList = res.result[0]
            for (let i = 0; i < res.result.length; i++) {
              that.pList.push([
                res.result[i].parentList
              ])
              that.cList.push([
                res.result[i].childList
              ])
            }
            that.modelVisible = true
          }*/
        });

      },
      initDictConfig(){
        initDictOptions('IsOutsource').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'isoutsource', res.result)
          }
        })
        initDictOptions('State').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'state', res.result)
          }
        })
      }
       
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
<!--
<style scoped>
  @import '~@assets/less/common.less'

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
</style>-->
