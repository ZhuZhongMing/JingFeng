<template>
  <div class="user-wrapper" :class="theme">
    <!-- 大屏连接 -->
    <span class="action">

        <a class="logout_title" href="http://47.105.51.27:91/" target="_blank">
        <!--<a-icon type="question-circle-o"></a-icon>-->
          <a-icon type="link" />前往大屏
        </a>

    </span>

    &nbsp;&nbsp;&nbsp;

    <span class="action" v-if="flag">
      <span class="logout_title" @click="showModal">
        <!--<a-icon type="question-circle-o"></a-icon>-->
        <a-icon type="highlight" />点击签到
      </span>
    </span>
    <!-- 签到对话框 -->
    <a-modal
      title="签到"
      :visible="visible"
      @ok="handleOk"
      :confirmLoading="confirmLoading"
      @cancel="handleCancel"
    >
      <p>
        <span>签到设备：</span>
        <a-select placeholder="请选择设备" v-model="id" size="large" style="width: 150px;">
          <a-select-option  v-for="(sb,index) in sbList" :key="sb.id" :value="sb.id">{{sb.equipname}}</a-select-option>
        </a-select>
      </p>

    </a-modal>


    <header-notice class="action"/>
    <a-dropdown>
      <span class="action action-full ant-dropdown-link user-dropdown-menu">
        <a-avatar class="avatar" size="small" :src="getAvatar()"/>
        <span v-if="isDesktop()">欢迎您，{{ nickname() }}</span>
      </span>
      <a-menu slot="overlay" class="user-dropdown-menu-wrapper">
        <!--<a-menu-item key="0">
          <router-link :to="{ name: 'account-center' }">
            <a-icon type="user"/>
            <span>个人中心</span>
          </router-link>
        </a-menu-item>-->
        <!--<a-menu-item key="1">
          <router-link :to="{ name: 'account-settings-base' }">
            <a-icon type="setting"/>
            <span>账户设置</span>
          </router-link>
        </a-menu-item>-->
        <a-menu-item key="3"  @click="systemSetting">
           <a-icon type="tool"/>
           <span>系统设置</span>
        </a-menu-item>
        <a-menu-item key="4" @click="updatePassword">
          <a-icon type="setting"/>
          <span>密码修改</span>
        </a-menu-item>
        <a-menu-item key="5" @click="updateCurrentDepart">
          <a-icon type="cluster"/>
          <span>切换部门</span>
        </a-menu-item>
       <!-- <a-menu-item key="2" disabled>
          <a-icon type="setting"/>
          <span>测试</span>
        </a-menu-item>
        <a-menu-divider/>
        <a-menu-item key="3">
          <a href="javascript:;" @click="handleLogout">
            <a-icon type="logout"/>
            <span>退出登录</span>
          </a>
        </a-menu-item>-->
      </a-menu>
    </a-dropdown>
    <span class="action">
      <a class="logout_title" href="javascript:;" @click="handleLogout">
        <a-icon type="logout"/>
        <span v-if="isDesktop()">&nbsp;退出登录</span>
      </a>
    </span>
    <user-password ref="userPassword"></user-password>
    <depart-select ref="departSelect" :closable="true" title="部门切换"></depart-select>
    <setting-drawer ref="settingDrawer" :closable="true" title="系统设置"></setting-drawer>
  </div>
</template>

<script>
  import HeaderNotice from './HeaderNotice'
  import UserPassword from './UserPassword'
  import SettingDrawer from "@/components/setting/SettingDrawer";
  import DepartSelect from './DepartSelect'
  import { mapActions, mapGetters } from 'vuex'
  import { mixinDevice } from '@/utils/mixin.js'

  import { getAction,postAction } from '@/api/manage'

  /*<a-select placeholder="请选择" v-decorator="['equipid']">
            <a-select-option  v-for="(sb,index) in sbList" :key="sb.id" :value="sb.id">{{sb.equipname}}</a-select-option>
          </a-select>*/

  export default {
    name: "UserMenu",
    mixins: [mixinDevice],
    components: {
      HeaderNotice,
      UserPassword,
      DepartSelect,
      SettingDrawer
    },
    props: {
      theme: {
        type: String,
        required: false,
        default: 'dark'
      }
    },
    data () {
      return  {
        flag:true,
        /*设备信息列表*/
        sbList: [],
        visible: false,
        confirmLoading: false,
        id:'',
      }
    },
    mounted () {
      this.getSBList ()
      this.checkSign ()
    },
    methods: {
      /*检查今日是否签到*/
      checkSign () {
        getAction('/system/mSignIn/checkSign',null).then((res)=>{
          if(res.success){
            if (parseInt(res.result) > 0) {
              this.flag = false
            } else {
              this.flag = true
            }
          }
        });
      } ,
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
      showModal() {
        this.visible = true;
      },
      handleOk(e) {
        //console.log("id:"+this.id)
        this.confirmLoading = true;
        if (this.id.length > 0 ) {
          postAction('/system/mSignIn/add', {'equipment':this.id}).then((res) => {
            if(res.success){
              this.$message.success(res.message)
              this.flag = false;
              this.visible = false;
              this.confirmLoading = false;
            }else{
              this.$message.warning(res.message)
              this.visible = false;
              this.confirmLoading = false;
            }
          })
        } else {
          this.$message.warning('请先选择签到设备号')

          this.confirmLoading = false;
        }

        /*this.confirmLoading = true;
        setTimeout(() => {
          this.visible = false;
          this.confirmLoading = false;
        }, 2000);*/
      },
      handleCancel(e) {

        this.visible = false;
      },
      ...mapActions(["Logout"]),
      ...mapGetters(["nickname", "avatar","userInfo"]),
      getAvatar(){
        console.log('url = '+ window._CONFIG['imgDomainURL']+"/"+this.avatar())
        return window._CONFIG['imgDomainURL']+"/"+this.avatar()
      },
      handleLogout() {
        const that = this

        this.$confirm({
          title: '提示',
          content: '真的要注销登录吗 ?',
          onOk() {
            return that.Logout({}).then(() => {
                window.location.href="/";
              //window.location.reload()
            }).catch(err => {
              that.$message.error({
                title: '错误',
                description: err.message
              })
            })
          },
          onCancel() {
          },
        });
      },
      updatePassword(){
        let username = this.userInfo().username
        this.$refs.userPassword.show(username)
      },
      updateCurrentDepart(){
        this.$refs.departSelect.show()
      },
      systemSetting(){
        this.$refs.settingDrawer.showDrawer()
      }
    }
  }
</script>

<style scoped>
  .logout_title {
    color: inherit;
    text-decoration: none;
  }
</style>