import JavascriptPDF from "jspdf";
import html2canvas from "html2canvas";
import {
  DESCRIPTION_MARGIN_TOP,
  PDF_CHART_MARGIN_LEFT,
  PDF_CHART_MARGIN_TOP,
  PDF_CHART_WIDTH,
  TITLE_FONT_SIZE,
  TITLE_MARGIN_LEFT,
  TITLE_MARGIN_TOP
} from "@/common/const";

let doc, marginTop;

export function download(presentationFormName) {
  doc = new JavascriptPDF("p", "mm", "a4");
  marginTop = PDF_CHART_MARGIN_TOP;
  doc.setFontSize(TITLE_FONT_SIZE);
  doc.text(TITLE_MARGIN_LEFT, TITLE_MARGIN_TOP, presentationFormName);

  return createPDF(presentationFormName);
}

function getDescription() {
  return html2canvas(document.getElementById("presentation-description")).then(element => {
    let imageData = element.toDataURL("image/png");
    if (imageData === "data:,") {
      // according to documentation of toDataURL, if the canvas is of 0 width, 0 height,
      // the function will return string "data:,". If the canvas is empty, directly return
      return;
    }
    let descriptionHeight = element.height * PDF_CHART_WIDTH / element.width;
    doc.addImage(imageData, "PNG", PDF_CHART_MARGIN_LEFT, DESCRIPTION_MARGIN_TOP, PDF_CHART_WIDTH, descriptionHeight, "", "FAST");
  });
}

function getChart(chartElement, idx) {
  return html2canvas(chartElement).then(element => {
    if (idx > 0 && idx % 2 === 0) {
      doc.addPage();
      marginTop = PDF_CHART_MARGIN_TOP;
    }
    let imageData = element.toDataURL("image/png");
    let chartHeight = element.height * PDF_CHART_WIDTH / element.width;
    doc.addImage(imageData, "PNG", PDF_CHART_MARGIN_LEFT, marginTop, PDF_CHART_WIDTH, chartHeight, "", "FAST");
    marginTop = chartHeight + marginTop * 2;
  });
}

async function createPDF(pdfName) {
  await getDescription();
  let chartElements = document.getElementsByClassName("presentation-section");
  for (let i = 0; i < chartElements.length; i++) {
    await getChart(chartElements[i], i);
  }
  doc.save(pdfName + ".pdf");
}
