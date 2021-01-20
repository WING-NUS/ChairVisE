<template>
  <button v-google-signin-button="clientId" class="google-signin-button">
    Login with Google
  </button>
</template>

<script>
import GoogleSignInButton from 'vue-google-signin-button-directive'
// eslint-disable-next-line no-unused-vars
import jsonwebtoken from 'jsonwebtoken'
import axios from 'axios'
import router from '@/router'

export default {
  directives: {
    GoogleSignInButton
  },
  data: () => ({
    clientId: '15299852497-a3u74lkbmpmg27tsogtstcikbu9ccftd.apps.googleusercontent.com'
  }),
  methods: {
    OnGoogleAuthSuccess (idToken) {
      axios.post('/api/login', null, {
        headers: {
          Authorization: idToken
        }
      }).then((res) => {
        if (res.status !== 201) {
          this.$message({
            duration: 2000,
            message: 'Login error with code ' + res.status,
            type: 'error'
          })
        } else {
          // console.log(res)
          window.sessionStorage.setItem('token', idToken)
          router.go(0)
          this.$message.success({
            duration: 1000,
            message: 'Welcome ' + jsonwebtoken.decode(idToken).name,
            type: 'error'
          })
        }
      })
      // Receive the idToken and make your magic with the backend
    },
    OnGoogleAuthFail (error) {
      console.log(error)
    }
  }
}
</script>

<style>
.google-signin-button {
  /*  color: black;*/
  /*  background-color: #99ccff;*/
  /*  height: 50px;*/
  font-size: 15px;
  /*  border-radius: 10px;*/
  /*  color: white;*/
  padding: 15px 15px 15px 15px;
  background: #e7e7e7;
  border-radius: 10px;
  border-color: #545c64;
}
</style>
