import { decode } from 'jsonwebtoken'

export function getUserName () {
  return decode(window.sessionStorage.getItem('token')).name
}

export function getUserEmail () {
  return decode(window.sessionStorage.getItem('token')).email
}
