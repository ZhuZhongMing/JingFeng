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
      <!--<a-button type="primary" icon="download" @click="handleExportXls('物料信息')">导出</a-button>
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

    <mMaterial-modal ref="modalForm" @ok="modalFormOk"></mMaterial-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import MMaterialModal from './modules/MMaterialModal'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  export default {
    name: "MMaterialList",
    mixins:[JeecgListMixin],
    components: {
      MMaterialModal
    },
    data () {
      return {
        description: '物料信息管理页面',
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
            title:'物料编号',
            align:"center",
            dataIndex: 'materialencode',
          },
          {
            title:'物料名称',
            align:"center",
            dataIndex: 'materialname',
          },
          /*{
            title:'别名',
            align:"center",
            dataIndex: 'alias'
          },*/
         /* {
            title:'英文名',
            align:"center",
            dataIndex: 'englishname'
          },
          {
            title:'检索码',
            align:"center",
            dataIndex: 'indexcode'
          },*/
          /*{
            title:'等级',
            align:"center",
            dataIndex: 'level'
          },*/
          {
            title:'型号',
            align:"center",
            dataIndex: 'model'
          },
          {
            title:'规格',
            align:"center",
            dataIndex: 'special'
          },
          {
            title:'颜色',
            align:"center",
            dataIndex: 'color'
          },
          {
            title:'单位',
            align:"center",
            dataIndex: 'purchaseorganize'
          },

          /*{
            title:'通用规格',
            align:"center",
            dataIndex: 'gspecial'
          },
          {
            title:'商标',
            align:"center",
            dataIndex: 'brand'
          },
          {
            title:'ABC类',
            align:"center",
            dataIndex: 'abccategory'
          },

          {
            title:'计算特性1',
            align:"center",
            dataIndex: 'spe1'
          },
          {
            title:'计算特性2',
            align:"center",
            dataIndex: 'spe2'
          },*/
          /*{
            title:'条形码',
            align:"center",
            dataIndex: 'barcode'
          },*/
          /*{
            title:'物料分类编码',
            align:"center",
            dataIndex: 'materialtypeencode',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['materialtypeencode'], text+"")
              }
            }
          },
          {
            title:'单位Id',
            align:"center",
            dataIndex: 'unitid'
          },
          {
            title:'是否虚拟件',
            align:"center",
            dataIndex: 'isvirtalpart',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['isvirtalpart'], text+"")
              }
            }
          },
          {
            title:'是否外购件',
            align:"center",
            dataIndex: 'ispurchasepart',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['ispurchasepart'], text+"")
              }
            }
          },
          {
            title:'是否通用件',
            align:"center",
            dataIndex: 'isgeneralpart',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['isgeneralpart'], text+"")
              }
            }
          },
          {
            title:'安全库存',
            align:"center",
            dataIndex: 'minwhstorge'
          },
          {
            title:'最高库存',
            align:"center",
            dataIndex: 'maxwhstorge'
          },*/
          /*{
            title:'当前库存',
            align:"center",
            dataIndex: 'currentwhstogre'
          },*/
          /*{
            title:'允许销售',
            align:"center",
            dataIndex: 'issell',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['issell'], text+"")
              }
            }
          },
          {
            title:'序列号',
            align:"center",
            dataIndex: 'serialnumber'
          },
          {
            title:'采购单位',
            align:"center",
            dataIndex: 'purchaseorganize'
          },
          {
            title:'产地',
            align:"center",
            dataIndex: 'productaddress'
          },
          {
            title:'是否入库检验',
            align:"center",
            dataIndex: 'iswhinspec',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['iswhinspec'], text+"")
              }
            }
          },
          {
            title:'成本日期',
            align:"center",
            dataIndex: 'costdate'
          },
          {
            title:'标准成本价',
            align:"center",
            dataIndex: 'standardcost'
          },
          {
            title:'销售价',
            align:"center",
            dataIndex: 'salescost'
          },*/
          /*{
            title:'采购价',
            align:"center",
            dataIndex: 'purchasecost'
          },*/
          {
            title:'物料类型',
            align:"center",
            dataIndex: 'mcategoryname',
            /*customRender:(text,code)=>{
              if(code.materialtypeencode == 0 || code.materialtypeencode == '0'){
                return '成品'
              } else if(code.materialtypeencode == 1 || code.materialtypeencode == '1'){
                return '半成品'
              } else if(code.materialtypeencode == 2 || code.materialtypeencode == '2'){
                return '原材料'
              } else {
                return ''+code.materialtypeencode
              }
            }*/
          },
          /*{
            title:'采购提前天数',
            align:"center",
            dataIndex: 'preparedays'
          },
          {
            title:'工序编号',
            align:"center",
            dataIndex: 'processid',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['processid'], text+"")
              }
            }
          },
          {
            title:'是否停用',
            align:"center",
            dataIndex: 'isstop',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['isstop'], text+"")
              }
            }
          },
          {
            title:'起初数值',
            align:"center",
            dataIndex: 'startnum'
          },
          {
            title:'图片',
            align:"center",
            dataIndex: 'figurepath',
            scopedSlots: {customRender: 'imgSlot'}
          },
          {
            title:'备注',
            align:"center",
            dataIndex: 'disp'
          },*/
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/system/mMaterial/list",
          delete: "/system/mMaterial/delete",
          deleteBatch: "/system/mMaterial/deleteBatch",
          exportXlsUrl: "/system/mMaterial/exportXls",
          importExcelUrl: "system/mMaterial/importExcel",
        },
        dictOptions:{
        } 
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
      }
       
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>