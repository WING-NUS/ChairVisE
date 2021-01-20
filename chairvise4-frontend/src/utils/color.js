import Color from 'color'
import palette from './open-color'

const colors = [
  palette.red,
  palette.blue,
  palette.yellow,
  palette.green,
  palette.pink,
  palette.cyan,
  palette.orange,
  palette.lime,
  palette.grape,
  palette.indigo,
  palette.teal,
  palette.violet
]

const chooseColorSet = (size) => {
  const colorSet = []
  for (let i = 0; i < size; i++) {
    colorSet.push(colors[i % colors.length][Math.floor(i / colors.length) % colors[0].length])
  }
  return colorSet
}

export const generateBorderColor = (size) => {
  return chooseColorSet(size)
}

export const generateBackgroundColor = (size) => {
  const colorSet = chooseColorSet(size)
  return colorSet.map(hex => new Color(hex).alpha(0.8).string())
}

export const generateSingleColorSet = (size, theme, startFactor, factor) => {
  const baseColor = colors[theme][4]
  const colorSet = []
  let curFactor = startFactor
  for (let i = 0; i < size; i++) {
    colorSet.push(new Color(baseColor).alpha(curFactor).string())
    curFactor *= factor
  }
  return colorSet
}
