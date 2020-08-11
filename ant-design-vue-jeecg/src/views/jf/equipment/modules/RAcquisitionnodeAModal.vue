<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="车间编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <!--<j-dict-select-tag type="list" v-decorator="['workshopid']" :trigger-change="true" dictCode="" placeholder="请选择车间编号"/>-->
          <a-select placeholder="请选择" v-decorator="['workshopid']">
            <a-select-option  v-for="(shop,index) in workshopList" :key="shop.id" :value="shop.id">{{shop.wsname}}</a-select-option>
          </a-select>
        </a-form-item>
          
        <a-form-item label="设备编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <!--<j-dict-select-tag type="list" v-decorator="['equipid']" :trigger-change="true" dictCode="" placeholder="请选择设备编号"/>-->
          <a-select placeholder="请选择" v-decorator="['equipid']">
            <a-select-option  v-for="(sb,index) in sbList" :key="sb.id" :value="sb.id">{{sb.equipname}}</a-select-option>
          </a-select>
        </a-form-item>
          
        <a-form-item label="部署位置（工序）" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <!--<j-dict-select-tag type="list" v-decorator="['location']" :trigger-change="true" dictCode="" placeholder="请选择部署位置（工序）"/>-->
          <a-select placeholder="请选择" v-decorator="['location']">
            <a-select-option  v-for="(process,index) in processList" :key="process.id" :value="process.id">{{process.wpname}}</a-select-option>
          </a-select>
        </a-form-item>
          
        <a-form-item label="位置描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'locationdis', validatorRules.locationdis]" placeholder="请输入位置描述"></a-input>
        </a-form-item>
          
        <a-form-item label="部署日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择部署日期" v-decorator="[ 'datetime', validatorRules.datetime]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="是否采集节点" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['isacquisition']" :trigger-change="true" dictCode="IsAcquisition" placeholder="请选择是否采集节点"/>
        </a-form-item>
          
        <a-form-item label="节点数据类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'nodedatatype', validatorRules.nodedatatype]" placeholder="请输入节点数据类型"></a-input>
        </a-form-item>
          
        <a-form-item label="数据保留天数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'storageperiod', validatorRules.storageperiod]" placeholder="请输入数据保留天数" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="网关编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'gatewayid', validatorRules.gatewayid]" placeholder="请输入网关编号"></a-input>
        </a-form-item>
          
        <a-form-item label="节点状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'nodestatus', validatorRules.nodestatus]" placeholder="请输入节点状态"></a-input>
        </a-form-item>
          
        <a-form-item label="动作数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'actionamount', validatorRules.actionamount]" placeholder="请输入动作数" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="生产数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'productamount', validatorRules.productamount]" placeholder="请输入生产数" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="负责人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'operator', validatorRules.operator]" placeholder="请输入负责人"></a-input>
        </a-form-item>
          
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['disp']" rows="4" placeholder="请输入备注"/>
        </a-form-item>
          
        
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction,getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  
  export default {
    name: "RAcquisitionnodeAModal",
    components: { 
      JDate,
      JDictSelectTag,
    },
    data () {
      return {
        /*工序信息列表*/
        processList: [],
        /*车间信息列表*/
        workshopList: [],
        /*设备信息列表*/
        sbList: [],
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        validatorRules:{
        workshopid:{},
        equipid:{},
        location:{},
        locationdis:{},
        datetime:{},
        isacquisition:{},
        nodedatatype:{},
        storageperiod:{},
        gatewayid:{},
        nodestatus:{},
        actionamount:{},
        productamount:{},
        operator:{},
        disp:{},
        },
        url: {
          add: "/system/rAcquisitionnodeA/add",
          edit: "/system/rAcquisitionnodeA/edit",
        }
     
      }
    },
    created () {
    },
    mounted () {
      this.getProcessList ()
      this.getWorkshopList()
      this.getSBList()
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'workshopid','equipid','location','locationdis','datetime','isacquisition','nodedatatype','storageperiod','gatewayid','nodestatus','actionamount','productamount','operator','disp'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
         
        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'workshopid','equipid','location','locationdis','datetime','isacquisition','nodedatatype','storageperiod','gatewayid','nodestatus','actionamount','productamount','operator','disp'))
      },
      /*获取工序下拉框内容*/
      getProcessList () {
        getAction('/system/mWorkprocess/getProcessList',null).then((res)=>{
          if(res.success){
            /*清空*/
            this.processList.splice(0)
            this.processList = res.result
          }
        });
      },
      /*获取车间下拉框内容*/
      getWorkshopList () {
        getAction('/system/mWorkShop/getAll',null).then((res)=>{
          if(res.success){
            /*清空*/
            this.workshopList.splice(0)
            this.workshopList = res.result
          }
        });
      },
      /*获取设备下拉框内容*/
      getSBList () {
        getAction('/system/rEquipment/getAll',null).then((res)=>{
          if(res.success){
            /*清空*/
            this.sbList.splice(0)
            this.sbList = res.result
          }
        });
      },
      
    }

  }
</script>