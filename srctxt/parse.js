import fs from 'fs'

let srctxtPath = "fulltext.txt"
let lines = fs.readFileSync(srctxtPath).split("\n")
let characterNames = [
  'VLADIMIR',
  'ESTRAGON',
  'LUCKY',
  'POZZO'
]
