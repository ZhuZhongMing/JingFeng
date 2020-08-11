<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <!--<a-row :gutter="24">

          <a-col :md="6" :sm="8">
            <a-form-item label="所属流水线设备编号">
              <a-input placeholder="请输入所属流水线设备编号" v-model="queryParam.asequipid"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="所属类别编号">
              <a-input placeholder="请输入所属类别编号" v-model="queryParam.categoryid"></a-input>
            </a-form-item>
          </a-col>
        <template v-if="toggleSearchStatus">
        <a-col :md="6" :sm="8">
            <a-form-item label="删除标识0-正常,1-已删除">
              <a-input placeholder="请输入删除标识0-正常,1-已删除" v-model="queryParam.delFlag"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="所属部门编号">
              <a-input placeholder="请输入所属部门编号" v-model="queryParam.deptid"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="备注">
              <a-input placeholder="请输入备注" v-model="queryParam.disp"></a-input>
            </a-form-item>
          </a-col>
          </template>
          <a-col :md="6" :sm="8" >
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>

        </a-row>-->
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <!--<div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('设备信息')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>-->

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

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <!--<a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>-->
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <rEquipment-modal ref="modalForm" @ok="modalFormOk"></rEquipment-modal>
  </a-card>
</template>

<script>
  import REquipmentModal from './modules/REquipmentModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import JSelectDepart from '@/components/jeecgbiz/JSelectDepart'


  export default {
    name: "REquipmentList",
    mixins:[JeecgListMixin],
    components: {
      REquipmentModal
    },
    data () {
      return {
        description: '设备信息管理页面',
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
            title: '设备编号',
            align:"center",
            dataIndex: 'id'
          },
		   /*{
            title: '所属流水线设备编号',
            align:"center",
            dataIndex: 'asequipid'
           },
		   {
            title: '所属类别编号',
            align:"center",
            dataIndex: 'categoryid'
           },*/
		   /*{
            title: '删除标识0-正常,1-已删除',
            align:"center",
            dataIndex: 'delFlag'
           },
		   {
            title: '所属部门编号',
            align:"center",
            dataIndex: 'deptid'
           },*/
		   /*{
            title: '备注',
            align:"center",
            dataIndex: 'disp'
           },*/
		   {
            title: '设备名称',
            align:"center",
            dataIndex: 'equipname'
           },
		   /*{
            title: '所属流水工序号',
            align:"center",
            dataIndex: 'location'
           },*/

		   /*{
            title: '产出系数',
            align:"center",
            dataIndex: 'outcoefficient'
           },
		   {
            title: '产出类型',
            align:"center",
            dataIndex: 'outtype'
           },*/
		   /*{
            title: '放置位置',
            align:"center",
            dataIndex: 'placement'
           },
		   {
            title: '功率',
            align:"center",
            dataIndex: 'power'
           },
		   {
            title: '额定电压',
            align:"center",
            dataIndex: 'ratedVoltage'
           },
		   {
            title: '状态',
            align:"center",
            dataIndex: 'state'
           },*/
       {
            title: '制造厂家',
            align:"center",
            dataIndex: 'manufacturer'
          },
		   {
            title: '设备用途',
            align:"center",
            dataIndex: 'rusage'
           },
		   {
            title: '车间编号',
            align:"center",
            dataIndex: 'wsname'
           },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
		url: {
          list: "/system/rEquipment/list",
          delete: "/system/rEquipment/delete",
          deleteBatch: "/system/rEquipment/deleteBatch",
          exportXlsUrl: "system/rEquipment/exportXls",
          importExcelUrl: "system/rEquipment/importExcel",
       },
    }
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    }
  },
    methods: {

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>