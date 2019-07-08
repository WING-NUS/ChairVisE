import axios from 'axios'
import {processMapping} from '@/store/helpers/processor.js'

export default {
  state: {
    hasDBSchemaSet: false,
    hasFileUploaded: false,
    hasFormatTypeSpecified: false,
    hasTableTypeSelected: false,
    hasHeaderSpecified: false,
    hasPredefinedSpecified: false,
    hasMappingFinished: false,
    isUploadSuccess: false,
    data: {
      dbSchema: null,
      uploadedData: [],
      uploadedLabel: [],
      mappingResult: [],
      processedResult: [],
      formatType: null,
      tableType: null,
      hasHeader: null,
      predefinedMapping: null,
      predefinedMappingId: null,
    },
    error: []
  },

  mutations: {
    setUploadSuccess(state, success) {
      state.isUploadSuccess = success;
    },

    setUploadedFile(state, data) {
      state.data.uploadedLabel = data[0];
      state.data.uploadedData = data;
      state.hasFileUploaded = true;
    },

    clearUploadedFile(state) {
      state.data.uploadedLabel = [];
      state.data.uploadedData = [];
      state.hasFileUploaded = false;
    },

    setDBSchema(state, dbSchema) {
      state.data.dbSchema = dbSchema;
      state.hasDBSchemaSet = true;
    },

    clearDBSchema(state) {
      state.data.dbSchema = [];
      state.hasDBSchemaSet = false;
    },

    setFormatType(state, formatType) {
      state.data.formatType = formatType;
      state.hasFormatTypeSpecified = true;
    },

    clearFormatType(state) {
      state.data.formatType = null;
      state.hasFormatTypeSpecified = false;
    },

    setTableType(state, selected) {
      state.data.tableType = selected;
      state.hasTableTypeSelected = true;
    },

    clearTableType(state) {
      state.data.tableType = null;
      state.hasTableTypeSelected = false;
    },

    setHasHeader(state, hasHeader) {
      state.data.hasHeader = hasHeader;
      state.hasHeaderSpecified = true;
    },

    clearHasHeader(state) {
      state.data.hasHeader = null;
      state.hasHeaderSpecified = false;
    },

    setPredefinedMapping(state, payload) {
      state.data.predefinedMapping = payload.mapping;
      state.data.predefinedMappingId = payload.id;
      state.hasPredefinedSpecified = true;
    },

    clearPredefinedMapping(state) {
      state.data.predefinedMapping = null;
      state.data.predefinedMappingId = null;
      state.hasPredefinedSpecified = false;
    },

    setMapping(state, payload) {
      try {
        state.error = [];
        state.data.mappingResult = payload.map;
        state.mappingFinished = true;
        state.data.processedResult =
          processMapping(payload.map,
            state.data.uploadedData,
            state.data.dbSchema,
            state.data.hasHeader);
      } catch (err) {
        state.error.push(err);
        state.mappingFinished = false;
        state.data.mappingResult = [];
        state.data.processedResult = [];
      }
    },

    clearMapping(state) {
      state.data.mappingResult = [];
      state.data.processedResult = [];
      state.mappingFinished = false;
    },

    setDataMappingError(state, err) {
      state.error.push(err);
    },

    clearError(state) {
      state.error = [];
    }
  },

  actions: {
    async persistMapping({commit, state}) {
      commit("setPageLoadingStatus", true);
      let endpoint;
      switch (state.data.tableType) {
        case 0:
          endpoint = "author";
          break;
        case 1:
          endpoint = "review";
          break;
        case 2:
          endpoint = "submission";
          break;
      }
      await axios.post("/api/record/" + endpoint, state.data.processedResult)
        .then(() => {
          commit("setPageLoadingStatus", false);
          commit("setUploadSuccess", true);
        })
        .catch(e => {
          commit("setPageLoadingStatus", false);
          commit("setUploadSuccess", false);
          commit("setDataMappingError", e.toString());
        });
    }
  }
}