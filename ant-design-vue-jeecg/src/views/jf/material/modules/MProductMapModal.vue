<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-row>
          <a-col :span="12">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="产品名称">
            <a-input placeholder="请输入产品名称" v-decorator="['productname', validatorRules.productname]" />
          </a-form-item>
          </a-col>
          <a-col :span="12">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="产品型号">
            <a-input placeholder="请输入产品型号" v-decorator="['producttype', validatorRules.producttype]" />
          </a-form-item>
          </a-col>
          <a-col :span="12">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="产品规格">
            <a-input placeholder="请输入产品规格" v-decorator="['productsize', validatorRules.productsize]" />
          </a-form-item>
          </a-col>
          <a-col :span="12">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="产品颜色">
            <a-input placeholder="请输入产品颜色" v-decorator="['productcolor', validatorRules.productcolor]" />
          </a-form-item>
          </a-col>
          <a-col :span="12">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="产品长度（节）">
            <a-input-number  :min="1" style="width: 100%" placeholder="请输入产品长度（节）" v-decorator="['productlength', validatorRules.productlength]" />
          </a-form-item>
          </a-col>
          <a-col :span="12">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="上刀">
            <!--<a-input placeholder="请输入上刀" v-decorator="['topblade', validatorRules.topblade]" />-->
            <a-select placeholder="请选择" v-decorator="['topblade',validatorRules.topblade]">
              <a-select-option  v-for="(material,index) in materialList" :key="material.id" :value="material.id">{{material.materialname}}</a-select-option>
            </a-select>
          </a-form-item>
          </a-col>
          <a-col :span="12">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="下刀">
            <!--<a-input placeholder="请输入下刀" v-decorator="['bottomblade', validatorRules.bottomblade]" />-->
            <a-select placeholder="请选择" v-decorator="['bottomblade',validatorRules.bottomblade]">
              <a-select-option  v-for="(material,index) in materialList" :key="material.id" :value="material.id">{{material.materialname}}</a-select-option>
            </a-select>
          </a-form-item>
          </a-col>
          <a-col :span="12">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="连接片">
            <!--<a-input placeholder="请输入连接片" v-decorator="['connectionblade', validatorRules.connectionblade]" />-->
            <a-select placeholder="请选择" v-decorator="['connectionblade',validatorRules.connectionblade]">
              <a-select-option  v-for="(material,index) in materialList1" :key="material.id" :value="material.id">{{material.materialname}}</a-select-option>
            </a-select>
          </a-form-item>
          </a-col>
          <a-col :span="12">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="传动片">
            <!--<a-input placeholder="请输入传动片" v-decorator="['driverblade', validatorRules.driverblade]" />-->
            <a-select placeholder="请选择" v-decorator="['driverblade',validatorRules.driverblade]">
              <a-select-option  v-for="(material,index) in materialList2" :key="material.id" :value="material.id">{{material.materialname}}</a-select-option>
            </a-select>
          </a-form-item>
          </a-col>
          <a-col :span="12">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="铆钉">
            <!--<a-input placeholder="请输入铆钉" v-decorator="['rivet', validatorRules.rivet]" />-->
            <a-select placeholder="请选择" v-decorator="['rivet',validatorRules.rivet]">
              <a-select-option  v-for="(material,index) in materialList3" :key="material.id" :value="material.id">{{material.materialname}}</a-select-option>
            </a-select>
          </a-form-item>
          </a-col>
          <a-col :span="12">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="是否带反弹传动片">
          <!--<a-input placeholder="请输入是否带反弹传动片0-否,1-是" v-decorator="['isresilient', {}]" />-->
          <a-select placeholder="请选择" v-decorator="['isresilient',validatorRules.isresilient]">
            <a-select-option :value="0">否</a-select-option>
            <a-select-option :value="1">是</a-select-option>
          </a-select>
        </a-form-item>
        </a-col>
        </a-row>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction,getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"

  export default {
    name: "MProductMapModal",
    data () {
      return {
        /*刀片列表*/
        materialList: [],
        /*连接片列表*/
        materialList1: [],
        /*传动片列表*/
        materialList2: [],
        /*铆钉列表*/
        materialList3: [],
        title:"操作",
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 8 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
          id:{},
          productname:{
            rules: [
              { required: true, message: '请输入产品名称' },
            ]
          },
          producttype:{
            rules: [
              { required: true, message: '请输入产品型号' },
            ]
          },
          productsize:{
            rules: [
              { required: true, message: '请输入产品规格' },
            ]
          },
          productcolor:{
            rules: [
              { required: true, message: '请输入产品颜色' },
            ]
          },
          productlength:{
            rules: [
              { required: true, message: '请输入产品长度' },
            ]
          },
          topblade:{
            rules: [
              { required: true, message: '请选择产品上刀' },
            ]
          },
          bottomblade:{
            rules: [
              { required: true, message: '请输入产品下刀' },
            ]
          },
          connectionblade:{
            rules: [
              { required: true, message: '请输入产品连接片' },
            ]
          },
          driverblade:{
            rules: [
              { required: true, message: '请输入产品传动片' },
            ]
          },
          rivet:{
            rules: [
              { required: true, message: '请输入产品铆钉' },
            ]
          },
          isresilient:{
            rules: [
              { required: true, message: '请选择是否带反弹传动片' },
            ]
          },
          createBy:{},
          createTime:{},
          updateBy:{},
          updateTime:{},
          delFlag:{},
        },
        url: {
          add: "/system/mProductMap/add",
          edit: "/system/mProductMap/edit",
        },
      }
    },
    created () {
    },
    mounted () {
      this.getMaterialList ()
      this.getMaterialList1 ()
      this.getMaterialList2 ()
      this.getMaterialList3 ()
    },
    methods: {
      /*materialtypeencode*/
      /*获取物料下拉框内容*/
      getMaterialList () {
        getAction('/system/mMaterial/getMaterialList',{'materialtypeencode':'1202843338441859074'}).then((res)=>{
          if(res.success){
            /*清空*/
            this.materialList.splice(0)
            this.materialList = res.result
          }
        });
      },
      getMaterialList1 () {
        getAction('/system/mMaterial/getMaterialList',{'materialtypeencode':'1202843382716932098'}).then((res)=>{
          if(res.success){
            /*清空*/
            this.materialList1.splice(0)
            this.materialList1 = res.result
          }
        });
      },
      getMaterialList2 () {
        getAction('/system/mMaterial/getMaterialList',{'materialtypeencode':'1202843216106594305'}).then((res)=>{
          if(res.success){
            /*清空*/
            this.materialList2.splice(0)
            this.materialList2 = res.result
          }
        });
      },
      getMaterialList3 () {
        getAction('/system/mMaterial/getMaterialList',{'materialtypeencode':'1202843448978546690'}).then((res)=>{
          if(res.success){
            /*清空*/
            this.materialList3.splice(0)
            this.materialList3 = res.result
          }
        });
      },
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'productname','producttype','productsize','productcolor','productlength','topblade','bottomblade','connectionblade','driverblade','rivet','isresilient','delFlag'))
		  //时间格式化
        });

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
            //时间格式化

            console.log(formData)
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


    }
  }
</script>

<style lang="less" scoped>

</style>