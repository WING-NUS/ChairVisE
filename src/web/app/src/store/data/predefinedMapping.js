export default [
  {
    id: 0,
    name: "No mapping",
    mapping: {
      dbTagIndices: [],
      importedTagIndices: []
    }
  },
  {
    id: 1,
    name: "Default Review Mapping",
    mapping: {
      dbTagIndices: [0, 1, 2, 3, 4, 6, 5, 7, 9, 10, 11, 12],
      importedTagIndices: [1, 0, 2, 3, 4, 5, 6, 7, 14, 12, 13, 15]
    }
  },
  {
    id: 2,
    name: "Default Author Mapping",
    mapping: {
      dbTagIndices: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9],
      importedTagIndices: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
    }
  },
  {
    id: 3,
    name: "Default Submission Mapping",
    mapping: {
      dbTagIndices: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12],
      importedTagIndices: [0, 1, 2, 3, 4, 5, 6, 8, 9, 10, 11, 12, 13]
    }
  }
]
