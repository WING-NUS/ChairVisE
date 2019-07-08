<template>
  <el-row class="sectionDetail">
    <div v-if="sectionDetail.type === WORD_CLOUD">
      <word-cloud-section-detail :sectionDetail="sectionDetail" :presentationId="presentationId"/>
    </div>
    <div v-else-if="sectionDetail.type === BAR_CHART">
      <bar-chart-section-detail :sectionDetail="sectionDetail" :presentationId="presentationId"/>
    </div>
    <div v-else-if="sectionDetail.type === PIE_CHART">
      <pie-chart-section-detail :sectionDetail="sectionDetail" :presentationId="presentationId"/>
    </div>
    <div v-else-if="sectionDetail.type === LINE_CHART">
      <line-chart-section-detail :sectionDetail="sectionDetail" :presentationId="presentationId"/>
    </div>
    <div v-else-if="sectionDetail.type === STATS">
      <stats-section-detail :sectionDetail="sectionDetail" :presentationId="presentationId"/>
    </div>
    <div v-else>
      <el-alert
        :title="`Unexpected Section Detail Type: ${sectionDetail.type}`"
        type="error">
      </el-alert>
    </div>
  </el-row>
</template>

<script>
  import WordCloudSectionDetail from "@/components/sectionDetail/WordCloudSectionDetail.vue"
  import BarChartSectionDetail from "@/components/sectionDetail/BarChartSectionDetail.vue"
  import PieChartSectionDetail from "@/components/sectionDetail/PieChartSectionDetail.vue"
  import LineChartSectionDetail from "@/components/sectionDetail/LineChartSectionDetail.vue"
  import StatsSectionDetail from "@/components/sectionDetail/StatsSectionDetail.vue"
  import {
    SECTION_TYPE_BAR_CHART,
    SECTION_TYPE_LINE_CHART,
    SECTION_TYPE_PIE_CHART,
    SECTION_TYPE_STATS,
    SECTION_TYPE_WORD_CLOUD
  } from "@/common/const";

  export default {
    props: {
      sectionDetail: {
        type: Object,
        required: true
      },
      presentationId: {
        type: String,
        required: true
      }
    },
    data() {
      return {
        // constant
        WORD_CLOUD: SECTION_TYPE_WORD_CLOUD,
        BAR_CHART: SECTION_TYPE_BAR_CHART,
        PIE_CHART: SECTION_TYPE_PIE_CHART,
        LINE_CHART: SECTION_TYPE_LINE_CHART,
        STATS: SECTION_TYPE_STATS
      }
    },
    components: {
      WordCloudSectionDetail,
      BarChartSectionDetail,
      PieChartSectionDetail,
      LineChartSectionDetail,
      StatsSectionDetail
    }
  }
</script>

<style scoped>
  .sectionDetail {
    margin-bottom: 30px;
  }
</style>

