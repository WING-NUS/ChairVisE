<template>
  <el-collapse v-model="activeNames">
    <el-collapse-item title="How to use our user guide?" name="1">
      We have provided 4 different panels for our user guide:
      <ol>
        <li> Overview</li>
        <li> Features - To describe the features that our system can provide </li>
        <li> Instructions - To provide a detailed instructions on how to use ChairVise features </li>
        <li> FAQ - To list out commonly asked questions by user </li>
      </ol>
      To use our user guide, click on any of the tabs above to begin exploring.
    </el-collapse-item>
    <el-collapse-item title="My chart seems to overlapped" name="2">
      <p> Scroll all the way to the top and click "PDF" / "Powerpoint" again. </p>
    </el-collapse-item>
    <el-collapse-item title="Cannot upload Review Record with no header for EasyChair" name="3">
      <p> Use the with header option. </p>
    </el-collapse-item>
  </el-collapse>
</template>

<script>
  export default {
    name: "FeatureGuide",
    data() {
      return {
        activeNames: []
      };
    },
  }
</script>

<style scoped>
</style>
