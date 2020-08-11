<template>
  <a-modal
    :title="title"
    :width="1300"
    :visible="visible"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel">
    <a-spin :spinning="confirmLoading">
      <!-- 主表单区域 -->
      <a-form :form="form">
        <a-row>

          <!--<a-col :span="12">
            <a-form-item label="创建日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择创建日期" v-decorator="[ 'createTime', validatorRules.createTime]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
            </a-form-item>
          </a-col>-->
        
          <a-col :span="12">
            <a-form-item label="部门编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-select-depart v-decorator="['deptid']" :trigger-change="true"/>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="业务员编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-select-user-by-dep v-decorator="['salesmanid']" :trigger-change="true"/>
            </a-form-item>
          </a-col>
        
          <!--<a-col :span="12">
            <a-form-item label="计划种类编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'plantypeid', validatorRules.plantypeid]" placeholder="请输入计划种类编号"></a-input>
            </a-form-item>
          </a-col>-->
        
          <a-col :span="12">
            <a-form-item label="业务日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择业务日期" v-decorator="[ 'saledate', validatorRules.saledate]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="审核人编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <!--<a-input v-decorator="[ 'auditorid', validatorRules.auditorid]" placeholder="请输入审核人编号"></a-input>-->
              <j-select-user-by-dep v-decorator="['auditorid']" :trigger-change="true"/>
            </a-form-item>
          </a-col>
        
          <!--<a-col :span="12">
            <a-form-item label="审核日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择审核日期" v-decorator="[ 'auditdate', validatorRules.auditdate]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="审核状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['auditstatus']" :trigger-change="true" dictCode="AuditStatus" placeholder="请选择审核状态"/>
            </a-form-item>
          </a-col>-->
        
          <a-col :span="24">
            <a-form-item label="备注" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <a-textarea v-decorator="['disp']" rows="4" placeholder="请输入备注"/>
            </a-form-item>
          </a-col>
        
        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="计划详情" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="mMainplanTable.loading"
            :columns="mMainplanTable.columns"
            :dataSource="mMainplanTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>
        </a-tab-pane>
        
      </a-tabs>

    </a-spin>
  </a-modal>
</template>

