<template>
  <a-drawer
    :title="title"
    :width="width"
    placement="right"
    :closable="false"
    @close="close"
    :visible="visible">
  
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="生产物料编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'materialencodeid', validatorRules.materialencodeid]" placeholder="请输入生产物料编码"></a-input>
        </a-form-item>
        <a-form-item label="生产车间编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'workshopid', validatorRules.workshopid]" placeholder="请输入生产车间编号"></a-input>
        </a-form-item>
        <a-form-item label="工序编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'workprocessid', validatorRules.workprocessid]" placeholder="请输入工序编号"></a-input>
        </a-form-item>
        <a-form-item label="数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'productnumber', validatorRules.productnumber]" placeholder="请输入数量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="已完成数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'finishednumber', validatorRules.finishednumber]" placeholder="请输入已完成数量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="计划开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择计划开始时间" v-decorator="[ 'planstarttime', validatorRules.planstarttime]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="计划结束时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择计划结束时间" v-decorator="[ 'planendtime', validatorRules.planendtime]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="实际开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择实际开始时间" v-decorator="[ 'realstarttime', validatorRules.realstarttime]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="实际结束时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择实际结束时间" v-decorator="[ 'realendtime', validatorRules.realendtime]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="需要工时" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'needworkhour', validatorRules.needworkhour]" placeholder="请输入需要工时" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="是否外协" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['isoutsource']" :trigger-change="true" dictCode="IsOutsource" placeholder="请选择是否外协"/>
        </a-form-item>
        <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['state']" :trigger-change="true" dictCode="State" placeholder="请选择状态"/>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['disp']" rows="4" placeholder="请输入备注"/>
        </a-form-item>
        
      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  
  export default {
    name: "MProductTaskOrderModal",
    components: { 
      JDate,
      JDictSelectTag,
    },
    data () {
      return {
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
        materialencodeid:{},
        workshopid:{},
        workprocessid:{},
        productnumber:{},
        finishednumber:{},
        planstarttime:{},
        planendtime:{},
        realstarttime:{},
        realendtime:{},
        needworkhour:{},
        isoutsource:{},
        state:{},
        disp:{},
        },
        url: {
          add: "/system/mProductTaskOrder/add",
          edit: "/system/mProductTaskOrder/edit",
        }
     
      }
    },
    created () {
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
          this.form.setFieldsValue(pick(this.model,'materialencodeid','workshopid','workprocessid','productnumber','finishednumber','planstarttime','planendtime','realstarttime','realendtime','needworkhour','isoutsource','state','disp'))
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
        this.form.setFieldsValue(pick(row,'materialencodeid','workshopid','workprocessid','productnumber','finishednumber','planstarttime','planendtime','realstarttime','realendtime','needworkhour','isoutsource','state','disp'))
      }
      
    }
  }
</script>

<style lang="less" scoped>
/** Button按钮间距 */
  .ant-btn {
    margin-left: 30px;
    margin-bottom: 30px;
    float: right;
  }
</style>