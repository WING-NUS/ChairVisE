<template>
  <div>
    <div>
      <el-container>
        <el-aside width="250px" v-if="isLogin">
          <list-of-presentation/>
        </el-aside>
        <el-main>
          <presentation-brief :id="id"/>
          <section-list-panel :presentationId="id"/>
        </el-main>
      </el-container>
    </div>
  </div>
</template>

<script>
  import ChartJsPluginDataLabels from 'chartjs-plugin-datalabels';
  import ListOfPresentation from '@/components/ListOfPresentation.vue'
  import PresentationBrief from "@/components/PresentationBrief.vue";
  import SectionListPanel from "@/components/SectionListPanel.vue";

  export default {
    name: 'Analyze',
    props: {
      id: String,
    },
    computed: {
      isLogin() {
        return this.$store.state.userInfo.isLogin
      },
      isAppLoading() {
        return this.$store.state.isPageLoading
      }
    },
    components: {
      PresentationBrief,
      ListOfPresentation,
      SectionListPanel,
      ChartJsPluginDataLabels
    }
  }
</script>