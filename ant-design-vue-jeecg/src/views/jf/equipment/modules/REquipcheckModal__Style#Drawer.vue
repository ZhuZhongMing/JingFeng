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

        <a-form-item label="设备编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'equipid', validatorRules.equipid]" placeholder="请输入设备编号"></a-input>
        </a-form-item>
        <a-form-item label="保养类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'maintaintype', validatorRules.maintaintype]" placeholder="请输入保养类型"></a-input>
        </a-form-item>
        <a-form-item label="保养结果" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'maintainresult', validatorRules.maintainresult]" placeholder="请输入保养结果"></a-input>
        </a-form-item>
        <a-form-item label="工时数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'workinghours', validatorRules.workinghours]" placeholder="请输入工时数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="隐患处理提醒" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'troubleremind', validatorRules.troubleremind]" placeholder="请输入隐患处理提醒"></a-input>
        </a-form-item>
        <a-form-item label="隐患修复预计日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择隐患修复预计日期" v-decorator="[ 'predictdate', validatorRules.predictdate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="保养部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-select-depart v-decorator="['maintaindept']" :trigger-change="true"/>
        </a-form-item>
        <a-form-item label="保养人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'maintainoperator', validatorRules.maintainoperator]" placeholder="请输入保养人"></a-input>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['disp']" rows="4" placeholder="请输入备注"/>
        </a-form-item>
        <a-form-item label="删除标识" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['delFlag']" :trigger-change="true" dictCode="del_flag" placeholder="请选择删除标识"/>
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
  import JSelectDepart from '@/components/jeecgbiz/JSelectDepart'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  
  export default {
    name: "REquipcheckModal",
    components: { 
      JDate,
      JSelectDepart,
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
        equipid:{},
        maintaintype:{},
        maintainresult:{},
        workinghours:{},
        troubleremind:{},
        predictdate:{},
        maintaindept:{},
        maintainoperator:{},
        disp:{},
        delFlag:{},
        },
        url: {
          add: "/system/rEquipcheck/add",
          edit: "/system/rEquipcheck/edit",
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
          this.form.setFieldsValue(pick(this.model,'equipid','maintaintype','maintainresult','workinghours','troubleremind','predictdate','maintaindept','maintainoperator','disp','delFlag'))
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
        this.form.setFieldsValue(pick(row,'equipid','maintaintype','maintainresult','workinghours','troubleremind','predictdate','maintaindept','maintainoperator','disp','delFlag'))
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