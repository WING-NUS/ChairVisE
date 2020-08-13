<template>
  <el-collapse v-model="activeNames">
    <el-collapse-item title="Importing CSV Data" name="1">      
      <ol>
        <li>Log in.</li>
        <li>Click on "Import Data".</li>
        <li>Fill in the forms. </li>
        <li>Upload your CSV data. </li>
        <li>Map the CSV column headers</li>
        <li>Upload!</li>
      </ol>      
    </el-collapse-item>
    <el-collapse-item title="Make Presentation" name="2">      
      <ol>
        <li>Click on "Analyze".</li>
        <li>Create a new presentation.</li>
        <li>Select the visualizations you want.</li>
        <li>Click "Add New Section"</li>
        <li>In Edit Mode, users can:</li>
        <ul>
          <li>Change the name and description.</li>
          <li>Edit the mode of visualization.</li>
          <li>Add data filters for the field.</li>
          <li>Preview the changes.</li>
        </ul>
        <li>There is no limit to the number of visualizations!</li>
      </ol>
    </el-collapse-item>
    <el-collapse-item title="Sharing Presentation" name="3">      
      <ol>
        <li>Click on "SHARE"</li>
        <li>Enter the email address of the person you want to share it with.</li>
        <li>Change the permission (view or edit) given to the user. </li>        
      </ol>
    </el-collapse-item>
    <el-collapse-item title="Download presentations as PDF" name="4"> 
      <p> Pre-requisites: You must have create a presentation. </p>
      <ol>
        <li>Click on "Analyze".</li>
        <li>Select the presentation that you want to view.</li>
        <li>Click "PDF".</li>
        <li>And you get the presentation generated in PDF.</li>
      </ol>
    </el-collapse-item>
    <el-collapse-item title="Download presentations as Powerpoint" name="5">
      <p> Pre-requisites: You must have create a presentation. </p>
      <ol>
        <li>Click on "Analyze".</li>
        <li>Select the presentation that you want to view.</li>
        <li>Click "Powerpoint".</li>
        <li>And you get the presentation generated in Powerpoint slides.</li>
      </ol>
    </el-collapse-item>
    <el-collapse-item title="Manage conference" name="6">
      <ol>
        <li>Click on "Track".</li>
        <li>Click on "Add new conferences".</li>
        <li>Create a new conference.</li>
        <li>Select the conference you want from the calendar.</li>
        <li>You get the conference details.</li>
      </ol>
    </el-collapse-item>
  </el-collapse>  
</template>

<script>
  export default {
    name: "InstructionsGuide",
    data() {
      return {
        activeNames: []
      };
    },
  }
</script>

<style scoped>
</style>
