<template>
  <el-row :gutter="20" class="map-container">
    <!-- left part of the page -->
    <el-col :span="10" :offset="1" class="map-area">
      <!-- db fields -->
      <div class="db-tags">
        <h3>Database fields</h3>
        <transition-group name="tags-group" tag="div">
          <div class="tag" v-for="(item, idx) in dbList.fieldMetaDataList"
               v-bind:key="idx"
               v-bind:class="[ idx === selectedDBTag ? 'active' : '', mappedDBTag.includes(idx) ? 'hidden' : '' ]"
               v-on:click="dbTagClicked(idx)">
            {{ item.name }}
          </div>
        </transition-group>
      </div>
      <!-- end of db fields -->

      <!-- imported tags -->
      <div class="import-tags">
        <h3>Imported data fields</h3>
        <transition-group name="tags-group" tag="div">
          <div class="tag" v-for="(item, idx) in importList"
               v-bind:key="idx"
               v-bind:class="[ idx === selectedImportTag ? 'active' : '', mappedImportTag.includes(idx) ? 'hidden' : '' ]"
               v-on:click="importTagClicked(idx)">
            {{ item }}
          </div>
        </transition-group>
      </div>
      <!-- end of imported tags -->

      <!-- button group -->
      <el-row class="button-row">
        <el-col>
          <el-button class="back-button" type="info" v-on:click="backClicked">Back</el-button>
          <el-button class="back-button" type="success" v-on:click="uploadClicked">Upload</el-button>
        </el-col>
      </el-row>
      <!-- end of button group -->
    </el-col>
    <!-- end of left part of the page -->

    <!-- right part of the page -->
    <el-col :span="12" class="map-result">
      <h3>Mapping</h3>
      <transition-group name="map-group" tag="div">
        <div class="pair-tag" v-for="(item, index) in mappedPairs" v-bind:key="index">
          <el-tag size="medium">{{ dbList.fieldMetaDataList[item[0]].type }}</el-tag>
          <p class="pair-info">
            {{ dbList.fieldMetaDataList[item[0]].name }}
            <i class="el-icon-caret-right"></i>
            {{ importList[item[1]] }}
          </p><i class="el-icon-close" v-on:click="removeMapClicked(index)"></i><br>
        </div>
      </transition-group>
      <transition name="fade" mode="out-in">
        <div class="no-map-info" v-show="mappedPairs.length === 0">
          <p>No mapping specified!</p>
        </div>
      </transition>
    </el-col>
    <!-- end of right part of the page -->

    <!-- dialogs -->
    <el-dialog
      title="Confirm"
      :visible.sync="hasSubmitted"
      width="30%" center>
      <span>After submission, your will not be able to modify your mapping. Are you sure that the columns are correctly mapped?</span>
      <span slot="footer" class="dialog-footer">
        <el-button v-on:click="hasSubmitted = false">Cancel</el-button>
        <el-button type="primary" v-on:click="submitMapping">Confirm</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="Success"
      :visible.sync="uploadSuccess"
      width="30%" center>
      <span>You have successfully imported data using the column mapping!</span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" v-on:click="closeSuccess">Sure</el-button>
      </span>
    </el-dialog>
    <!-- end of dialogs -->
  </el-row>
</template>

<script>
  import {deepCopy, filterPredefinedMap} from "@/common/utility"

  export default {
    name: "MappingTool",
    data() {
      return {
        // currently selected database tag and imported tag
        selectedDBTag: -1,
        selectedImportTag: -1,

        // ordered list of tags that have been
        // mapped with their data type details
        mappedDBTag: filterPredefinedMap(deepCopy(this.$store.state.dataMapping.data.predefinedMapping.dbTagIndices),
          this.$store.state.dataMapping.data.dbSchema.fieldMetaDataList),
        mappedImportTag: filterPredefinedMap(deepCopy(this.$store.state.dataMapping.data.predefinedMapping.importedTagIndices),
          this.$store.state.dataMapping.data.uploadedLabel),

        hasSubmitted: false,
        tableType: ""
      };
    },
    computed: {
      dbList: function () {
        return this.$store.state.dataMapping.data.dbSchema
      },
      // a list of size k * 2, k is the number of mapped pairs
      // the mapped pairs are indexes.
      mappedPairs: function () {
        let temp = this.mappedImportTag;
        return this.mappedDBTag.map(function (e, i) {
          return [e, temp[i]];
        });
      },

      // generates imported tags.
      // if initially no tag, just display column number
      importList: function () {
        if (this.$store.state.dataMapping.data.hasHeader) {
          return this.$store.state.dataMapping.data.uploadedLabel;
        }
        let lst = [];
        for (let i = 0; i < this.$store.state.dataMapping.data.uploadedLabel.length; i++) {
          lst.push("Column " + (i + 1));
        }
        return lst;
      },

      // gets errors
      errors: function () {
        return this.$store.state.dataMapping.error;
      },

      // whether upload is successful
      uploadSuccess: function () {
        return this.$store.state.dataMapping.isUploadSuccess;
      }
    },

    // display errors
    watch: {
      errors(newValue) {
        if (newValue.length > 0) {
          this.$notify.error({
            title: 'Error',
            message: newValue.join("\n")
          });
        }
      }
    },
    methods: {
      dbTagClicked: function (idx) {
        if (idx === this.selectedDBTag) {
          this.selectedDBTag = -1;
          return;
        }
        this.selectedDBTag = idx;
        if (this.selectedImportTag !== -1 && this.selectedDBTag !== -1) {
          this.mappedDBTag.push(this.selectedDBTag);
          this.mappedImportTag.push(this.selectedImportTag);
          this.selectedDBTag = -1;
          this.selectedImportTag = -1;
        }
      },
      importTagClicked: function (idx) {
        if (idx === this.selectedImportTag) {
          this.selectedImportTag = -1;
          return;
        }
        this.selectedImportTag = idx;
        if (this.selectedImportTag !== -1 && this.selectedDBTag !== -1) {
          this.mappedDBTag.push(this.selectedDBTag);
          this.mappedImportTag.push(this.selectedImportTag);
          this.selectedDBTag = -1;
          this.selectedImportTag = -1;
        }
      },
      removeMapClicked: function (idx) {
        this.mappedDBTag.splice(idx, 1);
        this.mappedImportTag.splice(idx, 1);
      },
      backClicked: function () {
        this.$store.commit("clearDBSchema");
        this.$store.commit("clearUploadedFile");
        this.$store.commit("clearFormatType");
        this.$store.commit("clearTableType");
        this.$store.commit("clearHasHeader");
        this.$store.commit("clearMapping");
        this.$store.commit("clearPredefinedMapping");
      },
      uploadClicked: function () {
        let map = deepCopy(this.mappedPairs);
        this.$store.commit("setMapping", {"map": map});
        if (this.errors.length === 0) {
          this.hasSubmitted = true;
        }
      },
      submitMapping: function () {
        this.hasSubmitted = false;
        this.$store.dispatch("persistMapping");
      },
      closeSuccess: function () {
        this.$store.commit("setUploadSuccess", false);
        this.$store.commit("clearDBSchema");
        this.$store.commit("clearUploadedFile");
        this.$store.commit("clearFormatType");
        this.$store.commit("clearTableType");
        this.$store.commit("clearHasHeader");
        this.$store.commit("clearMapping");
        this.$store.commit("clearError");
        this.$store.commit("clearPredefinedMapping");
      }
    },
    mounted() {
    },
    updated() {
    }
  };
