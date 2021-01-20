<template>
  <el-container>
    <el-row :gutter="20">
      <el-col :span="24"><b>123</b></el-col>
    </el-row>
    <el-row :gutter="10">
      <el-col :span="24"><b>123</b></el-col>
    </el-row>
  </el-container>
</template>

<script>
export default {
  name: 'Test'
}
</script>

<style scoped>
.el-col {
  min-height: 1px
}
</style>
