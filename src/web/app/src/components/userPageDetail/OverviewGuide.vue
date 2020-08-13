<template>
  <div>
    <h2>Welcome to Conference Data Visualization Management System</h2>
    <h3>
      This project is designed to enable conference program chairpersons to visualize and share conference submission
      statistics.
      By parsing information in different formats, we aim to assist user to obtain the most value out of the
      information uploaded.
      We also support sharing and exporting of such visualization.
    </h3>    
    <p> We have provided 4 different panels for our user guide: </p>
    <ol>
      <li> Overview</li>
      <li> Features - To describe the features that our system can provide </li>
      <li> Instructions - To provide a detailed instructions on how to use ChairVise features </li>
      <li> FAQ - To list out commonly asked questions by user </li>
    </ol>
    <!-- <img alt="Vue logo" src="@/assets/ProcessFlowPretty.jpg" id="general-guide"> -->
  </div>
</template>

<script>
  export default {
    name: 'OverviewGuide',
    props: {
      msg: String
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>  
  h2 {
    text-align: center;
  }

  h3 {
    font-weight: normal;
    text-align: justify;  
  }
  
  img {
    width: 100%;
  }
</style>
