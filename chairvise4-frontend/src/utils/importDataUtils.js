// eslint-disable-next-line no-unused-vars
const softConfAuthorHeader = ['Submission Number', 'Passcode', 'Date Received', 'Submission Type', 'Track(s)',
  'Acceptance Status', 'Contact Type', 'First Name', 'Last Name', 'Email', 'Affiliation', 'Address 1', 'Address 2',
  'Address 3', 'City', 'State/Province/Region', 'Postal Code/Zip', 'Country', 'Biography', 'Presenter']
// eslint-disable-next-line no-unused-vars
const softConfReviewHeader = []
// eslint-disable-next-line no-unused-vars
const softConfSubmissionHeader = []

// eslint-disable-next-line no-unused-vars
const authorHeader = ['authorName', 'country', 'organization', 'submissionId']

// eslint-disable-next-line no-unused-vars
const reviewHeader = ['reviewerName', 'submissionId', 'overallScore', 'comment', 'reviewDateTime']

// eslint-disable-next-line no-unused-vars
const submissionHeader = ['submissionId', 'title', 'abstract', 'type', 'acceptance', 'submissionDateTime']

function mappingAuthorSoftConf (unmappedData) {
  const mappedRes = []
  for (const r in unmappedData) {
    const row = unmappedData[r]
    mappedRes.push({
      authorName: row.firstname + ' ' + row.lastname,
      country: row.country,
      organization: row.affiliation,
      submissionId: row.submissionnumber
    })
  }
  return mappedRes
}

function mappingReviewSoftConf (unmappedData) {
  const mappedRes = []
  for (const r in unmappedData) {
    const row = unmappedData[r]
    mappedRes.push({
      revererName: row.reviewerfirstname + ' ' + row.reviewerlastname,
      submissionId: row.submissionid,
      overallScore: row.recommendation,
      comment: row.detailedcomments.replace('(OVERALL EVALUATION) ', ''),
      reviewDateTime: new Date(row.lastmodificationtime)
    })
  }
  return mappedRes
}

function mappingSubmissionSoftConf (unmappedData) {
  const mappedRes = []
  for (const r in unmappedData) {
    const row = unmappedData[r]
    mappedRes.push({
      submissionId: row.submissionid,
      title: row.title.replace('<NULL>', null),
      type: row.submissiontype,
      acceptance: row.acceptancestatus.toLowerCase() === 'accept',
      abstract: row.summary,
      keywords: row.keywords,
      submissionDateTime: new Date(row.submissiondate)
    })
  }
  return mappedRes
}

function mappingAuthorEasyChair (unmappedData) {
  const mappedRes = []
  for (const r in unmappedData) {
    const row = unmappedData[r]
    mappedRes.push({
      authorName: row.firstname + ' ' + row.lastname,
      country: row.country,
      organization: row.organization,
      submissionId: row.submissionid
    })
  }
  return mappedRes
}

function mappingReviewEasyChair (unmappedData) {
  const mappedRes = []
  for (const r in unmappedData) {
    const row = unmappedData[r]
    mappedRes.push({
      reviewerName: row.reviewername,
      submissionId: row.submissionid,
      overallScore: row.overallevaluationscore,
      comment: row.review_comments.replace('(OVERALL EVALUATION) ', ''),
      reviewDateTime: new Date(row.dateofreviewsubmission + ' ' + row.timeofreviewsubmission)
    })
  }
  return mappedRes
}

function mappingSubmissionEasyChair (unmappedData) {
  const mappedRes = []
  for (const r in unmappedData) {
    const row = unmappedData[r]
    mappedRes.push({
      submissionId: row.id,
      title: row.title.replace('<NULL>', null),
      type: row.trackname,
      acceptance: row.decision === 'accept',
      abstract: row.abstract,
      keywords: row.keywords,
      submissionDateTime: new Date(row.lastupdated)
    })
  }
  return mappedRes
}

export default {
  dataMappingHandler: function (data, fileType, tableType) {
    if (fileType === 0) {
      if (tableType === 0) {
        return mappingAuthorEasyChair(data)
      } else if (tableType === 1) {
        return mappingReviewEasyChair(data)
      } else {
        return mappingSubmissionEasyChair(data)
      }
    } else {
      if (tableType === 0) {
        return mappingAuthorSoftConf(data)
      } else if (tableType === 1) {
        return mappingReviewSoftConf(data)
      } else {
        return mappingSubmissionSoftConf(data)
      }
    }
  },
  saveIndexedDB: function (data, tableType, dbName) {
    const indexedDB = window.indexedDB || window.mozIndexedDB || window.webkitIndexedDB || window.msIndexedDB || window.shimIndexedDB
    const request = indexedDB.open(dbName)
    request.onerror = function (event) {
      console.log('error opening indexedDB')
    }
    request.onupgradeneeded = function (event) {
      const db = event.target.result
      if (!db.objectStoreNames.contains('author')) {
        db.createObjectStore('author', { autoIncrement: true })
      }
      if (!db.objectStoreNames.contains('submission')) {
        db.createObjectStore('submission', { autoIncrement: true })
      }
      if (!db.objectStoreNames.contains('review')) {
        db.createObjectStore('review', { autoIncrement: true })
      }
    }
    request.onsuccess = function (event) {
      const db = event.target.result
      // console.log(db.objectStoreNames)
      // console.log(endpoint)
      let tableName = ''
      if (tableType === 0) {
        tableName = 'author'
      } else if (tableType === 1) {
        tableName = 'review'
      } else {
        tableName = 'submission'
      }
      const transaction = db.transaction(tableName, 'readwrite')
      transaction.objectStore(tableName).clear()
      // eslint-disable-next-line no-unused-vars
      for (let i = 0; i < data.length; i++) {
        transaction.objectStore(tableName).put(data[i])
      }
      // const store = transaction.objectStore(endpoint).put(data.processedResult)
      // console.log(store)
    }
  }
}
