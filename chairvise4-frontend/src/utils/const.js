export const AccessLevel = Object.freeze({
  CAN_READ: 'CAN_READ',
  CAN_WRITE: 'CAN_WRITE'
})

export const ID_NEW_PRESENTATION = '__NEW__'

export const ID_NEW_CONFERENCE = '__NEW__'

export const SPECIAL_IDENTIFIER_PUBLIC = '%PUBLIC%'

export const SECTION_TYPE_WORD_CLOUD = 'word_cloud'
export const SECTION_TYPE_BAR_CHART = 'bar_chart'
export const SECTION_TYPE_PIE_CHART = 'pie_chart'
export const SECTION_TYPE_LINE_CHART = 'line_chart'
export const SECTION_TYPE_STATS = 'stats'
export const SECTION_TYPE_SCATTER_CHART = 'scatter_chart'
export const SECTION_TYPE_GRAPH_NETWORK = 'graph_network'
export const SECTION_TYPE_RADAR_CHART = 'radar'
export const SECTION_TYPE_DEPENDENCY_CHART = 'dependency_chart'

export const REVIEW_TABLE_ID = 1

export const REVIEW_DATE_DAY_FIELD = {
  name: 'Day of the Review Date',
  type: 'LocalDate'
}

export const REVIEW_DATE_TIME_FIELD = {
  name: 'Time of the Review Date',
  type: 'LocalTime'
}
// PDF
export const PDF_CHART_MARGIN_LEFT = 7 * 72 / 25.4
export const PDF_CHART_MARGIN_TOP = 15 * 72 / 25.4
export const PDF_CHART_WIDTH = 60 * 72 / 25.4
export const PDF_TITLE_MARGIN_LEFT = 7 * 72 / 25.4
export const PDF_TITLE_MARGIN_TOP = 20
export const PDF_DESCRIPTION_MARGIN_TOP = 25

export const PDF_TITLE_FONT_SIZE = 22

// PPTX
export const PPTX_CHART_MARGIN_LEFT = (1.0 * 72 / 25.4) / 12
export const PPTX_CHART_MARGIN_TOP = (0.75 * 72 / 25.4) / 12
export const PPTX_CHART_WIDTH = (40 * 72 / 25.4) / 12
export const PPTX_CHART_HEIGHT = (20 * 72 / 25.4) / 12

export const PPTX_TITLE_FONT_SIZE = 42
