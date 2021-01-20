export const deepCopy = (obj) => {
  return JSON.parse(JSON.stringify(obj))
}

//    deepcopy(predefinedMapping.dbTagIndices), dbSchema.fieldMetaDataList
// or deepcopy(predefinedMapping.importedTagIndices), data.uploadedLabel
export const filterPredefinedMap = (mappedIdArray, originalArray) => {
  const result = []
  for (let i = 0; i < mappedIdArray.length; i++) {
    if (mappedIdArray[i] < originalArray.length) {
      result.push(mappedIdArray[i])
    }
  }
  return result
}

export function objValueSort (obj) {
  const newkey = Object.keys(obj).sort(function (key1, key2) {
    return obj[key2] - obj[key1]
  })
  const newObj = {}
  for (let i = 0; i < newkey.length; i++) {
    newObj[newkey[i]] = obj[newkey[i]]
  }
  return newObj
}

export function arrayAvg (arr) {
  return arr.reduce((a, b) => {
    return a + b
  }) / arr.length
}
