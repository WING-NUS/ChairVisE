
export function idbGet (dbName, storeNames, callbacks) {
  const indexedDB = window.indexedDB || window.mozIndexedDB || window.webkitIndexedDB || window.msIndexedDB || window.shimIndexedDB
  const request = indexedDB.open(dbName)
  request.onerror = function (event) {
    console.log('error opening indexedDB')
  }
  request.onsuccess = function (event) {
    const db = event.target.result
    const result = {}
    const submissionTransaction = db.transaction(storeNames)
    for (const s in storeNames) {
      const storeName = storeNames[s]
      const submissionStore = submissionTransaction.objectStore(storeName)
      const getAllReq = submissionStore.getAll()
      getAllReq.onsuccess = function (event) {
        result[storeName] = event.target.result
      }
    }
    submissionTransaction.oncomplete = function (event) {
      if (storeNames.length === 1) {
        callbacks[0](result[storeNames[0]])
      } else {
        callbacks[0](result)
      }
    }
  }
}
