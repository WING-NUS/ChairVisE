<template>
  <el-menu class="navbar" mode="horizontal" active-text-color="#000000" router>
    <el-menu-item index="/home">
      <img id="logo" alt="Chairvise logo" src="@/assets/logo.png">
      <span style="font-size:15px; color: #1e9fc4"> CHAIRVISE V4.0 </span>
    </el-menu-item>
    <el-menu-item v-if="!isLogin" index="" class="login-button" >
      <Login/>
    </el-menu-item>
    <el-submenu index="2" class="navbar-submenu" popper-class="navbar-submenu-menu" v-if="isLogin" style="{}">
      <template slot="title">
        <i class="el-icon-user-solid"/>{{ userNickName }}
      </template>
      <el-menu-item index="/" v-if="isLogin" @click="logout">
        <i class="el-icon-close"/>
        Logout
      </el-menu-item>
    </el-submenu>
    <el-menu-item class="items" index="/ChairHub" :disabled="!isLogin" >ChairHub</el-menu-item>
    <el-menu-item class="items" index="/ConferenceList" :disabled="!isLogin">Conferences</el-menu-item>
  </el-menu>
</template>

<script>
import Login from '@/components/Login'
import router from '@/router'
import { decode } from 'jsonwebtoken'

export default {
  name: 'MenuBar',
  data () {
    return {
      isLogin: window.sessionStorage.getItem('token'),
      isFullscreenLoading: false
    }
  },
  components: {
    Login
  },
  computed: {
    userNickName () {
      return decode(window.sessionStorage.getItem('token')).name
    }
  },
  methods: {
    logout () {
      window.sessionStorage.clear()
      router.go(0)
    }
  }
}
</script>
<style scoped>

#logo {
  margin-right: 5px;
  width: 40px;
  height: 40px;
}

.el-menu-item {
  font-size: 15px;
}

.navbar > .items {
  float: right
}

i.el-icon-user-solid {
  margin-right: 0;
}

.login-button {
  float: right;
}

.navbar-submenu {
  font-weight: 400;
  font-size: 13px;
}

.navbar > .el-menu-item.is-active {
  border-bottom: 0;
}

.el-menu--horizontal > .el-submenu.navbar-submenu {
  float:right;
}
</style>
