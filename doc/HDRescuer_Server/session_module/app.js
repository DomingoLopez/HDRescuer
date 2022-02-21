let createError = require('http-errors');
let express = require('express');
let path = require('path');
let cookieParser = require('cookie-parser');
let logger = require('morgan');
let bodyParser = require('body-parser');
let validator = require('express-validator');

//Declaraciones de los 'objetos' de ruta exportados para su uso
let gestor_rutas_api = require('./routes/gestor_rutas');



let app = express();



const mongoose = require('mongoose');
mongoose.connect('mongodb://mongodb/sessiondata', {useNewUrlParser: true, useUnifiedTopology: true});

const db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function() {
  console.log("Conectado a mongoDB. Modulo de gestión de sesiones");
});


app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
app.use(bodyParser.json());         // to support JSON-encoded bodies
app.use(bodyParser.urlencoded({     // to support URL-encoded bodies
    extended: true
}));



//Rutas para uso de la api
app.use('/api/sessions', gestor_rutas_api);


module.exports = app;
