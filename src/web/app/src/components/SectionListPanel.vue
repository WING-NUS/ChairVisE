<template>
  <div>
    <div v-loading="isLoadingDBMetaData || isLoadingSectionList" v-if="!isNewPresentation">
      <el-aside width="300px" class="addRowRightAlign" v-if="isLogin">
        <el-card v-if="!isSectionListEmpty" >
          <div slot="header" class="clearfix">
            <span> Select version </span>
          </div>
          <el-select class= "versionInput" v-model="presentationFormVersion" placeholder="Please select a version" >
            <el-option v-for="v in versions" :key="v" :label="v" :value="v">
            </el-option>
          </el-select>        
        </el-card>
        <el-card>  
          <div slot="header" class="clearfix">
            <span> Add section </span>
          </div>
          <el-select class= "selectionInput" v-model="selectedNewSection" placeholder="Please select a section to add"
                    filterable>
            <el-option-group
              v-for="group in predefinedSections"
              :key="group.label"
              :label="group.label">
              <el-option
                v-for="item in group.options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-option-group>
          </el-select>
          <el-button class="selectionInputButton" icon="el-icon-plus" type="success" @click="addNewSection">Add New Section</el-button>
        </el-card>
      </el-aside>
      <br/>
      <el-alert
        v-if="isSectionListApiError"
        :title="sectionListApiErrorMsg"
        type="error" show-icon>
      </el-alert>
      <el-card shadow="hover">
        <abstract-section-detail class="presentation-section" v-for="section in sectionList" :sectionDetail="section"
                            :key="section.id" :presentationId="presentationId" :version="presentationFormVersion"/>
        <EmptySection v-if="isSectionListEmpty" />
      </el-card>
    </div>
  </div>
</template>

<script>
  import AbstractSectionDetail from "@/components/AbstractSectionDetail.vue"
  import {ID_NEW_PRESENTATION} from "@/common/const";
  import PredefinedQueries from "@/store/data/predefinedQueries"
  import EmptySection from "@/components/emptyStates/EmptySection.vue"

  export default {
    props: {
      presentationId: String,
    },
    watch: {
      presentationId: 'fetchSectionList',
      'presentationFormVersion'() {
        this.updateVersion();
      }
    },
    data() {
      return {
        selectedNewSection: '',
        presentationFormVersion: ''
      }
    },
    computed: {
      isLogin() {
        return this.$store.state.userInfo.isLogin
      },

      isPresentationEditable() {
        return this.$store.state.presentation.isPresentationEditable;
      },

      predefinedSections() {
        let sectionOptionsGroup = {};
        // grouping the predefined queries
        for (let key in PredefinedQueries) {
          if (!PredefinedQueries.hasOwnProperty(key)) {
            continue;
          }
          let groupName = PredefinedQueries[key].group;
          if (sectionOptionsGroup[groupName] === undefined) {
            sectionOptionsGroup[groupName] = [];
          }
          sectionOptionsGroup[groupName].push({
            value: key,
            label: PredefinedQueries[key].name,
          })
        }

        // generate to format that element ui requires
        let sectionOptions = [];
        for (let groupName in sectionOptionsGroup) {
          if (!sectionOptionsGroup.hasOwnProperty(groupName)) {
            continue;
          }
          sectionOptions.push({
            label: groupName,
            options: sectionOptionsGroup[groupName]
          })
        }
        return sectionOptions;
      },

      isNewPresentation() {
        return this.presentationId === ID_NEW_PRESENTATION
      },

      sectionList() {
        return this.$store.state.section.sectionList
      },
      isSectionListEmpty() {
        return this.$store.state.section.sectionList.length <= 0
      },
      isLoadingSectionList() {
        return this.$store.state.section.sectionListStatus.isLoading
      },
      isSectionListApiError() {
        return this.$store.state.section.sectionListStatus.isApiError
      },
      sectionListApiErrorMsg() {
        return this.$store.state.section.sectionListStatus.apiErrorMsg
      },
      isLoadingDBMetaData() {
        return this.$store.state.dbMetaData.entitiesStatus.isLoading
      },
      versions() {
        let list = Array.from(new Set(this.$store.state.presentation.versionList.map(v => v.versionId)));
        this.setDefaultValueForVersionList(list[0]);
        return list;
      },
    },
    components: {
      AbstractSectionDetail,
      EmptySection
    },
    mounted() {
      this.fetchSectionList();
      this.$store.dispatch('fetchDBMetaDataEntities');
      this.$store.dispatch('getVersionList');
    },
    methods: {
      updateVersion() {
        var value = this.presentationFormVersion;
        if (value === undefined) {
            value = this.versions[0];
        }
        this.$store.commit('setPresentationFormField', {
            field: 'version',
            value
        });
      },

      setDefaultValueForVersionList(value) {
        this.presentationFormVersion = value;
      },

      fetchSectionList() {
        if (this.isNewPresentation) {
          this.$store.commit('clearSectionList');
        } else {
          this.$store.dispatch('fetchSectionList', this.presentationId)
        }
      },

      addNewSection() {
        if (this.selectedNewSection.length === 0) {

          this.$notify.error({
            title: 'Error',
            message: 'Please select a section to add into presentation.'
          });
          return;
        }
        this.$store.dispatch('addSectionDetail', {
          presentationId: this.presentationId,
          selectedNewSection: this.selectedNewSection,
          dataSet: this.$store.state.userInfo.userEmail,
        }).then(() => {
          this.selectedNewSection = ''
        })
      }
    }
  }
</script>

<style scoped>
  .textBold {
    font-weight: bold;
  }
  .versionInput {
    display: inline-block;
    width: 100%;
  }
  .selectionInput {
    display: inline-block;
    width: 100%;
    margin-bottom: 16px;
  }
  .selectionInputButton {
    display: inline-block;
    width: 100%;
  }
  .addRowRightAlign {
    float: right;
    margin-top: 18px;
    margin-left: 16px;
  }
  .addRowRightAlign .el-card{
    margin-bottom: 16px;
  }
</style>