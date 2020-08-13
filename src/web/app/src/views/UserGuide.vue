<template>
  <div>
    <div>
      <el-container>
        <el-main>  
          <el-tabs :tab-position="tabPosition">
            <el-tab-pane label="Overview">
              <OverviewGuide/>
            </el-tab-pane>
            <el-tab-pane label="Features">
              <FeatureGuide/>
            </el-tab-pane>
            <el-tab-pane label="Instructions">              
              <InstructionsGuide/>
            </el-tab-pane>
            <el-tab-pane label="FAQ">
              <FAQGuide/>
            </el-tab-pane>
          </el-tabs>
        </el-main>
      </el-container>
    </div>
  </div>
</template>

<script>
  // @ is an alias to /src
  import OverviewGuide from '@/components/userPageDetail/OverviewGuide.vue'
  import FeatureGuide from '@/components/userPageDetail/FeatureGuide.vue'
  import InstructionsGuide from '@/components/userPageDetail/InstructionsGuide.vue'
  import FAQGuide from '@/components/userPageDetail/FAQGuide.vue'

  export default {
    name: 'userGuide',
    components: {
      OverviewGuide,
      FeatureGuide,
      InstructionsGuide,
      FAQGuide
    },      
    data() {
      return {
        tabPosition: 'top'
      }
    }
  }
</script>
<style scoped>
</style>