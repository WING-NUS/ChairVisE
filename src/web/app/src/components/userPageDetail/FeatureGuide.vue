<template>
  <el-collapse v-model="activeNames">
    <el-collapse-item title="Word cloud for all submissions keywords" name="1">        
      <img class="img-fluid" src="@/assets/userGuide/wordCloudSubmissions.png"/> 
    </el-collapse-item>
    <el-collapse-item title="Submission rank author/country/organization" name="2">
      <img class="img-fluid" src="@/assets/userGuide/SubmissionRankViz.png" />
    </el-collapse-item>
    <el-collapse-item title="Accepted submission organization rank" name="3">            
      <img class="img-fluid" src="@/assets/userGuide/AcceptedSubmissionOrgRank.png" />
    </el-collapse-item>
    <el-collapse-item title="Review Score Distribution/weighted evaluation score statistic summary" name="4">
      <img class="img-fluid" src="@/assets/userGuide/ReviewScoreDistributionViz.png" />
    </el-collapse-item>
    <el-collapse-item title="Reviewer Expertise Level Statistic Summary" name="5">
      <img class="img-fluid" src="@/assets/userGuide/ReviewerExpertiseLevelSummary.png" />
    </el-collapse-item>
    <el-collapse-item title="Number of Review Assignment based on Confidence Level using Scatter Chart" name="6">
      <img class="img-fluid" src="@/assets/userGuide/scatterChart.png" />
    </el-collapse-item>
    <el-collapse-item title="Co-authorship" name="7">
      <ul>
        <li> 
          <p> Graph Network </p>
          <img class="img-fluid" src="@/assets/userGuide/graphNetworkCoAuthorViz.png" />
        </li>
        <li>
          <p> Dependency Wheel </p>
          <img class="img-fluid" src="@/assets/userGuide/dependencyWheelCoAuthorViz.png" />
        </li>
        <li>
          <p> Radar Chart </p>
          <img class="img-fluid" src="@/assets/userGuide/radarChartCoAuthorViz.png" />
        </li>
      </ul>
    </el-collapse-item>    
    <el-collapse-item title="Enable data persistance" name="8">
      
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
  img {
    margin: auto;
  }
</style>
