import moment from 'moment';

function processDouble(raw) {
  if (!isNaN(parseFloat(raw))) {
    return parseFloat(raw);
  }
  // if not even string, return default value 0
  if (typeof(raw) !== "string") {
    return 0;
  }

  // TODO: figure out a better way to parse confidence level
  // below is a hack
  let rawStringList = raw.toLocaleLowerCase().split("\n");
  for (let i = 0; i < rawStringList.length; i++) {
    let rawString = rawStringList[i];
    if (rawString.includes("confidence:")) {
      // hard code the processing
      let confidenceValueString = rawString.trim().split(":")[1];
      return parseFloat(confidenceValueString);
    }
  }
  return 0;
}

// This is a rather complex function
// this function includes some parsing logic
export function processMapping(mapping, data, dbFields, hasLabel) {
  // validate
  let checkDateResult = dateCheck(mapping, dbFields);
  if (hasLabel) {
    data = data.slice(1);
  }
  if (checkDateResult !== undefined) {
    throw checkDateResult;
  }
  let result = [];
  let map = {};
  for (let i = 0; i < mapping.length; i++) {
    map[mapping[i][0]] = mapping[mapping[i][1]]
  }
  let dateField;
  for (let idx in dbFields.fieldMetaDataList) {
    if (dbFields.fieldMetaDataList[idx].type === "Date") {
      dateField = dbFields.fieldMetaDataList[idx].jsonProperty
    }
  }
  // for each row of data
  for (let i = 0; i < data.length; i++) {
    let row = data[i];
    let dataObject = {};

    let usingDate = false;
    let isSeparateDate = false;
    let localDate = null, localTime = null;
    // for each mapped database fields
    for (let idx in mapping) {
      let rawData = row[mapping[idx][1]];
      let fieldType = dbFields.fieldMetaDataList[mapping[idx][0]].type;

      // if date is selected, directly parse date as usual
      if (fieldType === "Date") {
        // TODO let user specify the format of the date instead of hardcoding
        rawData = moment(rawData, "YYYY-M-D H:m").format("YYYY-MM-DD hh:mm:ss");
        if (rawData === "Invalid date") {
          throw "invalid date format";
        }
        usingDate = true;
        isSeparateDate = false;
      }

      // if we are not using date and date time is not complete,
      // then store local date
      if (!usingDate && fieldType === "LocalDate" && localTime == null) {
        localDate = rawData;
        continue;
      }

      // similarly, store local time
      if (!usingDate && fieldType === "LocalTime" && localDate === null) {
        localTime = rawData;
        continue;
      }

      // then if date is complete, combine then together
      if (!usingDate && fieldType === "LocalDate" && localTime !== null) {
        rawData = moment(rawData + " " + localTime, "YYYY-M-D H:m").format("YYYY-MM-DD hh:mm:ss");
        if (rawData === "Invalid date") {
          throw "invalid date format";
        }
        isSeparateDate = true;
      }

      if (!usingDate && fieldType === "LocalTime" && localDate !== null) {
        rawData = moment(localDate + " " + rawData, "YYYY-M-D H:m").format("YYYY-MM-DD hh:mm:ss");
        if (rawData === "Invalid date") {
          throw "invalid date format";
        }
        isSeparateDate = true;
      }

      // parse integer
      if (fieldType === "int") {
        rawData = parseInt(rawData);
      }

      // parse double
      if (fieldType === "double") {
        rawData = processDouble(rawData);
      }

      // parse authors
      if (fieldType === "List") {
        let dataList = rawData.split("and");
        if (dataList.length === 1) {
          rawData = dataList;
        } else {
          let lastAuthor = dataList[1];
          let allAuthors = dataList[0].split(",");
          allAuthors.push(lastAuthor);
          rawData = allAuthors;
        }
        rawData = rawData.map(author => author.trim());
        //console.log(rawData);



        var convertstring=require("convert-string");
        for (var key in rawData){
            var author=rawData[key];
            let name=author.split(" ");
            var concatname="";
            for (var itemkey in name){
                var conv=convertstring.stringToBytes(name[itemkey]);
                var itemconv="";
                for(var a=0;a<conv.length;a++){
                    itemconv=itemconv.concat(String(conv[a]+18));
                }
                name[itemkey]=itemconv;
                concatname=concatname.concat(itemconv);
                concatname=concatname.concat(" ");
            }
            //concatname.trim();
            //console.log(concatname);
            rawData[key]=concatname;
         }
         rawData = rawData.map(author => author.trim());

         //console.log(rawData);

      }

      // if is separate date format, assign using date field
      // else, assign directly using date field
      if (isSeparateDate) {
        dataObject[dateField] = rawData;
        isSeparateDate = false;
      } else {
        dataObject[dbFields.fieldMetaDataList[mapping[idx][0]].jsonProperty] = rawData;
      }
    }
    result.push(dataObject);
  }
  return result;
}

export function dateCheck(mapping, dbFields) {
  let localDateExists = false;
  let localTimeExists = false;
  for (let idx in mapping) {
    let dbLabelType = dbFields.fieldMetaDataList[mapping[idx][0]].type;
    if (dbLabelType === "Date") {
      return;
    }
    if (dbLabelType === "LocalDate") {
      localDateExists = true;
    }
    if (dbLabelType === "LocalTime") {
      localTimeExists = true;
    }
  }
  if (localDateExists && !localTimeExists) {
    return "local time not specified when local date exists."
  }

  if (!localDateExists && localTimeExists) {
    return "local date not specified when local time exists."
  }
}