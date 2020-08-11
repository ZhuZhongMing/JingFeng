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

        <a-form-item label="编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'wpencode', validatorRules.wpencode]" placeholder="请输入编码"></a-input>
        </a-form-item>
          
        <a-form-item label="工序名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'wpname', validatorRules.wpname]" placeholder="请输入工序名称"></a-input>
        </a-form-item>
          
        <a-form-item label="工序性质" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'wpproperty', validatorRules.wpproperty]" placeholder="请输入工序性质"></a-input>
        </a-form-item>
          
        <a-form-item label="自动派工" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['isautotaskassign']" :trigger-change="true" dictCode="IsAutoTaskAssign" placeholder="请选择自动派工"/>
        </a-form-item>
          
        <a-form-item label="方向编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'stepnumber', validatorRules.stepnumber]" placeholder="请输入方向编号"></a-input>
        </a-form-item>
          
        <a-form-item label="是否禁用进度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['isstop']" :trigger-change="true" dictCode="IsStop" placeholder="请选择是否禁用进度"/>
        </a-form-item>
          
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <!--<a-input v-decorator="[ 'disp', validatorRules.disp]" placeholder="请输入备注"></a-input>-->
          <a-textarea v-decorator="['disp']" rows="4" placeholder="请输入备注"/>
        </a-form-item>
          
        
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  
  export default {
    name: "MWorkprocessModal",
    components: { 
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
        wpencode:{},
        wpname:{},
        wpproperty:{},
        isautotaskassign:{},
        stepnumber:{},
        isstop:{},
        disp:{},
        },
        url: {
          add: "/system/mWorkprocess/add",
          edit: "/system/mWorkprocess/edit",
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
          this.form.setFieldsValue(pick(this.model,'wpencode','wpname','wpproperty','isautotaskassign','stepnumber','isstop','disp'))
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
        this.form.setFieldsValue(pick(row,'wpencode','wpname','wpproperty','isautotaskassign','stepnumber','isstop','disp'))
      },
    }
  }
</script>