</script>

<style scoped>
  @keyframes pulse {
    from {
      -webkit-transform: scale3d(1, 1, 1);
      transform: scale3d(1, 1, 1);
    }

    50% {
      -webkit-transform: scale3d(1.1, 1.1, 1.1);
      transform: scale3d(1.1, 1.1, 1.1);
    }

    to {
      -webkit-transform: scale3d(1, 1, 1);
      transform: scale3d(1, 1, 1);
    }
  }

  .map-container h3 {
    letter-spacing: 1px;
  }

  .tags-group-move {
    transition: all 300ms ease-in-out 50ms;
  }

  .map-group-move {
    transition: all 600ms ease-in-out 50ms;
  }

  /* appearing */
  .map-group-enter-active {
    transition: all 300ms ease-out;
  }

  /* disappearing */
  .map-group-leave-active {
    transition: all 200ms ease-in;
  }

  /* appear at / disappear to */
  .map-group-enter {
    opacity: 0;
    transform: translateY(30px);
  }

  .map-group-leave-to {
    opacity: 0;
  }

  .fade-enter-active,
  .fade-leave-active {
    transition: opacity 0.3s ease;
  }

  .fade-enter, .fade-leave-to
    /* .component-fade-leave-active below version 2.1.8 */
  {
    opacity: 0;
  }

  .map-container {
    display: flex;
    flex-direction: row;
  }

  .db-tags {
    min-height: 90px;
  }

  .import-tags {
    min-height: 90px;
  }

  .tag {
    display: inline-block;
    height: 20px;
    margin: 5px 5px;
    padding: 7px 14px;
    background-color: #ffffff;
    border: 1px solid #007bff;
    color: #007bff;
    font-size: 14px;
    cursor: pointer;
    opacity: 1;
    z-index: 1;
    transition: opacity 0.2s, transform 0.3s, background-color 0.2s;
    border-radius: 5px;
    max-width: 400px;
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
    /* box-shadow: 0 2px 4px -1px rgba(0,0,0,.2), 0 4px 5px 0 rgba(0,0,0,.14), 0 1px 10px 0 rgba(0,0,0,.12); */
  }

  .tag.active {
    animation: pulse 1s infinite;
    background-color: #007bff;
    color: #ffffff;
    transition: 0.3s;
  }

  .tag.hidden {
    position: absolute;
    opacity: 0;
    z-index: -1;
    transition: 0.2s;
  }

  .tag:hover {
    background-color: #007bff;
    color: #ffffff;
    transition: 0.2s;
  }

  .map-result {
    display: flex;
    flex-direction: column;
    /* border: 1px dashed #565656; */
    border-radius: 5px;
    min-height: 300px;
    /* padding: 30px 10px; */
    transition: all 0.3s ease;
  }

  .pair-tag {
    margin: 5px 5px;
    padding: 15px 14px;
    letter-spacing: 1px;
    border-bottom: 1px solid #eee;
    color: #565656;
  }

  .pair-tag .pair-info {
    margin-left: 10px;
    transition: 1s ease;
    font-size: 14px;
    display: inline;
  }

  .pair-tag .el-icon-close {
    margin-top: 4px;
    cursor: pointer;
    transition: 0.3s;
    float: right;
  }

  .pair-tag .el-icon-caret-right {
    position: relative;
    top: 2px;
  }

  .pair-tag .el-icon-close:hover {
    color: crimson;
    transition: 0.3s;
  }

  .no-map-info {
    color: #777;
    font-weight: 300;
    position: absolute;
    top: 65px;
    margin-left: 10px;
  }

  .el-tag {
    margin-left: 0;
    padding: 0;
    width: 70px;
    text-align: center;
    font-size: 9px;
  }

  .el-input {
    margin-left: 78px;
    margin-top: 8px;
    width: 185px;
  }

  .button-row {
    margin-top: 40px;
  }
</style>