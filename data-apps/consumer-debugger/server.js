const express = require('express')
const app = express()

var msg = 'NOTHING-YET'

var bodyParser = require('body-parser')
var options = {
  inflate: true,
  limit: '10kb',
  type: 'text/xml'
};
app.use( bodyParser.text(options) );

app.get('/', (req, res) => {
    res.send(msg)
})

app.post('/debug', (req, res) => {
    console.log(req.body)
    msg = req.body
    res.status(200).send({
        success: 'true'
    })
})

// Listen to the App Engine-specified port, or 8081 otherwise
const PORT = process.env.PORT || 8081
app.listen(PORT, () => {
  console.log(`Server listening on port ${PORT}...`)
})
