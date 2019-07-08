import Color from 'color';
import palette from './open-color'

const colors = [
  palette['red'],
  palette['blue'],
  palette['yellow'],
  palette['green'],
  palette['pink'],
  palette['cyan'],
  palette['orange'],
  palette['lime'],
  palette['grape'],
  palette['indigo'],
  palette['teal'],
  palette['violet'],
];

const chooseColorSet = (size) => {
  let colorSet = [];
  for (let i = 0; i < size; i++) {
    colorSet.push(colors[i % colors.length][Math.floor(i / colors.length) % colors[0].length]);
  }
  return colorSet;
};

export const generateBorderColor = (size) => {
  return chooseColorSet(size);
};

export const generateBackgroundColor = (size) => {
  let colorSet = chooseColorSet(size);
  return colorSet.map(hex => new Color(hex).alpha(0.5).string());
};