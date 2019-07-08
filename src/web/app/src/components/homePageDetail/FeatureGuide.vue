<template>
  <div>
    <h3 id="general-guide">A step by step general guide to use the system:</h3>
    <ol>
      <li>Log in using your Google account through the "Login" button.</li>
      <li>Upload any number of csv files under the "Import Data" tab.</li>
      <li>Create a new presentation and add visualizations using the data uploaded.</li>
      <li>Share the presentation to your fellow friends to view or edit.</li>
    </ol>
    <img alt="Guiding Diagram" src="@/assets/GeneralGuide.png">
    <h3 id="features-available">Currently, these are the pre-defined features that we support:</h3>
    <ul>
      <li>Word cloud for all submissions keywords</li>
      <li>Submission rank author</li>
      <li>Submission rank country</li>
      <li>Submission rank organization</li>
      <li>Accepted submission organization rank</li>
      <li>Review Score Distribution</li>
      <li>Review weighted evaluation score statistic summary</li>
      <li>Reviewer Expertise Level Statistic Summary</li>
      <li class="more_detail">More options can be found under the presentation pre-defined dropdown.</li>
    </ul>
  </div>

</template>

<script>
  export default {
    name: "FeatureGuide",
  }
</script>

<style scoped>
  img {
    width: 100%;
    height: auto;
  }

  h3 {
    margin: 40px 0 0 0;
  }

  .more_detail {
    font-style: italic;
  }
</style>
