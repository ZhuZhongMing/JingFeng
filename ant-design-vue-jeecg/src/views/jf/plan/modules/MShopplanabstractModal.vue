<template>
  <a-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel">
    <a-spin :spinning="confirmLoading">
      <!-- 主表单区域 -->
      <a-form :form="form">
        <a-row>

          <a-col :span="24">
            <a-form-item label="部门编号" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <j-select-depart v-decorator="['deptid',validatorRules.deptid]" :trigger-change="true" @change="handleDeptChange"/>
            </a-form-item>
          </a-col>

          <a-col :span="24">
            <a-form-item label="车间编号" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <a-select placeholder="请选择" v-decorator="['workshopid', validatorRules.workshopid]">
                <a-select-option  v-for="(shop,index) in workshopList" :key="shop.value" :value="shop.value">{{shop.title}}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>

          <a-col :span="24">
            <a-form-item label="订单编号" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
                <a-select placeholder="请选择" v-decorator="['orderid', validatorRules.orderid]" @change="handleOrderChange">
                  <a-select-option  v-for="(shop,index) in orderList" :key="shop.id" :value="shop.id">{{shop.id}}</a-select-option>
                </a-select>
            </a-form-item>
          </a-col>

          <!--<a-col :span="12">
            <a-form-item label="批准人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-select-user-by-dep v-decorator="['personincharge']" :trigger-change="true"/>
            </a-form-item>
          </a-col>

          <a-col :span="12">
            <a-form-item label="批准日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择批准日期" v-decorator="[ 'inchargedate', validatorRules.inchargedate]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
            </a-form-item>
          </a-col>

          <a-col :span="12">
            <a-form-item label="审核人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-select-user-by-dep v-decorator="['auditor']" :trigger-change="true"/>
            </a-form-item>
          </a-col>

          <a-col :span="12">
            <a-form-item label="审核日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择审核日期" v-decorator="[ 'auditdate', validatorRules.auditdate]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
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
          <!--<a-col :span="24">
          <a-form-item label="合同附件" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
            <a-upload
              listType="picture-card"
              class="avatar-uploader"
              :showUploadList="false"
              :action="uploadAction"
              :data="{'isup':1}"
              :headers="headers"
              :beforeUpload="beforeUpload"
              @change="handleChange"
            >
              &lt;!&ndash; v-if="picUrl" model.accessory&ndash;&gt;
              <img v-if="picUrl" :src="getAvatarView()" alt="附件" style="height:104px;max-width:300px"/>
              <div v-else>
                <a-icon :type="uploadLoading ? 'loading' : 'plus'" />
                <div class="ant-upload-text">上传</div>
              </div>
            </a-upload>
          </a-form-item>
            &lt;!&ndash;{{model.accessory}}&ndash;&gt;
          </a-col>-->
        </a-row>
      </a-form>
      <!--{{workshopList}}<br/>
      {{materialList}}<br/>
      {{mShopplanTable.columns[1].options}}-->
      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="生产计划明细" :key="refKeys[0]" :forceRender="true">
          <!--<j-editable-table
            :ref="refKeys[0]"
            :loading="mShopplanTable.loading"
            :columns="mShopplanTable.columns"
            :dataSource="mShopplanTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>-->
          <my-j-editable-table
            :ref="refKeys[0]"
            :loading="mShopplanTable.loading"
            :columns="mShopplanTable.columns"
            :dataSource="mShopplanTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true" v-if="workshopList.length > 0"/>
        </a-tab-pane>


      </a-tabs>

    </a-spin>
  </a-modal>
</template>

<script>

  import { ACCESS_TOKEN } from "@/store/mutation-types"
  import Vue from 'vue'

  import pick from 'lodash.pick'
  import { FormTypes,getRefPromise } from '@/utils/JEditableTableUtil'
  /*import { JEditableTableMixin } from '@/mixins/JEditableTableMixin'*/
  import JDate from '@/components/jeecg/JDate'
  import JSelectDepart from '@/components/jeecgbiz/JSelectDepart'
  import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"

  import MyJEditableTable from '@/components/jeecg/JEditableTable'

  import JEditableTable from '@/components/jeecg/JEditableTable'
  import { VALIDATE_NO_PASSED, validateFormAndTables } from '@/utils/JEditableTableUtil'
  import { httpAction, getAction,postAction } from '@/api/manage'


  /*车间信息列表*/
  const workshopList = []
  /*全查询车间信息*/
