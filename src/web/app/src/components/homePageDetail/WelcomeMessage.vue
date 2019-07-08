<template>
  <div>
    <!--can change to a better logo next time-->
    <img alt="Vue logo" src="@/assets/logo.png">
    <h2>Welcome to Conference Data Visualization Management System</h2>
    <h3>
      This project is designed to enable conference program chairpersons to visualize and share conference submission
      statistics.
      By parsing information in different formats, we aim to assist user to obtain the most valued out of the
      information uploaded.
      We also support sharing and exporting of such visualization.
    </h3>
  </div>
</template>

<script>
  export default {
    name: 'WelcomeMessage',
    props: {
      msg: String
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h3 {
    font-weight: normal;
    text-align: justify;
  }

  img {
    width: 30%;
  }

  div {
    text-align: center;
  }
</style>
