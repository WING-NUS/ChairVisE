// import {
//   SECTION_TYPE_BAR_CHART, SECTION_TYPE_DEPENDENCY_CHART
// } from './const'

export default {
  author_country_rank: {
    name: 'Top 6 of Author\'s Country',
    group: 'author record',
    description: 'Top countries of the conference paper author.',
    options: {
      maintainAspectRatio: false,
      legend: {
        display: false
      }
    },
    others: {
      numDisplay: 6
    }
  },
  author_country_rank_doughnut_chart: {
    name: 'Distribution of Author\'s Country',
    group: 'author record',
    description: 'Top countries of the conference paper author.',
    options: {
      maintainAspectRatio: false,
      legend: {
        display: true
      }
    },
    others: {
      numDisplay: 6
    }
  },
  submission_accept_distribution: {
    name: 'Submission Accept / Declined',
    group: 'submission record',
    description: 'Acceptance ratio of all submission papers.',
    options: {
      maintainAspectRatio: false,
      legend: {
        display: true
      }
    },
    others: {}
  },
  submission_keywords_word_cloud: {
    name: 'Submission Keywords Word Cloud',
    group: 'submission record',
    description: 'Top 20 Keywords including in submissions.',
    options: {
      from: 0,
      to: 0,
      numOfOrientation: 1
    },
    others: {}
  },
  top_submission_review_score: {
    name: 'Average Review Score of Top Submissions',
    group: 'review record',
    description: 'Top submissions information',
    options: {
      maintainAspectRatio: false,
      scales: {
        xAxes: [{
          ticks: {
            suggestedMin: 0,
            suggestedMax: 3
          }
        }],
        yAxes: [{
          ticks: {
            fontSize: 11
          }
        }]
      }
    },
    others: {
      numDisplay: 10
    }
  }
}