/*  function getWorkShopList () {
    getAction('/system/mWorkShop/getAll',null).then((res)=>{
      if(res.success){
        /!*清空*!/
        workshopList.splice(0)
        //this.materialList = res.result
        for (let i = 0; i < res.result.length ; i++) {
          //console.log("id:"+res.result[i].id+"  name:"+res.result[i].materialname)
          workshopList.push({
            value : res.result[i].id,
            title : res.result[i].wsname
          })

        }
      }
    });
  }*/
  /*根据部门编号查询车间信息*/
  /*function getWorkShopListByDeptid (value) {
    let mWareHouse ={
      'deptid': value
    }
    getAction('/system/mWorkShop/getAll',mWareHouse).then((res)=>{
      if(res.success){
        /!*清空*!/
        workshopList.splice(0)
        for (let i = 0; i < res.result.length ; i++) {
          workshopList.push({
            value : res.result[i].id,
            title : res.result[i].wsname
          })
        }
      }
    });
  }*/
  /*物料列表*/
  const materialList = []
  /*function getMaterialList () {
    getAction('/system/mMaterial/getMaterialList',null).then((res)=>{
      if(res.success){
        /!*清空*!/
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
  }*/



  export default {
    name: 'MShopplanabstractModal',
    /*mixins: [JEditableTableMixin],*/
    components: {
      JDate,
      JSelectDepart,
      JSelectUserByDep,
      JDictSelectTag,
      JEditableTable,
      MyJEditableTable
    },
    data() {
      return {
        picUrl: "",
        uploadLoading:false,
        headers:{},
        workshopList,
        materialList,
        /*部门编号*/
        mydeptid:'',
        /*订单编号*/
        myorderid:'',
        /*订单信息列表*/
        orderList: [],

        title: '操作',
        visible: false,
        form: this.$form.createForm(this),
        confirmLoading: false,
        model: {},
        labelCol: {
          span: 6
        },
        wrapperCol: {
          span: 16
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
          deptid:{
            rules: [{
              required: true, message: '请选择部门!'
            }]
          },
          workshopid:{
            rules: [{
              required: true, message: '请选择车间!'
            }]
          },
          orderid:{},
          personincharge:{},
          inchargedate:{},
          auditor:{},
          auditdate:{},
          auditstatus:{},
          disp:{},
          accessory:{}
        },
        refKeys: ['mShopplan', ],
        tableKeys:['mShopplan', ],
        activeKey: 'mShopplan',
        // 车间计划明细
        mShopplanTable: {
          loading: false,
          dataSource: [],
          columns: [
            /*{
              title: '车间计划编号',
              key: 'shopplanid',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },*/
            /*{
              title: '车间编号',
              key: 'workshopid',
              type: FormTypes.select,
              defaultValue: '',
              options:this.workshopList,
              placeholder: '请输入${title}',
            },*/
            {
              title: '待加工产品',
              key: 'materialencode',
              type: FormTypes.select,
              options:this.materialList,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            /*{
              title: '加工类型',
              key: 'stype',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },*/
            {
              title: '数量',
              key: 'number',
              type: FormTypes.inputNumber,
              defaultValue: '',
              placeholder: '请输入${title}',
              disabled:false /*数量输入框的状态*/
            },
            {
              title: '计划时间',
              key: 'plantime',
              type: FormTypes.datetime,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            /*{
              title: '开始时间',
              key: 'starttime',
              type: FormTypes.datetime,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '完成时间',
              key: 'finishtime',
              type: FormTypes.datetime,
              defaultValue: '',
              placeholder: '请输入${title}',
            },*/
            /*{
              title: '主计划流水号',
              key: 'mainplanid',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },*/
            {
              title: '订单明细编号',
              key: 'orderid',
              type: FormTypes.input,
              options:[],
              defaultValue: '',
              disabled:true,
              placeholder: '请选择订单编号',
            },
            /*{
              title: '计划状态',
              key: 'planstatus',
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
          fileUpload: window._CONFIG['domianURL']+"/sys/common/upload",
          imgerver: window._CONFIG['domianURL']+"/sys/common/view",
          add: "/system/mShopplanabstract/add",
          edit: "/system/mShopplanabstract/edit",
          mShopplan: {
            list: '/system/mShopplanabstract/queryMShopplanByMainId'
          },
        }
      }
    },
    created () {

      const token = Vue.ls.get(ACCESS_TOKEN);
      this.headers = {"X-Access-Token":token}

      this.getWorkShopList ()
      this.getMaterialList()
    },
    mounted () {

      this.getOrderList()
    },
    computed:{
      uploadAction:function () {
        return this.url.fileUpload;
      }
    },
    methods: {
      beforeUpload: function(file){
        var fileType = file.type;
        //alert(fileType+"    "+typeof fileType)
        if(fileType.indexOf('image')<0){
          this.$message.warning('请上传图片');
          return false;
        }
        //TODO 验证文件大小
      },
      handleChange (info) {
        this.picUrl = "";
        this.model.accessory　= "";
        if (info.file.status === 'uploading') {
          this.uploadLoading = true;
          return
        }
        if (info.file.status === 'done') {
          var response = info.file.response;
          this.uploadLoading = false;
          //console.log(response);
          if(response.success){
            this.model.accessory = response.message;
            //console.log("response:"+response.message)
            this.picUrl = "Has no pic url yet";
          }else{
            this.$message.warning(response.message);
          }
        }
      },
      getAvatarView(){
        //console.log("路径："+this.model.accessory)
        return this.url.imgerver +"/"+ this.model.accessory;
      },
      /*判断添加还是修改*/
      /*checkAddOrUpdate(){
        console.log("判断："+this.model.orderid)
      },*/
      /*全查询产品信息*/
      getMaterialList () {
        getAction('/system/mProductMap/getAllList',null).then((res)=>{
          if(res.success){
            /*清空*/
            this.materialList.splice(0)
            //this.materialList = res.result
            for (let i = 0; i < res.result.length ; i++) {
              //console.log("id:"+res.result[i].id+"  name:"+res.result[i].materialname)
              this.materialList.push({
                value : res.result[i].id,
                title : res.result[i].productname
              })

            }
            //this.mShopplanTable.columns[1].options.splice(0)
            this.mShopplanTable.columns[0].options = []
            this.mShopplanTable.columns[0].options = this.materialList
          }
        });
      },
      /*根据订单号查询物料信息*/
      getMaterialListByOrderid (value) {
        let orderid = {
          'orderid': value
        }
        getAction('/system/mOrder/queryOrderListByMainId',orderid).then((res)=>{
          if(res.success){
            //console.log("明细："+JSON.stringify(res.result) +"sss   "+orderid)
            /*清空*/
            this.materialList.splice(0)
            //this.mShopplanTable.columns[1].options.splice(0)//改变传递至自组建中的值
            this.mShopplanTable.columns[0].options = []
            //this.mShopplanTable.dataSource = res.result//根据订单详情指定计划内容
            let d = []
            //this.materialList = res.result
            for (let i = 0; i < res.result.length ; i++) {
              this.materialList.push({
                value : res.result[i].materialencode,
                title : res.result[i].productname
              })

              /*物料编码*/
             /* res.result[i].materialencode = (res.result[i].materialencode +","+ res.result[i].id)*/

              /*数量*/
              res.result[i].number = res.result[i].allotnumber
              /*订单明细编号*/
              res.result[i].orderid = res.result[i].id
              /*主键*/
              res.result[i].id = ''
              /*备注*/
              res.result[i].disp = ''
              /*********************************************************************************************/
              this.mShopplanTable.columns[0].options.push({
                /*value : (res.result[i].materialencode +","+ res.result[i].id),*/
                value : res.result[i].materialencode,
                title : res.result[i].productname
              })
            }

            /*改变select属性*/
            this.mShopplanTable.columns[0].disabled = true
            this.mShopplanTable.dataSource = res.result//根据订单详情指定计划内容
          }
        });
      },
      /*全查询车间信息*/
      getWorkShopList () {
        getAction('/system/mWorkShop/getAll',null).then((res)=>{
          if(res.success){
            /*清空*/
            this.workshopList.splice(0)
            //this.materialList = res.result
            for (let i = 0; i < res.result.length ; i++) {
              //console.log("id:"+res.result[i].id+"  name:"+res.result[i].materialname)
              this.workshopList.push({
                value : res.result[i].id,
                title : res.result[i].wsname
              })

            }
            //this.mShopplanTable.columns[0].options.splice(0)
            this.mShopplanTable.columns[0].options = []
            this.mShopplanTable.columns[0].options = this.workshopList

          }
        });
      },
        /*根据部门编号查询车间信息*/
       getWorkShopListByDeptid (value) {
        let mWareHouse ={
          'deptid': value
        }
        getAction('/system/mWorkShop/getAll',mWareHouse).then((res)=>{
          if(res.success){
            /*清空*/
            this.workshopList.splice(0)
            //this.mShopplanTable.columns[0].options.splice(0)//改变传递至自组建中的值
            //this.workshopList = []
            if (res.result.length <= 0) {

              this.form.setFieldsValue({
                'workshopid':''
              });

              this.$message.warning("该部门没有生产车间")
            } else {
              for (let i = 0; i < res.result.length ; i++) {
                this.workshopList.push({
                  value : res.result[i].id,
                  title : res.result[i].wsname
                })
              }
            }

          }
        });
      },
      /*改变部门编号*/
      handleDeptChange(value) {
        this.mydeptid = value
        console.log("部门编号："+this.mydeptid)
        this.getWorkShopListByDeptid(value)
      },
      /*改变订单编号*/
      handleOrderChange(value) {
        this.myorderid = value
        this.getMaterialListByOrderid (value)
      },
      /*获取订单下拉内容*/
      getOrderList () {
        getAction('/system/mOrder/getAll',null).then((res)=>{
          if(res.success){
            /*清空*/
            this.orderList.splice(0)
            this.orderList = res.result
          }
        });
      },
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        let fieldval = pick(this.model,'deptid','workshopid','orderid','personincharge','inchargedate','auditor','auditdate','auditstatus','disp','accessory')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        //console.log("修改时的id:"+this.model.id)
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.mShopplan.list, params, this.mShopplanTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          mShopplanList: allValues.tablesValue[0].values,
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

        /*this.picUrl = "";
        this.model.accessory = "";*/

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

        if (record.deptid == null || record.deptid == ''){
          this.getWorkShopListByDeptid()/*根据部门查询*/
        } else {
          this.getWorkShopListByDeptid(record.deptid)/*根据部门查询*/

        }
        if (record.orderid == null || record.orderid == ''){
          this.getMaterialList()
        } else {
          /*改变select属性*/
          this.mShopplanTable.columns[0].disabled = true
          //this.getMaterialListByOrderid(record.orderid)
          this.visible = true
        }

        if (typeof this.editBefore === 'function') this.editBefore(record)
        this.visible = true
        this.activeKey = this.refKeys[0]
        this.form.resetFields()
        this.model = Object.assign({}, record)

        if (record.accessory != '' && record.accessory != null) {
          this.picUrl = "Has no pic url yet";
          //this.model.accessory = "";
        }

        //console.log("修改时调用："+JSON.stringify(record))
        if (typeof this.editAfter === 'function') this.editAfter(this.model)
      },
      /** 关闭弹窗，并将所有JEditableTable实例回归到初始状态 */
      close() {
        this.visible = false
        this.picUrl = "";
        //this.materialList.splice(0)
        //materialList.splice(0)
        this.eachAllTable((item) => {
          item.initialize()
        })

        /*改变select属性*/
        this.mShopplanTable.columns[0].disabled = false
        this.getMaterialList()
        this.getWorkShopList()

        this.$emit('close')
      },

      /** 查询某个tab的数据 */
      requestSubTableData(url, params, tab) {
        tab.loading = true
        getAction(url, params).then(res => {
          tab.dataSource = res.result || []

          //console.log("子表数据："+JSON.stringify(res.result))

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
        console.log("新增车间计划时的主要数据:  "+JSON.stringify(formData)+"   "+method)


        httpAction(url,formData, method).then((res) => {
          if (res.success) {
            //this.$message.success(res.message)

            for (let i = 0; i <formData.mShopplanList.length  ; i++) {
              formData.mShopplanList[i].shopplanid = res.result.id
              formData.mShopplanList[i].workshopid = formData.workshopid // 车间编号
              postAction('/system/mShopplanabstract/saveOrUpdate',formData.mShopplanList[i]).then((res) => {
                if (res.success) {
                  if ((i+1) == formData.mShopplanList.length){
                    this.$message.success(res.message)
                    /*this.$emit('ok')
                    this.close()*/


                    if (formData.orderid != null && formData.orderid != '') {
                      /*判断订单分配情况*/
                      getAction('/system/mOrder/updateById',{'orderid':formData.orderid}).then((res) => {
                        if (res.success) {
                          console.log("该订单下可分配数量："+res.result)
                        }
                      })
                    }
                    /*关闭弹窗*/
                    this.$emit('ok')
                    this.close()

                  }
                }
              })
            }

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