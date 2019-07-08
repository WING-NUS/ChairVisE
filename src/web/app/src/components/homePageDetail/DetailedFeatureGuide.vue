<template>
  <div>
    <h2>More Detailed User Guide</h2>
    <ul>
      <li id="import-guide"><h3>User Guide for Importing Data:</h3></li>
      <li class="without-bullets">
        <ul>
          <li>Step 1. Upon logging in, click on the "Import Data" tab.</li>
          <li>Step 2. Upload or drag the csv file from your computer.</li>
          <li>Step 3. Choose the table types of the csv file uploaded (i.e. Author Record, Review Record, Submission
            Record).
          </li>
          <li>Step 4. Select if there is a header row in the uploaded csv file.<br/>
          <li>Step 5. For your selected table type, map the database fields to the imported data fields one by one.</li>
          <li>Step 6. Finally click upload to update the database accordingly.</li>
          <li class="without-bullets">&nbsp;</li>
          <li><b>Note: </b> You can select any number of columns to map into the database. Please map all the data
            fields you need.
          </li>
        </ul>
      </li>
      <li id="add-guide"><h3>User Guide for Adding Presentation:</h3></li>
      <li class="without-bullets">
        <ul>
          <li>Step 1. Upon logging in, click on the "Analyze" tab.</li>
          <li>Step 2. Create a new presentation name (minimum 3 characters) with description (optional).</li>
          <li>Step 3. Select a list of pre-defined visualizations for submission, author or review.</li>
          <li>Step 4. Click "Add New Section" to view the selected visualization section.</li>
          <li>Step 5. For each pre-defined visualization, you can edit, delete, filter and preview.</li>
          <li>Step 6. In Edit Mode, users can:</li>
          <li class="without-bullets">
            <ul>
              <li>a. Change the name and add description of the visualization.</li>
              <li>b. Edit the mode of visualization.</li>
              <li>c. Add filter for the field and value to hide from the visualization.</li>
              <li>d. Preview the changes before making the final change through the save button.</li>
            </ul>
          </li>
          <li>Step 7. Delete - Remove the visualization section from the presentation.</li>
          <li>Step 8. There is no limit to the number of visualizations to added by the users.</li>
          <li class="without-bullets">&nbsp;</li>
          <li><b>Note: </b>The default mode for users is basic mode. Advanced mode is for users to make SQL-like queries
            straight from the database. We will update a separate documentation on this in the later stage.
          </li>
        </ul>
      </li>
      <li id="share-guide"><h3>User Guide for Sharing Presentation:</h3></li>
      <li class="without-bullets">
        <ul>
          <li>Step 1. Click on share button.</li>
          <li>Step 2. Enter the email address of the user to share to.</li>
          <li>Step 3. Change the permission (view or edit) given to the user.</li>
          <li class="without-bullets">&nbsp;</li>
          <li><b>Note: </b>There is also an option to give Public Access Control (view or edit). And you can view who
            has access to the presentation as well.
          </li>
        </ul>
      </li>
    </ul>
  </div>
</template>

<script>
  export default {
    name: "DetailedFeatureGuide",
  }
</script>

<style scoped>
  p {
    text-align: justify;
  }

  .without-bullets {
    list-style-type: none;
  }
</style>