<script>

  import pick from 'lodash.pick'
  import { FormTypes,getRefPromise } from '@/utils/JEditableTableUtil'
  import JDate from '@/components/jeecg/JDate'  
  import JSelectDepart from '@/components/jeecgbiz/JSelectDepart'
  import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JEditableTable from '@/components/jeecg/JEditableTable'
  import { VALIDATE_NO_PASSED, validateFormAndTables } from '@/utils/JEditableTableUtil'
  import { httpAction, getAction,postAction } from '@/api/manage'


  /*物料列表*/
  const materialList = []
  /*订单列表*/
  const orderList = []
  function getMaterialList () {
    getAction('/system/mMaterial/getMaterialList',null).then((res)=>{
      if(res.success){
        /*清空*/
        materialList.splice(0)
        //this.materialList = res.result
        for (let i = 0; i < res.result.length ; i++) {

          //console.log("id:"+res.result[i].id+"  name:"+res.result[i].materialname)
          materialList.push({
            value : res.result[i].id,
            title : res.result[i].materialname
          })

        }
      }
    });
  }
  function getOrderList () {
    getAction('/system/mOrder/getAll',null).then((res)=>{
      if(res.success){
        /*清空*/
        orderList.splice(0)
        //this.materialList = res.result
        for (let i = 0; i < res.result.length ; i++) {

          //console.log("id:"+res.result[i].id+"  name:"+res.result[i].materialname)
          orderList.push({
            value : res.result[i].id,
            title : res.result[i].id
          })

        }
      }
    });
  }

  export default {
    name: 'MMainplanabstractModal',

    components: {
      JDate,
      JSelectDepart,
      JSelectUserByDep,
      JDictSelectTag,
      JEditableTable
    },
    data() {
      return {
        title: '操作',
        visible: false,
        form: this.$form.createForm(this),
        confirmLoading: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 6 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 18 }
        },
        labelCol2: {
          span: 3
        },
        wrapperCol2: {
          span: 20
        },
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 1,
        validatorRules: {
          createTime:{},
          deptid:{},
          salesmanid:{},
          plantypeid:{},
          saledate:{},
          auditorid:{},
          auditdate:{},
          auditstatus:{},
          disp:{},
        },
        refKeys: ['mMainplan', ],
        tableKeys:['mMainplan', ],
        activeKey: 'mMainplan',
        // 计划详情
        mMainplanTable: {
          loading: false,
          dataSource: [],
          columns: [
            /*{
              title: '计划摘要编号',
              key: 'mainplanabstractid',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },*/
            {
              title: '物料编号',
              key: 'materialencode',
              type: FormTypes.select,
              defaultValue: '',
              placeholder: '请输入${title}',
              options:materialList,
            },
            {
              title: '数量',
              key: 'number',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '开始时间',
              key: 'starttime',
              type: FormTypes.date,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '完成时间',
              key: 'finishtime',
              type: FormTypes.date,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            /*{
              title: '计划类型编号',
              key: 'plantypeid',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },*/
            {
              title: '订单编号',
              key: 'orderid',
              type: FormTypes.select,
              defaultValue: '',
              placeholder: '请输入${title}',
              options:orderList,
            },
            {
              title: '客户名称',
              key: 'cname',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '加工顺序',
              key: 'processorder',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '版本号',
              key: 'versionno',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '图号',
              key: 'figureno',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '优先级编号',
              key: 'priorityid',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            /*{
              title: '计划状态编号',
              key: 'planstatusid',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },*/
            /*{
              title: '是否关闭',
              key: 'isclosed',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },*/
            {
              title: '备注',
              key: 'disp',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
          ]
        },
        url: {
          add: "/system/mMainplanabstract/add",
          edit: "/system/mMainplanabstract/edit",
          mMainplan: {
            list: '/system/mMainplanabstract/queryMMainplanByMainId'
          },
        }
      }
    },
    created () {
      getMaterialList()
      getOrderList ()
    },
    methods: {
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        let fieldval = pick(this.model,'createTime','deptid','salesmanid','plantypeid','saledate','auditorid','auditdate','auditstatus','disp')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.mMainplan.list, params, this.mMainplanTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          mMainplanList: allValues.tablesValue[0].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },

      /** 获取所有的editableTable实例 */
      getAllTable() {
        if (!(this.refKeys instanceof Array)) {
          throw this.throwNotArray('refKeys')
        }
        let values = this.refKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },

      /** 遍历所有的JEditableTable实例 */
      eachAllTable(callback) {
        // 开始遍历
        this.getAllTable().then(tables => {
          tables.forEach((item, index) => {
            if (typeof callback === 'function') {
              callback(item, index)
            }
          })
        })
      },

      /** 当点击新增按钮时调用此方法 */
      add() {
        if (typeof this.addBefore === 'function') this.addBefore()
        // 默认新增空数据
        let rowNum = this.addDefaultRowNum
        if (typeof rowNum !== 'number') {
          rowNum = 1
          console.warn('由于你没有在 data 中定义 addDefaultRowNum 或 addDefaultRowNum 不是数字，所以默认添加一条空数据，如果不想默认添加空数据，请将定义 addDefaultRowNum 为 0')
        }
        this.eachAllTable((item) => {
          item.add(rowNum)
        })
        if (typeof this.addAfter === 'function') this.addAfter(this.model)
        this.edit({})
      },
      /** 当点击了编辑（修改）按钮时调用此方法 */
      edit(record) {
        if (typeof this.editBefore === 'function') this.editBefore(record)
        this.visible = true
        this.activeKey = this.refKeys[0]
        this.form.resetFields()
        this.model = Object.assign({}, record)
        if (typeof this.editAfter === 'function') this.editAfter(this.model)
      },
      /** 关闭弹窗，并将所有JEditableTable实例回归到初始状态 */
      close() {
        this.visible = false
        this.eachAllTable((item) => {
          item.initialize()
        })
        this.$emit('close')
      },

      /** 查询某个tab的数据 */
      requestSubTableData(url, params, tab) {
        tab.loading = true
        getAction(url, params).then(res => {
          tab.dataSource = res.result || []
        }).finally(() => {
          tab.loading = false
        })
      },
      /** 发起请求，自动判断是执行新增还是修改操作 */
      request(formData) {
        let url = this.url.add, method = 'post'
        if (this.model.id) {
          url = this.url.edit
          method = 'put'
        }
        this.confirmLoading = true
        console.log("OK验证1111  :"+JSON.stringify(formData)+"   "+method)
        httpAction(url,formData, method).then((res) => {
          if (res.success) {
            //this.$message.success(res.message)

            for (let i = 0; i <formData.mMainplanList.length  ; i++) {
              formData.mMainplanList[i].mainplanabstractid = res.result.id
              postAction('/system/mMainplanabstract/saveOrUpdate',formData.mMainplanList[i]).then((res) => {
                if (res.success) {
                  this.$message.success(res.message)
                  this.$emit('ok')
                  this.close()
                }
              })
            }

            this.$emit('ok')
            this.close()
          } else {
            this.$message.warning(res.message)
          }
        }).finally(() => {
          this.confirmLoading = false
        })
      },

      /* --- handle 事件 --- */

      /** ATab 选项卡切换事件 */
      handleChangeTabs(key) {
        // 自动重置scrollTop状态，防止出现白屏
        getRefPromise(this, key).then(editableTable => {
          editableTable.resetScrollTop()
        })
      },
      /** 关闭按钮点击事件 */
      handleCancel() {
        this.close()
      },
      /** 确定按钮点击事件 */
      handleOk() {
        /** 触发表单验证 */
        this.getAllTable().then(tables => {
          /** 一次性验证主表和所有的次表 */
          return validateFormAndTables(this.form, tables)
        }).then(allValues => {
          if (typeof this.classifyIntoFormData !== 'function') {
            throw this.throwNotFunction('classifyIntoFormData')
          }
          let formData = this.classifyIntoFormData(allValues)

          // 发起请求
          return this.request(formData)
        }).catch(e => {
          if (e.error === VALIDATE_NO_PASSED) {
            // 如果有未通过表单验证的子表，就自动跳转到它所在的tab
            this.activeKey = e.index == null ? this.activeKey : this.refKeys[e.index]
          } else {
            console.error(e)
          }
        })
      },

      /* --- throw --- */

      /** not a function */
      throwNotFunction(name) {
        return `${name} 未定义或不是一个函数`
      },

      /** not a array */
      throwNotArray(name) {
        return `${name} 未定义或不是一个数组`
      }



    }
  }
</script>

<style scoped>
</style>